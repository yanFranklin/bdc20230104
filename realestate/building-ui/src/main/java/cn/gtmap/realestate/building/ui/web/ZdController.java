package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO;
import cn.gtmap.realestate.common.core.service.feign.building.ZdFeignService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/16
 * @description 宗地页面相关服务
 */
@Controller
@Validated
@RequestMapping("zd")
public class ZdController extends BaseController {

    @Autowired
    private ZdFeignService zdFeignService;

    /**
     * @param model
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 宗地分页查询页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        return "zd/zdList";
    }

    /**
     * @param model
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 宗地分页查询页面
     */
    @RequestMapping(value = "/picklist")
    public String picklist(Model model) {
        return "zd/pickZdlist";
    }

    /**
     * @param pageable
     * @param bdcdyh
     * @param djh
     * @param qlr
     * @param tdzl
     * @return java.lang.Object
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询
     */
    @ResponseBody
    @RequestMapping(value = "/listbypage")
    public Object listZdByPageJson(@LayuiPageable Pageable pageable, String bdcdyh, String djh, String tdzl, String qlr) {
        //处理前台传递的分页参数
        Map<String,Object> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(bdcdyh)){
            paramMap.put("bdcdyh",StringUtils.deleteWhitespace(bdcdyh));
        }
        if(StringUtils.isNotBlank(djh)){
            paramMap.put("djh",StringUtils.deleteWhitespace(djh));
        }
        if(StringUtils.isNotBlank(tdzl)){
            paramMap.put("zl",tdzl);
        }
        if(StringUtils.isNotBlank(qlr)){
            paramMap.put("qlr",qlr);
        }
        return zdFeignService.listZdByPageJson(pageable, JSONObject.toJSONString(paramMap));
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO
     * @description 根据地籍号查询宗地地籍信息
     */
    @ResponseBody
    @RequestMapping("/queryzddjdcb")
    public ZdDjdcbDO queryZdDjdcb(@NotBlank(message = "隶属宗地不能为空") String djh){
        return zdFeignService.queryZdByDjh(djh,"");
    }
}