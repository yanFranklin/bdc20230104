package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.inquiry.InquiryApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author wyh
 * @version 1.0
 * @date 2022/5/27 11:28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InquiryApp.class)
public class BdcSdqghServiceImplTest {
    @Autowired
    BdcSdqghServiceImpl bdcSdqghService;

    @Test
    public void qigh(){
        bdcSdqghService.qigh("5373395","");
    }

    @Test
    public void shuigh(){
        bdcSdqghService.shuigh("4170760","");
    }

}