package cn.gtmap.realestate.exchange.core.support.excel;

import cn.gtmap.realestate.exchange.core.dto.ExcelExportDTO;
import cn.gtmap.realestate.exchange.core.dto.ExcelExportSheetsDTO;
import cn.gtmap.realestate.exchange.core.ex.AppException;
import cn.gtmap.realestate.exchange.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.util.StringToolUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/5/22
 * @description 导出Excel公共逻辑
 */
@RestController
@RequestMapping(value = "/excel")
public class ExcelController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelController.class);


    /**
     * @param response       返回响应
     * @param excelExportDTO Excel信息实体
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 导出Excel（前台直接封装定义数据，不走后台再次查询处理逻辑）
     */
    @PostMapping(value = "/export")
    public void exportExcel(HttpServletResponse response, @ModelAttribute ExcelExportDTO excelExportDTO) {
        try (HSSFWorkbook workbook = new HSSFWorkbook();
             OutputStream outputStream = response.getOutputStream()) {
            // 创建单个工作表
            this.createSheet(workbook, excelExportDTO);

            //浏览器下载
            String fileName = URLEncoder.encode(excelExportDTO.getFileName() + ".xls", "utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            outputStream.flush();
            workbook.write(outputStream);
        } catch (IOException e) {
            LOGGER.error("系统导出Excel报错：{}", e.getMessage());
        }
    }

    /**
     * 导出含有多个工作簿的Excel表格
     * @param response       返回响应
     * @param sheetsDTO Excel信息实体
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping(value = "/sheets/export")
    public void exportExcelWithSheets(HttpServletResponse response, @ModelAttribute ExcelExportSheetsDTO sheetsDTO) {
        try (HSSFWorkbook workbook = new HSSFWorkbook();
             OutputStream outputStream = response.getOutputStream()) {

            // 处理多个工作表sheet
            for(ExcelExportDTO excelExportDTO : sheetsDTO.getSheets()) {
                this.createSheet(workbook, excelExportDTO);
            }

            //浏览器下载
            String fileName = URLEncoder.encode(sheetsDTO.getFileName() + ".xls", "utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            outputStream.flush();
            workbook.write(outputStream);
        } catch (Exception e) {
            LOGGER.error("系统导出Excel报错：{}", e.getMessage());
        }
    }

    /**
     * 生成单个sheet工作表
     * @param workbook 工作簿
     * @param excelExportDTO 单个工作表对应数据等信息
     */
    public void createSheet(HSSFWorkbook workbook, ExcelExportDTO excelExportDTO) {
        if (null == excelExportDTO || isExistEmpty(excelExportDTO)) {
            LOGGER.error("导出Excel中止，原因：提交的数据存在问题！");
            throw new AppException();
        }

        if (null == excelExportDTO.getRownum()) {
            excelExportDTO.setRownum(0);
        }

        // 默认工作簿名称
        String sheetName = excelExportDTO.getSheetName();
        if (StringUtils.isBlank(sheetName)) {
            sheetName = "工作簿";
        }

        // 创建工作簿
        HSSFSheet sheet = workbook.createSheet(sheetName);

        // 表格标题
        this.setCellTitle(workbook, sheet, excelExportDTO);
        // 表格列宽
        this.setCellWidth(sheet, excelExportDTO.getCellWidth());
        // 判断是否需要执行 行合并操作
        if(excelExportDTO.getMergeSameCell()){
            this.setCellDataAndMergeSameCell(workbook, sheet, excelExportDTO);
        }else{
            this.setCellData(workbook, sheet, excelExportDTO);
        }
        this.setTailSummaryContent(workbook,sheet,excelExportDTO);

        // 自定义功能扩展
        if(CollectionUtils.isNotEmpty(excelExportDTO.getExpandServices())) {
            for(ExcelExpandService expandService : excelExportDTO.getExpandServices()) {
                expandService.expand(workbook, sheet, excelExportDTO);
            }
        }
    }

    /**
     * @return {Boolean}   false: 都不为空； true: 只要有一个为空
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 判断实体属性是否存在空
     */
    private boolean isExistEmpty(ExcelExportDTO excelExportDTO) {
        return StringToolUtils.existItemNullOrEmpty(excelExportDTO.getFileName(),
                excelExportDTO.getCellTitle(), excelExportDTO.getCellKey(), excelExportDTO.getData());
    }

    /**
     * @param
     * @param workbook Excel模板
     * @param sheet    Excel工作簿
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 设置列标题
     */
    private void setCellTitle(HSSFWorkbook workbook, HSSFSheet sheet, ExcelExportDTO excelExportDTO) {
        int rowNum = excelExportDTO.getRownum();
        // 标题内容
        String title = excelExportDTO.getCellTitle();
        String[] cellTitleArr = title.split(CommonConstantUtils.ZF_YW_DH);

        // 标题样式
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        // 创建表名
        HSSFRow rowTableName = sheet.createRow(rowNum);
        HSSFCell cellTableName = rowTableName.createCell(0);
        cellTableName.setCellValue(sheet.getSheetName());
        cellTableName.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(
                rowNum, rowNum, 0, cellTitleArr.length - 1
        ));
        rowNum++;

        // 创建表头汇总内容
        if (StringUtils.isNotBlank(excelExportDTO.getSummaryContent())) {
            String[] summaryArr = excelExportDTO.getSummaryContent().split(CommonConstantUtils.ZF_YW_DH);
            Integer cellNum = 0;
            if (summaryArr.length > 1 && NumberUtils.isNumber(summaryArr[1])){
                cellNum = Integer.parseInt(summaryArr[1]);
            }

            rowTableName = sheet.createRow(rowNum);
            cellTableName = rowTableName.createCell(cellNum);
            cellTableName.setCellValue(summaryArr[0]);
            sheet.addMergedRegion(new CellRangeAddress(
                    rowNum, rowNum, cellNum, cellTitleArr.length - 1
            ));
            rowNum++;
        }
        //标题行样式
        HSSFCellStyle titleCellStyle = workbook.createCellStyle();
        HSSFFont titleFont = workbook.createFont();
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setFont(titleFont);
        titleFont.setBold(true);
        if (excelExportDTO.getAddBorder()){
            titleCellStyle.setBorderBottom(BorderStyle.THIN); //下边框    
            titleCellStyle.setBorderLeft(BorderStyle.THIN);//左边框    
            titleCellStyle.setBorderTop(BorderStyle.THIN);//上边框    
            titleCellStyle.setBorderRight(BorderStyle.THIN);//右边框
        }
        // 创建标题行
        HSSFRow rowTitle = sheet.createRow(rowNum);
        for (int i = 0; i < cellTitleArr.length; i++) {
            HSSFCell cell = rowTitle.createCell(i);
            cell.setCellValue(cellTitleArr[i]);
            cell.setCellStyle(titleCellStyle);
        }
        rowNum++;

        excelExportDTO.setRownum(rowNum);
    }

    /**
     * @param sheet Excel工作簿
     * @param width 多列宽
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 设置列宽
     */
    private void setCellWidth(HSSFSheet sheet, String width) {
        if (StringUtils.isBlank(width)) {
            return;
        }

        String[] cellWidthArr = width.split(",");
        for (int i = 0; i < cellWidthArr.length; i++) {
            sheet.setColumnWidth(i, Integer.valueOf(cellWidthArr[i]) * 256);
        }
    }

    /**
     * @param workbook       Excel模板
     * @param sheet          Excel工作簿
     * @param excelExportDTO Excel信息实体
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 设置列数据
     */
    private void setCellData(HSSFWorkbook workbook, HSSFSheet sheet, ExcelExportDTO excelExportDTO) {
        // 解析封装的JSON数据
        List<Map<String, Object>> dataList = JSON.parseObject(excelExportDTO.getData(), List.class);
        // 每列数据对应的key
        String[] cellKeyArr = excelExportDTO.getCellKey().split(",");
        // 列样式：默认居中
        HSSFCellStyle dataCellStyle = workbook.createCellStyle();
        //水平居中
        dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
        //垂直居中
        dataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        if (excelExportDTO.getAddBorder()){
            dataCellStyle.setBorderBottom(BorderStyle.THIN); //下边框    
            dataCellStyle.setBorderLeft(BorderStyle.THIN);//左边框    
            dataCellStyle.setBorderTop(BorderStyle.THIN);//上边框    
            dataCellStyle.setBorderRight(BorderStyle.THIN);//右边框
            dataCellStyle.setWrapText(true);
        }
        if(StringUtils.isNotBlank(excelExportDTO.getFontSize())){
            HSSFFont cellFont = workbook.createFont();
            cellFont.setFontHeightInPoints(Short.valueOf(excelExportDTO.getFontSize()));
            dataCellStyle.setFont(cellFont);
        }
        int rowNum = excelExportDTO.getRownum();
        for (int j = 0; j < dataList.size(); j++) {
            HSSFRow dataRow = sheet.createRow(j + rowNum);
            Map<String, Object> map = dataList.get(j);

            int cellNum = 0;
            for (String cellKey : cellKeyArr) {
                String value = "";
                if (map.containsKey(cellKey) && null != map.get(cellKey)) {
                    value = String.valueOf(map.get(cellKey));
                }
                HSSFCell dataCell = dataRow.createCell(cellNum++);
                dataCell.setCellValue(value);
                dataCell.setCellStyle(dataCellStyle);
            }
        }
        excelExportDTO.setRownum(rowNum + dataList.size());

    }

    /**
     * @param workbook       Excel模板
     * @param sheet          Excel工作簿
     * @param excelExportDTO Excel信息实体
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 设置表尾数据
     */
    private void setTailSummaryContent(HSSFWorkbook workbook, HSSFSheet sheet, ExcelExportDTO excelExportDTO){
        // 创建表尾汇总内容,汇总内容格式为json字符串，mergeCellValue为内容，mergeCellNum为合并列数
        if (StringUtils.isNotBlank(excelExportDTO.getTailSummaryContent())) {
            List<Map<String, Object>> contentList = JSON.parseObject(excelExportDTO.getTailSummaryContent(), List.class);
            if(CollectionUtils.isNotEmpty(contentList)){
                int rowNum = excelExportDTO.getRownum();
                HSSFRow dataRow = sheet.createRow(rowNum);
                int cellNum =0;
                for(Map<String,Object> content:contentList){
                    if(content.get("mergeCellNum") != null && NumberUtils.isNumber(content.get("mergeCellNum").toString())) {
                        int mergeCellNum =Integer.parseInt(content.get("mergeCellNum").toString());
                        HSSFCell dataCell = dataRow.createCell(cellNum);
                        dataCell.setCellValue(content.get("mergeCellValue") != null ?content.get("mergeCellValue").toString():"");
                        if(mergeCellNum >1) {
                            sheet.addMergedRegion(new CellRangeAddress(
                                    rowNum, rowNum, cellNum, cellNum + mergeCellNum - 1
                            ));
                        }
                        cellNum += mergeCellNum;
                    }
                }
            }

        }
    }

    /**
     *
     * @param workbook       Excel模板
     * @param sheet          Excel工作簿
     * @param excelExportDTO Excel信息实体
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 设置列数据，合并指定列中相同值。
     * <p>例如：excel中A列 A1：测试 A2：测试 A3:结果  会将A1与A2进行合并</p>
     */
    private void setCellDataAndMergeSameCell(HSSFWorkbook workbook, HSSFSheet sheet, ExcelExportDTO excelExportDTO){
        // 解析封装的JSON数据
        List<Map<String, Object>> dataList = JSON.parseObject(excelExportDTO.getData(), List.class);
        // 每列数据对应的key
        String[] cellKeyArr = excelExportDTO.getCellKey().split(",");
        String mergeColumnCellKeyListString = excelExportDTO.getMergeColumnCellKeyList();
        List<String> mergeColumnCellKeyList = JSON.parseObject(mergeColumnCellKeyListString, List.class);
        if (mergeColumnCellKeyList!=null&&mergeColumnCellKeyList.size()>0){
            Iterator<String> mergeColumnCellKeyIterator = mergeColumnCellKeyList.iterator();
            while (mergeColumnCellKeyIterator.hasNext()){
                if (!excelExportDTO.getCellKey().contains(mergeColumnCellKeyIterator.next())){
                    mergeColumnCellKeyIterator.remove();
                }
            }
        }
        // 列样式：默认居中、垂直居中
        HSSFCellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
        dataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        if (excelExportDTO.getAddBorder()){
            dataCellStyle.setBorderBottom(BorderStyle.THIN); //下边框    
            dataCellStyle.setBorderLeft(BorderStyle.THIN);//左边框    
            dataCellStyle.setBorderTop(BorderStyle.THIN);//上边框    
            dataCellStyle.setBorderRight(BorderStyle.THIN);//右边框
        }
        if(StringUtils.isNotBlank(excelExportDTO.getFontSize())){
            HSSFFont cellFont = workbook.createFont();
            cellFont.setFontHeightInPoints(Short.valueOf(excelExportDTO.getFontSize()));
            dataCellStyle.setFont(cellFont);
        }

        // 数据起始行
        int startRowNum = excelExportDTO.getRownum();
        // 存储记录合并列的数量
        Map<String,Integer> tempMap = new HashMap<>(1);
        // 自定义需要合并的cellKey
        List<String> mergeCellArr = excelExportDTO.getMergeCellKey()!=null?Arrays.asList(
                excelExportDTO.getMergeCellKey().split(CommonConstantUtils.ZF_YW_DH)):new ArrayList<>();
        // 当前列与下一列进行数据对比与合并
        for (int rowNum = 0, next = 1; rowNum < dataList.size(); rowNum++, next++) {
            int currentRowNum = rowNum + startRowNum; // 当前行数
            HSSFRow dataRow = sheet.createRow(currentRowNum);
            // 当前行的数据集合
            Map<String, Object> map = dataList.get(rowNum);
            // 下一行的数据集合,最后一条数据和第一条数据进行比对
            Map<String, Object> nextDataMap = next==dataList.size() ? null:dataList.get(next);

            // 合并MergeCellIdKey一样的字段才可以合并 不一样的话即使值一样也不合并
            Map<String, Object> lastDataMap = null;
            if(rowNum >0){
                lastDataMap = dataList.get(rowNum-1);
            }
            String curIdValue = String.valueOf(map.get(excelExportDTO.getMergeCellIdKey()));
            String lastIdValue = lastDataMap!=null?String.valueOf(lastDataMap.get(excelExportDTO.getMergeCellIdKey())):"";
            String nextIdValue = nextDataMap!=null?String.valueOf(nextDataMap.get(excelExportDTO.getMergeCellIdKey())):"";

            boolean sfHbLast = StringUtils.equals(curIdValue,lastIdValue);
            boolean sfHbNext = StringUtils.equals(curIdValue,nextIdValue);

            for(int colNum = 0; colNum<cellKeyArr.length; colNum++){
                String cellKey = cellKeyArr[colNum];
                String value = "";
                if (map.containsKey(cellKey) && null != map.get(cellKey)) {
                    value = String.valueOf(map.get(cellKey));
                }
                HSSFCell dataCell = dataRow.createCell(colNum);
                dataCell.setCellValue(value);
                dataCell.setCellStyle(dataCellStyle);
                // 判断当前列数据是否要合并
                if(mergeCellArr.contains(cellKey) && (nextDataMap == null || nextDataMap.containsKey(cellKey))){
                    this.mergeCell(sheet, currentRowNum, colNum, value,
                            cellKey, nextDataMap!=null?String.valueOf(nextDataMap.get(cellKey)):"", tempMap,sfHbLast,sfHbNext);
                }
                //判断列的值是否需要横向合并
                if (!mergeCellArr.contains(cellKey) && excelExportDTO.getMergeColumnCellKeyList()!=null && mergeColumnCellKeyList.size()>0){
                    List<String> mergeColumnCellKeyList2Deal = mergeColumnCellKeyList.stream().filter(mergeColumnCellKey -> mergeColumnCellKey.contains(cellKey)).collect(Collectors.toList());
                    if (mergeColumnCellKeyList2Deal.size() == 1){
                        //处理需要合并的单元格
                        String[] mergeColumnCellKeyArray2Deal = mergeColumnCellKeyList2Deal.get(0).split(CommonConstantUtils.ZF_YW_DH);
                        //用于校验需要横向合并的值是否与纵向合并的值冲突，冲突则不需要合并
                        List<String> tempList = Arrays.asList(mergeColumnCellKeyArray2Deal);
                        tempList = new ArrayList<>(tempList);
                        tempList.retainAll(mergeCellArr);
                        final String finalValue = value;
                        //校验值是否一致，一致才合并
                        if (tempList.size()==0 && mergeColumnCellKeyArray2Deal[mergeColumnCellKeyArray2Deal.length-1].equals(cellKey) && Arrays.stream(mergeColumnCellKeyArray2Deal).noneMatch(mergeColumnCellKey -> !mergeColumnCellKey.equals(cellKey) && !map.get(mergeColumnCellKey).equals(finalValue))){
                            //合并单元格
                            sheet.addMergedRegion(new CellRangeAddress(currentRowNum,currentRowNum,colNum-mergeColumnCellKeyArray2Deal.length+1,colNum));
                        }
                    }else if (mergeColumnCellKeyList2Deal.size()>1){
                        LOGGER.info("需要合并的列的key输入有误！参数：{}",excelExportDTO.getMergeColumnCellKeyList());
                    }
                }

            }
        }
    }


    /**
     * 合并单元格逻辑处理
     * @param sheet  Excel工作簿
     * @param currentRowNum 当前行数
     * @param currentColNum 当前列数
     * @param cellValue 单元格的值
     * @param cellKey 单元格所对应的KEY
     * @param nextData 下一行当前列的数据
     * @param tempMap 临时
     */
    private void mergeCell(HSSFSheet sheet, int currentRowNum, int currentColNum,
                           String cellValue, String cellKey, String nextData,
                           Map<String, Integer> tempMap,boolean sfHbLast,boolean sfHbNext){
        // 比较当前行的列与下一行的列值是否一致，一致时标记合并行
        if(StringUtils.isNoneBlank(nextData) && cellValue.equals(nextData) && sfHbNext){
            if(tempMap.containsKey(cellKey)){
                tempMap.put(cellKey, tempMap.get(cellKey)+1);
            }else{
                tempMap.put(cellKey, 1);
            }
        }else{
            // 执行行合并操作, 当存在需要合并数据时，进行行合并
            if(tempMap.containsKey(cellKey) && !tempMap.get(cellKey).equals(0) && sfHbLast){
                sheet.addMergedRegion(new CellRangeAddress(
                        currentRowNum-tempMap.get(cellKey), currentRowNum, currentColNum, currentColNum
                ));
                tempMap.put(cellKey, 0);
            }
        }
    }
}