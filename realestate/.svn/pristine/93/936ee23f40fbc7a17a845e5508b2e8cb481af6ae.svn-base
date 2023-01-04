package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.building.BaseUnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018/11/1
 * @description 林权信息服务测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class LqTest extends BaseUnitTest{
    private static final String PATH = "/building/rest/v1.0/lq";

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 根据BDCDYH查询林权基本信息
     */
    @Test
    public void queryLqByBdcdyh() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/{bdcdyh}", "320802007003JE00002L00000001"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH + "/{bdcdyh}", "320802007003JE00002L00000001987"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("",responseEmpty);
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 分页查询林权信息
     */
    @Test
    public void listLqDcbByPageJson() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/page")
                .param("bdcdyh", "320802006004GE00001L00000001")
                .param("tdzl","004地籍子区")
                .param("qlr","wbh3"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/page")
                .param("bdcdyh", "320802006004GE00001L000000012345")
                .param("tdzl","111")
                .param("qlr","111"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 通过dcbIndex查询林地所有权人
     */
    @Test
    public void listLdsyqrByDcbIndex() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/ldsyqr/dcbIndex/{dcbIndex}","7305e32a-8318-4401-81ea-94c617983dc2"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH + "/ldsyqr/dcbIndex/{dcbIndex}","7305e32a-8318-4401-81ea-94c617983dc2676767"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("[]",responseEmpty);
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 通过dcbIndex查询林木使用权人
     */
    @Test
    public void listLmsuqrByDcbIndex() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/lmsuqr/dcbIndex/{dcbIndex}","7305e32a-8318-4401-81ea-94c617983dc2"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH + "/lmsuqr/dcbIndex/{dcbIndex}","7305e32a-8318-4401-81ea-94c617983dc2676767"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("[]",responseEmpty);
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 通过dcbIndex查询林木所有权人
     */
    @Test
    public void listLmsyqrByDcbIndex() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/lmsyqr/dcbIndex/{dcbIndex}","7305e32a-8318-4401-81ea-94c617983dc2"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH + "/lmsyqr/dcbIndex/{dcbIndex}","7305e32a-8318-4401-81ea-94c617983dc2676767"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("[]",responseEmpty);
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 通过djh查询林地所有权人
     */
    @Test
    public void listLdsyqrByDjh() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/ldsyqr/djh/{djh}","320802006004GE00001"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH + "/ldsyqr/djh/{djh}","320802006004GE000012345"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("[]",responseEmpty);
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 通过djh查询林木使用权人
     */
    @Test
    public void listLmsuqrByDjh() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/lmsuqr/djh/{djh}","320802006004GE00001"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH + "/lmsuqr/djh/{djh}","320802006004GE000012345"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("[]",responseEmpty);
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 通过djh查询林木所有权人
     */
    @Test
    public void listLmsyqrByDjh() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/lmsyqr/djh/{djh}","320802006004GE00001"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH + "/lmsyqr/djh/{djh}","320802006004GE000012345"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("[]",responseEmpty);
    }
}
