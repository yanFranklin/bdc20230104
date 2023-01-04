package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.realestate.certificate.core.bo.BdcBdcqzhBO;
import cn.gtmap.realestate.certificate.core.service.BdcXmService;
import cn.gtmap.realestate.certificate.core.service.BdcXtZsbhmbService;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/11
 * @description  生成不动产权证号综合逻辑处理测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MockServletContext.class)
@WebAppConfiguration
public class BdcBdcqzhServiceImplTest {
    private BdcBdcqzhScServiceImpl bdcBdcqzhService;

    @Mock
    private BdcBdcqzhBhzServiceImpl bdcqzhBhzService;
    @Mock
    private BdcBdcqzhYlzhServiceImpl bdcqzhYlzhService;
    @Mock
    private BdcBdcqzhFhServiceImpl bdcqzhFhService;
    @Mock
    private BdcBdcqzhMrServiceImpl bdcqzhMrService;
    @Mock
    protected BdcXmService bdcXmService;
    @Mock
    protected BdcZsService bdcZsService;
    @Mock
    protected BdcXtZsbhmbService bdcXtZsbhmbService;

    @Before
    public void setUp() throws Exception{
        //模拟打桩生成目标测试类
        bdcBdcqzhService = mock(BdcBdcqzhScServiceImpl.class);
        // 开启预留证号
        ReflectionTestUtils.setField(bdcBdcqzhService, "ylzh", true);
        // 开启废号
        ReflectionTestUtils.setField(bdcBdcqzhService, "fh", true);
        // 不开启流水号拼接区域代码
        ReflectionTestUtils.setField(bdcBdcqzhService, "sqdm", false);
    }

    @Test
    public void testGenerateBdcqzh() throws Exception {
        //--不动产项目
        BdcXmDO bdcXmDO = mock(BdcXmDO.class);
        List<BdcXmDO> bdcXmDOList = mock(ArrayList.class);
        when(bdcXmDOList.get(0)).thenReturn(bdcXmDO);
        when(bdcXmService.queryLcAllBdcXm(anyString(), any())).thenReturn(bdcXmDOList);

        //--不动产证书
        BdcZsDO bdcZsDO = mock(BdcZsDO.class);
        List<BdcZsDO> bdcZsDOList = mock(ArrayList.class);
        when(bdcZsDOList.get(0)).thenReturn(bdcZsDO);
        when(bdcZsService.queryBdcZsByXmid(anyString())).thenReturn(bdcZsDOList);

        //--证书编号模板
        BdcXtZsbhmbDO bdcXtZsbhmbDO = mock(BdcXtZsbhmbDO.class);
        when(bdcXtZsbhmbService.queryBdcXtZsbhmb(any(BdcXmDO.class))).thenReturn(bdcXtZsbhmbDO);

        // 模拟不换证场景
        List<BdcBdcqzhDTO> bdcBdcqzhDTOList = mock(ArrayList.class);
        when(bdcqzhBhzService.generateBdcqzh(anyString())).thenReturn(bdcBdcqzhDTOList);
        BdcBdcqzhBO bdcBdcqzhBO = new BdcBdcqzhBO();
        bdcBdcqzhBO.setXmid("123");
        List<BdcBdcqzhDTO> result = bdcBdcqzhService.generateBdcqzh(bdcBdcqzhBO);
        verify(bdcBdcqzhService).generateBdcqzh(bdcBdcqzhBO);

        // 模拟正常生成证号
        when(bdcqzhBhzService.generateBdcqzh(anyString())).thenReturn(null);

        // --预留证号获取
        BdcBdcqzhDTO bdcBdcqzhDTO = mock(BdcBdcqzhDTO.class);
        when(bdcqzhYlzhService.resolveBdcqzh(any(BdcBdcqzhBO.class))).thenReturn(bdcBdcqzhDTO);
        BdcBdcqzhBO bdcBdcqzhBO2 = new BdcBdcqzhBO();
        bdcBdcqzhBO.setXmid("1234");
        List<BdcBdcqzhDTO> result2 = bdcBdcqzhService.generateBdcqzh(bdcBdcqzhBO2);
        verify(bdcBdcqzhService).generateBdcqzh(bdcBdcqzhBO2);

        // --废号
        when(bdcqzhYlzhService.resolveBdcqzh(any(BdcBdcqzhBO.class))).thenReturn(null);
        BdcBdcqzhDTO bdcBdcqzhDTO2 = mock(BdcBdcqzhDTO.class);
        when(bdcqzhFhService.resolveBdcqzh(any(BdcBdcqzhBO.class))).thenReturn(bdcBdcqzhDTO2);
        BdcBdcqzhBO bdcBdcqzhBO3 = new BdcBdcqzhBO();
        bdcBdcqzhBO.setXmid("1234");
        List<BdcBdcqzhDTO> result3 = bdcBdcqzhService.generateBdcqzh(bdcBdcqzhBO3);
        verify(bdcBdcqzhService).generateBdcqzh(bdcBdcqzhBO3);

        // --默认
        when(bdcqzhYlzhService.resolveBdcqzh(any(BdcBdcqzhBO.class))).thenReturn(null);
        when(bdcqzhFhService.resolveBdcqzh(any(BdcBdcqzhBO.class))).thenReturn(null);
        BdcBdcqzhDTO bdcBdcqzhDTO3 = mock(BdcBdcqzhDTO.class);
        when(bdcqzhMrService.resolveBdcqzh(any(BdcBdcqzhBO.class))).thenReturn(bdcBdcqzhDTO3);
        BdcBdcqzhBO bdcBdcqzhBO4 = new BdcBdcqzhBO();
        bdcBdcqzhBO.setXmid("12345");
        List<BdcBdcqzhDTO> result4 = bdcBdcqzhService.generateBdcqzh(bdcBdcqzhBO4);
        verify(bdcBdcqzhService).generateBdcqzh(bdcBdcqzhBO4);
    }
}