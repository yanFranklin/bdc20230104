package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcFdcqFdcqxmDO;
import cn.gtmap.realestate.common.core.domain.BdcFwfsssDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwZhsDO;
import cn.gtmap.realestate.common.core.dto.building.*;
import cn.gtmap.realestate.init.App;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.util.DateUtils;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description 从楼盘表加载数据到房地产权
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
public class InitLpbToBdcFdcqServiceImplTest {
    @Autowired
    private InitLpbToBdcFdcqServiceImpl initLpbToBdcFdcqService;

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
        bdcdyResponseDTO.setJyjg(Double.parseDouble("321.11"));
        bdcdyResponseDTO.setGhyt("3");
        bdcdyResponseDTO.setFwxz("4");
        bdcdyResponseDTO.setHxjg("686");
        bdcdyResponseDTO.setWlcs(99);
        bdcdyResponseDTO.setFwcs(100);
        bdcdyResponseDTO.setScjzmj(Double.parseDouble("444"));
        bdcdyResponseDTO.setSctnjzmj(Double.parseDouble("555"));
        bdcdyResponseDTO.setScftjzmj(Double.parseDouble("666"));
        bdcdyResponseDTO.setJgrq(new Date());
        bdcdyResponseDTO.setDytdmj(Double.parseDouble("777"));
        bdcdyResponseDTO.setFttdmj(Double.parseDouble("888"));
        bdcdyResponseDTO.setZrzh("333");
        bdcdyResponseDTO.setCh(987);
        bdcdyResponseDTO.setCg(Double.parseDouble("4.33"));
        bdcdyResponseDTO.setZzdmj("34232453");
        bdcdyResponseDTO.setBdcdyh("230522100033GB00053F00090016");
        bdcdyResponseDTO.setFwbm("fwhsfwbm");
        bdcdyResponseDTO.setZl("房屋坐落");
        bdcdyResponseDTO.setQsrq(new Date());
        bdcdyResponseDTO.setZzrq(new Date());
        initServiceQO.setBdcdyResponseDTO(bdcdyResponseDTO);

        DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();
        ZdDjdcbResponseDTO zdDjdcbResponseDTO = new ZdDjdcbResponseDTO();
        zdDjdcbResponseDTO.setZdDjdcbIndex("111");
        zdDjdcbResponseDTO.setQsrq(new Date());
        zdDjdcbResponseDTO.setZzrq(new Date());
        zdDjdcbResponseDTO.setScmj(Double.parseDouble("423.89"));
        List<DjxxQlrDTO> djxxQlrDTOList = new ArrayList<>();
        DjxxQlrDTO djxxQlrDTO = new DjxxQlrDTO();
        djxxQlrDTO.setQlrmc("权利人1");
        djxxQlrDTOList.add(djxxQlrDTO);
        djxxQlrDTO = new DjxxQlrDTO();
        djxxQlrDTO.setQlrmc("权利人2");
        djxxQlrDTOList.add(djxxQlrDTO);
        djxxResponseDTO.setQlrList(djxxQlrDTOList);
        djxxResponseDTO.setDjDcbResponseDTO(zdDjdcbResponseDTO);
        initServiceQO.setDjxxResponseDTO(djxxResponseDTO);

        initServiceQO.setGzwDcbResponseDTO(new GzwDcbResponseDTO());
        BdcFdcqDO bdcFdcq = (BdcFdcqDO) initLpbToBdcFdcqService.initQlxx(initServiceQO);
        Assert.assertEquals(bdcXmDO.getSlbh(), bdcFdcq.getSlbh());
        Assert.assertEquals(bdcXmDO.getDjlx(), bdcFdcq.getDjlx());
        Assert.assertEquals(bdcXmDO.getDjyy(), bdcFdcq.getDjyy());
        Assert.assertEquals(bdcXmDO.getXmid(), bdcFdcq.getXmid());
        Assert.assertEquals(bdcXmDO.getQllx(), bdcFdcq.getQllx());
        Assert.assertEquals(bdcXmDO.getBdcdyh(), bdcFdcq.getBdcdyh());
        Assert.assertEquals(bdcXmDO.getBdcdywybh(), bdcFdcq.getBdcdywybh());
        Assert.assertEquals(bdcXmDO.getZl(), bdcFdcq.getZl());
        Assert.assertEquals(bdcXmDO.getBdcdyfwlx(), bdcFdcq.getBdcdyfwlx());
        Assert.assertEquals(zdDjdcbResponseDTO.getQsrq().toString(), bdcFdcq.getTdsyqssj().toString());
        Assert.assertEquals(zdDjdcbResponseDTO.getZzrq().toString(), bdcFdcq.getTdsyjssj().toString());
        Assert.assertEquals(zdDjdcbResponseDTO.getScmj(), bdcFdcq.getTdsyqmj());
        Assert.assertEquals(bdcdyResponseDTO.getJyjg(), bdcFdcq.getJyjg());
        Assert.assertEquals(bdcdyResponseDTO.getGhyt(), bdcFdcq.getGhyt().toString());
        Assert.assertEquals(bdcdyResponseDTO.getFwxz(), bdcFdcq.getFwxz().toString());
        Assert.assertEquals(bdcdyResponseDTO.getHxjg(), String.valueOf(bdcFdcq.getFwjg()));
        Assert.assertEquals(bdcdyResponseDTO.getWlcs(), bdcFdcq.getSzc());
        Assert.assertEquals(bdcdyResponseDTO.getScjzmj(), bdcFdcq.getJzmj());
        Assert.assertEquals(bdcdyResponseDTO.getSctnjzmj(), bdcFdcq.getZyjzmj());
        Assert.assertEquals(bdcdyResponseDTO.getScftjzmj(), bdcFdcq.getFtjzmj());
        Assert.assertEquals(bdcdyResponseDTO.getJgrq().toString(), bdcFdcq.getJgsj().toString());
        Assert.assertEquals(bdcdyResponseDTO.getDytdmj(), bdcFdcq.getDytdmj());
        Assert.assertEquals(bdcdyResponseDTO.getFttdmj(), bdcFdcq.getFttdmj());
        Assert.assertEquals(bdcdyResponseDTO.getCh().toString(), bdcFdcq.getSzmyc());
        Assert.assertEquals(bdcdyResponseDTO.getCg(), bdcFdcq.getCg());
        Assert.assertEquals("权利人1 权利人2", bdcFdcq.getTdsyqr());
    }

    @Test
    public void initFdcqXm() throws Exception {
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
        bdcXmDO.setBdcdyh("320902004004GB00018F00010012");
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
        bdcdyResponseDTO.setXmmc("xmmc");
        bdcdyResponseDTO.setFwxz("11");
        bdcdyResponseDTO.setBdcdyh("1234567890123456789012345678");

        List<FwLjzDO> fwLjzDOList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FwLjzDO fwLjzDO = new FwLjzDO();
            fwLjzDO.setDh("33");
            fwLjzDO.setFwcs(333);
            fwLjzDO.setFwyt("22");
            fwLjzDO.setFwjg("44");
            fwLjzDO.setScjzmj(Double.parseDouble("432"));
            fwLjzDO.setJgrq(new Date());
            fwLjzDO.setZts(543);
            fwLjzDO.setZzdmj("420");
            fwLjzDOList.add(fwLjzDO);
        }
        bdcdyResponseDTO.setLjzList(fwLjzDOList);
        initServiceQO.setBdcdyResponseDTO(bdcdyResponseDTO);

        List<BdcFdcqFdcqxmDO> bdcFdcqFdcqxmDOList = initLpbToBdcFdcqService.initFdcqXm(initServiceQO, "42342", new InitServiceDTO());
        BdcFdcqFdcqxmDO bdcFdcqFdcqxmDO = bdcFdcqFdcqxmDOList.get(0);

        Assert.assertEquals(5, bdcFdcqFdcqxmDOList.size());
        Assert.assertEquals(bdcdyResponseDTO.getXmmc(), bdcFdcqFdcqxmDO.getXmmc());
        Assert.assertEquals(bdcdyResponseDTO.getFwxz(), bdcFdcqFdcqxmDO.getFwxz().toString());
        Assert.assertEquals("1234567890123456789", bdcFdcqFdcqxmDO.getBdcdywybh());

        Assert.assertEquals(fwLjzDOList.get(0).getDh(), bdcFdcqFdcqxmDO.getZh());
        Assert.assertEquals(fwLjzDOList.get(0).getFwcs(), bdcFdcqFdcqxmDO.getZcs());
        Assert.assertEquals(fwLjzDOList.get(0).getFwyt(), bdcFdcqFdcqxmDO.getGhyt().toString());
        Assert.assertEquals(fwLjzDOList.get(0).getFwjg(), bdcFdcqFdcqxmDO.getFwjg().toString());
        Assert.assertEquals(fwLjzDOList.get(0).getScjzmj(), bdcFdcqFdcqxmDO.getJzmj());
        Assert.assertEquals(DateUtils.getCurrentDateStr(DateUtils.simpleDateFormat), bdcFdcqFdcqxmDO.getJgsj().toString());
        Assert.assertEquals(fwLjzDOList.get(0).getZts(), bdcFdcqFdcqxmDO.getZts());
    }

    @Test
    public void initFdcqFsss() throws Exception {
        InitServiceQO initServiceQO = new InitServiceQO();
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setXmid("111222");
        initServiceQO.setBdcXm(bdcXmDO);
        BdcdyResponseDTO bdcdyResponseDTO = new BdcdyResponseDTO();
        bdcdyResponseDTO.setBdcdyh("bdcdyh");
        bdcdyResponseDTO.setFwbm("fwbm");
        List<FwZhsDO> fwZhsList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            FwZhsDO fwZhsDO = new FwZhsDO();
            fwZhsDO.setGhyt("3");
            fwZhsDO.setScjzmj(Double.parseDouble("1233"));
            fwZhsDO.setZl("fwfssszl");
            fwZhsDO.setSctnjzmj(Double.parseDouble("233"));
            fwZhsList.add(fwZhsDO);
        }
        bdcdyResponseDTO.setFwZhsList(fwZhsList);
        initServiceQO.setBdcdyResponseDTO(bdcdyResponseDTO);
        List<BdcFwfsssDO> bdcFwfsssDOList = initLpbToBdcFdcqService.initFdcqFsss(initServiceQO);
        BdcFwfsssDO bdcFwfsssDO = bdcFwfsssDOList.get(0);

        Assert.assertEquals(3, bdcFwfsssDOList.size());
        Assert.assertEquals(bdcXmDO.getXmid(), bdcFwfsssDO.getXmid());
        Assert.assertEquals(fwZhsList.get(0).getGhyt(), bdcFwfsssDO.getGhyt().toString());
        Assert.assertEquals(fwZhsList.get(0).getScjzmj(), bdcFwfsssDO.getJzmj());
        Assert.assertEquals(fwZhsList.get(0).getZl(), bdcFwfsssDO.getZl());
        Assert.assertEquals(fwZhsList.get(0).getSctnjzmj(), bdcFwfsssDO.getZyjzmj());
    }

}