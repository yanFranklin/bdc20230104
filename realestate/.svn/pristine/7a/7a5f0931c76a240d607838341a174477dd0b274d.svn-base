package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDaCxLog;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.exchange.BdcDaCxLogDTO;
import cn.gtmap.realestate.common.core.enums.DacxInterfaceTypeEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.exchange.BdcDaCxFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcBzbZmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcFczmFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 2019/1/17,1.0
 * @description 综合查询页面打印功能
 */
@Controller
@RequestMapping("/rest/v1.0/zszm/print")
public class BdcZszmPrintController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZszmPrintController.class);

    @Autowired
    private BdcBzbZmFeignService bdcBzbZmFeignService;

    @Autowired
    private PdfController pdfController;

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private BdcDaCxFeignService bdcDaCxFeignService;

    @Autowired
    private BdcFczmFeignService bdcFczmFeignService;

    @Autowired
    private BdcFczmController bdcFczmController;

    private final static String TIEM_FORMAT = "yyMMddHHmmss";

    private final static String ZSZM_BUTTON_PRING_FLAG_CODE = "00";

    @Value("${inquiry.ui.dacx.ewm.url:http://192.168.2.206:8598/realestate-inquiry-ui/rest/v1.0/zszm/print/ewm?ewmnr=}")
    private String ewmPictureUrl;

    private final static Map<String, String> interfacCodeMap = new HashMap<>();

    static {
        interfacCodeMap.put(DacxInterfaceTypeEnum.YFWFZM.interfaceType, DacxInterfaceTypeEnum.YFWFZM.code);
        interfacCodeMap.put(DacxInterfaceTypeEnum.YFWFZMLS.interfaceType, DacxInterfaceTypeEnum.YFWFZMLS.code);
        interfacCodeMap.put(DacxInterfaceTypeEnum.QSZM.interfaceType, DacxInterfaceTypeEnum.QSZM.code);
        interfacCodeMap.put(DacxInterfaceTypeEnum.YWTDXXZM.interfaceType, DacxInterfaceTypeEnum.YWTDXXZM.code);
    }

    @ResponseBody
    @GetMapping("/dacx/pdf")
    public void printPdf(HttpServletResponse response, @RequestParam(name = "dylx") String dylx, @RequestParam(name = "fileName") String fileName, @RequestParam(name = "modelWordUrl") String modelWordUrl, @RequestParam(name = "bdclx", required = false) String bdclx, @RequestParam(name = "xmlKey") String xmlKey, @RequestParam(name = "dacxFlag", required = false) boolean dacxFlag) {
        //参数校验
        if (StringUtils.isBlank(modelWordUrl) || StringUtils.isBlank(xmlKey) || StringUtils.isBlank(fileName) || StringUtils.isBlank(dylx)) {
            throw new RuntimeException("综合查询页面打印缺少必填参数");
        }
        String userName = userManagerUtils.getCurrentUserName();
        LOGGER.info("获取当前用户:{}", userName);
        //保存档案查询日志信息
        //保存日志记录
        String queryId = createQueryId(interfacCodeMap.getOrDefault(dylx, ZSZM_BUTTON_PRING_FLAG_CODE));
        //获取xml数据
        String xml = null;
        if (dylx.contains("qszm")) {
            if (StringUtils.isNotBlank(bdclx)) {
                xml = bdcBzbZmFeignService.getPrintXmlOfQszm(xmlKey, bdclx);
            }
        } else if (dylx.contains("yfwfzm")) {
            xml = bdcFczmController.getPrintXmlOfZfxx(xmlKey);
        } else if (dylx.contains("ywtdxxzm")) {
            xml = bdcFczmFeignService.getPrintXmlOfYwtdxxzm(xmlKey);
        }
        if (StringUtils.isBlank(xml)) {
            throw new RuntimeException("获取数据源失败！");
        }
        try {
            xml = initXmlStrWithQueryId(queryId, xml);
        } catch (Exception e) {
            LOGGER.error("添加queryId二维码失败", e);
            throw new RuntimeException("添加queryId二维码失败");
        }
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(modelWordUrl);
        pdfExportDTO.setFileName(fileName);
        pdfExportDTO.setXmlData(xml);
        //pdf保存大云供档案查询验证
        if (dacxFlag) {
            OutputStream outputStream = null;
            String pdfFile = pdfController.generatePdfFile(pdfExportDTO);
            try {
                StorageDto storageDto = storageClient.createRootFolder("clientId", UUIDGenerator.generate(), "档案查询留存", null);
                if (storageDto != null && StringUtils.isNotBlank(pdfFile)) {
                    //上传文件
                    File file = new File(pdfFile);
                    if (file.exists()) {
                        outputStream = response.getOutputStream();
                        StorageDto fileStorage = uploadPdf(queryId, outputStream, storageDto, file);
                        if (fileStorage == null) {
                            LOGGER.info("上传pdf文件失败");
                            throw new AppException("上传pdf文件失败，处理终止");
                        }
                        CommonResponse insertDaCxLogResponse = insertDaCxLog(queryId, fileStorage, storageDto.getId());
                        if (insertDaCxLogResponse == null || !insertDaCxLogResponse.isSuccess()) {
                            LOGGER.info("保存日志记录失败:{}", JSON.toJSONString(insertDaCxLogResponse));
                            throw new AppException("保存日志记录失败，处理终止");
                        }
//                        //更新日志记录
//                        CommonResponse updateDaCxLogResponse = updateDaCxLog(userName, fileStorage,storageDto.getId());
//                        if (insertDaCxLogResponse == null || !insertDaCxLogResponse.isSuccess()) {
//                            LOGGER.info("更新日志记录失败:{}", JSON.toJSONString(insertDaCxLogResponse));
//                            throw new AppException("更新日志记录失败，处理终止");
//                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("系统导出PDF报错：{}", e.toString());
                throw new AppException("系统导出PDF报错，处理终止");
            } finally {
                IOUtils.closeQuietly(outputStream);
                if (null != pdfFile) {
                    File file = new File(pdfFile);
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        } else {
            pdfController.exportPdf(response, pdfExportDTO);
        }
    }

    private String initXmlStrWithQueryId(String queryId, String xml) {
        // 添加二维码地址
        return xml.replaceAll("EWMURL", ewmPictureUrl + queryId);
    }

    private CommonResponse insertDaCxLog(String queryId, StorageDto fileStorage, String spaceId) {
        BdcDaCxLog bdcDaCxLog = BdcDaCxLog.BdcDaCxLogBuilder.aBdcDaCxLog().withId(queryId)
                .withCjsj(new Date())
                .withDacxloglx("2")
                .withDacxlogzt("0")
                .withForeign(fileStorage.getId())
                .withPdfSpaceId(spaceId)
                .withDags("BDCDJ").build();
        return bdcDaCxFeignService.saveBdcDaCxLog(bdcDaCxLog);
    }

    private StorageDto uploadPdf(String userName, OutputStream outputStream, StorageDto storageDto, File file) throws IOException {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(storageDto.getId());
        multipartDto.setClientId(storageDto.getClientId());
        multipartDto.setData(FileUtils.readFileToByteArray(file));
        multipartDto.setOwner(userName);
        multipartDto.setContentType("application/pdf");
        multipartDto.setOriginalFilename(file.getName());
        multipartDto.setName(storageDto.getName());
        storageDto = storageClient.multipartUpload(multipartDto);
        FileUtils.copyFile(file, outputStream);
        return storageDto;
    }

    private static String createQueryId(String dags) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIEM_FORMAT);
        StringBuffer stringBuffer = new StringBuffer();
        return stringBuffer.append(dags).append(UUIDGenerator.generate6()).append(simpleDateFormat.format(System.currentTimeMillis())).toString();
    }

    /**
     * @param ewmnr 二维码内容
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 获取二维码信息
     */
    @GetMapping(value = "/ewm")
    void ewm(@RequestParam(name = "ewmnr") String ewmnr, HttpServletResponse response) {
        LOGGER.info(ewmnr);
        response.setContentType("image/jpg;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment; filename=test.jpg");
        QRcodeUtils.encoderQRCode(ewmnr, null, response);
    }

    @ResponseBody
    @GetMapping("/dacx/{key}/{dylx}/xml")
    public String xmlDataForDacx(@PathVariable(name = "dylx") String dylx, @RequestParam(name = "bdclx", required = false) String bdclx, @PathVariable(name = "key") String key) {
        //保存日志记录
        String queryId = createQueryId(interfacCodeMap.getOrDefault(dylx, ZSZM_BUTTON_PRING_FLAG_CODE));
        //获取xml数据
        String xml = null;
        if (dylx.contains("qszm")) {
            if (StringUtils.isNotBlank(bdclx)) {
                xml = bdcBzbZmFeignService.getPrintXmlOfQszm(key, bdclx);
            }
        } else if (dylx.contains("yfwfzm")) {
            xml = bdcFczmController.getPrintXmlOfZfxx(key);
        } else if (dylx.contains("ywtdxxzm")) {
            xml = bdcFczmFeignService.getPrintXmlOfYwtdxxzm(key);
        }
        if (StringUtils.isBlank(xml)) {
            throw new RuntimeException("获取数据源失败！");
        }
        try {
            xml = initXmlStrWithQueryId(queryId, xml);
            xml = xml.replaceAll("&", "&amp;");
        } catch (Exception e) {
            LOGGER.error("添加queryId二维码失败", e);
            throw new RuntimeException("添加queryId二维码失败");
        }
        String dataJson = XmlEntityCommonConvertUtil.xmlToJson(xml);
        JSONArray dataJsonObject = deatWithXmlJSON(dataJson);
        if (dataJsonObject == null) {
            throw new AppException("转换json失败，处理终止");
        }
        BdcDaCxLogDTO bdcDaCxLog = BdcDaCxLogDTO.BdcDaCxLogDTOBuilder.aBdcDaCxLogDTO().withId(queryId)
                .withCjsj(new Date())
                .withDacxloglx("1")
                .withDacxlogzt("0")
                .withForeign(UUIDGenerator.generate16())
//                .withJkly("0")
                .withPdfJsonStr(dataJsonObject.toJSONString())
                .withUrl(CommonUtil.getCurrentRequestPath())
                .withDags("BDCDJ").build();
        CommonResponse commonResponse = bdcDaCxFeignService.saveBdcDacxLogWithPdfJsonStr(bdcDaCxLog);
        if (commonResponse == null || !commonResponse.isSuccess()) {
            LOGGER.info("保存日志记录失败:{}", JSON.toJSONString(commonResponse));
            throw new AppException("保存日志记录失败，处理终止");
        }
        return xml;
    }

    /**
     * 转换data数据源中data部分内容
     *
     * @param dataJSON
     * @param keys
     * @return
     */
    private static JSONObject fr3XmlStrData2JsonObject(JSONObject dataJSON, String keys) {
        JSONArray targetJsonArray = null;
        JSONObject tempTargetObject = null;
        JSONObject newJsonObject = new JSONObject();
        if (StringUtils.isNotBlank(keys)) {
            if (keys.contains(",")) {
                String[] split = keys.split(",");
                for (int i = 0; i < split.length; i++) {
                    if (i == split.length - 1) {
                        Object deep = dataJSON.get(split[i]);
                        if (deep instanceof JSONArray) {
                            targetJsonArray = (JSONArray) deep;
                        } else if (deep instanceof JSONObject) {
                            tempTargetObject = (JSONObject) deep;
                        }
                        break;
                    }
                    if (dataJSON.getJSONObject(split[i]) != null) {
                        dataJSON = dataJSON.getJSONObject(split[i]);
                    } else {
                        return null;
                    }
                }
            } else {
                targetJsonArray = dataJSON.getJSONArray(keys);
            }
            if (targetJsonArray != null) {
                for (int i = 0; i < targetJsonArray.size(); i++) {
                    JSONObject jsonObject = targetJsonArray.getJSONObject(i);
                    if (jsonObject.containsKey("$") && !"—".equals(jsonObject.getString("$"))) {
                        newJsonObject.put(jsonObject.getString("name"), jsonObject.get("$"));
                    }
                }
            } else if (tempTargetObject != null) {
                if (tempTargetObject.containsKey("$") && !"—".equals(tempTargetObject.getString("$"))) {
                    newJsonObject.put(tempTargetObject.getString("name"), tempTargetObject.get("$"));
                }
            }
        }
        return newJsonObject;
    }

    /**
     * 转换xml数据源中detail数据内容
     *
     * @param dataJSON
     * @param keys
     * @param targetJsonObject
     */
    private static void fr3XmlStrDetail2JsonObject(JSONObject dataJSON, String keys, JSONObject targetJsonObject) {
        JSONArray targetJsonArray = null;
        JSONObject tempTargetObject = null;
        JSONObject newJsonObject = new JSONObject();
        if (StringUtils.isNotBlank(keys)) {
            if (keys.contains(",")) {
                String[] split = keys.split(",");
                for (int i = 0; i < split.length; i++) {
                    if (i == split.length - 1) {
                        Object deep = dataJSON.get(split[i]);
                        if (deep instanceof JSONArray) {
                            targetJsonArray = (JSONArray) deep;
                        } else if (deep instanceof JSONObject) {
                            tempTargetObject = (JSONObject) deep;
                        }
                        break;
                    }
                    if (dataJSON.getJSONObject(split[i]) != null) {
                        dataJSON = dataJSON.getJSONObject(split[i]);
                    } else {
                        return;
                    }
                }
            } else {
                targetJsonArray = dataJSON.getJSONArray(keys);
            }
            if (targetJsonArray != null) {
                for (int i = 0; i < targetJsonArray.size(); i++) {
                    dealWithXmlDetails(targetJsonArray.getJSONObject(i), newJsonObject);
                }
            } else if (tempTargetObject != null) {
                dealWithXmlDetails(tempTargetObject, newJsonObject);
            }
        }
        targetJsonObject.putAll(newJsonObject);
    }

    private static void dealWithXmlDetails(JSONObject jsonObject, JSONObject newJsonObject) {
        if (jsonObject.containsKey("ID") && jsonObject.containsKey("row")) {
            String keyName = jsonObject.getString("ID");
            JSONArray tempJsonArray = new JSONArray();
            Object rowObject = jsonObject.get("row");
            if (rowObject instanceof JSONArray) {
                JSONArray row = (JSONArray) rowObject;
                if (row != null && row.size() > 0) {
                    for (int j = 0; j < row.size(); j++) {
                        JSONObject tempJsonObject = new JSONObject();
                        if ("—".equals(row.getJSONObject(j).getString("ID"))) {
                            continue;
                        }
                        tempJsonObject.put("ID", row.getJSONObject(j).get("ID"));
                        JSONArray data = row.getJSONObject(j).getJSONArray("data");
                        for (int k = 0; k < data.size(); k++) {
                            if (data.getJSONObject(k).containsKey("$")) {
                                tempJsonObject.put(data.getJSONObject(k).getString("name"), data.getJSONObject(k).get("$"));
                            }
                        }
                        tempJsonArray.add(tempJsonObject);
                    }
                }
            } else if (rowObject instanceof JSONObject) {
                JSONObject row = (JSONObject) rowObject;
                JSONObject tempJsonObject = new JSONObject();
                if (row.containsKey("ID") && !"—".equals(row.getString("ID"))) {
                    tempJsonObject.put("ID", row.get("ID"));
                    JSONArray data = row.getJSONArray("data");
                    for (int k = 0; k < data.size(); k++) {
                        if (data.getJSONObject(k).containsKey("$")) {
                            tempJsonObject.put(data.getJSONObject(k).getString("name"), data.getJSONObject(k).get("$"));
                        }
                    }
                    tempJsonArray.add(tempJsonObject);
                }
            }

            if (tempJsonArray.size() > 0) {
                newJsonObject.put(keyName, tempJsonArray);
            }
        }
    }

    /**
     * 处理xmlJsonStr
     *
     * @param dataJson
     * @return
     */
    private static JSONArray deatWithXmlJSON(String dataJson) {
        JSONObject jsonObject = JSON.parseObject(dataJson.replaceAll("\n", "").replaceAll("\t", "").replaceAll("@", ""));
        JSONArray target = new JSONArray();
        if (dataJson.contains("page")) {
            JSONObject fetchdatas = jsonObject.getJSONObject("fetchdatas");
            JSONArray page = fetchdatas.getJSONArray("page");
            if (page != null && page.size() > 0) {
                for (int i = 0; i < page.size(); i++) {
                    JSONObject temp = fr3XmlStrData2JsonObject(page.getJSONObject(i), "datas,data");
                    if (temp == null) {
                        temp = new JSONObject();
                    }
                    fr3XmlStrDetail2JsonObject(page.getJSONObject(i), "detail", temp);
                    target.add(temp);
                }
            }
        } else {
            JSONObject temp = fr3XmlStrData2JsonObject(jsonObject, "fetchdatas,datas,data");
            if (temp == null) {
                temp = new JSONObject();
            }
            fr3XmlStrDetail2JsonObject(jsonObject, "fetchdatas,detail", temp);
            target.add(temp);
        }
        if (target.size() > 0) {
            return target;
        }
        return null;
    }

}
