package cn.gtmap.realestate.inquiry.ui.config;

import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.elasticsearch.client.*;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/7/25
 * @description elasticsearch 连接工具
 */
public class ElasticLowerClient {

    private Logger logger = LoggerFactory.getLogger(ElasticLowerClient.class);

    private static ElasticLowerClient instance;
    private RestClient client;

    public static ElasticLowerClient getInstance(String hosts, String userName, String passWord) {
        if (instance == null) {
            synchronized (ElasticLowerClient.class) {
                if (instance == null) {
                    instance = new ElasticLowerClient(hosts, userName, passWord);
                }
            }
        }
        return instance;
    }

    /**
     * 带密码认证的
     *
     * @param hosts
     * @param userName
     * @param passWord
     */
    public ElasticLowerClient(String hosts, String userName, String passWord) {

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, passWord));  //es账号密码
        String[] hostsAndPorts = hosts.split(",");
        HttpHost[] httpHosts = new HttpHost[hostsAndPorts.length];
        for (int i = 0; i < hostsAndPorts.length; i++) {
            httpHosts[i] = HttpHost.create(hostsAndPorts[i]);
        }
        client = RestClient.builder(httpHosts).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                httpClientBuilder.disableAuthCaching();
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        }).build();
    }

    /**
     * 带ssl认证的
     *
     * @param hosts
     * @param keyStorePass
     * @param sslFile
     * @param keyStoreName
     */
    public ElasticLowerClient(String hosts, String keyStorePass, String sslFile, String keyStoreName) {

        try {
            Path keyStorePath = Paths.get(sslFile);
            KeyStore truststore = KeyStore.getInstance(keyStoreName);
            try (InputStream is = Files.newInputStream(keyStorePath)) {
                truststore.load(is, keyStorePass.toCharArray());
            }
            SSLContextBuilder sslBuilder = SSLContexts.custom().loadTrustMaterial(truststore, null);
            final SSLContext sslContext = sslBuilder.build();
            String[] hostsAndPorts = hosts.split(",");
            HttpHost[] httpHosts = new HttpHost[hostsAndPorts.length];
            for (int i = 0; i < hostsAndPorts.length; i++) {
                httpHosts[i] = HttpHost.create(hostsAndPorts[i]);
            }
            client = RestClient.builder(httpHosts).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                @Override
                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                    httpClientBuilder.disableAuthCaching();
                    return httpClientBuilder.setSSLContext(sslContext);
                }
            }).build();
        } catch (Exception e) {
            logger.error("ElasticSearch init fail!", e);
        }
    }
    public boolean existIndice(String indice) {
        List<String> existIndexList = new ArrayList<String>();
        try {
            Request request = new Request(
                    "HEAD",
                    "/" + indice + "");
            Response res = client.performRequest(request);
            if (res.getStatusLine().getStatusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    public boolean creatIndice(String indice) {
        String ent = "{\"settings\":{\"number_of_shards\":2,\"number_of_replicas\":1}}";
        List<String> existIndexList = new ArrayList<String>();
        try {
            Request request = new Request(
                    "PUT",
                    "/" + indice + "");
            request.setJsonEntity(ent);
            Response res = client.performRequest(request);
            if (res.getStatusLine().getStatusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    private void creatFieldData(String baseIndex, String field, String type) {

        String ent = "{\"properties\":{\"" + field + "\":{\"type\":\"keyword\"}}} ";
        String endpoint = "";
        if (StringUtils.isEmpty(type)) {
            endpoint = "/" + baseIndex + "/_mapping";
        } else {
            endpoint = "/" + baseIndex + "/_mapping" + "/" + type;
        }
        try {
            Request request = new Request(
                    "PUT",
                    endpoint);
            request.setJsonEntity(ent);
            Response res = client.performRequest(request);
            if (res.getStatusLine().getStatusCode() != 200) {
                String responseStr = EntityUtils.toString(res.getEntity());
                logger.error(responseStr);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public void insertList(List<String> list, String baseIndex, String type) throws IOException {

        if (!existIndice(baseIndex)) {
            creatIndice(baseIndex);
            logger.error("creatIndex:{}", baseIndex);
            creatFieldData(baseIndex, "method", type);
            logger.error("creatFieldData method");
            creatFieldData(baseIndex, "logLevel", type);
            logger.error("creatFieldData logLevel");
        }
        StringBuffer sendStr = new StringBuffer();
        for (int a = 0; a < list.size(); a++) {
            String map = list.get(a);
            String ent = "{\"index\":{\"_id\":\"" + UUIDGenerator.generate16() + "\"}} ";
            sendStr.append(ent);
            sendStr.append("\r\n");
            sendStr.append(map);
            sendStr.append("\r\n");
        }
        String endpoint = "";
        if (StringUtils.isEmpty(type)) {
            endpoint = "/" + baseIndex + "/_bulk";
        } else {
            endpoint = "/" + baseIndex + "/" + type + "/_bulk";
        }
        Request request = new Request(
                "PUT",
                endpoint);
        request.setJsonEntity(sendStr.toString());
        logger.debug(endpoint);
        logger.debug(sendStr.toString());
        client.performRequestAsync(request, new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                try {
                    String responseStr = EntityUtils.toString(response.getEntity());
                    //logger.error("ElasticSearch commit! success");
                    logger.debug("responseStr:{}", responseStr);
                } catch (IOException e) {
                    logger.error("ElasticSearch commit!", e);
                }
            }

            @Override
            public void onFailure(Exception e) {
                logger.error("ElasticSearch commit Failure!", e);
            }
        });
    }


    public String cat(String index) {
        String reStr = "";
        Request request = new Request(
                "GET",
                "/_cat/indices/" + index + "?v");
        try {
            Response res = client.performRequest(request);

            InputStream inputStream = res.getEntity().getContent();
            byte[] bytes = new byte[0];
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            String str = new String(bytes);
            reStr = str;
        } catch (Exception e) {
            e.printStackTrace();
            reStr = "";
        }
        return reStr;
    }

    public String get(String url, String queryStr) {
        String reStr = "";
        StringEntity stringEntity = new StringEntity(queryStr, "utf-8");
        stringEntity.setContentType("application/json");
        Request request = new Request(
                "GET",
                url);
        request.setEntity(stringEntity);
        try {
            Response res = client.performRequest(request);
            return EntityUtils.toString(res.getEntity(), "utf-8");
        } catch (Exception e) {
            reStr = "";
            e.printStackTrace();
        }
        return reStr;
    }

    public List<String> getExistIndices(String[] indices) {
        List<String> existIndexList = new ArrayList<String>();
        for (String index : indices) {
            try {
                Request request = new Request(
                        "HEAD",
                        "/" + index + "");
                Response res = client.performRequest(request);
                if (res.getStatusLine().getStatusCode() == 200) {
                    existIndexList.add(index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return existIndexList;
    }

    public boolean deleteIndex(String index) {
        try {
            Request request = new Request(
                    "DELETE",
                    "/" + index + "");
            Response res = client.performRequest(request);
            if (res.getStatusLine().getStatusCode() == 200) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void close() {
        try {
            client.close();
        } catch (IOException e) {
            logger.error("", e);
        }
    }

}
