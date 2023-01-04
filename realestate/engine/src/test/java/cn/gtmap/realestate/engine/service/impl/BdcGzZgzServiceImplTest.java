package cn.gtmap.realestate.engine.service.impl;

import cn.gtmap.realestate.engine.EngineApp;
import cn.gtmap.realestate.engine.core.service.BdcGzZgzService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/9/12.
 * @description  复制子规则信息后台接口验证测试方法
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EngineApp.class)
@WebAppConfiguration
@Transactional
public class BdcGzZgzServiceImplTest {

    @Autowired
    BdcGzZgzService bdcGzZgzService;

    @Test
    public void testCopyBdcGzZgz() throws Exception{
        String gzid = "f16c65eb1416ff8080816c64992e0045";
        String newGzid = this.bdcGzZgzService.copyBdcGzZgz(gzid);
        Assert.hasText(newGzid,"复制子规则信息失败！");
    }
}
