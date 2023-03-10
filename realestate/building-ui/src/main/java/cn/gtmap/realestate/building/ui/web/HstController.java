package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.building.ui.util.ParseUtils;
import cn.gtmap.realestate.building.ui.util.StorageImgUtils;
import cn.gtmap.realestate.building.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwHstDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.building.FwHstRequestDTO;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHstFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwLjzFeginService;
import cn.gtmap.realestate.common.core.service.feign.building.FwYcHsFeignService;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-24
 * @description
 */
@Controller
@RequestMapping("/hst")
public class HstController extends BaseController{

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private FwHsFeignService fwHsFeignService;

    @Autowired
    private FwLjzFeginService fwLjzFeginService;

    @Autowired
    private FwHstFeignService fwHstFeignService;
    @Autowired
    private FwYcHsFeignService fwYcHsFeignService;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param model
     * @param fwHsIndex
     * @return java.lang.String
     * @description ?????????????????????
     */
    @RequestMapping("/fwhstview")
    public String initFwHstView(Model model,@NotBlank(message = "????????????????????????") String fwHsIndex){
        model.addAttribute("fwHsIndex",fwHsIndex);
        return "hst/fwHstView";
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHsIndex
     * @return java.util.Map
     * @description ?????????????????????
     */
    @ResponseBody
    @RequestMapping("/queryfwhst")
    public Map queryFwHst(@NotBlank(message = "????????????????????????") String fwHsIndex,@NotBlank(message = "????????????????????????") String fwlx){
        Map resultMap = returnHtmlResult(true,"????????????");
        FwHsDO fwHsDO = new FwHsDO();
        if(StringUtils.equals("sc",fwlx)){
            fwHsDO = fwHsFeignService.queryFwHsByFwHsIndex(fwHsIndex,"");
        }else if(StringUtils.equals("yc",fwlx)){
            FwYchsDO fwYchsDO = fwYcHsFeignService.queryFwYcHsByFwHsIndex(fwHsIndex,"");
            BeanUtils.copyProperties(fwYchsDO,fwHsDO);
        }
        if(fwHsDO != null && StringUtils.isNotBlank(fwHsDO.getFwHstIndex())){
            resultMap.put("fwHstIndex",fwHsDO.getFwHstIndex());
            // ??????????????????
            FwHstDO fwHstDO = fwHstFeignService.queryHstByIndex(fwHsDO.getFwHstIndex(),"");
            // ??????????????? ??????????????????????????????  ??? base64
            // todo ?????????????????? ??????filecenter ??? ???????????????
            if(fwHstDO != null){
                String baseCode = ParseUtils.blobToStr(fwHstDO.getHst());
                if(StringUtils.isNotBlank(fwHstDO.getHstdownid())){
                    // ??????????????????????????????
                    String srcUrl = EnvironmentConfig.getEnvironment().getProperty("app.storage")
                            + "/rest/files/download/" + fwHstDO.getHstdownid();
                    resultMap.put("src", srcUrl);
                }else if(StringUtils.isNotBlank(baseCode)){
                    // base64 ??????
                    resultMap.put("src", baseCode);
                }
            }
        }
        return resultMap;
    }
    @ResponseBody
    @RequestMapping("/queryfwhstbybdcdyh")
    public Map queryFwHstByBdcdyh(@NotBlank(message = "??????????????????????????????") String bdcdyh){
        FwHsDO fwHsDO=fwHsFeignService.queryFwhsByBdcdyh(bdcdyh,"");
        String fwHsIndex=null;
        if(fwHsDO!=null){
            fwHsIndex=fwHsDO.getFwHsIndex();
        }
        return queryFwHst(fwHsIndex,"sc");
    }
    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param authentication
     * @param fwHsIndex ????????????
     * @param file ??????
     * @return java.util.Map
     * @description ???????????????
     */
    @ResponseBody
    @PostMapping("/uploadfwhst")
    public Map uploadfwhst(Authentication authentication,
                         @NotBlank(message = "????????????????????????") @RequestParam("fwHsIndex") String fwHsIndex,
                           @NotBlank(message = "????????????????????????") @RequestParam("fwlx") String fwlx,
                           @RequestParam("fwHstIndex") String fwHstIndex,
                         @NotNull(message = "??????????????????") @RequestParam("file") MultipartFile file) throws IOException {
        Map resultMap = returnHtmlResult(false,"????????????");
        String currentUserName = authentication.getName();
        MultipartDto multipartDto = StorageImgUtils.getUploadParamDto(currentUserName,file);
        StorageDto storageDto = storageClient.multipartUpload(multipartDto);
        if(storageDto != null){
            // ???????????????????????????
            FwHstRequestDTO fwHstRequestDTO = new FwHstRequestDTO();
            fwHstRequestDTO.setFwHsIndex(fwHsIndex);
            fwHstRequestDTO.setFwHstIndex(fwHstIndex);
            fwHstRequestDTO.setHstmc(multipartDto.getOriginalFilename());
            fwHstRequestDTO.setDownId(storageDto.getId());
            fwHstRequestDTO.setJlyhm(currentUserName);
            fwHstRequestDTO.setFwlx(fwlx);
            FwHstDO fwHstDO = fwHstFeignService.saveHst(fwHstRequestDTO);
            if(fwHstDO != null){
                resultMap = returnHtmlResult(true,"????????????");
                resultMap.put("imgId",storageDto.getId());
                resultMap.put("fwHstIndex",fwHstDO.getFwHstIndex());
            }
        }
        return resultMap;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwHsIndex
     * @return java.util.Map
     * @description ?????????????????????
     */
    @ResponseBody
    @RequestMapping("/delfwhst")
    public Map delFwHst(@NotBlank(message = "????????????????????????") String fwHsIndex,@NotBlank(message = "????????????????????????") String hslx){
        fwHstFeignService.delFwhsHst(fwHsIndex,hslx,"");
        return returnHtmlResult(true,"????????????");
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwDcbIndex
     * @return java.util.Map
     * @description ????????????????????????
     */
    @ResponseBody
    @RequestMapping("/queryfwljzpmt")
    public Map queryFwLjzPmt(@NotBlank(message = "???????????????????????????") String fwDcbIndex){
        Map resultMap = returnHtmlResult(true,"????????????");
        resultMap.put("fwHstIndex",fwDcbIndex);
        // ??????????????????
        FwHstDO fwHstDO = fwHstFeignService.queryHstByIndex(fwDcbIndex,"");
        // ??????????????? ??????????????????????????????  ??? base64
        // todo ?????????????????? ??????filecenter ??? ???????????????
        if(fwHstDO != null){
            String baseCode = ParseUtils.blobToStr(fwHstDO.getHst());
            if(StringUtils.isNotBlank(fwHstDO.getHstdownid())){
                // ??????????????????????????????
                String srcUrl = EnvironmentConfig.getEnvironment().getProperty("app.storage")
                        + "/rest/files/download/" + fwHstDO.getHstdownid();
                resultMap.put("src", srcUrl);
            }else if(StringUtils.isNotBlank(baseCode)){
                // base64 ??????
                resultMap.put("src", baseCode);
            }
        }
        return resultMap;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param authentication
     * @param fwDcbIndex
     * @param file ??????
     * @return java.util.Map
     * @description ????????? ?????????
     */
    @ResponseBody
    @PostMapping("/uploadfwljzpmt")
    public Map uploadfwLjzPmt(Authentication authentication,
                           @NotBlank(message = "???????????????????????????") @RequestParam("fwDcbIndex") String fwDcbIndex,
                           @NotNull(message = "??????????????????") @RequestParam("file") MultipartFile file) throws IOException {
        Map resultMap = returnHtmlResult(false,"????????????");
        String currentUserName = authentication.getName();
        MultipartDto multipartDto = StorageImgUtils.getUploadParamDto(currentUserName,file);
        StorageDto storageDto = storageClient.multipartUpload(multipartDto);
        if(storageDto != null){
            // ???????????????????????????
            FwHstRequestDTO fwHstRequestDTO = new FwHstRequestDTO();
            fwHstRequestDTO.setFwHstIndex(fwDcbIndex);
            fwHstRequestDTO.setHstmc(multipartDto.getOriginalFilename());
            fwHstRequestDTO.setDownId(storageDto.getId());
            fwHstRequestDTO.setJlyhm(currentUserName);
            FwHstDO fwHstDO = fwHstFeignService.saveLjzPmt(fwHstRequestDTO);
            if(fwHstDO != null){
                resultMap = returnHtmlResult(true,"????????????");
                resultMap.put("imgId",storageDto.getId());
                resultMap.put("fwHstIndex",fwHstDO.getFwHstIndex());
            }
        }
        return resultMap;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwDcbIndex
     * @return java.util.Map
     * @description ????????????????????????
     */
    @ResponseBody
    @RequestMapping("/delfwljzpmt")
    public Map delFwLjzPmt(@NotBlank(message = "???????????????????????????") String fwDcbIndex){
        fwHstFeignService.delFwLjzPmt(fwDcbIndex,"");
        return returnHtmlResult(true,"????????????");
    }


    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param authentication
     * @param fwHsIndexList ????????????
     * @param file ??????
     * @return java.util.Map
     * @description ??????????????????????????????????????????????????????
     */
    @ResponseBody
    @PostMapping("/uploadonehsttomanyhs")
    public Map uploadOneHstToManyHs(Authentication authentication,
                                    @RequestParam("fwHsIndexList") List<String> fwHsIndexList,
                                    @NotNull(message = "??????????????????") @RequestParam("file") MultipartFile file) throws IOException {
        Map resultMap = returnHtmlResult(false,"????????????");
        String currentUserName = authentication.getName();
        MultipartDto multipartDto = StorageImgUtils.getUploadParamDto(currentUserName,file);
        StorageDto storageDto = storageClient.multipartUpload(multipartDto);
        if(storageDto != null){
            // ???????????????????????????
            FwHstRequestDTO fwHstRequestDTO = new FwHstRequestDTO();
            fwHstRequestDTO.setHstmc(multipartDto.getOriginalFilename());
            fwHstRequestDTO.setDownId(storageDto.getId());
            fwHstRequestDTO.setJlyhm(currentUserName);
            FwHstDO fwHstDO = fwHstFeignService.saveHst(fwHstRequestDTO);
            if(fwHstDO != null){
                for(String fwHsIndex:fwHsIndexList){
                    FwHsDO fwHsDO=fwHsFeignService.queryFwHsByFwHsIndex(fwHsIndex,"");
                    fwHsDO.setFwHstIndex(fwHstDO.getFwHstIndex());
                    fwHsFeignService.updateFwHs(fwHsDO,false,"");
                }
                resultMap = returnHtmlResult(true,"????????????");
                resultMap.put("imgId",storageDto.getId());
                resultMap.put("fwHstIndex",fwHstDO.getFwHstIndex());
            }
        }
        return resultMap;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param model
     * @param fwHsIndexList
     * @return java.lang.String
     * @description  ????????????????????????????????????
     */
    @RequestMapping("/fwhsttomanyhsview")
    public String fwHstToManyHsView(Model model,@RequestParam("fwHsIndexList") List<String> fwHsIndexList){
        model.addAttribute("fwHsIndexList",fwHsIndexList);
        return "hst/fwHstToManyHsView";
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param model
     * @param fwDcbIndex
     * @return java.lang.String
     * @description ?????????????????????
     */
    @RequestMapping("/batchupload")
    public String batchUploadHst(Model model,String fwDcbIndex){
        model.addAttribute("fwDcbIndex",fwDcbIndex);
        return "hst/batchUploadHst";
    }


}
