package cn.gtmap.realestate.certificate.core.service.impl.sign;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzDzqzMapper;
import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzZzxxMapper;
import cn.gtmap.realestate.certificate.core.model.ResponseData;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzConfigDo;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzYwxxDo;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.model.signature.BdcDzzzSignaTure;
import cn.gtmap.realestate.certificate.core.model.signature.Seal;
import cn.gtmap.realestate.certificate.core.model.template.DzzzPdf;
import cn.gtmap.realestate.certificate.core.model.template.DzzzSignPosition;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileCenterService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileConfigService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzStorageService;
import cn.gtmap.realestate.certificate.core.service.sign.BdcDzqzService;
import cn.gtmap.realestate.certificate.core.service.sign.BdcDzzzSignCzService;
import cn.gtmap.realestate.certificate.util.*;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlBysldjFeginService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kinggrid.kgcore.enmu.KGQueryByEnum;
import com.kinggrid.kgcore.enmu.KGServerTypeEnum;
import com.kinggrid.pdf.DigitalSignatureByKey;
import com.kinggrid.pdf.KGPdfHummer;
import com.kinggrid.pdf.executes.PdfSignature4KG;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.kg.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;


/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0???2019/2/28 ????????????
 */
public class BdcDzzzSignCzServiceImpl implements BdcDzzzSignService, BdcDzzzSignCzService {
    private final Logger logger = LoggerFactory.getLogger(BdcDzzzSignCzServiceImpl.class);
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    BdcDzzzFileCenterService bdcDzzzFileCenterService;
    @Autowired
    private BdcDzzzStorageService bdcDzzzStorageService;
    @Autowired
    private BdcDzzzHttpService bdcDzzzHttpService;
    @Autowired
    private BdcDzzzZzxxService bdcDzzzZzxxService;
    @Resource
    private BdcDzzzZzxxMapper bdcDzzzZzxxMapper;
    @Autowired
    private BdcDzzzSignConfigService bdcDzzzSignConfigService;
    @Autowired
    private BdcDzzzConfigService bdcDzzzConfigService;
    @Autowired
    private BdcDzzzZzxxPdfService bdcDzzzZzxxPdfService;
    @Autowired
    private BdcDzzzZzzxConfigService bdcDzzzZzzxConfigService;
    @Autowired
    private BdcDzzzWaterMarkService bdcDzzzWaterMarkService;
    @Autowired
    BdcDzzzFileConfigService bdcDzzzFileConfigService;
    @Autowired
    BdcDzqzService bdcDzqzService;
    @Autowired
    BdcDzzzDzqzMapper bdcDzzzDzqzMapper;
    @Value("${dzqz.gg.gglx:}")
    private String gglxMapString;

    @Autowired
    BdcSlBysldjFeginService bdcSlBysldjFeginService;

    @Autowired
    BdcDzzzYwxxService bdcDzzzYwxxService;

    @Override
    public byte[] signature(Object o, byte[] out) {
        BdcDzzzSignaTure bdcDzzzSignaTure = (BdcDzzzSignaTure) o;
        DzzzSignPosition position = bdcDzzzSignaTure.getPosition();
        Seal seal = bdcDzzzSignaTure.getSeal();
        // ????????????
        String metaExecutesJson = getMetaExecutesJson(position, seal);

        // ??????SOAP
        String soapRequestData = getSoapRequestData(Base64Util.encodeByteToBase64Str(out), metaExecutesJson);

        return sign(soapRequestData);
    }

    private byte[] sign(String soapRequestData) {
        byte[] result = null;
        String msg = null;
        try {
            byte[] outputBytes = bdcDzzzHttpService.doPostWithString(BdcDzzzPdfUtil.SIGN_CZ_URL, soapRequestData, "text/xml;charset=UTF-8", "UTF-8");
            if (null != outputBytes) {
                msg = ReadXmlPropsUtil.analysisSoapXml(StringUtil.byteToStrUtf8(outputBytes));
            }

        } catch (IOException e) {
            logger.error("signatureChangZ:IOException", e);
        } catch (DocumentException e) {
            logger.error("signatureChangZ:DocumentException", e);
        }

        if (StringUtils.isNotBlank(msg)) {
            result = Base64Util.decodeBase64StrToByte(msg);
        }
        return result;
    }

    @Override
    public DzzzResponseModel verifyFile(String content, String bfjgxzqdm) {
        return bdcDzzzService.dzzzResponseSuccess(new ResponseData(ResponseEnum.VERIFY_FILE_WJJYSB.getCode(), ResponseEnum.VERIFY_FILE_WJJYSB.getMsg()));
    }

    private String getMetaExecutesJson(DzzzSignPosition position, Seal seal) {
        StringBuilder result = new StringBuilder();
        result.append("{\"keysn\":").append("\"" + seal.getKeysn() + "\",");
        result.append("\"signature\": [").append("{\"position\":");
        result.append(JSON.toJSONString(position)).append(",");
        result.append("\"seal\":");
        result.append(JSON.toJSONString(seal));
        result.append("}]}");
        return result.toString();
    }

    private String getSoapRequestData(String base64Data, String metaExecutesJson) {
        StringBuilder soapRequestData = new StringBuilder();
        soapRequestData.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.kinggrid.com\">");
        soapRequestData.append("<soapenv:Header/>");
        soapRequestData.append("<soapenv:Body>");
        soapRequestData.append("<ws:exec>");
        soapRequestData.append("<ws:pdf>");
        soapRequestData.append(base64Data);
        soapRequestData.append("</ws:pdf>");
        soapRequestData.append("<ws:metaExecutesJson>");
        soapRequestData.append(metaExecutesJson);
        soapRequestData.append("</ws:metaExecutesJson>");
        soapRequestData.append("<ws:metasignsJSON>");
        soapRequestData.append("</ws:metasignsJSON>");
        soapRequestData.append("</ws:exec>");
        soapRequestData.append("</soapenv:Body>");
        soapRequestData.append("</soapenv:Envelope>");
        return soapRequestData.toString();
    }


    @Override
    public DzzzResponseModel standSign(String zzids) {
        if (StringUtils.isBlank(zzids)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getMsg(), null);
        }

        String resultPath = PublicUtil.fileMkdirs(BdcDzzzPdfUtil.DZZZ_LOCAL_PATH + Constants.RESOURCES_PDF);
        JSONArray array = new JSONArray();
        String[] zzidArr = zzids.split(",");
        if (zzidArr.length > BdcDzzzPdfUtil.SOFTDOG_SIGN_COUNT) {
            return bdcDzzzService.dzzzResponseFalse("??????????????????????????????????????????" + BdcDzzzPdfUtil.SOFTDOG_SIGN_COUNT + "???", null);
        }
        for (String zzid : zzidArr) {
            BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcDzqzService.queryBdcDzzzQzxx(zzid);
            if (null == bdcDzzzZzxxDO) {
                logger.error("????????????????????????????????????id{}", zzid);
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getMsg(), null);
            }

            BdcDzzzZzxx bdcDzzzZzxx = bdcDzzzZzxxService.getBdcDzzzZzxxFromBdcDzzzZzxxDO(bdcDzzzZzxxDO, null);
            BdcDzzzConfigDo bdcDzzzConfigDo = bdcDzzzConfigService.queryBdcDzzzConfigDoByDwdm(bdcDzzzZzxx.getDwdm());
            if (null == bdcDzzzConfigDo) {
                logger.error("????????????????????????????????????qxdm={}", bdcDzzzZzxx.getDwdm());
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getMsg(), null);
            }
            if (StringUtils.isBlank(bdcDzzzConfigDo.getKeysn()) || StringUtils.isBlank(bdcDzzzConfigDo.getContainerName())
                    || StringUtils.isBlank(bdcDzzzConfigDo.getAccessCode())) {
                return bdcDzzzService.dzzzResponseFalse("???????????????????????????", null);
            }

            // ????????????
            DzzzPdf dzzzPdf = bdcDzzzZzxxPdfService.getDzqzPdf(bdcDzzzZzxx.getZzlxdm(),bdcDzzzZzxx.getDwdm());
            logger.info("dwdm???=========={}zzlxdm???======={}", bdcDzzzZzxx.getDwdm(), bdcDzzzZzxx.getZzlxdm());
            if (null == dzzzPdf) {
                //?????????????????????
                if (StringUtils.isNotBlank(gglxMapString)) {
                    Map<String, String> gglxMap = JSON.parseObject(gglxMapString, Map.class);
                    Map<String, Integer> qzxxMap = JSON.parseObject(gglxMap.get(bdcDzzzZzxx.getZzlxdm()), Map.class);
                    dzzzPdf = new DzzzPdf();
                    DzzzSignPosition dzzzSignPosition = new DzzzSignPosition();
                    dzzzSignPosition.setPage(qzxxMap.get("page"));
                    dzzzSignPosition.setX(qzxxMap.get("X"));
                    dzzzSignPosition.setY(qzxxMap.get("Y"));
                    dzzzPdf.setSign(dzzzSignPosition);
                } else {
                    logger.error("?????????????????????{}", bdcDzzzZzxx.getZzlxdm());
                    return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_TEMPLATE_ACQUIRED_ERROR.getCode(), null);
                }
            }

            // ?????????????????????
            byte[] createArr = bdcDzzzFileConfigService.downloadByZzwjlj(bdcDzzzZzxxDO.getZzqzlj());
            if (null == createArr) {
                logger.error("????????????????????????????????????{}", bdcDzzzZzxxDO.getZzqzlj());
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_NO_RECORD_ACQUIRED_ERROR.getMsg(), null);
            }

            String savePdfName = bdcDzzzZzxxDO.getBdcqzh() + Constants.BDC_DZZZ_FILENAME_SIGNA + Constants.BDC_DZZZ_FORMAT_PDF;
            String fileName = resultPath + savePdfName;
            logger.warn("??????????????????{}", fileName);
            KGPdfHummer hummer = null;

            try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                hummer = KGPdfHummer.createSignature(new ByteArrayInputStream(createArr), null, fileOutputStream, null, true);
                if (null == hummer) {
                    return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getMsg(), null);
                }

                DigitalSignatureByKey digitalSignatureByKey = new DigitalSignatureByKey();
                digitalSignatureByKey.setSigType(DigitalSignatureByKey.SIG_P7);
                hummer.setCertificate(null, digitalSignatureByKey);

                PdfSignature4KG pdfSignature4KG = new PdfSignature4KG(BdcDzzzPdfUtil.SOFTDOG_SIGN_CZ_URL,
                        KGServerTypeEnum.AUTO, bdcDzzzConfigDo.getKeysn(), bdcDzzzConfigDo.getContainerName(), bdcDzzzConfigDo.getAccessCode());
                pdfSignature4KG.setQueryBy(KGQueryByEnum.KEYSN);
                pdfSignature4KG.setPagen(dzzzPdf.getSign().getPage());
                pdfSignature4KG.setXY(dzzzPdf.getSign().getX(), dzzzPdf.getSign().getY());

                hummer.setPdfSignature(pdfSignature4KG);
                hummer.doSignature();
                byte[] hash_data = Hex.decode(digitalSignatureByKey.getHash());
                MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                hash_data = messageDigest.digest(hash_data);

                String needSigMessage = new String(Hex.encode(hash_data));
                String sigKeepData = digitalSignatureByKey.getSigKeepData();

                JSONObject obj = new JSONObject();
                obj.put("needSigMessage", needSigMessage);
                obj.put("sigKeepData", sigKeepData);
                obj.put("id", savePdfName);
                obj.put("zzid", zzid);
                array.add(obj);
                logger.warn("??????????????????????????????????????????{}", array);
            } catch (com.KGitextpdf.text.DocumentException e) {
                logger.error("BdcDzzzSignCzServiceImpl:localSign:DocumentException");
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getCode(), null);
            } catch (IOException e) {
                logger.error("BdcDzzzSignCzServiceImpl:localSign:IOException");
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getCode(), null);
            } catch (GeneralSecurityException e) {
                logger.error("BdcDzzzSignCzServiceImpl:localSign:GeneralSecurityException");
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getCode(), null);
            } finally {
                if (hummer != null) {
                    hummer.close();
                }
            }
        }

        return bdcDzzzService.dzzzResponseSuccess("???????????????", array);
    }

    @Override
    public DzzzResponseModel digitalSign(String signData, String cert) {
        String resultPath = BdcDzzzPdfUtil.DZZZ_LOCAL_PATH + Constants.RESOURCES_PDF;
        X509Certificate x509Certificate = null;
        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            x509Certificate = (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(Hex.decode(cert)));
        } catch (CertificateException e) {
            logger.error("BdcDzzzSignCzServiceImpl:rewriteSign:CertificateException");
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getMsg(), null);
        }

        JSONArray array = JSON.parseArray(signData);
        if (null == array || array.size() == 0) {
            logger.error("????????????????????????????????????????????????");
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getMsg(), null);
        }
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String fileName = obj.getString("id");
            String sigKeepData = obj.getString("sigKeepData");
            String sigData = obj.getString("sigData");
            String filePath = resultPath + fileName;

            DigitalSignatureByKey digitalSignatureByKey = new DigitalSignatureByKey();
            digitalSignatureByKey.setSigKeepData(sigKeepData);
            digitalSignatureByKey.setChain(new X509Certificate[]{x509Certificate});
            sigData = digitalSignatureByKey.reversalContents(sigData);
            digitalSignatureByKey.setExtSignature(Hex.decode(sigData));

            try {
                digitalSignatureByKey.rewriteContentsPKCS7(filePath, null);
            } catch (Exception e) {
                logger.error("BdcDzzzSignCzServiceImpl:rewriteSign:Exception");
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getMsg(), null);
            }

        }

        return updateSignInfo(array, resultPath);
    }

    /**
     * @param array
     * @param resultPath
     * @return
     * @description ?????????????????????????????????????????????
     */
    private DzzzResponseModel updateSignInfo(JSONArray array, String resultPath) {
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String zzid = obj.getString("zzid");
            String fileName = obj.getString("id");
            String filePath = resultPath + fileName;
            String storageDtoId = bdcDzzzStorageService.sendFileCenter(resultPath, zzid, fileName, Constants.BDC_DZZZ);
            logger.warn("??????????????????????????????{},??????????????????{}", storageDtoId, resultPath);
//            String zzqzlj = bdcDzzzFileCenterService.sendFileCenter(filePath, zzid, fileName, Constants.BDC_DZZZ);
            if (StringUtils.isBlank(storageDtoId)) {
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_UPLOAD_FILE_CENTER_ERROR.getMsg(), null);
            }
            //??????????????????
            BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcDzqzService.queryBdcDzzzQzxx(zzid);
            BdcDzzzZzxxDO bdcDzzz = new BdcDzzzZzxxDO();
            bdcDzzz.setZzid(zzid);
            bdcDzzz.setQzzt(Constants.DZZZ_QZZT_YQZ);
            bdcDzzz.setZzqzr(bdcDzzzZzxxDO.getZzbfjg());
            bdcDzzz.setZzqzsj(DateUtil.now());
            //????????????????????????
            bdcDzzz.setZzqzlj(storageDtoId);
            bdcDzzzDzqzMapper.updateBdcQzxxByZzid(bdcDzzz);
            //?????????????????????????????????????????????????????????????????????????????????????????????????????????
            BdcDzzzYwxxDo bdcDzzzYwxxDo = bdcDzzzYwxxService.queryYwxxByZzid(zzid);
            if (Objects.nonNull(bdcDzzzYwxxDo) && StringUtils.isNotBlank(bdcDzzzYwxxDo.getYwh())) {
                byte[] data = PublicUtil.fileToByte(resultPath + fileName);
                BdcPdfDTO bdcPdfDTO = new BdcPdfDTO();
                BASE64Encoder base64Encoder = new BASE64Encoder();
                bdcPdfDTO.setBase64str(base64Encoder.encode(data));
                bdcPdfDTO.setSlbh(bdcDzzzYwxxDo.getYwh());
                bdcSlBysldjFeginService.updateByslSjcl(bdcPdfDTO);
            }
            PublicUtil.deleteFile(filePath);
        }

        return bdcDzzzService.dzzzResponseSuccess("?????????????????????", null);
    }

    private DzzzResponseModel checkSignInfo(DzzzSignPosition position, Seal seal) {
        if (null == position || (null == position.getPage() || null == position.getX() || null == position.getY())) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), "{\"info\":\"position is error\"}");
        }

        if (null == seal || (StringUtils.isBlank(seal.getKeysn()) || StringUtils.isBlank(seal.getSealName()) || StringUtils.isBlank(seal.getSealPwd()))) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), "{\"info\":\"seal is error\"}");
        }

        return bdcDzzzService.dzzzResponseSuccess(null);
    }

    @Override
    public DzzzResponseModel sign(BdcDzzzSignaTure bdcDzzzSignaTure) {
        if (null != bdcDzzzSignaTure) {
            String content = bdcDzzzSignaTure.getContent();
            BdcDzzzZzxx bdcDzzzZzxx = bdcDzzzSignaTure.getBdcDzzzZzxx();
            DzzzSignPosition position = bdcDzzzSignaTure.getPosition();
            Seal seal = bdcDzzzSignaTure.getSeal();
            if (StringUtils.isBlank(content)) {
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), "{\"info\":\"content is empty\"}");
            }

            // ??????????????????
            DzzzResponseModel checkSignInfoModel = checkSignInfo(position, seal);
            if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkSignInfoModel.getHead().getStatus())) {
                return checkSignInfoModel;
            }

            // ??????????????????
            List<String> resultList = new ArrayList<>(1);
            resultList.add("zzqzlj");
            DzzzResponseModel checkBdcDzzzZzxxModel = bdcDzzzZzxxService.checkBdcDzzzZzxx(bdcDzzzZzxx, resultList);
            if (StringUtils.equals(ResponseEnum.FALSE.getCode(), checkBdcDzzzZzxxModel.getHead().getStatus())) {
                return checkBdcDzzzZzxxModel;
            }

            // ?????????????????????????????????
            byte[] inputBytes = Base64Util.decodeBase64StrToByte(content);
            String fileName = bdcDzzzZzxx.getBdcqzh() + Constants.BDC_DZZZ_FORMAT_PDF;
            String zzwjlj = bdcDzzzFileCenterService.sendFileCenter(inputBytes, bdcDzzzZzxx.getZzid(), fileName, Constants.BDC_DZZZ);
            if (StringUtils.isBlank(zzwjlj)) {
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_UPLOAD_FILE_CENTER_ERROR.getCode(), null);
            }
            bdcDzzzZzxx.setZzwjlj(zzwjlj);

            // ????????????
            String metaExecutesJson = getMetaExecutesJson(position, seal);

            // ??????SOAP
            String soapRequestData = getSoapRequestData(content, metaExecutesJson);

            // ????????????
            byte[] signData = sign(soapRequestData);

            // ?????????????????????
            String savePdfName = bdcDzzzZzxx.getBdcqzh() + Constants.BDC_DZZZ_FILENAME_SIGNA + Constants.BDC_DZZZ_FORMAT_PDF;
            String zzqzlj = bdcDzzzFileCenterService.sendFileCenter(signData, bdcDzzzZzxx.getZzid(), savePdfName, Constants.BDC_DZZZ);
            if (StringUtils.isBlank(zzqzlj)) {
                return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_UPLOAD_FILE_CENTER_ERROR.getCode(), null);
            }
            bdcDzzzZzxx.setZzqzlj(zzqzlj);
            bdcDzzzSignConfigService.updateSignInfo(signData, bdcDzzzZzxx);

            // ??????????????????
            DzzzResponseModel insertBdcDzzzZzxxModel = bdcDzzzZzxxService.insertBdcDzzzZzxx(bdcDzzzZzxx);
            if (StringUtils.equals(ResponseEnum.FALSE.getCode(), insertBdcDzzzZzxxModel.getHead().getStatus())) {
                return insertBdcDzzzZzxxModel;
            }

            // ??????????????????
            Map<String, Object> map = new HashMap<>(2);
            map.put("zzbs", bdcDzzzZzxx.getZzbs());
            map.put("zzqzlj", bdcDzzzFileCenterService.encodeFidByZzwjlj(zzqzlj));
            return bdcDzzzService.dzzzResponseSuccess(map);
        }
        return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
    }


    /**
     * @param
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description ????????????????????????????????????
     */
    @Override
    public DzzzResponseModel dzzzCancellation(BdcDzzzZzxx bdcDzzzZzxx) {
        DzzzResponseModel beforeCheckModel = bdcDzzzZzzxConfigService.bdcDzzzZzzxBeforeCheck(bdcDzzzZzxx);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), beforeCheckModel.getHead().getStatus())) {
            return bdcDzzzService.dzzzResponseFalse("???????????????????????????????????????", null);
        }

        BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcDzzzDzqzMapper.queryBdcDzzzQzxxByZzbs(bdcDzzzZzxx.getZzbs());
        DzzzResponseModel infoCheckModel = bdcDzzzZzzxConfigService.bdcDzzzZzzxInfoCheck(bdcDzzzZzxxDO);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), infoCheckModel.getHead().getStatus())) {
            return bdcDzzzService.dzzzResponseFalse("??????????????????????????????????????????????????????", null);
        }

        // ?????????????????????
        byte[] signArr = bdcDzzzFileConfigService.downloadByZzwjlj(bdcDzzzZzxxDO.getZzqzlj());
        if (null == signArr) {
            return bdcDzzzService.dzzzResponseFalse("?????????????????????", null);
        }

        // ?????????????????????????????????????????????????????????
        String fileName = bdcDzzzZzxxDO.getBdcqzh() + Constants.BDC_DZZZ_FORMAT_PDF;
        String zzwjlj = bdcDzzzStorageService.sendFileCenter(signArr, bdcDzzzZzxxDO.getZzid(), fileName, Constants.BDC_DZZZ);
        if (StringUtils.isBlank(zzwjlj)) {
            return bdcDzzzService.dzzzResponseFalse("???????????????????????????", null);
        }

        DzzzPdf dzzzPdf = bdcDzzzZzxxPdfService.getDzzzPdf(bdcDzzzZzxxDO.getZzlxdm());
        if (null == dzzzPdf) {
            //????????????????????????????????????
            dzzzPdf = bdcDzzzZzxxPdfService.getDzzzPdf("11100000MB03271699001");
            if (Objects.isNull(dzzzPdf)) {
                return bdcDzzzService.dzzzResponseFalse("??????????????????", null);
            }
        }

        // ?????????????????????????????????
        byte[] markArr = bdcDzzzWaterMarkService.addWatermarkZzzx(signArr, dzzzPdf, bdcDzzzZzxx.getZzbgyy());
        if (null == markArr) {
            return bdcDzzzService.dzzzResponseFalse("?????????????????????", null);
        }

        // ????????????????????????????????????????????????????????????
        String savePdfName = bdcDzzzZzxxDO.getBdcqzh() + Constants.BDC_DZZZ_FILENAME_SIGNA + Constants.BDC_DZZZ_FORMAT_PDF;
        String zzqzlj = bdcDzzzStorageService.sendFileCenter(markArr, bdcDzzzZzxxDO.getZzid(), savePdfName, Constants.BDC_DZZZ);
        if (StringUtils.isBlank(zzqzlj)) {
            return bdcDzzzService.dzzzResponseFalse("???????????????????????????", null);
        }

        // ??????????????????
        //??????????????????
        DzzzResponseModel updateResponseModel = bdcDzzzZzzxConfigService.updateDzqzZxxxInfo(bdcDzzzZzxxDO.getZzid(), bdcDzzzZzxx.getZzbgyy(), bdcDzzzZzxx.getZzbgsj(), zzqzlj);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), updateResponseModel.getHead().getStatus())) {
            return bdcDzzzService.dzzzResponseFalse("??????????????????????????????", null);
        }

        return bdcDzzzService.dzzzResponseSuccess("???????????????", null);
    }


}
