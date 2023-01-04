package cn.gtmap.realestate.certificate.core.thread;

import cn.gtmap.realestate.certificate.core.enums.DzzzClZtEnum;
import cn.gtmap.realestate.certificate.service.ECertificateClService;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDzzzCxDTO;

import java.util.concurrent.Callable;

public class DzzzClThread implements Callable<Boolean> {

    private ECertificateClService eCertificateClService;

    private BdcDzzzCxDTO bdcDzzzCxDTO;

    private DzzzClZtEnum clZtEnum;

    public DzzzClThread(ECertificateClService eCertificateClService, BdcDzzzCxDTO bdcDzzzCxDTO, DzzzClZtEnum clZtEnum) {
        this.eCertificateClService = eCertificateClService;
        this.bdcDzzzCxDTO = bdcDzzzCxDTO;
        this.clZtEnum = clZtEnum;
    }

    @Override
    public Boolean call() {
        return DzzzClZtEnum.DZZZ_CL_XZ.equals(clZtEnum) ? eCertificateClService.xzClDzzz(bdcDzzzCxDTO) : eCertificateClService.qfClDzzz(bdcDzzzCxDTO);
    }
}
