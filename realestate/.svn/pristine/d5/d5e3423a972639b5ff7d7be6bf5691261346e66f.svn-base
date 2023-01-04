package cn.gtmap.realestate.check.web.rest;

import cn.gtmap.realestate.check.service.CheckPlanService;
import cn.gtmap.realestate.check.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.check.CheckPlanDO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/7/30.
 * @description 检测计划
 */

@Controller
@RequestMapping("/rest/v1.0/checkPlan")
public class CheckPlanController extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckPlanController.class);
    @Autowired
    CheckPlanService checkPlanService;

    /**
     * 根据输入参数生成检查计划
     * @param qsrq
     * @param jsrq
     * @param dcjcts
     * @return
     */
    @ResponseBody
    @RequestMapping("/showPlan")
    public Map showPlan(@RequestParam(value = "qsrq", required = true) String qsrq,
                           @RequestParam(value = "jsrq", required = true) String jsrq,
                           @RequestParam(value = "dcjcts", required = true) int dcjcts) {
        Map<String,Object> params = new HashedMap();
        params.put("qsrq",qsrq);
        params.put("jsrq",jsrq);
        params.put("dcjcts",dcjcts);
        Map<String,Object> result = checkPlanService.getPlanDatas(params);
        if(MapUtils.isNotEmpty(result)){
            result.put("success",true);
        }
        return result;
    }

    /**
     * 保存查询计划
     * @param qsrq
     * @param jsrq
     * @param dcjcts
     * @param zsjl
     * @param jccs
     * @return
     */
    @ResponseBody
    @RequestMapping("/savePlan")
    public Object savePlan(CheckPlanDO checkPlanDO) {
        boolean success = true;
        try{
            checkPlanDO.setCreateuser(userManagerUtils.getUserAlias());
           checkPlanService.saveOrUpdatePlan(checkPlanDO);
        }catch (Exception e){
            success = false;
            LOGGER.error("保存检查计划失败：",e);
        }
        return success;
    }

    /**
     * 获取检查计划列表
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryPlan")
    public Object queryPlan(@LayuiPageable Pageable pageable) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        return  addLayUiCode(repository.selectPaging("queryCheckPlanByPage", map, pageable));
    }
}
