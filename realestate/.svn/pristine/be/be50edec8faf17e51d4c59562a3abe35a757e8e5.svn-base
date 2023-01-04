package cn.gtmap.realestate.certificate.core.service.file;
import java.io.InputStream;
import java.util.List;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/1/24
 */
public interface BdcDzzzFileCenterService {
    Integer getProjectFileId(String projectId) ;

    Integer createFileFolderByclmc(Integer parentId, String folderNodeName);

    Integer getNodeId(Integer paterntId, String nodeName) ;

   /* List<Node> geNodeByParentId(Integer parentId) ;*/

    Integer uploadFile(String path, Integer parentId, String name);

    Integer uploadFile(InputStream is, Integer parentId, String name);

    String sendFileCenter(String path, String node_Id, String nodeName, String directoryName);

    String sendFileCenter(byte[] out, String proid, String nodeName, String directoryName);

    String download(String nodeId, String path);

    byte[] downloadByZzwjlj(String zzwjlj);

    byte[] download(String nodeId);

    void deleteFileByProid(String proid, String directoryName);

    void deleteFileByZzwjlj(String zzwjlj);

    void deleteFileByzzid(String zzid);

    String getNodeIdByZzwjlj(String zzwjlj);

    String encodeFidByZzwjlj(String zzwjlj);

}
