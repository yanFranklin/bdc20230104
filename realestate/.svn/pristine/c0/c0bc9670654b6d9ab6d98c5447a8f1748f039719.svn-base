package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/1
 * @description 不动产受理服务
 */
public interface BdcSlService {

    /**
     * @param jbxxid     基本信息ID
     * @param bdcSlxxDTO 受理信息
     * @return 受理信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID获取受理信息
     */
    BdcSlxxDTO queryBdcSlxx(String jbxxid, BdcSlxxDTO bdcSlxxDTO,Integer sfzlcsh);



    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID更新受理信息
     */
    void updateBdcSlxx(BdcSlxxDTO bdcSlxxDTO);

    /**
     * @param jbxxid 基本信息ID
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID,工作流实例ID删除受理信息(全部信息删除)
     */
    void deleteSlxx(String jbxxid,String gzlslid);


}
