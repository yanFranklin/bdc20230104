package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.dto.building.ZlsxDTO;
import cn.gtmap.realestate.common.core.service.feign.building.ZlsxFeignService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-10
 * @description 坐落刷新
 */
@Controller
@RequestMapping("/zlsx")
public class ZlsxController extends BaseController {

    @Autowired
    private ZlsxFeignService zlsxFeignService;

    @RequestMapping("/view")
    public String zlsxView(){
        return "manage/zlsx";
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwDcbIndex
     * @return java.util.Map
     * @description 使用默认配置规则 刷新
     */
    @ResponseBody
    @RequestMapping("/ljz/byconfig")
    public Map ljzZlsxByConfig(@NotBlank(message = "逻辑幢主键不能为空") String fwDcbIndex,String qjgldm){
        ZlsxDTO zlsxDTO = new ZlsxDTO();
        zlsxDTO.setFwDcbIndex(fwDcbIndex);
        zlsxDTO.setQjgldm(qjgldm);
        zlsxFeignService.zlsxByConfig(zlsxDTO);
        return returnHtmlResult(true,"刷新成功");
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.util.List
     * @description 获取配置
     */
    @ResponseBody
    @RequestMapping("/getconfig")
    public ZlsxDTO getConfig(){
        return zlsxFeignService.getConfig();
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zlsxDTO
     * @return java.util.Map
     * @description 页面配置刷新规则刷新
     */
    @ResponseBody
    @RequestMapping("")
    public Map zlsx(ZlsxDTO zlsxDTO){
        zlsxFeignService.zlsxByRule(zlsxDTO);
        return returnHtmlResult(true,"刷新成功");
    }
}
