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
 * @version 1.3, 2018/12/5
 * @description BdcXtMryjRestController Tester.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BdcXtMryjRestControllerTest {


    /**
     * 获取可选意见
     */
    private static String REST_PATH_KXYJ = "/realestate-register/rest/v1.0/mryj/cjr/";
    /**
     * 获取默认意见
     */
    private static String REST_PATH_MRYJ = "/realestate-register/rest/v1.0/mryj/cjr/{cjrid}/gzldy/{gzldyid}/jd/{jdmc}";

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
     * @description listKxyjByCjr(String cjrid)
     */
    @Test
    public void testListKxyjByCjr() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(REST_PATH_KXYJ+"adminid").accept(MediaType.APPLICATION_JSON_UTF8_VALUE).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description queryMryj(String cjrid, String gzldyid, String jdmc)
     */
    @Test
    public void testQueryMryj() throws Exception {
        REST_PATH_MRYJ="/realestate-register/rest/v1.0/mryj/cjr/adminid/gzldy/2/jd/初审";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(REST_PATH_MRYJ).accept(MediaType.APPLICATION_JSON_UTF8_VALUE).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertNotNull(mvcResult.getResponse().getContentAsString());
    }


} 
