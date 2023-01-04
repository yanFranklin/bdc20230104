package cn.gtmap.realestate.config.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXxbdPzDO;
import cn.gtmap.realestate.common.core.domain.BdcXxbdSjPzDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.config.service.BdcXxbdPzService;
import cn.gtmap.realestate.config.service.BdcXxbdService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/28
 * @description 信息比对配置服务
 */
@Service
public class BdcXxbdPzServiceImpl implements BdcXxbdPzService {

    @Autowired
    private EntityMapper entityMapper;

    @Override
    public BdcXxbdPzDO queryBdcXxbdPzByBdlx(String bdlx){
        if(StringUtils.isNotBlank(bdlx)){
            Example example =new Example(BdcXxbdPzDO.class);
            example.createCriteria().andEqualTo("bdlx",bdlx);
            List<BdcXxbdPzDO> bdcXxbdPzDOList =entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(bdcXxbdPzDOList)){
                return bdcXxbdPzDOList.get(0);
            }
        }
        return null;

    }

    @Override
    public List<BdcXxbdSjPzDO> listBdcXxbdPzByBdlx(String bdlx){
        if(StringUtils.isNotBlank(bdlx)){
            Example example =new Example(BdcXxbdSjPzDO.class);
            example.createCriteria().andEqualTo("bdlx",bdlx);
            example.setOrderByClause("sxh");
            List<BdcXxbdSjPzDO> bdcXxbdSjPzDOList =entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(bdcXxbdSjPzDOList)){
                return bdcXxbdSjPzDOList;
            }
        }
        return new ArrayList<>();
    }
}
