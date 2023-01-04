package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.dto.accept.SfmLiscenseInfoDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.csjzzxx.sfm.SfmResponse;
import cn.gtmap.realestate.common.core.qo.accept.CsjZzxxQO;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/1/13.
 * @description 不动产权利人RESR接口服务，提取accept中处理权利人信息通用接口服务供外部接口调用
 */
public interface BdcSlQlrRestService {

    /**
     * 批量修改权利人（用于批量页面）
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: json 权利人集合JSON
     * @param: processInsId 流程实例ID
     * @param: xmid 项目ID
     * @return: Integer 修改数量
     */
    @PatchMapping(value = "/realestate-accept/rest/v1.0/qlr/list/pllc")
    Integer updatePlBdcQlr(@RequestBody String json, @RequestParam("processInsId") String processInsId,
                           @RequestParam("xmid") String xmid) throws Exception;
    /**
     * @description 批量修改权利人(用于批量组合页面)
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: json 权利人集合JSON
     * @param: processInsId 流程实例ID
     * @param: xmid 项目ID
     * @param: djxl 登记小类
     * @return: List<BdcQlrDO> 不动产权利人DO集合
     */
    @PatchMapping(value = "/realestate-accept/rest/v1.0/qlr/list/plzh")
    List<BdcQlrDO> updatePlzhBdcQlr(@RequestBody String json,
                                    @RequestParam("processInsId") String processInsId,
                                    @RequestParam("xmid") String xmid,
                                    @RequestParam("djxl") String djxl) throws Exception;
    /**
     * 修改权利人共有方式接口
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param:  bdcQlrList 不动产权利人集合,  gzlslid 工作流实例ID, xmid 项目ID, lclx 流程类型
     * @return: List<BdcQlrDO> 不动产权利人DO集合
     */
    @PatchMapping(value = "/realestate-accept/rest/v1.0/qlr/list/gyfs")
    List<BdcQlrDO> updateBdcQlrGyfs(@RequestBody List<BdcQlrDO> bdcQlrList,
                                    @RequestParam("processInsId") String processInsId,
                                    @RequestParam("xmid") String xmid,
                                    @RequestParam("lclx") String lclx) throws Exception;

    /**
     * @param json 权利人集合
     * @param processInsId 工作流实例ID
     * @param djxl 登记小类
     * @return 新增权利人
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量新增权利人（批量组合）
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/qlr/list/plzh")
    BdcQlrDO insertPlzhBdcQlr(@RequestBody String json,
                                    @RequestParam("processInsId") String processInsId,
                                    @RequestParam("djxl") String djxl,@RequestParam("syncTdsyqr") boolean syncTdsyqr);

    /**
     * @param qlrid 权利人ID
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  批量删除权利人(批量组合)
     */
    @DeleteMapping(value = "/realestate-accept/rest/v1.0/qlr/list/plzh")
    void deletePlzhBdcQlr(@RequestParam("qlrid") String qlrid,
                              @RequestParam("processInsId") String processInsId) throws Exception;

    /**
     * 新增、修改、删除权利人时，同步土地使用权人
     *
     * @param json         权利人集合
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/qlr/synctdsyqr")
    void syncTdsyqr(@RequestBody String json, @RequestParam(value = "processInsId") String processInsId);


    /**
     * @param yyzzm 营业执照码
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 南通页面输入营业执照调用接口查询权利人信息和附件信息
     * @date : 2021/5/11 10:53
     */
    @GetMapping("/realestate-accept/rest/v1.0/qlr/yyzz/{yyzzm}/{gzlslid}")
    void importYyzz(@PathVariable(name = "yyzzm") String yyzzm, @PathVariable(name = "gzlslid") String gzlslid, @RequestParam(name = "djxl") String djxl) throws IOException;


    /**
     * @param csjZzxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询长三角证照信息
     * @date : 2022/5/11 9:45
     */
    @PostMapping("/realestate-accept/rest/v1.0/qlr/csjzzxx")
    String querCsjZzxx(@RequestBody CsjZzxxQO csjZzxxQO);


    /**
     * @param csjZzxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 扫描苏服码并查询相关信息
     * @date : 2022/5/12 8:34
     */
    @PostMapping("/realestate-accept/rest/v1.0/qlr/csjzzxx/sfm")
    String querCsjZzxxBySfm(@RequestBody CsjZzxxQO csjZzxxQO);


    /**
     * @param csjZzxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 扫描苏服码并查询相关信息
     * @date : 2022/5/12 8:34
     */
    @PostMapping("/realestate-accept/rest/v1.0/qlr/csjzzxx/sfm/cx")
    List<SfmLiscenseInfoDTO> querCsjZzxxBySfmCx(@RequestBody CsjZzxxQO csjZzxxQO);

    /**
     * @param csjZzxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 扫描苏服码添加权利人
     * @date : 2022/5/12 8:34
     */
    @PostMapping("/realestate-accept/rest/v1.0/qlr/csjzzxx/sfm/add")
    void querCsjZzxxBySfmAdd(@RequestBody CsjZzxxQO csjZzxxQO);

    /**
     * @param csjZzxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 扫描苏服码添加附件
     * @date : 2022/5/12 8:34
     */
    @PostMapping("/realestate-accept/rest/v1.0/qlr/csjzzxx/sfm/addfj")
    void querCsjZzxxBySfmAddFj(@RequestBody CsjZzxxQO csjZzxxQO);
}
