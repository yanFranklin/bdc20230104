package cn.gtmap.realestate.exchange.service.impl.inf.wwsq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SlztServiceImplTest {
    @Autowired
    SlztServiceImpl slztService;

    @Test
    public void updateSlztCz() {
        Map data = new HashMap<>();
        data.put("processInsId","475408");
        data.put("isDelete","5");
        slztService.updateSlztCz(data);
    }
}