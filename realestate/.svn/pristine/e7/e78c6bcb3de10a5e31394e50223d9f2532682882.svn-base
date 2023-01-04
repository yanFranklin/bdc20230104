package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcYwblhzxxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.service.BdcYwblHzxxService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @program: realestate
 * @description: 业务办理核证信息实现层
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-21 09:47
 **/
@Service
public class BdcYwblHzxxServiceImpl implements BdcYwblHzxxService {

    @Autowired
    EntityMapper entityMapper;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询业务办理核证信息
     * @date : 2021/7/21 9:46
     */
    @Override
    public List<BdcYwblhzxxDO> listYwblHzxx(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcYwblhzxxDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }

    /**
     * @param bdcYwblhzxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增业务办理核证信息
     * @date : 2021/7/21 9:48
     */
    @Override
    public BdcYwblhzxxDO insertYwblHzxx(BdcYwblhzxxDO bdcYwblhzxxDO) {
        if (StringUtils.isNotBlank(bdcYwblhzxxDO.getHzxxid())) {
            entityMapper.updateByPrimaryKey(bdcYwblhzxxDO);
        } else {
            bdcYwblhzxxDO.setHzxxid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcYwblhzxxDO);
        }
        return bdcYwblhzxxDO;
    }
}
