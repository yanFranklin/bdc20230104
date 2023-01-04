package cn.gtmap.realestate.exchange.inf.hefei.wwsq;

import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.ExchangeApp;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.core.dto.hefei.hyxx.response.OpenApiResponseHyxxData;
import cn.gtmap.realestate.exchange.core.dto.hefei.hyxx.response.OpenApiResponseHyxxModel;
import cn.gtmap.realestate.exchange.core.dto.wwsq.RequestHead;
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
 * @version 1.0  2019-07-06
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class HyxxTest {

    private static String RESPONSE_JSON = "{\"dataList\":[{\"card_no\":\"qlrzjh1\",\"jhdjrq\":\"2019-06-03\",\"lhdjrq\":\"2019-05-03\",\"marital_status\":\"1\",\"name\":\"qlrmc1\",\"q_card_no\":\"ypeiouzjh1\",\"q_name\":\"ypeioumc1\",\"s_card_no\":\"peiouzjh1\",\"s_name\":\"peioumc1\"},{\"card_no\":\"qlrzjh2\",\"jhdjrq\":\"2019-06-03\",\"lhdjrq\":\"2019-05-03\",\"marital_status\":\"2\",\"name\":\"qlrmc2\",\"q_card_no\":\"ypeiouzjh2\",\"q_name\":\"ypeioumc2\",\"s_card_no\":\"peiouzjh2\",\"s_name\":\"peioumc2\"}]}";

    private static String CONVERT_RESPONSE_JSON = "[{\"hyzt\":\"1\",\"jhdjrq\":\"2019-06-03\",\"lhdjrq\":\"2019-05-03\",\"peioumc\":\"peioumc1\",\"peiouzjh\":\"peiouzjh1\",\"qlrmc\":\"qlrmc1\",\"qlrzjh\":\"qlrzjh1\",\"ypeioumc\":\"ypeioumc1\",\"ypeiouzjh\":\"ypeiouzjh1\"},{\"hyzt\":\"2\",\"jhdjrq\":\"2019-06-03\",\"lhdjrq\":\"2019-05-03\",\"peioumc\":\"peioumc2\",\"peiouzjh\":\"peiouzjh2\",\"qlrmc\":\"qlrmc2\",\"qlrzjh\":\"qlrzjh2\",\"ypeioumc\":\"ypeioumc2\",\"ypeiouzjh\":\"ypeiouzjh2\"}]";

    @Test
    public void test(){
        Map<String,Object> paramMap = new HashMap();
        paramMap.put("head",getHead());
        paramMap.put("body",getBody());
        String beanId = "wwsqHyxx";
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanId);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,paramMap,getServiceList());
        requestBuilder.setResponseBody(JSONObject.parseObject(RESPONSE_JSON));
        Object result = requestBuilder.invoke();
        Assert.assertEquals(CONVERT_RESPONSE_JSON,JSONObject.toJSONString(result));
    }

    private RequestHead getHead(){
        RequestHead head = new RequestHead();
        head.setRegionCode("123");
        head.setOrgid("456");
        return head;
    }

    private Map getBody(){
        Map map= new HashMap();
        map.put("qlrmc","和其正");
        map.put("qlrzjh","2323260222222");
        return map;
    }

    private List<BuildRequestService> getServiceList(){
        List<BuildRequestService> serviceList = new ArrayList<>();
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_REQUEST));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_RESPONSE));
        return serviceList;
    }

    public static void main(String[] args) {
        OpenApiResponseHyxxModel model = new OpenApiResponseHyxxModel();
        model.setDataList(getDataList());
        System.out.println(JSONObject.toJSONString(model));
    }

    private static List<OpenApiResponseHyxxData> getDataList(){
        List<OpenApiResponseHyxxData> list = new ArrayList<>();
        OpenApiResponseHyxxData data1 = new OpenApiResponseHyxxData();
        data1.setCard_no("qlrzjh1");
        data1.setJhdjrq("2019-06-03");
        data1.setLhdjrq("2019-05-03");
        data1.setMarital_status("1");
        data1.setName("qlrmc1");
        data1.setQ_card_no("ypeiouzjh1");
        data1.setQ_name("ypeioumc1");
        data1.setS_card_no("peiouzjh1");
        data1.setS_name("peioumc1");

        list.add(data1);

        OpenApiResponseHyxxData data2 = new OpenApiResponseHyxxData();
        data2.setCard_no("qlrzjh2");
        data2.setJhdjrq("2019-06-03");
        data2.setLhdjrq("2019-05-03");
        data2.setMarital_status("2");
        data2.setName("qlrmc2");
        data2.setQ_card_no("ypeiouzjh2");
        data2.setQ_name("ypeioumc2");
        data2.setS_card_no("peiouzjh2");
        data2.setS_name("peioumc2");
        list.add(data2);
        return list;
    }
}
