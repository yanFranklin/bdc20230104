package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.building.*;
import cn.gtmap.realestate.common.core.dto.register.BdcdyZtDTO;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/4/17
 * @description 不动产单元状态服务
 */
public interface BdcdyZtService {
    /**
     * @param bdcdyh 不动产单元号
     * @return BdcdyZtDTO 不动产单元状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元状态
     */
    BdcdyZtDTO queryBdcdyZt(String bdcdyh);

    /**
     * @param bdcdyList 不动产单元List
     * @return List<BdcdyZtDTO> 不动产单元List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量查询不动产单元状态
     */
    List<BdcdyZtDTO> queryBdcdyZtList(List<String> bdcdyList);


    /**
     * @param bdcdyList 不动产单元号 List
     * @return BatchBdcdyhSyncZtRequestDTO 不动产单元各种权利数量集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 批量查询不动产单元各种权利数量集合
     */
    BatchBdcdyhSyncZtRequestDTO queryBdcdyhSyncZtList(List<String> bdcdyList);

    /**
     * @param bdcdyh 不动产单元
     * @return BdcSyncZtRequestDTO 不动产单元各种权利数量集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 查询不动产单元各种权利数量集合
     */
    BdcSyncZtRequestDTO queryBdcdyhSyncZt(String bdcdyh);

    /**
     * @param bdcdyList 批量的单元参数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新权籍不动产单元状态
     */
    void syncBdcdyZtByBdcdyh(List<String> bdcdyList,String qjgldm);

    /**
     * @param bdcXmDOList 项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新权籍不动产单元的项目信息
     */
    void syncBdcdyxxByBdcXm(List<BdcXmDO> bdcXmDOList);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 同步权籍基本信息和同步权籍单元状态
     */
    void synQjJbxxAndBdcdyzt(String gzlslid);

    /**
     * @param xmidList 项目ID集合
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据xmid同步权籍单元状态
     */
    void syncBdcdyztByXmid(List<String> xmidList);

    /**
     * @param gzlslid     工作流实例ID
     * @param bdcXmDOList 已知的当前流程的项目信息
     * @return List<String> 需要操作的不动产单元信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前流程需要更新权籍的不动产单元号
     */
    List<String> querySyncQjBdcdyh(String gzlslid, List<BdcXmDO> bdcXmDOList);

    /**
     * @param xmidList 项目ID
     * @return List<String> 需要操作的不动产单元信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前流程需要更新权籍的不动产单元号
     */
    List<String> querySyncQjBdcdyh(List<String> xmidList);

    /**
     * @param zsxmid 证书项目ID
     * @return BdcdyZtDTO 不动产单元状态
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据证书项目ID查询证书状态
     */
    BdcdyZtDTO queryZsBdcdyZtByCqzsxmid(String zsxmid);

    /**
     * @param cqxmid 产权项目ID
     * @return BdcdyZtDTO 不动产单元状态
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据项目ID验证证书是否存在限制权利
     */
    Boolean existXzqlByCqxmid(String cqxmid, Integer bdclx);

    /**
     * @param bdcdyh 不动产单元号
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据bdcdyh查询不动产单元现势状态
     */
    BdcdyhZtResponseDTO commonQueryBdcdyhZtBybdcdyh(String bdcdyh,String qjgldm);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyhList
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO>
     * @description 批量根据BDCDYH查询不动产单元现势状态
     */
    List<BdcdyhZtResponseDTO> commonListBdcdyhZtBybdcdyh(List<String> bdcdyhList,String qjgldm);

    /**
     * @param bdcdyh
     * @param bdcdyhList
     * @return Integer
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 批量不动产单元合并，要把这些不动产单元和限制状态求和放到新的不动产单元号里
     */
    void commonUpdateBdcdyZtByPlBdcdyh(String bdcdyh, List<String> bdcdyhList,String qjgldm);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 保存锁定状态
      */
    void saveBdcdjSdZtWithDTO(BatchBdcdyhSyncZtRequestDTO batchDTO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyhList 单元号集合
     * @return {List} 现势状态信息
     * @description 批量查询不动产单元现势状态信息（不循环查询避免批量查询慢）
     */
    List<BdcdyhZtResponseDTO> commonListBdcdyhZtPlcx(List<String> bdcdyhList,String qjgldm);

}
