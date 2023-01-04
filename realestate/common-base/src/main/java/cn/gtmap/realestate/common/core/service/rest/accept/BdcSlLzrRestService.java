package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlLzrDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/1/15
 * @description 不动产受理领证人rest服务
 */
public interface BdcSlLzrRestService {

    /**
     * 根据领证人人ID获取不动产受理领证人
     *
     * @param lzrid 领证人人ID
     * @return 不动产受理领证人DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/lzr/{lzrid}")
    BdcSlLzrDO queryBdcSlLzrByLzrid(@PathVariable(value = "lzrid") String lzrid);

    /**
     * 根据项目ID获取不动产受理领证人
     *
     * @param xmid 项目ID
     * @return 不动产受理领证人
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/lzr/list/{xmid}")
    List<BdcSlLzrDO> listBdcSlLzrByXmid(@PathVariable(value = "xmid") String xmid);

    /**
     * 新增不动产受理领证人
     *
     * @param bdcSlLzrDO 不动产受理领证人
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/lzr/")
    BdcSlLzrDO insertBdcSlLzr(@RequestBody BdcSlLzrDO bdcSlLzrDO);

    /**
     * 更新不动产受理领证人
     *
     * @param bdcSlLzrDO 不动产受理领证人
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PutMapping("/realestate-accept/rest/v1.0/lzr/")
    Integer updateBdcSlLzr(@RequestBody BdcSlLzrDO bdcSlLzrDO);

    /**
     * 根据领证人人ID删除不动产受理领证人
     *
     * @param lzrid 领证人人ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/lzr/{lzrid}")
    Integer deleteBdcSlLzrByLzrid(@PathVariable(value = "lzrid") String lzrid);

    /**
     * 根据项目ID删除不动产受理领证人
     *
     * @param xmid 项目ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/lzr/list/{xmid}")
    Integer deleteBdcSlLzrByXmid(@PathVariable(value = "xmid") String xmid);

    /**
     * 批量修改领证人（用于批量页面）
     *
     * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @param: json 领证人JSON
     * @param: processInsId 流程实例ID
     * @param: xmid 项目ID
     * @return: Integer 修改数量
     */
    @PatchMapping(value = "/realestate-accept/rest/v1.0/lzr/list/pllc")
    Integer updatePlBdcLzr(@RequestBody String json, @RequestParam("processInsId") String processInsId,
                           @RequestParam("djxl") String djxl) throws Exception;
}
