package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.service.feign.building.ZrzFeignService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-11
 * @description 自然幢
 */
@Controller
@RequestMapping("/zrz")
public class ZrzController {

    @Autowired
    private ZrzFeignService zrzFeignService;


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param model
     * @param lszd
     * @return java.lang.String
     * @description 选择自然幢
     */
    @RequestMapping("/picklist")
    public String pickZrzList(Model model, String lszd){
        model.addAttribute("lszd",lszd);
        return "zrz/pickZrzList";
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable
     * @param lszd
     * @param zrzh
     * @param zldz
     * @return java.lang.Object
     * @description
     */
    @ResponseBody
    @RequestMapping("/listbypage")
    public Object listZrzByPage(@LayuiPageable Pageable pageable,String lszd,String zrzh,String zldz){
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(lszd)) {
            paramMap.put("lszd", StringUtils.deleteWhitespace(lszd));
        }
        if (StringUtils.isNotBlank(zrzh)) {
            paramMap.put("zrzh", StringUtils.deleteWhitespace(zrzh));
        }
        if (StringUtils.isNotBlank(zldz)) {
            paramMap.put("zldz", StringUtils.deleteWhitespace(zldz));
        }
        return zrzFeignService.listZrzByPage(pageable, JSONObject.toJSONString(paramMap));
    }
}
