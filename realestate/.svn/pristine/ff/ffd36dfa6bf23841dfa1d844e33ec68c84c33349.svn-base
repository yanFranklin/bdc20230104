package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.service.BdcBhService;
import cn.gtmap.realestate.accept.util.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/3/20
 * @description 编号服务
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class BdcBhServiceImplTest {
    @Autowired
    private BdcBhService bdcBhService;

    @Test(timeout=5000)
    @Transactional(rollbackFor = Exception.class) //标明此方法需使用事务
    public void testQueryBh() {
        for(int i=0;i<500;i++) {
            String bh =bdcBhService.queryBh(Constants.BH_SL, Constants.ZZSJFW_DAY);
            System.out.println(bh +"完成的请求数:"+(i+1));
        }



    }
}
