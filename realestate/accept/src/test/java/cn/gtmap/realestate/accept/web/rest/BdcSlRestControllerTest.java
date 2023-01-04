package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理rest服务 单元测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdcSlRestControllerTest {

    private static final String PATH = "/realestate-accept/rest/v1.0/sl/";

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void queryBdcSlxx() throws Exception {
        String jbxxid = "6dc1f87d53463aeda2e747e6";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + jbxxid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test0queryBdcSlxx() throws Exception {
        String jbxxid = "6dc1f87d53463aeda2e747e6";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH +"zlslxx/"+ jbxxid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updateBdcSlxx() throws Exception {
        BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        bdcSlJbxxDO.setJbxxid("6dc1f87d53463aeda2e747e6");
        bdcSlJbxxDO.setSlbh("226534");
        bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlxxDTO);
        mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new String()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void deleteBdcSlxx() throws Exception {
        String gzlslid = "111111111";
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + gzlslid))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void cshBdcSlxx() throws Exception {
        String jbxxid = "6dc1f87d53463aeda2e747e6";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + jbxxid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void cshSjcl() throws Exception {
        String gzlslid = "111111111";
        mockMvc.perform(MockMvcRequestBuilders.get(PATH +"cshsjcl/"+ gzlslid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void queryBdcdjxlPzBdclx() throws Exception {
        String processDefId = "fNfpkcNQab8pX8QH";
        String bdcdyh = "230123001006GB00007F00380059";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "djxlpz").param("processDefId",processDefId).param("bdcdyh",bdcdyh))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH+ "djxlpz"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Rollback
    public void wwsqCjBdcXm() throws Exception {
        WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO = new WwsqCjBdcXmRequestDTO();
        wwsqCjBdcXmRequestDTO.setGzyz(true);
        wwsqCjBdcXmRequestDTO.setSfss(true);
        BdcSlJbxxDO bdcSlJbxxDO =new BdcSlJbxxDO();
        bdcSlJbxxDO.setGzldyid("jsEKwceZQswjPAyx");
        bdcSlJbxxDO.setSlr("liaoxiang");
        BdcSlXmDO bdcSlXmDO =new BdcSlXmDO();
        bdcSlXmDO.setBdcdyh("340104483027GB00011F00110000");
        bdcSlXmDO.setXmid("20200120");
        bdcSlXmDO.setDjxl("2000402");
        BdcSlXmLsgxDO bdcSlXmLsgxDO =new BdcSlXmLsgxDO();
        bdcSlXmLsgxDO.setYxmid("E41LPV4WI9HIDWS2");
        bdcSlXmLsgxDO.setXmid("20200120");
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList =new ArrayList<>();
        bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
        List<BdcSlXmDTO> bdcSlXmDTOList =new ArrayList<>();
        BdcSlXmDTO bdcSlXmDTO =new BdcSlXmDTO();
        bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);
        bdcSlXmDTO.setBdcSlXmLsgxList(bdcSlXmLsgxDOList);
        DsfSlxxDTO dsfSlxxDTO =new DsfSlxxDTO();
        dsfSlxxDTO.setJyje(122.00);
        dsfSlxxDTO.setSply("3");
        bdcSlXmDTO.setDsfSlxxDTO(dsfSlxxDTO);

        BdcSlSqrDO bdcSlSqrDO =new BdcSlSqrDO();
        bdcSlSqrDO.setSqrmc("李谷");
        bdcSlSqrDO.setXmid("20200120");
        bdcSlSqrDO.setSbfwtc("1");
        bdcSlSqrDO.setHyzk("未婚");
        bdcSlSqrDO.setSqrlb("1");
        bdcSlSqrDO.setSfjm(0);
        bdcSlSqrDO.setSfbdhj(1);
        List<BdcSlJtcyDO> bdcSlJtcyDOList =new ArrayList<>();
        BdcSlJtcyDO bdcSlJtcyDO =new BdcSlJtcyDO();
        bdcSlJtcyDO.setJtcymc("李利");
        bdcSlJtcyDOList.add(bdcSlJtcyDO);
        List<BdcSlSqrDTO> bdcSlSqrDTOList =new ArrayList<>();
        BdcSlSqrDTO bdcSlSqrDTO =new BdcSlSqrDTO();
        bdcSlSqrDTO.setBdcSlSqrDO(bdcSlSqrDO);
        bdcSlSqrDTO.setBdcSlJtcyDOList(bdcSlJtcyDOList);
        bdcSlSqrDTOList.add(bdcSlSqrDTO);
        bdcSlXmDTO.setBdcSlSqrDTOList(bdcSlSqrDTOList);
        List<BdcSlSqrDO> bdcSlSqrDOList =new ArrayList<>();
        bdcSlSqrDOList.add(bdcSlSqrDO);
        bdcSlXmDTO.setBdcSlSqrDOList(bdcSlSqrDOList);

        BdcSlJyxxDO bdcSlJyxxDO =new BdcSlJyxxDO();
        bdcSlJyxxDO.setHtbh("20200219");
        bdcSlJyxxDO.setHtbasj(new Date());
        bdcSlJyxxDO.setZzsje(1.0d);
        bdcSlXmDTO.setBdcSlJyxxDO(bdcSlJyxxDO);

        List<BdcSwxxDTO> bdcSwxxDTOList =new ArrayList<>();
        BdcSwxxDTO bdcSwxxDTO =new BdcSwxxDTO();
        BdcSlHsxxDO bdcSlHsxxDO =new BdcSlHsxxDO();
        bdcSlHsxxDO.setHdjsjg(20.00);
        bdcSlHsxxDO.setSqrlb("1");
        bdcSwxxDTO.setBdcSlHsxxDO(bdcSlHsxxDO);

        List<BdcSlHsxxMxDO> bdcSlHsxxMxDOList =new ArrayList<>();
        BdcSlHsxxMxDO bdcSlHsxxMxDO =new BdcSlHsxxMxDO();
        bdcSlHsxxMxDO.setSjnse(100.00);
        bdcSlHsxxMxDO.setJmse(20.00);
        bdcSlHsxxMxDO.setYnse(80.00);
        bdcSlHsxxMxDOList.add(bdcSlHsxxMxDO);
        BdcSlHsxxMxDO bdcSlHsxxMxDO1 =new BdcSlHsxxMxDO();
        bdcSlHsxxMxDO1.setSjnse(100.00);
        bdcSlHsxxMxDO1.setJmse(10.00);
        bdcSlHsxxMxDO1.setYnse(90.00);
        bdcSlHsxxMxDOList.add(bdcSlHsxxMxDO1);
        bdcSwxxDTO.setBdcSlHsxxMxDOList(bdcSlHsxxMxDOList);
        bdcSwxxDTOList.add(bdcSwxxDTO);
        bdcSlXmDTO.setBdcSwxxDTOList(bdcSwxxDTOList);
        bdcSlXmDTOList.add(bdcSlXmDTO);
        BdcSlxxDTO bdcSlxxDTO =new BdcSlxxDTO();
        bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);
        bdcSlxxDTO.setBdcSlXmList(bdcSlXmDTOList);
        wwsqCjBdcXmRequestDTO.setBdcSlxxDTO(bdcSlxxDTO);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(wwsqCjBdcXmRequestDTO);
        String responseSuccess =mockMvc.perform(MockMvcRequestBuilders.post(PATH+"wwsq")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();;
    }


    @Test
    public void wwsqCjBdcXmGzyz() throws Exception {
        BdcSlJbxxDO bdcSlJbxxDO =new BdcSlJbxxDO();
        bdcSlJbxxDO.setGzldyid("jsEKwceZQswjPAyx");
        bdcSlJbxxDO.setSlr("liaoxiang");
        BdcSlXmDO bdcSlXmDO =new BdcSlXmDO();
        bdcSlXmDO.setBdcdyh("340104403005GB00098F00050075");
        bdcSlXmDO.setXmid("20200120");
        bdcSlXmDO.setDjxl("2000402");
        BdcSlXmLsgxDO bdcSlXmLsgxDO =new BdcSlXmLsgxDO();
        bdcSlXmLsgxDO.setYxmid("HWZ7SU2J2Y09Y74M");
        bdcSlXmLsgxDO.setXmid("20200120");
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList =new ArrayList<>();
        bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
        List<BdcSlXmDTO> bdcSlXmDTOList =new ArrayList<>();
        BdcSlXmDTO bdcSlXmDTO =new BdcSlXmDTO();
        bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);
        bdcSlXmDTO.setBdcSlXmLsgxList(bdcSlXmLsgxDOList);
        DsfSlxxDTO dsfSlxxDTO =new DsfSlxxDTO();
        dsfSlxxDTO.setJyje(122.00);
        bdcSlXmDTO.setDsfSlxxDTO(dsfSlxxDTO);

        BdcSlSqrDO bdcSlSqrDO =new BdcSlSqrDO();
        bdcSlSqrDO.setSqrmc("李谷");
        bdcSlSqrDO.setXmid("20200120");
        bdcSlSqrDO.setSbfwtc("1");
        bdcSlSqrDO.setHyzk("未婚");
        bdcSlSqrDO.setSqrlb("1");
        bdcSlSqrDO.setSfjm(0);
        bdcSlSqrDO.setSfbdhj(1);
        List<BdcSlJtcyDO> bdcSlJtcyDOList =new ArrayList<>();
        BdcSlJtcyDO bdcSlJtcyDO =new BdcSlJtcyDO();
        bdcSlJtcyDO.setJtcymc("李利");
        bdcSlJtcyDOList.add(bdcSlJtcyDO);
        List<BdcSlSqrDTO> bdcSlSqrDTOList =new ArrayList<>();
        BdcSlSqrDTO bdcSlSqrDTO =new BdcSlSqrDTO();
        bdcSlSqrDTO.setBdcSlSqrDO(bdcSlSqrDO);
        bdcSlSqrDTO.setBdcSlJtcyDOList(bdcSlJtcyDOList);
        bdcSlSqrDTOList.add(bdcSlSqrDTO);
        bdcSlXmDTO.setBdcSlSqrDTOList(bdcSlSqrDTOList);
        List<BdcSlSqrDO> bdcSlSqrDOList =new ArrayList<>();
        bdcSlSqrDOList.add(bdcSlSqrDO);
        bdcSlXmDTO.setBdcSlSqrDOList(bdcSlSqrDOList);

        BdcSlJyxxDO bdcSlJyxxDO =new BdcSlJyxxDO();
        bdcSlJyxxDO.setHtbh("20200219");
        bdcSlJyxxDO.setHtbasj(new Date());
        bdcSlJyxxDO.setZzsje(1.0d);
        bdcSlXmDTO.setBdcSlJyxxDO(bdcSlJyxxDO);

        bdcSlXmDTOList.add(bdcSlXmDTO);
        BdcSlxxDTO bdcSlxxDTO =new BdcSlxxDTO();
        bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);
        bdcSlxxDTO.setBdcSlXmList(bdcSlXmDTOList);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlxxDTO);
        String responseSuccess =mockMvc.perform(MockMvcRequestBuilders.post(PATH+"wwsq/gzyz")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void wwsqAutoTurn() throws Exception {
        String gzlslid ="test";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "wwsq/autoturn/"+gzlslid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH+ "wwsq/autoturn"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void zlcshBdcSlxx() throws Exception {
        String jbxxid ="test";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "zlcsh/"+jbxxid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH+ "zlcsh/"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void cshBdcSlSqxx() throws Exception {
        BdcSlCshDTO bdcSlCshDTO = new BdcSlCshDTO();
        bdcSlCshDTO.setJbxxid("1111");
        bdcSlCshDTO.setGzlslid("aaaaa");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlCshDTO);
        String responseSuccess =mockMvc.perform(MockMvcRequestBuilders.post(PATH+"sqxx")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();;
    }

    @Test
    public void getNslxdData() throws Exception {
        String processInsId ="test";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "nslxd")
                .param("processInsId",processInsId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH+ "nslxd"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void cshBdcSlYcslxx() throws Exception {
        BdcSlCshDTO bdcSlCshDTO = new BdcSlCshDTO();
        bdcSlCshDTO.setJbxxid("1111");
        bdcSlCshDTO.setGzlslid("aaaaa");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlCshDTO);
        String responseSuccess =mockMvc.perform(MockMvcRequestBuilders.post(PATH+"ycslxx")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();;
    }

    @Test
    public void initTsBdcDjxx() throws Exception {
        BdcTsDjxxRequestDTO bdcTsDjxxRequestDTO = new BdcTsDjxxRequestDTO();
        bdcTsDjxxRequestDTO.setGzlslid("111");
        bdcTsDjxxRequestDTO.setJbxxid("1111");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcTsDjxxRequestDTO);
        String responseSuccess =mockMvc.perform(MockMvcRequestBuilders.post(PATH+"tsdjxx")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();;
    }

    @Test
    public void changeAjztEnd() throws Exception {
        String processInsId = "1111";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "ajzt")
                .param("processInsId",processInsId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updateYwslzt() throws Exception {
        String processInsId = "1111";
        int ywslzt = 1;
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "ywslzt/"+ywslzt)
                .param("processInsId",processInsId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void syncYcslxx() throws Exception {
        String gzlslid = "111";
        String xmid = "2222";
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "ycsl")
                .param("gzlslid",gzlslid)
                .param("xmid",xmid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void listYcslxxList() throws Exception {
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setXmid("1111");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlXmQO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "list")
                .param("bdcSlXmQO",requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
    }

}
