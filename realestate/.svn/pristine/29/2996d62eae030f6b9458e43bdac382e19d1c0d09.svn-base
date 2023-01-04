package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcGzlwShDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcGzlwShQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/8/26
 * @description
 */
public interface BdcGzlwRestService {
    /**
     * @param data 待例外数据
     * @param slbh 受理编号
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 增加审核信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/addShxxData")
    BdcGzlwShDO addShxxData(@RequestParam(value = "data") String data, @RequestParam(value = "slbh") String slbh
            , @RequestParam(value = "xmid") String xmid);

    /**
     * @param data 待例外数据
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 增加审核信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/addShxxDataWithoutSlbh")
    void addShxxDataWithoutSlbh(@RequestBody String data,
                                @RequestParam(value = "qllx") String qllx);

    /**
     * @param pageable
     * @param paramJson
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 分页查询审核信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/queryBdcGzlw")
    Page<Map> queryBdcGzlw(Pageable pageable,
                           @RequestParam(value = "paramJson", required = false) String paramJson);

    /**
     * @param pageable
     * @param paramJson
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 分页查询审核信息 以不动产单元号分组
     */
    @PostMapping("/realestate-accept/rest/v1.0/bdcgzlwGroupByBdcdyh")
    Page<Map> bdcgzlwGroupByBdcdyh(Pageable pageable,
                           @RequestParam(value = "paramJson", required = false) String paramJson);

    /**
     * @param gzlslid
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 查询审核信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/listBdcGzlw")
    List<BdcGzlwShDO> listBdcGzlw(@RequestParam(value = "gzlslid")String gzlslid);

    /**
     * @param bdcGzlwShDOList
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 更改规则例外信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/updateBdcGzlwxx")
    Integer updateBdcGzlwxx(@RequestBody List<BdcGzlwShDO> bdcGzlwShDOList);

    /**
     * @param data
     * @param accept
     * @param shyj
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 审核审核信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/updateBdcGzlw")
    Integer updateBdcGzlw(@RequestParam(value = "data") String data,
                          @RequestParam(value = "accept") boolean accept,
                          @RequestParam(value = "shyj") String shyj);

    /**
     * @param gzlwid
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 通过规则例外id 删除规则例外信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/deleteBdcGzlwSh")
    void deleteBdcGzlwSh(@RequestParam(value = "gzlwid") String gzlwid);

    /**
     * @param bdcGzlwShDO
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 通过规则例外实体 删除规则例外信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/deleteBdcGzlw")
    void deleteBdcGzlwShByGzlw(@RequestBody BdcGzlwShDO bdcGzlwShDO);

    /**
     * @param bdcGzlwShQO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 通过例外参数获取规则例外
     */
    @PostMapping("/realestate-accept/rest/v1.0/listBdcGzlwByParam")
    List<BdcGzlwShDO> listBdcGzlwByParam(@RequestBody BdcGzlwShQO bdcGzlwShQO);

    /**
     * @param gzlslid
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 通过工作流获取规则例外
     */
    @GetMapping("/realestate-accept/rest/v1.0/listBdcGzlwShByGzlslid")
    List<BdcGzlwShDO> listBdcGzlwShByGzlslid(@RequestParam(value = "gzlslid") String gzlslid);
}
