package cn.gtmap.realestate.common.core.service;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2016/12/14
 * @description HttpClient模拟http请求的通用服务
 */
public interface HttpClientService {
    /**
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @param url 请求url地址
     * @return http请求的结果，已字节数组的方式返回
     * @description http get请求
     */
    byte[] doGet(final String url) throws Exception;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param httpGet
     * @return java.lang.String
     * @description
     */
    String doGet(HttpGet httpGet) throws IOException;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param httpPost
     * @return java.lang.String
     * @description http post请求
     */
    String doPost(HttpPost httpPost, String charset) throws IOException;

    /**
     * @param httpPost
     * @return java.lang.String
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description https post请求 忽略证书
     */
    String doHttpsPostHlzs(HttpPost httpPost, String charset) throws IOException;

    /**
     * @param httpPost
     * @return java.lang.String
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description https get 忽略证书
     */
    String doHttpsGetHlzs(HttpGet httpGet) throws IOException;
    /**
     * @param httpPost
     * @param zsmm 证书密码
     * @param zsym 证书域名
     * @return java.lang.String
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description https post请求 验证证书 默认就是utf-8的字符集处理
     */
    String doHttpsPost(HttpPost httpPost, String zsmm, String zsym) throws IOException;

    /**
     * @param httpGet
     * @param zsmm    证书密码
     * @param zsym    证书域名
     * @return java.lang.String
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description https get请求 验证证书 默认就是utf-8的字符集处理
     */
    String doHttpsGet(HttpGet httpGet, String zsmm, String zsym) throws IOException;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param httpEntityEnclosingRequestBase
     * @param charset
     * @return java.lang.String
     * @description http Get 请求
     */
    String sendRequest(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, String charset) throws IOException;
    /**
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @param url 请求url地址
     * @param postParams 请求参数
     * @return http请求的结果，已字节数组的方式返回
     * @description http post请求
     */
    byte[] doPost(final String url, final List<NameValuePair> postParams) throws IOException;


    /**
     * liyinqiao 比上面方法多一个 设置字符集 短信接口接口字符集要求是 GBK
     * @param url
     * @param postParams
     * @param charset
     * @return
     * @throws IOException
     */
    byte[] doPost(final String url, final List<NameValuePair> postParams, String charset) throws IOException;

    /**
     * liyinqiao 返回InputStream
     * @param url
     * @return
     * @throws IOException
     */
    InputStream doGetReturnStream(final String url) throws Exception;
}