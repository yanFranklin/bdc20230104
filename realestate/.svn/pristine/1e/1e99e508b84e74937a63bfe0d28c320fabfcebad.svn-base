package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcDsfSjclDTO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
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
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 1.0, 2018/11/5
 * @description 受理项目rest服务 单元测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdcSlSjclRestControllerTest {
    private static final String PATH = "/realestate-accept/rest/v1.0/sjcl/";
    private static BdcSlSjclDO bdcSlSjclDO;
    private static String sjclid;
    private static String xmid;
    private static String gzlslid;
    private static String clmc;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void test0insertBdcSlSjcl() throws Exception {
        sjclid = UUIDGenerator.generate16();
        xmid = UUIDGenerator.generate16();
        gzlslid = UUIDGenerator.generate16();
        bdcSlSjclDO = new BdcSlSjclDO();
        bdcSlSjclDO.setSjclid(sjclid);
        bdcSlSjclDO.setXmid(xmid);
        bdcSlSjclDO.setClmc("收件单");
        bdcSlSjclDO.setGzlslid(gzlslid);
        bdcSlSjclDO.setXh(3);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlSjclDO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        JSONAssert.assertEquals(responseSuccess, requestJson, true);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new String()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test1queryBdcSlSjclBySjclid() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + sjclid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlSjclDO);
        JSONAssert.assertEquals(responseSuccess, requestJson, true);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    @Test
    public void test3listBdcSlSjclByGzlslid() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "list/pl/" + gzlslid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        ObjectMapper objectMapper = new ObjectMapper();
        List<BdcSlSjclDO> bdcSlSjclDOList = new ArrayList<>();
        bdcSlSjclDOList.add(bdcSlSjclDO);
        String requestJson = objectMapper.writeValueAsString(bdcSlSjclDOList);
        JSONAssert.assertEquals(responseSuccess, requestJson, true);
        responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "list/" + UUIDGenerator.generate16()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        Assert.assertEquals(responseSuccess, "[]");
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }



    @Test
    public void test4updateBdcSlSjcl() throws Exception {
        bdcSlSjclDO.setBz("test002");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlSjclDO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(responseSuccess, "1");
        mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new String()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test5updateSjcl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(PATH +xmid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.put(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test6updateBdcSlSjcl() throws Exception {
        bdcSlSjclDO.setBz("test003");
        List<BdcSlSjclDO> bdcSlSjclDOList =new ArrayList<>();
        bdcSlSjclDOList.add(bdcSlSjclDO);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlSjclDOList);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH +"list/")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(responseSuccess, "1");
        mockMvc.perform(MockMvcRequestBuilders.put(PATH +"list/")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new String()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test7listSjclMc() throws Exception {
        List<String> sjclmc = new ArrayList<>();
        String mc  = "收件单";
        sjclmc.add(mc);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "sjclmc/").param("xmid",xmid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(sjclmc);
        JSONAssert.assertEquals(responseSuccess,requestJson,true);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test8listCshBdcSlSjcl() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "csh").param("xmid",xmid).param("gzlslid",gzlslid).param("djxl","8000401"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);

    }

    @Test
    public void test91changeSjclSxh() throws Exception {
        String czlx = "up";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH +"sxh/"+ sjclid +"/"+ czlx))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test92checkSjclYsFs() throws Exception {
        gzlslid ="85350";
        String responseSuccess =mockMvc.perform(MockMvcRequestBuilders.get(PATH + "gzyz/ysfs/" +gzlslid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(responseSuccess,"false");
    }

    @Test
    public void test93deleteBdcSlSjclBySjclid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + sjclid))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }



    @Test
    public void test95reCreateSjcl() throws Exception {
        gzlslid = "test";
        mockMvc.perform(MockMvcRequestBuilders.get(PATH +"/recreate/"+gzlslid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.get(PATH +"/recreate/"+gzlslid))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test96saveDsfSjcl() throws Exception {
        gzlslid = "test";
        List<BdcDsfSjclDTO> bdcDsfSjclDTOList = new ArrayList<>();
        BdcDsfSjclDTO bdcDsfSjclDTO = new BdcDsfSjclDTO();
        bdcDsfSjclDTO.setClmc("ceshi");
        bdcDsfSjclDTO.setYs(1);
        bdcDsfSjclDTO.setFs(1);
        bdcDsfSjclDTO.setBase64fj("1111111111111");
        bdcDsfSjclDTOList.add(bdcDsfSjclDTO);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcDsfSjclDTOList);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH +"dsf/"+gzlslid)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(responseSuccess, "1");
        mockMvc.perform(MockMvcRequestBuilders.put(PATH +"dsf/"+gzlslid)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new String()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
