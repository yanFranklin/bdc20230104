package cn.gtmap.realestate.portal.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.matcher.FlowableNodeClientMatcher;
import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.realestate.common.matcher.TaskHandleClientMatcher;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcGzlwFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcGzyzFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcYcslDjywDzbFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcShxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcXtMryjFeignService;
import cn.gtmap.realestate.common.core.service.rest.init.BdcXmRestService;
import cn.gtmap.realestate.common.core.service.rest.portal.BdcCheckRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import cn.gtmap.realestate.portal.ui.core.dto.WorkFlowDTO;
import cn.gtmap.realestate.portal.ui.service.BdcBtxYzService;
import cn.gtmap.realestate.portal.ui.service.BdcGzyzDealService;
import cn.gtmap.realestate.portal.ui.service.BdcSignService;
import cn.gtmap.realestate.portal.ui.service.impl.workflow.BdcWorkFlowAbstactServiceImpl_nantong;
import cn.gtmap.realestate.portal.ui.web.main.BaseController;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.gtmap.realestate.common.util.BdcGzyzTsxxUtils.checkTsxx;
/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/4/8.
 * @description
 */
@RestController
@RequestMapping("/rest/v1.0/check")
@Api(tags = "验证功能")
public class BdcCheckController extends BaseController implements BdcCheckRestService {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    BdcGzZhGzFeignService bdcGzZhGzFeignService;
    @Autowired
    BdcXmRestService bdcXmRestService;
    @Autowired
    BdcBtxYzService bdcBtxYzService;
    @Autowired
    WorkFlowUtils workFlowUtils;
    @Autowired
    BdcXtMryjFeignService bdcXtMryjFeignService;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    BdcShxxFeignService bdcShxxFeignService;
    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    @Autowired
    BdcSignService bdcSignService;
    @Autowired
    FlowableNodeClientMatcher flowableNodeClient;
    @Autowired
    TaskHandleClientMatcher taskHandleClient;
    @Autowired
    BdcYcslDjywDzbFeignService bdcYcslDjywDzbFeignService;
    @Autowired
    BdcGzyzFeignService bdcAcceptGzyzFeignService;
    @Autowired
    BdcGzyzDealService bdcGzyzDealService;
    @Autowired
    ProcessDefinitionClient processDefinitionClient;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcGzlwFeignService bdcGzlwFeignService;
    @Autowired
    BdcWorkFlowAbstactServiceImpl_nantong bdcWorkFlowAbstactServiceImpl_nantong;
    @Autowired
    BdcZsFeignService bdcZsFeignService;

    /**
     * @param processKey 工作流定义ID
     * @param gzlslid    工作流实例ID
     * @return 验证结果
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 规则验证
     */
    @PostMapping("/gzyz/{processKey}/{gzlslid}")
    public Object yzBdcgz(@PathVariable(name = "processKey") String processKey, @PathVariable(name = "gzlslid") String gzlslid,
                          @RequestParam(name = "mbtype") String mbType, @RequestParam(name = "taskid") String taskid) {
        if (StringUtils.isAnyBlank(gzlslid, processKey, mbType)) {
            throw new MissingArgumentException("gzlslid,processKey,mbType");
        }
        //转发的先查看平台配置
        if (StringUtils.equals(mbType, CommonConstantUtils.GZYZ_LX_ZF)) {
            if (StringUtils.isBlank(taskid)) {
                throw new MissingArgumentException(taskid);
            }
            //判定是否走平台
            boolean isptyz = flowableNodeClient.isPlatformVerify(taskid);
            //平台验证
            if (isptyz) {
                return bdcGzyzDealService.ptyz(taskid);
            }
        }
        String checkMb;
        switch (mbType) {
            case CommonConstantUtils.GZYZ_LX_TH:
                checkMb = CommonConstantUtils.GZYZ_MB_TH;
                break;
            case CommonConstantUtils.GZYZ_LX_ZF:
                checkMb = CommonConstantUtils.GZYZ_MB_ZF;
                break;
            case CommonConstantUtils.GZYZ_LX_SC:
                checkMb = CommonConstantUtils.GZYZ_MB_SC;
                break;
            case CommonConstantUtils.GZYZ_LX_RL:
                checkMb = CommonConstantUtils.GZYZ_MB_RL;
                break;
            case CommonConstantUtils.GZYZ_LX_SQSC:
                checkMb = CommonConstantUtils.GZYZ_MB_SQSC;
                break;
            default:
                checkMb = "";
        }
        if (StringUtils.isBlank(checkMb)) {
            throw new MissingArgumentException("验证规则模板类型不是标准模板类型！");
        }
        List<BdcGzYzTsxxDTO> listBdcGzYzTsxx = null;
        LOGGER.info("gzlslid:{}调用组合规则：{},taskid:{}",gzlslid,checkMb,taskid);
        //判空是否为一窗流程,一窗流程调用非登记流程规则验证接口
        if(bdcYcslDjywDzbFeignService.checkIsYcslLc(processKey)){
            listBdcGzYzTsxx =bdcAcceptGzyzFeignService.fdjlcGzyz(gzlslid,checkMb.replace("processKey", processKey));
        }else {
            UserDto userDto =userManagerUtils.getCurrentUser();
            listBdcGzYzTsxx = bdcGzyzDealService.yzgz(gzlslid, checkMb, processKey,userDto);
        }
        return checkTsxx(listBdcGzYzTsxx);
    }

    /**
     * @param formViewKey gzlslid
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 必填项验证
     */
    @PostMapping("/btxyz/{formViewKey}/{gzlslid}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("必填项验证")
    @ApiImplicitParams({@ApiImplicitParam(name = "formViewKey", value = "formKey", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "gzlslid", value = "gzlslid", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "taskId", value = "taskId", required = false, dataType = "String", paramType = "query")})
    public Object btxyz(@PathVariable("formViewKey") String formViewKey, @PathVariable("gzlslid") String gzlslid, @RequestParam(required = false) String taskId) {
        if (StringUtils.isAnyBlank(formViewKey, gzlslid)) {
            throw new MissingArgumentException("必填项验证 formViewKey、gzlslid 不能为空！");
        }
        return bdcBtxYzService.checkBtx(formViewKey, gzlslid, taskId);
    }


    /**
     * @param workFlowDTOList 工作流 DTO 集合
     * @return 是否签名
     * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 签名验证并且签名
     */
    @PostMapping("/sign/jd")
    @ApiOperation("验证该流程的节点是否可签名并且签名")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSignList", value = "签名数据结构结合", required = true, dataType = "BdcSignDTO")})
    @ResponseStatus(code = HttpStatus.OK)
    public boolean signCheck(@RequestBody List<WorkFlowDTO> workFlowDTOList) {
        if (CollectionUtils.isEmpty(workFlowDTOList)) {
            throw new MissingArgumentException("签名信息不能为空！");
        }
        return bdcSignService.signCheck(workFlowDTOList);
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存日志
     */
    @ResponseBody
    @PostMapping("/addIgnoreLog")
    public void addIgnoreLog(@RequestBody Map<String,Object> dataMap) {
        String data = MapUtils.getString(dataMap,"data");
        String gzlslid = MapUtils.getString(dataMap,"gzlslid");
        UserDto userDto = userManagerUtils.getCurrentUser();
        ProcessDefData processDefData = new ProcessDefData();
        Map<String, Object> map = new HashMap<>(4);
        map.put(CommonConstantUtils.VIEW_TYPE, CommonConstantUtils.PORTAL_UI);
        map.put(CommonConstantUtils.VIEW_TYPE_NAME, CommonConstantUtils.HLLX);
        map.put(CommonConstantUtils.ALIAS, userDto != null ? userDto.getAlias() : userManagerUtils.getCurrentUserName());
        if(StringUtils.isNotBlank(gzlslid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(list)){
                map.put(CommonConstantUtils.SLBH,list.get(0).getSlbh());
                map.put("gzldyid", list.get(0).getGzldyid());
                processDefData = processDefinitionClient.getProcessDefByProcessDefKey(list.get(0).getGzldyid());
            }
        }
        if(processDefData != null && StringUtils.isNotBlank(processDefData.getName())) {
            data = "流程名称:" + processDefData.getName() + " "+data;
        }
        map.put(CommonConstantUtils.HLNR, data);
        // 解析data中是否包含不动产单元号
        // 我们认为bdcdyh 里面必须包含GB俩个字母，
        // 以这个为根据判断data里面是否包含bdcdyh,有的话则根据以下规则截取出来
        // 目前的规则是 包含GB的则包含bdcdyh,往前截取12位，往后截取14位

        try{
            if(data.indexOf("GB") != -1) {
                LOGGER.info("开始截取bdcdyh，原值：{}", data);
                int index = data.lastIndexOf("G");
                String sfuStr = data.substring(index - 12, index);
                String preStr = data.substring(index + 2, index + 2 + 14);
                String bdcdyh = sfuStr + "GB" + preStr;
                map.put("bdcdyh", bdcdyh);
            }
        }catch (Exception e){
            LOGGER.error("截取bdcdyh出错：{}",e.getMessage());
        }
        AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), CommonConstantUtils.GZYZ_HL_ZF, map);
        zipkinAuditEventRepository.add(auditEvent);
    }

    /**
     *  保存例外日志
     * @param  data  日志内容
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/addLwLog")
    public void addLwLog(@RequestBody String data) {
        UserDto userDto = userManagerUtils.getCurrentUser();
        Map<String, Object> map = new HashMap<>(4);
        map.put(CommonConstantUtils.VIEW_TYPE, CommonConstantUtils.PORTAL_UI);
        map.put(CommonConstantUtils.VIEW_TYPE_NAME, CommonConstantUtils.LWLX);
        map.put(CommonConstantUtils.ALIAS, userDto != null ? userDto.getAlias() : userManagerUtils.getCurrentUserName());
        map.put(CommonConstantUtils.LWNR, data);
        AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), CommonConstantUtils.GZYZ_LW, map);
        zipkinAuditEventRepository.add(auditEvent);
    }

    /**
     * 增加规则例外审核信息
     */
    @ResponseBody
    @PostMapping("/addShxxData")
    public void addShxxData(String data, String slbh, String xmid) {
        bdcGzlwFeignService.addShxxData(data, slbh, xmid);
    }

    /**
     * 对外的验证服务
     * @param processKey 流程定义key
     * @param gzlslid    工作流实例ID
     * @param mbType     模板类型
     * @param taskid     任务ID
     * @return Object  验证结果
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 转发/退回规则验证
     */
    @Override
    @PostMapping("/feign/gzyz/{processKey}/{gzlslid}")
    public Object feignBdcgz(@PathVariable(name = "processKey") String processKey, @PathVariable(name = "gzlslid") String gzlslid, @RequestParam(name = "mbtype") String mbType, @RequestParam(name = "taskid") String taskid) {
        return yzBdcgz(processKey,gzlslid,mbType,taskid);
    }

    /**
     * @param checkMb  验证规则内容
     * @param gzlslid  工作流实例ID
     * @return 验证结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 特殊规则验证
     */
    @GetMapping("/tsgzyz/{checkMb}/{gzlslid}")
    public Object yzBdcTsgz(@PathVariable(name = "checkMb") String checkMb, @PathVariable(name = "gzlslid") String gzlslid) {
        if (StringUtils.isAnyBlank(gzlslid, checkMb)) {
            throw new MissingArgumentException("gzlslid, checkMb");
        }
        UserDto userDto =userManagerUtils.getCurrentUser();
        List<BdcGzYzTsxxDTO> listBdcGzYzTsxx = bdcGzyzDealService.yzgz(gzlslid, checkMb, "processKey",userDto);
        return checkTsxx(listBdcGzYzTsxx);
    }

    /**
     * 提供对外的表单必填项验证服务
     * @param formViewKey 表单formKey
     * @param gzlslid   工作流实例ID
     * @param taskId    任务实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 表单必填项验证结果
     */
    @Override
    @PostMapping("/feign/gzyz/btxyz/{formViewKey}/{gzlslid}")
    public Object feignBtxyz(@PathVariable("formViewKey") String formViewKey, @PathVariable("gzlslid") String gzlslid, @RequestParam(required = false) String taskId) {
        if (StringUtils.isAnyBlank(formViewKey, gzlslid)) {
            throw new MissingArgumentException("必填项验证 formViewKey、gzlslid 不能为空！");
        }
        return bdcBtxYzService.checkBtx(formViewKey, gzlslid, taskId);
    }

    /**
     * 验证特殊流程附件是否含有 电子证照 文件夹<br>
     * 特殊流程: BdcWorkFlowAbstactServiceImpl_nantong#sfJrHlw(java.lang.String)
     *
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(value = "/feign/gzyz/sfbj/dzzz/{processInsId}")
    @Override
    public boolean sfHyDzzz(@PathVariable(name = "processInsId") String processInsId) {
        String sfJrHlw = bdcWorkFlowAbstactServiceImpl_nantong.sfJrHlwProcessInsId(processInsId);
        // 流程不需要验证直接返回 true
        if (StringUtils.equals(sfJrHlw, "0")) {
            return true;
        }

        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(processInsId);
        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);

        // 未生成证书
        if (CollectionUtils.isEmpty(bdcZsDOS)) {
            return true;
        }

        // 生成证书判断是否存在 storageid，不存在说明没有下载成功电子证照
        for (BdcZsDO bdcZsDO : bdcZsDOS) {
            if (StringUtils.isNotBlank(bdcZsDO.getStorageid())) {
                return true;
            }
        }
        return false;
    }
}
