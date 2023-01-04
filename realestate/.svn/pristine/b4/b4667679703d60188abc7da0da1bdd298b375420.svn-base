package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.common.core.domain.building.FwHstDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dto.building.LjzResponseDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018-11-26
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
public class FwLjzServiceTest {

    @Autowired
    private FwLjzService fwLjzService;
    @Autowired
    private FwHstService fwHstService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwLjz-setupData.xml")
    public void deleteLjzByFwDcbIndexTest() {
        String fwDcbIndex = "12345";
        FwHstDO fwHstDO=fwHstService.queryFwHstByIndex(fwDcbIndex);
        Assert.assertNotNull(fwHstDO);
        int delResult = fwLjzService.deleteLjzByFwDcbIndex(fwDcbIndex,true);
        Assert.assertEquals(1, delResult);
        FwHstDO fwHstDONow=fwHstService.queryFwHstByIndex(fwDcbIndex);
        Assert.assertNull(fwHstDONow);

    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwLjz-setupData.xml")
    public void queryLjzByFwDcbIndex() {
        String fwDcbIndex = "12345";
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);
        Assert.assertNotNull(fwLjzDO);
        FwLjzDO emptyfwLjzDO = fwLjzService.queryLjzByFwDcbIndex("");
        Assert.assertNull(emptyfwLjzDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    public void insertLjz() {
        FwLjzDO fwLjzDO = new FwLjzDO();
        fwLjzDO.setFwDcbIndex("12345");
        fwLjzDO.setLszd("123123");
        fwLjzDO.setBdcdyh("230123333324");
        fwLjzDO = fwLjzService.insertLjz(fwLjzDO);
        FwLjzDO queryFwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwLjzDO.getFwDcbIndex());
        Assert.assertEquals(queryFwLjzDO.getFwDcbIndex(), fwLjzDO.getFwDcbIndex());

        FwLjzDO fwLjzDO1 = new FwLjzDO();
        fwLjzDO1.setLszd("123123");
        fwLjzDO1.setBdcdyh("230123333324");
        fwLjzDO1 = fwLjzService.insertLjz(fwLjzDO1);
        FwLjzDO queryFwLjzDO1 = fwLjzService.queryLjzByFwDcbIndex(fwLjzDO1.getFwDcbIndex());
        Assert.assertEquals(queryFwLjzDO1.getFwDcbIndex(), fwLjzDO1.getFwDcbIndex());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwLjz-setupData.xml")
    public void queryLjzByBdcdyh() {
        String bdcdyh = "230123333324";
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByBdcdyh(bdcdyh);
        Assert.assertNotNull(fwLjzDO);
        FwLjzDO emptyFwLjzDO = fwLjzService.queryLjzByBdcdyh("");
        Assert.assertNull(emptyFwLjzDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwLjz-setupData.xml")
    public void listLjzByFwXmxxIndex() {
        String fwXmxxIndex = "123424";
        List<FwLjzDO> list = fwLjzService.listLjzByFwXmxxIndex(fwXmxxIndex);
        Assert.assertNotNull(list);
        List<FwLjzDO> emptyList = fwLjzService.listLjzByFwXmxxIndex("");
        Assert.assertEquals(new ArrayList<>(0), emptyList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwLjz-setupData.xml")
    public void listLjzByDjhAndZrzh() {
        String lszd = "123123";
        String zrzh = "1";
        List<FwLjzDO> list = fwLjzService.listLjzByDjhAndZrzh(lszd, zrzh);
        Assert.assertNotNull(list);
        List<FwLjzDO> emptyList = fwLjzService.listLjzByDjhAndZrzh("", "");
        Assert.assertEquals(new ArrayList<>(0), emptyList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwLjz-setupData.xml")
    @ExpectedDatabase(value = "/data/fwLjz-expectData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateLjz() {
        FwLjzDO fwLjzDO = new FwLjzDO();
        fwLjzDO.setFwDcbIndex("12345");
        fwLjzDO.setLszd("123123");
        fwLjzDO.setBdcdyh("2301233333245678");
        int updateResult = fwLjzService.updateLjz(fwLjzDO, false);
        Assert.assertEquals(1, updateResult);
        int updateNullResult = fwLjzService.updateLjz(fwLjzDO, true);
        Assert.assertEquals(1, updateNullResult);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwLjz-setupData.xml")
    public void listLjzByPage() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("bdcdyh", "230123333324");
        paramMap.put("lszd", "123123");
        Pageable pageable = new PageRequest(0, 1);
        Page<Map> ljzResponseDTOPage = fwLjzService.listLjzByPage(pageable, paramMap);
        Assert.assertNotNull(ljzResponseDTOPage);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    public void creatLjzh() {
        String lszd="123123";
        String ljzh=fwLjzService.creatLjzh(lszd,0);
        Assert.assertNotNull(ljzh);
    }
}