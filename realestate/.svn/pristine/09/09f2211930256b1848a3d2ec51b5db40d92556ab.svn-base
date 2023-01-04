package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.GzwQlrService;
import cn.gtmap.realestate.common.core.domain.building.GzwQlrDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-22
 * @description 构筑物权利人服务
 */
@Service
public class GzwQlrServiceImpl implements GzwQlrService {

    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param gzwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.GzwQlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据构筑物INDEX查询构筑物权利人
     */
    @Override
    public List<GzwQlrDO> listGzwQlrByDcbIndex(String gzwDcbIndex) {
        if(StringUtils.isNotBlank(gzwDcbIndex)){
            Example example = new Example(GzwQlrDO.class);
            example.createCriteria().andEqualTo("gzwDcbIndex",gzwDcbIndex);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }
}
