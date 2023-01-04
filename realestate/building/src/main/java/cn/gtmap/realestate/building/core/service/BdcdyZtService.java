package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhlsztDO;
import cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO;
import cn.gtmap.realestate.common.core.dto.building.BatchBdcdyhSyncZtRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018/11/13
 * @description 不动产单元号现势状态查询接口
 */
public interface BdcdyZtService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO>
     * @description 根据幢主键查询 状态
     */
    List<SSjBdcdyhxsztDO> querySsjBdcdyhxsztDOByFwDcbIndex(String fwDcbIndex);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @param list
     * @return cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO
     * @description 在 LIST中查找 BDCDYH的 XSZT
     */
    SSjBdcdyhxsztDO getSsjBdcdyXsztInList(String bdcdyh,List<SSjBdcdyhxsztDO> list);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO
     * @description 根据BDCDYH 查询 状态实体
     */
    SSjBdcdyhxsztDO querySsjBdcdyhxsztDOByBdcdyh(String bdcdyh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param xsztDO
     * @param updateNull
     * @return void
     * @description 更新 XSZT
     */
    void updateXsztDO(SSjBdcdyhxsztDO xsztDO,boolean updateNull);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param xsztDO
     * @return void
     * @description 新增XSZT
     */
    void insertXsztDO(SSjBdcdyhxsztDO xsztDO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param lsztDO
     * @return void
     * @description 新增LSZT
     */
    void insertLsztDO(SSjBdcdyhlsztDO lsztDO);


    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO
     * @description 根据BDCDYH查询不动产单元现势状态
     */
    BdcdyhZtResponseDTO queryBdcdyhZtBybdcdyh(String bdcdyh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO
     * @description 根据BDCDYH查询状态 XSZT没有 查 LSZT
     */
    SSjBdcdyhxsztDO getBdcdyztDO(String bdcdyh);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhlsztDO
     * @description 根据BDCDYH 查询 历史状态
     */
    SSjBdcdyhlsztDO querySsjBdcdyhlsztDOByBdcdyh(String bdcdyh);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyhList
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO>
     * @description 批量根据BDCDYH查询不动产单元现势状态
     */
    List<BdcdyhZtResponseDTO> listBdcdyhZtBybdcdyh(List<String> bdcdyhList);

    /**
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @param bdcdyhZtRequestDTO
     * @return void
     * @description 根据BDCDYH修改不动产单元现势状态
     */
    Integer updateBdcdyZtByBdcdyh(BdcdyhZtRequestDTO bdcdyhZtRequestDTO);

    /**
     * sly 批量更新户室中的不动产单元状态
     *
     * @param fwhsList
     */
    void updateBdcdyZtByFwhsList(List<FwHsDO> fwhsList);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwYchsDOList
     * @return void
     * @description 批量更新预测户室中的不动产单元状态
     */
    void updateBdcdyZtByFwYchsList(List<FwYchsDO> fwYchsDOList);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return SSjBdcdyhxsztDO
     * @description 删除BDCDYH XSZT表数据
     *
     */
    SSjBdcdyhxsztDO deleteBdcdyhXszt(String bdcdyh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return void
     * @description 移除BDCDYH历史状态
     */
    void deleteBdcdyhLszt(String bdcdyh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bjrq
     * @return void
     * @description 根据BJRQ同步状态
     */
    void syncByDate(Date bjrq);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyhSet
     * @return void
     * @description 分页批量同步BDCDYH
     */
    void syncByBdcdyhByPage(Set<String> bdcdyhSet);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyhList
     * @return void
     * @description 根据BDCDYH查询
     */
    void syncByBdcdyh(List<String> bdcdyhList);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param batchDTO
     * @return void
     * @description 保存DTO中的状态数据
     */
    void saveZtWithDTO(BatchBdcdyhSyncZtRequestDTO batchDTO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param batchDTO
     * @return void
     * @description 保存DTO中锁定的状态数据
     */
    void saveSdZtWithDTO(BatchBdcdyhSyncZtRequestDTO batchDTO);

    /**
     * @param bdcdyh
     * @param bdcdyhList
     * @return Integer
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 批量不动产单元合并，要把这些不动产单元和限制状态求和放到新的不动产单元号里
     */
    Integer updateBdcdyZtByPlBdcdyh(String bdcdyh, List<String> bdcdyhList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyhList 单元号集合
     * @return {List} 现势状态信息
     * @description 批量查询不动产单元现势状态信息（不循环查询避免批量查询慢）
     */
    List<BdcdyhZtResponseDTO> listBdcdyhZtPlcx(List<String> bdcdyhList);
}