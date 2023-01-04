package cn.gtmap.realestate.exchange.quartz;

import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlPjqDO;
import cn.gtmap.realestate.common.core.dto.accept.SlymPjqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.access.MsgNoticeDTO;
import cn.gtmap.realestate.common.core.enums.AccessWarningEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlPjqFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeCxqq;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.sjpt.CxjgService;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogTypeService;
import cn.gtmap.realestate.exchange.util.DateUtil;
import cn.gtmap.realestate.exchange.util.constants.SjptConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-29
 * @description 省级平台 相关定时任务
 */
@Component
@EnableScheduling
public class SjptQuarzService {

    @Autowired
    private CxjgService cxjgService;

    // 查询申请 定时任务处理是否结束标志
    private static boolean GET_CXSQ_FINISHED = true;

    // 查询结果提交 定时任务处理是否结束标志
    private static boolean COMMIT_CXJG_FINISHED = true;

    private static final Logger LOGGER = LoggerFactory.getLogger(SjptQuarzService.class);

    // 查询申请的 服务ID
    private static final String CXSQ_BEANNAME = "sjpt_saveCxsqWithLog";

    // 查询结果提交的 服务ID
    private static final String CXJG_BEANNAME = "sjpt_commitCxjgWithLog";

    @Autowired
    private EntityMapper sjptEntityMapper;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    BdcdjMapper bdcdjMapper;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    ProcessTaskClient processTaskClient;
    @Autowired
    BdcSlPjqFeignService bdcSlPjqFeignService;
    @Autowired
    AccessLogTypeService accessLogTypeService;
    /**
     * 需要推送评价数据的qxdm集合
     */
    @Value("#{'${tspjsj.qxdmlist:}'.split(',')}")
    private List<String> tspjsjQxdmList;

    /**
     * 定时自动上报评价数据任务是否开启
     */
    @Value("${quartz.tspjsj.enable:false}")
    private boolean tspjsjEnable;

    /**
     * @param
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 定时任务获取查询请求
     */
    @Scheduled(cron = "${sjpt.cxsq.cron:0 0 23 * * ?}")
    @RedissonLock(lockKey = CommonConstantUtils.REDISSON_LOCK_SJPTCXSQ, description = "省级平台定时任务获取查询请求", waitTime = 10L, leaseTime = 600L)
    public void getCxsq() {
        if (StringUtils.equals(EnvironmentConfig.getEnvironment()
                .getProperty("sjpt.cxsq.switch"), "true") && GET_CXSQ_FINISHED) {
            GET_CXSQ_FINISHED = false;
            try {
                ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(CXSQ_BEANNAME);
                if (exchangeBean != null) {
                    InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean, new HashMap<>());
                    requestBuilder.invoke();
                } else {
                    throw new AppException("无法获取名称为：" + CXSQ_BEANNAME + "的配置信息");
                }
            } catch (Exception e) {
                LOGGER.error("定时请求查询申请异常", e);
            } finally {
                GET_CXSQ_FINISHED = true;
            }
        }
    }

    /**
     * @param
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 定时任务  执行查询请求
     */
    @Scheduled(cron = "${sjpt.queryCxjg.cron:0 0 23 * * ?}")
    @RedissonLock(lockKey = "REDISSON_LOCK_SJPTQUERYCXSQ", description = "省级平台定时任务执行查询请求", waitTime = 10L, leaseTime = 600L)
    public void queryCxjg() {
        if (StringUtils.equals(EnvironmentConfig.getEnvironment()
                .getProperty("sjpt.queryCxjg.switch"), "true")
                && cxjgService.checkLastQuarzFinished()) {
            LOGGER.info("开始定时任务查询申请处理");
            cxjgService.executeQueryCxsq(SjptConstants.SJPT_QUARTZ_INTERFACE_CALL_METHOD_SYSTEM);
        }
    }

    /**
     * @param
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 定时任务 提交查询结果(获取未推送的单号  循环提交)
     */
    @Scheduled(cron = "${sjpt.commitCxjg.cron:0 0 23 * * ?}")
    @RedissonLock(lockKey = "REDISSON_LOCK_SJPTCOMMITCXSQ", description = "省级平台定时任务提交查询请求", waitTime = 10L, leaseTime = 600L)
    public void commitCxjg() {
        if (StringUtils.equals(EnvironmentConfig.getEnvironment()
                .getProperty("sjpt.commitCxjg.switch"), "true")
                && COMMIT_CXJG_FINISHED) {
            COMMIT_CXJG_FINISHED = false;
            try {
                LOGGER.info("开始定时任务提交查询申请处理");
                cxjgService.executeCommitCxsq(SjptConstants.SJPT_QUARTZ_INTERFACE_CALL_METHOD_SYSTEM);
            } catch (Exception e) {
                LOGGER.error("定时提交查询结果异常", e);
            } finally {
                COMMIT_CXJG_FINISHED = true;
            }
        }
    }

    /**
     * 每天定时推送评价数据到省平台
     *
     * @param
     * @return
     * @Date 2022/8/23
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Scheduled(cron = "${sjpt.tspjsj.cron:0 0 20 * * ?}")
    @RedissonLock(lockKey = CommonConstantUtils.REDISSON_LOCK_PJSJTS, description = "推送评价数据定时任务", waitTime = 30L, leaseTime = 600L)
    public void commoitPjsj() {
        if (tspjsjEnable) {
            //当天日期
            Date pjsjDate = DateUtil.getCurDate();
            LOGGER.info("开始推送当天：{}，评价数据，推送的区县为：{}", pjsjDate, tspjsjQxdmList.toString());

            BdcXmQO xmQO = new BdcXmQO();
            xmQO.setSlsjDate(pjsjDate);
            if (CollectionUtils.isNotEmpty(tspjsjQxdmList)) {
                xmQO.setQxdmList(tspjsjQxdmList);
            }
            List<BdcXmDO> bdcXmDOList = bdcdjMapper.listBdcXmByslsj(xmQO);
            //根据slbh去重
            List<BdcXmDO> bdcXmDOS = bdcXmDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getSlbh()))), ArrayList::new));
            if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
                for (BdcXmDO bdcXmDO : bdcXmDOS) {
                    //查询评价表是否存在数据，存在则这条不推送
                    BdcSlPjqDO bdcSlPjqDO = bdcSlPjqFeignService.queryBdcSlPjqBySlbh(bdcXmDO.getSlbh());
                    if (null != bdcSlPjqDO) {
                        LOGGER.info("评价表中已存在改slbh的评价数据！受理编号：{}", bdcXmDO.getSlbh());
                        continue;
                    }
                    //组织推送省厅数据
                    SlymPjqDTO slymPjqDTO = new SlymPjqDTO();
                    slymPjqDTO.setMyd("1");
                    slymPjqDTO.setBdcXmDO(bdcXmDO);
                    slymPjqDTO.setSlbh(bdcXmDO.getSlbh());
                    slymPjqDTO.setSlr(bdcXmDO.getSlr());
                    slymPjqDTO.setQxdm(bdcXmDO.getQxdm());
                    List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listAllBdcQlr(bdcXmDOList.get(0).getGzlslid(), CommonConstantUtils.QLRLB_QLR, "");
                    //同时根据名称和证件号去重
                    List<BdcQlrDO> bdcQlrDOS = bdcQlrDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getQlrmc() + ";" + o.getZjh()))), ArrayList::new));
                    if (CollectionUtils.isNotEmpty(bdcQlrDOS)) {
                        slymPjqDTO.setQlrmc(StringToolUtils.resolveBeanToAppendStr(bdcQlrDOS, "getQlrmc", ","));
                        slymPjqDTO.setQlrlxfs(StringToolUtils.resolveBeanToAppendStr(bdcQlrDOS, "getDh", ","));
                    }

                    slymPjqDTO.setJdmc("受理");

                    slymPjqDTO.setPjsj(bdcXmDO.getSlsj());
                    // 推送评价结果至省厅
                    Object response = exchangeBeanRequestService.request("send_pjxx_to_sf", slymPjqDTO);
                    LOGGER.info("推送评价数据，省厅返回：{}", response.toString());
                    // 保存评价结果信息
                    LOGGER.info("开始保存评价数据，slbh:{}", bdcXmDO.getSlbh());
                    try {
                        this.bdcSlPjqFeignService.insertBdcSlPjq(convertPjqjg(slymPjqDTO));
                    } catch (Exception e) {
                        LOGGER.info("更新评价数据上报状态失败！失败原因：{}，失败数据为：{}", e, slymPjqDTO.toString());
                    }
                }
            } else {
                LOGGER.info("当天受理项目为0！slsj：{}", pjsjDate);
            }
        } else {
            LOGGER.info("定时推送评价数据任务没开！：{}", tspjsjEnable);
        }
    }

    /**
     * 评价数据对照
     *
     * @param
     * @return
     * @Date 2022/8/23
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    private BdcSlPjqDO convertPjqjg(SlymPjqDTO slymPjqDTO) {
        BdcSlPjqDO bdcSlPjqDO = new BdcSlPjqDO();
        BeanUtils.copyProperties(slymPjqDTO, bdcSlPjqDO);
        bdcSlPjqDO.setYwbh(slymPjqDTO.getSlbh());
        BdcXmDO bdcXmDO = slymPjqDTO.getBdcXmDO();
        bdcSlPjqDO.setBlry(bdcXmDO.getSlr());
        bdcSlPjqDO.setBlryid(bdcXmDO.getSlrid());
        bdcSlPjqDO.setYwmc(bdcXmDO.getGzldymc());
        bdcSlPjqDO.setYwblsj(bdcXmDO.getSlsj());
        bdcSlPjqDO.setDjbmdm(bdcXmDO.getDjbmdm());
        bdcSlPjqDO.setDjbmmc(bdcXmDO.getDjjg());
        bdcSlPjqDO.setSqrxm(slymPjqDTO.getQlrmc());
        bdcSlPjqDO.setSqrlxfs(slymPjqDTO.getQlrlxfs());
        return bdcSlPjqDO;
    }

    /**
     * @param
     * @return void
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 定时任务 省级查询
     */
//    @Scheduled(cron = "${sjpt.warnCxjg.cron:0 */1 * * * ?}")
    @Scheduled(cron = "${sjpt.warnCxjg.cron:0 0 20 * * ?}")
    @RedissonLock(lockKey = CommonConstantUtils.REDIS_SJPT_CXSQ, description = "定时任务提醒查询申请状态", waitTime = 10L, leaseTime = 600L)
    public void warnCxjg() {
        //查询已查询，未上报的数量
        List<GxPeCxqq> ycxwtsList = getGxPeCxqqs(SjptConstants.CXQQ_ZT_YCLWTS);
        if (CollectionUtils.isNotEmpty(ycxwtsList)) {
            /*
             * 发送消息提醒
             * */
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYwlx("省级查询申请");
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_18.getYjlx());
            msgNoticeDTO.setSlbh(String.valueOf(ycxwtsList.size()));
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
        }
        //查询超时的数量
        List<GxPeCxqq> cjList = getGxPeCxqqs(SjptConstants.CXQQ_ZT_CS);
        if (CollectionUtils.isNotEmpty(cjList)) {
            /*
             * 发送消息提醒
             * */
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYwlx("省级查询申请");
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_17.getYjlx());
            msgNoticeDTO.setSlbh(String.valueOf(cjList.size()));
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
        }
        //查询未处理的数量
        List<GxPeCxqq> wclList = getGxPeCxqqs(SjptConstants.CXQQ_ZT_WCL);
        if (CollectionUtils.isNotEmpty(wclList)) {
            /*
             * 发送消息提醒
             * */
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYwlx("省级查询申请");
//            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_18.getYjlx());
            msgNoticeDTO.setYjlx("19");
            msgNoticeDTO.setSlbh(String.valueOf(wclList.size()));
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
        }
        //查询部分查询的数量
        List<GxPeCxqq> bfcxList = getGxPeCxqqs(SjptConstants.CXQQ_ZT_BFCL);
        if (CollectionUtils.isNotEmpty(bfcxList)) {
            /*
             * 发送消息提醒
             * */
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYwlx("省级查询申请");
//            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_18.getYjlx());
            msgNoticeDTO.setYjlx("20");
            msgNoticeDTO.setSlbh(String.valueOf(bfcxList.size()));
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
        }


    }

    public List<GxPeCxqq> getGxPeCxqqs(String zt) {
        if (StringUtils.isNotBlank(zt)) {
            Date date = new Date();
            Example example = new Example(GxPeCxqq.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andFormatDateEqualTo("cjsj", DateUtils.format(date, "yyyy-MM-dd"), "yyyy-MM-dd");
            criteria.andEqualTo("zt", zt);
            return sjptEntityMapper.selectByExample(example);
        }
        return null;

    }


}
