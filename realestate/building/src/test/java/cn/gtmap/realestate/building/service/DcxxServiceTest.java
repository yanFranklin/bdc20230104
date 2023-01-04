package cn.gtmap.realestate.building.service;

import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.dto.building.DcxxDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.apache.commons.lang3.StringUtils;
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


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-27
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
public class DcxxServiceTest {

    @Autowired
    private DcxxService dcxxService;


    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/dcxx-setupData.xml")
    public void queryFwhsDcxxByFwHsIndex() {
        String fwHsIndex = "987654321";
        DcxxDTO dcxxDTO = dcxxService.queryFwDcxx(Constants.BDCDYFWLX_H,fwHsIndex);
        Assert.assertNotNull(dcxxDTO);
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/dcxx-setupData.xml")
    @ExpectedDatabase(value = "/data/dcxxupdate-expectData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateFwhsDcxx() {
        String fwHsIndex = "987654321";
        String dcyj = "这是我改后的调查意见";
        DcxxDTO dcxxDTO = new DcxxDTO();
        dcxxDTO.setBdcdyfwlx(Constants.BDCDYFWLX_H);
        dcxxDTO.setFwIndex(fwHsIndex);
        dcxxDTO.setDcyj(dcyj);
        Integer result = dcxxService.updateFwDcxx(dcxxDTO);
        Assert.assertTrue(result > 0);
        DcxxDTO dcxxDTO2 = dcxxService.queryFwDcxx(Constants.BDCDYFWLX_H,fwHsIndex);
        Assert.assertTrue(dcyj.equals(dcxxDTO2.getDcyj()));
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/dcxx-setupData.xml")
    public void deleteFwhsDcxxByFwHsIndex() {
        String fwHsIndex = "987654321";
        Integer result = dcxxService.deleteFwDcxx(Constants.BDCDYFWLX_H,fwHsIndex);
        Assert.assertTrue(result > 0);

        DcxxDTO dcxxDTO2 = dcxxService.queryFwDcxx(Constants.BDCDYFWLX_H,fwHsIndex);
        Assert.assertTrue(StringUtils.isBlank(dcxxDTO2.getDcyj())
                            && StringUtils.isBlank(dcxxDTO2.getCqly())
                && StringUtils.isBlank(dcxxDTO2.getDcz())
                && StringUtils.isBlank(dcxxDTO2.getFjsm())
                && StringUtils.isBlank(dcxxDTO2.getGyqk())
                && dcxxDTO2.getDcsj() == null
                && StringUtils.equals(fwHsIndex, dcxxDTO2.getFwIndex()));
    }
}