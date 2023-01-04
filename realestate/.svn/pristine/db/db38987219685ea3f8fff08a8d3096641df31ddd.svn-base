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
import cn.gtmap.realestate.accept.core.service.BdcSlSfxxService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
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
public class BdcSlSfxxServiceImplExtTest {
    @Autowired
    private BdcSlSfxxService bdcSlSfxxService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSfxx-setupData.xml")
    public void queryBdcSlSfxxBySfxxid() {
        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxService.queryBdcSlSfxxBySfxxid("6dc1f87d53463aeda2e747e6");
        Assert.assertNotNull(bdcSlSfxxDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSfxx-setupData.xml")
    public void listBdcSlSfxxByGzlslid() {
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid("602541");
        Assert.assertNotNull(bdcSlSfxxDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSfxx-setupData.xml")
    public void insertBdcSlSfxx() {
        BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
        bdcSlSfxxDO.setSfxxid(UUIDGenerator.generate16());
        BdcSlSfxxDO bdcSlSfxxDO1 = bdcSlSfxxService.insertBdcSlSfxx(bdcSlSfxxDO);
        Assert.assertNotNull(bdcSlSfxxDO1);
    }


    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSfxx-setupData.xml")
    public void updateBdcSlSfxx() {
        BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
        bdcSlSfxxDO.setSfxxid("6dc1f87d53463aeda2e747e6");
        Integer result = bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
        Assert.assertEquals(result.toString(), "1");
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSfxx-setupData.xml")
    public void deleteBdcSlSfxxBySfxxid() {
        Integer result = bdcSlSfxxService.deleteBdcSlSfxxBySfxxid("6dc1f87d53463aeda2e747e6");
        Assert.assertEquals(result.toString(), "1");
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSfxx-setupData.xml")
    public void deleteBdcSlSfxxByGzlslid() {
        Integer result = bdcSlSfxxService.deleteBdcSlSfxxByGzlslid("602575");
        Assert.assertEquals(result.toString(), "1");
    }
}
