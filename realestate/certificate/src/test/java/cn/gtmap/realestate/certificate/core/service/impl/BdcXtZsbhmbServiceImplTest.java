package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.CertificateApp;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtZsbhmbDO;
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

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/11
 * @description  证书编号模板配置表DAO操作测试
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
@DatabaseSetup(value = "/data/bdcXtZsbhmb-setupData.xml", type = DatabaseOperation.CLEAN_INSERT)
public class BdcXtZsbhmbServiceImplTest {
    @Autowired
    private BdcXtZsbhmbServiceImpl bdcXtZsbhmbService;


    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  获取证书编号模板测试
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testQueryBdcXtZsbhmb() throws Exception {
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setQxdm("340100");
        BdcXtZsbhmbDO bdcXtZsbhmbDO = bdcXtZsbhmbService.queryBdcXtZsbhmb(bdcXmDO);

        Assert.assertNotNull(bdcXtZsbhmbDO);
        Assert.assertEquals("sqsjc(nf)szsxqc不动产权证第num号", bdcXtZsbhmbDO.getBdcqzh());

        bdcXmDO.setQxdm("");
        bdcXtZsbhmbDO = bdcXtZsbhmbService.queryBdcXtZsbhmb(bdcXmDO);
        Assert.assertEquals(null, bdcXtZsbhmbDO);

        bdcXtZsbhmbDO = bdcXtZsbhmbService.queryBdcXtZsbhmb(new BdcXmDO());
        Assert.assertEquals(null, bdcXtZsbhmbDO);

        bdcXmDO.setQxdm("340221");
        Assert.assertEquals(null, bdcXtZsbhmbDO);
    }
}