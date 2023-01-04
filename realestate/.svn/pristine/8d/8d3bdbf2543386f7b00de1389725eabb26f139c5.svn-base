package cn.gtmap.realestate.init.service.other.impl;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.init.service.other.InitFwKgService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/6
 * @description
 */
@Service
public class InitFwKgServiceImpl implements InitFwKgService {
    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param djxl
     * @return bdcCshFwKgDO
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 根据业务类型获取该流程初始化服务的开关信息
     */
    @Override
    public BdcCshFwkgDO queryBdcCshFwKgDO(String djxl) {
        BdcCshFwkgDO bdcCshFwkgDO = null;
        if (StringUtils.isNotBlank(djxl)) {
            Example example = new Example(BdcCshFwkgDO.class);
            example.createCriteria().andEqualTo("djxl", djxl);
            List<BdcCshFwkgDO> bdcCshFwkgDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcCshFwkgDOList)) {
                bdcCshFwkgDO = bdcCshFwkgDOList.get(0);
            }
        }
        return bdcCshFwkgDO;
    }
}
