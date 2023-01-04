package cn.gtmap.realestate.engine.service.impl;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzBmdDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcBmdLoginDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.engine.EngineApp;
import cn.gtmap.realestate.engine.core.service.BdcGzBmdService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *  Tester.
 *
 * @author <Authors huangjian>
 * @version 1.0
 * @since <pre>10/31/2018</pre>
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
@SpringBootTest(classes = EngineApp.class)
//@SpringBootTest(classes = MockServletContext.class)
@WebAppConfiguration
public class BdcGzBmdServiceImplTest {
    @Autowired
    EntityMapper entityMapper;

    @Autowired
    BdcGzBmdService bmdService;

    /**
     * Method: queryBdcShxx(BdcShxxDO bdcShxx)
     */
    @Test
    @Transactional
    @Rollback(true)
    @DatabaseSetup(value = "/data/bdcBmd-setupData.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testQueryBdcShxx() throws Exception {
        String cary = "黄健";
        String carymm = "123456";
        String czryid = "2";

        BdcBmdLoginDTO bdcBmdLoginDTO = bmdService.checkIsLogin(cary, carymm);
        Assert.assertEquals(czryid, bdcBmdLoginDTO.getCzryid());

    }

    @Test
    @Transactional
    @Rollback(true)
    @DatabaseSetup(value = "/data/bdcBmd-setupData.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testDeleteBdcBmd() {
        List<BdcGzBmdDO> bdcGzBmdDOList = new ArrayList<>(2);
        BdcGzBmdDO bdcGzBmdDO = initBdcBmd();
        bdcGzBmdDOList.add(bdcGzBmdDO);
        bmdService.deleteBdcBmd(bdcGzBmdDOList);
        Assert.assertEquals(1, 1);
    }

    BdcGzBmdDO initBdcBmd() {
        BdcGzBmdDO bdcGzBmdDO = new BdcGzBmdDO();
        bdcGzBmdDO.setCzryid("2");
        bdcGzBmdDO.setCzry("黄健");
        return bdcGzBmdDO;
    }


}
