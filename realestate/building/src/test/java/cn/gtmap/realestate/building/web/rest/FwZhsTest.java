package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.building.BaseUnitTest;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.domain.building.FwZhsDO;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018/11/6
 * @description 子户室服务测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class FwZhsTest extends BaseUnitTest{
    @Autowired
    private EntityMapper entityMapper;
    private static final String PATH = "/building/rest/v1.0/zhs";

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 删除子户室
     */
    @Test
    public void deleteZhsByFwZhsIndex() throws Exception {
        FwZhsDO fwZhs = new FwZhsDO();
        fwZhs.setFwZhsIndex("9f4b7f60-553f-405f-af67-b6be144d177a");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(fwZhs);
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/9f4b7f60-553f-405f-af67-b6be144d177a")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwZhsDO fwZhs1 = entityMapper.selectByPrimaryKey(FwZhsDO.class,fwZhs.getFwZhsIndex());
        Assert.assertNull(fwZhs1);
        String errorRequestJson = objectMapper.writeValueAsString(fwZhs);
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH )
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(errorRequestJson))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 新增子户室
     */
    @Test
    @Transactional
    @Rollback(true)
    public void insertZhs() throws Exception {
        FwZhsDO fwZhs = new FwZhsDO();
        fwZhs.setWlcs(12);
        fwZhs.setFjh("12");
        fwZhs.setSxh(12);
        fwZhs.setFwHsIndex("f166e7c37a8d48484a7166e7c37a0001");
        fwZhs.setFwZhsIndex(UUIDGenerator.generate());
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(fwZhs);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwZhsDO fwZhs1 = entityMapper.selectByPrimaryKey(FwZhsDO.class,fwZhs.getFwZhsIndex());
        Assert.assertEquals(fwZhs1.getFwZhsIndex(),fwZhs.getFwZhsIndex());
        FwZhsDO errorFwZhs = new FwZhsDO();
        String errorRequestJson = objectMapper.writeValueAsString(errorFwZhs);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(errorRequestJson))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 批量新增子户室
     */
    @Test
    @Transactional
    @Rollback(true)
    public void batchInsert() throws Exception {
        List<FwZhsDO> list = new ArrayList<>();
        FwZhsDO fwZhs = new FwZhsDO();
        fwZhs.setWlcs(12);
        fwZhs.setFjh("12");
        fwZhs.setSxh(12);
        fwZhs.setFwHsIndex("f166e7c37a8d48484a7166e7c37a0001");
        fwZhs.setFwZhsIndex(UUIDGenerator.generate());
        FwZhsDO fwZhs1 = new FwZhsDO();
        fwZhs1.setWlcs(12);
        fwZhs1.setFjh("12");
        fwZhs1.setSxh(12);
        fwZhs1.setFwHsIndex("f166e7c37a8d48484a7166e7c37a0001");
        fwZhs1.setFwZhsIndex(UUIDGenerator.generate());
        list.add(fwZhs);
        list.add(fwZhs1);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(list);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/batchadd")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwZhsDO fwZhs2 = entityMapper.selectByPrimaryKey(FwZhsDO.class,fwZhs.getFwZhsIndex());
        Assert.assertEquals(fwZhs2.getFwZhsIndex(),fwZhs.getFwZhsIndex());
        FwZhsDO fwZhs3 = entityMapper.selectByPrimaryKey(FwZhsDO.class,fwZhs1.getFwZhsIndex());
        Assert.assertEquals(fwZhs3.getFwZhsIndex(),fwZhs1.getFwZhsIndex());

        List<FwZhsDO> errorList = new ArrayList<>();
        FwZhsDO errorFwZhs = new FwZhsDO();
        errorList.add(errorFwZhs);
        String errorRequestJson = objectMapper.writeValueAsString(errorList);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/batchadd")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(errorRequestJson))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Transactional
    @Rollback(true)
    public void relevanceZhs() throws Exception {
        List<FwZhsDO> list = new ArrayList<>();
        FwZhsDO fwZhsDO = new FwZhsDO();
        fwZhsDO.setFwHsIndex("0009e5fe-b354-4b38-b9e6-b81f067ad26d");
        fwZhsDO.setFwZhsIndex("012f19d5-e05a-40dc-820b-30ae46a88257");
        list.add(fwZhsDO);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(list);
        mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/relevance/0009e5fe-b354-4b38-b9e6-b81f067ad26d")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwZhsDO fwZhsDO1 = entityMapper.selectByPrimaryKey(FwZhsDO.class,fwZhsDO.getFwZhsIndex());
        Assert.assertEquals(fwZhsDO.getFwHsIndex(), fwZhsDO1.getFwHsIndex());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void cancelRelevanceZhs() throws Exception {
        List<FwZhsDO> list = new ArrayList<>();
        FwZhsDO fwZhsDO = new FwZhsDO();
        fwZhsDO.setFwZhsIndex("012f19d5-e05a-40dc-820b-30ae46a88257");
        list.add(fwZhsDO);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(list);
        mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/cancelRelevance")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwZhsDO fwZhsDO1 = entityMapper.selectByPrimaryKey(FwZhsDO.class,fwZhsDO.getFwZhsIndex());
        Assert.assertEquals(null, fwZhsDO1.getFwHsIndex());
    }
}
