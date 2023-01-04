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
 * 存量电子证照签发定时补偿服务
 *
 * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
 * @version 下午4:41 2021/2/2
 */
@Component
@ConditionalOnProperty("dzzzbc.clsj.zzqf.enable")
public class ECertificateClqfQuartzService {

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
    @Scheduled(cron = "${dzzzbc.clsj.zzqf.cron:0 0 20 * * ?}")
    public void clqf() {
        LOGGER.info("==================开启存量证照签发定时补偿服务==================");
        Integer size = eCertificateClService.dzzzClywbc(DzzzClZtEnum.DZZZ_CL_QF);
        LOGGER.info("==================存量证照签发定时补偿服务结束==================签发：{} 条数据", size);
    }

}
