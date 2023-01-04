package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.init.App;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/12/3.
 * @description 从原项目加载数据到房地产权 测试
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
public class InitYxmToBdcFdcqServiceImplTest {
    @Autowired
    private InitYxmToBdcFdcqServiceImpl initYxmToBdcFdcqService;

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/InitYxmToBdcFdcqSetup-data.xml")
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
        bdcXmDO.setDzwyt2(1);
        bdcXmDO.setDzwyt3(2);
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
        initServiceQO.setYxmid("4444444");

        BdcFdcqDO bdcFdcq = (BdcFdcqDO) initYxmToBdcFdcqService.initQlxx(initServiceQO);

        Assert.assertEquals(bdcXmDO.getSlbh(), bdcFdcq.getSlbh());
        Assert.assertEquals(bdcXmDO.getDjlx(), bdcFdcq.getDjlx());
        Assert.assertEquals(bdcXmDO.getDjyy(), bdcFdcq.getDjyy());
        Assert.assertEquals(bdcXmDO.getXmid(), bdcFdcq.getXmid());
        Assert.assertEquals(bdcXmDO.getQllx(), bdcFdcq.getQllx());
        Assert.assertEquals(bdcXmDO.getBdcdyh(), bdcFdcq.getBdcdyh());
        Assert.assertEquals(bdcXmDO.getBdcdywybh(), bdcFdcq.getBdcdywybh());
        Assert.assertEquals(bdcXmDO.getZl(), bdcFdcq.getZl());
        Assert.assertEquals(bdcXmDO.getBdcdyfwlx(), bdcFdcq.getBdcdyfwlx());
        Assert.assertEquals(bdcXmDO.getBz(), bdcFdcq.getBz());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals("tdsyqr", bdcFdcq.getTdsyqr());
        Assert.assertEquals(sdf.parse("2018-11-13 01:45:54"), bdcFdcq.getTdsyqssj());
        Assert.assertEquals(sdf.parse("2018-11-13 01:45:54"), bdcFdcq.getTdsyjssj());
        Assert.assertEquals("2222.0", bdcFdcq.getJyjg().toString());
        Assert.assertEquals("1", bdcFdcq.getGhyt().toString());
        Assert.assertEquals("2", bdcFdcq.getFwxz().toString());
        Assert.assertEquals("3", bdcFdcq.getFwjg().toString());
        Assert.assertEquals("6", bdcFdcq.getSzc().toString());
        Assert.assertEquals("11", bdcFdcq.getZcs().toString());
        Assert.assertEquals("222.0", bdcFdcq.getJzmj().toString());
        Assert.assertEquals("200.0", bdcFdcq.getZyjzmj().toString());
        Assert.assertEquals("22.0", bdcFdcq.getFtjzmj().toString());
        Assert.assertEquals("2018-11-13 1:45:54", bdcFdcq.getJgsj());
        Assert.assertEquals("20.0", bdcFdcq.getDytdmj().toString());
        Assert.assertEquals("200.0", bdcFdcq.getFttdmj().toString());
        Assert.assertEquals("4", bdcFdcq.getFwlx().toString());
        Assert.assertEquals("6", bdcFdcq.getSzmyc());
        Assert.assertEquals("3.0", bdcFdcq.getCg().toString());
        Assert.assertEquals("11", bdcFdcq.getMyzcs());
        Assert.assertEquals("50000.0", bdcFdcq.getTdsyqmj().toString());
        Assert.assertEquals("fwqqh", bdcFdcq.getFwqqh());
        Assert.assertEquals("400.0", bdcFdcq.getZzdmj().toString());
        Assert.assertEquals("0", bdcFdcq.getSfgyz().toString());
    }

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/InitYxmToBdcFdcqSetup-data.xml")
    public void initFdcqXm() throws Exception {
        InitServiceQO initServiceQO = new InitServiceQO();
        initServiceQO.setYxmid("4444444");

        List<BdcFdcqFdcqxmDO> bdcFdcqFdcqxmDOList = initYxmToBdcFdcqService.initFdcqXm(initServiceQO, "5224", new InitServiceDTO());

        Assert.assertEquals(1, bdcFdcqFdcqxmDOList.size());
        Assert.assertEquals("5224", bdcFdcqFdcqxmDOList.get(0).getQlid());
        Assert.assertEquals("34yg34", bdcFdcqFdcqxmDOList.get(0).getXmmc());
    }

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/InitYxmToBdcFdcqSetup-data.xml")
    public void initFdcqFsss() throws Exception {
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
        initServiceQO.setYxmid("4444444");

        List<BdcFwfsssDO> bdcFwfsssList = initYxmToBdcFdcqService.initFdcqFsss(initServiceQO);

        Assert.assertEquals(1, bdcFwfsssList.size());
        Assert.assertEquals(bdcXmDO.getXmid(), bdcFwfsssList.get(0).getXmid());
        Assert.assertEquals("45", bdcFwfsssList.get(0).getGhyt().toString());
    }

}