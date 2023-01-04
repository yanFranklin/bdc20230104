package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.realestate.certificate.core.bo.BdcBdcqzhBO;
import cn.gtmap.realestate.certificate.core.service.BdcXtYlzhService;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtYlzhDO;
import cn.gtmap.realestate.common.core.domain.BdcXtZsbhmbDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/11
 * @description 以预留证号方式处理不动产权证号测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MockServletContext.class)
@WebAppConfiguration
public class BdcBdcqzhYlzhServiceImplTest {
    private BdcBdcqzhYlzhServiceImpl bdcqzhYlzhService;

    @Mock
    protected BdcXtYlzhService bdcXtYlzhService;


    @Before
    public void setUp() throws Exception{
        //模拟打桩生成目标测试类
        bdcqzhYlzhService = mock(BdcBdcqzhYlzhServiceImpl.class);
    }

    @Test
    public void testResolveBdcqzh() throws Exception {
        BdcXtYlzhDO bdcXtYlzhDO = mock(BdcXtYlzhDO.class);
        when(bdcXtYlzhDO.getYlzhid()).thenReturn("YLSH_123");
        when(bdcXtYlzhDO.getBdcqzh()).thenReturn("皖(2018)合肥市不动产权第0000003号");
        when(bdcXtYlzhDO.getSqsjc()).thenReturn("皖");
        when(bdcXtYlzhDO.getNf()).thenReturn("2018");
        when(bdcXtYlzhDO.getSzsxqc()).thenReturn("合肥市");
        when(bdcXtYlzhDO.getZslx()).thenReturn(1);
        when(bdcXtYlzhDO.getZhlsh()).thenReturn("0000003");

        // 模拟获取预留证号
        when(bdcXtYlzhService.queryBdcXtYlzh(anyString())).thenReturn(bdcXtYlzhDO);
        // 模拟更新状态
        when(bdcXtYlzhService.updateBdcXtYlzhSyqk(any(BdcXtYlzhDO.class))).thenReturn(1);

        // 调用目标方法
        BdcBdcqzhBO bdcBdcqzhBO = mock(BdcBdcqzhBO.class);
        when(bdcBdcqzhBO.getZsid()).thenReturn("ZS_123");
        BdcXtZsbhmbDO bdcXtZsbhmbDO = mock(BdcXtZsbhmbDO.class);
        BdcBdcqzhDTO bdcBdcqzhDTO = bdcqzhYlzhService.resolveBdcqzh(bdcBdcqzhBO);
        verify(bdcqzhYlzhService).resolveBdcqzh(bdcBdcqzhBO);
    }
}