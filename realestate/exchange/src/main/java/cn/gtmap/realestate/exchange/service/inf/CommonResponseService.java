package cn.gtmap.realestate.exchange.service.inf;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-20
 * @description 常用的响应处理服务
 */
public interface CommonResponseService {


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param result
     * @return boolean
     * @description 判断返回是否是空 空返回false 非空返回true
     */
    boolean checkResultIsEmpty(Object result);

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param result
     * @return boolean
     * @description 判断是否为token验证错误  是返回true   否返回false
     */
    boolean checkResultTokenError(Object result);

}
