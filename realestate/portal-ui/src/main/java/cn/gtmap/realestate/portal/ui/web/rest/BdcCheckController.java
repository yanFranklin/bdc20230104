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
@Api(tags = "????????????")
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
     * @param processKey ???????????????ID
     * @param gzlslid    ???????????????ID
     * @return ????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ????????????
     */
    @PostMapping("/gzyz/{processKey}/{gzlslid}")
    public Object yzBdcgz(@PathVariable(name = "processKey") String processKey, @PathVariable(name = "gzlslid") String gzlslid,
                          @RequestParam(name = "mbtype") String mbType, @RequestParam(name = "taskid") String taskid) {
        if (StringUtils.isAnyBlank(gzlslid, processKey, mbType)) {
            throw new MissingArgumentException("gzlslid,processKey,mbType");
        }
        //??????????????????????????????
        if (StringUtils.equals(mbType, CommonConstantUtils.GZYZ_LX_ZF)) {
            if (StringUtils.isBlank(taskid)) {
                throw new MissingArgumentException(taskid);
            }
            //?????????????????????
            boolean isptyz = flowableNodeClient.isPlatformVerify(taskid);
            //????????????
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
            throw new MissingArgumentException("???????????????????????????????????????????????????");
        }
        List<BdcGzYzTsxxDTO> listBdcGzYzTsxx = null;
        LOGGER.info("gzlslid:{}?????????????????????{},taskid:{}",gzlslid,checkMb,taskid);
        //???????????????????????????,???????????????????????????????????????????????????
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
     * @description ???????????????
     */
    @PostMapping("/btxyz/{formViewKey}/{gzlslid}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("???????????????")
    @ApiImplicitParams({@ApiImplicitParam(name = "formViewKey", value = "formKey", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "gzlslid", value = "gzlslid", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "taskId", value = "taskId", required = false, dataType = "String", paramType = "query")})
    public Object btxyz(@PathVariable("formViewKey") String formViewKey, @PathVariable("gzlslid") String gzlslid, @RequestParam(required = false) String taskId) {
        if (StringUtils.isAnyBlank(formViewKey, gzlslid)) {
            throw new MissingArgumentException("??????????????? formViewKey???gzlslid ???????????????");
        }
        return bdcBtxYzService.checkBtx(formViewKey, gzlslid, taskId);
    }


    /**
     * @param workFlowDTOList ????????? DTO ??????
     * @return ????????????
     * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ????????????????????????
     */
    @PostMapping("/sign/jd")
    @ApiOperation("???????????????????????????????????????????????????")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "??????????????????"), @ApiResponse(code = 500, message = "??????????????????")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSignList", value = "????????????????????????", required = true, dataType = "BdcSignDTO")})
    @ResponseStatus(code = HttpStatus.OK)
    public boolean signCheck(@RequestBody List<WorkFlowDTO> workFlowDTOList) {
        if (CollectionUtils.isEmpty(workFlowDTOList)) {
            throw new MissingArgumentException("???????????????????????????");
        }
        return bdcSignService.signCheck(workFlowDTOList);
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description ????????????
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
            data = "????????????:" + processDefData.getName() + " "+data;
        }
        map.put(CommonConstantUtils.HLNR, data);
        // ??????data?????????????????????????????????
        // ????????????bdcdyh ??????????????????GB???????????????
        // ????????????????????????data??????????????????bdcdyh,??????????????????????????????????????????
        // ?????????????????? ??????GB????????????bdcdyh,????????????12??????????????????14???

        try{
            if(data.indexOf("GB") != -1) {
                LOGGER.info("????????????bdcdyh????????????{}", data);
                int index = data.lastIndexOf("G");
                String sfuStr = data.substring(index - 12, index);
                String preStr = data.substring(index + 2, index + 2 + 14);
                String bdcdyh = sfuStr + "GB" + preStr;
                map.put("bdcdyh", bdcdyh);
            }
        }catch (Exception e){
            LOGGER.error("??????bdcdyh?????????{}",e.getMessage());
        }
        AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), CommonConstantUtils.GZYZ_HL_ZF, map);
        zipkinAuditEventRepository.add(auditEvent);
    }

    /**
     *  ??????????????????
     * @param  data  ????????????
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
     * ??????????????????????????????
     */
    @ResponseBody
    @PostMapping("/addShxxData")
    public void addShxxData(String data, String slbh, String xmid) {
        bdcGzlwFeignService.addShxxData(data, slbh, xmid);
    }

    /**
     * ?????????????????????
     * @param processKey ????????????key
     * @param gzlslid    ???????????????ID
     * @param mbType     ????????????
     * @param taskid     ??????ID
     * @return Object  ????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ??????/??????????????????
     */
    @Override
    @PostMapping("/feign/gzyz/{processKey}/{gzlslid}")
    public Object feignBdcgz(@PathVariable(name = "processKey") String processKey, @PathVariable(name = "gzlslid") String gzlslid, @RequestParam(name = "mbtype") String mbType, @RequestParam(name = "taskid") String taskid) {
        return yzBdcgz(processKey,gzlslid,mbType,taskid);
    }

    /**
     * @param checkMb  ??????????????????
     * @param gzlslid  ???????????????ID
     * @return ????????????
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description ??????????????????
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
     * ??????????????????????????????????????????
     * @param formViewKey ??????formKey
     * @param gzlslid   ???????????????ID
     * @param taskId    ????????????ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return ???????????????????????????
     */
    @Override
    @PostMapping("/feign/gzyz/btxyz/{formViewKey}/{gzlslid}")
    public Object feignBtxyz(@PathVariable("formViewKey") String formViewKey, @PathVariable("gzlslid") String gzlslid, @RequestParam(required = false) String taskId) {
        if (StringUtils.isAnyBlank(formViewKey, gzlslid)) {
            throw new MissingArgumentException("??????????????? formViewKey???gzlslid ???????????????");
        }
        return bdcBtxYzService.checkBtx(formViewKey, gzlslid, taskId);
    }

    /**
     * ???????????????????????????????????? ???????????? ?????????<br>
     * ????????????: BdcWorkFlowAbstactServiceImpl_nantong#sfJrHlw(java.lang.String)
     *
     * @param processInsId ???????????????ID
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(value = "/feign/gzyz/sfbj/dzzz/{processInsId}")
    @Override
    public boolean sfHyDzzz(@PathVariable(name = "processInsId") String processInsId) {
        String sfJrHlw = bdcWorkFlowAbstactServiceImpl_nantong.sfJrHlwProcessInsId(processInsId);
        // ????????????????????????????????? true
        if (StringUtils.equals(sfJrHlw, "0")) {
            return true;
        }

        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(processInsId);
        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);

        // ???????????????
        if (CollectionUtils.isEmpty(bdcZsDOS)) {
            return true;
        }

        // ?????????????????????????????? storageid????????????????????????????????????????????????
        for (BdcZsDO bdcZsDO : bdcZsDOS) {
            if (StringUtils.isNotBlank(bdcZsDO.getStorageid())) {
                return true;
            }
        }
        return false;
    }
}
