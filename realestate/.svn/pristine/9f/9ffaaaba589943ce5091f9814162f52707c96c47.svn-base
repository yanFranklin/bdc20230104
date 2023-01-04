package cn.gtmap.realestate.certificate.core.service.impl.sign;

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.ResponseData;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzConfigDo;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.util.*;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/28 意源签章
 */
public class BdcDzzzSignIdeaBankServiceImpl implements BdcDzzzSignService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzSignIdeaBankServiceImpl.class);
    @Autowired
    private BdcDzzzConfigService bdcDzzzConfigService;
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzSignConfigService bdcDzzzSignConfigService;

    @Override
    public byte[] signature(Object o, byte[] out) {
        byte[] result = null;
        BdcDzzzZzxx bdcDzzzZzxx = (BdcDzzzZzxx)o;
        if (null != bdcDzzzZzxx) {
            BdcDzzzConfigDo BdcDzzzConfigDo = bdcDzzzConfigService.queryBdcDzzzConfigDoByDwdm(bdcDzzzZzxx.getDwdm());
            String containerName = null;
            String accessCode = null;
            if (StringUtils.isNotBlank(bdcDzzzZzxx.getDzqzwybs())) {
                containerName = bdcDzzzZzxx.getDzqzwybs();
                accessCode = ReadXmlPropsUtil.analysisSealXml(bdcDzzzZzxx.getZzbfjgdm(),bdcDzzzZzxx.getDzqzwybs());
            }
            if (null != BdcDzzzConfigDo && StringUtils.isBlank(containerName) && StringUtils.isBlank(accessCode)) {
                containerName = BdcDzzzConfigDo.getContainerName();
                accessCode = BdcDzzzConfigDo.getAccessCode();
            }
            if (StringUtils.isNotBlank(containerName) && StringUtils.isNotBlank(accessCode)) {
                result = signatureIdeaBank(bdcDzzzZzxx.getZstype(), bdcDzzzZzxx.getBdcqzh() + Constants.BDC_DZZZ_FORMAT_PDF
                        , out, containerName, accessCode);
                bdcDzzzSignConfigService.updateSignInfo(result, bdcDzzzZzxx);
            }
        }
        return result;
    }

    @Override
    public DzzzResponseModel verifyFile(String content, String bfjgxzqdm) {
        String filePath = PublicUtil.getPath(Constants.RESOURCES_PDF + UUIDGenerator.generate() + Constants.BDC_DZZZ_FORMAT_PDF);
        if (!Base64Util.decodeBase64StrToFile(content, filePath)) {
            return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.BASE64_CONVERT_FILE_ERROR.getCode(), ResponseEnum.BASE64_CONVERT_FILE_ERROR.getMsg()));
        }
        BdcDzzzConfigDo BdcDzzzConfigDo = bdcDzzzConfigService.queryBdcDzzzConfigDoByDwdm(bfjgxzqdm);
        if (null != BdcDzzzConfigDo) {
            int ret = NativeUtil.PCLibrary.INSTANCE4.IBES_verifyESealFromPDF(filePath,  BdcDzzzConfigDo.getContainerName(), BdcDzzzConfigDo.getAccessCode());
            PublicUtil.deleteFile(filePath);
            logger.info("IBES_verifyESealFromPDF : " + ret);
            if (0 == ret) {
                return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_FILE_WJYZTG.getCode(), ResponseEnum.VERIFY_FILE_WJYZTG.getMsg()));
            }
        }
        return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_FILE_WJJYSB.getCode(), ResponseEnum.VERIFY_FILE_WJJYSB.getMsg()));
    }

    private byte[] signatureIdeaBank(String zstype, String fileName, byte[] out, String containerName,
                                     String accessCode) {
        byte[] sPdfBytes = null;
        if (StringUtils.equals(Constants.BDC_ZSLX_MC_ZMS, zstype)) {
            sPdfBytes = signbylocationIdeaBank(fileName, out, Integer.parseInt(BdcDzzzPdfUtil.SIGN_IDEABANK_ZMS_PAGENO), Float.valueOf(BdcDzzzPdfUtil.SIGN_IDEABANK_ZMS_X)
                    , Float.valueOf(BdcDzzzPdfUtil.SIGN_IDEABANK_ZMS_Y), containerName,
                    accessCode);
        } else if (StringUtils.equals(Constants.BDC_ZSLX_MC_ZS, zstype)) {
            sPdfBytes = signbylocationIdeaBank(fileName, out, Integer.parseInt(BdcDzzzPdfUtil.SIGN_IDEABANK_ZS_PAGENO), Float.valueOf(BdcDzzzPdfUtil.SIGN_IDEABANK_ZS_X)
                    , Float.valueOf(BdcDzzzPdfUtil.SIGN_IDEABANK_ZS_Y), containerName,
                    accessCode);
        }

        return sPdfBytes;
    }

    public byte[] signbylocationIdeaBank(String fileName, byte[] out, int pageNo, float x, float y, String containerName,
                                         String accessCode) {
        byte[] result = null;
        String filePath = PublicUtil.byteToFile(BdcDzzzPdfUtil.DZZZ_LOCAL_PATH + Constants.RESOURCES_PDF + fileName, out);
        if (StringUtils.isNotBlank(filePath)) {
            int ret = NativeUtil.PCLibrary.INSTANCE4.IBES_affixESealToPDF(filePath, pageNo, x, y, containerName, accessCode);
            logger.info("IBES_affixESealToPDF : " + ret);
            if (0 == ret) {
                result = PublicUtil.fileToByte(filePath);
                PublicUtil.deleteFile(filePath);
            }
        }
        return result;
    }
}
