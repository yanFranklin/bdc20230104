package cn.gtmap.realestate.certificate.core.service.impl.validator;


import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.DzzzValidate;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzValidateService;
import cn.gtmap.realestate.certificate.core.service.sign.BdcDzqzValidateService;
import cn.gtmap.realestate.certificate.util.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description 验证注销证照接口原因是否按照国标传参
 */


public class BdcDzzzZzxxZxyyServiceImpl implements BdcDzzzValidateService, BdcDzqzValidateService {

    @Override
    public Map<String, Object> validate(DzzzValidate dzzzValidate) {
        Map<String, Object> resultMap = new HashMap<>(10);
        BdcDzzzZzxx bdcDzzzZzxx = null;
        if (null != dzzzValidate) {
            bdcDzzzZzxx = dzzzValidate.getBdcDzzzZzxx();
        }
        if (null != bdcDzzzZzxx && !(StringUtils.equals(bdcDzzzZzxx.getZzbgyy(), Constants.DZZZ_ZZZT_ZX_ZCZX) || StringUtils.equals(bdcDzzzZzxx.getZzbgyy(), Constants.DZZZ_ZZZT_ZX_YCZX))) {
            resultMap.put("zzbgyy", "zzbgyy is not standard");
        }

        return resultMap;
    }

    @Override
    public String getCheckCode() {
        return ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode();
    }

    @Override
    public String getDescription() {
        return ResponseEnum.RESPONSE_PARAMETER_ERROR.getMsg();
    }

}
