package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.dto.BdcCancelECertificateDTO;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.certificate.core.service.ECertificateModelServiceThread;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.ResponseHead;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzzzFeignService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ECertificateModelServiceThreadImpl implements ECertificateModelServiceThread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ECertificateModelServiceThreadImpl.class);

    /**
     * 获取token的应用名称
     */
    @Value("${eCertificate.tokenYymc:}")
    private String tokenYymc;

    @Autowired
    private BdcDzzzFeignService bdcDzzzFeignService;

    @Autowired
    private BdcZsService bdcZsService;


    /**
     * @param zzbsList
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 根据zzbsList注销证书线程处理类
     */
    @Override
    public void autoCancelECertificateByZzbsList(List<BdcCancelECertificateDTO> zzbsList) {
        for (BdcCancelECertificateDTO bdcCancelECertificateDTO : zzbsList) {
            Map paramMap = new HashMap();
            paramMap.put(Constants.KEY_ZZBS, bdcCancelECertificateDTO.getZzbs());
            paramMap.put(Constants.KEY_ZZBGYY, Constants.ZZBGYY_BDC_ZX);

            DzzzResponseModel dzzzResponseModel = bdcDzzzFeignService.zzzt2(tokenYymc, JSONObject.toJSONString(paramMap));
            if (null != dzzzResponseModel) {
                ResponseHead head = dzzzResponseModel.getHead();
                if (StringUtils.equals(Constants.STATUS_SUCCESS, head.getStatus())) {
                    LOGGER.warn("证书{}的电子证照{}注销成功！", bdcCancelECertificateDTO.getZsid(), bdcCancelECertificateDTO.getZzbs());
                    BdcZsDO bdcZsDO = new BdcZsDO();
                    bdcZsDO.setZzbs("");
                    bdcZsDO.setZsid(bdcCancelECertificateDTO.getZsid());
                    bdcZsService.updateBdcZs(bdcZsDO);
                    LOGGER.warn("证书：{} 更新注销电子证照附件 id：{}", bdcZsDO.getZsid(), bdcZsDO.getStorageid());
                } else {
                    LOGGER.error("证书【{}】电子证照标识【{}】注销失败！请求结果{}", bdcCancelECertificateDTO.getZsid(), bdcCancelECertificateDTO.getZzbs(), dzzzResponseModel);
                }
            }
        }

    }
}
