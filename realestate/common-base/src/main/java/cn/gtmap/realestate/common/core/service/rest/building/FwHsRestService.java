package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.dto.building.FwHsHouseIdDTO;
import cn.gtmap.realestate.common.core.qo.building.BdcTddysfxxQO;
import cn.gtmap.realestate.common.core.dto.building.DjdcbFwhsResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.FwHsHbZhsRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.FwhsBatchUpdateRequestDTO;
import cn.gtmap.realestate.common.core.vo.building.BatchUpdateFwhsVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-08
 * @description 房屋户室相关服务
 */
public interface FwHsRestService {

    /**
     * @param fwHsDO
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 新增户室服务
     */
    @PostMapping("/building/rest/v1.0/hs")
    FwHsDO insertFwHs(@RequestBody FwHsDO fwHsDO,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwHsDO
     * @param updateNull true表示空字段更新，false，false表示空字段不更新
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改户室信息服务
     */
    @PutMapping("/building/rest/v1.0/hs")
    Integer updateFwHs(@RequestBody FwHsDO fwHsDO, @RequestParam(name = "updateNull", required = false) boolean updateNull,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwHsIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键删除户室信息
     */
    @DeleteMapping("/building/rest/v1.0/hs/{fwHsIndex}")
    Integer deleteHsByFwHsIndex(@PathVariable("fwHsIndex") String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询户室基本信息
     */
    @GetMapping("/building/rest/v1.0/hs/bdcdy/{bdcdyh}")
    FwHsDO queryFwhsByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsHouseIdDTO
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 根据BDCDYH查询户室houseid信息
     */
    @GetMapping("/building/rest/v1.0/hs/bdcdy/houseid/{bdcdyh}")
    FwHsHouseIdDTO queryFwhsHouseIdByBdcdyh(@PathVariable("bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bdcdyhList
     * @return
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据BDCDYH查询权籍流程状态
     */
    @PostMapping("/building/rest/v1.0/qj/lczt")
    Map<String, List<String>> queryLcztByBdcdyh(@RequestBody List<String> bdcdyhList);

    /**
     * @param bdcdyhList
     * @return
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据BDCDYH查询房屋户室流程状态
     */
    @PostMapping("/building/rest/v1.0/hs/lczt")
    List<String> queryFwhsLcztByBdcdyh(@RequestBody List<String> bdcdyhList);

    /**
     * @param zl
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据zl查询户室基本信息
     */
    @GetMapping("/building/rest/v1.0/hs/zl")
    FwHsDO queryFwhsByZlAndBdcdyh(@RequestParam(name = "zl", required = false) String zl,@RequestParam(name = "bdcdyh", required = false)String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);
    /**
     * @param fwDcbIndex
     * @param fwHsIndexList
     * @return java.lang.Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改房屋户室逻辑幢
     */
    @PutMapping("/building/rest/v1.0/hs/ljz")
    void updateFwHsLjz(@RequestParam(value = "fwHsIndexList", required = false) List<String> fwHsIndexList, @RequestParam(value = "fwDcbIndex", required = false) String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fcdah
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @description 根据FCDAH查询FWHS
     */
    @GetMapping("/building/rest/v1.0/hs/fcdah/{fcdah}")
    List<FwHsDO> listFwhsByFcdah(@PathVariable("fcdah") String fcdah,@RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwbm
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @description 根据FWBM 查询 FWHS
     */
    @GetMapping("/building/rest/v1.0/hs/fwbm/{fwbm}")
    List<FwHsDO> listFwhsByFwbm(@PathVariable("fwbm") String fwbm,@RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwDcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @description 根据 fwDcbIndex 查询 FWHS
     */
    @GetMapping("/building/rest/v1.0/hs/fwdcbindex/{fwDcbIndex}")
    List<FwHsDO> listFwhsByFwDcbIndex(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @description 根据主键查询房屋户室
     */
    @GetMapping("/building/rest/v1.0/hs/{fwHsIndex}")
    FwHsDO queryFwHsByFwHsIndex(@PathVariable("fwHsIndex") String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable
     * @param paramJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @description
     */
    @PostMapping("/building/rest/v1.0/hs/listbypage")
    Page<FwHsDO> listFwHsByPageJson(Pageable pageable,
                                    @RequestParam(value = "paramJson", required = false) String paramJson);
    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable
     * @param paramJson
     * @return Map
     * @description
     */
    @PostMapping("/building/rest/v1.0/hs/gl/listbypage")
    Page<Map> glListFwHsByPageJson(Pageable pageable,
                                   @RequestParam(value = "paramJson", required = false) String paramJson);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param batchUpdateRequestDTO
     * @return void
     * @description 批量更新户室
     */
    @PutMapping("/building/rest/v1.0/hs/batchupdate")
    void batchUpdateFwhs(@RequestBody FwhsBatchUpdateRequestDTO batchUpdateRequestDTO);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjdcbFwhsResponseDTO
     * @description 根据不动产单元号查询地籍调查表房屋信息
     */
    @GetMapping("/building/rest/v1.0/hs/djdcb/bdcdyh")
    DjdcbFwhsResponseDTO queryDjdcbFwhsByBdcdyh(@RequestParam(value = "bdcdyh", required = false) String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable
     * @param paramJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @description
     */
    @PostMapping("/building/rest/v1.0/hs/zhslistbypage")
    Page<FwHsHbZhsRequestDTO> listHsHbZhsByPageJson(Pageable pageable,
                                                    @RequestParam(value = "paramJson", required = false) String paramJson);

    /**
     * @param fwHsDO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据房屋户室实体查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/hs/vaildbdcdyh/fwhsdo")
    List<String> listValidBdcdyhByFwHsDO(@RequestBody FwHsDO fwHsDO,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwHsIndexList
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据房屋户室主键集合查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/hs/vaildbdcdyh/fwhsindexlist")
    List<String> listValidBdcdyhByFwHsIndexList(@RequestBody List<String> fwHsIndexList,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bgbh
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据bgbh查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/hs/vaildbdcdyh/bgbh")
    List<String> listValidBdcdyhByBgbh(@RequestBody String bgbh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwHsIndex
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据fwHsIndex查询有效的不动产单元号
     */
    @GetMapping("/building/rest/v1.0/hs/vaildbdcdyh/fwHsIndex")
    List<String> listValidBdcdyhByFwHsIndex(@PathVariable("fwHsIndex") String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param jsonData
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据json查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/hs/vaildbdcdyh/json")
    List<String> listValidBdcdyhByJson(@RequestBody String jsonData,@RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @param batchUpdateFwhsVO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据批量修改户室vo查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/hs/vaildbdcdyh/batchfwhsvo")
    List<String> listValidBdcdyhByBatchUpdateFwhsVO(@RequestBody BatchUpdateFwhsVO batchUpdateFwhsVO);

    /**
     * @param fwbm 房屋编码
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 只根据房屋编码参数查询房屋户室信息
     */
    @GetMapping("/building/rest/v1.0/hs/onlyfwbm/{fwbm}")
    List<FwHsDO> queryFwhsOnlyByFwbm(@PathVariable("fwbm") String fwbm,@RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @param paramJson
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询实测预测户室信息
     * @date : 2021/4/7 15:55
     */
    @PostMapping("/building/rest/v1.0/hs/scycbypage")
    Page<FwHsDO> listFwScYcHsByPageJson(Pageable pageable,
                                        @RequestParam(value = "paramJson", required = false) String paramJson);

    /**
     * @param bdcTddysfxxQO 更新土地抵押释放信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新土地抵押释放信息
     */
    @PutMapping("/building/rest/v1.0/hs/tddysfxx/pl")
    void updateFwhsTddysfxx(@RequestBody BdcTddysfxxQO bdcTddysfxxQO);


}
