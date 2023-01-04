package cn.gtmap.realestate.certificate.core.service;


import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/5/20 电子证照签章接口
 */
public interface BdcDzzzSignConfigService {

    /**
     *
     * @param o
     * @param signCompany
     * @param out
     * @return
     * @description 根据签章公司名称获取对应签章service
     */
    byte[] sign(Object o, byte[] out, String signCompany);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param content
     * @param bfjgxzqdm
     * @param signCompany
     * @return
     * @description 电子证照文件验证
     */
    DzzzResponseModel verifyFile(String content, String bfjgxzqdm, String signCompany);

    void updateSignInfo(byte[] signArr, BdcDzzzZzxx bdcDzzzZzxx);

}
