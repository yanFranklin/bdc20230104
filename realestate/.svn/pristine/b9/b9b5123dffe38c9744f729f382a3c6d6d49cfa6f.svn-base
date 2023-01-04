package cn.gtmap.realestate.register.util;


import cn.gtmap.realestate.common.core.ex.AppException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author <a href="mailto:qianguoyi@gtmap.cn">qianguoyi</a>
 * @version 1.0
 * @date 2021/6/28 14:36
 * @description post请求
 */
public final class HttpPost{
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpPost.class);

    private HttpPost(){
    }

    public static String doPost(String httpUrl,Part[] parts) throws IOException{
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(httpUrl);
        httpClient.getParams().setContentCharset("utf-8");
        int resultCode;
        postMethod.setRequestEntity(new MultipartRequestEntity(parts,postMethod.getParams()));
        resultCode = httpClient.executeMethod(postMethod);
        LOGGER.info("调用接口的回显状态码:" + resultCode);
        if(resultCode != 200){
            throw new AppException("接口调用失败,接口调用返回状态码:" + resultCode);
        }
        String result = StreamUtils.copyToString(postMethod.getResponseBodyAsStream(),Charset.forName("utf-8"));
        return result;
    }
}
