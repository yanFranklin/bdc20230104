package cn.gtmap.realestate.building.service;

import cn.gtmap.realestate.common.core.domain.building.FwHstDO;
import cn.gtmap.realestate.common.core.dto.building.FwHstRequestDTO;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-25
 * @description
 */
public interface HstService {


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHstRequestDTO
     * @return cn.gtmap.realestate.common.core.domain.building.FwHstDO
     * @description 保存户室图
     */
    FwHstDO saveFwHst(FwHstRequestDTO fwHstRequestDTO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHstRequestDTO
     * @return cn.gtmap.realestate.common.core.domain.building.FwHstDO
     * @description 保存幢平面图
     */
    FwHstDO saveLjzPmt(FwHstRequestDTO fwHstRequestDTO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHsIndex
     * @return void
     * @description 删除户室的户室图
     */
    void delFwhsHst(String fwHsIndex,String hslx);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwDcbIndex
     * @return void
     * @description 删除独幢的平面图
     */
    void delFwLjzPmt(String fwDcbIndex);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHstDO
     * @return void
     * @description 删除房屋户室图 包含删除图片操作
     */
    void deleteFwHst(FwHstDO fwHstDO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return cn.gtmap.realestate.building.service.ReadHstService
     * @description 通过配置 获取 读取服务
     */
    ReadHstService getConfigReadService();
}
