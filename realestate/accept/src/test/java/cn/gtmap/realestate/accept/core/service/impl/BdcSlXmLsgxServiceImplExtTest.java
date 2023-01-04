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

import java.util.ArrayList;
import java.util.List;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.core.service.BdcSlXmLsgxService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
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
public class BdcSlXmLsgxServiceImplExtTest {
    @Autowired
    private BdcSlXmLsgxService bdcSlXmLsgxService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXmLsgx-setupData.xml")
    public void queryBdcSlXmLsgxByGxid() {
        BdcSlXmLsgxDO bdcSlXmLsgxDO = bdcSlXmLsgxService.queryBdcSlXmLsgxByGxid("6dc1f87d53463aeda2e747e6");
        Assert.assertNotNull(bdcSlXmLsgxDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXmLsgx-setupData.xml")
    public void listBdcSlXmLsgxByXmid() {
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgxByXmid("20180001");
        Assert.assertNotNull(bdcSlXmLsgxDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXmLsgx-setupData.xml")
    public void listBdcSlXmLsgx() {
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx("20180001","111111111",null);
        Assert.assertNotNull(bdcSlXmLsgxDOList);
        bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx("20180001","111111111",0);
        Assert.assertNotNull(bdcSlXmLsgxDOList);
        bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx("20180001","111111111",1);
        Assert.assertEquals("[]",bdcSlXmLsgxDOList.toString());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXmLsgx-setupData.xml")
    public void insertBdcSlXmLsgx() {
        BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO();
        bdcSlXmLsgxDO.setGxid(UUIDGenerator.generate16());
        bdcSlXmLsgxDO.setXmid(UUIDGenerator.generate16());
        bdcSlXmLsgxDO.setYxmid("234234");
        BdcSlXmLsgxDO bdcSlXmLsgxDO1 = bdcSlXmLsgxService.insertBdcSlXmLsgx(bdcSlXmLsgxDO);
        Assert.assertNotNull(bdcSlXmLsgxDO1);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXmLsgx-setupData.xml")
    public void updateBdcSlXmLsgx() {
        BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO();
        bdcSlXmLsgxDO.setGxid("6dc1f87d53463aeda2e747e6");
        bdcSlXmLsgxDO.setXmid(UUIDGenerator.generate16());
        Integer result = bdcSlXmLsgxService.updateBdcSlXmLsgx(bdcSlXmLsgxDO);
        Assert.assertEquals("1",result.toString() );
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXmLsgx-setupData.xml")
    public void deleteBdcSlXmLsgxByGxid() {
        Integer result = bdcSlXmLsgxService.deleteBdcSlXmLsgxByGxid("6dc1f87d53463aeda2e747e6");
        Assert.assertEquals("1",result.toString());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXmLsgx-setupData.xml")
    public void deleteBdcSlXmLsgxByXmid() {
        Integer result = bdcSlXmLsgxService.deleteBdcSlXmLsgxByXmid("20180001");
        Assert.assertEquals("2",result.toString());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXmLsgx-setupData.xml")
    public void deleteBdcSlXmLsgxByJbxxid() {
        bdcSlXmLsgxService.deleteBdcSlXmLsgx("20190417",null);
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx("20180001","",0);
        Assert.assertEquals(0,bdcSlXmLsgxDOList.size());
    }
}