package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.ZdJsydsybService;
import cn.gtmap.realestate.common.core.domain.building.ZdJsydsybDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/22
 * @description 宗地建设用地使用表服务
 */
@Service
public class ZdJsydsybServiceImpl implements ZdJsydsybService {

    @Autowired
    private EntityMapper entityMapper;

    @Override
    public ZdJsydsybDO queryZdJsydsybByDjh(String djh){
        ZdJsydsybDO zdJsydsybDO =null;
        if(StringUtils.isNotBlank(djh)){
            Example example =new Example(ZdJsydsybDO.class);
            example.createCriteria().andEqualTo("djh",djh);
            List<ZdJsydsybDO> zdJsydsybDOList=entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(zdJsydsybDOList)){
                zdJsydsybDO =zdJsydsybDOList.get(0);
            }
        }
        return zdJsydsybDO;

    }
}
