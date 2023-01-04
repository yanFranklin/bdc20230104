package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlYwlzDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 受理业务流转restservice
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-09-22 11:17
 **/
public interface BdcSlYwlzRestService {

    /**
     * @param bdcSlYwlzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询业务流转信息
     * @date : 2021/9/22 11:25
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/ywlz/list")
    List<BdcSlYwlzDO> listBdcSlYwlz(@RequestBody BdcSlYwlzDO bdcSlYwlzDO);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除业务流转信息
     * @date : 2021/9/22 11:31
     */
    @DeleteMapping(value = "/realestate-accept/rest/v1.0/ywlz/{gzlslid}")
    int deleteYwlz(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param porcessInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 继承收件材料
     * @date : 2021/9/22 14:56
     */
    @GetMapping("/realestate-accept/rest/v1.0/ywlz/extendsjcl")
    void extendSjcl(@RequestParam(value = "porcessInsId") String porcessInsId);
}
