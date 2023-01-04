package cn.gtmap.realestate.common.core.service.rest.engine;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcGzlwShDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcGzlwShLogDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcGzlwShQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/8/26
 * @description
 */
public interface BdcGzlwRestService {
    /**
     * @param bdcGzlwShDO 规则例外审核信息
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 增加审核信息
     */
    @PostMapping("/realestate-engine/rest/v1.0/addShxxData")
    void addShxxData(@RequestBody BdcGzlwShDO bdcGzlwShDO);

    /**
     * @param pageable
     * @param paramJson
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 查询审核信息
     */
    @PostMapping("/realestate-engine/rest/v1.0/queryBdcGzlw")
    Page<Map> queryBdcGzlw(Pageable pageable,
                           @RequestParam(value = "paramJson", required = false) String paramJson);

    /**
     * @param pageable
     * @param paramJson
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 查询审核信息
     */
    @PostMapping("/realestate-engine/rest/v1.0/bdcgzlwGroupByBdcdyh")
    Page<Map> bdcgzlwGroupByBdcdyh(Pageable pageable,
                           @RequestParam(value = "paramJson", required = false) String paramJson);

    /**
     * @param bdcGzlwShDOList
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 更改规则例外信息
     */
    @PostMapping("/realestate-engine/rest/v1.0/updateBdcGzlwxx")
    Integer updateBdcGzlwxx(@RequestBody List<BdcGzlwShDO> bdcGzlwShDOList);

    /**
     * @param bdcGzlwShDO
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 审核审核信息
     */
    @PostMapping("/realestate-engine/rest/v1.0/updateBdcGzlw")
    Integer updateBdcGzlw(@RequestBody BdcGzlwShDO bdcGzlwShDO);

    /**
     * @param bdcGzlwShLogDOList
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 新增审核日志信息
     */
    @PostMapping("/realestate-engine/rest/v1.0/addBdcGzlwLog")
    Integer addBdcGzlwLog(@RequestBody List<BdcGzlwShLogDO> bdcGzlwShLogDOList);

    /**
     * @param gzlslid
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 通过工作流实例id 获取审核通过的信息
     */
    @PostMapping("/realestate-engine/rest/v1.0/queryBdcGzlwSh")
    List<BdcGzlwShDO> queryBdcGzlwSh(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param gzlwid
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 通过规则例外id 删除规则例外信息
     */
    @PostMapping("/realestate-engine/rest/v1.0/deleteBdcGzlwSh")
    void deleteBdcGzlwSh(@RequestParam(value = "gzlwid") String gzlwid);

    /**
     * @param processInsId
     * @param currentUserName
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 工作流实例id 更新审核状态
     */
    @PostMapping("/realestate-engine/rest/v1.0/updateBdcGzlwShzt")
    void updateBdcGzlwShzt(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName);


    /**
     * @param gzlslid
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过gzlslid 删除所有相关的规则例外信息
     */
    @PostMapping("/realestate-engine/rest/v1.0/delete/all/bdcGzlwSh")
    void deleteBdcGzlwShByGzlslid(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param bdcGzlwShDO
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 通过gzlslid 删除当前所有的规则例外信息
     */
    @PostMapping("/realestate-engine/rest/v1.0/delete/bdcGzlwSh")
    void deleteBdcGzlwShByGzlw(@RequestBody BdcGzlwShDO bdcGzlwShDO);

    /**
     * @param bdcXmDO
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 查询所有拥有同样权利、不动产单元号的不动产项目
     */
    @PostMapping("/realestate-engine/rest/v1.0/query/all/bdcXmXzql")
    List<BdcXmDO> queryAllBdcXmXzql(@RequestBody BdcXmDO bdcXmDO);

    /**
     * 通过不动产规则例外审核QO查询规则例外信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcGzlwShQO 不动产规则例外审核QO
     * @return List<BdcGzlwShDO>
     */
    @PostMapping("/realestate-engine/rest/v1.0/listBdcGzlwByQO")
    List<BdcGzlwShDO> listBdcGzlwByBdcGzlwShQO(@RequestBody BdcGzlwShQO bdcGzlwShQO);

    /**
     * @param bdcGzlwShDOList 规则例外数据
     * @param slbh 受理编号
     * @param lwyy 例外原因
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量添加审核信息
     */
    @PostMapping("/realestate-engine/rest/v1.0/addShxxData/pl")
    BdcGzlwShDO addShxxDataPl(@RequestBody List<BdcGzlwShDO> bdcGzlwShDOList,
                              @RequestParam(value = "slbh") String slbh,
                              @RequestParam(value = "lwyy") String lwyy);

}
