package cn.gtmap.realestate.building.service;

import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyLcztResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.LpxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.getWwsqBdcdyxx.response.WwsqBdcdyxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.gzwxxcx.request.GzwxxcxQO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.gzwxxcx.response.GzwxxResponseDTO;
import cn.gtmap.realestate.common.core.qo.building.WwsqBdcdyxxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-23
 * @description 为受理子系统提供根据BDCLX分页查询BDCDY服务
 */
public interface AcceptBdcdyService {

    /**
     * @param pageable
     * @param qlxzAndZdtzm BDCDYH 的 第13+14位
     * @param zdtzm BDCDYH 的 第14位
     * @param dzwtzm BDCDYH 的 第20位
     * @param fwlx xmxx,ljz,hs,ychs
     * @param paramMap
     * @return Page<Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页土地及定着物
     */
    Page<Map> listTdAndDzwBdcdyByPage(Pageable pageable, String qlxzAndZdtzm,String zdtzm,
                                String dzwtzm, String fwlx, Map<String, Object> paramMap);

    /**
     * @param qlxzAndZdtzm BDCDYH 的 第13+14位
     * @param zdtzm BDCDYH 的 第14位
     * @param dzwtzm BDCDYH 的 第20位
     * @param fwlx xmxx,ljz,hs,ychs
     * @param paramMap
     * @return Page<Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 不分页 土地及定着物
     */
    List<Map> listTdAndDzwBdcdy(String qlxzAndZdtzm,String zdtzm, String dzwtzm,
                                String fwlx, Map<String, Object> paramMap);


    /**
     * @param pageable
     * @param zdtzm
     * @param dzwtzm
     * @param paramMap
     * @return Page<Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页海域及其定着物
     */
    Page<Map> listHyBdcdyByPage(Pageable pageable, String zdtzm,
                                   String dzwtzm, Map<String, Object> paramMap);

    /**
     * @param zdtzm
     * @param dzwtzm
     * @param paramMap
     * @return Page<Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 不分页 海域及其定着物
     */
    List<Map> listHyBdcdy(String zdtzm,
                                String dzwtzm, Map<String, Object> paramMap);


    /**
     * @param pageable
     * @param zdtzm
     * @param dzwtzm
     * @param paramMap
     * @return Page<Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页构筑物
     */
    Page<Map> listGzwBdcdyByPage(Pageable pageable, String zdtzm,
                                   String dzwtzm, Map<String, Object> paramMap);

    /**
     * @param zdtzm
     * @param dzwtzm
     * @param paramMap
     * @return Page<Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 不分页构筑物
     */
    List<Map> listGzwBdcdy(String zdtzm,
                                 String dzwtzm, Map<String, Object> paramMap);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页查询承包宗地不动产单元信息
     */
    Page<Map> listCbzdBdcdyByPage(Pageable pageable, String zdtzm,
                                 String dzwtzm, Map<String, Object> paramMap);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 不分页查询承包宗地不动产单元信息
     */
    List<Map> listCbzdBdcdy(String zdtzm,
                           String dzwtzm, Map<String, Object> paramMap);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyLcztResponseDTO
     * @description 根据BDCDYH 查询流程状态
     */
    BdcdyLcztResponseDTO queryBdcdyLczt(String bdcdyh);

    /**
     * @param gzlslid
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwLjzDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 工作流实例ID 查询所有LJZ
     */
    List<FwLjzDO> listFwLjzByGzlslid(String gzlslid);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 工作流实例ID 查询所有LJZ,且计算当前所有的户室信息，并根据户室用途和终止日期信息分组
     * @date : 2021/1/14 14:35
     */
    LpxxDTO listFwljzFzxx(String gzlslid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据施工编号查询坐落
     */
    String queryZlBySgbh(String sgbh);

    /**
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @param: xmids 受理项目id
     * @return: map
     * @description 查询幢信息，用于购物车发证方式任意组合时按幢分组
     */
    Map<String, List<BdcSlYwxxDTO>> getLjzxx(List<BdcSlYwxxDTO> bdcSlYwxxDTOList);

    /**
     * @param bdcdyh 不动产单元号
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据不动产单元号获取权属性质
     */
    String getQsxzByBdcdyh(String bdcdyh);

    /**
     * @param
     * @return
     * @author
     * @description 获取区县下林地总面积和宗地数量
     */
    List<Map> getLdmjAndZd(List<String> qxdmList);

    /**
     * @param wwsqBdcdyxxQO 不动产单元查询QO
     * @return 外网申请不动产单元信息分页结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页查询不动产单元信息提供给外网申请
     */
    Page<WwsqBdcdyxxDTO> listWwsqBdcdyxxDTOByPage(Pageable pageable, WwsqBdcdyxxQO wwsqBdcdyxxQO);

    /**
     * @param gzwxxcxQO 构筑物查询QO
     * @return 外网申请构筑物信息分页结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页查询构筑物信息提供给外网申请
     */
    Page<GzwxxResponseDTO> listWwsqGzwxxDTOByPage(Pageable pageable, GzwxxcxQO gzwxxcxQO);


    /**
     * @param pageable
     * @param qlxzAndZdtzm BDCDYH 的 第13+14位
     * @param zdtzm BDCDYH 的 第14位
     * @param dzwtzm BDCDYH 的 第20位
     * @param fwlx xmxx,ljz,hs,ychs
     * @param paramMap
     * @return Page<Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页历史土地及定着物
     */
    Page<Map> listLsTdAndDzwBdcdyByPage(Pageable pageable, String qlxzAndZdtzm,String zdtzm,
                                      String dzwtzm, String fwlx, Map<String, Object> paramMap);
}
