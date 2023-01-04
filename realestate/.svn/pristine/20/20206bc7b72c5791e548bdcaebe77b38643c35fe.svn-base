package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcYcslxxVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/12
 * @description 为页面提供获取、新增、更新、删除受理信息服务
 */
public interface BdcSlRestService {

    /**
     * @param jbxxid 基本信息ID
     * @return 受理信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID获取受理信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/sl/{jbxxid}")
    BdcSlxxDTO queryBdcSlxx(@PathVariable(value = "jbxxid") String jbxxid);

    /**
     * @param jbxxid 基本信息ID
     * @return 受理信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID获取增量受理信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/sl/zlslxx/{jbxxid}")
    BdcSlxxDTO queryBdcZlSlxx(@PathVariable(value = "jbxxid") String jbxxid);

    /**
     * @param bdcSlxxDTO 受理信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新受理信息
     */
    @PutMapping("/realestate-accept/rest/v1.0/sl/")
    void updateBdcSlxx(@RequestBody BdcSlxxDTO bdcSlxxDTO);

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流实例ID删除受理信息
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/sl/{gzlslid}/protal")
    void deleteBdcSlxx(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 初始化受理信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/sl/csh/{jbxxid}")
    void cshBdcSlxx(@PathVariable(value = "jbxxid") String jbxxid) throws Exception;

    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @param  gzlslid 工作流实例ID
     * @description 初始化收件材料
     */
    @GetMapping("/realestate-accept/rest/v1.0/sl/cshsjcl/{gzlslid}")
    void cshSjcl(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * 初始化合同信息的电子合同信息
     * @param gzlslid 工作流实例ID
     * @param htbh 合同编号
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/sl/cshHtxxSjcl")
    void cshHtxxSjcl(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "htbh") String htbh);

    /**
     * @param processDefId 工作流定义ID
     * @return 登记小类配置信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据定义id和权利类型获取配置信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/sl/djxlpz")
    Integer queryBdcdjxlPzBdclx(@RequestParam(value = "processDefId") String processDefId, @RequestParam(value = "bdcdyh") String bdcdyh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param wwsqCjBdcXmRequestDTO
     * @return cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmResponseDTO
     * @description 外网申请创建项目
     */
    @PostMapping("/realestate-accept/rest/v1.0/sl/wwsq")
    WwsqCjBdcXmResponseDTO wwsqCjBdcXm(@RequestBody WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO) throws Exception;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcSlxxDTO
     * @return List<Map<String, Object>>
     * @description 外网申请创建项目 规则验证
     */
    @PostMapping("/realestate-accept/rest/v1.0/sl/wwsq/gzyz")
    List<Map<String, Object>> wwsqCjBdcXmGzyz(@RequestBody BdcSlxxDTO bdcSlxxDTO);


    /**
     * @param gzlslid 工作流实例ID
     * @param autoForwardTaskDTO 下一节点信息
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 外网申请自动转发
     */
    @PostMapping("/realestate-accept/rest/v1.0/sl/wwsq/autoturn/{gzlslid}")
    void wwsqAutoTurn(@PathVariable(value = "gzlslid") String gzlslid,@RequestBody AutoForwardTaskDTO autoForwardTaskDTO) throws Exception;

    /**
     * @param gzlslid            工作流实例ID
     * @param autoForwardTaskDTO 下一节点信息
     * @author <a href="mailto:liyinqiao@gtmap.cn">liaoxiang</a>
     * @description 外网申请自动转发(包含规则验证)
     */
    @PostMapping("/realestate-accept/rest/v1.0/sl/wwsq/autoturnWithGzyz/{gzlslid}")
    Map<String, String> autoTurnWithGzyz(@PathVariable(value = "gzlslid") String gzlslid, @RequestBody AutoForwardTaskDTO autoForwardTaskDTO) throws Exception;


    /**
     * 外网申请自动转发
     * <ul>
     *     <li>如果节点未被认领，由配置中默认用户自动认领</li>
     *     <li>自动签名：签名信息取上一个节点用户，如果未被认领则取配置中默认用户</li>
     *     <li>转发前验证：必填项和规则验证</li>
     *     <li>转发到下个节点</li>
     * </ul>
     *
     * @param gzlslid            工作流实例ID
     * @param autoForwardTaskDTO 下一节点信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/sl/wwsq/autoturnZfyz/{gzlslid}")
    Map<String, String> autoturnZfyz(@PathVariable(value = "gzlslid") String gzlslid, @RequestBody AutoForwardTaskDTO autoForwardTaskDTO) throws Exception;

    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 增量初始化受理信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/sl/zlcsh/{jbxxid}")
    void zlcshBdcSlxx(@PathVariable(value = "jbxxid") String jbxxid) throws Exception;

    /**
     * @param bdcSlCshDTO 受理初始化对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化受理申请信息(非登记业务流程)
     */
    @PostMapping("/realestate-accept/rest/v1.0/sl/sqxx")
    void cshBdcSlSqxx(@RequestBody BdcSlCshDTO bdcSlCshDTO) throws Exception;

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: processInsId 工作流实例id
     * @return: List<Map<String,String>> 纳税联系单数据
     * @description 通过工作流实例id获取纳税联系单表单数据，返回值为List类型
     */
    @PostMapping("/realestate-accept/rest/v1.0/sl/nslxd")
    List<Map<String,String>> getNslxdData(@RequestParam("processInsId") String processInsId) throws Exception;

    /**
     * @param bdcSlCshDTO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化一窗受理流程不动产单元信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sl/ycslxx")
    void cshBdcSlYcslxx(@RequestBody BdcSlCshDTO bdcSlCshDTO) throws Exception;

    /**
     * @param bdcTsDjxxRequestDTO 一窗推送登记请求对象
     * @return 推送结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 推送一窗信息创建登记流程
     */
    @PostMapping("/realestate-accept/rest/v1.0/sl/tsdjxx")
    InitTsBdcDjxxResponseDTO initTsBdcDjxx(@RequestBody BdcTsDjxxRequestDTO bdcTsDjxxRequestDTO) throws Exception;

    /**
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新非登记流程案件状态为已办结
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/sl/ajzt")
    void changeAjztEnd(@RequestParam(value = "processInsId") String processInsId);

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新业务受理状态
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/sl/ywslzt/{ywslzt}")
    void updateYwslzt(@RequestParam(value = "processInsId") String processInsId,@PathVariable(value = "ywslzt") Integer ywslzt);

    /**
     * @param gzlslid 工作流实例ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 同步生成一窗受理信息
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/sl/ycsl")
    void syncYcslxx(@RequestParam(value = "gzlslid") String gzlslid,@RequestParam(value = "xmid",required = false) String xmid) throws Exception;

    /**
     * @param gzlslid 工作流实例ID
     * @return
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 同步生成受理信息
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/sl/slxx")
    void syncSlxx(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "xmid",required = false) String xmid) throws Exception;

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  一窗列表分页
     */
    @PostMapping("/realestate-accept/rest/v1.0/sl/ycsl/page")
    Page<BdcYcslxxVO> listYcslxxByPage(Pageable pageable, @RequestParam(name = "bdcSlXmQO", required = false) String bdcYcslQOStr);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  一窗列表(不分页)
     */
    @PostMapping("/realestate-accept/rest/v1.0/sl/ycsl/list")
    List<BdcYcslxxVO> listYcslxxList(@RequestParam(name = "bdcSlXmQO", required = false) String bdcYcslQOStr);

    /**
     * @param xmidList 项目ID集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除受理信息(购物车删除)
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/sl/list")
    void deleteBdcSlxx(@RequestBody List<String> xmidList);

    /**
     * @param gzlslid 工作流实例ID
     * @return 外网申请受理编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据登记工作流实例ID获取外网申请受理编号
     */
    @GetMapping("/realestate-accept/rest/v1.0/sl/wwsqslbh/{gzlslid}")
    String getWwsqSlbhByDjGzlslid(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 银行系统申请受理编号
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据登记工作流实例ID获取银行系统申请受理编号
     */
    @GetMapping("/realestate-accept/rest/v1.0/sl/yhxtslbh/{gzlslid}")
    String getYhxtSlbhByDjGzlslid(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    @PostMapping("/realestate-accept/rest/v1.0/sl/cshCjBdcXm")
    Map<String, String> cshCjBdcXm(@RequestBody BdcSlCshDTO bdcSlCshDTO) throws Exception;

    /**
     * @param bdcSlCshDTO 受理初始化实体
     * @return 验证结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化前通用验证逻辑
     */
    @PostMapping("/realestate-accept/rest/v1.0/sl/yzcsh")
    CommonResponse yzCshxxBeforeCj(@RequestBody BdcSlCshDTO bdcSlCshDTO);

    /**
     * @param dm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断是否主房
     * @date : 2022/7/14 15:19
     */
    @GetMapping("/realestate-accept/rest/v1.0/sl/sfzf")
    boolean checkSfzf(@RequestParam(name = "dm") String dm);
}
