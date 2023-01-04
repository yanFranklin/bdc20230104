package cn.gtmap.realestate.inquiry.service.changzhou.impl;

import cn.gtmap.realestate.common.core.domain.BdcCdBlxxDO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcCxzmdPdfDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcFwqlDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZwbInfoDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCxzmdPdfQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFwqlQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFwqlQlrQO;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlCdBlxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZfxxCxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcCxzmdPdfDataVO;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcCxzmdPdfVO;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcStfzmCxPdfVO;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcdjbcxPdfVO;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.QRcodeUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.inquiry.service.changzhou.BdcXxcxService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/8/25 15:11
 */
@Service
public class BdcXxcxServiceImpl implements BdcXxcxService {
    private static final Logger logger = LoggerFactory.getLogger(BdcXxcxServiceImpl.class);

    @Value("${url.acceptUrl:}")
    protected String acceptUrl;
    private static final String PROJECT_PATH = "/rest/v1.0/print/";
    /**
     * 常州大数据局不动产信息查询接口
     */
    @Value("${changzhou.cxzmdpdf.url:}")
    private String cxzmdpdfUrl;
    /**
     * 打印文件路径
     */
    @Value("${print.path:}")
    private String printPath;
    /**
     * 汇总证明单处理
     */
    @Autowired
    private BdcXxcxHzzmdService hzzmdService;
    /**
     * 明细证明单处理
     */
    @Autowired
    private BdcXxcxMxzmdService mxzmdService;

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    BdcSlCdBlxxFeignService bdcSlCdBlxxFeignService;

    @Autowired
    BdcZfxxCxFeignService bdcZfxxCxFeignService;
    /** 打印服务 */
    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;
    /**
     * PDF打印操作
     */
    @Autowired
    private PdfController pdfController;
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @return {BdcCxzmdPdfVO} 返回PDF数据
     * @description 查询权利人房产证明等内容并生成对应PDF文件
     */
    @Override
    public BdcCxzmdPdfVO generateCxzmdPdf(BdcCxzmdPdfQO bdcCxzmdPdfQO) {
        List<BdcCxzmdPdfDataVO> bdcCxzmdPdfDataVOList = new ArrayList<>();

        try {
            if(null == bdcCxzmdPdfQO || StringUtils.isAnyBlank(bdcCxzmdPdfQO.getXm(), bdcCxzmdPdfQO.getSfzh())) {
                logger.error("常州生成房产证明PDF处理 客户端未传递查询参数：姓名、身份证号，处理中止");
                return new BdcCxzmdPdfVO("1", "客户端未传递查询参数：姓名、身份证号", null);
            }

            BdcZwbInfoDTO bdcZwbInfoDTO = this.bdcCxzmdPdf(bdcCxzmdPdfQO);
            if (Objects.isNull(bdcZwbInfoDTO)) {
                logger.error("常州生成房产证明PDF处理 从大数据局未获取到房产数据，处理中止");
                return new BdcCxzmdPdfVO("1", "从大数据局接口未获取到房产数据", null);
            }

            // 获取汇总证明单PDF
            BdcCxzmdPdfDataVO cxzmdPdfData = hzzmdService.cxzmdPdf(bdcCxzmdPdfQO, bdcZwbInfoDTO,false);
            bdcCxzmdPdfDataVOList.add(cxzmdPdfData);

            // 获取证明单明细pdf
            List<BdcCxzmdPdfDTO> bdcCxzmdPdfDTOList = bdcZwbInfoDTO.getBdcZwbInfo();
            if (CollectionUtils.isEmpty(bdcCxzmdPdfDTOList)) {
                logger.info("未查询到详细的房产数据，不生成详细房产证明PDF：{}", JSON.toJSONString(bdcCxzmdPdfQO));
                BdcCxzmdPdfDataVO bdcqzPdfVO = new BdcCxzmdPdfDataVO("cxmx", null);
                bdcCxzmdPdfDataVOList.add(bdcqzPdfVO);
            } else {
                bdcCxzmdPdfDataVOList.addAll(mxzmdService.generateMxzmdPdf(bdcCxzmdPdfDTOList, bdcCxzmdPdfQO));
            }

            return new BdcCxzmdPdfVO("0", "成功", bdcCxzmdPdfDataVOList);
        }
        catch (Exception e) {
            logger.error("常州生成房产证明PDF处理出错：{}", e);
            return new BdcCxzmdPdfVO("1", "服务器内部错误", null);
        }
    }

    @Override
    public BdcCxzmdPdfVO generateCxzmdHzPdf(BdcCxzmdPdfQO bdcCxzmdPdfQO) {
        List<BdcCxzmdPdfDataVO> bdcCxzmdPdfDataVOList = new ArrayList<>();

        try {
            if(null == bdcCxzmdPdfQO || StringUtils.isAnyBlank(bdcCxzmdPdfQO.getXm(), bdcCxzmdPdfQO.getSfzh())) {
                logger.error("常州生成房产证明PDF处理 客户端未传递查询参数：姓名、身份证号，处理中止");
                return new BdcCxzmdPdfVO("1", "客户端未传递查询参数：姓名、身份证号", null);
            }

            BdcZwbInfoDTO bdcZwbInfoDTO = this.bdcCxzmdPdf(bdcCxzmdPdfQO);
            if (Objects.isNull(bdcZwbInfoDTO)) {
                logger.error("常州生成房产证明PDF处理 从大数据局未获取到房产数据，处理中止");
                return new BdcCxzmdPdfVO("1", "从大数据局接口未获取到房产数据", null);
            }

            // 获取汇总证明单PDF
            BdcCxzmdPdfDataVO cxzmdPdfData = hzzmdService.cxzmdPdf(bdcCxzmdPdfQO, bdcZwbInfoDTO,true);
            bdcCxzmdPdfDataVOList.add(cxzmdPdfData);
            return new BdcCxzmdPdfVO("0", "成功", bdcCxzmdPdfDataVOList);
        }
        catch (Exception e) {
            logger.error("常州生成房产证明汇总PDF处理出错：{}", e);
            return new BdcCxzmdPdfVO("1", "服务器内部错误", null);
        }
    }

    @Override
    public BdcCxzmdPdfVO generateCxzmdMxPdf(BdcCxzmdPdfQO bdcCxzmdPdfQO) {
        try {
            if(null == bdcCxzmdPdfQO || StringUtils.isAnyBlank(bdcCxzmdPdfQO.getXm(), bdcCxzmdPdfQO.getSfzh())) {
                logger.error("常州生成房产证明PDF处理 客户端未传递查询参数：姓名、身份证号，处理中止");
                return new BdcCxzmdPdfVO("1", "客户端未传递查询参数：姓名、身份证号", null);
            }

            BdcZwbInfoDTO bdcZwbInfoDTO = this.bdcCxzmdPdf(bdcCxzmdPdfQO);
            if (Objects.isNull(bdcZwbInfoDTO)) {
                logger.error("常州生成房产证明PDF处理 从大数据局未获取到房产数据，处理中止");
                return new BdcCxzmdPdfVO("1", "从大数据局接口未获取到房产数据", null);
            }

            // 获取证明单明细pdf
            List<BdcCxzmdPdfDTO> bdcCxzmdPdfDTOList = bdcZwbInfoDTO.getBdcZwbInfo();
            List<BdcCxzmdPdfDataVO> bdcCxzmdPdfDataVOList = new ArrayList<>();
            if (CollectionUtils.isEmpty(bdcCxzmdPdfDTOList)) {
                logger.info("未查询到详细的房产数据，不生成详细房产证明PDF：{}", JSON.toJSONString(bdcCxzmdPdfQO));
                BdcCxzmdPdfDataVO bdcqzPdfVO = new BdcCxzmdPdfDataVO("cxmx", null);
                bdcCxzmdPdfDataVOList.add(bdcqzPdfVO);
            } else {
                bdcCxzmdPdfDataVOList.addAll(mxzmdService.generateMxzmdPdf(bdcCxzmdPdfDTOList, bdcCxzmdPdfQO));
            }

            return new BdcCxzmdPdfVO("0", "成功", bdcCxzmdPdfDataVOList);
        }
        catch (Exception e) {
            logger.error("常州生成房产证明明细PDF处理出错", e);
            return new BdcCxzmdPdfVO("1", "服务器内部错误", null);
        }
    }

    @Override
    public BdcdjbcxPdfVO generateBdcdjbcxPdf(BdcCxzmdPdfQO bdcCxzmdPdfQO) {
        try {
            if (null == bdcCxzmdPdfQO || StringUtils.isAnyBlank(bdcCxzmdPdfQO.getXm(), bdcCxzmdPdfQO.getSfzh(), bdcCxzmdPdfQO.getBdcqzh())) {
                logger.error("常州不动产登记簿查询PDF接口 客户端未传递查询参数：姓名、身份证号、不动产权证号，处理中止");
                return new BdcdjbcxPdfVO("0", "查询失败，客户端未传递查询参数：姓名、身份证号", null);
            }
            String dylx;
            String mainPath = acceptUrl + PROJECT_PATH;
            if (StringUtils.indexOf(bdcCxzmdPdfQO.getBdcqzh(), CommonConstantUtils.BDC_BDCQZH_BS) > -1) {
                dylx = CommonConstantUtils.DYLX_PDFJK_CDXX_BDCQZ;

            } else {
                dylx = CommonConstantUtils.DYLX_PDFJK_CDXX_FCTDZ;
            }
            // 封装参数
            Map<String, List<Map>> paramMap = new HashMap<>(1);
            List<Map> bdcdyhListMap = new ArrayList<>(1);
            Map<String, Object> mapTemp = new HashMap<>(1);
            mapTemp.put("xm", bdcCxzmdPdfQO.getXm());
            mapTemp.put("zjh", bdcCxzmdPdfQO.getSfzh());
            mapTemp.put("bdcqzh", bdcCxzmdPdfQO.getBdcqzh());
            mapTemp.put("cxly", bdcCxzmdPdfQO.getFs());
            mapTemp.put("jycx",mainPath + "jycx/cdxxscpdfJycxImage?xm=" + URLEncoder.encode(bdcCxzmdPdfQO.getXm(),"UTF-8") +"&zjh=" +URLEncoder.encode(bdcCxzmdPdfQO.getSfzh(),"UTF-8") + "&dylx=" + URLEncoder.encode(dylx,"UTF-8"));
            bdcdyhListMap.add(mapTemp);
            paramMap.put(dylx, bdcdyhListMap);
            String xmlData = bdcPrintFeignService.print(paramMap);
            //生成PDF
            OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
            pdfExportDTO.setModelName(printPath + dylx + CommonConstantUtils.WJHZ_DOCX);
            String filename = bdcCxzmdPdfQO.getXm() + "不动产登记簿查询";
            pdfExportDTO.setFileName(filename);
            pdfExportDTO.setXmlData(xmlData);
            String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);
            File pdfFile = new File(pdfFilePath);
            if (!pdfFile.exists()) {
                logger.error("常州不动产登记簿查询PDF接口异常,PDF临时文件不存在，权利人{}、证件号{}、查询方式{}", bdcCxzmdPdfQO.getXm(), bdcCxzmdPdfQO.getSfzh(),bdcCxzmdPdfQO.getFs());
                return new BdcdjbcxPdfVO("0", "查询失败，服务器内部错误，生成PDF失败", null);
            }
            BdcdjbcxPdfVO bdcdjbcxPdfVO = new BdcdjbcxPdfVO("1", "查询成功", Base64Utils.getPDFBinary(pdfFile));
            return bdcdjbcxPdfVO;
        } catch (Exception e) {
            logger.error("常州不动产登记簿查询PDF接口异常，权利人{}、证件号{},异常信息：", bdcCxzmdPdfQO.getXm(), bdcCxzmdPdfQO.getSfzh(), e);
            return new BdcdjbcxPdfVO("0", "查询失败，服务器内部错误", null);
        }
    }

    @Override
    public BdcStfzmCxPdfVO generateStfzmPdf(BdcCxzmdPdfQO bdcCxzmdPdfQO) {
        try {
            if (null == bdcCxzmdPdfQO || StringUtils.isAnyBlank(bdcCxzmdPdfQO.getXm(), bdcCxzmdPdfQO.getSfzh())) {
                logger.error("常州房屋套次查询PDF接口 未传递查询参数：姓名、证件号，处理中止");
                return new BdcStfzmCxPdfVO("0", "查询失败，客户端未传递查询参数：姓名、身份证号", null);
            }
            String mainPath = acceptUrl + PROJECT_PATH;
            //获取打印类型
            String dylx = CommonConstantUtils.DYLX_PDFJK_STFZM;
            BdcFwqlQO bdcFwqlQO = new BdcFwqlQO();
            List<BdcFwqlQlrQO> bdcQlrQOList = new ArrayList<>();
            BdcFwqlQlrQO fwqlQlrQO = new BdcFwqlQlrQO();
            fwqlQlrQO.setQlrmc(bdcCxzmdPdfQO.getXm());
            fwqlQlrQO.setZjh(bdcCxzmdPdfQO.getSfzh());
            bdcQlrQOList.add(fwqlQlrQO);
            bdcFwqlQO.setQlrxx(bdcQlrQOList);
            List<BdcFwqlDTO> bdcFwqlList = bdcZfxxCxFeignService.listBdcFwqlDTO(bdcFwqlQO);
            BdcCdBlxxDO bdcCdBlxxDO = new BdcCdBlxxDO();
            bdcCdBlxxDO.setSqrmc(bdcCxzmdPdfQO.getXm());
            bdcCdBlxxDO.setSqrzjh(bdcCxzmdPdfQO.getSfzh());
            BdcCdBlxxDO bdcCdBlxx = bdcSlCdBlxxFeignService.queryBdcBlxx(bdcCdBlxxDO);
            //房屋查询为空并且受理补录信息的数据也为空
            if (CollectionUtils.isEmpty(bdcFwqlList) && (Objects.isNull(bdcCdBlxx) || StringUtils.isBlank(bdcCdBlxx.getBlxxid()))) {
                logger.warn("常州房屋套次查询PDF接口,权利人：{}，证件号：{} 房屋权利查询结果为空，更改首套房证明打印类型stfzm为 stfzm_0", bdcCxzmdPdfQO.getXm(), bdcCxzmdPdfQO.getSfzh());
                dylx = CommonConstantUtils.DYLX_PDFJK_STFZM_0;
            }
            //生成xml
            Map<String, List<Map>> paramMap = new HashMap<>(1);
            List<Map> bdcdyhListMap = new ArrayList<>(1);
            Map<String, Object> mapTemp = new HashMap<>(1);
            mapTemp.put("xm", bdcCxzmdPdfQO.getXm());
            mapTemp.put("zjh", bdcCxzmdPdfQO.getSfzh());
            mapTemp.put("cxly", bdcCxzmdPdfQO.getFs());
            mapTemp.put("jycx",mainPath + "jycx/cdxxscpdfJycxImage?xm=" + URLEncoder.encode(bdcCxzmdPdfQO.getXm(),"UTF-8")+"&zjh=" +URLEncoder.encode(bdcCxzmdPdfQO.getSfzh(),"UTF-8") + "&dylx=" + URLEncoder.encode(dylx,"UTF-8"));
            bdcdyhListMap.add(mapTemp);
            paramMap.put(dylx, bdcdyhListMap);
            String xmlData = bdcPrintFeignService.print(paramMap);
            //生成PDF
            OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
            pdfExportDTO.setModelName(printPath + dylx + CommonConstantUtils.WJHZ_DOCX);
            String filename = bdcCxzmdPdfQO.getXm() + "首套房证明";
            pdfExportDTO.setFileName(filename);
            pdfExportDTO.setXmlData(xmlData);
            String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);
            File pdfFile = new File(pdfFilePath);
            if (!pdfFile.exists()) {
                logger.error("常州房屋套次查询PDF接口异常,PDF临时文件不存在，权利人{}、证件号{}", bdcCxzmdPdfQO.getXm(), bdcCxzmdPdfQO.getSfzh());
                return new BdcStfzmCxPdfVO("0", "查询失败，服务器内部错误，生成PDF失败", null);
            }
            BdcStfzmCxPdfVO bdcCxzmdPdfVO = new BdcStfzmCxPdfVO("1", "查询成功", Base64Utils.getPDFBinary(pdfFile));
            return bdcCxzmdPdfVO;
        } catch (Exception e) {
            logger.error("常州房屋套次查询PDF接口异常，权利人{}、证件号{},异常信息：", bdcCxzmdPdfQO.getXm(), bdcCxzmdPdfQO.getSfzh(), e);
            return new BdcStfzmCxPdfVO("0", "查询失败，服务器内部错误", null);
        }
    }


    /**
     * 调用常州市大数据局登记接口获取用户房产数据
     * @param bdcCxzmdPdfQO 用户查询参数
     * @return {BdcZwbInfoDTO} 房产数据
     */
    private BdcZwbInfoDTO bdcCxzmdPdf(BdcCxzmdPdfQO bdcCxzmdPdfQO) throws Exception {
        if(StringUtils.isBlank(cxzmdpdfUrl)) {
            logger.error("常州查询证明生成PDF接口异常：未配置大数据局查询接口地址");
            throw new AppException("未配置大数据局查询接口地址");
        }

        // 调用大数据局接口获取房产数据
        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair("xm", bdcCxzmdPdfQO.getXm()));
        params.add(new BasicNameValuePair("sfzh", bdcCxzmdPdfQO.getSfzh()));
        params.add(new BasicNameValuePair("fs", bdcCxzmdPdfQO.getFs()));
        params.add(new BasicNameValuePair("zl", bdcCxzmdPdfQO.getZl()));
        String paramsStr = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
        HttpPost httpPost = new HttpPost(cxzmdpdfUrl + paramsStr);
        httpPost.setHeader("Content-Type","application/json; charset=UTF-8");
        logger.info("调用常州市大数据局登记接口请求URL：{}", httpPost.getURI());

        String response = httpClientService.doPost(httpPost,"UTF-8");
        if(StringUtils.isBlank(response)) {
            logger.error("常州查询证明生成PDF接口异常：未查询到大数据局数据");
            throw new AppException("未查询到大数据局数据");
        }
        logger.info("常州查询证明生成PDF接口：返回数据：{}", response);

        // 获取响应码
        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject head = jsonObject.getJSONObject("head");
        if(null == head || StringUtils.isBlank(head.getString("code"))) {
            logger.error("常州查询证明生成PDF接口异常：未获取到响应状态码");
            throw new AppException("未获取到响应状态码");
        }

        if (StringUtils.equals(head.getString("code"), "1111")) {
            logger.info("常州查询证明生成PDF接口异常：大数据局接口内部异常，失败原因:{}",head.getString("msg"));
            throw new AppException("大数据局接口内部异常");
        }

        // 处理二维码
        BdcZwbInfoDTO bdcZwbInfoDTO = JSON.parseObject(jsonObject.toJSONString(), BdcZwbInfoDTO.class);
        // 汇总证明二维码
        String fileName = printPath + "/temp/" + UUIDGenerator.generate() + ".png";
        QRcodeUtils.encoderQRCode(bdcZwbInfoDTO.getYanzheng(),fileName,null);
        bdcZwbInfoDTO.setYanzhengQRCodeUrl(fileName);
        // 详细证明二维码
        bdcZwbInfoDTO.getBdcZwbInfo().forEach(bdcCxzmd -> {
            String file = printPath + "/temp/" + UUIDGenerator.generate() + ".png";
            QRcodeUtils.encoderQRCode(bdcCxzmd.getYanzheng(),file,null);
            bdcCxzmd.setYanzhengQRCodeUrl(file);
        });

        return bdcZwbInfoDTO;
    }
}
