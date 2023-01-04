package cn.gtmap.realestate.certificate.service;

import cn.gtmap.realestate.certificate.core.enums.DzzzClZtEnum;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDzzzCxDTO;

import java.util.List;

/**
 * 电子证照存量服务
 *
 * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
 * @version 下午3:53 2021/2/3
 */
public interface ECertificateClService {

    Integer dzzzClywbc(DzzzClZtEnum clZtEnum);

    Boolean xzClDzzz(BdcDzzzCxDTO bdcDzzzCxDTO);

    Boolean qfClDzzz(BdcDzzzCxDTO bdcDzzzCxDTO);

    Integer xzClDzzz(List<String> zzbsList);

    Integer qfClDzzz(List<String> bdcqzhs);

    Integer plCreateHefeiClDzzz(List<String> zsids);

    Boolean createHefeiClDzzz(String zsid);
}
