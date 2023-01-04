package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.dto.pub.ResponseEntityDTO;
import cn.gtmap.realestate.exchange.ExchangeApp;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
@Slf4j
public class BdcQszmRestControllerTest {
    @Autowired
    BdcQszmRestController bdcQszmRestController;

    @Test
    public void queryQszmPdf() {
        ResponseEntityDTO responseEntityDTO = bdcQszmRestController.queryQszmPdf("皖(2020)蜀山区不动产证明第6000132号", "436F1055F41J39EM","");
        log.info(JSON.toJSONString(responseEntityDTO));
    }
}