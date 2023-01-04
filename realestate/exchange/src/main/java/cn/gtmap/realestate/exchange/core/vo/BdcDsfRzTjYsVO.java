package cn.gtmap.realestate.exchange.core.vo;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-03-24
 * @description 第三方日志统计  共享部门的颜色 VO
 */
public class BdcDsfRzTjYsVO {

    // 字典代码
    private String name;

    // 字典名称
    private String mc;

    // 字典颜色
    private String color;

    public BdcDsfRzTjYsVO(String name,String mc,String color){
        this.name = name;
        this.mc = mc;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
