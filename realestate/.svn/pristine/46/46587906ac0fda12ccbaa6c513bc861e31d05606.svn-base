package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.FwZhsDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/8
 * @description 子户室相关服务
 */
public interface FwZhsRestService {


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwZhsDO>
     * @description 根据BDCDYH查询子户室列表
     */
    @GetMapping("/building/rest/v1.0/zhs/bybdcdyh/{bdcdyh}")
    List<FwZhsDO> listFwZhsByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwZhsIndexList
     * @return void
     * @description 批量删除子户室
     */
    @DeleteMapping("/building/rest/v1.0/zhs/batchdel")
    void batchDelFwzhs(@RequestBody List<String> fwZhsIndexList,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwZhsIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键删除子户室信息
     */
    @DeleteMapping("/building/rest/v1.0/zhs/{fwZhsIndex}")
    void deleteZhsByFwZhsIndex(@PathVariable("fwZhsIndex") String fwZhsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwZhsDO
     * @return cn.gtmap.realestate.common.core.domain.building.FwZhsDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 新增子户室服务
     */
    @PostMapping("/building/rest/v1.0/zhs")
    FwZhsDO insertZhs(@RequestBody FwZhsDO fwZhsDO,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwZhsDO
     * @return java.lang.Integer
     * @description 更新子户室
     */
    @PutMapping("/building/rest/v1.0/zhs")
    Integer updateZhs(@RequestBody FwZhsDO fwZhsDO,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwZhsIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwZhsDO
     * @description 根据主键查询子户室
     */
    @GetMapping("/building/rest/v1.0/zhs/{fwZhsIndex}")
    FwZhsDO getFwzhsByIndex(@PathVariable("fwZhsIndex") String fwZhsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwZhsDOList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量新增子户室
     */
    @PostMapping("/building/rest/v1.0/zhs/batchadd")
    List<FwZhsDO> batchInsert(@RequestBody List<FwZhsDO> fwZhsDOList,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwZhsDOList
     * @param fwHsIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 关联子户室
     */
    @PutMapping("/building/rest/v1.0/zhs/relevance/{fwHsIndex}")
    void relevanceZhs(@RequestBody List<FwZhsDO> fwZhsDOList,
                      @PathVariable("fwHsIndex") String fwHsIndex
            ,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwZhsDOList
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 取消关联子户室
     */
    @PutMapping("/building/rest/v1.0/zhs/cancelRelevance")
    void cancelRelevanceZhs(@RequestBody List<FwZhsDO> fwZhsDOList,@RequestParam(name = "qjgldm", required = false) String qjgldm);



    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable
     * @param fwHsIndex
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.building.FwZhsDO>
     * @description 分页查询子户室
     */
    @PostMapping("/building/rest/v1.0/zhs/listbypage")
    Page<FwZhsDO> listFwZhsByPage(Pageable pageable,
                                  @RequestParam(name = "fwHsIndex", required = false) String fwHsIndex,
            @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwZhsDO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据子户室信息实体查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/zhs/validbdcdyh/fwzhsdo")
    List<String> listValidBdcdyhByFwZhsDO(@RequestBody FwZhsDO fwZhsDO,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwZhsIndexList
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据子户室主键list查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/zhs/validbdcdyh/indexlist")
    List<String> listValidBdcdyhByFwZhsIndexList(@RequestParam(value = "fwZhsIndexList", required = false) List<String> fwZhsIndexList,@RequestParam(name = "qjgldm", required = false) String qjgldm);
}
