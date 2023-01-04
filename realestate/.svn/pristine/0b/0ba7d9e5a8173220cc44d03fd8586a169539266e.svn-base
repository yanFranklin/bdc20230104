package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
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

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.core.service.BdcSlJbxxService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.util.UUIDGenerator;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/21
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
public class BdcSlJbxxServiceImplExtTest {
    @Autowired
    private BdcSlJbxxService bdcSlJbxxService;


    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlJbxx-setupData.xml")
    public void queryBdcSlJbxxByJbxxid() {
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByJbxxid("6dc1f87d53463aeda2e747e6");
        Assert.assertNotNull(bdcSlJbxxDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlJbxx-setupData.xml")
    public void queryBdcSlJbxxByGzlslid() {
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid("111111111");
        Assert.assertNotNull(bdcSlJbxxDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlJbxx-setupData.xml")
    public void insertBdcSlJbxx() {
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        bdcSlJbxxDO.setJbxxid(UUIDGenerator.generate16());
        bdcSlJbxxDO.setSlbh("20180909");
        bdcSlJbxxDO.setSlr("supersun");
        bdcSlJbxxDO = bdcSlJbxxService.insertBdcSlJbxx(bdcSlJbxxDO);
        Assert.assertNotNull(bdcSlJbxxDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlJbxx-setupData.xml")
    public void updateBdcSlJbxx() {
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        bdcSlJbxxDO.setJbxxid("6dc1f87d53463aeda2e747e6");
        bdcSlJbxxDO.setSlbh("20180909");
        bdcSlJbxxDO.setSlr("supersun");
        Integer result = bdcSlJbxxService.updateBdcSlJbxx(bdcSlJbxxDO);
        Assert.assertEquals(result.toString(), "1");
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlJbxx-setupData.xml")
    public void deleteBdcSlJbxxByJbxxid() {
        bdcSlJbxxService.deleteBdcSlJbxxByJbxxid("6dc1f87d53463aeda2e747e6");
    }

}