package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.building.BaseUnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018/11/1
 * @description 宗海服务测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class ZhTest extends BaseUnitTest {
    private static final String PATH = "/building/rest/v1.0/zh";

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 根据不动产单元号查询宗海地籍数据
     */
    @Test
    public void queryZhByBdcdyh() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/{bdcdyh}", "320802000000GG00642W0000000"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH + "/{bdcdyh}", "320802000000GG00642W000000011"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("",responseEmpty);
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 根据BDCDYH查询宗海权利人信息
     */
    @Test
    public void listZhQlrByBdcdyh() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/qlr/{bdcdyh}", "320802000000GG00641W00000000"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH + "/qlr/{bdcdyh}", "320802000000GG00641W00000000234"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("[]",responseEmpty);
    }
}
