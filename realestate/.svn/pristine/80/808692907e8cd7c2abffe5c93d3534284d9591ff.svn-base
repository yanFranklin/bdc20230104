package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.realestate.certificate.core.bo.BdcBdcqzhBO;
import cn.gtmap.realestate.certificate.core.service.BdcXtFhService;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtZsfhDO;
import cn.gtmap.realestate.common.core.domain.BdcXtZsbhmbDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXtZsfhDTO;
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
 * @description 回收废号方式获取不动产权证号逻辑类测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MockServletContext.class)
@WebAppConfiguration
public class BdcBdcqzhFhServiceImplTest {
    private BdcBdcqzhFhServiceImpl bdcqzhFhService;

    @Mock
    private BdcXtFhService bdcXtFhService;


    @Before
    public void setUp() throws Exception{
        //模拟打桩生成目标测试类
        bdcqzhFhService = mock(BdcBdcqzhFhServiceImpl.class);
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 从废号中获取可用证号 测试
     */
    @Test
    public void testResolveBdcqzh() throws Exception {
        // 模拟获取证号
        BdcXtZsfhDO bdcXtZsfhDO = new BdcXtZsfhDO();
        bdcXtZsfhDO.setFczhid("1");
        bdcXtZsfhDO.setXmid("UNIT_XM_12312321");
        bdcXtZsfhDO.setBdcqzh("皖(2018)合肥市不动产权第0000012号");
        bdcXtZsfhDO.setZhlsh("0000012");
        bdcXtZsfhDO.setNf("2018");
        bdcXtZsfhDO.setZslx(1);
        bdcXtZsfhDO.setQxdm("340100");
        bdcXtZsfhDO.setSqsjc("皖");
        bdcXtZsfhDO.setSzsxqc("合肥市");
        when(bdcXtFhService.queryBdcXtFh(any(BdcXtZsfhDTO.class))).thenReturn(bdcXtZsfhDO);

        // 模拟更新证号
        when(bdcXtFhService.updateBdcXtFhSyqk(any(BdcXtZsfhDO.class))).thenReturn(1);

        // 调用目标方法
        BdcBdcqzhBO bdcBdcqzhBO = mock(BdcBdcqzhBO.class);
        BdcBdcqzhDTO bdcBdcqzhDTO = bdcqzhFhService.resolveBdcqzh(bdcBdcqzhBO);
        verify(bdcqzhFhService).resolveBdcqzh(bdcBdcqzhBO);
    }
}