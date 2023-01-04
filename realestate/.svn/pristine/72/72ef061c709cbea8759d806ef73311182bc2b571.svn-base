package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.ZdJtcyService;
import cn.gtmap.realestate.common.core.domain.building.ZdJtcyDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/11
 * @description 宗地家庭成员
 */
@Service
public class ZdJtcyServiceImpl implements ZdJtcyService {

    @Autowired
    private EntityMapper entityMapper;

    @Override
    public List<ZdJtcyDO> listZdJtcyByDjh(String djh){
        if(StringUtils.isNotBlank(djh)){
            Example example =new Example(ZdJtcyDO.class);
            example.createCriteria().andEqualTo("djh",djh);
            List<ZdJtcyDO> zdJtcyDOList=entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(zdJtcyDOList)){
                return zdJtcyDOList;
            }
        }
        return Collections.emptyList();

    }
}
