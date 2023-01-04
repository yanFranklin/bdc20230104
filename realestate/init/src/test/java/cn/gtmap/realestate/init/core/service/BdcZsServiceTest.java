package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.init.App;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
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
 * 证书单体服务 测试
 *
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0
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
public class BdcZsServiceTest {
    @Autowired
    private BdcZsService bdcZsService;

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/BdcZsSetup-data.xml")
    public void queryBdcZsById() throws Exception {
        BdcZsDO bdcZsDO = bdcZsService.queryBdcZsById("111");

        System.out.println(bdcZsDO.toString());
    }

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/BdcZsSetup-data.xml")
    public void updateBdcZs() throws Exception {
        BdcZsDO bdcZsDO = new BdcZsDO();
        bdcZsDO.setZsid("111");
        bdcZsDO.setFj("附记");
        bdcZsService.updateBdcZs(bdcZsDO);
        bdcZsDO = bdcZsService.queryBdcZsById("111");

        System.out.println(bdcZsDO.toString());
    }

}