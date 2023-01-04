package cn.gtmap.realestate.init.service.djbxx.impl;

import cn.gtmap.realestate.common.core.domain.BdcBdcdjbDO;
import cn.gtmap.realestate.common.core.domain.BdcBdcdjbZdjbxxDO;
import cn.gtmap.realestate.common.core.domain.BdcBdcdjbZhjbxxDO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdDjdcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZhDjdcbResponseDTO;
import cn.gtmap.realestate.init.App;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.apache.commons.lang3.StringUtils;
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

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/10
 * @description 登记簿信息初始化测试
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
public class InitBdcdjbServiceImplTest {
    @Autowired
    private InitBdcdjbServiceImpl initBdcdjbService;

    @Test
    public void getVal() throws Exception {
    }

    @Test
    public void initBdcdjb() throws Exception {
        InitServiceQO initService = new InitServiceQO();
        DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();
        ZdDjdcbResponseDTO zdDjdcb = new ZdDjdcbResponseDTO();
        zdDjdcb.setDjh("1234567890123456789");
        djxxResponseDTO.setDjDcbResponseDTO(zdDjdcb);
        initService.setDjxxResponseDTO(djxxResponseDTO);

        BdcBdcdjbDO bdcBdcdjbDO = initBdcdjbService.initBdcdjb(initService);
        Assert.assertEquals(zdDjdcb.getDjh(), bdcBdcdjbDO.getZdzhh());
        Assert.assertEquals("120000", bdcBdcdjbDO.getSqs());
        Assert.assertEquals("123400", bdcBdcdjbDO.getSq());
        Assert.assertEquals(StringUtils.substring(zdDjdcb.getDjh(), 0, 6), bdcBdcdjbDO.getXsq());
        Assert.assertEquals(StringUtils.substring(zdDjdcb.getDjh(), 0, 9), bdcBdcdjbDO.getJd());
        Assert.assertEquals(StringUtils.substring(zdDjdcb.getDjh(), 0, 12), bdcBdcdjbDO.getJf());
    }

    @Test
    public void initBdcdjbZdjbxx() throws Exception {
        InitServiceQO initService = new InitServiceQO();
        DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();
        ZdDjdcbResponseDTO zdDjdcb = new ZdDjdcbResponseDTO();
        zdDjdcb.setDjh("1234567890123456789");
        zdDjdcb.setBdcdyh("1234567890123456789012345678");
        zdDjdcb.setQslx("qslx");
        zdDjdcb.setTdzl("土地坐落");
        zdDjdcb.setFzmj(Double.parseDouble("10000"));
        zdDjdcb.setScmj(Double.parseDouble("9999"));
        zdDjdcb.setMjdw(1);
        zdDjdcb.setTdyt("9");
        zdDjdcb.setDj("8");
        zdDjdcb.setQdjg(Double.parseDouble("431.123"));
        zdDjdcb.setQsxz("11");
        zdDjdcb.setSyqlx("22");
        zdDjdcb.setQlsdfs("33");
        zdDjdcb.setJzrjl(Double.parseDouble("0.001"));
        zdDjdcb.setJzmd(Double.parseDouble("0.02"));
        zdDjdcb.setJzxg(Double.parseDouble("0.3"));
        zdDjdcb.setZdszd("长白山东麓");
        zdDjdcb.setZdszn("南沙群岛南侧");
        zdDjdcb.setZdszx("乔格尔峰西侧");
        zdDjdcb.setZdszb("漠河北侧");
        zdDjdcb.setSztfh("321123");
        zdDjdcb.setBdcdyhzt("44");
        djxxResponseDTO.setDjDcbResponseDTO(zdDjdcb);
        initService.setDjxxResponseDTO(djxxResponseDTO);

        BdcBdcdjbZdjbxxDO bdcBdcdjbZdjbxx = initBdcdjbService.initBdcdjbZdjbxx(initService);
        Assert.assertEquals(zdDjdcb.getDjh(), bdcBdcdjbZdjbxx.getZddm());
        Assert.assertEquals(zdDjdcb.getBdcdyh(), bdcBdcdjbZdjbxx.getBdcdyh());
        Assert.assertEquals(zdDjdcb.getQslx(), bdcBdcdjbZdjbxx.getZdtzm());
        Assert.assertEquals(zdDjdcb.getTdzl(), bdcBdcdjbZdjbxx.getZl());
        Assert.assertEquals(zdDjdcb.getFzmj(), bdcBdcdjbZdjbxx.getZdmj());
        Assert.assertEquals(zdDjdcb.getMjdw(), bdcBdcdjbZdjbxx.getMjdw());
        Assert.assertEquals(zdDjdcb.getTdyt(), bdcBdcdjbZdjbxx.getYt().toString());
        Assert.assertEquals(zdDjdcb.getDj(), bdcBdcdjbZdjbxx.getDj().toString());
        Assert.assertEquals(zdDjdcb.getQdjg(), bdcBdcdjbZdjbxx.getJg());
        Assert.assertEquals(zdDjdcb.getQsxz(), bdcBdcdjbZdjbxx.getQllx().toString());
        Assert.assertEquals(zdDjdcb.getSyqlx(), bdcBdcdjbZdjbxx.getQlxz().toString());
        Assert.assertEquals(zdDjdcb.getQlsdfs(), bdcBdcdjbZdjbxx.getQlsdfs().toString());
        Assert.assertEquals(zdDjdcb.getJzrjl(), bdcBdcdjbZdjbxx.getRjl());
        Assert.assertEquals(zdDjdcb.getJzmd(), bdcBdcdjbZdjbxx.getJzmd());
        Assert.assertEquals(zdDjdcb.getJzxg(), bdcBdcdjbZdjbxx.getJzxg());
        Assert.assertEquals(zdDjdcb.getZdszd(), bdcBdcdjbZdjbxx.getZdszd());
        Assert.assertEquals(zdDjdcb.getZdszn(), bdcBdcdjbZdjbxx.getZdszn());
        Assert.assertEquals(zdDjdcb.getZdszx(), bdcBdcdjbZdjbxx.getZdszx());
        Assert.assertEquals(zdDjdcb.getZdszb(), bdcBdcdjbZdjbxx.getZdszb());
        Assert.assertEquals(zdDjdcb.getSztfh(), bdcBdcdjbZdjbxx.getTfh());
    }

    @Test
    public void initBdcdjbZhjbxx() throws Exception {
        InitServiceQO initService = new InitServiceQO();
        DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();
        ZhDjdcbResponseDTO zhDjdcb = new ZhDjdcbResponseDTO();
        zhDjdcb.setZhdm("1234567890123456789");
        zhDjdcb.setBdcdyh("1234567890123456789012345678");
        zhDjdcb.setZhtzm("11");
        zhDjdcb.setXmmc("项目名称");
        zhDjdcb.setXmxz("22");
        zhDjdcb.setYhzmj(Double.parseDouble("999999"));
        zhDjdcb.setZhmj(Double.parseDouble("8007.344"));
        zhDjdcb.setDb("33");
        zhDjdcb.setZhax(Double.parseDouble("123.45"));
        zhDjdcb.setYhlxa("44");
        zhDjdcb.setYhlxb("55");
        zhDjdcb.setYhwzsm("海带缠潜艇");
        zhDjdcb.setHdmc("雾霾防激光");
        zhDjdcb.setHddm("0987654321");
        zhDjdcb.setYdfw("全岛");
        zhDjdcb.setYdmj(Double.parseDouble("432.3"));
        zhDjdcb.setHdwz("南海海域");
        zhDjdcb.setHdyt("66");
        djxxResponseDTO.setDjDcbResponseDTO(zhDjdcb);
        initService.setDjxxResponseDTO(djxxResponseDTO);
        BdcBdcdjbZhjbxxDO bdcBdcdjbZhjbxx = initBdcdjbService.initBdcdjbZhjbxx(initService);

        Assert.assertEquals(zhDjdcb.getZhdm(), bdcBdcdjbZhjbxx.getZhdm());
        Assert.assertEquals(zhDjdcb.getBdcdyh(), bdcBdcdjbZhjbxx.getBdcdyh());
        Assert.assertEquals(zhDjdcb.getZhtzm(), bdcBdcdjbZhjbxx.getZhtzm());
        Assert.assertEquals(zhDjdcb.getXmmc(), bdcBdcdjbZhjbxx.getXmmc());
        Assert.assertEquals(zhDjdcb.getXmxz(), bdcBdcdjbZhjbxx.getXmxz().toString());
        Assert.assertEquals(zhDjdcb.getYhzmj(), bdcBdcdjbZhjbxx.getYhzmj());
        Assert.assertEquals(zhDjdcb.getZhmj(), bdcBdcdjbZhjbxx.getZhmj());
        Assert.assertEquals(zhDjdcb.getDb(), bdcBdcdjbZhjbxx.getDb().toString());
        Assert.assertEquals(zhDjdcb.getZhax(), bdcBdcdjbZhjbxx.getZhax());
        Assert.assertEquals(zhDjdcb.getYhlxa(), bdcBdcdjbZhjbxx.getYhlxa().toString());
        Assert.assertEquals(zhDjdcb.getYhlxb(), bdcBdcdjbZhjbxx.getYhlxb().toString());
        Assert.assertEquals(zhDjdcb.getHdmc(), bdcBdcdjbZhjbxx.getHdmc());
        Assert.assertEquals(zhDjdcb.getHddm(), bdcBdcdjbZhjbxx.getHddm());
        Assert.assertEquals(zhDjdcb.getYdmj(), bdcBdcdjbZhjbxx.getYdmj());
    }
}