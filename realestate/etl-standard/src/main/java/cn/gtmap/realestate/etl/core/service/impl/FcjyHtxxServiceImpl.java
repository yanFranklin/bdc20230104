package cn.gtmap.realestate.etl.core.service.impl;

import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.etl.core.domian.ClfHtbaDo;
import cn.gtmap.realestate.etl.core.domian.SpfHtbaDo;
import cn.gtmap.realestate.etl.core.service.FcjyHtxxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2020/5/12
 * @description
 */
@Service
public class FcjyHtxxServiceImpl implements FcjyHtxxService {
    @Autowired(required = false)
    @Qualifier("fcjyEntityMapper")
    private EntityMapper fcjyEntityMapper;

    @Override
    public ClfHtbaDo queryClfHtbaDoByHtbh(String htbh,String yjybh,String fwbm) {
        if (StringUtils.isNotBlank(htbh) ||StringUtils.isNotBlank(yjybh) ||StringUtils.isNotBlank(fwbm)) {
            Example example = new Example(ClfHtbaDo.class);
            if(StringUtils.isNotBlank(htbh)) {
                example.createCriteria().andEqualTo("htbh", htbh);
            }else if(StringUtils.isNotBlank(yjybh)){
                example.createCriteria().andEqualTo("yjybh", yjybh);
            }else{
                example.createCriteria().andEqualTo("fwbm", fwbm);
            }
            example.setOrderByClause("barq DESC");
            List<ClfHtbaDo> clfHtbaDos = fcjyEntityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(clfHtbaDos)) {
                return clfHtbaDos.get(0);
            }
        }
        return null;
    }

    @Override
    public SpfHtbaDo querySpfHtbaDoByHtbh(String htbh,String fwbm) {
        if (StringUtils.isNotBlank(htbh) ||StringUtils.isNotBlank(fwbm)) {
            Example example = new Example(SpfHtbaDo.class);
            if(StringUtils.isNotBlank(htbh)) {
                example.createCriteria().andEqualTo("htbh", htbh);
            }else{
                example.createCriteria().andEqualTo("fwbm", fwbm);
            }
            List<SpfHtbaDo> spfHtbaDoList = fcjyEntityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(spfHtbaDoList)) {
                return spfHtbaDoList.get(0);
            }
        }
        return null;
    }
}
