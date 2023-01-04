package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.init.App;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 生成证书服务测试
 *
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/13.
 * @description
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
public class BdcZsRestControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private EntityMapper entityMapper;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcZsSetup-data.xml")
    public void createBdcZs() throws Exception {
        String response = mockMvc.perform(post("/init/rest/v1.0/3444444465465/bdcqzs/sc"))
                .andExpect(status().isCreated())// 模拟向testRest发送get请求
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        if(response.contains("\"qlr\":\"权利人2\"")){
            response = "success";
        }
        Assert.assertEquals("success",response);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcZsSetup-data.xml")
    public void createBdcZm() throws Exception {
        String response = mockMvc.perform(post("/init/rest/v1.0/7861184896459698/bdcqz/sc"))
                .andExpect(status().isCreated())// 模拟向testRest发送get请求
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        if(response.contains("\"qlr\":\"权利人2\"")){
            response = "success";
        }
        Assert.assertEquals("success",response);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcZsSetup-data.xml")
    public void updateQlqtzkFj() throws Exception {
        String bothresponse = mockMvc.perform(post("/init/rest/v1.0/qlqtzkfj/7861184896459698/1"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("",bothresponse);

        String qlqtzkresponse = mockMvc.perform(post("/init/rest/v1.0/qlqtzkfj/7861184896459698/2"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("",qlqtzkresponse);

        String fjresponse = mockMvc.perform(post("/init/rest/v1.0/qlqtzkfj/7861184896459698/3"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("",bothresponse);
    }

}