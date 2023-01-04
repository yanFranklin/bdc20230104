package cn.gtmap.realestate.init.web.rest;

import com.alibaba.fastjson.JSONObject;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.App;
import cn.gtmap.realestate.init.web.BaseController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/8.
 * @description
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
public class BdcInitRestControllerTest extends BaseController {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private EntityMapper entityMapper;

    /*@Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcInitSetup-data.xml")
    public void csh() throws Exception {

        String responseSuccess = mockMvc.perform(post("/init/rest/v1.0/csh").contentType(MediaType.APPLICATION_JSON).content(getCshParam()))
                .andExpect(status().isCreated())// 模拟向testRest发送post请求
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("",responseSuccess);
        Example example = new Example(BdcXmDO.class);
        example.createCriteria().andEqualTo("xmid","cshtest");
        BdcXmDO bdcXmDO = entityMapper.selectByExample(BdcXmDO.class,example).get(0);
        Assert.assertEquals("320684100001GB00178W00000000",bdcXmDO.getBdcdyh());
        Example example2 = new Example(BdcQlrDO.class);
        example2.createCriteria().andEqualTo("xmid","cshtest");
        BdcQlrDO bdcQlrDO = entityMapper.selectByExample(BdcQlrDO.class,example2).get(0);
        Assert.assertEquals("权利人",bdcQlrDO.getQlrmc());
        BdcQlrDO bdcYwrDO = entityMapper.selectByExample(BdcQlrDO.class,example2).get(1);
        Assert.assertEquals("义务人",bdcYwrDO.getQlrmc());
        Example example3 = new Example(BdcZsDO.class);
        example3.createCriteria().andEqualTo("zsid",bdcQlrDO.getZsid());
        BdcZsDO bdcZsDO = entityMapper.selectByExample(BdcZsDO.class,example3).get(0);
        Assert.assertEquals("房屋建筑面积22.44",bdcZsDO.getMj());
        Example example5 = new Example(BdcXmLsgxDO.class);
        example5.createCriteria().andEqualTo("xmid","cshtest");
        BdcXmLsgxDO bdcXmLsgxDO = entityMapper.selectByExample(BdcXmLsgxDO.class,example5).get(0);
        Assert.assertEquals("26849498129582",bdcXmLsgxDO.getYxmid());
    }*/

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/RestBdcInitSetup-data.xml")
    public void deleteYwxx() throws Exception {

        Example example = new Example(BdcXmDO.class);
        example.createCriteria().andEqualTo("xmid","14816548974");
        BdcXmDO bdcXmDO = entityMapper.selectByExample(BdcXmDO.class,example).get(0);
        Assert.assertEquals("52984984984",bdcXmDO.getSlbh());
        Example example1 = new Example(BdcXmLsgxDO.class);
        example1.createCriteria().andEqualTo("xmid","14816548974");
        BdcXmLsgxDO bdcXmLsgxDO = entityMapper.selectByExample(BdcXmLsgxDO.class,example1).get(0);
        Assert.assertEquals("niibnik",bdcXmLsgxDO.getYxmid());
        Example example2 = new Example(BdcQlrDO.class);
        example2.createCriteria().andEqualTo("xmid","14816548974");
        BdcQlrDO bdcQlrDO = entityMapper.selectByExample(BdcQlrDO.class,example2).get(0);
        Assert.assertEquals("权利人",bdcQlrDO.getQlrmc());
        BdcQlrDO bdcYwrDO = entityMapper.selectByExample(BdcQlrDO.class,example2).get(1);
        Assert.assertEquals("义务人",bdcYwrDO.getQlrmc());
        Example example3 = new Example(BdcFdcqDO.class);
        example3.createCriteria().andEqualTo("xmid","14816548974");
        BdcFdcqDO bdcFdcqDO = entityMapper.selectByExample(BdcFdcqDO.class,example3).get(0);
        Assert.assertEquals("49849849849879849",bdcFdcqDO.getQlid());
        Example example4 = new Example(BdcZsDO.class);
        example4.createCriteria().andEqualTo("zsid","112");
        BdcZsDO bdcZsDO = entityMapper.selectByExample(BdcZsDO.class,example4).get(0);
        Assert.assertEquals("test1证书3",bdcZsDO.getBdcqzh());
        String responseSuccess = mockMvc.perform(delete("/init/rest/v1.0/delete/ywxx").param("xmids","14816548974"))
                .andExpect(status().isNoContent())// 模拟向testRest发送post请求
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("",responseSuccess);
        List<BdcXmDO> bdcXmDOList = entityMapper.selectByExample(BdcXmDO.class,example);
        Assert.assertEquals(null,bdcXmDOList);
        List<BdcXmLsgxDO> bdcXmLsgxDOList = entityMapper.selectByExample(BdcXmLsgxDO.class,example1);
        Assert.assertEquals(null,bdcXmLsgxDOList);
        List<BdcQlrDO> bdcQlrDOList = entityMapper.selectByExample(BdcQlrDO.class,example2);
        Assert.assertEquals(null,bdcQlrDOList);
        List<BdcFdcqDO> bdcFdcqDOList = entityMapper.selectByExample(BdcFdcqDO.class,example3);
        Assert.assertEquals(null,bdcFdcqDOList);
        List<BdcZsDO> bdcZsDOList = entityMapper.selectByExample(BdcZsDO.class,example4);
        Assert.assertEquals(null,bdcZsDOList);

        String responseError = mockMvc.perform(delete("/init/rest/v1.0/delete/ywxx"))
                .andExpect(status().is5xxServerError())// 模拟向testRest发送post请求
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("{\"code\":1,\"msg\":\"系统异常，请联系管理员\",\"detail\":[\"Required String[] parameter 'xmids' is not present\"]}",responseError);

    }

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    /**
     * 获取参数
     * @return
     */
    private String  getCshParam(){
        String id="cshtest";
        BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO();
        bdcSlXmLsgxDO.setGxid("6416421654");
        bdcSlXmLsgxDO.setXmid(id);
        bdcSlXmLsgxDO.setYxmid("26849498129582");
        bdcSlXmLsgxDO.setZxyql(1);
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
        bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        bdcSlJbxxDO.setXmly(1);
        bdcSlJbxxDO.setSlbh("cshinittest");
        bdcSlJbxxDO.setQxdm("320508");
        bdcSlJbxxDO.setGzlslid("cshInitTest");
        bdcSlJbxxDO.setSlsj(new Date());
        bdcSlJbxxDO.setSlr("初始化登记局");
        BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
        bdcSlXmDO.setXmid(id);
        bdcSlXmDO.setBdcdyh("320684100001GB00178W00000000");
        bdcSlXmDO.setBdclx(2);
        bdcSlXmDO.setDjxl("9");

        BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
        bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);
        bdcSlXmDTO.setBdcSlXmLsgxList(bdcSlXmLsgxDOList);

        List<BdcSlXmDTO> bdcSlXmDTOList = new ArrayList<>();
        bdcSlXmDTOList.add(bdcSlXmDTO);
        BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();
        bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);
        bdcSlxxDTO.setBdcSlXmList(bdcSlXmDTOList);

        return JSONObject.toJSONString(bdcSlxxDTO);
    }


    /**
     * 获取参数
     * @return
     */
    private String  getZlCshParam(){
        String id="f166f665708c402c80d266f665700002";
        BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO();
        bdcSlXmLsgxDO.setGxid("45845187448745");
        bdcSlXmLsgxDO.setXmid(id);
        bdcSlXmLsgxDO.setYxmid("268494981295824478");
        bdcSlXmLsgxDO.setZxyql(1);
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
        bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        bdcSlJbxxDO.setXmly(1);
        bdcSlJbxxDO.setSlbh("111");
        bdcSlJbxxDO.setQxdm("320508");
        bdcSlJbxxDO.setGzlslid("123456");
        bdcSlJbxxDO.setSlsj(new Date());
        bdcSlJbxxDO.setSlr("test");
        BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
        bdcSlXmDO.setXmid(id);
        bdcSlXmDO.setBdcdyh("230103001006GB00007F00020006");
        bdcSlXmDO.setBdclx(2);
        bdcSlXmDO.setDjxl("1");
        bdcSlXmDO.setSfzlcsh(1);
        BdcSlXmDO bdcSlXmDO1 = new BdcSlXmDO();
        bdcSlXmDO1.setXmid("f166f665708c402c80d266f665700004");
        bdcSlXmDO1.setBdcdyh("230123001006GB00007F00020322");
        bdcSlXmDO1.setBdclx(2);
        bdcSlXmDO1.setDjxl("2");
        bdcSlXmDO1.setSfzlcsh(1);
        BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
        bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);
        bdcSlXmDTO.setBdcSlXmLsgxList(bdcSlXmLsgxDOList);

        BdcSlXmDTO bdcSlXmDTO1 = new BdcSlXmDTO();
        bdcSlXmDTO1.setBdcSlXm(bdcSlXmDO1);
        List<BdcSlXmDTO> bdcSlXmDTOList = new ArrayList<>();
        bdcSlXmDTOList.add(bdcSlXmDTO);
        bdcSlXmDTOList.add(bdcSlXmDTO1);
        BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();
        bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);
        bdcSlxxDTO.setBdcSlXmList(bdcSlXmDTOList);

        BdcSlXmLsgxDO bdcSlXmLsgxDO2 = new BdcSlXmLsgxDO();
        bdcSlXmLsgxDO2.setGxid(UUIDGenerator.generate16());
        bdcSlXmLsgxDO2.setXmid("f166f665708c402c80d266f665700004");
        bdcSlXmLsgxDO2.setYxmid(id);
        bdcSlXmLsgxDO2.setZxyql(0);
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList2 = new ArrayList<>();
        bdcSlXmLsgxDOList2.add(bdcSlXmLsgxDO2);
        bdcSlXmDTO1.setBdcSlXmLsgxList(bdcSlXmLsgxDOList2);
        return JSONObject.toJSONString(bdcSlxxDTO);
    }

}