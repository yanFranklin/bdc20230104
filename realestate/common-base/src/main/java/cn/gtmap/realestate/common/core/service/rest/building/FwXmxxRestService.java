package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import cn.gtmap.realestate.common.core.dto.building.XmxxResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-08
 * @description 项目信息服务
 */
public interface FwXmxxRestService {

    /**
     * @param pageable
     * @param paramJson
     * @return java.lang.Object
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询项目信息
     */
    @PostMapping("/building/rest/v1.0/xmxx/page")
    Page<XmxxResponseDTO> listXmxxByPageJson(Pageable pageable,
                                             @RequestParam(value = "paramJson", required = false) String paramJson);

    /**
     * @param fwXmxxDO
     * @return cn.gtmap.realestate.common.core.domain.building.FwXmxxDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 新增项目信息
     */
    @PostMapping("/building/rest/v1.0/xmxx")
    FwXmxxDO insertFwXmxx(@RequestBody FwXmxxDO fwXmxxDO,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwXmxxDO
     * @param updateNull true表示空字段更新，false表示空字段不更新
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 修改项目信息(预留type字段, 根据type确定是修改还是变更)
     */
    @PutMapping("/building/rest/v1.0/xmxx")
    Integer updateFwXmxx(@RequestBody FwXmxxDO fwXmxxDO, @RequestParam(name = "updateNull",required = false) boolean updateNull,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwXmxxIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 删除项目信息
     */
    @DeleteMapping("/building/rest/v1.0/xmxx/{fwXmxxIndex}")
    Integer deleteXmxxByFwXmxxIndex(@PathVariable("fwXmxxIndex") String fwXmxxIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwXmxxIndexList
     * @param delFwK
     * @return void
     * @description 批量删除
     */
    @PostMapping("/building/rest/v1.0/xmxx/batchdel")
    void batchDelFwXmxxIndex(@RequestParam(value = "fwXmxxIndexList") List<String> fwXmxxIndexList,
                             @RequestParam(value = "delFwK", required = false) boolean delFwK,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwXmxxIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwXmxxDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据主键查询项目信息实体
     */
    @GetMapping("/building/rest/v1.0/xmxx/{fwXmxxIndex}")
    FwXmxxDO queryXmxxByFwXmxxIndex(@PathVariable("fwXmxxIndex") String fwXmxxIndex, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwXmxxIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwXmxxDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据bdcdyh查询项目信息实体
     */
    @GetMapping("/building/rest/v1.0/xmxx/bdcdyh")
    FwXmxxDO queryXmxxByBdcdyh(@RequestParam(name = "bdcdyh", required = false) String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwXmxxIndex
     * @return java.lang.Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 项目信息关联逻辑幢
     */
    @GetMapping("/building/rest/v1.0/xmxx/relevanceljz")
    Integer relevanceLjz(@RequestParam(name = "fwXmxxIndex", required = false) String fwXmxxIndex,
                         @RequestParam(name = "fwDcbIndex", required = false) String fwDcbIndex, @RequestParam(name = "qjgldm", required = false) String qjgldm);
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwDcbIndex
     * @return java.lang.Integer
     * @description 项目信息取消关联逻辑幢
     */
    @GetMapping("/building/rest/v1.0/xmxx/cancelrelevanceljz")
    Integer cancelRelevanceLjz(@RequestParam(name = "fwDcbIndex",required = false) String fwDcbIndex,
                               @RequestParam(name = "bdcdyfwlx",required = false) String bdcdyfwlx,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwXmxxDO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目信息实体查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/xmxx/validbdcdyh/xmxxdo")
    List<String> listValidBdcdyhByXmxxDO(@RequestBody FwXmxxDO fwXmxxDO,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwXmxxIndexList
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目信息主键list查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/xmxx/validbdcdyh/indexlist")
    List<String> listValidBdcdyhByXmxxList(@RequestBody List<String> fwXmxxIndexList,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwXmxxIndex
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目信息主键list查询有效的不动产单元号
     */
    @GetMapping("/building/rest/v1.0/xmxx/validbdcdyh/{fwXmxxIndex}")
    List<String> listValidBdcdyhByXmxxIndex(@PathVariable("fwXmxxIndex") String fwXmxxIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param jsonData
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据json查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/xmxx/vaildbdcdyh/json")
    List<String> listValidBdcdyhByJson(@RequestBody String jsonData,@RequestParam(name = "qjgldm", required = false) String qjgldm);
}
