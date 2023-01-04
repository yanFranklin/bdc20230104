package cn.gtmap.realestate.certificate.core.service;


import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/5/20 电子证照签章接口
 */
public interface BdcDzzzSignService {

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param o
     * @param out
     * @return
     * @description 电子证照签章接口
     */
    byte[] signature(Object o, byte[] out);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param content
     * @param bfjgxzqdm
     * @return
     * @description 电子证照文件验证
     */
    DzzzResponseModel verifyFile(String content, String bfjgxzqdm);
}
