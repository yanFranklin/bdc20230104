package cn.gtmap.realestate.common.core.support.pdf.service.impl.thread;

import cn.gtmap.realestate.common.core.support.pdf.service.OfficeDocService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.office.OfficeUtil;
import com.deepoove.poi.NiceXWPFDocument;
import org.apache.commons.io.IOUtils;
import org.docx4j.fonts.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/01/07
 * @description 单个PDF文档处理线程任务
 */
public class PdfTask implements Callable<String> {
    /**
     * 日志操作
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PdfTask.class);
    /**
     * 文档处理逻辑
     */
    private OfficeDocService officeDocService;
    /**
     * 数据内容
     */
    private Map<String, Object> pdfData;
    /**
     * 文档顺序号
     */
    private int index;
    /**
     * 文件临时操作父目录，同打印模板同一目录
     */
    private String printPath;
    /**
     * 模板名称
     */
    private String modelName;
    /**
     * 字体
     */
    private Mapper fontMapper;


    public PdfTask(OfficeDocService officeDocService,
                   Map<String, Object> pdfData,
                   int index,
                   String modelName,
                   String printPath,
                   Mapper fontMapper){

        this.officeDocService = officeDocService;
        this.printPath = printPath;
        this.pdfData = pdfData;
        this.index = index;
        this.modelName = modelName;
        this.fontMapper = fontMapper;
    }

    @Override
    public String call() throws Exception {
        LOGGER.debug("PDF导出正在处理第{}份文件！", index + 1);

        // 生成临时word
        String tempWordFileName = OfficeUtil.generateWordFileName(printPath);
        // 生成临时pdf
        String tempPdfFileName = OfficeUtil.generatePdfFileName(printPath);

        FileOutputStream wordFileOutStream = null;
        NiceXWPFDocument document = null;
        try{
            // 生成word
            wordFileOutStream = new FileOutputStream(new File(tempWordFileName));
            document = officeDocService.getNiceXWPFDocument(modelName, pdfData);
            document.write(wordFileOutStream);

            // 转换为pdf
            OfficeUtil.convertDocxToPDF(String.valueOf(pdfData.get(CommonConstantUtils.PDF_FWTP)), tempWordFileName, tempPdfFileName, fontMapper, printPath, modelName);

            // 添加水印
            OfficeUtil.addWaterMarkConfigToPdfFile(tempPdfFileName, pdfData, printPath);
            //添加图片水印（平铺背景）
            OfficeUtil.addPicWaterMarkConfigToPdfFile(tempPdfFileName,pdfData, printPath);
            //添加签章
            OfficeUtil.addQzPicConfigToPdfFile(tempPdfFileName, pdfData,printPath);
            LOGGER.debug("当前处理第{}页文档完成", index + 1);
            return tempPdfFileName;
        }
        finally {
            IOUtils.closeQuietly(wordFileOutStream);

            if(null != document) {
                document.close();
            }

            File wordFile = new File(tempWordFileName);
            if(wordFile.exists()){
                wordFile.delete();
            }
        }
    }
}