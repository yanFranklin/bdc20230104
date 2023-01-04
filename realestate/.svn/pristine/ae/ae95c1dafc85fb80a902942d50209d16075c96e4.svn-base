package cn.gtmap.realestate.common.core.support.pdf.service.impl;

import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.pdf.service.OfficeDataService;
import cn.gtmap.realestate.common.core.support.pdf.service.OfficeDocService;
import cn.gtmap.realestate.common.core.support.pdf.service.OfficePdfService;
import cn.gtmap.realestate.common.core.support.pdf.service.impl.thread.PdfTask;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.office.OfficeUtil;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.docx4j.fonts.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/01/06
 * @description PDF相关数据处理逻辑
 */
@Service
public class OfficePdfServiceImpl implements OfficePdfService {
    /**
     * 日志操作
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OfficePdfServiceImpl.class);

    /**
     * 字体存放目录，同打印模板同一目录
     */
    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;

    /**
     * 字体
     */
    private Mapper fontMapper;

    /**
     * 文档对象操作
     */
    @Autowired
    private OfficeDocService officeDocService;

    /**
     * 数据处理
     */
    @Autowired
    private OfficeDataService officeDataService;

    /**
     * PDF任务处理共享线程池定义
     */
    private ExecutorService executor = new ThreadPoolExecutor(
            // 核心线程数量
            4,
            // 最大线程数
            6,
            // 超时30秒
            30, TimeUnit.SECONDS,
            // 最大允许等待200个任务
            new ArrayBlockingQueue<>(200),
            // 过多任务直接主线程处理
            new ThreadPoolExecutor.CallerRunsPolicy()
    );


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 构造文件操作临时目录
     */
    @PostConstruct
    public void init(){
        // 创建PDF处理临时目录
        File file = new File(printPath + "temp/");
        if(!file.exists()){
            file.mkdirs();
        }

        // 加载字体
        fontMapper = OfficeUtil.getFontMapper(printPath);

        // 初始加载
        this.initLoadFile();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 初始预加载一下，避免实际操作慢
     */
    private void initLoadFile(){
        String pdfFile = OfficeUtil.generatePdfFileName(printPath);

        try {
            OfficeUtil.convertDocxToPDF(String.valueOf(CommonConstantUtils.SF_F_DM), printPath + "/File.docx", pdfFile, fontMapper, printPath, "");
        } catch (Exception e) {
            LOGGER.warn("PDF处理加载临时文件失败，没有配置{}文件！", printPath + "File.docx");
        } finally {
            File file = new File(pdfFile);
            if(file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param officeExportDTO 导出所需参数信息
     * @description 生成PDF文档（多页合并成一个）
     *
     *  注意：调用端需要考虑是否删除生成的临时PDF文件
     */
    @Override
    public String generatePdfFile(OfficeExportDTO officeExportDTO) {
        try{
            // 解析打印数据（数据量过大，XML解析时候可能会大量占用内容，需要相关模块优化）
            List<Map<String, Object>> dataList = officeDataService.getValDataList(officeExportDTO.getXmlData());
            if(CollectionUtils.isEmpty(dataList)){
                return null;
            }

            int dataSize = dataList.size();
            LOGGER.debug("导出PDF文档：{}，共{}页", officeExportDTO.getFileName() ,dataSize);

            BlockingQueue queue = ((ThreadPoolExecutor)executor).getQueue();
            List<Future<String>> tasksList = new ArrayList<>(dataSize);

            for(int index = 0; index < dataSize; index++){
                int freeCount = queue.remainingCapacity();

                // 数量控制，避免大批量OOM
                if(freeCount <= 10){
                    LOGGER.debug("导出PDF线程池队列剩余空间较少，开始等待提交任务，文件名：{}", officeExportDTO.getFileName());
                    long start = System.currentTimeMillis();
                    do{
                        Thread.sleep(500);
                    }while (queue.remainingCapacity() <= 10 || System.currentTimeMillis() - start < 5000);
                }

                // 每个线程任务生成一个word，再转pdf
                LOGGER.debug("导出PDF处理第{}个子任务提交", index + 1);
                PdfTask pdfTask = new PdfTask(officeDocService, dataList.get(index), index, officeExportDTO.getModelName(), printPath, fontMapper);
                tasksList.add(executor.submit(pdfTask));
            }

            // 合并PDF文件
            LOGGER.debug("PDF导出中间临时文件处理完毕，开始合并文件:{}！", officeExportDTO.getFileName());
            Map<String, String> pdfFileInfoMap = new HashMap<>(tasksList.size());
            for(int index = 0; index < tasksList.size(); index++) {
                pdfFileInfoMap.put(String.valueOf(index), tasksList.get(index).get());
            }
            return this.mergePdfFiles(pdfFileInfoMap);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new AppException("生成的临时PDF文件报错，处理终止, 异常原因：" + e.toString());
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pdfFiles 要合并的PDF文件
     * @return 临时文件地址
     * @description 合并多个PDF文件为一个
     */
    @Override
    public String mergePdfFiles(Map<String, String> pdfFiles) {
        if(MapUtils.isEmpty(pdfFiles)){
            LOGGER.error("合并PDF失败，原因：未指定要合并的PDF文件！");
            throw new AppException("合并PDF失败，原因：未指定要合并的PDF文件！");
        }

        String pdfFileName = OfficeUtil.generatePdfFileName(printPath);
        com.itextpdf.text.Document document = null;
        FileOutputStream fileOutputStream = null;
        PdfReader pdfReader = null;

        try {
            // 新建Document，设置纸张大小，根据随便一个PDF文档大小设置
            pdfReader = new PdfReader(pdfFiles.get("0"));
            document = new com.itextpdf.text.Document(pdfReader.getPageSize(1));
            fileOutputStream = new FileOutputStream(pdfFileName);
            PdfCopy copy = new PdfCopy(document, fileOutputStream);
            document.open();

            // 遍历需要保证取出按照文档顺序
            for(int index = 0; index < pdfFiles.size(); index++){
                // 每一份PDF
                PdfReader reader = new PdfReader(pdfFiles.get(String.valueOf(index)));
                // 处理每一页（需要注意索引从1开始）
                for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                    document.newPage();
                    copy.addPage(copy.getImportedPage(reader, i));
                }
                reader.close();
            }
            copy.close();
            LOGGER.debug("PDF导出合并文件处理完毕！");

            return pdfFileName;
        } catch (Exception e) {
            throw new AppException("合并PDF失败，原因：" + e.toString());
        } finally {
            // 依次关闭相关连接，避免应用文件句柄占用
            if(null != pdfReader) {
                pdfReader.close();
            }

            if(null != document){
                document.close();
            }

            if(null != fileOutputStream){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    LOGGER.error("系统导出PDF关闭资源报错：{}", e.getMessage());
                }
            }

            // 删除临时pdf
            if(MapUtils.isNotEmpty(pdfFiles)){
                for(Map.Entry<String, String> entry : pdfFiles.entrySet()){
                    File pdfFile = new File(entry.getValue());
                    if(pdfFile.exists()){
                        pdfFile.delete();
                    }
                }
                LOGGER.debug("系统导出PDF合并文件删除临时文件完成！");
            }
        }
    }
}
