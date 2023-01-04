package cn.gtmap.realestate.building.core.bo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-02
 * @description 楼盘表查询配置 字段实体
 */
@XStreamAlias("column")
public class ColumnBO {

    /**
     * 字段名 别名 英文，用于 取其他字段的值，但仍然作为别名字段应用的场景
     */
    private String alias;

    /**
     * 字段名
     */
    private String value;

    /**
     * 字段中文名 户室资源配置后 显示在单元格内
     */
    private String desc;

    /**
     * 类型： BUTTON 按钮、CONSTANT 常量、COLUMN(默认) 表字段、
     * ZD 字典项、 JOINCOLUMN 关联字段、SHOWCOLUMN 展示可勾选字段
     */
    private String type;

    /**
     * 按钮回调函数
     */
    private String btnCallback;

    /**
     * 按钮类型 事件名称
     */
    private String btnEvent;
    /**
     * 按钮类型 按钮样式
     */
    private String btnClass;

    /**
     * 按钮类型 是否放在更多操作中
     */
    private String btnMore;

    /**
     * 字典类型 字典项表实体名
     */
    private String zdDoClass;

    /**
     * TAB类型  hs ychs
     */
    private String tabType;

    private String constant;

    /**
     * SHOWCOLUMN字段类型 关联字段
     */
    private String refInfo;

    /**
     * SHOWCOLUMN字段类型 关联状态
     */
    private String refStatus;

    /**
     * SHOWCOLUMN字段类型   是否默认展示
     */
    private String defaultShow;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return
     * @description SHOWCOLUMN 执行JS 方法
     */
    private String showFunction;


    /**
     * JOINCOLUMN 字段类型 关联查询SQL
     */
    private String joinSql;

    /**
     * JOINCOLUMN 分隔符 当 查询结果多个时 使用,默认 “,”
     */
    private String separator;

    /**
     * NVLCOLUMN 逗号隔开
     */
    private String nvl;

    /*
     * 字体颜色
     * */
    private String font;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBtnEvent() {
        return btnEvent;
    }

    public void setBtnEvent(String btnEvent) {
        this.btnEvent = btnEvent;
    }

    public String getBtnClass() {
        return btnClass;
    }

    public void setBtnClass(String btnClass) {
        this.btnClass = btnClass;
    }

    public String getZdDoClass() {
        return zdDoClass;
    }

    public void setZdDoClass(String zdDoClass) {
        this.zdDoClass = zdDoClass;
    }

    public String getBtnCallback() {
        return btnCallback;
    }

    public void setBtnCallback(String btnCallback) {
        this.btnCallback = btnCallback;
    }

    public String getBtnMore() {
        return btnMore;
    }

    public void setBtnMore(String btnMore) {
        this.btnMore = btnMore;
    }

    public String getTabType() {
        return tabType;
    }

    public void setTabType(String tabType) {
        this.tabType = tabType;
    }

    public String getConstant() {
        return constant;
    }

    public void setConstant(String constant) {
        this.constant = constant;
    }

    public String getRefInfo() {
        return refInfo;
    }

    public void setRefInfo(String refInfo) {
        this.refInfo = refInfo;
    }

    public String getRefStatus() {
        return refStatus;
    }

    public void setRefStatus(String refStatus) {
        this.refStatus = refStatus;
    }

    public String getDefaultShow() {
        return defaultShow;
    }

    public void setDefaultShow(String defaultShow) {
        this.defaultShow = defaultShow;
    }

    public String getJoinSql() {
        return joinSql;
    }

    public void setJoinSql(String joinSql) {
        this.joinSql = joinSql;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getNvl() {
        return nvl;
    }

    public void setNvl(String nvl) {
        this.nvl = nvl;
    }

    public String getShowFunction() {
        return showFunction;
    }

    public void setShowFunction(String showFunction) {
        this.showFunction = showFunction;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }
}
