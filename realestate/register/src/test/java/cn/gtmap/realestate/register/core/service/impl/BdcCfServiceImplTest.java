package cn.gtmap.realestate.register.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.qo.register.BdcZxQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.register.App;
import cn.gtmap.realestate.register.core.dto.BdcQlNumDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * BdcCfServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11/13/2018</pre>
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
public class BdcCfServiceImplTest {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcCfServiceImpl bdcCfService;

    /**
     * Method: queryListBdcQl(String bdcdyh)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testQueryListBdcQl() throws Exception {
        String bdcdyh = "320902004004GB00018F00010003";
        BdcQlNumDTO bdcQlNumDTO = new BdcQlNumDTO();
        List<BdcQl> bdcCfDOList = bdcCfService.queryListBdcQl(bdcdyh, null);
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(1);
        bdcQlNumDTO = bdcCfService.countBdcQl(bdcQlNumDTO, bdcdyh, qsztList);
        int count = bdcQlNumDTO.getCfNum();
        Assert.assertTrue(CollectionUtils.isNotEmpty(bdcCfDOList));
        for (BdcQl bdcQl : bdcCfDOList) {
            BdcCfDO bdcCfDO1 = (BdcCfDO) bdcQl;
            Assert.assertEquals(bdcdyh, bdcCfDO1.getBdcdyh());
        }
        Assert.assertTrue(count == CollectionUtils.size(bdcCfDOList));

        bdcCfDOList = bdcCfService.queryListBdcQl("", null);
        count = bdcCfService.countBdcQl(bdcQlNumDTO, "", qsztList).getCfNum();
        Assert.assertTrue(CollectionUtils.isEmpty(bdcCfDOList));
        Assert.assertTrue(count == CollectionUtils.size(bdcCfDOList));
    }

    /**
     * Method: updateBdcCfZxDbxx(BdcCfDO bdcCfDO, String zxdbr, Date zxdjsj)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateBdcCfZxDbxx() throws Exception {
        String zxdbr = "taozi";
        Date zxdjsj = new Date();
        BdcCfDO bdcCfDO = entityMapper.selectByPrimaryKey(BdcCfDO.class, "1");
        BdcZxQO bdcZxQO = new BdcZxQO();
        bdcZxQO.setZxdbr(zxdbr);
        bdcZxQO.setZxdjsj(zxdjsj);
        bdcCfService.updateZxDbxx(bdcCfDO, bdcZxQO);
        BdcCfDO bdcCfDOTemp = entityMapper.selectByPrimaryKey(BdcCfDO.class, bdcCfDO.getQlid());
        if (null != bdcCfDOTemp && StringUtils.isNotBlank(bdcCfDOTemp.getQlid())) {
            Assert.assertEquals(bdcCfDOTemp.getJfdbr(), zxdbr);
        }
    }


} 
