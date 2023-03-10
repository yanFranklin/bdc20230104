package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.BdcShxxDO;
import cn.gtmap.realestate.common.core.domain.BdcXtMryjDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.register.BdcShxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcShxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcXtMryjFeignService;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcDjxlPzRestService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcShxxVO;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import cn.gtmap.realestate.register.ui.config.ShxxZhlcPrintDTO;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/12/14
 * @description ????????????????????????
 */
@RestController
@RequestMapping("/rest/v1.0/shxx")
public class BdcShxxController extends BaseController {
    @Autowired
    BdcXtMryjFeignService bdcXtMryjFeignService;
    @Autowired
    BdcShxxFeignService bdcShxxFeignService;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    WorkFlowUtils workFlowUtils;
    @Autowired
    BdcEntityFeignService bdcEntityFeignService;
    @Autowired
    BdcXtMryjFeignService bdcXtMryjRestService;
    @Autowired
    ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    BdcDjxlPzRestService bdcDjxlPzRestService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    ShxxZhlcPrintDTO shxxZhlcPrintDTO;

    @Value("${shxx.print.zhlcsplit:false}")
    private Boolean shxxSplit;

    @Value("#{'${shxx.print.zhajlcdyid:}'.split(',')}")
    private List<String> zhajlcList;


    /**
     * @param gzlslid       ???????????????ID
     * @param currentJdmc   ??????????????????
     * @param taskId        ????????????ID??????????????????shxxid
     * @param onlyCurrentJd ?????????????????????????????????????????????true ???????????????????????????????????????????????????????????????????????????
     * @return List<BdcShxxVO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     */
    @GetMapping(value = "/list")
    public Object listShxx(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "taskId") String taskId
            , @RequestParam(name = "currentJdmc") String currentJdmc, @RequestParam(name = "onlyCurrentJd") Boolean onlyCurrentJd) {
        BdcShxxQO bdcShxxQO = new BdcShxxQO();
        bdcShxxQO.setJdmc(currentJdmc);
        bdcShxxQO.setGzlslid(gzlslid);
        bdcShxxQO.setShxxid(taskId);
        bdcShxxQO.setOnlyCurrentJd(onlyCurrentJd);
        bdcShxxQO.setSignImageUrl(signImageUrl);
        // ?????????????????????????????????????????????????????????????????????
        if(StringUtils.isNotBlank(signImageWwUrl)){
            bdcShxxQO.setSignImageUrl(signImageWwUrl);
        }
        return bdcShxxFeignService.queryShJdxx(bdcShxxQO);
    }

    /**
     * @param taskId ??????Id
     * @return BdcShxxDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ??????????????????????????????????????????????????????????????????
     */
    @GetMapping(value = "/mryj/{taskId}")
    public BdcShxxVO queryMryj(@PathVariable(name = "taskId") String taskId) {
        return bdcShxxFeignService.queryMryj(taskId);
    }

    /**
     * ????????????????????????????????????
     *
     * @param gzlslid gzlslid
     * @return java.lang.Boolean ????????????
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    @GetMapping(value = "/process/{gzlslid}")
    public Boolean isEnded(@PathVariable(name = "gzlslid") String gzlslid) {
        ProcessInstanceData processInstance = processInstanceClient.getProcessInstance(gzlslid);
        if (processInstance == null) {
            throw new AppException("???????????????????????????");
        }
        return processInstance.isEnded();
    }

    /**
     * @param bdcShxxDO
     * @return signid
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description ??????sign id
     */
    @PutMapping(value = "/sign")
    public BdcShxxVO sign(@RequestBody BdcShxxDO bdcShxxDO) {
        BdcShxxVO bdcShxxVO = bdcShxxFeignService.getShxxSign(bdcShxxDO);
        bdcShxxVO.setQmtpdz(signImageUrl + bdcShxxVO.getQmid());
        // ?????????????????????????????????????????????????????????????????????
        if(StringUtils.isNotBlank(signImageWwUrl)){
            bdcShxxVO.setQmtpdz(signImageWwUrl + bdcShxxVO.getQmid());
        }
        return bdcShxxVO;
    }

    /**
     * @param shxxid ????????????ID
     * @return ???????????????
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ??????????????????
     */
    @DeleteMapping(value = "/sign/{shxxid}")
    public int deleteSign(@PathVariable(name = "shxxid") String shxxid) {
        if (StringUtils.isBlank(shxxid)) {
            throw new MissingArgumentException("????????????ID??????!");
        }
        return bdcShxxFeignService.deleteShxxSign(shxxid);
    }

    /**
     * @param taskId
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description ???????????????????????????
     */
    @GetMapping(value = "/kxyj")
    public List<BdcXtMryjDO> queryKxyj(@RequestParam(value = "taskId") String taskId) {
        if (StringUtils.isEmpty(taskId)) {
            throw new MissingArgumentException("taskId");
        }
        TaskData taskData = workFlowUtils.getTaskById(taskId);
        String jdmc = taskData.getTaskName();
        String gzldyKey = taskData.getProcessKey();
        return bdcXtMryjFeignService.listBdcXtKxyj(null, gzldyKey, jdmc);
    }

    /**
     * @param paramList
     * @return int
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ??????????????????????????????????????????
     */
    @PatchMapping(value = "/shxxList")
    public int updateShxx(@RequestBody List<BdcShxxDO> paramList) {
        if (CollectionUtils.isNotEmpty(paramList)) {
            return bdcShxxFeignService.updateShxxList(paramList);
        }
        return -1;
    }

    @GetMapping(value = "dylx/{gzlslid}")
    public Map<String, List<String>> getShxxDylx(@PathVariable(value = "gzlslid") String gzlslid) {
        Map<String, List<String>> dylx = new HashMap();
        if (StringUtils.isBlank(gzlslid)) {
            return dylx;
        }
        return bdcShxxFeignService.getShxxDylx(gzlslid);
    }


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????
     * @date : 2020/6/29 10:15
     */
    @GetMapping("/lclx")
    public int queryLclx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");
        }
        return bdcXmFeignService.makeSureBdcXmLx(gzlslid);
    }


    @GetMapping("/shxxBtn")
    public Map queryShxxBtn(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");
        }
        Map resultMap = new HashMap(2);
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        resultMap.put("lclx", xmlx);
        resultMap.put("zhajlc", false);
        if (shxxSplit && Objects.nonNull(shxxZhlcPrintDTO)) {
            //??????????????????id????????????id
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                //??????????????????
                bdcXmDTOList = bdcXmDTOList.stream().filter(bdcXmDTO -> StringUtils.isNotBlank(bdcXmDTO.getDjxl())).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcXmDTO::getDjxl))), ArrayList::new));
                Map<String, String> shxxBtnMap = new HashMap<>(2);
                boolean zhajlc = false;
                for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                    Map btnMap = shxxZhlcPrintDTO.getZhlcbtn().get(bdcXmDTO.getGzldyid());
                    if (MapUtils.isNotEmpty(btnMap)) {
                        Map<String, String> slymBtnMap = MapUtils.getMap(btnMap, bdcXmDTO.getDjxl(), null);
                        shxxBtnMap.put(bdcXmDTO.getDjxl(), MapUtils.getString(slymBtnMap, "spb", ""));
                    }
                    if (CollectionUtils.isNotEmpty(zhajlcList) && zhajlcList.contains(bdcXmDTO.getGzldyid())) {
                        zhajlc = true;
                    }
                }
                resultMap.put("zhajlc", zhajlc);
                resultMap.put("shxxBtn", shxxBtnMap);
            }
        }
        resultMap.put("shxxSplit", shxxSplit);
        return resultMap;
    }


    /**
     * ??????spb???pdf???????????????
     *
     * @param gzlslid
     * @param pdfUrl
     */
    @GetMapping(value = "/savePdfToWjzx")
    public String savePdfToWjzx(@RequestParam(name = "gzlslid") String gzlslid,
                                @RequestParam(name = "pdfUrl") String pdfUrl,
                                @RequestParam(name = "dyName") String dyName) {
        // ????????????
        /* ????????????
              ???????????????
                    pdf1
              ???????????????
                    pdf2
        */
        // ????????? ???????????? ?????????
        StorageDto storageDtoGml = null;
        // ?????????
        String fileName = dyName + ".pdf";
        // ????????????
        String folderName = "????????????";
        // ????????????????????????????????????????????????????????????????????????????????????
        // ?????????????????????
        List<StorageDto> storageDtoList = storageClient.listStoragesByName("clientId", gzlslid, null, folderName, null, 0);

        if(CollectionUtils.isNotEmpty(storageDtoList)) {
            LOGGER.info("????????????????????????????????????:{}",storageDtoList.size());
            List<String> listId = new ArrayList();
            // ???????????????????????????
            StorageDto storageDto = storageDtoList.get(0);
            if(null != storageDto){
                // ???????????? ???????????????????????????
                List<StorageDto> listEjWjj = storageClient.listAllSubsetStorages(storageDto.getId(),dyName,null,0,null,null);
                // ??????????????????????????????????????????????????????????????????????????????
                if(CollectionUtils.isNotEmpty(listEjWjj)){
                    StorageDto storageDtoWjj = listEjWjj.get(0);
                    // ?????? ????????????????????????pdf??????
                    List<StorageDto> listPdfFile = storageClient.listAllSubsetStorages(storageDtoWjj.getId(),null,null,null,null,null);
                    if(CollectionUtils.isNotEmpty(listPdfFile)){
                        for (StorageDto storageDtoFile : listPdfFile) {
                            if(null != storageDtoWjj){
                                listId.add(storageDtoFile.getId());
                            }
                        }
                    }
                    listId.add(storageDtoWjj.getId());
                }
            }
            storageDtoGml = storageDto;

            if (CollectionUtils.isNotEmpty(listId)) {
                boolean result = storageClient.deleteStorages(listId);
                LOGGER.info("???????????????????????????????????????{}", result);
            }

        }else{
            // ???????????????
            storageDtoGml = storageClient.createRootFolder("clientId", gzlslid, folderName, null);
        }

        if(storageDtoGml != null){
            // ??????????????? (????????????)
            StorageDto storageDto = storageClient.createFolder("clientId", "", storageDtoGml.getId(), dyName,"");

            if (storageDto != null) {
                //????????????
                byte[] fileByte = Base64Utils.getFile(pdfUrl);

                MultipartDto multipartDto = new MultipartDto();
                multipartDto.setNodeId(storageDto.getId());
                multipartDto.setClientId(storageDto.getClientId());
                if (fileByte != null) {
                    multipartDto.setData(fileByte);
                    multipartDto.setOwner(userManagerUtils.getCurrentUserName());
                    multipartDto.setContentType("application/pdf");
                    multipartDto.setSize(fileByte.length);
                    multipartDto.setOriginalFilename(fileName);
                    multipartDto.setName(storageDto.getName());
                }

                //???????????????????????????
                List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(gzlslid, folderName);
                if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
                    List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
                    if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                        BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
                        bdcSlSjclDO.setWjzxid(storageDto.getId());
                        bdcSlSjclDO.setDjxl(bdcXmDTOList.get(0).getDjxl());
                        bdcSlSjclDO.setClmc(folderName);
                        bdcSlSjclDO.setGzlslid(gzlslid);
                        bdcSlSjclDO.setFs(1);
                        bdcSlSjclDO.setYs(1);
                        bdcSlSjclDO.setSjlx(CommonConstantUtils.SJLX_QT);
                        bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
                    }
                }
                storageDto = storageClient.multipartUpload(multipartDto);
                if (null != storageDto) {
                    LOGGER.info("????????????pdf?????????????????????{}", JSONObject.toJSONString(storageDto));
                    return storageDto.getDownUrl();
                }
            }
        }

        return "";
    }

    /**
     * ??????spb???pdf
     * @param gzlslid
     * @param dyName
     */
    @GetMapping(value = "/ylPdf")
    public String ylPdf(@RequestParam(name = "gzlslid") String gzlslid,@RequestParam(name = "dyName") String dyName) {

        if(StringUtils.isBlank(gzlslid)){
            throw new AppException("??????????????????pdf??????????????????id");
        }
        if(StringUtils.isBlank(dyName)){
            throw new AppException("??????????????????pdf?????????????????????");
        }
        List<StorageDto> storageDtoList = storageClient.listStoragesByName("clientId", gzlslid, null, dyName, null, 0);

        if(CollectionUtils.isNotEmpty(storageDtoList)) {
            String id = storageDtoList.get(0).getId();
            List<StorageDto> listFile = storageClient.listAllSubsetStorages(id, StringUtils.EMPTY, 1, null, 0,null);
            if(CollectionUtils.isNotEmpty(listFile)){
                return listFile.get(0).getDownUrl();
            }
        }

        return "";
    }
}
