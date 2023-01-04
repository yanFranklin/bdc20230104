package cn.gtmap.realestate.exchange.inf.hefei.wwsq;

import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.ExchangeApp;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.core.dto.wwsq.RequestHead;
import cn.gtmap.realestate.exchange.core.dto.wwsq.zsyz.request.ZsyzRequestBody;
import cn.gtmap.realestate.exchange.service.inf.build.BuildRequestService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.util.constants.Constants;
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
 * @version 1.0  2019-07-01
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class ZsyzTest {


    @Test
    public void test(){
        Map<String,Object> paramMap = new HashMap();
        paramMap.put("head",getHead());
        paramMap.put("body",getBody());
        String beanId = "wwsqZsyz";
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanId);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,paramMap,getServiceList());
        Object result = requestBuilder.invoke();
        System.out.println(result);
    }

    private RequestHead getHead(){
        RequestHead head = new RequestHead();
        head.setRegionCode("123");
        head.setOrgid("456");
        return head;
    }

    private ZsyzRequestBody getBody(){
        ZsyzRequestBody body = new ZsyzRequestBody();
        body.setCertId("789");
        return body;
    }

    private List<BuildRequestService> getServiceList(){
        List<BuildRequestService> serviceList = new ArrayList<>();
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_SERVICEINFO));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_REQUEST));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_SENDREQUEST));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_RESPONSE));
        return serviceList;
    }

}
