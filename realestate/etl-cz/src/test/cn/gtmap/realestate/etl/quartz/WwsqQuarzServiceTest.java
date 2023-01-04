package cn.gtmap.realestate.etl.quartz;

import cn.gtmap.realestate.etl.EtlCzApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EtlCzApp.class)
@WebAppConfiguration
public class WwsqQuarzServiceTest {
    @Autowired
    WwsqQuarzService wwsqQuarzService;

    @Test
    public void reWwsqcj() {
        wwsqQuarzService.reWwsqcj();
    }

    @Test
    public void updateSlzt() {
        wwsqQuarzService.updateSlzt();
    }
}