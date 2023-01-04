package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
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
import java.util.*;


/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/12/3.
 * @description 从原项目加载数据到查封 测试
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
public class InitYxmToBdcCfServiceImplTest {
    @Autowired
    private InitYxmToBdcCfServiceImpl initYxmToBdcCfService;

    @Transactional(transactionManager = "transactionManager")
    @Test
    @Rollback
    @DatabaseSetup(value = "/data/InitYxmToBdcCfSetup-data.xml")
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

        BdcCfDO bdcCfDO = (BdcCfDO) initYxmToBdcCfService.initQlxx(initServiceQO);

        Assert.assertEquals(bdcXmDO.getSlbh(), bdcCfDO.getSlbh());
        Assert.assertEquals(bdcXmDO.getXmid(), bdcCfDO.getXmid());
        Assert.assertEquals(bdcXmDO.getBz(), bdcCfDO.getBz());
        Assert.assertEquals(bdcXmDO.getBdcdyh(), bdcCfDO.getBdcdyh());
        Assert.assertEquals(bdcXmDO.getBdcdywybh(), bdcCfDO.getBdcdywybh());
        Assert.assertEquals(bdcXmDO.getZl(), bdcCfDO.getZl());

        Assert.assertEquals("权利人、权利人、权利人", bdcCfDO.getBzxr());

        initServiceQO.setYxmid("12321");
        bdcCfDO = (BdcCfDO) initYxmToBdcCfService.initQlxx(initServiceQO);
        Assert.assertEquals("cfjg", bdcCfDO.getCfjg());
        Assert.assertEquals("cffw", bdcCfDO.getCffw());
        Assert.assertEquals("查封原因", bdcCfDO.getCfyy());
        Assert.assertEquals("gyqk", bdcCfDO.getGyqk());
        Assert.assertEquals("t4h00t2h4t", bdcCfDO.getCfwj());
        Assert.assertEquals("5435354232652", bdcCfDO.getBdcdyh());
        Assert.assertEquals("43534652636", bdcCfDO.getBdcdywybh());

        InitServiceDTO initServiceDTO = new InitServiceDTO();
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            BdcQlrDO bdcQlrDO = new BdcQlrDO();
            bdcQlrDO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            bdcQlrDO.setQlrmc("被执行人");
            bdcQlrDOList.add(bdcQlrDO);
        }
        initServiceDTO.setBdcQlrList(bdcQlrDOList);
        Map<String, InitServiceDTO> map = new HashMap<>();
        map.put("12321", initServiceDTO);
        initServiceQO.setServiceDataMap(map);
        bdcCfDO = (BdcCfDO) initYxmToBdcCfService.initQlxx(initServiceQO);
        Assert.assertEquals("被执行人、被执行人、被执行人", bdcCfDO.getBzxr());
    }

}