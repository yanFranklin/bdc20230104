package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.realestate.certificate.CertificateApp;
import cn.gtmap.realestate.certificate.core.service.impl.BdcYzhServiceImpl;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhQO;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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


/**
 * BdcYzhServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>12/04/2018</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CertificateApp.class)
@WebAppConfiguration
public class bdcYzhRestControllerTest {

    private static final String PATH = "/realestate-certificate/rest/v1.0/";
    @Autowired
    BdcYzhServiceImpl bdcYzhService;

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void queryBdcZsYzhTest() {
        // 构造一个Runner
        TestRunnable runner = new TestRunnable() {
            @Override
            public void runTest() throws Throwable {
                int count = 0;

                BdcYzhQO bdcYzhQO = new BdcYzhQO();
                bdcYzhQO.setNf("2017");
                bdcYzhQO.setZslx(1);
                bdcYzhQO.setOrderField("qzysxlh");
                bdcYzhQO.setOrderType("ASC");


                mockMvc.perform(MockMvcRequestBuilders.get(PATH + "4444444" + "/yzh")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .param("nf", "2017").param("zslx", "1"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andDo(MockMvcResultHandlers.print())
                        .andReturn();
            }
        };
        int runnerCount = 30;
        //Rnner数组，想当于并发多少个。
        TestRunnable[] trs = new TestRunnable[runnerCount];
        for (int i = 0; i < runnerCount; i++) {
            trs[i] = runner;
        }
        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
        try {
            // 开发并发执行数组里定义的内容
            mttr.runTestRunnables();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
} 
