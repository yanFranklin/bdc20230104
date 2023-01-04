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
 * @description 一张图controller
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
      * @description  附件重复提示信息日志输出
      */
    private static final String FJCF_TSXX ="附件重复，重复附件名为：{}";

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 上传材料成功提示信息日志输出
      */
    private static final String SCFJ_TSXX ="上传供地审批信息附件成功，返回id：{}";

    /**
     * 供地审批信息分页查询
     * @param pageable
     * @param bdcGdspxxQO
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/gdspxx/list")
    public Object listGdspxxByPage(@LayuiPageable Pageable pageable, BdcGdspxxQO bdcGdspxxQO)  {
        if(null != bdcGdspxxQO){
            LOGGER.info("调用yzt_cx_gdspxx接口入参：{}",bdcGdspxxQO);
        }else{
            throw new AppException("缺失查询参数");
        }
        Object object = exchangeInterfaceFeignService.requestInterface("yzt_cx_gdspxx",bdcGdspxxQO);
        if(null != object){
            LOGGER.info("调用yzt_cx_gdspxx接口返回值：{}",JSON.toJSONString(object));
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
     * 当前流程附件
     * @param gzlslid
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/fj/current/list/{processInsId}/{autoTzFj}")
    public Object getCurrentFj(@PathVariable(value = "processInsId") String gzlslid,@PathVariable(value = "autoTzFj") String autoTzFj)  {
        if(StringUtils.isNotBlank(gzlslid)){
            if(StringUtils.isNotBlank(autoTzFj) && Boolean.parseBoolean(autoTzFj)){
                List<StorageDto> listDqlc = storageClient.queryMenus(CommonConstantUtils.WJZX_CLIENTID,gzlslid,null,null,0,null, null,null);
                // 有自动拖拽的参数时，按照配置的参数，要把一张图附件中的包含配置的节点名的附件自动挂接到右侧
                Object obj = getGdspxxFj(gzlslid,"","true");
                if(null != obj){
                    List<StorageDto> list = (List<StorageDto>)obj;
                    if(StringUtils.isNotBlank(zdgjmc)){
                        for(StorageDto storageDto : list){
                            if(zdgjmc.contains(storageDto.getName())){
                                listDqlc.addAll(storageDto.getChildren());
                            }else{
                                // 不包含的时候 需要看看当前附件是否是目录 目录下面还有子节点
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
            throw new AppException("缺失工作流实例id");
        }
    }

    /**
     * 保存附件
     * @param json
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/fj/save/{processInsId}")
    public Object saveFj(@RequestBody String json,@PathVariable(value = "processInsId") String gzlslid)  {
        if(StringUtils.isNotBlank(json) && StringUtils.isNotBlank(gzlslid)){
            //用于验证同一结构下附件是否重复
            Map wjjgDistinctMap = new HashMap();
            for (Object obj : JSON.parseArray(json)) {
                //文件结构名称,按照目录结构拼接,中间用英文逗号分隔
                String wjjgmc ="";
                StorageDto storageDto = JSONObject.parseObject(obj.toString(), StorageDto.class);
                if(StringUtils.isNotBlank(storageDto.getName())){
                    wjjgmc =storageDto.getName();
                }
                // 不是目录的直接保存在根目录下
                if(storageDto.getType() != CommonConstantUtils.FILETYPE_ML ){
                    // 去重
                    if(wjjgDistinctMap.containsKey(wjjgmc)){
                        LOGGER.info(FJCF_TSXX,storageDto.getName());
                        continue;
                    }
                    saveCurrentFj(new StorageDto(),storageDto,gzlslid,wjjgDistinctMap,wjjgmc);
                    continue;
                }
                // 没有子文件的不做处理
                if(CollectionUtils.isEmpty(storageDto.getChildren()) && storageDto.getType() == CommonConstantUtils.FILETYPE_ML){
                    continue;
                }
                // 先检查当前流程有无该目录，没有需要新增一个目录
                boolean hasMl = checkExsitsMl(storageDto.getName(),gzlslid);

                // 当存在目录时，则在这目录下保存附件
                if(hasMl){
                    // 需要再当前目录下保存子文件
                    List<StorageDto> existPdfFiles = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID, gzlslid, "", storageDto.getName(), null, null);
                    List<StorageDto> listStorageDtos = storageDto.getChildren();
                    for(StorageDto subStorageDto : listStorageDtos){
                        createFile(existPdfFiles.get(0),subStorageDto,gzlslid,wjjgDistinctMap,wjjgmc);
                    }
                }else{// 当不存在时 则先创建目录
                    if(wjjgDistinctMap.containsKey(wjjgmc)){
                        LOGGER.info(FJCF_TSXX,storageDto.getName());
                        continue;
                    }
                    StorageDto  storageDto1 = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, storageDto.getName(), null);

                    // 保存受理附件材料
                    BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();

                    //新建文件夹
                    if (storageDto != null) {
                        bdcSlSjclDO.setWjzxid(storageDto1.getId());
                        bdcSlSjclDO.setClmc(storageDto1.getName());
                        this.saveSlSjcl(bdcSlSjclDO,gzlslid);
                    }

                    LOGGER.info(SCFJ_TSXX,storageDto1.getId());
                    wjjgDistinctMap.put(wjjgmc,"");
                    // 需要再当前目录下保存子文件
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
        // 不是目录则直接保存
        if(storageDto.getType() != CommonConstantUtils.FILETYPE_ML){
            // 去重
            if(wjjgDistinctMap.containsKey(wjjgmc)){
                LOGGER.info(FJCF_TSXX,storageDto.getName());
                return;
            }
            saveCurrentFj(exsitStorageDto, storageDto,gzlslid,wjjgDistinctMap,wjjgmc);
        // 目录则还需要再次判断
        }else if(storageDto.getType() == CommonConstantUtils.FILETYPE_ML){
            // 是目录 但是目录下不为空
            if(CollectionUtils.isNotEmpty(storageDto.getChildren())){
                // 已存在的则不再保存
                if(wjjgDistinctMap.containsKey(wjjgmc)){
                    LOGGER.info(FJCF_TSXX,storageDto.getName());
                    return;
                }
                // 创建目录
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
     * 保存附件
     * @param parentName
     * @param storageDto
     * @param wjjgmc 当前文件已拼接结构
     */
    private void saveCurrentFj(StorageDto exsitStorageDto, StorageDto storageDto,String gzlslid,Map wjjgDistinctMap,String wjjgmc){
        //上传文件
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

        // 创建文件前 先删除同名的文件
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
     * 检查是否已存在文件夹
     * @param fileName
     * @param gzlslid
     * @return
     */
    public boolean checkExsitsMl(String fileName, String gzlslid){
        List<StorageDto> existPdfFiles = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID, gzlslid, "", fileName, null, null);
        return CollectionUtils.isNotEmpty(existPdfFiles);
    }


    /**
     * 供地审批信息附件
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

                    LOGGER.info("调用yzt_fj_by_specialparam接口入参：{}",map);
                    obj = exchangeInterfaceFeignService.requestInterface("yzt_fj_by_specialparam",map);
                    if(null != obj){
                        LOGGER.info("调用yzt_fj_by_specialparam接口返回值：{}",JSONObject.toJSONString(obj));
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
            LOGGER.info("调用yzt_fj接口入参：{}",map);
            obj = exchangeInterfaceFeignService.requestInterface("yzt_fj",map);
            if(null != obj){
                LOGGER.info("调用yzt_fj接口返回值：{}",JSONObject.toJSONString(obj));
                BdcGdspxxFjDTO bdcGdspxxFjDTO = JSONObject.parseObject(JSON.toJSONString(obj), BdcGdspxxFjDTO.class);
                if(StringUtils.equals("true",bdcGdspxxFjDTO.getResult())){
                    // 转换附件的数据结构 保持和大云的一致
                    list = changeFjData(bdcGdspxxFjDTO);
                }
            }
        }

        return list;
    }

    /**
     * 转换附件的数据结构 保持和大云的一致
     * @param listFileDTO
     * @return
     */
    private List<StorageDto> changeFjDataZdgj(ListFileDTO listFileDTO){
        List<StorageDto> list = new ArrayList<>();
        if(null != listFileDTO.getProcessList()) {
            List<ProcessListFileDTO> listProcessListFileDTO = listFileDTO.getProcessList();
            if(CollectionUtils.isNotEmpty(listProcessListFileDTO)){
                for(ProcessListFileDTO processListFileDTO : listProcessListFileDTO){
                    // 第一次为目录
                    StorageDto storageDto = new StorageDto();
                    storageDto.setType(CommonConstantUtils.FILETYPE_ML);
                    storageDto.setName(processListFileDTO.getTypeName());

                    storageDto.setOwner(OWNER_FLAG);

                    // 二层目录的子节点
                    List<StorageDto> list2 = new ArrayList<>();

                    List<PropertiesFileDTO> propertiesFileDTOS = processListFileDTO.getProperties();
                    if(CollectionUtils.isNotEmpty(propertiesFileDTOS)){

                        for(PropertiesFileDTO propertiesFileDTO : propertiesFileDTOS){
                            StorageDto storageDto2 = new StorageDto();
                            storageDto2.setType(CommonConstantUtils.FILETYPE_ML);
                            storageDto2.setName(propertiesFileDTO.getText());
                            storageDto2.setOwner(OWNER_FLAG);

                            // 第二层依旧为目录
                            if(CollectionUtils.isNotEmpty(propertiesFileDTO.getChildren())){
                                List<StorageDto> list3 = new ArrayList<>();

                                for(ChildrenFileDTO childrenFileDTO : propertiesFileDTO.getChildren()){
                                    StorageDto children = new StorageDto();
                                    // 当link有值 说明是附件 没有值的时候是目录继续递归嵌套处理
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
     * 递归嵌套处理附件
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
                // 当link有值 说明是附件 没有值的时候是目录继续递归嵌套处理
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
     * 转换附件的数据结构 保持和大云的一致
     * @param bdcGdspxxFjDTO
     * @return
     */
    public List<StorageDto> changeFjData(BdcGdspxxFjDTO bdcGdspxxFjDTO){
        List<StorageDto> list = new ArrayList<>();
        if(null != bdcGdspxxFjDTO){
            // 第一层为根目录 固定不变
            FolderDTO folderDTO = bdcGdspxxFjDTO.getFolderDTO();

            StorageDto storageDto = new StorageDto();
            storageDto.setName(folderDTO.getFolderName());
            storageDto.setType(CommonConstantUtils.FILETYPE_ML);
            // 大云结构的第二层目录 包含供地附件的根目录下的文件 以及ChildrenFolderDTOList的子文件
            List<StorageDto> listSecSubFilesSto = new ArrayList<>();
            // 第二层文件夹
            List<FolderDTO> listSubFiles = folderDTO.getChildrenFolderDTOList();
            // 第二层文件（非文件夹的）
            List<FileDTO> listfileDTOList = folderDTO.getFileDTOList();
            // 处理第二层文件
            if(CollectionUtils.isNotEmpty(listfileDTOList)){
                for(FileDTO fileDTO : listfileDTOList){
                    StorageDto storageDto1 = new StorageDto();
                    storageDto1.setDownUrl(fileDTO.getFileUrl());
                    storageDto1.setName(fileDTO.getFileName());
                    String fileName = fileDTO.getFileName();
                    if(StringUtils.isNotBlank(fileName)){
                        // 默认为图片
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

            // 第二层文件夹的子文件
            if(CollectionUtils.isNotEmpty(listSubFiles)){
                for(FolderDTO folderDTO1 : listSubFiles){
                    StorageDto storageDto1 = new StorageDto();
                    storageDto1.setName(folderDTO1.getFolderName());
                    storageDto1.setType(CommonConstantUtils.FILETYPE_ML);
                    // 从第二层开是 递归嵌套处理
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
     * @param folderDTO 供地审批信息的附件
     * @param storageDto 大云结构的附件
     */
    public void dgQtGdspxxFj(FolderDTO folderDTO,StorageDto storageDto){

        List<StorageDto> list = new ArrayList<>();
        // 子文件不为空
        if(CollectionUtils.isNotEmpty(folderDTO.getChildrenFolderDTOList())){
            for(FolderDTO folderDTO1 : folderDTO.getChildrenFolderDTOList()){
                StorageDto storageDto1 = new StorageDto();
                storageDto1.setName(folderDTO1.getFolderName());
                storageDto1.setType(CommonConstantUtils.FILETYPE_ML);
                // 递归
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
                    // 默认为图片
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
        LOGGER.info("保存受理附件材料成功，返回id：{}",bdcSlSjclDO.getSjclid());

    }

    /**
     * 递归挂接配置的文件
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
