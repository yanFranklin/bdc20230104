package cn.gtmap.realestate.exchange.service.impl.inf.standard;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BdcCourtCxServiceImplTest {
    @Autowired
    BdcCourtCxServiceImpl bdcCourtCxService;

    @Test
    public void courtCxBdcQL() {
        bdcCourtCxService.CourtCxBdcQL();
    }

    @Test
    public void feedCourtCxBdcQL() {
    }

    @Test
    public void ywxxcx() {
    }

    @Test
    public void courtCxwsInfo() {
    }

    @Test
    public void courtCxZjInfo() {
    }

    @Test
    public void buildCourtKzUserRequest() {
    }

    @Test
    public void buildCourtKzHeadRequest() {
    }

    @Test
    public void buildCourtKzdhRequest() {
    }
}