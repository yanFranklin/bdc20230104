package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.building.BaseUnitTest;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018/11/13
 * @description 不动产单元号现势状态
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class BdcdyZtTest extends BaseUnitTest {
    private static final String PATH = "/building/rest/v1.0/bdcdyhzt";

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param
     * @return void
     * @description 根据BDCDYH查询不动产单元状态
     */
    @Test
    public void queryBdcdyhZtBybdcdyh() throws Exception {
        String responseSuccess = mockMvc.perform(get(PATH + "/{bdcdyh}", "320802001005GB00047F00020001"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
        String responseEmpty = mockMvc.perform(get(PATH + "/{bdcdyh}", "320802001005GB00047F000200012344"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void cshYxxmDxc() throws Throwable {
        TestRunnable[] trs = new TestRunnable[5];
        for (int i = 0; i < 5; i++) {
            TestRunnable runnable = new TestRunnable() {
                @Override
                public void runTest() throws Throwable {
                    BdcdyhZtRequestDTO bdcdyhZtRequestDTO = new BdcdyhZtRequestDTO();
                    bdcdyhZtRequestDTO.setBdcdyh("320802001001GB00008F00010001");
                    bdcdyhZtRequestDTO.setCf(1);
                    ObjectMapper objectMapper = new ObjectMapper();
                    String requestJson = objectMapper.writeValueAsString(bdcdyhZtRequestDTO);
                    LOGGER.info("<=====成功测试开始=====>");
                    for(int i = 0; i < 2; i++) {
                        mockMvc.perform(MockMvcRequestBuilders.put(PATH+"/320802001001GB00008F00010001")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(requestJson))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andDo(MockMvcResultHandlers.print())
                                .andReturn();
                    }
                    LOGGER.info("<=====成功测试结束=====>");
                }
            };
            trs[i] = runnable;
        }

        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);
        // 开发并发执行数组里定义的内容
        mttr.runTestRunnables();
    }
}
