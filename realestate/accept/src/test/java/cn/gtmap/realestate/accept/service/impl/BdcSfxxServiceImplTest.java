package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.core.mapper.BdcSlSfxxMapper;
import cn.gtmap.realestate.accept.service.BdcSfService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSfjfblResponseDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJfblQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/4/2
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@Slf4j
public class BdcSfxxServiceImplTest {
    @Autowired
    BdcSfService bdcSfService;

    @Autowired
    BdcSfxxServiceImpl bdcSfxxService;

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    Repo repo;

    @Autowired
    BdcSlSfxxMapper bdcSlSfxxMapper;

    @Test
    public void selectByExample() {
        cn.gtmap.realestate.common.core.support.mybatis.mapper.Example example = new cn.gtmap.realestate.common.core.support.mybatis.mapper.Example(BdcSlSfxxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("xmid", "DNFAWB8EFWZFU8HE");
        log.info(JSON.toJSONString(entityMapper.selectByExample(example)));
    }

    @Test
    public void insert() {
        BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
        bdcSlSfxxDO.setSfxxid("DNFAWB8EFWZFU8HE");
        bdcSlSfxxDO.setXmid("DNFAWB8EFWZFU8HE");
        bdcSlSfxxDO.setGzlslid("12we");
        bdcSlSfxxDO.setYwr("dsp");
        log.info(JSON.toJSONString(entityMapper.insertSelective(bdcSlSfxxDO)));
    }

    @Test
    public void jfbl() {
        BdcSlJfblQO bdcSlJfblQO = new BdcSlJfblQO();
        BdcSfjfblResponseDTO jfbl = bdcSfxxService.jfbl(bdcSlJfblQO);
        log.info(JSON.toJSONString(jfbl));
    }

    @Test
    public void cxjfqk() {
        BdcSlJfblQO bdcSlJfblQO = new BdcSlJfblQO();
        bdcSfxxService.cxjfqk(bdcSlJfblQO, false);
    }
}
