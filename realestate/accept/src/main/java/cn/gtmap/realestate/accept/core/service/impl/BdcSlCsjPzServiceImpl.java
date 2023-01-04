package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlCsjpzService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlCsjpzDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: realestate
 * @description: 长三角配置方法实现
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-05-10 14:09
 **/
@Service
public class BdcSlCsjPzServiceImpl implements BdcSlCsjpzService {

    @Autowired
    EntityMapper entityMapper;

    /**
     * @param bdcSlCsjpzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询服务
     * @date : 2022/5/10 14:10
     */
    @Override
    public List<BdcSlCsjpzDO> listCsjpz(BdcSlCsjpzDO bdcSlCsjpzDO) {
        if (StringToolUtils.isAllNullOrEmpty(bdcSlCsjpzDO.getLcmc(), bdcSlCsjpzDO.getGzldyid(), bdcSlCsjpzDO.getPzid(), bdcSlCsjpzDO.getZzlx())) {
            throw new AppException("至少需要一个查询条件");
        }
        return entityMapper.selectByObj(bdcSlCsjpzDO);
    }

    /**
     * @param pzid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除服务
     * @date : 2022/5/10 14:10
     */
    @Override
    public int deletCsjpz(String pzid) {
        if (StringUtils.isNotBlank(pzid)) {
            return entityMapper.deleteByPrimaryKey(BdcSlCsjpzDO.class, pzid);
        }
        return 0;
    }

    /**
     * @param bdcSlCsjpzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存服务
     * @date : 2022/5/10 14:11
     */
    @Override
    public BdcSlCsjpzDO saveCsjpz(BdcSlCsjpzDO bdcSlCsjpzDO) {
        if (StringUtils.isBlank(bdcSlCsjpzDO.getPzid())) {
            bdcSlCsjpzDO.setPzid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcSlCsjpzDO);
        } else {
            entityMapper.updateByPrimaryKey(bdcSlCsjpzDO);
        }
        return bdcSlCsjpzDO;
    }
}
