package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlPjqDO;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.accept.BdcMkqmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlPjqDTO;
import cn.gtmap.realestate.common.core.dto.accept.SlymPjqDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlPjqQO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @program: realestate
 * @description: 受理评价器rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-08-08 14:49
 **/
public interface BdcSlPjqRestService {

    /**
     * @param ywbh 受理编号
     * @param jdmc 节点名称
     * @return 不动产评价器Do
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据业务编号获取评价器信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/pjq/{ywbh}/{jdmc}")
    BdcSlPjqDO queryBdcSlPjqByYwbh(@PathVariable(value = "ywbh") String ywbh, @PathVariable(value = "jdmc") String jdmc);

    /**
     * @param ywbh 受理编号
     * @return 不动产评价器Do
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据业务编号获取评价器信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/pjq/{ywbh}")
    BdcSlPjqDO queryBdcSlPjqBySlbh(@PathVariable(value = "ywbh") String ywbh);

    /**
     * @param ywbh     受理编号
     * @param jdmc     节点名称
     * @param username 用户登录名
     * @return 不动产评价器Do
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据业务编号获取评价器信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/pjq/{ywbh}/{jdmc}/{username}")
    BdcSlPjqDO queryBdcSlPjqByYwbh(@PathVariable(value = "ywbh") String ywbh, @PathVariable(value = "jdmc")String jdmc,@PathVariable(value = "username") String username);

    /**
     * @param bdcSlPjqDO 不动产评价器Do
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产受理评价器信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/pjq/")
    BdcSlPjqDO insertBdcSlPjq(@RequestBody BdcSlPjqDO bdcSlPjqDO);

    /**
     * @param bdcSlPjqDO 不动产评价器Do
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新不动产受理评价器信息
     */
    @PutMapping("/realestate-accept/rest/v1.0/pjq/")
    Integer updateBdcSlPjq(@RequestBody BdcSlPjqDO bdcSlPjqDO);


    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: pageSize, pageNumber, bdcSlPjqDTO
     * @return: Page<BdcSlPjqDTO>
     * @description 分页查询不动产受理统计信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/pjtj/page")
    Page<BdcSlPjqDO> listBdcSlPjTjByPage(@RequestParam("pageSize") Integer pageSize,
                                          @RequestParam("pageNumber") Integer pageNumber,
                                          @RequestBody BdcSlPjqDTO bdcSlPjqDTO);

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: pageSize, pageNumber, BdcSlPjqQO
     * @return: Page<BdcSlPjqDTO>
     * @description 分页分组查询不动产受理统计信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/pjtj/group/page")
    Page<BdcSlPjqDTO> listGroupBdcSlPjTjByPage(@RequestParam("pageSize") Integer pageSize,
                                          @RequestParam("pageNumber") Integer pageNumber,
                                          @RequestBody BdcSlPjqQO bdcSlPjqQO);

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: pageSize, pageNumber, bdcSlPjqDTO
     * @return: Page<BdcSlPjqDTO>
     * @description 分页分组查询不动产受理统计信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/pjtj/group/list")
    List<BdcSlPjqDTO> listGroupBdcSlPjTj(@RequestBody BdcSlPjqQO bdcSlPjqQO);

    /**
     * @param gzlslid 工作流实例ID
     * @param taskid 任务ID
     * @return 评价器信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据配置调用不同评价器
     */
    @GetMapping("/realestate-accept/rest/v1.0/pjq/common/pj")
    SlymPjqDTO commonPj(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "taskid")String taskid, @RequestParam(value = "clientip")String clientip);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 摩科人证对比
     */
    @GetMapping("/realestate-accept/rest/v1.0/pjq/mkrzdb")
    Object mkrzdb(@RequestParam(value = "qlrmc") String qlrmc, @RequestParam(value = "qlrzjh")String qlrzjh, @RequestParam(value = "gzlslid")String gzlslid,@RequestParam(value = "clientip")String clientip);



    /**
     * @param gzlslid 工作流实例ID
     * @return 评价器信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据配置调用不同人证对比
     */
    @GetMapping("/realestate-accept/rest/v1.0/pjq/common/rzdb")
    Object commonRzdb(@RequestParam(value = "qlrmc",required = false) String qlrmc, @RequestParam(value = "qlrzjh",required = false)String qlrzjh, @RequestParam(value = "gzlslid")String gzlslid,@RequestParam(value = "clientip")String clientip);

    /**
     * @param slymPjqDTO 评价结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存评价记录
     */
    @PostMapping("/realestate-accept/rest/v1.0/pjq/pjjl")
    BdcCommonResponse savePjjl(@RequestBody SlymPjqDTO slymPjqDTO);

    /**
     * @param gzlslid 工作流实例ID
     * @return 评价器信息
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 摩科签字
     */
    @GetMapping("/realestate-accept/rest/v1.0/pjq/mkqz")
    Object mkqz(@RequestParam(value = "qlrmc",required = false) String qlrmc, @RequestParam(value = "qlrzjh",required = false)String qlrzjh, @RequestParam(value = "gzlslid")String gzlslid,@RequestParam(value = "clientip")String clientip);


    /**
     * @param processInsId 工作流实例ID
     * @return 评价器信息
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 工作流事件，推送评价给省厅
     */
    @PostMapping("/realestate-accept/rest/v1.0/pjq/mktspj")
    Object mktspj(@RequestParam(value = "processInsId")String processInsId, @RequestParam(value = "clientip")String clientip,@RequestParam(value = "taskId")String taskId);


    /**
     * @param
     * @return
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 摩科签字回传
     */
    @PostMapping("/realestate-accept/rest/v1.0/pjq/mkqzhc")
    Object mkqzhc(@RequestBody BdcMkqmDTO bdcMkqmDTO) throws IOException;


}
