package cn.gtmap.realestate.supervise.core.qo;

import cn.gtmap.realestate.supervise.core.domain.BdcLfCxjzjsDO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhuyong@gmail.com">zhuyong</a>
 * @version V1.0, 2021/09/10
 * @description 诚信机制建设 - 违规行为查询
 */
public class LfWgxwQO extends BdcLfCxjzjsDO {
    @ApiModelProperty(value = "记录时间起")
    private String jlsjq;

    @ApiModelProperty(value = "记录时间止")
    private String jlsjz;

    @ApiModelProperty(value = "审核时间起")
    private String shsjq;

    @ApiModelProperty(value = "记录时间止")
    private String shsjz;


    public String getJlsjq() {
        return jlsjq;
    }

    public void setJlsjq(String jlsjq) {
        this.jlsjq = jlsjq;
    }

    public String getJlsjz() {
        return jlsjz;
    }

    public void setJlsjz(String jlsjz) {
        this.jlsjz = jlsjz;
    }

    public String getShsjq() {
        return shsjq;
    }

    public void setShsjq(String shsjq) {
        this.shsjq = shsjq;
    }

    public String getShsjz() {
        return shsjz;
    }

    public void setShsjz(String shsjz) {
        this.shsjz = shsjz;
    }

    @Override
    public String toString() {
        return "LfWgxwQO{" +
                "jlsjq='" + jlsjq + '\'' +
                ", jlsjz='" + jlsjz + '\'' +
                ", shsjq='" + shsjq + '\'' +
                ", shsjz='" + shsjz + '\'' +
                '}';
    }
}
