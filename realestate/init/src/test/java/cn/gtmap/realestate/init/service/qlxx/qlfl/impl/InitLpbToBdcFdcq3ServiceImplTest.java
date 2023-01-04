package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcFdcq3DO;
import cn.gtmap.realestate.common.core.domain.BdcFdcq3GyxxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.building.ZdJzwsuqgydcDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.GzwDcbResponseDTO;
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
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description 从楼盘表加载数据到建筑物区分所有权业主共有部分登记信息 测试
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
public class InitLpbToBdcFdcq3ServiceImplTest {
    @Autowired
    private InitLpbToBdcFdcq3ServiceImpl initLpbToBdcFdcq3Service;

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
        initServiceQO.setBdcdyResponseDTO(new BdcdyResponseDTO());
        initServiceQO.setDjxxResponseDTO(new DjxxResponseDTO());
        initServiceQO.setGzwDcbResponseDTO(new GzwDcbResponseDTO());
        BdcFdcq3DO bdcFdcq3 = (BdcFdcq3DO) initLpbToBdcFdcq3Service.initQlxx(initServiceQO);
        Assert.assertEquals(bdcXmDO.getSlbh(), bdcFdcq3.getSlbh());
        Assert.assertEquals(bdcXmDO.getQllx(), bdcFdcq3.getQllx());
        Assert.assertEquals(bdcXmDO.getXmid(), bdcFdcq3.getXmid());
        Assert.assertEquals(bdcXmDO.getQszt(), bdcFdcq3.getQszt());
        Assert.assertEquals(bdcXmDO.getBz(), bdcFdcq3.getBz());
    }

    @Test
    public void initFdcq3Gyxx() throws Exception {
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
        List<ZdJzwsuqgydcDO> zdJzwsuqgydcDOList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ZdJzwsuqgydcDO zdJzwsuqgydcDO = new ZdJzwsuqgydcDO();
            zdJzwsuqgydcDO.setJzwdm("jzwdm");
            zdJzwsuqgydcDO.setJzwmj(Double.parseDouble("123"));
            zdJzwsuqgydcDO.setJzwmc("jzwmc");
            zdJzwsuqgydcDO.setFttdmj(Double.parseDouble("321"));
            zdJzwsuqgydcDO.setJzwsl(4);
            zdJzwsuqgydcDOList.add(zdJzwsuqgydcDO);
        }
        djxxResponseDTO.setZdJzwsuqgydcDOList(zdJzwsuqgydcDOList);
        initServiceQO.setDjxxResponseDTO(djxxResponseDTO);
        List<BdcFdcq3GyxxDO> bdcFdcq3GyxxList = initLpbToBdcFdcq3Service.initFdcq3Gyxx(initServiceQO, "qlid");
        BdcFdcq3GyxxDO bdcFdcq3GyxxDO = bdcFdcq3GyxxList.get(0);
        Assert.assertEquals(3, bdcFdcq3GyxxList.size());
        Assert.assertEquals(bdcXmDO.getSlbh(), bdcFdcq3GyxxDO.getSlbh());
        Assert.assertEquals(bdcXmDO.getXmid(), bdcFdcq3GyxxDO.getXmid());
        Assert.assertEquals(zdJzwsuqgydcDOList.get(0).getJzwdm(), bdcFdcq3GyxxDO.getJgzwbh());
        Assert.assertEquals(zdJzwsuqgydcDOList.get(0).getJzwmc(), bdcFdcq3GyxxDO.getJgzwmc());
        Assert.assertEquals(zdJzwsuqgydcDOList.get(0).getJzwmj(), bdcFdcq3GyxxDO.getJgzwmj());
        Assert.assertEquals(zdJzwsuqgydcDOList.get(0).getFttdmj(), bdcFdcq3GyxxDO.getFttdmj());
        Assert.assertEquals(zdJzwsuqgydcDOList.get(0).getJzwsl(), bdcFdcq3GyxxDO.getJgzwsl());
    }

}