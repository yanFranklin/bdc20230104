package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcTdcbnydsyqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.init.App;
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

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/12/4.
 * @description 土地承包经营权、农用地使用权的实现 测试
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
public class InitYxmToBdcTdcbnydsyqServiceImplTest {
    @Autowired
    private InitYxmToBdcTdcbnydsyqServiceImpl initYxmToBdcTdcbnydsyqService;

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/InitYxmToBdcTdcbSetup-data.xml")
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
        initServiceQO.setYxmid("4444444");

        BdcTdcbnydsyqDO bdcTdcbnydsyqDO = (BdcTdcbnydsyqDO) initYxmToBdcTdcbnydsyqService.initQlxx(initServiceQO);

        Assert.assertEquals(bdcXmDO.getSlbh(), bdcTdcbnydsyqDO.getSlbh());
        Assert.assertEquals(bdcXmDO.getDjlx(), bdcTdcbnydsyqDO.getDjlx());
        Assert.assertEquals(bdcXmDO.getDjyy(), bdcTdcbnydsyqDO.getDjyy());
        Assert.assertEquals(bdcXmDO.getXmid(), bdcTdcbnydsyqDO.getXmid());
        Assert.assertEquals(bdcXmDO.getQllx(), bdcTdcbnydsyqDO.getQllx());
        Assert.assertEquals(bdcXmDO.getBdcdyh(), bdcTdcbnydsyqDO.getBdcdyh());
        Assert.assertEquals(bdcXmDO.getBdcdywybh(), bdcTdcbnydsyqDO.getBdcdywybh());
        Assert.assertEquals(bdcXmDO.getZl(), bdcTdcbnydsyqDO.getZl());
        Assert.assertEquals(bdcXmDO.getBz(), bdcTdcbnydsyqDO.getBz());

        Assert.assertEquals("9", bdcTdcbnydsyqDO.getSyttlx().toString());
        Assert.assertEquals("7", bdcTdcbnydsyqDO.getYzyfs().toString());
        Assert.assertEquals("cyzl", bdcTdcbnydsyqDO.getCyzl());
        Assert.assertEquals("45353", bdcTdcbnydsyqDO.getSyzcl().toString());
        Assert.assertEquals("63434.33", bdcTdcbnydsyqDO.getCbmj().toString());
        Assert.assertEquals("5", bdcTdcbnydsyqDO.getTdsyqxz().toString());
        Assert.assertEquals("fbfdm", bdcTdcbnydsyqDO.getFbfdm());
        Assert.assertEquals("fbfmc", bdcTdcbnydsyqDO.getFbfmc());
        Assert.assertEquals("6.3", bdcTdcbnydsyqDO.getCdgd().toString());
        Assert.assertEquals("cdysz", bdcTdcbnydsyqDO.getCdysz());
        Assert.assertEquals("cdjq", bdcTdcbnydsyqDO.getCdjq());
        Assert.assertEquals("cdfgd", bdcTdcbnydsyqDO.getCdfgd());
    }

}