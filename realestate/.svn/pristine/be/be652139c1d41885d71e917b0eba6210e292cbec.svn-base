package cn.gtmap.realestate.certificate.core.service.impl.validator;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzZdCzztMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxCzztDo;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.DzzzValidate;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzValidateService;
import cn.gtmap.realestate.certificate.core.service.sign.BdcDzqzValidateService;
import cn.gtmap.realestate.certificate.util.CommonUtil;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.ReadXmlPropsUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/3/6
 */
public class BdcDzzzZzxxParameterErrorServiceImpl implements BdcDzzzValidateService, BdcDzqzValidateService {

    @Resource
    private BdcDzzzZdCzztMapper bdcDzzzZdCzztMapper;

    /**
     * <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     *
     * @param dzzzValidate
     * @return
     * @description 电子证照信息必填项验证
     */
    @Override
    public Map<String, Object> validate(DzzzValidate dzzzValidate) {
        Map<String, Object> resultMap = new HashMap<>(10);
        BdcDzzzZzxx bdcDzzzZzxx = null;
        if (null != dzzzValidate) {
            bdcDzzzZzxx = dzzzValidate.getBdcDzzzZzxx();
        }

        if (null != bdcDzzzZzxx) {
            //验证zzlxdm是否符合规范
            if (null != bdcDzzzZzxx && !CommonUtil.indexOfStrs(Constants.BDC_ZZLXDM,bdcDzzzZzxx.getZzlxdm())) {
                resultMap.put("zzlxdm", "zzlxdm is not standard");
            }

            //验证czztdmlxdm是否符合规范
            List<BdcDzzzZzxxCzztDo> czztDoList = bdcDzzzZzxx.getQlrxx();
            if (CollectionUtils.isNotEmpty(czztDoList)) {
                for (BdcDzzzZzxxCzztDo czztDo:czztDoList) {
                    if (!CommonUtil.indexOfStrs(Constants.DZZZ_CZZTDMLXDM,czztDo.getCzztdmlxdm())) {
                        resultMap.put("czztdmlxdm", "czztdmlxdm is not standard");
                        break;
                    }
                }
            }

            //验证qzrqmc是否和下发匹配
            if (StringUtils.isNotBlank(bdcDzzzZzxx.getDzqzwybs())) {
                String result = ReadXmlPropsUtil.analysisSealXml(bdcDzzzZzxx.getZzbfjgdm(),bdcDzzzZzxx.getDzqzwybs());
                if (StringUtils.isBlank(result)) {
                    resultMap.put("dzqzwybs", "dzqzwybs is not assigned to you");
                }
            }
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
