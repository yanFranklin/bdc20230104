package cn.gtmap.realestate.inquiry.core.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/4
 * @description 业务排号 呼叫信息
 */
@Table(name = "BDC_HJXX")
public class BdcHjxxDO {
    @Id
    @ApiModelProperty(value = "呼叫id")
    private String hjid;
    @ApiModelProperty(value = "中心名称")
    private String zxmc;
    @ApiModelProperty(value = "呼叫号")
    private String hjh;
    @ApiModelProperty(value = "生成时间")
    private Date scsj;
    @ApiModelProperty(value = "更新时间")
    private Date gxsj;
    @ApiModelProperty(value = "呼叫状态")
    private Integer hjzt;
    @ApiModelProperty(value = "业务名称")
    private String ywmc;
    @ApiModelProperty(value = "业务编码")
    private String ywbm;
    @ApiModelProperty(value = "评分")
    private Integer pf;
    @ApiModelProperty(value = "窗口号")
    private String ckh;
    @ApiModelProperty(value = "完成时间")
    private Date wcsj;

    public String getHjid() {
        return hjid;
    }

    public void setHjid(String hjid) {
        this.hjid = hjid;
    }

    public String getZxmc() {
        return zxmc;
    }

    public void setZxmc(String zxmc) {
        this.zxmc = zxmc;
    }

    public String getHjh() {
        return hjh;
    }

    public void setHjh(String hjh) {
        this.hjh = hjh;
    }

    public Date getScsj() {
        return scsj;
    }

    public void setScsj(Date scsj) {
        this.scsj = scsj;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    public Integer getHjzt() {
        return hjzt;
    }

    public void setHjzt(Integer hjzt) {
        this.hjzt = hjzt;
    }

    public String getYwmc() {
        return ywmc;
    }

    public void setYwmc(String ywmc) {
        this.ywmc = ywmc;
    }

    public String getYwbm() {
        return ywbm;
    }

    public void setYwbm(String ywbm) {
        this.ywbm = ywbm;
    }

    public Integer getPf() {
        return pf;
    }

    public void setPf(Integer pf) {
        this.pf = pf;
    }

    public String getCkh() {
        return ckh;
    }

    public void setCkh(String ckh) {
        this.ckh = ckh;
    }

    public Date getWcsj() {
        return wcsj;
    }

    public void setWcsj(Date wcsj) {
        this.wcsj = wcsj;
    }

    @Override
    public String toString() {
        return "BdcHjxxDO{" +
                "hjid='" + hjid + '\'' +
                ", zxmc='" + zxmc + '\'' +
                ", hjh='" + hjh + '\'' +
                ", scsj=" + scsj +
                ", gxsj=" + gxsj +
                ", hjzt=" + hjzt +
                ", ywmc='" + ywmc + '\'' +
                ", ywbm='" + ywbm + '\'' +
                ", pf=" + pf +
                '}';
    }
}
