package cn.gtmap.realestate.common.core.support.pdf;

import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.pdf.service.OfficeDocService;
import cn.gtmap.realestate.common.core.support.pdf.service.OfficePdfService;
import cn.gtmap.realestate.common.util.RedisUtils;
import com.deepoove.poi.NiceXWPFDocument;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/10/24
 * @description Word、Pdf 处理控制器
 */
@RestController
@RequestMapping(value = "/office")
public class PdfController {
    /**
     * 日志操作
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PdfController.class);

    /**
     * 打印模板文件所在路径
     */
    @Value("${print.path:/usr/local/bdc3/print/}")
    private String path;

    /**
     * 文档对象操作
     */
    @Autowired
    private OfficeDocService officeDocService;
    /**
     * PDF文件处理
     */
    @Autowired
    private OfficePdfService officePdfService;

    @Autowired
    private cn.gtmap.realestate.common.util.FileUtils localFileUtils;

    @Autowired
    private RedisUtils redisUtils;


    /**
     * @param officeExportDTO 导出参数信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 导出word（所有的合成一个word文件，每个内容分页展示）
     */
    @GetMapping(value = "/word/export")
    public void exportWord(HttpServletResponse response, @ModelAttribute OfficeExportDTO officeExportDTO) {
        if (null == officeExportDTO) {
            throw new AppException("导出文件失败，原因：未传入打印有效参数!");
        }
        if (StringUtils.isBlank(officeExportDTO.getModelName())) {
            throw new AppException("导出文件失败，原因：未指定打印模板!");
        }
        if (StringUtils.isBlank(officeExportDTO.getXmlData())) {
            throw new AppException("导出文件失败，原因：未指定打印数据源!");
        }

        // 处理模板文件
        this.resolveModelFile(officeExportDTO);

        try (// 浏览器输出流
             OutputStream outputStream = response.getOutputStream();
             // 获取文档对象
             NiceXWPFDocument xwpfDocument = officeDocService.getXwpfDocument(officeExportDTO)) {

            // 设置文件名
            String fileName = StringUtils.isBlank(officeExportDTO.getFileName()) ? "批量WORD文件" : officeExportDTO.getFileName();
            String file = URLEncoder.encode(fileName + ".docx", "utf-8");

            // 浏览器下载
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + file);
            if (null != xwpfDocument) {
                xwpfDocument.write(outputStream);
                outputStream.flush();
            }
        } catch (Exception e) {
            LOGGER.error("系统导出WORD报错：{}", e.getMessage());
        }
    }

    /**
     * @param officeExportDTO 导出参数信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 导出pdf（所有的合成一个pdf文件，每个内容分页展示）
     */
    @GetMapping(value = "/pdf/export")
    public void exportPdf(HttpServletResponse response, @ModelAttribute OfficeExportDTO officeExportDTO) {
        if (null == officeExportDTO) {
            throw new AppException("导出文件失败，原因：未传入打印有效参数!");
        }
        if (StringUtils.isBlank(officeExportDTO.getModelName())) {
            throw new AppException("导出文件失败，原因：未指定打印模板!");
        }
        if (StringUtils.isBlank(officeExportDTO.getXmlData())) {
            throw new AppException("导出文件失败，原因：未指定打印数据源!");
        }

        // 处理模板文件
        this.resolveModelFile(officeExportDTO);

        // 浏览器输出流
        OutputStream outputStream = null;
        File pdfFile = null;
        try {
            outputStream = response.getOutputStream();

            // 生成PDF文件
            String pdfFileName = officePdfService.generatePdfFile(officeExportDTO);
            if (StringUtils.isBlank(pdfFileName)) {
                LOGGER.error("系统导出PDF失败，因为未成功生成PDF文件，目标文件名：{}", officeExportDTO.getFileName());
                throw new AppException("系统导出PDF失败，因为未成功生成PDF文件");
            }

            pdfFile = new File(pdfFileName);
            if (!pdfFile.exists() || pdfFile.length() == 0L || !pdfFile.canRead()) {
                LOGGER.error("系统导出PDF失败，目标PDF文件不可操作或无内容：{}", pdfFileName);
                throw new AppException("系统导出PDF失败，目标PDF文件不可操作或无内容");
            }

            // 设置浏览器下载
            String fileName = StringUtils.isBlank(officeExportDTO.getFileName()) ? "批量PDF文件" : officeExportDTO.getFileName();
            String file = URLEncoder.encode(fileName + ".pdf", "utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + file);

            // 读取临时pdf文件，浏览器下载
            FileUtils.copyFile(pdfFile, outputStream);

            if(StringUtils.isNotBlank(officeExportDTO.getLocalFile())) {
                localFileUtils.uploadPdfFileToStorage(pdfFile, officeExportDTO.getLocalFile());
            }
        } catch (Exception e) {
            LOGGER.error("系统导出PDF报错：{}", e.toString());
            throw new AppException("系统导出PDF报错，处理终止");
        } finally {
            IOUtils.closeQuietly(outputStream);
            org.apache.commons.io.FileUtils.deleteQuietly(pdfFile);
        }
    }

    /**
     * @param officeExportDTO 导出参数信息
     * @return 生成的PDF路径
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 生成pdf文件（所有的合成一个pdf文件，每个内容分页展示）
     */
    @PostMapping(value = "/pdf/file")
    public String generatePdfFile(@RequestBody OfficeExportDTO officeExportDTO) {
        if (null == officeExportDTO) {
            throw new AppException("导出文件失败，原因：未传入打印有效参数!");
        }
        if (StringUtils.isBlank(officeExportDTO.getModelName())) {
            throw new AppException("导出文件失败，原因：未指定打印模板!");
        }
        if (StringUtils.isBlank(officeExportDTO.getXmlData())) {
            throw new AppException("导出文件失败，原因：未指定打印数据源!");
        }

        // 处理模板文件
        this.resolveModelFile(officeExportDTO);

        return officePdfService.generatePdfFile(officeExportDTO);
    }

    /**
     * 下载PDF，浏览器预览
     * @param fileName 文件名称
     */
    @GetMapping(value = "/download")
    public void exportPdf(HttpServletResponse response, @RequestParam("fileName")String fileName) {
        if(StringUtils.isBlank(fileName)) {
            throw new MissingArgumentException("下载文件失败，未指定下载文件路径");
        }
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            // 这里设置文件预览
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;filename=1.pdf");
            IOUtils.write(localFileUtils.getPdfStorageDownloadUrl(fileName), outputStream);
        } catch (Exception e) {
            LOGGER.error("下载文件失败", e);
            throw new AppException("下载文件失败，请重试");
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    /**
     * @param officeExportDTO 参数信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 处理模板文件
     * <p>
     * 模板文件可能是磁盘文件，也可能是远程平台文件，对于远程文件需要先下载文件
     */
    private void resolveModelFile(OfficeExportDTO officeExportDTO) {
        String filePath = officeExportDTO.getModelName();
        if (StringUtils.isNotBlank(filePath)) {
            filePath = filePath.trim();
        }
        if (!isHttpUrl(filePath)) {
            // 本地文件
            File diskFile = new File(filePath);
            if (!diskFile.exists()) {
                LOGGER.error("系统导出PDF失败，因为未找到本地模板文件：{}", officeExportDTO.getModelName());
                throw new AppException("系统导出PDF报错，处理终止");
            } else {
                // 存在不需要任何处理，后续直接读取即可
            }
        } else {
            // 远程文件
            /// 将URL最后的分隔内容作为文件标识
            String[] urlArr = filePath.split("/");
            if (null == urlArr) {
                LOGGER.error("系统导出PDF失败，因为未找到本地模板文件：{}", officeExportDTO.getModelName());
                throw new AppException("系统导出PDF报错，处理终止");
            }

            /// 判断是否已经下载到本地了
            String urlFileName = urlArr[urlArr.length - 1] + ".docx";
            File diskFile = new File(path + urlFileName);
            if (!diskFile.exists()) {
                /// 不存在直接下载
                try {
                    FileUtils.copyURLToFile(new URIBuilder(filePath).build().toURL(), diskFile, 5000, 30000);
                } catch (Exception e) {
                    LOGGER.error("系统导出PDF失败，因为模板文件下载失败：{}, {}", officeExportDTO.getModelName(), e.getMessage());
                    throw new AppException("系统导出PDF报错，处理终止");
                }
            } else {
                /// 存在不需要任何处理，后续直接读取即可
            }
            officeExportDTO.setModelName(diskFile.getAbsolutePath());
        }
    }

    /**
     * @param urls 需要判断的String类型url
     * @return true:是URL；false:不是URL
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 简单判断字符串是否为URL
     */
    private static boolean isHttpUrl(String urls) {
        String regex = "[a-zA-z]+://[^\\s]*";
        Pattern pat = Pattern.compile(regex.trim());
        Matcher mat = pat.matcher(urls.trim());
        return mat.matches();
    }
}