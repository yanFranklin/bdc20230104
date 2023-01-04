package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcDjyyDyfsGxDO;
import cn.gtmap.realestate.common.core.domain.BdcGgDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.init.core.service.BdcDjyyDyfsGxService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/08/02
 * @Description:
 */
@Service
public class BdcDjyyDyfsGxServiceImpl implements BdcDjyyDyfsGxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDjyyDyfsGxServiceImpl.class);

    @Autowired
    EntityMapper entityMapper;

    @Override
    public List<BdcDjyyDyfsGxDO> listBdcDjyyDyfsGx(BdcDjyyDyfsGxDO bdcDjyyDyfsGxDO) {
        if (StringUtils.isNotBlank(bdcDjyyDyfsGxDO.getDjyy())) {
            Example example = new Example(BdcDjyyDyfsGxDO.class);
            example.createCriteria().andEqualTo("djyy", bdcDjyyDyfsGxDO.getDjyy());
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }
}
