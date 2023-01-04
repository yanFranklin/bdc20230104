package cn.gtmap.realestate.certificate.core.service.impl.file;

import cn.gtmap.realestate.certificate.core.service.BdcDzzzHttpService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileCenterService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileService;
import cn.gtmap.realestate.certificate.core.service.impl.BdcDzzzServiceImpl;
import cn.gtmap.realestate.certificate.util.Base64Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/1/24
 */
@Service
public class BdcDzzzFileCenterServiceImpl implements BdcDzzzFileCenterService,BdcDzzzFileService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzFileCenterServiceImpl.class);
    @Autowired
    private BdcDzzzHttpService bdcDzzzHttpService;
    @Value("${spring.cloud.client.ipAddress}")
    protected String ipAddress;
    @Value("${server.port}")
    protected String serverPort;

    @Override
    public Integer getProjectFileId(String projectId)  {

        return null;

    }

    @Override
    public Integer createFileFolderByclmc(Integer parentId, String folderNodeName)  {
       /* Node tempNode = null;
        if (StringUtils.isNotBlank(folderNodeName)) {
            tempNode = nodeService.getNode(parentId, folderNodeName, true);
            if (tempNode != null) {
                return tempNode.getId();
            } else {
                return -1;
            }
        } else {
            return -1;
        }*/
       return null;
    }

    @Override
    public Integer getNodeId(Integer paterntId, String nodeName) {
        Integer nodeId = null;
        boolean isAdd = true;
        /*List<Node> nodeList = geNodeByParentId(paterntId);
        if (CollectionUtils.isNotEmpty(nodeList)) {
            for (Node node : nodeList) {
                if (StringUtils.equals(node.getName(), nodeName)) {
                    isAdd = false;
                    nodeId = node.getId();
                }
            }
        }*/
        if (isAdd) {
            nodeId = createFileFolderByclmc(paterntId, nodeName);
        }
        return nodeId;
    }



    @Override
    public Integer uploadFile(String path, Integer parentId, String name){
        Integer result = null;
        /*try {
            Node node = fileService.uploadFile(new File(path), parentId, name, null, true, false);
            if (node != null && node.getId() != null) {
                result = node.getId();
            }
        } catch (IOException e) {
            logger.error("BdcDzzzFileCenterServiceImpl:uploadFile:path", e);
        }*/

        return result;
    }

    @Override
    public Integer uploadFile(InputStream is, Integer parentId, String name) {
        Integer result = null;
       /* try {
            Node node = fileService.uploadFile(is, parentId, name, null, true, true);
            if (node != null && node.getId() != null) {
                result = node.getId();
            }
        } catch (IOException e) {
            logger.error("BdcDzzzFileCenterServiceImpl:uploadFile:InputStream", e);
        }*/
        return result;
    }



    @Override
    public String sendFileCenter(byte[] out, String node_Id, String nodeName, String directoryName) {
        String fileCenterPath = null;
       /* if (null != out && StringUtils.isNotBlank(node_Id)) {
            Integer projectFileId = getProjectFileId(node_Id);
            if (null != projectFileId) {
                Integer nodeParentId = getNodeId(projectFileId, directoryName);
                if (null != nodeParentId) {
                    Integer nodeId = uploadFile(new ByteArrayInputStream(out), nodeParentId, nodeName);
                    if (null != nodeId) {
                        fileCenterPath = "http://" + ipAddress + ":" + serverPort +  "/rest/v1.0/zzgx/zzxzfile?fid=" + nodeId;
                    }
                }
            }
        }*/

        return fileCenterPath;
    }

    @Override
    public String sendFileCenter(String path, String node_Id, String nodeName, String directoryName) {
        String fileCenterPath = null;
        /*if (StringUtils.isNotBlank(path) && StringUtils.isNotBlank(node_Id)) {
            Integer projectFileId = getProjectFileId(node_Id);
            if (null != projectFileId) {
                Integer nodeParentId = getNodeId(projectFileId, directoryName);
                if (null != nodeParentId) {
                    Integer nodeId = uploadFile(path, nodeParentId, nodeName);
                    if (null != nodeId) {
                        fileCenterPath = "http://" + ipAddress + ":" + serverPort + "/rest/v1.0/zzgx/zzxzfile?fid=" + nodeId;
                    }
                }
            }
        }*/

        return fileCenterPath;
    }



    @Override
    public void deleteFileByProid(String proid, String directoryName) {
        /*try {
            if (StringUtils.isNotBlank(proid)) {
                Integer projectFileId = getProjectFileId(proid);
                Integer nodeId = getNodeId(projectFileId, directoryName);
                if (null != nodeId) {
                    nodeService.remove(nodeId);
                }
            }
        } catch (Exception e) {
            logger.error("BdcDzzzFileCenterServiceImpl:deleteFileByProid", e);
        }*/

    }

    @Override
    public void deleteFileByZzwjlj(String zzwjlj) {
        /*if (StringUtils.isNotBlank(zzwjlj)) {
            String nodeId = getNodeIdByZzwjlj(zzwjlj);
            if (StringUtils.isNotBlank(nodeId)) {
                Node node = nodeService.getParentNode(Integer.parseInt(nodeId));
                if (null != node && null != node.getId()) {
                    nodeService.remove(node.getId());
                }
            }
        }*/
    }

    @Override
    public void deleteFileByzzid(String zzid) {
        /*if (StringUtils.isNotBlank(zzid)) {
            Integer projectFileId = getProjectFileId(zzid);
            if (null != projectFileId) {
                nodeService.remove(projectFileId);
            }
        }*/
    }

    @Override
    public String getNodeIdByZzwjlj(String zzwjlj) {
        String nodeId = null;
        if (StringUtils.isNotBlank(zzwjlj)) {
            String[] strs = zzwjlj.split("fid");
            if (strs.length == 2) {
                nodeId = strs[1].substring(1);
            }
        }
        return nodeId;
    }

    @Override
    public String encodeFidByZzwjlj(String zzwjlj) {
        String result = null;
        String nodeId = getNodeIdByZzwjlj(zzwjlj);
        if (StringUtils.isNotBlank(nodeId)) {
            result =  "http://" + ipAddress + ":" + serverPort + "/realestate-e-certificate/rest/v1.0/zzgx/zzxzfile?fid=" + Base64Util.encodeStrToBase64Str(nodeId);
        }
        return result;
    }

    @Override
    public String download(String nodeId, String path) {
        String result = null;
        /*try {
            if (StringUtils.isNotBlank(nodeId)) {
                fileService.downloadToFile(Integer.parseInt(nodeId), path);
                result = path;
            }
        } catch (IOException e) {
            logger.error("BdcDzzzFileCenterServiceImpl:download", e);
        }*/
        return result;
    }

    @Override
    public byte[] downloadByZzwjlj(String zzwjlj) {
        return download(getNodeIdByZzwjlj(zzwjlj));
    }

    @Override
    public byte[] download(String nodeId) {
        logger.info("采用文件下载方式{}", nodeId);
        byte[] result = null;
        /*if (StringUtils.isNotBlank(nodeId)) {
            try{
                result = bdcDzzzHttpService.doGet(BdcDzzzPdfUtil.BDCDJ2_FILE_CENTER_URL + "/file/get.do?token=whosyourdaddy&fid=" + nodeId);
            } catch (IOException e) {
                logger.error("BdcDzzzFileCenterServiceImpl:download:nodeId", e);
            }
        }*/

        return result;
    }
}
