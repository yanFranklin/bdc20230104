package cn.gtmap.realestate.building.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dto.building.BdcdyxxRequestDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTimeUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import sun.util.calendar.CalendarUtils;

import javax.swing.text.html.parser.Entity;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-11
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
public class BdcdyxxServiceTest {

    @Autowired
    private BdcdyxxService bdcdyxxService;

    @Autowired
    private ZdService zdService;

    @Autowired
    private ZdQlrService zdQlrService;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private NydQlrService nydQlrService;

    @Autowired
    private QszdQlrService qszdQlrService;

    @Autowired
    private FwFcqlrService fwFcqlrService;

    @Autowired
    private BdcdyService bdcdyService;

    @Test
    @Rollback
    @Transactional(transactionManager = "transactionManager")
    @DatabaseSetup(value = "/data/bdcdyxxZdDjdcb-setupData.xml")
    @ExpectedDatabase(value = "/data/bdcdyxxZdDjdcb-expectData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateZdDjdcb() throws ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        BdcdyxxRequestDTO bdcdyxxRequestDTO = new BdcdyxxRequestDTO();
        String bdcdyh = "230522101202GB00107W00000000";
        bdcdyxxRequestDTO.setBdcdyh(bdcdyh);
        BdcBdcdjbZdjbxxDO bdcBdcdjbZdjbxxDO = new BdcBdcdjbZdjbxxDO();
        bdcBdcdjbZdjbxxDO.setZl("南京");
        bdcBdcdjbZdjbxxDO.setYt("11");
        bdcBdcdjbZdjbxxDO.setQlxz(11);
        bdcdyxxRequestDTO.setBdcBdcdjbZdjbxxDO(bdcBdcdjbZdjbxxDO);

        BdcJsydsyqDO bdcJsydsyqDO = new BdcJsydsyqDO();
        String qsrqStr = "2018-09-09 12:00:00";
        Date  qsrq = DateUtils.parseDate(qsrqStr,"yyyy-MM-dd HH:mm:ss");
        bdcJsydsyqDO.setSyqqssj(qsrq);
        String jsrqStr = "2019-09-09 12:00:00";
        Date jsrq = DateUtils.parseDate(jsrqStr,"yyyy-MM-dd HH:mm:ss");
        bdcJsydsyqDO.setSyqjssj(jsrq);
        bdcJsydsyqDO.setTdsyqr("土地所有权人2");
        bdcdyxxRequestDTO.setBdcJsydsyqDO(bdcJsydsyqDO);
        bdcdyxxService.updateBdcdyxx(bdcdyxxRequestDTO);
    }

    @Test
    @Rollback
    @Transactional(transactionManager = "transactionManager")
    @DatabaseSetup(value = "/data/bdcdyxxNydDjdcb-setupData.xml")
    @ExpectedDatabase(value = "/data/bdcdyxxNydDjdcb-expectData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateNydDjdcb() throws ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        BdcdyxxRequestDTO bdcdyxxRequestDTO = new BdcdyxxRequestDTO();
        String bdcdyh = "230522101202GF00107W00000000";
        bdcdyxxRequestDTO.setBdcdyh(bdcdyh);
        setupDjdcbBdcdyxxReqDTO(bdcdyxxRequestDTO);
        bdcdyxxService.updateBdcdyxx(bdcdyxxRequestDTO);
    }

    @Test
    @Rollback
    @Transactional(transactionManager = "transactionManager")
    @DatabaseSetup(value = "/data/bdcdyxxQszdDjdcb-setupData.xml")
    @ExpectedDatabase(value = "/data/bdcdyxxQszdDjdcb-expectData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateQszdDjdcb() throws ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        BdcdyxxRequestDTO bdcdyxxRequestDTO = new BdcdyxxRequestDTO();
        String bdcdyh = "230522101202GA00107W00000000";
        bdcdyxxRequestDTO.setBdcdyh(bdcdyh);
        setupDjdcbBdcdyxxReqDTO(bdcdyxxRequestDTO);
        bdcdyxxService.updateBdcdyxx(bdcdyxxRequestDTO);
    }

    @Test
    @Rollback
    @Transactional(transactionManager = "transactionManager")
    @DatabaseSetup(value = "/data/bdcdyxxZdQlr-setupData.xml")
    public void updateZdQlr() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        BdcdyxxRequestDTO bdcdyxxRequestDTO = new BdcdyxxRequestDTO();
        String bdcdyh = "230522101202GB00107W00000000";
        bdcdyxxRequestDTO.setBdcdyh(bdcdyh);
        setupDjQlrReqDto(bdcdyxxRequestDTO);
        bdcdyxxService.updateBdcdyxx(bdcdyxxRequestDTO);

        List<ZdQlrDO> list = zdQlrService.listZdQlrByBdcdyh(bdcdyh);
        Assert.assertTrue(CollectionUtils.isNotEmpty(list) && list.size() == 2);
    }


    @Test
    @Rollback
    @Transactional(transactionManager = "transactionManager")
    @DatabaseSetup(value = "/data/bdcdyxxNydQlr-setupData.xml")
    public void updateNydQlr() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        BdcdyxxRequestDTO bdcdyxxRequestDTO = new BdcdyxxRequestDTO();
        String bdcdyh = "230522101202GF00107W00000000";
        bdcdyxxRequestDTO.setBdcdyh(bdcdyh);
        setupDjQlrReqDto(bdcdyxxRequestDTO);
        bdcdyxxService.updateBdcdyxx(bdcdyxxRequestDTO);

        List<NydQlrDO> list = nydQlrService.listNydQlrByBdcdyh(bdcdyh);
        Assert.assertTrue(CollectionUtils.isNotEmpty(list) && list.size() == 2);
    }

    @Test
    @Rollback
    @Transactional(transactionManager = "transactionManager")
    @DatabaseSetup(value = "/data/bdcdyxxQszdQlr-setupData.xml")
    public void updateQszdQlr() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        BdcdyxxRequestDTO bdcdyxxRequestDTO = new BdcdyxxRequestDTO();
        String bdcdyh = "230522101202GA00107W00000000";
        bdcdyxxRequestDTO.setBdcdyh(bdcdyh);
        setupDjQlrReqDto(bdcdyxxRequestDTO);
        bdcdyxxService.updateBdcdyxx(bdcdyxxRequestDTO);
        List<QszdQlrDO> list = qszdQlrService.listQszdQlrByBdcdyh(bdcdyh);
        Assert.assertTrue(CollectionUtils.isNotEmpty(list) && list.size() == 2);
    }

    @Test
    @Rollback
    @Transactional(transactionManager = "transactionManager")
    @DatabaseSetup(value = "/data/bdcdyxxFwFcqlr-setupData.xml")
    public void updateFwFcqlr() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        BdcdyxxRequestDTO bdcdyxxRequestDTO = new BdcdyxxRequestDTO();
        String bdcdyh = "230522101202GB00107F00000000";
        bdcdyxxRequestDTO.setBdcdyh(bdcdyh);
        setupDjQlrReqDto(bdcdyxxRequestDTO);
        bdcdyxxService.updateBdcdyxx(bdcdyxxRequestDTO);
        List<FwFcqlrDO> list = bdcdyService.listFwFcQlrByBdcdyh(bdcdyh);
        Assert.assertTrue(CollectionUtils.isNotEmpty(list) && list.size() == 2);
    }

    @Test
    @Rollback
    @Transactional(transactionManager = "transactionManager")
    @DatabaseSetup(value = "/data/bdcdyxxFwHs-setupData.xml")
    @ExpectedDatabase(value = "/data/bdcdyxxFwHs-expectData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateFwHs() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        BdcdyxxRequestDTO bdcdyxxRequestDTO = new BdcdyxxRequestDTO();
        String bdcdyh = "230522101202GB00107F00000000";
        bdcdyxxRequestDTO.setBdcdyh(bdcdyh);
        BdcFdcqDO bdcFdcqDO = new BdcFdcqDO();
        bdcFdcqDO.setZl("坐落");
        bdcFdcqDO.setFwxz(2);
        bdcFdcqDO.setSzc(4);
        bdcFdcqDO.setJzmj(78.78);
        bdcFdcqDO.setFtjzmj(89.89);
        bdcFdcqDO.setGhyt(22);
        bdcdyxxRequestDTO.setBdcFdcqDO(bdcFdcqDO);
        bdcdyxxService.updateBdcdyxx(bdcdyxxRequestDTO);
    }


    @Test
    @Rollback
    @Transactional(transactionManager = "transactionManager")
    @DatabaseSetup(value = "/data/bdcdyxxFwYchs-setupData.xml")
    @ExpectedDatabase(value = "/data/bdcdyxxFwYchs-expectData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateFwYcHs() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        BdcdyxxRequestDTO bdcdyxxRequestDTO = new BdcdyxxRequestDTO();
        String bdcdyh = "230522101202GB00107F00000000";
        bdcdyxxRequestDTO.setBdcdyh(bdcdyh);

        BdcYgDO bdcYgDO = new BdcYgDO();
        bdcYgDO.setZl("坐落");
        bdcYgDO.setFwxz(2);
        bdcYgDO.setSzc(4);
        bdcYgDO.setJzmj(78.78);
        bdcYgDO.setGhyt(22);
        bdcdyxxRequestDTO.setBdcYgDO(bdcYgDO);
        bdcdyxxService.updateBdcdyxx(bdcdyxxRequestDTO);
    }

    @Test
    @Rollback
    @Transactional(transactionManager = "transactionManager")
    @DatabaseSetup(value = "/data/bdcdyxxFwLjz-setupData.xml")
    @ExpectedDatabase(value = "/data/bdcdyxxFwLjz-expectData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateFwLjz() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        BdcdyxxRequestDTO bdcdyxxRequestDTO = new BdcdyxxRequestDTO();
        String bdcdyh = "230522101202GB00107F00000000";
        bdcdyxxRequestDTO.setBdcdyh(bdcdyh);
        BdcFdcqDO bdcFdcqDO = new BdcFdcqDO();
        bdcFdcqDO.setZl("坐落地址");
        bdcFdcqDO.setJzmj(189.89);
        bdcFdcqDO.setGhyt(99);
        bdcdyxxRequestDTO.setBdcFdcqDO(bdcFdcqDO);
        bdcdyxxService.updateBdcdyxx(bdcdyxxRequestDTO);
    }

    private void setupDjQlrReqDto(BdcdyxxRequestDTO bdcdyxxRequestDTO){
        // 权利人
        BdcQlrDO bdcQlrDO = new BdcQlrDO();
        bdcQlrDO.setQlrmc("权利人名称1");
        bdcQlrDO.setZjzl(1);
        bdcQlrDO.setZjh("123");
        bdcQlrDO.setQlrlb("1");
        bdcQlrDO.setFrmc("frmc");
        bdcQlrDO.setFrdh("frdh");
        bdcQlrDO.setDlrmc("dlrmc");
        bdcQlrDO.setDlrdh("dlrdh");
        BdcQlrDO bdcQlrDO2 = new BdcQlrDO();
        bdcQlrDO2.setQlrmc("权利人名称2");
        bdcQlrDO2.setZjzl(1);
        bdcQlrDO2.setZjh("123");
        bdcQlrDO2.setQlrlb("1");
        bdcQlrDO2.setFrmc("frmc");
        bdcQlrDO2.setFrdh("frdh");
        bdcQlrDO2.setDlrmc("dlrmc");
        bdcQlrDO2.setDlrdh("dlrdh");
        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        bdcQlrDOList.add(bdcQlrDO);
        bdcQlrDOList.add(bdcQlrDO2);
        bdcdyxxRequestDTO.setBdcQlrDOList(bdcQlrDOList);
    }


    private void setupDjdcbBdcdyxxReqDTO(BdcdyxxRequestDTO bdcdyxxRequestDTO) throws ParseException {
        BdcBdcdjbZdjbxxDO bdcBdcdjbZdjbxxDO = new BdcBdcdjbZdjbxxDO();
        bdcBdcdjbZdjbxxDO.setZl("南京");
        bdcBdcdjbZdjbxxDO.setYt("11");
        bdcBdcdjbZdjbxxDO.setQlxz(11);
        bdcdyxxRequestDTO.setBdcBdcdjbZdjbxxDO(bdcBdcdjbZdjbxxDO);

        BdcJsydsyqDO bdcJsydsyqDO = new BdcJsydsyqDO();
        String qsrqStr = "2018-09-09 12:00:00";
        Date  qsrq = DateUtils.parseDate(qsrqStr,"yyyy-MM-dd HH:mm:ss");

        bdcJsydsyqDO.setSyqqssj(qsrq);
        String jsrqStr = "2019-09-09 12:00:00";
        Date jsrq = DateUtils.parseDate(jsrqStr,"yyyy-MM-dd HH:mm:ss");
        bdcJsydsyqDO.setSyqjssj(jsrq);
        bdcJsydsyqDO.setTdsyqr("土地所有权人2");
        bdcdyxxRequestDTO.setBdcJsydsyqDO(bdcJsydsyqDO);
    }
}