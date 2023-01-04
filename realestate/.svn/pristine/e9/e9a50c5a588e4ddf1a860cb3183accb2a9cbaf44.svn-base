package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.mapper.BdcSlXmMapper;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import com.alibaba.fastjson.JSON;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.core.service.BdcSlXmService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.util.UUIDGenerator;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/30
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(classes = App.class)
public class BdcSlXmServiceImplExtTest {
    @Autowired
    private BdcSlXmService bdcSlXmService;
    @Autowired
    private BdcSlXmMapper bdcSlXmMapper;


    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXm-setupData.xml")
    public void queryBdcSlXmByXmid() {
        BdcSlXmDO bdcSlXmDO = bdcSlXmService.queryBdcSlXmByXmid("6dc1f87d53463aeda2e747e6");
        Assert.assertNotNull(bdcSlXmDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXm-setupData.xml")
    public void listBdcSlXmByJbxxid() {
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid("20180001","");
        Assert.assertNotNull(bdcSlXmDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXm-setupData.xml")
    public void insertBdcSlXm() {
        BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
        bdcSlXmDO.setXmid(UUIDGenerator.generate16());
        bdcSlXmDO.setJbxxid(UUIDGenerator.generate16());
        bdcSlXmDO.setBdcdyh("234234");
        BdcSlXmDO bdcSlXmDO1 = bdcSlXmService.insertBdcSlXm(bdcSlXmDO);
        Assert.assertNotNull(bdcSlXmDO1);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXm-setupData.xml")
    public void updateBdcSlXm() {
        BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
        bdcSlXmDO.setXmid("6dc1f87d53463aeda2e747e6");
        bdcSlXmDO.setJbxxid(UUIDGenerator.generate16());
        Integer result = bdcSlXmService.updateBdcSlXm(bdcSlXmDO);
        Assert.assertEquals(result.toString(), "1");
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXm-setupData.xml")
    public void deleteBdcSlXmByXmid() {
        Integer result = bdcSlXmService.deleteBdcSlXmByXmid("6dc1f87d53463aeda2e747e6");
        Assert.assertEquals(result.toString(), "1");
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXm-setupData.xml")
    public void deleteBdcSlXmByJbxxid() {
        Integer result = bdcSlXmService.deleteBdcSlXmByJbxxid("0313");
        Assert.assertEquals(result.toString(), "3");
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXm-setupData.xml")
    public void listBdcSLXmDTOByJbxxid() {
        List<BdcSlYwxxDTO> bdcSlYwxxDTOList= bdcSlXmService.listBdcSLXmDTOByJbxxid("0313");
        Assert.assertNotNull(bdcSlYwxxDTOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXm-setupData.xml")
    public void listBdcSLXmDTOByPage() {
        Pageable pageable =new PageRequest(0, 10);
        Map map =new HashMap();
        map.put("jbxxid","0313");
        Page<BdcSlYwxxDTO> bdcSlYwxxDTOPage= bdcSlXmService.listBdcSLXmDTOByPage(pageable,map);
        Assert.assertNotNull(bdcSlYwxxDTOPage.getContent());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXm-setupData.xml")
    public void updateBdcSlXmList() {
        List<BdcSlXmDO> bdcSlXmDOList =new ArrayList<>();
        BdcSlXmDO bdcSlXmDO =new BdcSlXmDO();
        bdcSlXmDO.setXmid("6dc1f87d53463aeda2e747e6");
        bdcSlXmDO.setBdcdyh("222222");
        bdcSlXmDOList.add(bdcSlXmDO);
        bdcSlXmDO =new BdcSlXmDO();
        bdcSlXmDO.setXmid("6dc1f87d53463aeda2e747s6");
        bdcSlXmDO.setBdcdyh("");
        bdcSlXmDOList.add(bdcSlXmDO);
        String json = JSON.toJSONString(bdcSlXmDOList);
        Integer result= bdcSlXmService.updateBdcSlXmList(json);
        Assert.assertEquals(result.toString(), "2");

    }


    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlXm-setupData.xml")
    public void deleteYxFwhs() {
        Integer result = bdcSlXmService.deleteYxFwhs("108466,229646","0313");
        Assert.assertEquals(result.toString(), "2");
        List<String> fwhsIndexList =new ArrayList<>();
        fwhsIndexList.add("123");
        result =bdcSlXmService.deleteYxFwhs(fwhsIndexList,"0313");
        Assert.assertEquals(result.toString(), "1");
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlSfxm-setupData.xml")
    public void updateSlxm() {
        Map map =new HashMap();
        map.put("jbxxid","6dc1f87d53463aeda2e74736");
        map.put("sfxxid","test");
        Integer result =bdcSlXmMapper.updateSlxmSfxxPl(map);

    }
}