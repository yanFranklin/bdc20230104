package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyLcztResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.LpxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.getWwsqBdcdyxx.response.WwsqBdcdyxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.gzwxxcx.response.GzwxxResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-23
 * @description 为受理子系统提供
 * 根据BDCLX分页查询BDCDY服务
 */
public interface AcceptBdcdyRestService {

    /**
     * @param pageable
     * @param qlxzAndZdtzm 权利性质及宗地特征码（BDCDYH 的 第13+14位） 多个用,隔开
     * @param zdtzm 宗地特征码(BDCDYH 的 第14位) 多个用,隔开
     * @param dzwtzm 定着物特征码(BDCDYH 的 第20位) 多个用,隔开
     * @param fwlx 判断查询类型 xmxx ljz hs ychs，空为全部
     * @param paramJson
     * @return Page<Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询土地及定着物类型不动产单元信息
     */
    @PostMapping("/building/rest/v1.0/accept/bdcdy/tddzwbypage")
    Page<Map> listTdAndDzwBdcdyByPage(Pageable pageable,
                                @RequestParam(name = "qlxzAndZdtzm",required = false) String qlxzAndZdtzm,
                                @RequestParam(name = "zdtzm",required = false) String zdtzm,
                                @RequestParam(name = "dzwtzm") String dzwtzm,
                                @RequestParam(name = "fwlx",required = false) String fwlx,
                                @RequestParam(name = "paramJson",required = false) String paramJson);

    /**
     * @param qlxzAndZdtzm 权利性质及宗地特征码（BDCDYH 的 第13+14位） 多个用,隔开
     * @param zdtzm 宗地特征码(BDCDYH 的 第14位) 多个用,隔开
     * @param dzwtzm 定着物特征码(BDCDYH 的 第20位) 多个用,隔开
     * @param fwlx 判断查询类型 xmxx ljz hs ychs，空为全部
     * @param paramJson
     * @return Page<Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 不分页查询土地及定着物类型不动产单元信息
     */
    @PostMapping("/building/rest/v1.0/accept/bdcdy/tddzw")
    List<Map> listTdAndDzwBdcdy(@RequestParam(name = "qlxzAndZdtzm",required = false) String qlxzAndZdtzm,
                                @RequestParam(name = "zdtzm",required = false) String zdtzm,
                                @RequestParam(name = "dzwtzm") String dzwtzm,
                                @RequestParam(name = "fwlx",required = false) String fwlx,
                                @RequestParam(name = "paramJson",required = false) String paramJson);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable
     * @param zdtzm
     * @param dzwtzm
     * @param paramJson
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @description 分页查询海域不动产单元信息
     */
    @PostMapping("/building/rest/v1.0/accept/bdcdy/hybypage")
    Page<Map> listHyBdcdyByPage(Pageable pageable,
                                   @RequestParam(name = "zdtzm",required = false) String zdtzm,
                                   @RequestParam(name = "dzwtzm",required = false) String dzwtzm,
                                   @RequestParam(name = "paramJson",required = false) String paramJson);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zdtzm
     * @param dzwtzm
     * @param paramJson
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @description 不分页查询海域不动产单元信息
     */
    @PostMapping("/building/rest/v1.0/accept/bdcdy/hy")
    List<Map> listHyBdcdy(@RequestParam(name = "zdtzm",required = false) String zdtzm,
                                @RequestParam(name = "dzwtzm",required = false) String dzwtzm,
                                @RequestParam(name = "paramJson",required = false) String paramJson);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable
     * @param zdtzm
     * @param dzwtzm
     * @param paramJson
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @description 分页查询构筑物不动产单元信息
     */
    @PostMapping("/building/rest/v1.0/accept/bdcdy/gzwbypage")
    Page<Map> listGzwBdcdyByPage(Pageable pageable,
                                 @RequestParam(name = "zdtzm",required = false) String zdtzm,
                                 @RequestParam(name = "dzwtzm",required = false) String dzwtzm,
                                 @RequestParam(name = "paramJson",required = false) String paramJson);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zdtzm
     * @param dzwtzm
     * @param paramJson
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @description 不分页查询构筑物不动产单元信息
     */
    @PostMapping("/building/rest/v1.0/accept/bdcdy/gzw")
    List<Map> listGzwBdcdy(@RequestParam(name = "zdtzm",required = false) String zdtzm,
                                 @RequestParam(name = "dzwtzm",required = false) String dzwtzm,
                                 @RequestParam(name = "paramJson",required = false) String paramJson);


    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页查询承包宗地不动产单元信息(以CBZD_DCBCBF_REL为主表)
     */
    @PostMapping("/building/rest/v1.0/accept/bdcdy/cbzdbypage")
    Page<Map> listCbzdBdcdyByPage(Pageable pageable,
                                 @RequestParam(name = "zdtzm",required = false) String zdtzm,
                                 @RequestParam(name = "dzwtzm",required = false) String dzwtzm,
                                 @RequestParam(name = "paramJson",required = false) String paramJson);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 不分页查询承包宗地不动产单元信息(以CBZD_DCBCBF_REL为主表)
     */
    @PostMapping("/building/rest/v1.0/accept/bdcdy/cbzd")
    List<Map> listCbzdBdcdy(@RequestParam(name = "zdtzm",required = false) String zdtzm,
                           @RequestParam(name = "dzwtzm",required = false) String dzwtzm,
                           @RequestParam(name = "paramJson",required = false) String paramJson);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 分页或列表查询不动产单元信息
      */
    @PostMapping("/building/rest/v1.0/accept/bdcdy/common")
    Object listBdcdyhByPageOrList(Pageable pageable,
                                  @RequestParam(name = "paramJson",required = false) String paramJson,
                                  @RequestParam(name = "loadpage",required = false) Boolean loadpage);





    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyLcztResponseDTO
     * @description 根据BDCDYH查询流程状态
     */
    @GetMapping("/building/rest/v1.0/accept/bdcdylczt/{bdcdyh}")
    BdcdyLcztResponseDTO queryBdcdyLczt(@PathVariable(name = "bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required =false) String qjgldm);

    /**
     * @param gzlslid
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwLjzDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据工作流实例ID查询当前项目下的所有房屋逻辑幢
     */
    @GetMapping("/building/rest/v1.0/accept/listljzbygzlslid/{gzlslid}")
    List<FwLjzDO> listFwLjzByGzlslid(@PathVariable(name = "gzlslid") String gzlslid,@RequestParam(name = "qjgldm", required =false) String qjgldm);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/1/14 15:47
     */
    @GetMapping("/building/rest/v1.0/accept/listljzbygzlslid/{gzlslid}/fzhsxx")
    LpxxDTO listFwLjzFzHsxx(@PathVariable(name = "gzlslid") String gzlslid,@RequestParam(name = "qjgldm", required =false) String qjgldm);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据施工编号查询坐落
     */
    @PostMapping(value = "/building/rest/v1.0/accept/sggadz/zl")
    String queryZlBySgbh(@RequestParam(name = "sgbh")String sgbh);

    /**
     * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @param: xmids 受理项目id
     * @return: map 按幢分组
     * @description 根据不动产单元号查询逻辑幢信息，用于购物车发证方式任意组合时按幢分组
     */
    @PostMapping("/building/rest/v1.0/accept/gwc")
    Map<String, List<BdcSlYwxxDTO>> getLjzxx(@RequestBody List<BdcSlYwxxDTO> bdcSlYwxxDTOList,@RequestParam(name = "qjgldm", required =false) String qjgldm);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据不动产单元号获取权属性质
     */
    @GetMapping("/building/rest/v1.0/accept/qsxz")
    String getQsxzByBdcdyh(@RequestParam(name = "bdcdyh") String bdcdyh);

    /**
     * @param
     * @return
     * @author
     * @description 获取区县下林地总面积和宗地数量
     */
    @GetMapping("/building/rest/v1.0/accept/getLdmjAndZd")
    List<Map>  getLdmjAndZd(@RequestParam(name = "qxdm") String qxdm);

    /**
     * @param paramJson 查询条件JSON字符串（WwsqBdcdyxxQO）
     * @return 外网申请不动产单元信息分页结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页查询不动产单元信息提供给外网申请
     */
    @PostMapping("/building/rest/v1.0/accept/wwsq/bdcdy")
    Page<WwsqBdcdyxxDTO> listWwsqBdcdyxxDTOByPage(Pageable pageable, @RequestParam(name = "paramJson",required = false) String paramJson);

    /**
     * @param paramJson 查询条件JSON字符串（GzwxxcxQO）
     * @return 外网申请构筑物信息分页结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页查询构筑物信息提供给外网申请
     */
    @PostMapping("/building/rest/v1.0/accept/wwsq/gzwxx")
    Page<GzwxxResponseDTO> listWwsqGzwxxDTOByPage(Pageable pageable, @RequestParam(name = "paramJson",required = false) String paramJson);
}
