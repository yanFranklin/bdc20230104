package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlJcggService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJcggDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @program: realestate
 * @description: 受理继承公告方法实现
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-12-01 20:34
 **/
@Service
public class BdcSlJcggServiceImpl implements BdcSlJcggService {
    @Autowired
    EntityMapper entityMapper;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询继承公告信息
     * @date : 2021/12/1 20:21
     */
    @Override
    public List<BdcSlJcggDO> listBdcSlJcgg(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcSlJcggDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            return entityMapper.selectByExampleNotNull(example);
        }
        return Collections.emptyList();
    }

    /**
     * @param bdcSlJcggDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存继承公告信息
     * @date : 2021/12/1 20:23
     */
    @Override
    public BdcSlJcggDO saveBdcSlJcgg(BdcSlJcggDO bdcSlJcggDO) {
        if (StringUtils.isBlank(bdcSlJcggDO.getGgid())) {
            bdcSlJcggDO.setGgid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcSlJcggDO);
        } else {
            entityMapper.updateByPrimaryKeySelective(bdcSlJcggDO);
        }
        return bdcSlJcggDO;
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除继承公告数据
     * @date : 2021/12/1 20:25
     */
    @Override
    public void deleteBdcSlJcgg(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcSlJcggDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            entityMapper.deleteByExampleNotNull(example);
        }
    }
}
