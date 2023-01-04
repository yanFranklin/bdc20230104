package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.exchange.ExchangeApp;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.request.ZfcxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.request.ZfcxRequestQlr;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
@Slf4j
public class StandardZfcxRestControllerTest {
    @Autowired
    StandardZfcxRestController standardZfcxRestController;

    @Test
    public void zfcx() {
        ZfcxRequestDTO zfcxRequestDTO = JSON.parseObject("{\n" +
                "    \"selarea\": \"\", \n" +
                "    \"clientusername\": \"1\", \n" +
                "    \"clientusercid\": \"123123\", \n" +
                "    \"ytcn\": \"3\", \n" +
                "    \"computerid\": \"4\", \n" +
                "    \"computermac\": \"5\", \n" +
                "    \"computername\": \"6\", \n" +
                "    \"psw\": \"7\", \n" +
                "    \"cxrzp\": \"8\", \n" +
                "    \"qlrlist\": [\n" +
                "        {\n" +
                "            \"qlr\": \"许**\", \n" +
                "            \"sfzh\": \"3411251990****8813\"\n" +
                "        }\n" +
                "    ]\n" +
                "}", ZfcxRequestDTO.class);
        Object o = standardZfcxRestController.zfcx("", zfcxRequestDTO);
//        String fileContent = standardZfcxRestController.generatrYfwfPdf(zfcxRequestDTO.getClientusername(),
//                zfcxRequestDTO.getClientusercid());
        log.info("{}", JSON.toJSONString(o));
        System.out.println("123");
    }
}