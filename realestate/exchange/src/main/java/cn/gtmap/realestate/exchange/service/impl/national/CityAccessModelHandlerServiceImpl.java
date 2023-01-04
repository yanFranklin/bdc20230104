package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.domain.exchange.*;
import cn.gtmap.realestate.common.core.domain.exchange.uniformity.*;
import cn.gtmap.realestate.common.core.dto.exchange.access.MsgNoticeDTO;
import cn.gtmap.realestate.common.core.enums.AccessWarningEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcDjxlPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.exchange.core.domain.BdcDbrzjlXqDO;
import cn.gtmap.realestate.exchange.core.domain.BdcJrDbrzjlDO;
import cn.gtmap.realestate.exchange.core.dto.AccessDyjeInfo;
import cn.gtmap.realestate.exchange.core.dto.common.BdcXmForZxAccessDTO;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.common.MessageModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlDyaqOldDO;
import cn.gtmap.realestate.exchange.core.dto.ywhjsb.BdcWlxmJrCzrzDTO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.accessLog.AccessLogMapper;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.core.national.CityAccess;
import cn.gtmap.realestate.exchange.core.qo.BdcDbrzQO;
import cn.gtmap.realestate.exchange.core.qo.BdcJrrzQO;
import cn.gtmap.realestate.exchange.core.thread.CityAccessSingleThread;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.gx.GxService;
import cn.gtmap.realestate.exchange.service.national.*;
import cn.gtmap.realestate.exchange.service.national.access.AccessDefaultValueService;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogTypeService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.DoubleUtils;
import cn.gtmap.realestate.exchange.util.*;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import cn.gtmap.realestate.exchange.util.enums.JrRzCgbsEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CityAccessModelHandlerServiceImpl implements CityAccesssModelHandlerService  {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CityAccessModelHandlerServiceImpl.class);

    /**
     * 批量项目抵押金额数据 redisKey
     */
    private static final String ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_REDIS_KEY = "ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_";

    @Autowired
    private XmlEntityConvertUtil xmlEntityConvertUtil;
    @Autowired
    private BdcXmMapper bdcXmMapper;
    @Autowired
    private BdcExchangeZddzService bdcExchangeZddzService;
    @Autowired
    private NationalAccessXmlContext nationalAccessXmlContext;
    @Autowired
    private GxService gxService;
    @Autowired
    private AccessDefaultValueService accessDefaultValueService;
    @Autowired
    private NationalAccessHeadModelService nationalAccessHeadModelService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Resource(name = "nationalAccessXmlZxWlxmdj")
    private NationalAccessXmlService nationalAccessXmlZxWlxmdj;
    @Autowired
    private AccessLogMapper accessLogMapper;
    @Autowired
    private AccessModelServiceThread accessModelServiceThread;
    @Autowired
    private ThreadEngine threadEngine;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    private AccessLogTypeService accessLogTypeService;
    @Autowired
    private CommonService commonService;
    @Autowired
    ComputeTaskTimeUtils computeTaskTimeUtils;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    AsyncDealUtils asyncDealUtils;
    @Autowired
    BdcDjxlPzFeignService bdcDjxlPzFeignService;

    @Autowired
    AccesssModelHandlerService accesssModelHandlerService;

    @Autowired
    BdcdjMapper bdcdjMapper;

    // 批量抵押单个

    @Value("${access.pldy.single:false}")
    private boolean accessPldy;
    /**
     * 判断是否在上报批量抵押业务时  处理抵押金额
     */
    @Value("${access.pldy.dealDyje:false}")
    private boolean accessPldyDealDyje;

    /**
     * 判断是否在上报批量抵押业务时 修改头信息中的业务流水号和所有实体中的ywh字段 为 SLBH
     */
    @Value("${access.pldy.dealYwh:false}")
    private boolean accessPldyDealYwh;

    @Value("${access.batch.process.thread.threshold.value:1000}")
    private int thresholdValue;

    @Value("${access.batch.process.thread.step.value:500}")
    private int stepValue;

    @Value("${access.filter.history.data.flag:false}")
    private boolean accessFilterHistoryDataFlag;
    @Value("${access.newAccess:true}")
    private boolean newAccessModel;
    @Value("#{'${access.outTimeGzldyidArr:}'.split(',')}")
    private List<String> outTimeGzldyidArr;
    @Value("#{'${access.dyaGzldyidArr:}'.split(',')}")
    private List<String> dyaGzldyidArr;
    /**
     * 56981 【盐城】省级限制上报抵押业务逻辑调整
     * 上一手产权项目，是存量房流程的工作流实例id
     */
    @Value("#{'${access.clfzyGzldyidArr:}'.split(',')}")
    private List<String> clfzyGzldyidArr;
    @Value("${outWorkDay:1.0}")
    private Double outWorkDay;
    @Value("${dyaMonth:6}")
    private int dyaMonth;
    /**
     * //big标识开关
     */
    @Value("${accessLog.turn-on-bizbs: false}")
    private boolean accessTurnOnBizBs;
    /*
     * 新上报标识逻辑是否开启
     * */
    @Value("${access.newbizbs: false}")
    private boolean newAccessBizBs;
    /**
     * 是否开启上报线程开关
     */
    @Value("${access.enable.thread:false}")
    private Boolean accessEnableThread;
    /**
     * 是否开启一致性上报
     */
    @Value("${accessLog.turn-on-yzx: false}")
    private boolean accessTurnOnYzx;
    /**
     * //合肥目前不需要新版上报默认值
     */
    @Value("${access.newDefault:true}")
    public boolean newDefault;
    /*
     * 业务汇交上报校验
     * */
    @Value("#{'${access.check.gzyzCode:1,2,3}'.split(',')}")
    private List<String> gzyzCodeList;
    /*
     * 配置不上报的xmly
     * */
    @Value("#{'${access.bsb.xmly:2,3}'.split(',')}")
    private List<Integer> bsbXmlyList;

    @Override
    public void cityAccessByProcessInsId(String processInsId) {
        LOGGER.info("开始市级上报汇交的gzlslid，{}", processInsId);
        if (StringUtils.isBlank(processInsId)) {
            return;
        }
        List<BdcXmDO> bdcXmList = bdcXmMapper.queryBdcXmList(processInsId);
        if (CollectionUtils.isEmpty(bdcXmList)) {
            LOGGER.error("市级数据汇交 - gzlslid:{}- 未查找到工作流实例相关项目", processInsId);
            return;
        }
        // 业务
        Integer lclx = bdcXmFeignService.makeSureBdcXmLx(bdcXmList);
        // 如果是批量抵押业务 只上报一条处理逻辑
        if (CommonConstantUtils.LCLX_PL.equals(lclx) && CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmList.get(0).getQllx()) && accessPldy) {
            BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(bdcXmList.get(0).getXmid());
            if (bdcCshFwkgSlDO != null && CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfscql())) {
                LOGGER.info("开始上报批量抵押汇交的gzlslid，{}", processInsId);
                access(bdcXmList.get(0), bdcXmList);
                return;
            }
        }
        // 批量组合的项目+抵押上报一条开+生成权利 处理
        bdcXmList = accessIfLclxPlzhNeeded(bdcXmList, processInsId, lclx);
        if (accessEnableThread && bdcXmList.size() > 1) {
            List<CityAccessSingleThread> listThread = new ArrayList<>();
            for (BdcXmDO bdcXmDO : bdcXmList) {
                listThread.add(new CityAccessSingleThread(this, bdcXmDO, bdcXmList));
            }
            threadEngine.excuteThread(listThread, true);
        } else {
            for (BdcXmDO bdcXmDO : bdcXmList) {
                access(bdcXmDO, bdcXmList);
            }
        }
        // 取不为空的不动产项目的ids
        List<String> xmids = bdcXmList.stream().map(BdcXmDO::getXmid).filter(StringUtils::isNotBlank).collect(Collectors.toList());
        //处理注销原权利的外联项目
        Set<BdcXmForZxAccessDTO> bdcXmForZxAccessDTOSet = commonService.queryBdcXmForZxAccessListByXmidsAndWlxmAndZxyql(xmids);
        if (CollectionUtils.isNotEmpty(bdcXmForZxAccessDTOSet)) {
            accessWlxm(bdcXmForZxAccessDTOSet);
        }
    }

    @Override
    public void singleAccess(BdcXmDO bdcXmDO, List<BdcXmDO> bdcXmDOList) {
        access(bdcXmDO, bdcXmDOList);
    }

    /**
     * @return
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 流程类型批量组合
     * @Date 2022/5/5
     * @Param
     **/
    private List<BdcXmDO> accessIfLclxPlzhNeeded(List<BdcXmDO> bdcXmList, String pId, Integer lclx) {
        if (CommonConstantUtils.LCLX_PLZH.equals(lclx)) {
            // 非抵押的不动产项目
            List<BdcXmDO> otherQlBdcXmList = bdcXmList.stream()
                    .filter(item -> !CommonConstantUtils.QLLX_DYAQ_DM.equals(item.getQllx()))
                    .collect(Collectors.toList());
            // 抵押的不动产项目中的第一个
            BdcXmDO bdcXmDO = bdcXmList.stream()
                    .filter(item -> CommonConstantUtils.QLLX_DYAQ_DM.equals(item.getQllx()))
                    .findFirst().orElse(null);
            // 是否需要过滤抵押上报的第一个项目
            Boolean ifNeedFilter = false;
            if (Objects.nonNull(bdcXmDO) && accessPldy) {
                BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(bdcXmDO.getXmid());
                if (bdcCshFwkgSlDO != null && CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfscql())) {
                    LOGGER.info("开始上报批量组合汇交的gzlslid，{}", pId);
                    ifNeedFilter = true;
                    access(bdcXmDO, otherQlBdcXmList);
                }
            }
            return Boolean.TRUE.equals(ifNeedFilter)
                    ? bdcXmList.stream()
                    .filter(item -> !StringUtils.equals(bdcXmDO.getXmid(), item.getXmid()))
                    .collect(Collectors.toList())
                    : bdcXmList;
        }
        return bdcXmList;
    }

    /**
     * @param bdcXmDO
     * @param bdcXmDOList 用于处理一些逻辑，为了防止每次都查就改到外面传，可以不传。
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理自动汇交具体方法
     */
    private void access(BdcXmDO bdcXmDO, List<BdcXmDO> bdcXmDOList) {
        //刚开始就记录数据到接入表中，cgbs为3，等待组织报文
        LOGGER.info("====================市级刚开始上报，存入数据库接入表一条======================:{}", bdcXmDO.getXmid());
        //数据接收，异步存入接入操作日志表，此时没有生成bwid
        asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 1, "服务接收到需要上报的项目信息，下一步进行配置文件校验", "", new Date(), "1");
        saveBdcAccess(new CityAccess(), JrRzCgbsEnum.BEGIN_WSB.getBs(), bdcXmDO, "");

        //判断是否需要上报，不上报存入一条数据到接入表，且上报状态为-2 不需要上报
        if (!sfsb(bdcXmDO, "", true)) {
            LOGGER.info("当前项目不需要上报，结束上报服务,bdcXmDO.getXmid：{}", bdcXmDO.getXmid());
            return;
        }

        MessageModel messageModel = new MessageModel();
        //旧版上报模型
        MessageModelOld messageModelOld;
        try {
            LOGGER.info("开始上报汇交,bdcXmDO.getXmid：{}", bdcXmDO.getXmid());
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                bdcXmDOList = bdcXmMapper.queryBdcXmList(bdcXmDO.getGzlslid());
            }
            // 获取不同类型的策略 service
            NationalAccessXmlService nationalAccessXmlService = getAccessXmlService(bdcXmDO);
            // 利用对应策略的 service 生成 message
            if (!ObjectUtils.isEmpty(nationalAccessXmlService)) {
                //异步保存结果日志
                asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 2, "根据登记类型" + bdcXmDO.getDjlx() + "成功找到对应的上报策略服务，下一步组织报文", "", new Date(), "1");
                // 获取 message
                if (newAccessModel) {
                    //新上报实体
                    try {
                        messageModel = getMessageModel(nationalAccessXmlService, bdcXmDO.getXmid());
                        //组织报文成功，获取到bwid
                        asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 3, "上报报文数据组织成功，生成报文id", messageModel.getHeadModel().getBizMsgID(), new Date(), "1");
                        int lclx = bdcXmFeignService.makeSureBdcXmLx(bdcXmDOList);
                        // 如果是批量抵押业务  则 单独处理批量
                        if (CommonConstantUtils.LCLX_PL.equals(lclx) && CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
                            // 批量抵押 处理所有业务号为SLBH 主要适用于安徽省 解决抵押总金额问题
                            if (accessPldyDealYwh) {
                                pldyDealMessageModelYwh(messageModel, bdcXmDOList);
                            }
                            // 批量抵押 处理抵押金额
                            if (accessPldyDealDyje) {
                                pldyDealMessageModelDyje(messageModel, bdcXmDOList);
                            }
                        } else if (CommonConstantUtils.LCLX_PLZH.equals(lclx) && CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
                            //组合批量抵押数据要根据证书序号来确定上报拆分逻辑
                            String key = getZsxhOrDjxlByBdcxm(bdcXmDO);
                            List<BdcXmDO> sameZsxhBdcXmList = new ArrayList<>();
                            for (BdcXmDO bdcXmTemp : bdcXmDOList) {
                                if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmTemp.getQllx()) && StringUtils.equals(key, getZsxhOrDjxlByBdcxm(bdcXmTemp))) {
                                    sameZsxhBdcXmList.add(bdcXmTemp);
                                }
                            }
                            if (CollectionUtils.isNotEmpty(sameZsxhBdcXmList)) {
                                if (accessPldyDealYwh) {
                                    pldyDealMessageModelYwh(messageModel, sameZsxhBdcXmList);
                                } else if (accessPldyDealDyje) {
                                    pldyDealMessageModelDyje(messageModel, sameZsxhBdcXmList);
                                }
                            }
                        }
                        //组合抵押流程上报 scywh处理(组合流程)
                        if ((CommonConstantUtils.LCLX_PLZH.equals(lclx) || CommonConstantUtils.LCLX_ZH.equals(lclx)) && CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
                            Map<String, String> queryYxmidMap = new HashMap<>(2);
                            queryYxmidMap.put("xmid", bdcXmDO.getXmid());
                            List<BdcXmLsgxDO> bdcXmLsgxDOS = bdcXmMapper.queryYxmidByXmidForZh(queryYxmidMap);
                            if (CollectionUtils.isNotEmpty(bdcXmLsgxDOS) && StringUtils.isNotBlank(bdcXmLsgxDOS.get(0).getYxmid()) && CollectionUtils.isNotEmpty(messageModel.getDataModel().getQlfQlDyaqList())) {
                                messageModel.getDataModel().getQlfQlDyaqList().forEach(qlfQlDyaqDO -> {
                                    qlfQlDyaqDO.setSsywh(bdcXmLsgxDOS.get(0).getYxmid());
                                });
                            }
                        }


                    } catch (Exception ex) {
                        LOGGER.info("组织上报数据报错！xmid:{},错误原因", bdcXmDO.getXmid(), ex);
                        saveAccessFailData(new CityAccess(), JrRzCgbsEnum.BWZZSB_WSB.getBs(), bdcXmDO, StringUtils.left(ExceptionUtils.getFeignErrorMsg(ex), 1000));
                        //异步保存接入操作日志
                        asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 3, "市级上报数据组织报文失败异常" + StringUtils.left(ExceptionUtils.getFeignErrorMsg(ex), 1000), "", new Date(), "0");
                        //发送通知，组织报文失败
                        MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
                        msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_2.getYjlx());
                        msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
                        accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
                    }
                    //  判断是否开启一致性，开启后，循环判断赋值
                    if (accessTurnOnYzx) {
                        autoAccessByMessageModelForYzx(messageModel);
                    } else {
                        autoAccessByMessageModel(messageModel);
                    }
                    String saveFlag = EnvironmentConfig.getEnvironment().getProperty("nationalAccessSaveGxDb");
                    if (StringUtils.equals(CommonConstantUtils.BOOL_TRUE, saveFlag)) {
                        // 保存DJF_DJ_YWXX
                        gxService.saveDjfDjYwxxDO(bdcXmDO.getXmid());
                    }
                    LOGGER.info("数据汇交 - 成功 xmid：{}", bdcXmDO.getXmid());
                }
            } else {
                //异步保存结果日志
                asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 2, "根据登记类型" + bdcXmDO.getDjlx() + "没有找到对应的上报策略服务返回空", "", new Date(), "0");
                /*
                 * 发送消息提醒
                 * */
                MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
                msgNoticeDTO.setYwlx(bdcXmDO.getGzldymc());
                msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_1.getYjlx());
                accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
                LOGGER.info("数据汇交 - 未找到对应的策略服务 xmid：{}", bdcXmDO.getXmid());
            }
        } catch (Exception e) {
            LOGGER.error("数据汇交失败：bdcXmDO.getXmid：{}", bdcXmDO.getXmid(), e);
        }
    }

    /**
     * @param messageModel
     * @param bdcXmList    @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量抵押 处理业务号 和 头信息中的业务流水号 为 SLBH
     */
    private void pldyDealMessageModelYwh(MessageModel messageModel, List<BdcXmDO> bdcXmList) {
        List<MessageModel> messageModelList = new ArrayList<>();
        String slbh = bdcXmList.get(0).getSlbh();
        // 获取不同类型的策略 service
        NationalAccessXmlService nationalAccessXmlService = getAccessXmlService(bdcXmList.get(0));
        // 替换实体中的YWH
        setYwhWithSlbh(nationalAccessXmlService, messageModel, slbh);
    }

    private void pldyDealMessageModelYwhOld(MessageModelOld messageModel, List<BdcXmDO> bdcXmList) {
        List<MessageModelOld> messageModelList = new ArrayList<>();
        String slbh = bdcXmList.get(0).getSlbh();
        // 获取不同类型的策略 service
        NationalAccessXmlService nationalAccessXmlService = getAccessXmlService(bdcXmList.get(0));
        // 替换实体中的YWH
        setYwhWithSlbhOld(nationalAccessXmlService, messageModel, slbh);
    }

    //  获取证书序号或登记小类
    private String getZsxhOrDjxlByBdcxm(BdcXmDO bdcXmDO) {
        BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(bdcXmDO.getXmid());
        String key =  "unknown";
        if (bdcCshFwkgSlDO != null && bdcCshFwkgSlDO.getZsxh() != null) {
            key = bdcCshFwkgSlDO.getZsxh().toString();
        } else if (bdcCshFwkgSlDO != null && StringUtils.isNotBlank(bdcCshFwkgSlDO.getDjxl())) {
            key = bdcCshFwkgSlDO.getDjxl();
        }
        return key;
    }

    /**
     * @param nationalAccessXmlService
     * @param messageModel
     * @param slbh
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 向实体中的YWH 字段 写入SLBH
     */
    private void setYwhWithSlbh(NationalAccessXmlService nationalAccessXmlService, MessageModel messageModel, String slbh) {
        if (messageModel != null && messageModel.getHeadModel() != null) {
            messageModel.getHeadModel().setRecFlowID(slbh);
        }
        DataModel dataModel = messageModel.getDataModel();
        Set<NationalAccessDataService> nationalAccessDataServiceSet = nationalAccessXmlService.getNationalAccessDataServiceSet();
        if (CollectionUtils.isNotEmpty(nationalAccessDataServiceSet)) {
            for (NationalAccessDataService service : nationalAccessDataServiceSet) {
                List dataList = service.getData(dataModel);
                if (CollectionUtils.isNotEmpty(dataList)) {
                    for (Object temp : dataList) {
                        try {
                            Method method = temp.getClass().getMethod("setYwh", String.class);
                            method.invoke(temp, slbh);
                        } catch (NoSuchMethodException e) {
                        } catch (IllegalAccessException e) {
                            LOGGER.error("处理批量抵押业务号异常");
                        } catch (InvocationTargetException e) {
                            LOGGER.error("处理批量抵押业务号异常");
                        }
                    }
                }
            }
        }
    }

    private void setYwhWithSlbhOld(NationalAccessXmlService nationalAccessXmlService, MessageModelOld messageModel, String slbh) {
        if (messageModel != null && messageModel.getHeadModel() != null) {
            messageModel.getHeadModel().setRecFlowID(slbh);
        }
        DataModelOld dataModel = messageModel.getDataModel();
        Set<NationalAccessDataService> nationalAccessDataServiceSet = nationalAccessXmlService.getNationalAccessDataServiceSet();
        if (CollectionUtils.isNotEmpty(nationalAccessDataServiceSet)) {
            for (NationalAccessDataService service : nationalAccessDataServiceSet) {
                List dataList = service.getDataOld(dataModel);
                if (CollectionUtils.isNotEmpty(dataList)) {
                    for (Object temp : dataList) {
                        try {
                            Method method = temp.getClass().getMethod("setYwh", String.class);
                            method.invoke(temp, slbh);
                        } catch (NoSuchMethodException e) {
                        } catch (IllegalAccessException e) {
                            LOGGER.error("处理批量抵押业务号异常");
                        } catch (InvocationTargetException e) {
                            LOGGER.error("处理批量抵押业务号异常");
                        }
                    }
                }
            }
        }
    }

    /**
     * 上报外联注销项目
     *
     * @param bdcXmForZxAccessDTOSet
     */
    public void accessWlxm(Set<BdcXmForZxAccessDTO> bdcXmForZxAccessDTOSet) {
        if (CollectionUtils.isNotEmpty(bdcXmForZxAccessDTOSet)) {
            for (BdcXmForZxAccessDTO zxTdInfo : bdcXmForZxAccessDTOSet) {
                /**
                 * BdcXmDO yBdcXmDo = commonService.getBdcXmByXmid(zxTdInfo.getXmid());
                 *  如果查询不到原项目 ，或者要注销的不动产单元号与当前笔一致，那就不上报注销登记
                 */
                if (zxTdInfo == null) {
                    continue;
                }
                if (StringUtils.isBlank(zxTdInfo.getXmid())) {
                    continue;
                }
                BdcXmDO wlxmDO = bdcXmMapper.queryBdcXm(zxTdInfo.getXmid());
                if (Objects.isNull(wlxmDO)) {
                    continue;
                }
                LOGGER.info("====================外联注销项目市级开始上报，存入数据库接入表一条======================:{}", zxTdInfo.getXmid());
                //数据接收，异步存入接入操作日志表，此时没有生成bwid
                BdcWlxmJrCzrzDTO wlxmJrCzrzDTO = new BdcWlxmJrCzrzDTO();
                wlxmJrCzrzDTO.setBwid("");
                wlxmJrCzrzDTO.setGxid(zxTdInfo.getGxid());
                wlxmJrCzrzDTO.setXmid(zxTdInfo.getXmid());
                wlxmJrCzrzDTO.setCzsj(new Date());
                wlxmJrCzrzDTO.setCznr("服务接收到需要上报的外联注销项目信息，下一步进行配置文件校验");
                wlxmJrCzrzDTO.setCzlx(1);
                wlxmJrCzrzDTO.setCzjg("1");
                asyncDealUtils.saveWlxmJrCzrz(wlxmJrCzrzDTO);
                saveWlxmCityAccess(new CityAccess(), JrRzCgbsEnum.BEGIN_WSB.getBs(), wlxmDO, "", zxTdInfo.getGxid());

                //判断是否需要上报，不上报存入一条数据到接入表，且上报状态为-2 不需要上报
                //外联注销项目的登记时间应该是当前流程的登记时间
                wlxmDO.setDjsj(zxTdInfo.getXmDjsj());
                if (!accesssModelHandlerService.wlxmSfsb(wlxmDO, "", true, zxTdInfo.getGxid())) {
                    LOGGER.info("当前项目不需要上报，结束上报服务,bdcXmDO.getXmid：{}", wlxmDO.getXmid());
                    return;
                }
                try {
                    if (newAccessModel) {
                        MessageModel messageModel = new MessageModel();
                        DataModel dataModel = nationalAccessXmlZxWlxmdj.getNationalAccessDataModel(zxTdInfo.getGxid() + CommonConstantUtils.ZF_YW_DH + zxTdInfo.getXmid());
                        if (dataModel == null || CollectionUtils.isEmpty(dataModel.getQlfQlZxdjList())) {
                            continue;
                        }
                        HeadModel headModel = nationalAccessHeadModelService.getAccessHeadModel(zxTdInfo.getYxmid(), true);
                        headModel.setRecType(nationalAccessXmlZxWlxmdj.getRecType());
                        headModel.setRecFlowID(dataModel.getQlfQlZxdjList().get(0).getYwh());
                        headModel.setPreCertID(zxTdInfo.getBdcqzh());
                        // 字典表和国标字典对照
                        headModel = bdcExchangeZddzService.handleHeadZddz(headModel);
                        dataModel = bdcExchangeZddzService.handleZddz(dataModel, dataModel.getQlfQlZxdjList().get(0).getYwh());
                        messageModel.setDataModel(dataModel);
                        messageModel.setHeadModel(headModel);
                        // 读取默认值配置表  赋默认值
                        accessDefaultValueService.setDefaultValueWithDefaultTable(nationalAccessXmlZxWlxmdj.getNationalAccessDataServiceSet(), messageModel, true);
                        if (accessTurnOnYzx) {
                            autoAccessByMessageModelForYzx(messageModel);
                        } else {
                            autoAccessByMessageModel(messageModel);

                        }
                        //成功后更新接入表的sjjrq 为当前介入项目的登记时间，根据messagemodel的bwid作为主键查询后更新
                        String bwid = headModel.getBizMsgID();
                        if (StringUtils.isNotBlank(bwid)) {
                            BdcAccessLog bdcAccessLog = new CityAccess();
                            bdcAccessLog.setSjjrrq(zxTdInfo.getXmDjsj());
                            bdcAccessLog.setYwbwid(bwid);
                            entityMapper.updateByPrimaryKeySelective(bdcAccessLog);
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("上报外联注销项目数据汇交失败：bdcXmDO.getXmid：{}", zxTdInfo.getYxmid(), e);
                }
            }
        }
    }

    /**
     * 根据bs标识保存数据
     *
     * @param
     * @return
     * @Date 2022/6/23
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public void saveBdcAccess(BdcAccessLog bdcAccessLog, Integer bs, BdcXmDO bdcXmDO, String xyxx) {
        try {
            bdcAccessLog.setCgbs(bs);
            bdcAccessLog.setXyxx(JrRzCgbsEnum.getMsgByBs(bs) + ":" + StringToolUtils.left(xyxx, 3900));
            bdcAccessLog.setYwlsh(bdcXmDO.getXmid());
            bdcAccessLog.setYwbwid(bdcXmDO.getXmid());
            bdcAccessLog.setUpdatetime(new Date());
            bdcAccessLog.setJrrq(new Date());
            bdcAccessLog.setBdcdyh(bdcXmDO.getBdcdyh());
            entityMapper.saveOrUpdate(bdcAccessLog, bdcAccessLog.getYwbwid());
            LOGGER.info("根据bs标识保存结束！");
        } catch (Exception e) {
            //发送通知，一开始保存就失败
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_2.getYjlx());
            msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
            LOGGER.info("根据BS保存接入表失败！xmid:{},失败原因：{}", bdcXmDO.getXmid(), e.getMessage());
            throw new AppException("根据BS保存接入表失败！");
        }

    }

    public void saveWlxmCityAccess(BdcAccessLog bdcAccessLog, Integer bs, BdcXmDO bdcXmDO, String xyxx, String gxid) {
        try {
            bdcAccessLog.setCgbs(bs);
            bdcAccessLog.setXyxx(JrRzCgbsEnum.getMsgByBs(bs) + ":" + StringToolUtils.left(xyxx, 3900));
            bdcAccessLog.setYwlsh(gxid);
            bdcAccessLog.setYwbwid(gxid);
            bdcAccessLog.setUpdatetime(new Date());
            bdcAccessLog.setJrrq(new Date());
            bdcAccessLog.setBdcdyh(bdcXmDO.getBdcdyh());
            entityMapper.saveOrUpdate(bdcAccessLog, bdcAccessLog.getYwbwid());
            LOGGER.info("根据bs标识保存结束！");
        } catch (Exception e) {
            //发送通知，一开始保存就失败
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_2.getYjlx());
            msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
            LOGGER.info("根据BS保存接入表失败！xmid:{},失败原因：{}", bdcXmDO.getXmid(), e.getMessage());
            throw new AppException("根据BS保存接入表失败！");
        }

    }

    /**
     * @param nationalAccessXmlService
     * @param xmid
     * @return
     * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
     * @description 获取上报数据对象模型
     */
    public MessageModel getMessageModel(NationalAccessXmlService nationalAccessXmlService, String xmid) {
        MessageModel messageModel = new MessageModel();
        DataModel dataModel = nationalAccessXmlService.getNationalAccessDataModel(xmid);
        HeadModel headModel = nationalAccessHeadModelService.getAccessHeadModel(xmid, false);
        headModel.setRecType(nationalAccessXmlService.getRecType());
        // 字典表和国标字典对照
        headModel = bdcExchangeZddzService.handleHeadZddz(headModel);
        dataModel = bdcExchangeZddzService.handleZddz(dataModel, xmid);

        messageModel.setDataModel(dataModel);
        messageModel.setHeadModel(headModel);
        // 读取默认值配置表  赋默认值
        accessDefaultValueService.setDefaultValueWithDefaultTable(nationalAccessXmlService.getNationalAccessDataServiceSet(), messageModel, true);
        return messageModel;
    }

    /**
     * @param messageModel
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 针对已有messageModel结构的场景  汇报上交
     */
    public boolean autoAccessByMessageModel(MessageModel messageModel) {
        boolean result = false;
        try {
            if (!ObjectUtils.isEmpty(messageModel)) {
                List<NationalAccessUpload> list = UploadServiceUtil.listNationalAccessUploadByType(UploadServiceUtil.CITY_ACCESS);
                // 上传
                for (NationalAccessUpload nationalAccessUpload : list) {
                    Boolean hjjg = nationalAccessUpload.upload(messageModel);
                    if (hjjg == null) {
                        continue;
                    } else if (!hjjg) {
                        result = false;
                        break;
                    } else {
                        result = true;
                    }
                }
            }

            String ywh = "";
            if (messageModel != null && messageModel.getHeadModel() != null) {
                ywh = messageModel.getHeadModel().getRecFlowID();
            }
            LOGGER.debug("汇交报文结束，ywh:{},result: {}", ywh, result);
            String saveFlag = EnvironmentConfig.getEnvironment().getProperty("nationalAccessSaveGxDb");
            if (StringUtils.isNotBlank(saveFlag) && StringUtils.equals(CommonConstantUtils.BOOL_TRUE, saveFlag)) {
                // 不保存DJF_DJ_YWXX
                try {
                    gxService.saveGxDataModel(messageModel.getDataModel());
                    gxService.saveDjfDjYwxxDO(messageModel.getHeadModel().getRecFlowID());
                    LOGGER.info("保存共享库结束，ywh:{}, result: {}", ywh, result);
                } catch (Exception e) {
                    LOGGER.error("保存共享库失败,ywh:{},原因:{}", ywh, e);
                }
            }
        } catch (Exception e) {
            LOGGER.error("messageModel数据汇交失败", e);
            saveBdcAccessByMessageModel(new CityAccess(), JrRzCgbsEnum.ACCESS_FAIL.getBs(), messageModel, StringUtils.left(ExceptionUtils.getFeignErrorMsg(e), 1000));
        }
        return result;
    }

    public boolean autoAccessByMessageModelOld(MessageModelOld messageModel) {
        boolean result = false;
        try {
            if (!ObjectUtils.isEmpty(messageModel)) {
                List<NationalAccessUpload> list = UploadServiceUtil.listNationalAccessUploadByType(UploadServiceUtil.CITY_ACCESS);
                // 上传
                for (NationalAccessUpload nationalAccessUpload : list) {
                    Boolean hjjg = nationalAccessUpload.uploadOld(messageModel);
                    if (hjjg == null) {
                        continue;
                    } else if (!hjjg) {
                        result = false;
                        break;
                    } else {
                        result = true;
                    }
                }
            }

            String ywh = "";
            if (messageModel != null && messageModel.getHeadModel() != null) {
                ywh = messageModel.getHeadModel().getRecFlowID();
            }
            LOGGER.debug("汇交报文结束，ywh:{},result: {}", ywh, result);
            String saveFlag = EnvironmentConfig.getEnvironment().getProperty("nationalAccessSaveGxDb");
            if (StringUtils.isNotBlank(saveFlag) && StringUtils.equals(CommonConstantUtils.BOOL_TRUE, saveFlag)) {
                // 不保存DJF_DJ_YWXX
                gxService.saveGxDataModelOld(messageModel.getDataModel());
                gxService.saveDjfDjYwxxDO(messageModel.getHeadModel().getRecFlowID());
                LOGGER.info("old保存共享库结束，ywh:{}, result: {}", ywh, result);
            }
        } catch (Exception e) {
            LOGGER.error("数据汇交失败", e);
        }
        return result;
    }

    /**
     * @param bdcXmDO
     * @return
     * @description 根据项目编码获取相应的策略服务
     */
    public NationalAccessXmlService getAccessXmlService(BdcXmDO bdcXmDO) {
        NationalAccessXmlService nationalAccessXmlService = null;
        /*
         * DJLX 登记类型
         * QLLX 权力类型
         * FWLX 房屋类型
         * BDCLX 不动产类型
         */
        if (!ObjectUtils.isEmpty(bdcXmDO) && !ObjectUtils.isEmpty(bdcXmDO.getDjlx())) {
            if (StringUtils.isNotEmpty(bdcXmDO.getDjlx().toString())) {
                nationalAccessXmlService = nationalAccessXmlContext.getNationalAccessXmlServiceByDbConfig(bdcXmDO);
            }
        } else {
            LOGGER.error("未查找到不动产类型记录 - bdcXmDO:{}", bdcXmDO.toString());
        }
        return nationalAccessXmlService;
    }

    /**
     * @param bdcXmDO
     * @param sfgzyz  是否需要验证
     * @param sfjlrz  是否记录日志
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断是否上报，特殊逻辑判断
     * @date : 2022/6/23 15:49
     */
    public boolean sfsb(BdcXmDO bdcXmDO, String sfgzyz, Boolean sfjlrz) {
        /*
         * 查询流程配置是否上报
         * */
        BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyidAndDjxl(bdcXmDO.getGzldyid(), bdcXmDO.getDjxl());
        if (Objects.nonNull(bdcDjxlPzDO)) {
            if (Objects.equals(CommonConstantUtils.SF_F_DM, bdcDjxlPzDO.getSfsb())) {
                //配置了不上报，返回false
                if (sfjlrz) {
                    asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "流程配置中设置了不上报", "", new Date(), "0");
                    saveBsbJrrz(bdcXmDO, "流程配置中设置了不上报");
                }
                return false;
            }
        }
        //盐城特殊判断处理     49702 【盐城】省级限制上报调整
        //先判断是否是配置的组合流程，判断是否超时
        if (CollectionUtils.isNotEmpty(outTimeGzldyidArr)) {
            if (outTimeGzldyidArr.contains(bdcXmDO.getGzldyid())) {
                if (null == bdcXmDO.getSlsj() || null == bdcXmDO.getDjsj()) {
                    LOGGER.info("该项目受理时间或登记时间为空！无法判断是否超时！请人工核查数据！xmid:{}", bdcXmDO.getXmid());
                    if (sfjlrz) {
                        asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "该项目受理时间或登记时间为空！无法判断是否超时！请人工核查数据！", "", new Date(), "0");
                    }
                } else {
                    LOGGER.info("开始比较时间！");
                    Object workTime = computeTaskTimeUtils.computeTaskTime(DateUtils.formateYmdhms(bdcXmDO.getSlsj()), DateUtils.formateYmdhms(bdcXmDO.getDjsj()));
                    LOGGER.info("开始比较时间！计算后工作日为：{}", workTime);

                    if (!"".equals(workTime)) {
                        //工作日大于1不上报
                        if (outWorkDay < (Double) workTime) {
                            LOGGER.info("工作日超过1个，不上报！xmid为：{}", bdcXmDO.getXmid());
                            if (sfjlrz) {
                                asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "工作日超过1个，不上报！", "", new Date(), "0");
                                saveBsbJrrz(bdcXmDO, "工作日超过1个，不上报");
                            }
                            return false;
                        }
                    }
                }

            }
        }
        //抵押业务判断-上手产权是不是6个月内通过转移获取的，是的话不上报
        if (CollectionUtils.isNotEmpty(dyaGzldyidArr) && dyaGzldyidArr.contains(bdcXmDO.getGzldyid())) {
            //56981 【盐城】省级限制上报抵押业务逻辑调整     要求根据抵押方式判断，一般抵押进行下面时间判断，其他抵押方式正常上报
            BdcDyaqDO bdcDyaqQO = new BdcDyaqDO();
            bdcDyaqQO.setXmid(bdcXmDO.getXmid());
            List<BdcDyaqDO> listDyaq = entityMapper.selectByObj(bdcDyaqQO);
            if (CollectionUtils.isNotEmpty(listDyaq)) {
                if (CommonConstantUtils.DYFS_YBDY.equals(listDyaq.get(0).getDyfs())) {
                    //查找上一手产权信息,先查fdcq表qszt=1的，用xmid查项目，比较时间
                    BdcFdcqDO fdcqDO = bdcXmMapper.quertXsFdcqByBdcdyh(bdcXmDO.getBdcdyh());
                    if (null != fdcqDO) {
                        //判断是否大于6个月
                        BdcXmQO xmQO = new BdcXmQO();
                        xmQO.setXmid(fdcqDO.getXmid());
                        List<BdcXmDO> cqxmDOList = bdcXmFeignService.listBdcXm(xmQO);
                        if (CollectionUtils.isNotEmpty(cqxmDOList)) {
                            BdcXmDO cqxmDO = cqxmDOList.get(0);
                            //判断要比较的时间是否都有值，无值不比较，直接跳过
                            if (null == bdcXmDO.getDjsj() || null == cqxmDO.getDjsj()) {
                                LOGGER.info("产权项目登记时间或抵押项目登记时间为空！无法判断是否超过6个月！请人工核查数据！xmid:{}", bdcXmDO.getXmid());
                                if (sfjlrz) {
                                    asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "产权项目登记时间或抵押项目登记时间为空！无法判断是否超过6个月！请人工核查数据！", "", new Date(), "0");
                                }
                            } else {
                                //指定转换格式
                                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                LOGGER.info("比较的时间抵押时间：{}，上一手产权时间:{}", bdcXmDO.getDjsj(), cqxmDO.getDjsj());
                                int days = (int) ((bdcXmDO.getDjsj().getTime() - cqxmDO.getDjsj().getTime()) / (1000 * 3600 * 24));
                                LOGGER.info("days相隔多少天：{}", days);
                                if (days <= Constants.DAY_180) {
                                    //存量房转移获得的产权在6个月内办理的抵押不进行上报
                                    if (CollectionUtils.isNotEmpty(clfzyGzldyidArr) && clfzyGzldyidArr.contains(cqxmDO.getGzldyid())) {
                                        LOGGER.info("上一手产权取得时间和当前抵押项目小于6个月，不上报！");
                                        if (sfjlrz) {
                                            asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "上一手产权取得时间和当前抵押项目小于6个月，不上报！", "", new Date(), "0");
                                            saveBsbJrrz(bdcXmDO, "上一手产权取得时间和当前抵押项目小于6个月，不上报！");
                                        }
                                        return false;
                                    }
                                }
                            }

                        }
                    }
                } else {
                    LOGGER.info("没有查到该抵押权利信息！无法判断是否是一般抵押！请人工核查数据！xmid:{}", bdcXmDO.getXmid());
                }
            }
        }
        // 判断是否是需要过滤的BDCDYH（配置的过滤xzqdm 和 是否是虚拟的BDCDYH）
        if (CommonUtil.checkFilterBdcdyh(bdcXmDO.getBdcdyh())) {
            if (sfjlrz) {
                asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "虚拟单元号不上报或者配置了过滤区县不上报", "", new Date(), "0");
                saveBsbJrrz(bdcXmDO, "虚拟单元号不上报或者配置了过滤区县不上报");
            }
            return false;
        }
        //判断是否过滤存量历史数据上报
        if (accessFilterHistoryDataFlag && bdcXmDO.getXmly() != null && CollectionUtils.isNotEmpty(bsbXmlyList) && bsbXmlyList.contains(bdcXmDO.getXmly())) {
            if (sfjlrz) {
                asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "存量历史数据不上报", "", new Date(), "0");
                saveBsbJrrz(bdcXmDO, "配置了存量历史数据不上报");
            }
            return false;
        }

        //校验是否允许上报
        if (CollectionUtils.isNotEmpty(gzyzCodeList) && Objects.nonNull(bdcXmDO.getDjsj())) {
            //根据配置的规则code ，判断是否需要上报
            if (!StringUtil.equals(CommonConstantUtils.BOOL_FALSE, sfgzyz)) {
                for (String code : gzyzCodeList) {
                    boolean result = ywhjYz(bdcXmDO, code, sfjlrz);
                    //配置了的规则只要验证不通过就是直接结束循环
                    if (!result) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void saveBsbJrrz(BdcXmDO bdcXmDO, String xyxx) {
        saveBdcAccess(new CityAccess(), JrRzCgbsEnum.NO_ACCESS.getBs(), bdcXmDO, xyxx);
    }

    /**
     * 根据bs标识保存数据
     *
     * @param
     * @return
     * @Date 2022/6/23
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public void saveBdcAccessByMessageModel(BdcAccessLog bdcAccessLog, Integer bs, MessageModel messageModel, String xyxx) {
        String ywh = "";
        try {
            bdcAccessLog.setCgbs(bs);
            bdcAccessLog.setXyxx(JrRzCgbsEnum.getMsgByBs(bs) + ":" + StringToolUtils.left(xyxx, 3900));
            if (messageModel != null && messageModel.getHeadModel() != null) {
                ywh = messageModel.getHeadModel().getRecFlowID();
                bdcAccessLog.setYwdm(messageModel.getHeadModel().getRecType());
                bdcAccessLog.setYwbwid(messageModel.getHeadModel().getBizMsgID());
            }
            bdcAccessLog.setYwlsh(ywh);
            bdcAccessLog.setUpdatetime(new Date());
            bdcAccessLog.setJrrq(new Date());
            //已生成汇交结构，之前以xmid为主键保存的信息需要删除，重新保存一条以BizMsgID 为主键的信息，后续均更新这条数据

            if (StringUtils.isNotBlank(ywh)) {
                LOGGER.info("删除之前xmid为主键值的记录！xmid:{}", ywh);
                Example example = new Example(bdcAccessLog.getClass());
                example.createCriteria().andEqualTo("ywbwid", ywh);
                entityMapper.deleteByExampleNotNull(example);
            }
            entityMapper.saveOrUpdate(bdcAccessLog, bdcAccessLog.getYwbwid());
            LOGGER.info("saveBdcAccessByMessageModel根据bs标识保存结束！");
        } catch (Exception e) {
            //发送通知，一上报就失败
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_2.getYjlx());
            msgNoticeDTO.setSlbh(ywh);
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
            LOGGER.info("根据BS保存接入表失败！xmid:{},失败原因：{}", ywh, e.getMessage());
            throw new AppException("MessageModel根据BS保存接入表失败！");
        }
    }


    /*
     * 业务汇交根据配置的code实现不同的逻辑验证
     * day 参数为时间，支持传入非当天的时间进行验证，例如 定时任务检查的是前一天的登簿数据，此时传入day参数为昨天的时间
     * */
    public boolean ywhjYz(BdcXmDO bdcXmDO, String code, Boolean sfjlczrz) {
        boolean result = true;
        String today = DateUtils.formateTime(new Date(System.currentTimeMillis()), DateUtils.DATE_FORMAT_2);
        String xmdjsj = DateUtils.formateTime(bdcXmDO.getDjsj(), DateUtils.DATE_FORMAT_2);
        switch (code) {
            case "1":
                //非当天登簿时间数据补报，如果登簿日志详情表不存在数据，不允许上报
                if (!StringUtils.equals(today, xmdjsj)) {
                    BdcDbrzQO bdcDbrzQO = new BdcDbrzQO();
                    bdcDbrzQO.setXmid(bdcXmDO.getXmid());
                    List<BdcDbrzjlXqDO> bdcDbrzjlXqDOList = accessLogMapper.listBdcDbrzjlXq(bdcDbrzQO);
                    LOGGER.warn("当前项目xmid{}非当天登簿，查询登簿日志详情记录入参{}，数据量{}", bdcXmDO.getXmid(), JSON.toJSONString(bdcDbrzQO), CollectionUtils.size(bdcDbrzjlXqDOList));
                    if (CollectionUtils.isEmpty(bdcDbrzjlXqDOList)) {
                        if (sfjlczrz) {
                            asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "当前项目非当天登簿，且在登簿日志详情表不存在数据不允许上报", "", new Date(), "0");
                            saveBsbJrrz(bdcXmDO, "当前项目非当天登簿，且在登簿日志详情表不存在数据不允许上报");
                        }
                        result = false;
                    }
                }
                break;
            case "2":
//                if (StringUtils.equals(today, xmdjsj)) {
                //查询接入表cbgs=0,1 成功上报或者已经发送到前置机，不允许再次上报
                BdcJrrzQO bdcJrrzQO = new BdcJrrzQO();
                bdcJrrzQO.setXmid(bdcXmDO.getXmid());
                bdcJrrzQO.setCgbsList(Arrays.asList(0, 1));
                //实际接入时间是当天的不允许补报
                bdcJrrzQO.setSjjrrq(new Date());
                List<BdcAccessLog> bdcAccessLogList = accessLogMapper.listBdcJrrz(bdcJrrzQO);
                LOGGER.warn("当前项目xmid{}接入表查询cgbs=0或者1的数据,不允许再次上报，查询入参{}，数据量{}", bdcXmDO.getXmid(), JSON.toJSONString(bdcJrrzQO), CollectionUtils.size(bdcAccessLogList));
                if (CollectionUtils.isNotEmpty(bdcAccessLogList)) {
                    if (sfjlczrz) {
                        asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "当前项目接入表存在已上报cgbs=0或者1的数据，不允许再次上报", "", new Date(), "0");
                        saveBsbJrrz(bdcXmDO, "当前项目接入表存在已上报cgbs=0或者1的数据,不允许再次上报");
                    }
                    result = false;
                }
//                }
                break;
            case "3":
                //判断当天登簿日志是否上报
                //判断登簿时间是否是今天的数据，不是今天不验证
                if (StringUtils.equals(today, xmdjsj)) {
                    BdcDbrzQO bdcDbrzQO = new BdcDbrzQO();
                    bdcDbrzQO.setAccessDate(new Date());
                    bdcDbrzQO.setCgbs(1);
                    bdcDbrzQO.setQxdm(BdcdyhToolUtils.subPreSixBdcdyh(bdcXmDO.getBdcdyh()));
                    List<BdcJrDbrzjlDO> dbrzjlDOList = accessLogMapper.listDbrzjl(bdcDbrzQO);
                    LOGGER.error("当前项目id{}查询当天登簿日志是否已经成功上报了，查询入参{}，数据量{}，成功不允许再次汇交上报", bdcXmDO.getXmid(), JSON.toJSONString(bdcDbrzQO), CollectionUtils.size(dbrzjlDOList));
                    if (CollectionUtils.isNotEmpty(dbrzjlDOList)) {
                        //已经成功上报的不允许汇交上报
                        if (sfjlczrz) {
                            asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "当天登簿日志已经上报了，不允许再次汇交上报", "", new Date(), "0");
                            saveBsbJrrz(bdcXmDO, "当天登簿日志已经上报了，不允许再次汇交上报");
                        }
                        result = false;
                    }
                }
                break;
            default:
                return true;
        }
        return result;
    }

    /**
     * 一致性上报
     *
     * @param messageModel
     * @return
     * @Date 2022/5/25
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public boolean autoAccessByMessageModelForYzx(MessageModel messageModel) {
        boolean result = false;

        MessageModelBdc messageModelBdc = new MessageModelBdc();
        DataModelBdc dataModelBdc = new DataModelBdc();
        String ywh = messageModel.getHeadModel().getRecFlowID();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(ywh);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        //地方权利类型名称
        String dfqllxmc = "";
        //地方权利性质名称
        String dfqlxzmc = "";
        //地方用途名称
        String dfytmc = "";
        //地方登记类型名称
        String dfdjlxmc = "";
        //地方登记类型代码
        String dfdjlxdm = "";
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            dfqllxmc = StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDOList.get(0).getQllx(), zdMap.get(Constants.QLLX));
            dfqlxzmc = StringToolUtils.convertBeanPropertyValueOfZdByString(bdcXmDOList.get(0).getQlxz(), zdMap.get(Constants.QLXZ));
            dfytmc = StringToolUtils.convertBeanPropertyValueOfZdByString(bdcXmDOList.get(0).getZdzhyt(), zdMap.get(Constants.TDYT));
            dfdjlxmc = StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDOList.get(0).getDjlx(), zdMap.get(Constants.DJLX));
            dfdjlxdm = String.valueOf(bdcXmDOList.get(0).getDjlx());

        }

        //循环赋值
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getKttZdjbxxList())) {
            List<KttZdjbxxBdc> zdjbxxBdcList = new ArrayList<>();
            for (KttZdjbxxDO kttZdjbxxDO : messageModel.getDataModel().getKttZdjbxxList()) {
                KttZdjbxxBdc kttZdjbxxBdc = new KttZdjbxxBdc();
                BeanUtils.copyProperties(kttZdjbxxDO, kttZdjbxxBdc);
                kttZdjbxxBdc.setDfqllxmc(dfqllxmc);
                kttZdjbxxBdc.setDfqlxzmc(dfqlxzmc);
                kttZdjbxxBdc.setDfytmc(dfytmc);

                zdjbxxBdcList.add(kttZdjbxxBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(zdjbxxBdcList);
                }
            }
            dataModelBdc.setKttZdjbxxList(zdjbxxBdcList);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getDjfDjGdList())) {
            List<DjfDjGdBdc> djfDjGdBdcList = new ArrayList<>();
            for (DjfDjGdDO djfDjGdDO : messageModel.getDataModel().getDjfDjGdList()) {
                DjfDjGdBdc djfDjGdBdc = new DjfDjGdBdc();
                BeanUtils.copyProperties(djfDjGdDO, djfDjGdBdc);
                djfDjGdBdc.setDfdjdldm(String.valueOf(bdcXmDOList.get(0).getDjlx()));
                djfDjGdBdc.setDfdjdlmc(dfdjlxmc);
                djfDjGdBdcList.add(djfDjGdBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(djfDjGdBdcList);
                }
            }
            dataModelBdc.setDjfDjGdList(djfDjGdBdcList);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getDjtDjSlsqList())) {
            List<DjtDjSlsqBdc> djtDjSlsqBdcList = new ArrayList<>();
            for (DjtDjSlsqDO djtDjSlsqDO : messageModel.getDataModel().getDjtDjSlsqList()) {
                DjtDjSlsqBdc djtDjSlsqBdc = new DjtDjSlsqBdc();
                BeanUtils.copyProperties(djtDjSlsqDO, djtDjSlsqBdc);
                djtDjSlsqBdc.setDfdjlxdm(dfdjlxdm);
                djtDjSlsqBdc.setDfdjlxmc(dfdjlxmc);
                djtDjSlsqBdcList.add(djtDjSlsqBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(djtDjSlsqBdcList);
                }
            }
            dataModelBdc.setDjtDjSlsqList(djtDjSlsqBdcList);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getKtfZhYhzkList())) {
            List<KtfZhYhzkBdc> ktfZhYhzkBdcList = new ArrayList<>();
            for (KtfZhYhzkDO ktfZhYhzkDO : messageModel.getDataModel().getKtfZhYhzkList()) {
                KtfZhYhzkBdc ktfZhYhzkBdc = new KtfZhYhzkBdc();
                BeanUtils.copyProperties(ktfZhYhzkDO, ktfZhYhzkBdc);
                ktfZhYhzkBdc.setDfyhfsmc(StringToolUtils.convertBeanPropertyValueOfZdByString(ktfZhYhzkDO.getYhfs(), zdMap.get(Constants.YHFS)));
                ktfZhYhzkBdcList.add(ktfZhYhzkBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(ktfZhYhzkBdcList);
                }
            }
            dataModelBdc.setKtfZhYhzkList(ktfZhYhzkBdcList);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getKttFwHList())) {
            List<KttFwHBdc> kttFwHBdcList = new ArrayList<>();
            for (KttFwHDO kttFwHDO : messageModel.getDataModel().getKttFwHList()) {
                KttFwHBdc kttFwHBdc = new KttFwHBdc();
                BeanUtils.copyProperties(kttFwHDO, kttFwHBdc);
                kttFwHBdc.setDfhxjgmc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttFwHDO.getHxjg(), zdMap.get(Constants.HXJG)));
                kttFwHBdc.setDffwyt1mc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttFwHDO.getFwyt1(), zdMap.get(Constants.FWYT)));
                kttFwHBdc.setDffwyt2mc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttFwHDO.getFwyt2(), zdMap.get(Constants.FWYT)));
                kttFwHBdc.setDffwyt3mc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttFwHDO.getFwyt3(), zdMap.get(Constants.FWYT)));
                kttFwHBdcList.add(kttFwHBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(kttFwHBdcList);
                }
            }
            dataModelBdc.setKttFwHList(kttFwHBdcList);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getKttFwLjzList())) {
            List<KttFwLjzBdc> kttFwLjzBdcList = new ArrayList<>();
            for (KttFwLjzDO kttFwLjzDO : messageModel.getDataModel().getKttFwLjzList()) {
                KttFwLjzBdc kttFwLjzBdc = new KttFwLjzBdc();
                BeanUtils.copyProperties(kttFwLjzDO, kttFwLjzBdc);
                kttFwLjzBdc.setDffwjg1mc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttFwLjzDO.getFwjg1(), zdMap.get(Constants.FWJG)));
                kttFwLjzBdc.setDffwjg2mc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttFwLjzDO.getFwjg2(), zdMap.get(Constants.FWJG)));
                kttFwLjzBdc.setDffwjg3mc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttFwLjzDO.getFwjg3(), zdMap.get(Constants.FWJG)));
                kttFwLjzBdc.setDffwyt1mc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttFwLjzDO.getFwyt1(), zdMap.get(Constants.FWYT)));
                kttFwLjzBdc.setDffwyt2mc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttFwLjzDO.getFwyt1(), zdMap.get(Constants.FWYT)));
                kttFwLjzBdc.setDffwyt3mc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttFwLjzDO.getFwyt1(), zdMap.get(Constants.FWYT)));
                kttFwLjzBdcList.add(kttFwLjzBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(kttFwLjzBdcList);
                }
            }
            dataModelBdc.setKttFwLjzList(kttFwLjzBdcList);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getKttFwZrzList())) {
            List<KttFwZrzBdc> kttFwZrzBdcList = new ArrayList<>();
            for (KttFwZrzDO kttFwZrzDO : messageModel.getDataModel().getKttFwZrzList()) {
                KttFwZrzBdc kttFwZrzBdc = new KttFwZrzBdc();
                BeanUtils.copyProperties(kttFwZrzDO, kttFwZrzBdc);
                kttFwZrzBdc.setDffwjgmc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttFwZrzDO.getFwjg(), zdMap.get(Constants.FWJG)));
                kttFwZrzBdcList.add(kttFwZrzBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(kttFwZrzBdcList);
                }
            }
            dataModelBdc.setKttFwZrzList(kttFwZrzBdcList);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getKttGyJzdList())) {
            List<KttGyJzdBdc> kttGyJzdBdcList = new ArrayList<>();
            for (KttGyJzdDO kttGyJzdDO : messageModel.getDataModel().getKttGyJzdList()) {
                KttGyJzdBdc kttGyJzdBdc = new KttGyJzdBdc();
                BeanUtils.copyProperties(kttGyJzdDO, kttGyJzdBdc);

                kttGyJzdBdc.setDfjblxmc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttGyJzdDO.getJblx(), zdMap.get(Constants.JBLX)));
                kttGyJzdBdc.setDfjzdlxmc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttGyJzdDO.getJzdlx(), zdMap.get(Constants.JZDLX)));
                kttGyJzdBdcList.add(kttGyJzdBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(kttGyJzdBdcList);
                }
            }
            dataModelBdc.setKttGyJzdList(kttGyJzdBdcList);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getKttGyJzxList())) {
            List<KttGyJzxBdc> kttGyJzxBdcList = new ArrayList<>();
            for (KttGyJzxDO kttGyJzxDO : messageModel.getDataModel().getKttGyJzxList()) {
                KttGyJzxBdc kttGyJzxBdc = new KttGyJzxBdc();
                BeanUtils.copyProperties(kttGyJzxDO, kttGyJzxBdc);
                kttGyJzxBdc.setDfjxxzmc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttGyJzxDO.getJxxz(), zdMap.get(Constants.JXXZ)));
                kttGyJzxBdc.setDfjzxlbmc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttGyJzxDO.getJzxlb(), zdMap.get(Constants.JZXLB)));
                kttGyJzxBdc.setDfjzxwzmc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttGyJzxDO.getJzxwz(), zdMap.get(Constants.JZXWZ)));
                kttGyJzxBdcList.add(kttGyJzxBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(kttGyJzxBdcList);
                }
            }
            dataModelBdc.setKttGyJzxList(kttGyJzxBdcList);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getKttZhjbxxList())) {
            List<KttZhjbxxBdc> kttZhjbxxBdcList = new ArrayList<>();
            for (KttZhjbxxDO kttZhjbxxDO : messageModel.getDataModel().getKttZhjbxxList()) {
                KttZhjbxxBdc kttZhjbxxBdc = new KttZhjbxxBdc();
                BeanUtils.copyProperties(kttZhjbxxDO, kttZhjbxxBdc);
                kttZhjbxxBdc.setDfxmxzmc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttZhjbxxDO.getXmxz(), zdMap.get(Constants.XMXZ)));
                kttZhjbxxBdc.setDfhdytmc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttZhjbxxDO.getHdyt(), zdMap.get(Constants.HDYT)));
                kttZhjbxxBdc.setDfyhlxamc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttZhjbxxDO.getYhlxa(), zdMap.get(Constants.HYSYLXA)));
                kttZhjbxxBdc.setDfyhlxbmc(StringToolUtils.convertBeanPropertyValueOfZdByString(kttZhjbxxDO.getYhlxb(), zdMap.get(Constants.HYSYLXB)));
                kttZhjbxxBdcList.add(kttZhjbxxBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(kttZhjbxxBdcList);
                }
            }
            dataModelBdc.setKttZhjbxxList(kttZhjbxxBdcList);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getQlfFwFdcqDzXmList())) {
            List<QlfFwFdcqDzXmBdc> qlfFwFdcqDzXmBdcList = new ArrayList<>();
            for (QlfFwFdcqDzXmDO qlfFwFdcqDzXmDO : messageModel.getDataModel().getQlfFwFdcqDzXmList()) {
                QlfFwFdcqDzXmBdc qlfFwFdcqDzXmBdc = new QlfFwFdcqDzXmBdc();
                BeanUtils.copyProperties(qlfFwFdcqDzXmDO, qlfFwFdcqDzXmBdc);
                qlfFwFdcqDzXmBdc.setDffwjgmc(StringToolUtils.convertBeanPropertyValueOfZdByString(qlfFwFdcqDzXmDO.getFwjg(), zdMap.get(Constants.FWJG)));
                qlfFwFdcqDzXmBdcList.add(qlfFwFdcqDzXmBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(qlfFwFdcqDzXmBdcList);
                }

            }
            dataModelBdc.setQlfFwFdcqDzXmList(qlfFwFdcqDzXmBdcList);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getQlfFwFdcqQfsyqList())) {
            List<QlfFwFdcqQfsyqBdc> qlfFwFdcqQfsyqBdcList = new ArrayList<>();
            for (QlfFwFdcqQfsyqDO qlfFwFdcqQfsyqDO : messageModel.getDataModel().getQlfFwFdcqQfsyqList()) {
                QlfFwFdcqQfsyqBdc qlfFwFdcqQfsyqBdc = new QlfFwFdcqQfsyqBdc();
                BeanUtils.copyProperties(qlfFwFdcqQfsyqDO, qlfFwFdcqQfsyqBdc);

                qlfFwFdcqQfsyqBdc.setDfqllxmc(dfqllxmc);
                qlfFwFdcqQfsyqBdcList.add(qlfFwFdcqQfsyqBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(qlfFwFdcqQfsyqBdcList);
                }
            }
            dataModelBdc.setQlfFwFdcqQfsyqList(qlfFwFdcqQfsyqBdcList);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getQlfQlCfdjList())) {
            List<QlfQlCfdjBdc> qlfQlCfdjBdcs = new ArrayList<>();
            for (QlfQlCfdjDO qlfQlCfdjDO : messageModel.getDataModel().getQlfQlCfdjList()) {
                QlfQlCfdjBdc qlfQlCfdjBdc = new QlfQlCfdjBdc();
                BeanUtils.copyProperties(qlfQlCfdjDO, qlfQlCfdjBdc);

                qlfQlCfdjBdc.setDfcflxmc(StringToolUtils.convertBeanPropertyValueOfZdByString(qlfQlCfdjDO.getCflx(), zdMap.get(Constants.CFLX)));
                qlfQlCfdjBdcs.add(qlfQlCfdjBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(qlfQlCfdjBdcs);
                }
            }
            dataModelBdc.setQlfQlCfdjList(qlfQlCfdjBdcs);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getQlfQlDyaqList())) {
            List<QlfQlDyaqBdc> qlfQlDyaqBdcList = new ArrayList<>();
            for (QlfQlDyaqDO qlfQlDyaqDO : messageModel.getDataModel().getQlfQlDyaqList()) {
                QlfQlDyaqBdc qlfQlDyaqBdc = new QlfQlDyaqBdc();
                BeanUtils.copyProperties(qlfQlDyaqDO, qlfQlDyaqBdc);

                qlfQlDyaqBdc.setDfdjlxmc(dfdjlxmc);
                qlfQlDyaqBdc.setDfdybdclxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDOList.get(0).getBdclx(), zdMap.get(Constants.BDCLX)));
                qlfQlDyaqBdc.setDfdyfsdm(qlfQlDyaqDO.getDyfs());
                qlfQlDyaqBdc.setDfdyfsmc(StringToolUtils.convertBeanPropertyValueOfZdByString(qlfQlDyaqDO.getDyfs(), zdMap.get(Constants.DYFS)));
                qlfQlDyaqBdcList.add(qlfQlDyaqBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(qlfQlDyaqBdcList);
                }

            }
            dataModelBdc.setQlfQlDyaqList(qlfQlDyaqBdcList);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getQlfQlDyiqList())) {
            List<QlfQlDyiqBdc> qlfQlDyiqBdcs = new ArrayList<>();
            for (QlfQlDyiqDO qlfQlDyiqDO : messageModel.getDataModel().getQlfQlDyiqList()) {
                QlfQlDyiqBdc qlfQlDyiqBdc = new QlfQlDyiqBdc();
                BeanUtils.copyProperties(qlfQlDyiqDO, qlfQlDyiqBdc);
                qlfQlDyiqBdc.setDfdjlxdm(dfdjlxdm);
                qlfQlDyiqBdc.setDfdjlxmc(dfdjlxmc);
                qlfQlDyiqBdcs.add(qlfQlDyiqBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(qlfQlDyiqBdcs);
                }
            }
            dataModelBdc.setQlfQlDyiqList(qlfQlDyiqBdcs);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getQlfQlHysyqList())) {
            List<QlfQlHysyqBdc> hysyqBdcs = new ArrayList<>();
            for (QlfQlHysyqDO qlfQlHysyqDO : messageModel.getDataModel().getQlfQlHysyqList()) {
                QlfQlHysyqBdc qlfQlHysyqBdc = new QlfQlHysyqBdc();
                BeanUtils.copyProperties(qlfQlHysyqDO, qlfQlHysyqBdc);
                qlfQlHysyqBdc.setDfdjlxmc(dfdjlxmc);
                qlfQlHysyqBdc.setDfqllxmc(dfqllxmc);
                hysyqBdcs.add(qlfQlHysyqBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(hysyqBdcs);
                }
            }
            dataModelBdc.setQlfQlHysyqList(hysyqBdcs);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getQlfQlJsydsyqList())) {
            List<QlfQlJsydsyqBdc> qlfQlJsydsyqBdcs = new ArrayList<>();
            for (QlfQlJsydsyqDO qlfQlJsydsyqDO : messageModel.getDataModel().getQlfQlJsydsyqList()) {
                QlfQlJsydsyqBdc qlfQlJsydsyqBdc = new QlfQlJsydsyqBdc();
                BeanUtils.copyProperties(qlfQlJsydsyqDO, qlfQlJsydsyqBdc);
                qlfQlJsydsyqBdc.setDfdjlxmc(dfdjlxmc);
                qlfQlJsydsyqBdc.setDfqllxmc(dfqllxmc);
                qlfQlJsydsyqBdcs.add(qlfQlJsydsyqBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(qlfQlJsydsyqBdcs);
                }
            }
            dataModelBdc.setQlfQlJsydsyqList(qlfQlJsydsyqBdcs);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getQlfQlNydsyqList())) {
            List<QlfQlNydsyqBdc> qlfQlNydsyqBdcs = new ArrayList<>();
            for (QlfQlNydsyqDO qlfQlNydsyqDO : messageModel.getDataModel().getQlfQlNydsyqList()) {
                QlfQlNydsyqBdc qlfQlNydsyqBdc = new QlfQlNydsyqBdc();
                BeanUtils.copyProperties(qlfQlNydsyqDO, qlfQlNydsyqBdc);

                qlfQlNydsyqBdc.setDfdjlxmc(dfdjlxmc);
                qlfQlNydsyqBdc.setDfqllxmc(dfqllxmc);
                qlfQlNydsyqBdc.setDfsyttlxmc(StringToolUtils.convertBeanPropertyValueOfZdByString(qlfQlNydsyqDO.getSyttlx(), zdMap.get(Constants.SYTTLX)));
                qlfQlNydsyqBdc.setDfyzyfsmc(StringToolUtils.convertBeanPropertyValueOfZdByString(qlfQlNydsyqDO.getYzyfs(), zdMap.get(Constants.YZYFS)));
                qlfQlNydsyqBdcs.add(qlfQlNydsyqBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(qlfQlNydsyqBdcs);
                }
            }
            dataModelBdc.setQlfQlNydsyqList(qlfQlNydsyqBdcs);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getQlfQlQtxgqlList())) {
            List<QlfQlQtxgqlBdc> qlfQlQtxgqlBdcs = new ArrayList<>();
            for (QlfQlQtxgqlDO qlfQlQtxgqlDO : messageModel.getDataModel().getQlfQlQtxgqlList()) {
                QlfQlQtxgqlBdc qlfQlQtxgqlBdc = new QlfQlQtxgqlBdc();
                BeanUtils.copyProperties(qlfQlQtxgqlDO, qlfQlQtxgqlBdc);

                qlfQlQtxgqlBdc.setDfdjlxmc(dfdjlxmc);
                qlfQlQtxgqlBdc.setDfqllxmc(dfqllxmc);
                qlfQlQtxgqlBdcs.add(qlfQlQtxgqlBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(qlfQlQtxgqlBdcs);
                }
            }
            dataModelBdc.setQlfQlQtxgqlList(qlfQlQtxgqlBdcs);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getQlfQlTdsyqList())) {
            List<QlfQlTdsyqBdc> qlfQlTdsyqBdcs = new ArrayList<>();
            for (QlfQlTdsyqDO qlfQlTdsyqDO : messageModel.getDataModel().getQlfQlTdsyqList()) {
                QlfQlTdsyqBdc qlfQlTdsyqBdc = new QlfQlTdsyqBdc();
                BeanUtils.copyProperties(qlfQlTdsyqDO, qlfQlTdsyqBdc);
                qlfQlTdsyqBdc.setDfdjlxmc(dfdjlxmc);
                qlfQlTdsyqBdc.setDfqllxmc(dfqllxmc);
                qlfQlTdsyqBdcs.add(qlfQlTdsyqBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(qlfQlTdsyqBdcs);
                }
            }
            dataModelBdc.setQlfQlTdsyqList(qlfQlTdsyqBdcs);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getQlfQlYgdjList())) {
            List<QlfQlYgdjBdc> qlfQlYgdjDOS = new ArrayList<>();
            for (QlfQlYgdjDO qlfQlYgdjDO : messageModel.getDataModel().getQlfQlYgdjList()) {
                QlfQlYgdjBdc qlfQlYgdjBdc = new QlfQlYgdjBdc();
                BeanUtils.copyProperties(qlfQlYgdjDO, qlfQlYgdjBdc);

                qlfQlYgdjBdc.setDfdjlxmc(dfdjlxmc);
                qlfQlYgdjBdc.setDffwjgmc(StringToolUtils.convertBeanPropertyValueOfZdByString(qlfQlYgdjDO.getFwjg(), zdMap.get(Constants.FWJG)));
                qlfQlYgdjBdc.setDfygdjzlmc(StringToolUtils.convertBeanPropertyValueOfZdByString(qlfQlYgdjDO.getYgdjzl(), zdMap.get(Constants.YGDJZL)));
                qlfQlYgdjDOS.add(qlfQlYgdjBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(qlfQlYgdjDOS);
                }
            }
            dataModelBdc.setQlfQlYgdjList(qlfQlYgdjDOS);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getQltFwFdcqDzList())) {
            List<QltFwFdcqDzBdc> qltFwFdcqDzBdcs = new ArrayList<>();
            for (QltFwFdcqDzDO qltFwFdcqDzDO : messageModel.getDataModel().getQltFwFdcqDzList()) {
                QltFwFdcqDzBdc qltFwFdcqDzBdc = new QltFwFdcqDzBdc();
                BeanUtils.copyProperties(qltFwFdcqDzDO, qltFwFdcqDzBdc);

                qltFwFdcqDzBdc.setDfdjlxmc(dfdjlxmc);
                qltFwFdcqDzBdc.setDfqllxmc(dfqllxmc);
                qltFwFdcqDzBdcs.add(qltFwFdcqDzBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(qltFwFdcqDzBdcs);
                }
            }
            dataModelBdc.setQltFwFdcqDzList(qltFwFdcqDzBdcs);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getQltFwFdcqYzList())) {
            List<QltFwFdcqYzBdc> qltFwFdcqYzBdcs = new ArrayList<>();
            for (QltFwFdcqYzDO qltFwFdcqYzDO : messageModel.getDataModel().getQltFwFdcqYzList()) {
                QltFwFdcqYzBdc qltFwFdcqYzBdc = new QltFwFdcqYzBdc();
                BeanUtils.copyProperties(qltFwFdcqYzDO, qltFwFdcqYzBdc);

                qltFwFdcqYzBdc.setDfdjlxmc(dfdjlxmc);
                qltFwFdcqYzBdc.setDfqllxmc(dfqllxmc);
                qltFwFdcqYzBdc.setDffwjgmc(StringToolUtils.convertBeanPropertyValueOfZdByString(qltFwFdcqYzDO.getFwjg(), zdMap.get(Constants.FWJG)));
                qltFwFdcqYzBdcs.add(qltFwFdcqYzBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(qltFwFdcqYzBdcs);
                }
            }
            dataModelBdc.setQltFwFdcqYzList(qltFwFdcqYzBdcs);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getQltQlGjzwsyqList())) {
            List<QltQlGjzwsyqBdc> qltQlGjzwsyqBdcs = new ArrayList<>();
            for (QltQlGjzwsyqDO qltQlGjzwsyqDO : messageModel.getDataModel().getQltQlGjzwsyqList()) {
                QltQlGjzwsyqBdc qltQlGjzwsyqBdc = new QltQlGjzwsyqBdc();
                BeanUtils.copyProperties(qltQlGjzwsyqDO, qltQlGjzwsyqBdc);

                qltQlGjzwsyqBdc.setDfdjlxmc(dfdjlxmc);
                qltQlGjzwsyqBdc.setDfqllxmc(dfqllxmc);
                qltQlGjzwsyqBdc.setDfgjzwlxmc(StringToolUtils.convertBeanPropertyValueOfZdByString(qltQlGjzwsyqDO.getGjzwlx(), zdMap.get(Constants.GJZWLX)));
                qltQlGjzwsyqBdcs.add(qltQlGjzwsyqBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(qltQlGjzwsyqBdcs);
                }
            }
            dataModelBdc.setQltQlGjzwsyqList(qltQlGjzwsyqBdcs);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getQltQlLqList())) {
            List<QltQlLqBdc> qltQlLqBdcs = new ArrayList<>();
            for (QltQlLqDO qltQlLqDO : messageModel.getDataModel().getQltQlLqList()) {
                QltQlLqBdc qltQlLqBdc = new QltQlLqBdc();
                BeanUtils.copyProperties(qltQlLqDO, qltQlLqBdc);

                qltQlLqBdc.setDfdjlxmc(dfdjlxmc);
                qltQlLqBdc.setDfqllxmc(dfqllxmc);
                qltQlLqBdc.setDflzmc(StringToolUtils.convertBeanPropertyValueOfZdByString(qltQlLqDO.getLz(), zdMap.get(Constants.LZ)));
                qltQlLqBdc.setDfqymc(StringToolUtils.convertBeanPropertyValueOfZdByString(qltQlLqDO.getQy(), zdMap.get(Constants.QY)));
                qltQlLqBdcs.add(qltQlLqBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(qltQlLqBdcs);
                }
            }
            dataModelBdc.setQltQlLqList(qltQlLqBdcs);
        }
        if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getZttGyQlrList())) {
            List<ZttGyQlrBdc> zttGyQlrBdcs = new ArrayList<>();
            for (ZttGyQlrDO zttGyQlrDO : messageModel.getDataModel().getZttGyQlrList()) {
                ZttGyQlrBdc zttGyQlrBdc = new ZttGyQlrBdc();
                BeanUtils.copyProperties(zttGyQlrDO, zttGyQlrBdc);

                zttGyQlrBdc.setDfqlrlxmc(StringToolUtils.convertBeanPropertyValueOfZdByString(zttGyQlrDO.getQlrlx(), zdMap.get(Constants.QLRLX)));
                zttGyQlrBdc.setDfgyfsmc(StringToolUtils.convertBeanPropertyValueOfZdByString(zttGyQlrDO.getGyfs(), zdMap.get(Constants.GYFS)));
                zttGyQlrBdcs.add(zttGyQlrBdc);
                if (newDefault) {
                    ClassHandleUtil.setDefaultValueToNullField(zttGyQlrBdcs);
                }
            }
            dataModelBdc.setZttGyQlrList(zttGyQlrBdcs);
        }
        messageModelBdc.setDataModel(dataModelBdc);
        messageModelBdc.setHeadModel(messageModel.getHeadModel());


        try {
            if (!ObjectUtils.isEmpty(messageModelBdc)) {
                List<NationalAccessUpload> list = UploadServiceUtil.listNationalAccessUploadByType(UploadServiceUtil.CITY_ACCESS);
                // 上传
                for (NationalAccessUpload nationalAccessUpload : list) {
                    Boolean hjjg = nationalAccessUpload.upload(messageModelBdc);
                    if (hjjg == null) {
                        continue;
                    } else if (!hjjg) {
                        result = false;
                        break;
                    } else {
                        result = true;
                    }
                }
            }

            LOGGER.debug("汇交报文结束，ywh:{},result: {}", ywh, result);
            String saveFlag = EnvironmentConfig.getEnvironment().getProperty("nationalAccessSaveGxDb");
            if (StringUtils.isNotBlank(saveFlag) && StringUtils.equals(CommonConstantUtils.BOOL_TRUE, saveFlag)) {
                // 不保存DJF_DJ_YWXX
                gxService.saveGxDataModel(messageModel.getDataModel());
                gxService.saveDjfDjYwxxDO(messageModel.getHeadModel().getRecFlowID());
                LOGGER.info("保存共享库结束，ywh:{}, result: {}", ywh, result);
            }
        } catch (Exception e) {
            LOGGER.error("数据汇交失败", e);
        }
        return result;
    }

    /**
     * @param messageModel
     * @param bdcXmList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量抵押业务 由于抵押金额问题 需 单独处理
     */
    private void pldyDealMessageModelDyje(MessageModel messageModel, List<BdcXmDO> bdcXmList) {
        int dyaXmNum = 0;
        // 批量抵押处理抵押金额
        List<MessageModel> messageModelList = new ArrayList<>();
        Double dzwZmj = new Double(0);
        // ywh 和 面积的对照关系
        Map<String, Double> ywhMjMap = Maps.newHashMapWithExpectedSize(bdcXmList.size());
        for (BdcXmDO bdcXmDO : bdcXmList) {
            if (StringUtils.isNotBlank(bdcXmDO.getXmid())) {
                Double tempMj = bdcXmDO.getDzwmj() != null ? bdcXmDO.getDzwmj() : new Double(0);
                // TD 使用 zdzhmj 处理
                if (tempMj == 0 && bdcXmDO.getZdzhmj() != null && bdcXmDO.getBdclx() != null
                        && StringUtils.equals(CommonConstantUtils.BDCLX_TD_DM, bdcXmDO.getBdclx().toString())) {
                    tempMj = bdcXmDO.getZdzhmj();
                }
                dzwZmj += tempMj;
                ywhMjMap.put(bdcXmDO.getXmid(), tempMj);
            }
            if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
                dyaXmNum++;
            }
        }
        LOGGER.info("dyaXmNum:{}", dyaXmNum);
        if (messageModel.getDataModel() != null
                && CollectionUtils.isNotEmpty(messageModel.getDataModel().getQlfQlDyaqList())) {
            QlfQlDyaqDO qlfQlDyaqDO = messageModel.getDataModel().getQlfQlDyaqList().get(0);
            //获取redis缓存中的其他项目抵押金额数据
            JSONObject accessDyjeInfoMap = new JSONObject(4);
            if (redisUtils.isExistKey(ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_REDIS_KEY + bdcXmList.get(0).getGzlslid())) {
                String stringValue = redisUtils.getStringValue(ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_REDIS_KEY + bdcXmList.get(0).getGzlslid());
                if (StringUtils.isNoneBlank(stringValue)) {
                    accessDyjeInfoMap = JSON.parseObject(stringValue);
                }
            }
            LOGGER.info("accessDyjeInfoMap:{}", accessDyjeInfoMap.toJSONString());
            //最后一笔xm抵押金额处理
            if (accessDyjeInfoMap.size() >= (dyaXmNum - 1)) {
                AtomicReference<Double> zgzqse = new AtomicReference<>(0D);
                AtomicReference<Double> bdbzzqse = new AtomicReference<>(0D);
                JSONObject finalAccessDyjeInfoMap = accessDyjeInfoMap;
                accessDyjeInfoMap.keySet().forEach(ywh -> {
                    AccessDyjeInfo object = finalAccessDyjeInfoMap.getObject(ywh, AccessDyjeInfo.class);
                    zgzqse.set(DoubleUtils.sum(zgzqse.get(), new Double(StringUtils.isNotBlank(object.getZgzqse()) ? object.getZgzqse() : "0")));
                    bdbzzqse.set(DoubleUtils.sum(bdbzzqse.get(), new Double(StringUtils.isNotBlank(object.getBdbzzqse()) ? object.getBdbzzqse() : "0")));
                });
                if (zgzqse.get() != 0D) {
                    qlfQlDyaqDO.setZgzqse(DoubleUtils.sub(new Double(qlfQlDyaqDO.getZgzqse()), zgzqse.get()));
                }
                if (bdbzzqse.get() != 0D) {
                    qlfQlDyaqDO.setBdbzzqse(DoubleUtils.sub(qlfQlDyaqDO.getBdbzzqse(), bdbzzqse.get()));
                }
                redisUtils.deleteKey(ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_REDIS_KEY + bdcXmList.get(0).getGzlslid());
            } else {
                AccessDyjeInfo accessDyjeInfo = AccessDyjeInfo.AccessDyjeInfoBuilder.anAccessDyjeInfo().withXmid(qlfQlDyaqDO.getYwh()).build();
                // 最高债权数额
                if (null != qlfQlDyaqDO.getZgzqse()) {
                    Double zgzqse = countDyje(dzwZmj, MapUtils.getDouble(ywhMjMap, qlfQlDyaqDO.getYwh()), new Double(qlfQlDyaqDO.getZgzqse()));
                    //bdcXmList里的第一笔计算逻辑修改为总金额-之前的所有金额
                    if (StringUtils.equals(qlfQlDyaqDO.getYwh(), bdcXmList.get(0).getXmid())) {
                        zgzqse = DoubleUtils.sub(new Double(qlfQlDyaqDO.getZgzqse())
                                , countDyje(dzwZmj, DoubleUtils.sub(dzwZmj, MapUtils.getDouble(ywhMjMap, qlfQlDyaqDO.getYwh())), new Double(qlfQlDyaqDO.getZgzqse())));
                    }
                    if (zgzqse != null) {
                        qlfQlDyaqDO.setZgzqse(zgzqse);
                        accessDyjeInfo.setZgzqse(Double.toString(qlfQlDyaqDO.getZgzqse()));
                    }
                }
                // 被担保被担保主债权数额
                if (null != qlfQlDyaqDO.getBdbzzqse()) {
                    Double bdbzzqse = countDyje(dzwZmj, MapUtils.getDouble(ywhMjMap, qlfQlDyaqDO.getYwh()), new Double(qlfQlDyaqDO.getBdbzzqse()));
                    //bdcXmList里的计算逻辑修改为总金额-之前的所有金额
                    if (StringUtils.equals(qlfQlDyaqDO.getYwh(), bdcXmList.get(0).getXmid())) {
                        bdbzzqse = DoubleUtils.sub(new Double(qlfQlDyaqDO.getBdbzzqse())
                                , countDyje(dzwZmj, DoubleUtils.sub(dzwZmj, MapUtils.getDouble(ywhMjMap, qlfQlDyaqDO.getYwh())), new Double(qlfQlDyaqDO.getBdbzzqse())));

                    }
                    if (bdbzzqse != null) {
                        qlfQlDyaqDO.setBdbzzqse(bdbzzqse);
                        accessDyjeInfo.setBdbzzqse(String.valueOf(qlfQlDyaqDO.getBdbzzqse()));
                    }
                }
                accessDyjeInfoMap.put(qlfQlDyaqDO.getYwh(), accessDyjeInfo);
                redisUtils.addStringValue(ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_REDIS_KEY + bdcXmList.get(0).getGzlslid(), JSON.toJSONString(accessDyjeInfoMap));
            }
        }
    }

    private void pldyDealMessageModelDyjeOld(MessageModelOld messageModel, List<BdcXmDO> bdcXmList) {
        int dyaXmNum = 0;
        // 批量抵押处理抵押金额
        List<MessageModel> messageModelList = new ArrayList<>();
        Double dzwZmj = new Double(0);
        // ywh 和 面积的对照关系
        Map<String, Double> ywhMjMap = Maps.newHashMapWithExpectedSize(bdcXmList.size());
        for (BdcXmDO bdcXmDO : bdcXmList) {
            if (StringUtils.isNotBlank(bdcXmDO.getXmid())) {
                Double tempMj = bdcXmDO.getDzwmj() != null ? bdcXmDO.getDzwmj() : new Double(0);
                // TD 使用 zdzhmj 处理
                if (tempMj == 0 && bdcXmDO.getZdzhmj() != null && bdcXmDO.getBdclx() != null
                        && StringUtils.equals(CommonConstantUtils.BDCLX_TD_DM, bdcXmDO.getBdclx().toString())) {
                    tempMj = bdcXmDO.getZdzhmj();
                }
                dzwZmj += tempMj;
                ywhMjMap.put(bdcXmDO.getXmid(), tempMj);
            }
            if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
                dyaXmNum++;
            }
        }
        LOGGER.info("dyaXmNum:{}", dyaXmNum);
        if (messageModel.getDataModel() != null
                && CollectionUtils.isNotEmpty(messageModel.getDataModel().getQlfQlDyaqList())) {
            QlfQlDyaqOldDO qlfQlDyaqDO = messageModel.getDataModel().getQlfQlDyaqList().get(0);
            //获取redis缓存中的其他项目抵押金额数据
            JSONObject accessDyjeInfoMap = new JSONObject(4);
            if (redisUtils.isExistKey(ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_REDIS_KEY + bdcXmList.get(0).getGzlslid())) {
                String stringValue = redisUtils.getStringValue(ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_REDIS_KEY + bdcXmList.get(0).getGzlslid());
                if (StringUtils.isNoneBlank(stringValue)) {
                    accessDyjeInfoMap = JSON.parseObject(stringValue);
                }
            }
            LOGGER.info("accessDyjeInfoMap:{}", accessDyjeInfoMap.toJSONString());
            //最后一笔xm抵押金额处理
            if (accessDyjeInfoMap.size() >= (dyaXmNum - 1)) {
                AtomicReference<Double> zgzqse = new AtomicReference<>(0D);
                AtomicReference<Double> bdbzzqse = new AtomicReference<>(0D);
                JSONObject finalAccessDyjeInfoMap = accessDyjeInfoMap;
                accessDyjeInfoMap.keySet().forEach(ywh -> {
                    AccessDyjeInfo object = finalAccessDyjeInfoMap.getObject(ywh, AccessDyjeInfo.class);
                    zgzqse.set(DoubleUtils.sum(zgzqse.get(), new Double(StringUtils.isNotBlank(object.getZgzqse()) ? object.getZgzqse() : "0")));
                    bdbzzqse.set(DoubleUtils.sum(bdbzzqse.get(), new Double(StringUtils.isNotBlank(object.getBdbzzqse()) ? object.getBdbzzqse() : "0")));
                });
                if (zgzqse.get() != 0D) {
                    qlfQlDyaqDO.setZgzqse(Double.toString(DoubleUtils.sub(new Double(qlfQlDyaqDO.getZgzqse()), zgzqse.get())));
                }
                if (bdbzzqse.get() != 0D) {
                    qlfQlDyaqDO.setBdbzzqse(Double.toString(DoubleUtils.sub(new Double(qlfQlDyaqDO.getBdbzzqse()), bdbzzqse.get())));
                }
                redisUtils.deleteKey(ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_REDIS_KEY + bdcXmList.get(0).getGzlslid());
            } else {
                AccessDyjeInfo accessDyjeInfo = AccessDyjeInfo.AccessDyjeInfoBuilder.anAccessDyjeInfo().withXmid(qlfQlDyaqDO.getYwh()).build();
                // 最高债权数额
                if (null != qlfQlDyaqDO.getZgzqse()) {
                    Double zgzqse = countDyje(dzwZmj, MapUtils.getDouble(ywhMjMap, qlfQlDyaqDO.getYwh()), new Double(qlfQlDyaqDO.getZgzqse()));
                    //bdcXmList里的第一笔计算逻辑修改为总金额-之前的所有金额
                    if (StringUtils.equals(qlfQlDyaqDO.getYwh(), bdcXmList.get(0).getXmid())) {
                        zgzqse = DoubleUtils.sub(new Double(qlfQlDyaqDO.getZgzqse())
                                , countDyje(dzwZmj, DoubleUtils.sub(dzwZmj, MapUtils.getDouble(ywhMjMap, qlfQlDyaqDO.getYwh())), new Double(qlfQlDyaqDO.getZgzqse())));
                    }
                    if (zgzqse != null) {
                        qlfQlDyaqDO.setZgzqse(zgzqse.toString());
                        accessDyjeInfo.setZgzqse(qlfQlDyaqDO.getZgzqse());
                    }
                }
                // 被担保被担保主债权数额
                if (StringUtils.isNotBlank(qlfQlDyaqDO.getBdbzzqse())) {
                    Double bdbzzqse = countDyje(dzwZmj, MapUtils.getDouble(ywhMjMap, qlfQlDyaqDO.getYwh()), new Double(qlfQlDyaqDO.getBdbzzqse()));
                    //bdcXmList里的计算逻辑修改为总金额-之前的所有金额
                    if (StringUtils.equals(qlfQlDyaqDO.getYwh(), bdcXmList.get(0).getXmid())) {
                        bdbzzqse = DoubleUtils.sub(new Double(qlfQlDyaqDO.getBdbzzqse())
                                , countDyje(dzwZmj, DoubleUtils.sub(dzwZmj, MapUtils.getDouble(ywhMjMap, qlfQlDyaqDO.getYwh())), new Double(qlfQlDyaqDO.getBdbzzqse())));

                    }
                    if (bdbzzqse != null) {
                        qlfQlDyaqDO.setBdbzzqse(bdbzzqse.toString());
                        accessDyjeInfo.setBdbzzqse(qlfQlDyaqDO.getBdbzzqse());
                    }
                }
                accessDyjeInfoMap.put(qlfQlDyaqDO.getYwh(), accessDyjeInfo);
                redisUtils.addStringValue(ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_REDIS_KEY + bdcXmList.get(0).getGzlslid(), JSON.toJSONString(accessDyjeInfoMap));
            }
        }
    }

    /**
     * @param dzwZmj      定着物总面积
     * @param singleDzwmj 单个定着物面积
     * @param dyje        抵押总额
     * @return java.lang.Double
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    private static Double countDyje(Double dzwZmj, Double singleDzwmj, Double dyje) {
        if (dyje != null && singleDzwmj != null && dzwZmj != null) {
            Double result = (singleDzwmj / dzwZmj) * dyje;

            if (result.isInfinite() || result.isNaN()) {
                return null;
            }

            BigDecimal b = new BigDecimal(result);
            result = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return result;
        }
        return null;
    }

    @Transactional(transactionManager = "serverTransactionManager", rollbackFor = Exception.class)
    public void saveAccessFailData(BdcAccessLog bdcAccessLog, Integer bs, BdcXmDO bdcXmDO, String xyxx) {
        String bwid = "";
        try {
            if (Objects.nonNull(bdcXmDO) && StringUtils.isNotBlank(bdcXmDO.getXmid())) {
                bwid = accesssModelHandlerService.getBizMsgId(bdcXmDO);
                LOGGER.error("当前xmid={}，组织报文失败，开始删除bwid=xmid 的数据", bdcXmDO.getXmid());
                Example example = new Example(bdcAccessLog.getClass());
                example.createCriteria().andEqualTo("ywbwid", bdcXmDO.getXmid());
                entityMapper.deleteByExampleNotNull(example);
                //之前的业务流水号相关数据全部更新成历史数据
                Example oldexample = new Example(bdcAccessLog.getClass());
                oldexample.createCriteria().andEqualTo("ywlsh", bdcXmDO.getXmid());
                List<BdcAccessLog> bdcAccessLogList = entityMapper.selectByExampleNotNull(oldexample);
                if (CollectionUtils.isNotEmpty(bdcAccessLogList)) {
                    Map<String, Object> batchUpdateAccessLogMap = new HashMap<>();
                    batchUpdateAccessLogMap.put("cgbs", JrRzCgbsEnum.HISTORY_DATA.getBs());
                    batchUpdateAccessLogMap.put("xmids", Collections.singletonList(bdcXmDO.getXmid()));
                    batchUpdateAccessLogMap.put("bdcdyhs", Collections.singletonList(bdcXmDO.getBdcdyh()));
                    batchUpdateAccessLogMap.put("fjxx", DateUtils.getCurrentTimeStr() + "由于报文组织失败，重新生成一条新的准确的bwid数据，需要将当前数据更新为历史状态，并插入新数据bwid=" + bwid);
                    if (bdcAccessLog instanceof CityAccess) {
                        bdcdjMapper.batchUpdateAccessLogCgbsCity(batchUpdateAccessLogMap);
                    }
                }
                //新插入一条bwid数据
                bdcAccessLog.setCgbs(bs);
                bdcAccessLog.setXyxx(JrRzCgbsEnum.getMsgByBs(bs) + ":" + StringToolUtils.left(xyxx, 3900));
                bdcAccessLog.setYwlsh(bdcXmDO.getXmid());
                bdcAccessLog.setYwbwid(bwid);
                bdcAccessLog.setUpdatetime(new Date());
                bdcAccessLog.setJrrq(new Date());
                bdcAccessLog.setBdcdyh(bdcXmDO.getBdcdyh());
                entityMapper.saveOrUpdate(bdcAccessLog, bdcAccessLog.getYwbwid());
            }
        } catch (Exception e) {
            asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 3, "上报数据组织报文异常，更新接入数据时存在异常" + StringUtils.left(e.getMessage(), 1000), bwid, new Date(), "0");
            //发送通知
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_2.getYjlx());
            msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
            LOGGER.info("根据BS保存接入表失败！xmid:{},失败原因：{}", bdcXmDO.getXmid(), e.getMessage());
            throw new AppException("报文组织失败且重新生成报文id保存时出现异常！");
        }
    }

}
