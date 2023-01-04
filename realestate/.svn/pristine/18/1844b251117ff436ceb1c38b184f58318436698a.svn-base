package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcWqbaLcGxService;
import cn.gtmap.realestate.common.core.domain.accept.BdcWqbaLcGxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
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
 * @program: realestate
 * @description: 网签备案流程关系服务实现
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-03-16 10:05
 **/
@Service
public class BdcWqbaLcGxServiceImpl implements BdcWqbaLcGxService {

    @Autowired
    EntityMapper entityMapper;

    /**
     * @param bdcWqbaLcGxDO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description //查询网签备案
     * @date : 2021/3/16 9:53
     */
    @Override
    public BdcWqbaLcGxDO queryWqbaLcGxDO(BdcWqbaLcGxDO bdcWqbaLcGxDO) {
        if (Objects.isNull(bdcWqbaLcGxDO)) {
            throw new AppException("查询网签备案流程关系缺失必要参数");
        }
        Example example = new Example(BdcWqbaLcGxDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(bdcWqbaLcGxDO.getGxid())) {
            return entityMapper.selectByPrimaryKey(BdcWqbaLcGxDO.class, bdcWqbaLcGxDO.getGxid());
        }
        if (StringUtils.isNotBlank(bdcWqbaLcGxDO.getGzlslid())) {
            criteria.andEqualTo("gzlslid", bdcWqbaLcGxDO.getGzlslid());
        }
        if (StringUtils.isNotBlank(bdcWqbaLcGxDO.getHtbh())) {
            criteria.andEqualTo("htbh", bdcWqbaLcGxDO.getHtbh());
        }
        List<BdcWqbaLcGxDO> bdcWqbaLcGxDOList = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(bdcWqbaLcGxDOList)) {
            return bdcWqbaLcGxDOList.get(0);
        }
        return new BdcWqbaLcGxDO();
    }

    /**
     * @param bdcWqbaLcGxDO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description //新增网签备案
     * @date : 2021/3/16 9:53
     */
    @Override
    public BdcWqbaLcGxDO insertWqbaLcGxDO(BdcWqbaLcGxDO bdcWqbaLcGxDO) {
        if (Objects.isNull(bdcWqbaLcGxDO)) {
            throw new AppException("新增网签备案流程信息缺失数据");
        }
        if (StringUtils.isNotBlank(bdcWqbaLcGxDO.getGxid())) {
            entityMapper.updateByPrimaryKey(bdcWqbaLcGxDO);
        } else {
            bdcWqbaLcGxDO.setGxid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcWqbaLcGxDO);
        }
        return bdcWqbaLcGxDO;
    }

    /**
     * @param bdcWqbaLcGxDO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description //更新网签备案
     * @date : 2021/3/16 9:53
     */
    @Override
    public BdcWqbaLcGxDO updateWqbaLcGxDO(BdcWqbaLcGxDO bdcWqbaLcGxDO) {
        if (Objects.isNull(bdcWqbaLcGxDO) || StringUtils.isBlank(bdcWqbaLcGxDO.getGxid())) {
            throw new AppException("更新网签备案流程信息缺失数据");
        }
        entityMapper.updateByPrimaryKey(bdcWqbaLcGxDO);
        return bdcWqbaLcGxDO;
    }
}
