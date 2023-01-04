package cn.gtmap.realestate.init.service.other;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.init.App;
import cn.gtmap.realestate.init.core.service.BdcZsService;
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
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/27
 * @description 不动产证书服务单元测试
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

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/BdcZsSetup-data.xml")
    public void queryBdcZsByIdTest(){
        BdcZsDO bdcZsDO = bdcZsService.queryBdcZsById("111");
        Assert.assertEquals("test1证书2",bdcZsDO.getBdcqzh());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/BdcZsSetup-data.xml")
    public void updateBdcZsByIdTest(){
        BdcZsDO bdcZsDO = bdcZsService.queryBdcZsById("111");
        bdcZsDO.setBdcqzh("证书更新");
        bdcZsService.updateBdcZs(bdcZsDO);
        bdcZsDO = bdcZsService.queryBdcZsById("111");
        Assert.assertEquals("证书更新",bdcZsDO.getBdcqzh());
    }
}
