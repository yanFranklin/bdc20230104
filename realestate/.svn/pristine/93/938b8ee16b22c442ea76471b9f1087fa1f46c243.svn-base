package cn.gtmap.realestate.certificate.core.service.impl.create;

import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.model.template.DzzzPdf;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.core.service.create.BdcDzzzCreateService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/28 创建电子证照--常州
 */
public class BdcDzzzCzCreateServiceImpl implements BdcDzzzCreateService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzCzCreateServiceImpl.class);
    @Autowired
    private BdcDzzzZzxxPdfService bdcDzzzZzxxPdfService;
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzCreateConfigService bdcDzzzCreateConfigService;
    @Autowired
    BdcDzzzZzxxService bdcDzzzZzxxService;
    @Autowired
    BdcDzqzPdfService bdcDzqzPdfService;

    @Override
    public DzzzResponseModel createDzzz(Object o) {
        BdcDzzzZzxx bdcDzzzZzxx = (BdcDzzzZzxx) o;
        // 验证
        DzzzResponseModel checkBdcDzzzZzxxModel = bdcDzqzPdfService.checkBdcDzqz(bdcDzzzZzxx);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkBdcDzzzZzxxModel.getHead().getStatus())) {
            return checkBdcDzzzZzxxModel;
        }

        //生成pdf 存储文件中心
        DzzzPdf dzzzPdf = bdcDzzzZzxxPdfService.getDzzzPdf(bdcDzzzZzxx.getZzlxdm());
        if (null == dzzzPdf) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_TEMPLATE_ACQUIRED_ERROR.getCode(), null);
        }

        byte[] createPdfArr = dzzzPdf.createPdf(bdcDzzzZzxx);
        if (null == createPdfArr) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_CREATE_PDF_ERROR.getCode(), null);
        }

        //如果存在附件图片，将图片存在大云文件中心
        if (StringUtils.isNotBlank(bdcDzzzZzxx.getFlImgBase64())) {
            DzzzResponseModel uploadFlImgModel = bdcDzzzCreateConfigService.uploadFlImgToStorage(bdcDzzzZzxx);
            if (StringUtils.equals(ResponseEnum.FALSE.getCode(), uploadFlImgModel.getHead().getStatus())) {
                return uploadFlImgModel;
            }
            logger.info("电子签章上传附件图片成功！");
        }


        // 上传文件中心
        String zzqzlj = bdcDzzzZzxxPdfService.sendStorage(bdcDzzzZzxx.getZzid(), bdcDzzzZzxx.getBdcqzh() + Constants.BDC_DZZZ_FILENAME_SIGNA, createPdfArr);
        if (StringUtils.isBlank(zzqzlj)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_CREATE_PDF_ERROR.getCode(), null);
        }
        bdcDzzzZzxx.setZzqzlj(zzqzlj);
        logger.info("模板定制接口生成pdf上传附件成功！");
        // 信息入库
        return bdcDzqzPdfService.insertBdcDzqz(bdcDzzzZzxx);

    }
}
