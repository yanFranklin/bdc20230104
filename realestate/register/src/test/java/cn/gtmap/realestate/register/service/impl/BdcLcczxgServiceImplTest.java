package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.register.App;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author wyh
 * @version 1.0
 * @date 2022/2/24 10:02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@Slf4j
public class BdcLcczxgServiceImplTest {
    @Autowired
    BdcLcczxgServiceImpl bdcLcczxgService;

    @Test
    public void testPrint() throws Exception {
        bdcLcczxgService.cfhzPdf("456443");
    }
}