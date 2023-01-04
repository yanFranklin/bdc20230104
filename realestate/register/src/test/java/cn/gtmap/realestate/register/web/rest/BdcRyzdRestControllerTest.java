package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.register.App;
import cn.gtmap.realestate.register.core.dto.BdcRyzdDTO;
import cn.gtmap.realestate.register.service.BdcRyzdService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

import static cn.gtmap.realestate.register.util.Constants.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/17
 * @description 冗余字段接口测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class BdcRyzdRestControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Mock
    private BdcRyzdService bdcRyzdService;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  处理冗余字段接口方法测试
     */
    @Test
    public void testUpdateRyzd() throws Exception {
        Map<String, Object> ryzdMap = new HashMap<>(20);
        ryzdMap.put(QLLX, 3);

        Map<String, Object> bdcqzh = new HashMap<>(2);
        bdcqzh.put("UNIT_QLR_111222", "证号123456");
        bdcqzh.put("UNIT_QLR_123212", "证号324322");
        ryzdMap.put(BDCQZH, bdcqzh);

        ryzdMap.put(DJJG, "不动产登记中心");
        ryzdMap.put(YWRZJH, "320826999912340000,HZ12313213");
        ryzdMap.put(QLRZJH, "320826999912341111,HZ34334232");
        ryzdMap.put(QLRZJZL, "1,2");
        ryzdMap.put(BDCDYH, "320902004004GB00018F00010012");
        ryzdMap.put(ZL, "南京市玄武区");
        ryzdMap.put(DJLX, 100);
        ryzdMap.put(YWR, "UNIT_YWR_231213,UNIT_YWR_4543543");
        ryzdMap.put(QLR, "UNIT_QLR_111222,UNIT_QLR_123212");
        ryzdMap.put(DBR, "赵明");
        ryzdMap.put(QSZT, 0);
        ryzdMap.put(YWRZJZL, "1,3");
        ryzdMap.put(BDCQZH_HB, "证号123456,证号324322");
        ryzdMap.put(GYFS, 1);

        BdcRyzdDTO bdcRyzd = Mockito.mock(BdcRyzdDTO.class);
        // 模拟获取冗余字段
        Mockito.when(bdcRyzdService.getRyzd(Mockito.eq("UNIT_XM_123"))).thenReturn(bdcRyzd);
        // 模拟更新冗余字段
        Mockito.doNothing().when(bdcRyzdService).updateRyzd(Mockito.eq(bdcRyzd));

        // 模拟MVC请求
        mockMvc.perform(MockMvcRequestBuilders.patch("/register/rest/v1.0/ryzd/UNIT_XM_123")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}