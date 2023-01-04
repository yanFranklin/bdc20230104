package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.dto.building.DcxxDTO;
import cn.gtmap.realestate.common.core.service.feign.building.FwLjzFeginService;
import cn.gtmap.realestate.common.core.service.feign.building.FwDcxxFeignService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-15
 * @description
 */
@Controller
@RequestMapping("/dcxx")
public class DcxxController extends BaseController {

    @Autowired
    private FwLjzFeginService fwLjzFeginService;

    @Autowired
    private FwDcxxFeignService fwDcxxFeignService;

    /**
     * @param model
     * @param bdcdyfwlx
     * @param fwIndex
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 初始化 编辑页面
     */
    @RequestMapping("/initsave")
    public String initSave(Model model,
                           @NotBlank(message = "不动产单元类型不能为空") String bdcdyfwlx,
                           @NotBlank(message = "房屋主键不能为空") String fwIndex) {
        model.addAttribute("bdcdyfwlx", bdcdyfwlx);
        model.addAttribute("fwIndex", fwIndex);
        return "dcxx/saveDcxx";
    }


    /**
     * @param bdcdyfwlx
     * @param fwIndex
     * @return cn.gtmap.realestate.common.core.dto.building.DcxxDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询调查信息
     */
    @RequestMapping("/querydcxx")
    @ResponseBody
    public DcxxDTO queryDcxxByIndex(@NotBlank(message = "不动产单元类型不能为空") String bdcdyfwlx,
                                    @NotBlank(message = "房屋主键不能为空") String fwIndex) {
        return fwDcxxFeignService.queryFwDcxx(bdcdyfwlx, fwIndex,"");
    }


    /**
     * @param dcxxDTO
     * @return java.util.Map
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存调查信息
     */
    @ResponseBody
    @RequestMapping("/savedcxx")
    public Map saveDcxx(DcxxDTO dcxxDTO) {
        fwDcxxFeignService.updateFwDcxx(dcxxDTO);
        return returnHtmlResult(true, "提交成功");
    }

}
