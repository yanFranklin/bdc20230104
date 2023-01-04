package cn.gtmap.realestate.certificate.core.service.create;


import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/8/22 电子证照创建接口
 */
public interface BdcDzzzJiangSuCreateService {

    DzzzResponseModel createOne(BdcDzzzZzxx bdcDzzzZzxx);

    DzzzResponseModel signUpload(BdcDzzzZzxx bdcDzzzZzxx, byte[] createPdfArr);
}
