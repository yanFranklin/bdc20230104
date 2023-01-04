package cn.gtmap.realestate.certificate.core.service.impl.sign;

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.ResponseData;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzConfigDo;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.model.template.DzzzPdf;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.util.*;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.sharesun.XSCloudSealUtil.XS_API_CloudSealSDK;
import com.xs.serverseal.ShareSunPDF;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;


/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/28 意源签章
 */
public class BdcDzzzSignXsyzServiceImpl implements BdcDzzzSignService {
    private final static Logger logger = LoggerFactory.getLogger(BdcDzzzSignXsyzServiceImpl.class);
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzSignConfigService bdcDzzzSignConfigService;
    @Autowired
    private BdcDzzzConfigService bdcDzzzConfigService;
    @Autowired
    private BdcDzzzZzxxPdfService bdcDzzzZzxxPdfService;

    @Override
    public byte[] signature(Object o, byte[] out) {
        byte[] result = null;
        BdcDzzzZzxx bdcDzzzZzxx = (BdcDzzzZzxx)o;
        if (null != bdcDzzzZzxx) {
            BdcDzzzConfigDo BdcDzzzConfigDo = bdcDzzzConfigService.queryBdcDzzzConfigDoByDwdm(bdcDzzzZzxx.getDwdm());
            // 查找模板
            DzzzPdf dzzzPdf = bdcDzzzZzxxPdfService.getDzzzPdf(bdcDzzzZzxx.getZzlxdm());
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
                result = signbylocationXsyz(BdcDzzzPdfUtil.XSYZ_SIGN_URL, out
                        , containerName, accessCode
                        , String.valueOf(dzzzPdf.getSign().getX()), String.valueOf(dzzzPdf.getSign().getY()), String.valueOf(dzzzPdf.getSign().getPage()));
                bdcDzzzSignConfigService.updateSignInfo(result, bdcDzzzZzxx);
            }
        }
        return result;
    }

    @Override
    public DzzzResponseModel verifyFile(String content, String bfjgxzqdm) {
        DzzzResponseModel dzzzResponseModel;
        String filePath = PublicUtil.getPath(Constants.RESOURCES_PDF + UUIDGenerator.generate() + Constants.BDC_DZZZ_FORMAT_PDF);
        if (!Base64Util.decodeBase64StrToFile(content, filePath)) {
            return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.BASE64_CONVERT_FILE_ERROR.getCode(), ResponseEnum.BASE64_CONVERT_FILE_ERROR.getMsg()));
        }
        int verify = ShareSunPDF.VerifySignature(filePath);
        if (verify == 1) {
            dzzzResponseModel = bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_FILE_WJQZJYSB.getCode(), ResponseEnum.VERIFY_FILE_WJQZJYSB.getMsg()));
        } else if (verify == 2) {
            dzzzResponseModel = bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_FILE_WJJYSB.getCode(), ResponseEnum.VERIFY_FILE_WJJYSB.getMsg()));
        } else {
            dzzzResponseModel = bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_FILE_WJYZTG.getCode(), ResponseEnum.VERIFY_FILE_WJYZTG.getMsg()));
        }
        PublicUtil.deleteFile(filePath);
        return dzzzResponseModel;
    }

    /**
     * @param signUr1       第三方签章地址
     * @param out           需要电子签章pdf文件路径
     * @param orgnizationid 机构组织证件号码
     * @param signtype      印章类型
     * @param x             x坐标
     * @param y             y坐标
     * @param index         签章页数
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn 返回文件流
     * @description 翔晟云章 对pdf文件进行电子签章
     */
    public static byte[] signbylocationXsyz(String signUr1, byte[] out, String orgnizationid, String signtype, String x, String y, String index) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("orgnizationid", orgnizationid);
        paramMap.put("signtype", signtype);
        paramMap.put("x", x);
        paramMap.put("y", y);
        paramMap.put("index", index);
        try{
            Object result = XS_API_CloudSealSDK.Send(signUr1, paramMap, out);
            if (null != result && result instanceof byte[]) {
                return (byte[])result;
            }
        } catch (Exception e) {
            logger.error("BdcDzzzSignXsyzServiceImpl:signbylocationXsyz");
        }
        return null;

    }
}
