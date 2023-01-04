package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.service.BdcSlService;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/3/26
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class BdcSlServiceImplTest {
    @Autowired
    List<BdcSlService> bdcSlServiceList;

    @Test
    @Rollback
    public void queryBdcSlxx() {
        BdcSlxxDTO bdcSlxxDTO =new BdcSlxxDTO();
        if (CollectionUtils.isNotEmpty(bdcSlServiceList)) {
            for (int i = 0; i < bdcSlServiceList.size(); i++) {
                BdcSlService bdcSlService = bdcSlServiceList.get(i);
                bdcSlxxDTO = bdcSlService.queryBdcSlxx("0313", bdcSlxxDTO,null);
            }
        }
        Assert.assertNotNull(bdcSlxxDTO.getBdcSlJbxx());
        Assert.assertNotNull(bdcSlxxDTO.getBdcSlXmList());

    }
}
