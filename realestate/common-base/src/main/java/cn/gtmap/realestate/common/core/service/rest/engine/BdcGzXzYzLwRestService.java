package cn.gtmap.realestate.common.core.service.rest.engine;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzLwDO;
import cn.gtmap.realestate.common.core.vo.engine.ui.BdcGzLwVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/11/16,1.0
 * @description
 */
public interface BdcGzXzYzLwRestService {

    /**
     * @param czry 操作人员
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询例外信息
     */
    @PostMapping("/realestate-engine/rest/v1.0/bdcgzlw/page")
    Page<BdcGzLwVO> listBdcXzyzLwPage(@RequestParam(name = "czry", required = false) String czry,
                                      @RequestParam(name = "ip", required = false) String ip,
                                      @RequestParam(name = "lwwh", required = false) String lwwh,
                                      Pageable pageable);

    /**
     * @param lwid 例外id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除例外信息
     */
    @DeleteMapping(path = "/realestate-engine/rest/v1.0/bdcgzlw/lwid")
    void deleteBdcLwYz(@RequestParam(name = "lwid", required = true) String lwid);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcGzLwDOList 例外规则集合
     * @return {int} 操作数据记录数
     * @description  新增的验证例外规则
     */
    @PostMapping(path = "/realestate-engine/rest/v1.0/bdcgzlw")
    int addBdcGzLw(@RequestBody List<BdcGzLwDO> bdcGzLwDOList);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcGzLwDOList 待删除例外规则集合
     * @return {int} 操作数据记录数
     * @description  批量删除已设置的验证例外规则
     */
    @DeleteMapping(path = "/realestate-engine/rest/v1.0/bdcgzlw")
    int deleteBdcGzLwList(@RequestBody List<BdcGzLwDO> bdcGzLwDOList);
}
