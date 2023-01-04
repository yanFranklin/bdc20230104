package cn.gtmap.realestate.certificate.core.service.file;


/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/3/21
 */
public interface BdcDzzzFileConfigService {

    String sendFileCenter(String path, String nodeId, String nodeName, String directoryName);

    String sendFileCenter(byte[] out, String nodeId, String nodeName, String directoryName);

    byte[] downloadByZzwjlj(String zzwjlj);

    byte[] download(String nodeId);

    void deleteFileByzzid(String zzid);
}
