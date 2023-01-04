package cn.gtmap.realestate.exchange.inf.hefei;

import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.ExchangeApp;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.core.dto.hefei.swxx.jswszt.JsWsztRequestBody;
import cn.gtmap.realestate.exchange.core.dto.wwsq.RequestHead;
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
 * @version 1.0  2019-07-08
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class JsWsztTest {

    @Test
    public void test(){
        Map paramMap = new HashMap();
        paramMap.put("head",getHead());
        paramMap.put("data",getRequestBody());
        String beanName = "jsWszt";
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanName);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,paramMap,getServiceList());
        Object response = requestBuilder.invoke();
        System.out.println(JSONObject.toJSONString(response));
    }

    private List<BuildRequestService> getServiceList(){
        List<BuildRequestService> serviceList = new ArrayList<>();
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_REQUEST));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_RESPONSE));
        return serviceList;
    }

    private JsWsztRequestBody getRequestBody(){
        JsWsztRequestBody body = new JsWsztRequestBody();
        body.setHtbh("1233456");
        body.setWszt("1");
        return body;
    }

    private RequestHead getHead(){
        RequestHead head = new RequestHead();
        head.setRegionCode("123");
        head.setOrgid("456");
        return head;
    }
}
