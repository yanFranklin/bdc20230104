package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.dto.building.DjdcbFwQlrResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/6
 * @description 权利人服务接口
 */
public interface FwFcqlrService {
    /**
     * @param fwFcqlrIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过权利人主键删除权利人
     */
    Integer deleteFcqlrByFwFcqlrIndex(String fwFcqlrIndex);

    /**
     * @param fwIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过房屋户室主键删除权利人(删除户室的时候调用)
     */
    void deleteFcqlrByFwIndex(String fwIndex);


    /**
     * @param fwFcqlrDOList
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 新增房屋房产权利人（批量）
     */
    List<FwFcqlrDO> batchInsertFwFcQlr(List<FwFcqlrDO> fwFcqlrDOList);

    /**
     * @param fwFcqlrDOList
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量验证外键
     */
    boolean validateBatchFk(List<FwFcqlrDO> fwFcqlrDOList);

    /**
     * @param fwFcqlrDOList
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量验证验证外键
     */
    boolean validateBatchPKAndFk(List<FwFcqlrDO> fwFcqlrDOList);

    /**
     * @param fwFcqlrDO
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 新增房屋房产权利人（单个）
     */
    FwFcqlrDO insertFwFcQlr(FwFcqlrDO fwFcqlrDO);

    /**
     * @param fwFcqlrDO
     * @param updateNull true表示空字段更新，false表示空字段不更新
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改房屋房产权利人（单个）
     */
    Integer updateFwFcQlr(FwFcqlrDO fwFcqlrDO, boolean updateNull);

    /**
     * @param fwFcqlrDOList
     * @param updateNull true表示空字段更新，false表示空字段不更新
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改房屋房产权利人（批量）
     */
    void batchUpdateFwFcQlr(List<FwFcqlrDO> fwFcqlrDOList, boolean updateNull);

    /**
     * @param fwHsIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据fwHsIndex查询权利人信息
     */
    List<FwFcqlrDO> listFwFcQlrByFwIndex(String fwHsIndex);

    /**
     * @param fwIndex
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据fwIndex 聚合 QLR
     */
    String wmConcatQlrByFwIndex(String fwIndex);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwFcqlrIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO
     * @description 根据主键查询房屋房产权利人
     */
    FwFcqlrDO queryFwFcByPk(String fwFcqlrIndex);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @param FwFcQlrDO
     * @return void
     * @description 根据BDCDYH 处理 房屋房产权利人的外键
     */
    void setFwFcQlrFkVal(String bdcdyh,FwFcqlrDO FwFcQlrDO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return boolean
     * @description 根据BDCDYH 判断是否 应该有 FWFCQLR实体
     */
    boolean checkNeedFwFcQlr(String bdcdyh);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param pageable
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.DjdcbFwQlrResponseDTO>
     * @description 权利人信息
     */
    Page<DjdcbFwQlrResponseDTO> listQlrByPageJson(Pageable pageable, Map map);
}
