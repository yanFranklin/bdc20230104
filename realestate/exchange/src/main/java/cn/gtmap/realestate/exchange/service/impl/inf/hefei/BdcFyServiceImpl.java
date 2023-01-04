package cn.gtmap.realestate.exchange.service.impl.inf.hefei;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCshXtPzFeignService;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.exchange.core.dto.hefei.fw.FycdRequest;
import cn.gtmap.realestate.exchange.core.dto.hefei.fw.fjxx.FjclmxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.exchange.config.CaqzConfig;
import cn.gtmap.realestate.exchange.core.dto.hefei.fw.fjxx.FjclDTO;
import cn.gtmap.realestate.exchange.core.dto.hefei.fw.fjxx.FjxxBean;
import cn.gtmap.realestate.exchange.core.ex.ValidException;
import cn.gtmap.realestate.exchange.core.qo.BdcFjxxQO;
import cn.gtmap.realestate.exchange.service.inf.hefei.BdcFyService;
import cn.gtmap.realestate.exchange.util.AhCaPdfQzUtils;
import cn.gtmap.realestate.exchange.util.PdfSearchPositionUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022/10/18
 * @description 合肥法院接口
 */
@Service
public class BdcFyServiceImpl implements BdcFyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcFyServiceImpl.class);

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private PdfController pdfController;

    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    @Autowired
    private BdcLsgxFeignService bdcLsgxFeignService;

    @Autowired
    private BdcXmFeignService xmFeignService;

    @Autowired
    private BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    private StorageClientMatcher storageClient;

    /**
     * 法院访问大云附件地址映射 IP 和 端口
     */
    @Value("${fy.fj.ip_port:}")
    protected String fyFjIpPortStandard;

    /**
     * ca签章配置项
     */
    @Autowired
    CaqzConfig caqzConfig;

    // 默认打印地址
    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;




    /**
     * 生成查解封回执单PDF
     *
     * @param gzlslid
     */
    @Override
    public void scCjfhzdPdf(String gzlslid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            throw new AppException("生成查解封回执单PDF请求，不动产项目不存在，工作流实例id：" + gzlslid);
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
        BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
        bdcXmLsgxQO.setXmid(bdcXmDO.getXmid());
        List<BdcXmLsgxDO> lsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
        String yxmid = "";
        if (CollectionUtils.isNotEmpty(lsgxDOList)) {
            yxmid = lsgxDOList.get(0).getYxmid();
        }
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        // 查询参数
        List<Map> csMap = new ArrayList<>(1);
        Map<String, Object> mapTemp = new HashMap<>(1);
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        // 查解封流程
        if (CommonConstantUtils.QLLX_CF.equals(bdcXmDO.getQllx())){
            BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(bdcXmDO.getXmid());
            // 查封流程，qllx为查封，生成权利
            Boolean cflc = CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfscql());
            String dylx = "";
            mapTemp.put("xmid", bdcXmDOS.get(0).getXmid());
            mapTemp.put("yxmid", yxmid);
            mapTemp.put("gzlslid", gzlslid);
            csMap.add(mapTemp);
            if (bdcXmDOS.size() == 1) {
                // 单个
                if (cflc) {
                    dylx = "cfhzd";
                } else {
                    dylx = "jfxxd";
                }
            } else {
                // 批量
                if (cflc) {
                    dylx = "cfhzdpl";
                } else {
                    dylx = "jfxxdpl";
                }
            }
            paramMap.put(dylx, csMap);
            pdfExportDTO.setModelName(printPath + dylx + CommonConstantUtils.WJHZ_DOCX);
            LOGGER.info("生成查解封回执单PDF请求，打印参数,打印类型:{}，打印模板路径：{}", dylx, printPath + dylx + CommonConstantUtils.WJHZ_DOCX);
            String xmlData = bdcPrintFeignService.print(paramMap);
            LOGGER.info("------xmlData:{}", xmlData);
            String fileName = UUIDGenerator.generate16();
            pdfExportDTO.setFileName(fileName);
            pdfExportDTO.setXmlData(xmlData);
            LOGGER.info("生成pdf文件");
            String pdfFile = pdfController.generatePdfFile(pdfExportDTO);
            LOGGER.info("生成pdf文件的路径，pdfFile：{}", pdfFile);
            String qxdm = bdcXmDOS.get(0).getQxdm();
            String pdfBinary = "";
            try {
                // 电子签章  合肥使用
                LOGGER.info("生成查解封回执单PDF，开始电子签章,pdfFile:{},qxdm:{}", pdfFile, qxdm);
                pdfBinary = fwdPdfQzInterface(pdfFile, qxdm,"以上");
                bdcUploadFileUtils.uploadBase64File(CommonConstantUtils.BASE64_QZ_PDF + pdfBinary, gzlslid, "查封回执单", "查封回执单", ".pdf");
            } catch (Exception e) {
                LOGGER.error("生成查解封回执单PDF,系统导出PDF报错:", e);
            }
        }
    }

    /**
     * 生成查档pdf
     * @param fycdRequest
     * @return
     */
    @Override
    public BdcCommonResponse scCdPdf(FycdRequest fycdRequest) {
        if (fycdRequest == null || StringUtils.isBlank(fycdRequest.getXmid()) || StringUtils.isBlank(fycdRequest.getQlrmc()) || StringUtils.isBlank(fycdRequest.getQlrzjh())) {
            return BdcCommonResponse.fail("查档缺少参数");
        }
        BdcCommonResponse bdcCommonResponse = new BdcCommonResponse();
        bdcCommonResponse.setCode("0");
        bdcCommonResponse.setCode("生成法院查档PDF失败");
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        // 查询参数
        List<Map> csMap = new ArrayList<>(1);
        Map<String, Object> mapTemp = new HashMap<>(1);
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(fycdRequest.getXmid());
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            return BdcCommonResponse.fail("查档项目不存在");
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
        BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
        bdcXmLsgxQO.setXmid(bdcXmDO.getXmid());
        List<BdcXmLsgxDO> lsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
        String yxmid = "";
        if (CollectionUtils.isNotEmpty(lsgxDOList)) {
            yxmid = lsgxDOList.get(0).getYxmid();
        }
        String dylx = "fycd";
        mapTemp.put("qlrmc", fycdRequest.getQlrmc());
        mapTemp.put("qlrzjh", fycdRequest.getQlrzjh());
        mapTemp.put("xmid", bdcXmDO.getXmid());
        mapTemp.put("yxmid", yxmid);
        mapTemp.put("qxdm", bdcXmDO.getQxdm());
        mapTemp.put("bdcdyh", bdcXmDO.getBdcdyh());
        mapTemp.put("gzlslid", bdcXmDO.getGzlslid());
        csMap.add(mapTemp);
        paramMap.put(dylx, csMap);
        pdfExportDTO.setModelName(printPath + dylx + CommonConstantUtils.WJHZ_DOCX);
        LOGGER.info("生成法院查档PDF请求，打印参数,打印类型:{}，打印模板路径：{}", dylx, printPath + dylx + CommonConstantUtils.WJHZ_DOCX);
        String xmlData = bdcPrintFeignService.print(paramMap);
        LOGGER.info("------xmlData:{}", xmlData);
        String fileName = UUIDGenerator.generate16();
        pdfExportDTO.setFileName(fileName);
        pdfExportDTO.setXmlData(xmlData);
        LOGGER.info("生成pdf文件");
        String pdfFile = pdfController.generatePdfFile(pdfExportDTO);
        LOGGER.info("生成pdf文件的路径，pdfFile：{}", pdfFile);
        String qxdm = bdcXmDO.getQxdm();
        String pdfBinary = "";
        try {
            // 电子签章  合肥使用
            LOGGER.info("生成法院查档PDF，开始电子签章,pdfFile:{},qxdm:{}", pdfFile, qxdm);
            pdfBinary = fwdPdfQzInterface(pdfFile, qxdm,"本次");
            bdcCommonResponse.setData(pdfBinary);
            bdcCommonResponse.setCode("1");
            bdcCommonResponse.setMessage("成功");
        } catch (Exception e) {
            LOGGER.error("生成法院查档PDF,系统导出PDF报错:", e);
            return BdcCommonResponse.fail("系统导出PDF报错");
        }
        return bdcCommonResponse;
    }

    /**
     * pdf文件进行ca签章
     *
     * @param path 文件路径
     * @return pdf的base64字符串
     * @throws IOException
     */
    private String fwdPdfQzInterface(String path, String qxdm,String qzKeyWord) throws IOException {
        //1.给定文件
        File pdfFile = new File(path);
        //3.IO流读取文件内容到byte数组
        byte[] pdfData = Base64Utils.getPDFByteArr(pdfFile);
        //4.指定关键字
        String keyword = qzKeyWord;
        //5.调用方法，给定关键字和文件
        List<float[]> positions = PdfSearchPositionUtil.findKeywordPostions(pdfData, keyword);
        //6.返回值类型是 List<float[]> 每个list元素代表一个匹配的位置，分别为 float[0]所在页码 float[1]所在x轴 float[2]所在y轴
        //7.调用服务端pdf签章接口
        if (CollectionUtils.isEmpty(positions)) {
            throw new AppException("ca签章未获取到位置");
        }
        String secretKey = caqzConfig.getSecretkey().get(qxdm);
        String uniqueId = caqzConfig.getUniqueid().get(qxdm);
        String fwdUrl = caqzConfig.getFwdpdfqzurl().get(qxdm);
        LOGGER.info("ca签章配置信息,secretKey:{},uniqueId:{},fwdUrl:{},qxdm:{}", secretKey, uniqueId, fwdUrl, qxdm);
        byte[] result = null;
        for (float[] position : positions) {
            // 关键字位置与实际盖章位置不在一处，调整x轴，y轴位置
            float x = position[1] + 400f;
            float y = position[2] - 100f;
            LOGGER.info("ca签章位置信息,页码：{}，x轴位置：{}，y轴位置:{}", position[0], x, y);
            result = AhCaPdfQzUtils.pdfAddHz(secretKey, uniqueId, fwdUrl, "", pdfData, String.valueOf((int) position[0]),
                    String.valueOf(x), String.valueOf(y), "", "", "", "");
        }
        String pdfBinary = "";
        if (result != null) {
            pdfBinary = Base64Utils.encodeByteToBase64Str(result);
        }
        return pdfBinary;
    }

    @Override
    public BdcCommonResponse queryFjxx(BdcFjxxQO bdcFjxxQO) {
        LOGGER.info("法院附件查询接口请求参数：{}", JSONObject.toJSONString(bdcFjxxQO));
        BdcCommonResponse commonResponse = new BdcCommonResponse();
        if (bdcFjxxQO == null || (StringUtils.isBlank(bdcFjxxQO.getXmid()) && StringUtils.isBlank(bdcFjxxQO.getSlbh())
                && StringUtils.isBlank(bdcFjxxQO.getWjjmc()))){
            return BdcCommonResponse.fail("参数不可全为空");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(bdcFjxxQO.getXmid());
        bdcXmQO.setSlbh(bdcFjxxQO.getSlbh());
        List<BdcXmDO> bdcXmDOS = xmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
            String gzlslid = bdcXmDOS.get(0).getGzlslid();
            FjclDTO fjclDTO = new FjclDTO();
            //查询受理收件材料信息
            List<FjxxBean> fjxxBeans = new ArrayList<>();
            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(gzlslid,bdcFjxxQO.getWjjmc());
            if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                for (BdcSlSjclDO slSjclDO : bdcSlSjclDOList) {
                    if (StringUtils.isNotBlank(slSjclDO.getClmc())) {
                        FjxxBean fjxxBean = new FjxxBean();
                        fjxxBean.setClmc(slSjclDO.getClmc());
                        fjxxBean.setFs(null != slSjclDO.getFs() ? String.valueOf(slSjclDO.getFs()) : "");
                        fjxxBean.setYs(null != slSjclDO.getYs() ? String.valueOf(slSjclDO.getYs()) : "");
                        fjxxBean.setFjlx(null != slSjclDO.getSjlx() ? String.valueOf(slSjclDO.getSjlx()) : "");
                        fjxxBean.setMrfjys(null != slSjclDO.getMrfs() ? String.valueOf(slSjclDO.getMrfs()) : "");
                        List<StorageDto> list = storageClient.listStoragesByName("clientId", gzlslid, null, slSjclDO.getClmc(), 1, 0);
                        if (CollectionUtils.isEmpty(list)) {
                            continue;
                        }

                        List<StorageDto> listFile = storageClient.listAllSubsetStorages(list.get(0).getId(), StringUtils.EMPTY, 1, null, 0, null);
                        if (CollectionUtils.isEmpty(listFile)) {
                            continue;
                        }
                        List<FjclmxDTO> fjclmxDTOS = new ArrayList<>();
                        for (StorageDto storageDto : listFile) {
                            FjclmxDTO fjclmxDTO = new FjclmxDTO();
                            fjclmxDTO.setFjid(storageDto.getId());
                            fjclmxDTO.setFjmc(storageDto.getName());
                            // 直接推送到税务附件过大会导致传输慢，因此改为传附件URL地址，由税务异步调用下载附件，字段还是继续沿用base64字段
                            String url = storageDto.getDownUrl();
                            if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(fyFjIpPortStandard)) {
                                // 大云附件地址例如：http://192.168.2.87:8030/storage/rest/files/download/ff8080817399496301740064a45a0363
                                url = "http://" + fyFjIpPortStandard + url.substring(url.indexOf("/storage"));
                            }
                            fjclmxDTO.setFjurl(url);
                            fjclmxDTOS.add(fjclmxDTO);
                        }
                        fjxxBean.setClnr(fjclmxDTOS);
                        fjxxBeans.add(fjxxBean);
                    }

                }
                fjclDTO.setFjxx(fjxxBeans);
                commonResponse.setData(fjclDTO);
            }
        }
        commonResponse.setCode("1");
        commonResponse.setMessage("查询成功");
        return commonResponse;
    }
}
