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
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019-07-06
 * @description 家庭成员
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class JtcyTest {

    private static String result = "{\"IsSuccess\": \"true\",\"TotalCount\": \"金晓艳\",\"ExceptionMessage\": \"金晓艳\",\"Message\": \"金晓艳\",\"DataType\": \"金晓艳\",\"ErrorCode\": \"金晓艳\",\"Result\": \" 340111197307267565 \",\"DataList\": [{\"xm\": \"父亲\",\"zj\": \"152827199011110909\",\"gx\": \"父亲\"}, {\t\"xm\": \"母亲\",\t\"zj\": \"152827199011110909\",\"gx\": \"母亲\"}, {\"xm\": \"子女\",\"zj\": \"152827199011110909\",\"gx\": \"子女\"}, {\"xm\": \"兄弟姐妹\",\"zj\": \"152827199011110909\",\"gx\": \"兄弟姐妹\"}]}";

    private static String RESPONSE_JSON = "{\"DataList\":[{\"xm\":\"123\",\"gmsfhm\":\"123\",\"mlxz\":\"123\",\"jgssxq\":\"123\",\"yhzgx\":\"123\"}]}";

    private static String CONVERT_RESPONSE_JSON = "[{\"hjdz\":\"123\",\"jgssxq\":\"123\",\"qlrmc\":\"123\",\"qlrzjh\":\"123\",\"yhzgx\":\"123\"}]";
    @Test
    public void test(){
        String beanName = "jtcyCx";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("qlrmc","111");
        paramMap.put("qlrzjh","222");
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanName);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,paramMap,getServiceList());
        requestBuilder.setResponseBody(JSONObject.parseObject(result));
        Object result = requestBuilder.invoke();
        System.out.println(JSONObject.toJSONString(result));
//        Assert.assertEquals(CONVERT_RESPONSE_JSON,JSONObject.toJSONString(result));
    }


    private List<BuildRequestService> getServiceList(){
        List<BuildRequestService> serviceList = new ArrayList<>();
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_SERVICEINFO));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_REQUEST));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_RESPONSE));
        return serviceList;
    }


}
