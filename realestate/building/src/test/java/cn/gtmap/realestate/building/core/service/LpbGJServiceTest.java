package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.dto.building.LpbGJRequestDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/29
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
public class LpbGJServiceTest {

    @Autowired
    private LpbGjService lpbGjService;
    @Autowired
    private FwHsService fwHsService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/lpbgj-setupData.xml")
    public void lpbGj() {
        LpbGJRequestDTO lpbGJRequestDTO=new LpbGJRequestDTO();
        lpbGJRequestDTO.setFwDcbIndex("f167b9f8f7cc402c802167b9f317013e");
        lpbGJRequestDTO.setCs(10);
        lpbGJRequestDTO.setMchs(10);
        lpbGJRequestDTO.setGjfs("1");
        lpbGjService.lpbGj(lpbGJRequestDTO);
        List<FwHsDO> fwHsDOList=fwHsService.listFwHstNullByFwDcbIndex("f167b9f8f7cc402c802167b9f317013e");
        Assert.assertNotNull(fwHsDOList);
    }
    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/lpbgj-setupData.xml")
    public void lpbGjtwo() {
        LpbGJRequestDTO lpbGJRequestDTO=new LpbGJRequestDTO();
        lpbGJRequestDTO.setFwDcbIndex("f167b9f8f7cc402c802167b9f317013e");
        lpbGJRequestDTO.setCs(10);
        lpbGJRequestDTO.setDys(2);
        lpbGJRequestDTO.setDymshs(10);
        lpbGJRequestDTO.setGjfs("2");
        lpbGjService.lpbGj(lpbGJRequestDTO);
        List<FwHsDO> fwHsDOList=fwHsService.listFwHstNullByFwDcbIndex("f167b9f8f7cc402c802167b9f317013e");
        Assert.assertNotNull(fwHsDOList);
    }
    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/lpbgj-setupData.xml")
    public void lpbGjtherr() {
        LpbGJRequestDTO lpbGJRequestDTO=new LpbGJRequestDTO();
        lpbGJRequestDTO.setFwDcbIndex("f167b9f8f7cc402c802167b9f317013e");
        lpbGJRequestDTO.setCs(10);
        lpbGJRequestDTO.setGjfs("3");
        List<String> hsdtgjList=new ArrayList<>();
        hsdtgjList.add("1,2");
        hsdtgjList.add("2,5");
        hsdtgjList.add("3,1");
        lpbGJRequestDTO.setHsdtgj(hsdtgjList);
        lpbGjService.lpbGj(lpbGJRequestDTO);
        List<FwHsDO> fwHsDOList=fwHsService.listFwHstNullByFwDcbIndex("f167b9f8f7cc402c802167b9f317013e");
        Assert.assertNotNull(fwHsDOList);
    }


}
