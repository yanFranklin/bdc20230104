package cn.gtmap.realestate.common.core.service;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/18
 * @description 实体操作接口定义
 */
public interface EntityService {
    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   json 需要更新的实体部分属性json字符串
     * @param   entityClass 要保存的对应实体Class
     * @return  {int} 更新数据记录数量
     * @description  更新实体对象部分属性
     */
    <T> int updateByJsonEntity(String json, Class<T> entityClass);

    /**
     * @param   json 需要更新的实体部分属性json字符串
     * @param   entityClass 要保存的对应实体Class
     * @return  {int} 更新数据记录数量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 插入实体对象部分属性
     */
    <T> int insertJsonEntity(String json, Class<T> entityClass);
}
