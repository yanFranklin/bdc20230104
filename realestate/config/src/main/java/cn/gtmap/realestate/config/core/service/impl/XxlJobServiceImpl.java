package cn.gtmap.realestate.config.core.service.impl;


import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobInfoDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobLogReportDO;
import cn.gtmap.realestate.common.job.enums.ExecutorBlockStrategyEnum;
import cn.gtmap.realestate.common.job.glue.GlueTypeEnum;
import cn.gtmap.realestate.common.job.util.DateUtil;
import cn.gtmap.realestate.config.core.mapper.*;
import cn.gtmap.realestate.config.core.service.XxlJobService;
import cn.gtmap.realestate.config.job.cron.CronExpression;
import cn.gtmap.realestate.config.job.route.ExecutorRouteStrategyEnum;
import cn.gtmap.realestate.config.job.scheduler.MisfireStrategyEnum;
import cn.gtmap.realestate.config.job.scheduler.ScheduleTypeEnum;
import cn.gtmap.realestate.config.job.thread.JobScheduleHelper;
import cn.gtmap.realestate.config.job.util.I18nUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import cn.gtmap.realestate.common.job.biz.model.ReturnT;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.*;

/**
 * core job action for xxl-job
 * @author  2016-5-28 15:30:33
 */
@Service
public class XxlJobServiceImpl implements XxlJobService {
	private static Logger logger = LoggerFactory.getLogger(XxlJobServiceImpl.class);

	@Resource
	private BdcJobGroupMapper bdcJobGroupMapper;
	@Resource
	private BdcJobInfoMapper bdcJobInfoMapper;
	@Resource
	public BdcJobLogMapper bdcJobLogMapper;
	@Resource
	private BdcJobLogGlueMapper bdcJobLogGlueMapper;
	@Resource
	private BdcJobLogReportMapper bdcJobLogReportMapper;
	
	@Override
	public Map<String, Object> pageList(int start, int length, int jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author) {

		// page list
		List<BdcJobInfoDO> list = bdcJobInfoMapper.pageList(start, length, jobGroup, triggerStatus, jobDesc, executorHandler, author);
		int list_count = bdcJobInfoMapper.pageListCount(start, length, jobGroup, triggerStatus, jobDesc, executorHandler, author);
		
		// package result
		Map<String, Object> maps = new HashMap<String, Object>();
	    maps.put("recordsTotal", list_count);		// 总记录数
	    maps.put("recordsFiltered", list_count);	// 过滤后的总记录数
	    maps.put("data", list);  					// 分页列表
		return maps;
	}

	@Override
	public ReturnT<String> add(BdcJobInfoDO bdcJobInfoDO) {

		// valid base
		BdcJobGroupDO group = bdcJobGroupMapper.load(bdcJobInfoDO.getJobGroup());
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
		if (ExecutorRouteStrategyEnum.match(bdcJobInfoDO.getExecutorRouteStrategy(), null) == null) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_executorRouteStrategy")+I18nUtil.getString("system_unvalid")) );
		}
		if (MisfireStrategyEnum.match(bdcJobInfoDO.getMisfireStrategy(), null) == null) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("misfire_strategy")+I18nUtil.getString("system_unvalid")) );
		}
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
		bdcJobInfoMapper.save(bdcJobInfoDO);
		if (bdcJobInfoDO.getId() < 1) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_add")+I18nUtil.getString("system_fail")) );
		}

		return new ReturnT<String>(String.valueOf(bdcJobInfoDO.getId()));
	}

	private boolean isNumeric(String str){
		try {
			int result = Integer.valueOf(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public ReturnT<String> update(BdcJobInfoDO bdcJobInfoDO) {

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
		if (ExecutorRouteStrategyEnum.match(bdcJobInfoDO.getExecutorRouteStrategy(), null) == null) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("jobinfo_field_executorRouteStrategy")+I18nUtil.getString("system_unvalid")) );
		}
		if (MisfireStrategyEnum.match(bdcJobInfoDO.getMisfireStrategy(), null) == null) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("misfire_strategy")+I18nUtil.getString("system_unvalid")) );
		}
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
		BdcJobGroupDO bdcJobGroupDO = bdcJobGroupMapper.load(bdcJobInfoDO.getJobGroup());
		if (bdcJobGroupDO == null) {
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
		if (exists_Bdc_jobInfoDO.getTriggerStatus() == 1 && !scheduleDataNotChanged) {
			try {
				Date nextValidTime = JobScheduleHelper.generateNextValidTime(bdcJobInfoDO, new Date(System.currentTimeMillis() + JobScheduleHelper.PRE_READ_MS));
				if (nextValidTime == null) {
					return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type")+I18nUtil.getString("system_unvalid")) );
				}
				nextTriggerTime = nextValidTime.getTime();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
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

	@Override
	public ReturnT<String> remove(int id) {
		BdcJobInfoDO xxlBdcJobInfoDO = bdcJobInfoMapper.loadById(id);
		if (xxlBdcJobInfoDO == null) {
			return ReturnT.SUCCESS;
		}

		bdcJobInfoMapper.delete(id);
		bdcJobLogMapper.delete(id);
		bdcJobLogGlueMapper.deleteByJobId(id);
		return ReturnT.SUCCESS;
	}

	@Override
	public ReturnT<String> start(int id) {
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
			logger.error(e.getMessage(), e);
			return new ReturnT<String>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type")+I18nUtil.getString("system_unvalid")) );
		}

		xxlBdcJobInfoDO.setTriggerStatus(1);
		xxlBdcJobInfoDO.setTriggerLastTime(0);
		xxlBdcJobInfoDO.setTriggerNextTime(nextTriggerTime);

		xxlBdcJobInfoDO.setUpdatetime(new Date());
		bdcJobInfoMapper.update(xxlBdcJobInfoDO);
		return ReturnT.SUCCESS;
	}

	@Override
	public ReturnT<String> stop(int id) {
        BdcJobInfoDO xxlBdcJobInfoDO = bdcJobInfoMapper.loadById(id);

		xxlBdcJobInfoDO.setTriggerStatus(0);
		xxlBdcJobInfoDO.setTriggerLastTime(0);
		xxlBdcJobInfoDO.setTriggerNextTime(0);

		xxlBdcJobInfoDO.setUpdatetime(new Date());
		bdcJobInfoMapper.update(xxlBdcJobInfoDO);
		return ReturnT.SUCCESS;
	}

	@Override
	public Map<String, Object> dashboardInfo() {

		int jobInfoCount = bdcJobInfoMapper.findAllCount();
		int jobLogCount = 0;
		int jobLogSuccessCount = 0;
		BdcJobLogReportDO xxlBdcJobLogReportDO = bdcJobLogReportMapper.queryLogReportTotal();
		if (xxlBdcJobLogReportDO != null) {
			jobLogCount = xxlBdcJobLogReportDO.getRunningCount() + xxlBdcJobLogReportDO.getSucCount() + xxlBdcJobLogReportDO.getFailCount();
			jobLogSuccessCount = xxlBdcJobLogReportDO.getSucCount();
		}

		// executor count
		Set<String> executorAddressSet = new HashSet<String>();
		List<BdcJobGroupDO> groupList = bdcJobGroupMapper.findAll();

		if (groupList!=null && !groupList.isEmpty()) {
			for (BdcJobGroupDO group: groupList) {
				if (group.getRegistryList()!=null && !group.getRegistryList().isEmpty()) {
					executorAddressSet.addAll(group.getRegistryList());
				}
			}
		}

		int executorCount = executorAddressSet.size();

		Map<String, Object> dashboardMap = new HashMap<String, Object>();
		dashboardMap.put("jobInfoCount", jobInfoCount);
		dashboardMap.put("jobLogCount", jobLogCount);
		dashboardMap.put("jobLogSuccessCount", jobLogSuccessCount);
		dashboardMap.put("executorCount", executorCount);
		return dashboardMap;
	}

	@Override
	public ReturnT<Map<String, Object>> chartInfo(Date startDate, Date endDate) {

		// process
		List<String> triggerDayList = new ArrayList<String>();
		List<Integer> triggerDayCountRunningList = new ArrayList<Integer>();
		List<Integer> triggerDayCountSucList = new ArrayList<Integer>();
		List<Integer> triggerDayCountFailList = new ArrayList<Integer>();
		int triggerCountRunningTotal = 0;
		int triggerCountSucTotal = 0;
		int triggerCountFailTotal = 0;

		List<BdcJobLogReportDO> logReportList = bdcJobLogReportMapper.queryLogReport(startDate, endDate);

		if (logReportList!=null && logReportList.size()>0) {
			for (BdcJobLogReportDO item: logReportList) {
				String day = DateUtil.formatDate(item.getTriggerDay());
				int triggerDayCountRunning = item.getRunningCount();
				int triggerDayCountSuc = item.getSucCount();
				int triggerDayCountFail = item.getFailCount();

				triggerDayList.add(day);
				triggerDayCountRunningList.add(triggerDayCountRunning);
				triggerDayCountSucList.add(triggerDayCountSuc);
				triggerDayCountFailList.add(triggerDayCountFail);

				triggerCountRunningTotal += triggerDayCountRunning;
				triggerCountSucTotal += triggerDayCountSuc;
				triggerCountFailTotal += triggerDayCountFail;
			}
		} else {
			for (int i = -6; i <= 0; i++) {
				triggerDayList.add(DateUtil.formatDate(DateUtil.addDays(new Date(), i)));
				triggerDayCountRunningList.add(0);
				triggerDayCountSucList.add(0);
				triggerDayCountFailList.add(0);
			}
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("triggerDayList", triggerDayList);
		result.put("triggerDayCountRunningList", triggerDayCountRunningList);
		result.put("triggerDayCountSucList", triggerDayCountSucList);
		result.put("triggerDayCountFailList", triggerDayCountFailList);

		result.put("triggerCountRunningTotal", triggerCountRunningTotal);
		result.put("triggerCountSucTotal", triggerCountSucTotal);
		result.put("triggerCountFailTotal", triggerCountFailTotal);

		return new ReturnT<Map<String, Object>>(result);
	}

}
