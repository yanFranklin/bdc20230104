package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
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

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/22
 * @description 从地籍数据 加载数据到查封 测试
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
public class InitLpbToBdcCfServiceImplTest {
    @Autowired
    private InitLpbToBdcCfServiceImpl initLpbToBdcCfService;

    @Test
    public void getVal() throws Exception {
    }

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
        BdcdyResponseDTO bdcdyResponseDTO = new BdcdyResponseDTO();
        bdcdyResponseDTO.setZl("房屋坐落");
        bdcdyResponseDTO.setBdcdyh("230522100033GB00053F00090016");
        bdcdyResponseDTO.setFwbm("fwbm");
        DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();
        List<DjxxQlrDTO> djxxQlrList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            DjxxQlrDTO djxxQlr = new DjxxQlrDTO();
            djxxQlr.setQlrmc("权利人");
            djxxQlrList.add(djxxQlr);
        }
        djxxResponseDTO.setQlrList(djxxQlrList);
        ZdDjdcbResponseDTO zdDjdcbResponse = new ZdDjdcbResponseDTO();
        zdDjdcbResponse.setTdzl("土地坐落");
        zdDjdcbResponse.setBdcdyh("土地不动产单元号");
        zdDjdcbResponse.setDjh("djh");
        djxxResponseDTO.setDjDcbResponseDTO(zdDjdcbResponse);
        initServiceQO.setDjxxResponseDTO(djxxResponseDTO);
        initServiceQO.setBdcXm(bdcXmDO);
        initServiceQO.setBdcdyResponseDTO(bdcdyResponseDTO);
        List<FwFcqlrDO> fwFcqlrList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            FwFcqlrDO fwFcqlr = new FwFcqlrDO();
            fwFcqlr.setQlr("权利人2");
            fwFcqlrList.add(fwFcqlr);
        }
        initServiceQO.setGzwDcbResponseDTO(new GzwDcbResponseDTO());
        BdcQl bdcQl = initLpbToBdcCfService.initQlxx(initServiceQO);

        Assert.assertEquals((new BdcCfDO()).getClass().toString(), bdcQl.getClass().toString());
        Assert.assertEquals(bdcXmDO.getSlbh(), bdcQl.getSlbh());
        Assert.assertEquals(bdcXmDO.getBdcdyh(), ((BdcCfDO) bdcQl).getBdcdyh());
        Assert.assertEquals(null, ((BdcCfDO) bdcQl).getBzxr());
        bdcdyResponseDTO.setQlrList(fwFcqlrList);
        initServiceQO.setBdcdyResponseDTO(bdcdyResponseDTO);
        bdcQl = initLpbToBdcCfService.initQlxx(initServiceQO);
        Assert.assertEquals("权利人2、权利人2、权利人2", ((BdcCfDO) bdcQl).getBzxr());
    }

}