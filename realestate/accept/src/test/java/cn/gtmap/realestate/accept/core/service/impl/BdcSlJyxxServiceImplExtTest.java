package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.core.service.BdcSlJyxxService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyBaxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyClfHtxxDTO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJyxxFeignService;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSONObject;
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

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/24
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
public class BdcSlJyxxServiceImplExtTest {
    @Autowired
    BdcSlJyxxService bdcSlJyxxService;
    @Autowired
    BdcSlJyxxFeignService bdcSlJyxxFeignService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlJbxx-setupData.xml")
    public void saveBdcSlJyxx() {
        BdcSlJyxxDO bdcSlJyxxDO =new BdcSlJyxxDO();
        bdcSlJyxxDO.setJyxxid(UUIDGenerator.generate16());
        bdcSlJyxxDO.setHtbah("123");
        bdcSlJyxxDO.setXmid("test");
        bdcSlJyxxDO =bdcSlJyxxService.saveBdcSlJyxx(bdcSlJyxxDO);
        Assert.assertNotNull(bdcSlJyxxDO);

    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlJyxx-setupData.xml")
    public void queryFcjyHtxx() {
        String xmid = UUIDGenerator.generate16();
        String json = "{\"bdcQlr\":[]," +
                "\"bdcSlFwxx\":{\"ckmj\":222.0,\"czqk\":\"222\",\"dxsmj\":222.0,\"fjh\":\"222\",\"fwdh\":\"222\",\"fwjg\":3," +
                "\"fwxxid\":\"36OJ4040K7WMZ7EL1\",\"fwyt\":11,\"glmj\":222.0,\"jzcx\":\"11\",\"jznf\":2018,\"szc\":111," +
                "\"tnmj\":222.0,\"xmid\":\"36OJ4040K7WMZ7EL\",\"xqmc\":\"御澜府222\",\"xzqh\":\"2222\",\"ycmj\":222.0,\"zcs\":222}," +
                "\"bdcSlJyxx\":{\"dj\":222.0,\"htbah\":\"222\",\"htbasj\":1561651200000,\"htbh\":\"222\",\"htdjsj\":1561651200000," +
                "\"htmj\":129.0,\"htzt\":2,\"jyje\":222.0,\"jylx\":1,\"jyxxid\":\"36OJ4040K7WMZ7EL1\",\"scjydjsj\":1561651200000,\"xmid\":\"36OJ4040K7WMZ7EL\"}," +
                "\"bdcSlSqr\":[]}";
        FcjyBaxxDTO fcjyClfHtxx = JSONObject.parseObject(json, FcjyBaxxDTO.class);
        bdcSlJyxxService.handleClfHtxxResponse(fcjyClfHtxx,xmid,"zhlc");
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcSlJyxx-setupData.xml")
    public void batchDeleteSlJyxx() {
        List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxService.listBdcSlJyxxByXmid("20180001");
        List<BdcSlJyxxDO> bdcSlJyxxDOList1 = bdcSlJyxxService.listBdcSlJyxxByXmid("20180002");
        Assert.assertEquals(2,bdcSlJyxxDOList.size());
        Assert.assertEquals(1,bdcSlJyxxDOList1.size());
        bdcSlJyxxDOList = bdcSlJyxxService.listBdcSlJyxxByXmid("20180001");
        bdcSlJyxxDOList1 = bdcSlJyxxService.listBdcSlJyxxByXmid("20180002");
        Assert.assertEquals(0,bdcSlJyxxDOList.size());
        Assert.assertEquals(0,bdcSlJyxxDOList1.size());
    }
}
