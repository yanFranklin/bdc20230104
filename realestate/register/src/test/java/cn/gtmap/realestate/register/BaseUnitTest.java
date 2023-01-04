package cn.gtmap.realestate.register;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
 * @version 1.0, 2018/10/29
 * @description 单元测试基础类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ImportResource(locations={"classpath:spring/config-mybatis.xml"})
public class BaseUnitTest {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    protected  MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
    @Before
    public void beforTest(){
        LOGGER.info("<=====测试开始=====>");
    }
    @After
    public void afterTest(){
        LOGGER.info("<=====测试结束=====>");
    }

    @Test
    public void test() {
        LOGGER.info("测试方法");
    }


}
