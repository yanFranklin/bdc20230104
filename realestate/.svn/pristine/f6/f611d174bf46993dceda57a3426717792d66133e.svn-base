package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcLqDO;
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
 * @description 从原项目加载数据到林权 测试
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
public class InitYxmToBdcLqServiceImplTest {
    @Autowired
    private InitYxmToBdcLqServiceImpl initYxmToBdcLqService;

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/InitYxmToBdcLqSetup-data.xml")
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

        BdcLqDO bdcLqDO = (BdcLqDO) initYxmToBdcLqService.initQlxx(initServiceQO);

        Assert.assertEquals(bdcXmDO.getSlbh(), bdcLqDO.getSlbh());
        Assert.assertEquals(bdcXmDO.getDjlx(), bdcLqDO.getDjlx());
        Assert.assertEquals(bdcXmDO.getDjyy(), bdcLqDO.getDjyy());
        Assert.assertEquals(bdcXmDO.getXmid(), bdcLqDO.getXmid());
        Assert.assertEquals(bdcXmDO.getQllx(), bdcLqDO.getQllx());
        Assert.assertEquals(bdcXmDO.getBdcdyh(), bdcLqDO.getBdcdyh());
        Assert.assertEquals(bdcXmDO.getBdcdywybh(), bdcLqDO.getBdcdywybh());
        Assert.assertEquals(bdcXmDO.getZl(), bdcLqDO.getZl());
        Assert.assertEquals(bdcXmDO.getBz(), bdcLqDO.getBz());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals("52.22",bdcLqDO.getSyqmj().toString());
        Assert.assertEquals(sdf.parse("2018-11-13 1:45:54"),bdcLqDO.getLdsyqssj());
        Assert.assertEquals(sdf.parse("2088-11-13 1:45:54"),bdcLqDO.getLdsyjssj());
        Assert.assertEquals("zysz",bdcLqDO.getZysz());
        Assert.assertEquals("32423",bdcLqDO.getZs().toString());
        Assert.assertEquals("9",bdcLqDO.getLz().toString());
        Assert.assertEquals("lzmc",bdcLqDO.getLzmc());
        Assert.assertEquals("2018",bdcLqDO.getZlnd().toString());
        Assert.assertEquals("小地名",bdcLqDO.getXdm());
        Assert.assertEquals("林班",bdcLqDO.getLb());
        Assert.assertEquals("小班",bdcLqDO.getXb());
        Assert.assertEquals("6",bdcLqDO.getLdsyqxz().toString());
        Assert.assertEquals("ldsyqxzmc",bdcLqDO.getLdsyqxzmc());
        Assert.assertEquals("2",bdcLqDO.getQy().toString());
        Assert.assertEquals("qymc",bdcLqDO.getQymc());
        Assert.assertEquals("发包方",bdcLqDO.getFbf());
    }

}