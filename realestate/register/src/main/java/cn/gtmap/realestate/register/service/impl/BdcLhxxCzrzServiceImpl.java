package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcLhxxCzrzDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.register.service.BdcLhxxCzrzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class BdcLhxxCzrzServiceImpl implements BdcLhxxCzrzService {

    @Autowired
    EntityMapper entityMapper;

    @Override
    public void addLhxxCzrz(BdcLhxxCzrzDO bdcLhxxCzrzDO) {
        if(StringUtils.isBlank(bdcLhxxCzrzDO.getLhxxczid())){
            bdcLhxxCzrzDO.setLhxxczid(UUIDGenerator.generate16());
        }
        entityMapper.insert(bdcLhxxCzrzDO);
    }

    @Override
    public void plAddLhxxCzrz(List<BdcLhxxCzrzDO> bdcLhxxCzrzDOList) {
        if(CollectionUtils.isEmpty(bdcLhxxCzrzDOList)){
            return;
        }
        for(BdcLhxxCzrzDO bdcLhxxCzrzDO : bdcLhxxCzrzDOList){
            if(StringUtils.isBlank(bdcLhxxCzrzDO.getLhxxczid())){
                bdcLhxxCzrzDO.setLhxxczid(UUIDGenerator.generate16());
            }
        }
        this.entityMapper.insertBatchSelective(bdcLhxxCzrzDOList);
    }

    @Override
    public List<BdcLhxxCzrzDO> queryLhxxCzrz(BdcLhxxCzrzDO bdcLhxxCzrzDO) {
        if(Objects.isNull(bdcLhxxCzrzDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到量化信息操作日志内容。");
        }
        Example example = new Example(BdcLhxxCzrzDO.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(bdcLhxxCzrzDO.getFwDcbIndex())){
            criteria.andEqualTo("fwDcbIndex", bdcLhxxCzrzDO.getFwDcbIndex());
        }
        if(StringUtils.isNotBlank(bdcLhxxCzrzDO.getLszd())){
            criteria.andEqualTo("lszd", bdcLhxxCzrzDO.getLszd());
        }
        if(StringUtils.isNotBlank(bdcLhxxCzrzDO.getGzlslid())){
            criteria.andEqualTo("gzlslid", bdcLhxxCzrzDO.getGzlslid());
        }
        if(Objects.nonNull(bdcLhxxCzrzDO.getLhlx())){
            criteria.andEqualTo("lhlx", bdcLhxxCzrzDO.getLhlx());
        }

        List<BdcLhxxCzrzDO> bdcLhxxCzrzDOList = entityMapper.selectByExample(example);
        return bdcLhxxCzrzDOList;
    }

}
