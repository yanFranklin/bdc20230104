package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.building.FwZhsDO;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwYcHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwZhsFeignService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-14
 * @description 子户室管理
 */
@Controller
@RequestMapping("/fwzhs")
public class FwZhsController extends BaseController {

    @Autowired
    private FwZhsFeignService fwZhsFeignService;

    @Autowired
    private FwHsFeignService fwHsFeignService;
    @Autowired
    private FwYcHsFeignService fwYcHsFeignService;

    @RequestMapping("/list")
    public String list(Model model,String fwHsIndex){
        model.addAttribute("fwHsIndex",fwHsIndex);
        return "fwzhs/fwzhsList";
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param model
     * @param fwHsIndex
     * @return java.lang.String
     * @description
     */
    @RequestMapping("/info")
    public String info(Model model,String fwHsIndex,String fwZhsIndex,String hslx){
        model.addAttribute("fwHsIndex",fwHsIndex);
        model.addAttribute("fwZhsIndex",fwZhsIndex);
        model.addAttribute("hslx",hslx);
        return "fwzhs/fwzhsInfo";
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable
     * @param fwHsIndex
     * @return java.lang.Object
     * @description 
     */
    @ResponseBody
    @RequestMapping("/listbypage")
    public Object listByPage(@LayuiPageable Pageable pageable, String fwHsIndex){
        // 此处如果FWHSINDEX不传，不给错误提示，只是返回空
        if(StringUtils.isNotBlank(fwHsIndex)){
            return fwZhsFeignService.listFwZhsByPage(pageable,fwHsIndex,"");
        }
        return null;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwZhsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwZhsDO
     * @description 根据主键查询子户室
     */
    @RequestMapping("/querybyindex")
    @ResponseBody
    public FwZhsDO queryFwzhsByIndex(@NotBlank(message = "子户室主键不能为空") String fwZhsIndex){
        return fwZhsFeignService.getFwzhsByIndex(fwZhsIndex,"");
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwZhsDO
     * @return java.util.Map
     * @description 保存户室实体
     */
    @ResponseBody
    @RequestMapping("/save")
    public Map saveZhs(FwZhsDO fwZhsDO){
        Map resultMap = new HashMap();
        if(StringUtils.isBlank(fwZhsDO.getFwZhsIndex())){
            resultMap.put("data", fwZhsFeignService.insertZhs(fwZhsDO,""));
        }else{
            // 更新
            fwZhsFeignService.updateZhs(fwZhsDO,"");
        }
        resultMap.putAll(returnHtmlResult(true, "提交成功"));
        return resultMap;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwZhsIndexListJson
     * @return java.util.Map
     * @description 根据主键删除子户室
     */
    @ResponseBody
    @RequestMapping(value = "/batchdel")
    public Map batchDel(@NotBlank String fwZhsIndexListJson){
        try{
            List<String> fwZhsIndexList = JSONObject.parseObject(fwZhsIndexListJson, ArrayList.class);
            if(CollectionUtils.isNotEmpty(fwZhsIndexList)){
                fwZhsFeignService.batchDelFwzhs(fwZhsIndexList,"");
            }
            return returnHtmlResult(true, "删除成功");
        }catch (Exception e){
            return returnHtmlResult(false, "删除异常");
        }
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwZhsDO
     * @description 继承主户室
     */
    @ResponseBody
    @RequestMapping("/extendfwhs")
    public FwZhsDO extendFwhs(FwZhsDO fwZhsDO,@NotBlank(message = "户室主键不能为空") String fwHsIndex
            ,@NotBlank(message = "户室类型不能为空") String hslx){
        Object fwHsDO=null;
        if(StringUtils.equals("sc",hslx)){
            fwHsDO= fwHsFeignService.queryFwHsByFwHsIndex(fwHsIndex,"");
        }else if(StringUtils.equals("yc",hslx)){
            fwHsDO=fwYcHsFeignService.queryFwYcHsByFwHsIndex(fwHsIndex,"");
        }
        if(fwHsDO != null){
            BeanUtils.copyProperties(fwHsDO,fwZhsDO);
        }
        return fwZhsDO;
    }

}
