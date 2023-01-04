package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.building.FwhsQlrDTO;
import cn.gtmap.realestate.common.core.dto.building.YcHsZtResDTO;
import cn.gtmap.realestate.common.core.dto.building.YchsAndQlrResponseDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018/11/10
 * @description 房屋预测户室service
 */
public interface FwYcHsService {

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据bdcdyh查询预测户室
     */
    FwYchsDO queryFwYcHsByBdcdyh(String bdcdyh);

    /**
     * @param fwYchsDO
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 新增预测户室服务
     */
    FwYchsDO insertFwYcHs(FwYchsDO fwYchsDO);

    /**
     * @param fwYchsDO
     * @param updateNull
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 修改预测户室服务
     */
    Integer updateFwYcHs(FwYchsDO fwYchsDO, boolean updateNull);

    /**
     * @param fwHsIndex
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据主键删除户室信息
     */
    Integer deleteYcHsByFwHsIndex(String fwHsIndex,boolean withRelated);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwYchsDO
     * @param withRelated
     * @return int
     * @description 根据查询记录删除预测户室
     */
    int deleteYchs(FwYchsDO fwYchsDO,boolean withRelated);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwYchsDO
     * @param updateNull
     * @return java.lang.Integer
     * @description 更新 FW_HS表 有BDCDYH变化，并且不知道原有BDCDYH情况
     */
    Integer updateFwYchsWithChangeBdcdyh(FwYchsDO fwYchsDO,boolean updateNull);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwYchsDO
     * @param updateNull
     * @param ybdcdyh
     * @return java.lang.Integer
     * @description 更新 FW_HS表 有BDCDYH变化，且知道原有BDCDYH情况（包括空的情况）
     */
    Integer updateFwYchsWithChangeBdcdyh(FwYchsDO fwYchsDO,boolean updateNull,String ybdcdyh);

    /**
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.FwYchsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询预测户室信息
     */
    Page<FwYchsDO> listYchsByPage(Pageable pageable, Map map);

    /**
     * @param pageable
     * @param map
     * @return Page<Map>
     * @author <a href="mailto:wangzijie@gtmap.cn">liyinqiao</a>
     * @description 关联功能分页查询预测户室信息
     */
    Page<Map> glListYchsByPage(Pageable pageable, Map map);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHsDO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwYchsDO>
     * @description 查询关联的预测户室列表
     */
    List<FwYchsDO> listGlYchs(FwHsDO fwHsDO);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.YchsAndQlrResponseDTO
     * @description 根据不动产单元号查询预测户室和预测户室权利人信息
     */
    YchsAndQlrResponseDTO queryYchsAndQlrByBdcdyh(String bdcdyh);

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwYchsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据LJZ主键查询预测户室列表
     */
    List<FwYchsDO> queryYchsByFwDcbIndex(String fwDcbIndex);

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwYchsDO>
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 根据LJZ主键查询预测户室列表，不进行排序
     */
    List<FwYchsDO> queryYchsByFwDcbIndexNoSort(String fwDcbIndex);

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwYchsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">gaolining</a>
     * @description 根据LJZ主键查询预测户室列表 包含权利人
     */
    List<FwhsQlrDTO> queryYchsWithQlr(String fwDcbIndex);

    /**
     * @param fwHsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwYchsDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键查询预测房屋户室
     */
    FwYchsDO queryFwYcHsByFwHsIndex(String fwHsIndex);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwYchsDOList
     * @return cn.gtmap.realestate.common.core.domain.building.FwYchsDO
     * @description 批量新增预测户室
     */
    List<FwYchsDO> batchInsertFwYchs(List<FwYchsDO> fwYchsDOList);


    /**
     * @param fwYchsDOList
     * @param updateNull
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改房屋预测户室（批量）
     */
    void batchUpdateFwYcHs(List<FwYchsDO> fwYchsDOList, boolean updateNull);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwbm
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwYchsDO>
     * @description 根据FWBM 查询可用的 FWYCHS
     */
    List<FwYchsDO> listKyFwYchsByFwbm(String fwbm);

    /**
     * @param ysfwbm
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwYchsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据 YSFWBM 查询可用的 FWYCHS
     */
    List<FwYchsDO> listKyFwYchsByYsfwbm(String ysfwbm, String zl);

    /**
     * @param fwDcbIndex
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据房屋fwdcbindex查询预测户室的信息和状态
     * @date : 2020/12/10 10:12
     */
    List<YcHsZtResDTO> listFwychsZt(String fwDcbIndex,String qjgldm);

    /**
     * @param YSFWBM
     * @author wangyinghao
     * @description 根据房屋YSFWBM查询预测户室的信息和状态
     * @date : 2020/12/10 10:12
     */
    List<FwYchsDO> listFwYchsByYsfwbm(String YSFWBM);;
}