package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlSfxmJmzcGxService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmJmzcGxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @program: realestate
 * @description: 收费项目减免政策关系实现服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-09 17:23
 **/
@Service
public class BdcSlSfxmJmzcGxServiceImpl implements BdcSlSfxmJmzcGxService {
    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param jmzt   减免状态
     * @param jmzcbz 减免政策标志
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/9/9 17:25
     */
    @Override
    public List<BdcSlSfxmJmzcGxDO> listSlSfxmJmzcGx(Integer jmzt, String jmzcbz) {
        Example example = new Example(BdcSlSfxmJmzcGxDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(jmzt)) {
            criteria.andEqualTo("jmzt", jmzt);
        }
        if (StringUtils.isNotBlank(jmzcbz)) {
            criteria.andEqualTo("jmzcbz", jmzcbz);
        }
        return entityMapper.selectByExample(example);
    }
}
