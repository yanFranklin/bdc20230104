package cn.gtmap.realestate.certificate.core.service;


import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/25
 */
public interface BdcDzzzCheckInfoService {


    /**
     * @param bdcDzzzZzxx   证照信息
     * @param checkCodeList 验证项
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 验证信息是否符合 批量
     */
    List<Map<String, Object>> check(BdcDzzzZzxx bdcDzzzZzxx, List<String> checkCodeList);

    /**
     *
     * @param bdcDzzzZzxx
     * @param checkCode
     * @return
     * @description 验证信息是否符合 单个
     */
    Map<String, Object> check(BdcDzzzZzxx bdcDzzzZzxx, String checkCode, List<String> resultList);

    /**
     *
     * @param resultMap
     * @param resultList
     * @param o
     * @return
     * @description 验证信息返回指定信息
     */
    Map<String, Object> getResultMap(Map<String, Object> resultMap, List<String> resultList, Object o);
}
