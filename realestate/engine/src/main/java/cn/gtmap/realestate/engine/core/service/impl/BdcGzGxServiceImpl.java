package cn.gtmap.realestate.engine.core.service.impl;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzGxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.engine.core.service.BdcGzGxService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/6
 * @description 不动产组合关系服务实现
 */
@Service
public class BdcGzGxServiceImpl implements BdcGzGxService {
    @Autowired
    private EntityMapper entityMapper;
    /**
     * @param zhid
     * @return bdcGzGxDOList
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 通过zhid获取bdcGzGxList
     */
    @Override
    public List<BdcGzGxDO> queryBdcGzGxListByZhid(String zhid) {
        Example example = new Example(BdcGzGxDO.class);
        example.createCriteria().andEqualTo("zhid",zhid);
        return entityMapper.selectByExample(example);
    }

    /**
     * @param gzid
     * @return bdcGzGxDOList
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 通过gzid获取所有组合关系
     */
    @Override
    public List<BdcGzGxDO> queryBdcGzGxListByGzid(String gzid) {
        Example example = new Example(BdcGzGxDO.class);
        example.createCriteria().andEqualTo("gzid",gzid);
        return entityMapper.selectByExample(example);
    }

    /**
     * @param bdcGzGxDO
     * @return bdcGzGxDO
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 新增bdcGzGxDO记录
     */
    @Override
    public BdcGzGxDO insertBdcGzGx(BdcGzGxDO bdcGzGxDO) {
        if (bdcGzGxDO != null) {
            if (StringUtils.isBlank(bdcGzGxDO.getGxid())) {
                bdcGzGxDO.setGxid(UUIDGenerator.generate());
            }
            entityMapper.insertSelective(bdcGzGxDO);
        }
        return bdcGzGxDO;
    }

    /**
     * @param zhid
     * @return num
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 根据zhid批量删除组合关系
     */
    @Override
    public int delBdcGzGxByZhid(String zhid) {
        int result = 0;
        if (StringUtils.isNotBlank(zhid)) {
            Example example = new Example(BdcGzGxDO.class);
            example.createCriteria().andEqualTo("zhid", zhid);
            result = entityMapper.deleteByExample(example);
        }
        return result;
    }

    /**
     *@author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu2</a>
     *@param  gzid
     *@return num 删除的记录数
     *@description 通过gzid删除规则关系
     */
    @Override
    public int delBdcGzGxByGzid(String gzid) {
        int result = 0;
        if (StringUtils.isNotBlank(gzid)) {
            Example example = new Example(BdcGzGxDO.class);
            example.createCriteria().andEqualTo("gzid", gzid);
            result = entityMapper.deleteByExample(example);
        }
        return result;
    }

    /**
     * @param gxid
     * @return num
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 根据zhid删除组合关系
     */
    @Override
    public int delBdcGzGxByGxid(String gxid) {
        int result = 0;
        if (StringUtils.isNotBlank(gxid)) {
            result = entityMapper.deleteByPrimaryKey(BdcGzGxDO.class, gxid);
        }
        return result;
    }
}
