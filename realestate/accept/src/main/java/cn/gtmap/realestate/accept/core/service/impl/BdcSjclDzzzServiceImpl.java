package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSjclDzzzService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.accept.BdcSjclDzzzDzDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @program: realestate
 * @description: 收件材料电子证照实现方法
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-01-02 15:13
 **/
@Service
public class BdcSjclDzzzServiceImpl implements BdcSjclDzzzService {

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;

    /**
     * @param bdcSjclDzzzDzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据收件材料名称获取配置表中的数据
     * @date : 2020/1/2 14:55
     */
    @Override
    public List<BdcSjclDzzzDzDO> querySjclDzzzDzByClmc(BdcSjclDzzzDzDO bdcSjclDzzzDzDO) {
        Example example = new Example(BdcSjclDzzzDzDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(bdcSjclDzzzDzDO.getClmc())) {
            criteria.andEqualTo("clmc", bdcSjclDzzzDzDO.getClmc());
        }
        if (StringUtils.isNotBlank(bdcSjclDzzzDzDO.getDzzzdm())) {
            criteria.andEqualTo("dzzzdm", bdcSjclDzzzDzDO.getDzzzdm());
        }
        if (StringUtils.isNotBlank(bdcSjclDzzzDzDO.getDzzzmc())) {
            criteria.andEqualTo("dzzzmc", bdcSjclDzzzDzDO.getDzzzmc());
        }
        List<BdcSjclDzzzDzDO> bdcSjclDzzzDzDOList = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(bdcSjclDzzzDzDOList)) {
            return bdcSjclDzzzDzDOList;
        }
        return Collections.emptyList();
    }

    /**
     * @param bdcSjclDzzzDzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增收件材料电子证照对照关系
     * @date : 2020/1/2 15:19
     */
    @Override
    public int insertBdcSjclDzzzDzDO(BdcSjclDzzzDzDO bdcSjclDzzzDzDO) {
        if (bdcSjclDzzzDzDO == null) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        if (StringUtils.isBlank(bdcSjclDzzzDzDO.getDzid())) {
            bdcSjclDzzzDzDO.setDzid(UUIDGenerator.generate16());
        }
        return entityMapper.insertSelective(bdcSjclDzzzDzDO);
    }

    /**
     * @param bdcSjclDzzzDzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新收件材料电子证照对照关系
     * @date : 2020/1/2 15:35
     */
    @Override
    public int updateBdcSjclDzzzDzDO(BdcSjclDzzzDzDO bdcSjclDzzzDzDO) {
        if (bdcSjclDzzzDzDO != null && StringUtils.isNotBlank(bdcSjclDzzzDzDO.getDzid())) {
            return entityMapper.updateByPrimaryKeySelective(bdcSjclDzzzDzDO);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
    }
}
