package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.common.core.domain.building.ZdJtcyDO;
import cn.gtmap.realestate.common.core.service.feign.building.ZdJtcyFeignService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/11
 * @description 宗地家庭成员
 */
@Controller
@RequestMapping("/zdjtcy")
public class ZdJtcyController {

    @Autowired
    ZdJtcyFeignService zdJtcyFeignService;

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  家庭成员页面
     */
    @RequestMapping("/view")
    public String initZdJtcyView(Model model, @NotBlank(message = "地籍号不能为空")
            String djh) {
        model.addAttribute("djh", djh);
        return "zd/zdJtcy";
    }

    /**
      * @param djh 地籍号
      * @return 宗地家庭成员信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据地籍号获取家庭成员
      */
    @ResponseBody
    @RequestMapping("")
    public List<ZdJtcyDO> listZdJtcyByDjh(String djh) {
        return zdJtcyFeignService.listZdJtcyByDjh(djh);
    }
}
