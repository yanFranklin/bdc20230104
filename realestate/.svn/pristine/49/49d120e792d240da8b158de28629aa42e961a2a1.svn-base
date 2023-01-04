package cn.gtmap.realestate.init.service.other.impl;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.App;
import cn.gtmap.realestate.init.core.service.BdcQllxService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/2
 * @description 查询权利类型信息
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
public class BdcQllxServiceTest {
    @Autowired
    private BdcQllxService bdcQllxService;
    @Autowired
    private EntityMapper entityMapper;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getTableName() throws Exception {
        // 获取查封表表明
        BdcCfDO bdcCfDO = new BdcCfDO();
        String tableName = bdcQllxService.getTableName(bdcCfDO);
        Assert.assertEquals("BDC_CF",tableName);
    }

    @Test
    public void makeSureQllx() throws Exception {
        // 获取不动产抵押权
        BdcQl bdcQl = bdcQllxService.makeSureQllx(CommonConstantUtils.QLLX_DYAQ_DM.toString());
        Assert.assertTrue(bdcQl instanceof BdcDyaqDO);
    }

    @Test
    public void queryQllxDO() throws Exception {
        // 成功获取抵押权信息
        BdcDyaqDO bdcDyaqDO = new BdcDyaqDO();
        bdcQllxService.queryQllxDO(bdcDyaqDO,"xm123");

      /*  // 抵押权信息不存在时
        thrown.expect(RuntimeException.class);
        bdcQllxService.queryQllxDO(bdcDyaqDO,"1");*/


    }


    @Test
    @Transactional
    @Rollback(true)
    public void updateQl() throws Exception {
        String id=UUIDGenerator.generate16();
        String xmid=UUIDGenerator.generate16();
        BdcFdcqDO bdcFdcqDO=new BdcFdcqDO();
        bdcFdcqDO.setQlid(id);
        bdcFdcqDO.setXmid(UUIDGenerator.generate16());
        bdcFdcqDO.setBdcdywybh(UUIDGenerator.generate16());
        bdcFdcqDO.setBdcdyh("1234567890");
        bdcFdcqDO.setSlbh("123");
        bdcQllxService.insertBdcQl(bdcFdcqDO);
        BdcQl bdcQl=entityMapper.selectByPrimaryKey(BdcFdcqDO.class,id);
        Assert.assertNotNull(bdcQl);

        bdcQl.setFj("123");
        bdcQllxService.updateBdcQl(bdcQl);
        bdcQl=entityMapper.selectByPrimaryKey(BdcFdcqDO.class,id);
        Assert.assertEquals("123",bdcQl.getFj());

        bdcQl.setFj(null);
        bdcQllxService.updateObj(bdcQl,id);
        bdcQl=entityMapper.selectByPrimaryKey(BdcFdcqDO.class,id);
        Assert.assertNull(bdcQl.getFj());
    }

}