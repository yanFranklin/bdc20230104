package cn.gtmap.realestate.exchange.inf.hefei;

import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.ExchangeApp;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.service.inf.build.BuildRequestService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-14
 * @description 税务取消作废接口测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class SwQszfTest {

    @Test
    public void test(){
        String beanName = "swQxzf";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("xmid","1000");
        paramMap.put("slbh","201906111234");
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanName);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,paramMap,getServiceList());
        requestBuilder.invoke();
        // 比对请求
        Object requestBody = requestBuilder.getRequestBody();
        Assert.assertEquals(JSONObject.toJSONString(requestBody),REQUESTJSON);
    }

    private  static String getCurrDate(){
        FastDateFormat formater = FastDateFormat.getInstance("yyyyMMdd");
        return formater.format(new Date());
    }

    private static String REQUESTJSON = "{\"body\":{\"ywsldh\":\"201906111234\"},\"head\":{\"channel_id\":\"AHxxBDC\",\"token\":\"GJSWZJAHSSWJ\",\"tran_date\":\""+getCurrDate()+"\",\"tran_id\":\"ahsw.fcjy.yth.fcjyqxzf\",\"tran_seq\":\"1000\"}}";

    private List<BuildRequestService> getServiceList(){
        List<BuildRequestService> serviceList = new ArrayList<>();
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_REQUEST));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_RESPONSE));
        return serviceList;
    }
}
