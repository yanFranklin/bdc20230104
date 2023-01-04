package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclPzDO;
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
 * @description 不动产登记收件材料配置rest服务单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdcSlSjclPzRestControllerTest {
    private static final String PATH = "/realestate-accept/rest/v1.0/sjclpz/";
    private static String djxl;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void test00saveBdcSlSjclPzDO() throws Exception {
        djxl = "test";
        BdcSlSjclPzDO bdcSlSjclPzDO = new BdcSlSjclPzDO();
        bdcSlSjclPzDO.setPzid("test");
        bdcSlSjclPzDO.setClmc("test");
        bdcSlSjclPzDO.setDjxl("test");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlSjclPzDO);
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
    public void test01listBdcSlSjclPzByDjxl() throws Exception {
        List<BdcSlSjclPzDO> bdcSlSjclPzDOList = new ArrayList<>();
        BdcSlSjclPzDO bdcSlSjclPzDO = new BdcSlSjclPzDO();
        bdcSlSjclPzDO.setPzid("test");
        bdcSlSjclPzDO.setClmc("test");
        bdcSlSjclPzDO.setDjxl("test");
        bdcSlSjclPzDOList.add(bdcSlSjclPzDO);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlSjclPzDOList);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "list/" + djxl))
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
    public void test03deleteBdcSlSjclPzDOByPzid() throws Exception {


        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.delete(PATH).param("pzid","test"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test04querySjclPzMaxSxh() throws Exception {

        BdcSlSjclPzDO bdcSlSjclPzDO = new BdcSlSjclPzDO();
        bdcSlSjclPzDO.setPzid("1111");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlSjclPzDO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/maxsxh")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

}
