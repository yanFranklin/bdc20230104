package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.common.core.domain.building.NydDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.NydJtcyDO;
import cn.gtmap.realestate.common.core.dto.building.NydPageResponseDTO;
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
public class NydServiceTest {

    @Autowired
    private NydService nydService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/nydDjdcb-setupData.xml")
    public void listNydJtcyByBdcdyh() {
        String bdcdyh = "320802007003GHkkkk9F00008888";
        List<NydJtcyDO> nydJtcyDoList = nydService.listNydJtcyByBdcdyh(bdcdyh);
        Assert.assertNotNull(nydJtcyDoList);
        List<NydJtcyDO> emptyList = nydService.listNydJtcyByBdcdyh("");
        Assert.assertEquals(new ArrayList<>(0), emptyList);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/nydDjdcb-setupData.xml")
    public void queryNydDjdcbByBdcdyh() {
        String bdcdyh = "230522101202GB00107W00000000";
        NydDjdcbDO nydDjdcbDO = nydService.queryNydDjdcbByBdcdyh(bdcdyh);
        Assert.assertNotNull(nydDjdcbDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/nydDjdcb-setupData.xml")
    public void queryNydDjdcbByDjh() {
        String djh = "230522101202GB00107";
        NydDjdcbDO nydDjdcbDO = nydService.queryNydDjdcbByDjh(djh);
        Assert.assertNotNull(nydDjdcbDO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/nydDjdcb-setupData.xml")
    public void listNydByPage() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("djh", "230522101202GB00107");
        paramMap.put("bdcdyh", "230522101202GB00107W00000000");
        paramMap.put("tdzl", "哈尔滨");
        Pageable pageable = new PageRequest(0, 1);
        Page<Map> pageResponseDTOS = nydService.listNydByPage(pageable, paramMap);
        Assert.assertNotNull(pageResponseDTOS);
    }
}
