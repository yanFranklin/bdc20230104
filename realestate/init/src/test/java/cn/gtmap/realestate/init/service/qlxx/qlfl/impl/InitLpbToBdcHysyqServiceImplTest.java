package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcHysyqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.GzwDcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZhDjdcbResponseDTO;
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

import java.util.Date;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/15
 * @description 从权籍加载数据到海域所有权
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
public class InitLpbToBdcHysyqServiceImplTest {
    @Autowired
    private InitLpbToBdcHysyqServiceImpl initLpbToBdcHysyqService;

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
        ZhDjdcbResponseDTO zhDjdcbResponseDTO = new ZhDjdcbResponseDTO();
        zhDjdcbResponseDTO.setQsrq(new Date());
        zhDjdcbResponseDTO.setZzrq(new Date());
        zhDjdcbResponseDTO.setSyjjnqk("syjjnqk");
        zhDjdcbResponseDTO.setXmmc("zhxmmc");
        djxxResponseDTO.setDjDcbResponseDTO(zhDjdcbResponseDTO);
        initServiceQO.setDjxxResponseDTO(djxxResponseDTO);

        initServiceQO.setGzwDcbResponseDTO(new GzwDcbResponseDTO());

        BdcHysyqDO bdcHysyqDO = (BdcHysyqDO) initLpbToBdcHysyqService.initQlxx(initServiceQO);
        Assert.assertEquals(bdcXmDO.getSlbh(), bdcHysyqDO.getSlbh());
        Assert.assertEquals(bdcXmDO.getXmid(), bdcHysyqDO.getXmid());
        Assert.assertEquals(bdcXmDO.getQllx().toString(), bdcHysyqDO.getQllx());
        Assert.assertEquals(bdcXmDO.getDjlx(), bdcHysyqDO.getDjlx());
        Assert.assertEquals(bdcXmDO.getBz(), bdcHysyqDO.getBz());
        Assert.assertEquals(bdcXmDO.getBdcdyh(), bdcHysyqDO.getBdcdyh());
        Assert.assertEquals(bdcXmDO.getBdcdywybh(), bdcHysyqDO.getBdcdywybh());
        Assert.assertEquals(bdcXmDO.getZl(), bdcHysyqDO.getZl());

        Assert.assertEquals(zhDjdcbResponseDTO.getQsrq().toString(), bdcHysyqDO.getSyqqssj().toString());
        Assert.assertEquals(zhDjdcbResponseDTO.getZzrq().toString(), bdcHysyqDO.getSyqjssj().toString());
        Assert.assertEquals(zhDjdcbResponseDTO.getSyjjnqk(), bdcHysyqDO.getSyjjnqk());
        Assert.assertEquals(zhDjdcbResponseDTO.getXmmc(), bdcHysyqDO.getXmmc());
    }

}