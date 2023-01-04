package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.common.core.domain.building.FwJsydzrzxxDO;
import cn.gtmap.realestate.common.core.domain.building.ZdJsydsybDO;
import cn.gtmap.realestate.common.core.qo.building.FwJsydzrzxxQO;
import cn.gtmap.realestate.common.core.service.feign.building.ZdJsydLhxxFeignService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/22
 * @description 宗地建设用地量化信息
 */
@Controller
@RequestMapping("/zdjsydlhxx")
public class ZdJsydLhxxController {

    @Autowired
    ZdJsydLhxxFeignService zdJsydLhxxFeignService;

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  宗地建设用地量化信息
     */
    @RequestMapping("/view")
    public String initZdJsydLhxxView(Model model, @NotBlank(message = "地籍号不能为空")
            String djh) {
        model.addAttribute("djh", djh);
        return "lhxx/zdjsydLhxx";
    }


    /**
     * @param djh 地籍号
     * @return 宗地建设用地使用表
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  查询宗地建设用地使用表
     */
    @ResponseBody
    @RequestMapping("/zdjsydsyb")
    public ZdJsydsybDO queryZdJsydsybByDjh(String djh) {
        return zdJsydLhxxFeignService.queryZdJsydsybByDjh(djh);
    }

    /**
     * @param fwJsydzrzxxQO 房屋建设用地自然幢信息查询QO
     * @return 房屋建设用地自然幢信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询房屋建设用地自然幢信息
     */
    @ResponseBody
    @RequestMapping("/jsydzrz")
    public List<FwJsydzrzxxDO> listFwJsydzrzxx(FwJsydzrzxxQO fwJsydzrzxxQO) {
        return zdJsydLhxxFeignService.listFwJsydzrzxx(fwJsydzrzxxQO);
    }


}
