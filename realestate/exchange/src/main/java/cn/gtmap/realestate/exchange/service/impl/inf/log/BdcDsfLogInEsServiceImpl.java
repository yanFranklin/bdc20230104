package cn.gtmap.realestate.exchange.service.impl.inf.log;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.clients.OrganizationManagerClient;
import cn.gtmap.gtc.sso.domain.dto.*;
import cn.gtmap.gtc.starter.gcas.config.GTAutoConfiguration;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkDO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.exchange.service.inf.log.BdcDsfLogService;
import cn.gtmap.realestate.exchange.util.DateUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
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

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-03-18
 * @description 第三方日志查询统计服务 (ES数据源)
 */
@Service
public class BdcDsfLogInEsServiceImpl implements BdcDsfLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDsfLogInEsServiceImpl.class);

    @Autowired
    private LogMessageClient logMessageClient;

    private final static String ES_QUERY_TYPE_LIKE = "like";

    private final static String ES_QUERY_TYPE_LIST = "list_equal";

    private final static String ES_QUERY_TYPE_EQUAL = "equal";

    private final static String ES_QUERY_DSF_FLAG = "gxbmbz";

    private final static String ES_QUERY_JKMCKEY = "jkmc";

    // 查询 调用趋势 天数
    private final static int ES_QUERY_DYQS_DAYS = 7;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Resource(name = "bdcDsfLogInAllServiceImpl")
    private BdcDsfLogInAllServiceImpl bdcDsfLogInAllService;

    @Autowired
    private ThreadEngine threadEngine;

    @Autowired
    private RedisUtils<String> redisUtils;

    @Autowired
    private OrganizationManagerClientMatcher organizationManagerClient;

    private static final String GXBM_DM_KEY = "gxbm:dm:key";

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
            CommonResponse<BdcDwJkDO> jkid = bdcDsfLogInAllService.getInterfaceLogMode((String) paramMap.get("jkid"));
            if (!jkid.isSuccess()) {
                LOGGER.error("获取接口信息异常:{}", paramMap.get("jkid"));
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
        LOGGER.info("查询es数据开始");
        Page<AuditLogDto> logPageResult = logMessageClient.listLogs(pageable.getPageNumber(), pageable.getPageSize(),
                Constants.EXCHANGE_ES_RZLX, null, null, begin, end, conditions);
        LOGGER.info("查询es数据结束:{}", logPageResult != null ? "1" : "0");
        return revertToDO(logPageResult);
    }

    /**
     * @param paramMap
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 第三方日志各系统请求统计
     */
    @Override
    public JSONObject countBdcDsfLogByGxbmbz(Map paramMap) {
        LOGGER.info("执行countBdcDsfLogByGxbmbz方法的开始时间:{}", DateUtils.formateTimeYmdhms(new Date()));
        JSONObject result = new JSONObject();
        // 转换查询条件
        List<QueryLogCondition> conditions = mapToCondiList(paramMap);
        // 开始时间
        Long begin = getQueryTime(paramMap,"kssj");
        // 结束时间
        Long end = getQueryTime(paramMap,"jssj");
        LOGGER.info("countBdcDsfLogByGxbmbz方法获取调用开始时间：{},参数为: {}", System.currentTimeMillis(), JSONObject.toJSONString(conditions));
        AccessStatsDto accessStatsDto = logMessageClient.statisticLog(ES_QUERY_DSF_FLAG,Constants.EXCHANGE_ES_RZLX,null,begin,end,conditions);
        LOGGER.info("countBdcDsfLogByGxbmbz方法获取调用结束时间：{}", System.currentTimeMillis());
        if(accessStatsDto != null && MapUtils.isNotEmpty(accessStatsDto.getDetails())){
            Iterator<Map.Entry<String, Integer>> iterator = accessStatsDto.getDetails().entrySet().iterator();
            List serieDataList = new ArrayList();
            List legendDataList = new ArrayList();
            while (iterator.hasNext()) {
                HashMap serieData = new HashMap();
                Map.Entry<String,Integer> entry = iterator.next();
                serieData.put("name",entry.getKey());
                serieData.put("value",entry.getValue());
                serieDataList.add(serieData);
                legendDataList.add(entry.getKey());
            }
            result.put("seriesData", serieDataList);
            result.put("legendData", legendDataList);
        }
        LOGGER.info("执行countBdcDsfLogByGxbmbz方法的结束时间:{}", DateUtils.formateTimeYmdhms(new Date()));
        return result;
    }

    /**
     * @param paramMap
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 第三方日志各系统调用趋势统计
     */
    @Override
    public JSONObject countBdcDsfLogXtdyqs(Map paramMap) {
        LOGGER.info("执行countBdcDsfLogXtdyqs方法的开始时间:{}", DateUtils.formateTimeYmdhms(new Date()));
        JSONObject result = new JSONObject();
        // 转换查询条件
        List<QueryLogCondition> conditions = mapToCondiList(paramMap);
        // 如果是根据共享部门做过滤，则只处理查询条件的共享部门
        List<String> gxbmList = new ArrayList<>();
        if (StringUtils.isNotBlank(MapUtils.getString(paramMap, "gxbmbz"))) {
            gxbmList.add(MapUtils.getString(paramMap, "gxbmbz"));
        } else {
            initGxbm(gxbmList);
        }
        // 返回的数据
        List<String> xAxisData = new Vector<>(ES_QUERY_DYQS_DAYS);
        List<SeriesData> seriesDataList = new ArrayList<>(gxbmList.size());
        Iterator<String> iterator = gxbmList.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            seriesDataList.add(SeriesData.build(next, "line", ES_QUERY_DYQS_DAYS));
        }
        // 取七天调用情况
        Date today = DateUtils.dealDate(new Date(), "00:00:00");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        List<OneDayTjThread> threadList = new ArrayList<>();
        for (int i = 0; i < ES_QUERY_DYQS_DAYS; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date beginDate = calendar.getTime();
            xAxisData.add(i, DateUtil.formateDateMdWithSplit(beginDate));
            OneDayTjThread thread = new OneDayTjThread(beginDate, conditions, seriesDataList, i);
            threadList.add(thread);
        }
        // 多线程执行 查询
        LOGGER.info("countBdcDsfLogXtdyqs方法查询logClient开始时间：{}", DateUtils.formateTimeYmdhms(new Date()));
        threadEngine.excuteThread(threadList, true);
        LOGGER.info("countBdcDsfLogXtdyqs方法查询logClient结束时间：{}", DateUtils.formateTimeYmdhms(new Date()));
        result.put("legendData", gxbmList);
        result.put("xAxisData", xAxisData);
        result.put("seriesData", seriesDataList);
        LOGGER.info("执行countBdcDsfLogXtdyqs方法的结束时间:{}", DateUtils.formateTimeYmdhms(new Date()));
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
        // 转换查询条件
        List<QueryLogCondition> conditions = mapToCondiList(paramMap);
        // 开始时间
        Long begin = getQueryTime(paramMap,"kssj");
        // 结束时间
        Long end = getQueryTime(paramMap,"jssj");

        // 接口名称维度的 统计 总数
        AccessStatsDto jkStatsDto = logMessageClient.statisticLog(ES_QUERY_JKMCKEY,Constants.EXCHANGE_ES_RZLX,null,begin,end,conditions);
        // 接口名称集合
        JSONArray yAxisData = new JSONArray();
        // 成功数量集合
        JSONArray seriesDataCg = new JSONArray();
        // 失败数量集合
        JSONArray seriesDataSb = new JSONArray();
        // 各接口详细情况集合
        JSONArray tableData = new JSONArray();
        if(MapUtils.isNotEmpty(jkStatsDto.getDetails())){
            Map<String,Integer> jkTjMap = jkStatsDto.getDetails();
            Iterator<Map.Entry<String,Integer>> iter = jkTjMap.entrySet().iterator();
            // 处理失败结果
            QueryLogCondition ycCondi = instCondi("ycxx",Constants.EXCHANGE_RZ_YCXX_PRE,ES_QUERY_TYPE_LIKE);
            conditions.add(ycCondi);
            AccessStatsDto jkSbStatsDto = logMessageClient.statisticLog(ES_QUERY_JKMCKEY,Constants.EXCHANGE_ES_RZLX,null,begin,end,conditions);
            // 循环接口总量统计结果
            while (iter.hasNext()){
                Map.Entry<String,Integer> entry = iter.next();
                yAxisData.add(entry.getKey());
                seriesDataCg.add(entry.getValue());
                Integer sbsl = 0;
                if(MapUtils.isNotEmpty(jkSbStatsDto.getDetails()) && jkSbStatsDto.getDetails().containsKey(entry.getKey())){
                    sbsl = jkSbStatsDto.getDetails().get(entry.getKey());
                }
                seriesDataSb.add(sbsl);

                JSONObject data = new JSONObject();
                data.put("CGZS",entry.getValue() - sbsl);
                data.put("MC",entry.getKey());
                data.put("ZS",entry.getValue());
                data.put("SBZS",sbsl);
                tableData.add(data);
            }
        }
        JSONObject result = new JSONObject();
        result.put("seriesDataCg",seriesDataCg);
        result.put("yAxisData",yAxisData);
        result.put("seriesDataSb",seriesDataSb);
        result.put("tableData",tableData);
        return result;
    }


    private void initGxbm(List<String> list){
//        List<Map> gxbmbz = bdcZdFeignService.queryBdcZd("gxbmbz");
//        if (CollectionUtils.isEmpty(gxbmbz)) {
//            return;
//        }
//        Iterator<Map> iterator = gxbmbz.iterator();
//        while (iterator.hasNext()) {
//            Map next = iterator.next();
//            list.add(MapUtils.getString(next,"DM"));
//        }
//

        if(!redisUtils.isExistKey(GXBM_DM_KEY)){
            redisUtils.deleteKey(GXBM_DM_KEY);
            List<Map> gxbmbz = bdcZdFeignService.queryBdcZd("gxbmbz");
            if (CollectionUtils.isEmpty(gxbmbz)) {
                return;
            }
            Iterator<Map> iterator = gxbmbz.iterator();
            while (iterator.hasNext()) {
                Map next = iterator.next();
                list.add(MapUtils.getString(next, "DM"));
            }
            LOGGER.info("从数据库字典表中取到：{}个部门", list.size());

            redisUtils.deleteKey(GXBM_DM_KEY);
            redisUtils.addSetValue(GXBM_DM_KEY, list);
            redisUtils.expire(GXBM_DM_KEY, 60L * 60 * 24 * 30 * 2);
        } else {
            List<String> redisList = Optional.of(redisUtils.getSetValue(GXBM_DM_KEY))
                    .map(ArrayList::new).orElse(new ArrayList<>());
            LOGGER.info("从redis中取到：{}个部门", redisList.size());
            list.addAll(redisList);
        }
    }




    @Override
    public Object countZj(Map paramMap) {
        LOGGER.info("执行countZj方法的开始时间:{}", DateUtils.formateTimeYmdhms(new Date()));
        // 转换查询条件
        List<QueryLogCondition> conditions = mapToCondiList(paramMap);
        // 开始时间
        Long begin = getQueryTime(paramMap,"kssj");
        // 结束时间
        Long end = getQueryTime(paramMap,"jssj");
        QueryLogCondition dsfEvent = instCondi("event",Constants.EXCHANGE_ES_RZLX,ES_QUERY_TYPE_EQUAL);
        conditions.add(dsfEvent);
        // 获取总数 维度为接口名称
        LOGGER.info("countZj方法获取总数 维度为接口名称调用开始时间：{},参数为conditions:{}", DateUtils.formateTimeYmdhms(new Date()), JSONObject.toJSONString(conditions));
        AccessStatsDto totalDto = logMessageClient.statisticLog(ES_QUERY_JKMCKEY,Constants.EXCHANGE_ES_RZLX,null,begin,end,conditions);
        LOGGER.info("countZj方法获取总数 维度为接口名称调用结束时间：{}", DateUtils.formateTimeYmdhms(new Date()));
        // 获取失败总数 维度为整个DSFJK
        QueryLogCondition ycCondi = instCondi("ycxx",Constants.EXCHANGE_RZ_YCXX_PRE,ES_QUERY_TYPE_LIKE);
        conditions.add(ycCondi);
        LOGGER.info("countZj方法获取失败总数 调用开始时间：{},参数为conditions:{}", DateUtils.formateTimeYmdhms(new Date()), JSONObject.toJSONString(conditions));
        AccessStatsDto ycDTO = logMessageClient.statisticLog("event",Constants.EXCHANGE_ES_RZLX,null,begin,end,conditions);
        LOGGER.info("countZj方法获取失败总数 调用结束时间：{}", DateUtils.formateTimeYmdhms(new Date()));

        JSONObject result = new JSONObject();
        int qqcsTotal = totalDto.getTotal();
        result.put("zs",totalDto.getTotal());
        // 接口总数
        result.put("jkzs",totalDto.getDetails().size());
        result.put("sbzs",ycDTO.getTotal());
        result.put("cgzs",qqcsTotal - ycDTO.getTotal());
        List<String> gxbmList = new ArrayList<>();
        initGxbm(gxbmList);
        result.put("gxbmgs", gxbmList.size());
        LOGGER.info("执行countZj方法的结束时间:{}", DateUtils.formateTimeYmdhms(new Date()));
        return result;
    }

    @Override
    public Object countMx(Map paramMap) {
        LOGGER.info("执行countMx方法的开始时间：{}", DateUtils.formateTimeYmdhms(new Date()));
        // 转换查询条件
        List<QueryLogCondition> conditions = mapToCondiList(paramMap);
        // 开始时间
        Long begin = getQueryTime(paramMap,"kssj");
        // 结束时间
        Long end = getQueryTime(paramMap,"jssj");
        List<String> gxbmList = new ArrayList<>(10);
        if(StringUtils.isNotBlank(MapUtils.getString(paramMap,"gxbmbz"))){
            gxbmList.add(MapUtils.getString(paramMap,"gxbmbz"));
        }else{
            initGxbm(gxbmList);
        }
        // 声明初始化大小，避免扩容
        List<Map<String,Object>> returnList = new ArrayList<>(gxbmList.size());
        // 获取总数
        LOGGER.info("countMx方法获取总数 调用开始时间：{},参数为conditions:{}", DateUtils.formateTimeYmdhms(new Date()), JSONObject.toJSONString(conditions));
        AccessStatsDto zsDto = logMessageClient.statisticLog(ES_QUERY_DSF_FLAG,Constants.EXCHANGE_ES_RZLX,null,begin,end,conditions);
        LOGGER.info("countMx方法获取总数 调用结束时间：{}", DateUtils.formateTimeYmdhms(new Date()));
        // 获取失败总数 维度为整个DSFJK
        QueryLogCondition ycCondi = instCondi("ycxx",Constants.EXCHANGE_RZ_YCXX_PRE,ES_QUERY_TYPE_LIKE);
        conditions.add(ycCondi);
        LOGGER.info("countMx方法获取失败总数 调用开始时间：{},参数为conditions:{}", DateUtils.formateTimeYmdhms(new Date()), JSONObject.toJSONString(conditions));
        AccessStatsDto sbDto = logMessageClient.statisticLog(ES_QUERY_DSF_FLAG,Constants.EXCHANGE_ES_RZLX,null,begin,end,conditions);
        LOGGER.info("countMx方法获取失败总数 调用结束时间：{}", DateUtils.formateTimeYmdhms(new Date()));
        // 计算成功总数
        Iterator<String> iterator = gxbmList.iterator();
        while (iterator.hasNext()){
            String gxbm = iterator.next();
            Integer zs = zsDto.getDetails().getOrDefault(gxbm, 0);
            Integer sb = sbDto.getDetails().getOrDefault(gxbm, 0);
            Map<String,Object> map = new HashMap<>();
            map.put("CGZS", zs - sb);
            map.put("GXBMBZ", gxbm);
            map.put("SBZS", sb);
            map.put("ZS", zs);
            returnList.add(map);
        }
        LOGGER.info("执行countMx方法的结束时间：{}", DateUtils.formateTimeYmdhms(new Date()));
        return returnList;
    }

    private Page<Map> revertToDO(Page<AuditLogDto> logPageResult){
        List<Map> rzContents = new ArrayList<>();
        if(logPageResult.hasContent()){
            List<AuditLogDto> auditLogDtos = logPageResult.getContent();
            for(AuditLogDto dto:auditLogDtos){
                if(CollectionUtils.isNotEmpty(dto.getBinaryAnnotations())){
                    Map dtoMap = revertBinaryToMap(dto.getBinaryAnnotations());
                    rzContents.add(dtoMap);
                }
            }
        }
        Page<Map> pageResult = new GTAutoConfiguration.DefaultPageImpl<>(rzContents,logPageResult.getNumber(),logPageResult.getSize(),logPageResult.getTotalElements());
        return pageResult;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param dataValues
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @description 返回值处理为 普通分页查询结果
     */
    private Map<String,Object> revertBinaryToMap(List<DataValue> dataValues){
        Map<String,Object> map = new HashMap<>();
        if(CollectionUtils.isNotEmpty(dataValues)){
            for(DataValue dataValue:dataValues){
                map.put(StringUtils.upperCase(dataValue.getKey()),dataValue.getValue());
            }
        }
        return map;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return cn.gtmap.gtc.sso.domain.dto.QueryLogCondition
     * @description 构造 ES 查询条件
     */
    private QueryLogCondition instCondi(String key,String value,String type){
        QueryLogCondition condition = new QueryLogCondition();
        condition.setType(type);
        condition.setKey(key);
        condition.setValue(value);
        return condition;
    }


    private List<QueryLogCondition> mapToCondiList(Map paramMap){
        List<QueryLogCondition> conditions = new ArrayList<>();
        String gxbmbz = MapUtils.getString(paramMap,"gxbmbz");
        if(StringUtils.isNotBlank(gxbmbz)){
            conditions.add(instCondi("gxbmbz",gxbmbz,ES_QUERY_TYPE_LIST));
        }
        String jkmc = MapUtils.getString(paramMap,"jkmc");
        if(StringUtils.isNotBlank(jkmc) && jkmc.contains(",")){
            conditions.add(instCondi("jkmc",jkmc,ES_QUERY_TYPE_LIST));
        } else if(StringUtils.isNotBlank(jkmc)) {
            conditions.add(instCondi("jkmc",jkmc,ES_QUERY_TYPE_LIKE));
        }
        String jkmcmh = MapUtils.getString(paramMap,"jkmcmh");
        if(StringUtils.isNotBlank(jkmcmh)){
            conditions.add(instCondi("jkmc",jkmcmh,ES_QUERY_TYPE_LIKE));
        }
        String slbh = MapUtils.getString(paramMap,"slbh");
        if(StringUtils.isNotEmpty(slbh)){
            conditions.add(instCondi("slbh",slbh,ES_QUERY_TYPE_EQUAL));
        }
        String bdcdz = MapUtils.getString(paramMap,"bdcdz");
        if(StringUtils.isNotEmpty(bdcdz)){
            conditions.add(instCondi("bdcdz",bdcdz,ES_QUERY_TYPE_LIKE));
        }
        String dsfdz = MapUtils.getString(paramMap,"dsfdz");
        if(StringUtils.isNotEmpty(dsfdz)){
            conditions.add(instCondi("dsfdz",dsfdz,ES_QUERY_TYPE_LIKE));
        }
        String bdcjs = MapUtils.getString(paramMap,"bdcjs");
        if(StringUtils.isNotEmpty(bdcjs)){
            if(StringUtils.equals("qqf",bdcjs)){
                conditions.add(instCondi("qqf","BDC",ES_QUERY_TYPE_LIKE));
            }
            if(StringUtils.equals("xyf",bdcjs)){
                conditions.add(instCondi("xyf","BDC",ES_QUERY_TYPE_LIKE));
            }
        }
        String czr = MapUtils.getString(paramMap,"czr");
        String bmid = MapUtils.getString(paramMap,"bmid");
        if(StringUtils.isNotEmpty(czr)){
            conditions.add(instCondi("czr",czr,ES_QUERY_TYPE_LIST));
        } else if (StringUtils.isNotBlank(bmid)){
            List<UserDto> users = new ArrayList<>();
            String[] bmidArray = bmid.split(CommonConstantUtils.ZF_YW_DH);
            for (int i = 0; i < bmidArray.length; i++) {
                List<UserDto> tempUsers = organizationManagerClient.listUsersByOrg(bmidArray[i]);
                if(CollectionUtils.isNotEmpty(tempUsers)){
                    users.addAll(tempUsers);
                }
            }
            if(CollectionUtils.isNotEmpty(users)){
                String orgUserName = users
                        .stream()
                        .map(UserDto::getUsername)
                        .collect(Collectors.joining(","));
                if(StringUtils.isNotBlank(orgUserName)){
                    czr = orgUserName;
                    conditions.add(instCondi("czr",czr,ES_QUERY_TYPE_LIST));
                }
            }
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
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @param timeKey
     * @return java.lang.Long
     * @description 获取查询时间
     */
    private Long getQueryTime(Map paramMap,String timeKey){
        String dateStr = MapUtils.getString(paramMap,timeKey);
        Long temp = null;
        if(StringUtils.isNotBlank(dateStr)){
            Date beginDate = DateUtils.formatDate(dateStr);
            if(beginDate != null){
                temp = beginDate.getTime();
            }
        }
        return temp;
    }

    class OneDayTjThread extends CommonThread {

        private Integer num;

        private Date beginDate;

        private List<QueryLogCondition> conditions;


        private List<SeriesData> seriesData;

        public OneDayTjThread(Date beginDate,List<QueryLogCondition> conditions,
                              List<SeriesData> seriesData, Integer num) {
            this.beginDate = beginDate;
            this.conditions = conditions;
            this.seriesData = seriesData;
            this.num = num;
        }

        @Override
        public void execute() throws Exception {
            getOneDay(beginDate,conditions,seriesData,num);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param beginDate 开始日期 00:00:00
     * @param conditions
     * @return void
     * @description 获取一天的 请求量
     */
    private void getOneDay(Date beginDate,List<QueryLogCondition> conditions, List<SeriesData> seriesData, Integer num){
        Long begin = beginDate.getTime();
        Long end = DateUtils.dealDate(beginDate,"23:59:59").getTime();
        AccessStatsDto accessStatsDto = logMessageClient.statisticLog(ES_QUERY_DSF_FLAG,Constants.EXCHANGE_ES_RZLX,null,begin,end,conditions);
        // 构建数据
        for (SeriesData series : seriesData) {
            Integer size = accessStatsDto.getDetails().get(series.getName());
            if (Objects.nonNull(size)) {
                series.getData().set(num, size);
            }
        }
    }




    public static class SeriesData{
        private String name;
        private String type;
        private ArrayList<Integer> data;

        public static SeriesData build(String name, String type, Integer dataInitSize) {
            SeriesData seriesData = new SeriesData();
            seriesData.name = name;
            seriesData.type = type;
            seriesData.data = new ArrayList<>(dataInitSize);
            for (Integer i = 0; i < dataInitSize; i++) {
                seriesData.data.add(i, 0);
            }
            return seriesData;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ArrayList<Integer> getData() {
            return data;
        }

        public void setData(ArrayList<Integer> data) {
            this.data = data;
        }
    }
}
