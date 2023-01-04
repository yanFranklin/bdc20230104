package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.ZdQlrService;
import cn.gtmap.realestate.building.core.service.ZdService;
import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.common.core.domain.building.HZdQlrDO;
import cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.ZdQlrDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/13
 * @description
 */
@Service
public class ZdQlrServiceImpl implements ZdQlrService {

    @Autowired
    private ZdService zdService;

    @Autowired
    private BdcdyService bdcdyService;

    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZdQlrDO>
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据bdcdyh查询宗地权利人
     */
    @Override
    public List<ZdQlrDO> listZdQlrByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            ZdDjdcbDO zdDjdcbDO = zdService.queryZdDjdcbByBdcdyh(bdcdyh);
            if (zdDjdcbDO != null && StringUtils.isNotBlank(zdDjdcbDO.getZdDjdcbIndex())) {
                return bdcdyService.listQlrByDjDcbIndex(zdDjdcbDO.getZdDjdcbIndex(), ZdQlrDO.class);
            }
        }
        return new ArrayList<>(0);
    }

    /**
     * @param djdcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZdQlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据调查表主键查询权利人
     */
    @Override
    public List<ZdQlrDO> listZdQlrByDjdcbIndex(String djdcbIndex) {
        return bdcdyService.listQlrByDjDcbIndex(djdcbIndex, ZdQlrDO.class);
    }

    /**
     * @param djdcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZdQlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据调查表主键查询备份权利人
     */
    @Override
    public List<HZdQlrDO> listHZdQlrByDjdcbIndex(String djdcbIndex) {
        return bdcdyService.listQlrByDjDcbIndex(djdcbIndex, HZdQlrDO.class);
    }

    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZdQlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH查询宗地权利人
     */
    @Override
    public List<ZdQlrDO> listZdQlrByDjh(String djh) {
        if(StringUtils.isNotBlank(djh)){
            Example example = new Example(ZdQlrDO.class);
            example.createCriteria().andEqualTo("djh", djh);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }

    @Override
    public List<ZdQlrDO> listZdQlrByDjhs(List<Object> djhs) {
        if (CollectionUtils.isNotEmpty(djhs)) {
            Example example = new Example(ZdQlrDO.class);
            example.createCriteria().andIn("djh", djhs);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }
}