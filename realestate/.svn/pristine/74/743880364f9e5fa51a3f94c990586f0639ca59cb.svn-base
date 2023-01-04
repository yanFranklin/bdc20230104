package cn.gtmap.realestate.engine.web.rest;


import cn.gtmap.realestate.common.core.domain.engine.BdcGzBmdDO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.engine.EngineApp;
import cn.gtmap.realestate.engine.web.BaseUnitTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/3/21 10:03
 * @description 白名单服务接口测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EngineApp.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class BdcGzBmdRestControllerTest {
    private MockMvc mockMvc;
    private static BdcGzBmdDO bmdDO;

    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * 请求URL前置路径
     */
    private static final String REST_PATH_BMD = "/realestate-engine/rest/v1.0/bmd";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }


    @Test
    public void testInit() {
        bmdDO = new BdcGzBmdDO();
        bmdDO.setCzry("gln");
        bmdDO.setCzrymm("12345");
        bmdDO.setCzryid(UUIDGenerator.generate());

    }

    @Test
    void bdcBmdPage() throws Exception {
        String url = "/page";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(REST_PATH_BMD + url).param("czry", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bmdDO);
        JSONAssert.assertEquals(responseSuccess, requestJson, true);
        mockMvc.perform(MockMvcRequestBuilders.get(REST_PATH_BMD + url))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    void deleteBmd() {
    }


    @Test
    void insertBdcBmd() throws Exception {

        BdcGzBmdDO errorBmd = new BdcGzBmdDO();
        errorBmd.setCzryid("f1694b9d08d7402883ed694b97210004");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bmdDO);
        String errorRequestJson = objectMapper.writeValueAsString(errorBmd);
        mockMvc.perform(MockMvcRequestBuilders.put("/")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.put("/")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void updateBdcBmd() {
    }


    /**
     * @return matcher 期望返回结果状态
     * @author <a href="mailto:zhuyong@gtmap.cn">huangjian</a>
     * @description 接口测试公共方法
     */
    public void mockMvcController(MockHttpServletRequestBuilder mockHttpServletRequestBuilder, ResultMatcher matcher) throws Exception {
        mockMvc.perform(mockHttpServletRequestBuilder.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(matcher)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}