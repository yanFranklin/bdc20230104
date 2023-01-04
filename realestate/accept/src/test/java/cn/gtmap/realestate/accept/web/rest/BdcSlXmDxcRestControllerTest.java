package cn.gtmap.realestate.accept.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import java.util.ArrayList;
import java.util.List;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCshSlxmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.util.UUIDGenerator;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/15
 * @description 不动产受理项目rest服务 多线程 单元测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class BdcSlXmDxcRestControllerTest {
    private static final String PATH = "/realestate-accept/rest/v1.0/xm/";
    private static String jbxxid;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 多线程测试  100线程每一个1000个不动产单元
     */
    @Test
    public void test0cshYxxmDxc() throws Throwable {
        jbxxid = UUIDGenerator.generate16();
        TestRunnable[] trs = new TestRunnable[100];
        for (int i = 0; i < 100; i++) {
            int j = i+1;
            TestRunnable runnable = new TestRunnable() {
                @Override
                public void runTest() throws Throwable {
                    BdcCshSlxmDTO bdcCshSlxmDTO = new BdcCshSlxmDTO();
                    List<BdcDjxlPzDO> bdcDjxlPzDOList = new ArrayList<>();
                    BdcDjxlPzDO bdcDjxlPzDO = new BdcDjxlPzDO();
                    bdcDjxlPzDO.setDjxl("4");
                    bdcDjxlPzDOList.add(bdcDjxlPzDO);
                    bdcCshSlxmDTO.setBdcDjxlPzDOList(bdcDjxlPzDOList);
                    List<BdcSlYwxxDTO> bdcSlYwxxDTOList = new ArrayList<>();
                    for (int h = 1; h <= 1000; h++) {
                        BdcSlYwxxDTO bdcSlYwxxDTO = new BdcSlYwxxDTO();
                        bdcSlYwxxDTO.setXmid(UUIDGenerator.generate16());
                        bdcSlYwxxDTO.setQlr("林克");
                        bdcSlYwxxDTO.setZl("贝加尔湖畔" + j + "单元" + h + "号");
                        bdcSlYwxxDTO.setBdcdyh("230123001006GB00007F" + String.format("%04d", j) + String.format("%04d", h));
                        bdcSlYwxxDTOList.add(bdcSlYwxxDTO);
                    }
                    bdcCshSlxmDTO.setGzlslid(jbxxid);
                    bdcCshSlxmDTO.setBdcSlYwxxDTOList(bdcSlYwxxDTOList);
                    ObjectMapper objectMapper = new ObjectMapper();
                    String requestJson = objectMapper.writeValueAsString(bdcCshSlxmDTO);
                    mockMvc.perform(MockMvcRequestBuilders.post(PATH + "csh/" + UUIDGenerator.generate16())
                            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .content(requestJson))
                            .andExpect(MockMvcResultMatchers.status().isCreated())
                            .andDo(MockMvcResultHandlers.print())
                            .andReturn();
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
