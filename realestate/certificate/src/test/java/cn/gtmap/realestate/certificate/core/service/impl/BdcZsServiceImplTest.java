package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.certificate.CertificateApp;
import cn.gtmap.realestate.certificate.BaseUnitTest;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlZsDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.UncategorizedSQLException;
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

import static org.junit.Assert.*;

/**
 * BdcZsServiceImpl Tester.
 *
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
 * @version 1.0 11/13/2018
 * @description
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
public class BdcZsServiceImplTest extends BaseUnitTest {

    @Autowired
    EntityMapper entityMapper;
    @Autowired
    private BdcZsService bdcZsService;

    /**
     * Method: queryBdcZs(String zsid)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testQueryBdcZs() throws Exception {
        //测试参数传空值
        BdcZsDO bdcZsDO = bdcZsService.queryBdcZs("");
        assertNull(bdcZsDO);

        //测试参数传证书表已有的证书ID
        BdcZsDO bdcZsDO1 = bdcZsService.queryBdcZs("zsid1");
        assertNotNull(bdcZsDO1);

        //测试参数传虚拟证号
        BdcZsDO bdcZsDO2 = bdcZsService.queryBdcZs("xxxxxx");
        //测试报错assertNotNull(bdcZsDO2);
        //测试通过
        assertNull(bdcZsDO2);

    }

    /**
     * Method: queryBdcZsByXmid(String xmid)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testQueryBdcZsByXmid() throws Exception {
        List<BdcZsDO> bdcZsList = null;
        //测试参数传空值
        bdcZsList = bdcZsService.queryBdcZsByXmid("");
        assertTrue(CollectionUtils.isEmpty(bdcZsList));

        //测试参数传已生成证书的xmid
        bdcZsList = bdcZsService.queryBdcZsByXmid("xm123");
        assertTrue(CollectionUtils.isNotEmpty(bdcZsList));

        //测试参数传虚拟不动产单元
        bdcZsList = bdcZsService.queryBdcZsByXmid("XXXXX");
        //测试报错assertTrue(CollectionUtils.isNotEmpty(bdcZsList));
        //测试通过
        assertTrue(CollectionUtils.isEmpty(bdcZsList));
    }

    /**
     * Method: listBdcZs(BdcZsQO bdcZsQO)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testListBdcZs() throws Exception {
        BdcZsQO bdcZsQO = new BdcZsQO();
        List<BdcZsDO> bdcZsList = null;

        bdcZsQO.setBdcdyh("320902004004GB00018F00010003");
        bdcZsList = bdcZsService.listBdcZs(bdcZsQO);
        assertTrue(CollectionUtils.isNotEmpty(bdcZsList));

        bdcZsQO.setBdcqzh("test1证书1");
        bdcZsList = bdcZsService.listBdcZs(bdcZsQO);
        assertTrue(CollectionUtils.isNotEmpty(bdcZsList) && bdcZsList.size() == 1);

        bdcZsQO.setZl("12345");
        bdcZsList = bdcZsService.listBdcZs(bdcZsQO);
        assertTrue(CollectionUtils.isEmpty(bdcZsList));
    }

    /**
     * Method: updateBdcZs(BdcZsDO bdcZsDO)
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateBdcZs() throws Exception {
        BdcZsDO bdcZsDO = new BdcZsDO();
        bdcZsDO.setQlqtzk("123456789abcdefg");
        try {
            bdcZsService.updateBdcZs(bdcZsDO);
        } catch (Exception e) {
            e.getMessage();
            assertTrue(e instanceof NullPointerException);
        }

        bdcZsDO.setZsid("zsid1");
        bdcZsService.updateBdcZs(bdcZsDO);

        BdcZsDO bdcZsDO1 = bdcZsService.queryBdcZs("zsid1");
        assertEquals("123456789abcdefg", bdcZsDO1.getQlqtzk());

        try {

            bdcZsDO.setZsid("xxxxxx");
            bdcZsService.updateBdcZs(bdcZsDO);
        } catch (Exception e) {
            assertTrue(e instanceof MyBatisSystemException);
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateLzr() throws Exception {
        List<String> list = new ArrayList();
        list.add("zsid1");
        list.add("zsid2");

        BdcFzjlZsDTO bdcFzjlZsDTO = new BdcFzjlZsDTO();
        bdcFzjlZsDTO.setLzr("我是领证人");
        bdcFzjlZsDTO.setZsidList(list);

        int count = bdcZsService.updateLzr(bdcFzjlZsDTO);
        Assert.assertEquals(2, count);

        count = bdcZsService.updateLzr(new BdcFzjlZsDTO());
        Assert.assertEquals(0, count);

        BdcZsDO bdcZsDO = entityMapper.selectByPrimaryKey(BdcZsDO.class, "zsid1");
        Assert.assertEquals("我是领证人", bdcZsDO.getLzr());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateFzr() throws Exception {
        List<String> list = new ArrayList();
        list.add("zsid1");
        list.add("zsid2");

        String fzr = "我是发证人";
        String fzrid = "fzrid";
        String alias = "发证人别名";

        UserDto userDto = null;
        int cout;
        try {
            cout = bdcZsService.updateFzr(list, userDto, false);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof MissingArgumentException);
        }

        userDto = new UserDto();
        userDto.setUsername(fzr);
        userDto.setAlias(alias);
        userDto.setId(fzrid);

        cout = bdcZsService.updateFzr(list, userDto, false);
        Assert.assertTrue(cout == 2);

        BdcZsDO bdcZsDO = entityMapper.selectByPrimaryKey(BdcZsDO.class, "zsid2");
        Assert.assertEquals(alias, bdcZsDO.getFzr());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateSzr() throws Exception {
        List<String> list = new ArrayList();
        list.add("zsid1");
        list.add("zsid2");

        String szr = "我是发证人";
        String szrid = "fzrid";
        String alias = "发证人别名";

        UserDto userDto = null;
        int cout;
        try {
            cout = bdcZsService.updateFzr(list, userDto, false);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof MissingArgumentException);
        }

        userDto = new UserDto();
        userDto.setUsername(szr);
        userDto.setAlias(alias);
        userDto.setId(szrid);

        cout = bdcZsService.updateSzr(list, userDto).getExcuteNum();
        Assert.assertTrue(cout == 2);

        BdcZsDO bdcZsDO = entityMapper.selectByPrimaryKey(BdcZsDO.class, "zsid2");
        Assert.assertEquals(alias, bdcZsDO.getSzr());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCountBdcZs() throws Exception {
        String xmid = "xm123";
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setXmid(xmid);

        int count = bdcZsService.countBdcZs(bdcZsQO);
        Assert.assertEquals(2, count);

        try {
            count = bdcZsService.countBdcZs(null);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof MissingArgumentException);
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testQueryZsXmByZsid() throws Exception {
        String zsid = "zsid1";
        List<BdcXmDO> bdcXmDOList = bdcZsService.queryZsXmByZsid(zsid);
        Assert.assertTrue(CollectionUtils.size(bdcXmDOList) == 1);

        try {
            bdcXmDOList = bdcZsService.queryZsXmByZsid("");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof UncategorizedSQLException);
        }
    }
} 
