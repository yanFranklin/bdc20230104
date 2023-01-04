package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.exchange.ExchangeApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author wyh
 * @version 1.0
 * @date 2022/3/2 17:29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class NantongQiControllerTest {
    @Autowired
    NantongQiController nantongQiController;

    @Autowired
    NantongShuiController nantongShuiController;

    @Test
    public void queryGasGh() {
        nantongQiController.qgh("3730283");
    }

    @Test
    public void queryShuiGh() {
        nantongShuiController.sgh("3730283");
    }
}