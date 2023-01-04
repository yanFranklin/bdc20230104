package cn.gtmap.realestate.check.web.main;

import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019-2-25
 * @description
 */
public class BaseController {
    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description Message提供者接口
     */
    @Autowired
    protected MessageProvider messageProvider;

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 用户信息工具
     */
    @Autowired
    protected UserManagerUtils userManagerUtils;

    @Autowired
    protected Repo repository;

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
