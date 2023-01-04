package cn.gtmap.realestate.certificate.core.service.impl.file;

import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileConfigService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileService;
import cn.gtmap.realestate.certificate.util.BdcDzzzPdfUtil;
import cn.gtmap.realestate.certificate.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/1/24
 */
public class BdcDzzzFileConfigServiceImpl implements BdcDzzzFileConfigService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<String, BdcDzzzFileService> bdcDzzzFileMap;

    @Override
    public String sendFileCenter(String path, String nodeId, String nodeName, String directoryName) {
        nodeName = nodeName + Constants.BDC_DZZZ_FORMAT_PDF;
        return getFileService().sendFileCenter(path, nodeId, nodeName, directoryName);
    }

    @Override
    public String sendFileCenter(byte[] out, String nodeId, String nodeName, String directoryName) {
        nodeName = nodeName + Constants.BDC_DZZZ_FORMAT_PDF;
        return getFileService().sendFileCenter(out, nodeId, nodeName, directoryName);
    }

    @Override
    public byte[] downloadByZzwjlj(String zzwjlj) {
        return getFileService().downloadByZzwjlj(zzwjlj);
    }

    @Override
    public byte[] download(String nodeId) {
        return getFileService().download(nodeId);
    }

    @Override
    public void deleteFileByzzid(String zzid) {
        getFileService().deleteFileByzzid(zzid);
    }

    public BdcDzzzFileService getFileService() {
        BdcDzzzFileService bdcDzzzFileService = bdcDzzzFileMap.get(BdcDzzzPdfUtil.DZZZ_SAVEFILE_TYPE);
        if (null == bdcDzzzFileService) {
            throw new NullPointerException("该附件保存" + BdcDzzzPdfUtil.DZZZ_SAVEFILE_TYPE + "没有对应的实现类！");
        }
        return bdcDzzzFileService;
    }

    public Map<String, BdcDzzzFileService> getBdcDzzzFileMap() {
        return bdcDzzzFileMap;
    }

    public void setBdcDzzzFileMap(Map<String, BdcDzzzFileService> bdcDzzzFileMap) {
        this.bdcDzzzFileMap = bdcDzzzFileMap;
    }
}
