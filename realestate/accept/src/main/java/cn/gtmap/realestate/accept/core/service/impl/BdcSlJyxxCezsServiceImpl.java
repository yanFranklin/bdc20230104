package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlJyxxCezsService;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxCezsDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/5/13
 * @description 不动产受理交易差额征收信息接口实现类
 */
@Service
public class BdcSlJyxxCezsServiceImpl implements BdcSlJyxxCezsService {

    @Autowired
    private EntityMapper entityMapper;

    @Override
    public BdcSlJyxxCezsDO saveBdcSlJyxx(BdcSlJyxxCezsDO bdcSlJyxxCezsDO) {
        if (StringUtils.isNotBlank(bdcSlJyxxCezsDO.getCezsid())) {
            entityMapper.updateByPrimaryKeySelective(bdcSlJyxxCezsDO);
        } else {
            bdcSlJyxxCezsDO.setCezsid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcSlJyxxCezsDO);
        }
        return bdcSlJyxxCezsDO;
    }

    @Override
    public List<BdcSlJyxxCezsDO> listBdcSlJyxxCezsByXmid(String xmid) {
        List<BdcSlJyxxCezsDO> bdcSlJyxxCezsDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlJyxxCezsDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            bdcSlJyxxCezsDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlJyxxCezsDOList)) {
            bdcSlJyxxCezsDOList = Collections.emptyList();
        }
        return bdcSlJyxxCezsDOList;
    }

}
