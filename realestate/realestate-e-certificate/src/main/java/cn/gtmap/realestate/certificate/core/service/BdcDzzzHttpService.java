package cn.gtmap.realestate.certificate.core.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/3/21
 */
public interface BdcDzzzHttpService {

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param url 请求url地址
     * @return http请求的结果，已字节数组的方式返回
     * @description http get请求
     */
    byte[] doGet(final String url) throws IOException;

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param url
     * @param postParams
     * @param charset
     * @return
     * @throws IOException
     */
    byte[] doPost(final String url, final List<NameValuePair> postParams, String charset) throws IOException;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param httpPost
     * @return java.lang.String
     * @description http post请求
     */
    String doPost(HttpPost httpPost, String charset) throws IOException;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param httpEntityEnclosingRequestBase
     * @param charset
     * @return java.lang.String
     * @description http Get 请求
     */
    String sendRequest(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, String charset) throws IOException;

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param url 请求url地址
     * @param jsonString 请求参数
     * @return http请求的结果，已字节数组的方式返回
     * @description http post请求 请求参数是有head，data格式的json形式
     */
    byte[] doPostWithJson(final String url, final String jsonString) throws IOException;

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param url 请求url地址
     * @param postParam 请求参数
     * @return http请求的结果，已字节数组的方式返回
     * @description http post请求
     */
    byte[] doPostWithString(final String url, final String postParam) throws IOException;

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param url
     * @param postParam
     * @param header
     * @param charset
     * @throws IOException
     * @description http post请求webservice服务
     */
    byte[] doPostWithString(String url, String postParam, String header, String charset) throws IOException;
}
