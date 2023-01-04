package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO;
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
public class bdcdyhServiceTest {

    @Autowired
    private BdcdyhService bdcdyhService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdyh-setupData.xml")
    public void creatFwBdcdyhByZdzhdmAndZrzh() {
        String zdzhdm = "1234567890123456789";
        String zrzh = "1";
        String bdcdyh = bdcdyhService.creatFwBdcdyhByZdzhdmAndZrzh(zdzhdm,zrzh);
        Assert.assertNotNull(bdcdyh);
        String zdzhdm1 = "9876543210123456789";
        String zrzh1 = "1";
        String bdcdyh1 = bdcdyhService.creatFwBdcdyhByZdzhdmAndZrzh(zdzhdm1,zrzh1);
        Assert.assertNotNull(bdcdyh);
        String empty = bdcdyhService.creatFwBdcdyhByZdzhdmAndZrzh("","");
        Assert.assertEquals("",empty);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    public void creatFwBdcdyhByZdzhdmAndZrzh1() {
        String zdzhdm = "1234567890123456789";
        String zrzh = "1";
        String bdcdyh = bdcdyhService.creatFwBdcdyhByZdzhdmAndZrzh(zdzhdm,zrzh);
        Assert.assertNotNull(bdcdyh);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdyh-setupData.xml")
    public void creatFwBdcdyhByFwDcbIndex() {
        String fwDcbIndex = "12324354";
        String bdcdyh = bdcdyhService.creatFwBdcdyhByFwDcbIndex(fwDcbIndex);
        Assert.assertNotNull(bdcdyh);
        String empty = bdcdyhService.creatFwBdcdyhByFwDcbIndex("");
        Assert.assertEquals("",empty);
    }
}
