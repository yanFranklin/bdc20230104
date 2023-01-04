package cn.gtmap.realestate.exchange.inf.hefei.nantong;

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
public class SjptCxwsTest {

    @Test
    public void test(){
        Map paramMap = new HashMap();
        paramMap.put("cxsqdh","cxsqdh");
        paramMap.put("wsbh","wsbh");
        String beanName = "sjpt_cxws";
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanName);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,paramMap,getServiceList());
        requestBuilder.setResponseBody(JSONObject.parseObject(response));
        Object response = requestBuilder.invoke();
        System.out.println(JSONObject.toJSONString(response));
    }

    private List<BuildRequestService> getServiceList(){
        List<BuildRequestService> serviceList = new ArrayList<>();
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_REQUEST));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_RESPONSE));
        return serviceList;
    }

    private static String response = "{" +
            "\"head\": {" +
            "\"code\": \"0000\"," +
            "\"msg\": \"success\"" +
            "}," +
            "\"data \": {" +
            "\"cxsqdh\": \"asjndjskxksfasxd\"," +
            "\"wsbh\": \"32020300001\"," +
            "\"cxjg\": [" +
            "{" +
            "\"xh\": 1," +
            "\"wjmc\": \"查询文书\"," +
            "\"wjlx\": \"1\"," +
            "\"wsnr\": \"5o6I5p2D5aeU5omY5Lmm\"" +
            "}" +
            "]" +
            "}" +
            "}";
}
