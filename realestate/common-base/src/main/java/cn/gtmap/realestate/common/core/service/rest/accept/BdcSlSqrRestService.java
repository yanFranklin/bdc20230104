package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlJtcyGroupDTO;
import cn.gtmap.realestate.common.core.dto.accept.YcslYmxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSqrQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/12
 * @description 不动产受理申请人rest服务
 */
public interface BdcSlSqrRestService {
    /**
     * @param sqrid 申请人人ID
     * @return 不动产受理申请人
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据申请人人ID获取不动产受理申请人
     */
    @GetMapping("/realestate-accept/rest/v1.0/sqr/{sqrid}")
    BdcSlSqrDO queryBdcSlSqrBySqrid(@PathVariable(value = "sqrid") String sqrid);

    /**
     * @param xmid 项目ID
     * @return 不动产受理申请人
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID获取不动产受理申请人
     */
    @GetMapping("/realestate-accept/rest/v1.0/sqr/list/{xmid}")
    List<BdcSlSqrDO> listBdcSlSqrByXmid(@PathVariable(value = "xmid") String xmid, @RequestParam("sqrlb") String sqrlb);

    /**
     * @param bdcSlSqrDO 不动产受理申请人
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理申请人
     */
    @PostMapping("/realestate-accept/rest/v1.0/sqr/")
    BdcSlSqrDO insertBdcSlSqr(@RequestBody BdcSlSqrDO bdcSlSqrDO);

    /**
     * @param bdcSlSqrDO 不动产受理申请人
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理申请人
     */
    @PutMapping("/realestate-accept/rest/v1.0/sqr/")
    Integer updateBdcSlSqr(@RequestBody BdcSlSqrDO bdcSlSqrDO);

    /**
     * @param sqrid 申请人人ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据申请人人ID删除不动产受理申请人
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/sqr/{sqrid}")
    Integer deleteBdcSlSqrBySqrid(@PathVariable(value = "sqrid") String sqrid);

    /**
     * @param xmid 项目ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID删除不动产受理申请人
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/sqr/list/{xmid}")
    Integer deleteBdcSlSqrByXmid(@PathVariable(value = "xmid") String xmid);

    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据基本信息ID删除不动产受理申请人
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/sqr/list/jbxxid/{jbxxid}")
    void deleteBdcSlSqrByJbxxid(@PathVariable(value = "jbxxid") String jbxxid);

    /**
     * @param gzlslid 工作流实例id
     * @param qlrlb 权利人类别
     * @param djxl 登记小类
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2019/11/30 13:57
     */
    @GetMapping("/realestate-accept/rest/v1.0/sqr/list/ywr/{gzlslid}/{qlrlb}")
    BdcQlrDO generateYwr(@PathVariable(value = "gzlslid") String gzlslid, @PathVariable(value = "qlrlb") String qlrlb, @RequestParam(name = "djxl", required = false) String djxl);

    /**
     * 根据查询参数查询受理申请人信息
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [bdcSlSqrQO] 受理申请人查询QO
     * @return: List<BdcSlSqrDO> 受理申请人DO集合
     */
    @PostMapping("/realestate-accept/rest/v1.0/sqr/list/sqrxx")
    List<BdcSlSqrDO> listBdcSlSqrByBdcSlSqrQO(@RequestBody BdcSlSqrQO bdcSlSqrQO);


    /**
     * @param xmid 项目ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据家庭分组获取申请人信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/sqr/getSqrxxGroupByJt/{xmid}")
    YcslYmxxDTO getSqrxxGroupByJt(@PathVariable(value = "xmid")String xmid);

    /**
     * @param xmid 项目ID
     * @param sqrlb 申请人类别
     * @param sfhb 是否合并多个申请人
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据家庭分组获取申请人信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/sqr/getSqrxxGroupByJtWithSqrlb/{xmid}")
    List<List<BdcSlJtcyGroupDTO>> getSqrxxGroupByJtWithSqrlb(@PathVariable(value = "xmid")String xmid, @RequestParam(name = "sqrlb", required = false)String sqrlb, @RequestParam(name = "sfhb", required = false)Boolean sfhb);

   /**
    * @param
    * @return
    * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
    * @description 批量更新申请人信息(组合流程同步 ）
    */
   @PutMapping(path = "/realestate-accept/rest/v1.0/sqr/sqrs")
   List<BdcSlSqrDO> saveBatchBdcSlSqrWithZh(@RequestBody String json, @RequestParam("gzlslid") String gzlslid, @RequestParam(value = "qllx", required = false) String qllx, @RequestParam(value = "djxl", required = false) String djxl);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量删除申请人(组合流程同步 ）
     */
    @DeleteMapping(path = "/realestate-accept/rest/v1.0/sqr/sqrs")
    void deleteBdcSqrsBySqrxxWithZh(@RequestParam("sqrid") String sqrid, @RequestParam("gzlslid") String gzlslid, @RequestParam(value = "qllx", required = false) String qllx, @RequestParam(value = "djxl", required = false) String djxl);

    /**
     * 同步一窗申请人信息
     * @param bdcQlrDOList 权利人信息
     * @param gzlslid 工作流实例ID
     * @param type 类型
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/sqr/sync/sqrxx")
    void syncSqrxx(@RequestBody List<BdcQlrDO> bdcQlrDOList, @RequestParam("gzlslid") String gzlslid, @RequestParam("type") String type);
}
