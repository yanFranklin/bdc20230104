package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.building.BaseUnitTest;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
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
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018/11/6
 * @description 户室服务测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class FwHsTest extends BaseUnitTest {
    private static final String PATH = "/building/rest/v1.0/hs";
    @Autowired
    private EntityMapper entityMapper;

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 修改户室信息
     */
    @Test
    @Transactional
    @Rollback(true)
    public void updateFwHs() throws Exception {
        FwHsDO fwHsDO = new FwHsDO();
        fwHsDO.setFwHsIndex("f166e79ef25648484a7166e79ef20001");
        fwHsDO.setWlcs(18);
        fwHsDO.setFjh("18");
        fwHsDO.setSxh(18);
        fwHsDO.setZl("18");
        fwHsDO.setFwDcbIndex("1062");

        FwHsDO errorFwHsDO = new FwHsDO();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(fwHsDO);
        String errorRequestJson = objectMapper.writeValueAsString(errorFwHsDO);
        mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/f166e79ef25648484a7166e79ef20001")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        FwHsDO fwHsDO1 = entityMapper.selectByPrimaryKey(FwHsDO.class,fwHsDO.getFwHsIndex());
        FwHsDO fwHsDO2 = new FwHsDO();
        BeanUtils.copyProperties(fwHsDO1,fwHsDO2);
        Assert.assertEquals(fwHsDO1.getFjh(),fwHsDO.getFjh());
        mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/f166e79ef25648484a7166e79ef20001")
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
     * @description 新增户室信息
     */
    @Test
    @Transactional
    @Rollback(true)
    public void insertFwHs() throws Exception {
        FwHsDO fwHsDO = new FwHsDO();
        fwHsDO.setWlcs(0);
        fwHsDO.setFjh("11");
        fwHsDO.setSxh(12);
        fwHsDO.setFwDcbIndex("1062");
        fwHsDO.setFwHsIndex(UUIDGenerator.generate());

        FwHsDO errorFwHsDO = new FwHsDO();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(fwHsDO);
        String errorRequestJson = objectMapper.writeValueAsString(errorFwHsDO);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwHsDO fwHsDO11 = entityMapper.selectByPrimaryKey(FwHsDO.class,fwHsDO.getFwHsIndex());
        Assert.assertEquals(fwHsDO.getFwHsIndex(),fwHsDO11.getFwHsIndex());
        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(errorRequestJson))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 根据主键删除户室信息
     */
    @Test
    public void deleteHsByFwHsIndex() throws Exception {
        FwHsDO fwHsDO = new FwHsDO();
        fwHsDO.setFwHsIndex("f166e7c37a8d48484a7166e7c37a0001");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(fwHsDO);
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/f166e7c37a8d48484a7166e7c37a0001")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwHsDO fwHsDO1 =entityMapper.selectByPrimaryKey(FwHsDO.class,fwHsDO.getFwHsIndex());
        Assert.assertNull(fwHsDO1);
        FwHsDO fwHsDO3 = new FwHsDO();
        String requestJsonError = objectMapper.writeValueAsString(fwHsDO3);
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH)
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
     * @description 根据BDCDYH查询户室基本信息
     */
    @Test
    public void queryFwhsByBdcdyh() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/bdcdy/{bdcdyh}", "320802001063GB00025F00010033"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH + "/bdcdy/{bdcdyh}", "320802001063GB00025F00010033987"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("",responseEmpty);
        String responseError = mockMvc.perform(get(PATH + "/bdcdy"))
                    .andExpect(status().is5xxServerError())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();
    }
}