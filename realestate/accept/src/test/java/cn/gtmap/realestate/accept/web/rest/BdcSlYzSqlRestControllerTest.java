package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.App;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @注释 不动产受理验证sql单元测试
 * @作者 gln
 * @创建日期 2019/5/27
 * @创建时间 17:16
 * @版本号 V 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdcSlYzSqlRestControllerTest {
    private static final String PATH = "/realestate-accept/rest/v1.0/sql/";
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void checkSql() throws Exception {
        Map<String, String> checkMap = new HashMap<>();
        checkMap.put("sqls", "select #{qlr} qlr,to_char(j.slsj,'yyyy\"年\"mm\"月\"dd\"日\"') slsj,t.zl,j.djjg,j.slr,j.slbh,j.cnqx,#{ewm} ewm ,#{djxlmc} djxlmc from \n" +
                "bdc_sl_xm t left join bdc_sl_jbxx j on j.jbxxid = t.jbxxid\n" +
                "where t.xmid=#{xmid};\n" +
                "select sum(t.fs) zfs from BDC_SL_SJCL t where t.xmid=#{xmid};");
        checkMap.put("cs", "xmid=35S15109DZWMZ03I;ewm=123;djxlmc=房屋所有权首次登记;qlr=中国建设银行");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(checkMap);
        String responseSuccess = mockMvc.perform(MockMvcRequestBuilders.post(PATH + "checksql")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(responseSuccess);
    }
}
