package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.dto.building.ZlsxDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
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


/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018/12/2
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
public class ZlsxServiceTest {

    @Autowired
    private ZlsxService zlsxService;

    @Autowired
    private EntityMapper entityMapper;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/zlsx-setupData.xml")
    public void zlsxByRule() {
        ZlsxDTO zlsxDTO = new ZlsxDTO();
        zlsxDTO.setDjh("230803214322GB00002");
        zlsxDTO.setFwDcbIndex("12345");
        zlsxDTO.isNullOnly();
        zlsxDTO.setSxgz("FW_LJZ.ZLDZ,FW_HS.zl,FW_HS.DYH,FW_K.ZLDZ,ZD_DJDCB.TDZL");
        zlsxService.zlsxByRule(zlsxDTO);
        FwHsDO fwHsDO = entityMapper.selectByPrimaryKey(FwHsDO.class,"212321");
        Assert.assertNotNull(fwHsDO.getZl());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/zlsx-setupData.xml")
    public void fwhsZlsxByRule() {
        ZlsxDTO zlsxDTO = new ZlsxDTO();
        zlsxDTO.setDjh("230803214322GB00002");
        zlsxDTO.setFwDcbIndex("12345");
        zlsxDTO.isNullOnly();
        zlsxDTO.setSxgz("FW_LJZ.ZLDZ,FW_HS.zl,FW_HS.DYH,FW_K.ZLDZ,ZD_DJDCB.TDZL");
        FwHsDO fwHsDO = new FwHsDO();
        fwHsDO.setFwDcbIndex("12345");
        fwHsDO.setFwHsIndex("212321");
        String zlExceptHs="哈尔滨";
        zlsxService.fwhsZlsxByRule(fwHsDO, zlsxDTO,zlExceptHs);
        FwHsDO fwHsDO1 = entityMapper.selectByPrimaryKey(FwHsDO.class,"212321");
        Assert.assertNotNull(fwHsDO1.getZl());
    }
}
