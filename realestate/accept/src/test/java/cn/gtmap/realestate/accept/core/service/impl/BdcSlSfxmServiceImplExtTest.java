package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmPzDO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

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

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.core.service.BdcSlSfxmService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.util.UUIDGenerator;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/22
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
public class BdcSlSfxmServiceImplExtTest {
    @Autowired
    private BdcSlSfxmService bdcSlSfxmService;


    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSfxm-setupData.xml")
    public void queryBdcSlSfxmBySfxmid() {
        BdcSlSfxmDO bdcSlSfxmDO = bdcSlSfxmService.queryBdcSlSfxmBySfxmid("6dc1f87d53463aeda2e747e6");
        Assert.assertNotNull(bdcSlSfxmDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSfxm-setupData.xml")
    public void listBdcSlSfxmBySfxxid() {
        List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid("20180001");
        Assert.assertNotNull(bdcSlSfxmDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSfxm-setupData.xml")
    public void listCshBdcSlSfxm() {
        List<BdcSlSfxmPzDO> bdcSlSfxmPzDOList =new ArrayList<>();
        BdcSlSfxmPzDO bdcSlSfxmPzDO =new BdcSlSfxmPzDO();
        bdcSlSfxmPzDO.setDjxl("2000401");
        bdcSlSfxmPzDO.setSfxmbz("100");
        bdcSlSfxmPzDOList.add(bdcSlSfxmPzDO);
        List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmService.listCshBdcSlSfxm(bdcSlSfxmPzDOList,"123");
        Assert.assertNotNull(bdcSlSfxmDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSfxm-setupData.xml")
    public void insertBdcSlSfxm() {
        BdcSlSfxmDO bdcSlSfxmDO = new BdcSlSfxmDO();
        bdcSlSfxmDO.setSfxmid(UUIDGenerator.generate16());
        bdcSlSfxmDO.setSfxxid(UUIDGenerator.generate16());
        BdcSlSfxmDO bdcSlSfxmDO1 = bdcSlSfxmService.insertBdcSlSfxm(bdcSlSfxmDO);
        Assert.assertNotNull(bdcSlSfxmDO1);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSfxm-setupData.xml")
    public void updateBdcSlSfxm() {
        BdcSlSfxmDO bdcSlSfxmDO = new BdcSlSfxmDO();
        bdcSlSfxmDO.setSfxmid("6dc1f87d53463aeda2e747e6");
        bdcSlSfxmDO.setSfxxid("20180001");
        Integer result = bdcSlSfxmService.updateBdcSlSfxm(bdcSlSfxmDO);
        Assert.assertEquals(result.toString(), "1");
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSfxm-setupData.xml")
    public void deleteBdcSlSfxmBySfxmid() {
        bdcSlSfxmService.deleteBdcSlSfxmBySfxmid("6dc1f87d53463aeda2e747e6");
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSfxm-setupData.xml")
    public void deleteBdcSlSfxmBySfxxid() {
        bdcSlSfxmService.deleteBdcSlSfxmBySfxxid("20180001");
    }
}