package cn.gtmap.realestate.check.core.thread;

import cn.gtmap.realestate.check.config.ValidateFileMap;
import cn.gtmap.realestate.check.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.check.utils.CheckAllXmThread;
import cn.gtmap.realestate.check.utils.Constants;
import cn.gtmap.realestate.check.utils.RuleThread;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.check.CheckGzjcLogDO;
import cn.gtmap.realestate.common.core.domain.check.CheckGzxxDO;
import cn.gtmap.realestate.common.core.domain.check.CheckLogDO;
import cn.gtmap.realestate.common.core.domain.check.CheckPlanDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 规则中转引擎类
 * @author lst
 * @version  v1.0,20-01-03
 * @description 规则中转，根据项目的流程和规则配置动态调用不同规则
 */
@Component
public class RuleEngine {

    /**
     * log日志对象
     * @author lst
     * @description log日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(RuleEngine.class);

    /**
     * entity对象Mapper
     * @author lst
     * @description entity对象Mapper
     */
    @Autowired
    private EntityMapper entityMapper;

    /**
     * bdcXm对象Mapper
     *
     * @author hqz
     * @description bdcXm对象Mapper
     */
    @Autowired
    private BdcXmMapper bdcXmMapper;

    /*
     * 强制校验文件，不管是否忽略
     * */
    @Autowired
    ValidateFileMap validateFileMap;

    /**
     * 线程池对象
     *
     * @author lst
     */
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Value("${check.plan.version:1}")
    private String planVersion;

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            // 核心线程数量
            Runtime.getRuntime().availableProcessors(),
            // 最大线程数
            Runtime.getRuntime().availableProcessors() * 2,
            // 空闲超时一小时（调用频繁）
            600, TimeUnit.SECONDS,
            // 阻塞队列
            new ArrayBlockingQueue<>(100),
            // 线程工厂
            new ThreadFactoryBuilder().setNameFormat("quartz-checkall-%d").build(),
            // 过多任务直接主线程处理
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    /**
     *规则执行方法
     * @author hqz
     * @description 规则执行方法
     * @return
     */
    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 定时任务检查逻辑调整，整库查询，从上次检查结束时间之后查询全库符合条件数据
     * @date : 2022/5/7 8:46
     */
    public List<CheckGzjcLogDO> excuteTask() {
        //检测日志结果
        List<CheckGzjcLogDO> result = new ArrayList<>();
        Date lastExcuteTime = null;
        Map<String, Object> parMap = new HashMap<>();

        //查询时间
        Date time = new Date(System.currentTimeMillis());
        //获取上一次的运行起始时间
        CheckLogDO checkLogDO = bdcXmMapper.queryPrevRunLog();
        if(checkLogDO!=null && checkLogDO.getQssj()!=null){
            lastExcuteTime=checkLogDO.getQssj();
        }
        //未办结项目记录表的查询
        List<BdcXmDO> bjXmLst =bdcXmMapper.selectBjxmFromWbj();
        //符合当前时间段的数据查询
        boolean check = true;
        // 查询数据开始条数
//        int begin = 0;
        // 待检测项目总数
        int xmsl = 0;
        // 办结项目数量
        int bjxmsl = bjXmLst.size();
        // 每次最多检查100000条数据
        while (check){
            try {
                parMap.put("lastExcuteTime", lastExcuteTime);
                List<BdcXmDO> bdcXmList = bdcXmMapper.selectAllBdcXm(parMap);
                check = false;
                xmsl = CollectionUtils.size(bdcXmList);
                //输出项目总数
                logger.warn("待检测项目数:{}", xmsl);
                checkAll(Constants.THREAD_PREFIX_QUARTZ, result, parMap, bdcXmList);
            }catch(Exception e){
                logger.error("全库扫描检查发生异常：", e);
                check = true;
            }
        }
        //记录到日志中
        saveCheckLog(time,xmsl,bjxmsl,result.size(),Constants.CHECK_TYPE_QUARTZ);
        return result;
    }
    /**
     * 检查计划执行方法
     * @author hqz
     * @description 规则执行方法
     * @return
     */
    public List<CheckGzjcLogDO> excutePlanTask(){
        //检测日志结果
        List<CheckGzjcLogDO> result=new ArrayList<>();
        Map<String,Object> parMap = new HashMap<>();
        if(StringUtils.isNotBlank(planVersion)){
            // 根据版本查询检查计划
            CheckPlanDO plan = new CheckPlanDO();
            plan.setVersion(planVersion==null?"1":planVersion);
            List<CheckPlanDO> checkPlanDOList = bdcXmMapper.queryCheckPlan(plan);
            if(CollectionUtils.isNotEmpty(checkPlanDOList)){
                plan = checkPlanDOList.get(0);
                // 比较检查次数和当前检查次数
                if(plan.getDqjccs()==null || plan.getJccs() > plan.getDqjccs()){
                    //查询时间
                    Date time=new Date(System.currentTimeMillis());
                    //未办结项目记录表的查询
                    List<BdcXmDO> bjXmLst =bdcXmMapper.selectBjxmFromWbj();
                    // 查询数据开始条数
                    int begin = plan.getDcjcts()* (plan.getDqjccs()==null?0:plan.getDqjccs());
                    // 待检测项目总数
                    int xmsl = 0;
                    // 办结项目数量
                    int bjxmsl = bjXmLst.size();
                    // 因为检查计划单次检查数据量不会很大暂时不做分批处理
                    try {
                        parMap.put("qsrq", plan.getQsrq());
                        parMap.put("jsrq", plan.getJsrq());
                        parMap.put("begin", begin);
                        parMap.put("end", begin + plan.getDcjcts());
                        List<BdcXmDO> bdcXmList = bdcXmMapper.selectAllBdcXmOrderByXmid(parMap);
                        if (CollectionUtils.isNotEmpty(bjXmLst)) {
                            bdcXmList.addAll(bjXmLst);
                        }
                        //输出项目总数
                        logger.warn("待检测项目数:{}", bdcXmList.size());
                        //与重检走相同的方法
                        retestTask(bdcXmList, false, Constants.THREAD_PREFIX_PLAN, null, result);
                        xmsl += bdcXmList.size();
                    }catch(Exception e){
                        logger.error("检查发生异常：",e);
                    }
                    // 更新检查计划
                    updateCheckPlan(plan);
                    //记录到日志中
                    saveCheckLog(time,xmsl,bjxmsl,result.size(),Constants.CHECK_TYPE_PLAN);
                }else{
                    // 检查计划执行完后，在日志表插入一条数据  定时任务检查时使用
                    // 先判断日志表有没有定时任务的日志，有则不插入
                    CheckLogDO checkLogDO = bdcXmMapper.queryPrevRunLog();
                    if(checkLogDO == null){
                        saveCheckLog(plan.getJsrq(),0,0,0,Constants.CHECK_TYPE_QUARTZ);
                    }
                }
            }
        }
        return result;
    }

    /**
    * 增量数据检查执行方法
    * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
    * @date 2022/4/15 14:50
    * @return 检测日志结果
    * @description 增量数据检查执行方法
    **/
    public List<CheckGzjcLogDO> excuteCheckZlsjTask(){
        //检测日志结果
        List<CheckGzjcLogDO> result=new ArrayList<>();
        HashMap<String, Object> parMap = new HashMap<>();
        //查询时间
        Date time = new Date(System.currentTimeMillis());
        //符合当前时间段的数据查询
        boolean check = true;
        int begin = 0;
        int xmsl = 0;
        while(check){
            try{
                //查询日期查当天的
                String cxrq = DateUtils.format(new Date(), "yyyy-MM-dd");
                parMap.put("begin", begin);
                parMap.put("end", begin + Constants.MAX_CHECKDATA);
                parMap.put("qsrq", cxrq);
                parMap.put("jsrq", cxrq);
                List<BdcXmDO> bdcXmList = bdcXmMapper.selectAllBdcXm(parMap);
                if (CollectionUtils.isEmpty(bdcXmList) || bdcXmList.size() < Constants.MAX_CHECKDATA) {
                    // 如果bdcXmList 为空则不终止下次检查
                    check = false;
                } else {
                    begin += Constants.MAX_CHECKDATA;
                }
                retestTask(bdcXmList, false, Constants.THREAD_PREFIX_ZL, null, result);
                xmsl+=bdcXmList.size();
            }catch (Exception e){
                logger.error("检查发生异常：",e);
            }
        }
        saveCheckLog(time,xmsl,0,result.size(),Constants.CHECK_TYPE_ZL);

        return result;
    }
    /**
     *规则执行方法
     * @author lst
     * @description 规则执行方法
     * @description 规则执行方法  是否是重检
     * @return
     */
    public String retestTask(List<BdcXmDO> bdcXmLst, boolean isRetest, String type, String gzid,List<CheckGzjcLogDO> result){
        String msg="";
        Map<String,Object> parMap = null;
        if (CollectionUtils.isNotEmpty(bdcXmLst)) {
            List<CheckGzxxDO> checkList = new ArrayList<>(10);
            parMap = new HashMap<>();
            if (StringUtils.isNotBlank(gzid)) {
                parMap.put("gzid", gzid);
                parMap.put("sfhl", CommonConstantUtils.SF_F_DM);
                checkList = bdcXmMapper.selectAllBdcGzxx(parMap);
            } else {
                List<CheckGzxxDO> gzxxLst = bdcXmMapper.selectAllBdcGzxx(parMap);
                //某些规则不管是否忽略异常，走配置xml
                //1.先判断配置的xml 的code 是否在验证表都存在，不存在直接返回提示信息
                //2.都存在不管是否忽略都执行
                //3. 找出其他不忽略的规则加入执行集合

                //缺失的规则code
                List<String> lackList = Lists.newArrayList();
                if (CollectionUtils.isNotEmpty(gzxxLst)) {
                    //转为map
                    Map<String, CheckGzxxDO> checkGzxxDOMap = gzxxLst.stream().collect(Collectors.toMap(CheckGzxxDO::getGzcode, Function.identity(), (k1, k2) -> k2));
                    for (Map.Entry<String, String> entyr : validateFileMap.getValidateMap().entrySet()) {
                        if (checkGzxxDOMap.containsKey(entyr.getKey())) {
                            checkList.add(checkGzxxDOMap.get(entyr.getKey()));
                        } else {
                            lackList.add(entyr.getKey());
                        }
                    }
                    //去除强制验证的规则信息，增加非强制验证且不可忽略的规则
                    gzxxLst.removeAll(checkList);
                    gzxxLst = gzxxLst.stream().filter(checkGzxxDO -> Objects.equals(0, checkGzxxDO.getSfhl())).collect(Collectors.toList());
                    checkList.addAll(gzxxLst);
                }
                if (CollectionUtils.isNotEmpty(lackList)) {
                    msg = "以下规则code 未配置，请先配置再执行检查" + lackList;
                    return msg;
                }
            }
            if (CollectionUtils.isEmpty(checkList)) {
                msg = "未查询到需要检测的规则";
                return msg;
            }
            //重检
            if (isRetest) {
                ThreadPoolExecutor threadPoolExecutor = taskExecutor.getThreadPoolExecutor();
                if (threadPoolExecutor.getActiveCount() > 0) {
                    String name = taskExecutor.getThreadNamePrefix();
                    if (StringUtils.equals(name, Constants.THREAD_PREFIX_QUARTZ)) {
                        msg = "定时检测任务正在运行,请稍后执行!";
                    }else if(StringUtils.equals(name,Constants.THREAD_PREFIX_RETEST)){
                        msg="重检任务正在运行,请稍后执行!";
                    }else if(StringUtils.equals(name,Constants.THREAD_PREFIX_TIMES)){
                        msg="时段检测正在运行,请稍后执行!";
                    }else if(StringUtils.equals(name,Constants.THREAD_PREFIX_RULES)){
                        msg="规则检测正在运行,请稍后执行!";
                    }else if(StringUtils.equals(name,Constants.THREAD_PREFIX_PLAN)){
                        msg="计划检测正在运行,请稍后执行!";
                    }else if(StringUtils.equals(name,Constants.THREAD_PREFIX_ZL)){
                        msg="增量数据定时检测任务正在运行,请稍后执行!";
                    }
                } else {
                    futureTask(bdcXmLst, type, checkList);
                }
            } else {
                List<CheckGzjcLogDO> list = excuteThread(bdcXmLst, type, checkList);
                if (result != null && CollectionUtils.isNotEmpty(list)) {
                    result.addAll(list);
                }
            }
        }
        return msg;
    }

    /**
     * @param type
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 执行全库扫描检查方法
     * @date : 2022/5/7 10:29
     */
    private String checkAll(String type, List<CheckGzjcLogDO> result, Map paramMap, List<BdcXmDO> bdcXmDOList) {
        String msg = "";
        Map<String, Object> parMap = new HashMap<>(1);
        //查询除所有的校验规则
        List<CheckGzxxDO> gzxxLst = bdcXmMapper.selectAllBdcGzxx(parMap);
        //某些规则不管是否忽略异常，走配置xml
        //1.先判断配置的xml 的code 是否在验证表都存在，不存在直接返回提示信息
        //2.都存在不管是否忽略都执行
        //3. 找出其他不忽略的规则加入执行集合

        //缺失的规则code
        List<String> lackList = Lists.newArrayList();
        List<CheckGzxxDO> checkList = new ArrayList<>(35);
        if (CollectionUtils.isNotEmpty(gzxxLst)) {
            //转为map
            Map<String, CheckGzxxDO> checkGzxxDOMap = gzxxLst.stream().collect(Collectors.toMap(CheckGzxxDO::getGzcode, Function.identity(), (k1, k2) -> k2));
            for (Map.Entry<String, String> entyr : validateFileMap.getValidateMap().entrySet()) {
                if (checkGzxxDOMap.containsKey(entyr.getKey())) {
                    checkList.add(checkGzxxDOMap.get(entyr.getKey()));
                } else {
                    lackList.add(entyr.getKey());
                }
            }
            //去除强制验证的规则信息，增加非强制验证且不可忽略的规则
            gzxxLst.removeAll(checkList);
            gzxxLst = gzxxLst.stream().filter(checkGzxxDO -> Objects.equals(0, checkGzxxDO.getSfhl())).collect(Collectors.toList());
            checkList.addAll(gzxxLst);
        }
        if (CollectionUtils.isNotEmpty(lackList)) {
            msg = "以下规则code 未配置，请先配置再执行检查" + lackList;
            return msg;
        }


        //sql 的走全库扫描方法，检查全部
        List<CheckGzxxDO> checkSqlGzxxList = checkList.stream().filter(checkGzxxDO -> Objects.equals(Constants.GZ_TYPE_SQL, checkGzxxDO.getGzlx())).collect(Collectors.toList());
        //剩下的规则走之前的逻辑循环项目数据//与重检走相同的方法
        checkList.removeAll(checkSqlGzxxList);
        List<CheckGzjcLogDO> list = new ArrayList<>(1);
//
        //规则和服务同时执行，线程并列
        List<CheckGzjcLogDO> finalList = list;
        CompletableFuture<List<CheckGzjcLogDO>> gzFuture = CompletableFuture.supplyAsync(() -> {
            finalList.addAll(excuteAllThread(type, checkSqlGzxxList, paramMap, bdcXmDOList));
            return null;
        }, threadPoolExecutor);
        CompletableFuture<List<CheckGzjcLogDO>> fwFuture = CompletableFuture.supplyAsync(() -> {
            if (CollectionUtils.size(bdcXmDOList) > 10000) {
                List<List> partBdcXmDoList = ListUtils.subList(bdcXmDOList, 10000);
                for (List partList : partBdcXmDoList) {
                    finalList.addAll(excuteThread(partList, type, checkList));
                }
            } else {
                finalList.addAll(excuteThread(bdcXmDOList, type, checkList));
            }
            return null;
        }, threadPoolExecutor);
        List<CompletableFuture<List<CheckGzjcLogDO>>> completableFutureList = new ArrayList<>();
        completableFutureList.add(gzFuture);
        completableFutureList.add(fwFuture);
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[2]));
        try {
            allFutures.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("执行全库扫描异常", e);
        }
//        if (CollectionUtils.isNotEmpty(checkSqlGzxxList)) {
//            list.addAll(excuteAllThread(type, checkSqlGzxxList, paramMap, bdcXmDOList));
//        }
//        if (CollectionUtils.isNotEmpty(checkList)) {
//            logger.warn("服务规则数量{}", CollectionUtils.size(checkList));
//            if (CollectionUtils.size(bdcXmDOList) > 10000) {
//                List<List> partBdcXmDoList = ListUtils.subList(bdcXmDOList, 10000);
//                for (List partList : partBdcXmDoList) {
//                    list.addAll(excuteThread(partList, type, checkList));
//                }
//            } else {
//                list.addAll(excuteThread(bdcXmDOList, type, checkList));
//            }
//        }
        if (result != null && CollectionUtils.isNotEmpty(finalList)) {
            result.addAll(finalList);
        }
        return msg;
    }


    /**
     * 异步方法
     *
     * @param bdcXmList
     * @param type
     * @param gzxx
     */
    @Async
    public void futureTask(List<BdcXmDO> bdcXmList, String type,List<CheckGzxxDO> gzxx) {
        excuteThread(bdcXmList,type,gzxx);
    }


    /**
     * 定时线程任务开启
     * @param bdcXmList 总的不动产项目集合
     * @param type 是否是重检
     */
    private List<CheckGzjcLogDO> excuteThread(List<BdcXmDO> bdcXmList, String type,List<CheckGzxxDO> gzxx) {
        List<CheckGzjcLogDO> list=null;
        if (CollectionUtils.isNotEmpty(bdcXmList)) {
            list=new CopyOnWriteArrayList<>();
            //线程池
            ThreadPoolExecutor threadPoolExecutor=taskExecutor.getThreadPoolExecutor();
            //lst 队列任务
            BlockingQueue<Runnable> queue= threadPoolExecutor.getQueue();
            //赋值线程前缀
            taskExecutor.setThreadNamePrefix(type);
            int  maxSize=threadPoolExecutor.getMaximumPoolSize()+queue.size();
            double countSize = bdcXmList.size();
            //lst 一个线程的最大值设置为100  充分利用最大线程数去执行 提高速度
            double limitSize= Math.floor(countSize / maxSize);
            limitSize = limitSize > 100 ? 100 : (limitSize < 1 ? 1 : limitSize);
            double length = Math.ceil(countSize / limitSize);
            //存储执行的任务
            List<Future> futureList=new ArrayList<>();
            List<BdcXmDO> excuteBdcXm;
            for (int i = 0; i < length; i++) {
                double start = i * limitSize;
                double end = start + limitSize;
                if (i == length - 1) {
                    end = countSize;
                }
                excuteBdcXm = bdcXmList.subList((int)start,(int)end);
                //增加任务
                RuleThread ruleThread = (RuleThread) Container.getBean("ruleThread");
                ruleThread.setBdcXmLst(excuteBdcXm);
                ruleThread.setName("线程" +type+ i);
                ruleThread.setGzxxLst(gzxx);
                ruleThread.setEntityMapper(entityMapper);
                ruleThread.setBdcXmMapper(bdcXmMapper);
                ruleThread.setLogList(list);
                futureList.add(taskExecutor.submit(ruleThread));
            }
            //重检线程 不需要等待所有线程结束
            if (StringUtils.equals(type, Constants.THREAD_PREFIX_QUARTZ) || StringUtils.equals(type, Constants.THREAD_PREFIX_PLAN) || StringUtils.equals(type, Constants.THREAD_PREFIX_ZL)) {
                shutDownThread(futureList);
            }
        } else {
            return Collections.emptyList();
        }
        return list;
    }


    private List<CheckGzjcLogDO> excuteAllThread(String type, List<CheckGzxxDO> gzxx, Map paramMap, List<BdcXmDO> bdcXmDOList) {
        logger.warn("当前定时任务扫描全库数据开始，检查数据量{}", CollectionUtils.size(bdcXmDOList));
        List<CheckGzjcLogDO> list = null;
        list = new CopyOnWriteArrayList<>();
        taskExecutor.setThreadNamePrefix(type);
        List<Future> futureList = new ArrayList<>();
        //增加任务
        CheckAllXmThread ruleThread = (CheckAllXmThread) Container.getBean("checkAllXmThread");
        ruleThread.setQueryMap(paramMap);
        ruleThread.setBdcXmLst(bdcXmDOList);
        ruleThread.setName("线程" + type + "全库扫描");
        ruleThread.setGzxxLst(gzxx);
        ruleThread.setEntityMapper(entityMapper);
        ruleThread.setBdcXmMapper(bdcXmMapper);
        ruleThread.setLogList(list);
        futureList.add(taskExecutor.submit(ruleThread));
        for (Future future : futureList) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                logger.error("规则全库扫描数据发生异常", e);
            }
        }
        //重检线程 不需要等待所有线程结束
        if (StringUtils.equals(type, Constants.THREAD_PREFIX_QUARTZ) || StringUtils.equals(type, Constants.THREAD_PREFIX_PLAN) || StringUtils.equals(type, Constants.THREAD_PREFIX_ZL)) {
            shutDownThread(futureList);
        }
        logger.warn("当前定时任务扫描全库数据结束，检查数据量{}", CollectionUtils.size(bdcXmDOList));
        return list;
    }

    private void shutDownThread(List<Future> list) {
        //线程池
        ThreadPoolExecutor threadPoolExecutor = taskExecutor.getThreadPoolExecutor();
        //lst 队列任务
        BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
        while (true) {
            int count = threadPoolExecutor.getActiveCount() + queue.size();
            if(count == 0){
                for(Future future:list){
                    //没取消也没完成的时候
                    while(!future.isDone() && !future.isCancelled()){
                        logger.info("线程池还有在工作的线程");
                        try {
                            Thread.sleep(200L);
                        } catch (Exception e) {
                            logger.error(null,e);
                        }
                    }
                }
                //lst 线程池bean是单例的  shutdown后下次任务无法新增线程
                break;
            } else {
                logger.info("线程池尚在工作中，当前触发{}个线程",count );
                try {
                    Thread.sleep(1000L);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    public String getJcqk(){
        String msg = "当前无任务在执行！";
        ThreadPoolExecutor threadPoolExecutor=taskExecutor.getThreadPoolExecutor();
        if(threadPoolExecutor.getActiveCount()>0){
            String name = taskExecutor.getThreadNamePrefix();
            if(StringUtils.equals(name,Constants.THREAD_PREFIX_QUARTZ)){
                msg="定时检测任务正在运行!";
            }else if(StringUtils.equals(name,Constants.THREAD_PREFIX_RETEST)){
                msg="重检任务正在运行!";
            }else if(StringUtils.equals(name,Constants.THREAD_PREFIX_TIMES)){
                msg="时段检测正在运行!";
            }else if(StringUtils.equals(name,Constants.THREAD_PREFIX_RULES)){
                msg="规则检测正在运行!";
            }else if(StringUtils.equals(name,Constants.THREAD_PREFIX_PLAN)){
                msg="计划检测正在运行!";
            }else if(StringUtils.equals(name,Constants.THREAD_PREFIX_ZL)){
                msg="增量数据定时检测任务正在运行!";
            }
        }
        return msg;
    }

    /**
     * 记录运行日志
     * @param beginTime  开始时间
     * @param xmsl 根据时间查出的项目数量
     * @param bjxmsl  查询未办结中已经办结的项目的数量
     * @param wtsl  问题数量
     */
    private void saveCheckLog(Date beginTime,int xmsl,int bjxmsl,int wtsl,String type){
        CheckLogDO checkLogDO=new CheckLogDO();
        checkLogDO.setLogid(UUIDGenerator.generate());
        checkLogDO.setJcxmsl(xmsl);
        checkLogDO.setJcbjxmsl(bjxmsl);
        checkLogDO.setQssj(beginTime);
        checkLogDO.setJssj(new Date());
        checkLogDO.setWtsl(wtsl);
        checkLogDO.setType(type);
        entityMapper.insertSelective(checkLogDO);
    }

    /**
     * 更新检查计划
     * @param checkPlanDO
     */
    private void updateCheckPlan(CheckPlanDO checkPlanDO){
        checkPlanDO.setDqjccs((checkPlanDO.getDqjccs()==null?0:checkPlanDO.getDqjccs())+1);
        checkPlanDO.setDqjcsj(new Date());
        entityMapper.updateByPrimaryKey(checkPlanDO);
    }

}
