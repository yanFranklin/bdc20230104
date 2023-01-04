package cn.gtmap.realestate.common.core.support.pdf.service.impl;

import cn.gtmap.realestate.common.core.dto.OfficeInnerTableDataDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.office.OfficeUtil;
import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.TableStyle;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import com.deepoove.poi.util.StyleUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGrid;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.gtmap.realestate.common.util.CommonConstantUtils.PDF_HHF;
import static com.deepoove.poi.policy.TextRenderPolicy.REGEX_LINE_CHARACTOR;
import static java.util.regex.Pattern.compile;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/01/06
 * @description 数据表格处理
 */
@Service
public class OfficeDocTablePolicy extends DynamicTableRenderPolicy {
    /**
     * 日志操作
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OfficeDocTablePolicy.class);

    private static final Pattern pattern = Pattern.compile("[0-9]*");

    /**
     * PDF、WORD数据表格处理特殊占位符或匹配正则表达式
     */
    // 表格名称正则表达式
    private static final String PDF_WORD_SUBTABLE_NAME_REG = "\\{\\{TABLE_[A-Za-z0-9_]+\\}\\}";
    // 表格中单元格字段正则表达式
    private static final String PDF_WORD_SUBTABLE_CELL_REG = "\\{\\{CELL_[A-Za-z0-9_]+\\}\\}";
    // 表格中复选框字段正则表达式
    private static final String PDF_WORD_SUBTABLE_CHECKBOX_REG = "\\{\\{&[A-Za-z0-9_]+";
    private static final String PDF_WORD_SUBTABLE_CHECKBOX_ALL_REG = "\\{\\{&[A-Za-z0-9_]+(#[0-9]+#)?\\}\\}";
    // 表格中图片字段正则表达式
    private static final String PDF_WORD_SUBTABLE_IMAGE_REG = "\\{\\{>[A-Za-z0-9_]+";
    // 表格中单元格字段正则表达式（前半部分）
    private static final String PDF_WORD_SUBTABLE_CELL_START_REG = "\\{\\{CELL_[A-Za-z0-9_]+";
    // 表格中单元格字段正则表达式（开头部分）
    private static final String PDF_WORD_SUBTABLE_CELL_START2_REG = "{{CELL_";
    // 表格中复选框字段正则表达式（开头部分）
    private static final String PDF_WORD_SUBTABLE_CHECKBOX_START_REG = "{{&";
    // 表格中图片字段正则表达式（开头部分），旧版，为了兼容继续支持
    private static final String PDF_WORD_SUBTABLE_IMAGE_START_REG = "{{>";
    // 表格中图片字段正则表达式（开头部分），新版
    private static final String PDF_WORD_SUBTABLE_IMAGE_START_REG2 = "PICT_";
    // 表格中嵌套子表字段正则表达式（开头部分）
    private static final String PDF_WORD_SUBTABLE_INNERTABLE_START_REG = "{{TABLE_ZB_";
    // 表格、单元格字段占位符开头
    private static final String PDF_WORD_SUBTABLE_CELL_START3_REG = "{{";
    // 表格、单元格字段占位符结尾
    private static final String PDF_WORD_SUBTABLE_CELL_END_REG = "}}";
    // 扩展表格顺序号
    private static final String PDF_WORD_SUBTABLE_INDEX_REG = "#[0-9]+#";
    // 扩展表格顺序号占位符
    private static final String PDF_WORD_SUBTABLE_INDEX_START_REG = "#";
    // 扩展表格顺序号占位符及单元格占位符结尾
    private static final String PDF_WORD_SUBTABLE_INDEX_END_REG = "#}}";
    // 嵌套子表ID和字段之间分隔
    private static final String PDF_WORD_CELL_TEXT_SPLIT = "_&&_";


    /**
     * 单元格样式
     */
    private static final TableStyle TABLE_ROW_STYLE;


    static {
        TABLE_ROW_STYLE = new TableStyle();
        TABLE_ROW_STYLE.setAlign(STJc.CENTER);
    }

    /**
     * 整个文档数据
     * 备注：这里因为OfficeDocTablePolicy每次new新建实例，不需要考虑线程安全问题
     */
    private Map<String, Object> officeData;

    public OfficeDocTablePolicy(){

    }

    public OfficeDocTablePolicy(Map<String, Object> data) {
        this.officeData = data;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @version 1.0, 2020/01/06
     * @description 数据表格处理
     */
    @Override
    public void render(XWPFTable table, Object data) {
        if (null == table || null == data) {
            LOGGER.error("PDF、WORD处理数据表格中止，原因：没有定义数据表格或者无对应数据！");
            return;
        }

        List<Map<String, Object>> tableData = (List<Map<String, Object>>) data;
        if (CollectionUtils.isEmpty(tableData)) {
            LOGGER.error("PDF、WORD处理数据表格中止，原因：无对应数据！");
            return;
        }

        // 获取表格名称
        String tableName = this.getTableName(table);

        // 解决快速打印方案表格内文字下移的问题
        if(String.valueOf(CommonConstantUtils.SF_S_DM).equals(String.valueOf(officeData.get(CommonConstantUtils.PDF_FWTP)))){
            table.setCellMargins(0, 1 , 100, 1);
        }

        try {
            if (this.isExpandTable(tableName)) {
                // 合并扩展类型子表
                resolveMergeTable(table, tableData);
            } else {
                // 普通扩展表格
                resolveGeneralTable(table, tableData);
            }
        }catch ( Exception exception){
            LOGGER.error("导出PDF、WORD处理子表出现错误：{}", exception.getMessage());
            throw exception;
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @version 1.0, 2020/01/06
     * @description 判断是否是整体扩展数据表格
     */
    private boolean isExpandTable(String tableName) {
        return StringUtils.startsWith(tableName, CommonConstantUtils.PDF_WORD_SUBTABLE_ZT1_PRE)
                || StringUtils.startsWith(tableName, CommonConstantUtils.PDF_WORD_SUBTABLE_ZT2_PRE);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param table 表格
     * @param data 数据内容
     * @description  整体扩展表格
     */
    private void resolveMergeTable(XWPFTable table, List<Map<String, Object>> data) {
        List<XWPFTableRow> rows = table.getRows();
        if(CollectionUtils.isEmpty(rows)){
            LOGGER.error("PDF、WORD处理数据表格中止，原因：表格无数据行内容！");
            return;
        }

        // 默认行数，循环中避免引用计算
        int size = rows.size();
        for (int index = 1; index < data.size(); index++) {
            // 在原有表格扩展行
            for (int i = 0; i < size; i++) {
                XWPFTableRow newRow = table.insertNewTableRow(table.getRows().size());
                copyTableRow(newRow, rows.get(i), index);
            }
        }
        // 设置模板数据表格数据
        setTableData(table.getRows(), data);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param target 目标行
     * @param source 原数据行
     * @description  复制行
     */
    public void copyTableRow(XWPFTableRow target, XWPFTableRow source, int index) {
        // 复制样式
        if (source.getCtRow() != null) {
            target.getCtRow().setTrPr(source.getCtRow().getTrPr());
        }
        // 复制单元格
        for (int i = 0; i < source.getTableCells().size(); i++) {
            XWPFTableCell cell1 = target.getCell(i);
            XWPFTableCell cell2 = source.getCell(i);
            if (cell1 == null) {
                cell1 = target.addNewTableCell();
            }
            copyTableCell(cell1, cell2, index);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param target 目标单元格cell
     * @param source 原数据单元格cell
     * @description  复制单元格
     */
    public void copyTableCell(XWPFTableCell target, XWPFTableCell source, int index) {
        // 列属性
        if (source.getCTTc() != null) {
            target.getCTTc().setTcPr(source.getCTTc().getTcPr());
        }
        // 删除段落
        for (int pos = 0; pos < target.getParagraphs().size(); pos++) {
            target.removeParagraph(pos);
        }
        // 添加段落
        for (XWPFParagraph sp : source.getParagraphs()) {
            XWPFParagraph targetP = target.addParagraph();
            copyParagraph(targetP, sp, index);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param target 目标段落
     * @param source 原数据段落
     * @description  复制段落
     */
    public void copyParagraph(XWPFParagraph target, XWPFParagraph source, int index) {
        // 设置段落样式
        target.getCTP().setPPr(source.getCTP().getPPr());

        // 移除所有的run
        for (int pos = target.getRuns().size() - 1; pos >= 0; pos--) {
            target.removeRun(pos);
        }

        // copy 新的run
        for (XWPFRun s : source.getRuns()) {
            XWPFRun targetrun = target.createRun();
            copyRun(targetrun, s, index);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param target 目标RUN
     * @param source 原数据RUN
     * @description  复制RUN
     */
    public void copyRun(XWPFRun target, XWPFRun source, int index) {
        // 设置run属性
        target.getCTR().setRPr(source.getCTR().getRPr());
        // 设置文本
        String text = source.text();

        if(StringUtils.startsWith(text, PDF_WORD_SUBTABLE_CELL_START3_REG)){
            // {{CELL_bdcqzh}} -->  {{CELL_bdcqzh#1#}}
            String endIndex = PDF_WORD_SUBTABLE_INDEX_START_REG + index + PDF_WORD_SUBTABLE_INDEX_END_REG;
            text = text.replace(PDF_WORD_SUBTABLE_CELL_END_REG, endIndex);
        }
        else if(StringUtils.startsWith(text, PDF_WORD_SUBTABLE_IMAGE_START_REG2)) {
            // 图片
            text = text + PDF_WORD_SUBTABLE_INDEX_START_REG + index + PDF_WORD_SUBTABLE_INDEX_START_REG;
        }

        target.setText(text);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param rows 表格行
     * @param data 数据内容
     * @description 设置模板数据表格数据
     */
    private void setTableData(List<XWPFTableRow> rows, List<Map<String, Object>> data) {
        // 嵌套子表顺序号
        int subTableIndex = 1;

        // 用于处理复选框顺序号 ， key 为复选框子项名称， value 为顺序号
        Map<String, Integer> checkBoxMap = new HashMap<>(10);

        for(XWPFTableRow row : rows){
            // 每一行对应的单元格
            List<ICell> cells = row.getTableICells();
            for(ICell cell : cells){
                String text = ((XWPFTableCell)cell).getText().trim();

                if (StringUtils.startsWith(text, PDF_WORD_SUBTABLE_CHECKBOX_START_REG)){
                    // 1、 复选框：例如 {{&qs_first}} *** 、{{&qs_first#1#}} ***
                    if(checkBoxMap.containsKey(text)){
                        int checkBoxIndex = checkBoxMap.get(text).intValue();
                        checkBoxMap.put(text, ++checkBoxIndex);
                    } else {
                        checkBoxMap.put(text, Integer.valueOf(0));
                    }
                    this.resolveCheckBox(data, (XWPFTableCell) cell, text, checkBoxMap.get(text).intValue());
                }
                else if (text.startsWith(PDF_WORD_SUBTABLE_IMAGE_START_REG)){
                    // 2、子表中图片(默认尺寸)：{{>name}}  {{>name#1#}}
                    this.resolveImage(data, (XWPFTableCell) cell, text);
                }
                else if (text.startsWith(PDF_WORD_SUBTABLE_IMAGE_START_REG2)){
                    // 3、子表中图片(支持默认尺寸、自定义尺寸)：PICT_name_60_60  PICT_name_60_60#1#
                    this.resolveImageWithSize(data, (XWPFTableCell) cell, text);
                }
                else if(text.endsWith(PDF_WORD_SUBTABLE_INDEX_END_REG)){
                    // 4、 子表的第二个之后的子项：{{CELL_bdcqzh#1#}}
                    this.resolveExpandTable(data, (XWPFTableCell) cell, text);
                }
                else if (text.startsWith(PDF_WORD_SUBTABLE_INNERTABLE_START_REG)){
                    // 5、 子表中嵌套子表：{{TABLE_ZB_jtcy_&&_XM:ZJH:GX}}
                    subTableIndex = this.resolveInnerTable(subTableIndex, (XWPFTableCell) cell, text);
                }
                else if(text.startsWith(PDF_WORD_SUBTABLE_CELL_START3_REG)){
                    // 6、 子表的第一个子项：{{CELL_bdcqzh}}
                    this.resolveFirstTable(data, (XWPFTableCell) cell, text);
                }
            }
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param data 数据集合
     * @param cell 单元格数据内容
     * @param text 文本内容
     * @description 处理子表图片
     */
    private void resolveImage(List<Map<String, Object>> data, XWPFTableCell cell, String text) {
        // 顺序号
        int indexNum = 0;
        String index = this.splitStr(text, PDF_WORD_SUBTABLE_INDEX_REG);
        if(StringUtils.isNotBlank(index)){
            index = index.replace(PDF_WORD_SUBTABLE_INDEX_START_REG, "");
            indexNum = Integer.parseInt(index);
        }

        // 获取字段在数据集合中的键名称：例如 {{>name}} 或 {{>name#1#}} 中 name
        String cellName = this.splitStr(text, PDF_WORD_SUBTABLE_IMAGE_REG);

        // 2、文档动态插入图片
        this.insertPicture(data, cell, indexNum, cellName, 60, 60);
    }


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param data 数据集合
     * @param cell 单元格数据内容
     * @param text 文本内容
     * @description 处理子表图片
     */
    private void resolveImageWithSize(List<Map<String, Object>> data, XWPFTableCell cell, String text) {
        // 子表顺序号
        int indexNum ;
        // 图片字段名称
        String cellName ;
        // 默认图片大小 60 * 60
        int width = 60, height = 60;

        // 1、获取图片字段、大小基本信息
        if(this.isMatch(text, "_[0-9]+_[0-9]+")) {
            // 如果设置了尺寸大小，例如 PICT_name_60_60_  PICT_name_60_60_#1#
            String[] arr = text.split("_");
            if(null == arr || arr.length < 4 || arr.length > 5){
                LOGGER.error("PDF、Word处理图片出错，模板未设置正确格式");
                return;
            }

            // 获取字段名称
            cellName = arr[1];
            // 图片尺寸大小
            width = Integer.parseInt(arr[2]);
            height = Integer.parseInt(arr[3]);
            // 顺序号
            indexNum = 4 == arr.length ? 0 : this.getIndexNum(arr[4]);
        }
        else {
            //没有设置尺寸大小，例如 PICT_name  、PICT_name#1#
            // 顺序号
            indexNum = this.getIndexNum(text);
            // 获取字段名称
            cellName = text.split("_")[1].split("#")[0];
        }

        // 2、文档动态插入图片
        this.insertPicture(data, cell, indexNum, cellName, width, height);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param data 数据集合
     * @param cell 单元格数据内容
     * @param indexNum 外层第几个子表
     * @param cellName 图片字段名称
     * @param width 图片宽
     * @param height 图片高
     * @description 文档动态插入图片
     */
    private void insertPicture(List<Map<String, Object>> data, XWPFTableCell cell, int indexNum, String cellName, int width, int height) {
        // 移除原有占位数据
        List<XWPFParagraph> paragraphs = cell.getParagraphs();
        Iterator<XWPFParagraph> it = paragraphs.iterator();
        int pos = paragraphs.size() - 1;
        while (it.hasNext()) {
            cell.removeParagraph(pos--);
        }

        // 新建占位内容
        XWPFParagraph paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();

        try {
            // 读取图片字节数据
            String pictureUrl = (String) data.get(indexNum).get(cellName);
            if (StringUtils.isBlank(pictureUrl)) {
                LOGGER.error("导出PDF、WORD处理子表图片失败，字段名称：{}，因为未获取到对应数据", cellName);
                return;
            }

            // 填充图片
            byte[] imageByteData = this.readRemoteFileToByteArray(pictureUrl);
            if (null != imageByteData) {
                run.addPicture(new ByteArrayInputStream(imageByteData), Document.PICTURE_TYPE_PNG, cellName, Units.toEMU(width), Units.toEMU(height));
            }
        } catch (Exception exception) {
            LOGGER.error("导出PDF、WORD处理子表图片失败，字段名称：{}，{}", cellName, exception.getMessage());
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param text 文本内容
     * @description 获取图片所在子表顺序号
     */
    private int getIndexNum(String text) {
        int indexNum = 0;
        String index = this.splitStr(text, PDF_WORD_SUBTABLE_INDEX_REG);
        if(StringUtils.isNotBlank(index)){
            index = index.replace(PDF_WORD_SUBTABLE_INDEX_START_REG, "").replace(PDF_WORD_SUBTABLE_CELL_END_REG, "");
            indexNum = Integer.parseInt(index);
        }
        return indexNum;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param data 数据集合
     * @param cell 单元格数据内容
     * @param text 文本内容
     * @description 处理子表第一个子项
     */
    private void resolveFirstTable(List<Map<String, Object>> data, XWPFTableCell cell, String text) {
        String cellName = this.splitStr(text, PDF_WORD_SUBTABLE_CELL_REG);
        String val = (String) data.get(0).get(cellName);
        cell.setText(val);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param cell 单元格
     * @param data 数据内容
     * @param text 文本内容
     * @description 设置模板数据表格数据
     */
    private void resolveExpandTable(List<Map<String, Object>> data, XWPFTableCell cell, String text) {
        // 表格顺序号
        String index = this.splitStr(text, PDF_WORD_SUBTABLE_INDEX_REG);
        index = index.replace(PDF_WORD_SUBTABLE_INDEX_START_REG, "");

        // 单元格名称
        String cellName = this.splitStr(text, PDF_WORD_SUBTABLE_CELL_START_REG);

        // 设置单元格实际值
        String val = (String) data.get(Integer.valueOf(index)).get(cellName);
        if(CollectionUtils.isNotEmpty(cell.getParagraphs())){
            XWPFParagraph paragraph = cell.getParagraphs().get(0);
            if(CollectionUtils.isNotEmpty(paragraph.getRuns())){
                paragraph.removeRun(0);
                XWPFRun run = paragraph.createRun();
                run.setText(val);
            }
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param subTableIndex 子表顺序号
     * @param cell 单元格数据内容
     * @param text 文本内容
     * @description 处理嵌套子表
     */
    private int resolveInnerTable(int subTableIndex, XWPFTableCell cell, String text) {
        // 嵌套子表，word模板设置格式：TABLE_ZB_子表名称_&&_XM:ZJH:GX
        String[] arr = text.replace(PDF_WORD_SUBTABLE_CELL_START3_REG, "")
                .replace(PDF_WORD_SUBTABLE_CELL_END_REG, "").split(PDF_WORD_CELL_TEXT_SPLIT);
        if(null == arr || arr.length < 2){
            return subTableIndex;
        }
        // 字体
        String fontType = "";
        // 字体大小
        String fontSize = "";
        //行高度
        String rowHeight = "";
        // 列宽设置
        String[] columnWidth = null;
        // 边框宽度
        String borderWidth = "4";

        if(arr.length > 2){
            // 例如 TABLE_ZB_jtcy_&&_XM:ZJH:GX_&&_FontsSimHei_&&_FontSize28_&&_RowHeight3000_&&_Width30:30:40_&&_Boder10
            for(int i = 2; i < arr.length; i++){
                String items = arr[i];
                if(items.startsWith(CommonConstantUtils.PDF_FONTS)){
                    fontType = items.substring(CommonConstantUtils.PDF_FONTS.length());
                    Map<String,String> fontMap = OfficeUtil.getFontMap();
                    fontType = fontMap.get(fontType);
                }
                if(items.startsWith(CommonConstantUtils.PDF_FONTSIZE)){
                    fontSize = items.substring(CommonConstantUtils.PDF_FONTSIZE.length());
                }
                if(items.startsWith(CommonConstantUtils.PDF_ROWHEIGHT)){
                    rowHeight = items.substring(CommonConstantUtils.PDF_ROWHEIGHT.length());
                }
                if(items.startsWith(CommonConstantUtils.PDF_COLUMN_WIDTH)) {
                    String width = items.substring(CommonConstantUtils.PDF_COLUMN_WIDTH.length());
                    if(StringUtils.isNotBlank(width)) {
                        columnWidth = width.split(CommonConstantUtils.ZF_YW_MH);
                    }
                }
                if(items.startsWith(CommonConstantUtils.PDF_COLUMN_BORDER)){
                    borderWidth = items.substring(CommonConstantUtils.PDF_COLUMN_BORDER.length());
                }
            }
        }
        // 子表ID及子表数据
        String subTableId = arr[0] + "_" + subTableIndex;
        List<Map<String, Object>> subTableData = (List<Map<String, Object>>) this.officeData.get(subTableId);
        if(CollectionUtils.isEmpty(subTableData)){
            // 当前嵌套子表没有数据内容则默认设置空内容，避免最终页面显示成设置的占位符
            this.setCellText(cell, "");
            subTableIndex++;
            return subTableIndex;
        }

        // 每列对应的字段
        String items = arr[1];
        String[] itemsArr = items.split(":");
        if(null == itemsArr){
            return subTableIndex;
        }

        // 获取行数
        int rowNum = subTableData.size();
        // 获取列数
        int colNum = itemsArr.length;

        // 移除原有占位数据
        List<XWPFParagraph> paragraphs = cell.getParagraphs();
        Iterator<XWPFParagraph> it = paragraphs.iterator();
        int pos = paragraphs.size() - 1;
        while(it.hasNext()){
            cell.removeParagraph(pos--);
        }

        // 新建表格
        XWPFParagraph paragraph = cell.addParagraph();

        XWPFRun run = paragraph.createRun();
        // 注意需要设置列数为0，在下面设值的时候动态绘制列
        XWPFTable subTable = ((NiceXWPFDocument) cell.getXWPFDocument()).insertNewTable(run, rowNum,0);
        CTTblGrid grid = subTable.getCTTbl().getTblGrid();
        if (grid == null) {
            subTable.getCTTbl().addNewTblGrid();
        }

        subTable.setLeftBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
        subTable.setRightBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
        subTable.setTopBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
        subTable.setBottomBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
        subTable.setInsideHBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
        subTable.setInsideVBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");

        // 内嵌子表所在单元格宽度
        double inCellWith = cell.getWidthDecimal();

        // 设置每个单元格数据
        for(int r = 0; r < rowNum; r++){
            for(int c = 0; c < colNum; c++){
                //设置内嵌表格行高
                if(StringUtils.isNotBlank(rowHeight)){
                    //判断是否为字体大小是否为数字
//                    Pattern pattern = Pattern.compile("[0-9]*");
                    Matcher isNum = pattern.matcher(rowHeight);
                    if( isNum.matches() ){
                        subTable.getRow(r).setHeight(Integer.parseInt(rowHeight));
                    }
                }

                OfficeInnerTableDataDTO innerTableDataDTO = new OfficeInnerTableDataDTO();
                innerTableDataDTO.setRow(subTable.getRow(r));
                innerTableDataDTO.setValue(String.valueOf(subTableData.get(r).get(itemsArr[c].toLowerCase())));
                innerTableDataDTO.setRowNum(r);
                innerTableDataDTO.setColumnNum(c);
                innerTableDataDTO.setTotalRowNum(rowNum);
                innerTableDataDTO.setTotalColumnNum(colNum);
                innerTableDataDTO.setFontType(fontType);
                innerTableDataDTO.setFontSize(fontSize);
                innerTableDataDTO.setBorderWidth(borderWidth);
                OfficeUtil.setInnerTableColumnText(innerTableDataDTO);

                // 设置每个列宽
                BigDecimal cw = null;
                if(null != columnWidth && columnWidth.length > 0 && null != columnWidth[c]) {
                    // 设置了宽度百分比则计算实际宽度值： 百分比 * 所在单元格总宽度 / 100
                    cw = BigDecimal.valueOf(Double.parseDouble(columnWidth[c])).multiply(new BigDecimal(inCellWith)).divide(new BigDecimal(100), 0, BigDecimal.ROUND_HALF_UP);
                } else {
                    // 如果没有设置宽度则当前列默认占总列数等宽宽度: 所在单元格总宽度 / 列数
                    cw = new BigDecimal(inCellWith).divide(new BigDecimal(colNum), 0, BigDecimal.ROUND_HALF_UP);
                }

                subTable.getRow(r).getCell(c).setWidthType(TableWidthType.DXA);
                subTable.getRow(r).getCell(c).setWidth(String.valueOf(cw));
            }
        }

        // 删除插入表格后下面多出的一行
        List<XWPFParagraph> newParagraphs = cell.getParagraphs();
        if(CollectionUtils.isNotEmpty(newParagraphs)){
            cell.removeParagraph(newParagraphs.size() - 1);
        }

        return ++subTableIndex;
    }

    /**
     * 设置文本框内容
     * @param cell 文本框
     * @param text 文本内容
     */
    private void setCellText(XWPFTableCell cell, String text){
        List<XWPFParagraph> paragraphs = cell.getParagraphs();
        if(CollectionUtils.isNotEmpty(paragraphs)){
            Iterator<XWPFParagraph> it = paragraphs.iterator();
            int pos = paragraphs.size() - 1;
            while(it.hasNext()){
                cell.removeParagraph(pos--);
            }
        }

        // 新建表格
        XWPFParagraph paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setText(text);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param data 数据集合
     * @param cell 单元格数据内容
     * @param text 文本内容
     * @param checkBoxIndex 子表扩展顺序号
     * @description 处理子表内复选框
     *
     *    例如 {{@qs_first}} *** 、{{@qs_first#1#}} ***
     *    后面的 *** 代表其它文字内容，因为复选框一般框和文字在一起
     */
    private void resolveCheckBox(List<Map<String, Object>> data, XWPFTableCell cell, String text, int checkBoxIndex) {
        // 子表顺序号：代表子表第几个子项
        int indexNum = 0;

        // 这边处理可能受模板影响，如果模板复制生成时候占位形式处理成功，如 {{@qs_first#1#}} ，那么直接去里面取顺序号
        // 如果没有，那么所有的占位符都是 {{@qs_first}} 这种，直接判断不出来，那么直接根据数据出现的次数累加判断
        if(StringUtils.contains(text, PDF_WORD_SUBTABLE_INDEX_END_REG)){
            String index = this.splitStr(text, PDF_WORD_SUBTABLE_INDEX_REG);
            if(StringUtils.isNotBlank(index)){
                index = index.replace(PDF_WORD_SUBTABLE_INDEX_START_REG, "");
                indexNum = Integer.parseInt(index);
            }
        } else {
            indexNum = checkBoxIndex;
        }

        // 获取字段在数据集合中的键名称：例如 {{@qs_first}} *** 中的 qs_first
        String cellName = this.splitStr(text, PDF_WORD_SUBTABLE_CHECKBOX_REG);
        // 获取复选框后提示内容
        String info = text.replaceAll(PDF_WORD_SUBTABLE_CHECKBOX_ALL_REG, "");

        // 移除原有占位数据
        List<XWPFParagraph> paragraphs = cell.getParagraphs();
        Iterator<XWPFParagraph> it = paragraphs.iterator();
        int pos = paragraphs.size() - 1;
        while(it.hasNext()){
            cell.removeParagraph(pos--);
        }

        // 新建占位内容
        XWPFParagraph paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = paragraph.createRun();

        ByteArrayInputStream byteArrayInputStream = null;
        try {
            // 获取复选框图片字节数据
            String pictureName = (String) data.get(indexNum).get(cellName);
            if(StringUtils.isBlank(pictureName)){
                LOGGER.error("导出PDF、WORD处理子表复选框失败，字段名称：{}，因为未获取到对应数据", cellName);
                return;
            }

            byte[] checkBoxData = StringUtils.equals(pictureName, CommonConstantUtils.PDF_WORD_CHECKBOX_PICTURE_Y) ? OfficeDataServiceImpl.checkBoxY : OfficeDataServiceImpl.checkBoxN;
            byteArrayInputStream = new ByteArrayInputStream(checkBoxData);

            // 填充复选框字段图片
            run.addPicture(byteArrayInputStream, Document.PICTURE_TYPE_PNG, cellName, 12, 12);

            // 复选框后的提示信息内容
            XWPFRun infoRun = paragraph.createRun();
            infoRun.setText(info);
        } catch (Exception exception){
            LOGGER.error("导出PDF、WORD处理子表复选框失败，字段名称：{}，{}", cellName,  exception.getMessage());
        } finally {
            IOUtils.closeQuietly(byteArrayInputStream);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param table 表格
     * @param data 数据内容
     * @description  普通扩展表格
     */
    private void resolveGeneralTable(XWPFTable table, List<Map<String, Object>> data) {
        // 获取字段名称
        List<String> cellNameList = getTableCellName(table);

        // 获取表名
        String tableName = this.getTableName(table);
        if(null == tableName){
            LOGGER.error("PDF、WORD处理数据表格中止，原因：单元格内嵌表解析错误");
            return;
        }
        String[] tempArr = tableName.split("_");
        //行高度
        String rowHeight="";
        // 字体
        String fontType="";
        // 字体大小
        String fontSize="";
        if(tempArr.length > 2){
            for(int i=2;i<tempArr.length;i++){
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
            }
        }
        if (CollectionUtils.isEmpty(cellNameList)) {
            return;
        }

        List<XWPFTableRow> rows = table.getRows();
        if(CollectionUtils.size(rows) < 2) {
            LOGGER.error("PDF、WORD处理数据表格中止，原因：单元格行设置不对");
            return;
        }
        int rowNum = CollectionUtils.size(rows) - 1;

        List<Map<String, Object>> labors = data;
        if (CollectionUtils.isNotEmpty(labors)) {
            // 删除默认的空行
            table.removeRow(rowNum);
            // 循环插入行（因为每次插入都是插入第一个位置，所有为了避免最终顺序反了，数据处理也倒序）
            for (int i = labors.size() - 1; i >= 0; i--) {
                XWPFTableRow insertNewTableRow = table.insertNewTableRow(rowNum);
                //设置普通扩展表格行高
                if(StringUtils.isNotBlank(rowHeight)){
                    //判断是否为字体大小是否为数字
//                    Pattern pattern = Pattern.compile("[0-9]*");
                    Matcher isNum = pattern.matcher(rowHeight);
                    if( isNum.matches() ){
                        insertNewTableRow.setHeight(Integer.parseInt(rowHeight));
                    }
                }
                // 列数根据设置模板列标题来，但是有可能没有，那就按照实际数据来
                // 如果直接按照实际数据来，因为有些单元格对应的数据可能为空，会造成后续数据对应错误
                int cellSize =  CollectionUtils.isEmpty(cellNameList) ? labors.get(i).size() : cellNameList.size();
                // 生成指定行列单元格
                for (int j = 0; j < cellSize; j++){
                    insertNewTableRow.createCell().setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                }

                String[] cellDatas = new String[cellSize];
                Map<String, Object> cellData = labors.get(i);

                // 生成一行数据
                if(CollectionUtils.isEmpty(cellNameList)){
                    int index = 0;
                    for(Map.Entry<String, Object> entry : cellData.entrySet()){
                        cellDatas[index++] = String.valueOf(entry.getValue());
                    }
                } else {
                    for(int k = 0; k < cellSize; k++){
                        Object itemData = cellData.get(cellNameList.get(k));
                        String dataStr = null == itemData ? "" : String.valueOf(itemData);
                        cellDatas[k] = formatStr(dataStr);
                    }
                }

                RowRenderData row = RowRenderData.build(cellDatas);
                row.setRowStyle(TABLE_ROW_STYLE);
                // 因为要设置字体大小和字体，重写了MiniTableRenderPolicy.Helper.renderRow
                renderRow(table, rowNum, row,fontSize,fontType);
            }
        }
    }

    /**
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @version 1.0, 2020/10/15
     * @description 重写了MiniTableRenderPolicy.Helper.renderRow
     */
    public static void renderRow(XWPFTable table, int row, RowRenderData rowData, String fontSize,String fontType) {
        if (null == rowData || rowData.size() <= 0){
            return;
        }
        XWPFTableRow tableRow = table.getRow(row);
        Objects.requireNonNull(tableRow, "Row " + row + " do not exist in the table");

        TableStyle rowStyle = rowData.getRowStyle();
        List<CellRenderData> cellList = rowData.getCellDatas();
        XWPFTableCell cell = null;

        for (int i = 0; i < cellList.size(); i++) {
            cell = tableRow.getCell(i);
            if (null == cell) {
                LOGGER.error("Extra cell data at row {}, but no extra cell: col {}", row, cell);
                break;
            }
            renderCell(cell, cellList.get(i), rowStyle, fontSize, fontType);
        }
    }

    /**
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @version 1.0, 2020/10/15
     * @description 处理单元格
     */
    public static void renderCell(XWPFTableCell cell, CellRenderData cellData, TableStyle rowStyle, String fontSize, String fontType) {
        TableStyle cellStyle = null == cellData.getCellStyle() ? rowStyle : cellData.getCellStyle();
        if (null != cellStyle && null != cellStyle.getBackgroundColor()) {
            cell.setColor(cellStyle.getBackgroundColor());
        }

        TextRenderData renderData = cellData.getRenderData();
        String cellText = renderData.getText();
        if (!StringUtils.isBlank(cellText)) {
            CTTc ctTc = cell.getCTTc();
            CTP ctP = ctTc.sizeOfPArray() == 0 ? ctTc.addNewP() : ctTc.getPArray(0);
            XWPFParagraph par = new XWPFParagraph(ctP, cell);
            StyleUtils.styleTableParagraph(par, cellStyle);
            String text = renderData.getText();
            String[] fragment = text.split("\\n", -1);
            if (fragment.length <= 1) {
                XWPFRun run=par.createRun();
                if(StringUtils.isNotBlank(fontType)){
                    run.setFontFamily(fontType);
                }
                if(StringUtils.isNotBlank(fontSize)){
                    //判断是否为字体大小是否为数字
//                    Pattern pattern = Pattern.compile("[0-9]*");
                    Matcher isNum = pattern.matcher(fontSize);
                    if( isNum.matches() ){
                        run.setFontSize(Integer.parseInt(fontSize));
                    }
                }

                if(text.contains(PDF_HHF)) {
                    // 解析换行符号
                    String[] array = text.split(PDF_HHF);
                    for(int i = 0; i < array.length; i++) {
                        run.setText(array[i]);
                        run.addBreak();
                    }
                } else {
                    if(StringUtils.equals(text, CommonConstantUtils.PDF_NULL_ROW_DATA)) {
                        // 当前数据为空行标识，需要展示空内容
                        text = "";
                    }
                    run.setText(text);
                }
            } else {
                for(int j = 0; j < fragment.length; ++j) {
                    if (0 != j) {
                        par = cell.addParagraph();
                        StyleUtils.styleTableParagraph(par, cellStyle);
                    }

                    XWPFRun run = par.createRun();
                    StyleUtils.styleRun(run, renderData.getStyle());
                    run.setText(fragment[j]);
                    if(StringUtils.isNotBlank(fontType)){
                        run.setFontFamily(fontType);
                    }
                    if(StringUtils.isNotBlank(fontSize)){
                        //判断是否为字体大小是否为数字
//                        Pattern pattern = Pattern.compile("[0-9]*");
                        Matcher isNum = pattern.matcher(fontSize);
                        if( isNum.matches() ){
                            run.setFontSize(Integer.parseInt(fontSize));
                        }
                    }
                }
            }
        }
    }

    /**
     * 针对单元号无法自动换行手动换行处理下
     */
    private String formatStr(String dataStr) {
        if(StringUtils.isNotBlank(dataStr) && 28 == dataStr.length() && dataStr.matches("[0-9A-Za-z]+")) {
            return dataStr.substring(0, 14) + "\n" + dataStr.substring(14, dataStr.length());
        }
        return dataStr;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @version 1.0, 2020/01/13
     * @description 获取表格名称
     */
    private String getTableName(XWPFTable table) {
        Pattern pattern = compile(PDF_WORD_SUBTABLE_NAME_REG);
        Matcher matcher = pattern.matcher(table.getText());
        String tableName = "";
        if (matcher.find()){
            tableName = matcher.group();
        }
        return tableName.replace(PDF_WORD_SUBTABLE_CELL_START3_REG, "")
                .replace(PDF_WORD_SUBTABLE_CELL_END_REG, "");
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description  获取Table列字段名称
     *
     *  例如解析后Table字段内容：序号{{CELL_xh}}	材料名称{{CELL_clmc}} 收件类型{{CELL_sjlx}} 份数{{CELL_fs}} {{TABLE_sjcl}}
     */
    private List<String> getTableCellName(XWPFTable table) {
        String text = table.getText();
        if(StringUtils.isBlank(text)){
            return null;
        }

        // 获取列字段名称
        Pattern pattern = compile(PDF_WORD_SUBTABLE_CELL_REG);
        Matcher matcher = pattern.matcher(text);
        List<String> cellNameList = new ArrayList<>(10);
        while (matcher.find()){
            cellNameList.add(matcher.group()
                    .replace(PDF_WORD_SUBTABLE_CELL_START2_REG, "")
                    .replace(PDF_WORD_SUBTABLE_CELL_END_REG, ""));
        }
        return cellNameList;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param str 目标字符串
     * @param reg 正则表达式
     * @description  正则匹配替换字符串
     */
    private String splitStr(String str, String reg){
        Pattern pattern = compile(reg);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()){
            return matcher.group()
                    .replace(PDF_WORD_SUBTABLE_CELL_START2_REG, "")
                    .replace(PDF_WORD_SUBTABLE_CHECKBOX_START_REG, "")
                    .replace(PDF_WORD_SUBTABLE_IMAGE_START_REG, "")
                    .replace(PDF_WORD_SUBTABLE_CELL_END_REG, "");
        }
        return null;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param str 目标字符串
     * @param reg 正则表达式
     * @description  正则匹配字符串
     */
    private boolean isMatch(String str, String reg) {
        Pattern pattern = compile(reg);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  filePath 文件路径
     * @description 读取远程文件数据为字节数组
     */
    private byte[] readRemoteFileToByteArray(String filePath) {
        File file = new File(UUIDGenerator.generate16() + ".png");
        try {
            FileUtils.copyURLToFile(new URIBuilder(filePath).build().toURL(), file);
            return FileUtils.readFileToByteArray(file);
        } catch (Exception e){
            LOGGER.error("导出PDF、WORD获取远程图片文件失败，文件：{}", filePath);
            return null;
        } finally {
            if(file.exists()){
                file.delete();
            }
        }

    }
}
