package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcShxxDO;
import cn.gtmap.realestate.common.core.domain.BdcXtMryjDO;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcShxxPdfDTO;
import cn.gtmap.realestate.common.core.qo.register.BdcShxxQO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcShxxRestService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcShxxVO;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.register.service.BdcDzzzService;
import cn.gtmap.realestate.register.service.BdcShbdPrintService;
import cn.gtmap.realestate.register.service.BdcShxxService;
import cn.gtmap.realestate.register.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/30
 * @description 审核信息业务Controller
 */
@RestController
@Api(tags = "不动产审核信息服务接口")
public class BdcShxxRestController extends BaseController implements BdcShxxRestService {
    @Autowired
    BdcShxxService bdcShxxService;
    @Autowired
    BdcShbdPrintService bdcShbdPrintService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcDzzzService bdcDzzzService;

    /**
     * @param bdcShxx 审核信息实体类
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新指定节点的审核信息
     */
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "更新指定节点的审核信息(更新审核意见和签名时间)")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcShxx", value = "审核信息DO对象(对象中必须有shyj)", required = true, dataType = "BdcShxxDO")})
    @Override
    public int updateBdcShxx(@RequestBody BdcShxxDO bdcShxx) {
        return bdcShxxService.updateBdcShxx(bdcShxx);
    }

    /**
     * @param bdcShxxDO 审核信息实体类
     * @return int 操作数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据主键查询shxx，没有则保存，有则更新
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新指定节点的审核信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcShxx", value = "审核信息DO对象", required = true, dataType = "BdcShxxDO")})
    @Override
    public int saveOrUpdateBdcShxx(@RequestBody BdcShxxDO bdcShxxDO) {
        return bdcShxxService.saveOrUpdateBdcShxx(bdcShxxDO);
    }

    /**
     * @param shxxid 审核信息ID
     * @return BdcShxxDO 审核信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据主键查询审核信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "依据主键查询审核信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "shxxid", value = "审核信息ID", required = true, dataType = "string", paramType = "path")})
    @Override
    public BdcShxxDO queryBdcShxxById(@PathVariable(name = "shxxid") String shxxid) {
        return bdcShxxService.queryBdcShxxById(shxxid);
    }

    /**
     * @param bdcShxxQO 审核信息查询对象
     * @return List<BdcShxxDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询审批信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询审核信息", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "审核项目ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jdmc", value = "节点名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "shryxm", value = "审核人员姓名", dataType = "string", paramType = "query")})
    @Override
    public List<BdcShxxDO> queryBdcShxx(@RequestBody BdcShxxQO bdcShxxQO) {
        return bdcShxxService.queryBdcShxx(bdcShxxQO);
    }

    /**
     * @param bdcShxxQO 审核信息查询对象
     * @param pageable  分页参数对象
     * @return Page<BdcShxxDO> 分页查询对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询审核信息
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/shxx/page")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询审核信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "size", value = "每页数据量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码（从0开始）", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "审核项目ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "jdmc", value = "节点名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "shryxm", value = "审核人员姓名", dataType = "string", paramType = "query")})
    public Page<BdcShxxDO> bdcShxxByPageJson(Pageable pageable, BdcShxxQO bdcShxxQO) {
        Map<String, Object> map = new HashMap<>(16);
        if (bdcShxxQO != null) {
            String json = JSONObject.toJSONString(bdcShxxQO);
            map = pageableSort(pageable, JSONObject.parseObject(json, HashMap.class));
        }
        return repo.selectPaging("listBdcShxxByPage", map, pageable);
    }


    /**
     * @param bdcPrintDTO 打印参数
     * @return String  获取打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取表单打印XMl")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流示例ID", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "dylx", value = "打印类型(审批表：bdcSqSpb)", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "bdcUrlDTO", value = "需要的url参数对象", required = true, dataType = "BdcUrlDTO", paramType = "body")})
    @Override
    public String bdPrintXml(@RequestBody BdcPrintDTO bdcPrintDTO) {
        return bdcShbdPrintService.bdPrintXml(bdcPrintDTO);
    }

    /**
     * @param bdcPrintDTO 打印参数
     * @return String  获取打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 审核表单打印xml获取(南通特供)
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取表单打印XMl")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流示例ID", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "dylx", value = "打印类型(审批表：bdcSqSpb)", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "bdcUrlDTO", value = "需要的url参数对象", required = true, dataType = "BdcUrlDTO", paramType = "body")})
    @Override
    public String bdPrintXmlNantong(@RequestBody BdcPrintDTO bdcPrintDTO) {
        return bdcShbdPrintService.bdPrintXmlNantong(bdcPrintDTO);
    }

    /**
     * @param paramList 审核信息集合
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 依据主键更新多条审核信息数据
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("依据主键更新多条审核信息数据")
    @Override
    @ApiImplicitParams({@ApiImplicitParam(name = "paramList", value = "需要更新的审核信息List", required = true, dataType = " List<BdcShxxDO>", paramType = "body")})
    public int updateShxxList(@RequestBody List<BdcShxxDO> paramList) {
        return bdcShxxService.updateShxxList(paramList);
    }

    /**
     * @param shxxid
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 删除审核意见和签名信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("删除审核意见和签名信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "shxxid", value = "审核信息ID", required = true, dataType = "string", paramType = "path")})
    public int deleteShxxSign(@PathVariable(name = "shxxid") String shxxid) {
        return bdcShxxService.deleteShxxSign(shxxid);
    }

    /**
     * @param shxxidList
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量删除审核意见和签名信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量删除审核意见和签名信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "shxxidList", value = "审核信息IDList", required = true, dataType = "List", paramType = "body")})
    public int deleteShxxSign(@RequestBody List<String> shxxidList) {
        return bdcShxxService.deleteShxxSign(shxxidList);
    }

    /**
     * @param taskId 当前任务ID
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 流程退回删除审核意见和签名信息，并保存审核结束时间
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("流程退回删除审核意见和签名信息，并保存审核结束时间")
    @ApiImplicitParams({@ApiImplicitParam(name = "taskId", value = "任务Id（shxxid）", required = true, dataType = "string", paramType = "path")})
    @Override
    public int deleteSignAndSaveShjssj(@PathVariable(name = "taskId") String taskId) {
        return bdcShxxService.deleteSignAndSaveShjssj(taskId);
    }

    /**
     * @param taskId 当前任务ID
     * @return int 操作的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新审核结束时间（taskId和shxxid一致）
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新审核结束时间（taskId和shxxid一致）")
    @ApiImplicitParams({@ApiImplicitParam(name = "taskId", value = "任务Id（shxxid）", required = true, dataType = "string", paramType = "path")})
    @Override
    public int updateShjssj(@PathVariable(name = "taskId") String taskId) {
        return bdcShxxService.updateShjssj(taskId);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据配置的sql生成默认意见")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "bdcXtMryjDO", value = "默认意见DO", required = true, dataType = "BdcXtMryjDO", paramType = "body")})
    @Override
    public String generateMryjBySql(@RequestParam(name = "gzlslid") String gzlslid, @RequestBody BdcXtMryjDO bdcXtMryjDO) {
        return bdcShxxService.generateMryjBySql(gzlslid, bdcXtMryjDO);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("推送电子证照数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "proccessInsId",value = "工作流实例Id",required = true,dataType = "string")})
    @Override
    public void pushDzzzDatas(@PathVariable("processInsId") String proccessInsId) {
        bdcDzzzService.pushDzzzDatas(proccessInsId);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return List<String>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取流程配置的审批表打印类型
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取流程配置的审批表打印类型")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")})
    @Override
    public Map<String, List<String>> getShxxDylx(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcShbdPrintService.getShxxDylx(gzlslid);
    }

    /**
     * @param bdcShxxQO 审核信息查询对象
     * @return 返回审核节点信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取签名意见，调用平台服务获取当前工作流配置的审核节点信息（出现异常则生成默认的初审，复审，核定节点信息），根据节点信息获取审核信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取签名意见，调用平台服务获取当前工作流配置的审核节点信息（出现异常则生成默认的初审，复审，核定节点信息），根据节点信息获取审核信息"
            , extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcShxxQO", value = "审核信息查询QO", dataType = "BdcShxxQO", paramType = "body")})
    @Override
    public List<BdcShxxVO> queryShJdxx(@RequestBody BdcShxxQO bdcShxxQO) {
        return bdcShxxService.queryShJdxx(bdcShxxQO);
    }

    /**
     * @param shxxid 任务Id
     * @return BdcShxxDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程节点，最新的审核信息以及默认意见
     */
    @Override
    public BdcShxxVO queryMryj(@PathVariable(value = "shxxid") String shxxid) {
        return bdcShxxService.queryMryj(shxxid);
    }

    /**
     * @param bdcShxxDO
     * @return signid
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取sign id
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "保存签名信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcShxxDO", value = "审核信息DO", dataType = "BdcShxxDO", paramType = "body")})
    @Override
    public BdcShxxVO getShxxSign(@RequestBody BdcShxxDO bdcShxxDO) {
        return bdcShxxService.getShxxSign(bdcShxxDO);
    }

    /**
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzlslid 工作流实例ID
     * @return {List} 生成的审核信息
     * @description  生成流程项目所有节点审核信息，意见内容采用默认意见
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "生成流程项目所有节点审核信息，意见内容采用默认意见")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")
    public List<BdcShxxDO> generateShxxOfProInsId(@PathVariable("gzlslid") String gzlslid) {
        return bdcShxxService.generateShxxOfProInsId(gzlslid);
    }


    /**
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param gzlslids 工作流实例ID集合
     * @param userName 当前用户
     * @return boolean
     * @description  初审和二审是否是一样的审核人
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "生成流程项目所有节点审核信息，意见内容采用默认意见")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslids", value = "工作流实例ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userName", value = "用户名称", required = true, dataType = "String")})
    public boolean hasSameShr(@RequestBody List<String> gzlslids,@RequestParam(name = "userName") String userName) {
        return bdcShxxService.hasSameShr(gzlslids,userName);
    }

    /**
     * @param processInsId
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除审核意见和签名信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "删除审核信息pdf")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "query")})
    public void deleteShxxPdf(@RequestParam(name = "processInsId") String processInsId) {
         bdcShxxService.deleteShxxPdf(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取打印参数")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcPrintDTO", value = "打印所需的参数的实体类", dataType = "BdcPrintDTO", paramType = "body")})
    public BdcDysjDTO getPrintDataMap(@RequestBody BdcPrintDTO bdcPrintDTO) {
        return bdcShbdPrintService.getPrintDataMap(bdcPrintDTO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "转发自动签名，意见内容采用默认意见")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")
    public List<BdcShxxDO> zdqm(@PathVariable(value = "processInsId") String processInsId, @RequestParam(name = "currentUserName",required = false) String currentUserName){
        return bdcShxxService.zdqm(processInsId,currentUserName);

    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcShxxQOList 审核信息参数
     * @return List<BdcShxxPdfDTO> 审批表信息
     * @description 获取打印审批表PDF
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取打印审批表PDF")
    @ApiImplicitParam(name = "bdcShxxQOList", value = "审核信息参数", dataType = "BdcShxxQO", paramType = "body")
    public List<BdcShxxPdfDTO> getBdcSpbPdf(@RequestBody List<BdcShxxQO> bdcShxxQOList) {
        return bdcShxxService.getBdcSpbPdf(bdcShxxQOList);
    }

}
