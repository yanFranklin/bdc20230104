package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.common.core.domain.accept.BdcYxbdcdyKgPzDO;
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

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/2/28
 * @description 不动产已选不动产单元开关rest服务 单元测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdcYxbdcdyKgPzRestControllerTest {
    private static final String PATH = "/realestate-accept/rest/v1.0/yxbdcdykgpz";
    private static String gzldyid;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void test00saveBdcYxbdcdyKgPzDO() throws Exception {
        gzldyid = "test";
        BdcYxbdcdyKgPzDO bdcYxbdcdyKgPzDO = new BdcYxbdcdyKgPzDO();
        bdcYxbdcdyKgPzDO.setGzldyid(gzldyid);
        bdcYxbdcdyKgPzDO.setPzid("test");
        bdcYxbdcdyKgPzDO.setZssl(1);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcYxbdcdyKgPzDO);
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
    public void test01queryBdcYxbdcdyKgPzDOByGzldyid() throws Exception {
        BdcYxbdcdyKgPzDO bdcYxbdcdyKgPzDO = new BdcYxbdcdyKgPzDO();
        bdcYxbdcdyKgPzDO.setGzldyid(gzldyid);
        bdcYxbdcdyKgPzDO.setPzid("test");
        bdcYxbdcdyKgPzDO.setZssl(1);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH ).param("gzldyid",gzldyid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcYxbdcdyKgPzDO);
        JSONAssert.assertEquals(responseSuccess, requestJson, true);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();


    }

    @Test
    public void test02deleteBdcYxbdcdyKgPzDOByGzldyid() throws Exception {
        String gzldyid = "test";

        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.delete(PATH).param("gzldyid",gzldyid))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }
}
