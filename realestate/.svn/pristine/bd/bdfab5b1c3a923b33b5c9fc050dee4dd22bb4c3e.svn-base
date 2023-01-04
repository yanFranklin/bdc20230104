package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxMxDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlHsxxQO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
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

/**
 * @program: realestate
 * @description: 受理核税信息rest服务单元测试
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-02-18 14:04
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdcSlHsxxRestControllerTest {
    private static final String HSPATH = "/realestate-accept/rest/v1.0/hsxx/";
    private static final String SWPATH = "/realestate-accept/rest/v1.0/sw/";
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }


    @Test
    public void test01updateBdcSlHsxx() throws Exception {
        BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
        bdcSlHsxxDO.setHsxxid("3BUF3158G92B302G");
        bdcSlHsxxDO.setXmid("41GB12330M2B702Z");
        bdcSlHsxxDO.setWszt(2);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlHsxxDO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.put(HSPATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(responseSuccess, "1");
        mockMvc.perform(MockMvcRequestBuilders.put(HSPATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new String()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test02listBdcSlHsxx() throws Exception {
        BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
        bdcSlHsxxQO.setHsxxid("3BUF3158G92B302G");
        bdcSlHsxxQO.setXmid("41GB12330M2B702Z");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlHsxxQO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(HSPATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.post(HSPATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new String()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test03updateBdcSlHsxxByXmidAndNsrsbh() throws Exception {
        BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
        bdcSlHsxxDO.setXmid("41GB12330M2B702Z");
        bdcSlHsxxDO.setNsrsbh("22333");
        bdcSlHsxxDO.setHsxxid("3BUF3158G92B302G");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlHsxxDO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.put(HSPATH + "xmid")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(responseSuccess, "1");
        mockMvc.perform(MockMvcRequestBuilders.put(HSPATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new String()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test04queryBdcSwxxDTO() throws Exception {
        String xmid = "41GB12330M2B702Z";
        String sqrlb = "1";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(SWPATH +  "/queryBdcSwxxDTO/" + xmid).param("sqrlb",sqrlb))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test05updateWsztByGzlslid() throws Exception {
        Integer wszt= 0;
        String gzlslid = "6645125";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(SWPATH+"zt/" +wszt + "/" + gzlslid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.get(SWPATH + "zt/" + wszt))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
