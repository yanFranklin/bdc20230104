package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.building.YchsAndQlrResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.YchsResponseDTO;
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

import java.util.HashMap;
import java.util.Map;

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
public class FwYcHsServiceTest {
    @Autowired
    private FwYcHsService fwYcHsService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwYchs-setupData.xml")
    public void queryFwYcHsByBdcdyh() {
        String bdcdyh = "230803214322GB00002F00060072";
        FwYchsDO fwYchsDO = fwYcHsService.queryFwYcHsByBdcdyh(bdcdyh);
        Assert.assertNotNull(fwYchsDO);
        FwYchsDO emptyFwYchsDO = fwYcHsService.queryFwYcHsByBdcdyh("");
        Assert.assertNull(emptyFwYchsDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    public void insertFwYcHs() {
        FwYchsDO fwYchsDO = new FwYchsDO();
        fwYchsDO.setFwHsIndex("212321");
        fwYchsDO.setFwDcbIndex("123321123");
        fwYchsDO.setBdcdyh("230803214322GB00002F00060099");
        fwYchsDO = fwYcHsService.insertFwYcHs(fwYchsDO);
        Assert.assertNotNull(fwYchsDO);

        FwYchsDO fwYchsDO1 = new FwYchsDO();
        fwYchsDO1.setFwDcbIndex("123321123");
        fwYchsDO1.setBdcdyh("230803214322GB00002F00060099");
        fwYchsDO1 = fwYcHsService.insertFwYcHs(fwYchsDO1);
        Assert.assertNotNull(fwYchsDO1);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwYchs-setupData.xml")
    @ExpectedDatabase(value = "/data/fwYchs-expectData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateFwYcHs() {
        FwYchsDO fwYchsDO = new FwYchsDO();
        fwYchsDO.setFwHsIndex("212321");
        fwYchsDO.setFwDcbIndex("123321123");
        fwYchsDO.setBdcdyh("230803214322GB00002F00060099");
        int updateResult = fwYcHsService.updateFwYcHs(fwYchsDO, false);
        Assert.assertEquals(1, updateResult);
        int updateNullResult = fwYcHsService.updateFwYcHs(fwYchsDO, true);
        Assert.assertEquals(1, updateNullResult);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwYchs-setupData.xml")
    public void deleteYcHsByFwHsIndex() {
        String fwHsIndex = "212321";
        int delResult = fwYcHsService.deleteYcHsByFwHsIndex(fwHsIndex,true);
        Assert.assertEquals(1, delResult);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwYchs-setupData.xml")
    public void listYchsByPage() {
        Map map = new HashMap();
        map.put("fwDcbIndex", "123321123");
        Pageable pageable = new PageRequest(0, 1);
        Page<FwYchsDO> ychsResponseDTO = fwYcHsService.listYchsByPage(pageable, map);
        Assert.assertNotNull(ychsResponseDTO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwYchs-setupData.xml")
    public void queryFwYcHsAndQlrByBdcdyh() {
        String bdcdyh = "230803214322GB00002F00060072";
        YchsAndQlrResponseDTO ychsAndQlrResponseDTO = fwYcHsService.queryYchsAndQlrByBdcdyh(bdcdyh);
        Assert.assertNotNull(ychsAndQlrResponseDTO);
        YchsAndQlrResponseDTO emptyYchsAndQlrResponseDTO = fwYcHsService.queryYchsAndQlrByBdcdyh("");
        Assert.assertNull(emptyYchsAndQlrResponseDTO);
    }
}