package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
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
public class FwHsServiceTest {
    @Autowired
    private FwHsService fwHsService;


    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwhs-setupData.xml")
    public void listFwHsListByFwDcbIndex() {
        String fwDcbIndex = "123321123";
        List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwDcbIndex);
        Assert.assertNotNull(fwHsDOList);
        List<FwHsDO> emptyList = fwHsService.listFwHsListByFwDcbIndex("");
        Assert.assertEquals(new ArrayList<>(0), emptyList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwhs-setupData.xml")
    public void queryFwHsByIndex() {
        String fwHsIndex = "212321";
        FwHsDO fwHsDO = fwHsService.queryFwHsByIndex(fwHsIndex);
        Assert.assertNotNull(fwHsDO);
        FwHsDO emptyFwHsDO = fwHsService.queryFwHsByIndex("");
        Assert.assertNull(emptyFwHsDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    public void insertFwHs() {
        FwHsDO fwHsDO = new FwHsDO();
        fwHsDO.setFwHsIndex("212321");
        fwHsDO.setBdcdyh("230803214322GB00002F00060099");
        fwHsDO.setFwDcbIndex("123321123");
        fwHsDO = fwHsService.insertFwHs(fwHsDO);
        FwHsDO queryFwHsDO = fwHsService.queryFwHsByIndex(fwHsDO.getFwHsIndex());
        Assert.assertEquals(queryFwHsDO.getFwHsIndex(), fwHsDO.getFwHsIndex());

        FwHsDO fwHsDO1 = new FwHsDO();
        fwHsDO1.setBdcdyh("230803214322GB00002F00060099");
        fwHsDO1.setFwDcbIndex("123321123");
        fwHsDO1 = fwHsService.insertFwHs(fwHsDO1);
        FwHsDO queryFwHsDO1 = fwHsService.queryFwHsByIndex(fwHsDO1.getFwHsIndex());
        Assert.assertEquals(queryFwHsDO1.getFwHsIndex(), fwHsDO1.getFwHsIndex());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwhs-setupData.xml")
    @ExpectedDatabase(value = "/data/fwhs-expectData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateFwHs() {
        FwHsDO fwHsDO = new FwHsDO();
        fwHsDO.setFwHsIndex("212321");
        fwHsDO.setBdcdyh("230803214322GB00002F00060099");
        fwHsDO.setFwDcbIndex("123321123");
        int updateResult = fwHsService.updateFwHs(fwHsDO, false);
        Assert.assertEquals(1, updateResult);
        int updateNullResult = fwHsService.updateFwHs(fwHsDO, true);
        Assert.assertEquals(1, updateNullResult);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwhs-setupData.xml")
    public void deleteHsByFwHsIndex() {
        String fwHsIndex = "212321";
        int delResult = fwHsService.deleteHsByFwHsIndex(fwHsIndex,true);
        Assert.assertEquals(1, delResult);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwhs-setupData.xml")
    public void queryFwhsByBdcdyh() {
        String bdcdyh = "230803214322GB00002F00060072";
        FwHsDO fwHsDO = fwHsService.queryFwhsByBdcdyh(bdcdyh);
        Assert.assertNotNull(fwHsDO);
        FwHsDO emptyFwHsDO = fwHsService.queryFwhsByBdcdyh("");
        Assert.assertNull(emptyFwHsDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwhs-setupData.xml")
    public void listFwHsByFwHstIndex() {
        String fwHstIndex = "123456";
        List<FwHsDO> fwHsDOList = fwHsService.listFwHsByFwHstIndex(fwHstIndex);
        Assert.assertNotNull(fwHsDOList);
        List<FwHsDO> emptyFwHsDOList = fwHsService.listFwHsByFwHstIndex("");
        Assert.assertEquals(new ArrayList<>(0), emptyFwHsDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwhstByFwdcb-setupData.xml")
    public void listFwHstNullByFwDcbIndex() {
        String fwDcbIndex = "123321123";
        List<FwHsDO> fwHsDOList = fwHsService.listFwHstNullByFwDcbIndex(fwDcbIndex);
        Assert.assertNotNull(fwHsDOList);
        List<FwHsDO> emptyFwHsDOList = fwHsService.listFwHstNullByFwDcbIndex("");
        Assert.assertEquals(new ArrayList<>(0), emptyFwHsDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwhs-setupData.xml")
    public void listFwHsBdcdyhNullByFwDcbIndex() {
        String fwDcbIndex = "123321123";
        List<FwHsDO> fwHsDOList = fwHsService.listFwHsZlNullByFwDcbIndex(fwDcbIndex);
        Assert.assertNotNull(fwHsDOList);
        List<FwHsDO> emptyFwHsDOList = fwHsService.listFwHsZlNullByFwDcbIndex("");
        Assert.assertEquals(new ArrayList<>(0), emptyFwHsDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwhsLjz-setupData.xml")
    @ExpectedDatabase(value = "/data/fwhsLjz-expectData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateFwHsLjz() {
        String fwDcbIndex = "123321123";
        String fwhs1 = "2123211";
        List<String> fwHsIndexList = new ArrayList<>();
        fwHsIndexList.add(fwhs1);
        fwHsService.updateFwHsLjz(fwHsIndexList, fwDcbIndex);
        FwHsDO queryFwHsDO = fwHsService.queryFwHsByIndex(fwhs1);
        Assert.assertEquals(queryFwHsDO.getFwDcbIndex(), fwDcbIndex);

    }
}