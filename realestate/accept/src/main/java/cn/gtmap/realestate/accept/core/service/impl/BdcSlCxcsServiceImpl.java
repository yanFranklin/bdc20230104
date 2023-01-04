package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlCxcsService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlCxcsDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/12/3
 * @description 不动产查询参数服务
 */
@Service
public class BdcSlCxcsServiceImpl implements BdcSlCxcsService {

    @Autowired
    EntityMapper entityMapper;

    @Override
    public List<BdcSlCxcsDO> queryBdcSlCxcsByGzlslid(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        Example example = new Example(BdcSlCxcsDO.class);
        example.createCriteria().andEqualTo("gzlslid", gzlslid);
        return entityMapper.selectByExample(example);
    }

    @Override
    public void saveBdcSlCxcsByGzlslid(BdcSlCxcsDO bdcSlCxcsDO) {
        if(Objects.isNull(bdcSlCxcsDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到查询参数信息");
        }
        Example example = new Example(BdcSlCxcsDO.class);
        example.createCriteria().andEqualTo("gzlslid", bdcSlCxcsDO.getGzlslid());
        List<BdcSlCxcsDO> bdcSlCxcsDOList =  entityMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(bdcSlCxcsDOList)){
            bdcSlCxcsDO.setCxid(bdcSlCxcsDOList.get(0).getCxid());
            this.entityMapper.updateByPrimaryKey(bdcSlCxcsDO);
        } else {
            bdcSlCxcsDO.setCxid(UUIDGenerator.generate16());
            this.entityMapper.insertSelective(bdcSlCxcsDO);
        }
    }
}
