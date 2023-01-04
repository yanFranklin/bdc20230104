package cn.gtmap.realestate.exchange.inf.hefei.wwsq;

import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.ExchangeApp;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.service.inf.build.BuildRequestService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-22
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class CxjfztTest {

    @Test
    public void test(){
        JSONObject job = new JSONObject();
        job.put("slbh","1233445");
        String beanName = "wwsq_ykqjfztcx";
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanName);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,job,getServiceList());
        requestBuilder.setResponseBody(JSONObject.parseObject(response));
        Object response = requestBuilder.invoke();
        System.out.println("请求参数：" + JSONObject.toJSONString(requestBuilder.getRequestBody()));
        System.out.println("响应参数：" + JSONObject.toJSONString(response));
    }

    private List<BuildRequestService> getServiceList(){
        List<BuildRequestService> serviceList = new ArrayList<>();
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_REQUEST));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_RESPONSE));
        return serviceList;
    }

    private static String response = "{\n" +
            "\t\"head\": {\n" +
            "\t\t\"regionCode\": \"地区编码\",\n" +
            "\t\t\" orgid \": \"机构编码\",\n" +
            "\t\t\" statusCode \": \"响应状态码\",\n" +
            "\t\t\" msg \": \"响应信息\"\n" +
            "\t},\n" +
            "\t\"data\": {\n" +
            "    \"jfzt\":\"1\" \n" +
            "}\t}\n";
}
