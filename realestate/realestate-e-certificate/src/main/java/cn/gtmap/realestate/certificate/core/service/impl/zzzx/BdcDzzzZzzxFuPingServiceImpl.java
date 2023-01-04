package cn.gtmap.realestate.certificate.core.service.impl.zzzx;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzZzxxMapper;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.model.template.DzzzPdf;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileCenterService;
import cn.gtmap.realestate.certificate.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;


/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/28 创建电子证照
 */
public class BdcDzzzZzzxFuPingServiceImpl implements BdcDzzzZzzxService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzZzzxFuPingServiceImpl.class);

    @Autowired
    private BdcDzzzZzzxConfigService bdcDzzzZzzxConfigService;
    @Resource
    private BdcDzzzZzxxMapper bdcDzzzZzxxMapper;
    @Autowired
    private BdcDzzzFileCenterService bdcDzzzFileCenterService;
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzWaterMarkService bdcDzzzWaterMarkService;
    @Autowired
    private BdcDzzzZzxxPdfService bdcDzzzZzxxPdfService;

    @Override
    public DzzzResponseModel dzzzZzzx(Object o) {
        BdcDzzzZzxx bdcDzzzZzxx = (BdcDzzzZzxx)o;

        DzzzResponseModel beforeCheckModel = bdcDzzzZzzxConfigService.bdcDzzzZzzxBeforeCheck(bdcDzzzZzxx);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), beforeCheckModel.getHead().getStatus())) {
            return beforeCheckModel;
        }

        BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcDzzzZzxxMapper.queryBdcDzzzZzxxByZzbs(bdcDzzzZzxx.getZzbs());
        DzzzResponseModel infoCheckModel = bdcDzzzZzzxConfigService.bdcDzzzZzzxInfoCheck(bdcDzzzZzxxDO);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), infoCheckModel.getHead().getStatus())) {
            return infoCheckModel;
        }

        // 下载已盖章文件
        byte[] signArr = bdcDzzzFileCenterService.downloadByZzwjlj(bdcDzzzZzxxDO.getZzqzlj());
        if (null == signArr) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getCode(), null);
        }

        DzzzPdf dzzzPdf = bdcDzzzZzxxPdfService.getDzzzPdf(bdcDzzzZzxxDO.getZzlxdm());
        if (null == dzzzPdf) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_TEMPLATE_ACQUIRED_ERROR.getCode(), null);
        }

        // 已盖章文件加盖注销水印
        byte[] markArr = bdcDzzzWaterMarkService.addWatermarkZzzx(signArr, dzzzPdf, bdcDzzzZzxx.getZzbgyy());
        if (null == markArr) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_WATERMARK_ERROR.getCode(), null);
        }

        // 加盖注销水印文件上传文件中心替换原签章文件
        String savePdfName = bdcDzzzZzxxDO.getBdcqzh() + Constants.BDC_DZZZ_FORMAT_PDF;
        String zzqzlj = bdcDzzzFileCenterService.sendFileCenter(markArr, bdcDzzzZzxxDO.getZzid(), savePdfName, Constants.BDC_DZZZ);
        if (StringUtils.isBlank(zzqzlj)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_UPLOAD_FILE_CENTER_ERROR.getCode(), null);
        }

        // 更新注销信息
        return bdcDzzzZzzxConfigService.updateDzzzZxxxInfo(bdcDzzzZzxxDO.getZzid(), bdcDzzzZzxx.getZzbgyy(), bdcDzzzZzxx.getZzbgsj());
    }
}
