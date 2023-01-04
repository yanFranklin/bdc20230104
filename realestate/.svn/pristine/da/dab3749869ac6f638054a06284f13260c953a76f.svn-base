package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.inquiry.InquiryApp;
import cn.gtmap.realestate.inquiry.service.BdcSdService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = InquiryApp.class)
@ContextConfiguration(locations = {"classpath:config-dbunit.xml"})
public class BdcSdServiceImplTest {

    @Autowired
    private BdcSdService bdcSdService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/sdData.xml")
    public void test0sdBdcdy() {
        List<BdcDysdDO> bdcDysdDOList = new ArrayList<>();
        BdcDysdDO bdcDysdDO = new BdcDysdDO();
        bdcDysdDO.setBdcdyh("340102000000GB00000W00004280");
        bdcDysdDOList.add(bdcDysdDO);
        Integer num = bdcSdService.sdBdcdy(bdcDysdDOList, 1);
        Assert.assertNotNull(num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/sdData.xml")
    public void sdBdczs() {
        List<BdcZssdDO> bdcZssdDOList = new ArrayList<>();
        BdcZssdDO bdcZssdDO = new BdcZssdDO();
        bdcZssdDO.setCqzh("合国用(2015)第084号");
        bdcZssdDOList.add(bdcZssdDO);
        String sdyy = "法院执行";
        Integer num = bdcSdService.sdBdczs(bdcZssdDOList, 1,sdyy);
        Assert.assertNotNull(num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/sdData.xml")
    public void jsBdcdy() {
        BdcDysdDO bdcDysdDO = new BdcDysdDO();
        List<BdcDysdDO> bdcDysdDOList = new ArrayList<>();
        String jsyy = "法院文件";
        bdcDysdDOList.add(bdcDysdDO);
        bdcDysdDO.setBdcdyh("340102000000GB00000W00004282");
        Integer num = bdcSdService.jsBdcdy(bdcDysdDOList, jsyy);
        Assert.assertNotNull(num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/sdData.xml")
    public void jsBdczs() {
        List<BdcZssdDO> bdcZssdDOList = new ArrayList<>();
        String jsyy = "法院文件";
        BdcZssdDO bdcZssdDO = new BdcZssdDO();
        bdcZssdDO.setCqzh("合国用(2015)第084号");
        bdcZssdDOList.add(bdcZssdDO);
        Assert.assertNotNull(bdcSdService.jsBdczs(bdcZssdDOList, jsyy));

    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/sdData.xml")
    public void queryBdcdySd() {
        BdcDysdDO bdcDysdDO = new BdcDysdDO();
        bdcDysdDO.setBdcdyh("340102000000GB00000W00004280");
        Assert.assertNotNull(bdcSdService.queryBdcdySd(bdcDysdDO));
        ;
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/sdData.xml")
    public void queryBdczsSd() {
        BdcZssdDO bdcZssdDO = new BdcZssdDO();
        bdcZssdDO.setCqzh("合国用(2015)第084号");
        Assert.assertNotNull(bdcSdService.queryBdczsSd(bdcZssdDO));
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/sdData.xml")
    public void saveBdcdysdBz() {
        BdcDysdDO bdcDysdDO = new BdcDysdDO();
        bdcDysdDO.setDysdid("123567");
        bdcDysdDO.setBz("不动产存在异议，进行锁定");
        Integer num = bdcSdService.saveBdcdysdBz(bdcDysdDO);
        Assert.assertNotNull(num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/sdData.xml")
    public void saveBdczssdBz() {
        BdcZssdDO bdcZssdDO = new BdcZssdDO();
        bdcZssdDO.setZssdid("1234");
        bdcZssdDO.setBz("执行锁定");
        Integer num = bdcSdService.saveBdczssdBz(bdcZssdDO);
        Assert.assertNotNull(num);
    }
}