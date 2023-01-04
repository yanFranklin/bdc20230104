package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlTdcrjService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlTdcrjDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/8
 * @description  出让金服务
 */
@Service
public class BdcSlTdcrjServiceImpl implements BdcSlTdcrjService {

    @Autowired
    private EntityMapper entityMapper;

    @Override
    public List<BdcSlTdcrjDO> listBdcSlTdcrjByXmid(String xmid){
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlTdcrjDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            return entityMapper.selectByExample(example);
        }
        return null;

    }

    @Override
    public void insertBdcSlTdcrj(BdcSlTdcrjDO bdcSlTdcrjDO){
        if(bdcSlTdcrjDO != null){
            if(StringUtils.isBlank(bdcSlTdcrjDO.getTdcrjid())){
                bdcSlTdcrjDO.setTdcrjid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcSlTdcrjDO);
        }
    }

    @Override
    public void deleteBdcSlTdcrj(String tdcrjid) {
        if(StringUtils.isNotBlank(tdcrjid)){
            entityMapper.deleteByPrimaryKey(BdcSlTdcrjDO.class,tdcrjid);
        }
    }
}
