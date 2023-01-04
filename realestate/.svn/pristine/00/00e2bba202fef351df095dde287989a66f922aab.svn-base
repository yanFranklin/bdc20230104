package cn.gtmap.realestate.common.core.support.fjcl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @description 附件材料服务
 */
@RestController
@RequestMapping(value = "/rest/v1.0/fjcl")
public class FjclController {

    /**
     * 附件服务
     */
    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    ProcessInstanceClientMatcher processInstanceClient;

    /**
     * 用户服务
     */
    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    /**
     * 附件上传通用服务
     */
    @Autowired
    private BdcUploadFileUtils bdcUploadFileUtils;

    /**
     * 上传文件夹
     *
     * @param gzlslid 工作流实例id
     * @param wjjmc   文件夹名称
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     */
    @ResponseBody
    @GetMapping("/folder")
    public Object createFolder(@RequestParam("gzlslid") String gzlslid, @RequestParam(value = "wjjmc",required = false) String wjjmc) {
        int count = 0;
        if(StringUtils.isBlank(gzlslid)) {
            throw new AppException("创建文件夹缺失gzlslid");
        }
        UserDto userDto = userManagerUtils.getCurrentUser();
        if(Objects.isNull(userDto)) {
            throw new AppException("创建文件夹时未获取到用户信息");
        }
        //页面传入文件名称根据名称创建
        if(StringUtils.isNotBlank(wjjmc)) {
            StorageDto storageDto = storageClient.createRootFolder("clientId", gzlslid, wjjmc, userDto.getId());
            ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(gzlslid);
            if (Objects.nonNull(processInstanceData) && StringUtils.isNotBlank(processInstanceData.getProcessInstanceId())) {
                //如果是流程数据，判断是否存在受理库文件
                List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(gzlslid, wjjmc);
                if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
                    List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
                    BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
                    bdcSlSjclDO.setFs(1);
                    bdcSlSjclDO.setYs(1);
                    bdcSlSjclDO.setGzlslid(gzlslid);
                    bdcSlSjclDO.setClmc(wjjmc);
                    bdcSlSjclDO.setDjxl(CollectionUtils.isNotEmpty(bdcXmDTOS) ? bdcXmDTOS.get(0).getDjxl() : "");
                    bdcSlSjclDO.setWjzxid(storageDto.getId());
                    bdcSlSjclDO.setSjlx(CommonConstantUtils.SJLX_QT);
                    bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
                }
            }
            return storageDto;
        }
        return count;
    }

    /**
     * 上传页面截图
     * <p>文件上传实体类必须参数：</p>
     * @param bdcPdfDTO 文件上传实体类
     * 必要参数：base64str  文件Base64字符串; gzlslid 工作流实例ID; foldname 文件夹名称;pdffjmc 文件名称;fileSuffix 文件后缀名）
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping(value = "/upload/screenshot")
    @ResponseStatus(HttpStatus.OK)
    public void uploadScreenShot(@RequestBody BdcPdfDTO bdcPdfDTO){
        if(StringUtils.isAnyBlank(bdcPdfDTO.getGzlslid(), bdcPdfDTO.getBase64str())){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例或图片base64字符串。");
        }
        try{
            this.bdcUploadFileUtils.uploadBase64File(bdcPdfDTO);
        }catch(IOException e){
            throw new AppException(ErrorCode.IO_EX, "图片添加水印异常");
        }
    }
}
