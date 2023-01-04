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
public class BaxxOldTest {

    @Test
    public void test(){
        Map paramMap = new HashMap();
        paramMap.put("cardNo","张三");
        paramMap.put("realNo","1242353546467");
        List<Map> paramList = new ArrayList<>(1);
        paramList.add(paramMap);
        String beanName = "fcjybaxxbycqzhandzjh";
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanName);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,paramList,getServiceList());
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
            "  \"code\": \"00000\",\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"SuperMap\": {\n" +
            "        \"HT\": {\n" +
            "          \"FW\": {\n" +
            "            \"FCBM\": \"皖2017合0245316号\",\n" +
            "            \"HTBH\": \"1564036706988\",\n" +
            "            \"XZQH\": \"滨湖区\",\n" +
            "            \"FWDZ\": \"合肥市滨湖区成都路2358号\",\n" +
            "            \"ZCS\": 29,\n" +
            "            \"SZC\": \"23\",\n" +
            "            \"JZMJ\": 108.59,\n" +
            "            \"JZJG\": \"钢筋混凝土结构\",\n" +
            "            \"FWYT\": \"住宅\",\n" +
            "            \"QSZYDX\": null,\n" +
            "            \"QSZYYT\": null,\n" +
            "            \"QSZYFS\": null,\n" +
            "            \"SYQQDFS\": null,\n" +
            "            \"QYRQ\": \"2019-07-25\",\n" +
            "            \"HTJE\": 1900000,\n" +
            "            \"FWDH\": \"11\",\n" +
            "            \"FJH\": \"2302\",\n" +
            "            \"TNMJ\": null,\n" +
            "            \"FSSSLX\": null,\n" +
            "            \"FSMJ\": null,\n" +
            "            \"DJ\": null,\n" +
            "            \"ZJGSID\": null,\n" +
            "            \"FWNM\": null,\n" +
            "            \"HTNM\": null,\n" +
            "            \"XQMC\": \"万象公馆\",\n" +
            "            \"BARQ\": \"2019-07-25\",\n" +
            "            \"QZTZRQ\": null,\n" +
            "            \"ZJGSDM\": \"91340100MA2MX02W5B\",\n" +
            "            \"ZJGSMC\": \"安徽如铭置业有限公司\",\n" +
            "            \"FKFS\": \"按揭付款\"\n" +
            "          },\n" +
            "          \"JYSF\": [\n" +
            "            {\n" +
            "              \"XM\": \"刘亚东\",\n" +
            "              \"ZJLX\": \"身份证\",\n" +
            "              \"ZJHM\": \"320981199012252735\",\n" +
            "              \"DH\": \"17855141530\",\n" +
            "              \"DJ\": null,\n" +
            "              \"GJ\": null,\n" +
            "              \"SZFE\": null,\n" +
            "              \"BJ\": 0,\n" +
            "              \"GYRBJ\": 0,\n" +
            "              \"GYFS\": null\n" +
            "            },\n" +
            "            {\n" +
            "              \"XM\": \"沈志成\",\n" +
            "              \"ZJLX\": \"身份证\",\n" +
            "              \"ZJHM\": \"340111199511076511\",\n" +
            "              \"DH\": \"13856531740\",\n" +
            "              \"DJ\": null,\n" +
            "              \"GJ\": null,\n" +
            "              \"SZFE\": null,\n" +
            "              \"BJ\": 1,\n" +
            "              \"GYRBJ\": 0,\n" +
            "              \"GYFS\": \"单独所有\"\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  ],\n" +
            "  \"msg\": \"操作成功\"\n" +
            "}";
}
