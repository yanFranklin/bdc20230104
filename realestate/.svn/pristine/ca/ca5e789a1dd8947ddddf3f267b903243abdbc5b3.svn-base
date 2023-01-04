package cn.gtmap.realestate.exchange.inf.hefei.wwsq;

import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.ExchangeApp;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.core.dto.wwsq.RequestHead;
import cn.gtmap.realestate.exchange.core.dto.wwsq.fcjy.request.FcjyBaxxRequestBody;
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
 * @version 1.0  2019-07-01
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class FcjyBaxxTest {

    private static String RESPONSE_JSON = "{\"code\":\"00000\",\"data\":[{\"SuperMap\":{\"HT\":{\"FW\":{\"FCBM\":\"瑶120015426\",\"HTBH\":\"1447573794612\",\"XZQH\":\"瑶海区\",\"FWDZ\":\"合肥市瑶海区芜湖路\",\"ZCS\":31.0,\"SZC\":18.0,\"JZMJ\":93.48,\"JZJG\":\"钢筋混凝土结构\",\"FWYT\":\"住宅\",\"QSZYDX\":null,\"QSZYYT\":null,\"QSZYFS\":null,\"SYQQDFS\":null,\"QYRQ\":\"2015-11-15\",\"HTJE\":750000.0,\"FWDH\":\"1\",\"FJH\":\"1802\",\"TNMJ\":null,\"DJ\":null,\"ZJGSID\":null,\"FWNM\":null,\"HTNM\":null,\"XQMC\":\"御景湾\",\"BARQ\":\"2015-11-15\",\"QZTZRQ\":null,\"ZJGSDM\":\"340100000545777\",\"ZJGSMC\":\"安徽省义和房地产营销策划有限公司\"},\"JYSF\":[{\"XM\":\"侯泽华\",\"ZJLX\":\"身份证\",\"ZJHM\":\"41302719700120404X\",\"DH\":\"13956949776\",\"DJ\":null,\"GJ\":null,\"SZFE\":null,\"BJ\":0},{\"XM\":\"贺学武\",\"ZJLX\":\"身份证\",\"ZJHM\":\"342101197007150217\",\"DH\":\"13705586373\",\"DJ\":null,\"GJ\":null,\"SZFE\":null,\"BJ\":1}]}}}],\"msg\":\"操作成功\"}";

    @Test
    public void test(){
        Map<String,Object> paramMap = new HashMap();
        paramMap.put("head",getHead());
        paramMap.put("body",getBody());
        String beanId = "wwsqFcjyBaxx";
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanId);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,paramMap,getServiceList());
        requestBuilder.setResponseBody(JSONObject.parseObject(RESPONSE_JSON));
        Object result = requestBuilder.invoke();
        System.out.println(JSONObject.toJSONString(requestBuilder.getRequestBody()));
        System.out.println(JSONObject.toJSONString(result));
//        Assert.assertEquals(CONVERT_REQUEST_JSON,JSONObject.toJSONString(requestBuilder.getRequestBody()));
//        Assert.assertEquals(CONVERT_RESPONSE_JSON,JSONObject.toJSONString(result));
    }

    private List<BuildRequestService> getServiceList(){
        List<BuildRequestService> serviceList = new ArrayList<>();
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_REQUEST));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_RESPONSE));
        return serviceList;
    }


    private RequestHead getHead(){
        RequestHead head = new RequestHead();
        head.setRegionCode("123");
        head.setOrgid("456");
        return head;
    }

    private FcjyBaxxRequestBody getBody(){
        FcjyBaxxRequestBody body = new FcjyBaxxRequestBody();
        body.setCqzh("产权证号");
        body.setQlrzjh("2326333");
        return body;
    }
}
