package cn.gtmap.realestate.exchange.core.support.mybatis.page.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zjh527@gmail.com">loafer</a>
 * @version 1.0 2014-2-17
 */
public interface Repo {
    /**
     * 单条数据查询
     *
     * @param statement 查询id
     * @param parameter 参数
     * @param <T>       数据类型
     * @return 数据
     */
    <T> T selectOne(String statement, Object parameter);

    /**
     * 查询数据列表
     *
     * @param statement 查询id
     * @param parameter 参数
     * @param <E>       list数据类型
     * @return list结果集
     */
    <E> List<E> selectList(String statement, Object parameter);

    /**
     * 分页查询数据
     *
     * @param statement 查询id
     * @param parameter 参数
     * @param offset    页数
     * @param limit     条数
     * @param <T>       page类型
     * @return 页面信息
     */
    <T> Page<T> selectPaging(String statement, Object parameter, int offset, int limit);

    /**
     * 分页查询数据
     *
     * @param statement 查询id
     * @param parameter 参数
     * @param pageable  分页信息
     * @param <T>       page类型
     * @return 页面信息
     */
    <T> Page<T> selectPaging(String statement, Object parameter, Pageable pageable);


    /**
     * 分页查询数据
     *
     * @param statement 查询id
     * @param parameter 参数
     * @param pageable  分页信息
     * @param flag      标志
     * @param <T>       page类型
     * @return 页面信息
     */
    <T> Page<T> selectPaging(String statement, Object parameter, Pageable pageable, String flag);

    /**
     * 按map形式返回数据集
     *
     * @param statement 查询id
     * @param parameter 参数
     * @param mapKey    map键值
     * @param <K>       key
     * @param <V>       value
     * @return map结果集
     */
    <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey);

    /**
     * 插入数据
     *
     * @param statement 查询id
     * @param parameter 参数
     * @return 操作条数
     */
    int insert(String statement, Object parameter);

    /**
     * 更新数据
     *
     * @param statement 查询id
     * @param parameter 参数
     * @return 操作条数
     */
    int update(String statement, Object parameter);

    /**
     * 删除数据
     *
     * @param statement 查询id
     * @param parameter 参数
     * @return 操作条数
     */
    int delete(String statement, Object parameter);

}
