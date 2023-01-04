package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.mapper.BdcSlHsxxMapper;
import cn.gtmap.realestate.accept.core.service.BdcSlSysService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSysxxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/12/13
 * @description 三要素服务
 */
@Service
public class BdcSlSysServiceImpl implements BdcSlSysService {

    @Autowired
    private BdcSlHsxxMapper bdcSlHsxxMapper;

    @Autowired
    private EntityMapper entityMapper;

    @Override
    public List<BdcSlSysxxDO> listBdcSlSysxx(String hsxxid, String slbh, String gzlslid){
        if(StringUtils.isNotBlank(hsxxid) ||StringUtils.isNotBlank(slbh) ||StringUtils.isNotBlank(gzlslid)){
            return bdcSlHsxxMapper.listBdcSlSysxx(hsxxid, slbh, gzlslid);
        }
        return new ArrayList<>();

    }

    @Override
    public BdcSlSysxxDO queryBdcSlSysxxByDzsphm(String dzsphm){
        if(StringUtils.isNotBlank(dzsphm)){
            Example example =new Example(BdcSlSysxxDO.class);
            example.createCriteria().andEqualTo("dzsphm",dzsphm);
            List<BdcSlSysxxDO> bdcSlSysxxDOList =entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(bdcSlSysxxDOList)){
                return bdcSlSysxxDOList.get(0);
            }
        }
        return null;

    }

    @Override
    public int updateBdcSlSysxx(String dzsphm, BdcSlSysxxDO bdcSlSysxxDO) {
        if(StringUtils.isBlank(dzsphm)){
            throw new MissingArgumentException("缺失参数电子税票号码");
        }
        Example example = new Example(BdcSlSysxxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dzsphm", dzsphm);
        return entityMapper.updateByExampleSelectiveNotNull(bdcSlSysxxDO, example);
    }
}
