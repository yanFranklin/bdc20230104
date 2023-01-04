package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.realestate.certificate.core.service.BdcXmService;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/11
 * @description 不动产权证号不换证处理 测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MockServletContext.class)
@WebAppConfiguration
public class BdcBdcqzhBhzServiceImplTest {
    private BdcBdcqzhBhzServiceImpl bdcqzhBhzService;

    @Mock
    private BdcXmFeignService bdcXmFeignService;
    @Mock
    protected BdcXmService bdcXmService;
    @Mock
    protected BdcZsService bdcZsService;


    @Before
    public void setUp() throws Exception{
        //模拟打桩生成目标测试类
        bdcqzhBhzService = mock(BdcBdcqzhBhzServiceImpl.class);
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 生成不动产项目证书（明）号 测试
     */
    @Test
    public void testGenerateBdcqzh() throws Exception {
        String dqxmid = "XM_12";
        String yxmid = "YXM_123";

        // 模拟获取开关项
        BdcCshFwkgSlDO bdcCshFwkgSlDO = new BdcCshFwkgSlDO();
        bdcCshFwkgSlDO.setId("1");
        bdcCshFwkgSlDO.setSfhz(0);
        when(bdcXmFeignService.queryFwkgsl(anyString())).thenReturn(bdcCshFwkgSlDO);

        // 模拟获取证书
        // --当前项目证书
        List<BdcZsDO> dqBdcZsDOList = new ArrayList<>(1);
        BdcZsDO dqBdcZsDO = new BdcZsDO();
        dqBdcZsDO.setZsid("ZS_123");
        dqBdcZsDOList.add(dqBdcZsDO);
        when(bdcZsService.queryBdcZsByXmid(eq(dqxmid))).thenReturn(dqBdcZsDOList);
        // -- 原项目证书
        List<BdcZsDO> yBdcZsDOList = new ArrayList<>(1);
        BdcZsDO yBdcZsDO = new BdcZsDO();
        yBdcZsDO.setZsid("YZS_123");
        yBdcZsDOList.add(yBdcZsDO);
        when(bdcZsService.queryBdcZsByXmid(eq(yxmid))).thenReturn(yBdcZsDOList);

        // 模拟查询项目历史关系
        List<BdcXmLsgxDO> bdcXmLsgxDOList = new ArrayList<>(1);
        BdcXmLsgxDO bdcXmLsgxDO = new BdcXmLsgxDO();
        bdcXmLsgxDO.setGxid("1");
        bdcXmLsgxDO.setXmid(dqxmid);
        bdcXmLsgxDO.setYxmid(yxmid);
        bdcXmLsgxDOList.add(bdcXmLsgxDO);
        when(bdcXmService.queryBdcXmLsgxByXmid(anyString())).thenReturn(bdcXmLsgxDOList);

        // 更新证书
        when(bdcZsService.updateBdcZs(any(BdcZsDO.class))).thenReturn(1);

        // 执行目标方法
        List<BdcBdcqzhDTO> bdcBdcqzhDTOList = bdcqzhBhzService.generateBdcqzh(dqxmid);
        verify(bdcqzhBhzService).generateBdcqzh(dqxmid);
    }
}