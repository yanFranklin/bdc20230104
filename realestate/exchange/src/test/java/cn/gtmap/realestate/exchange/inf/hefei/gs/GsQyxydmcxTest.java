package cn.gtmap.realestate.exchange.inf.hefei.gs;

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
 * @version 1.0  2019-08-07
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class GsQyxydmcxTest {


    public static String RESPONSE_JSON = "{\"IsSuccess\":true,\"Result\":1,\"TotalCount\":1,\"DataType\":\"json\",\"Message\":\"Message:901-委办局/企业【9811000048659837】访问服务【201906211705467q2clWHwgHXWgJjAIaC】成功，已有效查询到数据！\",\"ErrorFieldList\":null,\"ErrorTableList\":null,\"Data\":null,\"ExceptionMessage\":\"\",\"ErrorServiceId\":null,\"DataList\":[{\"business_scope\":\"服装、皮具、打印设备、计算机软硬件、电子产品、安防设备、五金交电、通讯器材、塑料制品、针纺织品、二手车、汽车及汽车配件、润滑油、机械设备、汽车用品、保健用品、成人用品、玩具、农副产品、家居用品、鞋帽、箱包、办公用品及耗材、饰品、化妆品、图书、鲜花礼品、建材、工艺品、日用百货、家具、预包装食品兼散装食品、数码产品及配件、影音电器、电脑及配件、电脑耗材及外设、母婴用品、家用电器及配件批发及零售（含网上销售）；品牌管理及咨询；展览展示服务；会议会展服务；网站建设及技术开发、技术转让、技术咨询；商务信息咨询；电子商务平台建设；自营和代理各类商品及技术进出口业务（国家限定企业经营或禁止进出口的商品和技术除外）。（依法须经批准的项目，经相关部门批准后方可开展经营活动）\",\"lgl_reg_no\":\"340100000912077\",\"lgl_name\":\"合肥飞凌服饰有限公司\",\"operate_start_date\":\"2014-02-26 00:00:00.000000000\",\"operate_end_date\":\"2044-02-25 00:00:00.000000000\",\"operate_status_id\":\"1000\",\"lgl_org_address\":\"合肥市蜀山区金寨路155号黄金广场5幢C1106室\",\"legal_person_name\":\"丁飞飞\",\"lgl_credit_no\":\"9134010009286856XM\"}],\"ErrorCode\":0}";

    @Test
    public void test(){
        String beanName = "gs_qyxydmcx";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("qlrmc","1000");
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanName);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,paramMap,getServiceList());
        requestBuilder.setResponseBody(JSONObject.parseObject(RESPONSE_JSON));
        Object response = requestBuilder.invoke();
        System.out.println(JSONObject.toJSONString(requestBuilder.getRequestBody()));
        System.out.println(JSONObject.toJSONString(response));
    }

    private List<BuildRequestService> getServiceList(){
        List<BuildRequestService> serviceList = new ArrayList<>();
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_REQUEST));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_RESPONSE));
        return serviceList;
    }
}
