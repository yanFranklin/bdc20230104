package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.FwHsBgxxDO;
import cn.gtmap.realestate.common.core.service.feign.building.EntityFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwhsBgFeignService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/16
 * @description 变更信息
 */
@Controller
@Validated
@RequestMapping("bgxx")
public class BgxxController extends BaseController {

    @Autowired
    private FwhsBgFeignService fwhsBgFeignService;

    @Autowired
    private EntityFeignService entityFeignService;

    /**
     * 变更信息页面
     * @param model
     * @param fwHsBgxxDO
     * @param bgbh
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @return
     */
    @RequestMapping(value = "/infobgxx")
    public String infobgxx(Model model,FwHsBgxxDO fwHsBgxxDO,String bgbh) {
        model.addAttribute("fwHsBgxxDO",fwHsBgxxDO);
        model.addAttribute("bgbh",bgbh);
        return "bgxx/bgxxInfo";
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHsBgxxDO
     * @return void
     * @description
     */
    @ResponseBody
    @RequestMapping(value = "/insertbgxx")
    public void insertFwHsBgxx(FwHsBgxxDO fwHsBgxxDO){
        fwhsBgFeignService.insertFwHsBgxx(fwHsBgxxDO,"");
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param jsonData
     * @return java.util.Map
     * @description 更新变更信息
     */
    @ResponseBody
    @RequestMapping(value = "/update")
    public void updateFwHsBgxx(@NotBlank(message = "参数不能为空")String jsonData){
        // 更新
        entityFeignService.updateEntityByJson(jsonData,FwHsBgxxDO.class.getName());
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bgbh
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsBgxxDO
     * @description 根据变更编号查询变更信息
     */
    @ResponseBody
    @RequestMapping(value = "/getbgxx")
    public FwHsBgxxDO getFwHsBgxxByBgbh(String bgbh){
        return fwhsBgFeignService.getFwHsBgxxByBgbh(bgbh,"");
    }

}