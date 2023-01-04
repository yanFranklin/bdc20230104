package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.FwKDO;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-28
 * @description 图层服务
 */
public interface FwKService {

    /**
     * @param lszd
     * @param zrzh
     * @return cn.gtmap.realestate.common.core.domain.building.FwKDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据 lszd 和 ZRZH 查询 自然幢
     */
    FwKDO queryFwKByLszdAndZrzh(String lszd, String zrzh, String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param lszd
     * @param zrzhList
     * @return void
     * @description 根据 lszd 和 ZRZH 删除 自然幢
     */
    void deleteFwKWithLszdAndZrzh(String lszd,List<String> zrzhList);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwXmxxIndex
     * @return void
     * @description  根据项目主键 删除 FW_K表
     */
    void deleteFwKByFwXmxxIndex(String fwXmxxIndex);

}
