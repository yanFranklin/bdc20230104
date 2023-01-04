package cn.gtmap.realestate.register.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.core.qo.register.BdcZxQO;
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
import java.util.Date;
import java.util.List;

/**
 * BdcYgServiceImpl Tester.
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
public class BdcYgServiceImplTest {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcYgServiceImpl bdcYgService;


    /**
     * Method: queryListBdcQl(String bdcdyh)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testQueryListBdcQl() throws Exception {
        String bdcdyh = "320902004004GB00018F00010003";

        List<BdcQl> bdcYgDOList = bdcYgService.queryListBdcQl(bdcdyh, null);
        Assert.assertTrue(CollectionUtils.isNotEmpty(bdcYgDOList));
        BdcQlNumDTO bdcQlNumDTO = new BdcQlNumDTO();
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(1);
        Integer count = bdcYgService.countBdcQl(bdcQlNumDTO, bdcdyh, qsztList).getYgNum();
        Assert.assertTrue(CollectionUtils.size(bdcYgDOList) == count);

        bdcYgDOList = bdcYgService.queryListBdcQl("", null);
        Assert.assertTrue(CollectionUtils.isEmpty(bdcYgDOList));
        count = bdcYgService.countBdcQl(bdcQlNumDTO, "", qsztList).getYgNum();
        Assert.assertTrue(CollectionUtils.size(bdcYgDOList) == count);

    }
    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateZxDbxx() throws Exception {
        String zxdbr = "taozi";
        Date zxdjsj = new Date();

        BdcYgDO bdcYgDO = entityMapper.selectByPrimaryKey(BdcYgDO.class, "1");
        bdcYgDO.setZxygdbr(zxdbr);
        bdcYgDO.setZxygdjsj(zxdjsj);

        BdcZxQO bdcZxQO = new BdcZxQO();
        bdcZxQO.setZxdjsj(zxdjsj);
        bdcZxQO.setZxdbr(zxdbr);
        bdcYgService.updateZxDbxx(bdcYgDO, bdcZxQO);

        BdcYgDO bdcYgDO1 = entityMapper.selectByPrimaryKey(BdcYgDO.class, bdcYgDO.getQlid());
        Assert.assertEquals(bdcYgDO1.getZxygdbr(), zxdbr);
    }


} 
