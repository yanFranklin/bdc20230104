package cn.gtmap.realestate.etl.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author wyh
 * @version 1.0
 * @date 2022/2/18 15:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class PushQlygServiceImplTest {
    @Autowired
    PushQlygServiceImpl pushQlygService;

    @Test
    public void pushQlygData() {
        Map queryMap = new HashMap();
        queryMap.put("gzlslid","4637501");
        pushQlygService.pushQlygData(queryMap);
    }
}