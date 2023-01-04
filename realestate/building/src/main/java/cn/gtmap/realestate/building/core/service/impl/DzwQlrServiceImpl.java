package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.DzwQlrService;
import cn.gtmap.realestate.common.core.domain.building.DzwQlrDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-25
 * @description 定着物权利人服务
 */
@Service
public class DzwQlrServiceImpl implements DzwQlrService {


    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param dzwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.DzwQlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据定着物主键查询定作物权利人
     */
    @Override
    public List<DzwQlrDO> listDzwQlrByDcbIndex(String dzwDcbIndex) {
        if(StringUtils.isNotBlank(dzwDcbIndex)){
            Example example = new Example(DzwQlrDO.class);
            example.createCriteria().andEqualTo("dzwDcbIndex",dzwDcbIndex);
            return entityMapper.selectByExample(example);
        }
        return new ArrayList<>(0);
    }
}
