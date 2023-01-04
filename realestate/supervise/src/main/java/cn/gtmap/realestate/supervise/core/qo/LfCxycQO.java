package cn.gtmap.realestate.supervise.core.qo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/08/31
 * @description 查询异常统计信息查询QO
 */
public class LfCxycQO {
    @ApiModelProperty(value = "人员角色")
    private String ryjs;

    @ApiModelProperty(value = "查询时间(起)")
    private String cxsjq;

    @ApiModelProperty(value = "查询时间(止)")
    private String cxsjz;

    @ApiModelProperty(value = "异常标准")
    private Integer ycbz;

    @ApiModelProperty(value = "用户ID")
    private List<String> yhids;

    @ApiModelProperty(value = "重复查询字段")
    private String cfcxzd;


    public List<String> getYhids() {
        return yhids;
    }

    public void setYhids(List<String> yhids) {
        this.yhids = yhids;
    }

    public String getRyjs() {
        return ryjs;
    }

    public void setRyjs(String ryjs) {
        this.ryjs = ryjs;
    }

    public String getCxsjq() {
        return cxsjq;
    }

    public void setCxsjq(String cxsjq) {
        this.cxsjq = cxsjq;
    }

    public String getCxsjz() {
        return cxsjz;
    }

    public void setCxsjz(String cxsjz) {
        this.cxsjz = cxsjz;
    }

    public Integer getYcbz() {
        return ycbz;
    }

    public void setYcbz(Integer ycbz) {
        this.ycbz = ycbz;
    }

    public String getCfcxzd() {
        return cfcxzd;
    }

    public void setCfcxzd(String cfcxzd) {
        this.cfcxzd = cfcxzd;
    }

    @Override
    public String toString() {
        return "LfCxycQO{" +
                "ryjs='" + ryjs + '\'' +
                ", cxsjq='" + cxsjq + '\'' +
                ", cxsjz='" + cxsjz + '\'' +
                ", ycbz=" + ycbz +
                ", yhids=" + yhids +
                ", cfcxzd='" + cfcxzd + '\'' +
                '}';
    }
}
