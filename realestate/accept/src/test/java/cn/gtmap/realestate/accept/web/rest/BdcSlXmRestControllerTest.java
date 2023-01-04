package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCshSlxmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理项目rest服务 单元测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdcSlXmRestControllerTest {
    private static final String PATH = "/realestate-accept/rest/v1.0/xm/";
    private static BdcSlXmDO bdcSlXmDO;
    private static BdcCshSlxmDTO bdcCshSlxmDTO;
    private static BdcSlYwxxDTO bdcSlYwxxDTO;
    private static List<BdcSlYwxxDTO> bdcSlYwxxDTOList;
    private static String jbxxid;
    private static String xmid;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void test0insertBdcSlXm() throws Exception {
        jbxxid = UUIDGenerator.generate16();
        xmid = UUIDGenerator.generate16();
        bdcSlXmDO = new BdcSlXmDO();
        bdcSlXmDO.setXmid(xmid);
        bdcSlXmDO.setJbxxid(jbxxid);
        bdcSlXmDO.setBdcdyh("320682");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlXmDO);
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
    public void test1queryBdcSlXmByXmid() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + xmid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlXmDO);
        JSONAssert.assertEquals(responseSuccess, requestJson, true);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test2listBdcSlXmByJbxxid() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "list/" + jbxxid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        ObjectMapper objectMapper = new ObjectMapper();
        List<BdcSlXmDO> bdcSlXmDOList = new ArrayList<>();
        bdcSlXmDOList.add(bdcSlXmDO);
        String requestJson = objectMapper.writeValueAsString(bdcSlXmDOList);
        JSONAssert.assertEquals(responseSuccess, requestJson, true);
        responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "list/" + UUIDGenerator.generate16()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        Assert.assertEquals(responseSuccess, "[]");
        mockMvc.perform(MockMvcRequestBuilders.get(PATH + "list/"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    @Test
    public void test3updateBdcSlXm() throws Exception {
        bdcSlXmDO.setBdcdyh("test002");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlXmDO);
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
    public void test4updateBdcSlXm() throws Exception {
        List<BdcSlXmDO> bdcSlXmDOList =new ArrayList<>();
        bdcSlXmDO.setBdcdyh("test003");
        bdcSlXmDOList.add(bdcSlXmDO);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlXmDOList);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH+"list/")
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
    public void test5listBdcSLXmByPageJson() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/yx/page").param("jbxxid", jbxxid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void test6listBdcSlYwxxByJbxxid() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "yx/ywxx/list/" + jbxxid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        bdcSlYwxxDTOList= JSONObject.parseArray(responseSuccess,BdcSlYwxxDTO.class);
        Assert.assertEquals(bdcSlYwxxDTOList.get(0).getXmid(), bdcSlXmDO.getXmid());
        mockMvc.perform(MockMvcRequestBuilders.get(PATH + "yx/ywxx/list/"))
                .andExpect(status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test7wlZs() throws Exception{
        bdcCshSlxmDTO =new BdcCshSlxmDTO();
        bdcCshSlxmDTO.setJbxxid(jbxxid);
        bdcSlYwxxDTO =new BdcSlYwxxDTO();
        bdcSlYwxxDTO.setXmid("f166f665708c402c80d266f665700001");
        bdcSlYwxxDTOList =new ArrayList<>();
        bdcSlYwxxDTOList.add(bdcSlYwxxDTO);
        bdcCshSlxmDTO.setBdcSlYwxxDTOList(bdcSlYwxxDTOList);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcCshSlxmDTO);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH+"wlzs")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post(PATH+"wlzs")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new String()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void test8queryYxBdcdyDTO() throws Exception {
        String jbxxid = "602575";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/yx").param("jbxxid", jbxxid))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void test91updateBdcSlXmZsxh() throws Exception {
        String jbxxid = "602541";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "list/1/" + jbxxid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH + "yx/list/"))
                .andExpect(status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test92deleteYxFwhs() throws Exception {
        String jbxxid = "602541";
        String fwhsIndexs = "4aaf949f-0015-4395-be14-a7e89d0a1475";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "list/deleteYxFwhs").param("fwhsIndexs",fwhsIndexs).param("jbxxid",jbxxid))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(System.currentTimeMillis());
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH+"list/deleteYxFwhs"))
                .andExpect(status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test93deleteBdcSlXmByXmid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + xmid))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test94deleteBdcSlXmByJbxxid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "list/" + jbxxid))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "list/"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test95queryYxBdcdyDTOGroupByDjxl() throws Exception {
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setJbxxid("test");
        bdcSlXmQO.setDjxl("2");
        bdcSlXmQO.setBdcdyh("3222222222222222222222222");
        String slXmQO = JSON.toJSONString(bdcSlXmQO);
        jbxxid = "test";
        String gzldyid = "test";

        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "yx/groupbydjxl")
                .param("slXmQO",slXmQO)
                .param("jbxxid",jbxxid)
                .param("gzldyid",gzldyid)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "yx/groupbydjxl")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void test96batchUpdateBdcSlXmZsxh() throws Exception {
        String zsxh = "11111111";
        jbxxid = "1111111";
        String djxl = "2";

        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "list/batchUpdateZsxh")
                .param("zsxh",zsxh)
                .param("jbxxid",jbxxid)
                .param("djxl",djxl))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "list/batchUpdateZsxh"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test96listGwcData() throws Exception {
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setJbxxid("test");
        bdcSlXmQO.setDjxl("2");
        bdcSlXmQO.setBdcdyh("3222222222222222222222222");
        String slXmQO = JSON.toJSONString(bdcSlXmQO);

        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "yx/gwc/list")
                .param("slXmQO",slXmQO)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "yx/gwc/list")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void test97bdcdyhIsRepeat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "list/" + jbxxid))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "list/"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test97queryBdcSlXmByLsgx() throws Exception {
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setJbxxid("test");
        bdcSlXmQO.setDjxl("2");
        bdcSlXmQO.setBdcdyh("3222222222222222222222222");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlXmQO);

        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/xmlsgx/bdcSlXm")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/xmlsgx/bdcSlXm")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new String()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void test99getLzrq() throws Exception {
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setXmid("111111111");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcXmDO);

        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "lzrq")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "lzrq")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new String()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }




}
