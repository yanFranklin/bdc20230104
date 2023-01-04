package cn.gtmap.realestate.check.utils;

import com.alibaba.fastjson.JSONObject;
import jxl.Workbook;
import jxl.write.*;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author ccx 2019/1/14
 * @description
 */
@Component
public class ExcelUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 导出  此方法并不通用，考虑到check导出可能没那么多，后期需要提取公共方法在处理
     * @param fileName
     * @param exportColumn
     * @param exportList
     * @param response
     * @throws WriteException
     * @throws IOException
     */
    public static void exportExcel(String fileName, Map<String,Object> exportColumn, List<Map<String, Object>> exportList, HttpServletResponse response) throws WriteException, IOException {
        LOGGER.info("exportExcel start");
        // 取得输出流
        OutputStream os = response.getOutputStream();
        response.reset();
        // 设置文件头
        response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
        // 设置输出类型
        response.setContentType("application/msexcel");
        // 创建excel
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        // 创建sheet
        WritableSheet sheet = workbook.createSheet("Sheet1", 0);
        sheet.setColumnView(0, 10);
        sheet.setColumnView(1, 35);
        sheet.setColumnView(2, 35);
        sheet.setColumnView(3, 30);
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

        // 正文样式
        // 字体
        WritableFont normalFont = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat wcfLeft = new WritableCellFormat(normalFont);
        // 边框
        wcfLeft.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        // 竖向居中对齐
        wcfLeft.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        // 横向居中对齐
        wcfLeft.setAlignment(jxl.format.Alignment.CENTRE);
        // 自动换行
        wcfLeft.setWrap(true);

        // 写入标题行
        int a = 0;
        for (String name : exportColumn.keySet()) {
            sheet.addCell(new Label(a, 0, name, wcfCenter));
            a++;
        }

        // 写入列表数据
        int rownum = 1;
        int rownumnew = 1;
        for (int i=0;i<exportList.size();i++) {
            Map<String,String> cxjgMap = JSONObject.parseObject(JSONObject.toJSONString(exportList.get(i)),Map.class);
            int colnum = 0;
            Iterator iter = exportColumn.entrySet().iterator();
            while (iter.hasNext()){
                Map.Entry entry = (Map.Entry) iter.next();
                Object value = entry.getValue();
                String cellValue = MapUtils.getString(cxjgMap,value);
                if(colnum == 0){
                    String oldCellValue = sheet.getCell(0,rownum-1).getContents();
                    if(!oldCellValue.equals(cellValue)){
                        if(rownumnew != rownum){
                            sheet.mergeCells(0, rownumnew, 0, rownum);
                        }
                        rownumnew++;
                    }else if(i==exportList.size()-1){
                        sheet.mergeCells(0, rownumnew-1, 0, rownum);
                    }
                }
                sheet.addCell(new Label(colnum, rownum, cellValue, wcfLeft));
                colnum++;
            }
            rownum++;
        }
        workbook.write();
        workbook.close();
    }
    /**
     * 导出  此方法并不通用，考虑到check导出可能没那么多，后期需要提取公共方法在处理
     * @param fileName
     * @param exportColumn
     * @param exportList
     * @param response
     * @throws WriteException
     * @throws IOException
     */
    public static void exportExcelStandard(String fileName, Map<String,Object> exportColumn, List<Map<String, Object>> exportList, HttpServletResponse response) throws WriteException, IOException {
        LOGGER.info("exportExcel start");
        // 取得输出流
        OutputStream os = response.getOutputStream();
        response.reset();
        // 设置文件头
        response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
        // 设置输出类型
        response.setContentType("application/msexcel");

        // 创建excel
        WritableWorkbook workbook = Workbook.createWorkbook(os);

        // 创建sheet
        WritableSheet sheet = workbook.createSheet("Sheet1", 0);
        sheet.setColumnView(0, 30);
        sheet.setColumnView(1, 20);
        sheet.setColumnView(2, 20);
        sheet.setColumnView(3, 20);
        sheet.setColumnView(4, 24);
        sheet.setColumnView(5, 15);
        sheet.setColumnView(6, 15);
        sheet.setColumnView(7, 60);
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

        // 正文样式
        // 字体
        WritableFont normalFont = new WritableFont(WritableFont.ARIAL, 10);
        WritableCellFormat wcfLeft = new WritableCellFormat(normalFont);
        // 边框
        wcfLeft.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        // 竖向居中对齐
        wcfLeft.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        // 横向居中对齐
        wcfLeft.setAlignment(jxl.format.Alignment.CENTRE);
        // 自动换行
        wcfLeft.setWrap(false);

        // 写入标题行
        int a = 0;
        for (String name : exportColumn.keySet()) {
            sheet.addCell(new Label(a, 0, name, wcfCenter));
            a++;
        }

        // 写入列表数据
        int rownum = 1;
        for (Map cxjg : exportList) {
            Map<String,String> cxjgMap = JSONObject.parseObject(JSONObject.toJSONString(cxjg),Map.class);
            int colnum = 0;
            Iterator iter = exportColumn.entrySet().iterator();
            while (iter.hasNext()){
                Map.Entry entry = (Map.Entry) iter.next();
                Object value = entry.getValue();
                String cellValue = MapUtils.getString(cxjgMap,value);
                sheet.addCell(new Label(colnum, rownum, cellValue, wcfLeft));
                colnum++;
            }
            rownum++;
        }
        workbook.write();
        workbook.close();
    }
}
