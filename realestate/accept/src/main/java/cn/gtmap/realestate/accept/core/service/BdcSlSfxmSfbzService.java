package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmSfbzDO;

import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 2019/1/22
 * @description 不动产收费项目收费标准数据库服务
 */
public interface BdcSlSfxmSfbzService {
    /**
     * @param sfxmdm 收费项目代码
     * @return 不动产收费项目收费标准
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费项目代码获取不动产收费项目收费标准
     */
    List<BdcSlSfxmSfbzDO> listBdcSlSfxmSfbzDO(String sfxmdm);

    List<BdcSlSfxmSfbzDO> listBdcSlSfxmSfbzDOAll();
}
