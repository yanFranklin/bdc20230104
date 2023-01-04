package cn.gtmap.realestate.common.core.service.rest.engine;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0 2020/7/7 14:49
 * @description 固定子规则接口服务
 */
public interface BdcGzGdZgzRestService {

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param zgzParamJson
     * @return
     * @description 获取固定子规则列表（不分页）
     */
    @GetMapping("/realestate-engine/rest/v1.0/gdzgz")
    List<BdcGzZgzDO> listBdcGzGdZgz(@RequestParam(name = "zgzParamJson", required = false) String zgzParamJson);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param bdcGzZgzDTO
     * @return
     * @description 保存固定子规则信息
     */
    @PostMapping("/realestate-engine/rest/v1.0/gdzgz")
    String saveBdcGzGdZgz(@RequestBody BdcGzZgzDTO bdcGzZgzDTO);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param bdcGzZgzDOList
     * @description 删除固定子规则信息
     */
    @DeleteMapping("/realestate-engine/rest/v1.0/gdzgz")
    void deleteBdcGzGdZgz(@RequestBody List<BdcGzZgzDO> bdcGzZgzDOList);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param gzid gzid
     * @return 固定子规则信息
     */
    @GetMapping("/realestate-engine/rest/v1.0/gdzgz/{gzid}")
    BdcGzZgzDTO queryBdcGzGdZgzDTO(@PathVariable("gzid") String gzid);
}
