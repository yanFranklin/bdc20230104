package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.register.BdcQlDjMlDTO;
import cn.gtmap.realestate.register.App;
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

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/21
 * @description 不动产登记簿信息业务服务逻辑实现测试类
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
public class BdcDjbxxServiceImplTest {
    @Autowired
    private BdcDjbxxServiceImpl bdcDjbxxService;


    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取不动产登记目录测试
     */
    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdjb-setupData.xml")
    public void testListBdcQlDjMl() throws Exception {
        List<BdcQlDjMlDTO> bdcQlDjMlDTOList = bdcDjbxxService.listBdcQlDjMl("320902004004GB00018");

        Assert.assertEquals(2, bdcQlDjMlDTOList.size());
        Assert.assertEquals("土地和房屋", bdcQlDjMlDTOList.get(1).getBdclx());
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取不动产登记簿信息测试
     */
    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdjb-setupData.xml")
    public void testQueryBdcBdcdjb() throws Exception {
        BdcBdcdjbDO bdcBdcdjb = bdcDjbxxService.queryBdcBdcdjb("320684111202GB00623");

        Assert.assertNotNull(bdcBdcdjb);
        Assert.assertEquals("登记机构1", bdcBdcdjb.getDjjg());
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取不动产宗地基本信息测试
     */
    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdjb-setupData.xml")
    public void testQueryBdcBdcdjbZdjbxx() throws Exception {
        BdcBdcdjbZdjbxxDO zdjbxx = bdcDjbxxService.queryBdcBdcdjbZdjbxx("320802003009GB00002W00000351");

        Assert.assertNotNull(zdjbxx);
        Assert.assertEquals("南京市玄武区", zdjbxx.getZl());
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产宗地变化情况测试
     */
    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdjb-setupData.xml")
    public void testListBdcBdcdjbZdjbxxZdbhqk() throws Exception {
        List<BdcBdcdjbZdjbxxZdbhqkDO> zdjbxxZdbhqkDOList = bdcDjbxxService.listBdcBdcdjbZdjbxxZdbhqk("320684111202GB00623");

        Assert.assertEquals(2, zdjbxxZdbhqkDOList.size());
        Assert.assertEquals("变化2", zdjbxxZdbhqkDOList.get(1).getBhnr());
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  查询宗海基本信息测试
     */
    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdjb-setupData.xml")
    public void testQueryBdcBdcdjbZhjbxx() throws Exception {
        BdcBdcdjbZhjbxxDO zhjbxx = bdcDjbxxService.queryBdcBdcdjbZhjbxx("320802001010GH00111W00000333");

        Assert.assertNotNull(zhjbxx);
        Assert.assertEquals("320802001010GH00222", zhjbxx.getZhdm());
        Assert.assertEquals("海岛名称", zhjbxx.getHdmc());
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  查询宗海基本信息用海状况测试
     */
    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdjb-setupData.xml")
    public void testListBdcBdcdjbZhjbxxYhzk() throws Exception {
        List<BdcBdcdjbZhjbxxYhzkDO> zhjbxxYhzkList = bdcDjbxxService.listBdcBdcdjbZhjbxxYhzk("320802001010GH00111W00000333");

        Assert.assertEquals(2, zhjbxxYhzkList.size());
        Assert.assertEquals("渔业", zhjbxxYhzkList.get(0).getJtyt());
        Assert.assertEquals(Double.valueOf(4534.2232), zhjbxxYhzkList.get(1).getSyjse());
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  查询宗海基本信息用海用岛坐标测试
     */
    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdjb-setupData.xml")
    public void testListBdcBdcdjbZhjbxxYhydzb() throws Exception {
        List<BdcBdcdjbZhjbxxYhydzbDO> zhjbxxYhydzbDOList = bdcDjbxxService.listBdcBdcdjbZhjbxxYhydzb("320802001010GH00111W00000333");

        Assert.assertEquals(4, zhjbxxYhydzbDOList.size());
        Assert.assertEquals(Double.valueOf(34234.234432), zhjbxxYhydzbDOList.get(1).getBw());
        Assert.assertEquals(Double.valueOf(45645.232), zhjbxxYhydzbDOList.get(3).getDj());
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  查询不动产登记簿宗海基本信息中的宗海变化情况测试
     */
    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcdjb-setupData.xml")
    public void testListBdcBdcdjbZhjbxxZhbhqk() throws Exception {
        List<BdcBdcdjbZhjbxxZhbhqkDO> zhjbxxZhbhqkDOList = bdcDjbxxService.listBdcBdcdjbZhjbxxZhbhqk("320802001010GH00111W00000333");

        Assert.assertEquals(2, zhjbxxZhbhqkDOList.size());
        Assert.assertEquals("面积扩大", zhjbxxZhbhqkDOList.get(0).getBhnr());
    }
}