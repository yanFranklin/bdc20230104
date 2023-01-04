package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.core.service.BdcYxbdcdyKgPzService;
import cn.gtmap.realestate.common.core.domain.accept.BdcYxbdcdyKgPzDO;
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
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/2/28
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
public class BdcYxbdcdyKgPzServiceImplExtTest {
    @Autowired
    private BdcYxbdcdyKgPzService bdcYxbdcdyKgPzService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcYxbdcdyKgPz-setupData.xml")
    public void queryBdcYxbdcdyKgPzDOByGzldyid() {
        BdcYxbdcdyKgPzDO bdcYxbdcdyKgPzDO = bdcYxbdcdyKgPzService.queryBdcYxbdcdyKgPzDOByGzldyid("111111111");
        Assert.assertNotNull(bdcYxbdcdyKgPzDO);
    }
}
