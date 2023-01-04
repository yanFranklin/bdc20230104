package cn.gtmap.realestate.certificate.core.service.impl.sign;

import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.DzzzValidate;
import cn.gtmap.realestate.certificate.core.service.sign.BdcDzqzCheckService;
import cn.gtmap.realestate.certificate.core.service.sign.BdcDzqzValidateService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 电子签章实现类
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-10 10:21
 **/
@Service
public class BdcDzqzCheckServiceImpl implements BdcDzqzCheckService {

    private Map<String, BdcDzqzValidateService> bdcDzqzValidateServiceMap;

    /**
     * @param bdcDzzzZzxx
     * @param checkCode
     * @param resultList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/3/10 10:20
     */
    @Override
    public Map<String, Object> check(BdcDzzzZzxx bdcDzzzZzxx, String checkCode, List<String> resultList) {
        Map<String, Object> returnMap = null;
        if (StringUtils.isNotBlank(checkCode) && null != bdcDzzzZzxx) {
            BdcDzqzValidateService bdcDzqzValidateService = getBdcDzqzValidateServiceByCheckCode(checkCode);
            DzzzValidate dzzzValidate = new DzzzValidate();
            dzzzValidate.setBdcDzzzZzxx(bdcDzzzZzxx);
            dzzzValidate.setResultList(resultList);
            returnMap = bdcDzqzValidateService.validate(dzzzValidate);
            if (MapUtils.isNotEmpty(returnMap)) {
                returnMap.put("info", bdcDzqzValidateService.getDescription());
            }
        }
        return returnMap;
    }

    public BdcDzqzValidateService getBdcDzqzValidateServiceByCheckCode(String checkCode) {
        BdcDzqzValidateService bdcDzqzValidateService = bdcDzqzValidateServiceMap.get(checkCode);
        if (null == bdcDzqzValidateService) {
            throw new NullPointerException("验证checkcode" + checkCode + "没有对应的实现类！");
        }
        return bdcDzqzValidateService;
    }

    public Map<String, BdcDzqzValidateService> getBdcDzqzValidateServiceMap() {
        return bdcDzqzValidateServiceMap;
    }

    public void setBdcDzqzValidateServiceMap(Map<String, BdcDzqzValidateService> bdcDzqzValidateServiceMap) {
        this.bdcDzqzValidateServiceMap = bdcDzqzValidateServiceMap;
    }
}
