package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcShxxDO;
import cn.gtmap.realestate.register.App;
import cn.gtmap.realestate.register.BaseUnitTest;
import cn.gtmap.realestate.register.service.impl.BdcShxxServiceImplTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * BdcShxxController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11/01/2018</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class RestBdcShxxControllerTest extends BaseUnitTest {
    private static final String PATH = "/register/rest/v1.0/shxx";

    @Test
    public void queryBdcShxx() throws Exception {
// 模拟向testRest发送get请求
        String responseSuccess = mockMvc.perform(get(PATH + "/list").param("shxxid", "1").param("xmid", "123456"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
// 模拟向testRest发送get请求
        String responseEmpty = mockMvc.perform(get(PATH + "/list").param("shxxid", "1").param("xmid", "123456").param("gzlslid", "0"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
// 模拟向testRest发送get请求
        String responseError = mockMvc.perform(get(PATH + "/list"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void insertBdcShxx() throws Exception {
        String shxxid = "123458";
        BdcShxxDO bdcShxxDO = new BdcShxxDO();
        bdcShxxDO.setShxxid(shxxid);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcShxxDO);

        //mock进行模拟
        // 正常返回测试
        mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/" + shxxid)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // 信息为空
        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new String()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updateBdcShxx() throws Exception {
        insertBdcShxx();
        String shxxid = "123458";
        String jdmc = "测试节点名称";
        BdcShxxDO bdcShxxDO = new BdcShxxDO();
        bdcShxxDO.setShxxid("1");
        bdcShxxDO.setShyj("测试审核意见");
        bdcShxxDO.setShjssj(new Date());
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcShxxDO);

        //mock进行模拟
        // 对象缺少占位符参数返回测试
        mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/" + shxxid)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.put(PATH + "/" + shxxid + "/" + jdmc)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void deleteBdcShxx() throws Exception {
        insertBdcShxx();
        String shxxid = "123458";
        String jdmc = "测试节点名称";
        BdcShxxDO bdcShxxDO = new BdcShxxDO();
        bdcShxxDO.setShxxid("1");
        bdcShxxDO.setShxxid(shxxid);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcShxxDO);
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/" + shxxid + "/" + jdmc)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void deleteBdcShxxByShxxid() throws Exception {
        insertBdcShxx();
        BdcShxxDO bdcShxxDO = new BdcShxxDO();
        bdcShxxDO.setShxxid("1");
        bdcShxxDO.setShxxid("xm1");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcShxxDO);
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/2")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

} 
