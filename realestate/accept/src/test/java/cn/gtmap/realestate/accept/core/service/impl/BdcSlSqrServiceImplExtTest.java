package cn.gtmap.realestate.accept.core.service.impl;

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

import java.util.List;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.core.service.BdcSlSqrService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.util.UUIDGenerator;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/30
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config-dbunit.xml"})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(classes = App.class)
public class BdcSlSqrServiceImplExtTest {
    @Autowired
    private BdcSlSqrService bdcSlSqrService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSqr-setupData.xml")
    public void queryBdcSlSqrBySqrid() {
        BdcSlSqrDO bdcSlSqrDO = bdcSlSqrService.queryBdcSlSqrBySqrid("6dc1f87d53463aeda2e747e6");
        Assert.assertNotNull(bdcSlSqrDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSqr-setupData.xml")
    public void listBdcSlSqrByXmid() {
        List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrService.listBdcSlSqrByXmid("20180001","");
        Assert.assertNotNull(bdcSlSqrDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSqr-setupData.xml")
    public void insertBdcSlSqr() {
        BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
        bdcSlSqrDO.setSqrid(UUIDGenerator.generate16());
        bdcSlSqrDO.setXmid(UUIDGenerator.generate16());
        bdcSlSqrDO.setSqrmc("234234");
        BdcSlSqrDO bdcSlSqrDO1 = bdcSlSqrService.insertBdcSlSqr(bdcSlSqrDO);
        Assert.assertNotNull(bdcSlSqrDO1);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSqr-setupData.xml")
    public void updateBdcSlSqr() {
        BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
        bdcSlSqrDO.setSqrid("6dc1f87d53463aeda2e747e6");
        bdcSlSqrDO.setXmid(UUIDGenerator.generate16());
        Integer result = bdcSlSqrService.updateBdcSlSqr(bdcSlSqrDO);
        Assert.assertEquals(result.toString(), "1");
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSqr-setupData.xml")
    public void deleteBdcSlSqrBySqrid() {
        Integer result = bdcSlSqrService.deleteBdcSlSqrBySqrid("6dc1f87d53463aeda2e747e6");
        Assert.assertEquals(result.toString(), "1");
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSqr-setupData.xml")
    public void deleteBdcSlSqrByXmid() {
        Integer result = bdcSlSqrService.deleteBdcSlSqrByXmid("20180001","");
        Assert.assertEquals(result.toString(), "2");
    }
}