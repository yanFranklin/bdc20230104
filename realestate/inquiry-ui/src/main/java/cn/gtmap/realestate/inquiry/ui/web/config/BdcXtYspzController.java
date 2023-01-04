package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcRedisFeignService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统颜色配置
 *
 * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019-11-01
 */
@RestController
@RequestMapping("/rest/v1.0/yspz")
public class BdcXtYspzController {

    @Autowired
    private BdcRedisFeignService bdcRedisFeignService;

    /**
     * 存储系统状态的颜色配置
     *
     * @param colorPzList
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/status/color")
    @ResponseBody
    public void importColorPz(@RequestBody Map<String,Object> colorPzList){
        bdcRedisFeignService.addStringValue("xtPzColor", JSONObject.toJSONString(colorPzList));
    }

    @GetMapping("/status/color")
    @ResponseBody
    public Object queryColorPz(){
        String xtPzColor = bdcRedisFeignService.getStringValue("xtPzColor");
        return JSONObject.parseObject(xtPzColor);
    }
}
