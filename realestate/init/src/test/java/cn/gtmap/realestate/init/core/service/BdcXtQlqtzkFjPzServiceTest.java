package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.init.App;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
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
 * 生成默认附记和权利其他状况测试
 *
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/13.
 * @description
 */
// @RunWith(SpringRunner.class) 或者@RunWith(MockitoJUnitRunner.class) 都可以

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
public class BdcXtQlqtzkFjPzServiceTest {
    @Autowired
    private BdcXtQlqtzkFjPzService bdcXtQlqtzkFjPzService;
    @Autowired
    private BdcZsService bdcZsService;
    @Autowired
    private BdcQllxService bdcQllxService;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Before
    public void setup() {

    }

    /**
     * 更新权利其他状况
     *
     * @return
     */
    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/BdcXtQlqtzkFjPzSetup-data.xml")
    public void updateQlqtzk() {
        bdcXtQlqtzkFjPzService.updateQlqtzkFjByMode("4444444", "2");
        BdcZsDO bdcZs = bdcZsService.queryBdcZsById("111");
        org.junit.Assert.assertEquals("登簿人：登簿人 坐落：个任务而我认为", bdcZs.getQlqtzk());
    }



    /**
     * 获取权利其他状况信息
     *
     * @return
     */
    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/BdcXtQlqtzkFjPzSetup-data.xml")
    public void getQlqtzkMessage() {
        org.junit.Assert.assertEquals("登簿人：登簿人 坐落：个任务而我认为", bdcXtQlqtzkFjPzService.getQlqtzkMessageByZsxx("4444444","111",null));
        org.junit.Assert.assertEquals("", bdcXtQlqtzkFjPzService.getQlqtzkMessageByZsxx("4444444",null,null));
    }

    /**
     * 获取附记信息
     *
     * @return
     */
    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/BdcXtQlqtzkFjPzSetup-data.xml")
    public void getFjMessage() {
        org.junit.Assert.assertEquals("", bdcXtQlqtzkFjPzService.getFjMessageByZsxx("4444445",null,null));
        org.junit.Assert.assertEquals("登簿人：登簿人 坐落：个任务而我认为", bdcXtQlqtzkFjPzService.getFjMessageByZsxx("4444444",null,null));
    }

    /**
     * 更新权利其他状况附记信息
     *
     * @return
     */
    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/BdcXtQlqtzkFjPzSetup-data.xml")
    public void updateQlqtzkFj() {
        bdcXtQlqtzkFjPzService.updateQlqtzkFjByMode("4444446", "1");
        BdcZsDO bdcZs = bdcZsService.queryBdcZsById("113");
        BdcQl bdcQl = bdcQllxService.queryQllxDO("4444446");
        org.junit.Assert.assertEquals("登簿人：登簿人 坐落：个任务而我认为", bdcZs.getQlqtzk());
        org.junit.Assert.assertEquals(null, bdcQl.getFj());

        bdcXtQlqtzkFjPzService.updateQlqtzkFjByMode("4444444", "2");
        bdcZs = bdcZsService.queryBdcZsById("111");
        bdcQl = bdcQllxService.queryQllxDO("4444444");
        org.junit.Assert.assertEquals(null,bdcQl.getFj());
        org.junit.Assert.assertEquals("登簿人：登簿人 坐落：个任务而我认为",bdcZs.getQlqtzk());

        bdcXtQlqtzkFjPzService.updateQlqtzkFjByMode("4444444", "3");
        bdcZs = bdcZsService.queryBdcZsById("111");
        bdcQl = bdcQllxService.queryQllxDO("4444444");
        org.junit.Assert.assertEquals(null,bdcQl.getFj());
        org.junit.Assert.assertEquals("登簿人：登簿人 坐落：个任务而我认为",bdcZs.getQlqtzk());
    }

}