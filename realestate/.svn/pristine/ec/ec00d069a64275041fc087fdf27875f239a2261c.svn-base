package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.register.App;
import cn.gtmap.realestate.register.BaseUnitTest;
import cn.gtmap.realestate.register.service.BdcXmxxService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/28
 * @description 登簿信息业务Controller测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class BdcDbxxRestControllerTest extends BaseUnitTest {
    /**
     * 登簿请求URL前置路径
     */
    private static final String REST_PATH_DB = "/realestate-register/rest/v1.0/djbxx";

    @Autowired
    BdcXmxxService bdcXmxxService;

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            // 核心线程数量
            20,
            // 最大线程数
            40,
            // 空闲超时一小时（调用频繁）
            3600, TimeUnit.SECONDS,
            // 阻塞队列
            new ArrayBlockingQueue<>(100),
            // 线程工厂
            new ThreadFactoryBuilder().setNameFormat("workflow-pool-%d").build(),
            // 过多任务直接主线程处理
            new ThreadPoolExecutor.CallerRunsPolicy()
    );


    @Test
    public void testInsertDbxx() throws Exception {

    }

    @Test
    public void testDeleteDbxx() throws Exception {

    }

    @Test
    public void testChangeQszt() throws Exception {

    }

    @Test
    public void testRevertQszt() throws Exception {

    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新案件状态接口测试
     */
    @Test
    public void testChangeAjzt() throws Exception {
        // 接口请求，并验证请求通过与否
        mockMvcController("/111111/ajzt", MockMvcResultMatchers.status().isOk());

        // 验证数据操作结果
        List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid("111111");
        for (BdcXmDO bdcXmDO : bdcXmDOList){
            Assert.assertEquals(2, bdcXmDO.getAjzt().intValue());
        }
    }

    /**
     * @param url 请求URL后缀地址
     * @return matcher 期望返回结果状态
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 接口测试公共方法
     */
    private void mockMvcController(String url, ResultMatcher matcher) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_PATH_DB + url)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(matcher)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    @Test
    public void tetsAsync() {
        for (int i = 0; i < 10; i++) {
            int m = i;
            try {
                LOGGER.info("执行第{}个方法,时间{}", m, DateUtils.getCurrentTimeStr());
                CompletableFuture.runAsync(() -> testMethord(m), threadPoolExecutor).get();
            } catch (InterruptedException | ExecutionException e) {
                if (m == 1 || m == 9) {
                    LOGGER.info("执行异常{}==={}", m, e);
                } else {
                    throw new AppException("执行接口异常接口号" + m + "异常信息" + e);
//                    e.printStackTrace();
                }
            }
        }
    }

    private void testMethord(Integer s) {
        if (s == 1 || s == 2 || s == 3) {
            throw new AppException("执行异常");
        }
    }

    private void testMethord2(Integer s) {
        LOGGER.info("执行方法2---参数{}", s);
    }
}