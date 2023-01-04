package cn.gtmap.realestate.building.service;

import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.FwBdcdyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-07
 * @description 不动产单元相关服务
 */
public interface BdcdyService {

    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.BdcdyPageResponseDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询实测户室不动产单元信息
     */
    Page<Map> listScFwHsBdcdy(Pageable pageable
            , Map<String, Object> paramMap);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable, paramMap
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @description 分页查询实测户室不动产单元信息
     */
    Page<Map> listHsForWwsq(Pageable pageable
            , Map<String, Object> paramMap);

    /**
     * @param pageable, paramJson
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询户室信息，实测预测
     * @date : 2020/12/16 18:20
     */
    Page<Map> listScYcHsByPage(Pageable pageable
            , Map<String, Object> paramMap);

    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.BdcdyPageResponseDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询预测户室不动产单元信息
     */
    Page<Map> listYcFwHsBdcdy(Pageable pageable, Map<String, Object> paramMap);


    /**
     * @param bdcdyh
     * @param bdcdyfwlx
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询不动单元基本信息
     */
    BdcdyResponseDTO queryBdcdy(String bdcdyh, String bdcdyfwlx);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwbm
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @description 根据FWBM查询FW类型BDCDY（包含FW_HS FW_YCHS FW_LJZ FW_XMXX）
     */
    BdcdyResponseDTO queryBdcdyByFwbm(String fwbm);

    /**
     * @param ysfwbm
     * @param hslx   hs ychs
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据预售房屋编码  查询户室BDCDY
     */
    BdcdyResponseDTO queryHsBdcdyByYsFwbm(String ysfwbm, String hslx, String zl);

    /**
     * @param bdcdyh
     * @param djxxDOClass
     * @return cn.gtmap.realestate.common.core.domain.building.DjxxDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询地籍数据（指定）
     */
    <T> T queryDjxxByBdcdyh(String bdcdyh, Class<T> djxxDOClass);

    /**
     * @param zhdm 宗海代码
     * @param djxxDOClass
     * @return cn.gtmap.realestate.common.core.domain.building.DjxxDO
     * @author chenchunxue
     * @description 根据宗海代码查询地籍数据（指定）
     */
    <T> T queryDjxxByZhdm(String zhdm, Class<T> djxxDOClass);

    /**
     * @param bdcdyh
     * @param djxxDOClass
     * @return cn.gtmap.realestate.common.core.domain.building.DjxxDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询地籍数据（指定）
     */
    <T> T queryDjxxByBdcdyhWithOrder(String bdcdyh, Class<T> djxxDOClass,String order);

    /**
     * @param zhdm
     * @param djxxDOClass
     * @return cn.gtmap.realestate.common.core.domain.building.DjxxDO
     * @author chenchunxue
     * @description 根据宗海代码查询地籍数据（指定）
     */
    <T> T queryDjxxByZhdmWithOrder(String zhdm, Class<T> djxxDOClass,String order);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @param djxxDOClass
     * @return T
     * @description 根据 DJH 查询地籍信息
     */
    <T> T queryDjxxByDjh(String djh, Class<T> djxxDOClass);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @param djxxDOClass
     * @return T
     * @description 根据 DJH 查询地籍信息的备份
     */
    <T> T queryDjxxByDjhWithOrder(String djh, Class<T> djxxDOClass,String order);

    /**
     * @param index
     * @param tClass
     * @return java.util.List<T>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键查询权利人
     */
    <T> List<T> listQlrByDjDcbIndex(String index, Class<T> tClass);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO>
     * @description 根据BDCDYH 查询 权利人列表
     */
    List<FwFcqlrDO> listFwFcQlrByBdcdyh(String bdcdyh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @param hslx
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @description 查询HS BDCDY
     */
    FwHsDO queryHsBdcdy(String bdcdyh,String hslx);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwIndex
     * @return java.lang.String
     * @description 根据FW主键 查询 BDCDYH
     */
    String queryBdcdyhByFwIndex(String fwIndex);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwbm
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.FwBdcdyDTO>
     * @description 根据FWBM查询户室类型的不动产单元
     */
    List<FwBdcdyDTO> queryFwBdcdyByFwbm(String fwbm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ybdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @description 根据ybdcdyh 查询户室 和 LJZ 的 BDCDY
     */
    BdcdyResponseDTO queryHsLjzBdcdyByYbdcdyh(String ybdcdyh);

    /**
     * @param bdcdyResponseDTO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化建设用地量化信息
     */
    BdcdyResponseDTO initJsydLhxx(BdcdyResponseDTO bdcdyResponseDTO);

    /**
     * @param bdcdyh 不动产单元号
     * @return 不动产单元房屋类型
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据bdcdyh查询不动产单元房屋类型
     */
    String queryBdcdyfwlx(String bdcdyh);

    /**
     * @param houseId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据house_id 先查户室信息再查其他相关数据--蚌埠特殊需求，特殊字段
     * @date : 2022/5/16 15:09
     */
    BdcdyResponseDTO queryBdcdyByHouseId(String houseId, String bdcdyh, String bdcdyfwlx);

    /**
     * @return
     * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
     * @description 根据houseId、坐落或是bdcdyh获取户室数据
     */
    List<BdcdyResponseDTO> queryBdcdy(String houseId,String bdcdyh,String zl);

    /**
     * @param houseId
     * @return 户室信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据houseId获取户室信息
     */
    List<Map> listFwScYcHsByHouseId(String houseId);
}
