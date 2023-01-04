package cn.gtmap.realestate.etl.web.main;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019-04-23
 * @description
 */
@Validated
public class BaseController {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    /**
     * 处理分页结构数据 添加code字段
     *
     * @param page
     * @return
     */
    public Object addLayUiCode(Page page) {
        Map map = null;
        if (page != null) {
            map = JSONObject.parseObject(JSONObject.toJSONString(page), Map.class);
            if (map != null) {
                map.put("msg", "成功");
            }
            if (map == null) {
                map = new HashMap(1);
                map.put("msg", "无数据");
            }
            map.put("code", 0);
        }
        return map;
    }
}
