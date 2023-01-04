package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.building.BaseUnitTest;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.dto.building.FwFcQlrRequestDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018/11/6
 * @description 房产权利人服务测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class FwFcQlrTest extends BaseUnitTest{

    @Autowired
    private EntityMapper entityMapper;

    private static final String PATH = "/building/rest/v1.0/fcqlr";

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 根据主键删除权利人信息
     */
    @Test
    public void deleteFcqlrByFwFcqlrIndex() throws Exception {
        FwFcqlrDO fwFcqlrDO = new FwFcqlrDO();
        fwFcqlrDO.setFwFcqlrIndex("123");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(fwFcqlrDO);
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/123")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwFcqlrDO fwFcqlrDO1 = entityMapper.selectByPrimaryKey(FwFcqlrDO.class,fwFcqlrDO.getFwFcqlrIndex());
        Assert.assertNull(fwFcqlrDO1);
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 新增权利人信息
     */
    @Test
    @Transactional
    @Rollback(true)
    public void insertQlr() throws Exception {
        FwFcqlrDO fwFcqlrDO = new FwFcqlrDO();
        fwFcqlrDO.setBdcqzh("123456789");
        fwFcqlrDO.setQlr("xyq");
        fwFcqlrDO.setQlrzjh("123456789");
        fwFcqlrDO.setFwIndex("101566");
        fwFcqlrDO.setFwFcqlrIndex(UUIDGenerator.generate());

        FwFcqlrDO fwFcqlrDOError = new FwFcqlrDO();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(fwFcqlrDO);
        String requestJsonError = objectMapper.writeValueAsString(fwFcqlrDOError);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwFcqlrDO fwFcqlrDO1 = entityMapper.selectByPrimaryKey(FwFcqlrDO.class,fwFcqlrDO.getFwFcqlrIndex());
        Assert.assertEquals(fwFcqlrDO1.getFwFcqlrIndex(),fwFcqlrDO.getFwFcqlrIndex());
        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJsonError))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 新增房屋房产权利人
     */
    @Test
    @Transactional
    @Rollback(true)
    public void batchInsertFwFcQlr() throws Exception {
        List<FwFcqlrDO> list = new ArrayList<>();
        FwFcqlrDO fwFcqlrDO = new FwFcqlrDO();
        fwFcqlrDO.setBdcqzh("123456789");
        fwFcqlrDO.setQlr("qqq");
        fwFcqlrDO.setQlrzjh("123456789");
        fwFcqlrDO.setFwIndex("101566");
        fwFcqlrDO.setFwFcqlrIndex(UUIDGenerator.generate());
        FwFcqlrDO fwFcqlrDO1 = new FwFcqlrDO();
        fwFcqlrDO1.setBdcqzh("123123");
        fwFcqlrDO1.setQlr("aaa");
        fwFcqlrDO1.setQlrzjh("123123");
        fwFcqlrDO1.setFwIndex("101566");
        fwFcqlrDO1.setFwFcqlrIndex(UUIDGenerator.generate());
        list.add(fwFcqlrDO);
        list.add(fwFcqlrDO1);

        List<FwFcqlrDO> listError = new ArrayList<>();
        FwFcqlrDO fwFcqlrDOError = new FwFcqlrDO();
        listError.add(fwFcqlrDOError);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(list);
        String requestJsonError = objectMapper.writeValueAsString(listError);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/batchadd")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwFcqlrDO fwFcqlrDO2 = entityMapper.selectByPrimaryKey(FwFcqlrDO.class,fwFcqlrDO.getFwFcqlrIndex());
        FwFcqlrDO fwFcqlrDO3 = entityMapper.selectByPrimaryKey(FwFcqlrDO.class,fwFcqlrDO1.getFwFcqlrIndex());
        Assert.assertEquals(fwFcqlrDO2.getFwFcqlrIndex(),fwFcqlrDO.getFwFcqlrIndex());
        Assert.assertEquals(fwFcqlrDO3.getFwFcqlrIndex(),fwFcqlrDO1.getFwFcqlrIndex());
        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/batchadd")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJsonError))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 修改房屋房产权利人（单个）
     */
    @Test
    @Transactional
    @Rollback(true)
    public void updateFwFcQlr() throws Exception {
        FwFcqlrDO fwFcqlrDO = new FwFcqlrDO();
        fwFcqlrDO.setBdcqzh("1298");
        fwFcqlrDO.setQlr("aaa");
        fwFcqlrDO.setQlrzjh("123456");
        fwFcqlrDO.setFwIndex("101566");
        fwFcqlrDO.setFwFcqlrIndex("f166ecf13a0048484a7166eced250002");

        FwFcqlrDO fwFcqlrDOError = new FwFcqlrDO();
        fwFcqlrDOError.setFwIndex("101566");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(fwFcqlrDO);
        String requestJsonError = objectMapper.writeValueAsString(fwFcqlrDOError);
        mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/f166ecf13a0048484a7166eced250002")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwFcqlrDO fwFcqlrDO1 = entityMapper.selectByPrimaryKey(FwFcqlrDO.class,fwFcqlrDO.getFwFcqlrIndex());
        Assert.assertEquals(fwFcqlrDO1.getBdcqzh(),fwFcqlrDO.getBdcqzh());
        mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/f166ecf13a0048484a7166eced250002")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJsonError))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 批量修改权利人信息
     */
    @Test
    @Transactional
    @Rollback(true)
    public void batchUpdateFwFcQlr() throws Exception {
        List<FwFcqlrDO> list = new ArrayList<>();
        FwFcqlrDO fwFcqlrDO = new FwFcqlrDO();
        fwFcqlrDO.setBdcqzh("123321");
        fwFcqlrDO.setQlr("aaa");
        fwFcqlrDO.setQlrzjh("123456");
        fwFcqlrDO.setFwIndex("101566");
        fwFcqlrDO.setFwFcqlrIndex("f166ecf13a0048484a7166eced250003");
        FwFcqlrDO fwFcqlrDO1 = new FwFcqlrDO();
        fwFcqlrDO1.setBdcqzh("1298");
        fwFcqlrDO1.setQlr("bbb");
        fwFcqlrDO1.setQlrzjh("123456");
        fwFcqlrDO1.setFwIndex("101566");
        fwFcqlrDO1.setFwFcqlrIndex("f166ecf13a0048484a7166eced250002");
        list.add(fwFcqlrDO);
        list.add(fwFcqlrDO1);

        List<FwFcqlrDO> listError = new ArrayList<>();
        FwFcqlrDO fwFcqlrDOError = new FwFcqlrDO();
        fwFcqlrDOError.setQlmj(35.46);
        listError.add(fwFcqlrDOError);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(list);
        String requestJsonError = objectMapper.writeValueAsString(listError);
        mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/batchUpdate")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwFcqlrDO fwFcqlrDO2 = entityMapper.selectByPrimaryKey(FwFcqlrDO.class,fwFcqlrDO.getFwFcqlrIndex());
        FwFcqlrDO fwFcqlrDO3 = entityMapper.selectByPrimaryKey(FwFcqlrDO.class,fwFcqlrDO1.getFwFcqlrIndex());
        Assert.assertEquals(fwFcqlrDO2.getBdcqzh(),fwFcqlrDO.getBdcqzh());
        Assert.assertEquals(fwFcqlrDO3.getBdcqzh(),fwFcqlrDO1.getBdcqzh());
        mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/batchUpdate")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJsonError))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 根据房屋户室主键查询权利人
     */
    @Test
    public void listQlr() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/list/{fwHsIndex}", "db85c74a-404f-46d5-8350-4d066a9b5594"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH + "/list/{fwHsIndex}", "db85c74a-404f-46d5-8350-4d066a9b559499999"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("",responseEmpty);
        String responseError =  mockMvc.perform(get(PATH + "/list"))
                .andExpect(status().is5xxServerError())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 根据BDCDYH查询房屋房产权利人
     */
    @Test
    public void listQlrByBdcdyh() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/listbybdcdyh/{bdcdyh}", "320802001057GB00176F00020007"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH + "/listbybdcdyh/{bdcdyh}?bdcdyfwlx=1", "320802001057GB00176F00020007"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("[]",responseEmpty);
    }
}
