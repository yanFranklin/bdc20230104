package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.init.App;
import cn.gtmap.realestate.init.web.BaseController;
import com.alibaba.fastjson.JSONObject;
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

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0  2018/10/31
 * @description 查询不动产信息测试
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
public class BdcXmRestServiceTest extends BaseController{
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final String PATH = "/init/rest/v1.0/xmxx";
    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcXmSetup-data.xml")
    public void listBdcXm() throws Exception {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid("147575");
        bdcXmQO.setSlbh("51651615");
        String requestJson = JSONObject.toJSONString(bdcXmQO);

        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post("/init/rest/v1.0/xmxx/list")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("[{\"xmid\":\"147575\",\"slbh\":\"51651615\",\"qllx\":4,\"djlx\":2,\"djyy\":\"22\",\"sqzsbs\":0,\"ybdcdyh\":null,\"sqfbcz\":1,\"bz\":\"34ewrwer444\",\"sqrsm\":\"345345\",\"ajzt\":1,\"sfwtaj\":0,\"gzljdmc\":\"为期请问群无\",\"qxdm\":\"22222\",\"slsj\":\"2018-11-13 01:45:54\",\"slrid\":\"64645645\",\"slr\":\"撒大声地\",\"xmly\":0,\"jssj\":\"2018-11-27 00:00:00\",\"djxl\":\"55\",\"bdclx\":2,\"ycqzh\":\"是否4324\",\"yfczh\":\"2342\",\"ytdzh\":\"546\",\"gzlslid\":\"34444444444\",\"spxtywh\":\"热舞若3\",\"ssxz\":\"3454635\",\"djjg\":\"驱蚊器翁\",\"spxtblzt\":0,\"spxtblztmc\":\"6764345\",\"bdcdyh\":\"320508055012GB13335F65900000\",\"bdcdywybh\":\"rwerwrw\",\"gyfs\":0,\"djsj\":\"2018-11-27 00:00:00\",\"dbr\":\"登簿人\",\"qszt\":0,\"zl\":\"个任务而我认为\",\"dzwmj\":22.44,\"dzwyt\":3,\"dzwyt2\":4,\"dzwyt3\":1,\"yhlxa\":0,\"yhlxb\":0,\"gzwlx\":0,\"lz\":0,\"mjdw\":0,\"zdzhmj\":0.0,\"qlr\":\"权利人\",\"qlrzjh\":\"2313\",\"ywr\":\"而体育\",\"ywrzjh\":\"6545\",\"jyhth\":\"驱蚊器\",\"bdcdyfwlx\":1,\"bdcqzh\":\"2222\",\"qlxz\":\"111\",\"clsjppzt\":null,\"cnqx\":null,\"gzldymc\":null,\"gzldyid\":null,\"qlrzjzl\":null,\"ywrzjzl\":null,\"qlrlx\":null,\"ytdytmc\":null,\"djbmdm\":null,\"zdzhyt\":null,\"zdzhyt2\":null,\"zdzhyt3\":null,\"bfqlqtzk\":null,\"ysfwbm\":null,\"zfxzspbh\":null,\"yxtcqzh\":null,\"sply\":null,\"swfybh\":null,\"zydjybsq\":0}]",responseSuccess);

        bdcXmQO.setXmid("123342");
        requestJson = JSONObject.toJSONString(bdcXmQO);
        String responseEmpty = mockMvc.perform(MockMvcRequestBuilders.post("/init/rest/v1.0/xmxx/list")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("[]",responseEmpty);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcXmSetup-data.xml")
    public void updateBdcXm() throws Exception{
        BdcXmDO bdcXmDO = newBdcXmDO();
        bdcXmDO.setXmid("26849498129582");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcXmDO);
        //mock进行模拟
        String response = mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("1",response);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcXmSetup-data.xml")
    public void bdcQlPageByPageJson() throws Exception{
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/page").param("bdcQlQoStr", "{\"qlrmc\":\"权利人2\",\"qlrmcmh\":\"1\"}").param("size","1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertTrue(responseSuccess.indexOf("78694196459698") >-1);
        String responseEmpty = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "/page").param("bdcQlQoStr", "{\"bdcqzh\":\"证号667\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("{\"content\":[],\"last\":true,\"totalElements\":0,\"totalPages\":0,\"number\":0,\"size\":20,\"sort\":null,\"first\":true,\"numberOfElements\":0}",responseEmpty);
    }

    public BdcXmDO newBdcXmDO(){
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setSlbh("123123");
        bdcXmDO.setBdclx(1);
        bdcXmDO.setGzlslid("84984998");
        bdcXmDO.setQllx(1);
        bdcXmDO.setDjsj(new Date());
        bdcXmDO.setBdcdyh("2103413123F00008373748");
        bdcXmDO.setBz("werwerwe");
        bdcXmDO.setQlr("测试项目更新");
        bdcXmDO.setDjyy("登记原因");
        bdcXmDO.setDbr("管理员");
        bdcXmDO.setZdzhmj(708.98);
        bdcXmDO.setQxdm("320501");
        return bdcXmDO;
    }

}