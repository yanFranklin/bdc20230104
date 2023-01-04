package cn.gtmap.realestate.supervise.task;

import cn.gtmap.gtc.workflow.clients.manage.ProcessInstanceClient;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.clients.statistics.ProcStatisticsClient;
import cn.gtmap.gtc.workflow.clients.statistics.WorkDayUtilClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.gtc.workflow.domain.statistics.BetweenWorkDayDto;
import cn.gtmap.gtc.workflow.vo.FastHandleTaskQueryCondition;
import cn.gtmap.gtc.workflow.vo.NonWorkDayQueryCondition;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.supervise.core.domain.*;
import cn.gtmap.realestate.supervise.core.mapper.LfDczjjgMapper;
import cn.gtmap.realestate.supervise.core.mapper.LfYcbjyjMapper;
import cn.gtmap.realestate.supervise.core.qo.LfBdcXmQO;
import cn.gtmap.realestate.supervise.core.qo.LfZjxxQO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 廉防系统同步大云工作流异常办件统计数据定时任务服务
 * @version V1.0, 2021/09/23 10:05
 * @author <a href="mailto:zhuyong@gmail.com">zhuyong</a>
 */
@Component
public class LfScheduledTaskService {
    private static Logger logger = LoggerFactory.getLogger(LfScheduledTaskService.class);

    private static DecimalFormat df = new DecimalFormat("#.00");

    // 非工作时间办件统计默认工作流节点
    private static final String LCTJJD = "受理,审核,初审,复审,登簿,发证";

    @Autowired
    private ProcStatisticsClient procStatisticsClient;

    @Autowired
    private WorkDayUtilClient workDayUtilClient;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private ProcessInstanceClient processInstanceClient;

    @Autowired
    private LfYcbjyjMapper lfYcbjyjMapper;

    @Autowired
    private LfDczjjgMapper lfDczjjgMapper;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    /**
     * 存量数据统计开始时间
     */
    @Value("${inventoryData.syncTime:2021-01-01 00:00:00}")
    private String syncTime;

    @Value("${fgzsj.beforeTime:08:00:00}")
    private String fgzsjBefore;

    @Value("${fgzsj.afterTime:19:00:00}")
    private String fgzsjAfter;

    @Value("${fgzsj.middleTime:13:00:00-14:00:00}")
    private String fgzsjMiddle;


    /**
     * 同步流程超期办件数据（每次同步当天数据）
     */
    @Scheduled(cron = "${everyDay.syncTime:0 0 23 * * ?}")
    public void synchronousOverdueItemsDataTask() {
        // 同步超期办件数据
        synchronousOverdueItemsData();
        // 同步超快办件数据
        synchronousFastProcessData();
        // 同步非工作日时间办件
        synchronousNonWorkDayData();

        // 更新项目数据
        updateBdcXmSj();
    }

    /**
     * 质检台账月度质检,每个月结束后，7天内（可配置），必须上传质检报告
     */
    @Scheduled(cron = "${zjtz.ydzjTime:0 0 0 8 1/1 ?}")
    public void zjtzMonthTask() {
        LfZjxxQO lfZjxxQO = new LfZjxxQO();
        // 1：随机抽查 2：月度质检 3：季度质检
        lfZjxxQO.setZjlx("2");
        lfZjxxQO.setZjsjq(DateUtils.getLastMonthFirstDay(new Date()));
        lfZjxxQO.setZjsjz(DateUtils.formateYmdhms(new Date()));
        List<BdcLfDczjjgZjxxDO> list = lfDczjjgMapper.listZjxxWithoutFj(lfZjxxQO);
        logger.info("月度质检定时任务,开始时间：{},结束时间：{},查询没有上传质检报告的质检信息：{}", DateUtils.getLastMonthFirstDay(new Date())
                ,new Date(),list.toString());
        // 有质检记录但没有上传质检报告
        if (CollectionUtils.isNotEmpty(list)){
            for (BdcLfDczjjgZjxxDO zjxxDO: list) {
                // 没有按时上传质检文件
                zjxxDO.setSfassczjwj("false");
            }
            entityMapper.batchSaveSelective(list);
            logger.info("月度质检,成功标记没有按时上传质检文件的质检信息：{}", list.toString());
        } else{
            // 没有质检记录，增加一条质检记录
            BdcLfDczjjgZjxxDO zjxxDO = new BdcLfDczjjgZjxxDO();
            zjxxDO.setId(UUIDGenerator.generate16());
            zjxxDO.setZjlx(2);
            zjxxDO.setZjsj(new Date());
            zjxxDO.setSfassczjwj("false");
            entityMapper.insertSelective(zjxxDO);
            logger.info("月度质检,自动生成一条质检信息并标记：{}", zjxxDO.toString());
        }
    }

    /**
     * 质检台账季度质检,每个季度结束后，10天内（可配置），必须上传质检报告
     */
    @Scheduled(cron = "${zjtz.jdzjTime:0 0 0 11 1/3 ?}")
    public void zjtzSeasonTask() {
        LfZjxxQO lfZjxxQO = new LfZjxxQO();
        // 1：随机抽查 2：月度质检 3：季度质检
        lfZjxxQO.setZjlx("3");
        lfZjxxQO.setZjsjq(DateUtils.getLastSeasonStartTime(new Date()));
        lfZjxxQO.setZjsjz(DateUtils.formateYmdhms(new Date()));
        List<BdcLfDczjjgZjxxDO> list = lfDczjjgMapper.listZjxxWithoutFj(lfZjxxQO);
        logger.info("季度质检定时任务,开始时间：{},结束时间：{},查询没有上传质检报告的质检信息：{}", DateUtils.getLastSeasonStartTime(new Date())
                ,new Date(),list.toString());
        // 有质检记录但没有上传质检报告
        if (CollectionUtils.isNotEmpty(list)){
            for (BdcLfDczjjgZjxxDO zjxxDO: list) {
                // 没有按时上传质检文件
                zjxxDO.setSfassczjwj("false");
            }
            entityMapper.batchSaveSelective(list);
            logger.info("季度质检,成功标记没有按时上传质检文件的质检信息：{}", list.toString());
        } else{
            // 没有质检记录，增加一条质检记录
            BdcLfDczjjgZjxxDO zjxxDO = new BdcLfDczjjgZjxxDO();
            zjxxDO.setId(UUIDGenerator.generate16());
            zjxxDO.setZjlx(3);
            zjxxDO.setZjsj(new Date());
            zjxxDO.setSfassczjwj("false");
            entityMapper.insertSelective(zjxxDO);
            logger.info("季度质检,自动生成一条质检信息并标记：{}", zjxxDO.toString());
        }
    }

    /**
     * 同步超期办件统计数据
     */
    private void synchronousOverdueItemsData() {
        // 获取同步数据范围开始时间，如果没有时间同步过则按照设置的时间同步，例如今年以来的数据；如果同步过，则同步上次同步后的时间段
        Date newestSyncTime = lfYcbjyjMapper.queryOverdueNewestSyncDataTime();
        String startTime = syncTime;
        if(null != newestSyncTime) {
            startTime = DateFormatUtils.format(newestSyncTime, DateUtils.sdf_ymdhms);
        }
        // 查询登簿项目
        LfBdcXmQO bdcXmQO = new LfBdcXmQO();
        bdcXmQO.setDjkssj(startTime);
        bdcXmQO.setDjjssj(DateFormatUtils.format(new Date(), DateUtils.sdf_ymdhms));
        List<BdcXmDO> bdcXmDOList = lfYcbjyjMapper.queryBdcXmByDjsj(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)) {
            logger.info("同步超期办件统计数据为空，统计范围开始时间：{}", startTime);
            return;
        }
        int num = 0;
        for(BdcXmDO bdcXmDO: bdcXmDOList) {
            List<BetweenWorkDayDto> querylist = new ArrayList<>();
            BetweenWorkDayDto cqscQO = new BetweenWorkDayDto();
            cqscQO.setProcessInsId(bdcXmDO.getGzlslid());
            cqscQO.setStartTime(DateFormatUtils.format(bdcXmDO.getSlsj(), DateUtils.sdf_ymdhms));
            cqscQO.setEndTime(DateFormatUtils.format(bdcXmDO.getDjsj(), DateUtils.sdf_ymdhms));
            // 按分钟计算
            cqscQO.setComputeMode(3);
            // 根据流程启动人员的工作日进行计算
            cqscQO.setProcessStartUser(bdcXmDO.getSlr());
            cqscQO.setCalculationType("processStartUser");
            querylist.add(cqscQO);
            // 计算超期时间,一个一个查
            try {
                logger.info("查询大云接口计算超期办件的超期时长，工作流实例id：{}，开始时间：{}，登记时间：{}", bdcXmDO.getGzlslid(), DateFormatUtils.format(bdcXmDO.getSlsj(), DateUtils.sdf_ymdhms),DateFormatUtils.format(bdcXmDO.getDjsj(), DateUtils.sdf_ymdhms));
                List<BetweenWorkDayDto> list = workDayUtilClient.batchGetBetweenDays(querylist);
                logger.info("查询大云接口计算超期办件的超期时长成功，返回参数为：{}", list.toString());
                if (CollectionUtils.isEmpty(list)){
                    logger.info("查询大云接口计算超期办件的超期时长返回值为空");
                    continue;
                }
                boolean isOverdue = list.get(0).isOverdue();
                if (isOverdue){
                    num += this.saveOverdueData(bdcXmDO,list.get(0));
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("查询大云接口超期办件时长失败");
            }
        }
        logger.info("保存超期办件统计数据{}条，统计范围开始时间：{}", num, startTime);
    }

    /**
     * 同步超快办件统计数据
     */
    private void synchronousFastProcessData() {
        // 获取同步数据范围开始时间，如果没有时间同步过则按照设置的时间同步，例如今年以来的数据；如果同步过，则同步上次同步后的时间段
        Date newestSyncTime = lfYcbjyjMapper.queryFastProcessNewestSyncDataTime();
        String startTime = null != newestSyncTime ? DateFormatUtils.format(newestSyncTime, DateUtils.sdf_ymdhms) : syncTime;
        // 查询登簿项目
        LfBdcXmQO bdcXmQO = new LfBdcXmQO();
        bdcXmQO.setDjkssj(startTime);
        bdcXmQO.setDjjssj(DateFormatUtils.format(new Date(), DateUtils.sdf_ymdhms));
        List<BdcXmDO> bdcXmDOList = lfYcbjyjMapper.queryBdcXmByDjsj(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            logger.info("同步超快办件统计数据为空，统计范围开始时间：{}", startTime);
            return;
        }
        int num = 0;
        for (BdcXmDO bdcXmDO : bdcXmDOList) {
            List<BetweenWorkDayDto> querylist = new ArrayList<>();
            BetweenWorkDayDto ckscQO = new BetweenWorkDayDto();
            ckscQO.setProcessInsId(bdcXmDO.getGzlslid());
            ckscQO.setStartTime(DateFormatUtils.format(bdcXmDO.getSlsj(), DateUtils.sdf_ymdhms));
            ckscQO.setEndTime(DateFormatUtils.format(bdcXmDO.getDjsj(), DateUtils.sdf_ymdhms));
            // 按分钟计算
            ckscQO.setComputeMode(3);
            // 根据流程启动人员的工作日进行计算
            ckscQO.setProcessStartUser(bdcXmDO.getSlr());
            ckscQO.setCalculationType("processStartUser");
            querylist.add(ckscQO);
            // 计算超快时间,一个一个查
            try {
                logger.info("查询大云接口计算超快办件的超快时长，工作流实例id：{}，开始时间：{}，登记时间：{}", bdcXmDO.getGzlslid(),DateFormatUtils.format(bdcXmDO.getSlsj(), DateUtils.sdf_ymdhms), DateFormatUtils.format(bdcXmDO.getDjsj(), DateUtils.sdf_ymdhms));
                List<BetweenWorkDayDto> list = workDayUtilClient.batchGetBetweenDays(querylist);
                if (CollectionUtils.isEmpty(list)) {
                    logger.info("查询大云接口计算超快办件的超快时长返回值为空");
                    continue;
                }
                if (list.get(0).getTimes() == 0){
                    logger.info("非工作时间办件的数据不统计");
                    continue;
                }
                // 查询超快时长配置表，获取配置的超快时长（分钟）
                Example example = new Example(BdcLfYcbjyjPzDO.class);
                example.createCriteria().andEqualTo("gzldykey", bdcXmDO.getGzldyid());
                List<BdcLfYcbjyjPzDO> pzList = entityMapper.selectByExampleNotNull(example);
                if (CollectionUtils.isEmpty(pzList)) {
                    logger.info("未设定该流程{}的超快办件时长，不进行统计！", bdcXmDO.getGzldyid());
                    continue;
                }
                Double ckbjsc = pzList.get(0).getCkbjsc();
                Double bjsc = list.get(0).getTimes().doubleValue();
                //ckbjsc单位是分钟,bjsc单位是分钟
                boolean isExistFastData = bjsc < ckbjsc ? true : false;
                logger.info("超快办件的时长标准为：{}分钟，实际办件时长为：{}分钟,是否超快办件：{}",ckbjsc,bjsc,isExistFastData);
                if (isExistFastData) {
                    num += this.saveFastProcessData(bdcXmDO, ckbjsc, bjsc);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("查询大云接口超快办件时长失败");
            }
        }
        logger.info("保存超快办件统计数据{}条，统计范围开始时间：{}", num, startTime);
    }

    /**
     * 同步非工作时间办件数据
     */
    private void synchronousNonWorkDayData() {
        List<BdcLfYcbjyjPzDO> pzList = entityMapper.select(new BdcLfYcbjyjPzDO());
        if(CollectionUtils.isEmpty(pzList)) {
            logger.error("未设定每个流程统计节点，无法统计！");
            return;
        }

        // 获取同步数据范围开始时间，如果没有时间同步过则按照设置的时间同步，例如今年以来的数据；如果同步过，则同步上次同步后的时间段
        NonWorkDayQueryCondition condition = new NonWorkDayQueryCondition();
        Date newestSyncTime = lfYcbjyjMapper.queryNonWorkDayNewestSyncDataTime();
        String startTime = null != newestSyncTime ? DateFormatUtils.format(newestSyncTime, DateUtils.sdf_ymdhms) : syncTime;
        condition.setStartTime(startTime);

        int num = 0;
        for(BdcLfYcbjyjPzDO pzxx : pzList) {
            if(StringUtils.isBlank(pzxx.getGzldykey())) {
                continue;
            }

            String nodeName = StringUtils.isBlank(pzxx.getLctjjd()) ? LCTJJD : pzxx.getLctjjd();
            condition.setNodeName(Arrays.asList(nodeName.split(CommonConstantUtils.ZF_YW_DH)));
            Page<Map<String, Object>> nonWorkDayData = procStatisticsClient.pageNonWorkDayHandleTask(condition, Integer.MAX_VALUE, 0);
            if(null == nonWorkDayData || CollectionUtils.isEmpty(nonWorkDayData.getContent())) {
                continue;
            }

            for(Map<String, Object> dataItem : nonWorkDayData.getContent()) {
                boolean isExistNonWorkDayData = this.isExistNonWorkDayData(dataItem);
                if(!isExistNonWorkDayData) {
                    String taskStartTime = String.valueOf(dataItem.get("TASKSTARTTIME"));
                    String taskEndTime = String.valueOf(dataItem.get("TASKENDTIME"));
                    if (check(taskStartTime) || check(taskEndTime)) {
                        num += this.saveNonWorkDayData(dataItem);
                    }
                }
            }
        }
        logger.info("保存非工作时间办件统计数据{}条，统计范围开始时间：{}", num, startTime);
    }

    /**
     * 当前超期办件数据是否已经存在
     * @param overdueData 超期办件数据
     * @return {boolean} true:已存在；false：不存在
     */
    private boolean isExistOverdueData(Map<String, Object> overdueData) {
        Example example = new Example(BdcLfYcbjyjCqbjDO.class);
        example.createCriteria().andEqualTo("gzlslid", overdueData.get("PROCESSINSID"))
                .andEqualTo("dqjdmc", overdueData.get("NODENAME"));
        List data = entityMapper.selectByExampleNotNull(example);

        return CollectionUtils.isNotEmpty(data);
    }

    /**
     * 保存超期办件数据到廉防系统
     * @param bdcXmDO 项目信息
     * @param workDayDto 大云返回数据
     * @return {int} 新增记录数量
     */
    private int saveOverdueData(BdcXmDO bdcXmDO, BetweenWorkDayDto workDayDto) {
        BdcLfYcbjyjCqbjDO cqbjDO = new BdcLfYcbjyjCqbjDO();
        cqbjDO.setId(UUIDGenerator.generate16());
        // 工作流定义id和工作流定义名称读取大云
        ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(bdcXmDO.getGzlslid());
        cqbjDO.setGzldyid(processInstanceData.getProcessDefinitionKey());
        cqbjDO.setGzldymc(processInstanceData.getProcessDefinitionName());
        cqbjDO.setGzlslid(bdcXmDO.getGzlslid());
        // 当前节点名称
        List<TaskData>  list = processTaskClient.processLastTasks(bdcXmDO.getGzlslid());
        cqbjDO.setDqjdmc(list.get(0).getTaskName());
        // 工作流超期时间
        cqbjDO.setGzlcqsj(DateFormatUtils.format(bdcXmDO.getDjsj(), DateUtils.sdf_ymdhms));
        // 流程启动者
        cqbjDO.setLcqdz(workDayDto.getProcessStartUser());
        // 流程相关人员
        cqbjDO.setLcxgry(workDayDto.getRelatedUser());
        // 工作流到期时间
        cqbjDO.setGzldqsj(DateFormatUtils.format(workDayDto.getDueTime(),DateUtils.sdf_ymdhms));
        cqbjDO.setSjtbsj(new Date());
        // 超期时长
        cqbjDO.setGzlcqsc(DateUtils.exchangeMin(workDayDto.getOverdueTime()));
        return entityMapper.insertSelective(cqbjDO);

    }

    /**
     * 当前超快办件数据是否已经存在
     * @param fastData 超期办件数据
     * @return {boolean} true:已存在；false：不存在
     */
    private boolean isExistFastProcessData(Map<String, Object> fastData) {
        Example example = new Example(BdcLfYcbjyjCkbjDO.class);
        example.createCriteria().andEqualTo("gzlslid", fastData.get("PROCINSID"));
        List data = entityMapper.selectByExampleNotNull(example);
        return CollectionUtils.isNotEmpty(data);
    }

    /**
     * 保存超快办件数据到廉防系统
     * @param bdcXmDO 项目信息
     * @param ckscpz 超快办件时长标准
     * @param bjsc 办件时长
     * @return {int} 新增记录数量
     */
    private int saveFastProcessData(BdcXmDO bdcXmDO,Double ckscpz,Double bjsc) {
        BdcLfYcbjyjCkbjDO ckbj = new BdcLfYcbjyjCkbjDO();
        ckbj.setId(UUIDGenerator.generate16());
        // 工作流定义名称
        ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(bdcXmDO.getGzlslid());
        ckbj.setGzldymc(processInstanceData.getProcessDefinitionName());
        // 工作流定义id
        ckbj.setGzldyid(processInstanceData.getProcessDefinitionKey());
        // 工作流实例id
        ckbj.setGzlslid(String.valueOf(bdcXmDO.getGzlslid()));
        // 流程开始时间
        ckbj.setLckssj(DateFormatUtils.format(bdcXmDO.getSlsj(),DateUtils.sdf_ymdhms));
        // 流程结束时间
        ckbj.setLcjssj(DateFormatUtils.format(bdcXmDO.getDjsj(), DateUtils.sdf_ymdhms));
        // 超快办件时长标准
        ckbj.setCkbjscbz(ckscpz);
        // 数据同步时间
        ckbj.setSjtbsj(new Date());
        // 办件时长
        ckbj.setBjsc(bjsc);
        return entityMapper.insertSelective(ckbj);
    }

    /**
     * 当前非工作时间办件数据是否已经存在
     * @param dataItem 非工作时间办件数据
     * @return {boolean} true:已存在；false：不存在
     */
    private boolean isExistNonWorkDayData(Map<String, Object> dataItem) {
        Example example = new Example(BdcLfYcbjyjFgzsjbjDO.class);
        example.createCriteria().andEqualTo("gzlslid", dataItem.get("PROCESSINSID"))
                .andEqualTo("jdmc", dataItem.get("TASKNAME"));
        List data = entityMapper.selectByExampleNotNull(example);

        return CollectionUtils.isNotEmpty(data);
    }

    /**
     * 保存非工作时间办件数据到廉防系统
     * @param nonWorkDayData 非工作时间办件数据
     * @return {int} 新增记录数量
     */
    private int saveNonWorkDayData(Map<String, Object> nonWorkDayData) {
        BdcLfYcbjyjFgzsjbjDO data = new BdcLfYcbjyjFgzsjbjDO();
        data.setId(UUIDGenerator.generate16());
        data.setGzldyid(String.valueOf(nonWorkDayData.get("PROCESSDEFKEY")));
        data.setGzldymc(String.valueOf(nonWorkDayData.get("PROCESSDEFNAME")));
        data.setGzlslid(String.valueOf(nonWorkDayData.get("PROCESSINSID")));
        data.setGzlslmc(String.valueOf(nonWorkDayData.get("PROCESSINSNAME")));
        data.setJdkssj(String.valueOf(nonWorkDayData.get("TASKSTARTTIME")));
        data.setJdjssj(String.valueOf(nonWorkDayData.get("TASKENDTIME")));
        data.setJdmc(String.valueOf(nonWorkDayData.get("TASKNAME")));
        data.setJdbjr(String.valueOf(nonWorkDayData.get("TASKASSIGNEE")));
        data.setSjtbsj(new Date());
        logger.info("保存非工作时间办件数据,数据为:{}",data.toString());
        return entityMapper.insertSelective(data);
    }

    /**
     * 同步项目数据到超期、超快办件表
     */
    private void updateBdcXmSj() {
        // 更新数据到超期办件表
        lfYcbjyjMapper.updateBdcXmSjToCqbj();
        // 更新数据到超快办件表
        lfYcbjyjMapper.updateBdcXmSjToCkbj();
        // 更新数据到非工作日办件表
        lfYcbjyjMapper.updateBdcXmSjToFgzrbj();
        logger.info("更新表中数据完成");
    }
    /**
     * 判断数据的时间是否是非工作时间,true:是“非工作时间”
     */
    private boolean check(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return false;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 截取日期的年月日,2022-02-21
        String date = dateStr.substring(0, 10);
        // 非工作时间
        String beginTime = date + " " + fgzsjBefore;
        String endTime = date + " " + fgzsjAfter;
        String[] middleTime = fgzsjMiddle.split("-");
        String middleTime1 = date + " " + middleTime[0];
        String middleTime2 = date + " " + middleTime[1];
        long long1 = 0;
        long long2 = 0;
        long long3 = 0;
        long long4 = 0;
        long long5 = 0;
        try {
            long1 = df.parse(beginTime).getTime();
            long2 = df.parse(endTime).getTime();
            long3 = df.parse(dateStr).getTime();
            long4 = df.parse(middleTime1).getTime();
            long5 = df.parse(middleTime2).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("日期格式转换异常");
        }
        return (long3 < long1 || long3 > long2 || (long3 > long4 && long3 < long5));
    }
}
