package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.qi.ClnrDTO;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author <a href="mailto:zhongjinpeng@gtmao.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/4 11:32
 * @description
 */
@Component
public class HttpClientUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

    @Autowired
    private HttpClientService httpClientService;

    /**
     * 发送post请求
     * @param url
     * @param param
     * @return
     */
    public String sendPostRequest(String url, Map<String,Object> param){
        return this.sendPostRequest(url, param, null);
    }

    /**
     * 发送post请求
     * @param url
     * @param param
     * @return
     */
    public String sendFormRequest(String url, Map<String,Object> param) {
        return this.sendFormRequest(url, param, null);
    }

    /**
     * 发送post请求
     * @param url
     * @param param
     * @return
     */
    public String sendFormRequest(String url, Map<String,Object> param, Map<String,Object> headers){
        LOGGER.info("发送请求，请求url:{},请求参数:{}", url, JSONObject.toJSONString(param));
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        if (MapUtils.isNotEmpty(headers)) {
            Iterator<Map.Entry<String, Object>> iterator = headers.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                if (Objects.nonNull(next.getValue())) {
                    httpPost.addHeader(next.getKey(), next.getValue().toString());
                }
            }
        }
        //设置请求参数
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        if (Objects.nonNull(param) && !param.isEmpty()) {
            for (String key : param.keySet()) {
                Object value = param.get(key);
                if (!(value instanceof String)) {
                    value = JSONObject.toJSONString(value);
                }
                parameters.add(new BasicNameValuePair(key, (String) value));
            }
        }
        //构建form表单实体
        UrlEncodedFormEntity formEntity = null;
        try {
            formEntity = new UrlEncodedFormEntity(parameters, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("表单提交报错");
            e.printStackTrace();
        }
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);
        String response = "";
        try {
            response = httpClientService.doPost(httpPost, "UTF-8");
        } catch (Exception e) {
            LOGGER.error("请求异常：url:{},param:{}", url, param, e);
            throw new AppException("httpPost请求异常");
        }
        LOGGER.info("请求成功,响应结果:{}",response);
        return response;
    }


    /**
     * 发送post请求
     * @param url
     * @param param
     * @return
     */
    public String sendFormRequestWithFile(String url, Map<String,Object> param, Map<String,Object> headers,Map<String, ClnrDTO> files
                                          ){
        LOGGER.info("发送请求，请求url:{},请求参数:{},请求头{},请求文件{}", url,
                JSONObject.toJSONString(param),
                JSONObject.toJSONString(headers),
                JSONObject.toJSONString(files)
        );
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url);
        //httpPost.addHeader("Content-Type", "multipart/form-data;charset=utf-8");
        LOGGER.info("发送请求，请求url:{},设置请求头{}", url,
                JSONObject.toJSONString(headers)
        );
        if (MapUtils.isNotEmpty(headers)) {
            Iterator<Map.Entry<String, Object>> iterator = headers.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                if (Objects.nonNull(next.getValue())) {
                    httpPost.addHeader(next.getKey(), next.getValue().toString());
                }
            }
        }
        //设置请求参数
        LOGGER.info("发送请求，请求url:{},设置请求文件{}", url,
                JSONObject.toJSONString(files)
        );
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(StandardCharsets.UTF_8);
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.setContentType(ContentType.MULTIPART_FORM_DATA);
        for (Map.Entry<String, ClnrDTO> fileEntry : files.entrySet()) {
            try {
                LOGGER.info("发送请求，请求url:{},设置请求文件{}", url,
                        fileEntry.getKey());
                builder.addBinaryBody(fileEntry.getKey(),
                        Base64Utils.decodeBase64StrToByte(fileEntry.getValue().getFjnr()),
                        ContentType.create("image"), fileEntry.getValue().getFjmc());
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.info("发送请求，请求url:{},设置请求文件{},错误{}", url,
                        fileEntry.getKey(),e.getMessage());
            }
        }
        //设置请求参数
        LOGGER.info("发送请求，请求url:{},设置请求参数{}", url,
                JSONObject.toJSONString(param)
        );
        if (Objects.nonNull(param) && !param.isEmpty()) {
            for (String key : param.keySet()) {
                Object value = param.get(key);
                if (!(value instanceof String)) {
                    value = JSONObject.toJSONString(value);
                }
                builder.addTextBody(key, (String) value,ContentType.create("text/plain", Consts.UTF_8));
            }
        }
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(builder.build());

        String response = "";
        try {
            response = httpClientService.doPost(httpPost, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("请求异常：url:{},param:{}", url, param, e);
            throw new AppException("httpPost请求异常");
        }
        LOGGER.info("请求成功,响应结果:{}",response);
        return response;
    }

    /**
     * 发送post请求
     * @param url
     * @param param
     * @return
     */
    public String sendPostRequest(String url, Map<String,Object> param, String businessLog){
        String jsonStr = JSONObject.toJSONString(param);
        LOGGER.info("{}发送请求，请求url:{},请求参数:{}", businessLog, url, jsonStr);
        // 创建请求内容
        String response = "";
        StringEntity entity = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            if(param.containsKey("token")){
                httpPost.setHeader("token",param.get("token").toString());
            }
            httpPost.setHeader("Accept","application/json, text/plain, */*");
            httpPost.setHeader("Cache-Control","no-cache");
            httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
            httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
            httpPost.setHeader("charset","UTF-8");
            entity = new StringEntity(jsonStr,"UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            response = httpClientService.doPost(httpPost, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("请求异常：url:{},param:{}", url, param, e);
            throw new AppException("httpPost请求异常");
        }
        LOGGER.info("请求成功,响应结果:{}",response);
        return response;
    }

    /**
     * 发送post请求
     * @param url
     * @param jsonStr
     * @return
     */
    public String sendPostJsonRequest(String url,String jsonStr, String businessLog){
        LOGGER.info("{}发送请求，请求url:{},请求参数:{}", businessLog, url, jsonStr);
        // 创建请求内容
        String response = "";
        StringEntity entity = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept","application/json, text/plain, */*");
            httpPost.setHeader("Cache-Control","no-cache");
            httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
            httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
            httpPost.setHeader("charset","UTF-8");
            entity = new StringEntity(jsonStr,"UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            response = httpClientService.doPost(httpPost, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("请求异常：url:{},param:{}", url, jsonStr, e);
            throw new AppException("httpPost请求异常");
        }
        LOGGER.info("{}请求成功,响应结果:{}",businessLog,response);
        return response;
    }

    /**
     * 发送post请求
     * @param url
     * @param jsonStr
     * @return
     */
    public String sendPostJsonHeaderRequest(String url,String jsonStr, String businessLog,Map<String,Object> headMap){
        LOGGER.info("{}发送请求，请求url:{},请求参数:{},请求头:{}", businessLog, url, jsonStr,JSONObject.toJSONString(headMap));
        // 创建请求内容
        String response = "";
        StringEntity entity = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept","application/json, text/plain, */*");
            httpPost.setHeader("Cache-Control","no-cache");
            httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
            httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
            httpPost.setHeader("charset","UTF-8");
            for (Map.Entry<String, Object> entry : headMap.entrySet()) {
                httpPost.setHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
            entity = new StringEntity(jsonStr,"UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            response = httpClientService.doPost(httpPost, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("请求异常：url:{},param:{}", url, jsonStr, e);
            throw new AppException("httpPost请求异常");
        }
        LOGGER.info("请求成功,响应结果:{}", response);
        return response;
    }


    /**
     * 发送post 表单请求请求
     *
     * @param url
     * @param jsonStr
     * @return
     */
    public String sendPostFormHeaderRequest(String url, Map<String, Object> param, String businessLog, Map<String, Object> headMap) {
        LOGGER.info("{}发送x-www-from-urlencoded请求，请求url:{},请求参数:{},请求头:{}", businessLog, url, JSONObject.toJSONString(param), JSONObject.toJSONString(headMap));
        // 创建请求内容
        String response = "";
        StringEntity entity = null;

        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        httpPost.setHeader("charset", "UTF-8");
        for (Map.Entry<String, Object> entry : headMap.entrySet()) {
            httpPost.setHeader(entry.getKey(), String.valueOf(entry.getValue()));
        }
        //设置请求参数
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        if (Objects.nonNull(param) && !param.isEmpty()) {
            for (String key : param.keySet()) {
                Object value = param.get(key);
                if (!(value instanceof String)) {
                    value = JSONObject.toJSONString(value);
                }
                parameters.add(new BasicNameValuePair(key, (String) value));
            }
        }
        //构建form表单实体
        UrlEncodedFormEntity formEntity = null;
        try {
            formEntity = new UrlEncodedFormEntity(parameters, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("表单提交报错");
            e.printStackTrace();
        }
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);
        try {
            response = httpClientService.doPost(httpPost, "UTF-8");
        } catch (Exception e) {
            LOGGER.error("请求异常：url:{},param:{}", url, param, e);
            throw new AppException("httpPost请求异常");
        }
        LOGGER.info("请求成功,响应结果:{}", response);
        return response;

    }

    /**
     * 发送post请求
     *
     * @param url
     * @param param
     * @return
     */
    public String sendGetRequest(String url, Map<String, Object> param) {
        LOGGER.info("发送请求，请求url:{},请求参数:{}", url, JSONObject.toJSONString(param));
        HttpGet request = new HttpGet(url);
        String response = "";
        try {
            response = httpClientService.doGet(request);
        } catch (Exception e) {
            LOGGER.error("请求异常：url:{},param:{}", url, param, e);
            throw new AppException("httpPost请求异常");
        }
        LOGGER.info("请求成功,响应结果:{}",response);
        return response;
    }

    /**
     * 发送post请求
     * @param url
     * @param param
     * @return
     */
    public String sendGetHeadRequest(String url, Map<String,Object> param,Map<String,Object> headMap){
        LOGGER.info("发送请求，请求url:{},请求参数:{},请求头:{}",url, JSONObject.toJSONString(param),JSONObject.toJSONString(headMap));
        HttpGet request = new HttpGet(url);
        for (Map.Entry<String, Object> entry : headMap.entrySet()) {
            request.setHeader(entry.getKey(), String.valueOf(entry.getValue()));
        }
        String response = "";
        try {
            response = httpClientService.doGet(request);
        } catch (Exception e) {
            LOGGER.error("请求异常：url:{},param:{}", url, param, e);
            throw new AppException("httpGet请求异常");
        }
        LOGGER.info("请求成功,响应结果:{}",response);
        return response;
    }

}
