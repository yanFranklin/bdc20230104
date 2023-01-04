package cn.gtmap.realestate.common.core.service.rest.rules;

import cn.gtmap.realestate.common.core.domain.BdcXtQlqtzkFjPzDO;
import cn.gtmap.realestate.common.core.domain.rules.BdcGzBmdDO;
import cn.gtmap.realestate.common.core.dto.rules.BdcLwryLoginDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/11/16,1.0
 * @description
 */
public interface BdcGzLwryRestService {
    /**
     * @param czry   白名单人员
     * @param czrymm 白名单人员密码
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查看白名单人员是否登录
     */
    @GetMapping("/realestate-rules/rest/v1.0/lwry/login")
    BdcLwryLoginDTO checkLogin(@RequestParam(name = "czry", required = true) String czry,
                               @RequestParam(name = "czrymm", required = true) String czrymm);

    /**
     * @param czry 白名单人员
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询白名单人员
     */
    @GetMapping("/realestate-rules/rest/v1.0/lwry/page")
    Page<BdcGzBmdDO> bdcLwRyPage(Pageable pageable,
                                 @RequestParam(name = "czry", required = false) String czry);

    /**
     * @param bdcGzBmdDO 白名单人员
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新白名单人员信息
     */
    @PutMapping(path = "/realestate-rules/rest/v1.0/lwry")
    Integer updateBdcLwRy(@RequestBody BdcGzBmdDO bdcGzBmdDO);

    /**
     * @param czryid 白名单人员id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除白名单人员信息
     */
    @DeleteMapping(path = "/realestate-rules/rest/v1.0/lwry/{czryid}")
    void deleteBdcLwRy(@PathVariable(value = "czryid") String czryid);

    /**
     * @param bdcGzBmdDO 白名单人员id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 增加白名单人员信息
     */
    @PostMapping(path = "/realestate-rules/rest/v1.0/lwry")
    void insertBdcLwRy(@RequestBody BdcGzBmdDO bdcGzBmdDO);

    /**
     * @param czryid
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据主键查询白名单人员信息
     */
    @GetMapping(path = "/realestate-rules/rest/v1.0/lwry")
    BdcGzBmdDO queryBmdByid(@RequestParam(value = "czryid") String czryid);

    /**
     * @param
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 删除白名单
     */
    @DeleteMapping("/realestate-rules/rest/v1.0/bmd")
    int deleteBmd(@RequestBody List<BdcGzBmdDO> bdcGzBmdDOs);

    /**
     * @param
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 新增白名单
     */
    @PutMapping("/realestate-rules/rest/v1.0/bmd")
    int saveBdcBmd(@RequestBody BdcGzBmdDO bdcGzBmdDO);

    /**
     * @param
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 修改白名单
     */
    @PostMapping("/realestate-rules/rest/v1.0/bmd")
    int updateBdcBmd(@RequestBody BdcGzBmdDO bdcGzBmdDO);


}
