package cn.gtmap.realestate.exchange.inf.hefei.fcjy;

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
public class BaxxTest {

    @Test
    public void test() {
        Map paramMap = new HashMap();
        paramMap.put("xmid", "600404D73E6A41219C5A622AAAC04AA1");
        List<Map> paramList = new ArrayList<>(1);
        paramList.add(paramMap);
        String beanName = "fcjybaxxbyxmid";
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanName);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean, paramList, getServiceList());
        //requestBuilder.setResponseBody(JSONObject.parseObject(response));
        Object response = requestBuilder.invoke();
        System.out.println("请求参数：" + JSONObject.toJSONString(requestBuilder.getRequestBody()));
        System.out.println("响应参数：" + JSONObject.toJSONString(response));
    }

    private List<BuildRequestService> getServiceList() {
        List<BuildRequestService> serviceList = new ArrayList<>();
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_REQUEST));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_RESPONSE));
        return serviceList;
    }
}
