package cn.gtmap.realestate.exchange.service.impl.inf.log;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.AccessStatsDto;
import cn.gtmap.gtc.sso.domain.dto.AuditLogDto;
import cn.gtmap.gtc.sso.domain.dto.DataValue;
import cn.gtmap.gtc.sso.domain.dto.QueryLogCondition;
import cn.gtmap.gtc.starter.gcas.config.GTAutoConfiguration;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkDO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcDwJkMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.bdcdsflog.BdcDsfLogMapper;
import cn.gtmap.realestate.exchange.service.inf.log.BdcDsfLogService;
import cn.gtmap.realestate.exchange.util.DateUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-03-18
 * @description 第三方日志查询统计服务
 */
@Service
public class BdcDsfLogInAllServiceImpl implements BdcDsfLogService {

    private static final Logger logger = LoggerFactory.getLogger(BdcDsfLogInAllServiceImpl.class);

    @Autowired
    private LogMessageClient logMessageClient;

    private final static String ES_QUERY_TYPE_LIKE = "like";

    private final static String ES_QUERY_TYPE_EQUAL = "equal";

    private final static String ES_QUERY_TYPE_IN = "list_equal";

    private final static String ES_QUERY_DSF_FLAG = "gxbmbz";

    private final static String ES_QUERY_JKMCKEY = "jkmc";

    // 查询 调用趋势 天数
    private final static int ES_QUERY_DYQS_DAYS = 7;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    private ThreadEngine threadEngine;

    @Autowired
    private BdcDwJkMapper bdcDwJkMapper;

    @Autowired
    private Repo repository;

    public volatile static List<Map> GXBMBZ_ZDLIST = new ArrayList<>();

    /**
     * 查询接口的日志记录方式
     *
     * @param interfaceId
     * @return
     */
    public CommonResponse<BdcDwJkDO> getInterfaceLogMode(String interfaceId) {
        if (StringUtils.isNoneBlank(interfaceId)) {
            BdcDwJkDO bdcDwJkDO = bdcDwJkMapper.searchApiInfoById(interfaceId);
            if (bdcDwJkDO != null) {
                return CommonResponse.ok(bdcDwJkDO);
            }
        }
        return CommonResponse.fail("9999", "未查询到相关信息id:" + interfaceId);
    }

    /**
     * 查询接口的日志记录方式
     *
     * @param interfaceIds
     * @return
     */
    public CommonResponse<List<BdcDwJkDO>> listInterfaceLogMode(String interfaceIds) {
        if (StringUtils.isNoneBlank(interfaceIds)) {
            List<BdcDwJkDO> bdcDwJkDOS = bdcDwJkMapper.listSearchApiInfo(Arrays.asList(interfaceIds.split(",")));
            if (bdcDwJkDOS != null) {
                return CommonResponse.ok(bdcDwJkDOS);
            }
        }
        return CommonResponse.fail("9999", "未查询到相关信息id:" + interfaceIds);
    }

    /**
     * @param pageable
     * @param paramMap
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    public Object listBdcDsfRzByPage(Pageable pageable, Map<String, Object> paramMap) {
        if (paramMap.containsKey("jkid")) {
            CommonResponse<BdcDwJkDO> jkid = this.getInterfaceLogMode((String) paramMap.get("jkid"));
            if (!jkid.isSuccess()) {
                logger.error("获取接口信息异常:{}", paramMap.get("jkid"));
                return null;
            }
            paramMap.remove("jkid");
            if (0 != jkid.getData().getJklx()) {
                paramMap.put("bdcdz", jkid.getData().getJkdz());
            } else {
                paramMap.put("jkmc", jkid.getData().getJkmc());
                paramMap.put("bdcdz", jkid.getData().getJkdz());
            }

        }
        // 转换查询条件
        List<QueryLogCondition> conditions = mapToCondiList(paramMap);
        // 开始时间
        Long begin = getQueryTime(paramMap, "kssj");
        // 结束时间
        Long end = getQueryTime(paramMap, "jssj");
        Page<AuditLogDto> logPageResult = logMessageClient.listLogs(pageable.getPageNumber(), pageable.getPageSize(),
                Constants.EXCHANGE_ES_RZLX, null, null, begin, end, conditions);
        //将部门标识和接口名称转换
        if(paramMap.containsKey("gxbmbz")){
            paramMap.put("gxbmbz",Arrays.asList(paramMap.get("gxbmbz").toString().split(",")));
        }
        if(paramMap.containsKey("jkmc") && paramMap.get("jkmc").toString().contains(",")){
            paramMap.put("gxbmbz",Arrays.asList(paramMap.get("jkmc").toString().split(",")));
            paramMap.remove("jkmc");
        }
        Page<Object> dbLog = repository.selectPaging("listBdcDsfLogByPageOrder", paramMap, pageable);
        Map<String, Object> resultMap = new HashMap<>();
        if (dbLog.getTotalElements() > 0 || logPageResult.getTotalElements() > 0) {
            resultMap.put("esLog", revertToDO(logPageResult));
            resultMap.put("dbLog", dbLog);
            return resultMap;
        }
        return null;
    }

    /**
     * @param paramMap
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 第三方日志各系统请求统计
     */
    @Override
    public JSONObject countBdcDsfLogByGxbmbz(Map paramMap) {
        JSONObject result = new JSONObject();
        // 转换查询条件
        List<QueryLogCondition> conditions = mapToCondiList(paramMap);
        // 开始时间
        Long begin = getQueryTime(paramMap, "kssj");
        // 结束时间
        Long end = getQueryTime(paramMap, "jssj");

        List<Map> mapList = bdcDsfLogMapper.countBdcDsfLogByGxbmbz(paramMap);
        //当存在分中心名称和工作流名称入参时因es数据库中无相关字段，es库不做查询
        if (paramMap.containsKey("fzxmc") || paramMap.containsKey("gzldymc")) {
            if (CollectionUtils.isNotEmpty(mapList)) {
                JSONArray seriesData = new JSONArray();
                JSONArray legendSet = new JSONArray();
                for (Map map : mapList) {
                    JSONObject serieData = new JSONObject();
                    serieData.put("name", map.get("GXBMBZ"));
                    serieData.put("value", map.get("ZS"));
                    legendSet.add((String) map.get("GXBMBZ"));
                    seriesData.add(serieData);
                }
                result.put("seriesData", seriesData);
                result.put("legendData", legendSet);
            }
        } else {
            Map<String, Object> dbMap = mapList.stream().collect(Collectors.toMap(map -> (String) map.get("GXBMBZ"), map -> map.get("ZS")));
            AccessStatsDto accessStatsDto = logMessageClient.statisticLog(ES_QUERY_DSF_FLAG, Constants.EXCHANGE_ES_RZLX, null, begin, end, conditions);
            if (accessStatsDto != null && MapUtils.isNotEmpty(accessStatsDto.getDetails())) {
                Iterator<Map.Entry<String, Integer>> iterator = accessStatsDto.getDetails().entrySet().iterator();
                JSONArray seriesData = new JSONArray();
                JSONArray legendSet = new JSONArray();
                while (iterator.hasNext()) {
                    JSONObject serieData = new JSONObject();
                    Map.Entry<String, Integer> entry = iterator.next();
                    serieData.put("name", entry.getKey());
                    Integer value = entry.getValue();
                    if (dbMap.get(entry.getKey()) != null) {
                        BigDecimal bigDecimal = (BigDecimal) dbMap.get(entry.getKey());
                        value += bigDecimal.intValue();
                        dbMap.remove(entry.getKey());
                    }
                    serieData.put("value", value);
                    seriesData.add(serieData);
                    legendSet.add(entry.getKey());
                }
                dbMap.entrySet().forEach(stringObjectEntry -> {
                    JSONObject serieData = new JSONObject();
                    serieData.put("name", stringObjectEntry.getKey());
                    serieData.put("value", stringObjectEntry.getValue());
                    seriesData.add(serieData);
                    legendSet.add(stringObjectEntry.getKey());
                });
                result.put("seriesData", seriesData);
                result.put("legendData", legendSet);
            }
        }

        return result;
    }

    @Autowired
    private BdcDsfLogMapper bdcDsfLogMapper;

    /**
     * @param paramMap
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 第三方日志各系统调用趋势统计
     */
    @Override
    public JSONObject countBdcDsfLogXtdyqs(Map paramMap) {
        JSONObject result = new JSONObject();
        //当存在分中心名称和工作流名称入参时因es数据库中无相关字段，es库不做查询
        if (paramMap.containsKey("fzxmc") || paramMap.containsKey("gzldymc")) {
            List<Map> mapList = new ArrayList<>(5);

            //循环获取7个周期的数据
            for (int i = 0; i < 7; i++) {
                paramMap.put("sysQssj", i);
                paramMap.put("sysJssj", i + 1);
                List<Map> mapListWeek1 = bdcDsfLogMapper.countBdcDsfLogWeek(paramMap);
                if (CollectionUtils.isNotEmpty(mapListWeek1)) {
                    mapList.addAll(mapListWeek1);
                }
            }

            Map<String, String> xAxisMap = bdcDsfLogMapper.countWeek();
            if (CollectionUtils.isNotEmpty(mapList)) {
                List<String> legendData = new ArrayList<>();
                JSONArray seriesData = new JSONArray();
                String[] xAxisData = new String[7];
                //循环出legendData
                for (Map map : mapList) {
                    legendData.add((String) map.get("GXBMBZ"));
                }
                //循环出xAxisData
                for (int i = 0; i < xAxisMap.size(); i++) {
                    xAxisData[i] = (xAxisMap.get("WEEK" + (i + 1)));
                }

                //循环 过滤出共享部门对应每个周的值 seriesData
                if (CollectionUtils.isNotEmpty(legendData)) {
                    for (String mc : legendData) {
                        JSONObject serieData = new JSONObject();
                        serieData.put("name", mc);
                        serieData.put("type", "line");
                        //serieData.put("stack","总量");
                        JSONArray serieDatas = new JSONArray();
                        //得到seriesData 中对应时间段的数组
                        for (String week : xAxisData) {
                            boolean jkzsFlag = false;
                            for (Map map : mapList) {
                                if (StringUtils.equals(mc, (String) map.get("GXBMBZ")) && StringUtils.equals(week, (String) map.get("WEEK"))) {
                                    serieDatas.add(map.get("JKZS"));
                                    jkzsFlag = true;
                                    break;
                                }
                            }
                            if (!jkzsFlag) {
                                serieDatas.add(0);
                            }
                        }
                        serieData.put("data", serieDatas);
                        seriesData.add(serieData);
                    }
                }

                result.put("legendData", legendData);
                result.put("xAxisData", xAxisData);
                result.put("seriesData", seriesData);
            }
        } else {
            // 转换查询条件
            List<QueryLogCondition> conditions = mapToCondiList(paramMap);
            // 如果是根据共享部门做过滤，则只处理查询条件的共享部门
            Map<String, Object> gxbmMap = new HashMap<>();
            if (StringUtils.isNotBlank(MapUtils.getString(paramMap, "gxbmbz"))) {
                gxbmMap.put(MapUtils.getString(paramMap, "gxbmbz"), new JSONArray());
            } else {
                gxbmMap = initGxbmMap(JSONArray.class);
            }
            // 取七天调用情况
            String[] xAxisData = new String[ES_QUERY_DYQS_DAYS];
            Date today = DateUtils.dealDate(new Date(), "00:00:00");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            Map<String, AccessStatsDto> dayTjMap = new HashMap<>();
            List<OneDayTjThread> threadList = new ArrayList<>();
            for (int i = 0; i < ES_QUERY_DYQS_DAYS; i++) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                Date beignDate = calendar.getTime();
                xAxisData[i] = DateUtil.formateDateMdWithSplit(beignDate);
                OneDayTjThread thread = new OneDayTjThread(beignDate, conditions, dayTjMap);
                threadList.add(thread);
            }
            // 多线程执行 查询
            threadEngine.excuteThread(threadList, true);
            // 按日期 处理 dayTjMap
            for (String rq : xAxisData) {
                AccessStatsDto statsDto = dayTjMap.get(rq);
                if (statsDto != null) {
                    for (String key : gxbmMap.keySet()) {
                        Map<String, Integer> details = statsDto.getDetails();
                        if (details.containsKey(key)) {
                            ((JSONArray) gxbmMap.get(key)).add(details.get(key));
                        } else {
                            ((JSONArray) gxbmMap.get(key)).add(0);
                        }
                    }
                }
            }

            JSONArray seriesData = new JSONArray();
            JSONArray legendSet = new JSONArray();
            Iterator<Map.Entry<String, Object>> iterator = gxbmMap.entrySet().iterator();
            //查询数据库数据
            Map<Integer, Map> tempMap = new HashMap();
            for (int i = 0; i < 7; i++) {
                paramMap.put("sysQssj", i);
                paramMap.put("sysJssj", i + 1);
                List<Map> mapListWeek1 = bdcDsfLogMapper.countBdcDsfLogWeek(paramMap);
                if (CollectionUtils.isNotEmpty(mapListWeek1)) {
                    tempMap.put(i, mapListWeek1.stream().collect(Collectors.toMap(map -> map.get("GXBMBZ"), map -> map.get("JKZS"))));
                }
            }
            while (iterator.hasNext()) {
                JSONObject serieData = new JSONObject();
                Map.Entry<String, Object> entry = iterator.next();
                serieData.put("name", entry.getKey());
                JSONArray value = (JSONArray) entry.getValue();
                for (int i = 0; i < 7; i++) {
                    if (i >= value.size()) {
                        break;
                    }
                    if (tempMap.get(i) != null && tempMap.get(i).get(entry.getKey()) != null) {
                        BigDecimal tempvalue = (BigDecimal) tempMap.get(i).get(entry.getKey());
                        value.set(i, value.getInteger(i) + tempvalue.intValue());
                    }
                }
                serieData.put("data", value);
                serieData.put("type", "line");
                seriesData.add(serieData);
                legendSet.add(entry.getKey());
            }
            result.put("legendData", legendSet);
            result.put("xAxisData", xAxisData);
            result.put("seriesData", seriesData);
        }

        return result;
    }

    /**
     * @param paramMap
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 第三方日志各系统二级各系统调用量柱状图
     */
    @Override
    public JSONObject countGxtDymx(Map paramMap) {
        JSONObject result = new JSONObject();
        //数据库数据
        List<Map> mapList = bdcDsfLogMapper.countGxtMx(paramMap);
        //当存在分中心名称和工作流名称入参时因es数据库中无相关字段，es库不做查询
        if (paramMap.containsKey("fzxmc") || paramMap.containsKey("gzldymc")) {
            if (CollectionUtils.isNotEmpty(mapList)) {
                List<String> yAxisData = new ArrayList<>();
                JSONArray seriesDataCg = new JSONArray();
                JSONArray seriesDataSb = new JSONArray();
                for (Map map : mapList) {
                    yAxisData.add((String) map.get("MC"));
                    seriesDataCg.add(map.get("CGZS"));
                    seriesDataSb.add(map.get("SBZS"));
                }
                result.put("yAxisData", yAxisData);
                result.put("seriesDataCg", seriesDataCg);
                result.put("seriesDataSb", seriesDataSb);
                result.put("tableData", mapList);
            }
        } else {
            // 转换查询条件
            List<QueryLogCondition> conditions = mapToCondiList(paramMap);
            // 开始时间
            Long begin = getQueryTime(paramMap, "kssj");
            // 结束时间
            Long end = getQueryTime(paramMap, "jssj");

            // 接口名称维度的 统计 总数
            AccessStatsDto jkStatsDto = logMessageClient.statisticLog(ES_QUERY_JKMCKEY, Constants.EXCHANGE_ES_RZLX, null, begin, end, conditions);
            // 接口名称集合
            JSONArray yAxisData = new JSONArray();
            // 成功数量集合
            JSONArray seriesDataCg = new JSONArray();
            // 失败数量集合
            JSONArray seriesDataSb = new JSONArray();
            // 各接口详细情况集合
            List<Map<String, Object>> esTotalData = new ArrayList<>();


            if (MapUtils.isNotEmpty(jkStatsDto.getDetails())) {
                Map<String, Integer> jkTjMap = jkStatsDto.getDetails();
                Iterator<Map.Entry<String, Integer>> iter = jkTjMap.entrySet().iterator();
                // 处理失败结果
                QueryLogCondition ycCondi = instCondi("ycxx", Constants.EXCHANGE_RZ_YCXX_PRE, ES_QUERY_TYPE_LIKE);
                conditions.add(ycCondi);
                AccessStatsDto jkSbStatsDto = logMessageClient.statisticLog(ES_QUERY_JKMCKEY, Constants.EXCHANGE_ES_RZLX, null, begin, end, conditions);
                // 循环接口总量统计结果
                while (iter.hasNext()) {
                    Map.Entry<String, Integer> entry = iter.next();
                    {
                        Integer sbsl = 0;
                        if (MapUtils.isNotEmpty(jkSbStatsDto.getDetails()) && jkSbStatsDto.getDetails().containsKey(entry.getKey())) {
                            sbsl = jkSbStatsDto.getDetails().get(entry.getKey());
                        }
                        Map<String, Object> data = new JSONObject();
                        data.put("CGZS", entry.getValue() - sbsl);
                        data.put("MC", entry.getKey());
                        data.put("ZS", entry.getValue());
                        data.put("SBZS", sbsl);
                        esTotalData.add(data);
                    }
                }
            }
            Map<String, Map<String, Object>> esDataMap = esTotalData.stream().collect(Collectors.toMap(map -> (String) map.get("MC"), map -> map));
            if (CollectionUtils.isNotEmpty(mapList)) {
                for (Map map : mapList) {
                    yAxisData.add((String) map.get("MC"));
                    if (esDataMap.get(map.get("MC")) != null) {
                        Integer cgzs = (Integer) esDataMap.get(map.get("MC")).get("CGZS");
                        BigDecimal cgzsDb = (BigDecimal) map.get("CGZS");
                        map.put("CGZS", cgzsDb.intValue() + cgzs);
                        Integer zs = (Integer) esDataMap.get(map.get("MC")).get("ZS");
                        BigDecimal zsDb = (BigDecimal) map.get("ZS");
                        map.put("ZS", zsDb.intValue() + zs);
                        Integer pjxysj = (Integer) esDataMap.get(map.get("MC")).get("PJXYSJ");
                        BigDecimal pjxysjDb = (BigDecimal) map.get("PJXYSJ");
                        if (pjxysj != null && pjxysjDb != null) {
                            map.put("PJXYSJ", pjxysjDb.intValue() + pjxysj);
                        }
                        Integer sbzs = (Integer) esDataMap.get(map.get("MC")).get("SBZS");
                        BigDecimal sbzsDb = (BigDecimal) map.get("SBZS");
                        map.put("SBZS", sbzsDb.intValue() + sbzs);
                        seriesDataSb.add(map.get("SBZS"));
                    }
                    seriesDataCg.add(map.get("CGZS"));
                    seriesDataSb.add(map.get("SBZS"));
                }
            }
            result.put("seriesDataCg", seriesDataCg);
            result.put("yAxisData", yAxisData);
            result.put("seriesDataSb", seriesDataSb);
            result.put("tableData", mapList);
        }
        return result;
    }

    private Map<String, Object> initGxbmMap(Class valueClass) {
        Map<String, Object> gxbmMap = new HashMap();
        if (CollectionUtils.isEmpty(GXBMBZ_ZDLIST)) {
            synchronized (GXBMBZ_ZDLIST) {
                GXBMBZ_ZDLIST = bdcZdFeignService.queryBdcZd("gxbmbz");
            }
        }
        try {
            if (CollectionUtils.isNotEmpty(GXBMBZ_ZDLIST)) {
                for (Map zdMap : GXBMBZ_ZDLIST) {
                    gxbmMap.put(MapUtils.getString(zdMap, "DM"), valueClass.newInstance());
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return gxbmMap;
    }


    @Override
    public Object countZj(Map paramMap) {
        JSONObject result = new JSONObject();
        Map resultMap = bdcDsfLogMapper.countBdcDsfLogZj(paramMap);
        //当存在分中心名称和工作流名称入参时因es数据库中无相关字段，es库不做查询
        if (paramMap.containsKey("fzxmc") || paramMap.containsKey("gzldymc")) {
            return resultMap;
        } else {
            // 转换查询条件
            List<QueryLogCondition> conditions = mapToCondiList(paramMap);
            // 开始时间
            Long begin = getQueryTime(paramMap, "kssj");
            // 结束时间
            Long end = getQueryTime(paramMap, "jssj");
            QueryLogCondition dsfEvent = instCondi("event", Constants.EXCHANGE_ES_RZLX, ES_QUERY_TYPE_EQUAL);
            conditions.add(dsfEvent);
            //数据库来源统计量
            // 获取总数 维度为接口名称
            AccessStatsDto totalDto = logMessageClient.statisticLog(ES_QUERY_JKMCKEY, Constants.EXCHANGE_ES_RZLX, null, begin, end, conditions);
            int qqcsTotal = totalDto.getTotal();
            // 接口总数
            BigDecimal jkzs = (BigDecimal) resultMap.get("jkzs");
            result.put("jkzs", jkzs.add(new BigDecimal(totalDto.getDetails().size())));

            // 获取失败总数 维度为整个DSFJK
            QueryLogCondition ycCondi = instCondi("ycxx", Constants.EXCHANGE_RZ_YCXX_PRE, ES_QUERY_TYPE_LIKE);
            conditions.add(ycCondi);
            AccessStatsDto ycDTO = logMessageClient.statisticLog("event", Constants.EXCHANGE_ES_RZLX, null, begin, end, conditions);
            BigDecimal sbzs = (BigDecimal) resultMap.get("sbzs");
            result.put("sbzs", sbzs.add(new BigDecimal(ycDTO.getTotal())));
            BigDecimal cgzs = (BigDecimal) resultMap.get("cgzs");
            result.put("cgzs", cgzs.add(new BigDecimal(qqcsTotal - ycDTO.getTotal())));
            Map<String, Object> gxbmMap = initGxbmMap(JSONObject.class);
            BigDecimal gxbmgs = (BigDecimal) resultMap.get("gxbmgs");
            result.put("gxbmgs", gxbmgs.add(new BigDecimal(gxbmMap.size())));
            result.put("pjxysj", resultMap.get("pjxysj"));
        }

        return result;
    }

    @Override
    public Object countMx(Map paramMap) {
        JSONArray jsonArray = new JSONArray();
        List<Map> mapList = bdcDsfLogMapper.countBdcDsfLogByGxbmbz(paramMap);
        //当存在分中心名称和工作流名称入参时因es数据库中无相关字段，es库不做查询
        if (paramMap.containsKey("fzxmc") || paramMap.containsKey("gzldymc")) {
            return mapList;
        } else {
            // 转换查询条件
            List<QueryLogCondition> conditions = mapToCondiList(paramMap);
            // 开始时间
            Long begin = getQueryTime(paramMap, "kssj");
            // 结束时间
            Long end = getQueryTime(paramMap, "jssj");
            Map<String, Object> gxbmMap = new HashMap<>();
            if (StringUtils.isNotBlank(MapUtils.getString(paramMap, "gxbmbz"))) {
                gxbmMap.put(MapUtils.getString(paramMap, "gxbmbz"), new JSONObject());
            } else {
                gxbmMap = initGxbmMap(JSONObject.class);
            }
            // 获取总数
            AccessStatsDto accessStatsDto = logMessageClient.statisticLog(ES_QUERY_DSF_FLAG, Constants.EXCHANGE_ES_RZLX, null, begin, end, conditions);
            for (String key : gxbmMap.keySet()) {
                Map<String, Integer> details = accessStatsDto.getDetails();
                if (details.containsKey(key)) {
                    ((JSONObject) gxbmMap.get(key)).put("ZS", details.get(key));
                } else {
                    ((JSONObject) gxbmMap.get(key)).put("ZS", 0);
                }
            }

            // 获取失败次数
            // 获取失败总数 维度为整个DSFJK
            QueryLogCondition ycCondi = instCondi("ycxx", Constants.EXCHANGE_RZ_YCXX_PRE, ES_QUERY_TYPE_LIKE);
            conditions.add(ycCondi);
            AccessStatsDto ycTj = logMessageClient.statisticLog(ES_QUERY_DSF_FLAG, Constants.EXCHANGE_ES_RZLX, null, begin, end, conditions);
            for (String key : gxbmMap.keySet()) {
                Map<String, Integer> details = ycTj.getDetails();
                if (details.containsKey(key)) {
                    ((JSONObject) gxbmMap.get(key)).put("SBZS", details.get(key));
                } else {
                    ((JSONObject) gxbmMap.get(key)).put("SBZS", 0);
                }
            }
            // 计算成功总数
            Iterator<Map.Entry<String, Object>> iterator = gxbmMap.entrySet().iterator();
            Map<String, Map> dbMap = mapList.stream().collect(Collectors.toMap(map -> (String) map.get("GXBMBZ"), map -> map));
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                String key = entry.getKey();
                JSONObject value = (JSONObject) entry.getValue();
                if (dbMap.get(key) != null) {
                    BigDecimal cgzs = (BigDecimal) dbMap.get(key).get("CGZS");
                    value.put("CGZS", value.getInteger("ZS") - value.getInteger("SBZS") + cgzs.intValue());
                    BigDecimal zs = (BigDecimal) dbMap.get(key).get("ZS");
                    value.put("ZS", value.getInteger("ZS") + zs.intValue());
                    BigDecimal pjxysj = (BigDecimal) dbMap.get(key).get("PJXYSJ");
                    value.put("PJXYSJ", pjxysj.intValue());
                    BigDecimal sbzs = (BigDecimal) dbMap.get(key).get("SBZS");
                    value.put("SBZS", value.getInteger("SBZS") + sbzs.intValue());
                } else {
                    value.put("CGZS", value.getInteger("ZS") - value.getInteger("SBZS"));
                    value.put("GXBMBZ", key);
                }
                jsonArray.add(value);
            }
        }

        return jsonArray;
    }

    private Page<Map> revertToDO(Page<AuditLogDto> logPageResult) {
        List<Map> rzContents = new ArrayList<>();
        if (logPageResult.hasContent()) {
            List<AuditLogDto> auditLogDtos = logPageResult.getContent();
            for (AuditLogDto dto : auditLogDtos) {
                if (CollectionUtils.isNotEmpty(dto.getBinaryAnnotations())) {
                    Map dtoMap = revertBinaryToMap(dto.getBinaryAnnotations());
                    rzContents.add(dtoMap);
                }
            }
        }
        Page<Map> pageResult = new GTAutoConfiguration.DefaultPageImpl<>(rzContents, logPageResult.getNumber(), logPageResult.getSize(), logPageResult.getTotalElements());
        return pageResult;
    }

    /**
     * @param dataValues
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 返回值处理为 普通分页查询结果
     */
    private Map<String, Object> revertBinaryToMap(List<DataValue> dataValues) {
        Map<String, Object> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(dataValues)) {
            for (DataValue dataValue : dataValues) {
                map.put(StringUtils.upperCase(dataValue.getKey()), dataValue.getValue());
            }
        }
        return map;
    }

    /**
     * @param
     * @return cn.gtmap.gtc.sso.domain.dto.QueryLogCondition
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造 ES 查询条件
     */
    private QueryLogCondition instCondi(String key, String value, String type) {
        QueryLogCondition condition = new QueryLogCondition();
        condition.setType(type);
        condition.setKey(key);
        condition.setValue(value);
        return condition;
    }


    private List<QueryLogCondition> mapToCondiList(Map paramMap) {
        List<QueryLogCondition> conditions = new ArrayList<>();
        String gxbmbz = MapUtils.getString(paramMap, "gxbmbz");
        if (StringUtils.isNotBlank(gxbmbz)) {
            conditions.add(instCondi("gxbmbz", gxbmbz, ES_QUERY_TYPE_EQUAL));
        }
        String jkmc = MapUtils.getString(paramMap, "jkmc");
        if (StringUtils.isNotBlank(jkmc)) {
            conditions.add(instCondi("jkmc", jkmc, ES_QUERY_TYPE_LIKE));
        }
        String jkmcs = MapUtils.getString(paramMap, "jkmcs");
        if (StringUtils.isNoneBlank(jkmcs)) {
            conditions.add(instCondi("jkmc", jkmc, ES_QUERY_TYPE_IN));
        }
        String slbh = MapUtils.getString(paramMap, "slbh");
        if (StringUtils.isNotEmpty(slbh)) {
            conditions.add(instCondi("slbh", slbh, ES_QUERY_TYPE_EQUAL));
        }
        String bdcdz = MapUtils.getString(paramMap, "bdcdz");
        if (StringUtils.isNotEmpty(bdcdz)) {
            conditions.add(instCondi("bdcdz", bdcdz, ES_QUERY_TYPE_LIKE));
        }
        String dsfdz = MapUtils.getString(paramMap, "dsfdz");
        if (StringUtils.isNotEmpty(dsfdz)) {
            conditions.add(instCondi("dsfdz", bdcdz, ES_QUERY_TYPE_LIKE));
        }
        String bdcjs = MapUtils.getString(paramMap, "bdcjs");
        if (StringUtils.isNotEmpty(bdcjs)) {
            if (StringUtils.equals("qqf", bdcjs)) {
                conditions.add(instCondi("qqf", "BDC", ES_QUERY_TYPE_LIKE));
            }
            if (StringUtils.equals("xyf", bdcjs)) {
                conditions.add(instCondi("xyf", "BDC", ES_QUERY_TYPE_LIKE));
            }
        }
        String czr = MapUtils.getString(paramMap, "czr");
        if (StringUtils.isNotEmpty(czr)) {
            conditions.add(instCondi("czr", czr, ES_QUERY_TYPE_EQUAL));
        }

        String alias = MapUtils.getString(paramMap,"alias");
        if(StringUtils.isNotEmpty(alias)){
            conditions.add(instCondi("alias",alias,ES_QUERY_TYPE_EQUAL));
        }
        String qxdm = MapUtils.getString(paramMap,"qxdm");
        if(StringUtils.isNotBlank(qxdm)){
            conditions.add(instCondi("qxdm",qxdm,ES_QUERY_TYPE_EQUAL));
        }
        return conditions;
    }

    /**
     * @param paramMap
     * @param timeKey
     * @return java.lang.Long
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取查询时间
     */
    private Long getQueryTime(Map paramMap, String timeKey) {
        String dateStr = MapUtils.getString(paramMap, timeKey);
        Long temp = null;
        if (StringUtils.isNotBlank(dateStr)) {
            Date beginDate = DateUtils.formatDate(dateStr);
            if (beginDate != null) {
                temp = beginDate.getTime();
            }
        }
        return temp;
    }

    class OneDayTjThread extends CommonThread {

        private Date beginDate;

        private List<QueryLogCondition> conditions;

        private Map<String, AccessStatsDto> dayTjMap;

        public OneDayTjThread(Date beginDate, List<QueryLogCondition> conditions, Map<String, AccessStatsDto> dayTjMap) {
            this.beginDate = beginDate;
            this.conditions = conditions;
            this.dayTjMap = dayTjMap;
        }

        @Override
        public void execute() throws Exception {
            getOneDay(beginDate, conditions, dayTjMap);
        }
    }

    /**
     * @param beginDate  开始日期 00:00:00
     * @param conditions
     * @param dayTjMap
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取一天的 请求量
     */
    private void getOneDay(Date beginDate, List<QueryLogCondition> conditions, Map<String, AccessStatsDto> dayTjMap) {
        Long begin = beginDate.getTime();
        Long end = DateUtils.dealDate(beginDate, "23:59:59").getTime();
        AccessStatsDto accessStatsDto = logMessageClient.statisticLog(ES_QUERY_DSF_FLAG, Constants.EXCHANGE_ES_RZLX, null, begin, end, conditions);
        dayTjMap.put(DateUtil.formateDateMdWithSplit(beginDate), accessStatsDto);
    }
}
