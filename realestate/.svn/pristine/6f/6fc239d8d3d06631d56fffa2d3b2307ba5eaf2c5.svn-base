package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.CertificateApp;
import cn.gtmap.realestate.common.core.domain.BdcXtYlzhDO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/11
 * @description 预留证号DAO操作测试
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
@DatabaseSetup(value = "/data/bdcXtYlzh-setupData.xml", type = DatabaseOperation.CLEAN_INSERT)
public class BdcXtYlzhServiceImplTest {
    @Autowired
    private BdcXtYlzhServiceImpl bdcXtYlzhService;


    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  获取证书对应的预留证号测试
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testQueryBdcXtYlzh() throws Exception {
        BdcXtYlzhDO bdcXtYlzhDO = bdcXtYlzhService.queryBdcXtYlzh("ZS_123121");

        assertNotNull(bdcXtYlzhDO);
        assertEquals("皖(2018)合肥市不动产权第0000012号", bdcXtYlzhDO.getBdcqzh());
        assertEquals("合肥市", bdcXtYlzhDO.getSzsxqc());

        bdcXtYlzhDO = bdcXtYlzhService.queryBdcXtYlzh("");
        Assert.assertEquals(null, bdcXtYlzhDO);

        bdcXtYlzhDO = bdcXtYlzhService.queryBdcXtYlzh("zsid");
        Assert.assertEquals(null, bdcXtYlzhDO);
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  更新预留证号使用情况测试
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateBdcXtYlzhSyqk() throws Exception {
        BdcXtYlzhDO bdcXtYlzhDO = new BdcXtYlzhDO();
        bdcXtYlzhDO.setYlzhid("2");
        bdcXtYlzhDO.setSyqk(3);

        int count = bdcXtYlzhService.updateBdcXtYlzhSyqk(bdcXtYlzhDO);
        assertEquals(1, count);

        count = bdcXtYlzhService.updateBdcXtYlzhSyqk(new BdcXtYlzhDO());
        assertEquals(0, count);

        bdcXtYlzhDO.setYlzhid("");
        assertEquals(0, count);
    }
}