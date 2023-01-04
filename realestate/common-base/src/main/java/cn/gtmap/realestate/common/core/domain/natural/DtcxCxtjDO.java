package cn.gtmap.realestate.common.core.domain.natural;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author by hy.
 * @version 1.0, 2018/8/27
 * @description
 */
@Table(name = "dtcx_cxtj")
public class DtcxCxtjDO {
    @Id
    @ApiModelProperty("条件id")
    private String tjid;
    @ApiModelProperty("查询id")
    private String cxid;

    @ApiModelProperty("查询代号")
    private String cxdh;
    @ApiModelProperty("条件字段id")
    private String tjzdid;
    @ApiModelProperty("条件字段名称")
    private String tjzdname;
    @ApiModelProperty("默认显示")
    private String mrxs;
    @ApiModelProperty("条件类型")
    private String tjtype;
    @ApiModelProperty("字典来源")
    private String zdly;
    @ApiModelProperty("对应字典ID")
    private String zdid;
    @ApiModelProperty("字段对应方式")
    private String zddyfs;

    @ApiModelProperty("条件用途")
    private String tjusage;
    @ApiModelProperty("优先级")
    private Integer priority;
    @ApiModelProperty("是否支持模糊 精确查询相互切换")
    private Integer canmhcx;
    @ApiModelProperty("默认模糊类型")
    private Integer mrmhlx;

    public String getTjusage() {
        return tjusage;
    }

    public void setTjusage(String tjusage) {
        this.tjusage = tjusage;
    }

    public String getCxdh() {
        return cxdh;
    }

    public void setCxdh(String cxdh) {
        this.cxdh = cxdh;
    }

    public String getTjid() {
        return tjid;
    }

    public void setTjid(String tjid) {
        this.tjid = tjid;
    }

    public String getCxid() {
        return cxid;
    }

    public void setCxid(String cxid) {
        this.cxid = cxid;
    }

    public String getTjzdid() {
        return tjzdid;
    }

    public void setTjzdid(String tjzdid) {
        this.tjzdid = tjzdid;
    }

    public String getTjzdname() {
        return tjzdname;
    }

    public void setTjzdname(String tjzdname) {
        this.tjzdname = tjzdname;
    }

    public String getMrxs() {
        return mrxs;
    }

    public void setMrxs(String mrxs) {
        this.mrxs = mrxs;
    }

    public String getTjtype() {
        return tjtype;
    }

    public void setTjtype(String tjtype) {
        this.tjtype = tjtype;
    }

    public String getZdly() {
        return zdly;
    }

    public void setZdly(String zdly) {
        this.zdly = zdly;
    }

    public String getZdid() {
        return zdid;
    }

    public void setZdid(String zdid) {
        this.zdid = zdid;
    }

    public String getZddyfs() {
        return zddyfs;
    }

    public void setZddyfs(String zddyfs) {
        this.zddyfs = zddyfs;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getCanmhcx() {
        return canmhcx;
    }

    public void setCanmhcx(Integer canmhcx) {
        this.canmhcx = canmhcx;
    }

    public Integer getMrmhlx() {
        return mrmhlx;
    }

    public void setMrmhlx(Integer mrmhlx) {
        this.mrmhlx = mrmhlx;
    }

    @Override
    public String toString() {
        return "DtcxCxtjDO{" +
                "tjid='" + tjid + '\'' +
                ", cxid='" + cxid + '\'' +
                ", cxdh='" + cxdh + '\'' +
                ", tjzdid='" + tjzdid + '\'' +
                ", tjzdname='" + tjzdname + '\'' +
                ", mrxs='" + mrxs + '\'' +
                ", tjtype='" + tjtype + '\'' +
                ", zdly='" + zdly + '\'' +
                ", zdid='" + zdid + '\'' +
                ", zddyfs='" + zddyfs + '\'' +
                ", tjusage='" + tjusage + '\'' +
                ", priority=" + priority +
                ", canmhcx=" + canmhcx +
                ", mrmhlx=" + mrmhlx +
                '}';
    }
}
