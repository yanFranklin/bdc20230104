package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.dto.register.BdcDjbQlDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import cn.gtmap.realestate.register.App;
import cn.gtmap.realestate.register.BaseUnitTest;
import cn.gtmap.realestate.register.config.PropsConfig;
import cn.gtmap.realestate.register.core.service.BdcQlService;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * BdcQlxxServiceImpl Tester.
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
@DatabaseSetup(value = "/data/bdcQl-setupData.xml", type = DatabaseOperation.CLEAN_INSERT)
public class BdcQlxxServiceImplDbUnitTest extends BaseUnitTest {
    @Autowired
    BdcQlxxServiceImpl bdcQlxxService;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    PropsConfig propsConfig;
    @Autowired
    BeansResolveUtils beansResolveUtils;

    @Test
    public void testQueryListBdcTdCq() throws Exception {
        String bdcdyh = "320902004004GB00018W00010003";
        List<BdcQl> list = bdcQlxxService.queryListBdcFfwCq("320902004004GB00018W00010003", null);
        LOGGER.info(list.toString());

        List<String> propsList = propsConfig.getFfwCqBeans();
        List<BdcQlService> bdcQlServiceList = beansResolveUtils.listBeans(propsList);
        if (CollectionUtils.isNotEmpty(bdcQlServiceList)) {
            for (BdcQlService bdcQlService : bdcQlServiceList) {
                Object object = bdcQlService.queryListBdcQl(bdcdyh, null);
                if (null != object && object instanceof ArrayList) {
                    LOGGER.info(object.toString());
                }
            }
        }

        list = bdcQlxxService.queryListBdcFfwCq("", null);
        Assert.assertTrue(CollectionUtils.isEmpty(list));
    }

    @Test
    public void testQueryListBdcFwCq() throws Exception {
        String bdcdyh = "320902004004GB00018F00010003";
        List<BdcQl> list = bdcQlxxService.queryListBdcFwCq(bdcdyh, null);
        LOGGER.info(list.toString());

        List<String> propsList = propsConfig.getFwCqBeans();
        List<BdcQlService> bdcQlServiceList = beansResolveUtils.listBeans(propsList);
        if (CollectionUtils.isNotEmpty(bdcQlServiceList)) {
            for (BdcQlService bdcQlService : bdcQlServiceList) {
                Object object = bdcQlService.queryListBdcQl(bdcdyh, null);
                if (null != object && object instanceof ArrayList) {
                    LOGGER.info(object.toString());
                }
            }
        }

        list = bdcQlxxService.queryListBdcFwCq("", null);
        Assert.assertTrue(CollectionUtils.isEmpty(list));
    }

    @Test
    public void testListBdcQlxx() throws Exception {
        String bdcdyh = "320902004004GB00018F00010003";
        for (int qllx = 1; qllx < 100; qllx++) {
            LOGGER.info("权利类型：" + Integer.toString(qllx));
            List<BdcQl> bdcQlList = bdcQlxxService.listBdcQlxx(bdcdyh, Integer.toString(qllx), null);
            LOGGER.info(bdcQlList.toString());
        }

        List<BdcQl> list = bdcQlxxService.listBdcQlxx("", null, null);
        Assert.assertTrue(CollectionUtils.isEmpty(list));
    }

    @Test
    public void testQueryBdcCqQllx() throws Exception {
        BdcDjbQlDTO bdcDjbQlDTO = new BdcDjbQlDTO();
        String bdcdyh = "320902004004GB00018F00010003";
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(1);
        Integer qllx = bdcQlxxService.queryBdcFwCqQllx(bdcDjbQlDTO, bdcdyh, qsztList).getFwQllx();
        LOGGER.info(bdcdyh + "的权利类型为：" + qllx);

        bdcdyh = "320902004004GB00018W00010003";
        qllx = bdcQlxxService.queryBdcQtCqQllx(bdcDjbQlDTO, bdcdyh, qsztList).getQtCqQllx();
        LOGGER.info(bdcdyh + "的权利类型为：" + qllx);

        bdcdyh = "320902004004JE00018L00010003";
        qllx = bdcQlxxService.queryBdcQtCqQllx(bdcDjbQlDTO, bdcdyh, qsztList).getQtCqQllx();
        LOGGER.info(bdcdyh + "的权利类型为：" + qllx);

        bdcdyh = "320902004004GH00018W00010003";
        qllx = bdcQlxxService.queryBdcQtCqQllx(bdcDjbQlDTO, bdcdyh, qsztList).getQtCqQllx();
        LOGGER.info(bdcdyh + "的权利类型为：" + qllx);
    }
}



