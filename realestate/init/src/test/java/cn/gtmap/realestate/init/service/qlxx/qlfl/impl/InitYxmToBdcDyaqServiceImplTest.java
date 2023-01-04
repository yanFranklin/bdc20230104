package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
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
 * @version 1.0  2018/12/3.
 * @description 从原项目加载数据到抵押权 测试
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
public class InitYxmToBdcDyaqServiceImplTest {
    @Autowired
    private InitYxmToBdcDyaqServiceImpl initYxmToBdcDyaqService;

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/InitYxmToBdcDyaSetup-data.xml")
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

        BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) initYxmToBdcDyaqService.initQlxx(initServiceQO);

        Assert.assertEquals(bdcXmDO.getSlbh(), bdcDyaqDO.getSlbh());
        Assert.assertEquals(bdcXmDO.getXmid(), bdcDyaqDO.getXmid());
        Assert.assertEquals(bdcXmDO.getDjlx(), bdcDyaqDO.getDjlx());
        Assert.assertEquals(bdcXmDO.getDjyy(), bdcDyaqDO.getDjyy());
        Assert.assertEquals(bdcXmDO.getBdcdyh(), bdcDyaqDO.getBdcdyh());
        Assert.assertEquals(bdcXmDO.getBdcdywybh(), bdcDyaqDO.getBdcdywybh());
        Assert.assertEquals(bdcXmDO.getZl(), bdcDyaqDO.getZl());

        Assert.assertEquals("共有情况", bdcDyaqDO.getGyqk());
        Assert.assertEquals("4", bdcDyaqDO.getDyfs().toString());
        Assert.assertEquals("5345.63", bdcDyaqDO.getBdbzzqse().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(sdf.parse("2018-11-13 01:45:54"), bdcDyaqDO.getZwlxqssj());
        Assert.assertEquals(sdf.parse("2022-11-13 01:45:54"), bdcDyaqDO.getZwlxjssj());

        initServiceQO.setYxmid("12321");

        bdcDyaqDO = (BdcDyaqDO) initYxmToBdcDyaqService.initQlxx(initServiceQO);

        Assert.assertEquals("债务人", bdcDyaqDO.getZwr());
        Assert.assertEquals("2", bdcDyaqDO.getDyfs().toString());
        Assert.assertEquals("zjjzwzl", bdcDyaqDO.getZjjzwzl());
        Assert.assertEquals("zjjzwdyfw", bdcDyaqDO.getZjjzwdyfw());
        Assert.assertEquals(sdf.parse("2018-11-13 01:45:54"), bdcDyaqDO.getZwlxqssj());
        Assert.assertEquals(sdf.parse("2022-11-13 01:45:54"), bdcDyaqDO.getZwlxjssj());
        Assert.assertEquals("zgzqqdss", bdcDyaqDO.getZgzqqdss());
        Assert.assertEquals("312.54", bdcDyaqDO.getZgzqqdse().toString());
        Assert.assertEquals("665666546", bdcDyaqDO.getZxdyywh());
        Assert.assertEquals("zxdyyy", bdcDyaqDO.getZxdyyy());
        Assert.assertEquals(sdf.parse("2022-11-13 01:45:54"), bdcDyaqDO.getZxdydjsj());
        Assert.assertEquals("zxdydbr", bdcDyaqDO.getZxdydbr());
        Assert.assertEquals("dyagyqk", bdcDyaqDO.getGyqk());
        Assert.assertEquals("dbfw", bdcDyaqDO.getDbfw());
        Assert.assertEquals("2", bdcDyaqDO.getDysw().toString());
        Assert.assertEquals("dkfs", bdcDyaqDO.getDkfs());
        Assert.assertEquals("54345.0", bdcDyaqDO.getBdbzzqse().toString());
        Assert.assertEquals("222", bdcDyaqDO.getJezl().toString());
        Assert.assertEquals("3452.23", bdcDyaqDO.getFwpgjg().toString());
        Assert.assertEquals("63435.33", bdcDyaqDO.getTdpgjg().toString());
        Assert.assertEquals("64635.0", bdcDyaqDO.getFwdyjg().toString());
        Assert.assertEquals("525.0", bdcDyaqDO.getTddyjg().toString());
        Assert.assertEquals("523.22", bdcDyaqDO.getTddymj().toString());
        Assert.assertEquals("632.33", bdcDyaqDO.getFwdymj().toString());
        Assert.assertEquals("1", bdcDyaqDO.getSfgtdb().toString());
        Assert.assertEquals("33333.33", bdcDyaqDO.getTddymjsum().toString());
        Assert.assertEquals("43233.22", bdcDyaqDO.getFwdymjsum().toString());
    }

}