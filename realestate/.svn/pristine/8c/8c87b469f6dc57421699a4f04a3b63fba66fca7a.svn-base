package cn.gtmap.realestate.certificate.core.service.file;


import java.util.List;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/9/25 文档中心上传文件
 */
public interface BdcDzzzStorageService {
    /**
     * 根据文件路径上传文件
     *
     * @param path
     * @param parentid
     * @param name
     * @return 节点id
     */
    String uploadFilePath(String path, String parentid, String name);

    /**
     * 根据input流上传文件
     *
     * @param bytes
     * @param parentId
     * @param name
     * @return 节点id
     */
    String uploadFileInput(byte[] bytes, String parentId, String name);

    /**
     * 根据证照id 创建或查询对应文件夹 返回文件夹id
     * @param projectId
     * @return
     */
    String getProjectFileId(String projectId);

    /**
     * 根据父节点id 及文件夹名称 创建文件夹
     * @param parentId
     * @param folderNodeName
     * @return
     * @throws NodeNotFoundException
     */
    String createFileFolderByclmc(String parentId, String folderNodeName) ;

    /**
     * 根据父节点id 文件名称 获取对应文件id
     * @param parentId
     * @param nodeName
     * @return
     * @throws NodeNotFoundException
     */
    String getNodeId(String parentId, String nodeName) ;

    String sendFileCenter(byte[] out, String nodeid, String nodeName, String directoryName);

    String sendFileCenter(String path, String nodeid, String nodeName, String directoryName);

    /**
     * 下载文件
     * @param nodeId
     * @return
     */
    byte[] download(String nodeId);

    /**
     * 根据父id下载所有文件夹主键 或所有文件主键
     * @param parentId
     * @return
     * @throws NodeNotFoundException
     */
    List<String> geNodeByParentId(String parentId) ;

    /**
     * 根据证照id 和 子目录id 删除文件
     * @param zzid
     * @param directoryName
     */
    void deleteFileByProid(String zzid, String directoryName);

    /**
     * 根据节点id删除文件
     * @param nodeId
     */
    void deleteFileByNodeid(String nodeId);

    /**
     * 根据证照id 删除所有数据
     * @param zzid
     */
    void deleteFileByzzid(String zzid);
}
