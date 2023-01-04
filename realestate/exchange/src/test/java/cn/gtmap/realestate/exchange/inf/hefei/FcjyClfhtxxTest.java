package cn.gtmap.realestate.exchange.inf.hefei;

import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.ExchangeApp;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.service.inf.build.BuildRequestService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
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
 * @version 1.0  2019-05-28
 * @description 读取房产交易系统存量房合同信息
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class FcjyClfhtxxTest {

    private static String RESPONSE_JSON = "{\"code\":\"00000\",\"data\":{\"SuperMap\":{\"HT\":{\"FW\":{\"FCBM\":\"合产8110444480\",\"HTBH\":\"1558403694899\",\"XZQH\":\"高新区\",\"FWDZ\":\"合肥市高新区习友路1777号\",\"ZCS\":28.0,\"SZC\":\"28\",\"JZMJ\":118.03,\"JZJG\":\"钢筋混凝土结构\",\"FWYT\":\"住宅\",\"QSZYDX\":null,\"QSZYYT\":null,\"QSZYFS\":null,\"SYQQDFS\":null,\"QYRQ\":\"2019-05-21\",\"HTJE\":1600000.0,\"FWDH\":\"33\",\"FJH\":\"2801\",\"TNMJ\":null,\"FSSSLX\":null,\"FSMJ\":null,\"DJ\":null,\"ZJGSID\":null,\"FWNM\":null,\"HTNM\":null,\"XQMC\":\"玉兰公馆二期\",\"BARQ\":\"2019-05-21\",\"QZTZRQ\":null,\"ZJGSDM\":\"340100001100412(1-1)\",\"ZJGSMC\":\"安徽中墅房地产经纪有限公司\"},\"JYSF\":[{\"XM\":\"陈佳乐\",\"ZJLX\":\"身份证\",\"ZJHM\":\"440582198507195557\",\"DH\":\"18255135410\",\"DJ\":null,\"GJ\":null,\"SZFE\":null,\"BJ\":0,\"GYRBJ\":0},{\"XM\":\"彭姗姗\",\"ZJLX\":\"身份证\",\"ZJHM\":\"430521198806022627\",\"DH\":\"17775356040\",\"DJ\":null,\"GJ\":null,\"SZFE\":null,\"BJ\":1,\"GYRBJ\":0},{\"XM\":\"罗海星\",\"ZJLX\":\"身份证\",\"ZJHM\":\"342201198805105613\",\"DH\":\"17775356040\",\"DJ\":null,\"GJ\":null,\"SZFE\":null,\"BJ\":1,\"GYRBJ\":1}]}}},\"msg\":\"操作成功\"}";

    private static String CONVERT_RESPONSE_JSON = "{\"msg\":\"操作成功\",\"bdcSlJyxx\":{\"htbah\":\"1558403694899\",\"htbasj\":1558368000000,\"htbh\":\"1558403694899\",\"htdjsj\":1558368000000,\"htmj\":118.03,\"jyje\":1600000.0},\"code\":\"00000\",\"bdcQlr\":[{\"dh\":\"18255135410\",\"qlrlb\":\"0\",\"qlrmc\":\"陈佳乐\",\"zjh\":\"440582198507195557\",\"zjzl\":1},{\"dh\":\"17775356040\",\"qlrlb\":\"1\",\"qlrmc\":\"彭姗姗\",\"zjh\":\"430521198806022627\",\"zjzl\":1},{\"dh\":\"17775356040\",\"qlrlb\":\"1\",\"qlrmc\":\"罗海星\",\"zjh\":\"342201198805105613\",\"zjzl\":1}],\"bdcSlSqr\":[{\"dh\":\"18255135410\",\"sqrlb\":\"0\",\"sqrmc\":\"陈佳乐\",\"zjh\":\"440582198507195557\",\"zjzl\":1},{\"dh\":\"17775356040\",\"sqrlb\":\"1\",\"sqrmc\":\"彭姗姗\",\"zjh\":\"430521198806022627\",\"zjzl\":1},{\"dh\":\"17775356040\",\"sqrlb\":\"1\",\"sqrmc\":\"罗海星\",\"zjh\":\"342201198805105613\",\"zjzl\":1}],\"bdcSlFwxx\":{\"fjh\":\"2801\",\"fwdh\":\"33\",\"fwjg\":3,\"fwyt\":10,\"xqmc\":\"玉兰公馆二期\"}}";

    private List<BuildRequestService> getServiceList(){
        List<BuildRequestService> serviceList = new ArrayList<>();
        serviceList.add((BuildRequestService) Container.getBean("buildResponseBodyServiceImpl"));
        return serviceList;
    }

    @Test
    public void testFcjyClfHtxx(){
        String contractNo = "10001";
        String beanId = "hfFcjyClfHtxx";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("contractNo",contractNo);
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanId);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,paramMap,getServiceList());
        requestBuilder.setResponseBody(JSONObject.parseObject(RESPONSE_JSON));
        Object result = requestBuilder.invoke();
//        System.out.println(JSONObject.toJSONString(result));
        Assert.assertEquals(CONVERT_RESPONSE_JSON,JSONObject.toJSONString(result));
    }

}
