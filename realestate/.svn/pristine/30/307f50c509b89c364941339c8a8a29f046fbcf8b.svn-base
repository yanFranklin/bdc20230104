package cn.gtmap.realestate.register.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcFwzlDO;
import cn.gtmap.realestate.common.core.qo.register.BdcZxQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.register.App;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * BdcFwzlServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11/13/2018</pre>
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

@DatabaseSetup(value = "/data/bdcQl-setupData.xml", type = DatabaseOperation.CLEAN_INSERT)
public class BdcFwzlServiceImplTest {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcFwzlServiceImpl bdcFwzlService;

    /**
     * Method: updateZxDbxx(BdcFwzlDO bdcFwzlDO, String zxdbr, Date zxdjsj)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateZxDbxx() throws Exception {
        String zxdbr = "taozi";
        Date zxdjsj = new Date();
        BdcFwzlDO bdcFwzlDO = entityMapper.selectByPrimaryKey(BdcFwzlDO.class, "1");
        BdcZxQO bdcZxQO = new BdcZxQO();
        bdcZxQO.setZxdjsj(zxdjsj);
        bdcZxQO.setZxdbr(zxdbr);
        bdcFwzlService.updateZxDbxx(bdcFwzlDO, bdcZxQO);
        bdcFwzlDO = entityMapper.selectByPrimaryKey(BdcFwzlDO.class, "1");
        Assert.assertEquals(zxdbr, bdcFwzlDO.getZxfwzldbr());
    }


} 
