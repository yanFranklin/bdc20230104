package cn.gtmap.realestate.common.util.office;

import com.alibaba.fastjson.JSONObject;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:qianguoyi@gtmap.cn">qianguoyi</a>
 * @version 1.0
 * @date 2021/7/5 8:56
 * @description excel工具类
 */
public final class ExcelUtil{
    private ExcelUtil(){
    }

    /**
     * 创建excel
     *
     * @param fileName    文件名称
     * @param valList     数据
     * @param response    response
     * @param listCumName 隐藏sheet页的查询属性名（根据需要可不传）
     * @throws IOException
     * @throws WriteException
     */
    public static void exportExcel(String fileName,List<List<String>> valList,HttpServletResponse response,List<String> listCumName,List<String> comList) throws IOException, WriteException{
        // 取得输出流
        OutputStream os = response.getOutputStream();
        try{
            response.reset();
            // 设置文件头
            response.setHeader("Content-disposition","attachment; filename=" + new String(fileName.getBytes("GB2312"),"ISO8859-1"));
            // 设置输出类型
            response.setContentType("application/msexcel");
            // 创建excel
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Excel查询");
            sheet.setDefaultColumnWidth(13);
            sheet.setDefaultRowHeight((short) (2 * 256));
            HSSFSheet hidden = workbook.createSheet("查询");
            hidden.setDefaultColumnWidth(13);
            hidden.setDefaultRowHeight((short) (2 * 256));
            //第一行
            HSSFRow row = hidden.createRow(0);
            //第二行
            HSSFRow row2 = hidden.createRow(1);
            for(int i = 0; i < listCumName.size(); i++){
                hidden.autoSizeColumn(i);
                //第c列
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(listCumName.get(i));
            }
            for(int i = 0; i < comList.size(); i++){
                //第c列
                HSSFCell cell2 = row2.createCell(i);
                cell2.setCellValue(comList.get(i));
            }
            workbook.setSheetHidden(1,true);

            // 写入标题列
            HSSFRow row3;
            int num = 0;
            for(int j = 0; j < valList.size(); j++){
                // Label,第一个参数为列，第二个参数为行
                // 每一行的名称
                List<String> list = valList.get(j);
                for(int i = 0; i < list.size(); i++){
                    //第一行
                    row3 = sheet.getRow(i);
                    if(row3 == null){
                        row3 = sheet.createRow(i);
                    }
                    String temp = list.get(i);
                    //第c列
                    HSSFCell cell = row3.createCell(num);
                    if(temp.contains(",")){
                        if(temp.contains("=")){
                            String[] strings = temp.split("=");
                            // 下拉框值
                            String defaultValue = strings[0];
                            String[] xlkVal = strings[1].split(",");
                            // 下拉框输入值
                            //(参数)起始行,终止行,起始列，终止列
                            CellRangeAddressList vehicle = new CellRangeAddressList(i,i,num,num);
                            // 创建下拉列表数据
                            DVConstraint constraintv = DVConstraint.createExplicitListConstraint(xlkVal);
                            // 绑定
                            HSSFDataValidation dataValidationv = new HSSFDataValidation(vehicle,constraintv);
                            sheet.addValidationData(dataValidationv);
                            cell.setCellValue(defaultValue);
                        }else{
                            String[] strings = temp.split(",");
                            //起始行,终止行,起始列，终止列
                            CellRangeAddressList vehicle = new CellRangeAddressList(i,i,num,num);
                            // 创建下拉列表数据
                            DVConstraint constraintv = DVConstraint.createExplicitListConstraint(strings);
                            // 绑定
                            HSSFDataValidation dataValidationv = new HSSFDataValidation(vehicle,constraintv);
                            sheet.addValidationData(dataValidationv);
                        }
                    }else{
                        cell.setCellValue(temp);
                    }
                }
                num++;
            }

            workbook.write(os);
            workbook.close();
        }finally{

            os.close();
        }
    }


    /**
     * 根据表头导出文件
     * @param fileName
     * @param exportColumn
     * @param exportList
     * @param response
     * @param exportColumnZd
     * @throws WriteException
     * @throws IOException
     */
    public static void exportExcelWithConsumer(String fileName,
                                   Map<String,String> exportColumn,
                                   List<Map<String, String>> exportList,
                                   HttpServletResponse response,
                                   Map<String, Consumer<Map<String, String>>> exportColumnFunction) throws WriteException, IOException {
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
            //处理数据
            exportColumnFunction.forEach((s, mapConsumer) -> mapConsumer.accept(cxjgMap));
            int colnum = 0;
            Iterator iter = exportColumn.entrySet().iterator();
            while (iter.hasNext()){
                Map.Entry entry = (Map.Entry) iter.next();
                Object value = entry.getValue();
                String cellValue = MapUtils.getString(cxjgMap,value);
//                if(colnum == 0){
//                    String oldCellValue = sheet.getCell(0,rownum-1).getContents();
//                    if(!oldCellValue.equals(cellValue)){
//                        if(rownumnew != rownum){
//                            sheet.mergeCells(0, rownumnew, 0, rownum);
//                        }
//                        rownumnew++;
//                    }else if(i==exportList.size()-1){
//                        sheet.mergeCells(0, rownumnew-1, 0, rownum);
//                    }
//                }
                sheet.addCell(new Label(colnum, rownum, cellValue, wcfLeft));
                colnum++;
            }
            rownum++;
        }
        workbook.write();
        workbook.close();
    }

    /**
     * 读取excel
     *
     * @param inputStream 文件流
     * @param hide        是否需要读取隐藏excel，默认读取隐藏的sheet 1的隐藏excel
     * @return 读取的返回数据
     * @throws BiffException
     * @throws IOException
     */
    public static Map<String,List<String>> readExcel(InputStream inputStream,boolean hide) throws BiffException, IOException{
        Workbook workbook = Workbook.getWorkbook(inputStream);
        //选取制定的sheet
        Sheet sheet = workbook.getSheet(0);
        //选取指定的cell
        //遍历循环得到所要的cell值
        Map<String,List<String>> stringListMap = new HashMap<>();
        // cxmc cxlx srz
        List<String> cxlxlist = new ArrayList<>();
        // 对输入的值进行字段转换
        Map<String,String> comMap = new HashMap<>();
        // 获取隐藏sheet页的数据
        if(hide){
            Sheet sheetCumName = workbook.getSheet(1);
            List<String> list = new ArrayList<>();
            List<String> comlist = new ArrayList<>();
            for(int i = 0; i < sheetCumName.getColumns(); i++){
                Cell cell = sheetCumName.getCell(i,0);
                Cell comcell = sheetCumName.getCell(i,1);
                //获取该cell的值
                list.add(cell.getContents());
                if(StringUtils.isNotEmpty(comcell.getContents())){
                    String[] arr = comcell.getContents().split(":");
                    comMap.put(arr[0],arr[1]);
                }
            }
            stringListMap.put("cumName",list);
//            stringListMap.put("comKey",comlist);
        }


        // 需要支持动态查询 用户可选择删除某些不需要的列，那么需要把第一行的中文属性名称读取
        List<String> mclist = new ArrayList<>();
        for(int i = 0; i < sheet.getColumns(); i++){
            Cell mcCell = sheet.getCell(i,0);
            Cell cell = sheet.getCell(i,1);
            mclist.add(mcCell.getContents());
            //获取该cell的值
            cxlxlist.add(cxString(cell.getContents()));
        }
        stringListMap.put("cxlx",cxlxlist);
        stringListMap.put("zwmc",mclist);
        // 处理输入框的下来值对应的字段值  这里由于字段进行拼接，所以提前进行转换
        // 这个地方获取值把每一行的输入值都放一个一个string，里面
        List<String> valList = new ArrayList<>();
        for(int i = 0; i < sheet.getColumns(); i++){
            String val = "";
            for(int j = 2; j < sheet.getRows(); j++){
                Cell cell = sheet.getCell(i,j);
                //获取该cell的值
                if(StringUtils.isNotEmpty(cell.getContents())){
                    // 下拉框的map如果有对应值，则转换为对应的字典值
                    val += StringUtils.isNotEmpty(comMap.get(cell.getContents())) ? comMap.get(cell.getContents()) + "|" : cell.getContents() + "|";
                }else{
                    // 空白参数用&&代替
                    val += "&&|";
                }
            }
            if(StringUtils.isNotEmpty(val)){
                valList.add(val.substring(0,val.length() - 1));
            }
        }
        stringListMap.put("srz",valList);

        return stringListMap;
    }

    // 获取查询类型
    private static String cxString(String cxlx){
        switch(cxlx){
            case "精确":
                return "1";
            case "左模糊":
                return "2";
            case "右模糊":
                return "3";
            case "全模糊":
                return "4";
            default:
                return "1";
        }
    }

    /**
     * 简单数据导出
     *
     * @param response response
     * @param excelList 数据
     * @param title 表格名称
     * @throws IOException
     */
    public static void simpleExport(HttpServletResponse response,List<List<String>>  excelList,String title) throws IOException{
        // 取得输出流
        OutputStream os = response.getOutputStream();
        try{
            response.reset();
            // 设置文件头
            response.setHeader("Content-disposition","attachment; filename=" + new String((title+".xls").getBytes("GB2312"),"ISO8859-1"));
            // 设置输出类型
            response.setContentType("application/msexcel");
            // 创建excel
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 表头样式
            HSSFCellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);

            HSSFSheet hidden = workbook.createSheet(title);
            CellRangeAddress region = new CellRangeAddress(0, 0, 0, excelList.get(0).size());
            hidden.addMergedRegion(region);
            // 写入标题
            HSSFRow titleRow = hidden.createRow(0);//第一行
            titleRow.setHeightInPoints(25);//行高
            HSSFCell titleCell = titleRow.createCell(0); //第c列
            titleCell.getCellStyle().setAlignment(HorizontalAlignment.CENTER);
            titleCell.setCellValue(title);

            // 写入数据
            hidden.setDefaultColumnWidth(25);
//            hidden.setDefaultRowHeight((short) (2 * 256));

            for (int i = 0; i < excelList.size(); i++){
                List<String> tempList = excelList.get(i);
                HSSFRow row = hidden.createRow(i+1);//第一行
                row.setHeightInPoints(25);//行高
                for (int j = 0; j < tempList.size(); j++){
                    HSSFCell cell = row.createCell(j); //第c列
                    cell.setCellValue(tempList.get(j));
                }
            }
            workbook.write(os);
            workbook.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally{

            os.close();
        }
    }

}
