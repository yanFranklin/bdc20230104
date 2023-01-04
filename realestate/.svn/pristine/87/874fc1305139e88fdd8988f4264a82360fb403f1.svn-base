package cn.gtmap.realestate.common.util.office;

import cn.gtmap.realestate.common.core.dto.OfficeInnerTableDataDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.annotations.PdfRubberStampAnnotation;
import com.spire.pdf.annotations.appearance.PdfAppearance;
import com.spire.pdf.graphics.PdfImage;
import com.spire.pdf.graphics.PdfTemplate;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.xdocreport.itext.extension.IPdfWriterConfiguration;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;
import org.apache.poi.xwpf.usermodel.*;
import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.RFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.geom.Rectangle2D;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.gtmap.realestate.common.util.CommonConstantUtils.PDF_HHF;
import static org.apache.commons.io.IOUtils.*;


/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/06/01
 * @description Office 文档处理工具类
 */
public class OfficeUtil {

    /**
     * 日志操作
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OfficeUtil.class);

    private static final Pattern pattern = Pattern.compile("[0-9]*");



    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param wordToPdfFastConverter word转pdf方案：1 快速方案 否则 之前旧版方案
     * @param docxPath word文件位置
     * @param pdfPath  pdf文件位置
     * @param fontMapper 字体
     * @description 将word文档转为pdf
     */
    public static void convertDocxToPDF(String wordToPdfFastConverter, String docxPath, String pdfPath, Mapper fontMapper, String printPath, String modelName) {
        OutputStream os = null;
        FileInputStream is = null;

        try {
            is = new FileInputStream(new File(docxPath));
            os = new FileOutputStream(pdfPath);
            if(String.valueOf(CommonConstantUtils.SF_S_DM).equals(wordToPdfFastConverter)) {
                LOGGER.info("{}打印word转pdf采用快速方案", modelName);
                XWPFDocument document = new XWPFDocument(is);
                PdfOptions options= PdfOptions.create();
                options.fontProvider((familyName, encoding, size, style, color) -> {
                    try {
                        BaseFont baseFont = BaseFont.createFont(printPath + getFont(familyName) + ",0", encoding, BaseFont.EMBEDDED);
                        return new com.lowagie.text.Font(baseFont, size, style, color);
                    } catch (Exception e) {
                        BaseFont baseFont = null;
                        try {
                            baseFont = BaseFont.createFont(printPath + getFont("宋体") + ",0", "", BaseFont.EMBEDDED);
                        } catch (Exception ex) { }
                        return new com.lowagie.text.Font(baseFont, size, style, color);
                    }
                });
                PdfConverter.getInstance().convert(document, os, options);
            } else {
                LOGGER.info("{}打印word转pdf采用普通方案", modelName);
                WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(is);
                // 设置字体
                OfficeUtil.configSimSunFont(mlPackage, fontMapper);
                FOSettings foSettings = Docx4J.createFOSettings();
                foSettings.setWmlPackage(mlPackage);
                Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
            }
        } catch (Exception e) {
            LOGGER.error("word转为pdf失败");
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is, os);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param wordIs word文件流
     * @param pdfOs  pdf文件流
     * @param fontMapper 字体
     * @description 将word文档转为pdf
     */
    public static void convertDocxToPDF(InputStream wordIs, OutputStream pdfOs, Mapper fontMapper) {
        if(wordIs ==null){
            LOGGER.error("未获取到WORD文件流");
            return;
        }
        try {
            WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(wordIs);
            // 设置字体
            OfficeUtil.configSimSunFont(mlPackage, fontMapper);
            FOSettings foSettings = Docx4J.createFOSettings();
            foSettings.setWmlPackage(mlPackage);
            Docx4J.toFO(foSettings, pdfOs, Docx4J.FLAG_EXPORT_PREFER_XSL);
        } catch (Exception e) {
            LOGGER.error("word转为pdf失败");
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(wordIs, pdfOs);
        }
    }



    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  设置文件字体
     */
    private static void configSimSunFont(WordprocessingMLPackage wordMLPackage, Mapper fontMapper) throws Exception {
        wordMLPackage.setFontMapper(fontMapper);
        RFonts rfonts = Context.getWmlObjectFactory().createRFonts();
        rfonts.setAsciiTheme(null);
        rfonts.setAscii("SimSun");
        wordMLPackage.getMainDocumentPart().getPropertyResolver().getDocumentDefaultRPr().setRFonts(rfonts);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 处理字体
     */
    public static Mapper getFontMapper(String printPath) {
        try {
            // 默认文档为宋体
            String fontFamily = "SimSun";
            // 加载字体文件
            URL simsunUrl = new URL("file:" + printPath + "simsun.ttc");
            PhysicalFonts.addPhysicalFonts(fontFamily, simsunUrl);
            PhysicalFont simsunFont = PhysicalFonts.get(fontFamily);
            //黑体
            URL simheiUrl = new URL("file:" + printPath + "simhei.ttf");
            PhysicalFonts.addPhysicalFonts("SimHei", simheiUrl);
            PhysicalFont simHeiFont = PhysicalFonts.get("SimHei");
            //微软雅黑
            URL msyhUrl = new URL("file:" + printPath + "msyh.ttf");
            PhysicalFonts.addPhysicalFonts("Msyh", msyhUrl);
            PhysicalFont msyhFont = PhysicalFonts.get("Msyh");
            //楷体
            URL simkaiUrl = new URL("file:" + printPath + "simkai.ttf");
            PhysicalFonts.addPhysicalFonts("Simkai", simkaiUrl);
            PhysicalFont simkaiFont = PhysicalFonts.get("Simkai");
            //隶书
            URL simliUrl = new URL("file:" + printPath + "simli.ttf");
            PhysicalFonts.addPhysicalFonts("Simli", simliUrl);
            PhysicalFont simliFont = PhysicalFonts.get("Simli");
            //仿宋_GB2312
            URL simfangUrl = new URL("file:" + printPath + "simfang.ttf");
            PhysicalFonts.addPhysicalFonts("Simfang", simfangUrl);
            PhysicalFont simfangFont = PhysicalFonts.get("Simfang");
            //幼圆
            URL youyuanUrl = new URL("file:" + printPath + "youyuan.ttf");
            PhysicalFonts.addPhysicalFonts("Youyuan", youyuanUrl);
            PhysicalFont youyuanFont = PhysicalFonts.get("Youyuan");

            Mapper fontMapper = new IdentityPlusMapper();
            fontMapper.put("宋体", simsunFont);
            fontMapper.put("黑体", simHeiFont);
            fontMapper.put("微软雅黑", msyhFont);
            fontMapper.put("楷体", simkaiFont);
            fontMapper.put("隶书", simliFont);
            fontMapper.put("仿宋_GB2312", simfangFont);
            fontMapper.put("幼圆", youyuanFont);
            return fontMapper;
        } catch (Exception e) {
            LOGGER.warn("目录无指定PDF打印需要字体：{}", printPath);
            return OfficeUtil.getFontMapper();
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 字体路径
     */
    public static String getFont(String fontName) {
        // 默认宋体
        String foontFileName = "simsun.ttc";
        switch (fontName) {
            case "宋体": foontFileName = "simsun.ttc"; break;
            case "黑体": foontFileName = "simhei.ttf"; break;
            case "微软雅黑": foontFileName = "msyh.ttf"; break;
            case "楷体": foontFileName = "simkai.ttf"; break;
            case "隶书": foontFileName = "simli.ttf"; break;
            case "仿宋": foontFileName = "simfang.ttf"; break;
            case "幼圆": foontFileName = "youyuan.ttf"; break;
        }
        return foontFileName;
    }


    public static Map getFontMap(){
        Map<String,String> fontMap = new HashMap<String,String>();
        fontMap.put("SimSun","宋体");
        fontMap.put("SimHei","黑体");
        fontMap.put("Msyh","微软雅黑");
        fontMap.put("SimKai","楷体");
        fontMap.put("SimLi","隶书");
        fontMap.put("SimFang","仿宋_GB2312");
        fontMap.put("YouYuan","幼圆");
        return fontMap;
    }

    private static Mapper getFontMapper(){
        Mapper fontMapper = new IdentityPlusMapper();
        fontMapper.put("微软雅黑",PhysicalFonts.get("Microsoft Yahei"));
        fontMapper.put("黑体",PhysicalFonts.get("SimHei"));
        fontMapper.put("楷体",PhysicalFonts.get("KaiTi"));
        fontMapper.put("隶书", PhysicalFonts.get("LiSu"));
        fontMapper.put("宋体",PhysicalFonts.get("SimSun"));
        fontMapper.put("宋体扩展",PhysicalFonts.get("simsun-extB"));
        fontMapper.put("新宋体",PhysicalFonts.get("NSimSun"));
        fontMapper.put("仿宋",PhysicalFonts.get("FangSong"));
        fontMapper.put("仿宋_GB2312",PhysicalFonts.get("FangSong_GB2312"));
        fontMapper.put("幼圆",PhysicalFonts.get("YouYuan"));
        fontMapper.put("华文宋体",PhysicalFonts.get("STSong"));
        fontMapper.put("华文仿宋", PhysicalFonts.get("STFangsong"));
        fontMapper.put("华文中宋",PhysicalFonts.get("STZhongsong"));
        fontMapper.put("华文行楷", PhysicalFonts.get("STXingkai"));
        return fontMapper;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param printPath 打印路径
     * @description  生成临时word文件名称
     */
    public static String generateWordFileName(String printPath){
        return printPath + "temp/" + UUIDGenerator.generate16() + ".docx";
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param printPath 打印路径
     * @description  生成临时pdf文件名称
     */
    public static String generatePdfFileName(String printPath){
        return printPath + "temp/" + UUIDGenerator.generate16() + ".pdf";
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pdfFilePath 需要加水印的PDF文件
     * @param waterMark 需要添加的水印内容
     * @param fontPath 字体文件路径（需要ttf格式字体，一般和打印word模板放到一起，默认宋体）
     * @return  PDF文件添加水印
     */
    public static void addWaterMarkToPdfFile(String pdfFilePath, String waterMark, String fontPath) {
        appendWaterMark(pdfFilePath, waterMark, fontPath, 30, 1);
    }

    /**
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @param pdfFilePath 需要加水印的PDF文件
     * @param pdfData 数据源数据
     * @param fontPath 字体文件路径（需要ttf格式字体，一般和打印word模板放到一起，默认宋体）
     * @return  PDF文件添加水印
     */
    public static void addWaterMarkConfigToPdfFile(String pdfFilePath, Map<String, Object> pdfData, String fontPath) {
        String waterMark = (String) pdfData.get(CommonConstantUtils.WATERMARK);
        //水印字
        String font="";
        // 字体大小默认值是30
        float fontSize=30;
        // 水印Y轴偏移因子默认值是1
        float factor=1;
        if(StringUtils.isNotBlank(waterMark)){
            String[] tempArr = waterMark.split("_");
            font=tempArr[0];
            if(tempArr.length > 1){
                for(int i=1;i<tempArr.length;i++){
                    String items = tempArr[i];
                    if(items.startsWith(CommonConstantUtils.WATERMARK_FONTSIZE)){
                        String fontSizeTemp=items.substring(CommonConstantUtils.WATERMARK_FONTSIZE.length());
                        if(NumberUtils.isNumber(fontSizeTemp)){
                            fontSize=Float.parseFloat(fontSizeTemp);
                        }
                    }
                    if(items.startsWith(CommonConstantUtils.WATERMARK_FACTOR)){
                        String factorTemp=items.substring(CommonConstantUtils.WATERMARK_FACTOR.length());
                        if(NumberUtils.isNumber(factorTemp)){
                            factor=Float.parseFloat(factorTemp);
                        }
                    }
                }
            }
            appendWaterMark(pdfFilePath, font, fontPath, fontSize, factor);
        }else{
            LOGGER.info("PDF{}未设置水印watermark字段", pdfFilePath);
        }
    }

    /**
     * PDF增加水印方法，自定义水印字体大小
     * @param pdfFilePath 需要加水印的PDF文件
     * @param waterMark  需要添加的水印内容
     * @param fontPath  字体文件路径（需要ttf格式字体，一般和打印word模板放到一起，默认宋体）
     * @param fontSzie  字体大小
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    public static void addWaterMarkToPdfFileWithFontSzie(String pdfFilePath, String waterMark, String fontPath, float fontSzie){
        // 设置水印Y轴偏移计算方式为： 将水印文字的长度作为斜边，Y轴的高度为直角边。
        float factor = (float) Math.sin(Math.PI/4);
        appendWaterMark(pdfFilePath, waterMark, fontPath, fontSzie, factor);
    }

    /**
     *  pdf文件添加水印防范
     * @param pdfFilePath 需要加水印的PDF文件
     * @param waterMark 需要添加的水印内容
     * @param fontPath 字体文件路径（需要ttf格式字体，一般和打印word模板放到一起，默认宋体）
     * @param fontSize 水印字体大小
     * @param factor  水印Y轴偏移因子
     */
    private static void appendWaterMark(String pdfFilePath, String waterMark, String fontPath, float fontSize, float factor){
        if(StringUtils.isAnyBlank(pdfFilePath, waterMark, fontPath)) {
            throw new AppException("未指定PDF文件、水印内容、字体路径参数");
        }

        File pdfFile = new File(pdfFilePath);
        if(!pdfFile.exists()) {
            throw new AppException("未找到指定路径PDF文件");
        }

        try {
            //打开pdf文件
            PDDocument doc = PDDocument.load(pdfFile);
            doc.setAllSecurityToBeRemoved(true);

            //遍历pdf所有页
            for (PDPage page : doc.getPages()) {
                PDPageContentStream cs = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true);

                //引入字体文件 解决中文汉字乱码问题
                PDFont font = PDType0Font.load(doc, new FileInputStream(fontPath + "simsun.ttf"), true);
                PDResources resources = page.getResources();
                PDExtendedGraphicsState r0 = new PDExtendedGraphicsState();

                // 水印透明度
                r0.setNonStrokingAlphaConstant(0.5f);
                r0.setAlphaSourceFlag(true);
                cs.setGraphicsStateParameters(r0);

                //水印颜色
                cs.setNonStrokingColor(220, 220, 220);
                cs.beginText();
                cs.setFont(font, fontSize);

                //根据水印文字大小长度计算横向坐标需要渲染几次水印
                float h = waterMark.length() * fontSize;
                for (int i = 0; i <= 5; i++) {
                    // 获取旋转实例
                    cs.setTextMatrix(Matrix.getRotateInstance(-150, i * 200, 0));
                    cs.showText(waterMark);
                    for (int j = 0; j < 10; j++) {
                        float ty = j * h * factor;
                        cs.setTextMatrix(Matrix.getRotateInstance(-150, i * 200, ty));
                        cs.showText(waterMark);
                    }
                }
                cs.endText();
                cs.restoreGraphicsState();
                cs.close();
            }
            doc.save(pdfFile);
        } catch (Exception e) {
            LOGGER.error("PDF{}添加水印失败: {}", pdfFilePath, e.getMessage());
            // 这里不抛出异常，即使没有生成水印也不影响正常文件生成
        }
    }

    /**
     * @author wangyinghao
     * @param pdfFilePath 需要加水印的PDF文件
     * @param pdfData 数据源数据
     * @param bgPath
     * @return  PDF文件添加图片水印（背景图片的方式）
     */
    public static void addPicWaterMarkConfigToPdfFile(String pdfFilePath,Map<String, Object> pdfData, String bgPath) {
        try {
            String waterMark = (String) pdfData.get(CommonConstantUtils.PICWATERMARK);
            if (StringUtils.isNotBlank(waterMark)) {
                String tempPdfWaterFileName = OfficeUtil.generatePdfFileName(bgPath);
                PdfReader reader = new PdfReader(pdfFilePath);
                PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(tempPdfWaterFileName));
                //图片路径
                String imagePath = bgPath + waterMark;
                // 插入水印
                Image img = Image.getInstance(imagePath);
                float imgHeight = img.getHeight();
                float imgWidth = img.getWidth();
                //开始水印
                for(int i = 1; i <= reader.getNumberOfPages(); i++) {
                    //当前的位置
                    float absoluteX = 10;
                    float absoluteY = 10;
                    //页面大小
                    Rectangle pageSize = reader.getPageSize(i);
                    float pageHeight = pageSize.getHeight();
                    float pageWidth = pageSize.getWidth();
                    //将水印铺满一整页
                    PdfContentByte under = stamp.getUnderContent(i);
                    while (absoluteY < pageHeight) {
                        Image imgBg = Image.getInstance(imagePath);
                        //设置图片水印的位置。
                        float currentX = absoluteX;
                        float currentY = absoluteY;
                        imgBg.setAbsolutePosition(currentX, currentY);
                        under.addImage(imgBg);
                        absoluteX += imgWidth + 50;
                        if(absoluteX >= pageWidth) {
                            absoluteX = 10;
                            absoluteY += imgHeight + 70;
                        }
                    }
                }
                // 关闭
                stamp.close();
                reader.close();
                addPicWaterMarkConfigToPdfFileBack(pdfFilePath, tempPdfWaterFileName);
            }else {
                LOGGER.info("PDF{}未设置图片水印picwatermark字段", pdfFilePath);
            }
        } catch (Exception e) {
            LOGGER.error("PDF{}添加图片水印失败: {}", pdfFilePath, e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * @author wutao2
     * @param pdfFilePath 需要加水印的PDF文件路径
     * @param pdfData 数据源数据
     * @return  PDF文件添加签章图片
     */
    public static void addQzPicConfigToPdfFile(String pdfFilePath,Map<String, Object> pdfData,String bgPath) {
        String qzPicPath = (String) pdfData.get(CommonConstantUtils.QZPICPATH);
        String keyWords = (String) pdfData.get(CommonConstantUtils.QZKEYWORDS);
        String qzPicSize = (String) pdfData.get(CommonConstantUtils.QZPICSIZE);
        String qzPicPosition = (String) pdfData.get(CommonConstantUtils.QZPICPOSITION);
        // 如果设置了签章图片和关键字
        if(StringUtils.isNotBlank(qzPicPath)){
            try {
                // 创建PdfDocument对象，加载PDF文档
                PdfDocument doc = new PdfDocument();
                doc.loadFromFile(pdfFilePath);
                // 页数
                int pageTotalNum = doc.getPages().getCount();
                // 获取印章图片的宽度和高度
                int width = 120;
                int height = 120;
                // 如果配置了签章图片大小
                if(StringUtils.isNotBlank(qzPicSize)){
                    String[] tempArr = qzPicSize.split(",");
                    if(tempArr.length == 2 && NumberUtils.isNumber(tempArr[0]) && NumberUtils.isNumber(tempArr[1])){
                        width=Integer.parseInt(tempArr[0]);
                        height=Integer.parseInt(tempArr[1]);
                    }
                }
                if(StringUtils.isNotBlank(keyWords)){
                    LOGGER.info("PDF{}中设置的关键字：{}", pdfFilePath, keyWords);
                    // 给定文件
                    File pdfFile = new File(pdfFilePath);
                    // IO流读取文件内容到byte数组
                    byte[] pdfData1 = Base64Utils.getPDFByteArr(pdfFile);

                    // 创建关键字集合
                    List<String> keyWordList = Arrays.asList(keyWords.split(","));
                    //调用方法，给定关键字和文件
                    List<float[]> positions =new ArrayList<float[]>();
                    for(String keyword : keyWordList){
                        List<float[]> pos = PdfSearchPositionUtil.findKeywordPostions(pdfData1, keyword);
                        positions.addAll(pos);
                    }
                    PdfReader reader = new PdfReader(pdfFilePath);
                    String tempPdfWaterFileName = OfficeUtil.generatePdfFileName(bgPath);
                    PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(tempPdfWaterFileName));
                    if(CollectionUtils.isNotEmpty(positions)){
                        // 遍历关键字位置给pdf加上签章图片
                        for(float[] position : positions){
                            // pdf小于十页，采用免费的插入图片的方法，大于十页用背景图片的方法
                            if(pageTotalNum < 10){
                                PdfPageBase page = doc.getPages().get((int)position[0]-1);
                                // 加载印章图片
                                PdfImage image = PdfImage.fromFile(qzPicPath);
                                // 创建PdfTemplate对象
                                PdfTemplate template = new PdfTemplate(width, height);
                                // 将图片绘制到模板
                                template.getGraphics().drawImage(image, 0, 0, width, height);

                                // 创建PdfRubebrStampAnnotation对象，指定大小和位置
                                Rectangle2D rect = new Rectangle2D.Float((float) position[1], (float) (page.getActualSize().getHeight() - position[2] - 60), width, height);
                                PdfRubberStampAnnotation pdfStamp = new PdfRubberStampAnnotation(rect);

                                // 创建PdfAppearance对象
                                PdfAppearance pdfAppearance = new PdfAppearance(pdfStamp);
                                // 将模板应用为PdfAppearance的一般状态
                                pdfAppearance.setNormal(template);
                                // 将PdfAppearance 应用为图章的样式
                                pdfStamp.setAppearance(pdfAppearance);

                                // 添加图章到PDF
                                page.getAnnotationsWidget().add(pdfStamp);
                                // 保存文档
                                doc.saveToFile(pdfFilePath, FileFormat.PDF);
                            } else{
                                PdfContentByte under = stamp.getUnderContent((int)position[0]);
                                // 设置图片水印的位置。
                                float currentX = position[1];
                                float currentY = position[2]-60;
                                // 获取签章图片，并且设置大小
                                Image imgBg = Image.getInstance(qzPicPath);
                                imgBg.scaleAbsolute(width, height);
                                imgBg.setAbsolutePosition(currentX, currentY);
                                // 添加图片
                                under.addImage(imgBg);
                            }
                        }
                        stamp.close();
                        reader.close();
                        if( pageTotalNum > 9){
                            addPicWaterMarkConfigToPdfFileBack(pdfFilePath, tempPdfWaterFileName);
                        }
                    } else {
                        LOGGER.info("PDF{}中未找到设置的关键字：{}", pdfFilePath, keyWords);
                    }
                }
                //给除了最后一页添加印章
                if(StringUtils.isNotBlank(qzPicPosition)){
                    LOGGER.info("PDF{}中设置除最后一页的签章图片", pdfFilePath);
                    // 签章坐标
                    float x;
                    float y;
                    String[] positionArr = qzPicPosition.split(",");
                    PdfReader reader = new PdfReader(pdfFilePath);
                    String tempPdfWaterFileName = OfficeUtil.generatePdfFileName(bgPath);
                    PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(tempPdfWaterFileName));
                    if(positionArr.length == 2 && NumberUtils.isNumber(positionArr[0]) && NumberUtils.isNumber(positionArr[1])){
                        x=Integer.parseInt(positionArr[0]);
                        y=Integer.parseInt(positionArr[1]);
                        // pdf小于十页，采用免费的插入图片的方法，大于十页用背景图片的方法
                        if(pageTotalNum < 10){
                            for(int i=0;i<pageTotalNum-1;i++){
                                PdfPageBase page = doc.getPages().get(i);
                                // 加载印章图片
                                PdfImage image = PdfImage.fromFile(qzPicPath);
                                // 创建PdfTemplate对象
                                PdfTemplate template = new PdfTemplate(width, height);
                                // 将图片绘制到模板
                                template.getGraphics().drawImage(image, 0, 0, width, height);

                                // 创建PdfRubebrStampAnnotation对象，指定大小和位置
                                Rectangle2D rect = new Rectangle2D.Float(x, (float) (page.getActualSize().getHeight() - y - 120), width, height);
                                PdfRubberStampAnnotation pdfStamp = new PdfRubberStampAnnotation(rect);

                                // 创建PdfAppearance对象
                                PdfAppearance pdfAppearance = new PdfAppearance(pdfStamp);
                                // 将模板应用为PdfAppearance的一般状态
                                pdfAppearance.setNormal(template);
                                // 将PdfAppearance 应用为图章的样式
                                pdfStamp.setAppearance(pdfAppearance);

                                // 添加图章到PDF
                                page.getAnnotationsWidget().add(pdfStamp);
                                // 保存文档
                                doc.saveToFile(pdfFilePath, FileFormat.PDF);
                            }

                        } else{
                            for(int i=1;i<pageTotalNum;i++){
                                PdfContentByte under = stamp.getUnderContent(i);
                                // 设置图片水印的位置。
                                float currentX = x;
                                float currentY = y;
                                // 获取签章图片，并且设置大小
                                Image imgBg = Image.getInstance(qzPicPath);
                                imgBg.scaleAbsolute(width, height);
                                imgBg.setAbsolutePosition(currentX, currentY);
                                // 添加图片
                                under.addImage(imgBg);
                            }
                        }
                        stamp.close();
                        reader.close();
                        if( pageTotalNum > 9){
                            addPicWaterMarkConfigToPdfFileBack(pdfFilePath, tempPdfWaterFileName);
                        }
                    } else{
                        LOGGER.info("PDF{}设置签章位置不正确", pdfFilePath);
                    }
                }
            }catch (Exception e){
                LOGGER.info("PDF{}添加签章图片失败，失败原因：{}", pdfFilePath, e.toString());
            }
        } else{
            LOGGER.info("PDF{}未设置签章图片或关键字", pdfFilePath);
        }

    }

    /**
     * 重新生成原图片
     * @param pdfFilePath
     * @param tempPdfWaterFileName
     * @throws IOException
     * @throws DocumentException
     */
    private static void addPicWaterMarkConfigToPdfFileBack(String pdfFilePath, String tempPdfWaterFileName) throws Exception {
        try {
            File orgPdfFile = new File(pdfFilePath);
            if(orgPdfFile.exists()){
                boolean delete = orgPdfFile.delete();
            }
            PdfReader newPdfReader = new PdfReader(tempPdfWaterFileName);
            PdfStamper newStamp = new PdfStamper(newPdfReader, new FileOutputStream(pdfFilePath));
            newStamp.close();
            newPdfReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            File tempPdfFile = new File(tempPdfWaterFileName);
            if(tempPdfFile.exists()){
                tempPdfFile.delete();
            }
        }
    }

    /**
     * 获取内嵌子表列宽
     * @param table 内嵌子表所在表格
     * @param tableData 内嵌子表对应数据
     * @param cellLocationRow 内嵌子表所在行
     * @param cellLocationCol 内嵌子表所在列
     * @param colNum 内嵌子表总列数
     */
    public static BigDecimal[] getInCellTableColumnWidth(XWPFTable table, List<Map<String, Object>> tableData, int cellLocationRow, int cellLocationCol, int colNum) {
        // 内嵌子表所在单元格宽度
        double inCellWith = table.getRow(cellLocationRow).getCell(cellLocationCol).getWidthDecimal();

        // 缓存每个列宽度
        BigDecimal[] colWidths = new BigDecimal[colNum];

        // 每行数据设置的列宽一致，只需要从第一行数据解析即可
        Map<String, Object> fieldNameMap = tableData.get(0);
        Iterator<Map.Entry<String, Object>> iterator = fieldNameMap.entrySet().iterator();
        while (iterator.hasNext()) {
            String[] field = iterator.next().getKey().split("_");
            if(field.length != 2 && field.length != 3) {
                LOGGER.error("解析内嵌表格字段名失败，原因：xml字段名配置错误");
                throw new AppException("xml单元格嵌套子表字段名称配置错误，解析失败");
            }

            // 字段设置的宽度占据百分比
            int colVal = Integer.parseInt(field[1]);
            if(colVal > 100) {
                LOGGER.error("解析内嵌表格字段名失败，原因：列宽度设置超出100%");
                throw new AppException("xml单元格嵌套子表列宽设置错误，解析失败");
            }

            if(2 == field.length) {
                // 如果没有设置宽度则当前列默认占总列数等宽宽度: 所在单元格总宽度 / 列数
                colWidths[colVal] = new BigDecimal(Double.toString(inCellWith)).divide(new BigDecimal(Integer.toString(colNum)), 0, BigDecimal.ROUND_HALF_UP);
            } else {
                // 设置了宽度百分比则计算实际宽度值： 百分比 * 所在单元格总宽度 / 100
                colWidths[colVal] = new BigDecimal(Double.toString(Double.parseDouble(field[2]))).multiply(new BigDecimal(Double.toString(inCellWith))).divide(new BigDecimal(Integer.toString(100)), 0, BigDecimal.ROUND_HALF_UP);
            }
        }

        return colWidths;
    }

    /**
     * 设置内嵌子表单元格列内容
     * @param innerTableDataDTO 单元格相关信息
     */
    public static void setInnerTableColumnText(OfficeInnerTableDataDTO innerTableDataDTO) {
        // 内嵌子表默认绘制了指定行、一列表格，对于第一列直接获取，不需要创建，否则会导致多出一列
        XWPFTableCell cell = 0 == innerTableDataDTO.getColumnNum() ? innerTableDataDTO.getRow().getCell(0) : innerTableDataDTO.getRow().createCell();

        // 设置内嵌子表四周边框无
        setBorder(cell, innerTableDataDTO);

        // 垂直居中
        innerTableDataDTO.getRow().getCell(innerTableDataDTO.getColumnNum()).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        XWPFParagraph paragraph;
        if(CollectionUtils.isNotEmpty(cell.getParagraphs())) {
            paragraph = cell.getParagraphs().get(0);
        } else {
            paragraph = innerTableDataDTO.getRow().createCell().addParagraph();
        }

        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setVerticalAlignment(TextAlignment.CENTER);

        XWPFRun run;
        if(CollectionUtils.isNotEmpty(paragraph.getRuns())) {
            run = paragraph.getRuns().get(0);
        } else {
            run = paragraph.createRun();
        }

        if(null == innerTableDataDTO.getValue()) {
            run.setText("");
        } else {
            String valStr = String.valueOf(innerTableDataDTO.getValue());
            if(valStr.contains(PDF_HHF)) {
                // 解析换行符号
                String[] array = valStr.split(PDF_HHF);
                for(int i = 0; i < array.length; i++) {
                    run.setText(array[i]);
                    run.addBreak();
                }
            } else {
                if(StringUtils.equals(valStr, CommonConstantUtils.PDF_NULL_ROW_DATA)) {
                    // 当前数据为空行标识，需要展示空内容
                    valStr = " ";
                }
                run.setText(valStr);
            }
            if(StringUtils.isNotBlank(innerTableDataDTO.getFontType())){
                run.setFontFamily(innerTableDataDTO.getFontType());
            }
            if(StringUtils.isNotBlank(innerTableDataDTO.getFontSize())){
                //判断是否为字体大小是否为数字
//                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher isNum = pattern.matcher(innerTableDataDTO.getFontSize());
                if( isNum.matches() ){
                    run.setFontSize(Integer.parseInt(innerTableDataDTO.getFontSize()));
                }
            }
        }
    }

    /**
     * 设置内嵌子表四周边框无
     * @param cell 当前单元格
     * @param innerTableDataDTO 单元格相关信息
     */
    public static void setBorder(XWPFTableCell cell, OfficeInnerTableDataDTO innerTableDataDTO) {
        if(StringUtils.isBlank(innerTableDataDTO.getBorderWidth())) {
            innerTableDataDTO.setBorderWidth("4");
        }

        CTTc ctTc = cell.getCTTc();
        CTTcPr tcPr = ctTc.addNewTcPr();
        CTTcBorders border = tcPr.addNewTcBorders();

        if(innerTableDataDTO.topNeedSetNoneBorder()) {
            CTBorder ctBorder = border.addNewTop();
            ctBorder.setVal(STBorder.NONE);
        } else {
            CTBorder ctBorder = border.addNewTop();
            ctBorder.setVal(STBorder.SINGLE);
            ctBorder.setSz(BigInteger.valueOf(Long.parseLong(innerTableDataDTO.getBorderWidth())));
        }

        if(innerTableDataDTO.isBottomRow()) {
            CTBorder ctBorder = border.addNewBottom();
            ctBorder.setVal(STBorder.NONE);
        } else {
            CTBorder ctBorder = border.addNewBottom();
            ctBorder.setVal(STBorder.SINGLE);
            ctBorder.setSz(BigInteger.valueOf(Long.parseLong(innerTableDataDTO.getBorderWidth())));
        }

        if(innerTableDataDTO.isLeftColumn()) {
            CTBorder ctBorder = border.addNewLeft();
            ctBorder.setVal(STBorder.NONE);
        } else {
            CTBorder ctBorder = border.addNewLeft();
            ctBorder.setVal(STBorder.SINGLE);
            ctBorder.setSz(BigInteger.valueOf(Long.parseLong(innerTableDataDTO.getBorderWidth())));
        }

        if(innerTableDataDTO.isRightColumn()) {
            CTBorder ctBorder = border.addNewRight();
            ctBorder.setVal(STBorder.NONE);
        } else {
            CTBorder ctBorder = border.addNewRight();
            ctBorder.setVal(STBorder.SINGLE);
            ctBorder.setSz(BigInteger.valueOf(Long.parseLong(innerTableDataDTO.getBorderWidth())));
        }
    }

    /**
     * 设置内嵌子表列宽
     * @param table 内嵌子表所在表格
     * @param inCellTable 内嵌子表
     * @param tableData 内嵌子表对应数据
     * @param cellLocationRow 内嵌子表所在行
     * @param cellLocationCol 内嵌子表所在列
     * @param colNum 内嵌子表总列数
     */
    public static void setInCellTableColumnWidth(XWPFTable table, XWPFTable inCellTable, List<Map<String, Object>> tableData, int cellLocationRow, int cellLocationCol, int colNum) {
        // 内嵌子表所在单元格宽度
        double inCellWith = table.getRow(cellLocationRow).getCell(cellLocationCol).getWidthDecimal();
        // 缓存每个列宽度
        BigDecimal[] colWidths = new BigDecimal[colNum];

        // 每行数据设置的列宽一致，只需要从第一行数据解析即可
        Map<String, Object> fieldNameMap = tableData.get(0);
        Iterator<Map.Entry<String, Object>> iterator = fieldNameMap.entrySet().iterator();
        while (iterator.hasNext()) {
            String[] field = iterator.next().getKey().split("_");
            if(field.length != 2 && field.length != 3) {
                LOGGER.error("解析内嵌表格字段名失败，原因：xml字段名配置错误");
                throw new AppException("xml单元格嵌套子表字段名称配置错误，解析失败");
            }

            // 字段设置的宽度占据百分比
            int colVal = Integer.parseInt(field[1]);
            if(colVal > 100) {
                LOGGER.error("解析内嵌表格字段名失败，原因：列宽度设置超出100%");
                throw new AppException("xml单元格嵌套子表列宽设置错误，解析失败");
            }

            if(2 == field.length) {
                // 如果没有设置宽度则当前列默认占总列数等宽宽度: 所在单元格总宽度 / 列数
                colWidths[colVal] = new BigDecimal(inCellWith).divide(new BigDecimal(colNum), 4, BigDecimal.ROUND_HALF_UP);
            } else {
                // 设置了宽度百分比则计算实际宽度值： 百分比 * 所在单元格总宽度 / 100
                colWidths[colVal] = new BigDecimal(Double.parseDouble(field[2])).multiply(new BigDecimal(inCellWith)).divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP);
            }
        }

        CTTblGrid ctTblGrid = inCellTable.getCTTbl().addNewTblGrid();
        for (BigDecimal width : colWidths) {
            CTTblGridCol gridCol = ctTblGrid.addNewGridCol();
            gridCol.setW(width.toBigInteger());
        }
    }
}
