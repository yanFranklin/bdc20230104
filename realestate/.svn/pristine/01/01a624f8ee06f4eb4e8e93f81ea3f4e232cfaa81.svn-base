package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcDjlxDjxlGxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.service.BdcDjlxDjxlGxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/5/31
 * @description 登记类型、登记小类 关系业务类
 */
@Service
public class BdcDjlxDjxlGxServiceImpl implements BdcDjlxDjxlGxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDjlxDjxlGxServiceImpl.class);
    @Autowired
    EntityMapper entityMapper;
    /**
     * @param bdcDjlxDjxlGxDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询
     */
    @Override
    public List<BdcDjlxDjxlGxDO> listBdcDjlxDjxlGx(BdcDjlxDjxlGxDO bdcDjlxDjxlGxDO) {

        if (bdcDjlxDjxlGxDO == null) {
            LOGGER.debug("初始化子系统——获取 登记类型、登记小类 关系配置的参数为空！");
            throw new AppException("获取初始化服务开关配置的参数不能为空！");
        }
        return entityMapper.selectByObj(bdcDjlxDjxlGxDO);
    }

    /**
     * @param bdcDjlxDjxlGxDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增
     */
    @Override
    public int insertBdcDjlxDjxlGx(BdcDjlxDjxlGxDO bdcDjlxDjxlGxDO) {
        if (bdcDjlxDjxlGxDO == null) {
            LOGGER.debug("初始化子系统——新增登记类型、登记小类 关系配置的参数为空！");
            throw new AppException("新增登记类型、登记小类 关系配置不能为空！");
        }
        if(StringUtils.isBlank(bdcDjlxDjxlGxDO.getId())){
            bdcDjlxDjxlGxDO.setId(UUIDGenerator.generate16());
        }
        return entityMapper.saveOrUpdate(bdcDjlxDjxlGxDO,bdcDjlxDjxlGxDO.getId());
    }

    /**
     * @param bdcDjlxDjxlGxDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改
     */
    @Override
    public int updateBdcDjlxDjxlGx(BdcDjlxDjxlGxDO bdcDjlxDjxlGxDO) {
        if (bdcDjlxDjxlGxDO == null) {
            LOGGER.debug("初始化子系统——修改登记类型、登记小类 关系配置的参数为空！");
            throw new AppException("修改登记类型、登记小类 关系配置的参数不能为空！");
        }
        return entityMapper.updateByPrimaryKeySelective(bdcDjlxDjxlGxDO);
    }

    /**
     * @param bdcDjlxDjxlGxDOList
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除
     */
    @Override
    public int deleteBdcDjlxDjxlGx(List<BdcDjlxDjxlGxDO> bdcDjlxDjxlGxDOList) {
        if (CollectionUtils.isEmpty(bdcDjlxDjxlGxDOList)) {
            LOGGER.debug("初始化子系统——删除登记类型、登记小类 关系配置的参数为空！");
            throw new AppException("删除登记类型、登记小类 关系配置的参数不能为空！");
        }
        return bdcDjlxDjxlGxDOList.stream().filter(bdcDjlxDjxlGxDO -> StringUtils.isNotBlank(bdcDjlxDjxlGxDO.getId())).mapToInt(bdcDjlxDjxlGxDO -> entityMapper.deleteByPrimaryKey(BdcDjlxDjxlGxDO.class, bdcDjlxDjxlGxDO.getId())).sum();
    }
}
