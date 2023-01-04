package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.building.BaseUnitTest;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/1
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class LjzTest extends BaseUnitTest {
    @Autowired
    private EntityMapper entityMapper;

    private static final String PATH = "/building/rest/v1.0/ljz";

    /**
     * @param
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询逻辑幢信息
     */
    @Test
    public void listLjzByPageJson() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/page").param("sort", "bdcdyh,desc").param("fwmc", "北京北路162号"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/page").param("fwmc", "北京北路162号345678"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        String responseError = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/page").param("sort", "hhh"))
                .andExpect(status().is5xxServerError())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 根据主键查询房屋逻辑幢
     */
    @Test
    public void queryLjzByFwDcbIndex() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/10621"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/773456"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("",responseEmpty);
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 删除逻辑幢信息
     */
    @Test
    public void deleteLjzByFwDcbIndex() throws Exception {
        FwLjzDO fwLjzDO = new FwLjzDO();
        fwLjzDO.setFwDcbIndex("10610");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(fwLjzDO);
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/10610")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwLjzDO fwLjzDO1 = entityMapper.selectByPrimaryKey(FwLjzDO.class,fwLjzDO.getFwDcbIndex());
        Assert.assertNull(fwLjzDO1);
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
     * @description 修改逻辑幢信息
     */
    @Test
    @Transactional
    @Rollback(true)
    public void updateFwLjz() throws Exception {
        FwLjzDO fwLjzDO = new FwLjzDO();
        fwLjzDO.setLszd("320802001011GB0011");
        fwLjzDO.setZrzh("123");
        fwLjzDO.setBdcdyfwlx("4");
        fwLjzDO.setFwcs(19);
        fwLjzDO.setFwDcbIndex("1062");

        FwLjzDO errorFwLjzDO = new FwLjzDO();
        errorFwLjzDO.setFwDcbIndex("1062");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(fwLjzDO);
        String errorRequestJson = objectMapper.writeValueAsString(errorFwLjzDO);
        mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/1062?fwDcbIndex=1062&lszd=320802001011GB0088&zrzh=2&bdcdyfwlx=4&fwcs=19")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwLjzDO fwLjzDO1 = entityMapper.selectByPrimaryKey(FwLjzDO.class,fwLjzDO.getFwDcbIndex());
        Assert.assertEquals(fwLjzDO.getLszd(),fwLjzDO1.getLszd());
        mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/1062?fwDcbIndex=1062&lszd=320802001011GB0088&zrzh=2&bdcdyfwlx=4&fwcs=19")
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
     * @description 新增逻辑幢
     */
    @Test
    @Transactional
    @Rollback(true)
    public void insertFwLjz() throws Exception {
        FwLjzDO fwLjzDO = new FwLjzDO();
        fwLjzDO.setLszd("320802001011GB0018");
        fwLjzDO.setZldz("哈尔滨市");
        fwLjzDO.setZrzh("123");
        fwLjzDO.setBdcdyfwlx("4");
        fwLjzDO.setFwcs(19);
        fwLjzDO.setFwDcbIndex("1062");
        fwLjzDO.setFwDcbIndex(UUIDGenerator.generate());

        FwLjzDO errorFwLjzDO = new FwLjzDO();
        errorFwLjzDO.setFwDcbIndex("1062");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(fwLjzDO);
        String errorRequestJson = objectMapper.writeValueAsString(errorFwLjzDO);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwLjzDO fwLjzDO1 = entityMapper.selectByPrimaryKey(FwLjzDO.class,fwLjzDO.getFwDcbIndex());
        Assert.assertEquals(fwLjzDO1.getFwDcbIndex(),fwLjzDO.getFwDcbIndex());
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
     * @description 根据宗地号 或 宗地号+自然幢号 查询逻辑幢列表
     */
    @Test
    public void listLjzByDjhAndZrzh() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/list/{djh}?zrzh=1" ,"320802001063GB00025"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH + "/list/{djh}?zrzh=1","320802001063GB00025789"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("",responseEmpty);
    }
}