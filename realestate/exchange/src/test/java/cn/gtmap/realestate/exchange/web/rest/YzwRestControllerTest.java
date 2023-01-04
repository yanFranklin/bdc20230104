package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.exchange.ExchangeApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * @author wyh
 * @version 1.0
 * @date 2022/9/8 10:19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class YzwRestControllerTest {
    @Autowired
    YzwRestController yzwRestController;

    @Test
    public void shareProcessData() {
        yzwRestController.shareProcessData("5542934");
    }

    @Test
    public void shareResultData() {
        yzwRestController.shareResultData("5542934");
    }
}