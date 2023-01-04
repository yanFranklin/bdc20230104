package cn.gtmap.realestate.common.core.service.rest.engine;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzBmdDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcBmdLoginDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 2019/03/04,1.0
 * @description 白名单人员接口
 */
public interface BdcGzBmdRestService {
    /**
     * @param czry   白名单人员
     * @param czrymm 白名单人员密码
     * @return BdcBmdLoginDTO BdcBmdLoginDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 查看白名单人员是否登录
     */
    @GetMapping("/realestate-engine/rest/v1.0/bmd/login")
    BdcBmdLoginDTO checkLogin(@RequestParam(name = "czry", required = true) String czry,
                              @RequestParam(name = "czrymm", required = true) String czrymm);

    /**
     * @param czry 白名单人员
     * @param pageable 分页
     *
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 分页查询白名单人员
     */
    @GetMapping("/realestate-engine/rest/v1.0/bmd/page")
    Page<BdcGzBmdDO> bdcBmdPage(Pageable pageable, @RequestParam(name = "czry", required = false) String czry);

    /**
     * @param bdcGzBmdDOs bdcGzBmdDOs
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 删除白名单
     */
    @DeleteMapping("/realestate-engine/rest/v1.0/bmd")
    int deleteBmd(@RequestBody List<BdcGzBmdDO> bdcGzBmdDOs);

    /**
     * @param bdcGzBmdDO bdcGzBmdDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 新增白名单
     */
    @PutMapping("/realestate-engine/rest/v1.0/bmd")
    int insertBdcBmd(@RequestBody BdcGzBmdDO bdcGzBmdDO);

    /**
     * @param bdcGzBmdDO bdcGzBmdDO
     * @return int
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 修改白名单
     */
    @PostMapping("/realestate-engine/rest/v1.0/bmd")
    int updateBdcBmd(@RequestBody BdcGzBmdDO bdcGzBmdDO);


}
