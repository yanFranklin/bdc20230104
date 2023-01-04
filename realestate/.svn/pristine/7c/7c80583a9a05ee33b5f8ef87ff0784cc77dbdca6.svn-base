package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlXmService;
import cn.gtmap.realestate.accept.core.service.BdcSlXqxxService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXqxxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: realestate
 * @description: 需求流转信息服务实现
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2020-09-23 17:24
 **/
@Service
public class BdcSlXqxxServiceImpl implements BdcSlXqxxService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    BdcSlXmService bdcSlXmService;

    /**
     * @param bdcSlXqxxDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询需求流转信息
     * @date : 2021/9/23 17:21
     */
    @Override
    public BdcSlXqxxDO queryBdcSlXqxx(BdcSlXqxxDO bdcSlXqxxDO) {
        List<BdcSlXqxxDO> bdcSlXqxxDOList = new ArrayList<>(1);
        bdcSlXqxxDOList = entityMapper.selectByObj(bdcSlXqxxDO);
        if(CollectionUtils.isNotEmpty(bdcSlXqxxDOList)) {
            return bdcSlXqxxDOList.get(0);
        }
        return new BdcSlXqxxDO();
    }

    /**
     * @param bdcSlXqxxDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 保存需求流转信息数据
     * @date : 2021/9/23 17:29
     */
    @Override
    public BdcSlXqxxDO saveBdcSlXqxx(BdcSlXqxxDO bdcSlXqxxDO) {
        if(StringUtils.isBlank(bdcSlXqxxDO.getXqxxid())) {
            bdcSlXqxxDO.setXqxxid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcSlXqxxDO);
        } else {
            entityMapper.updateByPrimaryKey(bdcSlXqxxDO);
        }
        return bdcSlXqxxDO;
    }

    /**
     * @param xqxxid
     * @param xmid
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除需求流转信息
     * @date : 2021/9/23 17:37
     */
    @Override
    public int deleteBdcSlXqxx(String xqxxid, String xmid) {
        if(StringUtils.isNotBlank(xqxxid)) {
            return entityMapper.deleteByPrimaryKey(BdcSlXqxxDO.class,xqxxid);
        } else if(StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlXqxxDO.class);
            example.createCriteria().andEqualTo("xmid",xmid);
            return entityMapper.deleteByExample(example);
        } else {
            return -1;
        }
    }

    @Override
    public void deleteBdcSlXqxxByGzlslid(String gzlslid) {
        if(StringUtils.isNotBlank(gzlslid)){
            Example example = new Example(BdcSlXqxxDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            entityMapper.deleteByExample(example);
        }
    }

}
