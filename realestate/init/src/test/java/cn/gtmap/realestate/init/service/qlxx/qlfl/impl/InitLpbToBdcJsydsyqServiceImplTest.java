package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcJsydsyqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.GzwDcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdDjdcbResponseDTO;
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
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2018/11/15
 * @description 从权籍加载数据到建设用地使用权 测试
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
public class InitLpbToBdcJsydsyqServiceImplTest {
    @Autowired
    private InitLpbToBdcJsydsyqServiceImpl initLpbToBdcJsydsyqService;

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
        ZdDjdcbResponseDTO zdDjdcbResponseDTO = new ZdDjdcbResponseDTO();
        zdDjdcbResponseDTO.setZdDjdcbIndex("111");
        zdDjdcbResponseDTO.setQdjg(Double.parseDouble("65643"));
        zdDjdcbResponseDTO.setQsrq(new Date());
        zdDjdcbResponseDTO.setZzrq(new Date());
        zdDjdcbResponseDTO.setScmj(Double.parseDouble("6735"));
        zdDjdcbResponseDTO.setFzmj(Double.parseDouble("32"));
        zdDjdcbResponseDTO.setQsxz("343496");
        zdDjdcbResponseDTO.setBdcdyh("024hng34h");
        zdDjdcbResponseDTO.setDjh("heg904hg8p9e");
        zdDjdcbResponseDTO.setTdzl("ht45ngpb");
        zdDjdcbResponseDTO.setTdsyzmc("34h890gb9");
        zdDjdcbResponseDTO.setQlsdfs("64");
        zdDjdcbResponseDTO.setSyqlx("57");
        djxxResponseDTO.setDjDcbResponseDTO(zdDjdcbResponseDTO);
        initServiceQO.setDjxxResponseDTO(djxxResponseDTO);
        initServiceQO.setGzwDcbResponseDTO(new GzwDcbResponseDTO());

        BdcJsydsyqDO bdcJsydsyqDO = (BdcJsydsyqDO) initLpbToBdcJsydsyqService.initQlxx(initServiceQO);
        Assert.assertEquals(bdcXmDO.getSlbh(), bdcJsydsyqDO.getSlbh());
        Assert.assertEquals(bdcXmDO.getXmid(), bdcJsydsyqDO.getXmid());
        Assert.assertEquals(bdcXmDO.getQllx(), bdcJsydsyqDO.getQllx());
        Assert.assertEquals(bdcXmDO.getDjlx(), bdcJsydsyqDO.getDjlx());
        Assert.assertEquals(bdcXmDO.getBz(), bdcJsydsyqDO.getBz());
        Assert.assertEquals(bdcXmDO.getBdcdyh(), bdcJsydsyqDO.getBdcdyh());
        Assert.assertEquals(bdcXmDO.getBdcdywybh(), bdcJsydsyqDO.getBdcdywybh());
        Assert.assertEquals(bdcXmDO.getZl(), bdcJsydsyqDO.getZl());
        Assert.assertEquals(bdcXmDO.getDjyy(), bdcJsydsyqDO.getDjyy());

        Assert.assertEquals(zdDjdcbResponseDTO.getQdjg().toString(),bdcJsydsyqDO.getQdjg().toString());
        Assert.assertEquals(zdDjdcbResponseDTO.getQsrq().toString(),bdcJsydsyqDO.getSyqqssj().toString());
        Assert.assertEquals(zdDjdcbResponseDTO.getZzrq().toString(),bdcJsydsyqDO.getSyqjssj().toString());
        Assert.assertEquals(zdDjdcbResponseDTO.getFzmj(),bdcJsydsyqDO.getSyqmj());
        Assert.assertEquals(zdDjdcbResponseDTO.getTdsyzmc(),bdcJsydsyqDO.getTdsyqr());
        Assert.assertEquals(zdDjdcbResponseDTO.getQlsdfs(),bdcJsydsyqDO.getQlsdfs().toString());
        Assert.assertEquals(zdDjdcbResponseDTO.getSyqlx(),bdcJsydsyqDO.getQlxz().toString());
    }

}