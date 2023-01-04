package cn.gtmap.realestate.register.web.rest;


import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import com.alibaba.fastjson.JSON;
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
 * @version 1.3, 2019/01/17
 * @description BdcDypzRestController Tester.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BdcDypzRestControllerTest {
    /**
     * 打印主表地址
     */
    private static String DYSJ_PATH="/realestate-register/rest/v1.0/dysjpz";
    /**
     * 打印字表地址
     */
    private static String DYSJ_ZB_PATH="/realestate-register/rest/v1.0/dyzbsjpz";

    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
    @After
    public void after() throws Exception {
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description listBdcDysjPz(BdcDysjPzDO bdcDysjPzDO)
     */
    @Test
    public void testListBdcDysjPz() throws Exception {
        BdcDysjPzDO bdcDysjPzDO=new BdcDysjPzDO();
        bdcDysjPzDO.setDylx("spb");
        String requestJson = JSON.toJSONString(bdcDysjPzDO);
        //mock进行模拟
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(DYSJ_PATH).accept(MediaType.APPLICATION_JSON_UTF8_VALUE).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description listBdcDysjZbPz(BdcDysjZbPzDO bdcDysjZbPzDO)
     */
    @Test
    public void testListBdcDysjZbPz() throws Exception {
        BdcDysjZbPzDO bdcDysjZbPzDO=new BdcDysjZbPzDO();
        bdcDysjZbPzDO.setDylx("spb");
        String requestJson = JSON.toJSONString(bdcDysjZbPzDO);
        //mock进行模拟
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(DYSJ_ZB_PATH).accept(MediaType.APPLICATION_JSON_UTF8_VALUE).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertNotNull(mvcResult.getResponse().getContentAsString());
    }


} 
