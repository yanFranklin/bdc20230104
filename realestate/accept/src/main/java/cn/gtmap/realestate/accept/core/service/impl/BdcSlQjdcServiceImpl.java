package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlQjdcService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlQjdcsqDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 权籍调查服务实现层
 * @date : 2021/8/5 17:27
 */
@Service
public class BdcSlQjdcServiceImpl implements BdcSlQjdcService {

    @Autowired
    EntityMapper entityMapper;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询权籍调查数据
     * @date : 2021/8/5 13:40
     */
    @Override
    public List<BdcSlQjdcsqDO> listSlQjdc(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcSlQjdcsqDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id删除权籍调查数据
     * @date : 2021/8/5 13:41
     */
    @Override
    public int deleteSlQjdc(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcSlQjdcsqDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            return entityMapper.deleteByExample(example);
        }
        return 0;
    }

    /**
     * @param bdcSlQjdcsqDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增权籍调查数据
     * @date : 2021/8/5 13:41
     */
    @Override
    public BdcSlQjdcsqDO saveSlQjdc(BdcSlQjdcsqDO bdcSlQjdcsqDO) {
        if (StringUtils.isBlank(bdcSlQjdcsqDO.getQjdcsqxxid())) {
            bdcSlQjdcsqDO.setQjdcsqxxid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcSlQjdcsqDO);
        } else {
            entityMapper.saveOrUpdate(bdcSlQjdcsqDO, bdcSlQjdcsqDO.getQjdcsqxxid());
        }
        return bdcSlQjdcsqDO;
    }
}
