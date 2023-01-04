package cn.gtmap.realestate.common.core.service.rest.rules;
/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/11/13 8:50
 * @description
 */

import cn.gtmap.realestate.common.core.domain.rules.BdcGzZhDO;
import cn.gtmap.realestate.common.core.dto.rules.BdcGzyzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.rules.GzZhResponseDTO;
import cn.gtmap.realestate.common.core.qo.rules.BdcGzZhQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/11/13 8:50
 * @description 规则组合接口
 */
public interface BdcGzZhRestService {
    /**
     * @param bdcGzZhQO
     * @return List<BdcGzyzTsxxDTO>
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据BdcGzZhQO获取验证信息
     */
    @RequestMapping(path = "/realestate-rules/rest/v1.0/zh/list", method = RequestMethod.POST)
    List<BdcGzyzTsxxDTO> listBdcGzyzTsxx(@RequestBody BdcGzZhQO bdcGzZhQO);

    /**
     * @param bdcGzZhQO
     * @param ywgzid
     * @return BdcGzyzTsxxDTO
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据BdcGzZhQO获取验证信息
     */
    @RequestMapping(path = "/realestate-rules/rest/v1.0/zh/{ywgzid}", method = RequestMethod.POST)
    List<BdcGzyzTsxxDTO> bdcGzyzTsxx(@PathVariable(value = "ywgzid") String ywgzid, @RequestBody BdcGzZhQO bdcGzZhQO);

    /**
     * @param pageable
     * @param gzzhmc
     * @param zhid
     * @return java.lang.Object
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 分页查询规则组合信息
     */
    @PostMapping("/realestate-rules/rest/v1.0/gzzh/page")
    Page<GzZhResponseDTO> listBdcGzZhByPageJson(Pageable pageable,
                                                @RequestParam(name = "gzzhmc", required = false) String gzzhmc,
                                                @RequestParam(name = "zhid", required = false) String zhid);

    /**
     * @param zhid
     * @return cn.gtmap.realestate.common.core.domain.rules.BdcGzZhDO
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据主键查询规则组合
     */
    @GetMapping("/realestate-rules/rest/v1.0/gzzh/{zhid}")
    BdcGzZhDO queryBdcGzZhByZhid(@PathVariable("zhid") String zhid);

    /**
     * @param bdcGzZhDO
     * @return cn.gtmap.realestate.common.core.domain.rules.BdcGzZhDO
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 新增规则组合信息
     */
    @PostMapping("/realestate-rules/rest/v1.0/gzzh")
    BdcGzZhDO insertBdcGzZh(@RequestBody BdcGzZhDO bdcGzZhDO);

    /**
     * @param bdcGzZhDO
     * @return void
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 修改规则组合信息
     */
    @PutMapping("/realestate-rules/rest/v1.0/gzzh")
    Integer updateBdcGzZh(@RequestBody BdcGzZhDO bdcGzZhDO);

    /**
     * @param zhid
     * @return void
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 删除规则组合信息
     */
    @DeleteMapping("/realestate-rules/rest/v1.0/gzzh/{zhid}")
    Integer deleteBdcGzZhByZhid(@PathVariable("zhid") String zhid);

    /**
     * @param processInsId
     * @return BdcGzyzTsxxDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据gzlslid获取验证返回提示信息
     */
    @PostMapping(path = "/realestate-rules/rest/v1.0/zh/list/{processInsId}")
    List<BdcGzyzTsxxDTO> listBdcGzyzTsxxForTrans(@PathVariable(value = "processInsId") String processInsId);

    /**
     * @param
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 验证sql语句合法性
     */
    @GetMapping("/realestate-rules/rest/v1.0/gzzh/checksql/{sjSql}")
    List<Map> checkSjSql(@PathVariable("sjSql") String sjSql);

}
