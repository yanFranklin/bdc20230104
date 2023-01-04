package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.certificate.CertificateApp;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlZsDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
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
 * BdcFzjlServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11/29/2018</pre>
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
public class BdcFzjlServiceImplTest {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcFzjlServiceImpl bdcFzjlService;

    @Test
    @Transactional
    @Rollback(true)
    public void testQueryBdcFzjl() throws Exception {
        // 分别持证
        String xmid = "xm123";
        String gzlslid = "123";

        // 批量流程
        String plgzlslid = "pl123";
        String plXmid = "plxm123";

        /**
         * 测试  BdcFzjlDTO queryFzjl(String xmid);
         */

        // 分别持证（两本证）
        BdcFzjlDTO bdcFzjlDTO = bdcFzjlService.queryFzjl(xmid, null);
        Assert.assertTrue(null != bdcFzjlDTO);
        Assert.assertTrue(CollectionUtils.size(bdcFzjlDTO.getBdcFzjlZsDTOList()) == 2);
        // 验证sqr
        String sqr = bdcFzjlDTO.getSqr();
        Assert.assertEquals("桃子1 桃子2 ", sqr);


        // 单流程发证记录,一本证
        bdcFzjlDTO = bdcFzjlService.queryFzjl(plXmid, null);
        Assert.assertTrue(null != bdcFzjlDTO);
        Assert.assertTrue(CollectionUtils.size(bdcFzjlDTO.getBdcFzjlZsDTOList()) == 1);
        // 验证sqr
        sqr = bdcFzjlDTO.getSqr();
        Assert.assertEquals("桃子1 桃子2 ", sqr);


        /**
         * 测试  List<BdcFzjlDTO> queryListBdcFzjl(String gzlslid, boolean sfhb);
         */
        // 批量合并
        List<BdcFzjlDTO> bdcFzjlDTOList = bdcFzjlService.queryListBdcFzjl(plgzlslid, true);
        Assert.assertTrue(CollectionUtils.isNotEmpty(bdcFzjlDTOList));
        Assert.assertTrue(CollectionUtils.size(bdcFzjlDTOList) == 1);

        // 批量不合并显示
        bdcFzjlDTOList = bdcFzjlService.queryListBdcFzjl(plgzlslid, false);
        Assert.assertTrue(CollectionUtils.isNotEmpty(bdcFzjlDTOList));
        Assert.assertTrue(CollectionUtils.size(bdcFzjlDTOList) == 2);

        // 分别持证，不合并显示
        bdcFzjlDTOList = bdcFzjlService.queryListBdcFzjl(gzlslid, false);
        Assert.assertTrue(CollectionUtils.isNotEmpty(bdcFzjlDTOList));
        Assert.assertTrue(CollectionUtils.size(bdcFzjlDTOList) == 1);
        Assert.assertTrue(CollectionUtils.isNotEmpty(bdcFzjlDTOList.get(0).getBdcFzjlZsDTOList()));
        Assert.assertTrue(CollectionUtils.size(bdcFzjlDTOList.get(0).getBdcFzjlZsDTOList()) == 2);

    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateFzjl() throws Exception {
        String lzr = "我是领证人";
        String lzrzjh = "领证人证件号";
        // 分别持证
        String xmid = "xm123";
        // 批量流程
        String plgzlslid = "pl123";
        String plXmid = "plxm123";
        // 备注
        String bz = "测试备注";

        /**
         * 测试  BdcFzjlDTO queryFzjl(String xmid);
         */

        // 分别持证
        BdcFzjlDTO bdcFzjlDTO = bdcFzjlService.queryFzjl(xmid, null);

        List<BdcFzjlZsDTO> bdcFzjlZsDTOList = new ArrayList();
        for (BdcFzjlZsDTO bdcFzjlZsDTO : bdcFzjlDTO.getBdcFzjlZsDTOList()) {
            bdcFzjlZsDTO.setLzr(lzr);
            bdcFzjlZsDTO.setLzrzjh(lzrzjh);
            bdcFzjlZsDTOList.add(bdcFzjlZsDTO);
        }
        int count = bdcFzjlService.updateFzjlLzr(bdcFzjlZsDTOList);
        Assert.assertEquals(2, count);
        bdcFzjlDTO = bdcFzjlService.queryFzjl(xmid, null);
        Assert.assertEquals(lzr, bdcFzjlDTO.getBdcFzjlZsDTOList().get(0).getLzr());
        Assert.assertEquals(lzr, bdcFzjlDTO.getBdcFzjlZsDTOList().get(1).getLzr());
        count = bdcFzjlService.updateFzjlBz(xmid, true, bz);
        Assert.assertEquals(1, count);
        count = bdcFzjlService.updateFzjlBz(xmid, false, bz);
        Assert.assertEquals(1, count);

        // 批量流程更新
        bdcFzjlDTO = bdcFzjlService.queryFzjl(plXmid, null);
        bdcFzjlZsDTOList = new ArrayList();
        for (BdcFzjlZsDTO bdcFzjlZsDTO : bdcFzjlDTO.getBdcFzjlZsDTOList()) {
            bdcFzjlZsDTO.setLzr(lzr);
            bdcFzjlZsDTO.setLzrzjh(lzrzjh);
            bdcFzjlZsDTOList.add(bdcFzjlZsDTO);
        }
        count = bdcFzjlService.updateFzjlLzr(bdcFzjlZsDTOList);
        Assert.assertEquals(1, count);
        bdcFzjlDTO = bdcFzjlService.queryFzjl(plXmid, null);
        Assert.assertEquals(lzrzjh, bdcFzjlDTO.getBdcFzjlZsDTOList().get(0).getLzrzjh());
        count = bdcFzjlService.updateFzjlBz(plXmid, false, bz);
        Assert.assertEquals(1, count);
        count = bdcFzjlService.updateFzjlBz(plXmid, true, bz);
        Assert.assertEquals(2, count);

        List<BdcFzjlDTO> bdcFzjlDTOList = bdcFzjlService.queryListBdcFzjl(plgzlslid, true);
        bdcFzjlZsDTOList = new ArrayList();
        for (BdcFzjlZsDTO bdcFzjlZsDTO : bdcFzjlDTOList.get(0).getBdcFzjlZsDTOList()) {
            bdcFzjlZsDTO.setLzr(lzr);
            bdcFzjlZsDTO.setLzrzjh(lzrzjh);
            bdcFzjlZsDTOList.add(bdcFzjlZsDTO);
        }
        count = bdcFzjlService.updateFzjlLzr(bdcFzjlZsDTOList);
        Assert.assertEquals(2, count);
        count = bdcFzjlService.updateFzjlBz(bdcFzjlDTOList.get(0).getXmid(), true, bz);
        Assert.assertEquals(2, count);

    }


    @Test
    @Transactional
    @Rollback(true)
    public void testupdateFzr() throws Exception {
        String gzlslid = "pl123";

        String fzr = "我是发证人";
        String fzrid = "fzrid";
        UserDto userDto = new UserDto();
        userDto.setId(fzrid);
        userDto.setUsername(fzr);

        int count = 0;
        try {
            count = bdcFzjlService.updateFzr("", userDto, false);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof MissingArgumentException);
        }
        count = bdcFzjlService.updateFzr(gzlslid, userDto, false);
        Assert.assertTrue(count == 2);
    }

}
