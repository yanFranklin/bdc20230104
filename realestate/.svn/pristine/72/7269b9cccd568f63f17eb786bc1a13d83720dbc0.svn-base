package cn.gtmap.realestate.natural.core.service.impl;

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
 * @date 2021/11/18 9:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NaturalApp.class)
@WebAppConfiguration
@Slf4j
public class ZrzyDjcqzhServiceImplTest {
    @Autowired
    ZrzyDjcqzhServiceImpl zrzyDjcqzhService;

    @Test
    public void generateCqzhOfPro() {
        zrzyDjcqzhService.generateCqzhOfPro("4526322");
    }
}