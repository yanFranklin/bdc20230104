package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.ZdService;
import cn.gtmap.realestate.building.core.service.ZdZjdxxService;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.common.core.domain.building.ZdZjdxxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/13
 * @description
 */
@Service
public class ZdZjdxxServiceImpl implements ZdZjdxxService {

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    ZdService zdService;
    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZdZjdxxDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据bdcdyh查询宅基地信息
     */
    @Override
    public List<ZdZjdxxDO> listZjdxxByBdcdy(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            String djh = BuildingUtils.getDjhByBdcdyh(bdcdyh);
            Example example = new Example(ZdZjdxxDO.class);
            example.createCriteria().andEqualTo("djh",djh);
            return entityMapper.selectByExample(example);
        }
        return new ArrayList<>(0);
    }
}
