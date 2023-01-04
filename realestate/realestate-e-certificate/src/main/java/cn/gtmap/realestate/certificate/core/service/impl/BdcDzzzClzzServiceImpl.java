package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.service.BdcDzzzClzzService;
import cn.gtmap.realestate.common.core.domain.electronic.BdcDzzzClzzDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BdcDzzzClzzServiceImpl implements BdcDzzzClzzService {

    @Autowired
    private EntityMapper entityMapper;

    @Override
    public int saveOrUpdateClzz(BdcDzzzClzzDO bdcDzzzClzzDO) {
        Example example = new Example(BdcDzzzClzzDO.class);
        example.createCriteria().andEqualTo("bdcqzh", bdcDzzzClzzDO.getBdcqzh());
        List<BdcDzzzClzzDO> bdcDzzzClzzDOS = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(bdcDzzzClzzDOS) && bdcDzzzClzzDOS.get(0) != null) {
            int flag = 0;
            for (BdcDzzzClzzDO dzzzClzzDO : bdcDzzzClzzDOS) {
                bdcDzzzClzzDO.setId(dzzzClzzDO.getId());
                flag += entityMapper.saveOrUpdate(bdcDzzzClzzDO, bdcDzzzClzzDO.getId());
            }
            return flag;
        }
        bdcDzzzClzzDO.setId(UUIDGenerator.generate16());
        return entityMapper.saveOrUpdate(bdcDzzzClzzDO, bdcDzzzClzzDO.getId());
    }
}
