package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
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

import cn.gtmap.realestate.accept.App;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/12
 * @description 不动产登记小类配置rest服务 单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdcDjxlPzRestControllerTest {
    private static final String PATH = "/realestate-accept/rest/v1.0/djxlpz/";
    private static String gzldyid;
    private static String pzid;
    private static String djxl = "4000901";
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void queryBdcDjxlPzByGzldyid() throws Exception {
        gzldyid = "dBFAZZDMQzsz8AXr";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"list/").param("gzldyid",gzldyid).param("qllx","4"))
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
    public void test01saveBdcDjxlPzDO() throws Exception {
        BdcDjxlPzDO bdcDjxlPzDO =new BdcDjxlPzDO();
        bdcDjxlPzDO.setGzldyid("dBFAZZDMQzsz8AXr");
        bdcDjxlPzDO.setDjxl("123111");
        bdcDjxlPzDO.setQllx(4);
        bdcDjxlPzDO.setSxh(1);
        pzid ="123422111";
        bdcDjxlPzDO.setPzid(pzid);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcDjxlPzDO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
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
    public void test02queryBdcDjxlPzDOByPzid() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH +pzid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);

    }

    @Test
    public void test03queryBdcDjxlPzByGzldyid() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH +"list/dBFAZZDMQzsz8AXr"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);

    }

    @Test
    public void test04deleteBdcDjxlPzDOByPzid() throws Exception {
        BdcDjxlPzDO bdcDjxlPzDO = new BdcDjxlPzDO();
        bdcDjxlPzDO.setGzldyid("dBFAZZDMQzsz8AXr");
        bdcDjxlPzDO.setPzid("123422111");
        bdcDjxlPzDO.setDjxl("4000901");
        List<BdcDjxlPzDO> bdcDjxlPzDOS = new ArrayList<>();
        bdcDjxlPzDOS.add(bdcDjxlPzDO);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcDjxlPzDOS);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.delete(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);

    }

    @Test
    public void test05queryBdcDjxlPzByGzldyidAndDjxl() throws Exception {
        gzldyid = "dBFAZZDMQzsz8AXr";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH+gzldyid + "/" + djxl))
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
    public void test06queryDjxlPzMaxSxh() throws Exception {
        BdcDjxlPzDO bdcDjxlPzDO = new BdcDjxlPzDO();
        bdcDjxlPzDO.setGzldyid(gzldyid);
        bdcDjxlPzDO.setDjxl(djxl);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcDjxlPzDO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "maxsxh")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
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
    public void test07listBdcDjxlPzByPage() throws Exception {
        BdcDjxlPzDO bdcDjxlPzDO = new BdcDjxlPzDO();
        bdcDjxlPzDO.setGzldyid(gzldyid);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "page")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JSONObject.toJSONString(bdcDjxlPzDO)))
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