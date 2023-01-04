package cn.gtmap.realestate.inquiry.service.changzhou.impl;

import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcCxzmdPdfDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZwbInfoDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCxzmdPdfQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcCxzmdPdfDataVO;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.office.OfficeUtil;
import cn.gtmap.realestate.inquiry.util.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 常州房产查询生成PDF接口 汇总证明单处理
 */
@Service
public class BdcXxcxHzzmdService {
    private static final Logger logger = LoggerFactory.getLogger(BdcXxcxHzzmdService.class);

    /**
     * 打印文件路径
     */
    @Value("${print.path:}")
    private String printPath;

    /**
     * PDF打印操作
     */
    @Autowired
    private PdfController pdfController;

    /**
     * 打印服务
     */
    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @param bdcZwbInfoDTO 房产数据
     * @return {String} PDF文件Base64数据
     * @description 生层汇总查询证明单PDF文件
     */
    public BdcCxzmdPdfDataVO cxzmdPdf(BdcCxzmdPdfQO bdcCxzmdPdfQO, BdcZwbInfoDTO bdcZwbInfoDTO,boolean filterZlflag) {
        // 1、获取打印数据
        List<BdcDysjDTO> printData = this.getPrintData(bdcCxzmdPdfQO, bdcZwbInfoDTO,filterZlflag);

        // 2、获取打印XML数据
        String xmlData = bdcPrintFeignService.printDatas(printData);
        logger.info("常州查询汇总证明单pdf对应XML数据：{}, 查询参数：{}", xmlData, JSON.toJSONString(bdcCxzmdPdfQO));
        if(StringUtils.isBlank(xmlData)) {
            return null;
        }

        // 3、生成PDF文件
        String pdfFilePath = this.generatePdfFile(xmlData);
        File pdfFile = new File(pdfFilePath);
        if(!pdfFile.exists()) {
            return null;
        }

        // 4、添加水印
        OfficeUtil.addWaterMarkToPdfFile(pdfFilePath, Constants.CZ_WATERMARK, printPath + "/");

        try {
            BdcCxzmdPdfDataVO bdcCxzmdPdfDataVO = new BdcCxzmdPdfDataVO();
            bdcCxzmdPdfDataVO.setType("zmd");
            bdcCxzmdPdfDataVO.setData(Base64Utils.getPDFBinary(pdfFile));
            return bdcCxzmdPdfDataVO;
        } finally {
            // TODO 删除临时文件
//            if(pdfFile.exists()) {
//                pdfFile.delete();
//            }
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @param bdcZwbInfoDTO 房产数据
     * @return {List} 主表数据
     * @description 生成打印数据
     */
    private List<BdcDysjDTO> getPrintData(BdcCxzmdPdfQO bdcCxzmdPdfQO, BdcZwbInfoDTO bdcZwbInfoDTO,boolean filterZlflag) {
        // 1、获取主表数据
        Map<String, String> parentData = this.getMainTable(bdcCxzmdPdfQO,bdcZwbInfoDTO);

        // 2、获取子表数据
        Multimap<String, List> childData = ArrayListMultimap.create();
        // 2.1 查询结果子表数据
        List<Map<String, String>> cxjgList = this.getCxjgTable(bdcZwbInfoDTO,filterZlflag);
        childData.put("ZT_cxjg",cxjgList);

        // 2.2 查询结果中的详细信息数据子表
        List<Map<String, String>> xxxxList = this.getCxjgChildTable(bdcZwbInfoDTO);
        childData.put("ZB_xxxx",xxxxList);

        // 3、设置打印模板格式
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>(1);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj(JSONObject.toJSONString(parentData));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(childData));
        bdcDysjDTO.setDyzd(Constants.CXZMD_XML);
        bdcDysjDTOList.add(bdcDysjDTO);
        return bdcDysjDTOList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @param bdcZwbInfoDTO 房产数据
     * @return {Map} 主表数据
     * @description 生成PDF打印模板主表对应数据
     */
    private Map<String, String> getMainTable(BdcCxzmdPdfQO bdcCxzmdPdfQO,BdcZwbInfoDTO bdcZwbInfoDTO) {
        Map<String,String> parentData = new HashMap<>(1);
        parentData.put("CXLY", bdcCxzmdPdfQO.getFs());
        parentData.put("XM", bdcCxzmdPdfQO.getXm());
        parentData.put("SFZH", bdcCxzmdPdfQO.getSfzh());
        parentData.put("CXSJ", DateUtils.formateTime(new Date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA)));
        parentData.put("CXSJ2", DateUtils.formateTime(new Date(), DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm", Locale.CHINA)));
        parentData.put("JYCX", bdcZwbInfoDTO.getYanzhengQRCodeUrl());
        return parentData;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZwbInfoDTO 房产数据
     * @return {List} 子表数据
     * @description 生成PDF打印模板查询结果子表数据
     */
    private List<Map<String, String>> getCxjgTable(BdcZwbInfoDTO bdcZwbInfoDTO,boolean filterZlflag) {
        List<Map<String,String>> cxjgList = new ArrayList<>();
        Map<String, String> cxjgMap = new HashMap<>();

        String ts = "0";
        if(CollectionUtils.isNotEmpty(bdcZwbInfoDTO.getBdcZwbInfo())) {
            if (filterZlflag){
                HashSet<String> zlSet = bdcZwbInfoDTO.getBdcZwbInfo().stream().collect(HashSet::new,
                        (set, item) -> set.add(item.getZl()),
                        HashSet::addAll);
                ts = String.valueOf(zlSet.size());
            }else {
                ts = String.valueOf(bdcZwbInfoDTO.getBdcZwbInfo().size());
            }
        }
        cxjgMap.put("ts", ts);
        cxjgList.add(cxjgMap);
        return cxjgList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZwbInfoDTO 房产数据
     * @return {List} 子表数据
     * @description 生成PDF打印模板查询结果中详细信息子表数据
     */
    private List<Map<String, String>> getCxjgChildTable(BdcZwbInfoDTO bdcZwbInfoDTO) {
        List<Map<String,String>> xxxxList = new ArrayList<>();

        if(CollectionUtils.isNotEmpty(bdcZwbInfoDTO.getBdcZwbInfo())) {
            for (BdcCxzmdPdfDTO bdcCxzmdPdfDTO : bdcZwbInfoDTO.getBdcZwbInfo()) {
                Map<String, String> xxxxMap = new HashMap<>();
                xxxxMap.put("zbxh", "1");
                xxxxMap.put("ZL", bdcCxzmdPdfDTO.getZl());
                xxxxMap.put("JZMJ", bdcCxzmdPdfDTO.getJzmj() + " ");

                String qlr = bdcCxzmdPdfDTO.getQlr();
                xxxxMap.put("QLR", qlr);

                String gyr = bdcCxzmdPdfDTO.getGyr();
                if(StringUtils.isNotBlank(gyr) && StringUtils.isNotBlank(qlr)) {
                    gyr = gyr.replaceAll(qlr, "").trim();
                }
                xxxxMap.put("GYR", gyr);
                xxxxMap.put("QDFS", bdcCxzmdPdfDTO.getQdfs());
                xxxxMap.put("DJSJ", bdcCxzmdPdfDTO.getDjsj());
                xxxxList.add(xxxxMap);
            }

            // 添加空白行
            Map<String, String> xxxxMap = new HashMap<>();
            xxxxMap.put("zbxh", "1");
            xxxxMap.put("ZL", "以下空白");
            xxxxList.add(xxxxMap);

            Map<String, String> xxxxBlankMap = new HashMap<>();
            xxxxBlankMap.put("zbxh", "1");
            xxxxBlankMap.put("ZL", " ");
            xxxxList.add(xxxxBlankMap);
        } else {
            Map<String, String> xxxxMap = new HashMap<>();
            xxxxMap.put("zbxh", "1");
            xxxxMap.put("ZL", "以下空白");
            xxxxList.add(xxxxMap);
        }

        return xxxxList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmlData XML数据
     * @return {String} PDF文件路径
     * @description 生成PDF文件
     */
    private String generatePdfFile(String xmlData) {
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(printPath + "bdcdjbcxzmd.docx");
        pdfExportDTO.setFileName("不动产登记簿查询证明单");
        pdfExportDTO.setXmlData(xmlData);
        String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);
        logger.info("常州查询汇总证明单pdf文件路径：{}", pdfFilePath);
        return pdfFilePath;
    }
}
