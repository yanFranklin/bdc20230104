package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.building.BaseUnitTest;
import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
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
 * @version 1.0  2018/10/31
 * @description 测试项目信息分页功能
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class XmxxTest extends BaseUnitTest {

    @Autowired
    private EntityMapper entityMapper;

    private static final String PATH = "/building/rest/v1.0/xmxx";
    /**
     * @param
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 测试项目信息分页功能
     */
    @Test
    public void listXmxxByPageJson() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/page").param("sort", "bdcdyh,desc").param("xmmc", "淮安市广州路55路"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/page").param("xmmc", "淮安市广州路55路1"))
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
     * @description 新增项目信息
     */
    @Test
    @Transactional
    @Rollback(true)
    public void insertFwXmxx() throws Exception {
        FwXmxxDO fwXmxxDO = new FwXmxxDO();
        fwXmxxDO.setDytdmj(99.99);
        fwXmxxDO.setFttdmj(99.99);
        fwXmxxDO.setJyjg(99.99);
        fwXmxxDO.setXmmc("123456");
        fwXmxxDO.setFwlx("2");
        fwXmxxDO.setFwxz("2");
        fwXmxxDO.setZl("南京");
        fwXmxxDO.setLszd("123456");
        fwXmxxDO.setFwXmxxIndex(UUIDGenerator.generate());

        FwXmxxDO errorFwXmxxDO = new FwXmxxDO();

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(fwXmxxDO);
        String errorRequestJson = objectMapper.writeValueAsString(errorFwXmxxDO);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwXmxxDO fwXmxxDO1 = entityMapper.selectByPrimaryKey(FwXmxxDO.class,fwXmxxDO.getFwXmxxIndex());
        Assert.assertEquals(fwXmxxDO.getFwXmxxIndex(),fwXmxxDO1.getFwXmxxIndex());
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
     * @description 修改项目信息
     */
    @Test
    @Transactional
    @Rollback(true)
    public void updateFwXmxx() throws Exception {
        FwXmxxDO fwXmxxDO = new FwXmxxDO();
        fwXmxxDO.setFwXmxxIndex("1CKG1332EF6KF30G");
        fwXmxxDO.setLszd("320811004002GB00010");
        fwXmxxDO.setDytdmj(99.99);
        fwXmxxDO.setFttdmj(99.99);
        fwXmxxDO.setJyjg(99.99);
        fwXmxxDO.setXmmc("123456");
        fwXmxxDO.setFwlx("2");
        fwXmxxDO.setFwxz("2");
        fwXmxxDO.setZl("南京");

        FwXmxxDO errorFwXmxxDO = new FwXmxxDO();
        errorFwXmxxDO.setFwXmxxIndex("1CKG1332EF6KF30G");
        errorFwXmxxDO.setJyjg(1.11);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(fwXmxxDO);
        String errorRequestJson = objectMapper.writeValueAsString(errorFwXmxxDO);
        mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/1CKG1332EF6KF30G")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwXmxxDO fwXmxxDO1 = entityMapper.selectByPrimaryKey(FwXmxxDO.class,fwXmxxDO.getFwXmxxIndex());
        Assert.assertEquals(fwXmxxDO1.getXmmc(),fwXmxxDO.getXmmc());
        mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/1CKG1332EF6KF30G")
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
     * @description 删除项目信息
     */
    @Test
    public void deleteXmxxByFwXmxxIndex() throws Exception {
        FwXmxxDO fwXmxxDO = new FwXmxxDO();
        fwXmxxDO.setFwXmxxIndex("cc13f1da-34f2-4ba4-9787-4a10e0fee44a");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(fwXmxxDO);
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/cc13f1da-34f2-4ba4-9787-4a10e0fee44a")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        FwXmxxDO fwXmxxDO1 = entityMapper.selectByPrimaryKey(FwXmxxDO.class,fwXmxxDO.getFwXmxxIndex());
        Assert.assertNull(fwXmxxDO1);
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}