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
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-22
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class DdztTest {

    @Test
    public void test(){
        JSONObject job = new JSONObject();
        job.put("ddbh","1050000865103662020031900128");
        job.put("ckh","1233445");
        job.put("shdm","1233445222222222222222222");
        job.put("zdh","1233445");

        JSONObject responseJob = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("Py_Ordr_No","1233445");
        data.put("PsRlt_Cd","1");
        data.put("Err_Pcsg_Inf","");
        responseJob.put("data",data);

        String beanName = "wwsq_pos_gxdd";
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanName);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,job,getServiceList());
        requestBuilder.setResponseBody(responseJob);
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

}
