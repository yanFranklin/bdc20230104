package cn.gtmap.realestate.accept.ui.web;


import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.yzt.cxgdspxx.response.YztCxGdspxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.yzt.cxgdspxx.response.YztCxGdspxxResponseDataDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.enums.FileContentTypeEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.accept.BdcGdspxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcYztFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 2020/12/18
 * @description ?????????controller
 */
@Controller
@RequestMapping("/yzt")
public class BdcYztController extends BaseController {

    @Autowired
    BdcYztFeignService bdcYztFeignService;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Value("${yzt.zdgj.jdmc:}")
    private String zdgjmc;
    
    public static final String OWNER_FLAG = "auto";

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  ????????????????????????????????????
      */
    private static final String FJCF_TSXX ="????????????????????????????????????{}";

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description ??????????????????????????????????????????
      */
    private static final String SCFJ_TSXX ="?????????????????????????????????????????????id???{}";

    /**
     * ??????????????????????????????
     * @param pageable
     * @param bdcGdspxxQO
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/gdspxx/list")
    public Object listGdspxxByPage(@LayuiPageable Pageable pageable, BdcGdspxxQO bdcGdspxxQO)  {
        if(null != bdcGdspxxQO){
            LOGGER.info("??????yzt_cx_gdspxx???????????????{}",bdcGdspxxQO);
        }else{
            throw new AppException("??????????????????");
        }
        Object object = exchangeInterfaceFeignService.requestInterface("yzt_cx_gdspxx",bdcGdspxxQO);
        if(null != object){
            LOGGER.info("??????yzt_cx_gdspxx??????????????????{}",JSON.toJSONString(object));
            YztCxGdspxxResponseDTO gdspxxDTO  = JSONObject.parseObject(JSON.toJSONString(object), YztCxGdspxxResponseDTO.class);
            if(StringUtils.equals("true",gdspxxDTO.getResult()) &&CollectionUtils.isNotEmpty(gdspxxDTO.getDataDTOS())){
                List<YztCxGdspxxResponseDataDTO> listBdcVYztGyGdxmDkDO = gdspxxDTO.getDataDTOS();
                return addLayUiCode(new PageImpl(listBdcVYztGyGdxmDkDO, pageable, listBdcVYztGyGdxmDkDO.size()));
            }else{
                return addLayUiCode(new PageImpl(new ArrayList(), pageable, 0));
            }
        }
        return addLayUiCode(new PageImpl(new ArrayList(), pageable, 0));
    }

    /**
     * ??????????????????
     * @param gzlslid
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/fj/current/list/{processInsId}/{autoTzFj}")
    public Object getCurrentFj(@PathVariable(value = "processInsId") String gzlslid,@PathVariable(value = "autoTzFj") String autoTzFj)  {
        if(StringUtils.isNotBlank(gzlslid)){
            if(StringUtils.isNotBlank(autoTzFj) && Boolean.parseBoolean(autoTzFj)){
                List<StorageDto> listDqlc = storageClient.queryMenus(CommonConstantUtils.WJZX_CLIENTID,gzlslid,null,null,0,null, null,null);
                // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                Object obj = getGdspxxFj(gzlslid,"","true");
                if(null != obj){
                    List<StorageDto> list = (List<StorageDto>)obj;
                    if(StringUtils.isNotBlank(zdgjmc)){
                        for(StorageDto storageDto : list){
                            if(zdgjmc.contains(storageDto.getName())){
                                listDqlc.addAll(storageDto.getChildren());
                            }else{
                                // ?????????????????? ??????????????????????????????????????? ???????????????????????????
                                List<StorageDto> children = storageDto.getChildren();
                                if(CollectionUtils.isNotEmpty(children)){
                                    getZdgjSto(listDqlc,children);
                                }
                            }
                        }
                    }
                }
                return listDqlc;
            }else{
                return storageClient.queryMenus(CommonConstantUtils.WJZX_CLIENTID,gzlslid,null,null,0,null, null,null);
            }
        }else{
            throw new AppException("?????????????????????id");
        }
    }

    /**
     * ????????????
     * @param json
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/fj/save/{processInsId}")
    public Object saveFj(@RequestBody String json,@PathVariable(value = "processInsId") String gzlslid)  {
        if(StringUtils.isNotBlank(json) && StringUtils.isNotBlank(gzlslid)){
            //?????????????????????????????????????????????
            Map wjjgDistinctMap = new HashMap();
            for (Object obj : JSON.parseArray(json)) {
                //??????????????????,????????????????????????,???????????????????????????
                String wjjgmc ="";
                StorageDto storageDto = JSONObject.parseObject(obj.toString(), StorageDto.class);
                if(StringUtils.isNotBlank(storageDto.getName())){
                    wjjgmc =storageDto.getName();
                }
                // ??????????????????????????????????????????
                if(storageDto.getType() != CommonConstantUtils.FILETYPE_ML ){
                    // ??????
                    if(wjjgDistinctMap.containsKey(wjjgmc)){
                        LOGGER.info(FJCF_TSXX,storageDto.getName());
                        continue;
                    }
                    saveCurrentFj(new StorageDto(),storageDto,gzlslid,wjjgDistinctMap,wjjgmc);
                    continue;
                }
                // ??????????????????????????????
                if(CollectionUtils.isEmpty(storageDto.getChildren()) && storageDto.getType() == CommonConstantUtils.FILETYPE_ML){
                    continue;
                }
                // ?????????????????????????????????????????????????????????????????????
                boolean hasMl = checkExsitsMl(storageDto.getName(),gzlslid);

                // ???????????????????????????????????????????????????
                if(hasMl){
                    // ???????????????????????????????????????
                    List<StorageDto> existPdfFiles = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID, gzlslid, "", storageDto.getName(), null, null);
                    List<StorageDto> listStorageDtos = storageDto.getChildren();
                    for(StorageDto subStorageDto : listStorageDtos){
                        createFile(existPdfFiles.get(0),subStorageDto,gzlslid,wjjgDistinctMap,wjjgmc);
                    }
                }else{// ??????????????? ??????????????????
                    if(wjjgDistinctMap.containsKey(wjjgmc)){
                        LOGGER.info(FJCF_TSXX,storageDto.getName());
                        continue;
                    }
                    StorageDto  storageDto1 = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, storageDto.getName(), null);

                    // ????????????????????????
                    BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();

                    //???????????????
                    if (storageDto != null) {
                        bdcSlSjclDO.setWjzxid(storageDto1.getId());
                        bdcSlSjclDO.setClmc(storageDto1.getName());
                        this.saveSlSjcl(bdcSlSjclDO,gzlslid);
                    }

                    LOGGER.info(SCFJ_TSXX,storageDto1.getId());
                    wjjgDistinctMap.put(wjjgmc,"");
                    // ???????????????????????????????????????
                    List<StorageDto> listStorageDtos = storageDto.getChildren();
                    for(StorageDto subStorageDto : listStorageDtos){
                        createFile(storageDto1,subStorageDto,gzlslid,wjjgDistinctMap,wjjgmc);
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @param
     * @param storageDto
     */
    public void createFile(StorageDto exsitStorageDto, StorageDto storageDto,String gzlslid,Map wjjgDistinctMap,String wjjgmc){
        wjjgmc+=CommonConstantUtils.ZF_YW_DH+storageDto.getName();
        // ???????????????????????????
        if(storageDto.getType() != CommonConstantUtils.FILETYPE_ML){
            // ??????
            if(wjjgDistinctMap.containsKey(wjjgmc)){
                LOGGER.info(FJCF_TSXX,storageDto.getName());
                return;
            }
            saveCurrentFj(exsitStorageDto, storageDto,gzlslid,wjjgDistinctMap,wjjgmc);
        // ??????????????????????????????
        }else if(storageDto.getType() == CommonConstantUtils.FILETYPE_ML){
            // ????????? ????????????????????????
            if(CollectionUtils.isNotEmpty(storageDto.getChildren())){
                // ???????????????????????????
                if(wjjgDistinctMap.containsKey(wjjgmc)){
                    LOGGER.info(FJCF_TSXX,storageDto.getName());
                    return;
                }
                // ????????????
                StorageDto  storageDto1 = storageClient.createFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, exsitStorageDto.getId(), storageDto.getName(),"");
                wjjgDistinctMap.put(storageDto.getName(),"");
                LOGGER.info(SCFJ_TSXX,storageDto1.getId());

                List<StorageDto> listStorageDtos = storageDto.getChildren();
                for(StorageDto subStorageDto : listStorageDtos){
                    createFile(storageDto1,subStorageDto,gzlslid,wjjgDistinctMap,wjjgmc);
                }
            }
        }
    }

    /**
     * ????????????
     * @param parentName
     * @param storageDto
     * @param wjjgmc ???????????????????????????
     */
    private void saveCurrentFj(StorageDto exsitStorageDto, StorageDto storageDto,String gzlslid,Map wjjgDistinctMap,String wjjgmc){
        //????????????
        byte[] fileByte = Base64Utils.getFile(storageDto.getDownUrl());

        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(exsitStorageDto.getId());
        multipartDto.setClientId(StringUtils.isBlank(exsitStorageDto.getClientId())?CommonConstantUtils.WJZX_CLIENTID:exsitStorageDto.getClientId());
        String contentType = "application/octet-stream";
        if(StringUtils.isNotBlank(storageDto.getName())){
            String fileType = storageDto.getName().split("\\.")[storageDto.getName().split("\\.").length -1];
            if(StringUtils.isNotBlank(fileType)){
                contentType =  FileContentTypeEnum.getMcByDm(fileType);
            }
        }
        if (fileByte != null) {
            multipartDto.setData(fileByte);
            multipartDto.setOwner(userManagerUtils.getCurrentUserName());
            multipartDto.setContentType(contentType);
            multipartDto.setSize(fileByte.length);
            multipartDto.setOriginalFilename(storageDto.getName());
            multipartDto.setName(storageDto.getName());
            multipartDto.setSpaceCode(gzlslid);
        }

        // ??????????????? ????????????????????????
        List<StorageDto> existPdfFiles = storageClient.listAllSubsetStorages(exsitStorageDto.getId(), storageDto.getName(),1,null,0,null);
        if(CollectionUtils.isNotEmpty(existPdfFiles)){
            List<String> ids = new ArrayList<>();
            for(StorageDto delStorageDto : existPdfFiles){
                ids.add(delStorageDto.getId());
            }
            storageClient.deleteStorages(ids);
        }

        storageDto = storageClient.multipartUpload(multipartDto);
        wjjgDistinctMap.put(wjjgmc,"");

        LOGGER.info(SCFJ_TSXX,storageDto.getId());

    }

    /**
     * ??????????????????????????????
     * @param fileName
     * @param gzlslid
     * @return
     */
    public boolean checkExsitsMl(String fileName, String gzlslid){
        List<StorageDto> existPdfFiles = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID, gzlslid, "", fileName, null, null);
        return CollectionUtils.isNotEmpty(existPdfFiles);
    }


    /**
     * ????????????????????????
     * @param proid
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/fj/getGdspxxFj/list/{processInsId}/{proid}/{autoTzFj}")
    public Object getGdspxxFj(@PathVariable(value = "processInsId") String processInsId,@PathVariable(value = "proid") String proid,@PathVariable(value = "autoTzFj") String autoTzFj)  {
        Map map = new HashMap();
        Object obj = null;
        List<StorageDto> list = new ArrayList<>();

        if(StringUtils.isNotBlank(autoTzFj) && Boolean.parseBoolean(autoTzFj)){
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
            if(CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                String bdcdyh = bdcXmDTOList.get(0).getBdcdyh();
                if(StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() > 19){
                    String djh = bdcdyh.substring(0,19);
                    map.put("codeKey","djh");
                    map.put("codeValue",djh);

                    LOGGER.info("??????yzt_fj_by_specialparam???????????????{}",map);
                    obj = exchangeInterfaceFeignService.requestInterface("yzt_fj_by_specialparam",map);
                    if(null != obj){
                        LOGGER.info("??????yzt_fj_by_specialparam??????????????????{}",JSONObject.toJSONString(obj));
                        JSONArray js = JSONObject.parseArray(JSONObject.toJSONString(obj));
                        if(CollectionUtils.isNotEmpty(js)){
                            String jsonString = JSONObject.toJSONString(js.get(0));
                            JSONObject jsonObject = JSON.parseObject(jsonString);
                            if(null != jsonObject){
                                ListFileDTO listFileDTO = JSONObject.parseObject(JSONObject.toJSONString(jsonObject),ListFileDTO.class);
                                if(null != listFileDTO && CollectionUtils.isNotEmpty(listFileDTO.getProcessList())){
                                    list = changeFjDataZdgj(listFileDTO);
                                }
                            }
                        }
                    }

                }
            }
        }else{
            map.put("proid",proid);
            LOGGER.info("??????yzt_fj???????????????{}",map);
            obj = exchangeInterfaceFeignService.requestInterface("yzt_fj",map);
            if(null != obj){
                LOGGER.info("??????yzt_fj??????????????????{}",JSONObject.toJSONString(obj));
                BdcGdspxxFjDTO bdcGdspxxFjDTO = JSONObject.parseObject(JSON.toJSONString(obj), BdcGdspxxFjDTO.class);
                if(StringUtils.equals("true",bdcGdspxxFjDTO.getResult())){
                    // ??????????????????????????? ????????????????????????
                    list = changeFjData(bdcGdspxxFjDTO);
                }
            }
        }

        return list;
    }

    /**
     * ??????????????????????????? ????????????????????????
     * @param listFileDTO
     * @return
     */
    private List<StorageDto> changeFjDataZdgj(ListFileDTO listFileDTO){
        List<StorageDto> list = new ArrayList<>();
        if(null != listFileDTO.getProcessList()) {
            List<ProcessListFileDTO> listProcessListFileDTO = listFileDTO.getProcessList();
            if(CollectionUtils.isNotEmpty(listProcessListFileDTO)){
                for(ProcessListFileDTO processListFileDTO : listProcessListFileDTO){
                    // ??????????????????
                    StorageDto storageDto = new StorageDto();
                    storageDto.setType(CommonConstantUtils.FILETYPE_ML);
                    storageDto.setName(processListFileDTO.getTypeName());

                    storageDto.setOwner(OWNER_FLAG);

                    // ????????????????????????
                    List<StorageDto> list2 = new ArrayList<>();

                    List<PropertiesFileDTO> propertiesFileDTOS = processListFileDTO.getProperties();
                    if(CollectionUtils.isNotEmpty(propertiesFileDTOS)){

                        for(PropertiesFileDTO propertiesFileDTO : propertiesFileDTOS){
                            StorageDto storageDto2 = new StorageDto();
                            storageDto2.setType(CommonConstantUtils.FILETYPE_ML);
                            storageDto2.setName(propertiesFileDTO.getText());
                            storageDto2.setOwner(OWNER_FLAG);

                            // ????????????????????????
                            if(CollectionUtils.isNotEmpty(propertiesFileDTO.getChildren())){
                                List<StorageDto> list3 = new ArrayList<>();

                                for(ChildrenFileDTO childrenFileDTO : propertiesFileDTO.getChildren()){
                                    StorageDto children = new StorageDto();
                                    // ???link?????? ??????????????? ???????????????????????????????????????????????????
                                    if(StringUtils.isNotBlank(childrenFileDTO.getLink()) && CollectionUtils.isEmpty(childrenFileDTO.getChildren())){
                                        children.setName(childrenFileDTO.getText());
                                        String fileType = childrenFileDTO.getText().split("\\.")[childrenFileDTO.getText().split("\\.").length -1];
                                        if(StringUtils.isNotBlank(fileType) && CommonConstantUtils.WJM_WJ.indexOf(fileType) > -1){
                                            children.setType(CommonConstantUtils.FILETYPE_WD);
                                        }else if(StringUtils.isNotBlank(fileType) && CommonConstantUtils.WJM_TP.indexOf(fileType) > -1){
                                            children.setType(CommonConstantUtils.FILETYPE_TP);
                                        }else{
                                            children.setType(CommonConstantUtils.FILETYPE_QT);
                                        }
                                        children.setDownUrl(childrenFileDTO.getLink());
                                        children.setOwner(OWNER_FLAG);
                                        list3.add(children);
                                    }else{
                                        dgQtGdspxxFjZdgj(list3,childrenFileDTO);
                                    }
                                }
                                storageDto2.setChildren(list3);
                            }
                            list2.add(storageDto2);
                        }
                    }
                    storageDto.setChildren(list2);
                    list.add(storageDto);
                }
            }
        }
        return list;
    }

    /**
     * ????????????????????????
     * @param listStorageDto
     * @param childrenFileDTO
     */
    private void dgQtGdspxxFjZdgj(List<StorageDto> listStorageDto, ChildrenFileDTO childrenFileDTO){
        List<ChildrenFileDTO> listClidren = childrenFileDTO.getChildren();
        if(CollectionUtils.isNotEmpty(listClidren)){
            StorageDto children2 = new StorageDto();
            children2.setType(CommonConstantUtils.FILETYPE_ML);
            children2.setName(childrenFileDTO.getText());
            children2.setOwner(OWNER_FLAG);
            List<StorageDto> listClidChildren = new ArrayList<>();
            for(ChildrenFileDTO childrenFileDTO1 : listClidren){
                StorageDto children = new StorageDto();
                // ???link?????? ??????????????? ???????????????????????????????????????????????????
                if(StringUtils.isNotBlank(childrenFileDTO1.getLink()) && CollectionUtils.isEmpty(childrenFileDTO1.getChildren())){
                    children.setName(childrenFileDTO1.getText());
                    String fileType = childrenFileDTO1.getText().split("\\.")[childrenFileDTO1.getText().split("\\.").length -1];
                    if(StringUtils.isNotBlank(fileType) && CommonConstantUtils.WJM_WJ.indexOf(fileType) > -1){
                        children.setType(CommonConstantUtils.FILETYPE_WD);
                    }else if(StringUtils.isNotBlank(fileType) && CommonConstantUtils.WJM_TP.indexOf(fileType) > -1){
                        children.setType(CommonConstantUtils.FILETYPE_TP);
                    }else{
                        children.setType(CommonConstantUtils.FILETYPE_QT);
                    }
                    children.setDownUrl(childrenFileDTO1.getLink());
                    children.setOwner(OWNER_FLAG);

                    listClidChildren.add(children);
                }else{
                    dgQtGdspxxFjZdgj(listClidChildren,childrenFileDTO1);
                }
                children2.setChildren(listClidChildren);
            }
            listStorageDto.add(children2);
        }
    }


    /**
     * ??????????????????????????? ????????????????????????
     * @param bdcGdspxxFjDTO
     * @return
     */
    public List<StorageDto> changeFjData(BdcGdspxxFjDTO bdcGdspxxFjDTO){
        List<StorageDto> list = new ArrayList<>();
        if(null != bdcGdspxxFjDTO){
            // ????????????????????? ????????????
            FolderDTO folderDTO = bdcGdspxxFjDTO.getFolderDTO();

            StorageDto storageDto = new StorageDto();
            storageDto.setName(folderDTO.getFolderName());
            storageDto.setType(CommonConstantUtils.FILETYPE_ML);
            // ?????????????????????????????? ?????????????????????????????????????????? ??????ChildrenFolderDTOList????????????
            List<StorageDto> listSecSubFilesSto = new ArrayList<>();
            // ??????????????????
            List<FolderDTO> listSubFiles = folderDTO.getChildrenFolderDTOList();
            // ????????????????????????????????????
            List<FileDTO> listfileDTOList = folderDTO.getFileDTOList();
            // ?????????????????????
            if(CollectionUtils.isNotEmpty(listfileDTOList)){
                for(FileDTO fileDTO : listfileDTOList){
                    StorageDto storageDto1 = new StorageDto();
                    storageDto1.setDownUrl(fileDTO.getFileUrl());
                    storageDto1.setName(fileDTO.getFileName());
                    String fileName = fileDTO.getFileName();
                    if(StringUtils.isNotBlank(fileName)){
                        // ???????????????
                        storageDto1.setType(CommonConstantUtils.FILETYPE_TP);
                        if(fileName.indexOf(".") > -1){
                            String fileType = fileName.split("\\.")[fileName.split("\\.").length -1];
                            if(StringUtils.isNotBlank(fileType) && CommonConstantUtils.WJM_WJ.indexOf(fileType) > -1){
                                storageDto1.setType(CommonConstantUtils.FILETYPE_WD);
                            }else if(StringUtils.isNotBlank(fileType) && CommonConstantUtils.WJM_TP.indexOf(fileType) > -1){
                                storageDto1.setType(CommonConstantUtils.FILETYPE_TP);
                            }else{
                                storageDto1.setType(CommonConstantUtils.FILETYPE_QT);
                            }
                        }
                    }
                    listSecSubFilesSto.add(storageDto1);
                }
            }

            // ??????????????????????????????
            if(CollectionUtils.isNotEmpty(listSubFiles)){
                for(FolderDTO folderDTO1 : listSubFiles){
                    StorageDto storageDto1 = new StorageDto();
                    storageDto1.setName(folderDTO1.getFolderName());
                    storageDto1.setType(CommonConstantUtils.FILETYPE_ML);
                    // ?????????????????? ??????????????????
                    dgQtGdspxxFj(folderDTO1,storageDto1);
                    listSecSubFilesSto.add(storageDto1);
                }
            }
            storageDto.setChildren(listSecSubFilesSto);
            list.add(storageDto);
        }
        return list;
    }

    /**
     *
     * @param folderDTO ???????????????????????????
     * @param storageDto ?????????????????????
     */
    public void dgQtGdspxxFj(FolderDTO folderDTO,StorageDto storageDto){

        List<StorageDto> list = new ArrayList<>();
        // ??????????????????
        if(CollectionUtils.isNotEmpty(folderDTO.getChildrenFolderDTOList())){
            for(FolderDTO folderDTO1 : folderDTO.getChildrenFolderDTOList()){
                StorageDto storageDto1 = new StorageDto();
                storageDto1.setName(folderDTO1.getFolderName());
                storageDto1.setType(CommonConstantUtils.FILETYPE_ML);
                // ??????
                dgQtGdspxxFj(folderDTO1,storageDto1);
                list.add(storageDto1);
            }
        }
        if(CollectionUtils.isNotEmpty(folderDTO.getFileDTOList())){
            for(FileDTO fileDTO : folderDTO.getFileDTOList()){
                StorageDto storageDto1 = new StorageDto();
                storageDto1.setDownUrl(fileDTO.getFileUrl());
                storageDto1.setName(fileDTO.getFileName());
                String fileName = fileDTO.getFileName();
                if(StringUtils.isNotBlank(fileName)){
                    // ???????????????
                    storageDto1.setType(CommonConstantUtils.FILETYPE_TP);
                    if(fileName.indexOf(".") > -1){
                        String fileType = fileName.split("\\.")[fileName.split("\\.").length -1];
                        if(StringUtils.isNotBlank(fileType) && CommonConstantUtils.WJM_WJ.indexOf(fileType) > -1){
                            storageDto1.setType(CommonConstantUtils.FILETYPE_WD);
                        }else if(StringUtils.isNotBlank(fileType) && CommonConstantUtils.WJM_TP.indexOf(fileType) > -1){
                            storageDto1.setType(CommonConstantUtils.FILETYPE_TP);
                        }else{
                            storageDto1.setType(CommonConstantUtils.FILETYPE_QT);
                        }
                    }
                }
                list.add(storageDto1);
            }
        }
        storageDto.setChildren(list);
    }

    /**
     *
     * @param bdcSlSjclDO
     */
    private void saveSlSjcl(BdcSlSjclDO bdcSlSjclDO,String gzlslid){
        List<BdcSlSjclDO> listSjcl = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(gzlslid);
        bdcSlSjclDO.setSjclid(UUIDGenerator.generate16());
        bdcSlSjclDO.setGzlslid(gzlslid);
        bdcSlSjclDO.setYs(1);
        bdcSlSjclDO.setFs(1);
        bdcSlSjclDO.setSjlx(1);
        bdcSlSjclDO.setXh(CollectionUtils.isEmpty(listSjcl)?0:listSjcl.size());
        bdcSlSjclDO = bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
        LOGGER.info("???????????????????????????????????????id???{}",bdcSlSjclDO.getSjclid());

    }

    /**
     * ???????????????????????????
     * @param listDqlc
     * @param children
     */
    private void getZdgjSto(List<StorageDto> listDqlc,List<StorageDto> children){
        for(StorageDto storageDto : children){
            if(zdgjmc.contains(storageDto.getName())){
                listDqlc.add(storageDto);
            }else{
                if(CollectionUtils.isNotEmpty(storageDto.getChildren())){
                    getZdgjSto(listDqlc,storageDto.getChildren());
                }
            }
        }
    }


}
