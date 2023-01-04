package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.core.service.BdcSlXztzPzService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclPzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXztzPzDO;
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

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/3/26
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
public class BdcSlXztzPzServiceImplExtTest {
    @Autowired
    BdcSlXztzPzService bdcSlXztzPzService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSjclPz-setupData.xml")
    public void queryBdcSlXztzPzDOByGzldyid() {
        BdcSlXztzPzDO bdcSlXztzPzDO=bdcSlXztzPzService.queryBdcSlXztzPzDOByGzldyid("XmXbJ5Wbwi2JwRR7");
        Assert.assertNotNull(bdcSlXztzPzDO);

    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXztzPz-setupData.xml")
    public void saveBdcSlXztzPzDO() {
        BdcSlXztzPzDO bdcSlXztzPzDO =new BdcSlXztzPzDO();
        bdcSlXztzPzDO.setPzid("2");
        bdcSlXztzPzDO.setGzldyid("Y3SDHH2JZ8NtmDWn");
        int result =bdcSlXztzPzService.saveBdcSlXztzPzDO(bdcSlXztzPzDO);
        Assert.assertEquals(1,result);

    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXztzPz-setupData.xml")
    public void deleteBdcSlXztzPzDOByGzldyid() {
        int result =bdcSlXztzPzService.deleteBdcSlXztzPzDOByGzldyid("Y3SDHH2JZ8NtmDWn");
        Assert.assertEquals(1,result);

    }
}
