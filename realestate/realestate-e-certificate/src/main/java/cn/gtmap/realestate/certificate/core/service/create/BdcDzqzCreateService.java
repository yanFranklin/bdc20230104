package cn.gtmap.realestate.certificate.core.service.create;


import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/8/22 电子证照创建接口
 */
public interface BdcDzqzCreateService {

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param o
     * @return
     * @description 2.0 生成签章
     */
    DzzzResponseModel createDzqz(Object o);
}
