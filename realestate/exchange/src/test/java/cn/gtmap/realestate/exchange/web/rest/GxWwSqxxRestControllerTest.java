package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.dto.exchange.dbxx.BdcxxCxDataDTO;
import cn.gtmap.realestate.common.core.dto.pub.ResponseEntityDTO;
import cn.gtmap.realestate.exchange.ExchangeApp;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
@Slf4j
public class GxWwSqxxRestControllerTest {
    @Autowired
    GxWwSqxxRestController gxWwSqxxRestController;

    @Test
    public void bdcspfbacx() {
        gxWwSqxxRestController.bdcspfbacx(null);
    }

    @Test
    public void djbxxCx() {
        BdcxxCxDataDTO bdcxxCxDataDTO = gxWwSqxxRestController.djbxxCx("340104000000GB00000F22000056");
        log.info(JSON.toJSONString(bdcxxCxDataDTO));
        System.out.println(bdcxxCxDataDTO);
    }
}