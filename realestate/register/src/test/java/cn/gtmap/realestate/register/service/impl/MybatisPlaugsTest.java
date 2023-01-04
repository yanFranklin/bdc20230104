package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.qo.init.BdcCfQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.register.App;
import cn.gtmap.realestate.register.core.mapper.BdcCfMapper;
import cn.gtmap.realestate.register.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.register.service.xxbl.BdcXxblLogService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@Slf4j
public class MybatisPlaugsTest {
    @Autowired
    private Repo repo;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcXxblLogService bdcXxblLogService;

    @Autowired
    BdcXmMapper bdcXmMapper;

    @Autowired
    BdcCfMapper bdcCfMapper;

    /**
     * 1.能否正确加密
     * 2.能否正确取到不同版本的字段
     */
    @Test
    public void commonMapperInsertMutilVersionTest() {
//        Example example = new Example(BdcCfDO.class);
//        example.createCriteria().andEqualTo("cfjg","123");
//        entityMapper.selectByExample();
        BdcCfDO bdcCfDO = new BdcCfDO();
        bdcCfDO.setQlid("4444");
        bdcCfDO.setCfwh("testcfwh");
        bdcCfDO.setJfywh("testjfywh");
        bdcCfDO.setJfjg("testjfjg");
        bdcCfDO.setSlbh("testslbh");
        entityMapper.insertSelective(bdcCfDO);
    }

    /**
     * 1.能否正确加密查询
     * 2.能否正确识别不同的条件
     */
    @Test
    public void commonMapperSelecttest() {
        Example example = new Example(BdcCfDO.class);
        example.createCriteria()
                .andEqualTo("jfjg","testjfjg")
                .andLike("jfywh","testjfywh")
        ;
        List<Object> objects = entityMapper.selectByExample(example);
        log.info("{}", JSON.toJSONString(objects));
    }

    /**
     * 1.能否正确识别不同版本的条件
     */
    @Test
    public void commonMapperDeletetest() {
        Example example = new Example(BdcCfDO.class);
        example.createCriteria()
                .andEqualTo("qlid","4444")
                .andLike("jfywh","testjfywh")
        ;
        entityMapper.deleteByExample(example);
    }

    /**
     * 返回
     * 1.返回基本类型
     * 2.返回map,返回map多版本
     * 3.返回对象,返回对象多版本
     *
     * 请求
     * 1 基本类型
     * 2 对象
     *
     */
    @Test
    public void mapperParamtest() {
        String s = bdcXmMapper.queryMaxXnBdcdyh("320402002001GB00573F47370001");
        log.info(s);
    }

    /**
     * 能否正确识别参数,map和bean两种方式
     * 能否正确处理返回值,map,bean,简单类型三种方式
     * repo,mapper两种查询情况
     */
    @Test
    public void mapperSimpletest() {
        //map请求方式 + 不同版本
        HashMap param = new HashMap<>();
        param.put("gzlslid","gzlslid");
        param.put("zsid","zsid");
        param.put("qllx","qllx");
        //List<BdcXmDO> bdcXmDOS = bdcXmMapper.queryXmByGzlslidAndZsid(param);
        //log.info("{}",JSON.toJSONString(bdcXmDOS));

        //bean请求方式
        BdcCfQO bdcCfQO = new BdcCfQO();
        bdcCfQO.setXmid(Arrays.asList("123","234"));
        //bdcCfMapper.listBdcCfxx(bdcCfQO);

        //bean请求方式 -- bean返回结果
        //List<BdcCfDO> bdcCfDOS = bdcCfMapper.listBdcCfxx(bdcCfQO);
        //log.info("{}",JSON.toJSONString(bdcCfDOS));

        //map返回结果--多版本
        List<Map<String, Object>> cfByXmid = bdcCfMapper.getCfByXmid(bdcCfQO);
        log.info("{}",JSON.toJSONString(cfByXmid));

        //repo查询
        Page<Object> getCfByXmid = repo.selectPaging("getCfByXmid", bdcCfQO, 0, 100);
        log.info("{}",JSON.toJSONString(getCfByXmid));

        List<Object> objects = repo.selectList("getCfByXmid", bdcCfQO);
        log.info("{}",JSON.toJSONString(objects));
    }

    @Test
    public void commonMapperUpdate() {
//        Example example = new Example(BdcCfDO.class);
//        example.createCriteria().andEqualTo("cfjg","123");
//        entityMapper.selectByExample();
        Example example = new Example(BdcCfDO.class);
        example.createCriteria()
                .andEqualTo("qlid","3333")
                .andLike("jfywh","testjfywh");

        BdcCfDO bdcCfDO = new BdcCfDO();
        //bdcCfDO.setQlid("4444");
        bdcCfDO.setCfwh("testcfwh4");
        bdcCfDO.setJfywh("testjfywh4");
        bdcCfDO.setJfjg("testjfjg4");
        bdcCfDO.setSlbh("testslbh4");
        entityMapper.updateByExampleSelectiveNotNull(bdcCfDO,example);
    }
}