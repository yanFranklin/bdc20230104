package cn.gtmap.realestate.common.core.support.pdf.service.impl;

import cn.gtmap.realestate.common.core.dto.OfficeInnerTableDataDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.office.OfficeUtil;
import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2020/9/9 18:50
 * @description 单元格内嵌表数据处理
 */
@Service
public class OfficeInCellTablePolicy extends DynamicTableRenderPolicy {
    /**
     * 日志操作
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OfficeInCellTablePolicy.class);

    private static final Pattern pattern = Pattern.compile("[0-9]*");

    // 表格名称正则表达式
    private static final String PDF_WORD_INCELL_TABLE_NAME_REG = "\\{\\{TABLE_NQ_[A-Za-z0-9_]*+\\}\\}";
    // 表格、单元格字段占位符开头
    private static final String PDF_WORD_SUBTABLE_CELL_START3_REG = "{{";
    // 表格、单元格字段占位符结尾
    private static final String PDF_WORD_SUBTABLE_CELL_END_REG = "}}";

    /**
     * 整个文档数据
     * 备注：这里因为OfficeInCellTablePolicy每次new新建实例，不需要考虑线程安全问题
     */
    private Map<String, Object> officeData;

    public OfficeInCellTablePolicy() {

    }

    public OfficeInCellTablePolicy(Map<String, Object> data) {
        this.officeData = data;
    }

    @Override
    public void render(XWPFTable table, Object data) {
        if (null == table || null == data) {
            LOGGER.error("PDF、WORD处理数据表格中止，原因：没有定义数据表格或者无对应数据！");
            return;
        }

        if (CollectionUtils.isEmpty(table.getRows())) {
            LOGGER.error("PDF、WORD处理数据表格中止，原因：表格无数据行内容！");
            return;
        }

        List<Map<String, Object>> tableData = (List<Map<String, Object>>) data;

        // 获取表名
        String tableName = this.getTableName(table);
        if(null == tableName){
            LOGGER.error("PDF、WORD处理数据表格中止，原因：单元格内嵌表解析错误");
            return;
        }

        // 表名 TABLE_NQ_XXX_行索引_列索引
        String[] tempArr = tableName.split("_");
        int cellLocationRow = Integer.parseInt(tempArr[3]);
        int cellLocationCol = Integer.parseInt(tempArr[4]);

        // 字体
        String fontType="";
        // 字体大小
        String fontSize="";
        // 行高度
        String rowHeight="";
        // 边框宽度
        String borderWidth = "4";

        if(tempArr.length > 5){
            for(int i = 5; i < tempArr.length; i++){
                String items = tempArr[i];
                if(items.startsWith(CommonConstantUtils.PDF_FONTS)){
                    fontType=items.substring(CommonConstantUtils.PDF_FONTS.length());
                    Map<String,String> fontMap=OfficeUtil.getFontMap();
                    fontType=fontMap.get(fontType);
                }
                if(items.startsWith(CommonConstantUtils.PDF_FONTSIZE)){
                    fontSize=items.substring(CommonConstantUtils.PDF_FONTSIZE.length());
                }
                if(items.startsWith(CommonConstantUtils.PDF_ROWHEIGHT)){
                    rowHeight=items.substring(CommonConstantUtils.PDF_ROWHEIGHT.length());
                }
                if(items.startsWith(CommonConstantUtils.PDF_COLUMN_BORDER)){
                    borderWidth = items.substring(CommonConstantUtils.PDF_COLUMN_BORDER.length());
                }
            }
        }

        List<XWPFTableRow> rows = table.getRows();
        // 获取内嵌表所在单元格对象
        XWPFTableCell cell = rows.get(cellLocationRow).getCell(cellLocationCol);
        // 移除原有占位数据
        List<XWPFParagraph> paragraphs = cell.getParagraphs();
        if (CollectionUtils.isNotEmpty(paragraphs)) {
            Iterator<XWPFParagraph> it = paragraphs.iterator();
            int pos = paragraphs.size() - 1;
            while (it.hasNext()) {
                cell.removeParagraph(pos--);
            }
        }

        XWPFParagraph paragraph = cell.addParagraph();
        XWPFRun run = paragraph.createRun();

        // 内嵌表没有数据则该单元格插入空字符串
        if (CollectionUtils.isEmpty(tableData)) {
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            run.setText("");
            return;
        }

        // 新建表格
        // 获取字段名,确定字段顺序
        String[][] fieldNameArray = this.getFieldNameArray(tableData);
        // 内嵌表行数
        int rowNum = tableData.size();
        // 内嵌表列数
        int colNum = fieldNameArray.length;

        // 注意需要设置列数为0，在下面设值的时候动态绘制列
        XWPFTable inCellTable = ((NiceXWPFDocument) cell.getXWPFDocument()).insertNewTable(run, rowNum, 0);
//        CTTblGrid grid = inCellTable.getCTTbl().getTblGrid();
//        if (grid == null) {
//            inCellTable.getCTTbl().addNewTblGrid();
//        }

        inCellTable.setLeftBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
        inCellTable.setRightBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
        inCellTable.setTopBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
        inCellTable.setBottomBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
        inCellTable.setInsideHBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
        inCellTable.setInsideVBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
        inCellTable.setCellMargins(0, 1 , 0, 1);
        // 解决快速打印方案内嵌表格内文字下移的问题
        if(String.valueOf(CommonConstantUtils.SF_S_DM).equals(String.valueOf(officeData.get(CommonConstantUtils.PDF_FWTP)))){
            inCellTable.setCellMargins(0, 1, 60, 1);
        }


        // 从数据源中获取设置的列宽
        BigDecimal[] columnWidth = OfficeUtil.getInCellTableColumnWidth(table, tableData, cellLocationRow, cellLocationCol, colNum);

        // 如果没有设置行高度则当前列默认占总行数等宽宽度: 所在单元格总高度 / 行数
        if (StringUtils.isBlank(rowHeight)) {
            double inCellHeight = table.getRow(cellLocationRow).getHeight();
            rowHeight = new BigDecimal(Double.toString(inCellHeight)).divide(new BigDecimal(Integer.toString(rowNum)), 0, BigDecimal.ROUND_HALF_UP) + "";
        }

        // 保存二层嵌套子表位置，key: 行 value: 列
        Map<String, Set<String>> zbMap = new HashMap<>();
        // 设置每个单元格数据
        for (int r = 0; r < rowNum; r++) {
            //设置内嵌表格行高
            if(StringUtils.isNotBlank(rowHeight)){
                //判断是否为字体大小是否为数字
//                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher isNum = pattern.matcher(rowHeight);
                if( isNum.matches() ){
                    inCellTable.getRow(r).setHeight(Integer.parseInt(rowHeight));
                }
            }
            for (int c = 0; c < colNum; c++) {
                String value = 0 == r ? fieldNameArray[c][2] : (String) tableData.get(r).get(fieldNameArray[c][1]);
                OfficeInnerTableDataDTO innerTableDataDTO = new OfficeInnerTableDataDTO();
                innerTableDataDTO.setRow(inCellTable.getRow(r));
                innerTableDataDTO.setValue(value);
                innerTableDataDTO.setRowNum(r);
                innerTableDataDTO.setColumnNum(c);
                innerTableDataDTO.setTotalRowNum(rowNum);
                innerTableDataDTO.setTotalColumnNum(colNum);
                innerTableDataDTO.setFontType(fontType);
                innerTableDataDTO.setFontSize(fontSize);
                innerTableDataDTO.setBorderWidth(borderWidth);


                if(StringUtils.startsWith(value, "ZB_") || StringUtils.startsWith(value, "zb_")) {
                    if(CollectionUtils.isEmpty(zbMap.get(String.valueOf(r)))) {
                        zbMap.put(String.valueOf(r), new HashSet<>());
                    }
                    zbMap.get(String.valueOf(r)).add(String.valueOf(c));
                    boolean needSetTopBorder =  isNeedSetTopBorder(zbMap, r, c);

                    // 子表单元格内嵌子表
                    XWPFTableCell innerCell = 0 == c ? inCellTable.getRow(r).getCell(0) : inCellTable.getRow(r).createCell();
                    OfficeUtil.setBorder(innerCell, innerTableDataDTO);
                    //设置内嵌表格行高
                    if (StringUtils.isNotBlank(rowHeight)) {
                        //判断是否为字体大小是否为数字
//                        Pattern pattern = Pattern.compile("[0-9]*");
                        Matcher isNum = pattern.matcher(rowHeight);
                        if (isNum.matches()) {
                            this.setInCellInnerTable(innerCell, value, columnWidth[c], needSetTopBorder, new BigDecimal(rowHeight));
                        } else {
                            this.setInCellInnerTable(innerCell, value, columnWidth[c], needSetTopBorder, null);
                        }
                    } else {
                        this.setInCellInnerTable(innerCell, value, columnWidth[c], needSetTopBorder, null);
                    }

                } else {
                    // 普通文本
                    OfficeUtil.setInnerTableColumnText(innerTableDataDTO);
                    // 设置文本垂直居中
                    inCellTable.getRow(r).getCell(c).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                }
            }
        }

        // 设置每个列宽度
        this.setInCellTableColumnWidth(table, inCellTable, tableData, cellLocationRow, cellLocationCol, colNum);

        // 删除插入表格后下面多出的段落 注：插入表格后该单元格段落数为0
        List<XWPFParagraph> newParagraphs = cell.getParagraphs();
        if (CollectionUtils.isNotEmpty(newParagraphs)) {
            cell.removeParagraph(newParagraphs.size() - 1);
        }
    }


    /**
     * 设置单元格内嵌子表（第二层子表嵌套）
     * @param innerCell 所在单元格
     * @param data 当前内嵌子表数据
     * @param zbNeedSetTopBorder 子表是否需要设置上边框
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private void setInCellInnerTable(XWPFTableCell innerCell, String data, BigDecimal innerCellWidth, boolean zbNeedSetTopBorder, BigDecimal rowHeight) {
        if(StringUtils.isBlank(data)) {
            return;
        }
        data = data.trim();

        // 解析获取单元格内嵌子表数据
        // 数据例如：ZB_身份证_320100200101011210_&&_户口簿_320100200101011210_&&_Width30:70_&&_Border10
        String innerTableDataStr = data.replace("ZB_", "").replace("zb_", "");
        String[] innerTableDataStrArray = innerTableDataStr.split("_&&_");
        if(null == innerTableDataStrArray || 0 == innerTableDataStrArray.length) {
            return;
        }

        // 列宽设置
        String[] columnWidth = null;
        // 字体
        String fontType = "";
        // 字体大小
        String fontSize = "";
        // 边框宽度
        String borderWidth = "4";
        // 行高设置
        String innerRowHeight = "";

        List<List<String>> innerTableData = new ArrayList<>();
        for(int i = 0; i < innerTableDataStrArray.length; i++) {
            if(innerTableDataStrArray[i].startsWith(CommonConstantUtils.PDF_FONTS)){
                fontType = innerTableDataStrArray[i].substring(CommonConstantUtils.PDF_FONTS.length());
                Map<String,String> fontMap = OfficeUtil.getFontMap();
                fontType = fontMap.get(fontType);
                continue;
            }

            if(innerTableDataStrArray[i].startsWith(CommonConstantUtils.PDF_FONTSIZE)){
                fontSize = innerTableDataStrArray[i].substring(CommonConstantUtils.PDF_FONTSIZE.length());
                continue;
            }

            if(innerTableDataStrArray[i].startsWith(CommonConstantUtils.PDF_ROWHEIGHT)){
                innerRowHeight = innerTableDataStrArray[i].substring(CommonConstantUtils.PDF_ROWHEIGHT.length());
                continue;
            }

            if(innerTableDataStrArray[i].startsWith(CommonConstantUtils.PDF_COLUMN_WIDTH)) {
                // 列宽设置
                String width = innerTableDataStrArray[i].substring(CommonConstantUtils.PDF_COLUMN_WIDTH.length());
                if(StringUtils.isNotBlank(width)) {
                    columnWidth = width.split(CommonConstantUtils.ZF_YW_MH);
                }
                continue;
            }

            if(innerTableDataStrArray[i].startsWith(CommonConstantUtils.PDF_COLUMN_BORDER)) {
                // 边框宽度设置
                borderWidth = innerTableDataStrArray[i].substring(CommonConstantUtils.PDF_COLUMN_BORDER.length());
                continue;
            }

            innerTableData.add(i, new ArrayList<>());
            // 行数据例如 身份证_320100200101011210_南京市
            for(String dataStr : innerTableDataStrArray[i].split("_")) {
                innerTableData.get(i).add(dataStr);
            }
        }

        // 移除原有占位数据
        List<XWPFParagraph> innerCellparagraphs = innerCell.getParagraphs();
        if (CollectionUtils.isNotEmpty(innerCellparagraphs)) {
            Iterator<XWPFParagraph> it2 = innerCellparagraphs.iterator();
            int pos = innerCellparagraphs.size() - 1;
            while (it2.hasNext()) {
                innerCell.removeParagraph(pos--);
            }
        }

        XWPFParagraph innerTableParagraph = innerCell.addParagraph();
        XWPFRun innerTableRun = innerTableParagraph.createRun();
        innerTableParagraph.setSpacingAfter(0);
        innerTableParagraph.setSpacingBefore(0);

        CTTcPr ctTcPr = innerCell.getCTTc().addNewTcPr();
        ctTcPr.addNewNoWrap();

        // 新建表格
        // 内嵌表行数
        int rowNum = innerTableData.size();
        // 内嵌表列数
        int colNum = innerTableData.get(0).size();

        // 注意需要设置列数为0，在下面设值的时候动态绘制列
        XWPFTable innerTable = ((NiceXWPFDocument) innerCell.getXWPFDocument()).insertNewTable(innerTableRun, rowNum, 0);
        CTTblGrid grid = innerTable.getCTTbl().getTblGrid();
        if (grid == null) {
            innerTable.getCTTbl().addNewTblGrid();
        }

        innerTable.setLeftBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
        innerTable.setRightBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
        innerTable.setTopBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
        innerTable.setBottomBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
        innerTable.setInsideHBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
        innerTable.setInsideVBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
        innerTable.setCellMargins(0, 1, 0 ,1);

        // 设置每个单元格数据
        for (int r = 0; r < rowNum; r++) {
            for (int c = 0; c < colNum; c++) {
                OfficeInnerTableDataDTO innerTableDataDTO = new OfficeInnerTableDataDTO();
                innerTableDataDTO.setRow(innerTable.getRow(r));
                innerTableDataDTO.setValue(innerTableData.get(r).get(c));
                innerTableDataDTO.setRowNum(r);
                innerTableDataDTO.setColumnNum(c);
                innerTableDataDTO.setTotalRowNum(rowNum);
                innerTableDataDTO.setTotalColumnNum(colNum);
                innerTableDataDTO.setFontType(fontType);
                innerTableDataDTO.setFontSize(fontSize);
                innerTableDataDTO.setBorderWidth(borderWidth);
                innerTableDataDTO.setZbNeedSetTopBorder(zbNeedSetTopBorder);
                OfficeUtil.setInnerTableColumnText(innerTableDataDTO);

                if(innerCellWidth.doubleValue() > 0) {
                    // 设置每个列宽
                    BigDecimal cw = null;
                    if(null != columnWidth && columnWidth.length > 0 && null != columnWidth[c]) {
                        // 设置了宽度百分比则计算实际宽度值： 百分比 * 所在单元格总宽度 / 100
                        cw = BigDecimal.valueOf(Double.parseDouble(columnWidth[c])).multiply(innerCellWidth).divide(new BigDecimal(100), 0, BigDecimal.ROUND_HALF_UP);
                    } else {
                        // 如果没有设置宽度则当前列默认占总列数等宽宽度: 所在单元格总宽度 / 列数
                        cw = innerCellWidth.divide(new BigDecimal(colNum), 0, BigDecimal.ROUND_HALF_UP);
                    }

                    innerTable.getRow(r).getCell(c).setWidthType(TableWidthType.DXA);
                    innerTable.getRow(r).getCell(c).setWidth(String.valueOf(cw));
                }

                // 设置文本垂直居中
                innerTable.getRow(r).getCell(c).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

                // 设置行高
                if(StringUtils.isNotBlank(innerRowHeight)) {
                    innerTable.getRow(r).setHeight(Integer.parseInt(innerRowHeight));
                } else if(null != rowHeight){
                    if(1 == rowNum) {
                        innerTable.getRow(r).setHeight(rowHeight.intValue());
                    } else {
                        innerTable.getRow(r).setHeight(rowHeight.divide(new BigDecimal(rowNum), 0, BigDecimal.ROUND_HALF_UP).intValue());
                    }
                }
            }
        }

        // 删除插入表格后下面多出的段落 注：插入表格后该单元格段落数为0
        List<XWPFParagraph> newParagraphs = innerCell.getParagraphs();
        if (CollectionUtils.isNotEmpty(newParagraphs)) {
            innerCell.removeParagraph(newParagraphs.size() - 1);
        }
    }

    /**
     * 设置内嵌子表列宽
     * @param table 内嵌子表所在表格
     * @param inCellTable 内嵌子表
     * @param tableData 内嵌子表对应数据
     * @param cellLocationRow 内嵌子表所在行
     * @param cellLocationCol 内嵌子表所在列
     * @param colNum 内嵌子表总列数
     */
    private void setInCellTableColumnWidth(XWPFTable table, XWPFTable inCellTable, List<Map<String, Object>> tableData, int cellLocationRow, int cellLocationCol, int colNum) {
        // 内嵌子表所在单元格宽度
        double inCellWith = table.getRow(cellLocationRow).getCell(cellLocationCol).getWidthDecimal();
        // 缓存每个列宽度
        BigDecimal[] colWidths = new BigDecimal[colNum];

        // 每行数据设置的列宽一致，只需要从第一行数据解析即可
        Map<String, Object> fieldNameMap = tableData.get(0);
        Iterator<Map.Entry<String, Object>> iterator = fieldNameMap.entrySet().iterator();
        while (iterator.hasNext()) {
            String[] field = iterator.next().getKey().split("_");
            if(field.length != 2 && field.length != 3) {
                LOGGER.error("解析内嵌表格字段名失败，原因：xml字段名配置错误");
                throw new AppException("xml单元格嵌套子表字段名称配置错误，解析失败");
            }

            // 字段设置的宽度占据百分比
            int colVal = Integer.parseInt(field[1]);
            if(colVal > 100) {
                LOGGER.error("解析内嵌表格字段名失败，原因：列宽度设置超出100%");
                throw new AppException("xml单元格嵌套子表列宽设置错误，解析失败");
            }

            if(2 == field.length) {
                // 如果没有设置宽度则当前列默认占总列数等宽宽度: 所在单元格总宽度 / 列数
                colWidths[colVal] = new BigDecimal(inCellWith).divide(new BigDecimal(colNum), 4, BigDecimal.ROUND_HALF_UP);
            } else {
                // 设置了宽度百分比则计算实际宽度值： 百分比 * 所在单元格总宽度 / 100
                colWidths[colVal] = new BigDecimal(Double.parseDouble(field[2])).multiply(new BigDecimal(inCellWith)).divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP);
            }
        }

        CTTblGrid ctTblGrid = inCellTable.getCTTbl().addNewTblGrid();
        for (BigDecimal width : colWidths) {
            CTTblGridCol gridCol = ctTblGrid.addNewGridCol();
            gridCol.setW(width.toBigInteger());
        }
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [tableData]
     * @return java.lang.String[][]
     * @description 解析字段名称 按照序号，字母定义，字段中文名称存入数组
     */
    private String[][] getFieldNameArray(List<Map<String, Object>> tableData) {
        //第一行数据为字段名
        Map<String, Object> fieldNameMap = tableData.get(0);
        String[][] fieldNameArray = new String[fieldNameMap.size()][3];
        Iterator<Map.Entry<String, Object>> iterator = fieldNameMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry) iterator.next();
            String[] field = entry.getKey().split("_");
            if(field.length != 2 && field.length != 3) {
                // 长度为2例如 ywrzjzl_2
                // 长度为3例如 ywrzjzl_2_30 30表示当前列宽度占所在单元格百分比
                LOGGER.error("解析内嵌表格字段名失败，原因：xml字段名配置错误");
                throw new AppException("xml单元格嵌套子表字段名称配置错误，解析失败");
            }
            int sequence = Integer.parseInt(field[1]);
            if (sequence < 0 || sequence >= fieldNameMap.size()) {
                LOGGER.error("解析内嵌表格字段名失败，原因：xml字段名配置错误");
                throw new AppException("xml单元格嵌套子表字段名称配置错误，解析失败");
            }
            fieldNameArray[sequence][0] = field[1];
            fieldNameArray[sequence][1] = entry.getKey();
            fieldNameArray[sequence][2] = (String) entry.getValue();
        }
        return fieldNameArray;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [table] 内嵌表所在父表
     * @return java.lang.String 本次即将生成内嵌表的表名
     * @description 获取单元格内嵌表表名
     * 1.通过正则匹配从table.text按顺序获取模板中所有内嵌表表名
     * 2.循环所有表名，得到内嵌表所在单元格对象，如果该单元格段落数为0，代表已经生成内嵌表，段落数大于0，则是本次需要生成内嵌表的单元格
     */
    private String getTableName(XWPFTable table){
        List<String> tableNameList = new ArrayList<>();
        String text = table.getText();
        Pattern pattern = compile(PDF_WORD_INCELL_TABLE_NAME_REG);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String t = matcher.group();
            tableNameList.add(t.replace(PDF_WORD_SUBTABLE_CELL_START3_REG, "")
                .replace(PDF_WORD_SUBTABLE_CELL_END_REG, ""));
        }

        for(String tableName: tableNameList){
            String[] tempArr = tableName.split("_");
            int cellLocationRow = Integer.parseInt(tempArr[3]);
            int cellLocationCol = Integer.parseInt(tempArr[4]);
            XWPFTableCell cell = table.getRows().get(cellLocationRow).getCell(cellLocationCol);
            if(null == cell) {
                return null;
            }

            if(cell.getParagraphs().size() > 0){
                return tableName;
            }
        }
        return null;
    }

    /**
     * 二层嵌套子表是否需要顶部边框
     * @param zbMap 二层嵌套子表位置数据
     * @param r 当前子表行
     * @param c 当前子表列
     */
    private boolean isNeedSetTopBorder(Map<String, Set<String>> zbMap, int r, int c) {
        if(MapUtils.isEmpty(zbMap) || 0 == r || CollectionUtils.isEmpty(zbMap.get(String.valueOf(r-1)))) {
            return false;
        }

        if(zbMap.get(String.valueOf(r-1)).contains(String.valueOf(c))) {
            // 当前二层子表所在列的上一行单元格也是二层嵌套子表则需要设置顶部边框
            return true;
        }
        return false;
    }

}
