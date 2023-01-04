package cn.gtmap.realestate.building.ui.core.vo.lpb;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-30
 * @description 楼盘表表格 动态列实体
 */
public class LpbTableColumnVO {

    private String field;

    private boolean unresize;

    private String align;

    private String title;

    private String fixed;

    private Integer width;

    private Integer rowspan;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public boolean isUnresize() {
        return unresize;
    }

    public void setUnresize(boolean unresize) {
        this.unresize = unresize;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFixed() {
        return fixed;
    }

    public void setFixed(String fixed) {
        this.fixed = fixed;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getRowspan() {
        return rowspan;
    }

    public void setRowspan(Integer rowspan) {
        this.rowspan = rowspan;
    }
}
