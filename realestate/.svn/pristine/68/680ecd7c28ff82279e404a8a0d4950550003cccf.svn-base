package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.realestate.certificate.service.BdcGdsjPzService;
import cn.gtmap.realestate.certificate.service.impl.BdcGdsjPzServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 11/19/2018
 * @description BdcGdsjPzRestController Tester.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BdcGdsjPzRestControllerTest {

    /**
     * 归档配置请求地址
     */
    private static String REST_PATH_PRINT = "/certificate/rest/v1.0/gdpz/";

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mvc;

    BdcGdsjPzService bdcGdsjPzService;

    @Before
    public void before() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        bdcGdsjPzService = mock(BdcGdsjPzServiceImpl.class);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description bdcXxGd(@ PathVariable ( " gzlslid ") String gzlslid)
     */
    @Test
    public void testBdcXxGd() throws Exception {
        String gzlslid = "111111";
        String responseSuccess = mvc.perform(get(REST_PATH_PRINT+gzlslid))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }


} 
