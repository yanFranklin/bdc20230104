package cn.gtmap.realestate.accept.web;

import cn.gtmap.realestate.accept.ui.AcceptUIApp;
import cn.gtmap.realestate.common.util.CardNumberTransformation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/12/31.
 * @description 核税信息接口单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AcceptUIApp.class)
@WebAppConfiguration
@ContextConfiguration
public class BdcSlHsxxRestControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlHsxxRestControllerTest.class);
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    public final static String url = "/ycsl";

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    /**
     * 三要素接口
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Test
    public void swsysHsxxTest() throws Exception {
        String xmid = "3BUF3158G92B302G";
        String htbh = "123";

        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(url+"/swsys/hsxx")
                .param("xmid", xmid)
                .param("htbh", htbh)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.hasText(responseSuccess,"获取税费三要素接口失败，更新税务信息失败。");
    }

    @Test
    public void id18To15() throws Exception {
        String cardno1 = "320102199003075379";
        String cardno2 = "320102199003070113";
        String cardno3 = "320102199003077796";
        String cardno4 = "320102199003073891";
        String cardno5 = "320102199003075790";
        String res1 = CardNumberTransformation.idCard18to15(cardno1);
        String res2 = CardNumberTransformation.idCard18to15(cardno2);
        String res3 = CardNumberTransformation.idCard18to15(cardno3);
        String res4 = CardNumberTransformation.idCard18to15(cardno4);
        String res5 = CardNumberTransformation.idCard18to15(cardno5);
        LOGGER.info("res1{}{}{}{}{}",res1+",",res2+",",res3+",",res4+",",res5);
    }


}
