package cn.gtmap.realestate.exchange.service.impl.national.access;

import cn.gtmap.realestate.common.core.bo.accessnewlog.*;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.exchange.sjpt.BdcExchangeZddz;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcDjxlPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
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
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @program: bdcdj3.0
 * @description: 登簿日志上报找平模式查询数据处理
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-12-14 11:26
 **/
@Service
public class AccessLogCheatServiceImpl implements AccessLogDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogDataServiceImpl.class);
    @Autowired
    private BdcdjMapper bdcdjMapper;
    @Autowired
    Repository repository;
    @Autowired
    private AccessLogMapper accessLogMapper;
    @Autowired
    private BdcZsFeignService zsFeignService;
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
    //是否开启找平模式
    @Value("${accessLog.turn-on-cheating: true}")
    private boolean accessTurnOnCheat;
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
     * @author <a href="mailto:yinyao@gtmap.cn">huangjian</a>
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
        accessNewLog.setAccessDate(date);
        accessNewLog.setRegisterInfo(getRegisterInfoNew(map, virtualFlag));
        accessNewLog.setAccessInfo(getAccessInfoNew(map, virtualFlag));
        List<Register> registerList = getRegisterList(map);
        RegisterList registerListDO = new RegisterList();
        registerListDO.setTotalNum(registerList.size());
        registerListDO.setRegister(registerList);
        List<Access> accessList = getAccessList(map);
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
     * @param map
     * @param virtualFlag
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取登簿信息 virtualFlag: 部分地区存在非省份的自定义的qxdm，并且需要查看登簿日志量，用于区分是否通过xm表的qxdm统计登簿量和接入量
     */
    @Override
    public RegisterInfoNew getRegisterInfoNew(Map map, boolean virtualFlag) {
        RegisterInfoNew registerInfo = new RegisterInfoNew();
        String mapperType = BdcdjMapper.class.getName();
        List<Map<String, String>> list = new ArrayList<>(10);
        //以接入表数据为准，同时查询外联项目的数据
        map.put("cgbsList", Arrays.asList(0, 1, 2));
        String getDjslMapStr = ".getCheatDbsj";
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
        try {
            list = repository.selectList(mapperType + getDjslMapStr, map);
        } catch (Exception e) {
            LOGGER.error("执行方法sql {}出现异常，方法入参{} ", mapperType + getDjslMapStr, map, e);
        }
        LOGGER.warn("当前区县{}查询当天的登簿量{}", MapUtils.getString(map, "qxdm"), CollectionUtils.size(list));
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
     * @param map
     * @param virtualFlag
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取接入信息 virtualFlag: 部分地区存在非省份的自定义的qxdm，并且需要查看登簿日志量，用于区分是否通过xm表的qxdm统计登簿量和接入量
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
        LOGGER.warn("当前区县{}找平模式下查询入参{},查询数据{}", map.get("qxdm"), JSON.toJSONString(map), JSON.toJSONString(accessSlList));
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
    public List<Register> getRegisterList(Map map) {
        List<Register> registerList = new ArrayList<>(10);
        String mapperType = BdcdjMapper.class.getName();
        String getRegisterDetails = ".getCheatRegisterDetails";
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
            LOGGER.error("查询登簿量详情sql{}异常，查询入参{}", mapperType + getRegisterDetails, map, e);
        }
        LOGGER.warn("当前区县{}查询的sqlid{}查询入参{}查询登簿详单数量{}", MapUtils.getString(map, "qxdm"), mapperType + getRegisterDetails, JSON.toJSONString(map), CollectionUtils.size(registerList));
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
                        }
                    }
                    //申请类型对照
                    if (null != register.getDJLX()) {
                        if (MapUtils.isNotEmpty(djlxMap) && StringUtils.isNotBlank(MapUtils.getString(djlxMap, register.getDJLX(), ""))) {
                            register.setDJLX(MapUtils.getString(djlxMap, register.getDJLX()));
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
                param.put("logTable", map.get("logTable"));
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
    public List<Access> getAccessList(Map map) {
        List<Access> accessList = new ArrayList<>();
        String mapperType = "";
        //不作弊，如实上报模式
        mapperType = AccessNewLogMapper.class.getName();
        String getAccessDetailsMapStr = ".getAccessDetails";
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
        LOGGER.warn("当前区县{}找平模式查询接入详单入参{},数据量{}", map.get("qxdm"), JSON.toJSONString(map), CollectionUtils.size(accessList));
        return accessList;
    }
}
