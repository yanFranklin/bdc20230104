package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXtMryjDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.register.service.BdcXtMryjService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/29
 * @description 默认意见业务类
 */
@Service
public class BdcXtMryjServiceImpl implements BdcXtMryjService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    UserManagerUtils userManagerUtils;

    /**
     * @param bdcXtMryjDO
     * @return List<BdcXtMryjDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取默认意见列表
     */
    @Override
    public List<BdcXtMryjDO> listBdcXtMryj(BdcXtMryjDO bdcXtMryjDO) {
        if (bdcXtMryjDO == null) {
            throw new MissingArgumentException("");
        }
        if (StringUtils.isEmpty(bdcXtMryjDO.getCjrid())) {
            bdcXtMryjDO.setCjrid(userManagerUtils.getCurrentUser().getId());
        }
        if (bdcXtMryjDO.getSfky() == null) {
            bdcXtMryjDO.setSfky(1);
        }
        return entityMapper.select(bdcXtMryjDO);
    }

    /**
     * @param cjrid@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据创建人获取可选意见
     */
    @Override
    public List<BdcXtMryjDO> listBdcXtKxyj(String cjrid, String gzldyKey, String jdmc) {
        if (StringUtils.isBlank(gzldyKey) || StringUtils.isBlank(jdmc)) {
            throw new MissingArgumentException("缺少参数：节点名称，工作流定义key");
        }
        Example example = new Example(BdcXtMryjDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(cjrid)) {
            criteria.andEqualTo("cjrid", StringUtils.trim(cjrid));
        }
        criteria.andEqualTo("gzldyid", StringUtils.trim(gzldyKey));
        criteria.andEqualTo("jdmc", StringUtils.trim(jdmc));
        criteria.andEqualTo("yjlx",  CommonConstantUtils.YJLX_KXYJ);
        criteria.andEqualTo("sfky",  CommonConstantUtils.SF_S_DM);
        return entityMapper.selectByExampleNotNull(example);
    }

    /**
     * @param cjrid
     * @param gzldyKey
     * @param jdmc
     * @return BdcXtMryjDO
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询系统配置默认意见
     */
    @Override
    public BdcXtMryjDO queryBdcXtMryj(String cjrid, String gzldyKey, String jdmc) {
        if (StringUtils.isEmpty(gzldyKey) || StringUtils.isEmpty(jdmc)) {
            throw new MissingArgumentException("gzldyKey,jdmc");
        }
        Example example = new Example(BdcXtMryjDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(cjrid)) {
            criteria.andEqualTo("cjrid", StringUtils.trim(cjrid));
        }
        criteria.andEqualTo("gzldyid", StringUtils.trim(gzldyKey));
        criteria.andEqualTo("jdmc", StringUtils.trim(jdmc));
        criteria.andEqualTo("yjlx", CommonConstantUtils.YJLX_MRYJ);
        criteria.andEqualTo("sfky", CommonConstantUtils.SF_S_DM);
        List<BdcXtMryjDO> mryjDOS = entityMapper.selectByExampleNotNull(example);
        if(CollectionUtils.isEmpty(mryjDOS)){
            return null;
        }
        if (CollectionUtils.size(mryjDOS) > 1) {
            throw new AppException("默认意见不能为多条");
        }
        return mryjDOS.get(0);
    }
}
