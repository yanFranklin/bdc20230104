package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDTO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobRegistryDO;
import cn.gtmap.realestate.common.core.dto.ReturnT;
import cn.gtmap.realestate.common.job.enums.RegistryConfig;
import cn.gtmap.realestate.config.core.mapper.BdcJobGroupMapper;
import cn.gtmap.realestate.config.core.mapper.BdcJobInfoMapper;
import cn.gtmap.realestate.config.core.mapper.BdcJobRegistryMapper;
import cn.gtmap.realestate.config.job.util.I18nUtil;
import org.springframework.beans.BeanUtils;
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
 * @author  2016-10-02 20:52:56
 */
@Controller
@RequestMapping("/jobgroup")
public class JobGroupController {

	@Resource
	public BdcJobInfoMapper bdcJobInfoMapper;
	@Resource
	public BdcJobGroupMapper bdcJobGroupMapper;
	@Resource
	private BdcJobRegistryMapper bdcJobRegistryMapper;

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
		List<BdcJobGroupDTO> list = bdcJobGroupMapper.pageList(start, length, appname, title);
		int list_count = bdcJobGroupMapper.pageListCount(start, length, appname, title);

		// package result
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("recordsTotal", list_count);		// 总记录数
		maps.put("recordsFiltered", list_count);	// 过滤后的总记录数
		maps.put("data", list);  					// 分页列表
		return maps;
	}

	/**
	 * 新增 执行器信息
	 * @param xxlBdcJobGroupDTO
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public ReturnT<String> save(BdcJobGroupDTO bdcJobGroupDTO){

		// valid
		if (bdcJobGroupDTO.getAppname()==null || bdcJobGroupDTO.getAppname().trim().length()==0) {
			return new ReturnT<String>(500, (I18nUtil.getString("system_please_input")+"AppName") );
		}
		if (bdcJobGroupDTO.getAppname().length()<4 || bdcJobGroupDTO.getAppname().length()>64) {
			return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_appname_length") );
		}
		if (bdcJobGroupDTO.getAppname().contains(">") || bdcJobGroupDTO.getAppname().contains("<")) {
			return new ReturnT<String>(500, "AppName"+I18nUtil.getString("system_unvalid") );
		}
		if (bdcJobGroupDTO.getTitle()==null || bdcJobGroupDTO.getTitle().trim().length()==0) {
			return new ReturnT<String>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("jobgroup_field_title")) );
		}
		if (bdcJobGroupDTO.getTitle().contains(">") || bdcJobGroupDTO.getTitle().contains("<")) {
			return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_title")+I18nUtil.getString("system_unvalid") );
		}
		if (bdcJobGroupDTO.getAddresstype()!=0) {
			if (bdcJobGroupDTO.getAddresslist()==null || bdcJobGroupDTO.getAddresslist().trim().length()==0) {
				return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_addresstype_limit") );
			}
			if (bdcJobGroupDTO.getAddresslist().contains(">") || bdcJobGroupDTO.getAddresslist().contains("<")) {
				return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_registryList")+I18nUtil.getString("system_unvalid") );
			}

			String[] addresss = bdcJobGroupDTO.getAddresslist().split(",");
			for (String item: addresss) {
				if (item==null || item.trim().length()==0) {
					return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_registryList_unvalid") );
				}
			}
		}

		// process
		bdcJobGroupDTO.setUpdatetime(new Date());

		BdcJobGroupDO bdcJobGroupDO = new BdcJobGroupDO();
		bdcJobGroupDO.setAddresslist(bdcJobGroupDTO.getAddresslist());
		bdcJobGroupDO.setId(bdcJobGroupDTO.getId());
		bdcJobGroupDO.setAddresstype(bdcJobGroupDTO.getAddresstype());
		bdcJobGroupDO.setUpdatetime(bdcJobGroupDTO.getUpdatetime());
		bdcJobGroupDO.setAppname(bdcJobGroupDTO.getAppname());
		bdcJobGroupDO.setTitle(bdcJobGroupDTO.getTitle());
		int ret = bdcJobGroupMapper.save(bdcJobGroupDO);
		return (ret>0)?ReturnT.SUCCESS:ReturnT.FAIL;
	}

	/**
	 * 更新 执行器信息
	 * @param xxlBdcJobGroupDTO
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public ReturnT<String> update(BdcJobGroupDTO xxlBdcJobGroupDTO){
		// valid
		if (xxlBdcJobGroupDTO.getAppname()==null || xxlBdcJobGroupDTO.getAppname().trim().length()==0) {
			return new ReturnT<String>(500, (I18nUtil.getString("system_please_input")+"AppName") );
		}
		if (xxlBdcJobGroupDTO.getAppname().length()<4 || xxlBdcJobGroupDTO.getAppname().length()>64) {
			return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_appname_length") );
		}
		if (xxlBdcJobGroupDTO.getTitle()==null || xxlBdcJobGroupDTO.getTitle().trim().length()==0) {
			return new ReturnT<String>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("jobgroup_field_title")) );
		}
		if (xxlBdcJobGroupDTO.getAddresstype() == 0) {
			// 0=自动注册
			List<String> registryList = findRegistryByAppName(xxlBdcJobGroupDTO.getAppname());
			String addresslistStr = null;
			if (registryList!=null && !registryList.isEmpty()) {
				Collections.sort(registryList);
				addresslistStr = "";
				for (String item:registryList) {
					addresslistStr += item + ",";
				}
				addresslistStr = addresslistStr.substring(0, addresslistStr.length()-1);
			}
			xxlBdcJobGroupDTO.setAddresslist(addresslistStr);
		} else {
			// 1=手动录入
			if (xxlBdcJobGroupDTO.getAddresslist()==null || xxlBdcJobGroupDTO.getAddresslist().trim().length()==0) {
				return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_addresstype_limit") );
			}
			String[] addresss = xxlBdcJobGroupDTO.getAddresslist().split(",");
			for (String item: addresss) {
				if (item==null || item.trim().length()==0) {
					return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_registryList_unvalid") );
				}
			}
		}

		// process
		xxlBdcJobGroupDTO.setUpdatetime(new Date());
		BdcJobGroupDO bdcJobGroupDO = new BdcJobGroupDO();
		BeanUtils.copyProperties(xxlBdcJobGroupDTO, bdcJobGroupDO);
		int ret = bdcJobGroupMapper.update(bdcJobGroupDO);
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
		List<BdcJobRegistryDO> list = bdcJobRegistryMapper.findAll(RegistryConfig.DEAD_TIMEOUT, new Date());
		if (list != null) {
			for (BdcJobRegistryDO item: list) {
				if (RegistryConfig.RegistType.EXECUTOR.name().equals(item.getRegistrygroup())) {
					String appname = item.getRegistrykey();
					List<String> registryList = appAddressMap.get(appname);
					if (registryList == null) {
						registryList = new ArrayList<String>();
					}

					if (!registryList.contains(item.getRegistryvalue())) {
						registryList.add(item.getRegistryvalue());
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
		int count = bdcJobInfoMapper.pageListCount(0, 10, id, -1,  null, null, null);
		if (count > 0) {
			//拒绝删除，该执行器使用中 在XxlJobInfo表中有改执行器在执行任务
			return new ReturnT<String>(500, I18nUtil.getString("jobgroup_del_limit_0") );
		}

		List<BdcJobGroupDTO> allList = bdcJobGroupMapper.findAll();
		if (allList.size() == 1) {
			//拒绝删除, 系统至少保留一个执行器
			return new ReturnT<String>(500, I18nUtil.getString("jobgroup_del_limit_1") );
		}

		int ret = bdcJobGroupMapper.remove(id);
		return (ret>0)?ReturnT.SUCCESS:ReturnT.FAIL;
	}

	/**
	 * 根据执行器id查询执行器信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/loadById")
	@ResponseBody
	public ReturnT<BdcJobGroupDTO> loadById(int id){
		BdcJobGroupDTO bdcJobGroupDTO = bdcJobGroupMapper.load(id);
		return bdcJobGroupDTO !=null?new ReturnT<BdcJobGroupDTO>(bdcJobGroupDTO):new ReturnT<BdcJobGroupDTO>(ReturnT.FAIL_CODE, null);
	}

}
