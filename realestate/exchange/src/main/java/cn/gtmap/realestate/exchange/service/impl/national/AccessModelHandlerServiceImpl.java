package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.domain.exchange.*;
import cn.gtmap.realestate.common.core.domain.exchange.uniformity.*;
import cn.gtmap.realestate.common.core.dto.exchange.access.MsgNoticeDTO;
import cn.gtmap.realestate.common.core.dto.exchange.access.SbxzVO;
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
import cn.gtmap.realestate.exchange.core.domain.JgLjzlxtxdGjDO;
import cn.gtmap.realestate.exchange.core.domain.JgWxzbwxxDO;
import cn.gtmap.realestate.exchange.core.dto.AccessDyjeInfo;
import cn.gtmap.realestate.exchange.core.dto.AccessLogDTO;
import cn.gtmap.realestate.exchange.core.dto.common.BdcXmForZxAccessDTO;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.common.MessageModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlDyaqOldDO;
import cn.gtmap.realestate.exchange.core.dto.ywhjsb.BdcWlxmJrCzrzDTO;
import cn.gtmap.realestate.exchange.core.dto.ywhjsb.YwhjyzDTO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttZdjbxxMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.accessLog.AccessLogMapper;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.core.national.NationalAccess;
import cn.gtmap.realestate.exchange.core.national.ProvinceAccess;
import cn.gtmap.realestate.exchange.core.qo.BdcDbrzQO;
import cn.gtmap.realestate.exchange.core.qo.BdcJrrzQO;
import cn.gtmap.realestate.exchange.core.qo.JgWxzBwxxQO;
import cn.gtmap.realestate.exchange.core.thread.AccessSingleThread;
import cn.gtmap.realestate.exchange.core.thread.AutoAccessHandlerThread;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.gx.GxService;
import cn.gtmap.realestate.exchange.service.jrsb.BdcYwhjSbService;
import cn.gtmap.realestate.exchange.service.national.*;
import cn.gtmap.realestate.exchange.service.national.access.AccessDefaultValueService;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogTypeService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.DoubleUtils;
import cn.gtmap.realestate.exchange.util.*;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import cn.gtmap.realestate.exchange.util.enums.JrRzCgbsEnum;
import cn.gtmap.realestate.exchange.util.rsa.RsaUtil;
import cn.gtmap.realestate.exchange.web.rest.NationalAccessRestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.util.ArrayUtils;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
 * @version 1.0, 2018/12/13
 * @description 数据汇交 service 实现
 */
@Service
public class AccessModelHandlerServiceImpl implements AccesssModelHandlerService {

    public static final String UNKNOWN = "unknown";

    private static final String DBRZJK = "realestate-exchange/rest/v1.0/access/dbrz/nt?date=#{date}&qxdm=#{qxdm}";
    private volatile static Integer count = 0;
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessModelHandlerServiceImpl.class);
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
    KttZdjbxxMapper kttZdjbxxMapper;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Autowired
    BdcYwhjSbService bdcYwhjSbService;


    @Autowired
    RestRpcUtils restRpcUtils;

    @Autowired
    NationalAccessRestController nationalAccessRestController;

    @Autowired
    CityAccesssModelHandlerService cityAccesssModelHandlerService;

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


    @Value("${accessLog.gdyyip:}")
    private String accessLogGdyyip;

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

    /**
     * 市级上报
     */
    @Value("${cityAccess.switch:false}")
    public boolean citySwtich;

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
    //qxdm是否需要对照
    @Value("${qxdm.convert:false}")
    protected boolean qxdmConvert;

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
        if (StringUtil.isNotBlank(bdcdyh) && bdcdyh.length() == 28) {
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
            //升级上报,新的后缀标识判断逻辑
            if (newAccessBizBs) {
                JgWxzBwxxQO jgWxzBwxxQO = new JgWxzBwxxQO();
                jgWxzBwxxQO.setYwh(bdcXmDO.getXmid());
                //todo: 查询结果数据量为1条直接根据状态判断，为多条时，取状态不等于3 的数据
                List<JgWxzbwxxDO> jgWxzbwxxDOList = accessLogMapper.queryWxzBwxx(jgWxzBwxxQO);
                //用xmid 查不到的可能是ywh存的是zsid，用当前xmid找到证书id
                if (CollectionUtils.isEmpty(jgWxzbwxxDOList)) {
                    LOGGER.warn("当前接入数据用xmid={}查询未销账报文信息表未找到数据，改为用证书zsid作为业务号查询是否是电子证照比对数据", bdcXmDO.getXmid());
                    //查询证书
                    List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(bdcXmDO.getXmid());
                    if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                        List<String> zsidList = bdcZsDOList.stream().map(BdcZsDO::getZsid).collect(Collectors.toList());
                        jgWxzBwxxQO = new JgWxzBwxxQO();
                        jgWxzBwxxQO.setZsidList(zsidList);
                        jgWxzbwxxDOList = accessLogMapper.queryWxzBwxx(jgWxzBwxxQO);
                        LOGGER.warn("用证书id={}查询到未销账报文信息表数据{}条", JSON.toJSONString(zsidList), CollectionUtils.size(jgWxzbwxxDOList));
                    }
                }
                //还是空的有可能JgWxzbwxxDO.ywh 是受理编号
                if (CollectionUtils.isEmpty(jgWxzbwxxDOList)) {
                    jgWxzBwxxQO = new JgWxzBwxxQO();
                    jgWxzBwxxQO.setYwh(bdcXmDO.getSlbh());
                    jgWxzbwxxDOList = accessLogMapper.queryWxzBwxx(jgWxzBwxxQO);
                }
                if (CollectionUtils.isNotEmpty(jgWxzbwxxDOList)) {
                    //查询结果数据量为1条直接根据状态判断，为多条时，取状态不等于3 的数据
                    JgWxzbwxxDO jgWxzbwxxDO;
                    if (CollectionUtils.size(jgWxzbwxxDOList) == 1) {
                        jgWxzbwxxDO = jgWxzbwxxDOList.get(0);
                    } else {
                        List<JgWxzbwxxDO> sbXzbwxxList = jgWxzbwxxDOList.stream().filter(jgWxzbwxx -> !StringUtils.equals(CommonConstantUtils.XZZT_XZCG, jgWxzbwxx.getXzzt())).collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(sbXzbwxxList)) {
                            jgWxzbwxxDO = sbXzbwxxList.get(0);
                        } else {
                            jgWxzbwxxDO = jgWxzbwxxDOList.get(0);
                        }
                    }

                    //判断改xmid是否已经上报
                    //存在电子证照比对的情况，是没有登簿日志上报记录的，所以去除以下判断逻辑
//                    List<BdcJrDbrzjlDO> dbrzjlDOS = accessLogMapper.listBdcJrDbrzjlList(qxdm, bdcXmDO.getDjsj());
//                    if (CollectionUtils.isNotEmpty(dbrzjlDOS)) {
                    BdcAccessLog jrbwDO = accessLogMapper.getAccessLogNotXmid(bdcXmDO.getXmid());
                    if (null != jrbwDO) {
                        //如果是有销账记录的，报文取值未销账报文信息表的数据
                        if (StringUtils.isNotBlank(jgWxzbwxxDO.getBwid())) {
                            if (StringUtils.length(jgWxzbwxxDO.getBwid()) > 17) {
                                bizMsgId = jgWxzbwxxDO.getBwid().substring(0, 18);
                            } else {
                                bizMsgId = jgWxzbwxxDO.getBwid();
                            }

                        }
                        switch (jgWxzbwxxDO.getXzzt()) {
                            case "1":
                            case "4":
                                if (StringUtils.equals(CommonConstantUtils.XZBWLX_JRSB, jgWxzbwxxDO.getXzbwlx()) || StringUtils.equals(CommonConstantUtils.XZBWLX_RKSB, jgWxzbwxxDO.getXzbwlx())
                                        || StringUtils.equals(CommonConstantUtils.XZBWLX_DBBYZ, jgWxzbwxxDO.getXzbwlx())) {
                                    //增加对比不一致的数据后缀
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
        if (qxdmConvert) {
            qxdm = commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", qxdm);
        }
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
     * @param ywdm
     * @return cn.gtmap.realestate.exchange.service.national.NationalAccessXmlService
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据YWDM 获取相应的策略服务
     */
    @Override
    public NationalAccessXmlService getAccessXmlServiceByYwdm(String ywdm) {
        NationalAccessXmlService nationalAccessXmlService = null;
        Map<String, String> param = Maps.newHashMap();
        param.put("ywdm", ywdm);
        List<Map<String, Object>> zdList = bdcXmMapper.getExchangeZd(param);
        if (CollectionUtils.isNotEmpty(zdList)) {
            Map<String, Object> bdcexchangeZdSqlxMap = zdList.get(0);
            if (MapUtils.isNotEmpty(bdcexchangeZdSqlxMap)) {
                nationalAccessXmlService = nationalAccessXmlContext.getNationNalServiceInstance(CommonUtil.formatEmptyValue(bdcexchangeZdSqlxMap.get("YWFWDM")));
            }
        } else {
            LOGGER.error("未找到对应的服务");
            throw new AppException("未找到对应的服务");
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

    /**
     * @param messageModel
     * @return
     * @author <a href="mailto:xutao@gtmap.cn">zdd</a>
     * @description 根据上报数据对象模型生成 xml 字符串
     */
    @Override
    public String getAccessXMLFroYzx(MessageModelBdc messageModel) {
        if (messageModel != null) {
            LOGGER.info("组织一致性xml的实体参数为：{}", messageModel.toString());
            String xml = xmlEntityConvertUtil.entityToXml(messageModel);
            xml = RsaUtil.setRsaSign(xml);
            return xml;
        }
        return null;
    }

    @Override
    public String getAccessXMLOld(MessageModelOld messageModel) {
        if (messageModel != null) {
            LOGGER.info("old组织xml的实体参数为：{}", messageModel.toString());
            String xml = xmlEntityConvertUtil.entityToXml(messageModel);
            xml = RsaUtil.setRsaSign(xml);
            return xml;
        }
        return null;
    }

    /**
     * @param pId
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据工作流实例ID 查询实例下所有项目回报
     */
    @Override
    public void autoAccessByProcessInsId(String pId) {
        LOGGER.info("开始上报汇交的gzlslid，{}", pId);
        if (StringUtils.isBlank(pId)) {
            return;
        }
        List<BdcXmDO> bdcXmList = bdcXmMapper.queryBdcXmList(pId);
        if (CollectionUtils.isEmpty(bdcXmList)) {
            LOGGER.error("数据汇交 - gzlslid:{}- 未查找到工作流实例相关项目", pId);
            return;
        }
        // 业务
        Integer lclx = bdcXmFeignService.makeSureBdcXmLx(bdcXmList);
        // 如果是批量抵押业务 只上报一条处理逻辑
        if (CommonConstantUtils.LCLX_PL.equals(lclx) && CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmList.get(0).getQllx()) && accessPldy) {
            BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(bdcXmList.get(0).getXmid());
            if (bdcCshFwkgSlDO != null && CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfscql())) {
                LOGGER.info("开始上报批量抵押汇交的gzlslid，{}", pId);
                access(bdcXmList.get(0), bdcXmList);
                return;
            }
        }
        // 批量组合的项目+抵押上报一条开+生成权利 处理
        bdcXmList = accessIfLclxPlzhNeeded(bdcXmList, pId, lclx);
        if (accessEnableThread && bdcXmList.size() > 1) {
            List<AccessSingleThread> listThread = new ArrayList<>();
            for (BdcXmDO bdcXmDO : bdcXmList) {
                listThread.add(new AccessSingleThread(this, bdcXmDO, bdcXmList));
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
        LOGGER.warn("当前流程实例id{}查询到需要注销的外联项目信息{}", pId, JSON.toJSONString(bdcXmForZxAccessDTOSet));
        if (CollectionUtils.isNotEmpty(bdcXmForZxAccessDTOSet)) {
            accessWlxm(bdcXmForZxAccessDTOSet);
        }
    }

    /**
     * @param gzlslid
     */
    @Override
    public void autoAccessWlzxXm(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcXmDO> bdcXmList = bdcXmMapper.queryBdcXmList(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmList)) {
                // 取不为空的不动产项目的ids
                List<String> xmids = bdcXmList.stream().map(BdcXmDO::getXmid).filter(StringUtils::isNotBlank).collect(Collectors.toList());
                //处理注销原权利的外联项目
                Set<BdcXmForZxAccessDTO> bdcXmForZxAccessDTOSet = commonService.queryBdcXmForZxAccessListByXmidsAndWlxmAndZxyql(xmids);
                LOGGER.warn("当前流程实例id{}查询到需要注销的外联项目信息{}", gzlslid, JSON.toJSONString(bdcXmForZxAccessDTOSet));
                if (CollectionUtils.isNotEmpty(bdcXmForZxAccessDTOSet)) {
                    accessWlxm(bdcXmForZxAccessDTOSet);
                }
            }
        }
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

    private String getZsxhOrDjxlByBdcxm(BdcXmDO bdcXmDO) {
        BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(bdcXmDO.getXmid());
        String key = UNKNOWN;
        if (bdcCshFwkgSlDO != null && bdcCshFwkgSlDO.getZsxh() != null) {
            key = bdcCshFwkgSlDO.getZsxh().toString();
        } else if (bdcCshFwkgSlDO != null && StringUtils.isNotBlank(bdcCshFwkgSlDO.getDjxl())) {
            key = bdcCshFwkgSlDO.getDjxl();
        }
        return key;
    }

    private static final String ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_REDIS_KEY = "ACCESS_BATCH_DYA_AMOUNT_OF_MONEY_INFO_";

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

    /**
     * @param xmid
     * @param withAllXm
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据项目主键 回报单个项目
     */
    @Override
    public void autoAccessByXmid(String xmid, Boolean withAllXm) {
        // 根据 xmid 查找不动产项目
        BdcXmDO bdcXmDO = null;
        if (org.apache.commons.lang.StringUtils.isNotBlank(xmid)) {
            bdcXmDO = bdcXmMapper.queryBdcXm(xmid);
        }
        if (!ObjectUtils.isEmpty(bdcXmDO)) {
            //  重新上报同一个工作流下的所有项目
            if (withAllXm && org.apache.commons.lang.StringUtils.isNotBlank(bdcXmDO.getGzlslid())) {
                LOGGER.info("重新上报同一个工作流下的所有项目,xmid:{},glzlslid:{}", xmid, bdcXmDO.getGzlslid());
                autoAccessByProcessInsId(bdcXmDO.getGzlslid());
            } else {
                // 只单独上传当前项目
                access(bdcXmDO, null);
            }
        } else {
            LOGGER.error("数据汇交 - xmid:{} - 未查找到项目", xmid);
        }
    }

    /**
     * @param xmidList
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目主键List 回报单个项目
     */
    @Override
    public void autoAccessByXmidList(List<String> xmidList) {
        if (CollectionUtils.isNotEmpty(xmidList)) {
            for (String xmid : xmidList) {
                autoAccessByXmid(xmid, false);
            }
        }
    }

    /**
     * @param xmidList@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 市级业务数据根据xmid 接入
     * @date : 2023/1/6 11:41
     */
    @Override
    public void autoAccessCityByXmidList(List<String> xmidList) {
        for (String xmid : xmidList) {
            BdcXmDO bdcXmDO = bdcXmMapper.queryBdcXm(xmid);
            if (Objects.nonNull(bdcXmDO)) {
                cityAccesssModelHandlerService.singleAccess(bdcXmDO, null);
            }
        }

    }

    /**
     * @param sbxzVOList@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 上报销账台账上报并更新销账状态
     * @date : 2022/12/13 16:50
     */
    @Override
    public void actoAccessBySbxz(List<SbxzVO> sbxzVOList) {
        if (CollectionUtils.isNotEmpty(sbxzVOList)) {
            List<String> sbxzIdList = new ArrayList<>(sbxzVOList.size());
            for (SbxzVO sbxzVO : sbxzVOList) {
                if (StringUtils.isNotBlank(sbxzVO.getXmid())) {
                    autoAccessByXmid(sbxzVO.getXmid(), false);
                    if (StringUtils.isNotBlank(sbxzVO.getSbxzid())) {
                        sbxzIdList.add(sbxzVO.getSbxzid());
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(sbxzIdList)) {
                updateXzztByid(sbxzIdList, "2");
            }
        }
    }

    /**
     * @param xmidList@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 外联项目上报
     * @date : 2022/12/6 13:56
     */
    @Override
    public void autoAccessWlxmByXmidList(List<String> xmidList) {
        if (CollectionUtils.isNotEmpty(xmidList)) {
            Set<BdcXmForZxAccessDTO> allXmSet = new HashSet<>(xmidList.size());
            if (CollectionUtils.size(xmidList) > 500) {
                List<List> xmidPartList = ListUtils.subList(xmidList, 500);
                for (List list : xmidPartList) {
                    Set<BdcXmForZxAccessDTO> bdcXmForZxAccessDTOSet = bdcXmMapper.listZxWlxmNotAccess(list);
                    if (CollectionUtils.isNotEmpty(bdcXmForZxAccessDTOSet)) {
                        allXmSet.addAll(bdcXmForZxAccessDTOSet);
                    }
                }
            } else {
                Set<BdcXmForZxAccessDTO> bdcXmForZxAccessDTOSet = bdcXmMapper.listZxWlxmNotAccess(xmidList);
                if (CollectionUtils.isNotEmpty(bdcXmForZxAccessDTOSet)) {
                    allXmSet.addAll(bdcXmForZxAccessDTOSet);
                }
            }

            LOGGER.warn("项目id数据{}查询到需要注销的外联项目信息{}", xmidList, JSON.toJSONString(allXmSet));
            if (CollectionUtils.isNotEmpty(allXmSet)) {
                accessWlxm(allXmSet);
            }
        }
    }

    /**
     * @param startDate
     * @param endDate
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 根据时间区间获取要上报的xmidList
     */
    @Override
    public List<String> getXmListToAccessByTimeInterval(String startDate, String endDate, String type, String xmly) {
        Map<String, Object> paramMap = new HashMap<>(12);
        paramMap.put("kssj", startDate);
        paramMap.put("jssj", endDate);
        paramMap.put("type", type);
        if (StringUtils.isNotBlank(xmly)) {
            paramMap.put("xmly", xmly);
        }
        if (accessFilterHistoryDataFlag) {
            paramMap.put("xmlyFlag", "true");
        }
        List<AccessLogDTO> maps = accessLogMapper.listAccessLogByPageOrderForBatchAccess(paramMap);
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
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
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
            if (nationalSwtich) {
                saveBdcAccessByMessageModel(new NationalAccess(), JrRzCgbsEnum.ACCESS_FAIL.getBs(), messageModel, StringUtils.left(ExceptionUtils.getFeignErrorMsg(e), 1000));
            }
            if (provinceSwtich) {
                saveBdcAccessByMessageModel(new ProvinceAccess(), JrRzCgbsEnum.ACCESS_FAIL.getBs(), messageModel, StringUtils.left(ExceptionUtils.getFeignErrorMsg(e), 1000));
            }
        }
        return result;
    }

    /**
     * @param messageModel
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 复制dataModel
     */
    @Override
    public void filterDataModel(MessageModel messageModel) {
        HeadModel headModel = messageModel.getHeadModel();
        DataModel dataModel = messageModel.getDataModel();
        DataModel targetDataModel = new DataModel();
        String ywdm = headModel.getRecType();
        // 获取不同类型的策略 service
        NationalAccessXmlService nationalAccessXmlService = getAccessXmlServiceByYwdm(ywdm);
        //异步保存结果日志
        asyncDealUtils.saveJrCzrz(headModel.getRecFlowID(), 2, "根据登记类型" + headModel.getRegType() + "成功找到对应的上报策略服务，下一步组织报文", "", new Date(), "1");
        Set<NationalAccessDataService> nationalAccessDataServiceSet = nationalAccessXmlService.getNationalAccessDataServiceSet();
        if (CollectionUtils.isNotEmpty(nationalAccessDataServiceSet)) {
            for (NationalAccessDataService service : nationalAccessDataServiceSet) {
                List dataList = service.getData(dataModel);
                if (CollectionUtils.isNotEmpty(dataList)) {
                    // 必填项默认赋值
                    accessDefaultValueService.setDefaultValue(dataList);
                    service.setData(targetDataModel, dataList);
                }
            }

            // 读取默认值配置表  赋默认值
            accessDefaultValueService.setDefaultValueWithDefaultTable(nationalAccessXmlService.getNationalAccessDataServiceSet(),
                    messageModel, false);
        }
        messageModel.setDataModel(targetDataModel);
    }

    /**
     * @param bdcXmDO
     * @return cn.gtmap.realestate.common.core.domain.exchange.MessageModel
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据项目获取上报的数据
     */
    private MessageModel getAccessData(BdcXmDO bdcXmDO) {
        // 判断是否是需要过滤的BDCDYH（配置的过滤xzqdm 和 是否是虚拟的BDCDYH）
        if (CommonUtil.checkFilterBdcdyh(bdcXmDO.getBdcdyh())) {
            return null;
        }
        MessageModel messageModel = null;
        try {
            // 获取不同类型的策略 service
            NationalAccessXmlService nationalAccessXmlService = getAccessXmlService(bdcXmDO);
            // 利用对应策略的 service 生成 message
            if (!ObjectUtils.isEmpty(nationalAccessXmlService)) {
                // 获取 message
                messageModel = getMessageModel(nationalAccessXmlService, bdcXmDO.getXmid());
            } else {
                LOGGER.error("数据汇交 - 未找到对应的策略服务 xmid：{}", bdcXmDO.getXmid());
            }
        } catch (Exception e) {
            LOGGER.error("数据汇交失败：bdcXmDO.getXmid：{}", bdcXmDO.getXmid(), e);
        }

        return messageModel;
    }

    /**
     * @return
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 根据项目id单独上报
     * @Date 2022/5/5
     * @Param
     **/
    @Override
    public void singleAccess(BdcXmDO bdcXmDO, List<BdcXmDO> bdcXmDOList) {
        access(bdcXmDO, bdcXmDOList);
    }

    /**
     * @param xmidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据xmid 更新wxzbwxx 表的xzzt
     * @date : 2022/6/22 10:37
     */
    @Override
    public void updateWxzBwxxXzzt(List<String> xmidList, String xzzt) {
        if (CollectionUtils.isNotEmpty(xmidList)) {
            JgWxzBwxxQO jgWxzBwxxQO = new JgWxzBwxxQO();
            jgWxzBwxxQO.setXmidList(xmidList);
            List<JgWxzbwxxDO> jgWxzbwxxDOList = accessLogMapper.queryWxzBwxx(jgWxzBwxxQO);
            if (CollectionUtils.isNotEmpty(jgWxzbwxxDOList)) {
                for (JgWxzbwxxDO jgWxzbwxxDO : jgWxzbwxxDOList) {
                    jgWxzbwxxDO.setXzzt(xzzt);
                    entityMapper.updateByPrimaryKey(jgWxzbwxxDO);
                }
            }
        }

    }

    /**
     * @param idList
     * @param xzzt
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据主键id更新销账状态
     * @date : 2022/9/29 9:06
     */
    @Override
    public void updateXzztByid(List<String> idList, String xzzt) {
        if (CollectionUtils.isNotEmpty(idList)) {
            for (String id : idList) {
                JgWxzbwxxDO jgWxzbwxxDO = new JgWxzbwxxDO();
                jgWxzbwxxDO.setXzzt(xzzt);
                jgWxzbwxxDO.setId(id);
                entityMapper.updateByPrimaryKeySelective(jgWxzbwxxDO);
            }
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
        /*
         * 查询流程配置是否上报
         * */
        try {
            if (StringUtils.isNotBlank(bdcXmDO.getGzldyid()) && StringUtils.isNotBlank(bdcXmDO.getDjxl())) {
                BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyidAndDjxl(bdcXmDO.getGzldyid(), bdcXmDO.getDjxl());
                if (Objects.isNull(bdcDjxlPzDO)) {
                    //如果为空单独根据流程定义id查询一下
                    List<BdcDjxlPzDO> bdcDjxlPzDOList = bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyid(bdcXmDO.getGzldyid());
                    if (CollectionUtils.isNotEmpty(bdcDjxlPzDOList)) {
                        bdcDjxlPzDO = bdcDjxlPzDOList.get(0);
                    }
                }
                if (Objects.nonNull(bdcDjxlPzDO)) {
                    if (Objects.equals(CommonConstantUtils.SF_F_DM, bdcDjxlPzDO.getSfsb()) || Objects.isNull(bdcDjxlPzDO.getSfsb())) {
                        //配置了不上报，返回false
                        if (sfjlrz) {
                            asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "流程配置中设置了不上报", "", new Date(), "0");
                            saveBsbJrrz(bdcXmDO, "流程配置中设置了不上报");
                        }
                        return false;
                    }
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

            //判断bdc_bdcdjb_zdjbxx 表是否存在数据，不存在当前数据也不允许接入不允许上报
            //根据不动产类型判断是查宗地还是宗海
            if (ArrayUtils.contains(CommonConstantUtils.BDCLX_HYSYQ, bdcXmDO.getBdclx())) {
                LOGGER.info("当前xmid{}不动产类型{}属于海域", bdcXmDO.getXmid(), bdcXmDO.getBdclx());
                Map<String, Object> param = new HashMap();
                param.put("ywh", bdcXmDO.getXmid());
                List<KttZhjbxxDO> kttZhjbxxDOList = bdcXmMapper.listZhjbxx(bdcXmDO.getXmid());
                if (CollectionUtils.isEmpty(kttZhjbxxDOList)) {
                    if (sfjlrz) {
                        asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "bdc_bdcdjb_zhjbxx表未查询到相关数据，上报终止", "", new Date(), "0");
                        saveBsbJrrz(bdcXmDO, "bdc_bdcdjb_zhjbxx表未查询到相关数据，上报终止");
                    }
                    return false;
                }
            } else {
                Map<String, Object> param = new HashMap();
                param.put("ywh", bdcXmDO.getXmid());
                List<KttZdjbxxDO> kttZdjbxxList = kttZdjbxxMapper.queryKttZdjbxxList(param);
                if (CollectionUtils.isEmpty(kttZdjbxxList)) {
                    if (sfjlrz) {
                        asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "bdc_bdcdjb_zdjbxx表未查询到相关数据，上报终止", "", new Date(), "0");
                        saveBsbJrrz(bdcXmDO, "bdc_bdcdjb_zdjbxx表未查询到相关数据，上报终止");
                    }
                    return false;
                }
            }

            //校验是否允许上报
            if (CollectionUtils.isNotEmpty(gzyzCodeList) && Objects.nonNull(bdcXmDO.getDjsj())) {
                //根据配置的规则code ，判断是否需要上报
                if (!StringUtil.equals(CommonConstantUtils.BOOL_FALSE, sfgzyz)) {
                    YwhjyzDTO ywhjyzDTO = new YwhjyzDTO();
                    ywhjyzDTO.setBdcXmDO(bdcXmDO);
                    ywhjyzDTO.setGxid("");
                    ywhjyzDTO.setSfjlrz(sfjlrz);
                    ywhjyzDTO.setSfwlxm(false);
                    for (String code : gzyzCodeList) {
                        ywhjyzDTO.setCode(code);
                        boolean result = ywhjYz(ywhjyzDTO);
                        //配置了的规则只要验证不通过就是直接结束循环
                        if (!result) {
                            return false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "业务汇交验证是否上报异常" + e.getMessage(), "", new Date(), "0");
            LOGGER.error("项目id{}业务汇交验证异常", bdcXmDO.getXmid(), e);
        }
        return true;
    }

    /*
     * 业务汇交根据配置的code实现不同的逻辑验证
     * day 参数为时间，支持传入非当天的时间进行验证，例如 定时任务检查的是前一天的登簿数据，此时传入day参数为昨天的时间
     * */
    public boolean ywhjYz(YwhjyzDTO ywhjyzDTO) {
        boolean result = true;
        BdcXmDO bdcXmDO = ywhjyzDTO.getBdcXmDO();
        String today = DateUtils.formateTime(new Date(System.currentTimeMillis()), DateUtils.DATE_FORMAT_2);
        String xmdjsj = DateUtils.formateTime(ywhjyzDTO.getBdcXmDO().getDjsj(), DateUtils.DATE_FORMAT_2);
        switch (ywhjyzDTO.getCode()) {
            case "1":
                //非当天登簿时间数据补报，如果登簿日志详情表不存在数据，不允许上报
                if (!StringUtils.equals(today, xmdjsj)) {
                    BdcDbrzQO bdcDbrzQO = new BdcDbrzQO();
                    bdcDbrzQO.setXmid(bdcXmDO.getXmid());
                    if (ywhjyzDTO.getSfwlxm()) {
                        //外联项目验证用关系id 作为ywlsh查询
                        bdcDbrzQO.setXmid(ywhjyzDTO.getGxid());
                    }
                    List<BdcDbrzjlXqDO> bdcDbrzjlXqDOList = accessLogMapper.listBdcDbrzjlXq(bdcDbrzQO);
                    LOGGER.warn("当前项目xmid{}非当天登簿，查询登簿日志详情记录入参{}，数据量{}", bdcXmDO.getXmid(), JSON.toJSONString(bdcDbrzQO), CollectionUtils.size(bdcDbrzjlXqDOList));
                    if (CollectionUtils.isEmpty(bdcDbrzjlXqDOList)) {
                        if (ywhjyzDTO.getSfjlrz()) {
                            if (ywhjyzDTO.getSfwlxm()) {
                                ywhjyzDTO.getBdcWlxmJrCzrzDTO().setCznr("当前项目非当天登簿，且在登簿日志详情表不存在数据不允许上报");
                                asyncDealUtils.saveWlxmJrCzrz(ywhjyzDTO.getBdcWlxmJrCzrzDTO());
                                saveBsbWlxmJrrz(bdcXmDO, "当前项目非当天登簿，且在登簿日志详情表不存在数据不允许上报", ywhjyzDTO.getGxid());
                            } else {
                                asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "当前项目非当天登簿，且在登簿日志详情表不存在数据不允许上报", "", new Date(), "0");
                                saveBsbJrrz(bdcXmDO, "当前项目非当天登簿，且在登簿日志详情表不存在数据不允许上报");
                            }
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
                if (ywhjyzDTO.getSfwlxm()) {
                    //外联项目验证用关系id 作为ywlsh查询
                    bdcJrrzQO.setXmid(ywhjyzDTO.getGxid());
                }
                bdcJrrzQO.setCgbsList(Arrays.asList(0, 1));
                //实际接入时间是当天的不允许补报
                bdcJrrzQO.setSjjrrq(new Date());
                List<BdcAccessLog> bdcAccessLogList = accessLogMapper.listBdcJrrz(bdcJrrzQO);
                LOGGER.warn("当前项目xmid{}接入表查询cgbs=0或者1的数据,不允许再次上报，查询入参{}，数据量{}", bdcXmDO.getXmid(), JSON.toJSONString(bdcJrrzQO), CollectionUtils.size(bdcAccessLogList));
                if (CollectionUtils.isNotEmpty(bdcAccessLogList)) {
                    if (ywhjyzDTO.getSfjlrz()) {
                        if (ywhjyzDTO.getSfwlxm()) {
                            ywhjyzDTO.getBdcWlxmJrCzrzDTO().setCznr("当前项目接入表存在已上报cgbs=0或者1的数据,不允许再次上报");
                            asyncDealUtils.saveWlxmJrCzrz(ywhjyzDTO.getBdcWlxmJrCzrzDTO());
                            saveBsbWlxmJrrz(bdcXmDO, "当前项目接入表存在已上报cgbs=0或者1的数据,不允许再次上报", ywhjyzDTO.getGxid());
                        } else {
                            asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "当前项目接入表存在已上报cgbs=0或者1的数据，不允许再次上报", "", new Date(), "0");
                            saveBsbJrrz(bdcXmDO, "当前项目接入表存在已上报cgbs=0或者1的数据,不允许再次上报");
                        }
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
                        if (ywhjyzDTO.getSfjlrz()) {
                            if (ywhjyzDTO.getSfwlxm()) {
                                ywhjyzDTO.getBdcWlxmJrCzrzDTO().setCznr("当天登簿日志已经上报了，不允许再次汇交上报");
                                asyncDealUtils.saveWlxmJrCzrz(ywhjyzDTO.getBdcWlxmJrCzrzDTO());
                                saveBsbWlxmJrrz(bdcXmDO, "当天登簿日志已经上报了，不允许再次汇交上报", ywhjyzDTO.getGxid());
                            } else {
                                asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "当天登簿日志已经上报了，不允许再次汇交上报", "", new Date(), "0");
                                saveBsbJrrz(bdcXmDO, "当天登簿日志已经上报了，不允许再次汇交上报");
                            }
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
     * 根据bs标识保存数据
     *
     * @param
     * @return
     * @Date 2022/6/23
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
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
     * @param bdcAccessLog
     * @param bs
     * @param bdcXmDO
     * @param xyxx
     * @param gxid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 外联数据保存接入表
     * @date : 2022/12/2 15:12
     */
    @Override
    public void saveWlxmAccess(BdcAccessLog bdcAccessLog, Integer bs, BdcXmDO bdcXmDO, String xyxx, String gxid) {
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
     * 保存组织报文失败的数据
     * 先删除原先bwid=xmid 的数据，重新生成新的bwid作为主键
     */
    @Transactional(transactionManager = "serverTransactionManager", rollbackFor = Exception.class)
    public void saveAccessFailData(BdcAccessLog bdcAccessLog, Integer bs, BdcXmDO bdcXmDO, String xyxx) {
        String bwid = "";
        try {
            if (Objects.nonNull(bdcXmDO) && StringUtils.isNotBlank(bdcXmDO.getXmid())) {
                bwid = getBizMsgId(bdcXmDO);
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
                    batchUpdateAccessLogMap.put("fjxx", DateUtils.getCurrentTimeStr() + "由于报文组织失败或者未找到对应的上报服务策略，重新生成一条新的准确的bwid数据，需要将当前数据更新为历史状态，并插入新数据bwid=" + bwid);
                    if (bdcAccessLog instanceof ProvinceAccess) {
                        bdcdjMapper.batchUpdateAccessLogCgbs(batchUpdateAccessLogMap);
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

    /**
     * 根据bs标识保存数据
     *
     * @param
     * @return
     * @Date 2022/6/23
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
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

    /**
     * @param xmidList
     * @param clzt
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新未接入表数据处理状态为1
     * @date : 2022/6/30 13:55
     */
    @Override
    public void updateWjrClzt(List<String> xmidList, Integer clzt) {
        if (CollectionUtils.isNotEmpty(xmidList)) {
            Map paramMap = new HashMap(3);
            paramMap.put("xmidList", xmidList);
            paramMap.put("clzt", clzt);
            paramMap.put("gxsj", new Date(System.currentTimeMillis()));
            accessLogMapper.updateWjrClzt(paramMap);
        }
    }

    /**
     *
     */
    @Override
    public void startAccess(BdcXmDO bdcXmDO, List<BdcXmDO> bdcXmDOList) {

        MessageModel messageModel = new MessageModel();
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
                        LOGGER.info("组织上报数据报错！xmid:{},错误原因：{}", bdcXmDO.getXmid(), ex);
                        /*
                         * 逻辑调整，在组织headModel时出现异常，此时bwid不会成功生成，把之前的bwid=xmid 的数据删了重新生成新的接入日志数据
                         * */
                        if (nationalSwtich) {
                            saveAccessFailData(new NationalAccess(), JrRzCgbsEnum.BWZZSB_WSB.getBs(), bdcXmDO, StringUtils.left(ExceptionUtils.getFeignErrorMsg(ex), 1000));
                        }
                        if (provinceSwtich) {
                            saveAccessFailData(new ProvinceAccess(), JrRzCgbsEnum.BWZZSB_WSB.getBs(), bdcXmDO, StringUtils.left(ExceptionUtils.getFeignErrorMsg(ex), 1000));
                        }
                        //异步保存接入操作日志
                        asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 3, "上报数据组织报文失败异常" + StringUtils.left(ExceptionUtils.getFeignErrorMsg(ex), 1000), "", new Date(), "0");
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
                if (nationalSwtich) {
                    saveAccessFailData(new NationalAccess(), JrRzCgbsEnum.BEGIN_WSB.getBs(), bdcXmDO, "未找到对应的上报服务策略");
                }
                if (provinceSwtich) {
                    saveAccessFailData(new ProvinceAccess(), JrRzCgbsEnum.BEGIN_WSB.getBs(), bdcXmDO, "未找到对应的上报服务策略");
                }
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
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新销账状态
     * @date : 2022/11/25 9:06
     */
    @Override
    public void updateXzzt() {
        //查询xzzt 为 1 和2 的数据
        JgWxzBwxxQO jgWxzBwxxQO = new JgWxzBwxxQO();
        List<String> xzztList = new ArrayList<>(2);
        xzztList.add("1");
        xzztList.add("2");
        xzztList.add("4");
        jgWxzBwxxQO.setXzztList(xzztList);
        List<JgWxzbwxxDO> jgWxzbwxxDOList = accessLogMapper.queryWxzBwxx(jgWxzBwxxQO);
        LOGGER.warn("查询到未上报销账信息表销账类型为1，2，4 的数据共{}条", CollectionUtils.size(jgWxzbwxxDOList));
        if (CollectionUtils.isNotEmpty(jgWxzbwxxDOList)) {
            for (JgWxzbwxxDO jgWxzbwxxDO : jgWxzbwxxDOList) {
                //根据ywh在JG_LJZLCYXD_GJ中没有数据时，将xzzt由2更新成3，若ywh在JG_LJZLCYXD_GJ中仍存在数据时，则将xzzt由1更新成4
                Example example = new Example(JgLjzlxtxdGjDO.class);
                example.createCriteria().andEqualTo("ywh", jgWxzbwxxDO.getYwh());
                List<JgLjzlxtxdGjDO> jgLjzlxtxdGjDOList = entityMapper.selectByExampleNotNull(example);
                //存在数据且xzzt !=1 ,更新成4，否则xzzt为1 的且有数据的不变
                if (CollectionUtils.isNotEmpty(jgLjzlxtxdGjDOList)) {
                    if (!StringUtils.equals("1", jgWxzbwxxDO.getXzzt())) {
                        LOGGER.warn("业务号{}在增量详单存在数据，且状态不是未销账，更新销账状态- 失败", jgWxzbwxxDO.getYwh());
                        jgWxzbwxxDO.setXzzt(CommonConstantUtils.XZZT_XZSB);
                        jgWxzbwxxDO.setSjgxsj(new Date());
                    }
                } else {
                    LOGGER.warn("业务号{}增量详单不存在数据，销账状态{}", jgWxzbwxxDO.getYwh(), jgWxzbwxxDO.getXzzt());
                    //如果不存在，124 的数据都更新成3
                    if (StringUtils.equals(CommonConstantUtils.XZZT_ZZXZ, jgWxzbwxxDO.getXzzt())
                            || StringUtils.equals(CommonConstantUtils.XZZT_XZSB, jgWxzbwxxDO.getXzzt())
                            || StringUtils.equals(CommonConstantUtils.XZZT_WXZ, jgWxzbwxxDO.getXzzt())) {
                        jgWxzbwxxDO.setXzzt(CommonConstantUtils.XZZT_XZCG);
                        jgWxzbwxxDO.setSjgxsj(new Date());
                    }
                }
            }
            entityMapper.batchSaveSelective(jgWxzbwxxDOList);
        }
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新存量数据销账状态
     * @date : 2022/11/28 10:47
     */
    @Override
    public void updateClsjXzzt() {
        LOGGER.warn("开启存量数据销账任务，检查xzbwlx是未上报（3）销账状态是未销账（1）的数据");
        JgWxzBwxxQO jgWxzBwxxQO = new JgWxzBwxxQO();
        jgWxzBwxxQO.setXzbwlx("3");
        jgWxzBwxxQO.setXzzt("1");
        List<JgWxzbwxxDO> jgWxzbwxxDOList = accessLogMapper.queryWxzBwxx(jgWxzBwxxQO);
        LOGGER.warn("定时任务查询入参{}，查询结果数据量{}", JSON.toJSONString(jgWxzBwxxQO), CollectionUtils.size(jgWxzbwxxDOList));
        if (CollectionUtils.isNotEmpty(jgWxzbwxxDOList)) {
            //登簿日志补偿区县和时间Map
            Map<String, Set<String>> dbrzBcqxMap = new HashMap<>(1);
            for (JgWxzbwxxDO jgWxzbwxxDO : jgWxzbwxxDOList) {
                //1.自动接入 ywh可能是xmid，可能是zsid，可能是slbh
                if (StringUtils.isNotBlank(jgWxzbwxxDO.getYwh())) {
                    List<BdcXmDO> bdcXmList = new ArrayList<>(2);
                    BdcXmDO bdcXmDO = bdcdjMapper.queryBdcXm(jgWxzbwxxDO.getYwh());
                    if (Objects.isNull(bdcXmDO)) {
                        //可能是证书id，用证书id找到项目数据
                        LOGGER.warn("当前未销账业务号{}当作项目id未查询到项目，开始作为证书id查询", jgWxzbwxxDO.getYwh());
                        List<BdcXmDO> bdcXmDOList = bdcdjMapper.queryZsXmByZsid(jgWxzbwxxDO.getYwh());
                        if (CollectionUtils.isEmpty(bdcXmDOList)) {
                            LOGGER.warn("当前未销账业务号{}当作证书id未查询到项目，开始作为受理编号查询", jgWxzbwxxDO.getYwh());
                            //当作受理编号来查
                            List<BdcXmDO> bdcXmDOS = bdcdjMapper.listBdcXmBySlbh(jgWxzbwxxDO.getYwh());
                            if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
                                bdcXmList.addAll(bdcXmDOS);
                            } else {
                                LOGGER.warn("当前未销账业务号{}当作受理编号未查询到项目", jgWxzbwxxDO.getYwh());
                            }
                        } else {
                            bdcXmList.addAll(bdcXmDOList);
                        }
                    } else {
                        bdcXmList.add(bdcXmDO);
                    }
                    if (CollectionUtils.isNotEmpty(bdcXmList)) {
                        for (BdcXmDO bdcXm : bdcXmList) {
                            LOGGER.warn("当前存量数据xmid{}开始执行存量数据汇交登记时间{}", bdcXm.getXmid(), bdcXm.getDjsj());
                            if (Objects.nonNull(bdcXm.getDjsj())) {
                                bdcYwhjSbService.clsjhj(bdcXm.getXmid());
                                //记录项目的登记时间和区县，那天 的登簿日志自动重新补报
                                LOGGER.warn("当前数据上报区县{},登记时间{}", jgWxzbwxxDO.getQxdm(), bdcXm.getDjsj());
                                if (StringUtils.isNotBlank(jgWxzbwxxDO.getQxdm()) && Objects.nonNull(bdcXm.getDjsj())) {
                                    Set<String> jrrqSet = dbrzBcqxMap.get(jgWxzbwxxDO.getQxdm());
                                    if (CollectionUtils.isEmpty(jrrqSet)) {
                                        jrrqSet = new HashSet<>(1);
                                        jrrqSet.add(DateUtils.formateTime(bdcXm.getDjsj(), DateUtils.DATE_FORMAT_2));
                                        dbrzBcqxMap.put(jgWxzbwxxDO.getQxdm(), jrrqSet);
                                    } else {
                                        jrrqSet.add(DateUtils.formateTime(bdcXm.getDjsj(), DateUtils.DATE_FORMAT_2));
                                    }
                                }
                            }
                        }
                    }
                    //2.更新销账状态
                    jgWxzbwxxDO.setSjgxsj(new Date());
                    jgWxzbwxxDO.setXzzt(CommonConstantUtils.XZZT_ZZXZ);
                }
            }
            //3.登簿日志自动重新补报
            if (MapUtils.isNotEmpty(dbrzBcqxMap)) {
                LOGGER.warn("登簿日志自动补报的区县信息{}", dbrzBcqxMap);
                for (String qxdm : dbrzBcqxMap.keySet()) {
                    Set<String> jrrqSet = dbrzBcqxMap.get(qxdm);
                    if (CollectionUtils.isNotEmpty(jrrqSet)) {
                        for (String jrrq : jrrqSet) {
                            LOGGER.warn("当前区县代码{}接入日期{},开始存量数据登簿日志补偿上报", qxdm, jrrq);
                            if (StringUtils.isNotBlank(accessLogGdyyip)) {
                                fixedAppAccessLog(jrrq, qxdm);
                            } else {
                                nationalAccessRestController.accessLogNt(jrrq, qxdm);
                            }
                        }
                    }
                }
            }
            //批量更新销账状态
            entityMapper.batchSaveSelective(jgWxzbwxxDOList);
        }
    }


    /**
     * @param bdcXmDO
     * @param bdcXmDOList 用于处理一些逻辑，为了防止每次都查就改到外面传，可以不传。
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理自动汇交具体方法
     */
    private void access(BdcXmDO bdcXmDO, List<BdcXmDO> bdcXmDOList) {
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

        //判断是否需要上报，不上报存入一条数据到接入表，且上报状态为-2 不需要上报
        if (!sfsb(bdcXmDO, "", true)) {
            LOGGER.info("当前项目不需要上报，结束上报服务,bdcXmDO.getXmid：{}", bdcXmDO.getXmid());
            return;
        }
        startAccess(bdcXmDO, bdcXmDOList);
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
                LOGGER.info("====================外联注销项目开始上报，存入数据库接入表一条======================:{}关系id{}", zxTdInfo.getXmid(), zxTdInfo.getGxid());
                //数据接收，异步存入接入操作日志表，此时没有生成bwid
                BdcWlxmJrCzrzDTO wlxmJrCzrzDTO = new BdcWlxmJrCzrzDTO();
                wlxmJrCzrzDTO.setBwid("");
                wlxmJrCzrzDTO.setGxid(zxTdInfo.getGxid());
                wlxmJrCzrzDTO.setXmid(zxTdInfo.getXmid());
                wlxmJrCzrzDTO.setCzsj(new Date());
                wlxmJrCzrzDTO.setCznr("服务接收到需要市级上报的外联注销项目信息，下一步进行配置文件校验");
                wlxmJrCzrzDTO.setCzlx(1);
                wlxmJrCzrzDTO.setCzjg("1");
                asyncDealUtils.saveWlxmJrCzrz(wlxmJrCzrzDTO);
                if (nationalSwtich) {
                    saveWlxmAccess(new NationalAccess(), JrRzCgbsEnum.BEGIN_WSB.getBs(), wlxmDO, "", zxTdInfo.getGxid());
                }
                if (provinceSwtich) {
                    saveWlxmAccess(new ProvinceAccess(), JrRzCgbsEnum.BEGIN_WSB.getBs(), wlxmDO, "", zxTdInfo.getGxid());
                }

                //判断是否需要上报，不上报存入一条数据到接入表，且上报状态为-2 不需要上报
                //外联注销项目的登记时间应该是当前流程的登记时间
                wlxmDO.setDjsj(zxTdInfo.getXmDjsj());
                if (!wlxmSfsb(wlxmDO, "", true, zxTdInfo.getGxid())) {
                    LOGGER.info("当前项目不需要上报，结束上报服务,bdcXmDO.getXmid：{}", wlxmDO.getXmid());
                    continue;
                }
                try {
                    MessageModel messageModel = new MessageModel();
                    //传入gxid 作为ywlsh
                    DataModel dataModel = nationalAccessXmlZxWlxmdj.getNationalAccessDataModel(zxTdInfo.getGxid() + CommonConstantUtils.ZF_YW_DH + zxTdInfo.getXmid());
                    if (dataModel == null || CollectionUtils.isEmpty(dataModel.getQlfQlZxdjList())) {
                        continue;
                    }
                    HeadModel headModel = nationalAccessHeadModelService.getAccessHeadModel(zxTdInfo.getXmid(), true);
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
                        if (nationalSwtich) {
                            BdcAccessLog bdcAccessLog = new NationalAccess();
                            bdcAccessLog.setSjjrrq(zxTdInfo.getXmDjsj());
                            bdcAccessLog.setYwbwid(bwid);
                            entityMapper.updateByPrimaryKeySelective(bdcAccessLog);
                        }
                        if (provinceSwtich) {
                            BdcAccessLog bdcAccessLog = new ProvinceAccess();
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
                List<NationalAccessUpload> list = UploadServiceUtil.listNationalAccessUpload();
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

    public void saveBsbJrrz(BdcXmDO bdcXmDO, String xyxx) {
        if (nationalSwtich) {
            saveBdcAccess(new NationalAccess(), JrRzCgbsEnum.NO_ACCESS.getBs(), bdcXmDO, xyxx);
        }
        if (provinceSwtich) {
            saveBdcAccess(new ProvinceAccess(), JrRzCgbsEnum.NO_ACCESS.getBs(), bdcXmDO, xyxx);
        }
    }

    public void saveBsbWlxmJrrz(BdcXmDO bdcXmDO, String xyxx, String gxid) {
        if (nationalSwtich) {
            saveWlxmAccess(new NationalAccess(), JrRzCgbsEnum.NO_ACCESS.getBs(), bdcXmDO, xyxx, gxid);
        }
        if (provinceSwtich) {
            saveWlxmAccess(new ProvinceAccess(), JrRzCgbsEnum.NO_ACCESS.getBs(), bdcXmDO, xyxx, gxid);
        }
    }

    private void fixedAppAccessLog(String date, String qxdm) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("date", date);
        params.put("qxdm", qxdm);
        try {
            LOGGER.info("定时登簿日志上报采用固定IP应用模式，调用应用：{}，服务地址：{}", accessLogGdyyip, DBRZJK);
            restRpcUtils.getRpcRequest(accessLogGdyyip, DBRZJK, params);
        } catch (Exception e) {
            LOGGER.error("定时登簿日志上报采用固定IP应用模式异常，继续采用调用当前应用上报逻辑，固定IP模式调用应用：{}、服务地址：{}", accessLogGdyyip, DBRZJK, e);
            nationalAccessRestController.accessLogNt(date, qxdm);
        }
    }

    @Override
    public boolean wlxmSfsb(BdcXmDO bdcXmDO, String sfgzyz, Boolean sfjlrz, String gxid) {
        /*
         * 查询流程配置是否上报
         * */
        try {
            BdcWlxmJrCzrzDTO bdcWlxmJrCzrzDTO = new BdcWlxmJrCzrzDTO();
            bdcWlxmJrCzrzDTO.setGxid(gxid);
            bdcWlxmJrCzrzDTO.setXmid(bdcXmDO.getXmid());
            bdcWlxmJrCzrzDTO.setCzlx(0);
            bdcWlxmJrCzrzDTO.setCzsj(new Date());
            bdcWlxmJrCzrzDTO.setCzjg("0");
            if (StringUtils.isNotBlank(bdcXmDO.getGzldyid())) {
                BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyidAndDjxl(bdcXmDO.getGzldyid(), bdcXmDO.getDjxl());
                if (Objects.nonNull(bdcDjxlPzDO)) {
                    if (Objects.equals(CommonConstantUtils.SF_F_DM, bdcDjxlPzDO.getSfsb()) || Objects.isNull(bdcDjxlPzDO.getSfsb())) {
                        //配置了不上报，返回false
                        if (sfjlrz) {
                            bdcWlxmJrCzrzDTO.setCznr("流程配置中设置了不上报");
                            asyncDealUtils.saveWlxmJrCzrz(bdcWlxmJrCzrzDTO);
                            saveBsbWlxmJrrz(bdcXmDO, "流程配置中设置了不上报", gxid);
                        }
                        return false;
                    }
                }
            }
            // 判断是否是需要过滤的BDCDYH（配置的过滤xzqdm 和 是否是虚拟的BDCDYH）
            if (CommonUtil.checkFilterBdcdyh(bdcXmDO.getBdcdyh())) {
                if (sfjlrz) {
                    bdcWlxmJrCzrzDTO.setCznr("虚拟单元号不上报或者配置了过滤区县不上报");
                    asyncDealUtils.saveWlxmJrCzrz(bdcWlxmJrCzrzDTO);
                    saveBsbWlxmJrrz(bdcXmDO, "虚拟单元号不上报或者配置了过滤区县不上报", gxid);
                }
                return false;
            }
            //判断是否过滤存量历史数据上报
            if (accessFilterHistoryDataFlag && bdcXmDO.getXmly() != null && CollectionUtils.isNotEmpty(bsbXmlyList) && bsbXmlyList.contains(bdcXmDO.getXmly())) {
                if (sfjlrz) {
                    bdcWlxmJrCzrzDTO.setCznr("存量历史数据不上报");
                    asyncDealUtils.saveWlxmJrCzrz(bdcWlxmJrCzrzDTO);
                    saveBsbWlxmJrrz(bdcXmDO, "配置了存量历史数据不上报", gxid);
                }
                return false;
            }

            //判断bdc_bdcdjb_zdjbxx 表是否存在数据，不存在当前数据也不允许接入不允许上报
            //根据不动产类型判断是查宗地还是宗海
            if (ArrayUtils.contains(CommonConstantUtils.BDCLX_HYSYQ, bdcXmDO.getBdclx())) {
                LOGGER.info("当前xmid{}不动产类型{}属于海域", bdcXmDO.getXmid(), bdcXmDO.getBdclx());
                List<KttZhjbxxDO> kttZhjbxxDOList = bdcXmMapper.listZhjbxx(bdcXmDO.getXmid());
                if (CollectionUtils.isEmpty(kttZhjbxxDOList)) {
                    if (sfjlrz) {
                        asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "bdc_bdcdjb_zhjbxx表未查询到相关数据，上报终止", "", new Date(), "0");
                        saveBsbJrrz(bdcXmDO, "bdc_bdcdjb_zhjbxx表未查询到相关数据，上报终止");
                    }
                    return false;
                }
            } else {
                Map<String, Object> param = new HashMap(1);
                param.put("ywh", bdcXmDO.getXmid());
                List<KttZdjbxxDO> kttZdjbxxList = kttZdjbxxMapper.queryKttZdjbxxList(param);
                if (CollectionUtils.isEmpty(kttZdjbxxList)) {
                    if (sfjlrz) {
                        bdcWlxmJrCzrzDTO.setCznr("bdc_bdcdjb_zdjbxx表未查询到相关数据，上报终止");
                        asyncDealUtils.saveWlxmJrCzrz(bdcWlxmJrCzrzDTO);
                        saveBsbWlxmJrrz(bdcXmDO, "bdc_bdcdjb_zdjbxx表未查询到相关数据，上报终止", gxid);
                    }
                    return false;
                }
            }

            //校验是否允许上报
            if (CollectionUtils.isNotEmpty(gzyzCodeList) && Objects.nonNull(bdcXmDO.getDjsj())) {
                //根据配置的规则code ，判断是否需要上报
                if (!StringUtil.equals(CommonConstantUtils.BOOL_FALSE, sfgzyz)) {
                    YwhjyzDTO ywhjyzDTO = new YwhjyzDTO();
                    ywhjyzDTO.setBdcXmDO(bdcXmDO);
                    ywhjyzDTO.setGxid(bdcWlxmJrCzrzDTO.getGxid());
                    ywhjyzDTO.setSfjlrz(sfjlrz);
                    ywhjyzDTO.setSfwlxm(true);
                    for (String code : gzyzCodeList) {
                        ywhjyzDTO.setCode(code);
                        ywhjyzDTO.setBdcWlxmJrCzrzDTO(bdcWlxmJrCzrzDTO);
                        boolean result = ywhjYz(ywhjyzDTO);
                        //配置了的规则只要验证不通过就是直接结束循环
                        if (!result) {
                            return false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("项目id{}业务汇交验证异常", bdcXmDO.getXmid(), e);
        }
        return true;
    }

}
