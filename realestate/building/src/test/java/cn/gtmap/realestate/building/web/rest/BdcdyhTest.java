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
 * Created by gtis on 2018/11/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class BdcdyhTest extends BaseUnitTest{
    private static final String PATH = "/building/rest/v1.0/bdcdyh";
    @Test
    public void creatFwBdcdyhByDjhAndZrzh() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/{djh}/{zrzh}", "320802007003GB00001","1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    public void creatFwBdcdyhByFwDcbIndex() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/{fwDcbIndex}", "00d444ec118249ab910602f87822dbb4"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }

}
