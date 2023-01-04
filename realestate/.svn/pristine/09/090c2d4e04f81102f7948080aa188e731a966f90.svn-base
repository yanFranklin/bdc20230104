package cn.gtmap.realestate.common.core.support.excel;

import cn.gtmap.realestate.common.core.dto.ExcelExportDTO;
import cn.gtmap.realestate.common.core.dto.ExcelExportSheetsDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

    @Autowired
    ApplicationContext applicationContext;

    /**
     * @param response       返回响应
     * @param excelExportDTO Excel信息实体
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 导出Excel（前台直接封装定义数据，不走后台再次查询处理逻辑）
     */
    @PostMapping(value = "/export")
    public void exportExcel(HttpServletResponse response, @ModelAttribute ExcelExportDTO excelExportDTO) {
        if (StringUtils.isNotBlank(excelExportDTO.getSort())){
            excelExportDTO = sortData(excelExportDTO);
        }
        try (HSSFWorkbook workbook = new HSSFWorkbook();
             OutputStream outputStream = response.getOutputStream()) {
            //Excel单元格最多存放32767个字符，需判断那些字段需要分割和字段需要分割成几个；
            if (Boolean.TRUE.equals(excelExportDTO.getCheakCell())) {
                this.generateExportDTO(excelExportDTO);
            }
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
     * 重新组织Excel导出信息实体（cellKey, cellTitle,cellWidth,data）
     * @param excelExportDTO
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     */
    public void generateExportDTO(ExcelExportDTO excelExportDTO) {
        Map<String, Integer> cellsDivision = new HashMap<>();

        //判断每个字段是否需要分割,需要分割成几份；
        cellsDivision = this.divideBigData(excelExportDTO);

        //重新组织表格结构（cellKey, cellTitle,cellWidth）
        this.generateExcel(excelExportDTO, cellsDivision);

    }

    /**
     * 重新组织表格结构（cellKey, cellTitle,cellWidth）
     * @param excelExportDTO
     * @param cellsDivision
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     */
    private void generateExcel(ExcelExportDTO excelExportDTO, Map<String, Integer> cellsDivision) {
        // 每列数据对应的key
        String[] cellKeyArr = excelExportDTO.getCellKey().split(CommonConstantUtils.ZF_YW_DH);
        StringBuffer cellKeySb = new StringBuffer(excelExportDTO.getCellKey());
        // 每列数据对应的title
        String[] cellTitleArr = excelExportDTO.getCellTitle().split(CommonConstantUtils.ZF_YW_DH);
        StringBuffer cellTitleSb = new StringBuffer(excelExportDTO.getCellTitle());
        // 每列数据对应的width
        String[] cellWidthArr = excelExportDTO.getCellWidth().split(CommonConstantUtils.ZF_YW_DH);
        StringBuffer cellWidthSb = new StringBuffer(excelExportDTO.getCellWidth());
        int index = 0;
        for (String cellKey : cellKeyArr) {
            int cellsNubmber = cellsDivision.get(cellKey);
            String cellTitle = cellTitleArr[index];
            String cellWidth = cellWidthArr[index];
            if (cellsNubmber > 1) {
                StringBuffer insertKeyStrSb = new StringBuffer("");
                StringBuffer insertTitleStrSb = new StringBuffer("");
                StringBuffer insertWidthStrSb = new StringBuffer("");
                //生成插入的字符串
                for (int i = 2; i <= cellsNubmber; i++) {
                    insertKeyStrSb.append(cellKey).append(i-1).append(",");
                    insertTitleStrSb.append(cellTitle).append(i-1).append(",");
                    insertWidthStrSb.append(cellWidth).append(",");
                }
                excelExportDTO.setCellKey(this.insertStr(cellKeySb,insertKeyStrSb,cellKey));
                excelExportDTO.setCellTitle(this.insertStr(cellTitleSb,insertTitleStrSb,cellTitle));
                excelExportDTO.setCellWidth(this.insertStr(cellWidthSb,insertWidthStrSb,cellWidth));
            }
            index ++;
        }
    }

    /**
     *
     * @param sb 原字符串
     * @param insertSb 待插入子字符串
     * @param str
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     */
    private String insertStr(StringBuffer sb,StringBuffer insertSb, String str) {
        int insertIndex = sb.indexOf(str) + str.length() + 1;
        StringBuffer res = sb.insert(insertIndex,insertSb.toString());
        return res.toString();
    }

    /**
     * 判断每个字段是否需要分割,需要分割成几份；
     * @param excelExportDTO
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     */
    private Map<String, Integer> divideBigData(ExcelExportDTO excelExportDTO) {
        Map<String, Integer> cellsDivision = new HashMap<>();

        // 解析封装的JSON数据
        List<Map<String, Object>> dataList = JSON.parseObject(excelExportDTO.getData(), List.class);
        // 每列数据对应的key
        String[] cellKeyArr = excelExportDTO.getCellKey().split(CommonConstantUtils.ZF_YW_DH);
        //初始化单元格分割map，每个单元格都默认不分割，值为1
        for (String key : cellKeyArr) {
            cellsDivision.put(key, 1);
        }

        for (int j = 0; j < dataList.size(); j++) {
            Map<String, Object> map = dataList.get(j);
            for (String cellKey : cellKeyArr) {
                String value = "";
                if (map.containsKey(cellKey) && null != map.get(cellKey)) {
                    value = String.valueOf(map.get(cellKey));
                    if(value.length() > 32765){
                        int divNumber = value.length() / 32765 +1;
                        //分割数据
                        map.put(cellKey, value.substring(0,32765));
                        for (int h = 1; h < divNumber; h++) {
                            int start = h * 32765;
                            int end = (h + 1) * 32765;
                            if (end > value.length()) {
                                end = value.length();
                            }
                            String key = cellKey + h;
                            String valueNew = value.substring(start, end);
                            map.put(key, valueNew);
                        }
                        int maxDivNumber = (divNumber > cellsDivision.get(cellKey)) ? divNumber : cellsDivision.get(cellKey);
                        cellsDivision.put(cellKey, maxDivNumber);
                    }
                }
            }
        }
        //重新封装JSON数据
        String data = JSONObject.toJSONString(dataList);
        excelExportDTO.setData(data);
        return cellsDivision;
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
        if(sheetName.equals(CommonConstantUtils.XCLQTJ_SHEETNAME)){
            //宣城林权统计特殊表头特殊处理
            this.setLqtjCellTitle(workbook, sheet, excelExportDTO);
        }else {
            this.setCellTitle(workbook, sheet, excelExportDTO);
        }

        // 表格列宽
        this.setCellWidth(sheet, excelExportDTO.getCellWidth());
        // 判断是否需要执行 行合并操作
        if(excelExportDTO.getMergeSameCell()){
            this.setCellDataAndMergeSameCell(workbook, sheet, excelExportDTO);
        }else{
            this.setCellData(workbook, sheet, excelExportDTO);
        }
        this.setTailSummaryContent(workbook,sheet,excelExportDTO);



        // 添加自定义扩展的实现类
        if(StringUtils.isNotBlank(excelExportDTO.getExpandServiceName())){
            List<ExcelExpandService> expandServices=new ArrayList<ExcelExpandService>();
            if (StringUtils.isNotBlank(excelExportDTO.getExpandServiceName())) {
                ExcelExpandService excelExpandService = applicationContext.getBean(excelExportDTO.getExpandServiceName(), ExcelExpandService.class);
                if (Objects.nonNull(excelExpandService)) {
                    expandServices.add(excelExpandService);
                }
            }
            /*//盐城excel导出特殊处理
            if(excelExportDTO.getExpandServiceName().equals(CommonConstantUtils.EXCELEXPANDSERVICE_YC)){
                expandServices.add(new ExcelExpandYcService());
            }*/
            excelExportDTO.setExpandServices(expandServices);
        }

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
     * @param
     * @param workbook Excel模板
     * @param sheet    Excel工作簿
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 设置林权统计的标题和表头
     */
    private void setLqtjCellTitle(HSSFWorkbook workbook, HSSFSheet sheet, ExcelExportDTO excelExportDTO) {
        int rowNum = excelExportDTO.getRownum();
        // 标题内容
        String title = excelExportDTO.getCellTitle();
        String[] cellTitleArr = title.split(CommonConstantUtils.ZF_YW_DH);

        // 标题样式
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short)14);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
//        style.setBorderBottom(BorderStyle.THIN); //下边框    
//        style.setBorderLeft(BorderStyle.THIN);//左边框    
//        style.setBorderTop(BorderStyle.THIN);//上边框    
//        style.setBorderRight(BorderStyle.THIN);//右边框

        // 创建表名
        HSSFRow rowTableName = sheet.createRow(rowNum);
        HSSFCell cellTableName = rowTableName.createCell(0);
        cellTableName.setCellValue(sheet.getSheetName());
        cellTableName.setCellStyle(style);

        // 备注样式
        HSSFCellStyle style1 = workbook.createCellStyle();
        HSSFFont font1 = workbook.createFont();
        font1.setBold(true);
        font1.setFontHeightInPoints((short)8);
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setFont(font1);
        style1.setBorderLeft(BorderStyle.NONE);
        style1.setBorderRight(BorderStyle.NONE);
        // 面积单位：亩  金额单位：万元
        HSSFCell cellbz = rowTableName.createCell(16);
        HSSFCell cellbz1 = rowTableName.createCell(17);
        cellbz1.setCellValue("面积单位：亩  金额单位：万元");
        cellbz.setCellStyle(style1);
        cellbz1.setCellStyle(style1);
        sheet.addMergedRegion(new CellRangeAddress(
                rowNum, rowNum, 0, 16
        ));
        sheet.addMergedRegion(new CellRangeAddress(
                rowNum, rowNum, 17, 19
        ));
        rowNum++;
        //标题行样式
        HSSFCellStyle titleCellStyle = workbook.createCellStyle();
        HSSFFont titleFont = workbook.createFont();
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
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
        String[] cellTitleArr1 = {"","","","","","","","林地使用权/森林、林木使用权","","林地承包经营权/林木所有权","","林地使用权/林木所有权","","林地经营权/林木所有权","","林地经营权/林木使用权","","","",""};
        HSSFRow rowTitle1 = sheet.createRow(++rowNum);
        for (int i = 0; i < cellTitleArr1.length; i++) {
            HSSFCell cell = rowTitle1.createCell(i);
            cell.setCellValue(cellTitleArr1[i]);
            cell.setCellStyle(titleCellStyle);
        }
        String[] cellTitleArr2 = {"","","","","","林地数","林地面积","林地数","林地面积","林地数","林地面积","林地数","林地面积","林地数","林地面积","林地数","林地面积","","",""};
        HSSFRow rowTitle2 = sheet.createRow(++rowNum);
        for (int i = 0; i < cellTitleArr2.length; i++) {
            HSSFCell cell = rowTitle2.createCell(i);
            cell.setCellValue(cellTitleArr2[i]);
            cell.setCellStyle(titleCellStyle);
        }
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 5, 6));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 16));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 7, 8));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 9, 10));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 11, 12));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 13, 14));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 15, 16));
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 17, 17));
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 18, 18));
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 19, 19));
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

    /**
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * 根据实体中需要排序的字段进行排序(仅支持单个字段排序)
    */
    private ExcelExportDTO sortData(ExcelExportDTO dto){
        List<Map> exportDataList = JSON.parseArray(dto.getData(),Map.class);
        List sortDataList = new ArrayList();
        //如果是排序字段为qszt_ha  对数据进行先根据现势-历史-临时 在根据坐落排序
        if ("qszt_ha".equals(dto.getSort())){
            exportDataList = exportDataList.stream().sorted(Comparator.comparing(map -> MapUtils.getString(map,"zl",""))).collect(Collectors.toList());
            Map<Object,List<Map>> groupDatas = exportDataList.stream().collect(Collectors.groupingBy(o -> o.get("qszt")));
            if (CollectionUtils.isNotEmpty(groupDatas.get(CommonConstantUtils.QSZT_XS))){
                sortDataList.addAll(groupDatas.get(CommonConstantUtils.QSZT_XS));
            }
            if (CollectionUtils.isNotEmpty(groupDatas.get(CommonConstantUtils.QSZT_HIS))){
                sortDataList.addAll(groupDatas.get(CommonConstantUtils.QSZT_HIS));
            }
            if (CollectionUtils.isNotEmpty(groupDatas.get(CommonConstantUtils.QSZT_LS))){
                sortDataList.addAll(groupDatas.get(CommonConstantUtils.QSZT_LS));
            }
            if (CollectionUtils.isNotEmpty(groupDatas.get(CommonConstantUtils.QSZT_ZZ))){
                sortDataList.addAll(groupDatas.get(CommonConstantUtils.QSZT_ZZ));
            }
        } else {
            if ("desc".equals(dto.getOrder())){
                exportDataList = exportDataList.stream().sorted(Comparator.comparing(map -> MapUtils.getString((Map) map,dto.getSort(),"")).reversed()).collect(Collectors.toList());
            }else {
                exportDataList = exportDataList.stream().sorted(Comparator.comparing(map -> MapUtils.getString(map,dto.getSort(),""))).collect(Collectors.toList());
            }
            sortDataList.addAll(exportDataList);
        }

        dto.setData(JSON.toJSONString(sortDataList));
        return dto;
    }
}