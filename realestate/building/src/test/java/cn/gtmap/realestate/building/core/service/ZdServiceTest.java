package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.ZdQlrDO;
import cn.gtmap.realestate.common.core.dto.building.ZdResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO;
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
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018-11-26
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
public class ZdServiceTest {

    @Autowired
    private ZdService zdService;

    @Autowired
    private ZdQlrService zdQlrService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/zdPage-setupData.xml")
    public void queryZdList() {
        List<ZdDjdcbDO> zdDjdcbDOList = zdService.queryZdList();
        Assert.assertEquals(new ArrayList<>(0), zdDjdcbDOList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/zdPage-setupData.xml")
    public void listZdByPage() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("djh", "230522101202GB00107");
        paramMap.put("bdcdyh", "230522101202GB00107W00000000");
        paramMap.put("tdzl", "哈尔滨");
        Pageable pageable = new PageRequest(0, 10);
        Page<Map> zdResponseDTOPage = zdService.listZdByPage(pageable, paramMap);
        Assert.assertNotNull(zdResponseDTOPage);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/zdPage-setupData.xml")
    public void querZdByDjh() {
        String djh = "230522101202GB00107";
        ZdDjdcbDO zdDjdcbDO = zdService.querZdByDjh(djh);
        Assert.assertNotNull(zdDjdcbDO);
        ZdDjdcbDO empty = zdService.querZdByDjh("");
        Assert.assertNull(empty);
    }


    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/zdPage-setupData.xml")
    public void queryZdDjdcbByBdcdyh() {
        String bdcdyh = "230522101202GB00107W00000000";
        ZdDjdcbDO zdDjdcbDO = zdService.queryZdDjdcbByBdcdyh(bdcdyh);
        Assert.assertNotNull(zdDjdcbDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/zdPage-setupData.xml")
    public void queryZdDjdcbByDjh() {
        String djh = "230522101202GB00107";
        ZdDjdcbDO zdDjdcbDO = zdService.queryZdDjdcbByDjh(djh);
        Assert.assertNotNull(zdDjdcbDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/zdPage-setupData.xml")
    public void listZdQlrByDjdcbIndex() {
        String djdcbIndex = "123456";
        List<ZdQlrDO> list = zdQlrService.listZdQlrByDjdcbIndex(djdcbIndex);
        Assert.assertNotNull(list);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/zdPage-setupData.xml")
    public void initZdTreeByFwDcbIndex() {
        String fwDcbIndex = "12345";
        ZdTreeResponseDTO zdTreeResponseDTO = zdService.initZdTreeByFwDcbIndex(fwDcbIndex);
        Assert.assertNotNull(zdTreeResponseDTO);
    }
}
