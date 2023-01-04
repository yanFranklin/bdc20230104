package cn.gtmap.realestate.exchange.inf.hefei;

import cn.gtmap.realestate.common.core.dto.exchange.swxx.QuerySwxxResponseDTO;
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
 * @description 税务查询
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class SwcxTest {

    @Test
    public void test(){
        String beanName = "swCxxx";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("xmid","1000");
        paramMap.put("slbh","201906111234");
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanName);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,paramMap,getServiceList());
        requestBuilder.setResponseBody(JSONObject.parseObject(RESPONSE_JSON));
        Object response = requestBuilder.invoke();
        // 输入请求实体
        Assert.assertEquals(CONVERT_REQUESTJSON,JSONObject.toJSONString(requestBuilder.getRequestBody()));
        QuerySwxxResponseDTO responseDTO
                = JSONObject.parseObject(JSONObject.toJSONString(response),QuerySwxxResponseDTO.class);
        Assert.assertEquals(CONVERT_REPONSE,JSONObject.toJSONString(responseDTO));


        InterfaceRequestBuilder requestBuilder2 = new InterfaceRequestBuilder(exchangeBean,paramMap,getServiceList());
        requestBuilder2.setResponseBody(JSONObject.parseObject(RESPONSE_JSON2));
        Object response2 = requestBuilder2.invoke();
        QuerySwxxResponseDTO responseDTO2
                = JSONObject.parseObject(JSONObject.toJSONString(response2),QuerySwxxResponseDTO.class);
        Assert.assertEquals(CONVERT_REPONSE2,JSONObject.toJSONString(responseDTO2));
    }

    private List<BuildRequestService> getServiceList(){
        List<BuildRequestService> serviceList = new ArrayList<>();
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_REQUEST));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_RESPONSE));
        return serviceList;
    }
    private  static String getCurrDate(){
        FastDateFormat formater = FastDateFormat.getInstance("yyyyMMdd");
        return formater.format(new Date());
    }

    private static String CONVERT_REPONSE = "{\"responseCode\":\"200\",\"responseMsg\":\"该合同已经完税\",\"wszt\":\"1\"}";

    private static String RESPONSE_JSON = "{\n" +
            "\t\"head\": {\n" +
            "\t\t\"channel_id\": \"AHxxBDC\",\n" +
            "\t\t\"rtn_code\": \"200\",\n" +
            "\t\t\"rtn_msg\": \"该合同已经完税\",\n" +
            "\t\t\"token\": \"11111111\",\n" +
            "\t\t\"tran_date\": \"2018xxx9\",\n" +
            "\t\t\"tran_id\": \"ahsw.fcjy.yth.fcjyxxcx\",\n" +
            "\t\t\"tran_seq\": \"4bc12232e7907b56a6006ad3dba4\"\n" +
            "\t},\n" +
            "\"body\": {\n" +
            "\"bj\": \"1\"\n" +
            "}\n" +
            "}";

    private static String RESPONSE_JSON1 = "{\n" +
            "\t\"body\": {\n" +
            "\t\t\"jbrdh\": \"0564-xxxx277\",\n" +
            "\t\t\"jbrxm\": \"xxxx\",\n" +
            "\t\t\"sbmxcxlb\": []\n" +
            "\t},\n" +
            "\t\"head\": {\n" +
            "\t\t\"channel_id\": \"AHxxBDC\",\n" +
            "\t\t\"rtn_code\": \"200\",\n" +
            "\t\t\"rtn_msg\": \"成功\",\n" +
            "\t\t\"token\": \"11111111\",\n" +
            "\t\t\"tran_date\": \"20181119\",\n" +
            "\t\t\"tran_id\": \"ahsw.fcjy.yth.fcjyxxcx\",\n" +
            "\t\t\"tran_seq\": \"4bc12232e7907b56a6006ad3dba4\"\n" +
            "\t}\n" +
            "}";


    private static String CONVERT_REQUESTJSON= "{\"body\":{\"ywsldh\":\"201906111234\"},\"head\":{\"channel_id\":\"AHxxBDC\",\"token\":\"GJSWZJAHSSWJ\",\"tran_date\":\""+getCurrDate()+"\",\"tran_id\":\"ahsw.fcjy.yth.fcjyxxcx\",\"tran_seq\":\"1000\"}}";

    private static String CONVERT_REPONSE2 = "{\"hsxxList\":[{\"bdcSlHsxxDO\":{\"hdjsjg\":718000.0,\"sjyzhj\":14360.0,\"sqrlb\":\"1\",\"ynsehj\":14360.0},\"bdcSlHsxxMxDOList\":[{\"mxzl\":\"10119\",\"sjnse\":14360.0,\"ynse\":14360.0}]},{\"bdcSlHsxxDO\":{\"hdjsjg\":718000.0,\"sjyzhj\":0.0,\"sqrlb\":\"0\",\"ynsehj\":0.0},\"bdcSlHsxxMxDOList\":[{\"mxzl\":\"10106\",\"sjnse\":0.0,\"ynse\":0.0},{\"mxzl\":\"10101\",\"sjnse\":0.0,\"ynse\":0.0}]}],\"responseCode\":\"200\",\"responseMsg\":\"成功\"}";

    private static String RESPONSE_JSON2 = "{\n" +
            "\t\"body\": {\n" +
            "\t\t\"sbmxcxlb\": [{\n" +
            "\t\t\t\"cjjg\": 718000.0,\n" +
            "\t\t\t\"fwwzdz\": \"开发区xxxxxxxxxx1-201\",\n" +
            "\t\t\t\"jyskblb\": [{\n" +
            "\t\t\t\t\"fcxxId\": \"0f4fda84084a4d10a97afdf3deed7776\",\n" +
            "\t\t\t\t\"jsje\": 718000.0,\n" +
            "\t\t\t\t\"jyfe\": 0.0,\n" +
            "\t\t\t\t\"mmfbz\": \"1\",\n" +
            "\t\t\t\t\"nsrid\": \"33838cbb63e84bc9be5e1a815103a0c7\",\n" +
            "\t\t\t\t\"se\": 14360.0,\n" +
            "\t\t\t\t\"sl\": 0.02,\n" +
            "\t\t\t\t\"zsxmDm\": \"10119\",\n" +
            "\t\t\t\t\"zsxmmc\": \"契税\"\n" +
            "\t\t\t}],\n" +
            "\t\t\t\"jyuuid\": \"0f4fda84084a4d10a97afdf3deed7776\",\n" +
            "\t\t\t\"jzmj\": \"156.17\",\n" +
            "\t\t\t\"mmfbz\": \"1\",\n" +
            "\t\t\t\"nsrmc\": \"陈xxxx\",\n" +
            "\t\t\t\"nsrsbh\": \"622322xxxx07241417\",\n" +
            "\t\t\t\"ynse\": 14360.0\n" +
            "\t\t}, {\n" +
            "\t\t\t\"cjjg\": 718000.0,\n" +
            "\t\t\t\"fwwzdz\": \"开发区xxxxxxxxxxxx1-201\",\n" +
            "\t\t\t\"jyskblb\": [{\n" +
            "\t\t   \"fcxxId\":\"0f4fda84084a4d10a97afdf3deed7776\",\n" +
            "\t\t\t\t\"jsje\": 718000.0,\n" +
            "\t\t\t\t\"jyfe\": 0.0,\n" +
            "\t\t\t\t\"mmfbz\": \"0\",\n" +
            "\t\t\t\t\"nsrid\": \"0e771e981a2e41aeb34a81ae046e3ed9\",\n" +
            "\t\t\t\t\"se\": 0.0,\n" +
            "\t\t\t\t\"sl\": 0.0,\n" +
            "\t\t\t\t\"zsxmDm\": \"10106\",\n" +
            "\t\t\t\t\"zsxmmc\": \"个人所得税\"\n" +
            "\t\t\t}, {\n" +
            "\t\t\t\t\"fcxxId\": \"0f4fda84084a4d10a97afdf3deed7776\",\n" +
            "\t\t\t\t\"jsje\": 718000.0,\n" +
            "\t\t\t\t\"jyfe\": 0.0,\n" +
            "\t\t\t\t\"mmfbz\": \"0\",\n" +
            "\t\t\t\t\"nsrid\": \"0e771e981a2e41aeb34a81ae046e3ed9\",\n" +
            "\t\t\t\t\"se\": 0.0,\n" +
            "\t\t\t\t\"sl\": 0.0,\n" +
            "\t\t\t\t\"zsxmDm\": \"10101\",\n" +
            "\t\t\t\t\"zsxmmc\": \"增值税\"\n" +
            "\t\t\t}],\n" +
            "\t\t\t\"jyuuid\": \"0f4fda84084a4d10a97afdf3deed7776\",\n" +
            "\t\t\t\"jzmj\": \"156.17\",\n" +
            "\t\t\t\"mmfbz\": \"0\",\n" +
            "\t\t\t\"nsrmc\": \"xxxxxxx\",\n" +
            "\t\t\t\"nsrsbh\": \"412725xxxx09258214\",\n" +
            "\t\t\t\"ynse\": 0.0\n" +
            "\t\t}]\n" +
            "\t},\n" +
            "\t\"head\": {\n" +
            "\t\t\"channel_id\": \"AHxxBDC\",\n" +
            "\t\t\"rtn_code\": \"200\",\n" +
            "\t\t\"rtn_msg\": \"成功\",\n" +
            "\t\t\"token\": \"11111111\",\n" +
            "\t\t\"tran_date\": \"2018xxxx9\",\n" +
            "\t\t\"tran_id\": \"ahsw.fcjy.yth.fcjyxxcx\",\n" +
            "\t\t\"tran_seq\": \"4bc12232e7907b56a6006ad3dba4\"\n" +
            "\t}\n" +
            "}";

}
