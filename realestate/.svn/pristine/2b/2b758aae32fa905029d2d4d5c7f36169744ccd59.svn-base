package cn.gtmap.realestate.exchange.core.support.mybatis.mapper;

import cn.gtmap.realestate.exchange.core.support.mybatis.utils.AnnotationsUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 封装的CommonMapper,实际上只对select方法做了处理<br>
 * <p/>
 * 该类起名Mapper结尾只是为了表面上看着统一，实际上和普通的Mapper不是一类东西
 *
 * @author zx
 */
@Component
public class EntityMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityMapper.class);

    @Autowired
    private CommonMapper commonMapper;

    private static final String EXAMPLEEMPTY = "example参数不能为空!";

    public CommonMapper getCommonMapper() {
        return commonMapper;
    }

    public void setCommonMapper(CommonMapper commonMapper) {
        this.commonMapper = commonMapper;
    }

    /**
     * 根据参数进行查询,record可以是Class<?>类型
     * <br>查询条件为属性String类型不为空，其他类型!=null时
     * <br>where property = ? and property2 = ? 条件
     *
     * @param record
     * @param <T>
     * @return
     */
    public <T> List<T> select(T record) {
        if (record == null) {
            throw new NullPointerException("实体或者主键参数不能为空!");
        }
        return (List<T>) EntityHelper.maplist2BeanList(commonMapper.select(record), record.getClass());
    }

    /**
     * 根据参数进行查询总数,record可以是Class<?>类型
     * <br>查询条件为属性String类型不为空，其他类型!=null时
     * <br>where property = ? and property2 = ? 条件
     *
     * @param record
     * @param <T>
     * @return
     */
    public <T> int count(T record) {
        return commonMapper.count(record);
    }

    /**
     * 根据主键查询结果，主键不能为null或空
     *
     * @param entityClass
     * @param key
     * @param <T>
     * @return
     */
    public <T> T selectByPrimaryKey(Class<T> entityClass, Object key) {
        return (T) EntityHelper.map2Bean(commonMapper.selectByPrimaryKey(entityClass, key), entityClass);
    }

    /**
     * 插入数据库，主键字段没有值的时候不会出现在sql中
     * <br>如果是自增主键，会自动获取值
     * <br>如果是自增主键，并且该主键属性有值，会使用主键的属性值，不会使用自增
     *
     * @param record
     * @param <T>
     * @return
     */
    public <T> int insert(T record) {
        return commonMapper.insert(record);
    }

    /**
     * 插入非空字段，其他和上面方法类似
     *
     * @param record
     * @param <T>
     * @return
     */
    public <T> int insertSelective(T record) {
        return commonMapper.insertSelective(record);
    }

    /**
     * 批量插入非空字段，其他和上面方法类似
     *
     * @param record
     * @param <T>
     * @return
     */
    public <T> int insertBatchSelective(List<T> record) {
        return commonMapper.insertBatchSelective(record);
    }

    /**
     * 批量插入多个类型的实体类，其他和上面方法类似
     *
     * @param record
     * @param <T>
     * @return
     */
    public <T> void batchSaveSelective(List<T> record) {
        for (T vo : record) {
            Method method1 = AnnotationsUtils.getAnnotationsName(vo);
            String id = null;
            try {
                if (method1.invoke(vo) != null) {
                    id = method1.invoke(vo).toString();
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                LOGGER.error(e.getMessage(), e);
            }
            saveOrUpdate(vo, id);

        }
    }

    /**
     * 保存或者更新
     *
     * @param record
     * @param key
     * @param <T>
     * @return
     */
    public <T> int saveOrUpdate(T record, Object key) {
        int count;
        if ((T) EntityHelper.map2Bean(commonMapper.selectByPrimaryKey(record.getClass(), key), record.getClass()) != null) {
            count = updateByPrimaryKeySelective(record);
        } else {
            count = insertSelective(record);
        }
        return count;
    }

    /**
     * 根据条件进行删除，条件不能为空，并且必须有至少一个条件才能删除
     * <br>该方法不能直接删除全部数据
     *
     * @param record
     * @param <T>
     * @return
     */
    public <T> int delete(T record) {
        return commonMapper.delete(record);
    }

    /**
     * 根据主键进行删除，主键不能为null或空
     *
     * @param entityClass
     * @param key
     * @param <T>
     * @return
     */
    public <T> int deleteByPrimaryKey(Class<T> entityClass, Object key) {
        return commonMapper.deleteByPrimaryKey(entityClass, key);
    }

    /**
     * 根据主键更新全部字段，空字段也会更新数据库
     *
     * @param record
     * @param <T>
     * @return
     */
    public <T> int updateByPrimaryKey(T record) {
        return commonMapper.updateByPrimaryKey(record);
    }

    /**
     * 根据主键更新非空属性字段，不能给数据库数据设置null或空
     *
     * @param record
     * @param <T>
     * @return
     */
    public <T> int updateByPrimaryKeySelective(T record) {
        return commonMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * lst 根据主键更新全部字段，空字段也会更新数据库
     * null也会更新
     *
     * @param record
     * @param <T>
     * @return
     */
    public <T> int updateByPrimaryKeyNull(T record) {
        return commonMapper.updateByPrimaryKeyNull(record);
    }

    /**
     * 通过Example类来查询count
     *
     * @param entityClass
     * @param example     可以是Mybatis生成器生成的Example类或者通用的Example类
     * @param <T>
     * @return
     */
    public <T> int countByExample(Class<T> entityClass, Object example) {
        return commonMapper.countByExample(entityClass, example);
    }

    /**
     * 通过Example类来查询count
     *
     * @param example
     * @param
     * @return
     */
    public int countByExample(Example example) {
        if (example == null) {
            throw new NullPointerException(EXAMPLEEMPTY);
        }
        return commonMapper.countByExample(example.getEntityClass(), example);
    }

    /**
     * 通过Example删除
     *
     * @param entityClass
     * @param example     可以是Mybatis生成器生成的Example类或者通用的Example类
     * @param <T>
     * @return
     */
    public <T> int deleteByExample(Class<T> entityClass, Object example) {
        return commonMapper.deleteByExample(entityClass, example);
    }

    /**
     * @param example 条件对象
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @description 根据example去删除记录，同时为了安全会严格检查example对象中的条件是否为空
     */
    public <T> int deleteByExampleNotNull(Class<T> entityClass, Object example) {
        return checkExampleParamNull((Example) example) ? 0 : commonMapper.deleteByExample(entityClass, example);
    }

    /**
     * 通过Example删除
     *
     * @param example
     * @param
     * @return
     */
    public int deleteByExample(Example example) {
        if (example == null) {
            throw new NullPointerException(EXAMPLEEMPTY);
        }
        return commonMapper.deleteByExample(example.getEntityClass(), example);
    }

    /**
     * @param example 条件对象
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @description 根据example去删除记录，同时为了安全会严格检查example对象中的条件是否为空
     */
    public int deleteByExampleNotNull(Example example) {
        if (example == null) {
            throw new NullPointerException(EXAMPLEEMPTY);
        }
        return checkExampleParamNull(example) ? 0 : commonMapper.deleteByExample(example.getEntityClass(), example);
    }

    /**
     * 通过Example来查询
     *
     * @param entityClass
     * @param example     可以是Mybatis生成器生成的Example类或者通用的Example类
     * @param <T>
     * @return
     */
    public <T> List<T> selectByExample(Class<T> entityClass, Object example) {
        return (List<T>) EntityHelper.maplist2BeanList(commonMapper.selectByExample(entityClass, example), entityClass);
    }

    /**
     * @param entityClass 实体类
     * @param example     条件对象
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @description 根据example去查找表，同时为了安全会严格检查example对象中的条件是否为空
     */
    public <T> List<T> selectByExampleNotNull(Class<T> entityClass, Example example) {
        return checkExampleParamNull(example) ? null : (List<T>) EntityHelper.maplist2BeanList(commonMapper.selectByExample(entityClass, example), entityClass);
    }

    /**
     * @param example 查询条件
     * @return 查询条件是否为空，example里面没有查询条件，或者查询条件的值都为空字符串
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @description
     */
    private boolean checkExampleParamNull(Example example) {
        boolean result = true;
        if (example != null) {

            List<Example.Criteria> criteriaList = example.getOredCriteria();
            if (CollectionUtils.isNotEmpty(criteriaList)) {
                for (Example.Criteria criteria : criteriaList) {
                    List<Example.Criterion> criterionList = criteria.getCriteria();
                    if (CollectionUtils.isNotEmpty(criterionList)) {
                        for (Example.Criterion criterion : criterionList) {
                            if (StringUtils.isNotBlank(criterion.getCondition())
                                    && criterion.getValue() != null && StringUtils.isNotBlank(String.valueOf(criterion.getValue()))) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 通过Example来查询
     *
     * @param example
     * @param <T>
     * @return
     */
    public <T> List<T> selectByExample(Example example) {
        if (example == null) {
            throw new NullPointerException(EXAMPLEEMPTY);
        }
        return (List<T>) EntityHelper.maplist2BeanList(commonMapper.selectByExample(example.getEntityClass(), example), example.getEntityClass());
    }

    /**
     * @param example 条件对象
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @description 根据example去查找表，同时为了安全会严格检查example对象中的条件是否为空
     */
    public <T> List<T> selectByExampleNotNull(Example example) {
        if (example == null) {
            throw new NullPointerException(EXAMPLEEMPTY);
        }
        return checkExampleParamNull(example) ? null : (List<T>) EntityHelper.maplist2BeanList(commonMapper.selectByExample(example.getEntityClass(), example), example.getEntityClass());
    }

    /**
     * 通过Example进行更新非空字段
     *
     * @param record
     * @param example 可以是Mybatis生成器生成的Example类或者通用的Example类
     * @param <T>
     * @return
     */
    public <T> int updateByExampleSelective(T record, Object example) {
        return commonMapper.updateByExampleSelective(record, example);
    }

    /**
     * @param record
     * @param example 条件对象
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @description 根据查询条件更新表，同时为了安全会严格检查example对象中的条件是否为空
     */
    public <T> int updateByExampleSelectiveNotNull(T record, Object example) {
        return checkExampleParamNull((Example) example) ? 0 : commonMapper.updateByExampleSelective(record, example);
    }

    /**
     * 通过Example进行更新全部字段
     *
     * @param record
     * @param example 可以是Mybatis生成器生成的Example类或者通用的Example类
     * @param <T>
     * @return
     */
    public <T> int updateByExample(T record, Object example) {
        return commonMapper.updateByExample(record, example);
    }

    /**
     * @param record
     * @param example 条件对象
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @description 根据查询条件更新表，同时为了安全会严格检查example对象中的条件是否为空
     */
    public <T> int updateByExampleNotNull(T record, Object example) {
        return checkExampleParamNull((Example) example) ? 0 : commonMapper.updateByExample(record, example);
    }

    /**
     * @param obj
     * @return
     * @author <a href="mailto:lisongtao@gmail.com">lst</a>
     * @description obj对象转example
     */
    public Example objToExample(Object obj) {
        if (obj != null) {
            List<Field> list = new ArrayList();
            Class<?> clazz = obj.getClass();
            for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
                Field[] fields = clazz.getDeclaredFields();
                if (fields != null && fields.length > 0) {
                    list.addAll(Arrays.asList(fields));
                }
            }
            if (CollectionUtils.isNotEmpty(list)) {
                Boolean b = false;
                Example example = new Example(obj.getClass());
                Example.Criteria criteria = example.createCriteria();
                for (Field field : list) {
                    field.setAccessible(true);
                    try {
                        if (field.get(obj) != null && StringUtils.isNotBlank(field.get(obj).toString())) {
                            b = true;
                            criteria.andEqualTo(field.getName(), field.get(obj));
                        }
                    } catch (IllegalAccessException e) {
                        LOGGER.error(null, e);
                    }
                }
                if (b) {
                    return example;
                }
            }
        }
        return null;
    }

    /**
     * @param example
     * @param <T>
     * @return
     * @author <a href="mailto:lisongtao@gmail.com">lst</a>
     * 通过Obj来查询
     * @description Obj必须是实体类  内自动赋值的字段要处理  例如主键  集合类型的字段无法处理
     */
    public <T> List<T> selectByObj(Object example) {
        if (example == null) {
            throw new NullPointerException(EXAMPLEEMPTY);
        }
        Example listExample = objToExample(example);

        return listExample == null ? null : (List<T>) EntityHelper.maplist2BeanList(commonMapper.selectByExample(example.getClass(), listExample), example.getClass());
    }


    /**
     * @param jsonObject 需要保存的实体属性json
     * @param entity     要保存的对应实体
     * @return {int} 更新数据记录数量
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description <p>
     * 更新实体对象部分属性  <br>
     * 1、json字符串为要更新的属性 <br>
     * 2、json中属性为 null值的会更新为null值
     * </p>
     */
    public <T> int updateByJsonEntity(JSONObject jsonObject, T entity) {
        if (MapUtils.isEmpty(jsonObject) || null == entity) {
            throw new NullPointerException("JSON内容或者实体不能为空！");
        }

        return commonMapper.updateByJsonEntity(jsonObject, entity);
    }
}
