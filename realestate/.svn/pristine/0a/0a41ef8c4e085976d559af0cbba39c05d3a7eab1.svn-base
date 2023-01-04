package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcShxxDO;
import cn.gtmap.realestate.register.App;
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
import cn.gtmap.realestate.common.core.qo.register.BdcShxxQO;

import java.util.List;

/**
 * BdcShxxServiceImpl Tester.
 *
 * @author <Authors zhangwentao>
 * @version 1.0
 * @since <pre>10/31/2018</pre>
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
public class BdcShxxServiceImplExtTest {
    @Autowired
    BdcShxxServiceImpl bdcShxxService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcShxx-setupData.xml")
    public void testQueryBdcShxx(){
        BdcShxxQO bdcShxxQO = new BdcShxxQO();
        bdcShxxQO.setXmid("123");
        List<BdcShxxDO> bdcShxxDO = bdcShxxService.queryBdcShxx(bdcShxxQO);
        Assert.assertNotNull(bdcShxxDO);
    }
} 
