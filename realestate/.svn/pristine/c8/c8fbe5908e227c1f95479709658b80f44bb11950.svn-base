package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcDjxlQllxGxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.service.BdcDjxlQllxGxService;
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
 * @description 登记类型 权利类型 关系业务类
 */
@Service
public class BdcDjxlQllxGxServiceImpl implements BdcDjxlQllxGxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDjxlQllxGxServiceImpl.class);
    @Autowired
    EntityMapper entityMapper;

    /**
     * @param bdcDjxlQllxGxDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询
     */
    @Override
    public List<BdcDjxlQllxGxDO> listBdcDjxlQllxGx(BdcDjxlQllxGxDO bdcDjxlQllxGxDO) {
        if (bdcDjxlQllxGxDO == null) {
            LOGGER.debug("初始化子系统——获取 登记类型 权利类型 关系配置的参数为空！");
            throw new AppException("获取登记类型 权利类型 关系配置的参数不能为空！");
        }
        return entityMapper.selectByObj(bdcDjxlQllxGxDO);
    }

    /**
     * @param bdcDjxlQllxGxDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增
     */
    @Override
    public int insertBdcDjxlQllxGx(BdcDjxlQllxGxDO bdcDjxlQllxGxDO) {
        if (bdcDjxlQllxGxDO == null) {
            LOGGER.debug("初始化子系统——新增登记类型 权利类型  关系配置的参数为空！");
            throw new AppException("新增登记类型 权利类型  关系配置不能为空！");
        }
        if(StringUtils.isBlank(bdcDjxlQllxGxDO.getId())){
            bdcDjxlQllxGxDO.setId(UUIDGenerator.generate16());
        }
        return entityMapper.saveOrUpdate(bdcDjxlQllxGxDO,bdcDjxlQllxGxDO.getId());
    }

    /**
     * @param bdcDjxlQllxGxDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改
     */
    @Override
    public int updateBdcDjxlQllxGx(BdcDjxlQllxGxDO bdcDjxlQllxGxDO) {
        if (bdcDjxlQllxGxDO == null) {
            LOGGER.debug("初始化子系统——修改登记类型 权利类型  关系配置的参数为空！");
            throw new AppException("修改登记类型 权利类型 关系配置的参数不能为空！");
        }
        return entityMapper.updateByPrimaryKeySelective(bdcDjxlQllxGxDO);
    }

    /**
     * @param bdcDjxlQllxGxDOList
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除
     */
    @Override
    public int deleteBdcDjxlQllxGx(List<BdcDjxlQllxGxDO> bdcDjxlQllxGxDOList) {

        if (CollectionUtils.isEmpty(bdcDjxlQllxGxDOList)) {
            LOGGER.debug("初始化子系统——删除登记类型 权利类型  关系配置的参数为空！");
            throw new AppException("删除登记类型 权利类型  关系配置的参数不能为空！");
        }
        return bdcDjxlQllxGxDOList.stream().filter(bdcDjxlQllxGxDO -> StringUtils.isNotBlank(bdcDjxlQllxGxDO.getId())).mapToInt(bdcDjxlQllxGxDO -> entityMapper.deleteByPrimaryKey(BdcDjxlQllxGxDO.class, bdcDjxlQllxGxDO.getId())).sum();
    }
}
