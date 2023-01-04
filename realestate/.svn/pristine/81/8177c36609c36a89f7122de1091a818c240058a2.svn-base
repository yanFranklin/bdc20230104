package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.ex.AppException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.concurrent.TimeUnit;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0, 2020/09/03
 * @description HttpClient模拟https请求的通用服务 外网申请等需要验证证书的用
 */
public class HttpsClientUtil {
    protected static Logger LOGGER = LoggerFactory.getLogger(HttpsClientUtil.class);

    /**
     * 获得https的CloseableHttpClient
     *
     * @param zsmm 证书密码
     * @param zsym 绑定的证书域名
     * @return
     */
    public static CloseableHttpClient getHttpsClient(String zsmm, String zsym) {
        if (StringUtils.isNoneBlank(zsmm, zsym)) {
            //调用ssl接口客户端jvm安装证书路径
            SSLContext sslContext = null;
            try {
                File file = new File(System.getProperty("java.home") + "/lib/security/cacerts");
                LOGGER.info("SSL验证获取证书文件路径：{},{}", file.getPath(), file.getAbsolutePath());
                sslContext = SSLContexts.custom()
                        .loadTrustMaterial(file, zsmm.toCharArray()).build();
            } catch (Exception e) {
                LOGGER.error("验证证书失败,", e);
            }
            if (sslContext == null) {
                throw new AppException("验证证书失败，证书密码：" + zsmm + ",证书域名：" + zsym);
            }
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslConnectionSocketFactory).build();
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry, null, null, null, 30 * 1000, TimeUnit.MILLISECONDS);
            connectionManager.setMaxTotal(100);
            connectionManager.setDefaultMaxPerRoute(20);
            RequestConfig config = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
            final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                    Boolean result = hv.verify(zsym, sslSession);
                    return result;
                }
            };
            return HttpClientBuilder.create()
                    .setSSLSocketFactory(sslConnectionSocketFactory)
                    .setConnectionManager(connectionManager)
                    .setDefaultRequestConfig(config).setSSLHostnameVerifier(hostnameVerifier).build();
        }
        return HttpClients.createDefault();
    }

    /**
     * 设置信任自签名证书
     *
     * @param keyStorePath 密钥库路径
     * @param keyStorepass 密钥库密码
     * @return
     */
    public static SSLContext custom(String keyStorePath, String keyStorepass) {
        SSLContext sc = null;
        FileInputStream instream = null;
        KeyStore trustStore = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            instream = new FileInputStream(new File(keyStorePath));
            trustStore.load(instream, keyStorepass.toCharArray());
            // 相信自己的CA和所有自签名的证书
            sc = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
            // 构造 javax.net.ssl.TrustManager 对象
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
            tmf.init(trustStore);
            TrustManager[] tms = tmf.getTrustManagers();
            // 使用构造好的 TrustManager 访问相应的 https 站点
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tms, new java.security.SecureRandom());
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            try {
                instream.close();
            } catch (IOException e) {
            }
        }
        return sc;
    }


}