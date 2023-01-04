package cn.gtmap.realestate.exchange.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by chenyongqiang on 2020-3-24.
 */
public class ExportExcelUtil {

    public static void exportExcelBdcDsfLog(HttpServletResponse response, List<JSONObject> flList, List<JSONObject> dataList, Map zdMap){
        try(OutputStream os = response.getOutputStream()){

            String fileName = URLEncoder.encode("第三方子系统日志导出详情.xls", "utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            //创建HSSFWorkbook工作薄对象
            HSSFWorkbook workbook=new HSSFWorkbook();
            //创建HSSFSheet对象
            HSSFSheet sheet=workbook.createSheet("sheet1");

            //加粗字体样式
            HSSFCellStyle boldStyle = workbook.createCellStyle();
            HSSFFont boldFont = workbook.createFont();
            boldFont.setFontName("宋体");//设置字体名称
            boldFont.setBold(true);
            boldFont.setFontHeightInPoints((short)10);//设置字号
            boldStyle.setFont(boldFont);
            boldStyle.setBorderBottom(BorderStyle.THIN); //下边框
            boldStyle.setBorderLeft(BorderStyle.THIN);//左边框
            boldStyle.setBorderTop(BorderStyle.THIN);//上边框
            boldStyle.setBorderRight(BorderStyle.THIN);//右边框
            boldStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
            boldStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            boldStyle.setWrapText(true);

            //正常字体样式
            HSSFCellStyle normalStyle = workbook.createCellStyle();
            HSSFFont normalFont = workbook.createFont();
            normalFont.setFontName("宋体");//设置字体名称
            normalFont.setFontHeightInPoints((short)10);//设置字号
            normalStyle.setFont(boldFont);
            normalStyle.setBorderBottom(BorderStyle.THIN); //下边框
            normalStyle.setBorderLeft(BorderStyle.THIN);//左边框
            normalStyle.setBorderTop(BorderStyle.THIN);//上边框
            normalStyle.setBorderRight(BorderStyle.THIN);//右边框
            normalStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
            normalStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            //第一部分 展示头信息
            //标题一
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell=row.createCell(0);
            //cell.setCellValue("分中心请求记录统计表");
            cell.setCellStyle(boldStyle);

            if (CollectionUtils.isNotEmpty(flList)) {
                //创建第二行
                HSSFRow rowSec = sheet.createRow(1);
                //创建第三行
                HSSFRow rowThi = sheet.createRow(2);
                //创建第四行
                HSSFRow rowFou = sheet.createRow(3);
                int c = 0;
                cell=rowSec.createCell(c);
//                cell.setCellValue("");
                cell.setCellStyle(boldStyle);

                c += 1;
                cell = rowSec.createCell(c);
                cell.setCellValue("登记部门名称");
                cell.setCellStyle(boldStyle);

                //标题二
                c += 1;
                cell=rowSec.createCell(c);
                //cell.setCellValue("共享部门");
                cell.setCellStyle(boldStyle);

                cell=rowThi.createCell(c);
                cell.setCellValue("是否接入政府数据共享交换平台(或大数据中心等)");
                cell.setCellStyle(boldStyle);

                //创建头信息栏
                c += 1;
                for (JSONObject job : flList) {
                    cell=rowThi.createCell(c);
                    cell.setCellValue(getZdMc("gxbmbz", job.getString("GXBMBZ"), zdMap));
                    cell.setCellStyle(boldStyle);

                    List<String> jkmcList = (List<String>)job.get("JKMC");
                    if (CollectionUtils.isNotEmpty(jkmcList)) {
                        int j = c;
                        for (String jkmc : jkmcList) {
                            cell = rowFou.createCell(j);
                            cell.setCellValue(jkmc);
                            cell.setCellStyle(boldStyle);
                            j++;
                        }

                        //给第二行合并的单元格之前加边框
                        for (int n = c + 1; n < j; n++) {
                            cell=rowThi.createCell(n);
                            //cell.setCellValue("");
                            cell.setCellStyle(boldStyle);
                        }

                        if (j - 1 > c) {
                            CellRangeAddress cellRangeAddressSec = new CellRangeAddress(2, 2, c, j - 1);
                            sheet.addMergedRegion(cellRangeAddressSec);
                        }

                        c = j;
                    }
                }

                //给第一行合并的单元格加边框
                for (int i = 1; i < c; i++) {
                    cell=row.createCell(i);
                    //cell.setCellValue("");
                    cell.setCellStyle(boldStyle);
                }
                //合并第一行标题
                CellRangeAddress rowAddressOne = new CellRangeAddress(0, 0, 0, c-1);
                sheet.addMergedRegion(rowAddressOne);

                //给第二行合并的单元格加边框
                for (int i = 3; i < c; i++) {
                    cell=rowSec.createCell(i);
                    //cell.setCellValue("");
                    cell.setCellStyle(boldStyle);
                }
                //合并第二行标题
                CellRangeAddress rowAddressSec = new CellRangeAddress(1, 1, 2, c-1);
                sheet.addMergedRegion(rowAddressSec);

                //给第三、四行合并的单元格加边框
                for (int i = 0; i < 2; i++) {
                    cell=rowThi.createCell(i);
                    //cell.setCellValue("");
                    cell.setCellStyle(boldStyle);

                    cell =rowFou.createCell(i);
                    //cell.setCellValue("");
                    cell.setCellStyle(boldStyle);
                }

                //合并第一列，第二行到第四行
                CellRangeAddress colAddressSec = new CellRangeAddress(1, 3, 0, 0);
                sheet.addMergedRegion(colAddressSec);

                //合并第二列，第二行到第四行
                CellRangeAddress colAddressThi = new CellRangeAddress(1, 3, 1, 1);
                sheet.addMergedRegion(colAddressThi);

                //给第四行合并的单元格加边框
                cell =rowFou.createCell(2);
                //cell.setCellValue("");
                cell.setCellStyle(boldStyle);
                //合并第三列，第三行到第四行
                CellRangeAddress colAddressFou = new CellRangeAddress(2, 3, 2, 2);
                sheet.addMergedRegion(colAddressFou);

                //第二部分 将查询出的数据展示
                if (CollectionUtils.isNotEmpty(dataList)) {
                    int rowCount = 4;
                    for (JSONObject job : dataList) {
                        //起始列索引
                        int colCount = 0;
                        HSSFRow r = sheet.createRow(rowCount);
                        cell = r.createCell(colCount);
                        //cell.setCellValue(getZdMc("djbmdm", job.getString("DJBMDM"), zdMap));
//                        cell.setCellValue("");
                        cell.setCellStyle(normalStyle);

                        colCount += 1;
                        cell = r.createCell(colCount);
                        cell.setCellValue(getZdMc("djbmdm", job.getString("DJBMDM"), zdMap));
                        cell.setCellStyle(normalStyle);

                        //赋值是否接入政府数据共享交换平台(或大数据中心等)
                        colCount += 1;
                        cell = r.createCell(colCount);
                        //cell.setCellValue("1");
                        cell.setCellStyle(normalStyle);

                        colCount += 1;
                        //共享部门分组名称
                        String cellValue = "";
                        for (int i = colCount; i < c; i++) {
                            cell = rowThi.getCell(i);
                            if (null != cell && StringUtils.isNotBlank(cell.getStringCellValue())) {
                                cellValue = cell.getStringCellValue();
                            }
                            //判断数据是否属于该共享部门分组，不是则直接赋值0
                            if (!StringUtils.equals(getZdMc("gxbmbz", job.getString("GXBMBZ"), zdMap), cellValue)) {
                                cell = r.createCell(i);
                                cell.setCellValue("0");
                                cell.setCellStyle(normalStyle);
                                continue;
                            }
                            //接口名称
                            cell = rowFou.getCell(i);
                            if (null != cell) {
                                boolean flag = false;
                                String jkmcValue = cell.getStringCellValue();
                                List<Map> jkmcList = (List<Map>)job.get("MX");
                                if (CollectionUtils.isNotEmpty(jkmcList)) {
                                    //判断数据是否有对应接口名称的值
                                    for (Map map : jkmcList) {
                                        if (StringUtils.equals(String.valueOf(map.get("MC")),jkmcValue)) {
                                            cell = r.createCell(i);
                                            cell.setCellValue(String.valueOf(map.get("ZS")));
                                            cell.setCellStyle(normalStyle);
                                            flag = true;
                                            break;
                                        }
                                    }
                                    if (!flag) {
                                        cell = r.createCell(i);
                                        cell.setCellValue("0");
                                        cell.setCellStyle(normalStyle);
                                    }
                                }
                            }
                        }

                        //行数增加
                        rowCount += 1;
                    }
                }

            }

            os.flush();
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param zdMC
     * @param zdDm
     * @param zdMap
     * @return
     * 字典值转换
     */
    private static String getZdMc(String zdMC,String zdDm, Map zdMap){
        List<Map> zdList = (List<Map>)zdMap.get(zdMC);
        if (CollectionUtils.isNotEmpty(zdList)) {
            for (Map zd : zdList) {
                if (StringUtils.equals(zdDm, String.valueOf(zd.get("DM")))) {
                    return String.valueOf(zd.get("MC"));
                }
            }
        }

        return zdDm;
    }
}
