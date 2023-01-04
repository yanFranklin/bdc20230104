package cn.gtmap.realestate.common.core.service.Impl;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.util.HttpsClientUtil;
import cn.gtmap.realestate.common.util.SkipHttpsUtil;
import com.google.common.base.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2016/12/14
 * @description HttpClient模拟http请求的通用服务
 */
@Service
public class HttpClientServiceImpl implements HttpClientService {

    protected static Logger LOGGER = LoggerFactory.getLogger(HttpClientServiceImpl.class);

    @Autowired
    private HttpClient httpClient;

    /**
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @param url 请求url地址
     * @return http请求的结果，已字节数组的方式返回
     * @description http get请求
     */
    @Override
    public byte[] doGet(final String url) throws Exception {
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            response = ((CloseableHttpClient) httpClient).execute(httpGet);
            if (response != null && HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
                return EntityUtils.toByteArray(response.getEntity());
            }else{
                throw new AppException("httpGet请求失败，响应码："+response.getStatusLine().getStatusCode()
                        +"，请求URL："+httpGet.getURI());
            }
        } catch (Exception e){
            throw new AppException(ErrorCode.SOCKET_EX, "调用远程附件url地址异常:" + e.getMessage());
        }
        finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * @param httpGet
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    public String doGet(HttpGet httpGet) throws IOException {
        String responseStr = "";
        CloseableHttpResponse response = null;
        byte[] resultByte = null;
        try {
            response = ((CloseableHttpClient) httpClient).execute(httpGet);
            if (response != null && HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
                resultByte =  EntityUtils.toByteArray(response.getEntity());
            }else{
                throw new AppException("httpGet请求失败，响应码："+response.getStatusLine().getStatusCode()
                        +"，请求URL："+httpGet.getURI());
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        if (resultByte != null) {
            responseStr = StringUtils.toEncodedString(resultByte, Charsets.UTF_8);
        }
        return responseStr;
    }

    /**
     * @param httpPost
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description http post请求
     */
    @Override
    public String doPost(HttpPost httpPost,String charset) throws IOException {
        return sendRequest(httpPost,charset);
    }

    @Override
    public String doHttpsPostHlzs(HttpPost httpPost, String charset) throws IOException {
        try (CloseableHttpClient httpclient = (CloseableHttpClient) SkipHttpsUtil.wrapClient();
             CloseableHttpResponse response = httpclient.execute(httpPost);) {
            if (response != null && HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                return EntityUtils.toString(response.getEntity(), StringUtils.isNotBlank(charset) ? charset : "UTF-8");
            } else {
                throw new AppException("http请求失败，响应码：" + response.getStatusLine().getStatusCode());
            }
        }
    }

    @Override
    public String doHttpsGetHlzs(HttpGet httpGet) throws IOException {
        try (CloseableHttpClient httpclient = (CloseableHttpClient) SkipHttpsUtil.wrapClient();
             CloseableHttpResponse response = httpclient.execute(httpGet);) {
             if (response != null && HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                throw new AppException("http请求失败，响应码：" + response.getStatusLine().getStatusCode());
            }
        }
    }

    @Override
    public String doHttpsPost(HttpPost httpPost, String zsmm, String zsym) throws IOException {
        try (CloseableHttpClient httpclient = HttpsClientUtil.getHttpsClient(zsmm, zsym);
             CloseableHttpResponse response = httpclient.execute(httpPost);) {
            if (response != null && HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                throw new AppException("http请求失败，响应码：" + response.getStatusLine().getStatusCode());
            }
        }
    }

    @Override
    public String doHttpsGet(HttpGet httpGet, String zsmm, String zsym) throws IOException {
        try (CloseableHttpClient httpclient = HttpsClientUtil.getHttpsClient(zsmm, zsym);
             CloseableHttpResponse response = httpclient.execute(httpGet);) {
            if (response != null && HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                throw new AppException("http请求失败，响应码：" + response.getStatusLine().getStatusCode());
            }
        }
    }

    /**
     * @param httpEntityEnclosingRequestBase
     * @param charset
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description http Get 请求
     */
    @Override
    public String sendRequest(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, String charset) throws IOException {
        CloseableHttpResponse response = null;
        try {
            // 辅助日志
            if(null != httpEntityEnclosingRequestBase){
                LOGGER.info("调用http请求entity为：{}",httpEntityEnclosingRequestBase.getEntity());
                if(null != httpEntityEnclosingRequestBase.getEntity()) {
                    try {
                        LOGGER.info("调用http请求getContent为：{}",httpEntityEnclosingRequestBase.getEntity().getContent());
                    }catch (Exception e){
                        //有可能会文件过长导致报错
                    }
                }
            }
            response = ((CloseableHttpClient) httpClient).execute(httpEntityEnclosingRequestBase);

            // 辅助日志
            if(response != null){
                LOGGER.info("请求返回响应码：{}",response.getStatusLine().getStatusCode());
            }else{
                LOGGER.info("返回response为null");
            }

            if (response != null && HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
                String responseStr = EntityUtils.toString(response.getEntity(),StringUtils.isNotBlank(charset) ? charset : "UTF-8");
                LOGGER.info("response.getEntity()为:{}",responseStr);
                return responseStr;
            } else {
                throw new AppException("http请求失败，响应码："+response.getStatusLine().getStatusCode()
                        +"，请求URL："+httpEntityEnclosingRequestBase.getURI());
            }
        } finally {
            if (response != null){
                response.close();
            }
        }
    }


    /**
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @param url 请求url地址
     * @param postParams 请求参数
     * @return http请求的结果，已字节数组的方式返回
     * @description http post请求
     */
    @Override
    public byte[] doPost(final String url, final List<NameValuePair> postParams) throws IOException {
        return doPost(url,postParams,"utf-8");
    }

    /**
     * liyinqiao 比上面方法多一个 设置字符集 短信接口接口字符集要求是 GBK
     * @param url
     * @param postParams
     * @param charset
     * @return
     * @throws IOException
     */
    @Override
    public byte[] doPost(final String url, final List<NameValuePair> postParams, String charset) throws IOException {
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(postParams, charset));
            response = ((CloseableHttpClient) httpClient).execute(httpPost);
            if (response != null && HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                return EntityUtils.toByteArray(response.getEntity());
            } else {
                return null;
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    @Override
    public InputStream doGetReturnStream(String url) throws Exception {
        byte[] responseBody = doGet(url);
        if(responseBody != null){
            InputStream byteResponseStream = new ByteArrayInputStream(responseBody);
            return byteResponseStream;
        }else{
            return null;
        }
    }
}
