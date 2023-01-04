package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.CertificateApp;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
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
 * BdcQlrServiceImpl Tester.
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
public class BdcQlrServiceImplTest {
    @Autowired
    BdcQlrServiceImpl bdcQlrService;

    /**
     * Method: queryListBdcQlr(String xmid, Integer qlrlb)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testQueryListBdcQlr() throws Exception {
        String xmid = "xm123";
        int qlrlb = 1;
        List<BdcQlrDO> bdcQlrDOList = bdcQlrService.queryListBdcQlr(xmid, qlrlb);
        Assert.assertTrue(CollectionUtils.size(bdcQlrDOList) == 2);

        try {
            bdcQlrDOList = bdcQlrService.queryListBdcQlr("", qlrlb);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof MissingArgumentException);
        }
    }


} 
