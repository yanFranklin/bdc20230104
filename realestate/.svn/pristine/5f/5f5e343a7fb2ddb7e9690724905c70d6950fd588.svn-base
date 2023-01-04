package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.mapper.ZdJzwsuqgydcMapper;
import cn.gtmap.realestate.building.core.service.ZdJzwsuqgydcService;
import cn.gtmap.realestate.common.core.domain.building.HZdJzwsuqgydcDO;
import cn.gtmap.realestate.common.core.domain.building.ZdJzwsuqgydcDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-22
 * @description 宗地建筑物所有权共有调查服务
 */
@Service
public class ZdJzwsuqgydcServiceImpl implements ZdJzwsuqgydcService {

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private ZdJzwsuqgydcMapper zdJzwsuqgydcMapper;

    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZdJzwsuqgydcDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH查询宗地建筑物共有列表
     */
    @Override
    public List<ZdJzwsuqgydcDO> listZdJzwgyByDjh(String djh) {
        if(StringUtils.isNotBlank(djh)){
            Example example = new Example(ZdJzwsuqgydcDO.class);
            example.createCriteria().andEqualTo("djh",djh);
            return entityMapper.selectByExample(example);
        }
        return new ArrayList<>(0);
    }

    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZdJzwsuqgydcDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH查询 备份宗地建筑物共有列表
     */
    @Override
    public List<HZdJzwsuqgydcDO> listHZdJzwgyByDjh(String djh) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("djh",djh);
        return zdJzwsuqgydcMapper.listLastBgGydcByDjh(paramMap);
    }
}
