package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.realestate.certificate.core.bo.BdcBdcqzhBO;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.mockito.Mockito.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/11
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MockServletContext.class)
@WebAppConfiguration
public class BdcBdcqzhMrServiceImplTest {
    private BdcBdcqzhMrServiceImpl bdcqzhMrService;

    @Mock
    protected BdcZsService bdcZsService;

    @Before
    public void setUp() throws Exception{
        //模拟打桩生成目标测试类
        bdcqzhMrService = mock(BdcBdcqzhMrServiceImpl.class);
    }

    @Test
    public void testResolveBdcqzh() throws Exception {
        // 模拟获取顺序号
        when(bdcZsService.queryMaxSxh(any(BdcBdcqzhBO.class))).thenReturn(0);

        // 调用目标方法
        BdcBdcqzhBO bdcBdcqzhBO = mock(BdcBdcqzhBO.class);
        when(bdcBdcqzhBO.getZslx()).thenReturn(1);
        when(bdcBdcqzhBO.getSqsjc()).thenReturn("皖");
        when(bdcBdcqzhBO.getNf()).thenReturn("2018");
        when(bdcBdcqzhBO.getSzsxqc()).thenReturn("合肥市");
        when(bdcBdcqzhBO.getBdcqzhws()).thenReturn(7);
        when(bdcBdcqzhBO.getCssxh()).thenReturn(0);
        when(bdcBdcqzhBO.getSqdm()).thenReturn("34");

        // 调用目标方法
        BdcBdcqzhDTO bdcBdcqzhDTO = bdcqzhMrService.resolveBdcqzh(bdcBdcqzhBO);
        verify(bdcqzhMrService).resolveBdcqzh(bdcBdcqzhBO);
    }
}