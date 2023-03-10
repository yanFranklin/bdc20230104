package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDTO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobInfoDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobLogDO;
import cn.gtmap.realestate.common.job.biz.ExecutorBiz;
import cn.gtmap.realestate.common.job.biz.model.KillParam;
import cn.gtmap.realestate.common.job.biz.model.LogParam;
import cn.gtmap.realestate.common.job.biz.model.LogResult;
import cn.gtmap.realestate.common.core.dto.ReturnT;
import cn.gtmap.realestate.common.job.util.DateUtil;
import cn.gtmap.realestate.config.core.mapper.BdcJobGroupMapper;
import cn.gtmap.realestate.config.core.mapper.BdcJobInfoMapper;
import cn.gtmap.realestate.config.core.mapper.BdcJobLogMapper;
import cn.gtmap.realestate.config.job.complete.XxlJobCompleter;
import cn.gtmap.realestate.config.job.exception.XxlJobException;
import cn.gtmap.realestate.config.job.scheduler.XxlJobScheduler;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * index controller
 * @author  2015-12-19 16:13:16
 */
@Controller
@RequestMapping("/joblog")
public class JobLogController {
	private static Logger logger = LoggerFactory.getLogger(JobLogController.class);

	@Resource
	private BdcJobGroupMapper bdcJobGroupMapper;
	@Resource
	public BdcJobInfoMapper bdcJobInfoMapper;
	@Resource
	public BdcJobLogMapper bdcJobLogMapper;

	@RequestMapping
	public String index(HttpServletRequest request, Model model, @RequestParam(required = false, defaultValue = "0") Integer jobId) {

		// ???????????????
		List<BdcJobGroupDTO> bdcJobGroupDTOList_all =  bdcJobGroupMapper.findAll();

		// filter group
		List<BdcJobGroupDTO> bdcJobGroupDTOList = JobInfoController.filterJobGroupByRole(request, bdcJobGroupDTOList_all);
		if (bdcJobGroupDTOList ==null || bdcJobGroupDTOList.size()==0) {
			throw new XxlJobException(I18nUtil.getString("jobgroup_empty"));
		}

		model.addAttribute("JobGroupList", bdcJobGroupDTOList);

		// ??????
		if (jobId > 0) {
			BdcJobInfoDO bdcJobInfoDO = bdcJobInfoMapper.loadById(jobId);
			if (bdcJobInfoDO == null) {
				throw new RuntimeException(I18nUtil.getString("jobinfo_field_id") + I18nUtil.getString("system_unvalid"));
			}

			model.addAttribute("jobInfo", bdcJobInfoDO);

			// valid permission ????????????????????????jobGroup?????????
			JobInfoController.validPermission(request, bdcJobInfoDO.getJobGroup());
		}

		return "joblog/joblog.index";
	}

	@RequestMapping("/getJobsByGroup")
	@ResponseBody
	public ReturnT<List<BdcJobInfoDO>> getJobsByGroup(int jobGroup){
		List<BdcJobInfoDO> list = bdcJobInfoMapper.getJobsByGroup(jobGroup);
		return new ReturnT<List<BdcJobInfoDO>>(list);
	}
	
	@RequestMapping("/pageList")
	@ResponseBody
	public Map<String, Object> pageList(HttpServletRequest request,
										@RequestParam(required = false, defaultValue = "0") int start,
										@RequestParam(required = false, defaultValue = "10") int length,
										int jobGroup, int jobId, int logStatus, String filterTime) {

		// valid permission ????????????????????????jobGroup?????????
		JobInfoController.validPermission(request, jobGroup);	// ???????????????????????????????????????????????????????????????????????? jobGroup
		
		// parse param
		Date triggerTimeStart = null;
		Date triggerTimeEnd = null;
		if (filterTime!=null && filterTime.trim().length()>0) {
			String[] temp = filterTime.split(" - ");
			if (temp.length == 2) {
				triggerTimeStart = DateUtil.parseDateTime(temp[0]);
				triggerTimeEnd = DateUtil.parseDateTime(temp[1]);
			}
		}
		
		// page query
		List<BdcJobLogDO> list = bdcJobLogMapper.pageList(start, length, jobGroup, jobId, triggerTimeStart, triggerTimeEnd, logStatus);
		int list_count = bdcJobLogMapper.pageListCount(start, length, jobGroup, jobId, triggerTimeStart, triggerTimeEnd, logStatus);
		
		// package result
		Map<String, Object> maps = new HashMap<String, Object>();
	    maps.put("recordsTotal", list_count);		// ????????????
	    maps.put("recordsFiltered", list_count);	// ????????????????????????
	    maps.put("data", list);  					// ????????????
		return maps;
	}

	@RequestMapping("/logDetailPage")
	public String logDetailPage(int id, Model model){

		// base check
		ReturnT<String> logStatue = ReturnT.SUCCESS;
		BdcJobLogDO bdcJobLogDO = bdcJobLogMapper.load(id);
		if (bdcJobLogDO == null) {
            throw new RuntimeException(I18nUtil.getString("joblog_logid_unvalid"));
		}

        model.addAttribute("triggerCode", bdcJobLogDO.getTriggerCode());
        model.addAttribute("handleCode", bdcJobLogDO.getHandleCode());
        model.addAttribute("executorAddress", bdcJobLogDO.getExecutorAddress());
        model.addAttribute("triggerTime", bdcJobLogDO.getTriggerTime().getTime());
        model.addAttribute("logId", bdcJobLogDO.getId());
		return "joblog/joblog.detail";
	}

	@RequestMapping("/logDetailCat")
	@ResponseBody
	public ReturnT<LogResult> logDetailCat(String executorAddress, long triggerTime, long logId, int fromLineNum){
		try {
			ExecutorBiz executorBiz = XxlJobScheduler.getExecutorBiz(executorAddress);
			ReturnT<LogResult> logResult = executorBiz.log(new LogParam(triggerTime, logId, fromLineNum));

			// is end
            if (logResult.getContent()!=null && logResult.getContent().getFromLineNum() > logResult.getContent().getToLineNum()) {
                BdcJobLogDO bdcJobLogDO = bdcJobLogMapper.load(logId);
                if (bdcJobLogDO.getHandleCode() > 0) {
                    logResult.getContent().setEnd(true);
                }
            }

			return logResult;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ReturnT<LogResult>(ReturnT.FAIL_CODE, e.getMessage());
		}
	}

	@RequestMapping("/logKill")
	@ResponseBody
	public ReturnT<String> logKill(int id){
		// base check
		BdcJobLogDO log = bdcJobLogMapper.load(id);
		BdcJobInfoDO bdcJobInfoDO = bdcJobInfoMapper.loadById(log.getJobId());
		if (bdcJobInfoDO ==null) {
			return new ReturnT<String>(500, I18nUtil.getString("jobinfo_glue_jobid_unvalid"));
		}
		if (ReturnT.SUCCESS_CODE != log.getTriggerCode()) {
			return new ReturnT<String>(500, I18nUtil.getString("joblog_kill_log_limit"));
		}

		// request of kill
		ReturnT<String> runResult = null;
		try {
			ExecutorBiz executorBiz = XxlJobScheduler.getExecutorBiz(log.getExecutorAddress());
			runResult = executorBiz.kill(new KillParam(bdcJobInfoDO.getId()));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			runResult = new ReturnT<String>(500, e.getMessage());
		}

		if (ReturnT.SUCCESS_CODE == runResult.getCode()) {
			log.setHandleCode(ReturnT.FAIL_CODE);
			log.setHandleMsg( I18nUtil.getString("joblog_kill_log_byman")+":" + (runResult.getMsg()!=null?runResult.getMsg():""));
			log.setHandleTime(new Date());
			XxlJobCompleter.updateHandleInfoAndFinish(log);
			return new ReturnT<String>(runResult.getMsg());
		} else {
			return new ReturnT<String>(500, runResult.getMsg());
		}
	}

	@RequestMapping("/clearLog")
	@ResponseBody
	public ReturnT<String> clearLog(int jobGroup, int jobId, int type){

		Date clearBeforeTime = null;
		int clearBeforeNum = 0;
		if (type == 1) {
			clearBeforeTime = DateUtil.addMonths(new Date(), -1);	// ?????????????????????????????????
		} else if (type == 2) {
			clearBeforeTime = DateUtil.addMonths(new Date(), -3);	// ?????????????????????????????????
		} else if (type == 3) {
			clearBeforeTime = DateUtil.addMonths(new Date(), -6);	// ?????????????????????????????????
		} else if (type == 4) {
			clearBeforeTime = DateUtil.addYears(new Date(), -1);	// ??????????????????????????????
		} else if (type == 5) {
			clearBeforeNum = 1000;		// ?????????????????????????????????
		} else if (type == 6) {
			clearBeforeNum = 10000;		// ?????????????????????????????????
		} else if (type == 7) {
			clearBeforeNum = 30000;		// ?????????????????????????????????
		} else if (type == 8) {
			clearBeforeNum = 100000;	// ?????????????????????????????????
		} else if (type == 9) {
			clearBeforeNum = 0;			// ????????????????????????
		} else {
			return new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("joblog_clean_type_unvalid"));
		}

		List<Long> logIds = null;
		do {
			logIds = bdcJobLogMapper.findClearLogIds(jobGroup, jobId, clearBeforeTime, clearBeforeNum, 1000);
			if (logIds!=null && logIds.size()>0) {
				bdcJobLogMapper.clearLog(logIds);
			}
		} while (logIds!=null && logIds.size()>0);

		return ReturnT.SUCCESS;
	}

}
