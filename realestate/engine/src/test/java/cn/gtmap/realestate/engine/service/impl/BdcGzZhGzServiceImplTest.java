package cn.gtmap.realestate.engine.service.impl;

import cn.gtmap.realestate.engine.EngineApp;
import cn.gtmap.realestate.engine.core.service.BdcGzZhGzService;
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
 * @version 1.0, 2019/9/16.
 * @description 复制组合规则数据后台接口验证测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EngineApp.class)
@WebAppConfiguration
@Transactional
public class BdcGzZhGzServiceImplTest {

    @Autowired
    BdcGzZhGzService bdcGzZhGzService;

    @Test
    public void testCopyZhGzInfo() throws Exception{
        String copyZhid = "f16a919cf98bff8080816a9058bf004b";
        String newzhid = this.bdcGzZhGzService.copyBdcGzZhgz(copyZhid);
        Assert.hasText(newzhid,"复制组合规则信息失败！");
    }
}
