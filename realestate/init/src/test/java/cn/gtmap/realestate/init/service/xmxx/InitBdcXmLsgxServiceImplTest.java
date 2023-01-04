package cn.gtmap.realestate.init.service.xmxx;

import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.init.App;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmLsgxService;
import cn.gtmap.realestate.init.service.xmxx.impl.InitBdcXmLsgxServiceImpl;
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
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/28
 * @description 项目历史关系单元测试
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
public class InitBdcXmLsgxServiceImplTest {
    @Autowired
    private InitBdcXmLsgxServiceImpl initBdcXmLsgxService;
    @Autowired
    private BdcXmLsgxService bdcXmLsgxService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/InitBdcXmLsgxSetup-data.xml")
    public void initBdcXmLsgxTest(){
        List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcXmLsgxService.queryBdcXmLsgxByXmid("4849619684",null);
        int bdcXmLsgxNum = bdcXmLsgxDOList.size();
        InitServiceQO initServiceQO = new InitServiceQO();
        initServiceQO.setBdcXmLsgxList(bdcXmLsgxDOList);
        bdcXmLsgxDOList = initBdcXmLsgxService.initBdcXmLsgx(initServiceQO);
        Assert.assertNotEquals(bdcXmLsgxNum,bdcXmLsgxDOList.size());
    }
}
