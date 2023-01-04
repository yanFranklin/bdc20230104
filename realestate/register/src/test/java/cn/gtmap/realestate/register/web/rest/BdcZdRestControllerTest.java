package cn.gtmap.realestate.register.web.rest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3,
 * @description BdcZdRestController Tester. 字典项服务测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BdcZdRestControllerTest {

    /**
     * 获取字典列表
     */
    private static String REST_PATH_ZD_LIST = "/realestate-register/rest/v1.0/bdcZd/list";
    /**
     * 获取字典
     */
    private static String REST_PATH_ZD = "/realestate-register/rest/v1.0/bdcZd/";

    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void before() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description listBdcZd()
     */
    @Test
    public void testListBdcZd() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(REST_PATH_ZD_LIST).accept(MediaType.APPLICATION_JSON_UTF8_VALUE).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description queryBdcZd(@ PathVariable ( name = " zdmc ") String zdmc)
     */
    @Test
    public void testQueryBdcZd() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(REST_PATH_ZD+"/djlx").accept(MediaType.APPLICATION_JSON_UTF8_VALUE).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertNotNull(mvcResult.getResponse().getContentAsString());
    }


} 
