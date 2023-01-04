package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcByslDO;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @version 1.0, 2022/08/24.
 * @description Accept不予登记不予受理Rest接口服务
 */
public interface BdcBysldjRestService {

    /**
     * @description 根据工作流实例ID获取不予受理信息
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/bysl/{gzlslid}")
    List<BdcByslDO> queryBdcByslDOByGzlslid(@PathVariable(value = "gzlslid") String gzlslid);




    /**
     * @param byslid 不予受理ID
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 依据不予受理ID删除不予受理信息
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/bysl/deletebybysl/{byslid}")
    Integer deleteBdcByslDOByByslid(@PathVariable(value = "byslid") String byslid);


    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 依据工作流实例ID删除不予受理信息
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/bysl/deletebygzl/{gzlslid}")
    Integer deleteBdcByslDOByGzlslid(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param bdcPdfDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据受理编号更新当前不予受理流程的附件材料
     * @date : 2022/8/25 10:32
     */
    @PostMapping("/realestate-accept/rest/v1.0/bysl/fjxx")
    void updateByslSjcl(@RequestBody BdcPdfDTO bdcPdfDTO);

    /**
     * @param processInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 工作流事件，推送不予受理/登记信息到签章台账
     * @date : 2022/8/25 16:05
     */
    @GetMapping("/realestate-accept/rest/v1.0/bysl/tsqz")
    void tsByslDzqz(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 依据工作流实例ID更新不予受理信息
     */
    @PutMapping("/realestate-accept/rest/v1.0/bysl/savebygzl")
    Integer saveBdcByslDOByGzlslid(@RequestBody String json);

}
