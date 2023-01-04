package cn.gtmap.realestate.exchange.service.inf;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-30
 * @description ExchangeBean 请求方式
 */
public interface ExchangeBeanRequestService {

    /**
     * @param beanName
     * @param requestObject
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 请求 exchangebean
     */
    Object request(String beanName, Object requestObject);

    /**
     * @param beanName
     * @param requestObject
     * @param entityClass
     * @return T
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 请求 exchangebean
     */
    <T> T request(String beanName, Object requestObject, Class<T> entityClass);

    /**
     * @param beanName
     * @param requestObject
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 与省级平台相关的接口 记录日志
     */
    Object sjptRequest(String beanName, Object requestObject);

    /**
      * 替换接口URL
      * @param beanName
      * @return
      * @Date 2021/7/16
      * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
         */
    Object requestByUrl(String beanName, Object requestObject, String url);

    /**
     * @param beanName
     * @param requestObject
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 请求 exchangebean
     */
    Object swRequest(String beanName, Object requestObject);
}
