package cn.gtmap.realestate.certificate.core.service.impl;


import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.DzzzValidate;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzCheckInfoService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzValidateService;
import cn.gtmap.realestate.certificate.util.PublicUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description 电子证照验证类
 */
@Service
public class BdcDzzzCheckInfoServiceImpl implements BdcDzzzCheckInfoService {

    private Map<String, BdcDzzzValidateService> bdcDzzzValidateMap;

    @Override
    public List<Map<String, Object>> check(BdcDzzzZzxx bdcDzzzZzxx, List<String> checkCodeList) {
        List<Map<String, Object>> resultList = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(checkCodeList) && null != bdcDzzzZzxx) {
            for (String checkCode : checkCodeList) {
                BdcDzzzValidateService bdcDzzzValidateService = getBdcDzzzValidateServiceByCheckCode(checkCode);
                DzzzValidate dzzzValidate = new DzzzValidate();
                dzzzValidate.setBdcDzzzZzxx(bdcDzzzZzxx);
                Map<String, Object> returnMap = bdcDzzzValidateService.validate(dzzzValidate);
                if (null != returnMap && returnMap.size() > 0) {
                    returnMap.put("info", bdcDzzzValidateService.getDescription());
                    resultList.add(returnMap);
                }
            }
        }
        return resultList;
    }

    @Override
    public Map<String, Object> check(BdcDzzzZzxx bdcDzzzZzxx, String checkCode, List<String> resultList) {
        Map<String, Object> returnMap = null;
        if (StringUtils.isNotBlank(checkCode) && null != bdcDzzzZzxx) {
            BdcDzzzValidateService bdcDzzzValidateService = getBdcDzzzValidateServiceByCheckCode(checkCode);
            DzzzValidate dzzzValidate = new DzzzValidate();
            dzzzValidate.setBdcDzzzZzxx(bdcDzzzZzxx);
            dzzzValidate.setResultList(resultList);
            returnMap = bdcDzzzValidateService.validate(dzzzValidate);
            if (MapUtils.isNotEmpty(returnMap)) {
                returnMap.put("info", bdcDzzzValidateService.getDescription());
            }
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getResultMap(Map<String, Object> resultMap, List<String> resultList, Object o) {
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (String fieldName : resultList) {
                resultMap.put(fieldName, PublicUtil.getDeclaredFieldValueByName(fieldName, o));
            }
        }
        return resultMap;
    }

    public BdcDzzzValidateService getBdcDzzzValidateServiceByCheckCode(String checkCode) {
        BdcDzzzValidateService bdcDzzzValidateService = bdcDzzzValidateMap.get(checkCode);
        if (null == bdcDzzzValidateService) {
            throw new NullPointerException("验证checkcode" + checkCode + "没有对应的实现类！");
        }
        return bdcDzzzValidateService;
    }

    public Map<String, BdcDzzzValidateService> getBdcDzzzValidateMap() {
        return bdcDzzzValidateMap;
    }

    public void setBdcDzzzValidateMap(Map<String, BdcDzzzValidateService> bdcDzzzValidateMap) {
        this.bdcDzzzValidateMap = bdcDzzzValidateMap;
    }
}
