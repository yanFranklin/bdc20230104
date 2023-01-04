package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author wyh
 * @version 1.0
 * @date 2022/2/18 17:21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class ReadZdtHefeiFtpServiceImplTest {
    @Autowired
    ReadZdtHefeiFtpServiceImpl readZdtHefeiFtpService;

    @Test
    public void getZdtByBdcdyhByFTP() {
        readZdtHefeiFtpService.readBase64ByBdcdyh("340103162011GB00026F00050054","340124");
    }
}