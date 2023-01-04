package cn.gtmap.realestate.exchange.inf.hefei.wwsq;

import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.ExchangeApp;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.core.dto.wwsq.RequestHead;
import cn.gtmap.realestate.exchange.core.dto.wwsq.fcjy.request.FcjyClfHtxxRequestBody;
import cn.gtmap.realestate.exchange.service.inf.build.BuildRequestService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
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
 * @version 1.0  2019-06-26
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class FcjyClfHtxxTest {

    private static String RESPONSE_JSON = "{\"code\":\"00000\",\"data\":{\"SuperMap\":{\"HT\":{\"FW\":{\"FCBM\":\"合产8110444480\",\"HTBH\":\"1558403694899\",\"XZQH\":\"高新区\",\"FWDZ\":\"合肥市高新区习友路1777号\",\"ZCS\":28.0,\"SZC\":\"28\",\"JZMJ\":118.03,\"JZJG\":\"钢筋混凝土结构\",\"FWYT\":\"住宅\",\"QSZYDX\":null,\"QSZYYT\":null,\"QSZYFS\":null,\"SYQQDFS\":null,\"QYRQ\":\"2019-05-21\",\"HTJE\":1600000.0,\"FWDH\":\"33\",\"FJH\":\"2801\",\"TNMJ\":null,\"FSSSLX\":null,\"FSMJ\":null,\"DJ\":null,\"ZJGSID\":null,\"FWNM\":null,\"HTNM\":null,\"XQMC\":\"玉兰公馆二期\",\"BARQ\":\"2019-05-21\",\"QZTZRQ\":null,\"ZJGSDM\":\"340100001100412(1-1)\",\"ZJGSMC\":\"安徽中墅房地产经纪有限公司\"},\"JYSF\":[{\"XM\":\"陈佳乐\",\"ZJLX\":\"身份证\",\"ZJHM\":\"440582198507195557\",\"DH\":\"18255135410\",\"DJ\":null,\"GJ\":null,\"SZFE\":null,\"BJ\":0,\"GYRBJ\":0},{\"XM\":\"彭姗姗\",\"ZJLX\":\"身份证\",\"ZJHM\":\"430521198806022627\",\"DH\":\"17775356040\",\"DJ\":null,\"GJ\":null,\"SZFE\":null,\"BJ\":1,\"GYRBJ\":0},{\"XM\":\"罗海星\",\"ZJLX\":\"身份证\",\"ZJHM\":\"342201198805105613\",\"DH\":\"17775356040\",\"DJ\":null,\"GJ\":null,\"SZFE\":null,\"BJ\":1,\"GYRBJ\":1}]}}},\"msg\":\"操作成功\"}";
    private static String CONVERT_REQUEST_JSON = "{\"contractNo\":\"789\"}";
    private static String CONVERT_RESPONSE_JSON = "{\"head\":{\"msg\":\"操作成功\",\"regionCode\":\"高新区\",\"statusCode\":\"00000\"},\"body\":{\"barq\":\"2019-05-21\",\"fcbahth\":\"1558403694899\",\"gxrxx\":[{\"gxrlx\":\"0\",\"gxrmc\":\"陈佳乐\",\"gxrsfzjzl\":\"身份证\",\"gxrzjh\":\"440582198507195557\"},{\"gxrlx\":\"1\",\"gxrmc\":\"彭姗姗\",\"gxrsfzjzl\":\"身份证\",\"gxrzjh\":\"430521198806022627\"},{\"gxrlx\":\"1\",\"gxrmc\":\"罗海星\",\"gxrsfzjzl\":\"身份证\",\"gxrzjh\":\"342201198805105613\"}],\"htqdrq\":\"2019-05-21\",\"jyjg\":\"1600000.0\",\"zl\":\"合肥市高新区习友路1777号\"}}";

    @Test
    public void test(){
        Map<String,Object> paramMap = new HashMap();
        paramMap.put("head",getHead());
        paramMap.put("body",getBody());
        String beanId = "wwsqFcjyClfHtxx";
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanId);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,paramMap,getServiceList());
        requestBuilder.setResponseBody(JSONObject.parseObject(RESPONSE_JSON));
        Object result = requestBuilder.invoke();
        Assert.assertEquals(CONVERT_REQUEST_JSON,JSONObject.toJSONString(requestBuilder.getRequestBody()));
        Assert.assertEquals(CONVERT_RESPONSE_JSON,JSONObject.toJSONString(result));
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

    private FcjyClfHtxxRequestBody getBody(){
        FcjyClfHtxxRequestBody body = new FcjyClfHtxxRequestBody();
        body.setHtbh("789");
        return body;
    }
}
