package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.BdcShxxDO;
import cn.gtmap.realestate.common.core.domain.BdcXtMryjDO;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcShxxPdfDTO;
import cn.gtmap.realestate.common.core.qo.register.BdcShxxQO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcShxxVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
 * @version 1.3, 2018/11/3
 * @description 审核信息接口
 */
public interface BdcShxxRestService {

    /**
     * @param gzlslid 工作流实例ID
     *
     * @return BdcShxxDO 返回保存的对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 新增审核信息，初始化审核信息
     */
//    @RequestMapping(value = "/realestate-register/rest/v1.0/shxx/{gzlslid}/{jdmc}", method = RequestMethod.POST)
//    BdcShxxDO insertBdcShxx(@PathVariable("gzlslid") String gzlslid, @PathVariable("jdmc") String jdmc);
//
//    /**
//     * @param processInsId  工作流实例ID
//     * @param nextNodeNames 下一个节点名称（会有多个，以英文逗号分隔）
//     * @return List<BdcShxxDO> 返回保存的对象
//     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
//     * @description 工作流转发新增审核信息，初始化审核信息
//     */
//    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/shxx", method = RequestMethod.POST)
//    List<BdcShxxDO> insertBdcShxxList(@RequestParam(value = "processInsId") String processInsId, @RequestParam("nextNodeNames") String nextNodeNames);

    /**
     * @param bdcShxx 审核信息实体类
     * @return int 返回操作的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新指定节点的审核信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/shxx", method = RequestMethod.PUT)
    int updateBdcShxx(@RequestBody BdcShxxDO bdcShxx);

    /**
     * @param bdcShxxDO 审核信息实体类
     * @return int 操作数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据主键查询shxx，没有则保存，有则更新
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/shxx", method = RequestMethod.POST)
    int saveOrUpdateBdcShxx(@RequestBody BdcShxxDO bdcShxxDO);

    @RequestMapping(value = "/realestate-register/rest/v1.0/shxx/{shxxid}", method = RequestMethod.GET)
    BdcShxxDO queryBdcShxxById(@PathVariable(name = "shxxid") String shxxid);
    /**
     * @param  bdcShxxQO
     * @return List<BdcShxxDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description  获取审核信息接口
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/shxx/list", method = RequestMethod.POST)
    List<BdcShxxDO> queryBdcShxx(@RequestBody BdcShxxQO bdcShxxQO);


    /**
     * @param bdcPrintDTO 打印参数
     * @return String  获取打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 审核表单打印xml获取
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/print/spb/xml", method = RequestMethod.POST)
    String bdPrintXml(@RequestBody BdcPrintDTO bdcPrintDTO);

    /**
     * @param bdcPrintDTO 打印参数
     * @return String  获取打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 审核表单打印xml获取(南通特供)
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/print/spb/xml/nantong", method = RequestMethod.POST)
    String bdPrintXmlNantong(@RequestBody BdcPrintDTO bdcPrintDTO);
    /**
     * @param paramList 审核信息集合
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 依据主键更新多条审核信息数据
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/shxxList", method = RequestMethod.PATCH)
    int updateShxxList(@RequestBody List<BdcShxxDO> paramList);

    /**
     * @param shxxid
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 删除审核意见和签名信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/sign/{shxxid}", method = RequestMethod.DELETE)
    int deleteShxxSign(@PathVariable(name = "shxxid") String shxxid);

    /**
     * @param shxxidList
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量删除审核意见和签名信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/sign", method = RequestMethod.DELETE)
    int deleteShxxSign(@RequestBody List<String> shxxidList);

    /**
     * @param taskId 当前任务ID
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 流程退回删除审核意见和签名信息，并保存审核结束时间
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/sign/{taskId}", method = RequestMethod.DELETE)
    int deleteSignAndSaveShjssj(@PathVariable(name = "taskId") String taskId);

    /**
     * @param taskId 当前任务ID
     * @return int 操作的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新审核结束时间（taskId和shxxid一致）
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/shjssj/{taskId}", method = RequestMethod.PUT)
    int updateShjssj(@PathVariable(name = "taskId") String taskId);

    @RequestMapping(value = "/realestate-register/rest/v1.0/mryj/sql", method = RequestMethod.POST)
    String generateMryjBySql(@RequestParam(name = "gzlslid") String gzlslid, @RequestBody BdcXtMryjDO bdcXtMryjDO);

    @RequestMapping(value = "/realestate-register/rest/v1.0/pushZzxx/{processInsId}",method = RequestMethod.GET)
    void pushDzzzDatas(@PathVariable(value = "processInsId") String proccessInsId);
    /**
     * @param gzlslid 工作流实例ID
     * @return List<String>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取流程配置的打印类型
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/shxx/dylx/{gzlslid}", method = RequestMethod.GET)
    Map<String, List<String>> getShxxDylx(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param bdcShxxQO 审核信息查询对象
     * @return 返回审核节点信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取签名意见，调用平台服务获取当前工作流配置的审核节点信息（出现异常则生成默认的初审，复审，核定节点信息），根据节点信息获取审核信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/shxx/jdxx", method = RequestMethod.POST)
    List<BdcShxxVO> queryShJdxx(@RequestBody BdcShxxQO bdcShxxQO);

    /**
     * @param shxxid 任务Id
     * @return BdcShxxDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程节点，最新的审核信息以及默认意见
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/shxx/mryj/{shxxid}", method = RequestMethod.GET)
    BdcShxxVO queryMryj(@PathVariable(value = "shxxid") String shxxid);

    /**
     * @param bdcShxxDO
     * @return signid
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取sign id
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/shxx/sign", method = RequestMethod.POST)
    BdcShxxVO getShxxSign(@RequestBody BdcShxxDO bdcShxxDO);

    /**
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzlslid 工作流实例ID
     * @return {List} 生成的审核信息
     * @description  生成流程项目所有节点审核信息，意见内容采用默认意见
     */
    @PostMapping("/realestate-register/rest/v1.0/{gzlslid}/shxx")
    List<BdcShxxDO> generateShxxOfProInsId(@PathVariable("gzlslid")String gzlslid);

    /**
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param gzlslids 工作流实例ID集合
     * @param userName 当前用户
     * @return boolean
     * @description  初审和二审是否是一样的审核人
     */
    @PostMapping("/realestate-register/rest/v1.0/shxx/hasSameShr")
    boolean hasSameShr(@RequestBody List<String> gzlslids,@RequestParam(value = "userName") String userName);


    /**
     * @param processInsId
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除审核意见和签名信息
     */
    @DeleteMapping("/realestate-register/rest/v1.0/shxx/deleteShxxPdf/{processInsId}")
    void deleteShxxPdf(@PathVariable(value = "processInsId") String processInsId);

    /**
     * 获取打印数据
     * <p>返回执行好sql的主表与子表数据，其中主表数据中添加了收件材料名称</p>
     * @param bdcPrintDTO 打印所需的参数的实体类
     * @return BdcDysjDTO  打印数据
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-register/rest/v1.0/print/shjdxx/data")
    BdcDysjDTO getPrintDataMap(@RequestBody BdcPrintDTO bdcPrintDTO);

    /**
     * @param processInsId 工作流实例ID
     * @return 审核信息列表
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 转发自动签名
     */
    @PostMapping("/realestate-register/rest/v1.0/shxx/zdqm/{processInsId}")
    List<BdcShxxDO> zdqm(@PathVariable(value = "processInsId") String processInsId, @RequestParam(name = "currentUserName",required = false) String currentUserName);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcShxxQOList 审核信息参数
     * @return List<BdcShxxPdfDTO> 审批表信息
     * @description 获取打印审批表PDF
     */
    @PostMapping("/realestate-register/rest/v1.0/shxx/bdcspb/pdf")
    List<BdcShxxPdfDTO> getBdcSpbPdf(@RequestBody List<BdcShxxQO> bdcShxxQOList);
}
