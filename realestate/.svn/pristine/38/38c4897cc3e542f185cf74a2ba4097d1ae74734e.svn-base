package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.CertificateApp;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
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
 * BdcXmServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11/30/2018</pre>
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
@SpringBootTest(classes = CertificateApp.class)
@DatabaseSetup(value = "/data/bdcZs-setupData.xml", type = DatabaseOperation.CLEAN_INSERT)
public class BdcXmServiceImplTest {
    @Autowired
    BdcXmServiceImpl bdcXmService;
    @Autowired
    EntityMapper entityMapper;

    /**
     * Method: updateXmBz(String xmid, String bz)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateXmBz() throws Exception {
        String xmid = "xm123";
        String bz = "测试备注";
        int count = bdcXmService.updateXmBz(xmid, bz);
        Assert.assertEquals(1, count);
        BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
        Assert.assertEquals(bz, bdcXmDO.getBz());
    }

    /**
     * Method: queryListBdcXm(String xmid, String gzlslid)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testQueryListBdcXm() throws Exception {
        String xmid = "xm123";
        String gzlslid = "123";
        List<BdcXmDO> bdcXmDOList = bdcXmService.queryLcAllBdcXm(xmid, null);
        Assert.assertEquals(1, CollectionUtils.size(bdcXmDOList));

        bdcXmDOList = bdcXmService.queryLcAllBdcXm(xmid, gzlslid);
        Assert.assertEquals(1, CollectionUtils.size(bdcXmDOList));

        bdcXmDOList = bdcXmService.queryLcAllBdcXm(xmid, gzlslid);
        Assert.assertEquals(1, CollectionUtils.size(bdcXmDOList));
        try {
            bdcXmDOList = bdcXmService.queryLcAllBdcXm("", "");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof MissingArgumentException);
        }
    }

    /**
     * Method: queryBdcXmLsgxByXmid(String xmid)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testQueryBdcXmLsgxByXmid() throws Exception {
        String xmid = "xm123";
        List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcXmService.queryBdcXmLsgxByXmid(xmid);
        Assert.assertEquals(1, CollectionUtils.size(bdcXmLsgxDOList));

        xmid = "";
        bdcXmLsgxDOList = bdcXmService.queryBdcXmLsgxByXmid(xmid);
        Assert.assertEquals(0, CollectionUtils.size(bdcXmLsgxDOList));
    }


}
