package cn.gtmap.realestate.common.core.service.rest.init;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/3/1
 * @description 不动产业务数据回写接口
 */
public interface BdcYwsjHxRestService {
    /**
     * @param gzlslid
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询业务数据
     */
    @GetMapping(value = "/init/rest/v1.0/{gzlslid}/ywsj")
    List<Map<String, Object>> queryBdcYwsj(@PathVariable("gzlslid") String gzlslid) throws Exception;

    /**
     *  更新流程扩展属性
     * @param gzlslid
     * @param params key: 表列名称  object：属性
     */
    @PatchMapping("/init/rest/v1.0/update/bdcywsj")
    public void updateBdcYwsj(@RequestParam("gzlslid")String gzlslid, @RequestBody Map<String, Object> params);

    /**
     * @param gzlslid
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存/更新组织的业务数据
     */
    @PutMapping(value = "/init/rest/v1.0/{gzlslid}/ywsj")
    void saveBdcYwsj(@PathVariable("gzlslid") String gzlslid) throws Exception;
    /**
     * @param gzlslid
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除业务数据
     */
    @DeleteMapping(value = "/init/rest/v1.0/{gzlslid}/ywsj")
    void deleteBdcYwsj(@PathVariable("gzlslid")String gzlslid) throws Exception;
}
