package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.mapper.JyqDkMapper;
import cn.gtmap.realestate.building.core.service.JyqDkQlrService;
import cn.gtmap.realestate.common.core.domain.building.JyqDkLcfDO;
import cn.gtmap.realestate.common.core.domain.building.JyqDkQlrDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2020-11-6
 * @description 经营权地块权利人服务
 */
@Service
public class JyqDkQlrServiceImpl implements JyqDkQlrService {

    @Autowired
    private JyqDkMapper jyqDkMapper;

    @Autowired
    private EntityMapper entityMapper;


    @Override
    public List<JyqDkQlrDO> listJyqDkQlrByDjdcbIndex(String jyqDkDcbIndex) {
        if(StringUtils.isNotBlank(jyqDkDcbIndex)) {
            return jyqDkMapper.listJyqDkQlrByDjdcbIndex(jyqDkDcbIndex);
        }else{
            return Collections.emptyList();
        }
    }

    @Override
    public JyqDkLcfDO queryJyqDkLcfByJyqDkLcfIndex(String jyqdklcfIndex){
        if(StringUtils.isNotBlank(jyqdklcfIndex)){
            return entityMapper.selectByPrimaryKey(JyqDkLcfDO.class,jyqdklcfIndex);
        }
        return null;

    }


}


