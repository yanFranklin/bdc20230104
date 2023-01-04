package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.register.App;
import cn.gtmap.realestate.register.BaseUnitTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/11
 * @description 不动产登记簿信息服务接口测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class BdcDjbxxRestControllerTest extends BaseUnitTest{
    /**
     * 登记簿请求URL前置路径
     */
    private static final String REST_PATH_DJB = "/register/rest/v1.0/djb";

    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a
     * @description 获取不动产单元目录
     */
    @Test
    public void testListBdcQlDjMl() throws Exception {
        //测试正常返回结果
        mockMvcController("/123/qldjml", MockMvcResultMatchers.status().isOk());

        //测试缺少占位符异常返回
        mockMvcController("/qldjml", MockMvcResultMatchers.status().is2xxSuccessful());
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取不动产登记簿信息接口测试
     */
    @Test
    public void testQueryBdcBdcdjb() throws Exception {
        //测试正常返回结果
        mockMvcController("/123", MockMvcResultMatchers.status().isOk());

        //测试缺少占位符异常返回
        mockMvcController("/", MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testQueryBdcQlQtSx() throws Exception {

    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取不动产宗地基本信息接口测试
     */
    @Test
    public void testQueryBdcBdcdjbZdjbxx() throws Exception {
        //测试正常返回结果
        mockMvcController("/123/zdjbxx", MockMvcResultMatchers.status().isOk());

        //测试缺少占位符异常返回
        mockMvcController("/zdjbxx", MockMvcResultMatchers.status().is2xxSuccessful());
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产宗地变化情况接口测试
     */
    @Test
    public void testListBdcBdcdjbZdjbxxZdbhqk() throws Exception {
        //测试正常返回结果
        mockMvcController("/123/zdjbxx/zdbhqk", MockMvcResultMatchers.status().isOk());

        //测试缺少占位符异常返回
        mockMvcController("/zdjbxx/zdbhqk", MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产登记簿宗海基本信息测试
     */
    @Test
    public void testQueryBdcBdcdjbZhjbxx() throws Exception {
        //测试正常返回结果
        mockMvcController("/123/zhjbxx", MockMvcResultMatchers.status().isOk());

        //测试缺少占位符异常返回
        mockMvcController("/zhjbxx", MockMvcResultMatchers.status().is2xxSuccessful());
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产登记簿宗海基本信息中的用海状况 测试
     */
    @Test
    public void testListBdcBdcdjbZhjbxxYhzk() throws Exception {
        //测试正常返回结果
        mockMvcController("/123/zhjbxx/yhzk", MockMvcResultMatchers.status().isOk());

        //测试缺少占位符异常返回
        mockMvcController("/zhjbxx/yhzk", MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产登记簿宗海基本信息中的用海用岛坐标测试
     */
    @Test
    public void testListBdcBdcdjbZhjbxxYhydzb() throws Exception {
        //测试正常返回结果
        mockMvcController("/123/zhjbxx/yhydzb", MockMvcResultMatchers.status().isOk());

        //测试缺少占位符异常返回
        mockMvcController("/zhjbxx/yhydzb", MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产登记簿宗海基本信息中的宗海变化情况测试
     */
    @Test
    public void testListBdcBdcdjbZhjbxxZhbhqk() throws Exception {
        //测试正常返回结果
        mockMvcController("/123/zhjbxx/zhbhqk", MockMvcResultMatchers.status().isOk());

        //测试缺少占位符异常返回
        mockMvcController("/zhjbxx/zhbhqk", MockMvcResultMatchers.status().isNotFound());
    }

   /**
    * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
    * @param    url 请求URL后缀地址
    * @return   matcher 期望返回结果状态
    * @description  接口测试公共方法
    */
    private void mockMvcController(String url, ResultMatcher matcher) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_PATH_DJB + url)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(matcher)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}