package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDypzFeignService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.accept.core.service.BdcSlSjclService;
import cn.gtmap.realestate.accept.service.BdcRzdbService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.common.util.office.OfficeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/11/19.
 * @description  人证对比信息服务
 */
@Service
public class BdcRzdbServiceImpl implements BdcRzdbService {

    private static final Logger logger = LoggerFactory.getLogger(BdcRzdbServiceImpl.class);

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    BdcSlSjclService bdcSlSjclService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    private RedisUtils redisUtils;
    /**
     * PDF打印操作
     */
    @Autowired
    private PdfController pdfController;

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    @Autowired
    private BdcDypzFeignService bdcDypzFeignService;
    /**
     * accept-app应用地址
     */
    @Value("${url.acceptUrl:}")
    protected String acceptUrl;
    /**
     * 打印文件路径
     */
    @Value("${print.path:}")
    private String printPath;

    /**
     * 版本
     */
    @Value("${data.version:}")
    private String dataVersion;
    /**
     * 打印模块请求地址
     */
    private static final String PROJECT_PATH = "/rest/v1.0/print/";
    /**
     * 时间格式化格式
     */
    private static final DateTimeFormatter DATE_FMT_YMDHMS = DateTimeFormatter.ofPattern("yyyy年MM月dd日HH时mm分ss秒", Locale.CHINA);

    @Override
    public String scrzdbPdf(String slbh, String gzlslid) {
        // 1、获取打印数据
        List<BdcDysjDTO> printData = this.getPrintData(slbh,gzlslid);

        // 2、获取打印XML数据
        String xmlData = bdcPrintFeignService.printDatas(printData);
        logger.info("生成人证对比pdf对应XML数据：{}, 受理编号为：{}", xmlData, slbh);

        if(org.apache.commons.lang.StringUtils.isBlank(xmlData)) {
            return null;
        }

        // 3、生成PDF文件
        String pdfFilePath = this.generatePdfFile(xmlData);
        File pdfFile = new File(pdfFilePath);
        if(!pdfFile.exists()) {
            return null;
        }

        // 4、盐城添加水印，其他水印配置在数据源配置的打印字段
        if (CommonConstantUtils.SYSTEM_VERSION_YC.equals(dataVersion)){
            String watermark= Constants.YC_WATERMARK + DateUtils.formateTime(new Date(), DATE_FMT_YMDHMS);
            OfficeUtil.addWaterMarkToPdfFileWithFontSzie(pdfFilePath,  watermark, printPath + "/", 15);
        }

        // 5、上传pdf文件至大云
        try{
            uploadFile(gzlslid, pdfFilePath);
        }catch (IOException e){
            throw new AppException("上传PDF文件至大云中心出错，错误信息为：" + e.getMessage());
        }
        return pdfFilePath;
    }

    // 获取图片返回图片文件流
    @Override
    public void generateSuccessOrErrorImage(String type, HttpServletResponse response) {
        if(StringUtils.isNoneBlank(type)){
            String fileName = type + ".png";
            response.setContentType("image/png");
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
            File image = new File(printPath + fileName);
            try {
                FileUtils.copyFile(image, response.getOutputStream());
            } catch (IOException e) {
                logger.error("获取正确或失败图片失败:{}",e.getMessage());
            }
        }
    }

    /**
     * 获取打印数据库
     * @param slbh 受理编号
     * @return {List} 打印数据集合
     */
    private List<BdcDysjDTO> getPrintData(String slbh,String gzlslid){
        // 1、获取主表数据
        Map<String, String> parentData = new HashMap(2);
        parentData.put("SLBH",slbh);

        // 2、获取子表数据
        Multimap<String, List> childData = ArrayListMultimap.create();
        // 2.1 查询结果子表数据
        List<Map<String, Object>> rzdbxxList = this.queryRzdbxx(slbh,gzlslid);
        childData.put("ZT_dbxx", rzdbxxList);

        // 3、设置打印模板格式
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>(1);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj(JSONObject.toJSONString(parentData));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(childData));
        // 如果配置了rzbdjg的数据源，读数据源
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx("rzdbjg");
        List<BdcDysjPzDO> bdcDysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
        if (CollectionUtils.isEmpty(bdcDysjPzDOList)){
            // 盐城的模板
            bdcDysjDTO.setDyzd(Constants.RZDB_XML);
        }else{
            // 读数据源获取模板
            bdcDysjDTO.setDyzd(bdcDysjPzDOList.get(0).getDyzd());
        }
        bdcDysjDTOList.add(bdcDysjDTO);
        return bdcDysjDTOList;
    }

    /**
     * 根据受理编号获取人证对比信息
     * @param slbh
     * @return
     */
    private List<Map<String,Object>> queryRzdbxx(String slbh,String gzlslid){
        // 调用exchange接口获取人证比对信息
        Map<String ,String> param = new HashMap(4);
        param.put("slbh", slbh);
        Object response = this.exchangeInterfaceFeignService.requestInterface("rzdbcx_cz", param);
        if(Objects.isNull(response)){
            throw new NullPointerException("查询对比信息接口错误，返回值为空。");
        }
        Map<String, String> resultMap = (Map<String, String>) response;
        List<Map<String, Object>> resultList = new ArrayList<>(10);
        if("0".equals(resultMap.get("code"))) {
            List<Map> mapList = new Gson().fromJson(JSON.toJSONString(resultMap.get("qlrxxList")), new TypeToken<List<Map>>() {}.getType());
            for (Map map : mapList) {
                String sfzzpName = map.get("qlrmc") + "_" + map.get("zjh") + "_身份证头像照片";
                String xczpName = map.get("qlrmc") + "_" + map.get("zjh") + "_现场照片";
                map.put("sfzzp", getImageFile((String) map.get("sfzzp"), gzlslid, sfzzpName));
                map.put("xczp", getImageFile((String) map.get("xczp"), gzlslid, xczpName));
                boolean pass = (boolean) Optional.ofNullable(map.get("pass")).orElse(false);
                if (pass) {
                    map.put("passurl", acceptUrl + PROJECT_PATH + "image/success");
                } else {
                    map.put("passurl", acceptUrl + PROJECT_PATH + "image/error");
                }
                resultList.add(toUpperKey(map));
            }
        }
        return resultList;
    }

    /**
     * Map转换将Key转成大写
     */
    public Map<String, Object> toUpperKey(Map<String, Object> map) {
        Map<String, Object> resultMap = new HashMap<>();
        if (map == null || map.isEmpty()) {
            return resultMap;
        }
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            String newKey = key.toUpperCase();
            resultMap.put(newKey, map.get(key));
        }
        return resultMap;
    }

    /**
     * 将图片的base64字符串转换成文件流下载地址
     * @param base64str 图片Base64码
     * @return {String} 图片的文件流下载地址
     */
    private String getImageFile(String base64str,String gzlslid,String fjmc){
        if(StringUtils.isNotBlank(base64str)){
            if (CommonConstantUtils.SYSTEM_VERSION_YC.equals(dataVersion)){
                String key = UUIDGenerator.generate16();
                redisUtils.addStringValue(key, base64str.replaceAll("\r|\n*", ""), 360);
                return acceptUrl + PROJECT_PATH + "zp/PNG/" + String.valueOf(1F)+ "/" + key;
            }else{
                // 上传图片到文件夹
                try {
                    String fjid = bdcUploadFileUtils.uploadBase64File(CommonConstantUtils.BASE64_QZ_IMAGE + base64str, gzlslid, "人证对比",fjmc , ".jpg");
                    StorageDto storageDto = storageClient.findById(fjid);
                    return storageDto.getDownUrl();
                }catch (Exception e){
                    logger.error("人证对比的证件头像附件上传失败",e);
                }
            }
        }
        return "";
    }

    /**
     * 生成PDF文件
     * @param xmlData XML数据
     * @return {String} PDF文件路径
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    private String generatePdfFile(String xmlData) {
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(printPath + "rzdb.docx");
        pdfExportDTO.setFileName("人证对比结果");
        pdfExportDTO.setXmlData(xmlData);
        String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);
        logger.info("人证对比结果pdf文件路径：{}", pdfFilePath);
        return pdfFilePath;
    }

    /**
     * 上传PDF文件至大云中心
     * @param gzlslid    工作流实例ID
     * @param pdfFilePath  pdf文件地址
     * @throws IOException
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    private void uploadFile(String gzlslid, String pdfFilePath) throws IOException {
        File pdfFile = new File(pdfFilePath);
        if(!pdfFile.exists()){
            throw new AppException("上传pdf文件至大云失败，原因：文件不存在，文件地址:"+pdfFilePath );
        }


        String pdfBase64Str = "data:application/pdf;base64," + Base64Utils.encodeByteToBase64Str(FileUtils.readFileToByteArray(pdfFile));
        BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "", "人证对比", "人证对比", CommonConstantUtils.WJHZ_PDF);
        bdcPdfDTO.setBase64str(pdfBase64Str);
        bdcUploadFileUtils.uploadBase64File(bdcPdfDTO);
    }
}
