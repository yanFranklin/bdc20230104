package cn.gtmap.realestate.register.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.register.App;
import cn.gtmap.realestate.register.core.dto.BdcQlNumDTO;
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

import java.util.ArrayList;
import java.util.List;

/**
 * BdcLqServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11/15/2018</pre>
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
public class BdcLqServiceImplTest {
    @Autowired
    BdcLqServiceImpl bdcLqService;
    @Autowired
    EntityMapper entityMapper;

    /**
     * Method: queryListBdcQl(String bdcdyh)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testQueryListBdcQl() throws Exception {
        String bdcdyh = "320902004004JE00018L00010003";

        List<BdcQl> bdcLqDOList = bdcLqService.queryListBdcQl(bdcdyh, null);
        BdcQlNumDTO bdcQlNumDTO = new BdcQlNumDTO();
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(1);
        Integer count = bdcLqService.countBdcQl(bdcQlNumDTO, bdcdyh, qsztList).getCqNum();
        Assert.assertTrue(CollectionUtils.isNotEmpty(bdcLqDOList));
        Assert.assertTrue(count == CollectionUtils.size(bdcLqDOList));

        bdcLqDOList = bdcLqService.queryListBdcQl("", null);
        count = bdcLqService.countBdcQl(bdcQlNumDTO, "", qsztList).getCqNum();
        Assert.assertTrue(CollectionUtils.isEmpty(bdcLqDOList));
        Assert.assertTrue(count == CollectionUtils.size(bdcLqDOList));
    }
} 
