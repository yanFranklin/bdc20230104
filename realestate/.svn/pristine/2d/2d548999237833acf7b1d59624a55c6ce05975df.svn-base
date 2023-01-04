package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcQlrtzMrzDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.init.core.service.BdcQlrtzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/8/30
 * @description 权利人特征服务
 */
@Service
public class BdcQlrtzServiceImpl implements BdcQlrtzService {

    @Autowired
    private EntityMapper entityMapper;

    @Override
    public Integer getQlrtzMrz(Integer qllx,String qlrlb){
        if(qllx ==null ||StringUtils.isBlank(qlrlb)){
            return null;
        }
        Example example =new Example(BdcQlrtzMrzDO.class);
        example.createCriteria().andEqualTo("qllx",qllx).andEqualTo("qlrlb",qlrlb);
        List<BdcQlrtzMrzDO> qlrtzMrzDOList =entityMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(qlrtzMrzDOList)){
            return qlrtzMrzDOList.get(0).getQlrtz();
        }
        return null;
    }
}
