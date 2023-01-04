package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.register.App;
import cn.gtmap.realestate.register.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.UncategorizedSQLException;
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
import java.util.List;
import java.util.NoSuchElementException;

/**
 * BdcXmxxServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11/09/2018</pre>
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
@DatabaseSetup(value = "/data/bdcXm-setupData.xml", type = DatabaseOperation.CLEAN_INSERT)
public class BdcXmxxServiceImplTest {
    @Autowired
    BdcXmxxServiceImpl bdcXmxxService;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcXmMapper bdcXmMapper;

    /**
     * Method: updateBdcXmDbxx(BdcXmDO bdcXmDO, String dbr, Date djsj, String djjg)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateBdcXmDbxx() throws Exception {
        BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, "123458");
        DbxxQO dbxxQO = new DbxxQO();
        BeanUtils.copyProperties(bdcXmDO, dbxxQO);

        dbxxQO.setXmid(null);
        try {
            int result = bdcXmMapper.updateBdcXmDbxxAndQsztByXmid(dbxxQO);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof UncategorizedSQLException);
        }

        dbxxQO.setXmid("");
        try {
            int result = bdcXmMapper.updateBdcXmDbxxAndQsztByXmid(dbxxQO);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof UncategorizedSQLException);
        }


        dbxxQO.setDjsj(new Date());
        dbxxQO.setDbr("桃子");
        dbxxQO.setDjjg("测试登记机构");
        try {
            bdcXmxxService.updateBdcXmDbxx(dbxxQO);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof MissingArgumentException);
        }

        dbxxQO.setXmid("123458");
        bdcXmxxService.updateBdcXmDbxx(dbxxQO);
        bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, "123458");
        Assert.assertEquals(bdcXmDO.getDbr(), "桃子");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateBdcXmDbxxNull() throws Exception {
        BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, "123458");
        DbxxQO dbxxQO = new DbxxQO();
        dbxxQO.setXmid("123458");
        bdcXmxxService.updateBdcXmDbxx(dbxxQO);
        bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, "123458");
        Assert.assertEquals(bdcXmDO.getDbr(), null);
    }

    /**
     * Method: getBdcXmLsgxByXmid(String xmid)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testGetBdcXmLsgxByXmid() throws Exception {
        List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcXmxxService.getBdcXmLsgxByXmid("123458");
        if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
            Assert.assertEquals("123458", bdcXmLsgxDOList.get(0).getXmid());
        } else {
            Assert.assertTrue(CollectionUtils.isEmpty(bdcXmLsgxDOList));
        }
        try {
            bdcXmLsgxDOList = bdcXmxxService.getBdcXmLsgxByXmid(" ");
            bdcXmLsgxDOList = bdcXmxxService.getBdcXmLsgxByXmid(null);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NoSuchElementException);
        }
    }

    /**
     * Method: getBdcXmLsgxByXmidAndZxyql(String xmid, Integer zxyql)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testGetBdcXmLsgxByXmidAndZxyql() throws Exception {
        List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcXmxxService.getBdcXmLsgxByXmidAndZxyql("123458", 1);
        if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
            Assert.assertEquals("123458", bdcXmLsgxDOList.get(0).getXmid());
        } else {
            Assert.assertTrue(CollectionUtils.isEmpty(bdcXmLsgxDOList));
        }
        bdcXmLsgxDOList = bdcXmxxService.getBdcXmLsgxByXmidAndZxyql("", null);
        Assert.assertTrue(CollectionUtils.isEmpty(bdcXmLsgxDOList));
    }

    /**
     * Method: getListBdcXmByGzlslid(String gzlslid)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testGetlistBdcXmByGzlslid() throws Exception {
        List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid("123");
        Assert.assertTrue(CollectionUtils.isNotEmpty(bdcXmDOList));
        try {
            bdcXmxxService.getListBdcXmByGzlslid(" ");
            bdcXmxxService.getListBdcXmByGzlslid(null);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NoSuchElementException);
        }
    }
} 
