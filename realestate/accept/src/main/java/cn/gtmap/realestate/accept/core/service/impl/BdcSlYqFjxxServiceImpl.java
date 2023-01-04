package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlYqFjxxService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYqFjxxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BdcSlYqFjxxServiceImpl implements BdcSlYqFjxxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlYqFjxxServiceImpl.class);

    @Autowired
    private EntityMapper entityMapper;

    @Override
    public List<BdcSlYqFjxxDO> listBdcSlYqFjxx(BdcSlYqFjxxDO bdcSlYqFjxxDO) {
        if(Objects.isNull(bdcSlYqFjxxDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少查询参数");
        }
        Example example = new Example(BdcSlYqFjxxDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(bdcSlYqFjxxDO.getGzlslid())) {
            criteria.andEqualTo("gzlslid", bdcSlYqFjxxDO.getGzlslid());
        }
        if (StringUtils.isNotBlank(bdcSlYqFjxxDO.getSlbh())) {
            criteria.andEqualTo("slbh", bdcSlYqFjxxDO.getSlbh());
        }
        if (StringUtils.isNotBlank(bdcSlYqFjxxDO.getWjzxid())) {
            criteria.andEqualTo("wjzxid", bdcSlYqFjxxDO.getWjzxid());
        }
        return entityMapper.selectByExample(example);
    }

    @Override
    public void batchSaveBdcSlYqFjxx(List<BdcSlYqFjxxDO> bdcSlYqFjxxDOList) {
        if(CollectionUtils.isEmpty(bdcSlYqFjxxDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到需要保存的云签信息");
        }
        for(BdcSlYqFjxxDO bdcSlYqFjxxDO : bdcSlYqFjxxDOList){
          if(StringUtils.isBlank(bdcSlYqFjxxDO.getId())){
              bdcSlYqFjxxDO.setId(UUIDGenerator.generate16());
          }
        }
        entityMapper.batchSaveSelective(bdcSlYqFjxxDOList);
    }

    @Override
    public void deleteBdcSlYqFjxxByGzlslid(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        Example example = new Example(BdcSlYqFjxxDO.class);
        example.createCriteria().andEqualTo("gzlslid",gzlslid);
        entityMapper.deleteByExample(example);
    }
}
