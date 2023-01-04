package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjyySjclPzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.accept.BdcDjyySjclPzQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcDjyySjclPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/28
 * @description 收件材料
 */
@Controller
@RequestMapping("/sjcl")
@Validated
public class BdcSjclController extends BaseController {

    @Autowired
    private StorageClientMatcher storageClient;
    @Value("#{'${slym.sjclmc:}'.split(',')}")
    private List<String> sjclmcList;
    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;
    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcDjyySjclPzFeignService bdcDjyySjclPzFeignService;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    /**
     * 上传文件夹
     *
     * @param gzlslid 工作流实例id
     * @param wjjmc   文件夹名称
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
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
        //页面传入文件名称根据名称创建，否则读取配置信息
        if(StringUtils.isNotBlank(wjjmc)) {
            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(gzlslid, wjjmc);
            StorageDto storageDto = storageClient.createRootFolder("clientId", gzlslid, wjjmc, userDto.getId());
            if (CollectionUtils.isEmpty(bdcSlSjclDOList) && Objects.nonNull(storageDto)) {
                insertBdcslsjclDo(storageDto.getId(), gzlslid, wjjmc);
            }
            return storageDto;
        } else if(CollectionUtils.isNotEmpty(sjclmcList)) {
            for(String clmc: sjclmcList) {
                StorageDto storageDto = storageClient.createRootFolder("clientId", gzlslid, clmc, userDto.getId());
                List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(gzlslid, clmc);
                if (CollectionUtils.isEmpty(bdcSlSjclDOList) && Objects.nonNull(storageDto)) {
                    insertBdcslsjclDo(storageDto.getId(), gzlslid, clmc);
                }
                count++;
            }
        }
        return count;
    }

    /**
     * 上传页面截图
     * @param bdcPdfDTO 文件上传实体类
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PostMapping(value = "/upload/screenshot")
    @ResponseStatus(HttpStatus.OK)
    public void uploadScreenShot(@RequestBody BdcPdfDTO bdcPdfDTO) {
        if (StringUtils.isAnyBlank(bdcPdfDTO.getGzlslid(), bdcPdfDTO.getBase64str())) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例或图片base64字符串。");
        }
        try {
            this.bdcUploadFileUtils.uploadBase64File(bdcPdfDTO);
        } catch (IOException e) {
            throw new AppException(ErrorCode.IO_EX, "图片添加水印异常");
        }
    }

    public void insertBdcslsjclDo(String wjzxid, String gzlslid, String wjmc) {
        BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            bdcSlSjclDO.setDjxl(bdcXmDTOList.get(0).getDjxl());
        }
        bdcSlSjclDO.setGzlslid(gzlslid);
        bdcSlSjclDO.setWjzxid(wjzxid);
        bdcSlSjclDO.setClmc(wjmc);
        bdcSlSjclDO.setFs(1);
        bdcSlSjclDO.setYs(1);
        bdcSlSjclDO.setSjlx(CommonConstantUtils.SJLX_QT);
        bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
    }

    @ResponseBody
    @GetMapping("/djyysjclpz")
    public Object listDjyySjclPz(@NotBlank(message = "查询收件材料配置工作流实例id不可为空") String gzlslid, String djxl) {
        //获取登记原因
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        if (StringUtils.isNotBlank(djxl)) {
            bdcXmQO.setDjxl(djxl);
        }
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        String djyy = "";
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            //未获取到登记数据查询受理数据
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                djyy = bdcSlXmDOList.get(0).getDjyy();
                djxl = bdcSlXmDOList.get(0).getDjxl();
            }
        } else {
            djyy = bdcXmDOList.get(0).getDjyy();
        }
        BdcDjyySjclPzQO bdcDjyySjclPzQO = new BdcDjyySjclPzQO();
        bdcDjyySjclPzQO.setDjyy(djyy);
        bdcDjyySjclPzQO.setDjxl(djxl);
        bdcDjyySjclPzQO.setSfbc(CommonConstantUtils.SF_F_DM);
        List<BdcDjyySjclPzDO> bdcDjyySjclPzDOList = bdcDjyySjclPzFeignService.querySjclPz(bdcDjyySjclPzQO);
        LOGGER.info("当前流程实例id{}登记原因{}，登记小类{},获取到的收件材料配置{}", gzlslid, djyy, djxl, bdcDjyySjclPzDOList);
        if (CollectionUtils.isNotEmpty(bdcDjyySjclPzDOList)) {
            return bdcDjyySjclPzDOList;
        }
        return null;
    }


    /**
     * 获取文件夹下的文件的storage地址
     * @param gzlslid 工作流实例ID
     * @param wjjmc 文件夹名称
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 文件信息
     */
    @ResponseBody
    @GetMapping("/getFileStorageUrl")
    public Object getFileStorageUrl(@RequestParam("gzlslid") String gzlslid,
                                    @RequestParam(value = "wjjmc",required = false) String wjjmc) {
        if(StringUtils.isAnyBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID或文件夹名称");
        }
        List<BdcSlSjclDO> bdcSlSjclDOList =this.bdcSlSjclFeignService.listBdcSlSjclByClmc(gzlslid, wjjmc);
        if(CollectionUtils.isEmpty(bdcSlSjclDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到" + wjjmc + "收件材料信息" );
        }
        List<StorageDto> listFile = storageClient.listAllSubsetStorages(bdcSlSjclDOList.get(0).getWjzxid(), "", 1, 1,0,null);
        return listFile;
    }
}
