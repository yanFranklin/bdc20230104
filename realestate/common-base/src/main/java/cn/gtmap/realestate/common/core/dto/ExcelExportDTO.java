package cn.gtmap.realestate.common.core.dto;

import cn.gtmap.realestate.common.core.support.excel.ExcelExpandService;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/5/22
 * @description  Excel导出信息实体定义
 */
public class ExcelExportDTO {
    /**
     * 初始行定位
     */
    private Integer rownum;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 工作簿名称
     */
    private String sheetName;

    /**
     * 表头汇总信息
     */
    private String summaryContent;

    /**
     * 表尾汇总信息
     */
    private String tailSummaryContent;

    /**
     * 列标题，以英文逗号分隔，例如  序号,名称,备注
     */
    private String cellTitle;

    /**
     * 列对应的key，以英文逗号分隔，内容要与cellTitle定义的标题顺序一致
     */
    private String cellKey;

    /**
     * 列宽，以英文逗号分隔，内容要与cellTitle定义的标题顺序一致
     */
    private String cellWidth;

    /**
     * 要导出的JSON数据，内容要与cellTitle定义的标题顺序一致
     */
    private String data;

    /**
     * 是否合并相同值的列: false(不合并)，true(合并)
     */
    private Boolean mergeSameCell;
    /**
     * 指定需要合并相同值的列
     */
    private String mergeCellKey;
    /**
     * 指定横向合并的单元格的key值
     */
    private String mergeColumnCellKeyList;

    /**
     * 是否添加边框
     * @return
     */
    private Boolean addBorder;

    /**
     * 字体大小
     */
    private String fontSize;

    /**
     * 合并指定主键key
     */
    private String mergeCellIdKey;

    /**
     * 扩展接口的名字
     */
    private String expandServiceName;

    /**
     * 总计信息
     */
    private String totalInfo;
    /**
     * 是否检查单元格数据量过大的标示
     */
    private Boolean cheakCell;
    /**
     * 扩展接口
     */
    private List<ExcelExpandService> expandServices;

    /**
     * 排序字段
     */
    private String sort;

    /**
     * 排序方式
     */
    private String order;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<ExcelExpandService> getExpandServices() {
        return expandServices;
    }

    public void setExpandServices(List<ExcelExpandService> expandServices) {
        this.expandServices = expandServices;
    }

    public void setAddBorder(Boolean addBorder) {
        this.addBorder = addBorder;
    }

    public boolean getAddBorder() {
        return addBorder  == null ? false : addBorder;
    }

    public void setAddBorder(boolean addBorder) {
        this.addBorder = addBorder;
    }

    public String getMergeColumnCellKeyList() {
        return mergeColumnCellKeyList;
    }

    public void setMergeColumnCellKeyList(String mergeColumnCellKeyList) {
        this.mergeColumnCellKeyList = mergeColumnCellKeyList;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getRownum() {
        return rownum;
    }

    public void setRownum(Integer rownum) {
        this.rownum = rownum;
    }


    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getCellTitle() {
        return cellTitle;
    }

    public void setCellTitle(String cellTitle) {
        this.cellTitle = cellTitle;
    }

    public String getData() {
        return data;
    }

    public String getSummaryContent() {
        return summaryContent;
    }

    public void setSummaryContent(String summaryContent) {
        this.summaryContent = summaryContent;
    }

    public String getTailSummaryContent() {
        return tailSummaryContent;
    }

    public void setTailSummaryContent(String tailSummaryContent) {
        this.tailSummaryContent = tailSummaryContent;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCellKey() {
        return cellKey;
    }

    public void setCellKey(String cellKey) {
        this.cellKey = cellKey;
    }

    public String getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(String cellWidth) {
        this.cellWidth = cellWidth;
    }

    public Boolean getMergeSameCell() {
        return mergeSameCell == null ? false : mergeSameCell;
    }

    public void setMergeSameCell(Boolean mergeSameCell) {
        this.mergeSameCell = mergeSameCell;
    }

    public String getMergeCellKey() {
        return mergeCellKey;
    }

    public void setMergeCellKey(String mergeCellKey) {
        this.mergeCellKey = mergeCellKey;
    }

    public String getMergeCellIdKey() {
        return mergeCellIdKey;
    }

    public void setMergeCellIdKey(String mergeCellIdKey) {
        this.mergeCellIdKey = mergeCellIdKey;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getExpandServiceName() { return expandServiceName; }

    public void setExpandServiceName(String expandServiceName) { this.expandServiceName = expandServiceName; }

    public String getTotalInfo() { return totalInfo; }

    public void setTotalInfo(String totalInfo) { this.totalInfo = totalInfo; }

    public Boolean getCheakCell() {
        return cheakCell;
    }

    public void setCheakCell(Boolean cheakCell) {
        this.cheakCell = cheakCell;
    }

    @Override
    public String toString() {
        return "ExcelExportDTO{" +
                "rownum=" + rownum +
                ", fileName='" + fileName + '\'' +
                ", sheetName='" + sheetName + '\'' +
                ", summaryContent='" + summaryContent + '\'' +
                ", cellTitle='" + cellTitle + '\'' +
                ", cellKey='" + cellKey + '\'' +
                ", cellWidth='" + cellWidth + '\'' +
                ", data='" + data + '\'' +
                ", mergeSameCell=" + mergeSameCell +
                ", mergeCellKey='" + mergeCellKey + '\'' +
                ", mergeColumnCellKeyList='" + mergeColumnCellKeyList + '\'' +
                ", addBorder=" + addBorder +
                ", fontSize=" + fontSize +
                ", mergeCellIdKey='" + mergeCellIdKey + '\'' +
                ", expandServices=" + expandServices +
                ", expandServiceName=" + expandServiceName +
                ", totalInfo=" + totalInfo +
                ", cheakCell=" + cheakCell +
                '}';
    }
}
