package cn.gtmap.realestate.register.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcFdcq3DO;
import cn.gtmap.realestate.common.core.domain.BdcFdcq3GyxxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.register.App;
import cn.gtmap.realestate.register.core.service.BdcFdcq3GyxxService;
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
 * BdcFdcq3GyxxServiceImpl Tester.
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
public class BdcFdcq3GyxxServiceImplTest {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcFdcq3GyxxService bdcFdcq3GyxxService;


    /**
     * Method: queryListBdcQlByXmid(String xmid)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testQueryListBdcQlByXmid() throws Exception {
        BdcFdcq3DO bdcFdcq3DO = entityMapper.selectByPrimaryKey(BdcFdcq3DO.class, "1");

        String xmid = "xm123";

        List<BdcFdcq3GyxxDO> bdcFdcq3GyxxDOList = bdcFdcq3GyxxService.queryListBdcQlByXmid(xmid);
        Assert.assertTrue(CollectionUtils.isNotEmpty(bdcFdcq3GyxxDOList));
        for (BdcFdcq3GyxxDO bdcFdcq3GyxxDO : bdcFdcq3GyxxDOList) {
            Assert.assertEquals(bdcFdcq3GyxxDO.getXmid(), xmid);
        }

        bdcFdcq3GyxxDOList = bdcFdcq3GyxxService.queryListBdcQlByXmid("");
        Assert.assertTrue(CollectionUtils.isEmpty(bdcFdcq3GyxxDOList));
    }
} 
