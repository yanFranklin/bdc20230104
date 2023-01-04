package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.common.core.domain.building.GzwDcbDO;
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
public class GzwDcbServiceTest {
    @Autowired
    private GzwDcbService gzwDcbService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/gzw-setupData.xml")
    public void getGzwDcbDOByBdcdyh() {
        String bdcdyh = "320802007003GHkkkk9F00008888";
        GzwDcbDO gzwDcbDO = gzwDcbService.getGzwDcbDOByBdcdyh(bdcdyh);
        Assert.assertNotNull(gzwDcbDO);
        GzwDcbDO emptyGzwDcbDO = gzwDcbService.getGzwDcbDOByBdcdyh("");
        Assert.assertNull(emptyGzwDcbDO);
    }
}