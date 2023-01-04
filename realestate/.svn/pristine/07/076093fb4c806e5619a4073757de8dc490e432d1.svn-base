package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.init.App;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
import org.springframework.util.Assert;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/26
 * @description 项目历史关系服务
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
public class BdcXmLsgxServiceTest {
    @Autowired
    private BdcXmLsgxService bdcXmLsgxService;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/BdcXmLsgxSetup-data.xml")
    public void queryBdcXmLsgxList() throws Exception {
        // 正常查询
        Assert.notEmpty(bdcXmLsgxService.queryBdcXmLsgxList("1232123344", null, null), SUCCESS);
        Assert.notEmpty(bdcXmLsgxService.queryBdcXmLsgxList(null, "34444444444", null), SUCCESS);
        // 缺少参数
        try {
            bdcXmLsgxService.queryBdcXmLsgxList(null, null, null);
            fail();
        } catch (MissingArgumentException ex) {
            assertThat(ex.getMessage(), is("缺失参数:未传入有效参数"));
        }

    }

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/BdcXmLsgxSetup-data.xml")
    public void queryBdcXmLsgxByXmid() throws Exception {
        // 存在传入参数
        Assert.notEmpty(bdcXmLsgxService.queryBdcXmLsgxByXmid("32",null), SUCCESS);
        // 传入参数为空
        Assert.isTrue(CollectionUtils.isEmpty(bdcXmLsgxService.queryBdcXmLsgxByXmid(null,null)),SUCCESS);
    }

}