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
import cn.gtmap.realestate.accept.core.service.BdcSlSjclService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
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
public class BdcSlSjclServiceImplExtTest {
    @Autowired
    private BdcSlSjclService bdcSlSjclService;


    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSjcl-setupData.xml")
    public void queryBdcSlSjclBySjclid() {
        BdcSlSjclDO bdcSlSjclDO = bdcSlSjclService.queryBdcSlSjclBySjclid("6dc1f87d53463aeda2e747e6");
        Assert.assertNotNull(bdcSlSjclDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSjcl-setupData.xml")
    public void queryBdcSlSjclByWjzxid() {
        BdcSlSjclDO bdcSlSjclDO = bdcSlSjclService.queryBdcSlSjclByWjzxid("8a8181516962587b01696bcf27b80001");
        Assert.assertNotNull(bdcSlSjclDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSjcl-setupData.xml")
    public void listBdcSlSjclByGzlslid() {
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjclByGzlslid("582373");
        Assert.assertNotNull(bdcSlSjclDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSjcl-setupData.xml")
    public void listBdcSlSjcl() {
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjcl("8a8181516962587b01696bcf27b80001","111111111");
        Assert.assertNotNull(bdcSlSjclDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSjcl-setupData.xml")
    public void insertBdcSlSjcl() {
        BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
        bdcSlSjclDO.setSjclid(UUIDGenerator.generate16());
        bdcSlSjclDO.setXmid(UUIDGenerator.generate16());
        bdcSlSjclDO.setClmc("234234");
        BdcSlSjclDO bdcSlSjclDO1 = bdcSlSjclService.insertBdcSlSjcl(bdcSlSjclDO);
        Assert.assertNotNull(bdcSlSjclDO1);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSjcl-setupData.xml")
    public void updateBdcSlSjcl() {
        BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
        bdcSlSjclDO.setSjclid("6dc1f87d53463aeda2e747e6");
        bdcSlSjclDO.setXmid(UUIDGenerator.generate16());
        Integer result = bdcSlSjclService.updateBdcSlSjcl(bdcSlSjclDO);
        Assert.assertEquals(result.toString(), "1");
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSjcl-setupData.xml")
    public void deleteBdcSlSjclBySjclid() {
        Integer result = bdcSlSjclService.deleteBdcSlSjclBySjclid("6dc1f87d53463aeda2e747e6");
        Assert.assertEquals(result.toString(), "1");
    }

}