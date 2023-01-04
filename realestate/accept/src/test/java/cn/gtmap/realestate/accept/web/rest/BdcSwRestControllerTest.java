package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxMxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSfSsxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSfxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSwxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.TsswDataQO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/2/18
 * @description 不动产税务信息服务 单元测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdcSwRestControllerTest {
    private static final String PATH = "/realestate-accept/rest/v1.0/sw";
    private static String xmid;
    private static String gzlslid;
    private static String slbh;
    private static String htbh;
    private static String wszt;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void test00tsClfSwxx() throws Exception {
        gzlslid = "test";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH +"/tsclfswxx/"+gzlslid))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test01tsSpfSwxx() throws Exception {
        gzlslid = "test";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH +"/tsspfswxx/"+gzlslid))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test02getSwxx() throws Exception {
        xmid = "test";
        slbh = "test";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH +"/getswxx/"+xmid+"/"+slbh))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH +"/getswxx/"+xmid+"/"+slbh))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test03qxzfSwxx() throws Exception {
        xmid = "test";
        slbh = "test";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH +"/zfswxx/"+xmid+"/"+slbh))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test04qxzfLcSwxx() throws Exception {
        gzlslid = "test";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH).param("gzlslid",gzlslid))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test05updateWsztByHtbh() throws Exception {
        htbh = "test";
        wszt = "test";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH +"/"+htbh+"/"+wszt))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test06checkSfwsByGzlslid() throws Exception {
        gzlslid = "test";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH +"/wszt/"+gzlslid))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test07querySpfwszt() throws Exception {
        gzlslid = "test";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH +"/spfwszt/"+gzlslid))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH +"/spfwszt/"+gzlslid))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test08getSpfXmWszt() throws Exception {
        BdcXmDO BdcXmDO = new BdcXmDO();
        BdcXmDO.setXmid("test");
        BdcXmDO.setSlbh("test");
        BdcXmDO.setSwfybh("test");
        BdcXmDO.setZl("test");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(BdcXmDO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH +"/spfwszt")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        JSONAssert.assertEquals(responseSuccess, requestJson, true);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH +"/spfwszt"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test09getSwsysHsxx() throws Exception {

        gzlslid = "test";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH +"/swsys/"+gzlslid))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH +"/swsys/"+gzlslid))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test10yhddcxAndJkrk() throws Exception {
        gzlslid = "test";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH +"/yhddcx/jkrk").param("gzlslid",gzlslid))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test11yhjkrk() throws Exception {
        BdcSfSsxxDTO bdcSfSsxxDTO = new BdcSfSsxxDTO();
        BdcSfxxDTO bdcSfxxDTO = new BdcSfxxDTO();
        BdcSlSfxmDO bdcSlSfxmDO = new BdcSlSfxmDO();
        bdcSlSfxmDO.setSfxxid("test");
        List<BdcSlSfxmDO> bdcSlSfxmDOS = new ArrayList<>();
        bdcSlSfxmDOS.add(bdcSlSfxmDO);
        BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
        bdcSlSfxxDO.setXmid("test");
        bdcSfxxDTO.setBdcSlSfxmDOS(bdcSlSfxmDOS);
        bdcSfxxDTO.setBdcSlSfxxDO(bdcSlSfxxDO);
        List<BdcSfxxDTO> bdcSfxxDTOS = new ArrayList<>();
        bdcSfxxDTOS.add(bdcSfxxDTO);
        bdcSfSsxxDTO.setBdcSfxxDTOS(bdcSfxxDTOS);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSfSsxxDTO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH +"/jkrk")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test11getTsswDataDTO() throws Exception {
        TsswDataQO tsswDataQO = new TsswDataQO();
        tsswDataQO.setHtbh("test");
        tsswDataQO.setSlbh("test");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(tsswDataQO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH +"/gettsswxx")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH +"/gettsswxx"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test12saveSwxxDTOByHtbh() throws Exception {
        List<BdcSwxxDTO> bdcSwxxDTOList = new ArrayList<>();
        BdcSwxxDTO bdcSwxxDTO = new BdcSwxxDTO();
        BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
        bdcSlHsxxDO.setXmid("test");
        bdcSlHsxxDO.setWszt(1);
        bdcSlHsxxDO.setFpdm("test");
        bdcSwxxDTO.setBdcSlHsxxDO(bdcSlHsxxDO);

        List<BdcSlHsxxMxDO> bdcSlHsxxMxDOList = new ArrayList<>();
        BdcSlHsxxMxDO bdcSlHsxxMxDO = new BdcSlHsxxMxDO();
        bdcSlHsxxMxDO.setHsxxid("test");
        bdcSlHsxxMxDO.setHsxxmxid("test");
        bdcSlHsxxMxDOList.add(bdcSlHsxxMxDO);
        bdcSwxxDTO.setBdcSlHsxxMxDOList(bdcSlHsxxMxDOList);

        bdcSwxxDTOList.add(bdcSwxxDTO);
        gzlslid = "test";

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSwxxDTOList);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH +"/saveSwxxDTO").param("gzlslid",gzlslid)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH +"/saveSwxxDTO").param("gzlslid",gzlslid))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
