package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.dto.building.*;
import cn.gtmap.realestate.common.core.qo.building.BdcTddysfxxQO;
import cn.gtmap.realestate.common.core.vo.building.BatchUpdateFwhsVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-10-27
 * @description 房屋户室service
 */
public interface FwHsService {

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    List<FwHsDO> listFwHsListByFwDcbIndex(String fwDcbIndex);

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">caolu</a>
     * @description 根据LJZ主键查询户室列表，不进行排序
     */
    List<FwHsDO> listFwHsByFwDcbIndex(String fwDcbIndex);

    /**
     * @param fwDcbIndex
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询房屋户室信息包含权利人
     * @date : 2020/12/22 10:51
     */
    List<FwhsQlrDTO> listFwhsWithQlr(String fwDcbIndex);

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 查询坐落为空的户室
     */
    List<FwHsDO> listFwHsZlNullByFwDcbIndex(String fwDcbIndex);

    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 查询为空的为空的户室
     */
    List<FwHsDO> listFwHstNullByFwDcbIndex(String fwDcbIndex);

    /**
     * @param fwHsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    FwHsDO queryFwHsByIndex(String fwHsIndex);

    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从户室基本信息实体查询预测信息
     */
    public List<FwHsDO> queryFwycHsByIndexAndScmj(String fwDcbIndex);

    /**
     * @param fwHsDO
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 新增户室接口服务
     */
    FwHsDO insertFwHs(FwHsDO fwHsDO);
    /**
     * @param fwHsDO
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 新增户室接口服务 增加判断是否新增不动产单元号标示
     */
    FwHsDO insertFwHsCreatBdcdyh(FwHsDO fwHsDO ,boolean creatBdcdyh);

    /**
     * @param fwHsDO
     * @param updateNull true表示空字段不更新，false，表示空字段更新
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改户室接口服务
     */
    Integer updateFwHs(FwHsDO fwHsDO, boolean updateNull);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHsDO
     * @param updateNull
     * @return java.lang.Integer
     * @description 更新 FW_HS表 有BDCDYH变化，并且不知道原有BDCDYH情况
     */
    Integer updateFwHsWithChangeBdcdyh(FwHsDO fwHsDO,boolean updateNull);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHsDO
     * @param updateNull
     * @param ybdcdyh
     * @return java.lang.Integer
     * @description 更新 FW_HS表 有BDCDYH变化，且知道原有BDCDYH情况（包括空的情况）
     */
    Integer updateFwHsWithChangeBdcdyh(FwHsDO fwHsDO,boolean updateNull,String ybdcdyh);

    /**
     * @param fwHsIndex
     * @return Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 删除户室接口服务
     */
    Integer deleteHsByFwHsIndex(String fwHsIndex,boolean withRelated);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHsDO
     * @return int
     * @description 删除户室
     */
    int deleteFwhs(FwHsDO fwHsDO,boolean withRelated);

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询户室基本信息
     */
    FwHsDO queryFwhsByBdcdyh(String bdcdyh);

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsHouseIdDTO
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 根据BDCDYH查询户室houseid信息
     */
    FwHsHouseIdDTO queryFwhsHouseIdByBdcdyh(String bdcdyh);

    /**
     * @param bdcdyhList
     * @return
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description
     */
    Map<String, List<String>> queryLcztByBdcdyh(List<String> bdcdyhList);

    /**
     * @param bdcdyhList
     * @return
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据BDCDYH查询户室流程状态
     */
    List<String> queryFwhsLcztByBdcdyh(List<String> bdcdyhList);
    /**
     * @param fwHstIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据户室图主键查询房屋户室
     */
    List<FwHsDO> listFwHsByFwHstIndex(String fwHstIndex);

    /**
     * @param fwDcbIndex
     * @param fwHsIndexList
     * @return java.lang.Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改房屋户室逻辑幢
     */
    void updateFwHsLjz(List<String> fwHsIndexList, String fwDcbIndex);


    /**
     * @param fcdah
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FCDAH查询FWHS
     */
    List<FwHsDO> listKyFwhsByFcdah(String fcdah);


    /**
     * @param fwbm
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FWBM查询FWHS
     */
    List<FwHsDO> listKyFwhsByFwbm(String fwbm);

    /**
     * @param fwbm  房屋编码
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 只根据房屋编码参数查询房屋户室信息
     */
    List<FwHsDO> queryFwhsOnlyByFwbm(String fwbm);

    /**
     * @param ysfwbm
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据YSFWBM查询可用房屋户室
     */
    List<FwHsDO> listKyFwhsByYsfwbm(String ysfwbm, String zl);


    /**
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据FW_DCB_INDEX 查询 可用 FW_HS
     */
    List<FwHsDO> listKyFwhsByFwDcbIndex(String fwDcbIndex);

    /**
     * @param ycfwbm
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据户室的一些参数查询同一FW_DCB_INDEX下的 FW_HS
     */
    List<FwHsDO> listFwhsByYcfwbm(String ycfwbm);


    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询FWHS
     */
    Page<FwHsDO> listFwHsByPage(Pageable pageable, Map<String, String> paramMap);


    /**
     * @param fwHsDOList
     * @return
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 新增房屋户室（批量）
     */
    List<FwHsDO> batchInsertFwHs(List<FwHsDO> fwHsDOList);

    /**
     * @param fwHsDOList
     * @param updateNull
     * @return
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 修改房屋户室（批量）
     */
    void batchUpdateFwHs(List<FwHsDO> fwHsDOList, boolean updateNull);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjdcbFwhsResponseDTO
     * @description 根据不动产单元号查询地籍调查表房屋信息
     */
    DjdcbFwhsResponseDTO queryDjdcbFwhsByBdcdyh(String bdcdyh);

    /**
     * @param pageable
     * @param paramMap
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询FWZHS
     */
    Page<FwHsHbZhsRequestDTO> listHsHbZhsByPage(Pageable pageable, Map<String, String> paramMap);

    /**
     * @param fwHsDO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据房屋户室实体查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByFwHsDO(FwHsDO fwHsDO);

    /**
     * @param fwHsIndexList
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据房屋户室主键集合查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByFwHsIndexList(List<String> fwHsIndexList);

    /**
     * @param bgbh
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据bgbh查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByBgbh(String bgbh);

    /**
     * @param fwHsIndex
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据fwHsIndex查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByFwHsIndex(String fwHsIndex);
    /**
     * @param jsonData
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据json查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByJson(String jsonData);
    /**
     * @param batchUpdateFwhsVO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据批量修改户室vo查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByBatchUpdateFwhsVO(BatchUpdateFwhsVO batchUpdateFwhsVO);

    /**
     * @param ybdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 原
     */
    List<FwHsDO> listKyFwhsByYbdcdyh(String ybdcdyh);

    /**
     * @param paramMap
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询实测预测户室信息，统一处理成fwhsdo
     * @date : 2021/4/7 15:57
     */
    Page<FwHsDO> listScYcHsByPage(Pageable pageable, Map<String, String> paramMap);

    /**
     * @param zl
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据zl查询户室基本信息
     */
    FwHsDO queryFwhsByZlAndBdcdyh(String zl, String bdcdyh);

    /**
     * @param houseid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据house_id 查询户室信息
     * @date : 2022/5/16 15:13
     */
    List<Map> queryFwhsByHouseId(String houseid);

    /**
     * @param bdcTddysfxxQO 更新土地抵押释放信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新土地抵押释放信息
     */
    void updateFwhsTddysfxx(BdcTddysfxxQO bdcTddysfxxQO);

    /**
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 上传ftp
     */
    void downloadFcfhtHefei(FhtDTO fhtDTO) throws IOException;
    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 根据bdcdyh从FTP下载户室图
     */
    List<String> getFcfhtFromFTP(String bdcdyh) throws IOException;
    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 从档案接口获取户室图上传到FTP并返回户室图base64字符串List
     */
    List<String> getFxfhtFromDaxx(String bdcdyh,String slbh,String bdcqzh,String qjgldm) throws IOException;
}
