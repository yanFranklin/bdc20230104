package cn.gtmap.realestate.certificate.core.service.impl.create;

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.core.service.create.BdcDzzzCreateService;
import cn.gtmap.realestate.certificate.core.service.create.BdcDzzzJiangSuCreateService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileConfigService;
import cn.gtmap.realestate.certificate.util.BdcDzzzPdfUtil;
import cn.gtmap.realestate.certificate.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/28 创建电子证照
 */
public class BdcDzzzJiangSuCreateServiceImpl implements BdcDzzzCreateService,BdcDzzzJiangSuCreateService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzJiangSuCreateServiceImpl.class);
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzZzxxPdfService bdcDzzzZzxxPdfService;
    @Autowired
    private BdcDzzzSignConfigService bdcDzzzSignConfigService;
    @Autowired
    private BdcDzzzFileConfigService bdcDzzzFileConfigService;

    @Override
    public DzzzResponseModel createDzzz(Object o) {
        BdcDzzzZzxx bdcDzzzZzxx = (BdcDzzzZzxx)o;
        // 验证
        DzzzResponseModel checkBdcDzzzZzxxModel = bdcDzzzZzxxPdfService.checkBdcDzzz(bdcDzzzZzxx);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkBdcDzzzZzxxModel.getHead().getStatus())) {
            return checkBdcDzzzZzxxModel;
        }

        DzzzResponseModel createBdcDzzzZzxxModel = createOne(bdcDzzzZzxx);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), createBdcDzzzZzxxModel.getHead().getStatus())) {
            return createBdcDzzzZzxxModel;
        }

        // 信息入库
        return bdcDzzzZzxxPdfService.insertBdcDzzz(bdcDzzzZzxx);
    }

    @Override
    public DzzzResponseModel createOne(BdcDzzzZzxx bdcDzzzZzxx){
        // pdf生成
        byte[] createPdfArr = bdcDzzzZzxxPdfService.createPdfBdcDzzz(bdcDzzzZzxx);
        if (null == createPdfArr) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_CREATE_PDF_ERROR.getCode(), null);
        }

        return signUpload(bdcDzzzZzxx, createPdfArr);
    }

    @Override
    public DzzzResponseModel signUpload(BdcDzzzZzxx bdcDzzzZzxx, byte[] createPdfArr){
        // 签章
        byte[] signArr = bdcDzzzSignConfigService.sign(bdcDzzzZzxx, createPdfArr, BdcDzzzPdfUtil.SIGN_COMPANY);
        if (null == signArr) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SIGNATURE_PDF_ERROR.getCode(), null);
        }

        // 上传文件中心
        String zzqzlj = bdcDzzzFileConfigService.sendFileCenter(signArr, bdcDzzzZzxx.getZzid(), bdcDzzzZzxx.getBdcqzh(), Constants.BDC_DZZZ);
        if (StringUtils.isBlank(zzqzlj)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_UPLOAD_FILE_CENTER_ERROR.getCode(), null);
        }
        bdcDzzzZzxx.setZzqzlj(zzqzlj);

        return bdcDzzzService.dzzzResponseSuccess(ResponseEnum.SUCCESS.getMsg(), null);
    }

}
