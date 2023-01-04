package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/12
 * @description 不动产受理项目历史关系rest服务
 */
public interface BdcSlXmLsgxRestService {
    /**
     * @param gxid 关系ID
     * @return 不动产受理项目历史关系
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据关系ID获取不动产受理项目历史关系
     */
    @GetMapping("/realestate-accept/rest/v1.0/xmlsgx/{gxid}")
    BdcSlXmLsgxDO queryBdcSlXmLsgxByGxid(@PathVariable(value = "gxid") String gxid);

    /**
     * @param xmid 项目ID
     * @return 不动产受理项目历史关系
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID获取不动产受理项目历史关系
     */
    @GetMapping("/realestate-accept/rest/v1.0/xmlsgx/list/{xmid}")
    List<BdcSlXmLsgxDO> listBdcSlXmLsgxByXmid(@PathVariable(value = "xmid") String xmid);

    /**
     * @param xmid 项目id
     * @param yxmid 原项目id
     * @return 不动产受理项目历史关系
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据项目id或者原项目id获取不动产受理历史关系
     */
    @GetMapping("/realestate-accept/rest/v1.0/xmlsgx/list")
    List<BdcSlXmLsgxDO> listBdcSlXmLsgx(@RequestParam(value = "xmid",required = false) String xmid,@RequestParam(value = "yxmid",required = false) String yxmid,@RequestParam(value = "sfwlzs",required = false) Integer sfwlzs);

    /**
     * @param bdcSlXmLsgxDO 不动产受理项目历史关系
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理项目历史关系
     */
    @PostMapping("/realestate-accept/rest/v1.0/xmlsgx/")
    BdcSlXmLsgxDO insertBdcSlXmLsgx(@RequestBody BdcSlXmLsgxDO bdcSlXmLsgxDO);

    /**
     * @param bdcSlXmLsgxDO 不动产受理项目历史关系
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理项目历史关系
     */
    @PutMapping("/realestate-accept/rest/v1.0/xmlsgx/")
    Integer updateBdcSlXmLsgx(@RequestBody BdcSlXmLsgxDO bdcSlXmLsgxDO);

    /**
     * @param gxid 关系ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据关系ID删除不动产受理项目历史关系
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/xmlsgx/{gxid}")
    Integer deleteBdcSlXmLsgxByGxid(@PathVariable(value = "gxid") String gxid);

    /**
     * @param xmid 项目ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID删除不动产受理项目历史关系
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/xmlsgx/list/{xmid}")
    Integer deleteBdcSlXmLsgxByXmid(@PathVariable(value = "xmid") String xmid);

    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据基本信息ID删除不动产受理项目历史关系
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/xmlsgx/list/jbxxid/{jbxxid}")
    void deleteBdcSlXmLsgxByJbxxid(@PathVariable(value = "jbxxid") String jbxxid);

    /**
     * @param fcxmid 项目ID
     * @return 不动产受理项目历史关系
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据项目ID新增不动产受理项目历史关系
     */
    @PostMapping("/realestate-accept/rest/v1.0/xmlsgx/list/tdz")
    BdcSlXmLsgxDO insertBdcSlXmLsgxByTdz(@RequestParam(name = "fcxmid") String fcxmid, @RequestParam(name = "tdxmid") String tdxmid, @RequestBody BdcSlXmDO bdcSlXmDO);


    /**
     * @param gzlslid 项目id
     * @return 不动产受理项目上下手关系
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description  根据gzlslid获取不动产受理上下手关系数据
     */
    @GetMapping("/realestate-accept/rest/v1.0/xmlsgx/sxxdata")
    List<Map> sxxBdcXx(@RequestParam(value = "gzlslid",required = true) String gzlslid);

}
