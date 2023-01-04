package cn.gtmap.realestate.common.core.service.rest.rules;/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/13 8:50
 * @description
 */

import cn.gtmap.realestate.common.core.domain.rules.BdcGzYwgzDO;
import cn.gtmap.realestate.common.core.dto.rules.GzYwgzResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/13
 * @description 不动产业务规则接口
 */
public interface BdcGzYwgzRestService {

    /**
     * @param pageable
     * @param gzmc
     * @param ywgzid
     * @return java.lang.Object
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 分页查询业务规则信息
     */
    @PostMapping("/realestate-rules/rest/v1.0/yzgz/page")
    Page<GzYwgzResponseDTO> listBdcGzYwgzByPageJson(Pageable pageable,
                                                    @RequestParam(name = "gzmc", required = false) String gzmc,
                                                    @RequestParam(name = "ywgzid", required = false) String ywgzid);

    /**
     * @param ywgzid
     * @return cn.gtmap.realestate.common.core.domain.rules.BdcGzYwgzDO
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据主键查询业务规则
     */
    @GetMapping("/realestate-rules/rest/v1.0/yzgz/{ywgzid}")
    BdcGzYwgzDO queryBdcGzYwgzByYwgzid(@PathVariable("ywgzid") String ywgzid);

    /**
     * @param bdcGzYwgzDO
     * @return cn.gtmap.realestate.common.core.domain.rules.BdcGzYwgzDO
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 新增业务规则信息
     */
    @PostMapping("/realestate-rules/rest/v1.0/yzgz")
    BdcGzYwgzDO insertBdcGzYwgz(@RequestBody BdcGzYwgzDO bdcGzYwgzDO);

    /**
     * @param bdcGzYwgzDO
     * @return void
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 修改业务规则信息
     */
    @PutMapping("/realestate-rules/rest/v1.0/yzgz")
    Integer updateBdcGzYwgz(@RequestBody BdcGzYwgzDO bdcGzYwgzDO);

    /**
     * @param ywgzid
     * @return void
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 删除业务规则信息
     */
    @DeleteMapping("/realestate-rules/rest/v1.0/yzgz/{ywgzid}")
    Integer deleteBdcGzYwgzByYwgzid(@PathVariable("ywgzid") String ywgzid);

    /**
     * @return listMap
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除业务规则信息
     */
    @GetMapping("/realestate-rules/rest/v1.0/yzgz/list")
    List<HashMap> listBdcGzywgzMcAndId();
}
