package cn.gtmap.realestate.config.service.impl;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.config.core.mapper.BdcJobGroupMapper;
import cn.gtmap.realestate.config.service.BdcJobGroupService;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
 * @version 1.0, 2022/01/01 15:12
 * @description jobGroup实现类
 */
@Slf4j
@Service
public class BdcJobGroupServiceImpl implements BdcJobGroupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcJobGroupService.class);

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private Repo repo;
    @Autowired
    private BdcJobGroupMapper bdcJobGroupMapper;



    /**
     * 分页获取执行器数据列表
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param pageable pageable
     * @param bdcJobGroupDO jobGroup
     * @return JobGroup
     */
    @Override
    public Page<BdcJobGroupDO> listBdcJobGroupPage(Pageable pageable, BdcJobGroupDO bdcJobGroupDO) {
        return repo.selectPaging("listBdcJobGroupByPage", bdcJobGroupDO,pageable);
    }

    /**
     * 保存执行器信息 没有记录则新增
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobGroupDO
     * @return jobGroup
     */
    @Override
    public BdcJobGroupDO saveJobGroup(BdcJobGroupDO bdcJobGroupDO) {

        if (!CheckParameter.checkAnyParameter(bdcJobGroupDO)){
            throw new AppException("传入参数为空！");
        }

        if (Objects.nonNull(bdcJobGroupDO.getId())){
            bdcJobGroupDO.setId(12);
            entityMapper.insertSelective(bdcJobGroupDO);
        } else {
            Example example= new Example(BdcJobGroupDO.class,false);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("id", bdcJobGroupDO.getId());
            entityMapper.updateByExampleSelectiveNotNull(bdcJobGroupDO, example);
        }
        return bdcJobGroupDO;
    }



}
