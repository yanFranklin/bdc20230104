package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.mapper.BdcCfbhMapper;
import cn.gtmap.realestate.init.core.service.BdcCfbhService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/30
 * @description 查封编号服务
 */
@Service
public class BdcCfbhServiceImpl implements BdcCfbhService {

    @Autowired
    BdcCfbhMapper bdcCfbhMapper;

    @Autowired
    EntityMapper entityMapper;

    @RedissonLock(lockKey = CommonConstantUtils.REDISSON_LOCK_CFBH, description = "获取查封编号", waitTime = 60L, leaseTime = 30L)
    @Override
    public String generateCfbh(String qlid){
        if(StringUtils.isBlank(qlid)){
            return StringUtils.EMPTY;
        }
        Integer maxlsh = bdcCfbhMapper.getMaxCfbh();
        final Integer lsh = (maxlsh == null ? 1 : (maxlsh + 1));
        String cfbh =String.format("%06d", lsh);
        BdcCfDO bdcCfDO =new BdcCfDO();
        bdcCfDO.setQlid(qlid);
        bdcCfDO.setCfbh(cfbh);
        entityMapper.updateByPrimaryKeySelective(bdcCfDO);
        return cfbh;
    }
}
