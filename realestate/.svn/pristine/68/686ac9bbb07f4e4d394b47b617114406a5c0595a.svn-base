package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.CertificateApp;
import cn.gtmap.realestate.certificate.core.mapper.BdcYzhMapper;
import cn.gtmap.realestate.certificate.core.service.BdcYzhSymxService;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhsymxDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhSyqkQO;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;

/**
 * BdcYzhServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>12/04/2018</pre>
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
@DatabaseSetup(value = "/data/bdcYzh-setupData.xml", type = DatabaseOperation.CLEAN_INSERT)
public class bdcYzhServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(bdcYzhServiceImplTest.class);


    @Autowired
    BdcYzhServiceImpl bdcYzhService;
    @Autowired
    BdcYzhMapper bdcYzhMapper;
    @Autowired
    MessageProvider messageProvider;
    @Autowired
    BdcYzhScServiceImpl bdcYzhScService;
    @Autowired
    BdcYzhSymxService bdcYzhSymxService;

    /**
     * Method: queryBdcYzh(String yzhid)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testQueryBdcYzh() throws Exception {
        String yzhid = "yzhid1";
        BdcYzhDTO bdcYzhDTO = bdcYzhService.queryBdcYzhAndYzhmx(yzhid);
        Assert.assertEquals(yzhid, bdcYzhDTO.getYzhid());
        for (BdcYzhsymxDO bdcYzhsymxDO : bdcYzhDTO.getBdcYzhsymxDOList()) {
            Assert.assertEquals(yzhid, bdcYzhsymxDO.getYzhid());
        }
    }

    /**
     * Method: queryBdcYzhsymx(String yzhid)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testQueryBdcYzhsymx() throws Exception {
        String yzhid = "yzhid1";
        List<BdcYzhsymxDO> bdcYzhsymxDOList = bdcYzhSymxService.queryBdcYzhsymx(yzhid);
        Assert.assertTrue(CollectionUtils.isNotEmpty(bdcYzhsymxDOList));
        for (BdcYzhsymxDO bdcYzhsymxDO : bdcYzhsymxDOList) {
            Assert.assertEquals(yzhid, bdcYzhsymxDO.getYzhid());
        }
    }

    /**
     * Method: queryListBdcYzh(BdcYzhQO bdcYzhQO)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testQueryListBdcYzh() throws Exception {
        BdcYzhQO bdcYzhQO = new BdcYzhQO();
        bdcYzhQO.setZslx(1);

        List<BdcYzhDTO> bdcYzhDTOList = bdcYzhService.queryListBdcYzh(bdcYzhQO);
        Assert.assertEquals((Integer) 1, bdcYzhDTOList.get(0).getZslx());

        // 测试默认排序是qzyslsh asc
        Assert.assertEquals("lsh1", bdcYzhDTOList.get(0).getQzysxlh());


        bdcYzhQO.setNf("2017");
        bdcYzhQO.setZslx(2);
        bdcYzhQO.setOrderField("qzysxlh");
        bdcYzhQO.setOrderType("DESC");
        bdcYzhDTOList = bdcYzhService.queryListBdcYzh(bdcYzhQO);
        Assert.assertEquals("lsh4", bdcYzhDTOList.get(0).getQzysxlh());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateBdcYzhSyqk() throws Exception {
        String yzhid = "yzhid1";
        BdcYzhSyqkQO bdcYzhSyqkQO = new BdcYzhSyqkQO();
        bdcYzhSyqkQO.setYzhid(yzhid);
        bdcYzhSyqkQO.setSyqk(2);
        bdcYzhSyqkQO.setSyr("我是使用人");
        bdcYzhSyqkQO.setSyrid("syrid");
        bdcYzhSyqkQO.setSyyy("测试使用");
        bdcYzhSyqkQO.setZsid("zsid");
        bdcYzhSyqkQO.setQzysxlh("lsh2");

        BdcYzhsymxDO bdcYzhsymxDO = bdcYzhService.updateBdcYzhSyqk(bdcYzhSyqkQO);
        Assert.assertEquals(yzhid, bdcYzhsymxDO.getYzhid());

        BdcYzhDTO bdcYzhDTO = bdcYzhService.queryBdcYzhAndYzhmx(yzhid);
        Assert.assertEquals(bdcYzhDTO.getSyqk(), (Integer) 2);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testQueryBdcZsYzh() throws Exception {
        String zsid = "zsid1";
        String qxdm = "340221";
        int zslx = 1;
        BdcYzhQO bdcYzhQO = new BdcYzhQO();
        bdcYzhQO.setZslx(zslx);
        bdcYzhQO.setZsid(zsid);
        bdcYzhQO.setSyqk(0);
        bdcYzhQO.setQxdm(qxdm);

        BdcYzhDTO bdcYzhDTO = bdcYzhService.queryBdcZsYzh(zsid, bdcYzhQO);
        Assert.assertEquals("lsh1", bdcYzhDTO.getQzysxlh());

        BdcYzhDTO bdcYzhDTO1 = bdcYzhService.queryBdcYzhAndYzhmx(bdcYzhDTO.getYzhid());
        Assert.assertEquals(zsid, bdcYzhDTO1.getZsid());

        try {
            bdcYzhDTO = bdcYzhService.queryBdcZsYzh(zsid, null);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof MissingArgumentException);
        }

        try {
            bdcYzhDTO = bdcYzhService.queryBdcZsYzh("", bdcYzhQO);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof MissingArgumentException);
        }

    }

    @Test
    @Transactional
    @Rollback(true)
    public void testQueryBatchZsYzh() throws Exception {
        List<String> zsidList = new ArrayList();
        zsidList.add("zsid1");
        zsidList.add("zsid2");
        List<String> zmidList = new ArrayList();
        zmidList.add("zmid3");
        zmidList.add("zmid4");

        List<BdcYzhQO> bdcYzhQOList = new ArrayList();
        try {
            List<BdcYzhDTO> bdcYzhDTOListTemp = bdcYzhService.queryBatchZsYzh(bdcYzhQOList);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof MissingArgumentException);
        }
        // 证书查询
        BdcYzhQO bdcYzhQO = new BdcYzhQO();
        bdcYzhQO.setZslx(1);
        bdcYzhQO.setSyqk(0);
        bdcYzhQO.setQxdm("340221");
        bdcYzhQO.setZsidList(zsidList);
        bdcYzhQOList.add(bdcYzhQO);
        // 证明查询
        BdcYzhQO bdcYzhQOTemp = new BdcYzhQO();
        bdcYzhQOTemp.setZslx(2);
        bdcYzhQOTemp.setSyqk(0);
        bdcYzhQOTemp.setQxdm("340221");
        bdcYzhQOTemp.setZsidList(zmidList);
        bdcYzhQOList.add(bdcYzhQOTemp);

        List<BdcYzhDTO> bdcYzhDTOList = bdcYzhService.queryBatchZsYzh(bdcYzhQOList);
        Assert.assertEquals(4, CollectionUtils.size(bdcYzhDTOList));

        for (BdcYzhDTO bdcYzhDTO : bdcYzhDTOList) {
            BdcYzhDO bdcYzhDO = bdcYzhService.queryBdcYzhAndYzhmx(bdcYzhDTO.getYzhid());
            Assert.assertNotNull(bdcYzhDO.getZsid());
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testGetMinBdcYzh() throws Exception {
        BdcYzhQO bdcYzhQO = new BdcYzhQO();
        BdcYzhDTO bdcYzhDTO = bdcYzhScService.getMinBdcYzh(bdcYzhQO);
        Assert.assertEquals(bdcYzhDTO.getQzysxlh(), "lsh1");

        bdcYzhQO.setQxdm("340221");
        bdcYzhDTO = bdcYzhScService.getMinBdcYzh(bdcYzhQO);
        Assert.assertEquals(bdcYzhDTO.getQzysxlh(), "lsh1");

        bdcYzhQO.setQxdm("340000");
        bdcYzhDTO = bdcYzhScService.getMinBdcYzh(bdcYzhQO);
        Assert.assertEquals(null, bdcYzhDTO);
    }
} 
