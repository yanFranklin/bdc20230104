package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
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

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/26
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
public class FwFcqlrServiceTest {
    @Autowired
    private FwFcqlrService fwFcqlrService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwFcqlr-setupData.xml")
    public void deleteFcqlrByFwFcqlrIndex() {
        String fwFcqlrIndex = "gtis";
        int result = fwFcqlrService.deleteFcqlrByFwFcqlrIndex(fwFcqlrIndex);
        Assert.assertEquals(1, result);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwFcqlr-setupData.xml")
    public void deleteFcqlrByFwIndex() {
        String fwIndex = "1062";
        fwFcqlrService.deleteFcqlrByFwIndex(fwIndex);
        List<FwFcqlrDO> fwFcqlrDOList = fwFcqlrService.listFwFcQlrByFwIndex(fwIndex);
        Assert.assertNull(fwFcqlrDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    public void batchInsertFwFcQlr() {
        FwFcqlrDO fwFcqlrDO = new FwFcqlrDO();
        fwFcqlrDO.setFwFcqlrIndex("123");
        fwFcqlrDO.setFwIndex("321");
        fwFcqlrDO.setQlr("gtis");
        FwFcqlrDO fwFcqlrDOtwo = new FwFcqlrDO();
        fwFcqlrDOtwo.setFwFcqlrIndex("321");
        fwFcqlrDOtwo.setFwIndex("123");
        fwFcqlrDOtwo.setQlr("gtmap");
        List<FwFcqlrDO> fwFcqlrDOList = new ArrayList<>();
        fwFcqlrDOList.add(fwFcqlrDO);
        fwFcqlrDOList.add(fwFcqlrDOtwo);
        List<FwFcqlrDO> fcqlrDOList = fwFcqlrService.batchInsertFwFcQlr(fwFcqlrDOList);
        Assert.assertEquals(2,fcqlrDOList.size());

        FwFcqlrDO fwFcqlrDO1 = new FwFcqlrDO();
        fwFcqlrDO1.setFwIndex("321");
        fwFcqlrDO1.setQlr("gtis");
        FwFcqlrDO fwFcqlrDO2 = new FwFcqlrDO();
        fwFcqlrDO2.setFwIndex("123");
        fwFcqlrDO2.setQlr("gtmap");
        List<FwFcqlrDO> fwFcqlrDOList1 = new ArrayList<>();
        fwFcqlrDOList1.add(fwFcqlrDO1);
        fwFcqlrDOList1.add(fwFcqlrDO2);
        List<FwFcqlrDO> fcqlrDOList1 = fwFcqlrService.batchInsertFwFcQlr(fwFcqlrDOList1);
        Assert.assertEquals(2,fcqlrDOList1.size());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    public void insertFwFcQlr() {
        FwFcqlrDO fwFcqlrDO = new FwFcqlrDO();
        fwFcqlrDO.setFwFcqlrIndex("123");
        fwFcqlrDO.setFwIndex("321");
        fwFcqlrDO.setQlr("gtis");
        fwFcqlrDO = fwFcqlrService.insertFwFcQlr(fwFcqlrDO);
        FwFcqlrDO queryFwFcqlrDO = fwFcqlrService.queryFwFcByPk(fwFcqlrDO.getFwFcqlrIndex());
        Assert.assertEquals(queryFwFcqlrDO.getFwFcqlrIndex(),fwFcqlrDO.getFwFcqlrIndex());

        FwFcqlrDO fwFcqlrDO1 = new FwFcqlrDO();
        fwFcqlrDO1.setFwIndex("321");
        fwFcqlrDO1.setQlr("gtis");
        fwFcqlrDO1 = fwFcqlrService.insertFwFcQlr(fwFcqlrDO1);
        FwFcqlrDO queryFwFcqlrDO1 = fwFcqlrService.queryFwFcByPk(fwFcqlrDO1.getFwFcqlrIndex());
        Assert.assertEquals(queryFwFcqlrDO1.getFwFcqlrIndex(),fwFcqlrDO1.getFwFcqlrIndex());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwFcqlr-setupData.xml")
    @ExpectedDatabase(value = "/data/fwFcqlr-expectData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void updateFwFcQlr() {
        FwFcqlrDO fwFcqlrDO = new FwFcqlrDO();
        fwFcqlrDO.setFwFcqlrIndex("gtis");
        fwFcqlrDO.setFwIndex("1062");
        fwFcqlrDO.setQlr("test");
        int updateResult = fwFcqlrService.updateFwFcQlr(fwFcqlrDO, false);
        int updateNullResult = fwFcqlrService.updateFwFcQlr(fwFcqlrDO, true);
        Assert.assertEquals(1, updateResult);
        Assert.assertEquals(1, updateNullResult);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwFcqlr-setupData.xml")
    @ExpectedDatabase(value = "/data/fwFcqlr-expectData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void batchUpdateFwFcQlr() {
        List<FwFcqlrDO> fwFcqlrDOList = new ArrayList<>();
        FwFcqlrDO fwFcqlrDO = new FwFcqlrDO();
        fwFcqlrDO.setFwFcqlrIndex("gtis");
        fwFcqlrDO.setFwIndex("1062");
        fwFcqlrDO.setQlr("test");
        fwFcqlrDOList.add(fwFcqlrDO);
        fwFcqlrService.batchUpdateFwFcQlr(fwFcqlrDOList, false);
        FwFcqlrDO fwFcqlrDO1 = fwFcqlrService.queryFwFcByPk("gtis");
        Assert.assertEquals(fwFcqlrDO.getQlr(), fwFcqlrDO1.getQlr());
    }


    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwFcqlr-setupData.xml")
    public void listFwFcQlrByFwIndex() {
        String fwHsIndex = "1062";
        List<FwFcqlrDO> fcqlrDOList = fwFcqlrService.listFwFcQlrByFwIndex(fwHsIndex);
        Assert.assertNotNull(fcqlrDOList);
        List<FwFcqlrDO> emptyList = fwFcqlrService.listFwFcQlrByFwIndex("");
        Assert.assertEquals(new ArrayList<>(0), emptyList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwFcqlr-setupData.xml")
    public void wmConcatQlrByFwIndex() {
        String fwHsIndex = "1062";
        String qlr = fwFcqlrService.wmConcatQlrByFwIndex(fwHsIndex);
        Assert.assertEquals("gtmap", qlr);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwFcqlr-setupData.xml")
    public void queryFwFcByPk() {
        String fwFcqlrIndex = "gtis";
        FwFcqlrDO fwFcqlrDO = fwFcqlrService.queryFwFcByPk(fwFcqlrIndex);
        Assert.assertNotNull(fwFcqlrDO);
        FwFcqlrDO emptyFwFcqlrDO = fwFcqlrService.queryFwFcByPk("");
        Assert.assertNull(emptyFwFcqlrDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwFcqlr-setupData.xml")
    public void validateBatchFk() {
        List<FwFcqlrDO> fwFcqlrDOList = new ArrayList<>();
        FwFcqlrDO fwFcqlrDO = new FwFcqlrDO();
        fwFcqlrDO.setFwFcqlrIndex("gtis");
        fwFcqlrDO.setFwIndex("1062");
        fwFcqlrDO.setQlr("gtmap");
        fwFcqlrDOList.add(fwFcqlrDO);
        FwFcqlrDO fwFcqlrDO1 = new FwFcqlrDO();
        fwFcqlrDO1.setQlr("gtis");
        fwFcqlrDOList.add(fwFcqlrDO1);
        boolean flag = fwFcqlrService.validateBatchFk(fwFcqlrDOList);
        //Assert.assertTrue(flag);
        Assert.assertFalse(flag);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwFcqlr-setupData.xml")
    public void validateBatchPKAndFk() {
        List<FwFcqlrDO> fwFcqlrDOList = new ArrayList<>();
        FwFcqlrDO fwFcqlrDO = new FwFcqlrDO();
        fwFcqlrDO.setFwFcqlrIndex("gtis");
        fwFcqlrDO.setFwIndex("1062");
        fwFcqlrDO.setQlr("gtmap");
        fwFcqlrDOList.add(fwFcqlrDO);
        FwFcqlrDO fwFcqlrDO1 = new FwFcqlrDO();
        fwFcqlrDO1.setFwIndex("1062");
        fwFcqlrDO1.setQlr("gtis");
        fwFcqlrDOList.add(fwFcqlrDO1);
        boolean flag = fwFcqlrService.validateBatchPKAndFk(fwFcqlrDOList);
        Assert.assertFalse(flag);
    }
}