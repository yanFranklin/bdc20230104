package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.job.JobGroup;
import cn.gtmap.realestate.common.core.domain.job.JobRegistry;
import cn.gtmap.realestate.common.job.biz.model.ReturnT;
import cn.gtmap.realestate.common.job.enums.RegistryConfig;
import cn.gtmap.realestate.config.core.mapper.XxlJobGroupDao;
import cn.gtmap.realestate.config.core.mapper.XxlJobInfoDao;
import cn.gtmap.realestate.config.core.mapper.XxlJobRegistryDao;
import cn.gtmap.realestate.config.job.util.I18nUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * job group controller
 * @author xuxueli 2016-10-02 20:52:56
 */
@Controller
@RequestMapping("/jobgroup")
public class JobGroupController {

	@Resource
	public XxlJobInfoDao xxlJobInfoDao;
	@Resource
	public XxlJobGroupDao xxlJobGroupDao;
	@Resource
	private XxlJobRegistryDao xxlJobRegistryDao;

	/**
	 * 模板页面地址
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String index(Model model) {
		return "jobgroup/jobgroup.index";
	}

	/**
	 * @param request
	 * @param start
	 * @param length
	 * @param appname
	 * @param title
	 * @return 单表查询 注册的执行器信息
	 */
	@RequestMapping("/pageList")
	@ResponseBody
	public Map<String, Object> pageList(HttpServletRequest request,
										@RequestParam(required = false, defaultValue = "0") int start,
										@RequestParam(required = false, defaultValue = "10") int length,
										String appname, String title) {

		// page query
		List<JobGroup> list = xxlJobGroupDao.pageList(start, length, appname, title);
		int list_count = xxlJobGroupDao.pageListCount(start, length, appname, title);

		// package result
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("recordsTotal", list_count);		// 总记录数
		maps.put("recordsFiltered", list_count);	// 过滤后的总记录数
		maps.put("data", list);  					// 分页列表
		return maps;
	}

	/**
	 * 新增 执行器信息
	 * @param xxlJobGroup
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public ReturnT<String> save(JobGroup xxlJobGroup){

		// valid
		if (xxlJobGroup.getAppname()==null || xxlJobGroup.getAppname().trim().length()==0) {
			return new ReturnT<String>(500, (I18nUtil.getString("system_please_input")+"AppName") );
		}
		if (xxlJobGroup.getAppname().length()<4 || xxlJobGroup.getAppname().length()>64) {
			return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_appname_length") );
		}
		if (xxlJobGroup.getAppname().contains(">") || xxlJobGroup.getAppname().contains("<")) {
			return new ReturnT<String>(500, "AppName"+I18nUtil.getString("system_unvalid") );
		}
		if (xxlJobGroup.getTitle()==null || xxlJobGroup.getTitle().trim().length()==0) {
			return new ReturnT<String>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("jobgroup_field_title")) );
		}
		if (xxlJobGroup.getTitle().contains(">") || xxlJobGroup.getTitle().contains("<")) {
			return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_title")+I18nUtil.getString("system_unvalid") );
		}
		if (xxlJobGroup.getAddressType()!=0) {
			if (xxlJobGroup.getAddressList()==null || xxlJobGroup.getAddressList().trim().length()==0) {
				return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_addressType_limit") );
			}
			if (xxlJobGroup.getAddressList().contains(">") || xxlJobGroup.getAddressList().contains("<")) {
				return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_registryList")+I18nUtil.getString("system_unvalid") );
			}

			String[] addresss = xxlJobGroup.getAddressList().split(",");
			for (String item: addresss) {
				if (item==null || item.trim().length()==0) {
					return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_registryList_unvalid") );
				}
			}
		}

		// process
		xxlJobGroup.setUpdateTime(new Date());

		int ret = xxlJobGroupDao.save(xxlJobGroup);
		return (ret>0)?ReturnT.SUCCESS:ReturnT.FAIL;
	}

	/**
	 * 更新 执行器信息
	 * @param xxlJobGroup
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public ReturnT<String> update(JobGroup xxlJobGroup){
		// valid
		if (xxlJobGroup.getAppname()==null || xxlJobGroup.getAppname().trim().length()==0) {
			return new ReturnT<String>(500, (I18nUtil.getString("system_please_input")+"AppName") );
		}
		if (xxlJobGroup.getAppname().length()<4 || xxlJobGroup.getAppname().length()>64) {
			return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_appname_length") );
		}
		if (xxlJobGroup.getTitle()==null || xxlJobGroup.getTitle().trim().length()==0) {
			return new ReturnT<String>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("jobgroup_field_title")) );
		}
		if (xxlJobGroup.getAddressType() == 0) {
			// 0=自动注册
			List<String> registryList = findRegistryByAppName(xxlJobGroup.getAppname());
			String addressListStr = null;
			if (registryList!=null && !registryList.isEmpty()) {
				Collections.sort(registryList);
				addressListStr = "";
				for (String item:registryList) {
					addressListStr += item + ",";
				}
				addressListStr = addressListStr.substring(0, addressListStr.length()-1);
			}
			xxlJobGroup.setAddressList(addressListStr);
		} else {
			// 1=手动录入
			if (xxlJobGroup.getAddressList()==null || xxlJobGroup.getAddressList().trim().length()==0) {
				return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_addressType_limit") );
			}
			String[] addresss = xxlJobGroup.getAddressList().split(",");
			for (String item: addresss) {
				if (item==null || item.trim().length()==0) {
					return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_registryList_unvalid") );
				}
			}
		}

		// process
		xxlJobGroup.setUpdateTime(new Date());

		int ret = xxlJobGroupDao.update(xxlJobGroup);
		return (ret>0)?ReturnT.SUCCESS:ReturnT.FAIL;
	}

	/**
	 * 存在疑问
	 * 根据执行器的名字，查找已经注册的执行器IP地址, 将注册的执行器加入到xxl-job-group分组中
	 * @param appnameParam
	 * @return
	 */
	private List<String> findRegistryByAppName(String appnameParam){
		HashMap<String, List<String>> appAddressMap = new HashMap<String, List<String>>();
		//查找所有没有死亡的注册器（注册更新时间超过60s算作死亡的执行器）
		List<JobRegistry> list = xxlJobRegistryDao.findAll(RegistryConfig.DEAD_TIMEOUT, new Date());
		if (list != null) {
			for (JobRegistry item: list) {
				if (RegistryConfig.RegistType.EXECUTOR.name().equals(item.getRegistryGroup())) {
					String appname = item.getRegistryKey();
					List<String> registryList = appAddressMap.get(appname);
					if (registryList == null) {
						registryList = new ArrayList<String>();
					}

					if (!registryList.contains(item.getRegistryValue())) {
						registryList.add(item.getRegistryValue());
					}
					appAddressMap.put(appname, registryList);
				}
			}
		}
		return appAddressMap.get(appnameParam);
	}

	/**
	 * 删除 执行器
	 * @param id
	 * @return
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public ReturnT<String> remove(int id){

		// valid 查看执行器任务表，查看改该执行器是否在执行任务 （不管该任务的调度状态是运行还是停止）
		int count = xxlJobInfoDao.pageListCount(0, 10, id, -1,  null, null, null);
		if (count > 0) {
			//拒绝删除，该执行器使用中 在XxlJobInfo表中有改执行器在执行任务
			return new ReturnT<String>(500, I18nUtil.getString("jobgroup_del_limit_0") );
		}

		List<JobGroup> allList = xxlJobGroupDao.findAll();
		if (allList.size() == 1) {
			//拒绝删除, 系统至少保留一个执行器
			return new ReturnT<String>(500, I18nUtil.getString("jobgroup_del_limit_1") );
		}

		int ret = xxlJobGroupDao.remove(id);
		return (ret>0)?ReturnT.SUCCESS:ReturnT.FAIL;
	}

	/**
	 * 根据执行器id查询执行器信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/loadById")
	@ResponseBody
	public ReturnT<JobGroup> loadById(int id){
		JobGroup jobGroup = xxlJobGroupDao.load(id);
		return jobGroup!=null?new ReturnT<JobGroup>(jobGroup):new ReturnT<JobGroup>(ReturnT.FAIL_CODE, null);
	}

}
