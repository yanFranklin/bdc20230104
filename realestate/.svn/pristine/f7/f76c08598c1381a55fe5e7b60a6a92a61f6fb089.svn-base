package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dto.building.FttdmjRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.LjzJzmjRequestDTO;
import cn.gtmap.realestate.common.core.service.feign.building.CalculatedAreaFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwLjzFeginService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019/1/15
 * @description
 */
@Controller
@Validated
@RequestMapping("calculated")
public class CalculateController extends BaseController {
    @Autowired
    private CalculatedAreaFeignService calculatedAreaFeignService;
    @Autowired
    private FwLjzFeginService fwLjzFeginService;


    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return java.lang.String
     * @description 逻辑幢建筑面积计算页面
     */
    @RequestMapping("ljzzjmjview")
    public String calculateLjzJzmjView(){
        return "calculate/ljzJzmj";
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return java.lang.String
     * @description 分摊土地面积计算页面
     */
    @RequestMapping("fttdview")
    public String calculateFttdmjView(){
        return "calculate/fttdmj";
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param
     * @return java.lang.String
     * @description 分摊土地面积计算页面
     */
    @RequestMapping("/lpbfttdview")
    public String lpbCalculateFttdmjView(Model model, @NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex,String qjgldm){
        model.addAttribute("fwDcbIndex",fwDcbIndex);
        FwLjzDO fwLjzDO=fwLjzFeginService.queryLjzByFwDcbIndex(fwDcbIndex,qjgldm);
        if(fwLjzDO!=null){
            model.addAttribute("djh",fwLjzDO.getLszd());
            model.addAttribute("qjgldm",qjgldm);
        }
        return "calculate/lpbfttdmj";
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param ljzJzmjRequestDTO
     * @return java.util.Map
     * @description 计算逻辑幢建筑面积
     */
    @ResponseBody
    @RequestMapping("ljzzjmj")
    public Map calculateLjzJzmj(LjzJzmjRequestDTO ljzJzmjRequestDTO){
        calculatedAreaFeignService.calculatedLjzJzmj(ljzJzmjRequestDTO);
        return returnHtmlResult(true, "计算完成");
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwDcbIndex
     * @return void
     * @description 计算逻辑幢建筑面积(通过配置)
     */
    @ResponseBody
    @RequestMapping("ljzzjmj/byconfig")
    public Map calculateLjzJzmjByConfig(@NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex,String qjgldm){
        List<String> fwDcbIndexList=new ArrayList<>();
        fwDcbIndexList.add(fwDcbIndex);
        LjzJzmjRequestDTO ljzJzmjRequestDTO=new LjzJzmjRequestDTO();
        ljzJzmjRequestDTO.setFwDcbIndex(fwDcbIndexList);
        ljzJzmjRequestDTO.setQjgldm(qjgldm);
        calculatedAreaFeignService.calculatedLjzJzmjByConfig(ljzJzmjRequestDTO);
        return returnHtmlResult(true, "计算完成");
    }


    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param  fttdmjRequestDTO
     * @return java.util.Map
     * @description 计算分摊土地面积
     */
    @ResponseBody
    @RequestMapping("fttdmj")
    public Map calculateFttdmj(FttdmjRequestDTO fttdmjRequestDTO){
        int result=calculatedAreaFeignService.calculatedFttdmj(fttdmjRequestDTO);
        if(result!=0){
            return returnHtmlResult(true, "计算完成");
        }else {
            return returnHtmlResult(false, "计算失败，请检查数据");
        }
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param  fwDcbIndex
     * @return java.util.Map
     * @description 计算分摊土地面积
     */
    @ResponseBody
    @RequestMapping("fttdmj/byconfig")
    public Map calculateFttdmjByConfig(@NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex){
        List<String> fwDcbIndexList=new ArrayList<>();
        fwDcbIndexList.add(fwDcbIndex);
        FttdmjRequestDTO fttdmjRequestDTO=new FttdmjRequestDTO();
        fttdmjRequestDTO.setFwDcbIndex(fwDcbIndexList);
        calculatedAreaFeignService.calculatedFttdmjByConfig(fttdmjRequestDTO);
        return returnHtmlResult(true, "计算完成");
    }




}