package cn.gtmap.realestate.certificate.core.service.impl.file;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileCenterService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzStorageService;
import cn.gtmap.realestate.certificate.util.PublicUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BdcDzzzStorageServiceImpl implements BdcDzzzStorageService,BdcDzzzFileService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BdcDzzzFileCenterService bdcDzzzFileCenterService;
    @Autowired
    private StorageClientMatcher storageClient;
    @Value("${spring.cloud.client.ipAddress}")
    protected String ipAddress;
    @Value("${server.port}")
    protected String serverPort;

    @Override
    public String uploadFilePath(String path, String parentId, String name) {
        String storageDtoId = null;
        byte[] data = PublicUtil.fileToByte(path + name);
        if (null != data && StringUtils.isNotBlank(parentId)&& StringUtils.isNotBlank(name)) {
            storageDtoId = uploadFileInput(data, parentId, name);
        }
        return storageDtoId;
    }

    public String uploadFilePathReplace(String path, String parentId, String name, String id) {
        String storageDtoId = null;
        byte[] data = PublicUtil.fileToByte(path + name);
        if (null != data && StringUtils.isNotBlank(parentId) && StringUtils.isNotBlank(name) && StringUtils.isNotBlank(id)) {
            storageDtoId = uploadFileInputReplace(data, parentId, name, id);
        }
        return storageDtoId;
    }

    @Override
    public String getProjectFileId(String projectId) {
        String storageDtoId = null;
        if (StringUtils.isNotBlank(projectId)) {
            StorageDto storageDto = storageClient.createRootFolder("dzzz", "dzzz", projectId, null);
            if (null != storageDto) {
                storageDtoId = storageDto.getId();
            }
        }

        return storageDtoId;
    }

    @Override
    public String createFileFolderByclmc(String parentId, String folderNodeName)  {
        String storageDtoId = null;
        if (StringUtils.isNotBlank(parentId) && StringUtils.isNotBlank(folderNodeName)) {
            StorageDto storageDto = storageClient.createFolder("dzzz", "dzzz", parentId, folderNodeName,null);
            if (null != storageDto) {
                storageDtoId = storageDto.getId();
            }
        }

        return storageDtoId;
    }

    @Override
    public String getNodeId(String parentId, String nodeName)  {
        String storageDtoId = null;
        if (StringUtils.isNotBlank(parentId) && StringUtils.isNotBlank(nodeName)) {
            List<StorageDto> storageDtoList = storageClient.listAllSubsetStorages(parentId, nodeName, 1, null, null,null);
            if (CollectionUtils.isNotEmpty(storageDtoList)) {
                storageDtoId = storageDtoList.get(0).getId();
            }
        }

        return storageDtoId;
    }

    public String getFileParentId(String node_Id, String directoryName){
        String nodeParentId = null;
        if (StringUtils.isNotBlank(node_Id) && StringUtils.isNotBlank(directoryName)) {
            String projectFileId = getProjectFileId(node_Id);
            if (StringUtils.isNotBlank(projectFileId)) {
                nodeParentId = getNodeId(projectFileId, directoryName);
                if (StringUtils.isBlank(nodeParentId)) {
                    nodeParentId = createFileFolderByclmc(projectFileId, directoryName);
                }
            }
        }
        return nodeParentId;
    }

    @Override
    public String sendFileCenter(byte[] out, String nodeid, String nodeName, String directoryName) {
        String fileCenterPath = null;
        String nodeParentId = getFileParentId(nodeid, directoryName);
        if (StringUtils.isNotBlank(nodeParentId)) {
            String nodeId = getNodeId(nodeParentId, nodeName);
            if (StringUtils.isBlank(nodeId)) {
                nodeId = uploadFileInput(out, nodeParentId, nodeName);
            } else {
                uploadFileInputReplace(out, nodeParentId, nodeName, nodeId);
            }

            if (StringUtils.isNotBlank(nodeId)) {
                fileCenterPath = "http://" + ipAddress + ":" + serverPort + "/realestate-e-certificate/rest/v1.0/zzgx/zzxzfile?fid=" + nodeId;
            }
        }
        return fileCenterPath;
    }

    @Override
    public byte[] downloadByZzwjlj(String zzwjlj) {
        byte[] result = null;
        if (StringUtils.isNotBlank(zzwjlj)) {
            String nodeId = bdcDzzzFileCenterService.getNodeIdByZzwjlj(zzwjlj);
            if (StringUtils.isNotBlank(nodeId)) {
                result = download(nodeId);
            }
        }
        return result;
    }

    @Override
    public String sendFileCenter(String path, String nodeid, String nodeName, String directoryName) {
        String fileCenterPath = null;
        String nodeParentId = getFileParentId(nodeid, directoryName);
        if (StringUtils.isNotBlank(nodeParentId)) {
            String nodeId = getNodeId(nodeParentId, nodeName);
            if (StringUtils.isBlank(nodeId)) {
                nodeId = uploadFilePath(path, nodeParentId, nodeName);
            } else {
                uploadFilePathReplace(path, nodeParentId, nodeName, nodeId);
            }

            if (StringUtils.isNotBlank(nodeId)) {
                fileCenterPath = "http://" + ipAddress + ":" + serverPort + "/realestate-e-certificate/rest/v1.0/zzgx/zzxzfile?fid=" + nodeId;
            }
        }
        return fileCenterPath;
    }


    @Override
    public String uploadFileInput(byte[] bytes, String parentId, String name) {
        String storageDtoId = null;
        if (null != bytes && StringUtils.isNotBlank(parentId)&& StringUtils.isNotBlank(name)) {
            MultipartDto multipartDto = new MultipartDto();
            multipartDto.setName(name);
            multipartDto.setOriginalFilename(name);
            multipartDto.setSize(bytes.length);
            multipartDto.setData(bytes);
            multipartDto.setContentType("application/pdf");
            multipartDto.setNodeId(parentId);
            multipartDto.setOwner(null);
            StorageDto storageDto = storageClient.multipartUpload(multipartDto);
            if (storageDto != null) {
                storageDtoId = storageDto.getId();
            }
        }

        return storageDtoId;
    }

    public String uploadFileInputReplace(byte[] bytes, String parentId, String name, String id) {
        String storageDtoId = null;
        if (null != bytes && StringUtils.isNotBlank(parentId)&& StringUtils.isNotBlank(name)&& StringUtils.isNotBlank(id)) {
            MultipartDto multipartDto = new MultipartDto();
            multipartDto.setName(name);
            multipartDto.setOriginalFilename(name);
            multipartDto.setSize(bytes.length);
            multipartDto.setData(bytes);
            multipartDto.setContentType("application/pdf");
            multipartDto.setNodeId(parentId);
            multipartDto.setOwner(null);
            StorageDto storageDto = storageClient.replaceUpload(id,multipartDto);
            if (null != storageDto) {
                storageDtoId = storageDto.getId();
            }
        }

        return storageDtoId;
    }

    @Override
    public byte[] download(String nodeId) {
        logger.info("采用大云下载方式{}", nodeId);

        byte[] srtbyte = null;
        try {
            MultipartDto multipartDto = storageClient.download(nodeId);
            if (null != multipartDto) {
                srtbyte = multipartDto.getData();
                logger.info("采用大云下载方式nodeId{},名称：{}", nodeId, multipartDto.getName());
            }
        } catch (Exception e) {
            logger.error("BdcDzzzStorageServiceImpl:download: {}, Cause:{}", e.getMessage(), e.getCause());
        }

        return srtbyte;
    }

    @Override
    public List<String> geNodeByParentId(String parentId) {
        List<String> nodeList = new ArrayList<>();
        List<StorageDto> storageDtoList = storageClient.listAllSubsetStorages(parentId,null,1,null, null,null);
        if (CollectionUtils.isNotEmpty(storageDtoList)){
            for (StorageDto storageDto: storageDtoList){
                nodeList.add(storageDto.getId());
            }
        }

        return nodeList;
    }

    @Override
    public void deleteFileByProid(String zzid, String directoryName) {
        if (StringUtils.isNoneBlank(zzid, directoryName)) {
            List<StorageDto> rootList = storageClient.listAllRootStorages("dzzz", "dzzz", null, zzid, 1, 0, null,null);

            if (CollectionUtils.isNotEmpty(rootList)) {
                StorageDto root = rootList.get(0);
                List<StorageDto> directoryList = storageClient.listAllSubsetStorages(root.getId(), directoryName, 1, 0, null,null);
                if (CollectionUtils.isNotEmpty(directoryList)) {
                    StorageDto directory = directoryList.get(0);
                    List<String> fileList = new ArrayList<>();
                    fileList.add(directory.getId());
                    storageClient.deleteStorages(fileList);
                }
            }
        }
    }

    @Override
    public void deleteFileByNodeid(String nodeId) {
        if (StringUtils.isNotBlank(nodeId)) {
            List<String> nodeIdList = new ArrayList<>();
            nodeIdList.add(nodeId);
            storageClient.deleteStorages(nodeIdList);
        }
    }

    @Override
    public void deleteFileByzzid(String zzid) {
        String storageId = getProjectFileId(zzid);
        if (StringUtils.isNotBlank(storageId)) {
            List<String> fileList = new ArrayList<>();
            fileList.add(storageId);
            storageClient.deleteStorages(fileList);
        }
    }

}
