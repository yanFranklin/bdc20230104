package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.service.BdcDzzzHttpService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/3/21
 */
@Service
public class BdcDzzzHttpServiceImpl implements BdcDzzzHttpService {
    private Logger logger = LoggerFactory.getLogger(BdcDzzzHttpServiceImpl.class);

    @Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    private RequestConfig config;

    @Override
    public byte[] doGet(final String url) throws IOException {
        byte[] result = null;
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(config);
            response = httpClient.execute(httpGet);
            if (response != null && HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                result = EntityUtils.toByteArray(response.getEntity());
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }

        return result;
    }

    @Override
    public byte[] doPost(final String url, final List<NameValuePair> postParams, String charset) throws IOException {
        byte[] result = null;
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(config);
            httpPost.setEntity(new UrlEncodedFormEntity(postParams, charset));
            response = httpClient.execute(httpPost);
            if (response != null && HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                result = EntityUtils.toByteArray(response.getEntity());
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }

        return result;
    }

    @Override
    public String doPost(HttpPost httpPost, String charset) throws IOException {
        return sendRequest(httpPost,charset);
    }

    @Override
    public String sendRequest(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, String charset) throws IOException {
        String result = null;
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpEntityEnclosingRequestBase);
            if (response != null && HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
                result = EntityUtils.toString(response.getEntity(), StringUtils.isNotBlank(charset) ? charset : "UTF-8");
            }
        } finally {
            if (response != null){
                response.close();
            }
        }
        return result;
    }

    @Override
    public byte[] doPostWithJson(final String url, final String jsonString) throws IOException {
        byte[] result = null;
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(config);
            /*JSONObject jsonObject = new JSONObject();
            JSONObject headjsonObject = new JSONObject();
            jsonObject.put("head", headjsonObject);
            jsonObject.put("data", datajsonObject == null ? new JSONObject() : datajsonObject);*/
            StringEntity stringEntity = new StringEntity(jsonString, "utf-8");
            stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);

            if(null == response) {
                logger.info("调用服务地址：{}，返回response为空", url);
            } else {
                String data = new String(EntityUtils.toByteArray(response.getEntity()));
                int status = response.getStatusLine().getStatusCode();
                logger.info("调用服务地址：{}，返回状态：{}，返回数据内容：{}", url, status, data);
                if(HttpStatus.SC_OK == status) {
                    result = data.getBytes();
                }
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }

        return result;
    }

    @Override
    public byte[] doPostWithString(String url, String postParam) throws IOException {
        byte[] result = null;
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(config);
            StringEntity stringEntity = new StringEntity(postParam);
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            if (response != null && HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                result = EntityUtils.toByteArray(response.getEntity());
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }

        return result;
    }

    @Override
    public byte[] doPostWithString(String url, String postParam, String header, String charset) throws IOException {
        byte[] result = null;
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(config);
            httpPost.setHeader(HTTP.CONTENT_TYPE, header);
            StringEntity stringEntity = new StringEntity(postParam,
                    Charset.forName(charset));
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            if (response != null && HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                result = EntityUtils.toByteArray(response.getEntity());
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }

        return result;
    }

}
