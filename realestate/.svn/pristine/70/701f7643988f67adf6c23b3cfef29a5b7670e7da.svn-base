package cn.gtmap.realestate.etl.quartz;


import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.enums.HlwSlztEnum;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.etl.core.domian.wwsq.GxWwSqxmDo;
import cn.gtmap.realestate.etl.core.service.HlwYwxxDataService;
import cn.gtmap.realestate.etl.service.HlwYwxxService;
import cn.gtmap.realestate.etl.util.Constants;
import cn.gtmap.server.utils.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021-11-02
 * @description 外网申请 相关定时任务
 */
@Service
@ConditionalOnProperty("gx.wwsqcj.enable")
public class WwsqQuarzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WwsqQuarzService.class);

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 外网申请创建最大值
     */
    private static final Integer WWSQCJ_MAX = 1000;

    private static final String WW_UPDATE_SLZT = "wwsqupdateslzt";

    @Autowired
    private HlwYwxxDataService hlwYwxxDataService;

    @Autowired
    private HlwYwxxService hlwYwxxService;

    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    ProcessInstanceClientMatcher processInstanceClientMatcher;

    @Autowired
    BdcCzrzFeignService bdcCzrzFeignService;

    /**
     * 是否开启监听读取共享库外网申请数据进行创件功能
     */
    @Value("${gx.wwsqcj.enable:false}")
    private Boolean openWwsqListener;

    /**
     * 是否开启重试
     */
    @Value("${gx.wwsqcjcxcj.enable:false}")
    private Boolean openWwsqcjcxcj;

    /**
     * 是否开启状态通知
     */
    @Value("${gx.wwsqcjzttx.enable:false}")
    private Boolean openWwsqcjzttx;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 定时任务一次创建数量
     */
    @Value("${gx.wwsqcj.cjsl:100}")
    private Integer cjsl;

    @Scheduled(cron = "${gx.wwsqcj.cron:0 0/30 * * * ?}")
    @RedissonLock(lockKey = Constants.QUARTZ_WWSQCJ_JOB_NAME, description = "外网申请创建定时任务", waitTime = 30L, leaseTime = 600L)
    public void listenWwsqcj() {
        try {
            if (!Boolean.TRUE.equals(openWwsqListener)) {
                LOGGER.info("外网申请定时任务结束,未开启外网申请创建定时任务");
                return;
            }
            //一次允许创建的数量
            Integer cjNumMax = cjsl < WWSQCJ_MAX ? cjsl : WWSQCJ_MAX;
            List<GxWwSqxmDo> sqxmDoList = hlwYwxxDataService.listWcjYhWwsqXmidList();
            Integer count = CollectionUtils.isNotEmpty(sqxmDoList) ? sqxmDoList.size() : 0;
            LOGGER.info("开始执行定时外网申请创建任务,当前获取到未创建流程：{}条,本次允许创建流程最大数量：{}", count, cjNumMax);
            cjBdcXm(cjNumMax, sqxmDoList, false);
        } catch (Exception e) {
            LOGGER.error("执行定时外网申请创建任务报错：{}", e.getMessage(), e);
        }
    }

    private void cjBdcXm(Integer cjNumMax, List<GxWwSqxmDo> sqxmDoList, Boolean cxcj) {
        Integer cjNum = 0;
        Integer successNum = 0;
        Integer failNum = 0;
        if (CollectionUtils.isNotEmpty(sqxmDoList)) {
            for (GxWwSqxmDo gxWwSqxmDo : sqxmDoList) {
                Map<String, Object> resultMap = new HashMap<>();
                try {
                    resultMap = hlwYwxxService.cjBdcXm(gxWwSqxmDo.getXmid(), gxWwSqxmDo.getSqslbh(), "", cxcj);
                } catch (Exception e) {
                    LOGGER.error("外网项目ID:{} 外网编号：{}创建失败，异常信息:{}", gxWwSqxmDo.getXmid(), gxWwSqxmDo.getSqslbh(), e);
                    try {
                        JSONObject hxObject = new JSONObject();
                        hxObject.put("xmid", gxWwSqxmDo.getXmid());
                        hxObject.put("byslyy", "系统内部服务异常");
                        hxObject.put("shzt", Constants.SHZT_ABANDON);
                        hxObject.put("czrmc", "系统退回");
                        hlwYwxxService.hxDjxx(hxObject);
                    } catch (Exception error) {
                        LOGGER.error("外网项目ID:{} 外网编号：{}通知互联网异常,异常信息:{}", gxWwSqxmDo.getXmid(), gxWwSqxmDo.getSqslbh(),error);
                    }
                }
                cjNum++;
                if (StringUtils.isNotBlank(MapUtils.getString(resultMap, "gzlslid"))) {
                    successNum++;
                    LOGGER.info("外网项目ID:{}外网编号：{}创建成功，登记流程工作流实例ID:{}", gxWwSqxmDo.getXmid(), gxWwSqxmDo.getSqslbh(), MapUtils.getString(resultMap, "gzlslid"));
                } else {
                    failNum++;
                    String msg = MapUtils.getString(resultMap, "msg");
                    LOGGER.info("外网项目ID:{}外网编号：{}创建失败，异常信息:{}", gxWwSqxmDo.getXmid(), gxWwSqxmDo.getSqslbh(), StringUtils.isNotBlank(msg) ? msg : "创建异常,具体参考异常输出");
                }
                if (cjNum.equals(cjNumMax)) {
                    break;
                }
            }
        }
        LOGGER.info("执行定时外网申请创建任务结束,本次创建成功：{}条,创建失败：{}条", successNum, failNum);
    }


    @Scheduled(cron = "${gx.wwsqcjcxcj.cron:0 0 23 * * ?}")
    @RedissonLock(lockKey = Constants.QUARTZ_CXWWSQCJ_JOB_NAME, description = "外网申请重新创建定时任务", waitTime = 30L, leaseTime = 600L)
    public void reWwsqcj() {
        if (!Boolean.TRUE.equals(openWwsqcjcxcj)) {
            LOGGER.info("外网申请定时任务结束,未开启外网申请重新创建定时任务");
            return;
        }
        try {
            //筛选出所有的失败的xmid
            Date dayBeginTime = DateUtil.getDayBeginTime(new Date());
            Date dayEndTime = DateUtil.getDayEndTime(new Date());
            List<String> sbXmids = hlwYwxxDataService.listFailedWwsqXmidList(dayBeginTime, dayEndTime);
            if (CollectionUtils.isEmpty(sbXmids)) {
                LOGGER.info("开始执行定时外网申请失败重新创建任务,未找到失败项目");
                return;
            }
            LOGGER.info("开始执行定时外网申请失败重新创建任务{}条", sbXmids.size());
            //分批执行
            List<List<String>> partition = Lists.partition(sbXmids, 100);
            for (List<String> xmids : partition) {
                //查找出原始的信息
                List<GxWwSqxmDo> sqxmDoList = hlwYwxxDataService.listWwsqListByXmids(xmids);
                if (CollectionUtils.isNotEmpty(sqxmDoList)) {
                    cjBdcXm(sqxmDoList.size() + 1, sqxmDoList, true);
                }
            }
        } catch (Exception e) {
            LOGGER.error("执行定时外网申请重新创建任务报错：{}", e.getMessage(), e);
        }
    }


    @Scheduled(cron = "${gx.wwsqcjzttx.cron:0 0 23 * * ?}")
    @RedissonLock(lockKey = Constants.QUARTZ_WWSQZTTZ_JOB_NAME, description = "外网申请状态通知定时任务", waitTime = 30L, leaseTime = 600L)
    public void updateSlzt() {
        if (!Boolean.TRUE.equals(openWwsqcjzttx)) {
            LOGGER.info("外网申请定时任务结束,未开启外网申请重新创建定时任务");
            return;
        }
        try {
            //筛选出所有的今天创建的xmid
            Date dayBeginTime = DateUtil.getDayBeginTime(new Date());
            Date dayEndTime = DateUtil.getDayEndTime(new Date());
            List<String> gzlslids = hlwYwxxDataService.listSuccessWwsqXmidList(dayBeginTime, dayEndTime);
            if (CollectionUtils.isEmpty(gzlslids)) {
                LOGGER.info("定时更新受理状态,未找到项目");
                return;
            }
            LOGGER.info("定时更新受理状态任务{}条", gzlslids.size());
            for (String gzlslid : gzlslids) {
                try {
                    //获取xm状态
                    BdcXmQO bdcXmQO = new BdcXmQO();
                    bdcXmQO.setGzlslid(gzlslid);
                    List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isEmpty(bdcXmDOS)) {
                        continue;
                    }
                    BdcXmDO bdcXmDO = bdcXmDOS.get(0);
                    LOGGER.info("定时更新受理状态任务gzlslid{}外网编号：{}", gzlslid,bdcXmDO.getSpxtywh());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("processInsId", gzlslid);
                    //目前只需要处理办结和撤回状态
                    if (bdcXmDO.getAjzt().equals(CommonConstantUtils.AJZT_YHCH_DM)) {
                        jsonObject.put("isDelete", HlwSlztEnum.ABANDON.getSlzt());
                        //获取退回意见
                        BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
                        bdcCzrzDO.setGzlslid(gzlslid);
                        bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_WWTJ.key());
                        List<BdcCzrzDO> bdcCzrzDOS = bdcCzrzFeignService.listBdcCzrz(bdcCzrzDO);
                        if(CollectionUtils.isNotEmpty(bdcCzrzDOS) && StringUtils.isNotBlank(bdcCzrzDOS.get(0).getCzyy())){
                            jsonObject.put("opinion", bdcCzrzDOS.get(0).getCzyy());
                        }
                    } else if (bdcXmDO.getAjzt().equals(CommonConstantUtils.AJZT_YB_DM)) {
                        jsonObject.put("isDelete", HlwSlztEnum.SZDB.getSlzt());
                    } else {
                        continue;
                    }
                    LOGGER.info("定时更新受理状态，hlwslh:{},推送信息:{}", bdcXmDO.getSpxtywh(), jsonObject);
                    exchangeInterfaceFeignService.requestInterface(WW_UPDATE_SLZT, jsonObject);
                } catch (Exception error) {
                    LOGGER.error("外网项目ID:{} 通知互联网异常,异常信息:{}", gzlslid, error);
                }
            }
        } catch (Exception e) {
            LOGGER.error("执行定时外网申请重新创建任务报错：{}", e.getMessage(), e);
        }
    }
}
