package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.qo.inquiry.BdcdyxxQO;
import cn.gtmap.realestate.inquiry.InquiryApp;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InquiryApp.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdcdyXxCxRestControllerTest {
    private static final String PATH = "/realestate-inquiry/rest/v1.0/bdcdyxx";

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }


    @Test
    public void test0listBdcdyByPage() throws Exception {
        BdcdyxxQO bdcdyxxQO = new BdcdyxxQO();
        bdcdyxxQO.setBdcdyh("340104404005GB00047F00010128");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcdyxxQO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/page")
                .param("pageSize", "1")
                .param("pageNumber", "10")
                .param("bdcdyxxQOJson", requestJson)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        JSONAssert.assertEquals(responseSuccess, requestJson, true);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/page")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test1listBdcdyByPage() throws Exception {
        BdcdyxxQO bdcdyxxQO = new BdcdyxxQO();
        bdcdyxxQO.setBdcdyh("340104404005GB00047F00010128");
        bdcdyxxQO.setQlrmc("中国农业银行股份有限公司合肥新政务区支行");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcdyxxQO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/page")
                .param("pageSize", "1")
                .param("pageNumber", "10")
                .param("bdcdyxxQOJson", requestJson)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        JSONAssert.assertEquals(responseSuccess, requestJson, true);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/page")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test2listBdcdyByPage() throws Exception {
        BdcdyxxQO bdcdyxxQO = new BdcdyxxQO();
        bdcdyxxQO.setBdcdyh("340104404005GB00047F00010128");
        bdcdyxxQO.setQlrmc("中国农业银行股份有限公司合肥新政务区支行");
        bdcdyxxQO.setDjqsrq("2013-01-23");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcdyxxQO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/page")
                .param("pageSize", "1")
                .param("pageNumber", "10")
                .param("bdcdyxxQOJson", requestJson)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        JSONAssert.assertEquals(responseSuccess, requestJson, true);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/page")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}