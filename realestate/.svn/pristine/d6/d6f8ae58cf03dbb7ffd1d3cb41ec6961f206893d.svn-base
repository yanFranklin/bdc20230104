package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcLqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.building.*;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/15
 * @description 从权籍加载数据到林权
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
public class InitLpbToBdcLqServiceImplTest {
    @Autowired
    private InitLpbToBdcLqServiceImpl initLpbToBdcLqService;

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
        bdcXmDO.setBdcdyh("bdcdyh");
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

        DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();
        NydDjdcbResponseDTO nydDjdcbResponseDTO = new NydDjdcbResponseDTO();
        nydDjdcbResponseDTO.setLqzysz("lqzysz");
        nydDjdcbResponseDTO.setLqzs(((Integer) 2312).longValue());
        nydDjdcbResponseDTO.setLqlz("864");
        nydDjdcbResponseDTO.setLqzlnd(2222);
        nydDjdcbResponseDTO.setLqxdm("lqxdm");
        nydDjdcbResponseDTO.setLqlb("lqlb");
        nydDjdcbResponseDTO.setLqxb("lqxb");
        nydDjdcbResponseDTO.setSyqlx("43");
        nydDjdcbResponseDTO.setLqqy("3246");

        djxxResponseDTO.setDjDcbResponseDTO(nydDjdcbResponseDTO);
        List<DjxxQlrDTO> djxxQlrDTOList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            DjxxQlrDTO qlrDTO = new DjxxQlrDTO();
            qlrDTO.setQlrmc("所有权利人");
            djxxQlrDTOList.add(qlrDTO);
        }
        djxxResponseDTO.setLmsuqrList(djxxQlrDTOList);
        djxxQlrDTOList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            DjxxQlrDTO qlrDTO = new DjxxQlrDTO();
            qlrDTO.setQlrmc("使用权利人");
            djxxQlrDTOList.add(qlrDTO);
        }
        djxxResponseDTO.setLmsyqrList(djxxQlrDTOList);
        initServiceQO.setDjxxResponseDTO(djxxResponseDTO);

        initServiceQO.setGzwDcbResponseDTO(new GzwDcbResponseDTO());

        BdcLqDO bdcLqDO = (BdcLqDO) initLpbToBdcLqService.initQlxx(initServiceQO);
        Assert.assertEquals(bdcXmDO.getSlbh(), bdcLqDO.getSlbh());
        Assert.assertEquals(bdcXmDO.getXmid(), bdcLqDO.getXmid());
        Assert.assertEquals(bdcXmDO.getQllx(), bdcLqDO.getQllx());
        Assert.assertEquals(bdcXmDO.getDjlx(), bdcLqDO.getDjlx());
        Assert.assertEquals(bdcXmDO.getBz(), bdcLqDO.getBz());
        Assert.assertEquals(bdcXmDO.getBdcdyh(), bdcLqDO.getBdcdyh());
        Assert.assertEquals(bdcXmDO.getBdcdywybh(), bdcLqDO.getBdcdywybh());
        Assert.assertEquals(bdcXmDO.getZl(), bdcLqDO.getZl());
        Assert.assertEquals(bdcXmDO.getDjyy(), bdcLqDO.getDjyy());

        Assert.assertEquals("所有权利人 所有权利人 所有权利人", bdcLqDO.getSllmsyqr1());
        Assert.assertEquals("使用权利人 使用权利人 使用权利人", bdcLqDO.getSllmsyqr2());

        Assert.assertEquals(nydDjdcbResponseDTO.getLqzysz(), bdcLqDO.getZysz());
        Assert.assertEquals(nydDjdcbResponseDTO.getLqzs(), bdcLqDO.getZs());
        Assert.assertEquals(nydDjdcbResponseDTO.getLqlz(), bdcLqDO.getLz().toString());
        Assert.assertEquals(nydDjdcbResponseDTO.getLqzlnd(), bdcLqDO.getZlnd());
        Assert.assertEquals(nydDjdcbResponseDTO.getLqxdm(), bdcLqDO.getXdm());
        Assert.assertEquals(nydDjdcbResponseDTO.getLqlb(), bdcLqDO.getLb());
        Assert.assertEquals(nydDjdcbResponseDTO.getLqxb(), bdcLqDO.getXb());
        Assert.assertEquals(nydDjdcbResponseDTO.getSyqlx().toString().substring(0,1), bdcLqDO.getLdsyqxz().toString());
        Assert.assertEquals(nydDjdcbResponseDTO.getLqqy(), bdcLqDO.getQy().toString());
    }

}