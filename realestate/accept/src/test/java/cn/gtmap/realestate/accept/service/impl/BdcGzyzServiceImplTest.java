package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.App;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@Slf4j
public class BdcGzyzServiceImplTest {
    @Autowired
    BdcGzyzServiceImpl bdcGzyzService;

    @Test
    public void sfzjjgYthpt() {
        log.info("{}", JSON.toJSONString(bdcGzyzService.sfzjjgYthpt("DNFAWB8EFWZFU8HE","DNFAWB8EFWZ")));
    }
}