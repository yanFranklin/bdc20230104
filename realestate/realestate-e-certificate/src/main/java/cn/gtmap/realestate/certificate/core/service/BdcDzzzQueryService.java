package cn.gtmap.realestate.certificate.core.service;

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;

/**
 * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
 * @version 1.0 2019/1/21
 * @description 电子证照共享接口
 */
public interface BdcDzzzQueryService {

    /**
     * @param jsonString
     * @return 需要返回的电子证照标识信息
     * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
     * @description 电子证照检索接口业务处理接口
     */
    DzzzResponseModel zzjs(String jsonString);


    /**
     * @param jsonString 获取电子证照信息
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn 需要返回的电子证照元数据信息
     * @description 电子证照-信息获取功能
     */
    DzzzResponseModel zzysj(String jsonString);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param jsonString
     * @return
     * @description 电子证照-信息查询
     */
    DzzzResponseModel zzcx(String jsonString);

    DzzzResponseModel zzcxParamCheck(String jsonString);

    DzzzResponseModel zzjsParamCheck(String jsonString);
}
