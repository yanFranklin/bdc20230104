package cn.gtmap.realestate.common.core.service.rest.rules;/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/15
 * @description
 */

import cn.gtmap.realestate.common.core.domain.rules.BdcGzZhGxDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/15
 * @description 规则组合关系接口
 */
public interface BdcGzZhGxRestService {

    /**
     * @param zhid
     * @return List<BdcGzZhGxDO>
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据组合id查询规则组合关系
     */
    @GetMapping("/realestate-rules/rest/v1.0/gzzhgx/{zhid}")
    List<BdcGzZhGxDO> listBdcGzZhGxByZhid(@PathVariable("zhid") String zhid);

    /**
     * @param bdcGzZhGxDO
     * @return cn.gtmap.realestate.common.core.domain.rules.BdcGzZhGxDO
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 新增规则组合关系信息
     */
    @PostMapping("/realestate-rules/rest/v1.0/gzzhgx")
    BdcGzZhGxDO insertBdcGzZhGx(@RequestBody BdcGzZhGxDO bdcGzZhGxDO);

    /**
     * @param gxid
     * @return Integer
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据主键删除规则组合关系信息
     */
    @DeleteMapping("/realestate-rules/rest/v1.0/gzzhgx/{gxid}")
    Integer deleteBdcGzZhGxByGxid(@PathVariable("gxid") String gxid);

    /**
     * @param zhid
     * @return Integer
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据组合ID删除规则组合关系信息
     */
    @DeleteMapping("/realestate-rules/rest/v1.0/gzzhgx/list/{zhid}")
    Integer deleteBdcGzZhGxByZhid(@PathVariable("zhid") String zhid);

}
