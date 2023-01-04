package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.CertificateApp;
import cn.gtmap.realestate.common.core.domain.BdcXtZsfhDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXtZsfhDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
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

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/10
 * @description 不动产权证号DAO操作测试
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
public class BdcXtFhServiceImplTest {
    @Autowired
    private BdcXtFhServiceImpl bdcXtFhService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcXtFh-setupData.xml")
    public void testQueryBdcXtFh() throws Exception {
        BdcXtZsfhDTO bdcXtZsfhDTO = new BdcXtZsfhDTO();

        try {
            bdcXtFhService.queryBdcXtFh(bdcXtZsfhDTO);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof MissingArgumentException);
        }

        bdcXtZsfhDTO.setQxdm("340100");
        bdcXtZsfhDTO.setNf("2018");
        bdcXtZsfhDTO.setZslx(1);

        BdcXtZsfhDO bdcXtFh = bdcXtFhService.queryBdcXtFh(bdcXtZsfhDTO);
        Assert.assertNotNull(bdcXtFh);
        Assert.assertEquals("皖(2018)合肥市不动产权第0000012号", bdcXtFh.getBdcqzh());
        Assert.assertEquals("合肥市", bdcXtFh.getSzsxqc());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcXtFh-setupData.xml")
    public void testUpdateBdcXtFhSyqk() throws Exception {
        BdcXtZsfhDO bdcXtZsfhDO = new BdcXtZsfhDO();
        try {
            bdcXtFhService.updateBdcXtFhSyqk(bdcXtZsfhDO);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof MissingArgumentException);
        }

        bdcXtZsfhDO.setFczhid("1");
        bdcXtZsfhDO.setSyqk(3);

        int count = bdcXtFhService.updateBdcXtFhSyqk(bdcXtZsfhDO);
        Assert.assertEquals(1, count);
    }
}