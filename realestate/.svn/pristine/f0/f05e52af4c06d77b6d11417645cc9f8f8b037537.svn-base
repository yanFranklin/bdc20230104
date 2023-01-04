package cn.gtmap.realestate.init.service.other.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtZsmbPzDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.init.App;
import cn.gtmap.realestate.init.core.service.BdcXtZsmbPzService;
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
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/2
 * @description 证书模板配置服务
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
public class BdcXtZsmbPzServiceTest {
    @Autowired
    private BdcXtZsmbPzService bdcXtZsmbPzService;


    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/BdcXtZsPzSetup-data.xml")
    public void queryZsmbContentTest() {
        BdcXmDO bdcXmDO=new BdcXmDO();
        bdcXmDO.setQllx(2);
        bdcXmDO.setXmid("4444445");
        BdcZsDO bdcZsDO = bdcXtZsmbPzService.queryZsmbContent(bdcXmDO);
        Assert.assertNotNull(bdcZsDO);
        Assert.assertNotNull(bdcZsDO.getMj());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/BdcXtZsPzSetup-data.xml")
    public void queryBdcXtZsmbTest() {
        BdcXtZsmbPzDO bdcXtZsmbPzDO = bdcXtZsmbPzService.queryBdcXtZsmb(2);
        Assert.assertNotNull(bdcXtZsmbPzDO);
        Assert.assertNotNull(bdcXtZsmbPzDO.getMbsql());
    }
}