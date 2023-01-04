package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjxgDO;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @description 数据修改管理服务
 * @date : 2022/11/30
 */
public interface BdcSlSjxgRestService {


    /**
     * @param gzlslid
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 查询数据修改数据
     * @date : 2022/11/30
     */
    @GetMapping("/realestate-accept/rest/v1.0/querysjxg/{gzlslid}")
    BdcSlSjxgDO querySlSjxgDO(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 根据工作流实例id删除数据修改数据
     * @date : 2022/11/30
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/sjxg/{gzlslid}")
    int deleteSlSjxg(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param bdcSlSjxgDO
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 新增数据修改
     * @date : 2022/11/30
     */
    @PostMapping("/realestate-accept/rest/v1.0/sjxg")
    BdcSlSjxgDO saveSlSjxg(@RequestBody BdcSlSjxgDO bdcSlSjxgDO);
}
