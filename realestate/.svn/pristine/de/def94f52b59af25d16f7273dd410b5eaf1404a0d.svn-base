package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcYjSfDdxxService;
import cn.gtmap.realestate.common.core.domain.accept.BdcYjSfDdxxDO;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/10
 * @description 不动产银行月结单号与受理编号关系服务
 */
@Service
public class BdcYjSfDdxxServiceImpl implements BdcYjSfDdxxService {

    @Autowired
    private EntityMapper entityMapper;

    @Override
    public int insertYjSfDdxx(BdcYjSfDdxxDO bdcYjSfDdxxDO) {
        int result = 0;
        if(Objects.nonNull(bdcYjSfDdxxDO)){
            bdcYjSfDdxxDO.setId(UUIDGenerator.generate16());
            result =entityMapper.insertSelective(bdcYjSfDdxxDO);
        }
        return result;
    }

    @Override
    public int updateYjSfDdxxByYjdh(BdcYjSfDdxxDO bdcYjSfDdxxDO) {
        int result = 0;
        if (bdcYjSfDdxxDO != null && StringUtils.isNotBlank(bdcYjSfDdxxDO.getYjdh())) {
            Example example= new Example(BdcYjSfDdxxDO.class,false);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("yjdh",bdcYjSfDdxxDO.getYjdh());
            result = entityMapper.updateByExampleSelectiveNotNull(bdcYjSfDdxxDO, example);
        }
        return result;
    }

    @Override
    public void zfYjSfDdxxByYjdh(List<String> yjdhList) {
        if(CollectionUtils.isEmpty(yjdhList)){
            throw new MissingArgumentException("未获取到月结单号信息。");
        }
        for(String yjdh: yjdhList){
            BdcYjSfDdxxDO bdcYjSfDdxxDO = new BdcYjSfDdxxDO();
            bdcYjSfDdxxDO.setYjdh(yjdh);
            bdcYjSfDdxxDO.setDdzt(BdcSfztEnum.YZF.key());
            bdcYjSfDdxxDO.setZtxgsj(new Date());
            this.updateYjSfDdxxByYjdh(bdcYjSfDdxxDO);
        }
    }

    @Override
    public BdcYjSfDdxxDO queryBdcYjSfDdxxByYjdh(String yjdh) {
        if(StringUtils.isBlank(yjdh)){
            throw new MissingArgumentException("缺失参数月结单号。");
        }
        Example example= new Example(BdcYjSfDdxxDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("yjdh",yjdh);
        List<BdcYjSfDdxxDO> bdcYjSfDdxxDOList =  entityMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(bdcYjSfDdxxDOList)){
            return bdcYjSfDdxxDOList.get(0);
        }
        return null;
    }

}
