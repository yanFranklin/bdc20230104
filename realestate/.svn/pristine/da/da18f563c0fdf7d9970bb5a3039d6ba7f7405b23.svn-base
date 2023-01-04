package cn.gtmap.realestate.building.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.building.core.service.CalculatedAreaService;
import cn.gtmap.realestate.building.core.service.FwHsService;
import cn.gtmap.realestate.building.core.service.FwLjzService;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dto.building.FttdmjRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.FwlxBgRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.LjzJzmjRequestDTO;
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

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/5
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
public class CalculatedAreaServiceTest {

    @Autowired
    private CalculatedAreaService calculatedAreaService;
    @Autowired
    private FwLjzService fwLjzService;
    @Autowired
    private FwHsService fwHsService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/ljzjzmj-setupData.xml")
    public void calculatedLjzJzmj(){
        LjzJzmjRequestDTO ljzJzmjRequestDTO=new LjzJzmjRequestDTO();
        ljzJzmjRequestDTO.setZdh("1234567890987654321");
        ljzJzmjRequestDTO.setMjlb("sc");
        ljzJzmjRequestDTO.isDxs();
        ljzJzmjRequestDTO.isZhs();
        calculatedAreaService.calculatedLjzJzmj(ljzJzmjRequestDTO);
        FwLjzDO fwLjzDO=fwLjzService.queryLjzByFwDcbIndex("gtis");
        Assert.assertNotNull(fwLjzDO.getScjzmj());
    }
    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fttdmj-setupData.xml")
    public void calculatedFttdMj(){
        FttdmjRequestDTO fttdmjRequestDTO = new FttdmjRequestDTO();
        fttdmjRequestDTO.setZdh("1234567890987654321");
        fttdmjRequestDTO.setJsgsxh("4");
        fttdmjRequestDTO.setLcs(10);
        calculatedAreaService.calculatedFttdmj(fttdmjRequestDTO);
        FwHsDO fwHsDO=fwHsService.queryFwHsByIndex("gtmap");
        Assert.assertNotNull(fwHsDO.getFttdmj());
    }
    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fttdmj-setupData.xml")
    public void calculatedFttdMjFtxs(){
        FttdmjRequestDTO fttdmjRequestDTO = new FttdmjRequestDTO();
        fttdmjRequestDTO.setZdh("1234567890987654321");
        fttdmjRequestDTO.setJsgsxh("1");
        fttdmjRequestDTO.setFtxs(4.5);
        calculatedAreaService.calculatedFttdmj(fttdmjRequestDTO);
        FwHsDO fwHsDO=fwHsService.queryFwHsByIndex("gtmap");
        Assert.assertNotNull(fwHsDO.getFttdmj());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fttdmj-setupData.xml")
    public void calculatedFttdMjZd(){
        FttdmjRequestDTO fttdmjRequestDTO = new FttdmjRequestDTO();
        fttdmjRequestDTO.setZdh("1234567890987654321");
        fttdmjRequestDTO.setJsgsxh("2");
        fttdmjRequestDTO.setZdnlzzjzmj("ljz");
        fttdmjRequestDTO.setMjlb("sc");
        calculatedAreaService.calculatedFttdmj(fttdmjRequestDTO);
        FwHsDO fwHsDO=fwHsService.queryFwHsByIndex("gtmap");
        Assert.assertNotNull(fwHsDO.getFttdmj());

        FttdmjRequestDTO fttdmjRequestDTO2 = new FttdmjRequestDTO();
        fttdmjRequestDTO2.setZdh("1234567890987654321");
        fttdmjRequestDTO2.setJsgsxh("2");
        fttdmjRequestDTO2.setZdnlzzjzmj("ljz");
        fttdmjRequestDTO2.setMjlb("fz");
        calculatedAreaService.calculatedFttdmj(fttdmjRequestDTO2);
        FwHsDO fwHsDO2=fwHsService.queryFwHsByIndex("gtmap");
        Assert.assertNotNull(fwHsDO2.getFttdmj());

        FttdmjRequestDTO fttdmjRequestDTO3 = new FttdmjRequestDTO();
        fttdmjRequestDTO3.setZdh("1234567890987654321");
        fttdmjRequestDTO3.setJsgsxh("2");
        fttdmjRequestDTO3.setZdnlzzjzmj("zrz");
        fttdmjRequestDTO3.setMjlb("fz");
        calculatedAreaService.calculatedFttdmj(fttdmjRequestDTO3);
        FwHsDO fwHsDO3=fwHsService.queryFwHsByIndex("gtmap");
        Assert.assertNotNull(fwHsDO3.getFttdmj());

        FttdmjRequestDTO fttdmjRequestDTO4 = new FttdmjRequestDTO();
        fttdmjRequestDTO4.setZdh("1234567890987654321");
        fttdmjRequestDTO4.setJsgsxh("2");
        fttdmjRequestDTO4.setZdnlzzjzmj("zrz");
        fttdmjRequestDTO4.setMjlb("fz");
        calculatedAreaService.calculatedFttdmj(fttdmjRequestDTO4);
        FwHsDO fwHsDO4=fwHsService.queryFwHsByIndex("gtmap");
        Assert.assertNotNull(fwHsDO4.getFttdmj());

    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/fttdmj-setupData.xml")
    public void calculatedFttdMjZrz(){
        FttdmjRequestDTO fttdmjRequestDTO = new FttdmjRequestDTO();
        fttdmjRequestDTO.setZdh("1234567890987654321");
        fttdmjRequestDTO.setJsgsxh("3");
        calculatedAreaService.calculatedFttdmj(fttdmjRequestDTO);
        FwHsDO fwHsDO=fwHsService.queryFwHsByIndex("gtmap");
        Assert.assertNotNull(fwHsDO.getFttdmj());
    }
}