package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.etl.EtlApp;
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
 * @date 2022/9/8 11:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EtlApp.class)
@WebAppConfiguration
public class PushDataControllerTest {
    @Autowired
    PushDataController pushDataController;

    @Test
    public void qlygData() {
        pushDataController.qlygData("5542934");
    }
}