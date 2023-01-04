package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcGjzwsyqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.building.GzwDcbDO;
import cn.gtmap.realestate.common.core.dto.building.*;
import cn.gtmap.realestate.init.App;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.netflix.discovery.converters.Auto;
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
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/20
 * @description 从地籍数据 加载数据到构建筑物所有权 测试
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
public class InitLpbToBdcGjzwsyqServiceImplTest {
    @Autowired
    private InitLpbToBdcGjzwsyqServiceImpl initLpbToBdcGjzwsyqService;

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
        bdcXmDO.setBdcdyh("320902004004GB00018F00010005");
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
        ZhDjdcbResponseDTO zhDjdcbResponseDTO = new ZhDjdcbResponseDTO();
        zhDjdcbResponseDTO.setZhmj(Double.parseDouble("78874"));
        zhDjdcbResponseDTO.setQsrq(new Date());
        zhDjdcbResponseDTO.setZzrq(new Date());
        djxxResponseDTO.setDjDcbResponseDTO(zhDjdcbResponseDTO);
        List<DjxxQlrDTO> djxxQlrDTOList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            DjxxQlrDTO qlrDTO = new DjxxQlrDTO();
            qlrDTO.setQlrmc("权利人");
            djxxQlrDTOList.add(qlrDTO);
        }
        djxxResponseDTO.setQlrList(djxxQlrDTOList);
        initServiceQO.setDjxxResponseDTO(djxxResponseDTO);

        initServiceQO.setBdcdyResponseDTO(new BdcdyResponseDTO());

        GzwDcbResponseDTO gzwDcbResponseDTO = new GzwDcbResponseDTO();
        GzwDcbDO gzwDcbDO = new GzwDcbDO();
        gzwDcbDO.setGzwlx("65");
        gzwDcbDO.setGzwghyt("876");
        gzwDcbDO.setJgsj(new Date());
        gzwDcbDO.setMj(Double.parseDouble("76368"));
        gzwDcbDO.setBz("fasfggaha");
        gzwDcbDO.setZt("8465");
        gzwDcbDO.setQsxz("6345");
        gzwDcbDO.setGyqk("3345");
        gzwDcbDO.setZdzhdm("320902004004GB00018");
        gzwDcbResponseDTO.setGzwDcbDO(gzwDcbDO);
        initServiceQO.setGzwDcbResponseDTO(gzwDcbResponseDTO);

        BdcGjzwsyqDO bdcGjzwsyqDO = (BdcGjzwsyqDO) initLpbToBdcGjzwsyqService.initQlxx(initServiceQO);
        Assert.assertEquals(bdcXmDO.getSlbh(), bdcGjzwsyqDO.getSlbh());
        Assert.assertEquals(bdcXmDO.getXmid(), bdcGjzwsyqDO.getXmid());
        Assert.assertEquals(bdcXmDO.getQllx().toString(), bdcGjzwsyqDO.getQllx());
        Assert.assertEquals(bdcXmDO.getDjyy(), bdcGjzwsyqDO.getDjyy());
        Assert.assertEquals(bdcXmDO.getDjlx(), bdcGjzwsyqDO.getDjlx());
        Assert.assertEquals(bdcXmDO.getQszt(), bdcGjzwsyqDO.getQszt());
        Assert.assertEquals(bdcXmDO.getBz(), bdcGjzwsyqDO.getBz());
        Assert.assertEquals(bdcXmDO.getBdcdyh(), bdcGjzwsyqDO.getBdcdyh());
        Assert.assertEquals(bdcXmDO.getBdcdywybh(), bdcGjzwsyqDO.getBdcdywybh());
        Assert.assertEquals(bdcXmDO.getZl(), bdcGjzwsyqDO.getZl());

        Assert.assertEquals(zhDjdcbResponseDTO.getQsrq().toString(), bdcGjzwsyqDO.getTdhysyqssj().toString());
        Assert.assertEquals(zhDjdcbResponseDTO.getZzrq().toString(), bdcGjzwsyqDO.getTdhysyjssj().toString());
        Assert.assertEquals(zhDjdcbResponseDTO.getZhmj().toString(), bdcGjzwsyqDO.getTdhysymj().toString());
        Assert.assertEquals("权利人 权利人 权利人", bdcGjzwsyqDO.getTdhysyqr());

        Assert.assertEquals(gzwDcbDO.getGzwlx(), bdcGjzwsyqDO.getGjzwlx());
        Assert.assertEquals(gzwDcbDO.getGzwghyt(), bdcGjzwsyqDO.getGjzwghyt());
        Assert.assertEquals(gzwDcbDO.getJgsj(), bdcGjzwsyqDO.getJgsj());
        Assert.assertEquals(gzwDcbDO.getMj(), bdcGjzwsyqDO.getGjzwmj());
        Assert.assertEquals(gzwDcbDO.getGyqk(), bdcGjzwsyqDO.getGyqk());
    }

}