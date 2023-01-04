package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.common.core.domain.building.NydQlrDO;
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
public class NydQlrServiceTest {
    @Autowired
    private NydQlrService nydQlrService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/nydQlr-setupData.xml")
    public void listNydQlrByBdcdyh() {
        String bdcdyh = "230522101202GB00107W00000000";
        List<NydQlrDO> nydQlrDOList = nydQlrService.listNydQlrByBdcdyh(bdcdyh);
        Assert.assertNotNull(nydQlrDOList);
        List<NydQlrDO> emptyList = nydQlrService.listNydQlrByBdcdyh("");
        Assert.assertEquals(new ArrayList<>(0), emptyList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/nydQlr-setupData.xml")
    public void listNydQlrByDjh() {
        String djh = "230522101202GB00107";
        List<NydQlrDO> nydQlrDOList = nydQlrService.listNydQlrByDjh(djh);
        Assert.assertNotNull(nydQlrDOList);
        List<NydQlrDO> emptyList = nydQlrService.listNydQlrByDjh("");
        Assert.assertEquals(new ArrayList<>(0), emptyList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/nydQlr-setupData.xml")
    public void listLqQlrByDjh() {
        String djh = "230522101202GB00107";
        List<NydQlrDO> nydQlrDOList = nydQlrService.listLqQlrByDjh(djh);
        Assert.assertNotNull(nydQlrDOList);
        List<NydQlrDO> emptyList = nydQlrService.listLqQlrByDjh("");
        Assert.assertEquals(new ArrayList<>(0), emptyList);
    }
}