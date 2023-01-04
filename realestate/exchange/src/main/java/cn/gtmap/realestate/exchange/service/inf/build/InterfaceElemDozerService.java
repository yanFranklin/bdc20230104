package cn.gtmap.realestate.exchange.service.inf.build;

import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;

import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-17
 * @description 交互接口服务中  ELEMENT元素的 对照处理逻辑
 */
public interface InterfaceElemDozerService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param source
     * @param dozerBeanMapper
     * @return java.lang.Object
     * @description 转换
     */
    Object convert(Object source, DozerBeanMapper dozerBeanMapper) throws InstantiationException, IllegalAccessException;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param elementMap
     * @param dozerBeanMapper
     * @return java.lang.Object
     * @description 汇总元素对照后的实体对象
     */
    Object collectCovert(Map<String,Object> elementMap,DozerBeanMapper dozerBeanMapper) throws IllegalAccessException, InstantiationException;
    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param sourceObject
     * @param targetClass
     * @param dozerBeanMapper
     * @param targetObject
     * @return java.lang.Object
     * @description 单个实体转换
     */
    Object convertSingleObj(Object sourceObject, Class targetClass,
                            DozerBeanMapper dozerBeanMapper,Object targetObject) throws IllegalAccessException, InstantiationException;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param source
     * @param exchangeBeanId
     * @return java.lang.Object
     * @description
     */
    Object request(Object source, String exchangeBeanId);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param dozerBeanMapper
     * @param sourceObject
     * @param targetObject
     * @return void
     * @description 对照 并 验证
     */
    void dozerMapWithValid(DozerBeanMapper dozerBeanMapper,Object sourceObject,Object targetObject);

}
