package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.App;
import cn.gtmap.realestate.init.service.other.CopyAcceptIntoInitService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/8
 * @description 受理数据覆盖初始化数据服务测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config-dbunit.xml"})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = App.class)
public class CopyAcceptIntoInitServiceTest {
    @Autowired
    CopyAcceptIntoInitService copyAcceptIntoInitService;
    @Autowired
    BdcXmService bdcXmService;

    @Test
    public void copyBdcSlXmxx(){
        BdcSlxxDTO bdcSlxxDTO = initAcceptDTO();
        copyAcceptIntoInitService.copyBdcSlXmxx(bdcSlxxDTO);
        bdcXmService.queryBdcXmByPrimaryKey("999");
    }

    @Test
    public void copyBdcSlQlxx(){
        BdcSlxxDTO bdcSlxxDTO = initAcceptDTO();
        copyAcceptIntoInitService.copyBdcSlQlxx(bdcSlxxDTO);
    }

    public BdcSlxxDTO initAcceptDTO(){
        BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO();
        bdcSlXmLsgxDO.setGxid(UUIDGenerator.generate16());
        bdcSlXmLsgxDO.setXmid("123456");
        bdcSlXmLsgxDO.setYxmid("bdc-123");
        bdcSlXmLsgxDO.setZxyql(1);
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
        bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        bdcSlJbxxDO.setXmly(1);
        bdcSlJbxxDO.setSlbh("111");
        bdcSlJbxxDO.setGzlslid("test");
        BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
        bdcSlXmDO.setXmid("123456");
        bdcSlXmDO.setBdcdyh("666");
        bdcSlXmDO.setBdclx(2);
        bdcSlXmDO.setDjxl("1");
        bdcSlXmDO.setDyje(123.43);
        bdcSlXmDO.setBdbzzqse(234.54);
        bdcSlXmDO.setLb("林斑");
        bdcSlXmDO.setSyqmj(9999.00);
        BdcSlXmDO bdcSlXmDO1 = new BdcSlXmDO();
        bdcSlXmDO1.setXmid("f166f665708c402c80d266f665700003");
        bdcSlXmDO1.setBdcdyh("555");
        bdcSlXmDO1.setBdclx(2);
        bdcSlXmDO1.setDjxl("2");
        bdcSlXmDO1.setDyje(123.43);
        bdcSlXmDO1.setBdbzzqse(234.54);
        bdcSlXmDO1.setLb("林斑");
        bdcSlXmDO1.setSyqmj(9999.00);
        BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
        bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);
        bdcSlXmDTO.setBdcSlXmLsgxList(bdcSlXmLsgxDOList);
        BdcSlXmDTO bdcSlXmDTO1 = new BdcSlXmDTO();
        bdcSlXmDTO1.setBdcSlXm(bdcSlXmDO1);
        List<BdcSlXmDTO> bdcSlXmDTOList = new ArrayList<>();
        bdcSlXmDTOList.add(bdcSlXmDTO);
        bdcSlXmDTOList.add(bdcSlXmDTO1);
        BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();
        bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);
        bdcSlxxDTO.setBdcSlXmList(bdcSlXmDTOList);
        return bdcSlxxDTO;
    }

}
