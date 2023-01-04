package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.init.App;
import cn.gtmap.realestate.init.web.BaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/1
 * @description 权利人信息获取接口测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config-dbunit.xml"})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = App.class)
public class RestBdcQlrControllerTest extends BaseController{
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final String PATH = "/init/rest/v1.0/qlr";

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQlrSetup-data.xml")
    public void listBdcQlr() throws Exception{
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid("32");
        bdcQlrQO.setZjh("8748879");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcQlrQO);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post("/init/rest/v1.0/qlr/list")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("[{\"qlrid\":\"1234321\",\"xmid\":\"32\",\"qlrmc\":null,\"zjzl\":null,\"zjh\":\"8748879\",\"txdz\":null,\"yb\":null,\"xb\":null,\"frmc\":null,\"frdh\":null,\"frzjzl\":null,\"frzjh\":null,\"dlrmc\":null,\"dlrdh\":null,\"dlrzjzl\":null,\"dlrzjh\":null,\"dljg\":null,\"qlrlx\":null,\"qlrlb\":\"1\",\"qlbl\":null,\"gyfs\":null,\"gyqk\":null,\"dh\":null,\"sshy\":null,\"bz\":null,\"sfczr\":null,\"sxh\":null,\"bdcqzh\":null,\"zsid\":null,\"qlrftmj\":null,\"lzr\":null,\"lzrdh\":null,\"lzrzjzl\":null,\"lzrzjh\":null,\"qlrwbzt\":null}]",responseSuccess);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQlrSetup-data.xml")
    public void insertBdcQlr() throws Exception{
        BdcQlrDO bdcQlrDO = newBdcQlr();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcQlrDO);

        //mock进行模拟
        // 正常返回测试
        String response = mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("{\"qlrid\":\"1694684531684691621654\",\"xmid\":\"7878787878\",\"qlrmc\":\"测试保存更新\",\"zjzl\":null,\"zjh\":\"1231231231432\",\"txdz\":null,\"yb\":null,\"xb\":null,\"frmc\":\"法人\",\"frdh\":null,\"frzjzl\":null,\"frzjh\":null,\"dlrmc\":null,\"dlrdh\":null,\"dlrzjzl\":null,\"dlrzjh\":null,\"dljg\":null,\"qlrlx\":2,\"qlrlb\":\"1\",\"qlbl\":null,\"gyfs\":null,\"gyqk\":\"qwqeqe\",\"dh\":null,\"sshy\":null,\"bz\":null,\"sfczr\":1,\"sxh\":null,\"bdcqzh\":null,\"zsid\":\"453534534\",\"qlrftmj\":null,\"lzr\":null,\"lzrdh\":null,\"lzrzjzl\":null,\"lzrzjh\":null,\"qlrwbzt\":null}",response);
        // 信息为空
        String responsenull = mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new String()))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();

        Assert.assertEquals("{\"code\":1,\"msg\":\"系统异常，请联系管理员\",\"detail\":[\"Required request body is missing: public cn.gtmap.realestate.common.core.domain.BdcQlrDO cn.gtmap.realestate.init.web.rest.BdcQlrRestController.insertBdcQlr(cn.gtmap.realestate.common.core.domain.BdcQlrDO)\"]}",responsenull);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQlrSetup-data.xml")
    public void updateBdcQlr() throws Exception{
        BdcQlrDO bdcQlrDO = newBdcQlr();
        bdcQlrDO.setQlrid("123456");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcQlrDO);
        //mock进行模拟
        String response = mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("0",response);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQlrSetup-data.xml")
    public void deleteBdcQlr() throws Exception{
        String qlrid = "12uo28";
        String response = mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/" + qlrid)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(new String()))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
    }

    public BdcQlrDO newBdcQlr(){
        BdcQlrDO bdcQlrDO = new BdcQlrDO();
        bdcQlrDO.setQlrid("1694684531684691621654");
        bdcQlrDO.setQlrmc("测试保存更新");
        bdcQlrDO.setQlrlb("1");
        bdcQlrDO.setXmid("7878787878");
        bdcQlrDO.setZjh("1231231231432");
        bdcQlrDO.setQlrlx(2);
        bdcQlrDO.setSfczr(1);
        bdcQlrDO.setZsid("453534534");
        bdcQlrDO.setGyqk("qwqeqe");
        bdcQlrDO.setFrmc("法人");
        return bdcQlrDO;
    }
}
