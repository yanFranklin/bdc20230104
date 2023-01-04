package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.dto.building.YcScHsGlRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.YcScHsZzglRequestDTO;
import cn.gtmap.realestate.common.core.service.feign.building.FwYcHsFeignService;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2018/12/11
 * @description 房屋户室关联
 */
@Controller
@RequestMapping("fwhsgl")
public class FwHsGlController extends BaseController {
    @Autowired
    FwYcHsFeignService fwYcHsFeignService;

    /**
     * @param model
     * @param fwDcbIndex
     * @return java.lang.String
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 预测实测户室挂接
     */
    @RequestMapping("/ycsc")
    public String list(Model model, @NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex,String qjgldm) {
        model.addAttribute("fwDcbIndex", fwDcbIndex);
        model.addAttribute("qjgldm", qjgldm);
        return "fwhs/ycscgl";
    }

    /**
     * 预测户室关联实测户室
     *
     * @param ycScHsGlDTO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/ycschsgl")
    public Map ycscHsGl(YcScHsGlRequestDTO ycScHsGlDTO) {
        if (CollectionUtils.isEmpty(ycScHsGlDTO.getYchsIndexList())) {
            return returnHtmlResult(false, "预测户室主键不能为空");
        }
        fwYcHsFeignService.ycscHsGl(ycScHsGlDTO);
        return returnHtmlResult(true, "成功");
    }

    /**
     * 取消实测户室关联的预测户室
     *
     * @param ycScHsGlDTO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/cancleycscgl")
    public Map cancleYcscGl(YcScHsGlRequestDTO ycScHsGlDTO) {
        fwYcHsFeignService.ycscHsQxGl(ycScHsGlDTO);
        return returnHtmlResult(true, "成功");
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.util.Map
     * @description 整幢取消关联
     */
    @ResponseBody
    @RequestMapping(value = "/canclezzgl")
    public Map cancleZzYcscGl(@NotBlank(message = "幢主键不能为空") String fwDcbIndex,String qjgldm){
        fwYcHsFeignService.ycscZzQxgl(fwDcbIndex,qjgldm);
        return returnHtmlResult(true,"取消关联成功");
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param model
     * @param fwDcbIndex
     * @return java.lang.String
     * @description 整幢关联
     */
    @RequestMapping("/zzglview")
    public String toZzglView(Model model,
            @NotBlank(message = "幢主键不能为空")String fwDcbIndex,String qjgldm){
        model.addAttribute("fwDcbIndex",fwDcbIndex);
        model.addAttribute("qjgldm",qjgldm);
        return "fwhs/ycscglZz";
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ycScHsZzglRequestDTO
     * @return java.util.Map
     * @description 整幢关联
     */
    @ResponseBody
    @RequestMapping("/zzgl")
    public Map zzgl(YcScHsZzglRequestDTO ycScHsZzglRequestDTO){
        fwYcHsFeignService.ycscZzgl(ycScHsZzglRequestDTO);
        return returnHtmlResult(true,"关联成功");
    }


}
