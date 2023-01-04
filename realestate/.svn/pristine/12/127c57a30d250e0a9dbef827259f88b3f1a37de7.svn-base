package cn.gtmap.realestate.inquiry.ui.web.rest.bengbu;


import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlWjscDTO;
import cn.gtmap.realestate.common.core.dto.portal.WorkFlowCommonDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZszmCxFeignService;
import cn.gtmap.realestate.common.core.service.feign.portal.BdcWorkFlowFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDbxxFeignService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0, 16:02 2022/12/27
 * @description 撤销查询controller
 */
@RestController
@RequestMapping(value = "/rest/v1.0/cxcx")
public class BdcCxcxController extends BaseController {
    // 上传的文件夹名称
    private final static String folderName = "撤销材料";
    // 文件管理器默认权限
    private static final String defaultAuthority = "{\"CanRefresh\":1,\"CanCreateNewFolder\":0,\"CanAddFolder\":0,\"CanDeleteFolder\":0,\"CanRenameFolder\":0,\"CanAddFile\":1,\"CanDeleteFile\":1,\"CanRenameFile\":1,\"CanDelete\":1,\"CanRename\":1,\"CanPrint\":1,\"CanDownload\":1,\"CanUpload\":1,\"CanTakePhoto\":1,\"CanScan\":1,\"CanEdit\":-1}";

    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcWorkFlowFeignService bdcWorkFlowFeignService;
    @Autowired
    BdcDbxxFeignService bdcDbxxFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    ProcessTaskClient processTaskClient;
    @Autowired
    BdcZszmCxFeignService bdcZszmCxFeignService;

    /**
     * @description 撤销材料上传文件夹
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/12/30 16:16
     * @param spaceId
     * @return StorageDto
     */
    @ResponseBody
    @GetMapping("/folder")
    public StorageDto createFolder(@RequestParam("spaceId") String spaceId) {
        UserDto userDto = userManagerUtils.getCurrentUser();
        String userid="";
        if(userDto != null){
            userid =userDto.getId();
        }
        // 新建文件夹
        return storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, spaceId, folderName, userid);
    }

    /**
     * @description 获取文件上传参数
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/12/27 17:08
     * @return BdcSlWjscDTO
     */
    @GetMapping("/getBdcSlWjscDTO")
    public BdcSlWjscDTO getBdcSlWjscDTO() {
        BdcSlWjscDTO bdcSlWjscDTO = new BdcSlWjscDTO();
        bdcSlWjscDTO.setToken(getAccountToken());
        bdcSlWjscDTO.setClientId(CommonConstantUtils.WJZX_CLIENTID);
        bdcSlWjscDTO.setsFrmOption(defaultAuthority);
        return bdcSlWjscDTO;
    }

    /**
     * @description 确认撤销
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/12/28 9:54
     * @param workFlowCommonDTO
     * @return Object
     */
    @PostMapping("/abandon")
    public Object abandonTask(@RequestBody WorkFlowCommonDTO workFlowCommonDTO) {
        if (Objects.isNull(workFlowCommonDTO) || (StringUtils.isBlank(workFlowCommonDTO.getProcessInstanceId()) && StringUtils.isBlank(workFlowCommonDTO.getReason()))) {
            throw new MissingArgumentException("撤销原因和流程实例 ID 不能为空！");
        }
        Map<String, String> responseMap = new HashMap<>();
        // 判断是否可以撤销
        String gzlslid = workFlowCommonDTO.getProcessInstanceId();
        // 获取当前节点是否为受理节点
        List<TaskData> processRunningTasks = processTaskClient.processRunningTasks(gzlslid);
        if (CollectionUtils.isEmpty(processRunningTasks)) {
            responseMap.put("code", "500");
            responseMap.put("msg", "工作流实例id" + gzlslid + "未查询到当前流程节点信息");
            return responseMap;
        }
        String taskName = processRunningTasks.get(0).getTaskName();
        if (!CommonConstantUtils.JD_SL.equals(taskName)) {
            responseMap.put("code", "500");
            responseMap.put("msg", "工作流实例id" + gzlslid + "当前节点非受理节点，无法撤销");
            return responseMap;
        }
        // 撤销材料是否为空
        List<StorageDto> storageDtoList = storageClient.queryMenus(CommonConstantUtils.WJZX_CLIENTID, gzlslid, "", 0, 0, null, null, null);
        if (CollectionUtils.isEmpty(storageDtoList)) {
            responseMap.put("code", "500");
            responseMap.put("msg", "工作流实例id" + gzlslid + "缺少撤销材料");
            return responseMap;
        }
        List<StorageDto> cxclStorageDtoList = storageDtoList.stream().filter(storageDto -> folderName.equals(storageDto.getName())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cxclStorageDtoList) || CollectionUtils.isEmpty(cxclStorageDtoList.get(0).getChildren())) {
            responseMap.put("code", "500");
            responseMap.put("msg", "工作流实例id" + gzlslid + "缺少撤销材料");
            return responseMap;
        }
        String isAbandon = bdcWorkFlowFeignService.isAbandon(gzlslid);
        LOGGER.info("验证是否可以撤销结果为{}", isAbandon);
        if (!"success".equals(isAbandon)) {
            responseMap.put("code", "500");
            responseMap.put("msg", "工作流实例id" + gzlslid + "不可以撤销");
            return responseMap;
        }
        // 执行撤销流程
        bdcWorkFlowFeignService.abandonTask(workFlowCommonDTO);
        responseMap.put("code", "200");
        responseMap.put("msg", "工作流实例id" + gzlslid + "撤销成功");
        return responseMap;
    }

    /**
     * @author: <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @param: bdcGzYzQO 规则验证查询参数
     * @param: zhbs 组合标识
     * @return {List} 验证结果
     * @description 撤销流程前规则验证
     */
    @PostMapping("/gzyz")
    public List<Map<String, Object>> checkBeforePrint(@RequestBody BdcGzYzQO bdcGzYzQO) {
        if (null == bdcGzYzQO || StringUtils.isBlank(bdcGzYzQO.getZhbs()) || CollectionUtils.isEmpty(bdcGzYzQO.getBdcGzYzsjDTOList()) ) {
            throw new AppException("验证异常：未指定组合规则标识或验证参数");
        }
        return bdcZszmCxFeignService.checkBdcdyhGz(bdcGzYzQO);
    }

}
