package cn.gtmap.realestate.common.util;

import cn.gtmap.gtc.clients.UserManagerClient;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.clients.v1.StorageClient;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.storage.domain.enums.FileTypeEnum;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 文档中心工具类
 */
@Component
public class StorageUtils {
    private static final Logger logger = LoggerFactory.getLogger(StorageUtils.class);

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private UserManagerClient userManagerClient;


    /**
     * 复制某个业务场景的所有附件材料
     * @param originalClientId 原业务附件材料的clientId
     * @param originalSpaceId 原业务附件材料的spaceId
     * @param targetClientId 目标业务的clientId
     * @param targetSpaceId 目标业务的spaceId
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    public void copyAllFjcl(String originalClientId, String originalSpaceId, String targetClientId, String targetSpaceId) {
        if(StringToolUtils.isAllNullOrEmpty(originalClientId, originalSpaceId)) {
            throw new MissingArgumentException("复制附件材料未定义原业务clientId、spaceId，参数：" +
                     originalClientId + "," + originalSpaceId + "," + targetClientId + "," + targetSpaceId);
        }

        if(StringToolUtils.isAllNullOrEmpty(targetClientId, targetSpaceId)) {
            throw new MissingArgumentException("复制附件材料未定义目标业务clientId、spaceId，参数：" +
                    originalClientId + "," + originalSpaceId + "," + targetClientId + "," + targetSpaceId);
        }

        UserDto userDto = userManagerClient.getCurrentUser();
        String userName = null == userDto ? "" : userDto.getAlias();

        // 查询根目录文件夹和文件
        List<StorageDto> rootStorages = storageClient.listAllRootStorages(originalClientId, originalSpaceId, null,null,null,null,null,null);
        if(CollectionUtils.isEmpty(rootStorages)) {
            logger.warn("业务复制附件材料终止，原因：原有业务ClientId：{}，spaceId:{}无附件材料", originalClientId, originalSpaceId);
        }

        rootStorages.forEach(rootStorage -> {
            copyStorage(targetClientId, targetSpaceId, rootStorage, null, userName);
        });
    }

    /**
     * 递归复制文件（夹）
     * @param targetClientId 目标业务的clientId
     * @param targetSpaceId 目标业务的spaceId
     * @param storageDto 当前待复制文件（夹）
     * @param parentFolderId 父文件夹ID
     * @param userName 当前用户
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private void copyStorage(String targetClientId, String targetSpaceId, StorageDto storageDto, String parentFolderId, String userName) {
        if(null == storageDto || StringUtils.isBlank(storageDto.getId())) {
            return;
        }

        if(FileTypeEnum.FOLDER.intValue() == storageDto.getType()) {
            // 1、文件类型是文件夹
            // 复制文件夹自身
            StorageDto folder = storageClient.createFolder(targetClientId, targetSpaceId, parentFolderId, storageDto.getName(), userName);
            logger.info("新建文件夹：{}，对应业务信息：{}", storageDto.getName(), targetSpaceId);

            // 查找子文件夹和子文件
            List<StorageDto>  childStorageList = storageClient.listAllSubsetStorages(storageDto.getId(), null, null, null, null, null);
            if(CollectionUtils.isEmpty(childStorageList)) {
                return;
            }

            childStorageList.forEach(childStorage -> {
                copyStorage(targetClientId, targetSpaceId, childStorage, folder.getId(), userName);
            });
        } else {
            // 2、文件类型是文件
            MultipartDto multipartDto = storageClient.download(storageDto.getId());
            multipartDto.setClientId(targetClientId);
            multipartDto.setSpaceCode(targetSpaceId);
            multipartDto.setNodeId(parentFolderId);
            multipartDto.setOwner(userName);
            storageClient.multipartUpload(multipartDto);
            logger.info("上传文件：{}，对应业务信息：{}", storageDto.getName(), targetSpaceId);
        }
    }
}
