package cn.gtmap.realestate.common.matcher;

import cn.gtmap.gtc.storage.clients.v1.StorageClient;
import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/03/24
 * @description 大云storage存储接口类V1.x版本适配
 */
@Component
public class StorageClientMatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageClientMatcher.class);
    @Autowired
    private StorageClient storageClientV1;

    /**
     * @param clientId  应用Id
     * @param spaceId  存储空间Id
     * @param name 文件名称
     * @param owner 拥有者
     * @return
     * @description 新建根目录下文件夹, 已存在将不创建（参数个数按照V1.x版本组织，对业务层统一）
     */
    public StorageDto createRootFolder(String clientId, String spaceId, String name, String owner) {
        return storageClientV1.createRootFolder(clientId, spaceId, name, owner);
    }

    /**
     * @param clientId ， 应用Id，
     * @param spaceId  ， 存储空间Id，
     * @param owner    拥有者，
     * @param name     文件名称，
     * @param nodeId:  上级文件夹Id，
     * @return
     * @description 新建文件夹， nodeId 为空将建在用户根目录下（参数个数按照V1.x版本组织，对业务层统一）
     */
    public StorageDto createFolder(String clientId, String spaceId, String nodeId, String name, String owner) {
        return storageClientV1.createFolder(clientId, spaceId, nodeId, name, owner);
    }

    /**
     * @param multipartDto 文件
     * @return
     * @description 上传文件
     */
    public StorageDto multipartUpload(MultipartDto multipartDto) {
        return storageClientV1.multipartUpload(multipartDto);
    }

    /**
     * @param  clientId ， 应用Id，
     * @param  spaceId ， 存储空间Id，
     * @param owner 为空时查询所有用户文件
     * @param name 文件名称
     * @param enabled: 0: 删除状态， 1：正常状态
     * @param type: 0：目录 1：文件，2: 图片 3： 文档 4： 视频 5：音乐，6：其他, null:全部
     * @return
     * @description 按文件名称查询所有文件
     */
    public List<StorageDto> listStoragesByName(String clientId, String spaceId, String owner, String name, Integer enabled, Integer type) {
        return storageClientV1.listStoragesByName(clientId, spaceId, owner, name, enabled, type);
    }

    /**
     * @param fids 文件id列表
     * @return
     * @description 删除文件
     */
    public boolean deleteStorages(List<String> fids) {
        return storageClientV1.deleteStorages(fids);
    }

    /**
     * @param storageId 文件ID
     * @return
     * @description 下载
     */
    public MultipartDto download(String storageId) {
        return storageClientV1.download(storageId);
    }

    /**
     * @param fid 文件ID
     * @return BaseResultDto.code  0: 成功 msg: base64数据， 1： 失败， msg 失败信息
     * @description 获取图片base64编码数据
     */
    public BaseResultDto downloadBase64(String fid) {
        return storageClientV1.downloadBase64(fid);
    }

    /**
     * @param fid 文件
     * @return
     * @description 根据Id查询文件信息
     */
    public StorageDto findById(String fid) {
        return storageClientV1.findById(fid);
    }

    /**
     * @param id:要查询的文件节点，
     * @param name         文件名称，
     * @param enabled:     0: 删除状态， 1：正常状态
     * @param type:        0：目录 1：文件，2: 图片 3： 文档 4： 视频 5：音乐，6：其他, null:全部
     * @param orderType: 排序方式   createOrderAsc 根据创建时间正序排序  createOrderDesc：创建时间倒序排序  默认值:createOrderAsc
     * @return
     * @description 根据目录id查询目录下的文件信息（参数个数按照V1.x版本组织，对业务层统一）
     */
    public List<StorageDto> listAllSubsetStorages(String id, String name, Integer enabled, Integer type, Integer queryCount, String orderType) {
        return storageClientV1.listAllSubsetStorages(id, name, enabled, type, queryCount, orderType);
    }

    /**
     * @param username 用户名
     * @return
     * @description 获取用户签名ID
     */
    public String userSign(String username) {
        return storageClientV1.userSign(username);
    }

    /**
     * @param clientId ， 应用Id，
     * @param spaceId  ， 存储空间Id，
     * @param nodeId   目录ID，
     * @param enabled: 0: 删除状态， 1：正常状态，
     * @param type:    0：目录 1：文件，2: 图片 3： 文档 4： 视频 5：音乐，6：其他
     * @param name: 文件名
     * @param pattern: 查询模式   equal：精确查询  like:模糊查询  不传的话默认equal
     * @param orderType: 排序方式   createOrderAsc 根据创建时间正序排序  createOrderDesc：创建时间倒序排序  默认值:createOrderAsc
     * @return
     * @description 查询目录菜单
     */
    public List<StorageDto> queryMenus(String clientId, String spaceId, String nodeId, Integer type, Integer queryCount, String name, String pattern, String orderType){
        return storageClientV1.queryMenus(clientId, spaceId, nodeId, type, queryCount, name, pattern, orderType);
    }

    /**
     * @param clientId ， 应用Id，
     * @param spaceId  ， 存储空间Id，
     * @param nodeId   目录ID，
     * @param type:    0：目录 1：文件，2: 图片 3： 文档 4： 视频 5：音乐，6：其他
     * @return
     * @description 查询目录菜单
     */
    public List<StorageDto> queryMenus(String clientId, String spaceId, String nodeId, Integer type, Integer queryCount) {
        return storageClientV1.queryMenus(clientId, spaceId, nodeId, type, queryCount, null, null, null);
    }

    /**
     * @param multipartDto 文件
     * @return
     * @description 替换上传文件
     */
    public StorageDto replaceUpload(String id, MultipartDto multipartDto) {
        return storageClientV1.replaceUpload(id, multipartDto);
    }

    /**
     * @param clientId   ， 应用Id，
     * @param spaceId    ， 存储空间Id，
     * @param owner      拥有者， name 文件名称，
     * @param enabled:   0: 删除状态， 1：正常状态，
     * @param type:      0：目录 1：文件，2: 图片 3： 文档 4： 视频 5：音乐，6：其他
     * @param queryCount 1:查询份数
     * @param orderType: 排序方式   createOrderAsc 根据创建时间正序排序  createOrderDesc：创建时间倒序排序  默认值:createOrderAsc
     * @return
     * @description 文档拥有者所有根节点文件
     */
    public List<StorageDto> listAllRootStorages(String clientId, String spaceId, String owner, String name, Integer enabled, Integer type, Integer queryCount, String orderType) {
        return storageClientV1.listAllRootStorages(clientId, spaceId, owner, name, enabled, type, queryCount, orderType);
    }

    /**
     * @param owner    为空时查询所有用户文件
     * @param nodeIds  目录Ids
     * @param enabled: 0: 删除状态， 1：正常状态
     * @param type:    0：目录 1：文件，2: 图片 3： 文档 4： 视频 5：音乐，6：其他, null:全部
     * @param subpath: 是否递归所有子目录  1： 是 2：否
     * @return
     * @description 批量查询文件夹下文件个数
     */
    public Map<String, Long> getFoldersCount(List<String> nodeIds, String owner, Integer enabled, Integer type, Integer subpath) {
        return storageClientV1.getFoldersCount(nodeIds, owner, enabled, type, subpath);
    }

    /**
     * @param id 文件id,  name: 文件名
     * @return
     * @description 文件重命名
     */
    public boolean rename(String id, String name) {
        return storageClientV1.rename(id, name);
    }

    /**
     * @param ids      文件id
     * @param clientId ， 应用Id，
     * @param spaceId  ， 存储空间Id，
     * @return
     * @description 设置删除文件状态
     */
    public boolean deleteStatus(String clientId, String spaceId, List<String> ids) {
        return storageClientV1.deleteStatus(clientId, spaceId, ids);
    }

    /**
     * @param clientId ， 应用Id，
     * @param spaceId  ， 存储空间Id，
     * @param owner    拥有者， name 文件名称，
     * @param nodeId:  上级文件夹Id，
     * @param type:    0：目录 1：文件，2: 图片 3： 文档 4： 视频 5：音乐，6：其他
     * @return
     * @description 查看是否存在
     */
    public boolean checkExist(String clientId, String spaceId, String nodeId, String name, String owner, Integer type) {
        return storageClientV1.checkExist(clientId, spaceId, nodeId, name, owner, type);
    }

    /**
     * @param nodeId 文件夹节点ID
     * @param ids    要移动的文件ID
     * @return
     * @description 移动文件
     */
    public BaseResultDto moveAllStorages(String nodeId, List<String> ids) {
        return storageClientV1.moveAllStorages(nodeId, ids);
    }

    /**
     * 新建文件夹, 项目模式下spaceId不能为空,表示项目Id;names为逗号分隔字符串
     * @param clientId
     * @param spaceId
     * @param nodeId
     * @param names
     * @param owner
     * @return
     */
    public List<StorageDto> createFolderMulti(String clientId, String spaceId, String nodeId, String names, String owner) {
        return storageClientV1.createFolderMulti(clientId, spaceId, nodeId, names, owner);
    }

    /**
     * 新建根目录下文件夹, 已存在将不创建;names为逗号分隔字符串
     *
     * @param clientId ， 应用Id，
     * @param spaceId  ， 存储空间Id，
     * @param owner    拥有者，
     * @param names    文件名称 逗号间隔，
     * @return
     */
    public List<StorageDto> createRootFolderMulti(String clientId, String spaceId, String names, String owner) {
        return storageClientV1.createRootFolderMulti(clientId, spaceId, names, owner);
    }
}
