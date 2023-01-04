package cn.gtmap.realestate.certificate.core.service;

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/3/21
 */
public interface BdcDzzzRestTemplateService {

    DzzzResponseModel postForBdcDzzzZzxx(String url, BdcDzzzZzxx bdcDzzzZzxx);

    <T, A> T exchange(String url, HttpMethod method, ParameterizedTypeReference<T> responseBodyType, A requestBody);

    DzzzResponseModel exchangePostDzzzResponseModel(String url, Object requestBody);
}
