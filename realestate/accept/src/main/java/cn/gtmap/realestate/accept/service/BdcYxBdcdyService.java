package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.YxBdcdyDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/15
 * @description 已选不动产单元服务
 */
public interface BdcYxBdcdyService {
    /**
     * @param bdcSlYwxxDTOPage 不动产业务信息分页
     * @param jbxxid           基本信息ID
     * @return 已选不动产单元
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 获取已选不动产单元
     */
    YxBdcdyDTO queryYxBdcdyDTO(Page<BdcSlYwxxDTO> bdcSlYwxxDTOPage, String jbxxid, String gzldyid);

    /**
     * @param bdcSlYwxxDTOList 不动产业务信息集合
     * @param jbxxid           基本信息ID
     * @return 已选不动产单元
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 获取已选不动产单元
     */
    YxBdcdyDTO queryYxBdcdyDTO(List<BdcSlYwxxDTO> bdcSlYwxxDTOList, String jbxxid, String gzldyid,String djxl);

    /**
     * @param slXmQO
     * @param jbxxid  基本信息ID
     * @param gzldyid 工作流定义ID
     * @param sfazfz  是否按幢分组
     * @return 已选不动产单元
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据登记小类分组查询已选业务信息(不分页)
     */
    List<YxBdcdyDTO> queryYxBdcdyDTOGroupByDjxl(String slXmQO, String jbxxid, String gzldyid, String sfazfz);
}
