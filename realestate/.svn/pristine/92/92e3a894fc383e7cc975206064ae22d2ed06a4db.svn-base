package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcQtxgqlDO;
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


/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/12/4.
 * @description 从原项目加载数据到其他相关权利 测试
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
public class InitYxmToBdcQtxgqlServiceImplTest {
    @Autowired
    private InitYxmToBdcQtxgqlServiceImpl initYxmToBdcQtxgqlService;

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/InitYxmToBdcQtxgqlSetup-data.xml")
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

        BdcQtxgqlDO bdcQtxgqlDO =(BdcQtxgqlDO)initYxmToBdcQtxgqlService.initQlxx(initServiceQO);

        Assert.assertEquals(bdcXmDO.getSlbh(), bdcQtxgqlDO.getSlbh());
        Assert.assertEquals(bdcXmDO.getDjlx(), bdcQtxgqlDO.getDjlx());
        Assert.assertEquals(bdcXmDO.getDjyy(), bdcQtxgqlDO.getDjyy());
        Assert.assertEquals(bdcXmDO.getXmid(), bdcQtxgqlDO.getXmid());
        Assert.assertEquals(bdcXmDO.getQllx(), bdcQtxgqlDO.getQllx());
        Assert.assertEquals(bdcXmDO.getBdcdyh(), bdcQtxgqlDO.getBdcdyh());
        Assert.assertEquals(bdcXmDO.getBdcdywybh(), bdcQtxgqlDO.getBdcdywybh());
        Assert.assertEquals(bdcXmDO.getZl(), bdcQtxgqlDO.getZl());
        Assert.assertEquals(bdcXmDO.getBz(), bdcQtxgqlDO.getBz());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals("sylx",bdcQtxgqlDO.getSylx());
        Assert.assertEquals("qsfs",bdcQtxgqlDO.getQsfs());
        Assert.assertEquals("9999.34",bdcQtxgqlDO.getQsl().toString());
        Assert.assertEquals("qsyt",bdcQtxgqlDO.getQsyt());
        Assert.assertEquals("754745.5",bdcQtxgqlDO.getKcmj().toString());
        Assert.assertEquals("开采矿种",bdcQtxgqlDO.getKckz());
        Assert.assertEquals("开采方式",bdcQtxgqlDO.getKcfs());
        Assert.assertEquals("scgm",bdcQtxgqlDO.getScgm());
        Assert.assertEquals(sdf.parse("2098-11-13 1:45:54"),bdcQtxgqlDO.getQljssj());
        Assert.assertEquals(sdf.parse("2018-11-13 1:45:54"),bdcQtxgqlDO.getQlqssj());
    }

}