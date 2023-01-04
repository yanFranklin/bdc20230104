package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.common.core.domain.building.CbzdCbfDO;
import cn.gtmap.realestate.common.core.domain.building.CbzdDcbDO;
import cn.gtmap.realestate.common.core.domain.building.CbzdFbfDO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/26
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config-dbunit.xml"})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(classes = App.class)
public class CbzdSereviceTest {
    @Autowired
    private CbzdService cbzdService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/cbzd-setupData.xml")
    public void queryCdzdDcbByBdcdyh() {
        String bdcdyh = "320802007003GHkkkk9F00008888";
        CbzdDcbDO cbzdDcbDO = cbzdService.queryCdzdDcbByBdcdyh(bdcdyh);
        Assert.assertNotNull(cbzdDcbDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/cbzd-setupData.xml")
    public void queryCbzdDcbByDjh() {
        String djh = "320802007003GHkkkk9";
        CbzdDcbDO cbzdDcbDO = cbzdService.queryCbzdDcbByDjh(djh);
        Assert.assertNotNull(cbzdDcbDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/cbzd-setupData.xml")
    public void listCbfByBdcdyh() {
        String bdcdyh = "320802007003GHkkkk9F00008888";
        List<CbzdCbfDO> cbzdCbfDOList = cbzdService.listCbfByBdcdyh(bdcdyh);
        Assert.assertNotNull(cbzdCbfDOList);
    }


    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/cbzd-setupData.xml")
    public void listCbzdBdcdy() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("djh", "320802007003GHkkkk9");
        paramMap.put("bdcdyh", "320802007003GHkkkk9F00008888");
        paramMap.put("qlr", "test1");
        Pageable pageable = new PageRequest(0, 1);
        Page<Map> cbzdResponseDTOPage = cbzdService.listCbzdBdcdy(pageable, paramMap);
        Assert.assertNotNull(cbzdResponseDTOPage);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/cbzd-setupData.xml")
    public void listFbfByBdcdy() {
        String bdcdyh = "320802007003GHkkkk9F00008888";
        CbzdFbfDO cbzdFbfDO = cbzdService.getFbfByBdcdyh(bdcdyh);
        Assert.assertNotNull(cbzdFbfDO);
        CbzdFbfDO empty = cbzdService.getFbfByBdcdyh("");
        Assert.assertEquals(empty, null);
    }
}