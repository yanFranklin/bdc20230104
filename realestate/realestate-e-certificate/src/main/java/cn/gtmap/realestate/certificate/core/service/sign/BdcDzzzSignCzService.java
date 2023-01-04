package cn.gtmap.realestate.certificate.core.service.sign;


import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.signature.BdcDzzzSignaTure;

public interface BdcDzzzSignCzService {

    DzzzResponseModel standSign(String zzids);

    DzzzResponseModel sign(BdcDzzzSignaTure bdcDzzzSignaTure);

    DzzzResponseModel digitalSign(String signData, String cert);


    /**
     * @param
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 常州电子证照前台界面作废
     */
    DzzzResponseModel dzzzCancellation(BdcDzzzZzxx bdcDzzzZzxx);
}
