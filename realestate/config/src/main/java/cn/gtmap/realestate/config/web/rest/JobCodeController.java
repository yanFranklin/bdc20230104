package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.job.BdcJobInfoDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobLogGlueDO;
import cn.gtmap.realestate.common.job.biz.model.ReturnT;
import cn.gtmap.realestate.common.job.glue.GlueTypeEnum;
import cn.gtmap.realestate.config.core.mapper.BdcJobInfoMapper;
import cn.gtmap.realestate.config.core.mapper.BdcJobLogGlueMapper;
import cn.gtmap.realestate.config.job.util.I18nUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * job code controller
 * @author  2015-12-19 16:13:16
 */
@Controller
@RequestMapping("/jobcode")
public class JobCodeController {
	
	@Resource
	private BdcJobInfoMapper bdcJobInfoMapper;
	@Resource
	private BdcJobLogGlueMapper bdcJobLogGlueMapper;

	@RequestMapping
	public String index(HttpServletRequest request, Model model, int jobId) {
		BdcJobInfoDO bdcJobInfoDO = bdcJobInfoMapper.loadById(jobId);
		List<BdcJobLogGlueDO> bdcJobLogGlueDOS = bdcJobLogGlueMapper.findByJobId(jobId);

		if (bdcJobInfoDO == null) {
			throw new RuntimeException(I18nUtil.getString("jobinfo_glue_jobid_unvalid"));
		}
		if (GlueTypeEnum.BEAN == GlueTypeEnum.match(bdcJobInfoDO.getGlueType())) {
			throw new RuntimeException(I18nUtil.getString("jobinfo_glue_gluetype_unvalid"));
		}

		// valid permission
		JobInfoController.validPermission(request, bdcJobInfoDO.getJobGroup());

		// Glue类型-字典
		model.addAttribute("GlueTypeEnum", GlueTypeEnum.values());

		model.addAttribute("jobInfo", bdcJobInfoDO);
		model.addAttribute("jobLogGlues", bdcJobLogGlueDOS);
		return "jobcode/jobcode.index";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public ReturnT<String> save(Model model, int id, String glueSource, String glueRemark) {
		// valid
		if (glueRemark==null) {
			return new ReturnT<String>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("jobinfo_glue_remark")) );
		}
		if (glueRemark.length()<4 || glueRemark.length()>100) {
			return new ReturnT<String>(500, I18nUtil.getString("jobinfo_glue_remark_limit"));
		}
		BdcJobInfoDO exists_Bdc_jobInfoDO = bdcJobInfoMapper.loadById(id);
		if (exists_Bdc_jobInfoDO == null) {
			return new ReturnT<String>(500, I18nUtil.getString("jobinfo_glue_jobid_unvalid"));
		}
		
		// update new code
		exists_Bdc_jobInfoDO.setGlueSource(glueSource);
		exists_Bdc_jobInfoDO.setGlueRemark(glueRemark);
		exists_Bdc_jobInfoDO.setGlueUpdatetime(new Date());

		exists_Bdc_jobInfoDO.setUpdatetime(new Date());
		bdcJobInfoMapper.update(exists_Bdc_jobInfoDO);

		// log old code
		BdcJobLogGlueDO bdcJobLogGlueDO = new BdcJobLogGlueDO();
		bdcJobLogGlueDO.setJobId(exists_Bdc_jobInfoDO.getId());
		bdcJobLogGlueDO.setGlueType(exists_Bdc_jobInfoDO.getGlueType());
		bdcJobLogGlueDO.setGlueSource(glueSource);
		bdcJobLogGlueDO.setGlueRemark(glueRemark);

		bdcJobLogGlueDO.setAddTime(new Date());
		bdcJobLogGlueDO.setUpdatetime(new Date());
		bdcJobLogGlueMapper.save(bdcJobLogGlueDO);

		// remove code backup more than 30
		bdcJobLogGlueMapper.removeOld(exists_Bdc_jobInfoDO.getId(), 30);

		return ReturnT.SUCCESS;
	}
	
}
