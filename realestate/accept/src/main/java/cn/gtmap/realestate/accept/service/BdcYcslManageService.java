package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxInitDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcTsDjxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.accept.InitTsBdcDjxxResponseDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcYcslxxVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/12/9
 * @description 一窗受理逻辑处理服务
 */
public interface BdcYcslManageService {

    /**
     * @param bdcYwxxDTOList 业务数据模块
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化一窗受理信息
     */
    BdcSlxxInitDTO cshBdcYcslXx(List<BdcYwxxDTO> bdcYwxxDTOList, BdcSlxxDTO bdcSlxxDTO) throws Exception;


    /**
     * @param bdcYwxxDTOList 业务数据模块
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description   将标准的业务数据转换为一窗受理相关信息
     */
    BdcSlxxInitDTO changeYwxxDTOToBdcSlYcslxx(List<BdcYwxxDTO> bdcYwxxDTOList, BdcSlxxDTO bdcSlxxDTO) throws Exception;

    /**
     * @param bdcYwxxDTO 业务数据模块
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description   将标准的业务数据转换为一窗受理相关信息
     */
    BdcSlxxInitDTO changeYwxxDTOToBdcSlYcslxxSingle(BdcYwxxDTO bdcYwxxDTO, BdcSlXmDTO bdcSlXmDTO);

    /**
     * @param bdcTsDjxxRequestDTO 一窗推送登记请求对象
     * @return 推送结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 推送一窗信息创建登记流程
     */
    InitTsBdcDjxxResponseDTO initTsBdcDjxx(BdcTsDjxxRequestDTO bdcTsDjxxRequestDTO) throws Exception;

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新非登记流程案件状态为已办结
     */
    void changeAjztEnd(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新非登记流程案件状态为已办结
     */
    void updateYwslzt(String gzlslid,Integer ywslzt);


    /**
     * @param xmid 项目ID
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 同步生成一窗受理信息
     */
    BdcSlxxInitDTO syncYcslxx(String xmid,String jbxxid) throws Exception;

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid 组合流程需要传递
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 同步生成一窗受理信息
     */
    void syncLcYcslxx(String gzlslid,String xmid) throws Exception;

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid 组合流程需要传递
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 同步生成受理信息
     */
    void syncLcSlxx(String gzlslid,String xmid) throws Exception;

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid
     * @param bdcSlxxDTO 受理信息对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 同步生成一窗受理信息
     */
    void syncWwsqxxToYcslxx(String gzlslid,String xmid,BdcSlxxDTO bdcSlxxDTO) throws Exception;

    /**
     * @param bdcYcslQOStr 查询条件
     * @return 分页
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  一窗信息分页
     */
    Page<BdcYcslxxVO> listYcslxxByPage(Pageable pageable,String bdcYcslQOStr);

    /**
     * @param bdcYcslQOStr 查询条件
     * @return 列表
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  一窗信息不分页
     */
    List<BdcYcslxxVO> listYcslxxList(String bdcYcslQOStr);

    /**
     * @param gzlslid 工作流实例ID
     * @return 外网申请受理编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据登记工作流实例ID获取外网申请受理编号
     */
    String getWwsqSlbhByDjGzlslid(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 银行系统的申请受理编号
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据登记工作流实例ID获取互联网+的银行系统的受理编号
     */
    String getYhxtSlbhByDjGzlslid(String gzlslid);


}
