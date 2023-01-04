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
 * @description 承包宗地信息服务测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class CbzdTest extends BaseUnitTest{

    private static final String PATH = "/building/rest/v1.0/cbzd";

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 根据BDCDYH查询承包宗地基本信息
     */
    @Test
    public void queryCbzdByBdcdyh() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/{bdcdyh}", "320802007003JE00002L00000001"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH + "/{bdcdyh}", "320802007003JE00002L00000001345"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("",responseEmpty);
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 根据BDCDYH查询发包方信息
     */
    @Test
    public void listFbfByBdcdyh() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/fbf/{bdcdyh}", "320802006001GC00001W00000000"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH + "/fbf/{bdcdyh}", "320802006001GC00001W00000000345"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("[]",responseEmpty);
    }
    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 根据BDCDYH查询承包宗地承包方信息
     */
    @Test
    public void listCbfByBdcdyh() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/cbf/{bdcdyh}", "320802006001GC00001W00000000"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH + "/cbf/{bdcdyh}", "320802006001GC00001W00000000234"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("[]",responseEmpty);
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 分页查询承包宗地信息
     */
    @Test
    public void listCbzdBdcdy() throws Exception {
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/page")
                .param("bdcdyh", "320802006001GC00001W00000000")
                .param("djh","320802006001GC00001"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/page")
                .param("bdcdyh", "320802006001GC00001W00000000123")
                .param("djh","320802006001GC00001"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

}
