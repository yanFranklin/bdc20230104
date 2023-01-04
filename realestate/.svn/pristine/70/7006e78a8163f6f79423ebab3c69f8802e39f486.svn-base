package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
 * @version 1.0, 2019/6/24
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdcSlJyxxRestControllerTest {
    private static final String PATH = "/realestate-accept/rest/v1.0/jyxx/";
    private static BdcSlJyxxDO bdcSlJyxxDO;
    private static String jyxxid;
    private static String xmid;

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void test0saveBdcSlJyxx() throws Exception {
        jyxxid = UUIDGenerator.generate16();
        xmid =UUIDGenerator.generate16();
        bdcSlJyxxDO = new BdcSlJyxxDO();
        bdcSlJyxxDO.setJyxxid(jyxxid);
        bdcSlJyxxDO.setHtbah("test");
        bdcSlJyxxDO.setXmid("320682");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlJyxxDO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test1listBdcSlJyxxByXmid() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "list/" + xmid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test2queryBdcSlJyxxByJyxxid() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH  + jyxxid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test3insertBdcSlJyxx() throws Exception {
        bdcSlJyxxDO = new BdcSlJyxxDO();
        jyxxid = UUIDGenerator.generate16();
        bdcSlJyxxDO.setJyxxid(jyxxid);
        bdcSlJyxxDO.setHtbah("test1");
        bdcSlJyxxDO.setXmid("320682");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlJyxxDO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test4deleteBdcSlJyxxByJyxxid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + jyxxid))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test5deleteBdcSlJyxxByXmid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "xm/320682"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test6queryFcjyXgxx() throws Exception{
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH  + "fcjyxgxx").param("name","张三").param("cardNo","320682"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test7queryFcjyClfHtxx() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH  + "fcjyhtxx/320682/320682/1/zhlc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test8updateBatchBdcSlJyxx() throws Exception {
        BdcSlJyxxDO bdcSlJyxxDO = new BdcSlJyxxDO();
        bdcSlJyxxDO.setJyxxid("3A995447MO8X3F3U");
        bdcSlJyxxDO.setXmid(xmid);
        bdcSlJyxxDO.setHtbh("320682");
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.put(PATH+"pl")
                .param("gzlslid","6593338").param("jsonStr",JSONObject.toJSONString(bdcSlJyxxDO)).param("djxl","2000402"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test9updateXmSlJyxx() throws Exception {
        BdcSlJyxxDO bdcSlJyxxDO = new BdcSlJyxxDO();
        bdcSlJyxxDO.setJyxxid("3A995447MO8X3F3U");
        bdcSlJyxxDO.setXmid(xmid);
        bdcSlJyxxDO.setHtbh("320682");
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.put(PATH+"xm")
                .param("xmid","3C2H30042AIT7031").param("jsonStr",JSONObject.toJSONString(bdcSlJyxxDO)).param("djxl","2000402"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test10updateSlJyxxByXmid() throws Exception {
        BdcSlJyxxDO bdcSlJyxxDO = new BdcSlJyxxDO();
        bdcSlJyxxDO.setJyxxid("3A995447MO8X3F3U");
        bdcSlJyxxDO.setXmid(xmid);
        bdcSlJyxxDO.setHtbh("320682");
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.put(PATH+"xmid")
                .param("xmid","3C2H30042AIT7031").param("jsonStr",JSONObject.toJSONString(bdcSlJyxxDO)).param("djxl","2000402"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test12queryHfwxzjJnzt() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH  + "hfwxzjyz").param("xmid",xmid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test13queryBdcSlJyxxByHtbh() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH  + "htbh").param("htbh","1704007511"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
