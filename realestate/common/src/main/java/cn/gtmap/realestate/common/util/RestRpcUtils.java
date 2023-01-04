package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.ex.AppException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/10
 * @description  REST服务RPC调用处理逻辑
 */
@Component
public class RestRpcUtils {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestRpcUtils.class);

    /**
     * 服务发现
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 发起REST请求
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 用户权限
     */
    @Autowired
    private TokenUtils tokenUtils;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param appName 请求应用名称（可以是微服务实例名称，也可以直接指定IP：端口形式）
     * @param url  请求URL地址
     * @param params 参数列表
     * @description GET方式请求RPC调用
     *
     *  1、GET请求方式只支持路径传参、后缀传参形式
     *  2、参数统一采用占位符 #{} 格式
     *  3、例如: /realestate-inquiry/rest/v1.0/print/zfxx/#{bdcdyh}/#{xmid}/xml?bdcdyh=#{bdcdyh}
     */
    public <T> T getRpcRequest(String appName, String url, Map<String, Object> params){
        if(StringUtils.isBlank(appName) || StringUtils.isBlank(url)){
            throw new AppException("执行GET方式请求RPC调用发生错误，原因：未定义应用名称或请求URL地址！");
        }

        try {
            // 1、匹配参数获取URL
            String requestUrl = this.resolveRequestUrl(appName, url, params);

            // 2、获取请求RestTemplate
            RestTemplate template = this.getRestTemplate(appName);

            // 3、执行请求
            String result = template.getForObject(requestUrl, String.class);
            return (T) DataParseUtils.parseType(result);
        } catch (Exception e){
            LOGGER.error("执行GET方式请求RPC调用发生异常：", e);
            throw new AppException("执行GET方式请求RPC调用发生异常：" + e.getMessage());
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param appName 请求应用名称（可以是微服务实例名称，也可以直接指定IP：端口形式）
     * @param url  请求URL地址
     * @description GET方式请求RPC调用
     */
    public <T> T getRpcRequest(String appName, String url){
        if(StringUtils.isBlank(appName) || StringUtils.isBlank(url)){
            throw new AppException("执行GET方式请求RPC调用发生错误，原因：未定义应用名称或请求URL地址！");
        }

        try {
            // 1、获取请求RestTemplate
            RestTemplate template = this.getRestTemplate(appName);

            // 2、执行请求
            String result = template.getForObject(url, String.class);

            // 3、处理结果
            return (T) DataParseUtils.parseType(result);
        } catch (Exception e){
            LOGGER.error("执行GET方式请求RPC调用发生异常，请求目标 {}, 请求地址 {}", appName, url, e);
            throw new AppException("执行GET方式请求RPC调用发生异常，请求目标 " + appName + ", 请求地址 " + url + "：" + e.getMessage());
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param appName 请求应用名称（可以是微服务实例名称，也可以直接指定IP：端口形式）
     * @param url  请求URL地址
     * @description POST方式请求RPC调用
     */
    public <T> T postRpcRequest(String appName, String url, Object param){
        if(StringUtils.isBlank(appName) || StringUtils.isBlank(url)){
            throw new AppException("执行POST方式请求RPC调用发生错误，原因：未定义应用名称或请求URL地址！");
        }

        if(null == param) {
            return null;
        }

        try {
            // 1、获取请求RestTemplate
            RestTemplate template = this.getRestTemplate(appName);

            // 2、执行请求
            String result = template.postForObject(url, param, String.class);

            // 3、处理结果
            return (T) DataParseUtils.parseType(result);
        } catch (Exception e){
            LOGGER.error("执行GET方式请求RPC调用发生异常", e);
            throw new AppException("执行GET方式请求RPC调用发生异常：" + e.getMessage());
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param appName 请求应用名称（可以是微服务实例名称，也可以直接指定IP：端口形式）
     * @param url  请求URL地址
     * @param params 参数列表
     * @description  处理请求URL地址
     */
    private String resolveRequestUrl(String appName, String url, Map<String, Object> params) throws URISyntaxException {
        if(MapUtils.isEmpty(params)){
            return url;
        }

        // 匹配URL地址中参数
        for(Map.Entry<String, Object> entry : params.entrySet()){
            url = url.replaceAll("#\\{" + entry.getKey() + "\\}", String.valueOf(entry.getValue()));
        }

        // 加权限Token
        url = new URIBuilder(url).addParameter("access_token", tokenUtils.getAccessToken()).build().toString();
        return  "http://" + appName.trim() + url.trim();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param appName 应用名称
     * @description 根据请求应用获取RestTemplate对象
     *  说明：
     *  1、请求应用为 IP：端口类型，则使用原生RestTemplete对象
     *  2、请求应用为微服务应用名称，则使用负载均衡方式
     */
    public RestTemplate getRestTemplate(String appName) {
        List<String> servicesList = discoveryClient.getServices();
        if (CollectionUtils.isNotEmpty(servicesList)) {
            for (String service : servicesList) {
                if (StringUtils.equals(service, appName)) {
                    return restTemplate;
                }
            }
        }
        return new RestTemplate();
    }

    /**
     * @param appName
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description deleteRPC请求方式
     * @date : 2022/3/31 14:31
     */
    public void deleteRpcRequest(String appName, String url, Map<String, Object> params) {
        if (StringUtils.isBlank(appName) || StringUtils.isBlank(url)) {
            throw new AppException("执行DELETE方式请求RPC调用发生错误，原因：未定义应用名称或请求URL地址！");
        }
        try {
            // 1、获取请求RestTemplate
            RestTemplate template = this.getRestTemplate(appName);
            // 2、执行请求
            template.delete(url);
        } catch (Exception e) {
            LOGGER.error("执行GET方式请求RPC调用发生异常", e);
            throw new AppException("执行GET方式请求RPC调用发生异常：" + e.getMessage());
        }
    }

    /**
     * @param appName
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description put RPC请求方式
     * @date : 2022/3/31 14:55
     */
    public void putRpcRequest(String appName, String url, Object param) {
        if (StringUtils.isBlank(appName) || StringUtils.isBlank(url)) {
            throw new AppException("执行DELETE方式请求RPC调用发生错误，原因：未定义应用名称或请求URL地址！");
        }
        if (null != param) {
            try {
                // 1、获取请求RestTemplate
                RestTemplate template = this.getRestTemplate(appName);
                // 2、执行请求
                template.put(url, param, String.class);
            } catch (Exception e) {
                LOGGER.error("执行GET方式请求RPC调用发生异常", e);
                throw new AppException("执行GET方式请求RPC调用发生异常：" + e.getMessage());
            }
        }
    }

    /**
     * @param appName
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description put RPC请求方式
     * @date : 2022/3/31 14:55
     */
    public void patchRpcRequest(String appName, String url, Object param) {
        if (StringUtils.isBlank(appName) || StringUtils.isBlank(url)) {
            throw new AppException("执行DELETE方式请求RPC调用发生错误，原因：未定义应用名称或请求URL地址！");
        }
        if (null != param) {
            try {
                // 1、获取请求RestTemplate
                RestTemplate template = this.getRestTemplate(appName);
                // 2、执行请求
                template.patchForObject(url, param, String.class);
            } catch (Exception e) {
                LOGGER.error("执行GET方式请求RPC调用发生异常", e);
                throw new AppException("执行GET方式请求RPC调用发生异常：" + e.getMessage());
            }
        }
    }
}
