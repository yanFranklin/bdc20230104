package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgDO;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/5/31
 * @description 初始化服务开关业务类
 */
public interface BdcCshFwkgService {
    /**
     * 查询初始化开关表
     * @param bdcCshFwkgDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询
     */
    List<BdcCshFwkgDO> listBdcCshFwkg(BdcCshFwkgDO bdcCshFwkgDO);

    /**
     * 新增
     * @param bdcCshFwkgDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增
     */
    int insertBdcCshFwkg(BdcCshFwkgDO bdcCshFwkgDO);

    /**
     * 修改
     * @param bdcCshFwkgDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改
     */
    int updateBdcCshFwkg(BdcCshFwkgDO bdcCshFwkgDO);

    /**
     * 删除
     * @param bdcCshFwkgDOList
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除
     */
    int deleteBdcCshFwkg(List<BdcCshFwkgDO> bdcCshFwkgDOList);
    /**
    * @author chenchunxue 2020/9/10
    * @param
    * @return cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO
    * @description 获取默认实例表信息
    */
    BdcCshFwkgSlDO getDefaultSl();
}
