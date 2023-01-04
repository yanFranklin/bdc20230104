package cn.gtmap.realestate.exchange.core.support.mybatis.page.repository;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2017/12/7.
 * @description
 */
public class TotalRepository extends Repository {
    @Autowired
    private HttpServletRequest request;

    private static final String LOADTOTAL = "loadTotal";

    /**
     * 对数据进行分页查询
     *
     * @param statement
     * @param parameter
     * @param pageable
     * @param <T>
     * @return
     */
    @Override
    public <T> Page<T> selectPaging(String statement, Object parameter, Pageable pageable) {
        Map map = request.getParameterMap();
        Map paramMap = new HashMap();
        if (parameter != null) {
            paramMap = JSONObject.parseObject(JSONObject.toJSONString(parameter), Map.class);
        }
        if (map != null) {
            if (map.containsKey(LOADTOTAL)) {
                String loadTotal;
                if (map.get(LOADTOTAL).getClass().isArray()) {
                    loadTotal = ((String[]) map.get(LOADTOTAL))[0];
                } else {
                    loadTotal = map.get(LOADTOTAL).toString();
                }
                paramMap.put(LOADTOTAL, StringUtils.isNotBlank(loadTotal) ? BooleanUtils.toBoolean(loadTotal) : Boolean.FALSE);
            }
        }
        //增加排序功能
        return super.selectPaging(statement, orderBySort(pageable, paramMap), pageable);
    }

}
