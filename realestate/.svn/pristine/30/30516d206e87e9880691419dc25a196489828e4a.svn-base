package cn.gtmap.realestate.register.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcFdcqFdcqxmDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.register.App;
import cn.gtmap.realestate.register.core.qo.BdcQlQO;
import cn.gtmap.realestate.register.core.service.BdcFdcqFdcqXmService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.commons.collections.CollectionUtils;
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
 * BdcFdcqFdcqXmServiceImpl Tester.
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
public class BdcFdcqFdcqXmServiceImplTest {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcFdcqFdcqXmService bdcFdcqFdcqXmService;


    /**
     * Method: queryFdcqxm(String qlid)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testQueryFdcqxm() throws Exception {
        List<BdcFdcqFdcqxmDO> bdcFdcqFdcqxmDOList = bdcFdcqFdcqXmService.listFdcqxm("1");
        Assert.assertTrue(CollectionUtils.isNotEmpty(bdcFdcqFdcqxmDOList));

        bdcFdcqFdcqxmDOList = bdcFdcqFdcqXmService.listFdcqxm("");
        Assert.assertTrue(CollectionUtils.isEmpty(bdcFdcqFdcqxmDOList));
    }

    /**
     * Method: countFdcqFdcqXm(BdcQlQO bdcQlQO)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testCountFdcqFdcqXm() throws Exception {
        BdcQlQO bdcQlQO = new BdcQlQO();
        bdcQlQO.setBdcdyh("320902004004GB00018F00010003");
        Integer count = bdcFdcqFdcqXmService.countFdcqFdcqXm(bdcQlQO);
        Assert.assertTrue(count == 5);
    }
} 
