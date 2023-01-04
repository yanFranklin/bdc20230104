package cn.gtmap.realestate.init.service.other.impl;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.GzwQlrDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.building.DjDcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxQlrDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdDjdcbResponseDTO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.App;
import cn.gtmap.realestate.init.core.service.BdcQlrService;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/6
 * @description 权利人服务测试
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
public class BdcQlrServiceTest {
    @Autowired
    private BdcQlrService bdcQlrService;
    @Autowired
    DozerBeanMapper initDozerMapper;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/BdcQlrSetup-data.xml")
    public void delQlrTest(){
        //正确参数传入
        int delNum = bdcQlrService.deleteQlr("32",null);
        Assert.assertEquals(1,delNum);
        delNum = bdcQlrService.deleteQlr("323","1");
        Assert.assertEquals(1,delNum);
        //无效参数传入
        delNum = bdcQlrService.deleteQlr("","");
        Assert.assertEquals(0,delNum);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/BdcQlrSetup-data.xml")
    public void delQlrByQlrId(){
        //正确参数传入
        int delNum = bdcQlrService.deleteBdcQlrByQlrId("1234321");
        Assert.assertEquals(1,delNum);
        //无效参数传入
        delNum = bdcQlrService.deleteBdcQlrByQlrId("");
        Assert.assertEquals(0,delNum);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/BdcQlrSetup-data.xml")
    public void inheritYxmBdcQlrTest(){
        //无效参数传入
        List<BdcQlrDO> bdcQlrDOList = bdcQlrService.inheritYxmBdcQlr("",null,null);
        Assert.assertEquals(0,bdcQlrDOList.size());
        //正确参数传入
        //继承为权利人
        List<BdcQlrDO> bdcQlrDOList1 = bdcQlrService.inheritYxmBdcQlr("999","666","1");
        Assert.assertEquals("1",bdcQlrDOList1.get(0).getQlrlb());
        //继承为义务人
        List<BdcQlrDO> bdcQlrDOList2 = bdcQlrService.inheritYxmBdcQlr("999","666","2");
        Assert.assertEquals("2",bdcQlrDOList2.get(0).getQlrlb());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/BdcQlrSetup-data.xml")
    public void inheritYxmBdcQlrTestForHb(){
        //无效参数传入
        List<BdcQlrDO> bdcQlrDOList = bdcQlrService.inheritYxmBdcQlr(new ArrayList<>(),null,null);
        Assert.assertEquals(0,bdcQlrDOList.size());
        //正确参数传入
        BdcQlrDO bdcQlrDO = new BdcQlrDO();
        bdcQlrDO.setXmid("7898");
        bdcQlrDO.setQlrlb("1");
        List<BdcQlrDO> ybdcQlrDOList = bdcQlrService.queryBdcQlrByQlrxx(bdcQlrDO,null);
        //继承为权利人
        List<BdcQlrDO> bdcQlrDOList1 = bdcQlrService.inheritYxmBdcQlr(ybdcQlrDOList,"666","1");
        Assert.assertEquals("1",bdcQlrDOList1.get(0).getQlrlb());
        //继承为义务人
        List<BdcQlrDO> bdcQlrDOList2 = bdcQlrService.inheritYxmBdcQlr(ybdcQlrDOList,"666","2");
        Assert.assertEquals("2",bdcQlrDOList2.get(0).getQlrlb());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/BdcQlrSetup-data.xml")
    public void inheritYxmBdcYwrTest(){
        //无效参数传入
        List<BdcQlrDO> bdcQlrDOList = bdcQlrService.inheritYxmBdcYwr("",null,null);
        Assert.assertEquals(0,bdcQlrDOList.size());
        //正确参数传入
        //继承为权利人
        List<BdcQlrDO> bdcQlrDOList1 = bdcQlrService.inheritYxmBdcYwr("999","666","1");
        Assert.assertEquals("1",bdcQlrDOList1.get(0).getQlrlb());
        //继承为义务人
        List<BdcQlrDO> bdcQlrDOList2 = bdcQlrService.inheritYxmBdcYwr("999","666","2");
        Assert.assertEquals("2",bdcQlrDOList2.get(0).getQlrlb());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/BdcQlrSetup-data.xml")
    public void inheritYxmBdcYwrTestForHb(){
        //无效参数传入
        List<BdcQlrDO> bdcQlrDOList = bdcQlrService.inheritYxmBdcQlr(new ArrayList<>(),null,null);
        Assert.assertEquals(0,bdcQlrDOList.size());
        //正确参数传入
        BdcQlrDO bdcQlrDO = new BdcQlrDO();
        bdcQlrDO.setXmid("7898");
        bdcQlrDO.setQlrlb("2");
        List<BdcQlrDO> ybdcQlrDOList = bdcQlrService.queryBdcQlrByQlrxx(bdcQlrDO,null);
        //继承为权利人
        List<BdcQlrDO> bdcQlrDOList1 = bdcQlrService.inheritYxmBdcYwr(ybdcQlrDOList,"666","1");
        Assert.assertEquals("1",bdcQlrDOList1.get(0).getQlrlb());
        //继承为义务人
        List<BdcQlrDO> bdcQlrDOList2 = bdcQlrService.inheritYxmBdcYwr(ybdcQlrDOList,"666","2");
        Assert.assertEquals("2",bdcQlrDOList2.get(0).getQlrlb());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/BdcQlrSetup-data.xml")
    public void inheritLpbQlr(){
        //无效参数传入
        List<BdcQlrDO> bdcQlrDOList = bdcQlrService.inheritLpbQlr(null,null,null,null);
        Assert.assertEquals(0,bdcQlrDOList.size());
        //正确参数传入
        List<FwFcqlrDO> fwFcqlrDOList = getFwFcQlrList();
        DjxxResponseDTO djxxResponseDTO = getDjxxResponseDTO();
        List<GzwQlrDO> gzwQlrDOList = getGzwQlrList();

        List<Object> qlrList = new ArrayList<>();
        qlrList.add(fwFcqlrDOList);
        //继承为权利人
        List<BdcQlrDO> bdcQlrDOList1 = bdcQlrService.inheritLpbQlr(qlrList,"666","320508042080GB10223F12300000","1");
        Assert.assertEquals("1",bdcQlrDOList1.get(0).getQlrlb());
        //继承为义务人
        List<BdcQlrDO> bdcQlrDOList2 = bdcQlrService.inheritLpbQlr(qlrList,"666","320508042080GB10223W00000000","2");
        Assert.assertEquals("2",bdcQlrDOList2.get(0).getQlrlb());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/BdcQlrSetup-data.xml")
    public void queryBdcQlrTest(){
        BdcQlrDO bdcQlrDO = new BdcQlrDO();
        bdcQlrDO.setXmid("999");
        List<BdcQlrDO> bdcQlrDOList = bdcQlrService.queryBdcQlrByQlrxx(bdcQlrDO,null);
        Assert.assertEquals(2,bdcQlrDOList.size());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/BdcQlrSetup-data.xml")
    public void insertBdcQlrTest(){
        BdcQlrDO bdcQlrDO = new BdcQlrDO();
        bdcQlrDO.setQlrid(UUIDGenerator.generate16());
        bdcQlrDO.setXmid("werwerewrtrasa");
        bdcQlrDO.setQlrlb("1");
        bdcQlrDO.setQlrmc("被怀疑");
        bdcQlrService.insertBdcQlr(bdcQlrDO);
        List<BdcQlrDO> bdcQlrDOList = bdcQlrService.queryBdcQlrByQlrxx(bdcQlrDO,null);
        Assert.assertEquals(1,bdcQlrDOList.size());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/BdcQlrSetup-data.xml")
    public void updateBdcQlrTest(){
        BdcQlrDO bdcQlrDO = new BdcQlrDO();
        bdcQlrDO.setQlrid("12uou21");
        bdcQlrDO.setQlrmc("update");
        int updateNum = bdcQlrService.updateBdcQlr(bdcQlrDO);
        Assert.assertEquals(1,updateNum);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/BdcQlrSetup-data.xml")
    public void listBdcQlrOrderBySxhTset(){
        List<BdcQlrDO> bdcQlrDOList = bdcQlrService.listBdcQlrOrderBySxh("0o0o0","1");
        Assert.assertEquals("order1",bdcQlrDOList.get(0).getQlrmc());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/BdcQlrSetup-data.xml")
    public void turnQlrAccordQlrLb(){
        BdcQlrDO bdcQlrDO = new BdcQlrDO();
        BdcQlrDO bdcQlrDOTmp = new BdcQlrDO();
        bdcQlrDOTmp.setXmid("12321");
        List<BdcQlrDO> ybdcQlrDOList = bdcQlrService.queryBdcQlrByQlrxx(bdcQlrDOTmp,null);
        BdcQlrDO ybdcQlrDO = ybdcQlrDOList.get(0);
        if(ybdcQlrDO!=null){
            bdcQlrDO.setXmid("998");
            bdcQlrDO.setQlrlb("2");
            bdcQlrDO.setQlrid(UUIDGenerator.generate16());
            //读取xml配置文件映射对应的信息
            initDozerMapper.map(ybdcQlrDO,bdcQlrDO);
            Assert.assertEquals("dozer测试",bdcQlrDO.getQlrmc());
        }
    }

    private List<FwFcqlrDO> getFwFcQlrList(){
        FwFcqlrDO fwFcqlrDO = new FwFcqlrDO();
        fwFcqlrDO.setQlr("房产权利人1");
        FwFcqlrDO fwFcqlrDO2 = new FwFcqlrDO();
        fwFcqlrDO2.setQlr("房产权利人2");
        List<FwFcqlrDO> fwFcqlrDOList = new ArrayList<>();
        fwFcqlrDOList.add(fwFcqlrDO);
        fwFcqlrDOList.add(fwFcqlrDO2);
        return fwFcqlrDOList;
    }

    private List<GzwQlrDO> getGzwQlrList(){
        GzwQlrDO gzwQlrDO = new GzwQlrDO();
        gzwQlrDO.setQlr("构筑物权利人1");
        GzwQlrDO gzwQlrDO2 = new GzwQlrDO();
        gzwQlrDO2.setQlr("构筑物权利人2");
        List<GzwQlrDO> gzwQlrDOList = new ArrayList<>();
        gzwQlrDOList.add(gzwQlrDO);
        gzwQlrDOList.add(gzwQlrDO2);
        return gzwQlrDOList;
    }

    private DjxxResponseDTO getDjxxResponseDTO(){
        DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();
        djxxResponseDTO.setLmsuqrList(null);
        djxxResponseDTO.setLmsuqrList(null);
        DjxxQlrDTO djxxQlrDTO = new DjxxQlrDTO();
        djxxQlrDTO.setQlrmc("土地权利人");
        List<DjxxQlrDTO> tdqlrList = new ArrayList<>();
        tdqlrList.add(djxxQlrDTO);
        djxxResponseDTO.setQlrList(tdqlrList);
        DjDcbResponseDTO djDcbResponseDTO =  new ZdDjdcbResponseDTO();
        djxxResponseDTO.setDjDcbResponseDTO(djDcbResponseDTO);
        return djxxResponseDTO;
    }
}

