package cn.gtmap.realestate.accept.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.service.BdcBhService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCshSlxmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.util.UUIDGenerator;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/15
 * @description 受理后台服务串联
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdcSlLcRestControllerTest {
    private MockMvc mockMvc;
    private static BdcSlJbxxDO bdcSlJbxxDO;
    private static BdcCshSlxmDTO bdcCshSlxmDTO;
    @Autowired
    private BdcBhService bdcBhService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private BdcdyFeignService bdcdyFeignService;

    @Before // 在测试开始前初始化工作
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test0() {
        /**
         * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
         * @description 初始化受理基本信息
         */
        bdcSlJbxxDO = new BdcSlJbxxDO();
        bdcSlJbxxDO.setJbxxid(UUIDGenerator.generate16());
        bdcSlJbxxDO.setSlbh(bdcBhService.queryBh("SLBH", Constants.ZZSJFW_DAY));
        bdcSlJbxxDO.setGzldyid("SUPERSUN001");
        bdcSlJbxxDO.setGzlslid("SUPERSUN001");
        bdcSlJbxxDO.setQxdm("320112");
        bdcSlJbxxDO.setDjjg("南京国图");
        bdcSlJbxxDO.setSlsj(new Date());
        bdcSlJbxxDO.setSlrid("0");
        bdcSlJbxxDO.setSlr("吕树");

        /**
         * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
         * @description 初始化已选不动产单元DTO
         */
        bdcCshSlxmDTO = new BdcCshSlxmDTO();
        List<BdcDjxlPzDO> bdcDjxlPzDOList = new ArrayList<>();
        BdcDjxlPzDO bdcDjxlPzDO = new BdcDjxlPzDO();
        bdcDjxlPzDO.setDjxl("4");
        bdcDjxlPzDOList.add(bdcDjxlPzDO);
        bdcCshSlxmDTO.setBdcDjxlPzDOList(bdcDjxlPzDOList);
        List<BdcSlYwxxDTO> bdcSlYwxxDTOList = new ArrayList<>();
        BdcSlYwxxDTO bdcSlYwxxDTO = new BdcSlYwxxDTO();
        bdcSlYwxxDTO.setXmid(UUIDGenerator.generate16());
        bdcSlYwxxDTO.setQlr("林克");
        bdcSlYwxxDTO.setYbdcqz("国字001");
        bdcSlYwxxDTO.setZl("贝加尔湖畔1单元1号");
        bdcSlYwxxDTO.setBdcdyh("230123001006GB00007F" + String.format("%04d", 1) + String.format("%04d", 1));
        bdcSlYwxxDTOList.add(bdcSlYwxxDTO);
        bdcCshSlxmDTO.setGzlslid(bdcSlJbxxDO.getJbxxid());
        bdcCshSlxmDTO.setBdcSlYwxxDTOList(bdcSlYwxxDTOList);
    }



    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理基本信息
     */
    @Test
    public void test2() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcSlJbxxDO);
        mockMvc.perform(MockMvcRequestBuilders.post("/realestate-accept/rest/v1.0/jbxx/")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 查询不动产单元
     */
    @Test
    public void test3() throws Exception {
//        Pageable pageable = new Pageable() {
//            @Override
//            public int getPageNumber() {
//                return 0;
//            }
//
//            @Override
//            public int getPageSize() {
//                return 10;
//            }
//
//            @Override
//            public int getOffset() {
//                return 0;
//            }
//
//            @Override
//            public Sort getSort() {
//                return null;
//            }
//
//            @Override
//            public Pageable next() {
//                return null;
//            }
//
//            @Override
//            public Pageable previousOrFirst() {
//                return null;
//            }
//
//            @Override
//            public Pageable first() {
//                return null;
//            }
//
//            @Override
//            public boolean hasPrevious() {
//                return false;
//            }
//        };
//        Page<BdcdyPageResponseDTO> bdcdyPageResponseDTOPage = bdcdyFeignService.listScFwHsBdcdy(pageable, "");
//        List<BdcdyPageResponseDTO> bdcdyPageResponseDTOList = bdcdyPageResponseDTOPage.getContent();
//        if (CollectionUtils.isNotEmpty(bdcdyPageResponseDTOList)) {
//            bdcCshSlxmDTO.getBdcSlYwxxDTOList().get(0).setBdcdyh(bdcdyPageResponseDTOList.get(0).getBdcdyh());
//            bdcCshSlxmDTO.getBdcSlYwxxDTOList().get(0).setQlr(bdcdyPageResponseDTOList.get(0).getQlr());
//            bdcCshSlxmDTO.getBdcSlYwxxDTOList().get(0).setZl(bdcdyPageResponseDTOList.get(0).getZl());
//            bdcCshSlxmDTO.getBdcSlYwxxDTOList().get(0).setQjid(bdcdyPageResponseDTOList.get(0).getFwHsIndex());
//        }
    }

    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 选择不动产单元操作
     */
    @Test
    public void test4() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(bdcCshSlxmDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/realestate-accept/rest/v1.0/xm/csh/" + bdcSlJbxxDO.getSlrid())
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 推送输出之初始化系统
     */
    @Test
    public void test5() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/realestate-accept/rest/v1.0/sl/csh/" + bdcSlJbxxDO.getJbxxid()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
    }
}
