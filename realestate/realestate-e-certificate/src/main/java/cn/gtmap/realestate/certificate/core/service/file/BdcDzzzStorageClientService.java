package cn.gtmap.realestate.certificate.core.service.file;

import cn.gtmap.realestate.certificate.core.model.storage.MultipartDto;
import cn.gtmap.realestate.certificate.core.model.storage.StorageDto;

import java.util.List;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @Version 1.0
 * @description 1.0，2019/10/15 文档中心服务调用
 */
public interface BdcDzzzStorageClientService {
    /**
     * @param id:要查询的文件节点，
     * @param name         文件名称，
     * @param enabled:     0: 删除状态， 1：正常状态
     * @param type:        0：目录 1：文件，2: 图片 3： 文档 4： 视频 5：音乐，6：其他, null:全部
     * @return
     * @description 根据目录id查询目录下的文件信息
     */
    List<StorageDto> listAllSubsetStorages(String id,
                                           String name,
                                           Integer enabled,
                                           Integer type);

    /**
     * @param clientId ， 应用Id，
     * @param spaceId  ， 存储空间Id，
     * @param owner    拥有者， name 文件名称，
     * @param enabled: 0: 删除状态， 1：正常状态，
     * @param type:    0：目录 1：文件，2: 图片 3： 文档 4： 视频 5：音乐，6：其他
     * @return
     * @description 文档拥有者所有根节点文件
     */
    List<StorageDto> listAllRootStorages(String clientId,
                                         String spaceId,
                                         String owner,
                                         String name,
                                         Integer enabled,
                                         Integer type);

    /**
     * @param ids 文件id列表
     * @return
     * @description 删除文件
     */
    boolean deleteStorages(List<String> ids);

    /**
     * @param multipartDto 文件
     * @return
     * @description 上传文件
     */
    StorageDto multipartUpload(MultipartDto multipartDto);

    /**
     * @param multipartDto 文件
     * @return
     * @description 上传文件 替换
     */
    StorageDto replaceUpload(String id, MultipartDto multipartDto);

    /**
     * @param  clientId ， 应用Id，
     * @param  spaceId ， 存储空间Id，
     * @param  owner 拥有者， name 文件名称，
     * @return
     * @description 新建根目录下文件夹, 已存在将不创建
     */
    StorageDto createRootFolder(String clientId,
                                String spaceId,
                                String name,
                                String owner);

    /**
     * @param  clientId ， 应用Id，
     * @param  spaceId ， 存储空间Id，
     * @param  owner 拥有者，
     * @param  name 文件名称，
     * @param  nodeId: 上级文件夹Id，
     * @return
     * @description 新建文件夹， nodeId 为空将建在用户根目录下
     */
    StorageDto createFolder(String clientId,
                            String spaceId,
                            String nodeId,
                            String name,
                            String owner);
}
