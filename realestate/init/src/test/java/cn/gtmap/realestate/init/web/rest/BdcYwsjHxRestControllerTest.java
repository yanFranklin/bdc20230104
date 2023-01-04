package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.init.App;
import com.alibaba.fastjson.JSON;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @param
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @return
 * @description 业务数据回写测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config-dbunit.xml"})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = App.class)
public class BdcYwsjHxRestControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final String PATH = "/init/rest/v1.0/";

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    /**
     * Method: queryBdcYwsj(String gzlslid, String xmid)
     *//*
    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/BdcXmSetup-data.xml")
    public void testQueryBdcYwsj() throws Exception {
        String xmid = "4444444";
        String gzlslid = "34444444444";
        Map<String, String> param = new HashMap<String, String>();
        param.put("xmid", xmid);
        param.put("gzlslid", gzlslid);
        String response = mockMvc.perform(MockMvcRequestBuilders.get(PATH + gzlslid + "/ywsj")
                .param("xmid",xmid)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JSON.toJSONString(param)))
                .andExpect(status().isCreated())// 模拟向testRest发送get请求
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertTrue(Boolean.parseBoolean(response));
    }

    *//**
     * Method: saveBdcYwsj(String gzlslid, String xmid)
     *//*
    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/BdcXmSetup-data.xml")
    public void testSaveBdcYwsj() throws Exception {
        String xmid = "4444444";
        String gzlslid = "34444444444";
        Map<String, String> param = new HashMap<String, String>();
        param.put("xmid", xmid);
        param.put("gzlslid", gzlslid);
        String response = mockMvc.perform(MockMvcRequestBuilders.put(PATH + gzlslid + "/ywsj")
                .param("xmid",xmid)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JSON.toJSONString(param)))
                .andExpect(status().isCreated())// 模拟向testRest发送get请求
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertTrue(Boolean.parseBoolean(response));
    }*/

    /**
     * Method: updateBdcYwsj(String gzlslid, String xmid, String ywsjid)
     */
    @Test
    public void testUpdateBdcYwsj() throws Exception {
    }

    /**
     * Method: deleteBdcYwsj(String gzlslid, String ywsjid, String xmid)
     */
    @Test
    public void testDeleteBdcYwsj() throws Exception {
    }


} 
