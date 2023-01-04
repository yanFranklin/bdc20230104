package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.exchange.ExchangeApp;
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
public class YhxxTsRestControllerTest {
    @Autowired
    YhxxTsRestController yhxxTsRestController;

    @Test
    public void tzyyh() {
        yhxxTsRestController.tzyyh("3533282","");
    }

    @Test
    public void cftzyyh() {
        yhxxTsRestController.cftzyyh("1234543322","");
    }

    @Test
    public void ysftzyh() {
        yhxxTsRestController.ysftzyh("3533282","");
    }
}