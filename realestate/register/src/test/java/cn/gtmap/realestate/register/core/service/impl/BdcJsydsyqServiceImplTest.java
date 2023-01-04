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
 * BdcJsydsyqServiceImpl Tester.
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
public class BdcJsydsyqServiceImplTest {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcJsydsyqServiceImpl bdcJsydsyqService;

    /**
     * Method: queryListBdcQl(String bdcdyh)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testQueryListBdcQl() throws Exception {
        String bdcdyh = "320902004004GB00018F00010003";

        List<BdcQl> bdcJsydsyqDOList = bdcJsydsyqService.queryListBdcQl(bdcdyh, null);
        Assert.assertTrue(CollectionUtils.isNotEmpty(bdcJsydsyqDOList));
        BdcQlNumDTO bdcQlNumDTO = new BdcQlNumDTO();
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(1);
        Integer count = bdcJsydsyqService.countBdcQl(bdcQlNumDTO, bdcdyh, qsztList).getCqNum();
        Assert.assertTrue(count == CollectionUtils.size(bdcJsydsyqDOList));

        bdcJsydsyqDOList = bdcJsydsyqService.queryListBdcQl("", null);
        Assert.assertTrue(CollectionUtils.isEmpty(bdcJsydsyqDOList));
        count = bdcJsydsyqService.countBdcQl(bdcQlNumDTO, "", qsztList).getCqNum();
        Assert.assertTrue(count == CollectionUtils.size(bdcJsydsyqDOList));

    }
} 
