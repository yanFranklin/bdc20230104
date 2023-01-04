package cn.gtmap.realestate.certificate.core.service.impl;


import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzRestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019-9-2
 */
@Service
public class BdcDzzzRestTemplateServiceImpl implements BdcDzzzRestTemplateService {

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public DzzzResponseModel postForBdcDzzzZzxx(String url, BdcDzzzZzxx bdcDzzzZzxx) {
        //设置请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json;charset=utf-8");

        //将请求头和请求参数设置到HttpEntity中
        HttpEntity<BdcDzzzZzxx> httpEntity = new HttpEntity<>(bdcDzzzZzxx, httpHeaders);

        return restTemplate.postForObject(url, httpEntity, DzzzResponseModel.class);
    }

    @Override
    public <T, A> T exchange(String url, HttpMethod method, ParameterizedTypeReference<T> responseBodyType, A requestBody) {
        RestTemplate restTemplate = new RestTemplate();
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        // 发送请求
        HttpEntity<A> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<T> resultEntity = restTemplate.exchange(url, method, entity, responseBodyType);
        return resultEntity.getBody();
    }

    @Override
    public DzzzResponseModel exchangePostDzzzResponseModel(String url, Object requestBody) {
        ParameterizedTypeReference<DzzzResponseModel> responseBodyType = new ParameterizedTypeReference<DzzzResponseModel>(){};
        return exchange(url
                , HttpMethod.POST, responseBodyType, requestBody);
    }
}
