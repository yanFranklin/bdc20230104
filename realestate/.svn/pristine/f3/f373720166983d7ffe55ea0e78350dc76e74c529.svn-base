package cn.gtmap.interchange.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/10
 * @description  REST服务RPC调用处理逻辑
 */
@Component
public class RestRpcUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestRpcUtils.class);

    private RestTemplate restTemplate = new RestTemplate();


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param url  请求URL地址
     * @description POST方式请求RPC调用
     */
    public <T> T postRpcRequest(String url, Object param, String clazz) throws Exception {
        if(StringUtils.isBlank(url) || null == param){
            throw new Exception("执行POST方式请求RPC调用发生错误，原因：未定义请求URL地址或参数为空！");
        }

        try {
            String result = restTemplate.postForObject(url, param, String.class);
            if(StringUtils.isBlank(result)) {
                LOGGER.warn("调用服务地址返回结果为空，对应URL：{}", url);
                return null;
            }
            return (T) JSON.parseObject(result, Class.forName(clazz));
        } catch (Exception e){
            LOGGER.error("执行POST方式请求RPC调用发生异常，对应URL：{}，异常信息：{}", url, e.getMessage());
            throw e;
        }
    }

    /**
     * 执行公告信息推送（这里使用上面Rest请求虽然同样显示成功，得到成功返回结果但是实际却没有公告信息，推测相关请求头有要求，直接使用调用方提供代码）
     * @param url 推送地址
     * @param jsonObject 推送内容
     * @return {String} 推送返回结果
     * @throws IOException
     */
    public static String send(String url, JSONObject jsonObject) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        StringEntity request = new StringEntity(jsonObject.toString(), "utf-8");
        request.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(request);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        LOGGER.info("请求地址：{}", url);

        CloseableHttpResponse response = null;
        String body = "";
        try {
            response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                body = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            return body;
        } finally {
            response.close();
        }
    }
}
