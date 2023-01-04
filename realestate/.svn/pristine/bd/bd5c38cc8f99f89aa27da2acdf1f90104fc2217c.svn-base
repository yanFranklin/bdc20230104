package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlXqxxDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXqxxQO;
import org.springframework.web.bind.annotation.*;

/**
 * @program: realestate
 * @description: 需求流转rest服务
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2021-02-23 17:54
 **/
public interface BdcSlXqxxRestService {

    /**
     * @param bdcSlXqxxQO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询需求流转信息
     * @date : 2021/2/23 17:54
     */
    @PostMapping("/realestate-accept/rest/v1.0/xqxx/query")
    BdcSlXqxxDO queryBdcSlXqxx(@RequestBody BdcSlXqxxQO bdcSlXqxxQO);

    /**
     * @param bdcSlXqxxDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 保存需求流转信息
     * @date : 2021/2/23 17:54
     */
    @PostMapping("/realestate-accept/rest/v1.0/xqxx/save")
    BdcSlXqxxDO saveBdcSlXqxx(@RequestBody BdcSlXqxxDO bdcSlXqxxDO);

    /**
     * @param xqxxid,xmid
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除需求流转信息
     * @date : 2021/2/23 17:54
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/xqxx/delete")
    int deleteBdcSlXqxx(@RequestParam(value = "xqxxid", required = false) String xqxxid, @RequestParam(value = "xmid", required = false) String xmid);

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 用于删除流程时，同时删除需求流转信息内容
     * @date : 2021/2/23 17:54
     */
    @GetMapping("/realestate-accept/rest/v1.0/xqxx/remove")
    void deleteBdcSlXqxxByGzlslid(@RequestParam(value = "processInsId") String processInsId);
}
