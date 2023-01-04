package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlJrlwService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJrLwDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @program: bdcdj3.0
 * @description: 接入例外方法实现类
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-10-05 16:14
 **/
@Service
public class BdcSlJrlwServiceImpl implements BdcSlJrlwService {
    @Autowired
    EntityMapper entityMapper;

    /**
     * @param bdcSlJrLwDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增或者更新
     * @date : 2022/10/5 16:09
     */
    @Override
    public int saveOrUpdateJrlw(List<BdcSlJrLwDO> bdcSlJrLwDOList) {
        int count = 0;
        if (CollectionUtils.isNotEmpty(bdcSlJrLwDOList)) {
            for (BdcSlJrLwDO bdcSlJrLwDO : bdcSlJrLwDOList) {
                if (Objects.nonNull(bdcSlJrLwDO) && StringUtils.isNotBlank(bdcSlJrLwDO.getWxzid())) {
                    Example example = new Example(BdcSlJrLwDO.class);
                    example.createCriteria().andEqualTo("wxzid", bdcSlJrLwDO.getWxzid());
                    List<BdcSlJrLwDO> bdcSlJrLwList = entityMapper.selectByExampleNotNull(example);
                    if (CollectionUtils.isNotEmpty(bdcSlJrLwList)) {
                        bdcSlJrLwDO.setLwid(bdcSlJrLwList.get(0).getLwid());
                        count += entityMapper.updateByPrimaryKeySelective(bdcSlJrLwDO);
                    } else {
                        bdcSlJrLwDO.setLwid(UUIDGenerator.generate16());
                        count += entityMapper.insertSelective(bdcSlJrLwDO);
                    }
                }
            }
        }
        return count;
    }
}
