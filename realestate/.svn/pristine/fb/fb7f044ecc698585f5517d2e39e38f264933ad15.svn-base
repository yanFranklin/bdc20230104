package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcFcdaDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.inquiry.InquiryApp;
import cn.gtmap.realestate.inquiry.core.mapper.BdcZfxxMapper;
import cn.gtmap.realestate.inquiry.service.BdcZfxxCxService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InquiryApp.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdcZfxxCxServiceImplTest {

    @Autowired
    private BdcZfxxCxService bdcZfxxCxService;

    @Autowired
    BdcZfxxMapper bdcZfxxMapper;

    @Test
    public void test0getBdcFcdaDTO() {
        String bdcdyh = "340104404005GB00047F00010128";
        BdcFcdaDTO bdcFcdaDTO = bdcZfxxCxService.getBdcFcdaDTO(bdcdyh,"");
        Assert.assertNotNull(bdcFcdaDTO);
    }

    @Test
    public void test1getBdcFcdaDTO() {
        String bdcdyh = "340104404005GB00047F0001012";
        BdcFcdaDTO bdcFcdaDTO = bdcZfxxCxService.getBdcFcdaDTO(bdcdyh,"");
        Assert.assertNotNull(bdcFcdaDTO);
    }

    @Test
    public void test3listBdcZfxxDTO() {
        BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();
        List<BdcQlrQO> bdcQlrQOList = new ArrayList<>();
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setQlrmc("黄健");
        bdcQlrQO.setZjh("342401199405018675");
        bdcQlrQOList.add(bdcQlrQO);
        bdcZfxxQO.setCxly("1");
        bdcZfxxQO.setQlrxx(bdcQlrQOList);
        Assert.assertNotNull(bdcZfxxCxService.listBdcZfxxDTO(bdcZfxxQO));
    }

    @Test
    public void test4listBdcNtZfxxDTO() {
        BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();
        List<BdcQlrQO> bdcQlrQOList = new ArrayList<>();
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setQlrmc("黄健");
        bdcQlrQO.setZjh("342401199405018675");
        bdcQlrQOList.add(bdcQlrQO);
        bdcZfxxQO.setCxly("1");
        bdcZfxxQO.setQlrxx(bdcQlrQOList);
        Assert.assertNotNull(bdcZfxxCxService.listBdcNtZfxxDTO(bdcZfxxQO));
    }

    @Test
    public void tetst02(){
        BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setQlrmc("李勇/");
        bdcQlrQO.setZjh("320721196905170410");
        bdcZfxxQO.setQlrxx(Arrays.asList(bdcQlrQO));
        List<BdcZfxxDTO> htbaxx = bdcZfxxMapper.lisSpfhtbhxx(bdcZfxxQO);
    }
}