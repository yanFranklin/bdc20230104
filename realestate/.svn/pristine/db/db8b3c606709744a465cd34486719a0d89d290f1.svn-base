package cn.gtmap.realestate.inquiry.ui.util;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcHzdytjDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcYbbdytjDTO;
import jxl.write.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2018/12/13
 * @description
 */
public final class ExportExcelUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportExcelUtils.class);

    private ExportExcelUtils() {
    }

    /**
     * 设置标题单元格格式
     *
     * @return 标题格式
     * @throws WriteException
     */
    public static WritableCellFormat titleFormat() throws WriteException {
        // 标题样式
        // 粗体
        WritableFont boldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat wcfCenter = new WritableCellFormat(boldFont);
        // 边框
        wcfCenter.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        // 竖向居中对齐
        wcfCenter.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        // 横向居中对齐
        wcfCenter.setAlignment(jxl.format.Alignment.CENTRE);
        // 不自动换行
        wcfCenter.setWrap(false);

        return wcfCenter;
    }

    /**
     * 设置内容单元格格式
     *
     * @return 内容格式
     * @throws WriteException
     */
    public static WritableCellFormat contentFormat() throws WriteException {
        // 正文样式
        // 字体
        WritableFont normalFont = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat wcfLeft = new WritableCellFormat(normalFont);
        // 边框
        wcfLeft.setBorder(jxl.format.Border.NONE, jxl.format.BorderLineStyle.THIN);
        // 竖向居中对齐
        wcfLeft.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        // 横向居中对齐
        wcfLeft.setAlignment(jxl.format.Alignment.LEFT);
        // 不自动换行
        wcfLeft.setWrap(false);

        return wcfLeft;
    }

    /**
     * 通过导出的实体类写入sheet页
     *
     * @param exportDataList
     * @param sheet
     * @param titleFormat
     * @param contentFormat
     * @throws WriteException
     * @throws IllegalAccessException
     */
    public static void writeSheetByClass(List exportDataList, WritableSheet sheet, WritableCellFormat titleFormat, WritableCellFormat contentFormat)
            throws WriteException {
        if (exportDataList != null && !exportDataList.isEmpty()) {
            Object exportData = exportDataList.get(0);
            Class exportDataClass = exportData.getClass();
            Field[] fieldExportDataList = exportDataClass.getDeclaredFields();
            // 写入标题行 将列名存入标题行中
            for (int i = 0; i < fieldExportDataList.length; i++) {
                sheet.addCell(new Label(i, 0, fieldExportDataList[i].getName(), titleFormat));
                fieldExportDataList[i].setAccessible(true);
            }
            try {
                writeSheetContentByClass(exportDataList, fieldExportDataList, sheet, contentFormat);
            } catch (IllegalAccessException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 通过导出的Map写入sheet页
     *
     * @param titleList
     * @param contentList
     * @param sheet
     * @param titleFormat
     * @param contentFormat
     * @throws WriteException
     */
    public static void writeSheetByMap(List<String> titleList, List<Map> contentList, WritableSheet sheet, WritableCellFormat titleFormat, WritableCellFormat contentFormat)
            throws WriteException {
        if (titleList != null && !titleList.isEmpty() && sheet != null) {
            for (int i = 0; i < titleList.size(); i++) {
                String title = titleList.get(i);
                sheet.addCell(new Label(i, 0, title, titleFormat));
            }

            // 写入列表数据
            int i = 1;
            for (Map<String, Object> map : contentList) {
                int j = 0;
                for (String title : titleList) {
                    String content = "";
                    if (map.get(title) != null) {
                        content = map.get(title).toString();
                    }
                    sheet.addCell(new Label(j, i, content, contentFormat));
                    j++;
                }
                i++;
            }
        }
    }

    private static void writeSheetContentByClass(List exportDataList, Field[] fieldExportDataList, WritableSheet sheet, WritableCellFormat contentFormat) throws IllegalAccessException, WriteException {
        // 写入内容 将每行数据存入其中
        for (int i = 0; i < exportDataList.size(); i++) {
            Object exportData = exportDataList.get(i);
            for (int j = 0; j < fieldExportDataList.length; j++) {
                if (fieldExportDataList[j].get(exportData) != null) {
                    sheet.addCell(new Label(j, i + 1, fieldExportDataList[j].get(exportData).toString(), contentFormat));
                }
            }
        }
    }

    /**
     * 汇总抵押统计导出excel
     * @param filePath
     * @param bdcHzdytjDTO
     * @param bdcYbbdytjDTO
     */
    public static void exportHzdytj(String filePath, BdcHzdytjDTO bdcHzdytjDTO, BdcYbbdytjDTO bdcYbbdytjDTO){
        String pdfOpfile = filePath+ "model.xlsx";
        File file = new File(pdfOpfile);
        FileOutputStream out = null;
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheet = wb.getSheetAt(0); // 获取到工作表，因为一个excel可能有多个工作表
            XSSFSheet sheet2 = wb.getSheetAt(1); // 获取到工作表，因为一个excel可能有多个工作表
            out =  new FileOutputStream(pdfOpfile);

//            XSSFFont font = wb.createFont();
//            font.setFontHeightInPoints((short) 40);
//            font.setFontHeight((short) 40);
//            CellStyle cellStyle = wb.createCellStyle();

            // 第一个sheet也的值
            XSSFRow row  = sheet.createRow((short) (5));
            row.setHeight((short)600);

            //sheet.removeRow(row);
            row.createCell(0).setCellValue("南通市");
            row.createCell(1).setCellValue("市本级");
            row.createCell(2).setCellValue(bdcHzdytjDTO.getCtdcount());
            row.createCell(3).setCellValue(bdcHzdytjDTO.getCtdmj());
            row.createCell(4).setCellValue(bdcHzdytjDTO.getCtddyje());
            row.createCell(5).setCellValue(bdcHzdytjDTO.getCtdavgjemj());

            row.createCell(6).setCellValue(bdcHzdytjDTO.getZjgccount());
            row.createCell(7).setCellValue(bdcHzdytjDTO.getZjgcmj());
            row.createCell(8).setCellValue(bdcHzdytjDTO.getZjgcdyje());
            row.createCell(9).setCellValue(bdcHzdytjDTO.getZjgcavg());

            row.createCell(10).setCellValue(bdcHzdytjDTO.getFdytcount());
            row.createCell(11).setCellValue(bdcHzdytjDTO.getFdytmj());
            row.createCell(12).setCellValue(bdcHzdytjDTO.getFdytdyje());
            row.createCell(13).setCellValue(bdcHzdytjDTO.getFdytavg());
            //row.setRowStyle(cellStyle);
            // 第一个sheet也的值
            XSSFRow row2  = sheet2.createRow((short) (5));
            row2.setHeight((short)600);
            //row2.setRowStyle(cellStyle);
            //sheet2.removeRow(row2);
            row2.createCell(0).setCellValue("南通市");
            row2.createCell(1).setCellValue("市本级");
            row2.createCell(2).setCellValue(bdcYbbdytjDTO.getGylcount());
            row2.createCell(3).setCellValue(bdcYbbdytjDTO.getGylmj());
            row2.createCell(4).setCellValue(bdcYbbdytjDTO.getGylzj());
            row2.createCell(5).setCellValue(bdcYbbdytjDTO.getGyljg());
            row2.createCell(6).setCellValue(bdcYbbdytjDTO.getGyldyje());

            row2.createCell(7).setCellValue(bdcYbbdytjDTO.getZzlcount());
            row2.createCell(8).setCellValue(bdcYbbdytjDTO.getZzlzj());
            row2.createCell(9).setCellValue(bdcYbbdytjDTO.getZzljg());
            row2.createCell(10).setCellValue(bdcYbbdytjDTO.getZzldyje());
            if (wb != null) {
                wb.write(out);
            }
            if (out != null) {
                out.close();
            }
        }catch (Exception e){
            LOGGER.error("往excel表中写入数据异常：" + e.getMessage());
        }finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.error("流关闭异常");
                }
            }
        }

    }
}
