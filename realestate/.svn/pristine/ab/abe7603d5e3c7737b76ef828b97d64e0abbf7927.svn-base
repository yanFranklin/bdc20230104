package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclPzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXztzPzDO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2019/3/26,1.0
 * @description 受理选择台账配置服务单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdcSlXztzPzRestControllerTest {
    private static final String PATH = "/realestate-accept/rest/v1.0/xztzpz";
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void queryBdcSlXztzPzDOByGzldyid() throws Exception {
        String gzldyid = "test";
        BdcSlXztzPzDO bdcSlXztzPzDO = new BdcSlXztzPzDO();
        bdcSlXztzPzDO.setGzldyid("test");
        bdcSlXztzPzDO.setPzid("test");
        bdcSlXztzPzDO.setXztzlx("test");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlXztzPzDO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH).param("gzldyid",gzldyid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        JSONAssert.assertEquals(responseSuccess, requestJson, true);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH + gzldyid))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void saveBdcSlXztzPzDO() throws Exception {
        String gzldyid = "test";
        BdcSlXztzPzDO bdcSlXztzPzDO = new BdcSlXztzPzDO();
        bdcSlXztzPzDO.setGzldyid("test");
        bdcSlXztzPzDO.setPzid("test");
        bdcSlXztzPzDO.setXztzlx("test");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlXztzPzDO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);

    }

    @Test
    public void deleteBdcSlXztzPzDOByGzldyid() throws Exception {
        String gzldyid = "test";

        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.delete(PATH).param("gzldyid",gzldyid))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }
}
