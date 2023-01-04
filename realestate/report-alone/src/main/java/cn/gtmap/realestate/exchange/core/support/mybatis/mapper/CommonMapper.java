package cn.gtmap.realestate.exchange.core.support.mybatis.mapper;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 这个仍然是接口类，不需要被继承，可以直接用
 *
 * @author liuzh
 */
public interface CommonMapper {
    /**
     * 根据参数进行查询,record可以是Class<?>类型
     * <br>查询条件为属性String类型不为空，其他类型!=null时
     * <br>where property = ? and property2 = ? 条件
     *
     * @param record
     * @param <T>
     * @return
     */
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @SelectProvider(type = CommonProvider.class, method = "select")
    <T> List<Map<String, Object>> select(@Param("record") T record);

    /**
     * 根据参数进行查询总数,record可以是Class<?>类型
     * <br>查询条件为属性String类型不为空，其他类型!=null时
     * <br>where property = ? and property2 = ? 条件
     *
     * @param record
     * @param <T>
     * @return
     */
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @SelectProvider(type = CommonProvider.class, method = "count")
    <T> int count(@Param("record") T record);

    /**
     * 根据主键查询结果，主键不能为null或空
     *
     * @param entityClass
     * @param key
     * @param <T>
     * @return
     */
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @SelectProvider(type = CommonProvider.class, method = "selectByPrimaryKey")
    <T> Map<String, Object> selectByPrimaryKey(@Param("entityClass") Class<T> entityClass, @Param("key") Object key);

    /**
     * 插入数据库，主键字段没有值的时候不会出现在sql中
     * <br>如果是自增主键，会自动获取值
     * <br>如果是自增主键，并且该主键属性有值，会使用主键的属性值，不会使用自增
     *
     * @param record
     * @param <T>
     * @return
     */
    @InsertProvider(type = CommonProvider.class, method = "insert")
    <T> int insert(@Param("record") T record);

    /**
     * 插入非空字段，其他和上面方法类似
     *
     * @param record
     * @param <T>
     * @return
     */
    @InsertProvider(type = CommonProvider.class, method = "insertSelective")
    <T> int insertSelective(@Param("record") T record);

    /**
     * 批量插入非空字段，其他和上面方法类似
     *
     * @param record
     * @param <T>
     * @return
     */
    @InsertProvider(type = CommonProvider.class, method = "insertBatchSelective")
    <T> int insertBatchSelective(@Param("record") List<T> record);

    /**
     * 根据条件进行删除，条件不能为空，并且必须有至少一个条件才能删除
     * <br>该方法不能直接删除全部数据
     *
     * @param record
     * @param <T>
     * @return
     */
    @DeleteProvider(type = CommonProvider.class, method = "delete")
    <T> int delete(@Param("record") T record);

    /**
     * 根据主键进行删除，主键不能为null或空
     *
     * @param entityClass
     * @param key
     * @param <T>
     * @return
     */
    @DeleteProvider(type = CommonProvider.class, method = "deleteByPrimaryKey")
    <T> int deleteByPrimaryKey(@Param("entityClass") Class<T> entityClass, @Param("key") Object key);

    /**
     * 根据主键更新全部字段，空字段也会更新数据库
     *
     * @param record
     * @param <T>
     * @return
     */
    @UpdateProvider(type = CommonProvider.class, method = "updateByPrimaryKey")
    <T> int updateByPrimaryKey(@Param("record") T record);

    /**
     * lst 根据主键更新全部字段，空字段也会更新数据库
     * null也会更新
     *
     * @param record
     * @param <T>
     * @return
     */
    @UpdateProvider(type = CommonProvider.class, method = "updateByPrimaryKeyNull")
    <T> int updateByPrimaryKeyNull(@Param("record") T record);

    /**
     * 根据主键更新非空属性字段，不能给数据库数据设置null或空
     *
     * @param record
     * @param <T>
     * @return
     */
    @UpdateProvider(type = CommonProvider.class, method = "updateByPrimaryKeySelective")
    <T> int updateByPrimaryKeySelective(@Param("record") T record);

    /**
     * 通过Example类来查询count
     *
     * @param entityClass
     * @param example
     * @param <T>
     * @return
     */
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @SelectProvider(type = CommonProvider.class, method = "countByExample")
    <T> int countByExample(@Param("entityClass") Class<T> entityClass, @Param("example") Object example);

    /**
     * 通过Example删除
     *
     * @param entityClass
     * @param example
     * @param <T>
     * @return
     */
    @DeleteProvider(type = CommonProvider.class, method = "deleteByExample")
    <T> int deleteByExample(@Param("entityClass") Class<T> entityClass, @Param("example") Object example);

    /**
     * 通过Example来查询
     *
     * @param entityClass
     * @param example
     * @param <T>
     * @return
     */
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @SelectProvider(type = CommonProvider.class, method = "selectByExample")
    <T> List<Map<String, Object>> selectByExample(@Param("entityClass") Class<T> entityClass, @Param("example") Object example);

    /**
     * 通过Example进行更新非空字段
     *
     * @param record
     * @param example
     * @param <T>
     * @return
     */
    @UpdateProvider(type = CommonProvider.class, method = "updateByExampleSelective")
    <T> int updateByExampleSelective(@Param("record") T record, @Param("example") Object example);

    /**
     * 通过Example进行更新全部字段
     *
     * @param record
     * @param example
     * @param <T>
     * @return
     */
    @UpdateProvider(type = CommonProvider.class, method = "updateByExample")
    <T> int updateByExample(@Param("record") T record, @Param("example") Object example);


    /**
     * @param jsonObject 需要保存的实体属性json
     * @param record     要保存的对应实体
     * @return {int} 更新数据记录数量
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description <p>
     * 更新实体对象部分属性  <br>
     * 1、json字符串为要更新的属性 <br>
     * 2、json中属性为 null值的会更新为null值
     * </p>
     */
    @UpdateProvider(type = CommonProvider.class, method = "updateByJsonEntity")
    <T> int updateByJsonEntity(@Param("json") JSONObject jsonObject, @Param("record") T record);
}