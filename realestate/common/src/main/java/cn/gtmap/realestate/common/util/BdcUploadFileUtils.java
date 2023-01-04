package cn.gtmap.realestate.common.util;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.enums.FileContentTypeEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSjclQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/5/18
 * @description 不动产文件上传操作类
 */
@Component
public class BdcUploadFileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcUploadFileUtils.class);

    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  是否组合收件
      */
    @Value("${zhlc.splitsjcl:false}")
    private boolean splitSjcl;

    /**
     * 1、上传base64附件至大云文件中心
     * @param {base64Str}  base64字符串
     * @param {gzlslid}  工作流实例ID
     * @param {foldname}  文件夹目录
     * @param {fjmc}    附件名称
     * @param {fileSuffix}   附件后缀 （.docx, .pdf, .png）
     * @return {String}  文件中心附件id
     * @throws IOException
     */
    public String uploadBase64File(String base64Str, String gzlslid, String foldname, String fjmc, String fileSuffix) throws IOException {
        MultipartFile file = Base64Utils.base64ToMultipart(base64Str);
        if(null == file) {
            throw new AppException("保存PDF文件操作失败,原因: PDF文件转换Base64失败!");
        }
        BdcPdfDTO bdcPdfDTO = new BdcPdfDTO();
        bdcPdfDTO.setFoldName(foldname);
        bdcPdfDTO.setGzlslid(gzlslid);
        bdcPdfDTO.setFileSuffix(fileSuffix);
        if(StringUtils.isBlank(fjmc)){
            bdcPdfDTO.setPdffjmc(file.getName());
        }else{
            bdcPdfDTO.setPdffjmc(fjmc);
        }
        StorageDto storageDto = this.upload(bdcPdfDTO, file);
        return storageDto.getId();
    }

    /**
     * 将Base64字符串内容生成文件对象，并上传至文件中心
     * <p><code>BdcPdfDTO</code>未设置文件名后缀时，默认设置为<code>PNG</code>文件</p>
     * @param {bdcPdfDTO} 文件上传的实体
     * @return {String} 文件中心附件id
     */
    public String uploadBase64File(BdcPdfDTO bdcPdfDTO) throws IOException {
        if(StringUtils.isBlank(bdcPdfDTO.getBase64str())){
            throw new AppException("未获取到文件Base64字符串");
        }
        MultipartFile file = Base64Utils.base64ToMultipart(bdcPdfDTO.getBase64str());
        if(null == file) {
            throw new AppException("保存PDF文件操作失败,原因: 文件转换Base64失败!");
        }
        if(StringUtils.isBlank(bdcPdfDTO.getFileSuffix())){
            bdcPdfDTO.setFileSuffix(CommonConstantUtils.WJHZ_PNG);
        }
        if(StringUtils.isBlank(bdcPdfDTO.getPdffjmc())){
            bdcPdfDTO.setPdffjmc(file.getName());
        }
        StorageDto storageDto = this.upload(bdcPdfDTO, file);
        return storageDto.getId();
    }

    /**
     * 上传PDF文件至文件中心
     * <p>获取参数中<code>pdfFilePath</code>的文件，将文件上传至大云文件中心</p>
     * @param {bdcPdfDTO} 文件上传的实体
     * @return {String} 文件中心附件id
     * @throws IOException
     */
    public String uploadPdfByFilePath(BdcPdfDTO bdcPdfDTO) throws IOException {
        File pdfFile = new File(bdcPdfDTO.getPdfFilePath());
        if(!pdfFile.exists()){
            throw new AppException("上传pdf文件至大云失败，原因：文件不存在，文件地址:"+ bdcPdfDTO.getPdfFilePath() );
        }

        String pdfBase64Str = "data:application/pdf;base64," + Base64Utils.encodeByteToBase64Str(FileUtils.readFileToByteArray(pdfFile));
        MultipartFile file = Base64Utils.base64ToMultipart(pdfBase64Str);
        if(null == file) {
            throw new AppException("保存PDF文件操作失败,原因: PDF文件转换Base64失败!");
        }

        StorageDto storageDto = this.upload(bdcPdfDTO, file);
        return storageDto.getId();
    }

    /**
     * 上传PDF文件至文件中心
     * <p>获取参数中<code>pdfUrl</code>的文件，将文件上传至大云文件中心</p>
     * @param {bdcPdfDTO} 文件上传的实体
     * @return {String} 文件中心附件id
     * @throws IOException
     */
    public String uploadPdfByUrl(BdcPdfDTO bdcPdfDTO) throws IOException{
        if(StringUtils.isBlank(bdcPdfDTO.getPdfUrl())){
            throw new AppException("未获取到文件下载URL");
        }
        URL url = new URL(bdcPdfDTO.getPdfUrl());
        MultipartFile file = null;
        if(StringUtils.isBlank(bdcPdfDTO.getFileSuffix())){
            bdcPdfDTO.setFileSuffix(CommonConstantUtils.WJHZ_PNG);
        }
        try {
            if (StringUtils.equals(CommonConstantUtils.WJHZ_PNG, bdcPdfDTO.getFileSuffix())) {
                String base64 = Base64Utils.encodeDzzzImageToBase64(url);
                if (StringUtils.isNotBlank(base64)) {
                    base64 = "data:image/png;base64," + base64;
                    file = Base64Utils.base64ToMultipart(base64);
                }
            } else {
                file = Base64Utils.createFileItem(url, bdcPdfDTO.getFileSuffix().replace(".", ""));
            }
        }catch (Exception e){
            throw new AppException("上传文件至大云失败，原因：文件转换失败，文件地址:"+ bdcPdfDTO.getPdfUrl());
        }
        if(null == file) {
            throw new AppException("上传文件至大云失败，原因：文件不存在，文件地址:"+ bdcPdfDTO.getPdfUrl());
        }
        if(StringUtils.isBlank(bdcPdfDTO.getPdffjmc())){
            bdcPdfDTO.setPdffjmc(file.getName());
        }
        StorageDto storageDto = this.upload(bdcPdfDTO, file);
        return storageDto.getId();
    }
    /**
     * 文件上传大云操作
     * @param {file}  spring 构建的用于上传的文件实体类
     * @param {bdcPdfDTO} 文件上传的实体
     * @return {StorageDto} 附件DTO对象
     * @throws IOException
     */
    public StorageDto upload(BdcPdfDTO bdcPdfDTO, MultipartFile file) throws IOException {
        String djxl ="";
        if(splitSjcl){
            int xmlx =bdcXmFeignService.makeSureBdcXmLx(bdcPdfDTO.getGzlslid());
            if(CommonConstantUtils.LCLX_PLZH.equals(xmlx) ||CommonConstantUtils.LCLX_ZH.equals(xmlx)){
                bdcPdfDTO.setZhsjcl(true);
                if(StringUtils.isBlank(bdcPdfDTO.getXmid())){
                    LOGGER.info("工作流实例ID:{}组合收件未传xmid",bdcPdfDTO.getGzlslid());
                    List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcPdfDTO.getGzlslid());
                    if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
                        bdcPdfDTO.setXmid(bdcXmDTOList.get(0).getXmid());
                        djxl =bdcXmDTOList.get(0).getDjxl();
                        bdcXmDTOList = bdcXmDTOList.stream().filter(t-> ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, t.getQllx())).collect(Collectors.toList());
                        if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
                            bdcPdfDTO.setXmid(bdcXmDTOList.get(0).getXmid());
                            bdcPdfDTO.setDjxl(djxl);
                            djxl =bdcXmDTOList.get(0).getDjxl();
                        }
                    }
                }else{
                    BdcXmQO bdcXmQO = new BdcXmQO();
                    bdcXmQO.setXmid(bdcPdfDTO.getXmid());
                    List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isNotEmpty(bdcXmDOList) &&StringUtils.isNotBlank(bdcXmDOList.get(0).getDjxl())) {
                        djxl =bdcXmDOList.get(0).getDjxl();
                    }
                }
            }

        }
        // 1、删除已上传的文件
        this.deleteExistPdfFile(bdcPdfDTO);

        // 2、判断是否存在文件夹，没有即创建文件夹目录
        BdcSlSjclQO bdcSlSjclQO = new BdcSlSjclQO();
        bdcSlSjclQO.setGzlslid(bdcPdfDTO.getGzlslid());
        bdcSlSjclQO.setClmc(bdcPdfDTO.getFoldName());
        bdcSlSjclQO.setDjxl(djxl);
        List<BdcSlSjclDO> sjclList = bdcSlSjclFeignService.listBdcSlSjcl(bdcSlSjclQO);

        String nodeId = "";
        // 没有文件夹目录，创建目录
        if(CollectionUtils.isEmpty(sjclList)){
            UserDto userDto = userManagerUtils.getCurrentUser();
            if(Boolean.TRUE.equals(bdcPdfDTO.getZhsjcl()) &&StringUtils.isNotBlank(djxl)){
                List<Map> djxlZdList = bdcZdFeignService.queryBdcZd("djxl");
                String djxlmc = StringToolUtils.convertBeanPropertyValueOfZdByString(djxl, djxlZdList);
                //组合收件
                StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, bdcPdfDTO.getGzlslid(), djxlmc, userDto != null ? userDto.getId() : "");
                StorageDto childFolder = storageClient.createFolder(CommonConstantUtils.WJZX_CLIENTID, bdcPdfDTO.getGzlslid(), storageDto.getId(), bdcPdfDTO.getFoldName(), userDto != null ? userDto.getId() : "");
                nodeId =childFolder.getId();
            }else {
                StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, bdcPdfDTO.getGzlslid(), bdcPdfDTO.getFoldName(), userDto != null ? userDto.getId() : "");
                nodeId = storageDto.getId();
            }
            // 添加收件材料信息
            if(StringUtils.isNotBlank(nodeId)){
                this.addSjclxx(bdcPdfDTO, nodeId);
            }
        }else {
            nodeId = sjclList.get(0).getWjzxid();
        }

        // 3、构造上传的文件信息，进行文件上传
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setClientId(CommonConstantUtils.WJZX_CLIENTID);
        multipartDto.setName(bdcPdfDTO.getPdffjmc());
        multipartDto.setNodeId(nodeId);
        multipartDto.setData(file.getBytes());
        multipartDto.setContentType(file.getContentType());
        multipartDto.setSize(file.getSize());
        multipartDto.setOriginalFilename(bdcPdfDTO.getPdffjmc() + bdcPdfDTO.getFileSuffix());
        StorageDto dto = storageClient.multipartUpload(multipartDto);
        LOGGER.info("{}附件信息{}，下载地址{}", bdcPdfDTO.getPdffjmc(), JSON.toJSONString(dto), dto.getDownUrl());
        return dto;
    }

    /**
     * 添加收件材料信息
     * @param bdcPdfDTO  不动产PDF传输类
     * @param wjzxId  文件中心ID
     */
    public void addSjclxx(BdcPdfDTO bdcPdfDTO, String wjzxId){
        List<BdcSlSjclDO> sjclList = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(bdcPdfDTO.getGzlslid());
        int xh = 0;
        if(CollectionUtils.isNotEmpty(sjclList)){
            xh = sjclList.size();
        }
        BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
        bdcSlSjclDO.setGzlslid(bdcPdfDTO.getGzlslid());
        bdcSlSjclDO.setClmc(bdcPdfDTO.getFoldName());
        bdcSlSjclDO.setFs(1);
        bdcSlSjclDO.setYs(1);
        bdcSlSjclDO.setMrfs(1);
        bdcSlSjclDO.setSjlx(CommonConstantUtils.SJLX_QT);
        bdcSlSjclDO.setXh(++xh);
        bdcSlSjclDO.setWjzxid(wjzxId);
        if(StringUtils.isNotBlank(bdcPdfDTO.getDjxl())){
            bdcSlSjclDO.setDjxl(bdcPdfDTO.getDjxl());
        }else {
            List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcPdfDTO.getGzlslid());
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                bdcSlSjclDO.setDjxl(bdcXmDTOList.get(0).getDjxl());
            }
        }
        this.bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
    }

    /**
     * 删除现有的PDF文件
     */
    private void deleteExistPdfFile(BdcPdfDTO bdcPdfDTO) {
        // 查
        List<StorageDto> existPdfFiles = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID, bdcPdfDTO.getGzlslid(), "", bdcPdfDTO.getPdffjmc()+bdcPdfDTO.getFileSuffix(), null, null);
        // 删
        if (CollectionUtils.isNotEmpty(existPdfFiles)) {
            List<String> fids = existPdfFiles.stream().map(StorageDto::getId).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(fids)) {
                storageClient.deleteStorages(fids);
                LOGGER.info("删除现有PDF文件，避免重复，工作流实例ID：{}，文件ID ：{}", bdcPdfDTO.getGzlslid(), fids);
            }
        }
    }


    /**
     * 循环创建大云文件
     *
     * @param currentRootStorage   当前根目录
     * @param storageDtoList       文件列表
     * @param spaceId              空间
     * @param coverExistFile       是否覆盖已存在的文件
     * @param createSjcl           创建收件材料
     * @param notCreateEmptyFolder 不创建空文件夹
     * @param userCurrentUserOwner 是否使用当前用户作为所有人
     */
    public void createFiles(StorageDto currentRootStorage,
                            List<StorageDto> storageDtoList,
                            String spaceId,
                            Boolean coverExistFile,
                            Boolean createSjcl,
                            Boolean notCreateEmptyFolder,
                            Boolean userCurrentUserOwner
    ) {
        if (CollectionUtils.isEmpty(storageDtoList)) {
            return;
        }
        for (StorageDto storageDto : storageDtoList) {
            //判断是文件夹还是文件
            if (CommonConstantUtils.FILETYPE_ML.equals(storageDto.getType())) {
                // 如果文件夹下没有文件，且不创建空文件夹则跳过
                if (CollectionUtils.isEmpty(storageDto.getChildren()) && notCreateEmptyFolder) {
                    continue;
                }
                StorageDto currentFolder = storageDto;
                boolean mlExist = checkFileExsits(storageDto.getName(), spaceId, currentRootStorage.getId(), CommonConstantUtils.FILETYPE_ML);
                if (!mlExist) {
                    //创建目录
                    currentFolder = createFolder(storageDto.getName(), spaceId, currentRootStorage.getId(), userCurrentUserOwner);
                    //新建文件夹
                    if (Objects.nonNull(currentFolder) && createSjcl) {
                        // 保存受理附件材料
                        BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
                        bdcSlSjclDO.setWjzxid(currentFolder.getId());
                        bdcSlSjclDO.setClmc(currentFolder.getName());
                        this.saveSlSjcl(bdcSlSjclDO, spaceId);
                    }
                }
                //循环子文件
                createFiles(currentFolder,
                        storageDto.getChildren(),
                        spaceId,
                        coverExistFile,
                        createSjcl,
                        notCreateEmptyFolder,
                        userCurrentUserOwner
                );
            } else {
                //检测文件是否存在
                boolean fileExsits = checkFileExsits(storageDto.getName(), spaceId, currentRootStorage.getId(), null);
                //如果不覆盖已存在的文件
                if (!coverExistFile && fileExsits) {
                    continue;
                }
                createFile(currentRootStorage, storageDto, spaceId, userCurrentUserOwner);
            }
        }
    }

    /**
     * 检查是否已存
     *
     * @param fileName
     * @param spaceId
     * @param nodeId
     * @param type
     * @return
     */
    public boolean checkFileExsits(String fileName, String spaceId, String nodeId, Integer type) {
        return storageClient.checkExist(CommonConstantUtils.WJZX_CLIENTID, spaceId, nodeId, fileName, null, type);
    }


    /**
     * 创建文件夹
     *
     * @param fileName
     * @param spaceId
     * @param nodeId
     * @param userCurrentUserOwner
     * @return
     */
    public StorageDto createFolder(String fileName, String spaceId, String nodeId, Boolean userCurrentUserOwner) {
        String owner = null;
        if (userCurrentUserOwner) {
            UserDto currentUser = userManagerUtils.getCurrentUser();
            if (Objects.nonNull(currentUser)) {
                owner = currentUser.getUsername();
            }
        }
        return storageClient.createFolder(CommonConstantUtils.WJZX_CLIENTID,
                spaceId,
                nodeId,
                fileName, owner);
    }

    /**
     * 创建文件
     *
     * @param
     * @param storageDto
     */
    public StorageDto createFile(StorageDto exsitStorageDto, StorageDto storageDto, String spaceId, Boolean userCurrentUserOwner) {
        String owner = null;
        if (userCurrentUserOwner) {
            UserDto currentUser = userManagerUtils.getCurrentUser();
            if (Objects.nonNull(currentUser)) {
                owner = currentUser.getUsername();
            }
        }
        //上传文件
        byte[] fileByte = Base64Utils.getFile(storageDto.getDownUrl());
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(exsitStorageDto.getId());
        multipartDto.setClientId(CommonConstantUtils.WJZX_CLIENTID);
        String contentType = "application/octet-stream";
        if (StringUtils.isNotBlank(storageDto.getName())) {
            String fileType = storageDto.getName().split("\\.")[storageDto.getName().split("\\.").length - 1];
            if (StringUtils.isNotBlank(fileType)) {
                contentType = FileContentTypeEnum.getMcByDm(fileType);
            }
        }
        if (fileByte != null) {
            multipartDto.setData(fileByte);
            multipartDto.setOwner(owner);
            multipartDto.setContentType(contentType);
            multipartDto.setSize(fileByte.length);
            multipartDto.setOriginalFilename(storageDto.getName());
            multipartDto.setName(storageDto.getName());
            multipartDto.setSpaceCode(spaceId);
        }

        // 创建文件前 尝试删除同名的文件
        List<StorageDto> existFiles = storageClient.listAllSubsetStorages(
                exsitStorageDto.getId(),
                storageDto.getName(),
                1, null, 0, null);
        if (CollectionUtils.isNotEmpty(existFiles)) {
            List<String> ids = new ArrayList<>();
            for (StorageDto delStorageDto : existFiles) {
                ids.add(delStorageDto.getId());
            }
            storageClient.deleteStorages(ids);
        }

        return storageClient.multipartUpload(multipartDto);
    }

    /**
     * @param bdcSlSjclDO
     */
    private void saveSlSjcl(BdcSlSjclDO bdcSlSjclDO, String gzlslid) {
        List<BdcSlSjclDO> listSjcl = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(gzlslid);
        bdcSlSjclDO.setSjclid(UUIDGenerator.generate16());
        bdcSlSjclDO.setGzlslid(gzlslid);
        bdcSlSjclDO.setYs(1);
        bdcSlSjclDO.setFs(1);
        bdcSlSjclDO.setSjlx(1);
        bdcSlSjclDO.setXh(CollectionUtils.isEmpty(listSjcl) ? 0 : listSjcl.size());
        bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
    }
}
