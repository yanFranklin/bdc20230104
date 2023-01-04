package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSONObject;
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
 * @version 1.0, 2018/11/5
 * @description 不动产受理权利人rest服务 单元测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdcSlQlrRestControllerTest {
    private static final String PATH = "/realestate-accept/rest/v1.0/qlr";
    private static BdcQlrDO bdcQlrDO = new BdcQlrDO();
    private static String qlrid;
    private static String xmid;
    private static String gzlslid;
    private static String djxl;
    private static String lclx;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void test0updatePlBdcQlr() throws Exception {
        qlrid = UUIDGenerator.generate16();
        xmid = UUIDGenerator.generate16();
        gzlslid ="test";
        List<BdcQlrDO> bdcQlrDOS = new ArrayList<>();
        bdcQlrDO.setXmid(xmid);
        bdcQlrDO.setQlrid(qlrid);
        bdcQlrDO.setQlrlb("1");
        bdcQlrDOS.add(bdcQlrDO);
        String json = JSONObject.toJSONString(bdcQlrDOS);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.patch(PATH +"/list/pllc")
                .param("processInsId",gzlslid)
                .param("xmid",xmid)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        JSONAssert.assertEquals(responseSuccess, json, true);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH +"/list/pllc")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test1updatePlzhBdcQlr() throws Exception {
        qlrid = UUIDGenerator.generate16();
        xmid = UUIDGenerator.generate16();
        gzlslid ="test";
        djxl = "1";
        List<BdcQlrDO> bdcQlrDOS = new ArrayList<>();
        bdcQlrDO.setXmid(xmid);
        bdcQlrDO.setQlrid(qlrid);
        bdcQlrDO.setQlrlb("1");
        bdcQlrDOS.add(bdcQlrDO);
        String json = JSONObject.toJSONString(bdcQlrDOS);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.patch(PATH +"/plzh")
                .param("processInsId",gzlslid)
                .param("xmid",xmid)
                .param("djxl",djxl)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        JSONAssert.assertEquals(responseSuccess, json, true);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH +"/plzh")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test2updateBdcQlrGyfs() throws Exception {
        qlrid = UUIDGenerator.generate16();
        xmid = UUIDGenerator.generate16();
        gzlslid ="test";
        lclx = "1";
        List<BdcQlrDO> bdcQlrDOS = new ArrayList<>();
        bdcQlrDO.setXmid(xmid);
        bdcQlrDO.setQlrid(qlrid);
        bdcQlrDO.setQlrlb("1");
        bdcQlrDOS.add(bdcQlrDO);
        String json = JSONObject.toJSONString(bdcQlrDOS);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.patch(PATH +"/list/gyfs")
                .param("processInsId",gzlslid)
                .param("xmid",xmid)
                .param("lclx",lclx)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        JSONAssert.assertEquals(responseSuccess, json, true);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH +"/list/gyfs")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


}
