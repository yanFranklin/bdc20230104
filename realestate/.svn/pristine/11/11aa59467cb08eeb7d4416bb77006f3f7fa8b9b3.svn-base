package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.etl.service.impl.PushQlygServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PushDataControllerTest {

    @Autowired
    PushQlygServiceImpl pushQlygService;

    @Test
    public void pushQlygData() {
        Map queryMap = new HashMap();
        queryMap.put("gzlslid","457645");
        pushQlygService.pushQlygData(queryMap);
    }
}