package cn.gtmap.realestate.exchange.service.impl;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.exchange.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.exchange.core.domain.BdcJrDbrzjlDO;
import cn.gtmap.realestate.exchange.core.domain.BdcXmDO;
import cn.gtmap.realestate.exchange.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.*;
import cn.gtmap.realestate.exchange.core.domain.exchange.old.MessageModelOld;
import cn.gtmap.realestate.exchange.core.dto.AccessDyjeInfo;
import cn.gtmap.realestate.exchange.core.dto.AccessLogDTO;
import cn.gtmap.realestate.exchange.core.dto.BdcCshFwkgSlDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.accessLog.AccessLogMapper;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.core.national.NationalAccess;
import cn.gtmap.realestate.exchange.core.national.ProvinceAccess;
import cn.gtmap.realestate.exchange.core.qo.BdcDbrzQO;
import cn.gtmap.realestate.exchange.core.qo.BdcJrrzQO;
import cn.gtmap.realestate.exchange.core.qo.BdcXmQO;
import cn.gtmap.realestate.exchange.core.qo.JgWxzBwxxQO;
import cn.gtmap.realestate.exchange.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.exchange.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.exchange.core.thread.AutoAccessHandlerThread;
import cn.gtmap.realestate.exchange.core.thread.ThreadEngine;
import cn.gtmap.realestate.exchange.service.AccessModelHandlerService;
import cn.gtmap.realestate.exchange.service.impl.national.NationalAccessXmlContext;
import cn.gtmap.realestate.exchange.service.national.*;
import cn.gtmap.realestate.exchange.service.national.access.AccessDefaultValueService;
import cn.gtmap.realestate.exchange.util.*;
import cn.gtmap.realestate.exchange.util.enums.JrRzCgbsEnum;
import cn.gtmap.realestate.exchange.util.rsa.RsaUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
 * @version 1.0, 2018/12/13
 * @description 数据汇交 service 实现
 */
@Service
public class AccessModelHandlerServiceImpl implements AccessModelHandlerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessModelHandlerServiceImpl.class);

    private static final String ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_REDIS_KEY = "ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_";
    public static final String UNKNOWN = "unknown";


    @Autowired
    @Qualifier("serverEntityMapper")
    private EntityMapper entityMapper;
    @Autowired
    private BdcXmMapper bdcXmMapper;
    @Autowired
    private NationalAccessXmlContext nationalAccessXmlContext;

    @Autowired
    private NationalAccessHeadModelService nationalAccessHeadModelService;

    @Autowired
    private BdcExchangeZddzService bdcExchangeZddzService;

    @Autowired
    private AccessDefaultValueService accessDefaultValueService;
    @Autowired
    private XmlEntityConvertUtil xmlEntityConvertUtil;
    @Autowired
    private AccessLogMapper accessLogMapper;
    /**
     * 抵押业务工作流定义id
     */
    @Value("#{'${access.dyaGzldyidArr:}'.split(',')}")
    private List<String> dyaGzldyidArr;
    /**
     * 抵押上报不超过几个月配置，为整数型
     */
    @Value("${dyaMonth:6}")
    private int dyaMonth;

    /**
     * 判断是否过滤存量历史数据上报
     */
    @Value("${access.filter.history.data.flag:false}")
    private boolean accessFilterHistoryDataFlag;
    /**
     * #是否启用新上报方式
     * access.newAccess: true
     */
    @Value("${access.newAccess:true}")
    private boolean newAccessModel;
    // 判断是否在上报批量抵押业务时 修改头信息中的业务流水号和所有实体中的ywh字段 为 SLBH
    @Value("${access.pldy.dealYwh:false}")
    private boolean accessPldyDealYwh;
    // 判断是否在上报批量抵押业务时  处理抵押金额
    @Value("${access.pldy.dealDyje:false}")
    private boolean accessPldyDealDyje;

    @Value("${access.batch.process.thread.threshold.value:1000}")
    private int thresholdValue;

    @Value("${access.batch.process.thread.step.value:500}")
    private int stepValue;
    @Autowired
    private AccessModelServiceThread accessModelServiceThread;
    @Autowired
    private ThreadEngine threadEngine;
    //big标识开关
    @Value("${accessLog.turn-on-bizbs: false}")
    private boolean accessTurnOnBizBs;

    /*
     * 新上报标识逻辑是否开启
     * */
    @Value("${access.newbizbs: false}")
    private boolean newAccessBizBs;

    /*
     * 业务汇交上报校验
     * */
    @Value("#{'${access.check.gzyzCode:1,2,3}'.split(',')}")
    private List<String> gzyzCodeList;

    /**
     * 国家上报
     */
    @Value("${nationalAccess.switch:false}")
    public boolean nationalSwtich;

    /**
     * 省级上报
     */
    @Value("${provinceAccess.switch:true}")
    public boolean provinceSwtich;

    @Autowired
    AsyncDealUtils asyncDealUtils;

    /**
     * @param bdcXmDO
     * @param bdcXmDOList 用于处理一些逻辑，为了防止每次都查就改到外面传，可以不传。
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理自动汇交具体方法
     */
    @Override
    public void access(BdcXmDO bdcXmDO, List<BdcXmDO> bdcXmDOList) {

        //刚开始就记录数据到接入表中，cgbs为3，等待组织报文

        LOGGER.info("====================刚开始上报，存入数据库接入表一条======================:{}", bdcXmDO.getXmid());
        //数据接收，异步存入接入操作日志表，此时没有生成bwid
        asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 1, "服务接收到需要上报的项目信息，下一步进行配置文件校验", "", new Date(), "1");
        if (nationalSwtich) {
            saveBdcAccess(new NationalAccess(), JrRzCgbsEnum.BEGIN_WSB.getBs(), bdcXmDO, "");
        }
        if (provinceSwtich) {
            saveBdcAccess(new ProvinceAccess(), JrRzCgbsEnum.BEGIN_WSB.getBs(), bdcXmDO, "");
        }
        //判断是否上报
        if (!sfsb(bdcXmDO, "true", true)) {
            LOGGER.warn("当前项目不需要上报，结束上报服务,bdcXmDO.getXmid：{}", bdcXmDO.getXmid());
            return;
        }
        LOGGER.info("开始上报汇交,bdcXmDO.getXmid：{}", bdcXmDO.getXmid());

        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            bdcXmDOList = bdcXmMapper.queryBdcXmList(bdcXmDO.getGzlslid());
        }
        MessageModel messageModel = new MessageModel();
        //旧版上报模型
        MessageModelOld messageModelOld;
        try {
            // 获取不同类型的策略 service
            NationalAccessXmlService nationalAccessXmlService = getAccessXmlService(bdcXmDO);
            // 利用对应策略的 service 生成 message
            if (!ObjectUtils.isEmpty(nationalAccessXmlService)) {
                asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 2, "根据登记类型" + bdcXmDO.getDjlx() + "成功找到对应的上报策略服务，下一步组织报文", "", new Date(), "1");
                // 获取 message
                //新上报实体
                try {
                    messageModel = getMessageModel(nationalAccessXmlService, bdcXmDO.getXmid());
                    asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 3, "上报报文数据组织成功，生成报文id", messageModel.getHeadModel().getBizMsgID(), new Date(), "1");
                    int lclx = bdcXmLxByAllXm(bdcXmDOList);
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
                        Map<String, String> queryYxmidMap = new HashMap<>();
                        queryYxmidMap.put("xmid", bdcXmDO.getXmid());
                        List<BdcXmLsgxDO> bdcXmLsgxDOS = bdcXmMapper.queryYxmidByXmidForZh(queryYxmidMap);
                        if (CollectionUtils.isNotEmpty(bdcXmLsgxDOS) && StringUtils.isNotBlank(bdcXmLsgxDOS.get(0).getYxmid()) && CollectionUtils.isNotEmpty(messageModel.getDataModel().getQlfQlDyaqList())) {
                            messageModel.getDataModel().getQlfQlDyaqList().forEach(qlfQlDyaqDO -> {
                                qlfQlDyaqDO.setSsywh(bdcXmLsgxDOS.get(0).getYxmid());
                            });
                        }
                    }

                } catch (Exception e) {
                    LOGGER.info("组织上报数据报错！xmid:{},错误原因：{}", bdcXmDO.getXmid(), e.toString());
                    if (nationalSwtich) {
                        saveBdcAccess(new NationalAccess(), JrRzCgbsEnum.BWZZSB_WSB.getBs(), bdcXmDO, StringUtils.left(e.getMessage(), 1000));
                    }
                    if (provinceSwtich) {
                        saveBdcAccess(new ProvinceAccess(), JrRzCgbsEnum.BWZZSB_WSB.getBs(), bdcXmDO, StringUtils.left(e.getMessage(), 1000));
                    }
                    //异步保存接入操作日志
                    asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 3, "上报数据组织报文失败异常" + StringUtils.left(e.getMessage(), 1000), "", new Date(), "0");
                }
                autoAccessByMessageModel(messageModel);
                LOGGER.info("数据汇交 - 成功 xmid：{}", bdcXmDO.getXmid());
            } else {
                //异步保存结果日志
                asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 2, "根据登记类型" + bdcXmDO.getDjlx() + "没有找到对应的上报策略服务返回空", "", new Date(), "0");
                LOGGER.info("数据汇交 - 未找到对应的策略服务 xmid：{}", bdcXmDO.getXmid());
            }
        } catch (Exception e) {
            LOGGER.error("数据汇交失败：bdcXmDO.getXmid：{}", bdcXmDO.getXmid(), e);
        }
    }

    /**
     * @param bdcXmDO
     * @return
     * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
     * @description 根据项目编码获取相应的策略服务
     */
    @Override
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
     * @param nationalAccessXmlService
     * @param xmid
     * @return
     * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
     * @description 获取上报数据对象模型
     */
    @Override
    public MessageModel getMessageModel(NationalAccessXmlService nationalAccessXmlService, String xmid) {
        MessageModel messageModel = new MessageModel();
        DataModel dataModel = nationalAccessXmlService.getNationalAccessDataModel(xmid);
        HeadModel headModel = nationalAccessHeadModelService.getAccessHeadModel(xmid);
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

            // 将 redis 替换成内存缓存
            if(MemoryCacheUtil.isExistKey(ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_REDIS_KEY + bdcXmList.get(0).getGzlslid())){
                String stringValue = MemoryCacheUtil.getStringValue(ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_REDIS_KEY + bdcXmList.get(0).getGzlslid());
                if (StringUtils.isNoneBlank(stringValue)) {
                    accessDyjeInfoMap = JSON.parseObject(stringValue);
                }
            }
            
//            if (redisUtils.isExistKey()) {
//                String stringValue = redisUtils.getStringValue(ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_REDIS_KEY + bdcXmList.get(0).getGzlslid());
//                if (StringUtils.isNoneBlank(stringValue)) {
//                    accessDyjeInfoMap = JSON.parseObject(stringValue);
//                }
//            }

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
                    qlfQlDyaqDO.setBdbzzqse(Double.toString(DoubleUtils.sub(new Double(qlfQlDyaqDO.getBdbzzqse()), bdbzzqse.get())));
                }
//                redisUtils.deleteKey(ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_REDIS_KEY + bdcXmList.get(0).getGzlslid());
                MemoryCacheUtil.deleteKey(ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_REDIS_KEY + bdcXmList.get(0).getGzlslid());
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
                //redisUtils.addStringValue(ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_REDIS_KEY + bdcXmList.get(0).getGzlslid(), JSON.toJSONString(accessDyjeInfoMap));
                MemoryCacheUtil.addStringValue(ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_REDIS_KEY + bdcXmList.get(0).getGzlslid(), JSON.toJSONString(accessDyjeInfoMap));
            }
        }
    }

    /**
     * @param messageModel
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 针对已有messageModel结构的场景  汇报上交
     */
    @Override
    public boolean autoAccessByMessageModel(MessageModel messageModel) {
        boolean result = false;
        try {
            if (!ObjectUtils.isEmpty(messageModel)) {
                List<NationalAccessUpload> list = UploadServiceUtil.listNationalAccessUpload();
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
        } catch (Exception e) {
            LOGGER.error("数据汇交失败", e);
            if (nationalSwtich) {
                saveBdcAccessByMessageModel(new NationalAccess(), JrRzCgbsEnum.ACCESS_FAIL.getBs(), messageModel, StringUtils.left(e.getMessage(), 1000));
            }
            if (provinceSwtich) {
                saveBdcAccessByMessageModel(new ProvinceAccess(), JrRzCgbsEnum.ACCESS_FAIL.getBs(), messageModel, StringUtils.left(e.getMessage(), 1000));
            }
        }
        return result;
    }


    /**
     * 判定该流程的类型
     *
     * @param bdcXmList 不动产项目集合
     * @return 1:普通  2：组合  3：批量  4:批量组合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 判定该流程的类型
     */
    public int bdcXmLxByAllXm(@NotEmpty(message = "项目集合不能为空") List<BdcXmDO> bdcXmList) {
        int lx = CommonConstantUtils.LCLX_PT;
        boolean multiple = bdcXmList.size() > 1;
        if (multiple) {
            //默认赋值为批量
            lx = CommonConstantUtils.LCLX_PL;
            //循环项目查看是否有多个不动产单元
            Set<String> bdcdyhSet = new HashSet<>();
            for (BdcXmDO xm : bdcXmList) {
                //已经存在同一个不动产单元的为组合
                if (bdcdyhSet.contains(xm.getBdcdyh())) {
                    lx = CommonConstantUtils.LCLX_ZH;
                }
                bdcdyhSet.add(xm.getBdcdyh());
                //组合的判定是不是批量组合
                if (CommonConstantUtils.LCLX_ZH.equals(lx) && bdcdyhSet.size() > 1) {
                    lx = CommonConstantUtils.LCLX_PLZH;
                    break;
                }
            }
        }
        return lx;
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

    private String getZsxhOrDjxlByBdcxm(BdcXmDO bdcXmDO) {
        BdcCshFwkgSlDO bdcCshFwkgSlDO = entityMapper.selectByPrimaryKey(BdcCshFwkgSlDO.class, bdcXmDO.getXmid());
        String key = UNKNOWN;
        if (bdcCshFwkgSlDO != null && bdcCshFwkgSlDO.getZsxh() != null) {
            key = bdcCshFwkgSlDO.getZsxh().toString();
        } else if (bdcCshFwkgSlDO != null && StringUtils.isNotBlank(bdcCshFwkgSlDO.getDjxl())) {
            key = bdcCshFwkgSlDO.getDjxl();
        }
        return key;
    }

    /**
     * @param bdcXmDO
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    public String getBizMsgId(BdcXmDO bdcXmDO) {
        if (bdcXmDO == null) {
            return "";
        }
        String qxdm = bdcXmDO.getQxdm();
        String bdcdyh = bdcXmDO.getBdcdyh();
        if (StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() == 28) {
            qxdm = bdcdyh.substring(0, 6);
        }
        String bizMsgId = "";
        String bbbs = "";

        /**
         * （1）补报的报文，当天登簿日志未上报时，报文id不加后缀；
         * （2）重新上报的报文，当天登簿日志未上报时，报文id不加后缀；
         * （3）补报的报文，当天登簿日志已上报，报文id增加“BBXZ”标识(报文ID_ BBXZ)；
         * （4）重新上报的报文，当天登簿日志已上报，报文id增加“CBXZ”标识（报文ID_CBXZ）；
         * （5）对数据质量整改完善后，如需重新上报更新报文信息，在原报文id后增加“BL”标识（报文ID_BL）；
         */
        if (accessTurnOnBizBs) {
            //升级销账的逻辑
            if (newAccessBizBs) {
                JgWxzBwxxQO jgWxzBwxxQO = new JgWxzBwxxQO();
                jgWxzBwxxQO.setYwh(bdcXmDO.getXmid());
                List<JgWxzbwxxDO> jgWxzbwxxDOList = accessLogMapper.queryWxzBwxx(jgWxzBwxxQO);
                if (CollectionUtils.isNotEmpty(jgWxzbwxxDOList)) {
                    JgWxzbwxxDO jgWxzbwxxDO = jgWxzbwxxDOList.get(0);
                    //判断xmid是否已经上报
//                    List<BdcJrDbrzjlDO> dbrzjlDOS = accessLogMapper.listBdcJrDbrzjlList(qxdm, bdcXmDO.getDjsj());
//                    if (CollectionUtils.isNotEmpty(dbrzjlDOS)) {
                    BdcAccessLog jrbwDO = accessLogMapper.getAccessLogNotXmid(bdcXmDO.getXmid());
                    if (null != jrbwDO) {
                        bizMsgId = jrbwDO.getYwbwid().substring(0, 18);
                        switch (jgWxzbwxxDO.getXzzt()) {
                            case "1":
                            case "4":
                                if (StringUtils.equals(CommonConstantUtils.XZBWLX_JRSB, jgWxzbwxxDO.getXzbwlx()) || StringUtils.equals(cn.gtmap.realestate.common.util.CommonConstantUtils.XZBWLX_RKSB, jgWxzbwxxDO.getXzbwlx())) {
                                    //
                                    LOGGER.warn("当前xmid={}在未销账报文信息数据xzbwlx={}---xzzt={}，增加后缀标识CBXZ", bdcXmDO.getXmid(), jgWxzbwxxDO.getXzbwlx(), jgWxzbwxxDO.getXzzt());
                                    bbbs = "CBXZ";
                                    }
                                    if (StringUtils.equals(CommonConstantUtils.XZBWLX_WSB, jgWxzbwxxDO.getXzbwlx())) {
                                        LOGGER.warn("当前xmid={}在未销账报文信息数据xzbwlx={}---xzzt={}，增加后缀标识BBXZ", bdcXmDO.getXmid(), jgWxzbwxxDO.getXzbwlx(), jgWxzbwxxDO.getXzzt());
                                        bbbs = "BBXZ";
                                    }
                                    break;
                                case "3":
                                    LOGGER.warn("当前xmid={}在未销账报文信息数据xzzt={}，增加后缀标识BL", bdcXmDO.getXmid(), jgWxzbwxxDO.getXzzt());
                                    bbbs = "BL";
                                    break;
                                case "2":
                                    //正在销账提示异常
                                    throw new AppException("正在销账,不允许上报");
                                default:
                                    break;
                            }
                        } else {
                            bbbs = "BBXZ";
                        }
//                    }
                }
            } else {
                List<BdcJrDbrzjlDO> dbrzjlDOS = accessLogMapper.listBdcJrDbrzjlList(qxdm, bdcXmDO.getDjsj());
                if (CollectionUtils.isNotEmpty(dbrzjlDOS)) {
                    //判断改xmid是否已经上报
                    BdcAccessLog jrbwDO = accessLogMapper.getProvinceAccessYwbwidByXmid(bdcXmDO.getXmid());
                    if (null != jrbwDO) {
                        if (JrRzCgbsEnum.ACCESS_FAIL.getBs().equals(jrbwDO.getCgbs())
                                || JrRzCgbsEnum.HISTORY_DATA.getBs().equals(jrbwDO.getCgbs())
                                || JrRzCgbsEnum.WATING_RESP.getBs().equals(jrbwDO.getCgbs())) {
                            //也属于第三种情况
                            bizMsgId = jrbwDO.getYwbwid().substring(0, 18);
                            bbbs = "BBXZ";
                        }
                        if (JrRzCgbsEnum.RESP_FAIL.getBs().equals(jrbwDO.getCgbs())) {
                            bizMsgId = jrbwDO.getYwbwid().substring(0, 18);
                            bbbs = "CBXZ";
                        }
                        if (JrRzCgbsEnum.RESP_SUCCESS.getBs().equals(jrbwDO.getCgbs())) {
                            bizMsgId = jrbwDO.getYwbwid().substring(0, 18);
                            bbbs = "BL";
                        }
                    } else {
                        bbbs = "BBXZ";
                    }
                }
            }
        }
        if (StringUtils.isBlank(bizMsgId)) {
            bizMsgId = getBizMsgId(qxdm);
        }
        bizMsgId += bbbs;

        return bizMsgId;
    }

    /**
     * @param qxdm
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据区县代码 获取流水号
     */
    @Override
    public String getBizMsgId(String qxdm) {
        String bizMsgId = getBizId().toString();
        if (bizMsgId.length() > 6) {
            bizMsgId = bizMsgId.substring(bizMsgId.length() - 6);
        } else {
            bizMsgId = String.format("%06d", Integer.valueOf(bizMsgId));
        }
        FastDateFormat fastDateFormat = FastDateFormat.getInstance("yyMMdd");
        bizMsgId = fastDateFormat.format(new Date()) + bizMsgId;

        //业务报文 ID
        bizMsgId = qxdm + bizMsgId;
        return bizMsgId;
    }

    /**
     * 获取 BizId
     *
     * @return
     */
    private synchronized Integer getBizId() {
        return bdcXmMapper.getBizMsgID();
    }

    /**
     * @param messageModel
     * @return
     * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
     * @description 根据上报数据对象模型生成 xml 字符串
     */
    @Override
    public String getAccessXML(MessageModel messageModel) {
        if (messageModel != null) {
            LOGGER.info("组织xml的实体参数为：{}", messageModel.toString());
            String xml = xmlEntityConvertUtil.entityToXml(messageModel);
            xml = RsaUtil.setRsaSign(xml);
            return xml;
        }
        return null;
    }

    @Override
    public void autoAccessByXmidList(List<String> xmidList) {
        if (CollectionUtils.isNotEmpty(xmidList)) {
            for (String xmid : xmidList) {
                autoAccessByXmid(xmid);
            }
        }
    }

    /**
     * @param startDate
     * @param endDate
     * @return void
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据时间区间获取要上报的xmidList
     */
    @Override
    public List<String> getXmListToAccessByTimeInterval(String startDate, String endDate, String type, String xmly) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("kssj", startDate);
        paramMap.put("jssj", endDate);
        paramMap.put("type", type);
        if (StringUtils.isNotBlank(xmly)) {
            paramMap.put("xmly", xmly);
        }
        if (accessFilterHistoryDataFlag) {
            paramMap.put("xmlyFlag", "true");
        }
        List<AccessLogDTO> maps = bdcXmMapper.listAccessLogByPageOrderForBatchAccess(paramMap);
        if (CollectionUtils.isNotEmpty(maps)) {
            ArrayList<String> xmidList = maps.stream().collect(ArrayList::new, (list, item) -> {
                list.add(item.getXmid());
            }, ArrayList::addAll);
            return xmidList;
        }
        return null;
    }

    /**
     * @param xmidList
     * @return void
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据时间区间上报
     */
    @Override
    public void autoAccessByTimeInterval(List<String> xmidList) {
        LOGGER.info("开始批量补录上报");
        if (CollectionUtils.isNotEmpty(xmidList)) {
            LOGGER.info("待处理数量:" + xmidList.size());
            if (xmidList.size() > thresholdValue) {
                LOGGER.info("多线程处理开始");
                List<AutoAccessHandlerThread> listThread = new ArrayList<>();
                //多线程处理操作
                List<List<String>> list = groupListByQuantity(xmidList, stepValue);
                for (List<String> tempXmid : list) {
                    AutoAccessHandlerThread accessHandlerThread = new AutoAccessHandlerThread(accessModelServiceThread, tempXmid);
                    listThread.add(accessHandlerThread);
                }
                threadEngine.excuteThread(listThread, true);
            } else {
                LOGGER.info("单线程处理开始");
                autoAccessByXmidList(xmidList);
            }
        } else {
            LOGGER.info("待处理的项目idList为空");
        }
    }

    /**
     * @param bdcXmDO
     * @param sfgzyz  是否需要验证
     * @param sfjlrz  是否记录日志
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断是否上报，特殊逻辑判断
     * @date : 2022/6/23 15:49
     */
    @Override
    public boolean sfsb(BdcXmDO bdcXmDO, String sfgzyz, Boolean sfjlrz) {
        //抵押业务判断-上手产权是不是6个月内通过转移获取的，是的话不上报
        if (CollectionUtils.isNotEmpty(dyaGzldyidArr)) {
            if (dyaGzldyidArr.contains(bdcXmDO.getGzldyid())) {
                //查找上一手产权信息,先查fdcq表qszt=1的，用xmid查项目，比较时间
                BdcFdcqDO fdcqDO = bdcXmMapper.quertXsFdcqByBdcdyh(bdcXmDO.getBdcdyh());
                if (null != fdcqDO) {
                    //判断是否大于6个月
                    BdcXmQO xmQO = new BdcXmQO();
                    xmQO.setXmid(fdcqDO.getXmid());
                    List<BdcXmDO> cqxmDOList = bdcXmMapper.listBdcXm(xmQO);
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
                            LocalDate endDate = LocalDate.parse(DateUtils.formateYmdhms(bdcXmDO.getDjsj()), fmt);
                            LocalDate startDate = LocalDate.parse(DateUtils.formateYmdhms(cqxmDO.getDjsj()), fmt);
                            Period period = Period.between(startDate, endDate);

                            if (period.getYears() == 0 && period.getMonths() <= dyaMonth) {
                                LOGGER.info("上一手产权  取得时间和当前抵押项目小于6个月，不上报！");
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
        if (accessFilterHistoryDataFlag && bdcXmDO.getXmly() != null && CommonConstantUtils.XMLY_CLGD_DM.equals(bdcXmDO.getXmly())) {
            if (sfjlrz) {
                asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "存量历史数据不上报", "", new Date(), "0");
                saveBsbJrrz(bdcXmDO, "配置了存量历史数据不上报");
            }
            return false;
        }

        //校验是否允许上报
        if (CollectionUtils.isNotEmpty(gzyzCodeList) && Objects.nonNull(bdcXmDO.getDjsj())) {
            //根据配置的规则code ，判断是否需要上报
            if (!StringUtils.equals(cn.gtmap.realestate.common.util.CommonConstantUtils.BOOL_FALSE, sfgzyz)) {
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

    /**
     * 根据已生成的messageModel进行接入数据保存
     *
     * @param bdcAccessLog
     * @param bs
     * @param messageModel
     * @param xyxx
     * @return
     * @Date 2022/6/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public void saveBdcAccessByMessageModel(BdcAccessLog bdcAccessLog, Integer bs, MessageModel messageModel, String xyxx) {
        String ywh = "";
        try {
            bdcAccessLog.setCgbs(bs);
            bdcAccessLog.setXyxx(JrRzCgbsEnum.getMsgByBs(bs) + ":" + cn.gtmap.realestate.common.util.StringToolUtils.left(xyxx, 3900));
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
            LOGGER.info("根据BS保存接入表失败！xmid:{},失败原因：{}", ywh, e.getMessage());
            throw new AppException("MessageModel根据BS保存接入表失败！");

        }
    }

    /**
     * @param bdcAccessLog
     * @param bs
     * @param bdcXmDO
     * @param xyxx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存接入表数据
     * @date : 2022/7/19 8:51
     */
    @Override
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
            LOGGER.info("根据BS保存接入表失败！xmid:{},失败原因：{}", bdcXmDO.getXmid(), e.getMessage());
            throw new AppException("根据BS保存接入表失败！");
        }
    }


    /**
     * 按数量分组list
     *
     * @param list
     * @param quantity
     * @return
     */
    public static List<List<String>> groupListByQuantity(List<String> list, int quantity) {
        if (list == null || list.size() == 0) {
            return null;
        }

        if (quantity <= 0) {
            new IllegalArgumentException("Wrong quantity.");
        }

        List<List<String>> wrapList = new ArrayList();
        int count = 0;
        while (count < list.size()) {
            wrapList.add(new ArrayList(list.subList(count, Math.min((count + quantity), list.size()))));
            count += quantity;
        }

        return wrapList;
    }

    public void autoAccessByXmid(String xmid) {
        // 根据 xmid 查找不动产项目
        BdcXmDO bdcXmDO = null;
        if (StringUtils.isNotBlank(xmid)) {
            bdcXmDO = bdcXmMapper.queryBdcXm(xmid);
        }
        if (!ObjectUtils.isEmpty(bdcXmDO)) {
            // 只单独上传当前项目
            access(bdcXmDO, null);
        } else {
            LOGGER.error("数据汇交 - xmid:{} - 未查找到项目", xmid);
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

    public void saveBsbJrrz(BdcXmDO bdcXmDO, String xyxx) {
        if (nationalSwtich) {
            saveBdcAccess(new NationalAccess(), JrRzCgbsEnum.NO_ACCESS.getBs(), bdcXmDO, xyxx);
        }
        if (provinceSwtich) {
            saveBdcAccess(new ProvinceAccess(), JrRzCgbsEnum.NO_ACCESS.getBs(), bdcXmDO, xyxx);
        }
    }

}
