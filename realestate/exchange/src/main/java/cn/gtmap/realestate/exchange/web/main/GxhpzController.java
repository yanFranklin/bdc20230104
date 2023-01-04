package cn.gtmap.realestate.exchange.web.main;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2020/1/17
 * @description 个性化配置
 */
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/gxhpz")
public class GxhpzController {
    /**
     * Redis操作
     */
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 用户信息
     */
    @Autowired
    private UserManagerUtils userManagerUtils;

    /**
     * @param gxhMap
     * @param url
     * @return
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 保存用户个性化配置
     */
    @ResponseBody
    @PostMapping("/save/table")
    public String gxhpzSave(@RequestBody List<Map> gxhList, @RequestParam("tableid") String tableid, @RequestParam("url") String url) {
        return gxhpzSave(gxhList, tableid, url, "table");
    }

    /**
     * @param gxhMap
     * @param url
     * @return
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 保存用户个性化配置
     */
    @ResponseBody
    @PostMapping("/save/form")
    public String gxhpzSaveForm(@RequestBody List<Map> gxhList, @RequestParam("formid") String formid, @RequestParam("url") String url) {
        return gxhpzSave(gxhList, formid, url, "form");
    }

    /**
     * @param url
     * @return
     * @description 获取用户个性化配置
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @ResponseBody
    @GetMapping("/query/table")
    public String gxhpzQuery(@RequestParam("tableid") String tableid ,@RequestParam("url") String url) {
        String configJson = queryGxhpz(url);
        if (StringUtils.isNotEmpty(configJson)){
            JSONObject configObject = JSONObject.parseObject(configJson);

            if (StringUtils.isNotEmpty(configObject.getString("table"))){
                return configObject.getJSONObject("table").getString(tableid);
            }
        }
        return null;
    }

    /**
     * @param url
     * @return
     * @description 获取用户个性化配置
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @ResponseBody
    @GetMapping("/query/form")
    public String gxhpzQueryForm(@RequestParam("formid") String formid ,@RequestParam("url") String url) {
        String configJson = queryGxhpz(url);
        if (StringUtils.isNotEmpty(configJson)){
            JSONObject configObject = JSONObject.parseObject(configJson);

            if (StringUtils.isNotEmpty(configObject.getString("form"))){
                return configObject.getJSONObject("form").getString(formid);
            }
        }
        return null;
    }

    private String gxhpzSave(List<Map> gxhList, String tableid, String url, String type) {
        if (gxhList == null || StringUtils.isBlank(url)) {
            throw new AppException("参数缺失，无法保存个性化配置！");
        }
        String gxhJson;

        String json = queryGxhpz(url);
        if (StringUtils.isNotEmpty(json)) {
            JSONObject gxhpz = JSONObject.parseObject(json);
            if (StringUtils.isNotEmpty(gxhpz.getString(type))) {
                JSONObject table = gxhpz.getJSONObject(type);
                table.put(tableid, gxhList);

                gxhpz.put(type, table);
            } else {
                JSONObject table = new JSONObject();
                table.put(tableid, gxhList);
                gxhpz.put(type, table);
            }
            gxhJson = gxhpz.toJSONString();
        } else {
            JSONObject config = new JSONObject();
            JSONObject table = new JSONObject();
            table.put(tableid, gxhList);
            config.put(type, table);

            gxhJson = config.toJSONString();
        }

        String username = userManagerUtils.getCurrentUserName();
        String key = username + url;
        // 保存个性化信息
        return redisUtils.setStringValue(key, gxhJson);
    }

    private String queryGxhpz(String url) {
        if (StringUtils.isBlank(url)) {
            throw new AppException("参数缺失，无法获得个性化配置！");
        }
        String username = userManagerUtils.getCurrentUserName();
        String key = username + url;
        // 获取redis中保存的个性化设置
        return redisUtils.getStringValue(key);
    }
}