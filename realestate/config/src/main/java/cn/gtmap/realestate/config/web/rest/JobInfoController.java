package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobInfoDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobUserDO;
import cn.gtmap.realestate.common.job.biz.model.ReturnT;
import cn.gtmap.realestate.common.job.enums.ExecutorBlockStrategyEnum;
import cn.gtmap.realestate.common.job.glue.GlueTypeEnum;
import cn.gtmap.realestate.common.job.util.DateUtil;
import cn.gtmap.realestate.config.core.mapper.BdcJobGroupMapper;
import cn.gtmap.realestate.config.core.service.LoginService;
import cn.gtmap.realestate.config.core.service.XxlJobService;
import cn.gtmap.realestate.config.job.exception.XxlJobException;
import cn.gtmap.realestate.config.job.route.ExecutorRouteStrategyEnum;
import cn.gtmap.realestate.config.job.scheduler.MisfireStrategyEnum;
import cn.gtmap.realestate.config.job.scheduler.ScheduleTypeEnum;
import cn.gtmap.realestate.config.job.thread.JobScheduleHelper;
import cn.gtmap.realestate.config.job.thread.JobTriggerPoolHelper;
import cn.gtmap.realestate.config.job.trigger.TriggerTypeEnum;
import cn.gtmap.realestate.config.job.util.I18nUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * index controller
 * @author xuxueli 2015-12-19 16:13:16
 */
@Controller
@RequestMapping("/jobinfo")
public class JobInfoController {
	private static Logger logger = LoggerFactory.getLogger(JobInfoController.class);

	@Resource
	private BdcJobGroupMapper bdcJobGroupMapper;
	@Resource
	private XxlJobService xxlJobService;
	
	@RequestMapping
	public String index(HttpServletRequest request, Model model, @RequestParam(required = false, defaultValue = "-1") int jobGroup) {

		// 枚举-字典
		model.addAttribute("ExecutorRouteStrategyEnum", ExecutorRouteStrategyEnum.values());	    // 路由策略-列表
		model.addAttribute("GlueTypeEnum", GlueTypeEnum.values());								// Glue类型-字典
		model.addAttribute("ExecutorBlockStrategyEnum", ExecutorBlockStrategyEnum.values());	    // 阻塞处理策略-字典
		model.addAttribute("ScheduleTypeEnum", ScheduleTypeEnum.values());	    				// 调度类型
		model.addAttribute("MisfireStrategyEnum", MisfireStrategyEnum.values());	    			// 调度过期策略

		// 执行器列表
		List<BdcJobGroupDO> bdcJobGroupDOList_all =  bdcJobGroupMapper.findAll();

		// filter group
		List<BdcJobGroupDO> bdcJobGroupDOList = filterJobGroupByRole(request, bdcJobGroupDOList_all);
		if (bdcJobGroupDOList ==null || bdcJobGroupDOList.size()==0) {
			throw new XxlJobException(I18nUtil.getString("jobgroup_empty"));
		}

		model.addAttribute("JobGroupList", bdcJobGroupDOList);
		model.addAttribute("jobGroup", jobGroup);

		return "jobinfo/jobinfo.index";
	}

	public static List<BdcJobGroupDO> filterJobGroupByRole(HttpServletRequest request, List<BdcJobGroupDO> bdcJobGroupDOList_all){
		List<BdcJobGroupDO> bdcJobGroupDOList = new ArrayList<>();
		if (bdcJobGroupDOList_all !=null && bdcJobGroupDOList_all.size()>0) {
			BdcJobUserDO loginUser = (BdcJobUserDO) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
			if (loginUser.getRole() == 1) {
				bdcJobGroupDOList = bdcJobGroupDOList_all;
			} else {
				List<String> groupIdStrs = new ArrayList<>();
				if (loginUser.getPermission()!=null && loginUser.getPermission().trim().length()>0) {
					groupIdStrs = Arrays.asList(loginUser.getPermission().trim().split(","));
				}
				for (BdcJobGroupDO groupItem: bdcJobGroupDOList_all) {
					if (groupIdStrs.contains(String.valueOf(groupItem.getId()))) {
						bdcJobGroupDOList.add(groupItem);
					}
				}
			}
		}
		return bdcJobGroupDOList;
	}
	public static void validPermission(HttpServletRequest request, int jobGroup) {
		BdcJobUserDO loginUser = (BdcJobUserDO) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
		if (!loginUser.validPermission(jobGroup)) {
			throw new RuntimeException(I18nUtil.getString("system_permission_limit") + "[username="+ loginUser.getUsername() +"]");
		}
	}

	/**
	 * 单表查询 调度任务信息
	 * @param start
	 * @param length
	 * @param jobGroup
	 * @param triggerStatus
	 * @param jobDesc
	 * @param executorHandler
	 * @param author
	 * @return
	 */
	@RequestMapping("/pageList")
	@ResponseBody
	public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start,  
			@RequestParam(required = false, defaultValue = "10") int length,
			int jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author) {
		
		return xxlJobService.pageList(start, length, jobGroup, triggerStatus, jobDesc, executorHandler, author);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public ReturnT<String> add(BdcJobInfoDO bdcJobInfoDO) {
		return xxlJobService.add(bdcJobInfoDO);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public ReturnT<String> update(BdcJobInfoDO bdcJobInfoDO) {
		return xxlJobService.update(bdcJobInfoDO);
	}

	/**
	 * 根据任务id 删除任务
	 * @param id
	 * @return
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public ReturnT<String> remove(int id) {
		return xxlJobService.remove(id);
	}
	
	@RequestMapping("/stop")
	@ResponseBody
	public ReturnT<String> pause(int id) {
		return xxlJobService.stop(id);
	}
	
	@RequestMapping("/start")
	@ResponseBody
	public ReturnT<String> start(int id) {
		return xxlJobService.start(id);
	}
	
	@RequestMapping("/trigger")
	@ResponseBody
	//@PermissionLimit(limit = false)
	public ReturnT<String> triggerJob(int id, String executorParam, String addresslist) {
		// force cover job param
		if (executorParam == null) {
			executorParam = "";
		}

		JobTriggerPoolHelper.trigger(id, TriggerTypeEnum.MANUAL, -1, null, executorParam, addresslist);
		return ReturnT.SUCCESS;
	}

	@RequestMapping("/nextTriggerTime")
	@ResponseBody
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
			logger.error(e.getMessage(), e);
			return new ReturnT<List<String>>(ReturnT.FAIL_CODE, (I18nUtil.getString("schedule_type")+I18nUtil.getString("system_unvalid")) + e.getMessage());
		}
		return new ReturnT<List<String>>(result);

	}
	
}
