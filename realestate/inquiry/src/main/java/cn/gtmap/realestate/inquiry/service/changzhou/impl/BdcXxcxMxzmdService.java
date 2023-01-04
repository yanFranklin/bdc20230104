package cn.gtmap.realestate.inquiry.service.changzhou.impl;

import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.config.BdcDysjPzDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCxzmdPdfQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.service.rest.config.BdcDysjPzRestService;
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
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static cn.gtmap.realestate.common.util.CommonConstantUtils.PDF_HHF;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 常州房产查询生成PDF接口 明细证明单处理
 */
@Service
public class BdcXxcxMxzmdService {
    private static final Logger logger = LoggerFactory.getLogger(BdcXxcxServiceImpl.class);

    /**
     * 不动产登记簿查询证明明细PDF模板
     */
    private final static String BDC_DJB_CM_ZMD_MX_TEMPLATE_NAME = "bdcdjbcxmx.docx";
    /**
     * 查询证明明细打印数据类型
     */
    private final static String CXZMDMX_DYSJLX = "cxzmdmx";
    /**
     * pdf文件名
     */
    private final static String BDC_DJB_CM_ZMD_MX_FILE_NAME = "不动产登记簿查询证明明细";
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
    private BdcDysjPzRestService dysjPzRestService;

    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;

    /**
     * 生成查询明细单PDF证明
     * @param bdcCxzmdPdfDTOList 查询明细单原始数据
     * @param bdcCxzmdPdfQO 查询参数
     * @return {List} 证明信息
     * @throws Exception
     */
    public List<BdcCxzmdPdfDataVO> generateMxzmdPdf(List<BdcCxzmdPdfDTO> bdcCxzmdPdfDTOList, BdcCxzmdPdfQO bdcCxzmdPdfQO) throws Exception {
        List<BdcCxzmdPdfDataVO> bdcCxzmdPdfDataVOList = new ArrayList<>();
        Map<String, BdcCxzmdPdfDTO> bdcqzhCxzmdMap = this.resolveData(bdcCxzmdPdfDTOList);
        for(Map.Entry<String, BdcCxzmdPdfDTO> entry : bdcqzhCxzmdMap.entrySet()) {
            // 默认传进来的查询参数姓名就是查档人
            entry.getValue().setCdr(bdcCxzmdPdfQO.getXm());
            bdcCxzmdPdfDataVOList.add(this.cxzmdMxPdf(entry.getValue(), bdcCxzmdPdfQO.getFs()));
        }
        return bdcCxzmdPdfDataVOList;
    }

    /**
     * 处理查询证明单明细数据
     * @param bdcCxzmdPdfDTOList 大数据局返回的数据
     * @return {Map} 按照证号分组的数据
     * @throws Exception
     */
    private Map<String, BdcCxzmdPdfDTO> resolveData(List<BdcCxzmdPdfDTO> bdcCxzmdPdfDTOList) throws Exception {
        // 根据不动产权证号分组：若证号相同，需要将证号相同的所有的房子显示在一张表单上
        Map<String, BdcCxzmdPdfDTO> bdcqzhCxzmdMap = new HashMap<>();
        for(BdcCxzmdPdfDTO cxzmdPdfDTO : bdcCxzmdPdfDTOList) {
            if(StringUtils.isNotBlank(cxzmdPdfDTO.getBdcqzh())) {
                if(bdcqzhCxzmdMap.containsKey(cxzmdPdfDTO.getBdcqzh())) {
                    BdcCxzmdPdfDTO newBdcCxzmd = bdcqzhCxzmdMap.get(cxzmdPdfDTO.getBdcqzh());

                    BdcCxzmdPdfFwxxDTO fwxxDTO = new BdcCxzmdPdfFwxxDTO();
                    BeanUtils.copyProperties(fwxxDTO, cxzmdPdfDTO);
                    newBdcCxzmd.getFwxx().add(fwxxDTO);

                    newBdcCxzmd.getDyInfo().addAll(cxzmdPdfDTO.getDyInfo());
                    newBdcCxzmd.getCfInfo().addAll(cxzmdPdfDTO.getCfInfo());
                    newBdcCxzmd.getJzqInfo().addAll(cxzmdPdfDTO.getJzqInfo());
                    newBdcCxzmd.setJlsl(newBdcCxzmd.getJlsl() + 1);
                    newBdcCxzmd.setBdcdyh(newBdcCxzmd.getBdcdyh() + "等" + newBdcCxzmd.getJlsl() + "个");
                } else {
                    BdcCxzmdPdfDTO newBdcCxzmd = JSON.parseObject(JSON.toJSONString(cxzmdPdfDTO), BdcCxzmdPdfDTO.class);
                    newBdcCxzmd.setJlsl(1);

                    newBdcCxzmd.setFwxx(new ArrayList<>());
                    BdcCxzmdPdfFwxxDTO fwxxDTO = new BdcCxzmdPdfFwxxDTO();
                    BeanUtils.copyProperties(fwxxDTO, cxzmdPdfDTO);
                    newBdcCxzmd.getFwxx().add(fwxxDTO);

                    if(CollectionUtils.isEmpty(newBdcCxzmd.getDyInfo())) {
                        newBdcCxzmd.setDyInfo(new ArrayList<>());
                    }
                    if(CollectionUtils.isEmpty(newBdcCxzmd.getCfInfo())) {
                        newBdcCxzmd.setCfInfo(new ArrayList<>());
                    }
                    if(CollectionUtils.isEmpty(newBdcCxzmd.getJzqInfo())) {
                        newBdcCxzmd.setJzqInfo(new ArrayList<>());
                    }

                    bdcqzhCxzmdMap.put(cxzmdPdfDTO.getBdcqzh(), newBdcCxzmd);
                }
            }
        }

        // 去重处理、添加标题行数据
        for(Map.Entry<String, BdcCxzmdPdfDTO> entry : bdcqzhCxzmdMap.entrySet()) {
            BdcCxzmdPdfDTO cxzmdPdfDTO = entry.getValue();

            // 合并限制权利信息
            if(CollectionUtils.isNotEmpty(cxzmdPdfDTO.getDyInfo())) {
                cxzmdPdfDTO.setDyInfo(cxzmdPdfDTO.getDyInfo().stream().distinct().collect(Collectors.toList()));
                for(BdcCxzmdPdfDyDTO dyDTO : cxzmdPdfDTO.getDyInfo()) {
                    dyDTO.setDyqx(dyqx(dyDTO.getDyqx()));
                }

                BdcCxzmdPdfDyDTO title = new BdcCxzmdPdfDyDTO();
                title.setBdczmh("不动产证明号");
                title.setDyqr("抵押权人");
                title.setDyqx("抵押期限");
                title.setDbfw("担保范围");
                // 根据抵押方式判断，当为一般抵押时，字段显示为‘被担保主债权金额’。当为最高额抵押时，字段显示为‘最高债权额’
                title.setDyje("一般抵押权".equals(cxzmdPdfDTO.getDyInfo().get(0).getDylx()) ? "被担保主债权金额" : "最高债权额");
                title.setSfczjz("是否存在禁止或限制转让抵押不动产的约定");
                cxzmdPdfDTO.getDyInfo().add(0, title);
            }

            if(CollectionUtils.isNotEmpty(cxzmdPdfDTO.getCfInfo())) {
                cxzmdPdfDTO.setCfInfo(cxzmdPdfDTO.getCfInfo().stream().distinct().collect(Collectors.toList()));

                BdcCxzmdPdfCfInfoDTO cxzmdPdfCfInfoDTO = new BdcCxzmdPdfCfInfoDTO();
                cxzmdPdfCfInfoDTO.setXzr("查封机构及文号");
                cxzmdPdfCfInfoDTO.setXzqx("查封时间");
                cxzmdPdfDTO.getCfInfo().add(0, cxzmdPdfCfInfoDTO);
            }

            if(CollectionUtils.isNotEmpty(cxzmdPdfDTO.getJzqInfo())) {
                cxzmdPdfDTO.setJzqInfo(cxzmdPdfDTO.getJzqInfo().stream().distinct().collect(Collectors.toList()));

                BdcCxzmdPdfJzqDTO jzqDTO = new BdcCxzmdPdfJzqDTO();
                jzqDTO.setJzqzlsh("不动产证明号");
                jzqDTO.setQlr("居住权人");
                jzqDTO.setCdzmqx("居住权期限");
                cxzmdPdfDTO.getJzqInfo().add(0, jzqDTO);
            }

            if(CollectionUtils.isNotEmpty(cxzmdPdfDTO.getFwxx())) {
                cxzmdPdfDTO.setFwxx(cxzmdPdfDTO.getFwxx().stream().distinct().collect(Collectors.toList()));
                BdcCxzmdPdfFwxxDTO title = new BdcCxzmdPdfFwxxDTO();
                title.setFh("幢号/房号");
                title.setSzc("所在层");
                title.setZcs("总层数");
                title.setFwjg("房屋结构");
                title.setJzmj("建筑面积");
                title.setTnjzmj("套内建筑面积");
                title.setGhyt("规划用途");
                cxzmdPdfDTO.getFwxx().add(0, title);
            }
        }
        return bdcqzhCxzmdMap;
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcCxzmdPdfDTO 房产数据
     * @param cxly 查询来源
     * @return {BdcCxzmdPdfVO}
     * @description 生成查询证明单明细PDF文件
     */
    private BdcCxzmdPdfDataVO cxzmdMxPdf(BdcCxzmdPdfDTO bdcCxzmdPdfDTO, String cxly){
        BdcCxzmdPdfDataVO bdcCxzmdPdfVO = new BdcCxzmdPdfDataVO();
        bdcCxzmdPdfVO.setOwnerid(bdcCxzmdPdfDTO.getOwnerId());
        bdcCxzmdPdfVO.setXm(bdcCxzmdPdfDTO.getXm());
        bdcCxzmdPdfVO.setGyr(bdcCxzmdPdfDTO.getGyr());
        bdcCxzmdPdfVO.setZl(bdcCxzmdPdfDTO.getZl());
        bdcCxzmdPdfVO.setType("cxmx");

        try {
            OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
            pdfExportDTO.setModelName(printPath + BDC_DJB_CM_ZMD_MX_TEMPLATE_NAME);
            pdfExportDTO.setFileName(BDC_DJB_CM_ZMD_MX_FILE_NAME);
            pdfExportDTO.setXmlData(generatePdfXmlData(bdcCxzmdPdfDTO, cxly));
            logger.info("===>生成pdf参数:{}", JSONObject.toJSONString(pdfExportDTO));
            String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);
            logger.info("===>常州查询汇总证明单明细pdf文件路径：{}", pdfFilePath);
            OfficeUtil.addWaterMarkToPdfFile(pdfFilePath, Constants.CZ_WATERMARK, printPath + "/");
            // 转换Base64数据
            try {
                File pdfFile = new File(pdfFilePath);
                if(!pdfFile.exists()) {
                    return null;
                }
                bdcCxzmdPdfVO.setData(Base64Utils.getPDFBinary(pdfFile));
                return  bdcCxzmdPdfVO;
            } finally {
                // TODO 删除临时文件
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 查询证明单明细数据转换为XML格式数据
     * @param bdcCxzmdPdfDTO 查询明细数据
     * @param cxly 查询来源
     * @return {String} XML打印数据
     */
    private String generatePdfXmlData(BdcCxzmdPdfDTO bdcCxzmdPdfDTO, String cxly) throws DocumentException {
        BdcDysjPzDTO dysjPzDTO = dysjPzRestService.getPzxx(CXZMDMX_DYSJLX);
        if(null == dysjPzDTO || StringUtils.isBlank(dysjPzDTO.getId())) {
            logger.error("生成查询证明单明细失败，原因：未配置明细打印数据源{}", CXZMDMX_DYSJLX);
            throw new MissingArgumentException("");
        }

        // 1、获取主表数据
        Map parentData = JSON.parseObject(JSON.toJSONString(bdcCxzmdPdfDTO), Map.class);
        parentData.putAll(getQueryParam(cxly));
        // 校验查询二维码
        parentData.put("jycx", StringUtils.isBlank(bdcCxzmdPdfDTO.getYanzhengQRCodeUrl()) ? "" : bdcCxzmdPdfDTO.getYanzhengQRCodeUrl());

        // 2、获取子表数据
        Multimap<String, List> childData = ArrayListMultimap.create();
        Document document = DocumentHelper.parseText(dysjPzDTO.getDyzd());
        Element rootElement = document.getRootElement();
        Element pageElement = rootElement.element("page");
        List<Element> detailElements = pageElement.elements("detail");
        for(Element detailElement : detailElements) {
            String idVal = detailElement.attributeValue("ID");
            List<Map> detailData = new ArrayList<>();
            if(StringUtils.startsWith(idVal, "NQ_fwxx")) {
                detailData = JSON.parseArray(JSON.toJSONString(bdcCxzmdPdfDTO.getFwxx()), Map.class);
            } else if(StringUtils.startsWith(idVal, "NQ_qlrxx")) {
                detailData = JSON.parseArray(JSON.toJSONString(qlrxx(bdcCxzmdPdfDTO)), Map.class);
            } else if(StringUtils.startsWith(idVal, "NQ_jzqxx")) {
                detailData = JSON.parseArray(JSON.toJSONString(bdcCxzmdPdfDTO.getJzqInfo()), Map.class);
            } else if(StringUtils.startsWith(idVal, "NQ_xzqlxx")) {
                detailData = JSON.parseArray(JSON.toJSONString(bdcCxzmdPdfDTO.getCfInfo()), Map.class);
            } else if(StringUtils.startsWith(idVal, "NQ_dyqxx")) {
                detailData = JSON.parseArray(JSON.toJSONString(bdcCxzmdPdfDTO.getDyInfo()), Map.class);
            }

            if(CollectionUtils.isNotEmpty(detailData)) {
                childData.put(idVal, detailData);
            }
        }

        // 3、设置打印模板格式
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>(1);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj(JSONObject.toJSONString(parentData));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(childData));
        bdcDysjDTO.setDyzd(dysjPzDTO.getDyzd());
        bdcDysjDTOList.add(bdcDysjDTO);

        // 2、获取打印XML数据
        return bdcPrintFeignService.printDatas(bdcDysjDTOList);
    }

    /**
     * 抵押期限添加换行处理
     * @param dyqx 抵押期限
     */
    private static String dyqx(String dyqx) {
        String[] dyqxArray = dyqx.split("至");
        if(2 == dyqxArray.length) {
            return dyqxArray[0] + PDF_HHF + "至" + PDF_HHF + dyqxArray[1];
        }
        return dyqx;
    }

    /**
     * 添加权利人新想
     * @param bdcCxzmdPdfDTO 查询证明单数据
     */
    private static List<BdcCxzmdPdfQlrDTO> qlrxx(BdcCxzmdPdfDTO bdcCxzmdPdfDTO) {
        List<BdcCxzmdPdfQlrDTO> data = new ArrayList<>();

        // 没有共有人，单个权利人
        BdcCxzmdPdfQlrDTO qlr = new BdcCxzmdPdfQlrDTO();
        qlr.setQlr(StringUtils.isBlank(bdcCxzmdPdfDTO.getQlr()) ? "" : bdcCxzmdPdfDTO.getQlr());
        qlr.setGyqk(StringUtils.isBlank(bdcCxzmdPdfDTO.getGyqk()) ? "" : bdcCxzmdPdfDTO.getGyqk());
        qlr.setFwsyqzh(StringUtils.isBlank(bdcCxzmdPdfDTO.getBdcqzh()) ? "" : bdcCxzmdPdfDTO.getBdcqzh());
        qlr.setTdsyqzh(StringUtils.isBlank(bdcCxzmdPdfDTO.getTdsyqzh()) ? "" : bdcCxzmdPdfDTO.getTdsyqzh());
        data.add(qlr);

        if(CollectionUtils.isNotEmpty(bdcCxzmdPdfDTO.getGyrList())) {
            // 多个权利人
            for(BdcCxzmdPdfGyrDTO gyrDTO : bdcCxzmdPdfDTO.getGyrList()) {
                BdcCxzmdPdfQlrDTO qlrxx = new BdcCxzmdPdfQlrDTO();
                qlrxx.setQlr(StringUtils.isBlank(gyrDTO.getGyr()) ? "" : gyrDTO.getGyr());
                qlrxx.setGyqk(StringUtils.isBlank(bdcCxzmdPdfDTO.getGyqk()) ? "" : bdcCxzmdPdfDTO.getGyqk());
                qlrxx.setFwsyqzh(StringUtils.isBlank(gyrDTO.getGyrzh()) ? "" : gyrDTO.getGyrzh());
                qlrxx.setTdsyqzh(StringUtils.isBlank(bdcCxzmdPdfDTO.getTdsyqzh()) ? "" : bdcCxzmdPdfDTO.getTdsyqzh());
                data.add(qlrxx);
            }
        }
        return data;
    }

    /**
     * 生成每一页基本信息: 查询来源，查询时间
     * @param cxly 查询来源
     */
    private Map getQueryParam(String cxly) {
        Map param = new HashMap();
        // 查询时间 年-月-日 时:分:秒
        param.put("cxsj", DateUtils.formateTime(new Date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA)));
        // 查询来源
        param.put("cxly", StringUtils.isBlank(cxly) ? "" : cxly);
        // 查询时间 年/月/日 时:分
        param.put("cxsj2", DateUtils.formateTime(new Date(), DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm", Locale.CHINA)));

        return param;
    }
}
