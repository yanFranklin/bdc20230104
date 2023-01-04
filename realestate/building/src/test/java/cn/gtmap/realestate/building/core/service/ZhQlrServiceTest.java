package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.common.core.domain.building.ZhQlrDO;
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
public class ZhQlrServiceTest {

    @Autowired
    private ZhQlrService zhQlrService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/zhDjdcb-setupData.xml")
    public void listZhQlrByBdcdyh() {
        String bdcdyh = "320802007003GH00009W00000000";
        List<ZhQlrDO> zhQlrDoList = zhQlrService.listZhQlrByBdcdyh(bdcdyh);
        Assert.assertNotNull(zhQlrDoList);
        List<ZhQlrDO> emptyList = zhQlrService.listZhQlrByBdcdyh("");
        Assert.assertEquals(new ArrayList<>(0), emptyList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/zhDjdcb-setupData.xml")
    public void listZhQlrByZhDcbIndex() {
        String dcbIndex = "111111111";
        List<ZhQlrDO> zhQlrDoList = zhQlrService.listZhQlrByZhDcbIndex(dcbIndex);
        Assert.assertNotNull(zhQlrDoList);
    }
}
