package cn.gtmap.realestate.common.core.dto;

import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2021/10/14
 * @description word、pdf打印内置表格单元格数据渲染传参实体
 */
public class OfficeInnerTableDataDTO {
    /**
     * 单元格所在行
     */
    private XWPFTableRow row;

    /**
     * 单元格渲染数据值
     */
    private Object value;

    /**
     * 单元格行号（从0开始）
     */
    private int rowNum;

    /**
     * 单元格列号（从0开始）
     */
    private int columnNum;

    /**
     * 所在内嵌表格总行数
     */
    private int totalRowNum;

    /**
     * 所在内嵌表格总列数
     */
    private int totalColumnNum;

    /**
     * 字体类型
     */
    private String fontType;

    /**
     * 字体大小
     */
    private String fontSize;

    /**
     * 边框宽度
     */
    private String borderWidth;

    /**
     * 子表是否需要设置上边框
     */
    private Boolean zbNeedSetTopBorder;


    public Boolean getZbNeedSetTopBorder() {
        return zbNeedSetTopBorder;
    }

    public void setZbNeedSetTopBorder(Boolean zbNeedSetTopBorder) {
        this.zbNeedSetTopBorder = zbNeedSetTopBorder;
    }

    public String getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(String borderWidth) {
        this.borderWidth = borderWidth;
    }

    /**
     * 顶部不需要设置边框
     * @return true: 不需要 false: 需要
     */
    public boolean topNeedSetNoneBorder() {
        if(0 == rowNum) {
            if(null != zbNeedSetTopBorder && zbNeedSetTopBorder) {
                // 二层嵌套子表需要设置上边框并且当前单元格在该子表第一行
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 是否最底部行
     * @return
     */
    public boolean isBottomRow() {
        return totalRowNum - 1 == rowNum;
    }

    /**
     * 是否最左边列
     * @return
     */
    public boolean isLeftColumn() {
        return 0 == columnNum;
    }

    /**
     * 是否最右边列
     * @return
     */
    public boolean isRightColumn() {
        return totalColumnNum - 1 == columnNum;
    }

    public XWPFTableRow getRow() {
        return row;
    }

    public void setRow(XWPFTableRow row) {
        this.row = row;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

    public int getTotalRowNum() {
        return totalRowNum;
    }

    public void setTotalRowNum(int totalRowNum) {
        this.totalRowNum = totalRowNum;
    }

    public int getTotalColumnNum() {
        return totalColumnNum;
    }

    public void setTotalColumnNum(int totalColumnNum) {
        this.totalColumnNum = totalColumnNum;
    }

    public String getFontType() {
        return fontType;
    }

    public void setFontType(String fontType) {
        this.fontType = fontType;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    @Override
    public String toString() {
        return "OfficeInnerTableDataDTO{" +
                "row=" + row +
                ", value=" + value +
                ", rowNum=" + rowNum +
                ", columnNum=" + columnNum +
                ", totalRowNum=" + totalRowNum +
                ", totalColumnNum=" + totalColumnNum +
                ", fontType='" + fontType + '\'' +
                ", fontSize='" + fontSize + '\'' +
                ", borderWidth='" + borderWidth + '\'' +
                '}';
    }
}
