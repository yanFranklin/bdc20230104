package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlPjqDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlPjqDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlPjqQO;
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
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/2/18
 * @description 不动产受理评价器rest服务 单元测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdcSlPjqRestControllerTest {
    private static final String PATH = "/realestate-accept/rest/v1.0/pjq";
    private static BdcQlrDO bdcQlrDO;
    private static String ywbh;
    private static String jdmc;
    private static String gzlslid;
    private static String usernme;
    private static String lclx;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void test0queryBdcSlPjqByYwbh() throws Exception {
        ywbh = UUIDGenerator.generate16();
        jdmc = "受理";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH +"/"+ywbh+"/"+jdmc))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH +"/"+ywbh+"/"+jdmc))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test1queryBdcSlPjqByYwbh() throws Exception {
        ywbh = UUIDGenerator.generate16();
        jdmc = "受理";
        usernme = "张三";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH +"/"+ywbh+"/"+jdmc+"/"+usernme))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH +"/"+ywbh+"/"+jdmc))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test2insertBdcSlPjq() throws Exception {
        BdcSlPjqDO bdcSlPjqDO = new BdcSlPjqDO();
        bdcSlPjqDO.setBlryid("111");
        bdcSlPjqDO.setJdmc("受理");
        bdcSlPjqDO.setPjjlid("222222");

        String json = JSONObject.toJSONString(bdcSlPjqDO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH +"/")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);

    }

    @Test
    public void test2updateBdcSlPjq() throws Exception {
        BdcSlPjqDO bdcSlPjqDO = new BdcSlPjqDO();
        bdcSlPjqDO.setBlryid("111222");
        bdcSlPjqDO.setJdmc("受理");
        bdcSlPjqDO.setPjjlid("222222");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlPjqDO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.put(PATH +"/")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test3listBdcSlPjTjByPage() throws Exception {
        BdcSlPjqDTO bdcSlPjqDTO = new BdcSlPjqDTO();
        bdcSlPjqDTO.setYwbh("22222");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlPjqDTO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH +"/page")
                .param("pageSize","1")
                .param("pageNumber","10")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH+"/page")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new String()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test4listGroupBdcSlPjTjByPage() throws Exception {
        BdcSlPjqQO bdcSlPjqQO = new BdcSlPjqQO();
        bdcSlPjqQO.setBlryid("22222");
        bdcSlPjqQO.setYwbh("22222");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlPjqQO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH +"/group/page")
                .param("pageSize","1")
                .param("pageNumber","10")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH+"/group/page")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new String()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
    @Test
    public void test5listGroupBdcSlPjTj() throws Exception {
        BdcSlPjqQO bdcSlPjqQO = new BdcSlPjqQO();
        bdcSlPjqQO.setBlryid("22222");
        bdcSlPjqQO.setYwbh("22222");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlPjqQO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH +"/group/list")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH+"/group/list")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new String()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


}
