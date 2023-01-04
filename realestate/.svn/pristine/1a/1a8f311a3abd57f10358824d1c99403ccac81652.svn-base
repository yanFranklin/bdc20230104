package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.common.core.domain.building.FwZhsDO;
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
public class FwZhsServiceTest {
    @Autowired
    private FwZhsService fwZhsService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwZhs-setupData.xml")
    public void listFwZhsByFwHsIndex() {
        String fwHsIndex = "212321";
        List<FwZhsDO> fwZhsDOS = fwZhsService.listFwZhsByFwHsIndex(fwHsIndex);
        Assert.assertNotNull(fwZhsDOS);
        List<FwZhsDO> emptyFwZhsDOS = fwZhsService.listFwZhsByFwHsIndex("");
        Assert.assertEquals(new ArrayList<>(0), emptyFwZhsDOS);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwZhs-setupData.xml")
    public void queryFwZhsByPk() {
        String fwZhsIndex = "098890";
        FwZhsDO fwZhsDO = fwZhsService.queryFwZhsByPk(fwZhsIndex);
        Assert.assertNotNull(fwZhsDO);
        FwZhsDO emptyFwZhsDO = fwZhsService.queryFwZhsByPk("");
        Assert.assertNull(emptyFwZhsDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwZhs-setupData.xml")
    public void relevanceZhs() {
        String fwHsIndex = "212321";
        List<FwZhsDO> list = fwZhsService.listFwZhsByFwHsIndex(fwHsIndex);
        fwZhsService.relevanceZhs(list, fwHsIndex);
        List<FwZhsDO> list1 = fwZhsService.listFwZhsByFwHsIndex(fwHsIndex);
        Assert.assertNotNull(list1.get(0).getFwHsIndex(), fwHsIndex);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwZhs-setupData.xml")
    public void cancelRelevanceZhs() {
        String fwHsIndex = "212321";
        List<FwZhsDO> list = fwZhsService.listFwZhsByFwHsIndex(fwHsIndex);
        fwZhsService.cancelRelevanceZhs(list);
        List<FwZhsDO> list1 = fwZhsService.listFwZhsByFwHsIndex(fwHsIndex);
        Assert.assertNull(list1);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwZhs-setupData.xml")
    public void deleteZhsByFwZhsIndex() {
        String fwZhsIndex = "098890";
        int delResult = fwZhsService.deleteZhsByFwZhsIndex(fwZhsIndex);
        Assert.assertEquals(1,delResult);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwZhs-setupData.xml")
    public void deleteZhsByFwHsIndex() {
        String fwHsIndex = "212321";
        fwZhsService.deleteZhsByFwHsIndex(fwHsIndex);
        List<FwZhsDO> fwZhsDOList = fwZhsService.listFwZhsByFwHsIndex(fwHsIndex);
        Assert.assertNull(fwZhsDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @ExpectedDatabase(value = "/data/fwZhs-expectData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void insertZhs() {
        FwZhsDO fwZhsDO = new FwZhsDO();
        fwZhsDO.setFwZhsIndex("123456");
        fwZhsDO.setFwHsIndex("654321");
        fwZhsDO = fwZhsService.insertZhs(fwZhsDO);
        FwZhsDO fwZhsDO1 = fwZhsService.queryFwZhsByPk(fwZhsDO.getFwZhsIndex());
        Assert.assertEquals(fwZhsDO1.getFwZhsIndex(),fwZhsDO.getFwZhsIndex());

        FwZhsDO fwZhsDO2 = new FwZhsDO();
        fwZhsDO2.setFwHsIndex("654321");
        fwZhsDO2 = fwZhsService.insertZhs(fwZhsDO2);
        FwZhsDO fwZhsDO3 = fwZhsService.queryFwZhsByPk(fwZhsDO2.getFwZhsIndex());
        Assert.assertEquals(fwZhsDO3.getFwZhsIndex(),fwZhsDO2.getFwZhsIndex());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @ExpectedDatabase(value = "/data/fwZhs-expectData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void batchInsert() {
        List<FwZhsDO> list = new ArrayList<>();
        FwZhsDO fwZhsDO = new FwZhsDO();
        fwZhsDO.setFwZhsIndex("098890");
        fwZhsDO.setFwHsIndex( "212321");
        list.add(fwZhsDO);
        list = fwZhsService.batchInsert(list);
        Assert.assertEquals(1,list.size());

        List<FwZhsDO> list1 = new ArrayList<>();
        FwZhsDO fwZhsDO1 = new FwZhsDO();
        fwZhsDO1.setFwHsIndex( "212321");
        list1.add(fwZhsDO1);
        list1 = fwZhsService.batchInsert(list1);
        Assert.assertEquals(1,list1.size());
    }
}