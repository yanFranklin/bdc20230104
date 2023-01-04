package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmPzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmSfbzDO;
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
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdcSlSfxmPzRestControllerTest {
    private static final String PATH = "/realestate-accept/rest/v1.0/";
    private static String djxl;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void test00saveBdcSlSfxmPzDO() throws Exception {
        djxl = "test";
        BdcSlSfxmPzDO bdcSlSfxmPzDO = new BdcSlSfxmPzDO();
        bdcSlSfxmPzDO.setSfxmpzid("test");
        bdcSlSfxmPzDO.setSfxmdm("test");
        bdcSlSfxmPzDO.setDjxl("test");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlSfxmPzDO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.put(PATH +"/sfxmpz")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);

    }

    @Test
    public void test01listBdcSlSfxmPzByDjxl() throws Exception {
        List<BdcSlSfxmPzDO> bdcSlSfxmPzDOList = new ArrayList<>();
        BdcSlSfxmPzDO bdcSlSfxmPzDO = new BdcSlSfxmPzDO();
        bdcSlSfxmPzDO.setSfxmpzid("test");
        bdcSlSfxmPzDO.setDjxl("test");
        bdcSlSfxmPzDO.setSfxmdm("test");
        bdcSlSfxmPzDOList.add(bdcSlSfxmPzDO);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlSfxmPzDOList);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "sfxmpz/list/" + djxl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        JSONAssert.assertEquals(responseSuccess, requestJson, true);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test02listBdcSlSfxmSfbzDO() throws Exception {
        String sfxmdm = "10000";
        List<BdcSlSfxmSfbzDO> bdcSlSfxmSfbzDOList = new ArrayList<>();
        BdcSlSfxmSfbzDO bdcSlSfxmSfbzDO = new BdcSlSfxmSfbzDO();
        bdcSlSfxmSfbzDO.setSfxmdm("10000");
        bdcSlSfxmSfbzDO.setSfxmbz("test");
        bdcSlSfxmSfbzDOList.add(bdcSlSfxmSfbzDO);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlSfxmSfbzDOList);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "sfxmbz/list/" + sfxmdm))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        JSONAssert.assertEquals(responseSuccess, requestJson, true);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test03deleteBdcSlSfxmPzDOByPzid() throws Exception {


        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.delete(PATH+"/sfxmpz").param("sfxmpzid","test"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test04querySfxmPzMaxSxh() throws Exception {
        BdcSlSfxmPzDO bdcSlSfxmPzDO = new BdcSlSfxmPzDO();
        bdcSlSfxmPzDO.setSfxmpzid("11111");
        String path ="/realestate-accept/rest/v1.0/djsfxmpzxlpz/maxsxh";
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlSfxmPzDO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(path)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);

    }

    @Test
    public void test05listAllBdcSlSfxmDTO() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"sfxmpz/all"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }


}
