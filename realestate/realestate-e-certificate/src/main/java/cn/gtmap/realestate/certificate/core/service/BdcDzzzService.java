package cn.gtmap.realestate.certificate.core.service;


import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/28
 */
public interface BdcDzzzService {

    /**
     * @param obj     响应数据
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn DzzzResponseModel  返回信息
     * @description 响应数据处理,成功
     */
    DzzzResponseModel dzzzResponseSuccess(Object obj);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param message
     * @param obj
     * @return
     * @description 响应数据处理,成功
     */
    DzzzResponseModel dzzzResponseSuccess(String message, Object obj);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param message
     * @param obj
     * @return
     * @description 响应数据处理,失败
     */
    DzzzResponseModel dzzzResponseFalse(String message, Object obj);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param msg1
     * @param msg2
     * @return
     * @description 响应数据处理,失败
     */
    DzzzResponseModel dzzzResponseFalse(String msg1, String msg2);

    /**
     * @param message 异常信息
     * @param status  响应状态
     * @param obj     响应数据
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn DzzzResponseModel  返回信息
     * @description 响应数据处理（正常返回信息）
     */
    DzzzResponseModel dzzzResponse(String status, String message, Object obj);

    /**
     * @param parameter
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 不动产电子证照分页查询
     */
    <T> Page<T> selectPaging(String statement, Object parameter, Pageable pageable);
}
