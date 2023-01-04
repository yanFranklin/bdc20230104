package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.init.App;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

import static org.junit.Assert.*;

/**
 * @author by Administrator.
 * @version 1.0, 2018/11/27
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = App.class)
public class BdcQllxServiceTest {
    @Autowired
    private BdcQllxService bdcQllxService;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/BdcQllxSetup-data.xml")
    public void getTableName() throws Exception {
        Assert.assertEquals("BDC_FDCQ", bdcQllxService.getTableName(new BdcFdcqDO()));
        Assert.assertNull(bdcQllxService.getTableName(null));
    }

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/BdcQllxSetup-data.xml")
    public void makeSureQllx() throws Exception {
        Assert.assertEquals((new BdcFdcqDO()).toString(), bdcQllxService.makeSureQllx("4").toString());
    }

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/BdcQllxSetup-data.xml")
    public void queryQllxDO() throws Exception {
        Assert.assertNotNull(bdcQllxService.queryQllxDO(new BdcFdcqDO(), "4444444"));

        Assert.assertNull(bdcQllxService.queryQllxDO(null, "12345"));
    }

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/BdcQllxSetup-data.xml")
    public void queryQllxDO1() throws Exception {

        Assert.assertNotNull(bdcQllxService.queryQllxDO("4444444"));

        Assert.assertNull(bdcQllxService.queryQllxDO("12345"));

        thrown.expect(MissingArgumentException.class);
        bdcQllxService.queryQllxDO("");
    }

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/BdcQllxSetup-data.xml")
    public void updateBdcQl() throws Exception {
        BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQllxService.queryQllxDO("4444444");
        bdcFdcqDO.setTdsyqr("更新测试");
        Assert.assertEquals(1, bdcQllxService.updateBdcQl(bdcFdcqDO));
        bdcFdcqDO = (BdcFdcqDO) bdcQllxService.queryQllxDO("4444444");
        Assert.assertEquals("更新测试", bdcFdcqDO.getTdsyqr());

        thrown.expect(MissingArgumentException.class);
        bdcQllxService.updateBdcQl(null);
    }

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/BdcQllxSetup-data.xml")
    public void insertBdcQl() throws Exception {
        BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQllxService.queryQllxDO("4444444");
        bdcFdcqDO.setQlid("");
        Assert.assertEquals(1, bdcQllxService.insertBdcQl(bdcFdcqDO));
    }

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/BdcQllxSetup-data.xml")
    public void insertOrUpdateBdcQl() throws Exception {
        BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQllxService.queryQllxDO("4444444");
        bdcFdcqDO.setTdsyqr("更新测试");
        bdcQllxService.insertOrUpdateBdcQl(bdcFdcqDO);
        bdcFdcqDO = (BdcFdcqDO) bdcQllxService.queryQllxDO("4444444");
        Assert.assertEquals("更新测试", bdcFdcqDO.getTdsyqr());

        bdcFdcqDO.setQlid("");
        bdcQllxService.insertOrUpdateBdcQl(bdcFdcqDO);
    }

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/BdcQllxSetup-data.xml")
    public void updateObj() throws Exception {
        BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQllxService.queryQllxDO("4444444");
        bdcFdcqDO.setTdsyqr("更新测试");
        bdcQllxService.updateObj(bdcFdcqDO, "1");
        bdcFdcqDO = (BdcFdcqDO) bdcQllxService.queryQllxDO("4444444");
        Assert.assertEquals("更新测试", bdcFdcqDO.getTdsyqr());

        thrown.expect(MissingArgumentException.class);
        bdcQllxService.updateObj(null, null);
    }

}