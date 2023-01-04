package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcYwblBahdDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.service.BdcYwblBahdService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @program: realestate
 * @description: 业务办理备案核定实现层
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-21 14:31
 **/
@Service
public class BdcYwblBahdServiceImpl implements BdcYwblBahdService {

    @Autowired
    EntityMapper entityMapper;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询备案核对信息
     * @date : 2021/7/21 14:28
     */
    @Override
    public List<BdcYwblBahdDO> listYwblBahd(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcYwblBahdDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }

    /**
     * @param bdcYwblBahdDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增备案核对信息
     * @date : 2021/7/21 14:29
     */
    @Override
    public BdcYwblBahdDO insertYwblBahd(BdcYwblBahdDO bdcYwblBahdDO) {
        if (StringUtils.isNotBlank(bdcYwblBahdDO.getBahdid())) {
            entityMapper.updateByPrimaryKey(bdcYwblBahdDO);
        } else {
            bdcYwblBahdDO.setBahdid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcYwblBahdDO);
        }
        return bdcYwblBahdDO;
    }
}
