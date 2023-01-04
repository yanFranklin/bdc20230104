package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxInitDTO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/13
 * @description 初始化不动产项目信息
 */
public interface CshBdcSlXmService {
    /**
     * @param bdcSlYwxxDTOList 不动产受理项目前台
     * @param gzlslid           基本信息ID
     * @param czrid           操作人员ID
     * @param gzldyid           工作流定义id
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 初始化受理项目信息
     */
    void cshYxxm( List<BdcSlYwxxDTO> bdcSlYwxxDTOList, String gzlslid, String gzldyid, String jbxxid, String czrid);

    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @param  gzlslid 工作流实例ID
     * @description 初始化收件材料
     */
    void cshSjcl(String gzlslid);

    /**
     * @param bdcSlCshDTO 受理初始化对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化受理申请信息(非登记业务流程)
     */
    void cshBdcSlSqxx(BdcSlCshDTO bdcSlCshDTO) throws Exception;


     /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 初始化创建不动产登记项目
      */
     Map<String,String> cshCjBdcXm(BdcSlCshDTO bdcSlCshDTO) throws Exception;

    /**
     * 初始化合同信息的电子合同信息
     * @param gzlslid 工作流实例ID
     * @param htbh 合同编号
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
     void cshHtxxSjcl(String gzlslid, String htbh);

     /**
       * @param bdcSlCshDTO 受理初始化实体
       * @return 验证结果
       * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
       * @description 初始化前通用验证逻辑
      */
     CommonResponse yzCshxxBeforeCj(BdcSlCshDTO bdcSlCshDTO);

    /**
     * @param slbhList 受理编号集合
     * @param shzt     审核状态
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 规则例外审核后的处理
     */
    void afterGzlwsh(List<String> slbhList, Integer shzt);

    /**
     * @param dm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据配置的用途名称判断是否主房
     * @date : 2022/7/14 15:21
     */
    boolean checkSfzf(String dm);


}
