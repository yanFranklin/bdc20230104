package cn.gtmap.realestate.building.service;


import cn.gtmap.realestate.building.App;
import cn.gtmap.realestate.building.core.dbs.DynamicDataSource;
import cn.gtmap.realestate.building.core.dbs.DynamicDataSourceContextHolder;

import cn.gtmap.realestate.building.core.dbs.SwitchDB;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2021/6/4
 * @description 切换数据库测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class DbChangeTest {

    private static final Logger logger = LoggerFactory.getLogger(DbChangeTest.class);

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    DynamicDataSource dynamicDataSource;

    @Autowired
    private SwitchDB switchDB;

    private String bdcdyh="340103162011GB00026F00050054";



    @Test(timeout=5000)
    //@Transactional(rollbackFor = Exception.class) //标明此方法需使用事务
    public void testProcess(){
        String dbName ="";
        switchDB.change(dbName);
        Object result =process(dbName,"insert");
        logger.info("dbName:{}结果:{}",dbName,result);
        result =process(dbName,"read");
        logger.info("dbName:{}结果:{}",dbName,result);
        dbName ="lujiang";
        switchDB.change(dbName);
        result =process(dbName,"insert");
        logger.info("dbName:{}结果:{}",dbName,result);
        result =process(dbName,"read");
        logger.info("dbName:{}结果:{}",dbName,result);
    }

    /**
     * 事务测试
     * 注意：(1)有@Transactional注解的方法，方法内部不可以做切换数据库操作
     *      (2)在同一个service其他方法调用带@Transactional的方法，事务不起作用，（比如：在本类中使用testProcess调用process()）
     *         可以用其他service中调用带@Transactional注解的方法，或在controller中调用.
     * @param dbName
     * @return
     */
    //propagation 传播行为 isolation 隔离级别  rollbackFor 回滚规则
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public Object process(String dbName,String czlx) {
        String currentKey = DynamicDataSourceContextHolder.getDataSourceKey();
        logger.info("＝＝＝＝＝service当前连接的数据库是:{}" ,currentKey);
        if(StringUtils.equals("insert",czlx)) {
            FwLjzDO fwLjzDO = new FwLjzDO();
            fwLjzDO.setFwDcbIndex(UUIDGenerator.generate16());
            int count = entityMapper.insertSelective(fwLjzDO);
            if (count > 0) {
                return "新增成功"+fwLjzDO.getFwDcbIndex();
            } else {
                //手动回滚事务
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return "新增错误，事务已回滚";
            }
        }else{
            Example example =new Example(FwHsDO.class);
            example.createCriteria().andEqualTo("bdcdyh",bdcdyh);
            return entityMapper.selectByExampleNotNull(example);

        }
    }
}
