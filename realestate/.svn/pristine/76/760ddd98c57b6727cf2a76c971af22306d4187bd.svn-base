package cn.gtmap.realestate.certificate.core.service.impl.create;

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.model.template.DzzzPdf;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.core.service.create.BdcDzzzCreateService;
import cn.gtmap.realestate.certificate.util.BdcDzzzPdfUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/28 创建电子证照
 */
public class BdcDzzzFuPingCreateServiceImpl implements BdcDzzzCreateService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzFuPingCreateServiceImpl.class);
    @Autowired
    private BdcDzzzZzxxPdfService bdcDzzzZzxxPdfService;
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzSignConfigService bdcDzzzSignConfigService;
    @Autowired
    private BdcDzzzCreateConfigService bdcDzzzCreateConfigService;

    @Override
    public DzzzResponseModel createDzzz(Object o) {
        BdcDzzzZzxx bdcDzzzZzxx = (BdcDzzzZzxx)o;
        // 验证
        DzzzResponseModel checkBdcDzzzZzxxModel = bdcDzzzZzxxPdfService.checkBdcDzzz(bdcDzzzZzxx);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkBdcDzzzZzxxModel.getHead().getStatus())) {
            return checkBdcDzzzZzxxModel;
        }

        // 查找模板
        DzzzPdf dzzzPdf = bdcDzzzZzxxPdfService.getDzzzPdf(bdcDzzzZzxx.getZzlxdm());
        if (null == dzzzPdf) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_TEMPLATE_ACQUIRED_ERROR.getCode(), null);
        }

        // 根据模板创建PDF
        byte[] createPdfArr = dzzzPdf.createPdf(bdcDzzzZzxx);
        if (null == createPdfArr) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_CREATE_PDF_ERROR.getCode(), null);
        }

        // 保存证书附录图片
        if (StringUtils.isNotBlank(bdcDzzzZzxx.getFlImgBase64())) {
            DzzzResponseModel uploadFlImgModel = bdcDzzzCreateConfigService.uploadFlImg(bdcDzzzZzxx);
            if (StringUtils.equals(ResponseEnum.FALSE.getCode(), uploadFlImgModel.getHead().getStatus())) {
                return uploadFlImgModel;
            }
        }

        // 签章
        byte[] signArr = bdcDzzzSignConfigService.sign(bdcDzzzZzxx, createPdfArr, BdcDzzzPdfUtil.SIGN_COMPANY);
        if (null == signArr) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SIGNATURE_PDF_ERROR.getCode(), null);
        }
        // 上传文件中心
        String zzqzlj = bdcDzzzZzxxPdfService.sendFileCenter(bdcDzzzZzxx.getZzid(), bdcDzzzZzxx.getBdcqzh(), signArr);
        if (StringUtils.isBlank(zzqzlj)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_UPLOAD_FILE_CENTER_ERROR.getCode(), null);
        }
        bdcDzzzZzxx.setZzqzlj(zzqzlj);

        // 信息入库
        return bdcDzzzZzxxPdfService.insertBdcDzzz(bdcDzzzZzxx);
    }
}
