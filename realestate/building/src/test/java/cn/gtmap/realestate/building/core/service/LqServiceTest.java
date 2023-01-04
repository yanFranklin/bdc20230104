package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.common.core.domain.building.LqDcbDO;
import cn.gtmap.realestate.common.core.domain.building.NydQlrDO;
import cn.gtmap.realestate.common.core.dto.building.LqDcbPageResponseDTO;
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
 * @version 1.0  2018/11/27
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
public class LqServiceTest {
    @Autowired
    private LqService lqService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/lq-setupData.xml")
    public void queryLqDcbByBdcdyh() {
        String bdcdyh = "230803214322GB00002F00060072";
        LqDcbDO lqDcbDO = lqService.queryLqDcbByBdcdyh(bdcdyh);
        Assert.assertNotNull(lqDcbDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/lq-setupData.xml")
    public void queryLqDcbByDjh() {
        String djh = "230803214322GB00002";
        LqDcbDO lqDcbDO = lqService.queryLqDcbByDjh(djh);
        Assert.assertNotNull(lqDcbDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/lq-setupData.xml")
    public void listLqDcbByPageJson() {
        Map<String, Object> paramMap = new HashMap<>();
        Pageable pageable = new PageRequest(0, 1);
        Page<Map> lqDcbPageResponseDTOPage = lqService.listLqDcbByPageJson(pageable, paramMap);
        Assert.assertNotNull(lqDcbPageResponseDTOPage);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/lq-setupData.xml")
    public void listLmsyqrByDcbIndex() {
        String dcbIndex = "gtmap";
        List<NydQlrDO> nydQlrDOList = lqService.listLmsyqrByDcbIndex(dcbIndex);
        Assert.assertNotNull(nydQlrDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/lq-setupData.xml")
    public void listLmsuqrByDcbIndex() {
        String dcbIndex = "gtmap";
        List<NydQlrDO> nydQlrDOList = lqService.listLmsuqrByDcbIndex(dcbIndex);
        Assert.assertNotNull(nydQlrDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/lq-setupData.xml")
    public void listLdsyqrByDcbIndex() {
        String dcbIndex = "gtmap";
        List<NydQlrDO> nydQlrDOList = lqService.listLdsyqrByDcbIndex(dcbIndex);
        Assert.assertNotNull(nydQlrDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/lq-setupData.xml")
    public void listLmsyqrByDjh() {
        String djh = "230803214322GB00002";
        List<NydQlrDO> nydQlrDOList = lqService.listLmsyqrByDjh(djh);
        Assert.assertNotNull(nydQlrDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/lq-setupData.xml")
    public void listLmsuqrByDjh() {
        String djh = "230803214322GB00002";
        List<NydQlrDO> nydQlrDOList = lqService.listLmsuqrByDjh(djh);
        Assert.assertNotNull(nydQlrDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/lq-setupData.xml")
    public void listLdsyqrByDjh() {
        String djh = "230803214322GB00002";
        List<NydQlrDO> nydQlrDOList = lqService.listLdsyqrByDjh(djh);
        Assert.assertNotNull(nydQlrDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/lq-setupData.xml")
    public void listQlrByLx() {
        List<NydQlrDO> nydQlrDOList = new ArrayList<>();
        NydQlrDO nydQlrDO = new NydQlrDO();
        nydQlrDO.setDjdcbIndex("gtmap");
        nydQlrDO.setNydQlrIndex("3454546");
        nydQlrDO.setSflmsuqr("1");
        nydQlrDOList = lqService.listQlrByLx(nydQlrDOList, "lmsuqr");
        Assert.assertNotNull(nydQlrDOList);
    }
}