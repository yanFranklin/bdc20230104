package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.register.App;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import cn.gtmap.realestate.register.service.BdcDbxxService;
import cn.gtmap.realestate.register.service.BdcQlxxService;
import cn.gtmap.realestate.register.service.BdcXmxxService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
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

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/28
 * @description 登簿信息服务实现类中部分DAO方法DbUnit测试
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
public class BdcDbxxServiceImplDbUnitTest {
    @Autowired
    private BdcDbxxService bdcDbxxService;
    @Autowired
    private BdcQlxxService bdcQlxxService;
    @Autowired
    BdcXmxxService bdcXmxxService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    EntityMapper entityMapper;


    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新项目案件状态为2已完成状态 测试
     */
    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcDbxx-setupData.xml")
    public void testChangeAjzt() throws Exception {
        bdcDbxxService.changeAjzt("f166d2ef22ad402c803c66d2ef220001");
        bdcDbxxService.changeAjzt("f166d2ef22ad402c803c66d2ef220002");
        bdcDbxxService.changeAjzt("f166d2ef22ad402c803c66d2ef220003");

        List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid("CS111222");
        Assert.assertEquals(3, bdcXmDOList.size());

        for (BdcXmDO bdcXmDO : bdcXmDOList){
            Assert.assertEquals(2, bdcXmDO.getAjzt().intValue());
        }
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/bdcDbxx-setupData.xml")
    public void testUpdateBdcDbxxAndQszt(){
        //bdcDbxxService.updateBdcDbxxAndQsztSyncQj("gzlslid_1", "admin");
        String gzlslid = "gzlslid_1";
        String userName = "admin";
        String djjg = "测试登记机构";

        DbxxQO dbxxQO = new DbxxQO();
        dbxxQO.setDbr(userName);
        dbxxQO.setDjjg(djjg);
        dbxxQO.setDjsj(new Date());
        dbxxQO.setQszt(CommonConstantUtils.QSZT_VALID);
        dbxxQO.setZxQszt(CommonConstantUtils.QSZT_HISTORY);

        List<BdcQl> bdcQls=bdcQlxxService.queryBdcCqlist("320902004004GB00018F00010003");
        bdcQls.forEach(bdcQl -> {
            // 虚拟当前权利
            if (StringUtils.equals(bdcQl.getXmid(), "xmid_1")) {
                dbxxQO.setXmid("xmid_1");
                // 更新项目
                bdcXmxxService.updateBdcXmDbxx(dbxxQO);
                // 更新权利
                bdcQlxxService.udpateBdcQlDbxxAndQszt(bdcQl, dbxxQO);
            }
        });
        // 验证修改状态后的数据
        List<BdcQl> bdcQlList = bdcQlxxService.queryBdcCqlist("320902004004GB00018F00010003");
        bdcQls.forEach(bdcQl -> {
            // 虚拟当前权利
            if(StringUtils.equals(bdcQl.getXmid(),"xmid_1")){
                Assert.assertEquals(1,bdcQl.getQszt().intValue());
                Assert.assertEquals(djjg, bdcQl.getDjjg());

                BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, "xmid_1");
                Assert.assertEquals(1, bdcXmDO.getQszt().intValue());
                Assert.assertEquals(djjg, bdcXmDO.getDjjg());
            }
            // 虚拟原权利
            if (StringUtils.equals(bdcQl.getXmid(), "yxmid_1")) {
                Assert.assertEquals(2, bdcQl.getQszt().intValue());
                Assert.assertEquals(djjg, bdcQl.getDjjg());
                BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
                Assert.assertEquals(userName, bdcDyaqDO.getZxdydbr());

                BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, "yxmid_1");
                Assert.assertEquals(2, bdcXmDO.getQszt().intValue());
            }
        });
    }
}