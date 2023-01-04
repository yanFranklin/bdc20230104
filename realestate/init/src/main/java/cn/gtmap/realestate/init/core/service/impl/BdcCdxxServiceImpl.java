package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcCdxxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.service.BdcCdxxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: realestate
 * @description: 查档信息服务实现
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-23 17:24
 **/
@Service
public class BdcCdxxServiceImpl implements BdcCdxxService {
    @Autowired
    private EntityMapper entityMapper;
    /**
     * @param bdcCdxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询查档信息
     * @date : 2020/9/23 17:21
     */
    @Override
    public BdcCdxxDO queryBdcCdxx(BdcCdxxDO bdcCdxxDO) {
        List<BdcCdxxDO> bdcCdxxDOList = new ArrayList<>(1);
        bdcCdxxDOList = entityMapper.selectByObj(bdcCdxxDO);
        if(CollectionUtils.isNotEmpty(bdcCdxxDOList)) {
            return bdcCdxxDOList.get(0);
        }
        return new BdcCdxxDO();
    }

    /**
     * @param bdcCdxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存查档信息数据
     * @date : 2020/9/23 17:29
     */
    @Override
    public BdcCdxxDO saveBdcCdxx(BdcCdxxDO bdcCdxxDO) {
        if(StringUtils.isBlank(bdcCdxxDO.getCdxxid())) {
            bdcCdxxDO.setCdxxid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcCdxxDO);
        } else {
            entityMapper.updateByPrimaryKey(bdcCdxxDO);
        }
        return bdcCdxxDO;
    }

    /**
     * @param cdxxid
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除查档信息
     * @date : 2020/9/23 17:37
     */
    @Override
    public int deleteBdcCdxx(String cdxxid, String xmid) {
        if(StringUtils.isNotBlank(cdxxid)) {
            return entityMapper.deleteByPrimaryKey(BdcCdxxDO.class,cdxxid);
        } else if(StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcCdxxDO.class);
            example.createCriteria().andEqualTo("xmid",xmid);
            return entityMapper.deleteByExample(example);
        } else {
            return -1;
        }
    }
}
