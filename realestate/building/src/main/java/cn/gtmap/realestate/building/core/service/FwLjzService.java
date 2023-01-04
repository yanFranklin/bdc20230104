package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dto.building.FwlxBgRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/2
 * @description 逻辑幢服务接口
 */
public interface FwLjzService {
    /**
     * @param fwDcbIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键删除逻辑幢信息
     */
    Integer deleteLjzByFwDcbIndex(String fwDcbIndex,boolean withRelated);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwLjzDO
     * @return int
     * @description 根据数据记录删除逻辑幢信息
     */
    int deleteLjz(FwLjzDO fwLjzDO,boolean withRelated);

    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwLjzDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键查询逻辑幢
     */
    FwLjzDO queryLjzByFwDcbIndex(String fwDcbIndex);

    /**
     * @param fwLjzDO
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 新增逻辑幢信息
     */
    FwLjzDO insertLjz(FwLjzDO fwLjzDO);

    /**
     * @param fwLjzDo
     * @param updateNull true表示空字段更新，false，表示空字段不更新
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 修改逻辑幢信息
     */
    Integer updateLjz(FwLjzDO fwLjzDo, boolean updateNull);

    /**
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<Map>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询逻辑幢信息
     */
    Page<Map> listLjzByPage(Pageable pageable, Map map);

    /**
     * @param pageable
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询预测楼盘表
     * @date : 2020/12/28 17:16
     */
    Page<Map> listYcLjzByPage(Pageable pageable, Map map);

    /**
     * @param djh
     * @param zrzh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwLjzDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据宗地号  或 宗地号+自然幢号 查询逻辑幢列表
     */
    List<FwLjzDO> listLjzByDjhAndZrzh(String djh, String zrzh);

    /**
     * @param fwXmxxIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwLjzDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目信息主键查询逻辑幢信息
     */
    List<FwLjzDO> listLjzByFwXmxxIndex(String fwXmxxIndex);

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwLjzDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 查询逻辑幢基本信息
     */
    FwLjzDO queryLjzByBdcdyh(String bdcdyh);

    /**
     * @param bdcdyhList
     * @return
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据BDCDYH查询房逻辑幢流程状态
     */
    List<String> queryLjzLcztByBdcdyh(List<String> bdcdyhList);

    /**
     * @param fwDcbIndex
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据逻辑幢主键 查询 不动产单元FWLX
     */
    String queryBdcdyFwlxByFwDcbIndex(String fwDcbIndex);


    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param lszd
     * @return java.lang.String
     * @description 生成逻辑幢号
     */
    String creatLjzh(String lszd,Integer cs);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwLjzDO
     * @param updateNull
     * @return java.lang.Integer
     * @description 更新 FW_HS表 有BDCDYH变化，并且不知道原有BDCDYH情况
     */
    Integer updateFwLjzWithChangeBdcdyh(FwLjzDO fwLjzDO, boolean updateNull);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwLjzDO
     * @param updateNull
     * @param ybdcdyh
     * @return java.lang.Integer
     * @description 更新 FW_HS表 有BDCDYH变化，且知道原有BDCDYH情况（包括空的情况）
     */
    Integer updateFwLjzWithChangeBdcdyh(FwLjzDO fwLjzDO,boolean updateNull,String ybdcdyh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwLjzDO>
     * @description 根据DJH查询逻辑幢列表
     */
    List<FwLjzDO> listLjzByDjhOrderByZrzh(String djh);

    /**
     * @param fwLjzDO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据逻辑幢实体查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByFwLjzDO(FwLjzDO fwLjzDO);

    /**
     * @param fwDcbIndexList
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据逻辑幢主键集合查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByFwLjzIndexList(List<String> fwDcbIndexList);

    /**
     * @param fwDcbIndex
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据fwDcbIndex查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByFwDcbIndex(String fwDcbIndex);

    /**
     * @param fwlxBgRequestDTO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据房屋类型变更dto查询有效的不动产单元号（因为逻辑幢变更所以写这里）
     */
    List<String> listValidBdcdyhByFwlxBgRequestDTO(FwlxBgRequestDTO fwlxBgRequestDTO);
    /**
     * @param jsonData
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据json查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByJson(String jsonData);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ljzh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwLjzDO>
     * @description 根据LJZH查询逻辑幢列表
     */
    List<FwLjzDO> listKyFwljzByLjzh(String ljzh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ybdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwLjzDO>
     * @description 根据YBDCDYH 查询 独幢数据
     */
    List<FwLjzDO> listKyFwLjzByYbdcdyh(String ybdcdyh);
    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [slbh] 受理编号
     * @return: String 匹配过后的受理编号
     * @description 当受理编号为CH开头时，通过fw_ljz和s_sj_bgsh关联查询，采用s_sj_bgsh的slbh
     */
    String queryfwljzLinkBgsh(String slbh);


}
