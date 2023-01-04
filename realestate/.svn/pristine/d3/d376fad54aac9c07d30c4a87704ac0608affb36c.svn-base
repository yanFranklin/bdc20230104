package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.GzwDcbService;
import cn.gtmap.realestate.common.core.domain.building.GzwDcbDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-22
 * @description 构筑物相关服务
 */
@Service
public class GzwDcbServiceImpl implements GzwDcbService {

    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.GzwDcbDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询构筑物 基本信息
     */
    @Override
    public GzwDcbDO getGzwDcbDOByBdcdyh(String bdcdyh) {
        if(StringUtils.isNotBlank(bdcdyh)){
            Example example = new Example(GzwDcbDO.class);
            example.createCriteria().andEqualTo("bdcdyh",bdcdyh);
            List<GzwDcbDO> gzwDcbDOList = entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(gzwDcbDOList)){
                return gzwDcbDOList.get(0);
            }
        }
        return null;
    }


}
