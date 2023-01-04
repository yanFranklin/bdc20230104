package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.ZdQlrDO;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018-11-26
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
public class ZdQlrServiceTest {

    @Autowired
    private ZdQlrService zdQlrService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/zdPage-setupData.xml")
    public void listZdQlrByBdcdyh() {
        String bdcdyh = "230522101202GB00107W00000000";
        List<ZdQlrDO> zdDjdcbDOList = zdQlrService.listZdQlrByBdcdyh(bdcdyh);
        Assert.assertNotNull(zdDjdcbDOList);
        List<ZdQlrDO> emptyList = zdQlrService.listZdQlrByBdcdyh("");
        Assert.assertEquals(new ArrayList<>(0), emptyList);
    }
}
