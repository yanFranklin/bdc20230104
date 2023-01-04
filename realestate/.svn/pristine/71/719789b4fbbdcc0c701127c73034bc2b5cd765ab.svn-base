package cn.gtmap.realestate.natural.service.impl;

import cn.gtmap.realestate.natural.NaturalApp;
import lombok.extern.slf4j.Slf4j;
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
 * @date 2021/11/18 15:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NaturalApp.class)
@WebAppConfiguration
@Slf4j
public class ZrzyZsServiceImplTest {
    @Autowired
    ZrzyZsServiceImpl zrzyZsService;

    @Test
    public void listZryzZs() {
        try {
            zrzyZsService.initZrzyqzs("4526322", false, false, null, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}