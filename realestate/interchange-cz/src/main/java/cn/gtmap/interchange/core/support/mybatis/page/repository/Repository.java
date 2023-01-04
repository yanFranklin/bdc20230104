package cn.gtmap.interchange.core.support.mybatis.page.repository;

import cn.gtmap.interchange.core.support.mybatis.page.PaginationInterceptor;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

/**
 * @author <a href="mailto:zjh527@gmail.com">loafer</a>
 * @version 1.0 2014-2-17
 */

public class Repository extends SqlSessionDaoSupport implements Repo {
    private static final Logger LOGGER = LoggerFactory.getLogger(Repository.class);
    private static final String LOADTOTAL = "loadTotal";

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return this.getSqlSession().selectOne(statement, parameter);
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        return this.getSqlSession().selectList(statement, parameter);
    }

    @Override
    public <T> Page<T> selectPaging(String statement, Object parameter, int offset, int limit) {
        RowBounds rowBounds = new RowBounds(offset, limit);
        List<T> rows = this.getSqlSession().selectList(statement, parameter, rowBounds);

        int total = PaginationInterceptor.getPaginationTotal();

        Pageable pageable = new PageRequest(offset, limit);
        return new PageImpl(rows, pageable, total);
    }

    @Override
    public <T> Page<T> selectPaging(String statement, Object parameter, Pageable pageable) {
        RowBounds rowBounds = new RowBounds(pageable.getPageNumber(), pageable.getPageSize());
        Map<String, Object> map = new HashMap<String, Object>();
        if (parameter != null) {
            map = JSONObject.parseObject(JSONObject.toJSONString(parameter), Map.class);
        }
        List<T> rows = this.getSqlSession().selectList(statement, orderBySort(pageable, map), rowBounds);
        int total = PaginationInterceptor.getPaginationTotal();
        /*
        lst  添加判断是否是没有查询总页数的(东北区域)  对查询总页数的原逻辑没影响
        东北版新增拦截器赋值-1
        */
        if (total == -1) {
            if (CollectionUtils.isNotEmpty(rows)) {
                if (rows.size() == pageable.getPageSize()) {
                    total = (pageable.getPageNumber() + 1) * pageable.getPageSize() + 1;
                } else {
                    total = (pageable.getPageNumber() + 1) * pageable.getPageSize();
                }
            } else {
                total = 0;
            }
        }

        return new PageImpl(rows, pageable, total);
    }


    /**
     * 查询zmd台账的数据  增加判断数据是否为 null
     *
     * @param statement
     * @param parameter
     * @param pageable
     * @param flag
     * @return
     * @author wuhongrui
     * @description
     */
    @Override
    public <T> Page<T> selectPaging(String statement, Object parameter, Pageable pageable, String flag) {
        RowBounds rowBounds = new RowBounds(pageable.getPageNumber(), pageable.getPageSize());
        List<T> rows = this.getSqlSession().selectList(statement, parameter, rowBounds);
        JSONObject obj;
        JSONArray arr = new JSONArray();
        for (Iterator iterator = rows.iterator(); iterator.hasNext(); ) {
            T t = (T) iterator.next();
            obj = new JSONObject((Map<String, Object>) t);
            arr.add(obj);
        }

        List<T> result = new ArrayList();
        Map<Object, Object> map;


        for (int i = 0; i < arr.size(); i++) {
            JSONObject temp = arr.getJSONObject(i);
            map = new HashMap();
            for (Map.Entry<String, Object> entry : temp.entrySet()) {

                if (null == entry.getValue() || "".equals(entry.getValue())) {
                    map.put(entry.getKey(), "");
                } else {
                    map.put((T) entry.getKey(), entry.getValue());
                }

            }
            result.add((T) map);
        }

        int total = PaginationInterceptor.getPaginationTotal();
        return new PageImpl(result, pageable, total);
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
        return this.getSqlSession().selectMap(statement, parameter, mapKey);
    }

    @Override
    public int insert(String statement, Object parameter) {
        return this.getSqlSession().insert(statement, parameter);
    }

    @Override
    public int update(String statement, Object parameter) {
        return this.getSqlSession().update(statement, parameter);
    }

    @Override
    public int delete(String statement, Object parameter) {
        return this.getSqlSession().delete(statement, parameter);
    }

    /**
     * @param pageable
     * @param map
     * @return java.util.Map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 增加排序
     */
    public Map orderBySort(Pageable pageable, Map map) {
        if (pageable!=null && pageable.getSort() != null) {
            String sortParameter = "";
            String sort = String.valueOf(pageable.getSort());
            String disposeSort = HumpToUnderline(sort.replace(":", ""));
            if (disposeSort.indexOf("_A_S_C") != -1) {
                sortParameter = disposeSort.replace("_A_S_C", "ASC");
            }
            if (disposeSort.indexOf("_D_E_S_C") != -1) {
                sortParameter = disposeSort.replace("_D_E_S_C", "DESC");
            }
            map.put("sort", sortParameter);
        }
        return map;
    }

    /**
     * @param para
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 驼峰式命名法转下划线命名法
     */
    public String HumpToUnderline(String para) {
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;//定位
        for (int i = 0; i < para.length(); i++) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "_");
                temp += 1;
            }
        }
        return sb.toString().toUpperCase();
    }
}
