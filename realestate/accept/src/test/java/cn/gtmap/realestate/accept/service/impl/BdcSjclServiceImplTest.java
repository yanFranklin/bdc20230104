package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.service.BdcSjclService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/3/26
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class BdcSjclServiceImplTest {
    @Autowired
    BdcSjclService bdcSjclService;

    @Test
    @Rollback
    public void testSaveSjcl() {
        BdcSlSjclDO bdcSlSjclDO =new BdcSlSjclDO();
        bdcSlSjclDO.setXmid("123");
        bdcSlSjclDO.setSjclid("");
        List<BdcSlSjclDO> bdcSlSjclDOList =new ArrayList<>();
        bdcSlSjclDOList.add(bdcSlSjclDO);
        Integer result =bdcSjclService.saveSjcl(JSON.toJSONString(bdcSlSjclDOList));
        Assert.assertEquals(result.toString(), "1");
    }
}
