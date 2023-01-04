package cn.gtmap.realestate.init.service.qlxx.qlfl.impl;

import cn.gtmap.realestate.common.core.domain.BdcTdcbnydsyqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.GzwDcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.NydDjdcbResponseDTO;
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
 * @version 1.0  2018/11/29.
 * @description 从楼盘表加载数据到土地承包经营权、农用地使用权测试
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
public class InitLpbToBdcTdcbnydsyqServiceImplTest {
    @Autowired
    private InitLpbToBdcTdcbnydsyqServiceImpl initLpbToBdcTdcbnydsyqService;

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
        NydDjdcbResponseDTO nydDjdcbResponseDTO = new NydDjdcbResponseDTO();
        nydDjdcbResponseDTO.setNydDjdcbIndex("111");
        nydDjdcbResponseDTO.setCbzdsyttlx("432");
        nydDjdcbResponseDTO.setCbzdyzyfs("625");
        nydDjdcbResponseDTO.setCbzdsyzxl(((Integer) 642652).longValue());
        nydDjdcbResponseDTO.setScmj(Double.parseDouble("76643726"));
        nydDjdcbResponseDTO.setSyqlx("6625");
        nydDjdcbResponseDTO.setQsrq(new Date());
        nydDjdcbResponseDTO.setZzrq(new Date());
        nydDjdcbResponseDTO.setCbzdccgd(Double.parseDouble("4.13"));
        nydDjdcbResponseDTO.setCbzdcdysz("h49g3r2g9t");
        nydDjdcbResponseDTO.setCbzdcdjq("h4665h5w");
        nydDjdcbResponseDTO.setCbzdcdfgd(Double.parseDouble("0.99"));
        djxxResponseDTO.setDjDcbResponseDTO(nydDjdcbResponseDTO);
        initServiceQO.setDjxxResponseDTO(djxxResponseDTO);
        initServiceQO.setGzwDcbResponseDTO(new GzwDcbResponseDTO());

        BdcTdcbnydsyqDO bdcTdcbnydsyqDO = (BdcTdcbnydsyqDO) initLpbToBdcTdcbnydsyqService.initQlxx(initServiceQO);
        Assert.assertEquals(bdcXmDO.getSlbh(), bdcTdcbnydsyqDO.getSlbh());
        Assert.assertEquals(bdcXmDO.getXmid(), bdcTdcbnydsyqDO.getXmid());
        Assert.assertEquals(bdcXmDO.getQllx(), bdcTdcbnydsyqDO.getQllx());
        Assert.assertEquals(bdcXmDO.getDjlx(), bdcTdcbnydsyqDO.getDjlx());
        Assert.assertEquals(bdcXmDO.getBz(), bdcTdcbnydsyqDO.getBz());
        Assert.assertEquals(bdcXmDO.getBdcdyh(), bdcTdcbnydsyqDO.getBdcdyh());
        Assert.assertEquals(bdcXmDO.getBdcdywybh(), bdcTdcbnydsyqDO.getBdcdywybh());
        Assert.assertEquals(bdcXmDO.getZl(), bdcTdcbnydsyqDO.getZl());
        Assert.assertEquals(bdcXmDO.getDjyy(), bdcTdcbnydsyqDO.getDjyy());

        Assert.assertEquals(nydDjdcbResponseDTO.getCbzdsyttlx(), bdcTdcbnydsyqDO.getSyttlx().toString());
        Assert.assertEquals(nydDjdcbResponseDTO.getCbzdyzyfs(), bdcTdcbnydsyqDO.getYzyfs().toString());
        Assert.assertEquals(nydDjdcbResponseDTO.getCbzdsyzxl(), bdcTdcbnydsyqDO.getSyzcl());
        Assert.assertEquals(nydDjdcbResponseDTO.getScmj(), bdcTdcbnydsyqDO.getCbmj());
        Assert.assertEquals(nydDjdcbResponseDTO.getSyqlx(), bdcTdcbnydsyqDO.getTdsyqxz().toString());
        Assert.assertEquals(nydDjdcbResponseDTO.getQsrq().toString(), bdcTdcbnydsyqDO.getCbqssj().toString());
        Assert.assertEquals(nydDjdcbResponseDTO.getZzrq().toString(), bdcTdcbnydsyqDO.getCbjssj().toString());
        Assert.assertEquals(nydDjdcbResponseDTO.getCbzdccgd(), bdcTdcbnydsyqDO.getCdgd());
        Assert.assertEquals(nydDjdcbResponseDTO.getCbzdcdysz(), bdcTdcbnydsyqDO.getCdysz());
        Assert.assertEquals(nydDjdcbResponseDTO.getCbzdcdjq(), bdcTdcbnydsyqDO.getCdjq());
        Assert.assertEquals(nydDjdcbResponseDTO.getCbzdcdfgd().toString(), bdcTdcbnydsyqDO.getCdfgd());

    }

}