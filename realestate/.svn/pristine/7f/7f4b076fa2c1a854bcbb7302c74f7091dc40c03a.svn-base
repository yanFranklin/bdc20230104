package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import cn.gtmap.realestate.common.core.dto.building.XmxxResponseDTO;
import cn.gtmap.realestate.common.core.vo.building.FwXmxxBgVO;
import cn.gtmap.realestate.common.core.vo.building.FwXmxxHbVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/5
 * @description 项目信息服务接口
 */
public interface FwXmxxService {

    /**
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.XmxxResponseDTO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询项目信息
     */
    Page<XmxxResponseDTO> listXmxxByPage(Pageable pageable, Map map);

    /**
     * @param fwXmxxDO
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 新增项目信息
     */
    FwXmxxDO insertFwXmxx(FwXmxxDO fwXmxxDO);

    /**
     * @param fwXmxxDO
     * @param updateNull true表示空字段更新，false表示空字段不更新
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改项目信息(预留type字段, 根据type确定是修改还是变更)
     */
    Integer updateFwXmxx(FwXmxxDO fwXmxxDO, boolean updateNull);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwXmxxIndex
     * @return void
     * @description 根据主键删除项目信息
     */
    Integer deleteFwXmxxByFwXmxxIndex(String fwXmxxIndex,boolean withRelated);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwXmxxIndexList
     * @param delFwK
     * @return void
     * @description 批量删除项目信息
     */
    void batchDelFwXmxx(List<String> fwXmxxIndexList, boolean delFwK);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwXmxxIndex
     * @param withQlr
     * @param withLjz
     * @return java.lang.Integer
     * @description 根据主键删除项目信息和相关数据 判断是否删除逻辑幢
     */
    Integer deleteFwXmxxByIdxWithRelated(String fwXmxxIndex,boolean withQlr,boolean withLjz);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwXmxxDO
     * @param withRelated
     * @return java.lang.Integer
     * @description  根据数据记录删除项目信息
     */
    Integer deleteFwXmxx(FwXmxxDO fwXmxxDO,boolean withRelated);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwXmxxDO
     * @description 根据BDCDYH 查询项目信息
     */
    FwXmxxDO queryXmxxByBdcdyh(String bdcdyh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwXmxxIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwXmxxDO
     * @description 根据主键查询项目信息
     */
    FwXmxxDO queryXmxxByPk(String fwXmxxIndex);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwXmxxDO
     * @param updateNull
     * @return java.lang.Integer
     * @description 更新 FW_HS表 有BDCDYH变化，并且不知道原有BDCDYH情况
     */
    Integer updateFwXmxxWithChangeBdcdyh(FwXmxxDO fwXmxxDO, boolean updateNull);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwXmxxDO
     * @param updateNull
     * @param ybdcdyh
     * @return java.lang.Integer
     * @description 更新 FW_HS表 有BDCDYH变化，且知道原有BDCDYH情况（包括空的情况）
     */
    Integer updateFwXmxxWithChangeBdcdyh(FwXmxxDO fwXmxxDO,boolean updateNull,String ybdcdyh);


    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwXmxxIndex
     * @return java.lang.Integer
     * @description 项目信息关联逻辑幢
     */
    Integer relevanceLjz(String fwXmxxIndex,String fwDcbIndex);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwDcbIndex
     * @return java.lang.Integer
     * @description 项目信息取消关联逻辑幢
     */
    Integer cancelRelevanceLjz(String fwDcbIndex, String bdcdyfwlx);

    /**
     * @param fwXmxxDO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目信息实体查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByXmxxDO(FwXmxxDO fwXmxxDO);
    /**
     * @param fwXmxxIndexList
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目信息主键list查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByXmxxList(List<String> fwXmxxIndexList);

    /**
     * @param fwXmxxIndex
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目信息主键list查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByXmxxIndex(String fwXmxxIndex);


    /**
     * @param fwXmxxBgVO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据FwXmxxBgVO查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByFwXmxxBgVO(FwXmxxBgVO fwXmxxBgVO);

    /**
     * @param fwXmxxHbVO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据FwXmxxHbVO查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByFwXmxxHbVO(FwXmxxHbVO fwXmxxHbVO);

    /**
     * @param jsonData
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据json查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByJson(String jsonData);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param xmbm
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwXmxxDO>
     * @description 根据XMBM 查询 可用的FWXMXX
     */
    List<FwXmxxDO> listKyFwXmxxByXmbm(String xmbm);

}