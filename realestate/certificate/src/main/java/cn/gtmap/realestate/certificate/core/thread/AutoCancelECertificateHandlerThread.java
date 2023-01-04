package cn.gtmap.realestate.certificate.core.thread;

import cn.gtmap.realestate.certificate.core.dto.BdcCancelECertificateDTO;
import cn.gtmap.realestate.certificate.core.service.ECertificateModelServiceThread;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021-02-02
 * @description 根据zzbsList注销证书线程处理类
 */
public class AutoCancelECertificateHandlerThread extends CommonThread {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoCancelECertificateHandlerThread.class);

    /**
     * 服务
     */
    private ECertificateModelServiceThread eCertificateModelServiceThread;

    private List<BdcCancelECertificateDTO> zzbsList;

    public AutoCancelECertificateHandlerThread(ECertificateModelServiceThread eCertificateModelServiceThread, List<BdcCancelECertificateDTO> zzbsList) {
        this.eCertificateModelServiceThread = eCertificateModelServiceThread;
        this.zzbsList = zzbsList;
    }

    public ECertificateModelServiceThread getAccessModelServiceThread() {
        return eCertificateModelServiceThread;
    }

    public void setAccessModelServiceThread(ECertificateModelServiceThread eCertificateModelServiceThread) {
        this.eCertificateModelServiceThread = eCertificateModelServiceThread;
    }

    public List<BdcCancelECertificateDTO> getZzbsList() {
        return zzbsList;
    }

    public void setZzbsList(List<BdcCancelECertificateDTO> zzbsList) {
        this.zzbsList = zzbsList;
    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
//        LOGGER.info("线程开始处理：");
//        LOGGER.info("参数：{}", JSON.toJSONString(zzbsList));
        eCertificateModelServiceThread.autoCancelECertificateByZzbsList(zzbsList);
    }
}
