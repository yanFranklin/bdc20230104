package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.common.core.domain.accept.BdcGzlwShDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCshSlxmDTO;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @program: realestate
 * @description: 规则例外rest服务单元测试
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-02-19 16:06
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdcGzlwRestControllerTest {
    private static final String PATH = "/realestate-accept/rest/v1.0/";
    private static BdcGzlwShDO bdcGzlwShDO;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void test01addShxxData() throws Exception {
        HashMap hashMap = new HashMap();
        hashMap.put("bdcdyh","340103103011GB00168F00010379");
        hashMap.put("gzid", UUIDGenerator.generate16());
        hashMap.put("gzmc","验证所在宗地不动产单元是否存在抵押test");
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "addShxxData")
                .param("data",JSONObject.toJSONString(hashMap))
                .param("slbh","202001090001")
        .param("xmid","41992550P82B6067"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test02addShxxDataWithoutSlbh() throws Exception {
        BdcCshSlxmDTO bdcCshSlxmDTO = new BdcCshSlxmDTO();
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "addShxxDataWithoutSlbh")
                .param("qllx","4")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JSONObject.toJSONString(bdcCshSlxmDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test03queryBdcGzlw() throws Exception {
        bdcGzlwShDO = new BdcGzlwShDO();
        bdcGzlwShDO.setBdcdyh("340103103011GB00168F00010379");
        bdcGzlwShDO.setSlbh("202001090001");
        bdcGzlwShDO.setXmid("41992550P82B6067");
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "queryBdcGzlw")
                .param("paramJson",JSONObject.toJSONString(bdcGzlwShDO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }
    @Test
    public void test04bdcgzlwGroupByBdcdyh() throws Exception {
        bdcGzlwShDO = new BdcGzlwShDO();
        bdcGzlwShDO.setBdcdyh("340103103011GB00168F00010379");
        bdcGzlwShDO.setSlbh("202001090001");
        bdcGzlwShDO.setXmid("41992550P82B6067");
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "bdcgzlwGroupByBdcdyh")
                .param("paramJson",JSONObject.toJSONString(bdcGzlwShDO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test05listBdcGzlw() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "listBdcGzlw")
                .param("gzlslid","6637136"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test06updateBdcGzlwxx() throws Exception {
        bdcGzlwShDO = new BdcGzlwShDO();
        bdcGzlwShDO.setBdcdyh("340103103011GB00168F00010379");
        bdcGzlwShDO.setSlbh("202001090001");
        bdcGzlwShDO.setXmid("41992550P82B6067");
        List<BdcGzlwShDO> bdcGzlwShDOList = new ArrayList<>();
        bdcGzlwShDOList.add(bdcGzlwShDO);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcGzlwShDOList);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "updateBdcGzlwxx")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test07updateBdcGzlw() throws Exception {
        bdcGzlwShDO = new BdcGzlwShDO();
        bdcGzlwShDO.setBdcdyh("340103103011GB00168F00010379");
        bdcGzlwShDO.setSlbh("202001090001");
        bdcGzlwShDO.setXmid("41992550P82B6067");
        List<BdcGzlwShDO> bdcGzlwShDOList = new ArrayList<>();
        bdcGzlwShDOList.add(bdcGzlwShDO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "updateBdcGzlw")
                .param("data",JSONObject.toJSONString(bdcGzlwShDOList)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test08deleteBdcGzlwSh() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "deleteBdcGzlwSh")
                .param("gzlwid","3f194713520f4780baff82eca9a368055"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test09deleteBdcGzlwShByGzlw() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcGzlwShDO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "deleteBdcGzlw")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }
}
