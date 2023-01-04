package cn.gtmap.realestate.exchange.web;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/4
 * @description
 */
public class BaseController {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Layui请求返回成功编码
     */
    public final static int LAYUI_SUCCESS_CODE = 0;
    /**
     * Layui请求返回错误编码
     */
    public final static int LAYUI_ERROR_CODE = 1;

    public static HashMap returnSuccesResult(Boolean success, String msg) {
        HashMap resultMap = new HashMap(1);
        resultMap.put("success", success);
        resultMap.put("msg", msg);
        return resultMap;
    }


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
            map.put("code", LAYUI_SUCCESS_CODE);
        }
        return map;
    }

    /**
     * 处理list结构数据 添加code字段
     *
     * @param list
     * @return
     */
    public Object addLayUiCode(List list) {
        Map<String, Object> map = new HashMap(3);
        if (CollectionUtils.isEmpty(list)) {
            map.put("msg", "无数据");
        } else {
            map.put("msg", "成功");
            map.put("data", list);
        }
        map.put("code", LAYUI_SUCCESS_CODE);

        return map;
    }

    /**
     * @param msg 错误信息
     * @return
     */
    public Object addLayUiErrorCode(String msg) {
        Map map = new HashMap(4);
        map.put("code", LAYUI_ERROR_CODE);
        map.put("msg", msg);
        return map;
    }

    /**
     * @param pageable
     * @param map
     * @return java.util.Map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 处理排序信息，处理pageable中sort传入的参数，
     * 将排序信息和查询信息整理成一个map作为查询条件
     */

    public static Map pageableSort(Pageable pageable, Map map) {
        if (pageable.getSort() != null && !"UNSORTED".equals(String.valueOf(pageable.getSort()))) {
            String sort = String.valueOf(pageable.getSort());
            String sortParameter = sort.replace(":", "");
            map.put("sortParameter", sortParameter);
        }
        return map;
    }

    /**
     * sly 前台分页默认从1开始，后台分页默认从0开始，在这里做减1处理
     *
     * @param pageable
     * @return
     */
    public static Pageable delPageRequest(Pageable pageable) {
        if (pageable.getPageNumber() > 0) {
            return new PageRequest(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        } else {
            return pageable;
        }
    }

}
