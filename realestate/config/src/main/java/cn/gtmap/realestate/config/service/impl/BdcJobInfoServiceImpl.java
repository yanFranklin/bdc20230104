package cn.gtmap.realestate.config.service.impl;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDTO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobInfoDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobRegistryDO;
import cn.gtmap.realestate.common.core.dto.ReturnT;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.job.enums.ExecutorBlockStrategyEnum;
import cn.gtmap.realestate.common.job.enums.RegistryConfig;
import cn.gtmap.realestate.common.job.glue.GlueTypeEnum;
import cn.gtmap.realestate.common.job.util.DateUtil;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.config.core.mapper.*;
import cn.gtmap.realestate.config.core.service.impl.XxlJobServiceImpl;
import cn.gtmap.realestate.config.job.cron.CronExpression;
import cn.gtmap.realestate.config.job.route.ExecutorRouteStrategyEnum;
import cn.gtmap.realestate.config.job.scheduler.MisfireStrategyEnum;
import cn.gtmap.realestate.config.job.scheduler.ScheduleTypeEnum;
import cn.gtmap.realestate.config.job.thread.JobScheduleHelper;
import cn.gtmap.realestate.config.job.thread.JobTriggerPoolHelper;
import cn.gtmap.realestate.config.job.trigger.TriggerTypeEnum;
import cn.gtmap.realestate.config.job.util.I18nUtil;
import cn.gtmap.realestate.config.service.BdcJobGroupService;
import cn.gtmap.realestate.config.service.BdcJobInfoService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.*;


/**
 * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
 * @version 1.0, 2022/01/01 15:12
 * @description jobInfo实现类
 */
@Slf4j
@Service
public class BdcJobInfoServiceImpl implements BdcJobInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcJobGroupService.class);

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private Repo repo;
    @Autowired
    private BdcJobGroupMapper bdcJobGroupMapper;
    @Autowired
    public BdcJobInfoMapper bdcJobInfoMapper;
    @Resource
    public BdcJobLogMapper bdcJobLogMapper;
    @Resource
    private BdcJobLogGlueMapper bdcJobLogGlueMapper;
    @Resource
    private BdcJobLogReportMapper bdcJobLogReportMapper;
    @Autowired
    private BdcJobRegistryMapper bdcJobRegistryMapper;

    /**
     * 分页获取任务数据列表
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param pageable pageable
     * @param bdcJobInfoDO
     * @return JobGroup
     */
    @Override
    public Page<BdcJobInfoDO> listBdcJobInfoPage(Pageable pageable, BdcJobInfoDO bdcJobInfoDO) {
        return repo.selectPaging("listBdcJobInfoByPage", bdcJobInfoDO,pageable);
    }

    /**
     * 添加任务
     *
     * @param bdcJobInfoDO
     * @return Object Object
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public ReturnT<String> addJobnfo(BdcJobInfoDO bdcJobInfoDO) {

        // valid base 验证基础的有效性
        BdcJobGroupDTO group = bdcJobGroupMapper.load(bdcJobInfoDO.getJobGroup());
        //请选择执行器
        if (group == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_choose")+I18nUtil.getString("jobinfo_field_jobgroup")) );
        }
        //请输入任务描述
        if (bdcJobInfoDO.getJobDesc()==null || bdcJobInfoDO.getJobDesc().trim().length()==0) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_input")+I18nUtil.getString("jobinfo_field_jobdesc")) );
        }
        //请输入任务负责人（作者）
        if (bdcJobInfoDO.getAuthor()==null || bdcJobInfoDO.getAuthor().trim().length()==0) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_input")+I18nUtil.getString("jobinfo_field_author")) );
        }

        // valid trigger
        //调度类型 合法性检查
        ScheduleTypeEnum scheduleTypeEnum = ScheduleTypeEnum.match(bdcJobInfoDO.getScheduleType(), null);
        if (scheduleTypeEnum == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type")+I18nUtil.getString("system_unvalid")) );
        }
        if (scheduleTypeEnum == ScheduleTypeEnum.CRON) {
            if (bdcJobInfoDO.getScheduleConf()==null || !CronExpression.isValidExpression(bdcJobInfoDO.getScheduleConf())) {
                return new ReturnT<String>(ReturnT.FAIL_CODE, "Cron"+I18nUtil.getString("system_unvalid"));
            }
        } else if (scheduleTypeEnum == ScheduleTypeEnum.FIX_RATE/* || scheduleTypeEnum == ScheduleTypeEnum.FIX_DELAY*/) {
            if (bdcJobInfoDO.getScheduleConf() == null) {
                return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type")) );
            }
            try {
                int fixSecond = Integer.valueOf(bdcJobInfoDO.getScheduleConf());
                if (fixSecond < 1) {
                    return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type")+I18nUtil.getString("system_unvalid")) );
                }
            } catch (Exception e) {
                return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type")+I18nUtil.getString("system_unvalid")) );
            }
        }

        // valid job
        if (GlueTypeEnum.match(bdcJobInfoDO.getGlueType()) == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_gluetype")+I18nUtil.getString("system_unvalid")) );
        }
        if (GlueTypeEnum.BEAN==GlueTypeEnum.match(bdcJobInfoDO.getGlueType()) && (bdcJobInfoDO.getExecutorHandler()==null || bdcJobInfoDO.getExecutorHandler().trim().length()==0) ) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_input")+"JobHandler") );
        }
        // 》fix "\r" in shell
        if (GlueTypeEnum.GLUE_SHELL==GlueTypeEnum.match(bdcJobInfoDO.getGlueType()) && bdcJobInfoDO.getGlueSource()!=null) {
            bdcJobInfoDO.setGlueSource(bdcJobInfoDO.getGlueSource().replaceAll("\r", ""));
        }

        // valid advanced
        bdcJobInfoDO.setExecutorRouteStrategy("FIRST");
        if (ExecutorRouteStrategyEnum.match(bdcJobInfoDO.getExecutorRouteStrategy(), null) == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_executorRouteStrategy")+I18nUtil.getString("system_unvalid")) );
        }
        bdcJobInfoDO.setMisfireStrategy("DO_NOTHING");
        if (MisfireStrategyEnum.match(bdcJobInfoDO.getMisfireStrategy(), null) == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("misfire_strategy")+I18nUtil.getString("system_unvalid")) );
        }
        bdcJobInfoDO.setExecutorBlockStrategy("SERIAL_EXECUTION");
        if (ExecutorBlockStrategyEnum.match(bdcJobInfoDO.getExecutorBlockStrategy(), null) == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_executorBlockStrategy")+I18nUtil.getString("system_unvalid")) );
        }

        // 》ChildJobId valid
        if (bdcJobInfoDO.getChildJobId()!=null && bdcJobInfoDO.getChildJobId().trim().length()>0) {
            String[] childJobIds = bdcJobInfoDO.getChildJobId().split(",");
            for (String childJobIdItem: childJobIds) {
                if (childJobIdItem!=null && childJobIdItem.trim().length()>0 && isNumeric(childJobIdItem)) {
                    BdcJobInfoDO childBdcJobInfoDO = bdcJobInfoMapper.loadById(Integer.parseInt(childJobIdItem));
                    if (childBdcJobInfoDO ==null) {
                        return new ReturnT<String>(ReturnT.FAIL_CODE,
                                MessageFormat.format((I18nUtil.getString("jobinfo_field_childJobId")+"({0})"+I18nUtil.getString("system_not_found")), childJobIdItem));
                    }
                } else {
                    return new ReturnT<String>(ReturnT.FAIL_CODE,
                            MessageFormat.format((I18nUtil.getString("jobinfo_field_childJobId")+"({0})"+I18nUtil.getString("system_unvalid")), childJobIdItem));
                }
            }

            // join , avoid "xxx,,"
            String temp = "";
            for (String item:childJobIds) {
                temp += item + ",";
            }
            temp = temp.substring(0, temp.length()-1);

            bdcJobInfoDO.setChildJobId(temp);
        }

        // add in db
        bdcJobInfoDO.setAddTime(new Date());
        bdcJobInfoDO.setUpdatetime(new Date());
        bdcJobInfoDO.setGlueUpdatetime(new Date());
        if (!Objects.nonNull(bdcJobInfoDO.getId())) {
            int count = bdcJobInfoMapper.findAllCount();
            bdcJobInfoDO.setId(count + 1);
        }
        bdcJobInfoMapper.save(bdcJobInfoDO);
        if (bdcJobInfoDO.getId() < 1) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_add")+I18nUtil.getString("system_fail")) );
        }

        return new ReturnT<String>(String.valueOf(bdcJobInfoDO.getId()));
    }

    /**
     * 保存任务信息
     *
     * @param bdcJobInfoDO
     * @return bdcJobInfoDO
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public ReturnT<String> saveJobnfo(BdcJobInfoDO bdcJobInfoDO) {
        return this.addJobnfo(bdcJobInfoDO);
    }

    /**
     * 更新任务信息
     *
     * @param bdcJobInfoDO jobGroup
     * @return jobGroup jobGroup
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public ReturnT<String> updateJobnfo(BdcJobInfoDO bdcJobInfoDO) {
        // valid base
        if (bdcJobInfoDO.getJobDesc()==null || bdcJobInfoDO.getJobDesc().trim().length()==0) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_input")+I18nUtil.getString("jobinfo_field_jobdesc")) );
        }
        if (bdcJobInfoDO.getAuthor()==null || bdcJobInfoDO.getAuthor().trim().length()==0) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("system_please_input")+I18nUtil.getString("jobinfo_field_author")) );
        }

        // valid trigger
        ScheduleTypeEnum scheduleTypeEnum = ScheduleTypeEnum.match(bdcJobInfoDO.getScheduleType(), null);
        if (scheduleTypeEnum == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type")+I18nUtil.getString("system_unvalid")) );
        }
        if (scheduleTypeEnum == ScheduleTypeEnum.CRON) {
            if (bdcJobInfoDO.getScheduleConf()==null || !CronExpression.isValidExpression(bdcJobInfoDO.getScheduleConf())) {
                return new ReturnT<String>(ReturnT.FAIL_CODE, "Cron"+I18nUtil.getString("system_unvalid") );
            }
        } else if (scheduleTypeEnum == ScheduleTypeEnum.FIX_RATE /*|| scheduleTypeEnum == ScheduleTypeEnum.FIX_DELAY*/) {
            if (bdcJobInfoDO.getScheduleConf() == null) {
                return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type")+I18nUtil.getString("system_unvalid")) );
            }
            try {
                int fixSecond = Integer.valueOf(bdcJobInfoDO.getScheduleConf());
                if (fixSecond < 1) {
                    return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type")+I18nUtil.getString("system_unvalid")) );
                }
            } catch (Exception e) {
                return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type")+I18nUtil.getString("system_unvalid")) );
            }
        }

        // valid advanced
        bdcJobInfoDO.setExecutorRouteStrategy("FIRST");
        if (ExecutorRouteStrategyEnum.match(bdcJobInfoDO.getExecutorRouteStrategy(), null) == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_executorRouteStrategy")+I18nUtil.getString("system_unvalid")) );
        }
        bdcJobInfoDO.setMisfireStrategy("DO_NOTHING");
        if (MisfireStrategyEnum.match(bdcJobInfoDO.getMisfireStrategy(), null) == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("misfire_strategy")+I18nUtil.getString("system_unvalid")) );
        }
        bdcJobInfoDO.setExecutorBlockStrategy("SERIAL_EXECUTION");
        if (ExecutorBlockStrategyEnum.match(bdcJobInfoDO.getExecutorBlockStrategy(), null) == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_executorBlockStrategy")+I18nUtil.getString("system_unvalid")) );
        }

        // 》ChildJobId valid
        if (bdcJobInfoDO.getChildJobId()!=null && bdcJobInfoDO.getChildJobId().trim().length()>0) {
            String[] childJobIds = bdcJobInfoDO.getChildJobId().split(",");
            for (String childJobIdItem: childJobIds) {
                if (childJobIdItem!=null && childJobIdItem.trim().length()>0 && isNumeric(childJobIdItem)) {
                    BdcJobInfoDO childBdcJobInfoDO = bdcJobInfoMapper.loadById(Integer.parseInt(childJobIdItem));
                    if (childBdcJobInfoDO ==null) {
                        return new ReturnT<String>(ReturnT.FAIL_CODE,
                                MessageFormat.format((I18nUtil.getString("jobinfo_field_childJobId")+"({0})"+I18nUtil.getString("system_not_found")), childJobIdItem));
                    }
                } else {
                    return new ReturnT<String>(ReturnT.FAIL_CODE,
                            MessageFormat.format((I18nUtil.getString("jobinfo_field_childJobId")+"({0})"+I18nUtil.getString("system_unvalid")), childJobIdItem));
                }
            }

            // join , avoid "xxx,,"
            String temp = "";
            for (String item:childJobIds) {
                temp += item + ",";
            }
            temp = temp.substring(0, temp.length()-1);

            bdcJobInfoDO.setChildJobId(temp);
        }

        // group valid
        BdcJobGroupDTO bdcJobGroupDTO = bdcJobGroupMapper.load(bdcJobInfoDO.getJobGroup());
        if (bdcJobGroupDTO == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_jobgroup")+I18nUtil.getString("system_unvalid")) );
        }

        // stage job info
        BdcJobInfoDO exists_Bdc_jobInfoDO = bdcJobInfoMapper.loadById(bdcJobInfoDO.getId());
        if (exists_Bdc_jobInfoDO == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_id")+I18nUtil.getString("system_not_found")) );
        }

        // next trigger time (5s后生效，避开预读周期)
        long nextTriggerTime = exists_Bdc_jobInfoDO.getTriggerNextTime();
        boolean scheduleDataNotChanged = bdcJobInfoDO.getScheduleType().equals(exists_Bdc_jobInfoDO.getScheduleType()) && bdcJobInfoDO.getScheduleConf().equals(exists_Bdc_jobInfoDO.getScheduleConf());
        if (Objects.nonNull(exists_Bdc_jobInfoDO.getTriggerStatus()) && exists_Bdc_jobInfoDO.getTriggerStatus() == 1 && !scheduleDataNotChanged) {
            try {
                Date nextValidTime = JobScheduleHelper.generateNextValidTime(bdcJobInfoDO, new Date(System.currentTimeMillis() + JobScheduleHelper.PRE_READ_MS));
                if (nextValidTime == null) {
                    return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type")+I18nUtil.getString("system_unvalid")) );
                }
                nextTriggerTime = nextValidTime.getTime();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type")+I18nUtil.getString("system_unvalid")) );
            }
        }

        exists_Bdc_jobInfoDO.setJobGroup(bdcJobInfoDO.getJobGroup());
        exists_Bdc_jobInfoDO.setJobDesc(bdcJobInfoDO.getJobDesc());
        exists_Bdc_jobInfoDO.setAuthor(bdcJobInfoDO.getAuthor());
        exists_Bdc_jobInfoDO.setAlarmEmail(bdcJobInfoDO.getAlarmEmail());
        exists_Bdc_jobInfoDO.setScheduleType(bdcJobInfoDO.getScheduleType());
        exists_Bdc_jobInfoDO.setScheduleConf(bdcJobInfoDO.getScheduleConf());
        exists_Bdc_jobInfoDO.setMisfireStrategy(bdcJobInfoDO.getMisfireStrategy());
        exists_Bdc_jobInfoDO.setExecutorRouteStrategy(bdcJobInfoDO.getExecutorRouteStrategy());
        exists_Bdc_jobInfoDO.setExecutorHandler(bdcJobInfoDO.getExecutorHandler());
        exists_Bdc_jobInfoDO.setExecutorParam(bdcJobInfoDO.getExecutorParam());
        exists_Bdc_jobInfoDO.setExecutorBlockStrategy(bdcJobInfoDO.getExecutorBlockStrategy());
        exists_Bdc_jobInfoDO.setExecutorTimeout(bdcJobInfoDO.getExecutorTimeout());
        exists_Bdc_jobInfoDO.setExecutorFailRetryCount(bdcJobInfoDO.getExecutorFailRetryCount());
        exists_Bdc_jobInfoDO.setChildJobId(bdcJobInfoDO.getChildJobId());
        exists_Bdc_jobInfoDO.setTriggerNextTime(nextTriggerTime);

        exists_Bdc_jobInfoDO.setUpdatetime(new Date());
        bdcJobInfoMapper.update(exists_Bdc_jobInfoDO);


        return ReturnT.SUCCESS;
    }

    /**
     * 删除 任务
     *
     * @param id
     * @return ReturnT
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public ReturnT<String> removeJobnfo(Integer id) {
        BdcJobInfoDO bdcJobInfoDO = bdcJobInfoMapper.loadById(id);
        if (bdcJobInfoDO == null) {
            return ReturnT.SUCCESS;
        }

        bdcJobInfoMapper.delete(id);
//        bdcJobLogMapper.delete(id);
//        bdcJobLogGlueMapper.deleteByJobId(id);
        return ReturnT.SUCCESS;
    }

    /**
     * 终止 任务
     *
     * @param id
     * @return ReturnT
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public ReturnT<String> pauseJobnfo(Integer id) {
        BdcJobInfoDO xxlBdcJobInfoDO = bdcJobInfoMapper.loadById(id);

        xxlBdcJobInfoDO.setTriggerStatus(0);
        xxlBdcJobInfoDO.setTriggerLastTime(0);
        xxlBdcJobInfoDO.setTriggerNextTime(0);

        xxlBdcJobInfoDO.setUpdatetime(new Date());
        bdcJobInfoMapper.update(xxlBdcJobInfoDO);
        return ReturnT.SUCCESS;
    }

    /**
     * 启动 任务
     *
     * @param id
     * @return ReturnT
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public ReturnT<String> startJobInfo(Integer id) {
        BdcJobInfoDO xxlBdcJobInfoDO = bdcJobInfoMapper.loadById(id);

        // valid
        ScheduleTypeEnum scheduleTypeEnum = ScheduleTypeEnum.match(xxlBdcJobInfoDO.getScheduleType(), ScheduleTypeEnum.NONE);
        if (ScheduleTypeEnum.NONE == scheduleTypeEnum) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type_none_limit_start")) );
        }

        // next trigger time (5s后生效，避开预读周期)
        long nextTriggerTime = 0;
        try {
            Date nextValidTime = JobScheduleHelper.generateNextValidTime(xxlBdcJobInfoDO, new Date(System.currentTimeMillis() + JobScheduleHelper.PRE_READ_MS));
            if (nextValidTime == null) {
                return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type")+I18nUtil.getString("system_unvalid")) );
            }
            nextTriggerTime = nextValidTime.getTime();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type")+I18nUtil.getString("system_unvalid")) );
        }

        xxlBdcJobInfoDO.setTriggerStatus(1);
        xxlBdcJobInfoDO.setTriggerLastTime(0);
        xxlBdcJobInfoDO.setTriggerNextTime(nextTriggerTime);

        xxlBdcJobInfoDO.setUpdatetime(new Date());
        bdcJobInfoMapper.update(xxlBdcJobInfoDO);
        return ReturnT.SUCCESS;
    }

    /**
     * 执行一次 任务
     *
     * @param id
     * @param executorParam
     * @param addresslist
     * @return ReturnT
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public ReturnT<String> triggerJobInfo(Integer id, String executorParam, String addresslist) {
        // force cover job param
        if (executorParam == null) {
            executorParam = "";
        }

        JobTriggerPoolHelper.trigger(id, TriggerTypeEnum.MANUAL, -1, null, executorParam, addresslist);
        return ReturnT.SUCCESS;
    }

    /**
     * 任务下次执行时间
     *
     * @param scheduleType
     * @param scheduleConf
     * @return ReturnT
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public ReturnT<List<String>> nextTriggerTime(String scheduleType, String scheduleConf) {
        BdcJobInfoDO paramBdcJobInfoDO = new BdcJobInfoDO();
        paramBdcJobInfoDO.setScheduleType(scheduleType);
        paramBdcJobInfoDO.setScheduleConf(scheduleConf);

        List<String> result = new ArrayList<>();
        try {
            Date lastTime = new Date();
            for (int i = 0; i < 5; i++) {
                lastTime = JobScheduleHelper.generateNextValidTime(paramBdcJobInfoDO, lastTime);
                if (lastTime != null) {
                    result.add(DateUtil.formatDateTime(lastTime));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new ReturnT<List<String>>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type")+I18nUtil.getString("system_unvalid")) + e.getMessage());
        }
        return new ReturnT<List<String>>(result);
    }

    /**
     * 根据任务id查询任务信息
     *
     * @param id
     * @return ReturnT
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public ReturnT<BdcJobInfoDO> loadJobInfoById(Integer id) {
//        bdcJobInfoMapper.loadById(id);
        return new ReturnT<>(bdcJobInfoMapper.loadById(id));
    }

    private boolean isNumeric(String str){
        try {
            int result = Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
