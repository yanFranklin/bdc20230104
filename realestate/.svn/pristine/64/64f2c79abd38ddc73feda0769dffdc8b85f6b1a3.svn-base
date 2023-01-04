package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.register.config.PropsConfig;
import cn.gtmap.realestate.register.core.dto.BdcRyzdDTO;
import cn.gtmap.realestate.register.core.service.BdcQlService;
import cn.gtmap.realestate.register.core.service.impl.BdcJsydsyqServiceImpl;
import cn.gtmap.realestate.register.service.BdcRyzdService;
import cn.gtmap.realestate.register.util.Constants;
import org.apache.commons.collections.MapUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/17
 * @description 冗余字段逻辑处理类测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MockServletContext.class)
@WebAppConfiguration
public class BdcRyzdServiceImplTest {
    BdcRyzdService bdcRyzdService;

    @Mock
    private EntityMapper entityMapper;
    @Mock
    private BdcQllxFeignService qllxFeignService;
    @Mock
    private BdcQlrFeignService qlrFeignService;
    @Mock
    private BdcZsFeignService zsFeignService;
    @Mock
    private PropsConfig propsConfig;
    @Mock
    private BeansResolveUtils beansResolveUtils;

    /**
     * 模拟测试数据
     */
    // 项目ID
    private String xmid = "UNIT_XM_12345";
    // 受理编号
    private String slbh = "BH_12345";
    // 国有建设用地使用权
    private Integer qllx = 3;
    // 首次登记
    private Integer djlx = 100;
    // 证书ID
    private String zsid1 = "UNIT_ZS_555";
    // 证书ID
    private String zsid2 = "UNIT_ZS_666";


    @Before
    public void setUp() throws Exception{
        //模拟打桩生成目标测试类
        bdcRyzdService = mock(BdcRyzdServiceImpl.class);
    }


    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  获取冗余字段对应的值测试
     */
    @Test
    public void testGetRyzd() throws Exception {
        // 模拟获取权利人
        List<BdcQlrDO> bdcQlrList = this.listBdcQlr();
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        when(qlrFeignService.listBdcQlr(eq(bdcQlrQO))).thenReturn(bdcQlrList);

        // 模拟获取义务人
        List<BdcQlrDO> bdcYwrList = this.listBdcYwr();
        BdcQlrQO bdcYwrQO = new BdcQlrQO();
        bdcYwrQO.setXmid(xmid);
        bdcYwrQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
        when(qlrFeignService.listBdcQlr(eq(bdcYwrQO))).thenReturn(bdcYwrList);

        // 模拟获取证书
        when(zsFeignService.queryBdcZs(eq(zsid1))).thenReturn(listBdcZsDO().get(0));
        when(zsFeignService.queryBdcZs(eq(zsid2))).thenReturn(listBdcZsDO().get(1));

        // 模拟获取权利信息
        BdcQl bdcQl = this.getBdcQl();
        when(qllxFeignService.queryQlxx(anyString())).thenReturn(bdcQl);

        // 模拟获取项目信息
        BdcXmDO bdcXmDO = this.getBdcXm();
        when(entityMapper.selectByPrimaryKey(eq(BdcXmDO.class), anyString())).thenReturn(bdcXmDO);

        // 调用目标方法
        BdcRyzdDTO bdcRyzd = bdcRyzdService.getRyzd(xmid);
        verify(bdcRyzdService).getRyzd(xmid);

        /// 需要验证返回值
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新冗余字段测试
     */
    @Test
    public void testUpdateRyzd() throws Exception {
        // 模拟更新不动产项目
        when(entityMapper.updateByPrimaryKeySelective(any(BdcXmDO.class))).thenReturn(1);
        // 模拟更新不动产权利人
        when(entityMapper.updateByPrimaryKeySelective(any(BdcQlrDO.class))).thenReturn(1);

        // 模拟获取权利信息
        BdcQl bdcQl = this.getBdcQl();
        when(qllxFeignService.queryQlxx(anyString())).thenReturn(bdcQl);

        // 调用目标方法
        BdcRyzdDTO bdcRyzd = mock(BdcRyzdDTO.class);
        bdcRyzdService.updateRyzd(bdcRyzd);
        verify(bdcRyzdService).updateRyzd(bdcRyzd);
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return  权利人集合
     * @description 模拟权利人数据
     */
    private List<BdcQlrDO> listBdcQlr(){
        BdcQlrDO bdcQlr = new BdcQlrDO();
        bdcQlr.setQlrid("UNIT_QLR_111222");
        bdcQlr.setXmid(xmid);
        bdcQlr.setQlrmc("张一");
        bdcQlr.setZjzl(1); //身份证
        bdcQlr.setZjh("320826111122223333");
        bdcQlr.setQlrlb("1");  //权利人
        bdcQlr.setZsid(zsid1);

        BdcQlrDO bdcQlr2 = new BdcQlrDO();
        bdcQlr2.setQlrid("UNIT_QLR_242432");
        bdcQlr2.setXmid(xmid);
        bdcQlr2.setQlrmc("王二");
        bdcQlr2.setZjzl(2); //港澳台
        bdcQlr2.setZjh("GAT22131232132");
        bdcQlr2.setQlrlb("1");  //权利人
        bdcQlr2.setZsid(zsid2);

        List<BdcQlrDO> list = new ArrayList<>(2);
        list.add(bdcQlr);
        list.add(bdcQlr2);

        return list;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return  义务人集合
     * @description 模拟义务人数据
     */
    private List<BdcQlrDO> listBdcYwr(){
        BdcQlrDO bdcQlr = new BdcQlrDO();
        bdcQlr.setQlrid("UNIT_YWR_333444");
        bdcQlr.setXmid(xmid);
        bdcQlr.setQlrmc("李三");
        bdcQlr.setZjzl(1); //身份证
        bdcQlr.setZjh("320826666677778888");
        bdcQlr.setQlrlb("2");  //义务人

        BdcQlrDO bdcQlr2 = new BdcQlrDO();
        bdcQlr2.setQlrid("UNIT_YWR_555666");
        bdcQlr2.setXmid(xmid);
        bdcQlr2.setQlrmc("赵五");
        bdcQlr2.setZjzl(3); //护照
        bdcQlr2.setZjh("HZ121321332");
        bdcQlr2.setQlrlb("2");  //义务人

        List<BdcQlrDO> list = new ArrayList<>(2);
        list.add(bdcQlr);
        list.add(bdcQlr2);

        return list;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return  证书集合
     * @description  模拟不动产证书数据
     */
    private List<BdcZsDO> listBdcZsDO(){
        BdcZsDO zsDO = new BdcZsDO();
        zsDO.setZsid(zsid1);
        zsDO.setBdcqzh("证号123456");
        zsDO.setZhlsh("123456");
        zsDO.setNf("2018");

        BdcZsDO zsDO2 = new BdcZsDO();
        zsDO2.setZsid(zsid2);
        zsDO2.setBdcqzh("证号2323423");
        zsDO2.setZhlsh("123457");
        zsDO2.setNf("2018");

        List<BdcZsDO> list = new ArrayList<>(2);
        list.add(zsDO);
        list.add(zsDO2);

        return list;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return  权利信息
     * @description 模拟权利信息数据
     */
    private BdcQl getBdcQl(){
        BdcJsydsyqDO jsydsyqDO = new BdcJsydsyqDO();
        jsydsyqDO.setQlid("JSYDSYQ_12333242");
        jsydsyqDO.setSlbh("BH2213231");
        jsydsyqDO.setBdcdyh("320902004004GB00018F00010012");
        jsydsyqDO.setBdcdywybh("WY320902004004GB00018F00010012");
        jsydsyqDO.setDjjg("南京市不动产登记中心");
        jsydsyqDO.setDbr("赵明");
        jsydsyqDO.setQszt(0);
        jsydsyqDO.setXmid(xmid);
        return jsydsyqDO;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return  不动产项目信息
     * @description 模拟获取不动产项目信息数据
     */
    private BdcXmDO getBdcXm(){
        //国有建设用地用地使用权首次登记
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setXmid(xmid);
        bdcXmDO.setSlbh(slbh);
        bdcXmDO.setQllx(qllx);
        bdcXmDO.setDjlx(djlx);
        bdcXmDO.setDjyy("国有建设用地使用权首次登记测试");
        bdcXmDO.setSqzsbs(0); //单一版
        bdcXmDO.setSqfbcz(0); //申请分别持证 否
        bdcXmDO.setBz("测试数据");
        bdcXmDO.setBdcdyh("320902004004GB00018F00010012");
        bdcXmDO.setZl("南京市玄武区");
        bdcXmDO.setQxdm("223111");
        bdcXmDO.setJyhth("HTH121212112");
        return bdcXmDO;
    }
}