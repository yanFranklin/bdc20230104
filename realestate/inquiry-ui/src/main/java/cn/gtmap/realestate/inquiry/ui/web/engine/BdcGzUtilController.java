package cn.gtmap.realestate.inquiry.ui.web.engine;

import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZdbFeignService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/6
 * @description
 */
@RestController
@RequestMapping("bdcGzUtil")
public class BdcGzUtilController {
    @Autowired
    private BdcGzZdbFeignService bdcGzZdbFeignService;
    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param selectName,classNam
     *@return zdjson
     *@description 获取下拉框字典表数据
     */
    @RequestMapping(value = "/getSelect")
    public String listZdb(String selectName) throws ClassNotFoundException {
        Class<Object> className = (Class<Object>) Class.forName(selectName);
        List<Map> maps = bdcGzZdbFeignService.getZdTable(selectName,className);
        return JSON.toJSONString(maps);
    }
}
