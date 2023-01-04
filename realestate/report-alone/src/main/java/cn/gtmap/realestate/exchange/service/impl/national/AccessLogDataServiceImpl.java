package cn.gtmap.realestate.exchange.service.impl.national;
/**
 * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
 * @version 1.0, 2018/12/16
 * @description 组织接入日志
 */


import cn.gtmap.realestate.common.core.bo.accessnewlog.*;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.exchange.core.domain.zd.BdcExchangeZddz;
import cn.gtmap.realestate.exchange.core.dto.accessLog.AccessLog;
import cn.gtmap.realestate.exchange.core.dto.accessLog.AccessLogs;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.accessLog.AccessLogMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.accessLog.AccessNewLogMapper;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.core.support.mybatis.page.repository.Repository;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogDataService;
import cn.gtmap.realestate.exchange.util.Constants;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.*;

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
    XmlEntityConvertUtil xmlEntityConvertUtil;
    @Autowired
    @Qualifier("serverRepository")
    Repository repository;
    @Autowired
    private AccessLogMapper accessLogMapper;


    /**
     * 数据sql版本
     */
    private String version;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
    //是否登簿日志详情模式
    @Value("${accessLog.turn-on-details: false}")
    private boolean accessTurnOnDetails;
    @Value("${accessLog.zszmh-default: }")
    private String zszmhdefault;


    //是否开启多区县登簿上报
    @Value("${accessLog.turn-on-areacode: false}")
    private boolean accessLogAreaCode;
    //区县代码对应areacode
    @Value("#{${accessLog.areacode-qxdm: {'340101':'340101-1','340102':'320102-1'}}}")
    private Map<String, String> areaCodeqxdmMap;

    /**
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取每天的日志接入数据
     */
    @Override
    public AccessLogs getAccessLogs(Map map, Date date, boolean virtualFlag) {
        AccessLogs accessLogs = new AccessLogs();
        List<AccessLog> accessLogList = new ArrayList<>();
        AccessLog accessLog = new AccessLog();
        if (map.containsKey("qxdm") && map.get("qxdm") != null) {
            accessLog.setAreaCode(map.get("qxdm").toString());
        }
        if (map.containsKey("qxmc") && map.get("qxmc") != null) {
            accessLog.setAreaName(map.get("qxmc").toString());
        }
        accessLog.setAccessDate(date);
        accessLog.setRegisterInfo(getRegisterInfo(map, virtualFlag));
        accessLog.setAccessInfo(getAccessInfo(map, virtualFlag));

        accessLogList.add(accessLog);
        if (CollectionUtils.isNotEmpty(accessLogList)) {
            accessLogs.setAccessLogList(accessLogList);
        }
        return accessLogs;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取登簿信息
     */
    @Override
    public RegisterInfo getRegisterInfo(Map map, boolean virtualFlag) {
        RegisterInfo registerInfo = new RegisterInfo();
        String mapperType = "";
        if (accessTurnOnCheat) {
            //开启作弊找平模式
            LOGGER.info("登簿数量开启cheating模式");
            mapperType = BdcdjMapper.class.getName();
        } else {
            //不作弊，如实上报模式
            mapperType = AccessNewLogMapper.class.getName();
        }
        List<Map<String, String>> list = null;
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
        if (org.apache.commons.lang.StringUtils.isNotBlank(accessFilterGzldyIds)) {
            map.put("filterGzldyIds", Arrays.asList(accessFilterGzldyIds.split(",")));
        }
        try {
            list = repository.selectList(mapperType + getDjslMapStr, map);

        } catch (Exception e) {
            list = repository.selectList(BdcdjMapper.class.getName() + getDjslMapStr, map);

        }
        int i0 = 0, i1 = 0, i2 = 0, i3 = 0, i4 = 0, i5 = 0, i6 = 0, i7 = 0, i8 = 0, i9 = 0;
        Set<Integer> businessType = new HashSet<>();
        if (CollectionUtils.isNotEmpty(list)) {
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
        registerInfo.setEasementReg(i9);
        registerInfo.setMortgageReg(i0);

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
    public AccessInfo getAccessInfo(Map map, boolean virtualFlag) {
        AccessInfo accessInfo = new AccessInfo();
        String mapperType = "";
        if (accessTurnOnCheat) {
            //开启作弊找平模式
            LOGGER.info("接入数量开启cheating模式");
            mapperType = BdcdjMapper.class.getName();
        } else {
            //不作弊，如实上报模式
            mapperType = AccessNewLogMapper.class.getName();
        }
        List<Map<String, String>> accessSlList = null;
        String getAccessSlMapStr = ".getAccessSl";
        if (virtualFlag) {
            getAccessSlMapStr = ".getXnAccessSl";
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
            accessInfo.setEasementReg(Integer.valueOf(String.valueOf(accessSlList.get(9).get("NUM"))));
            accessInfo.setMortgageReg(Integer.valueOf(String.valueOf(accessSlList.get(0).get("NUM"))));

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
        if (accessTurnOnCheat) {
            //开启作弊找平模式
            LOGGER.info("登簿详情开启cheating模式,查询表{}", MapUtils.getString(map, "logTable", ""));
            map.put("cgbsList", Arrays.asList(0, 1, 2));
        } else {
            map.put("cgbsList", null);
        }
/*        if (accessTurnOnCheat) {
            //开启作弊找平模式
            LOGGER.info("登簿详情开启cheating模式");
            mapperType = BdcdjMapper.class.getName();
        } else {
            //不作弊，如实上报模式
            mapperType = AccessNewLogMapper.class.getName();
        }*/
        String getRegisterDetails = ".getRegisterDetails";
        try {

            registerList = repository.selectList(mapperType + getRegisterDetails, map);
        } catch (Exception e) {
            registerList = repository.selectList(BdcdjMapper.class.getName() + getRegisterDetails, map);
        }
        if (CollectionUtils.isNotEmpty(registerList)) {
            for (Register register : registerList) {
                if (accessTurnOnCheat) {
                    register.setSFSB(Constants.SFSB_S);
                } else {
                    //不作弊，根据xmid查jr表ywlsh，有且cgbs !=-1就代表已上报，没有就是未上报
                    BdcAccessLog bdcAccessLog = accessLogMapper.getProvinceAccessYwbwidByXmid(register.getYWH());
                    if (null != bdcAccessLog) {
                        if (-1 == bdcAccessLog.getCgbs()) {
                            register.setSFSB(Constants.SFSB_F);
                        } else {
                            register.setSFSB(Constants.SFSB_S);
                        }
                        register.setBWID(bdcAccessLog.getYwbwid());
                    } else {
                        register.setSFSB(Constants.SFSB_F);
                    }
                }
                //qllx对照
                if (null != register.getQLLX()) {
                    LOGGER.info("登簿日志权利类型为：{}", register.getQLLX());
                    HashMap zdmap = new HashMap();
                    //权利类型
                    zdmap.put("bdcdjdm", register.getQLLX());
                    zdmap.put("zdlx", "qllx");
                    BdcExchangeZddz bdcExchangeZddz = bdcdjMapper.getBdcExchangeZddz(zdmap);
                    if (bdcExchangeZddz != null) {
                        LOGGER.info("对照后登簿日志权利类型为：{}", bdcExchangeZddz.getStddm());
                        register.setQLLX(bdcExchangeZddz.getStddm());
                    }

                }
                //申请类型
                if (null != register.getDJLX()) {
                    LOGGER.info("登簿日志申请类型为：{}", register.getDJLX());
                    HashMap zdmap = new HashMap();
                    //权利类型
                    zdmap.put("bdcdjdm", register.getQLLX());
                    zdmap.put("zdlx", "djlx");
                    BdcExchangeZddz bdcExchangeZddz = bdcdjMapper.getBdcExchangeZddz(zdmap);
                    if (bdcExchangeZddz != null) {
                        LOGGER.info("登簿日志申请类型为：{}", bdcExchangeZddz.getStddm());
                        register.setDJLX(bdcExchangeZddz.getStddm());
                    }

                }

                //根据xmid查询证书证明号,江苏要求;分割
                Map zsmap = new HashMap();
                zsmap.put("xmid", register.getYWH());
                List<BdcZsDO> zsDOList = bdcdjMapper.listBdcZs(zsmap);
                String zszmh = "";
                if (CollectionUtils.isNotEmpty(zsDOList)) {
                    for (BdcZsDO zsDO : zsDOList) {
                        zszmh += zsDO.getBdcqzh() + zszmhfgf;
                    }
                    if (org.apache.commons.lang.StringUtils.isNotBlank(zszmh)) {
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
        }


        String xml = xmlEntityConvertUtil.entityToXml(registerList);
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
        if (accessTurnOnCheat) {
            //开启作弊找平模式
            LOGGER.info("接入详情开启cheating模式");
            mapperType = BdcdjMapper.class.getName();
        } else {
            //不作弊，如实上报模式
            mapperType = AccessNewLogMapper.class.getName();
            map.put("cgbsList", Arrays.asList(0, 1, 2));
        }
        String getAccessDetailsMapStr = ".getAccessDetails";
        if (virtualFlag) {
            getAccessDetailsMapStr = ".getXnAccessDetails";
        }
        try {

            accessList = repository.selectList(mapperType + getAccessDetailsMapStr, map);
        } catch (Exception e) {
            accessList = repository.selectList(BdcdjMapper.class.getName() + getAccessDetailsMapStr, map);
        }


        String xml = xmlEntityConvertUtil.entityToXml(accessList);

        return accessList;
    }


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
        String xml = xmlEntityConvertUtil.entityToXml(accessNewLog);
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
         * 2.正常模式--查所有接入日志数据除了-2 和9 的
         * @date : 2022/6/24 11:44
         */
        mapperType = BdcdjMapper.class.getName();
        if (accessTurnOnCheat) {
            LOGGER.info("登簿数量开启cheating模式-查询表{}", MapUtils.getString(map, "logTable", ""));
            List<Integer> cgbsList = Arrays.asList(0, 1, 2);
            map.put("cgbsList", cgbsList);
        }
/*        if (accessTurnOnCheat) {
            //开启作弊找平模式
            LOGGER.info("登簿数量开启cheating模式");
            mapperType = BdcdjMapper.class.getName();
        } else {
            //不作弊，如实上报模式
            mapperType = AccessNewLogMapper.class.getName();
        }*/
        List<Map<String, String>> list = null;
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
        try {
            list = repository.selectList(mapperType + getDjslMapStr, map);

        } catch (Exception e) {
            list = repository.selectList(BdcdjMapper.class.getName() + getDjslMapStr, map);

        }


        int i0 = 0, i1 = 0, i2 = 0, i3 = 0, i4 = 0, i5 = 0, i6 = 0, i7 = 0, i8 = 0, i9 = 0;
        Set<Integer> businessType = new HashSet<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (Map<String, String> par : list) {
                LOGGER.warn("当前登簿日志信息查询入参{}，查询结果数量{}", map, CollectionUtils.size(list));
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
        String xml = xmlEntityConvertUtil.entityToXml(registerInfo);

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
        if (accessTurnOnCheat) {
            //开启作弊找平模式
            LOGGER.info("接入数量开启cheating模式");
            mapperType = BdcdjMapper.class.getName();
        } else {
            //不作弊，如实上报模式
            mapperType = AccessNewLogMapper.class.getName();
            map.put("cgbsList", Arrays.asList(0, 1, 2));
        }
        List<Map<String, String>> accessSlList = null;
        String getAccessSlMapStr = ".getAccessSl";
        if (virtualFlag) {
            getAccessSlMapStr = ".getXnAccessSl";
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
        String xml = xmlEntityConvertUtil.entityToXml(accessInfo);

        return accessInfo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}



