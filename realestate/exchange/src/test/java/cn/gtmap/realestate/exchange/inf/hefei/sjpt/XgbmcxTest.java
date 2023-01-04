package cn.gtmap.realestate.exchange.inf.hefei.sjpt;

import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.hyxx.request.HyxxCxywcsRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.hyxx.request.HyxxRequestDTO;
import cn.gtmap.realestate.exchange.ExchangeApp;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.service.inf.build.BuildRequestService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-28
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class XgbmcxTest {


    @Resource(name = "buildRequest")
    private BuildRequestService requestService;


    @Test
    public void hyxx(){
        HyxxRequestDTO hyxxRequestDTO = new HyxxRequestDTO();
        hyxxRequestDTO.setPage(1);
        hyxxRequestDTO.setSize(2);
        List<HyxxCxywcsRequestDTO> requestDTOS = new ArrayList<>();
        HyxxCxywcsRequestDTO dto1 = new HyxxCxywcsRequestDTO();
        dto1.setQlrmc("1");
        dto1.setQlrzjh("2");
        requestDTOS.add(dto1);

        HyxxCxywcsRequestDTO dto2 = new HyxxCxywcsRequestDTO();
        dto2.setQlrmc("3");
        dto2.setQlrzjh("4");
        requestDTOS.add(dto2);
        hyxxRequestDTO.setCxywcs(requestDTOS);
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean("xgbmcx_hyxx");
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,hyxxRequestDTO);
        requestService.build(requestBuilder);
        System.out.println(JSONObject.toJSONString(requestBuilder.getRequestBody()));
    }
}
