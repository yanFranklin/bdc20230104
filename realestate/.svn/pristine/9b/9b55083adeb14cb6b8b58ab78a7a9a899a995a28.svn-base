package cn.gtmap.realestate.exchange.service.impl.national.access;
/**
 * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
 * @version 1.0, 2018/12/16
 * @description 组织接入日志
 */


import cn.gtmap.realestate.common.core.bo.accessnewlog.*;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.exchange.sjpt.BdcExchangeZddz;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDjxlPzQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcDjxlPzFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repository;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.accessLog.AccessLogMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.accessLog.AccessNewLogMapper;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogDataService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 登簿日志服务
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0
 * @description
 */
@Service
@ConfigurationProperties(prefix = "data")
public class AccessLogDataServiceImpl implements AccessLogDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogDataServiceImpl.class);
    @Autowired
    private BdcdjMapper bdcdjMapper;
    @Autowired
    Repository repository;
    @Autowired
    private AccessLogMapper accessLogMapper;
    @Autowired
    XmlEntityConvertUtil xmlEntityConvertUtil;
    /**
     * 数据sql版本
     */
    private String version;

    @Value("${access.filter.history.data.flag:false}")
    private boolean accessFilterHistoryDataFlag;
    @Value("${access.xnbdcdyh:false}")
    private boolean accessFilterXnBdcdyhFlag;
    @Value("${access.filter.special.gzlslids:}")
    private String accessFilterGzldyIds;
    @Value("${accessLog.zszmhfgf: ;}")
    private String zszmhfgf;
    @Value("${accessLog.zszmh-default: }")
    private String zszmhdefault;
    //是否开启多区县登簿上报
    @Value("${accessLog.turn-on-areacode: false}")
    private boolean accessLogAreaCode;

    @Value("${qxdm.convert:false}")
    private boolean qxdmFilter;
    //区县代码对应areacode
    @Value("#{${accessLog.areacode-qxdm: {'340101':'340101-1','340102':'320102-1'}}}")
    private Map<String, String> areaCodeqxdmMap;
    /**
     * 1 默认
     * 2 各区县登簿日志上报时则从bdc_jr_sjjl表取值组织报文、保存到bdc_jr_sjjl字段中
     */
    @Value("${access.data.part.field:1}")
    private Integer accessDataPartField;

    @Value("${access.xsdSfjrXz: false}")
    private boolean xsdSfjrXz;

    /*
     * 市区区县代码，合肥下面有些区县在登记项目表没有数据，只有接入表数据依然按照接入表统计
     * */
    @Value("#{'${accessLog.sqqxdmList: 340100}'.split(',')}")
    private List<String> sqqxdmList;

    @Autowired
    BdcDjxlPzFeignService bdcDjxlPzFeignService;

    @Value("${ydya.convert:false}")
    private boolean ydyaConvert;

    @Autowired
    BdcXmMapper bdcXmMapper;


    /**
     * @param map
     * @param date
     * @param virtualFlag
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 获取每天的日志接入数据  新版登簿日志
     */
    @Override
    public AccessNewLogs getAccessNewLogs(Map map, Date date, boolean virtualFlag) {

        AccessNewLogs accessNewLogs = new AccessNewLogs();
        List<AccessNewLog> accessLogList = new ArrayList<>();
        AccessNewLog accessNewLog = new AccessNewLog();
        if (accessLogAreaCode) {
            if (!areaCodeqxdmMap.isEmpty()) {
                LOGGER.info("areaCodeqxdmMap配置项为:{}", areaCodeqxdmMap.toString());
                if (map.get("qxdm") != null) {
                    accessNewLog.setAreaCode(areaCodeqxdmMap.get(map.get("qxdm").toString()));
                }
            }
        } else {
            if (map.containsKey("qxdm") && map.get("qxdm") != null) {
                accessNewLog.setAreaCode(map.get("qxdm").toString());
            }

        }
        if (map.containsKey("qxmc") && map.get("qxmc") != null) {
            accessNewLog.setAreaName(map.get("qxmc").toString());
        }
        if (qxdmFilter) {
            //判断登簿日志上报查询数据是否按照区县代码过滤查询数据
            map.put("qxdmgl", true);
        }

        //查询不需要上报的流程定义id，也就是流程配置是否上报是否的数据
        try {
            BdcDjxlPzQO bdcDjxlPzQO = new BdcDjxlPzQO();
            bdcDjxlPzQO.setSfsb(0);
            List<String> bsbdyidList = bdcDjxlPzFeignService.listGzldyid(bdcDjxlPzQO);
            if (CollectionUtils.isNotEmpty(bsbdyidList)) {
                map.put("bsblcdyid", bsbdyidList);
            }
        } catch (Exception ex) {
            LOGGER.error("查询不上报流程定义id异常", ex);
        }
        accessNewLog.setAccessDate(date);
        accessNewLog.setRegisterInfo(getRegisterInfoNew(map, virtualFlag));
        accessNewLog.setAccessInfo(getAccessInfoNew(map, virtualFlag));
        List<Register> registerList = getRegisterList(map, virtualFlag);
        RegisterList registerListDO = new RegisterList();
        registerListDO.setTotalNum(registerList.size());
        registerListDO.setRegister(registerList);
        List<Access> accessList = getAccessList(map, virtualFlag);
        AccessList accessListDO = new AccessList();
        accessListDO.setTotalNum(accessList.size());
        accessListDO.setAccess(accessList);
        accessNewLog.setRegisterList(registerListDO);
        accessNewLog.setAccessList(accessListDO);
        accessLogList.add(accessNewLog);
        if (CollectionUtils.isNotEmpty(accessLogList)) {
            accessNewLogs.setAccessNewLogList(accessLogList);
        }

        return accessNewLogs;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取登簿信息
     */
    @Override
    public RegisterInfoNew getRegisterInfoNew(Map map, boolean virtualFlag) {
        RegisterInfoNew registerInfo = new RegisterInfoNew();
        String mapperType = "";


        /**
         * @param map
         * @param virtualFlag
         * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
         * @description 逻辑调整，登簿日志统一根据接入量组织数据
         * 1.找平模式--查询状态为012 的数据
         * 2.正常模式--查所有接入日志数据
         * @date : 2022/6/24 11:44
         */
        /*
         * 2022/11/29
         * 逻辑调整  不再采用找平模式，登簿量查询项目表数据djsj为当天的数据，并排除汇交验证不通过的数据，也就是sfsb验证=false 的数据
         * */
        mapperType = BdcdjMapper.class.getName();
        List<Map<String, String>> list = new ArrayList<>(10);
        String getDjslMapStr = ".getDjsl";
        if (virtualFlag) {
            getDjslMapStr = ".getXnDjsl";
        }
        if (accessFilterHistoryDataFlag) {
            map.put("historyFlag", true);
        }
        if (!accessFilterXnBdcdyhFlag) {
            map.put("xnBdcdyhFlag", true);
        }
        if (StringUtils.isNotBlank(accessFilterGzldyIds)) {
            map.put("filterGzldyIds", Arrays.asList(accessFilterGzldyIds.split(",")));
        }
        /*
         * 处理逻辑 合肥下面区县在项目表没有数据，是etl 抽取的，所以djlx 的判断查接入表的ywdm前三位
         * */
        if (Objects.equals(CommonConstantUtils.ACCESS_DATA_PART_FIELD_FROM_BDC_JR_SJJL, accessDataPartField)) {
            String qxdm = String.valueOf(map.get("qxdm"));
            if (StringUtils.isNotBlank(qxdm) && !sqqxdmList.contains(qxdm)) {
                LOGGER.warn("当前区县{}从bdc_jr_sjjl 表取djlx处理", qxdm);
                getDjslMapStr = ".getJrYwdm";
            }
        }
        //查询当天被外联注销的项目数据
        List<Map<String, String>> wlzxXmList = bdcdjMapper.listWlzxXm(map);
        LOGGER.warn("当前区县{}查询当天的外联注销项目量{}", MapUtils.getString(map, "qxdm"), CollectionUtils.size(wlzxXmList));
        try {
            list = repository.selectList(mapperType + getDjslMapStr, map);
        } catch (Exception e) {
            list = repository.selectList(BdcdjMapper.class.getName() + getDjslMapStr, map);
        }
        LOGGER.warn("当前区县{}查询当天的登簿量{}", MapUtils.getString(map, "qxdm"), CollectionUtils.size(list));
        if (CollectionUtils.isNotEmpty(wlzxXmList)) {
            list.addAll(wlzxXmList);
        }
        int i0 = 0, i1 = 0, i2 = 0, i3 = 0, i4 = 0, i5 = 0, i6 = 0, i7 = 0, i8 = 0, i9 = 0;
        Set<Integer> businessType = new HashSet<>();
        if (CollectionUtils.isNotEmpty(list)) {
            LOGGER.warn("当前登簿日志信息查询入参{}，查询结果数量{}", map, CollectionUtils.size(list));
            for (Map<String, String> par : list) {
                int val = MapUtils.getIntValue(par, "NATIONALYWDM");
                if (val == 0) {
                    i0++;
                } else if (val == 100) {
                    i1++;
                } else if (val == 200) {
                    i2++;
                } else if (val == 300) {
                    i3++;
                } else if (val == 400) {
                    i4++;
                } else if (val == 500) {
                    i5++;
                } else if (val == 600) {
                    i6++;
                } else if (val == 700) {
                    i7++;
                } else if (val == 800) {
                    i8++;
                } else if (val == 900 || val == 901 || val == 902) {
                    i9++;
                }
                businessType.add(val);
            }
        }
        registerInfo.setFirstReg(i1);
        registerInfo.setTransferReg(i2);
        registerInfo.setChangeReg(i3);
        registerInfo.setLogoutReg(i4);
        registerInfo.setRiviseReg(i5);
        registerInfo.setDissentingReg(i6);
        registerInfo.setAdvanceReg(i7);
        registerInfo.setSeizeReg(i8);
        registerInfo.setOtherReg(i9);

        int total = i0 + i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9;
        registerInfo.setTotalNum(total);
        registerInfo.setBusinessTypeCount(businessType.size());
        return registerInfo;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取接入信息
     */
    @Override
    public AccessInfoNew getAccessInfoNew(Map map, boolean virtualFlag) {
        AccessInfoNew accessInfo = new AccessInfoNew();
        String mapperType = "";
        mapperType = AccessNewLogMapper.class.getName();
        //非找平模式下查询cgbs=0，1，2 的成功放置前置机的数据
        map.put("cgbsList", Arrays.asList(0, 1, 2));
        List<Map<String, String>> accessSlList = null;
        String getAccessSlMapStr = ".getAccessSl";
        if (virtualFlag) {
            getAccessSlMapStr = ".getXnAccessSl";
        }

        /*
         * 处理逻辑 合肥下面区县在项目表没有数据，是etl 抽取的，所以djlx 的判断查接入表的ywdm前三位
         * */
        if (Objects.equals(CommonConstantUtils.ACCESS_DATA_PART_FIELD_FROM_BDC_JR_SJJL, accessDataPartField)) {
            String qxdm = String.valueOf(map.get("qxdm"));
            if (StringUtils.isNotBlank(qxdm) && !sqqxdmList.contains(qxdm)) {
                LOGGER.warn("当前区县{}从bdc_jr_sjjl 表取djlx处理", qxdm);
                getAccessSlMapStr = ".getAccessJrYwdm";
            }
        }
        try {
            accessSlList = repository.selectList(mapperType + getAccessSlMapStr, map);
        } catch (Exception e) {
            accessSlList = repository.selectList(BdcdjMapper.class.getName() + getAccessSlMapStr, map);
        }
        if (CollectionUtils.isNotEmpty(accessSlList) && accessSlList.size() == 10) {
            accessInfo.setFirstReg(Integer.valueOf(String.valueOf(accessSlList.get(1).get("NUM"))));
            accessInfo.setTransferReg(Integer.valueOf(String.valueOf(accessSlList.get(2).get("NUM"))));
            accessInfo.setChangeReg(Integer.valueOf(String.valueOf(accessSlList.get(3).get("NUM"))));
            accessInfo.setLogoutReg(Integer.valueOf(String.valueOf(accessSlList.get(4).get("NUM"))));
            accessInfo.setRiviseReg(Integer.valueOf(String.valueOf(accessSlList.get(5).get("NUM"))));
            accessInfo.setDissentingReg(Integer.valueOf(String.valueOf(accessSlList.get(6).get("NUM"))));
            accessInfo.setAdvanceReg(Integer.valueOf(String.valueOf(accessSlList.get(7).get("NUM"))));
            accessInfo.setSeizeReg(Integer.valueOf(String.valueOf(accessSlList.get(8).get("NUM"))));
            accessInfo.setOtherReg((Integer.valueOf(String.valueOf(accessSlList.get(9).get("NUM")))));
            int count = 0;
            for (int i = 1; i < accessSlList.size(); i++) {
                if (0 == Integer.valueOf(String.valueOf(accessSlList.get(i).get("NUM")))) {
                    LOGGER.info("当日无该类型业务接入");
                } else {
                    count++;
                }
            }
            accessInfo.setBusinessTypeCount(count);


            int total = Integer.valueOf(String.valueOf(accessSlList.get(0).get("NUM"))) + Integer.valueOf(String.valueOf(accessSlList.get(1).get("NUM")))
                    + Integer.valueOf(String.valueOf(accessSlList.get(2).get("NUM"))) + Integer.valueOf(String.valueOf(accessSlList.get(3).get("NUM")))
                    + Integer.valueOf(String.valueOf(accessSlList.get(4).get("NUM"))) + Integer.valueOf(String.valueOf(accessSlList.get(5).get("NUM")))
                    + Integer.valueOf(String.valueOf(accessSlList.get(6).get("NUM"))) + Integer.valueOf(String.valueOf(accessSlList.get(7).get("NUM")))
                    + Integer.valueOf(String.valueOf(accessSlList.get(8).get("NUM"))) + Integer.valueOf(String.valueOf(accessSlList.get(9).get("NUM")));
            accessInfo.setTotalNum(total);
        }
        return accessInfo;
    }

    /**
     * 查询当日登簿详情集合
     *
     * @param map
     * @return
     * @Date 2022/4/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public List<Register> getRegisterList(Map map, boolean virtualFlag) {
        List<Register> registerList = new ArrayList<>();
        String mapperType = "";

        /**
         * @param map
         * @param virtualFlag
         * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
         * @description 逻辑调整，登簿日志统一根据接入量组织数据
         * 1.找平模式--查询状态为012 的数据
         * 2.正常模式--查所有接入日志数据
         * @date : 2022/6/24 11:44
         */
        mapperType = BdcdjMapper.class.getName();
        map.put("cgbsList", null);
        if (!accessFilterXnBdcdyhFlag) {
            map.put("xnBdcdyhFlag", true);
        }
        if (StringUtils.isNotBlank(accessFilterGzldyIds)) {
            map.put("filterGzldyIds", Arrays.asList(accessFilterGzldyIds.split(",")));
        }
        String getRegisterDetails = ".getRegisterDetails";
        if (Objects.equals(CommonConstantUtils.ACCESS_DATA_PART_FIELD_FROM_BDC_JR_SJJL, accessDataPartField)) {
            String qxdm = String.valueOf(map.get("qxdm"));
            if (StringUtils.isNotBlank(qxdm) && !sqqxdmList.contains(qxdm)) {
                LOGGER.warn("当前区县{}从bdc_jr_sjjl 表取djlx处理", qxdm);
                getRegisterDetails = ".getJrRegisterDetails";
            }
        }
        try {
            registerList = repository.selectList(mapperType + getRegisterDetails, map);
        } catch (Exception e) {
            registerList = repository.selectList(BdcdjMapper.class.getName() + getRegisterDetails, map);
        }
        LOGGER.warn("当前区县{}查询的sqlid{}查询登簿详单数量{}", MapUtils.getString(map, "qxdm"), mapperType, CollectionUtils.size(registerList));
        //查询外联项目详单
        List<Register> wlxmRegisterList = bdcdjMapper.listWlxmRegsiterDetails(map);
        LOGGER.warn("当前区县{}查询外联项目登簿详单数量{}", MapUtils.getString(map, "qxdm"), CollectionUtils.size(wlxmRegisterList));
        if (CollectionUtils.isNotEmpty(wlxmRegisterList)) {
            registerList.addAll(wlxmRegisterList);
        }
        if (CollectionUtils.isNotEmpty(registerList)) {
            HashMap zdmap = new HashMap();
            //权利类型
            zdmap.put("zdlx", "qllx");
            List<BdcExchangeZddz> qllxList = bdcdjMapper.queryBdcExchangeZddzList(zdmap);
            Map<String, String> qllxMap = qllxList.stream().collect(Collectors.toMap(BdcExchangeZddz::getBdcdjdm, BdcExchangeZddz::getStddm, (key1, key2) -> key2));
            zdmap.clear();
            zdmap.put("zdlx", "djlx");
            List<BdcExchangeZddz> djlxList = bdcdjMapper.queryBdcExchangeZddzList(zdmap);
            Map<String, String> djlxMap = djlxList.stream().collect(Collectors.toMap(BdcExchangeZddz::getBdcdjdm, BdcExchangeZddz::getStddm, (key1, key2) -> key2));
            LOGGER.warn("当前登簿日志详情查询入参{}，查询结果数量{}", map, CollectionUtils.size(registerList));
            List<List> subLists = ListUtils.subList(registerList, 500);
            for (List subList : subLists) {
                List<String> ywidList = new ArrayList<>();
                for (Object object : subList) {
                    Register register = (Register) object;
                    //qllx对照
                    if (null != register.getQLLX()) {
                        //权利类型取值，判断预抵押是否处理
                        if (ydyaConvert && StringUtils.equals(CommonConstantUtils.QLLX_YG_DM.toString(), register.getQLLX()) && StringUtils.isNotBlank(register.getYWH())) {
                            BdcYgDO bdcYdya = bdcXmMapper.queryBdcYdya(register.getYWH());
                            LOGGER.info("当前xmid{}查询到预抵押数据{}且配置了预抵押对照为抵押权利类型", register.getYWH(), JSON.toJSONString(bdcYdya));
                            if (Objects.nonNull(bdcYdya)) {
                                register.setQLLX(CommonConstantUtils.QLLX_DYAQ_DM.toString());
                            }
                        }
                        if (MapUtils.isNotEmpty(qllxMap) && StringUtils.isNotBlank(MapUtils.getString(qllxMap, register.getQLLX(), ""))) {
                            register.setQLLX(MapUtils.getString(qllxMap, register.getQLLX()));
                            LOGGER.info("对照后登簿日志权利类型为：{}", register.getQLLX());
                        }
                    }
                    //申请类型对照
                    if (null != register.getDJLX()) {
                        if (MapUtils.isNotEmpty(djlxMap) && StringUtils.isNotBlank(MapUtils.getString(djlxMap, register.getDJLX(), ""))) {
                            register.setDJLX(MapUtils.getString(djlxMap, register.getDJLX()));
                            LOGGER.info("对照后登簿日志权利类型为：{}", register.getDJLX());
                        }
                    }
                    ywidList.add(register.getYWH());
                    //根据xmid查询证书证明号,江苏要求;分割
                    if (StringUtils.isBlank(register.getZSZMH()) && StringUtils.isNotBlank(register.getYWH())) {
                        List<BdcZsDO> zsDOList = bdcdjMapper.listBdczsByXmid(register.getYWH());
                        String zszmh = "";
                        if (CollectionUtils.isNotEmpty(zsDOList)) {
                            for (BdcZsDO zsDO : zsDOList) {
                                zszmh += zsDO.getBdcqzh() + zszmhfgf;
                            }
                            if (StringUtils.isNotBlank(zszmh)) {
                                zszmh = zszmh.substring(0, zszmh.length() - 1);
                            }
                        }
                        if (StringUtils.isBlank(zszmh)) {
                            if (StringUtils.isNotBlank(zszmhdefault)) {
                                zszmh = zszmhdefault;
                            }
                        }
                        register.setZSZMH(zszmh);
                    }
                    ClassHandleUtil.setDefaultValueToNullField(register);
                }
                List<Register> registers = (List<Register>) subList;
                HashMap param = new HashMap();
                param.put("ywidList", ywidList);
                List<BdcAccessLog> bdcAccessLogList = accessLogMapper.getProvinceAccessYwbwidByXmidList(param);
                Map<String, BdcAccessLog> map1 = bdcAccessLogList.stream().collect((Collectors.toMap(BdcAccessLog::getYwlsh, Function.identity())));
                if (CollectionUtils.isNotEmpty(bdcAccessLogList)) {
                    for (Register register : registers) {
                        if (map1.containsKey(register.getYWH())) {
                            if (-1 == map1.get(register.getYWH()).getCgbs()) {
                                register.setSFSB(Constants.SFSB_F);
                            } else {
                                register.setSFSB(Constants.SFSB_S);
                            }
                        }
                    }
                }
            }
        }
        return registerList;
    }

    /**
     * 查询接入详情集合
     *
     * @param map
     * @return
     * @Date 2022/4/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public List<Access> getAccessList(Map map, boolean virtualFlag) {
        List<Access> accessList = new ArrayList<>();
        String mapperType = "";
        //不作弊，如实上报模式
        mapperType = AccessNewLogMapper.class.getName();
        String getAccessDetailsMapStr = ".getAccessDetails";
        if (virtualFlag) {
            getAccessDetailsMapStr = ".getXnAccessDetails";
        }
        if (Objects.equals(CommonConstantUtils.ACCESS_DATA_PART_FIELD_FROM_BDC_JR_SJJL, accessDataPartField)) {
            String qxdm = String.valueOf(map.get("qxdm"));
            if (StringUtils.isNotBlank(qxdm) && !sqqxdmList.contains(qxdm)) {
                LOGGER.warn("当前区县{}从bdc_jr_sjjl 表取djlx处理", qxdm);
                getAccessDetailsMapStr = ".getJrAccessDetails";
            }
        }
        try {

            accessList = repository.selectList(mapperType + getAccessDetailsMapStr, map);
        } catch (Exception e) {
            accessList = repository.selectList(BdcdjMapper.class.getName() + getAccessDetailsMapStr, map);
        }
        return accessList;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}



