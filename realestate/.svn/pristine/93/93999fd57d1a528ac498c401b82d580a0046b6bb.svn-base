package cn.gtmap.realestate.util;

import cn.gtmap.realestate.certificate.CertificateApp;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.model.encrypt.SM2KeyPair;
import cn.gtmap.realestate.certificate.util.SM2Util;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/6/3
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CertificateApp.class)
public class EnumUtilTests {

    @Test
    public void valueTest() throws IOException {
        ResponseEnum[] responseEnums = ResponseEnum.values();

        System.out.print("enum值：" + responseEnums.length);

    }

}
