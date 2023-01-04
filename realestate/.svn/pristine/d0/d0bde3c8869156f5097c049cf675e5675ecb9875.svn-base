package cn.gtmap.realestate.exchange.core.aop;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * .
 * <p/>一张网AOP推送
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0, 2014/6/23
 */
@Aspect
@Component
public class YzwAspect {
    private final Logger log = LoggerFactory.getLogger(YzwAspect.class);

    @Value("${shareData.yzw.enable:false}")
    private boolean shareYzw;
    @Value("${etl.url}")
    private String etlUrl;
    @Autowired
    private HttpClient httpClient;

    /**
     * 推送过程表之后执行一张网推送
     *
     * @param proid
     */
    @After("execution (* cn.gtmap.realestate.exchange.service.impl.inf.yzw.YzwServiceImpl.*(..)) && args(gzlslid,isbj)")
    public void shareDataToYzw(final String gzlslid,final boolean isbj) {
        // 自动推送共享业务数据到前置库
        if (shareYzw) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    StringBuilder sb = new StringBuilder(etlUrl);
                    sb.append("/rest/v1.0/push/qlygData?gzlslid=");
                    sb.append(gzlslid);
                    CloseableHttpResponse response = null;
                    try {
                        HttpGet httpGet = new HttpGet(sb.toString());
                        response = ((CloseableHttpClient) httpClient).execute(httpGet);
                        log.info("etl推送一张网数据成功，推送工作流实例ID为" + gzlslid);
                    } catch (IOException e) {
                        log.error("etl推送一张网数据失败，推送工作流实例ID为" + gzlslid, e);
                    } finally {
                        if (response != null) {
                            try {
                                response.close();
                            } catch (IOException e) {
                                log.error("etl推送一张网数据失败，推送工作流实例ID为" + gzlslid, e);
                            }
                        }
                    }
                }
            };
            new Thread(runnable).start();
        }
    }
}
