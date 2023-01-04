package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcFwjsztckDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.service.BdcFwjsztCkService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @program: realestate
 * @description: 房屋建设状态查看实现类
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-20 14:01
 **/
@Service
public class BdcFwjsztCkServiceImpl implements BdcFwjsztCkService {
    @Autowired
    EntityMapper entityMapper;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 插叙房屋建设状态查看信息
     * @date : 2021/7/20 14:03
     */
    @Override
    public List<BdcFwjsztckDO> queryBdcFwjszt(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcFwjsztckDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }

    /**
     * @param bdcFwjsztckDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增房屋建设状态查看信息
     * @date : 2021/7/20 14:09
     */
    @Override
    public BdcFwjsztckDO insertFwjsztCk(BdcFwjsztckDO bdcFwjsztckDO) {
        if (StringUtils.isNotBlank(bdcFwjsztckDO.getWyckid())) {
            entityMapper.updateByPrimaryKey(bdcFwjsztckDO);
        } else {
            bdcFwjsztckDO.setWyckid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcFwjsztckDO);
        }
        return bdcFwjsztckDO;
    }
}
