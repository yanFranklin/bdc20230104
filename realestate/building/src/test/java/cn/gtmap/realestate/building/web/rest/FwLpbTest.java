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
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-05
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class FwLpbTest extends BaseUnitTest {

    private static final String PATH = "/building/rest/v1.0/{code}/fwhs/{fwHsIndex}";

    private static final String PATH_LIST = "/building/rest/v1.0/{code}/{fwDcbIndex}/fwhs";

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 查询楼盘表数据
     */
    @Test
    public void queryFwHsListByFwDcbIndex() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH_LIST,"ad","10339")).andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH_LIST,"ad","103399999")).andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("",responseEmpty);
    }

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 查询户室基本数据
     */
    @Test
    public void queryFwHsByIndex() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH,"ad","10339")).andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH,"ad","10339789666")).andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("",responseEmpty);
    }
}