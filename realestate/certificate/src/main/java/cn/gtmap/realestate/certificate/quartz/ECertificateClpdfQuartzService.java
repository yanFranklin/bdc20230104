package cn.gtmap.realestate.certificate.quartz;

import cn.gtmap.realestate.certificate.core.enums.DzzzClZtEnum;
import cn.gtmap.realestate.certificate.service.ECertificateClService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 存量电子证照下载定时补偿服务
 *
 * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
 * @version 下午3:32 2021/2/3
 */

@Component
@ConditionalOnProperty("dzzzbc.clsj.zzpdf.enable")
public class ECertificateClpdfQuartzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ECertificateClqfQuartzService.class);

    @Autowired
    private ECertificateClService eCertificateClService;

    /**
     * 存量证照签发定时补偿
     *
     * @param
     * @return void
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    @Scheduled(cron = "${dzzzbc.clsj.zzpdf.cron:0 0 22 * * ?}")
    public void clpdf() {
        LOGGER.info("==================开启存量证照下载 pdf 定时补偿服务==================");
        Integer size = eCertificateClService.dzzzClywbc(DzzzClZtEnum.DZZZ_CL_XZ);
        LOGGER.info("==================存量证照下载 pdf 定时补偿服务结束==================下载：{} 条数据", size);
    }
}
