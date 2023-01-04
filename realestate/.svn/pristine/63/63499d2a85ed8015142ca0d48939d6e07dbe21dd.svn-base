package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmLsgxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.init.App;
import com.alibaba.fastjson.JSON;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/10/31
 * @description 查询不动产信息测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config-dbunit.xml"})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = App.class)
@Slf4j
public class BdcXmServiceTest {
    private static final String RESULT = "result";
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final String MSG = "参数错误！";
    @Autowired
    private BdcXmService bdcXmService;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

//    @Transactional(transactionManager = "transactionManager")
//    @Test
//    @Rollback
//    @DatabaseSetup(value = "/data/BdcXmSetup-data.xml")
//    public void listBdcXm() throws Exception {
//        // 无参数传入
//        BdcXmDO bdcXmDO = new BdcXmDO();
//        List list = bdcXmService.listBdcXm(bdcXmDO);
//        Assert.isNull(list, FAIL);
//
//        // 传入正确参数
//        bdcXmDO.setSlbh("1232123344");
//        list = bdcXmService.listBdcXm(bdcXmDO);
//        Assert.notEmpty(list, SUCCESS);
//    }
//
//    @Transactional(transactionManager = "transactionManager")
//    @Test
//    @Rollback
//    @DatabaseSetup(value = "/data/BdcXmSetup-data.xml")
//    public void queryBdcXmByPrimaryKey() throws Exception {
//
//        // 传入正确参数
//        BdcXmDO bdcXmDO = bdcXmService.queryBdcXmByPrimaryKey("4444444");
//        Assert.isInstanceOf(bdcXmDO.getClass(), bdcXmDO);
//
//        // 传入空参数
//        thrown.expect(MissingArgumentException.class);
//        bdcXmService.queryBdcXmByPrimaryKey("");
//    }
//
//    @Transactional(transactionManager = "transactionManager")
//    @Test
//    @Rollback
//    @DatabaseSetup(value = "/data/BdcXmSetup-data.xml")
//    public void updateBdcXm() {
//        BdcXmDO bdcXmDO = new BdcXmDO();
//        bdcXmDO.setXmid("4444444");
//        bdcXmDO.setAjzt(1);
//        org.junit.Assert.assertEquals(1, bdcXmService.updateBdcXm(bdcXmDO));
//
//        bdcXmDO.setXmid("");
//        thrown.expect(MissingArgumentException.class);
//        bdcXmService.updateBdcXm(bdcXmDO);
//        bdcXmService.updateBdcXm(null);
//    }


    @Test
    public void queryBdcxmLsgxByXmid() throws Exception {
        BdcXmLsgxDTO bdcXmLsgxDTO = bdcXmService.queryBdcxmLsgxByXmid("00HB95WQFV0G4VY5");
        log.info("{}", JSON.toJSONString(bdcXmLsgxDTO));
    }
}