package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.building.BaseUnitTest;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
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
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018/11/10
 * @description 预测户室服务测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class FwYcHsTest extends BaseUnitTest {
    @Autowired
    private EntityMapper entityMapper;

    private static final String PATH = "/building/rest/v1.0/ychs";

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 根据BDCDYH查询户室基本信息
     */
    @Test
    public void queryFwYcHsByBdcdyh() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/bdcdy/{bdcdyh}", "320805003022GB00024F00020133"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH + "/bdcdy/{bdcdyh}", "320805003022GB00024F000201332324"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("",responseEmpty);
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 新增预测户室服务
     */
    @Test
    @Transactional
    @Rollback(true)
    public void insertFwYcHs() throws Exception {
        FwYchsDO fwYchsDO = new FwYchsDO();
        fwYchsDO.setFwHsIndex(UUIDGenerator.generate());
        fwYchsDO.setBdcdyh("230123333");
        fwYchsDO.setFwDcbIndex("1062");

        FwYchsDO ErrorFwYchsDO = new FwYchsDO();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(fwYchsDO);
        String errorRequestJson = objectMapper.writeValueAsString(ErrorFwYchsDO);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwYchsDO fwYchsDO1 = entityMapper.selectByPrimaryKey(FwYchsDO.class,fwYchsDO.getFwHsIndex());
        Assert.assertEquals(fwYchsDO1.getFwHsIndex(),fwYchsDO.getFwHsIndex());
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
     * @description 修改户室信息
     */
    @Test
    @Transactional
    @Rollback(true)
    public void updateFwHs() throws Exception {
        FwYchsDO fwYchsDO = new FwYchsDO();
        fwYchsDO.setFwHsIndex("f166fccd7d7648484a7166fccd7d0001");
        fwYchsDO.setBdcdyh("12344321");
        fwYchsDO.setFwDcbIndex("1066");

        FwYchsDO ErrorFwYchsDO = new FwYchsDO();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(fwYchsDO);
        String errorRequestJson = objectMapper.writeValueAsString(ErrorFwYchsDO);
        mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/f166fccd7d7648484a7166fccd7d0001")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwYchsDO fwYchsDO1 = entityMapper.selectByPrimaryKey(FwYchsDO.class,fwYchsDO.getFwHsIndex());
        Assert.assertEquals(fwYchsDO1.getBdcdyh(),fwYchsDO.getBdcdyh());
        mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/f166fccd7d7648484a7166fccd7d0001")
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
     * @description 根据主键删除户室信息
     */
    @Test
    public void deleteYcHsByFwHsIndex() throws Exception {
        FwYchsDO fwYchsDO = new FwYchsDO();
        fwYchsDO.setFwHsIndex("c9f48adbe49f06f2");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(fwYchsDO);
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/00ef0b31-370e-42e3-a74b-fcac54f4bfdd")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwYchsDO fwYchsDO1 = entityMapper.selectByPrimaryKey(FwYchsDO.class,fwYchsDO.getFwHsIndex());
        Assert.assertNull(fwYchsDO1);
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
     * @description 通过逻辑幢主键分页查询预测户室信息
     */
    @Test
    public void listYchsByFwDcbIndex() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/{fwDcbIndex}/page", "2e4304e6-9164-41b5-b978-37946bf1ba2c"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/{fwDcbIndex}/page", "2e4304e6-9164-41b5-b978-37946bf1ba2c234"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }


}
