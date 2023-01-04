package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/2
 * @description 单表查询权利基本信息
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
public class RestBdcQllxControllerTest extends BaseController {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private EntityMapper entityMapper;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void queryQlxx() throws Exception {
        MvcResult responseSuccess = mockMvc.perform(get("/init/rest/v1.0/qlxx/445566"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andDo(print())
                .andReturn();
        Assert.assertNotNull(responseSuccess);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void listQlxx()throws Exception{
        String responseSuccess = mockMvc.perform(get("/init/rest/v1.0/qlxx/list/slbh").param("slbh", "1232123344"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("[{\"@Clazz\":\"cn.gtmap.realestate.common.core.domain.BdcFdcqDO\",\"qlid\":\"1\",\"tdsyqr\":\"tdsyqr\",\"tdsyqssj\":\"2018-11-13 01:45:54\",\"tdsyjssj\":\"2018-11-13 01:45:54\",\"jyjg\":2222.0,\"ghyt\":1,\"fwxz\":2,\"fwjg\":3,\"szc\":6,\"zcs\":11,\"jzmj\":222.0,\"zyjzmj\":200.0,\"ftjzmj\":22.0,\"jgsj\":\"2018-11-13 1:45:54\",\"dytdmj\":20.0,\"fttdmj\":200.0,\"slbh\":\"1232123344\",\"djlx\":21,\"djyy\":\"djyy\",\"qllx\":4,\"xmid\":\"445566\",\"gyqk\":\"单独所有\",\"djjg\":\"djjg\",\"djsj\":\"2018-11-13 01:45:54\",\"dbr\":\"dbr\",\"fj\":null,\"qszt\":1,\"zh\":null,\"fwlx\":4,\"szmyc\":\"6\",\"cg\":3.0,\"myzcs\":\"11\",\"tdsyqmj\":50000.0,\"bz\":\"bz\",\"fwqqh\":\"fwqqh\",\"zzdmj\":400.0,\"fwsx\":1,\"bdcdyh\":\"bdcdyh\",\"bdcdywybh\":\"bdcwybh\",\"zl\":\"zl\",\"bdcdyfwlx\":1,\"sfgyz\":0,\"fjh\":null,\"zxywh\":null,\"zxyy\":null,\"zxdbr\":null,\"zxdjsj\":null}]",responseSuccess);

        String responseSuccess2 = mockMvc.perform(get("/init/rest/v1.0/qlxx/list/processInsId").param("processInsId", "34444444444"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("[{\"@Clazz\":\"cn.gtmap.realestate.common.core.domain.BdcFdcqDO\",\"qlid\":\"1\",\"tdsyqr\":\"tdsyqr\",\"tdsyqssj\":\"2018-11-13 01:45:54\",\"tdsyjssj\":\"2018-11-13 01:45:54\",\"jyjg\":2222.0,\"ghyt\":1,\"fwxz\":2,\"fwjg\":3,\"szc\":6,\"zcs\":11,\"jzmj\":222.0,\"zyjzmj\":200.0,\"ftjzmj\":22.0,\"jgsj\":\"2018-11-13 1:45:54\",\"dytdmj\":20.0,\"fttdmj\":200.0,\"slbh\":\"1232123344\",\"djlx\":21,\"djyy\":\"djyy\",\"qllx\":4,\"xmid\":\"445566\",\"gyqk\":\"单独所有\",\"djjg\":\"djjg\",\"djsj\":\"2018-11-13 01:45:54\",\"dbr\":\"dbr\",\"fj\":null,\"qszt\":1,\"zh\":null,\"fwlx\":4,\"szmyc\":\"6\",\"cg\":3.0,\"myzcs\":\"11\",\"tdsyqmj\":50000.0,\"bz\":\"bz\",\"fwqqh\":\"fwqqh\",\"zzdmj\":400.0,\"fwsx\":1,\"bdcdyh\":\"bdcdyh\",\"bdcdywybh\":\"bdcwybh\",\"zl\":\"zl\",\"bdcdyfwlx\":1,\"sfgyz\":0,\"fjh\":null,\"zxywh\":null,\"zxyy\":null,\"zxdbr\":null,\"zxdjsj\":null}]",responseSuccess);

        }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void listZxQlxx()throws Exception{
        String responseSuccess = mockMvc.perform(get("/init/rest/v1.0/zxqlxx/list/slbh").param("slbh", "1232123344"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("[{\"@Clazz\":\"cn.gtmap.realestate.common.core.domain.BdcFdcqDO\",\"qlid\":\"45984926354\",\"tdsyqr\":\"tdr\",\"tdsyqssj\":\"2018-11-13 01:45:54\",\"tdsyjssj\":\"2018-11-13 01:45:54\",\"jyjg\":2222.0,\"ghyt\":1,\"fwxz\":2,\"fwjg\":3,\"szc\":6,\"zcs\":11,\"jzmj\":222.0,\"zyjzmj\":200.0,\"ftjzmj\":22.0,\"jgsj\":\"2018-11-13 1:45:54\",\"dytdmj\":20.0,\"fttdmj\":200.0,\"slbh\":\"124\",\"djlx\":21,\"djyy\":\"djyy\",\"qllx\":4,\"xmid\":\"niibnik\",\"gyqk\":\"单独所有\",\"djjg\":\"djjg\",\"djsj\":\"2018-11-13 01:45:54\",\"dbr\":\"dbr\",\"fj\":null,\"qszt\":1,\"zh\":null,\"fwlx\":4,\"szmyc\":\"6\",\"cg\":3.0,\"myzcs\":\"11\",\"tdsyqmj\":50000.0,\"bz\":\"bz\",\"fwqqh\":\"fwqqh\",\"zzdmj\":400.0,\"fwsx\":1,\"bdcdyh\":\"bdcdyh\",\"bdcdywybh\":\"bdcwybh\",\"zl\":\"zl\",\"bdcdyfwlx\":1,\"sfgyz\":0,\"fjh\":null,\"zxywh\":null,\"zxyy\":null,\"zxdbr\":null,\"zxdjsj\":null}]",responseSuccess);

        String responseSuccess2 = mockMvc.perform(get("/init/rest/v1.0/zxqlxx/list/processInsId").param("processInsId", "34444444444"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("[{\"@Clazz\":\"cn.gtmap.realestate.common.core.domain.BdcFdcqDO\",\"qlid\":\"45984926354\",\"tdsyqr\":\"tdr\",\"tdsyqssj\":\"2018-11-13 01:45:54\",\"tdsyjssj\":\"2018-11-13 01:45:54\",\"jyjg\":2222.0,\"ghyt\":1,\"fwxz\":2,\"fwjg\":3,\"szc\":6,\"zcs\":11,\"jzmj\":222.0,\"zyjzmj\":200.0,\"ftjzmj\":22.0,\"jgsj\":\"2018-11-13 1:45:54\",\"dytdmj\":20.0,\"fttdmj\":200.0,\"slbh\":\"124\",\"djlx\":21,\"djyy\":\"djyy\",\"qllx\":4,\"xmid\":\"niibnik\",\"gyqk\":\"单独所有\",\"djjg\":\"djjg\",\"djsj\":\"2018-11-13 01:45:54\",\"dbr\":\"dbr\",\"fj\":null,\"qszt\":1,\"zh\":null,\"fwlx\":4,\"szmyc\":\"6\",\"cg\":3.0,\"myzcs\":\"11\",\"tdsyqmj\":50000.0,\"bz\":\"bz\",\"fwqqh\":\"fwqqh\",\"zzdmj\":400.0,\"fwsx\":1,\"bdcdyh\":\"bdcdyh\",\"bdcdywybh\":\"bdcwybh\",\"zl\":\"zl\",\"bdcdyfwlx\":1,\"sfgyz\":0,\"fjh\":null,\"zxywh\":null,\"zxyy\":null,\"zxdbr\":null,\"zxdjsj\":null}]",responseSuccess);

    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void updateTdsyqTset() throws Exception{
        BdcTdsyqDO bdcTdsyqDO = new BdcTdsyqDO();
        bdcTdsyqDO.setQlid("45984926354");
        bdcTdsyqDO=entityMapper.select(bdcTdsyqDO).get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcTdsyqDO);
        String num = mockMvc.perform(MockMvcRequestBuilders.put("/init/rest/v1.0/qlxx/tdsyq")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("1",num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void updateJsydsyqTset() throws Exception{
        BdcJsydsyqDO bdcJsydsyqDO = new BdcJsydsyqDO();
        bdcJsydsyqDO.setQlid("45984354");
        bdcJsydsyqDO=entityMapper.select(bdcJsydsyqDO).get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcJsydsyqDO);
        String num = mockMvc.perform(MockMvcRequestBuilders.put("/init/rest/v1.0/qlxx/jsydsyq")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("1",num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void updateFdcqTset() throws Exception{
        BdcFdcqDO bdcFdcqDO = new BdcFdcqDO();
        bdcFdcqDO.setQlid("45984926354");
        bdcFdcqDO=entityMapper.select(bdcFdcqDO).get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcFdcqDO);
        String num = mockMvc.perform(MockMvcRequestBuilders.put("/init/rest/v1.0/qlxx/fdcq")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("1",num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void updateTdcbnydsyqTset() throws Exception{
        BdcTdcbnydsyqDO bdcTdcbnydsyqDO = new BdcTdcbnydsyqDO();
        bdcTdcbnydsyqDO.setQlid("45984354");
        bdcTdcbnydsyqDO=entityMapper.select(bdcTdcbnydsyqDO).get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcTdcbnydsyqDO);
        String num = mockMvc.perform(MockMvcRequestBuilders.put("/init/rest/v1.0/qlxx/tdcbnydsyq")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("1",num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void updateLqTset() throws Exception{
        BdcLqDO bdcLqDO = new BdcLqDO();
        bdcLqDO.setQlid("4598464354");
        bdcLqDO=entityMapper.select(bdcLqDO).get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcLqDO);
        String num = mockMvc.perform(MockMvcRequestBuilders.put("/init/rest/v1.0/qlxx/lq")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("1",num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void updateHysyqTset() throws Exception{
        BdcHysyqDO bdcHysyqDO = new BdcHysyqDO();
        bdcHysyqDO.setQlid("459844987464354");
        bdcHysyqDO=entityMapper.select(bdcHysyqDO).get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcHysyqDO);
        String num = mockMvc.perform(MockMvcRequestBuilders.put("/init/rest/v1.0/qlxx/hysyq")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("1",num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void updateGjzwsyqTset() throws Exception{
        BdcGjzwsyqDO bdcGjzwsyqDO = new BdcGjzwsyqDO();
        bdcGjzwsyqDO.setQlid("4598447464354");
        bdcGjzwsyqDO=entityMapper.select(bdcGjzwsyqDO).get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcGjzwsyqDO);
        String num = mockMvc.perform(MockMvcRequestBuilders.put("/init/rest/v1.0/qlxx/gjzwsyq")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("1",num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void updateDyiqTset() throws Exception{
        BdcDyiqDO bdcDyiqDO = new BdcDyiqDO();
        bdcDyiqDO.setQlid("45984474654864354");
        bdcDyiqDO=entityMapper.select(bdcDyiqDO).get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcDyiqDO);
        String num = mockMvc.perform(MockMvcRequestBuilders.put("/init/rest/v1.0/qlxx/dyiq")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("1",num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void updateQtxgqlTset() throws Exception{
        BdcQtxgqlDO bdcQtxgqlDO = new BdcQtxgqlDO();
        bdcQtxgqlDO.setQlid("45984474654864354");
        bdcQtxgqlDO=entityMapper.select(bdcQtxgqlDO).get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcQtxgqlDO);
        String num = mockMvc.perform(MockMvcRequestBuilders.put("/init/rest/v1.0/qlxx/qtxgql")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("1",num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void updateFdcq3Tset() throws Exception{
        BdcFdcq3DO bdcFdcq3DO = new BdcFdcq3DO();
        bdcFdcq3DO.setQlid("45984474654864354");
        bdcFdcq3DO=entityMapper.select(bdcFdcq3DO).get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcFdcq3DO);
        String num = mockMvc.perform(MockMvcRequestBuilders.put("/init/rest/v1.0/qlxx/fdcq3")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("1",num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void updateDyaqTset() throws Exception{
        BdcDyaqDO bdcDyaqDO = new BdcDyaqDO();
        bdcDyaqDO.setQlid("45984474654864354");
        bdcDyaqDO=entityMapper.select(bdcDyaqDO).get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcDyaqDO);
        String num = mockMvc.perform(MockMvcRequestBuilders.put("/init/rest/v1.0/qlxx/dyaq")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("1",num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void updateYgTset() throws Exception{
        BdcYgDO bdcYgDO = new BdcYgDO();
        bdcYgDO.setQlid("45984474654864354");
        bdcYgDO=entityMapper.select(bdcYgDO).get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcYgDO);
        String num = mockMvc.perform(MockMvcRequestBuilders.put("/init/rest/v1.0/qlxx/yg")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("1",num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void updateYyTset() throws Exception{
        BdcYyDO bdcYyDO = new BdcYyDO();
        bdcYyDO.setQlid("45984474654864354");
        bdcYyDO=entityMapper.select(bdcYyDO).get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcYyDO);
        String num = mockMvc.perform(MockMvcRequestBuilders.put("/init/rest/v1.0/qlxx/yy")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("1",num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void updateCfTset() throws Exception{
        BdcCfDO bdcCfDO = new BdcCfDO();
        bdcCfDO.setQlid("45984474654864354");
        bdcCfDO=entityMapper.select(bdcCfDO).get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcCfDO);
        String num = mockMvc.perform(MockMvcRequestBuilders.put("/init/rest/v1.0/qlxx/cf")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("1",num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void updateFdcqxmTset() throws Exception{
        BdcFdcqFdcqxmDO bdcFdcqFdcqxmDO = new BdcFdcqFdcqxmDO();
        bdcFdcqFdcqxmDO.setQlid("45984474654864354");
        bdcFdcqFdcqxmDO=entityMapper.select(bdcFdcqFdcqxmDO).get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcFdcqFdcqxmDO);
        String num = mockMvc.perform(MockMvcRequestBuilders.put("/init/rest/v1.0/qlxx/fdcqxm")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("1",num);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcQllxSetup-data.xml")
    public void updateFdcq3GyxxTset() throws Exception{
        BdcFdcq3GyxxDO bdcFdcq3GyxxDO = new BdcFdcq3GyxxDO();
        bdcFdcq3GyxxDO.setQlid("45984474654864354");
        bdcFdcq3GyxxDO=entityMapper.select(bdcFdcq3GyxxDO).get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcFdcq3GyxxDO);
        String num = mockMvc.perform(MockMvcRequestBuilders.put("/init/rest/v1.0/qlxx/fdcq3/gyxx")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("1",num);
    }
}