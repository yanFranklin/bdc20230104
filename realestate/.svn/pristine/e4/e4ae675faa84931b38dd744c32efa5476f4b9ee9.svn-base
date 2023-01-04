package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.common.core.domain.building.QszdQlrDO;
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
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-12
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
public class QszdQlrServiceTest {

    @Autowired
    private QszdQlrService qszdQlrService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/qszdDjdcb-setupData.xml")
    public void listQszdQlrByBdcdyh() {
        String bdcdyh = "230522101202GB00107W00000000";
        List<QszdQlrDO> qszdQlrDOList = qszdQlrService.listQszdQlrByBdcdyh(bdcdyh);
        Assert.assertNotNull(qszdQlrDOList);
        List<QszdQlrDO> emptyList = qszdQlrService.listQszdQlrByBdcdyh("");
        Assert.assertEquals(new ArrayList<>(0), emptyList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/qszdDjdcb-setupData.xml")
    public void listQszdQlrByQszdDjdcbIndex() {
        String qszdDjdcbIndex = "123456";
        List<QszdQlrDO> list = qszdQlrService.listQszdQlrByQszdDjdcbIndex(qszdDjdcbIndex);
        Assert.assertNotNull(list);
        List<QszdQlrDO> emptyList = qszdQlrService.listQszdQlrByQszdDjdcbIndex("");
        Assert.assertEquals(new ArrayList<>(0), emptyList);
    }
}
