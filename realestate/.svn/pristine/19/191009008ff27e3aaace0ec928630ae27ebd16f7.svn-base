package cn.gtmap.realestate.common.core.service.rest.config;

import cn.gtmap.realestate.common.core.domain.BdcXtQlqtzkFjPzDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/2/18
 * @description 不动产权利其他状况、附记配置服务
 */
public interface BdcXtQlqtzkFjPzRestService {
    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取权利其他状况附记配置
     */
    @GetMapping("/realestate-config/rest/v1.0/qlqtzkFj/page")
    Page<BdcXtQlqtzkFjPzDO> listBdcXtQlqtzkFjPz(Pageable pageable, @RequestParam(name = "qlqtzkFjParamJson", required = false) String qlqtzkFjParamJson);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存权利其他状况附记配置
     */
    @PutMapping("/realestate-config/rest/v1.0/qlqtzkFj")
    int saveBdcXtQlqtzkFjPz(@RequestBody BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改权利其他状况附记配置
     */
    @PostMapping("/realestate-config/rest/v1.0/qlqtzkFj")
    int updateBdcXtQlqtzkFjPz(@RequestBody BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除权利其他状况附记配置
     */
    @DeleteMapping("/realestate-config/rest/v1.0/qlqtzkFj")
    int deleteBdcXtQlqtzkFjPz(@RequestBody List<BdcXtQlqtzkFjPzDO> bdcXtQlqtzkFjPzDO);
    /**
     * @param sqlList
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 验证sql
     */
    @PostMapping("/realestate-config/rest/v1.0/qlqtzkFj/check")
    boolean checkBdcXtQlqtzkFjPz(@RequestParam(name="sqlList") List<String> sqlList, @RequestBody Map params);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取权利其他状况、附记 配置
     */
    @PostMapping("/realestate-config/rest/v1.0/qlqtzkFj/list")
    List<BdcXtQlqtzkFjPzDO> listQlqtzkFjpz(@RequestBody BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO);
}
