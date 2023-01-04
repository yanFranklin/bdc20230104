package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018/11/29
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
public class BdcdyZtServiceTest {

    @Autowired
    private BdcdyZtService bdcdyZtService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdyZt-setupData.xml")
    public void querySsjBdcdyhxsztDOByBdcdyh() {
        String bdcdyh = "230803214322GB00002F00060072";
        SSjBdcdyhxsztDO sSjBdcdyhxsztDO = bdcdyZtService.querySsjBdcdyhxsztDOByBdcdyh(bdcdyh);
        Assert.assertNotNull(sSjBdcdyhxsztDO);
        SSjBdcdyhxsztDO empty = bdcdyZtService.querySsjBdcdyhxsztDOByBdcdyh("");
        Assert.assertNull(empty);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdyZt-setupData.xml")
    public void listSsjBdcdyhxsztDOByBdcdyh() {
        String bdcdyh = "230803214322GB00002F00060072";
        String bdcdyh1 = "230803214322GB00002F00060074";
        List<String> bdcdyhList = new ArrayList<>();
        bdcdyhList.add(bdcdyh);
        bdcdyhList.add(bdcdyh1);
        List<String> bdcdyhFalseList = new ArrayList<>();
        bdcdyhFalseList.add("230803214322GB00002F00060073");
        List<BdcdyhZtResponseDTO> bdcdyhZtTrue = bdcdyZtService.listBdcdyhZtBybdcdyh(bdcdyhList);
        Assert.assertNotNull(bdcdyhZtTrue);
        List<BdcdyhZtResponseDTO> bdcdyhZtFalse = bdcdyZtService.listBdcdyhZtBybdcdyh(bdcdyhFalseList);
        Assert.assertNotNull(bdcdyhZtFalse);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdyZt-setupData.xml")
    public void queryBdcdyhZtBybdcdyh() {
        String bdcdyh = "230803214322GB00002F00060072";
        BdcdyhZtResponseDTO bdcdyhZtTrue = bdcdyZtService.queryBdcdyhZtBybdcdyh(bdcdyh);
        Assert.assertNotNull(bdcdyhZtTrue);
        BdcdyhZtResponseDTO empty = bdcdyZtService.queryBdcdyhZtBybdcdyh("");
        Assert.assertNull(empty);
        BdcdyhZtResponseDTO bdcdyhZtFalse = bdcdyZtService.queryBdcdyhZtBybdcdyh("230803214322GB00002F00060073");
        Assert.assertNotNull(bdcdyhZtFalse);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdyZt-setupData.xml")
    public void updateBdcdyZtByBdcdyh() {
        BdcdyhZtRequestDTO requestDTO = new BdcdyhZtRequestDTO();
        requestDTO.setBdcdyh("230803214322GB00002F00060072");
        requestDTO.setCf(1);
        requestDTO.setZjgcdy(1);
        requestDTO.setDya(1);
        requestDTO.setDyi(1);
        requestDTO.setSd(1);
        requestDTO.setYcf(1);
        requestDTO.setYdya(1);
        requestDTO.setYg(1);
        requestDTO.setYy(1);
        int updateResult = bdcdyZtService.updateBdcdyZtByBdcdyh(requestDTO);
        Assert.assertEquals(1, updateResult);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdyZt-setupData.xml")
    public void updateBdcdyZtByBdcdyh3() {
        BdcdyhZtRequestDTO requestDTO = new BdcdyhZtRequestDTO();
        requestDTO.setBdcdyh("230803214322GB00002F00060073");
        requestDTO.setCf(1);
        requestDTO.setZjgcdy(1);
        requestDTO.setDya(1);
        requestDTO.setDyi(1);
        requestDTO.setSd(1);
        requestDTO.setYcf(1);
        requestDTO.setYdya(1);
        requestDTO.setYg(1);
        requestDTO.setYy(1);
        int updateResult = bdcdyZtService.updateBdcdyZtByBdcdyh(requestDTO);
        Assert.assertEquals(1, updateResult);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdyZt-setupData.xml")
    public void updateBdcdyZtByBdcdyh1() {
        BdcdyhZtRequestDTO requestDTO = new BdcdyhZtRequestDTO();
        requestDTO.setBdcdyh("230803214322GB00002F00060072");
        requestDTO.setCf(-1);
        requestDTO.setZjgcdy(-1);
        requestDTO.setDya(-1);
        requestDTO.setDyi(-1);
        requestDTO.setSd(-1);
        requestDTO.setYcf(-1);
        requestDTO.setYdya(-1);
        requestDTO.setYg(-1);
        requestDTO.setYy(-1);
        int updateResult = bdcdyZtService.updateBdcdyZtByBdcdyh(requestDTO);
        Assert.assertEquals(1, updateResult);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdyZt-setupData.xml")
    public void updateBdcdyZtByBdcdyh2() {
        BdcdyhZtRequestDTO requestDTO = new BdcdyhZtRequestDTO();
        requestDTO.setBdcdyh("230803214322GB00002F00060072");
        requestDTO.setCf(0);
        requestDTO.setZjgcdy(0);
        requestDTO.setDya(0);
        requestDTO.setDyi(0);
        requestDTO.setSd(0);
        requestDTO.setYcf(0);
        requestDTO.setYdya(0);
        requestDTO.setYg(0);
        requestDTO.setYy(0);
        thrown.expect(AppException.class);
        int updateResult = bdcdyZtService.updateBdcdyZtByBdcdyh(requestDTO);
        Assert.assertEquals(0, updateResult);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdyZt-setupData.xml")
    public void updateBdcdyZtByBdcdyh4() {
        BdcdyhZtRequestDTO requestDTO = new BdcdyhZtRequestDTO();
        requestDTO.setBdcdyh("230803214322GB00002F00060073");
        requestDTO.setCf(-1);
        requestDTO.setZjgcdy(-1);
        requestDTO.setDya(-1);
        requestDTO.setDyi(-1);
        requestDTO.setSd(-1);
        requestDTO.setYcf(-1);
        requestDTO.setYdya(-1);
        requestDTO.setYg(-1);
        requestDTO.setYy(-1);
        thrown.expect(AppException.class);
        int updateResult = bdcdyZtService.updateBdcdyZtByBdcdyh(requestDTO);
        Assert.assertEquals(0, updateResult);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdyZt-setupData.xml")
    //@ExpectedDatabase(value = "/data/bdcdyZt-expectData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateBdcdyZtByBdcdyh5() {
        BdcdyhZtRequestDTO requestDTO = new BdcdyhZtRequestDTO();
        requestDTO.setBdcdyh("230803214322GB00002F00060073");
        requestDTO.setCf(0);
        requestDTO.setZjgcdy(0);
        requestDTO.setDya(0);
        requestDTO.setDyi(0);
        requestDTO.setSd(0);
        requestDTO.setYcf(0);
        requestDTO.setYdya(0);
        requestDTO.setYg(0);
        requestDTO.setYy(0);
        thrown.expect(AppException.class);
        int updateResult = bdcdyZtService.updateBdcdyZtByBdcdyh(requestDTO);
        Assert.assertEquals(0, updateResult);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdyZt-setupData.xml")
    public void getBdcdyztDOTest() {
        String bdcdyh = "230803214322GB00002F00060072";
        SSjBdcdyhxsztDO sSjBdcdyhxsztDO = bdcdyZtService.getBdcdyztDO(bdcdyh);
        Assert.assertNotNull(sSjBdcdyhxsztDO);
        String bdcdyhError = "230803214322GB";
        try {
            bdcdyZtService.getBdcdyztDO(bdcdyhError);
        } catch (Exception e) {
            Assert.assertEquals("AppException{code=1007, message='不动产单元号异常'}", e.toString());
        }
    }
}
