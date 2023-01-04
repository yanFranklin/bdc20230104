package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.InterfaceCodeBeanFactory;
import cn.gtmap.realestate.register.App;
import cn.gtmap.realestate.register.core.service.BdcXzQlService;
import cn.gtmap.realestate.register.core.service.impl.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

/**
 * BdcQlxxServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11/29/2018</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class BdcQlxxServiceImplTest {
    @Autowired
    BdcQlxxServiceImpl bdcQlxxService;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    Set<BdcXzQlService> bdcXzQlServiceSet;
    @Autowired
    BdcDyaqServiceImpl bdcDyaqService;
    @Autowired
    BdcYyServiceImpl bdcYyService;
    @Autowired
    BdcYgServiceImpl bdcYgService;
    @Autowired
    BdcDyiqServiceImpl bdcDyiqService;
    @Autowired
    BdcFwzlServiceImpl bdcFwzlService;
    @Autowired
    BdcCfServiceImpl bdcCfService;


    /**
     * Method: updateBdcQlZxDbxx(BdcQl bdcQl, String zxdbr, Date zxdjsj)
     */
    @Test
    public void testUpdateBdcQlZxDbxx() throws Exception {
        // 抵押
        BdcQl bdcQl = new BdcDyaqDO();
        String className = String.valueOf(bdcQl.getClass().getSimpleName());
        BdcXzQlService bdcXzQlService = InterfaceCodeBeanFactory.getBean(bdcXzQlServiceSet, className);
        if (null != bdcXzQlService) {
            Assert.assertEquals(bdcXzQlService, bdcDyaqService);
        }
        // 地役
        bdcQl = new BdcDyiqDO();
        className = String.valueOf(bdcQl.getClass().getSimpleName());
        bdcXzQlService = InterfaceCodeBeanFactory.getBean(bdcXzQlServiceSet, className);
        if (null != bdcXzQlService) {
            Assert.assertEquals(bdcXzQlService, bdcDyiqService);
        }
        // 异议
        bdcQl = new BdcYyDO();
        className = String.valueOf(bdcQl.getClass().getSimpleName());
        bdcXzQlService = InterfaceCodeBeanFactory.getBean(bdcXzQlServiceSet, className);
        if (null != bdcXzQlService) {
            Assert.assertEquals(bdcXzQlService, bdcYyService);
        }

        bdcQl = new BdcYgDO();
        className = String.valueOf(bdcQl.getClass().getSimpleName());
        bdcXzQlService = InterfaceCodeBeanFactory.getBean(bdcXzQlServiceSet, className);
        if (null != bdcXzQlService) {
            Assert.assertEquals(bdcXzQlService, bdcYgService);
        }
        bdcQl = new BdcCfDO();
        className = String.valueOf(bdcQl.getClass().getSimpleName());
        bdcXzQlService = InterfaceCodeBeanFactory.getBean(bdcXzQlServiceSet, className);
        if (null != bdcXzQlService) {
            Assert.assertEquals(bdcXzQlService, bdcCfService);
        }

        bdcQl = new BdcFwzlDO();
        className = String.valueOf(bdcQl.getClass().getSimpleName());
        bdcXzQlService = InterfaceCodeBeanFactory.getBean(bdcXzQlServiceSet, className);
        if (null != bdcXzQlService) {
            Assert.assertEquals(bdcXzQlService, bdcFwzlService);
        }
    }
}



