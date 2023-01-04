package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcShxxDO;
import cn.gtmap.realestate.common.core.qo.register.BdcShxxQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.register.App;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
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
import java.util.NoSuchElementException;

/**
 * BdcShxxServiceImpl Tester.
 *
 * @author <Authors zhangwentao>
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
@SpringBootTest(classes = App.class)
public class BdcShxxServiceImplTest {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcShxxServiceImpl bdcShxxService;
    /**
     * Method: insertBdcShxx(BdcShxxDO bdcShxx)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testInsertBdcShxx() throws Exception {
        BdcShxxDO bdcShxxDO = new BdcShxxDO();
        try {
            bdcShxxService.insertBdcShxx(bdcShxxDO);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof MyBatisSystemException);
        }

        bdcShxxDO.setGzlslid("2");
        bdcShxxDO.setShxxid("123458");
        bdcShxxDO.setShryid("测试审核人员id");
        bdcShxxDO.setShryxm("测试审核人员姓名");
        try {
            bdcShxxService.insertBdcShxx(bdcShxxDO);
            Example example = new Example(BdcShxxDO.class);
            example.createCriteria().andEqualTo("shxxid", bdcShxxDO.getShxxid());
            List<BdcShxxDO> bdcShxxDOList = entityMapper.selectByExampleNotNull(example);
            Assert.assertTrue(CollectionUtils.isNotEmpty(bdcShxxDOList));
            Assert.assertEquals(bdcShxxDO.getShxxid(), bdcShxxDOList.get(0).getShxxid());
        } catch (Exception e) {
            Assert.assertTrue(e instanceof DuplicateKeyException);
        }
    }

    /**
     * Method: updateBdcShxx(BdcShxxDO bdcShxx)
     */
    @Test
    @Transactional
    @Rollback(true)
    @DatabaseSetup(value = "/data/bdcShxx-setupData.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testUpdateBdcShxx() throws Exception {
        BdcShxxDO bdcShxxDO = new BdcShxxDO();
        try {
            bdcShxxService.updateBdcShxx(bdcShxxDO);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NoSuchElementException);
        }

        bdcShxxDO.setShyj("测试审核意见");
        try {
            bdcShxxService.updateBdcShxx(bdcShxxDO);
            BdcShxxDO bdcShxxDO1 = entityMapper.selectByPrimaryKey(BdcShxxDO.class, bdcShxxDO.getShxxid());
            if (null != bdcShxxDO1) {
                Assert.assertEquals(bdcShxxDO1.getShyj(), "测试审核意见");
            }
        } catch (Exception e) {
            Assert.assertTrue(e instanceof MyBatisSystemException);
        }

        bdcShxxDO.setShxxid("2");
        try {
            bdcShxxService.updateBdcShxx(bdcShxxDO);
            BdcShxxDO bdcShxxDO1 = entityMapper.selectByPrimaryKey(BdcShxxDO.class, bdcShxxDO.getShxxid());
            Assert.assertEquals(bdcShxxDO.getShyj(), "测试审核意见");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof MyBatisSystemException);
        }
    }


    /**
     * Method: queryBdcShxx(BdcShxxDO bdcShxx)
     */
    @Test
    @Transactional
    @Rollback(true)
    @DatabaseSetup(value = "/data/bdcShxx-setupData.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void testQueryBdcShxx() throws Exception {
        String gzlslid = "1";
        String shxxid = "123458";

        BdcShxxQO bdcShxxQO = new BdcShxxQO();
        List<BdcShxxDO> bdcShxxDOList = bdcShxxService.queryBdcShxx(bdcShxxQO);
        Assert.assertTrue(CollectionUtils.isEmpty(bdcShxxDOList));

        bdcShxxQO.setShxxid(shxxid);
        List<BdcShxxDO> bdcShxxDOListA = bdcShxxService.queryBdcShxx(bdcShxxQO);
        if (CollectionUtils.isNotEmpty(bdcShxxDOListA)) {
            Assert.assertEquals(bdcShxxDOListA.get(0).getShxxid(), shxxid);
        }
        bdcShxxQO.setGzlslid(gzlslid);
        List<BdcShxxDO> bdcShxxDOListB = bdcShxxService.queryBdcShxx(bdcShxxQO);
        if (CollectionUtils.isNotEmpty(bdcShxxDOListB)) {
            Assert.assertEquals(bdcShxxDOListB.get(0).getGzlslid(), gzlslid);
        }

        bdcShxxQO.setGzlslid(gzlslid);
        bdcShxxQO.setShxxid(shxxid);
        List<BdcShxxDO> bdcShxxDOListC = bdcShxxService.queryBdcShxx(bdcShxxQO);
        if (CollectionUtils.isNotEmpty(bdcShxxDOListC)) {
            Assert.assertEquals(bdcShxxDOListC.get(0).getGzlslid(), gzlslid);
        }
    }
} 
