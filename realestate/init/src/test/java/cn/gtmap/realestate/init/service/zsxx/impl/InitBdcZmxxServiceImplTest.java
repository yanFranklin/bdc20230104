package cn.gtmap.realestate.init.service.zsxx.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.App;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
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
 * 初始化证明信息测试
 *
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/28.
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = App.class)
public class InitBdcZmxxServiceImplTest {
    @Autowired
    private InitBdcZmxxServiceImpl initBdcZmxxService;

    @Test
    public void getVal() throws Exception {
        Assert.assertEquals(CommonConstantUtils.ZSLX_ZM.toString(), initBdcZmxxService.getVal());
    }

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/InitBdcZsSetup-data.xml")
    public void initZsxx() throws Exception {
        InitServiceQO initServiceQO = new InitServiceQO();
        BdcXmDO bdcXm = new BdcXmDO();
        bdcXm.setXmid("12321");
        initServiceQO.setBdcXm(bdcXm);
        List<BdcZsDO> bdcZsList = initBdcZmxxService.initZsxx(initServiceQO);
        BdcZsDO bdcZs = bdcZsList.get(0);
        Assert.assertEquals(1, bdcZsList.size());
        Assert.assertEquals("权利人1 权利人2", bdcZs.getQlr());
        Assert.assertEquals("登簿人：登簿人 坐落：个任务而我认为\n" +
                "附记", bdcZs.getFj());
    }

}