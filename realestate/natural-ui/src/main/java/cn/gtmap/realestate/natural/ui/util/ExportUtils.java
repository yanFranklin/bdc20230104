package cn.gtmap.realestate.natural.ui.util;

import jxl.Workbook;
import jxl.write.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0, 2019/10/06
 * @description 导出工具
 */
public class ExportUtils {
    private static final String[] convertList = new String[]{"dzwyt", "qllx", "qlxz", "fwjg", "fwxz", "fwlx", "dyfs"};

    private static final String[] dateList = new String[]{"tdsyqssj", "tdsyjssj"};

    /**
     * @param
     * @return
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 导出文件
     */
    public static void exportExcel(String fileName, LinkedHashMap<String, String> exportCol,
                                   List<Map> exportData, HttpServletResponse response) throws WriteException, IOException {
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
        String sheetName = StringUtils.split(fileName, "Excel")[0];
        WritableSheet sheet = workbook.createSheet(sheetName, 0);

        // 标题样式
        // 粗体
        WritableFont boldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
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
        WritableFont normalFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
        WritableCellFormat wcfLeft = new WritableCellFormat(normalFont);
        // 边框
        wcfLeft.setBorder(jxl.format.Border.NONE, jxl.format.BorderLineStyle.THIN);
        // 竖向居中对齐
        wcfLeft.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        // 横向居中对齐
        wcfLeft.setAlignment(jxl.format.Alignment.CENTRE);
        // 不自动换行
        wcfLeft.setWrap(true);

        // 写入标题行
        int a = 0;
        for (String name : exportCol.keySet()) {
            sheet.addCell(new Label(a, 0, name, wcfCenter));
            sheet.setColumnView(a, 20);
            a++;
        }
        // 写入列表数据
        int rownum = 1;
        for (Map<String, Object> dataMap : exportData) {
            int colnum = 0;
            Iterator iter = exportCol.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object value = entry.getValue();
                final Object cellValue = Optional.ofNullable(dataMap.get(value)).orElse("");
                if(StringUtils.equals(value.toString(), "xh")){
                    sheet.addCell(new Label(colnum, rownum, String.valueOf(rownum), wcfLeft));
                } else if (cellValue instanceof Date) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sheet.addCell(new Label(colnum, rownum, sdf.format(cellValue), wcfLeft));
                } else if(cellValue instanceof Long && ArrayUtils.contains(dateList, value)){
                    if(Objects.nonNull(cellValue)){
                        Date date = new Date((long) cellValue);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                        sheet.addCell(new Label(colnum, rownum, sdf.format(date), wcfLeft));
                    }else{
                        sheet.addCell(new Label(colnum, rownum, "", wcfLeft));
                    }
                } else if(cellValue instanceof Long && StringUtils.equals("time",value.toString())){
                    if(Objects.nonNull(cellValue)){
                        Date date = new Date((long) cellValue);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
                        sheet.addCell(new Label(colnum, rownum, sdf.format(date), wcfLeft));
                    }else{
                        sheet.addCell(new Label(colnum, rownum, "", wcfLeft));
                    }
                } else {
                    sheet.addCell(new Label(colnum, rownum, String.valueOf(cellValue), wcfLeft));
                }
                colnum++;
            }
            rownum++;
        }
        workbook.write();
        workbook.close();
    }

    private static String getMcByDm(Map<String, List<Map>> zdListMap, Object key, String dmvalue) {
        String mcvalue = dmvalue;
        if (MapUtils.isNotEmpty(zdListMap)) {
            List<Map> zdList = zdListMap.get(key);
            if (CollectionUtils.isNotEmpty(zdList)) {
                for (Map zd : zdList) {
                    if (StringUtils.equals(MapUtils.getString(zd, "DM"), dmvalue)) {
                        mcvalue = MapUtils.getString(zd, "MC");
                        break;
                    }
                }
            }
        }
        return mcvalue;
    }


}
