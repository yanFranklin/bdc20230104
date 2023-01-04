package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.service.BdcDysjPzService;
import cn.gtmap.realestate.register.App;
import cn.gtmap.realestate.register.BaseUnitTest;
import cn.gtmap.realestate.register.service.BdcDypzService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;


/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 1.0, 2018/11/11
 * @description BdcPrintRestController Tester.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class BdcPrintRestControllerTest extends BaseUnitTest {

    /**
     * 打印请求地址
     */
    private static String REST_PATH_PRINT = "/realestate-register/rest/v1.0/print";
    private static String REST_PATH_OTHER_DATASOURCE = "/realestate-register/rest/v1.0/print/qtsjy";

    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    BdcDysjPzService bdcDysjPzService;
    @Autowired
    BdcDypzService bdcDypzService;


    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description print(List < Map > list)
     */
    @Test
    public void print() throws Exception {
        Map param1 = Maps.newHashMap();
        param1.put("xmid", "difficultid");
        param1.put("sjxxid", "1");
        Map param2 = Maps.newHashMap();
        param2.put("xmid", "f166cf582831402c803c66cf58280001");
        param2.put("sjxxid", "2");
        List list = Lists.newArrayList(param1, param2);
        Map map3 = Maps.newHashMap();
        map3.put("spb", list);

        String requestJson = JSON.toJSONString(map3);
        //mock进行模拟
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(REST_PATH_PRINT).accept(MediaType.APPLICATION_JSON_UTF8_VALUE).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testDiffSource() {
        Map param1 = Maps.newHashMap();
        param1.put("xmid", "difficultid");
        param1.put("sjxxid", "1");
        param1.put("dylx", "spb");
//        Map param2 = Maps.newHashMap();
//        param2.put("xmid", "f166cf582831402c803c66cf58280001");
//        param2.put("sjxxid", "2");
//        List list = Lists.newArrayList(param1, param2);
//        Map map3 = Maps.newHashMap();
//        map3.put("spb", list);
        /**
         * 获取主表配置
         */
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx("spb");
        List<BdcDysjPzDO> bdcDysjPzDOS = bdcDypzService.listBdcDysjPz(bdcDysjPzDO);
        String dyzd=bdcDysjPzDOS.get(0).getDyzd();
        /**
         * 获取字表配置
         */
        BdcDysjZbPzDO bdcDysjZbPzDO = new BdcDysjZbPzDO();
        bdcDysjZbPzDO.setDylx("spb");
        List<BdcDysjZbPzDO> bdcDysjZbPzDOS = bdcDypzService.listBdcDysjZbPz(bdcDysjZbPzDO);
        Map data = bdcDysjPzService.queryPrintDatasList(param1, "bdcRegisterConfigMapper", bdcDysjPzDOS);
        Multimap detail = bdcDysjPzService.queryPrintDetailList(param1, "bdcRegisterConfigMapper", bdcDysjZbPzDOS);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj(JSON.toJSONString(data));
        bdcDysjDTO.setDyzbsj(JSON.toJSONString(detail));
        bdcDysjDTO.setDylx("spb");
        bdcDysjDTO.setDyzd(dyzd);
        String requestJson = JSON.toJSONString(Lists.newArrayList(bdcDysjDTO));
        //mock进行模拟
        MvcResult mvcResult = null;
        try {
            mvcResult = mvc.perform(MockMvcRequestBuilders.post(REST_PATH_OTHER_DATASOURCE).accept(MediaType.APPLICATION_JSON_UTF8_VALUE).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(requestJson))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            Assert.assertNotNull(mvcResult.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
