package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.common.core.domain.building.FwHstDO;
import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import cn.gtmap.realestate.common.core.dto.building.XmxxResponseDTO;
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
public class FwXmxxServiceTest {

    @Autowired
    private FwXmxxService fwXmxxService;
    @Autowired
    private FwHstService fwHstService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwXmxx-setupData.xml")
    public void listXmxxByPage() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("bdcdyh", "98457345123");
        paramMap.put("lszd", "123123");
        Pageable pageable = new PageRequest(0, 1);
        Page<XmxxResponseDTO> xmxxResponseDTOPage = fwXmxxService.listXmxxByPage(pageable, paramMap);
        Assert.assertNotNull(xmxxResponseDTOPage);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwXmxx-setupData.xml")
    public void queryXmxxByBdcdyh() {
        String bdcdyh = "98457345123";
        FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByBdcdyh(bdcdyh);
        Assert.assertNotNull(fwXmxxDO);
        FwXmxxDO emptyFwXmxxDO = fwXmxxService.queryXmxxByBdcdyh("");
        Assert.assertNull(emptyFwXmxxDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwXmxx-setupData.xml")
    public void queryXmxxByPk() {
        String fwXmxxIndex = "321123";
        FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByPk(fwXmxxIndex);
        Assert.assertNotNull(fwXmxxDO);
        FwXmxxDO emptyFwXmxxDO = fwXmxxService.queryXmxxByPk("");
        Assert.assertNull(emptyFwXmxxDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwXmxx-setupData.xml")
    @ExpectedDatabase(value = "/data/fwXmxx-expectData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateFwXmxx() {
        FwXmxxDO fwXmxxDO = new FwXmxxDO();
        fwXmxxDO.setFwXmxxIndex("321123");
        fwXmxxDO.setLszd("123123");
        fwXmxxDO.setBdcdyh("54654567");
        int updateResult = fwXmxxService.updateFwXmxx(fwXmxxDO, false);
        Assert.assertEquals(1, updateResult);
        int updateNullResult = fwXmxxService.updateFwXmxx(fwXmxxDO, true);
        Assert.assertEquals(1, updateNullResult);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    public void insertFwXmxx() {
        FwXmxxDO fwXmxxDO = new FwXmxxDO();
        fwXmxxDO.setFwXmxxIndex("321123");
        fwXmxxDO.setLszd("123123");
        fwXmxxDO.setBdcdyh("54654567");
        fwXmxxDO = fwXmxxService.insertFwXmxx(fwXmxxDO);
        FwXmxxDO queryFwXmxxDO = fwXmxxService.queryXmxxByPk(fwXmxxDO.getFwXmxxIndex());
        Assert.assertEquals(queryFwXmxxDO.getFwXmxxIndex(),fwXmxxDO.getFwXmxxIndex());

        FwXmxxDO fwXmxxDO1 = new FwXmxxDO();
        fwXmxxDO1.setLszd("123123");
        fwXmxxDO1.setBdcdyh("54654567");
        fwXmxxDO1 = fwXmxxService.insertFwXmxx(fwXmxxDO1);
        FwXmxxDO queryFwXmxxDO1 = fwXmxxService.queryXmxxByPk(fwXmxxDO1.getFwXmxxIndex());
        Assert.assertEquals(queryFwXmxxDO1.getFwXmxxIndex(),fwXmxxDO1.getFwXmxxIndex());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fwXmxx-setupData.xml")
    public void deleteFwXmxxByFwXmxxIndexTest() {
        String index = "321123";
        FwHstDO fwHstDOOld=fwHstService.queryFwHstByIndex(index);
        Assert.assertNotNull(fwHstDOOld);
        int delResult = fwXmxxService.deleteFwXmxxByFwXmxxIndex(index,true);
        Assert.assertEquals(1,delResult);
        FwHstDO fwHstDO=fwHstService.queryFwHstByIndex(index);
        Assert.assertNull(fwHstDO);

    }
}
