package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.GzwDcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdDjdcbResponseDTO;
import cn.gtmap.realestate.init.App;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
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

import java.util.Date;


/**
 * @author by Administrator.
 * @version 1.0, 2018/11/29
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
@SpringBootTest(classes = App.class)
public class InitLpbToBdcYgServiceImplTest {
    @Autowired
    private InitLpbToBdcYgServiceImpl initLpbToBdcYgService;

    @Test
    public void initQlxx() throws Exception {
        InitServiceQO initServiceQO = new InitServiceQO();
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setXmid("111222");
        bdcXmDO.setSlbh("slbh");
        bdcXmDO.setQllx(1);
        bdcXmDO.setDjlx(100);
        bdcXmDO.setDjyy("11111");
        bdcXmDO.setSqzsbs(1);
        bdcXmDO.setSqfbcz(1);
        bdcXmDO.setBz("bzbzbzbzbzbzbzbzbz");
        bdcXmDO.setSqrsm("sqrsm");
        bdcXmDO.setAjzt(1);
        bdcXmDO.setSfwtaj(0);
        bdcXmDO.setGzljdmc("gzlslmc");
        bdcXmDO.setQxdm("qxdm");
        bdcXmDO.setSlsj(new Date());
        bdcXmDO.setSlrid("slrid");
        bdcXmDO.setSlr("slr");
        bdcXmDO.setXmly(1);
        bdcXmDO.setJssj(new Date());
        bdcXmDO.setDjxl("22");
        bdcXmDO.setBdclx(1);
        bdcXmDO.setYcqzh("ycqzh");
        bdcXmDO.setYfczh("yfczh");
        bdcXmDO.setYtdzh("ytdzh");
        bdcXmDO.setGzlslid("gzlslid");
        bdcXmDO.setSpxtywh("spxtywh");
        bdcXmDO.setSsxz("ssxz");
        bdcXmDO.setDjjg("djjg");
        bdcXmDO.setSpxtblzt(0);
        bdcXmDO.setBdcdyh("230522100033GB00053F00090016");
        bdcXmDO.setBdcdywybh("bdcdywybh");
        bdcXmDO.setDjsj(new Date());
        bdcXmDO.setDbr("dbr");
        bdcXmDO.setQszt(0);
        bdcXmDO.setZl("zl");
        bdcXmDO.setDzwmj(Double.parseDouble("111"));
        bdcXmDO.setDzwyt(1);
        bdcXmDO.setDzwyt2(2);
        bdcXmDO.setDzwyt3(3);
        bdcXmDO.setYhlxa(1);
        bdcXmDO.setYhlxb(2);
        bdcXmDO.setGzwlx(3);
        bdcXmDO.setLz(4);
        bdcXmDO.setMjdw(1);
        bdcXmDO.setZdzhmj(Double.parseDouble("222"));
        bdcXmDO.setQlr("qlr");
        bdcXmDO.setQlrzjh("qlrzjh");
        bdcXmDO.setYwr("ywr");
        bdcXmDO.setYwrzjh("ywrzjh");
        bdcXmDO.setJyhth("jyhth");
        bdcXmDO.setBdcdyfwlx(8);
        bdcXmDO.setBdcqzh("bdcqzh");
        initServiceQO.setBdcXm(bdcXmDO);

        BdcdyResponseDTO bdcdyResponseDTO = new BdcdyResponseDTO();
        bdcdyResponseDTO.setGhyt("2");
        bdcdyResponseDTO.setFwxz("2");
        bdcdyResponseDTO.setScjzmj(Double.parseDouble("613.52"));
        bdcdyResponseDTO.setJyjg(Double.parseDouble("424325.32"));
        bdcdyResponseDTO.setGyqk("243");
        bdcdyResponseDTO.setWlcs(2);
        bdcdyResponseDTO.setFwcs(24);
        bdcdyResponseDTO.setFttdmj(Double.parseDouble("353.33"));
        initServiceQO.setBdcdyResponseDTO(bdcdyResponseDTO);

        DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();
        ZdDjdcbResponseDTO zdDjdcbResponseDTO = new ZdDjdcbResponseDTO();
        zdDjdcbResponseDTO.setQsrq(new Date());
        zdDjdcbResponseDTO.setZzrq(new Date());
        djxxResponseDTO.setDjDcbResponseDTO(zdDjdcbResponseDTO);
        initServiceQO.setDjxxResponseDTO(djxxResponseDTO);
        initServiceQO.setGzwDcbResponseDTO(new GzwDcbResponseDTO());

        BdcYgDO bdcYgDO = (BdcYgDO) initLpbToBdcYgService.initQlxx(initServiceQO);

        Assert.assertEquals(bdcXmDO.getSlbh(), bdcYgDO.getSlbh());
        Assert.assertEquals(bdcXmDO.getXmid(), bdcYgDO.getXmid());
        Assert.assertEquals(bdcXmDO.getDjlx(), bdcYgDO.getDjlx());
        Assert.assertEquals(bdcXmDO.getBz(), bdcYgDO.getBz());
        Assert.assertEquals(bdcXmDO.getBdcdyh(), bdcYgDO.getBdcdyh());
        Assert.assertEquals(bdcXmDO.getBdcdywybh(), bdcYgDO.getBdcdywybh());
        Assert.assertEquals(bdcXmDO.getZl(), bdcYgDO.getZl());
        Assert.assertEquals(bdcXmDO.getDjyy(), bdcYgDO.getDjyy());

        Assert.assertEquals(zdDjdcbResponseDTO.getQsrq().toString(), bdcYgDO.getTdsyqssj().toString());
        Assert.assertEquals(zdDjdcbResponseDTO.getZzrq().toString(), bdcYgDO.getTdsyjssj().toString());

        Assert.assertEquals(bdcdyResponseDTO.getGhyt(), String.valueOf(bdcYgDO.getGhyt()));
        Assert.assertEquals(bdcdyResponseDTO.getFwxz(), String.valueOf(bdcYgDO.getFwxz()));
        Assert.assertEquals(bdcdyResponseDTO.getScjzmj(), bdcYgDO.getJzmj());
        Assert.assertEquals(bdcdyResponseDTO.getJyjg(), bdcYgDO.getQdjg());
        Assert.assertEquals(bdcdyResponseDTO.getGyqk(), bdcYgDO.getGyqk());
        Assert.assertEquals(bdcdyResponseDTO.getWlcs(), bdcYgDO.getSzc());
        Assert.assertEquals(bdcdyResponseDTO.getFwcs(), bdcYgDO.getZcs());
        Assert.assertEquals(bdcdyResponseDTO.getJyjg(), bdcYgDO.getJyje());
        Assert.assertEquals(bdcdyResponseDTO.getWlcs().toString(), bdcYgDO.getSzmyc());
        Assert.assertEquals(bdcdyResponseDTO.getFttdmj(), bdcYgDO.getFttdmj());
    }

}