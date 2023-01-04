package cn.gtmap.realestate.inquiry.core.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/4
 * @description 业务排号 窗口信息
 */
@Table(name = "BDC_CKXX")
public class BdcCkxxDO {
    @Id
    @ApiModelProperty(value = "窗口id")
    private String ckid;
    @ApiModelProperty(value = "窗口号")
    private String ckh;
    @ApiModelProperty(value = "窗口状态")
    private Integer ckzt;
    @ApiModelProperty(value = "中心名称")
    private String zxmc;
    @ApiModelProperty(value = "更新时间")
    private Date gxsj;
    @ApiModelProperty(value = "呼叫号")
    private String hjh;

    public String getCkid() {
        return ckid;
    }

    public void setCkid(String ckid) {
        this.ckid = ckid;
    }

    public String getCkh() {
        return ckh;
    }

    public void setCkh(String ckh) {
        this.ckh = ckh;
    }

    public Integer getCkzt() {
        return ckzt;
    }

    public void setCkzt(Integer ckzt) {
        this.ckzt = ckzt;
    }

    public String getZxmc() {
        return zxmc;
    }

    public void setZxmc(String zxmc) {
        this.zxmc = zxmc;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    public String getHjh() {
        return hjh;
    }

    public void setHjh(String hjh) {
        this.hjh = hjh;
    }

    @Override
    public String toString() {
        return "BdcCkxxDO{" +
                "ckid='" + ckid + '\'' +
                ", ckh='" + ckh + '\'' +
                ", ckzt=" + ckzt +
                ", zxmc='" + zxmc + '\'' +
                ", gxsj=" + gxsj +
                ", hjh='" + hjh + '\'' +
                '}';
    }
}
