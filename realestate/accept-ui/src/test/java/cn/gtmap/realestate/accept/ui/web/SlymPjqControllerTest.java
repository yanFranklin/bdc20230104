package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.AcceptUIApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * @author wyh
 * @version 1.0
 * @date 2022/11/7 14:23
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AcceptUIApp.class)
@WebAppConfiguration
@ContextConfiguration
@Slf4j
public class SlymPjqControllerTest {
    @Autowired
    SlymPjqController slymPjqController;

    @Test
    public void commonPj() {
        slymPjqController.commonPj("","",null);
    }
}