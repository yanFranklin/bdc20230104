package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgDO;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.service.BdcCshFwkgService;
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
 * @description 初始化服务开关业务类
 */
@Service
public class BdcCshFwkgServiceImpl implements BdcCshFwkgService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcCshFwkgServiceImpl.class);

    @Autowired
    EntityMapper entityMapper;

    /**
     * @param bdcCshFwkgDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询
     */
    @Override
    public List<BdcCshFwkgDO> listBdcCshFwkg(BdcCshFwkgDO bdcCshFwkgDO) {
        if (bdcCshFwkgDO == null) {
            LOGGER.debug("初始化子系统——获取初始化服务开关配置的参数为空！");
            throw new AppException("获取初始化服务开关配置的参数不能为空！");
        }
        return entityMapper.selectByObj(bdcCshFwkgDO);
    }

    /**
     * @param bdcCshFwkgDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增
     */
    @Override
    public int insertBdcCshFwkg(BdcCshFwkgDO bdcCshFwkgDO) {
        if (bdcCshFwkgDO == null) {
            LOGGER.debug("初始化子系统——新增初始化服务开关配置的参数为空！");
            throw new AppException("新增初始化服务开关配置不能为空！");
        }
        if(StringUtils.isBlank(bdcCshFwkgDO.getId())){
            bdcCshFwkgDO.setId(UUIDGenerator.generate16());
        }
        return entityMapper.saveOrUpdate(bdcCshFwkgDO,bdcCshFwkgDO.getId());
    }

    /**
     * @param bdcCshFwkgDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改
     */
    @Override
    public int updateBdcCshFwkg(BdcCshFwkgDO bdcCshFwkgDO) {
        if (bdcCshFwkgDO == null) {
            LOGGER.debug("初始化子系统——修改初始化服务开关配置的参数为空！");
            throw new AppException("修改初始化服务开关配置的参数不能为空！");
        }
        return entityMapper.updateByPrimaryKeySelective(bdcCshFwkgDO);
    }

    /**
     * @param bdcCshFwkgDOList
     * @return 删除数量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除
     */
    @Override
    public int deleteBdcCshFwkg(List<BdcCshFwkgDO> bdcCshFwkgDOList) {
        if (CollectionUtils.isEmpty(bdcCshFwkgDOList)) {
            LOGGER.debug("初始化子系统——删除初始化服务开关配置的参数为空！");
            throw new AppException("删除初始化服务开关配置的参数不能为空！");
        }
        return bdcCshFwkgDOList.stream().filter(bdcCshFwkgDO -> StringUtils.isNotBlank(bdcCshFwkgDO.getId())).mapToInt(bdcCshFwkgDO -> entityMapper.deleteByPrimaryKey(BdcCshFwkgDO.class, bdcCshFwkgDO.getId())).sum();
    }

    @Override
    public BdcCshFwkgSlDO getDefaultSl() {
        BdcCshFwkgSlDO cshFwkgSlDO = new BdcCshFwkgSlDO();
        cshFwkgSlDO.setQlrsjly(CommonConstantUtils.QLRSJLY_LPB);
        cshFwkgSlDO.setYwrsjly(CommonConstantUtils.QLRSJLY_LPB);
        cshFwkgSlDO.setSfscql(CommonConstantUtils.SF_S_DM);
        cshFwkgSlDO.setSfsczs(CommonConstantUtils.SF_S_DM);
        cshFwkgSlDO.setSfscjtcy(CommonConstantUtils.SF_S_DM);
        cshFwkgSlDO.setSfgzdj(CommonConstantUtils.SF_S_DM);
        return cshFwkgSlDO;
    }
}
