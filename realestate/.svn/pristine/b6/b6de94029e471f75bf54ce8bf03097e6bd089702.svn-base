package cn.gtmap.realestate.certificate.core.thread;

import cn.gtmap.realestate.certificate.service.ECertificateClService;

import java.util.concurrent.Callable;

/**
 * @description 合肥存量电子证照制证线程类
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @date 2023/1/3 15:30
 */
public class HefeiDzzzClThread implements Callable<Boolean> {

    private ECertificateClService eCertificateClService;

    private String zsid;

    public HefeiDzzzClThread(ECertificateClService eCertificateClService, String zsid) {
        this.eCertificateClService = eCertificateClService;
        this.zsid = zsid;
    }

    @Override
    public Boolean call() {
        return eCertificateClService.createHefeiClDzzz(zsid);
    }
}
