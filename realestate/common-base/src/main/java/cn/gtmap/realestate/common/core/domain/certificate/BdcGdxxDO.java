package cn.gtmap.realestate.common.core.domain.certificate;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen/a>"
 * @version 1.0, 2018/11/16
 * @description 归档信息
 */
@Table(
        name = "BDC_GDXX"
)
public class BdcGdxxDO {
    @Id
    @ApiModelProperty(value = "归档信息id")
    private String gdxxid;

    @ApiModelProperty(value = "项目id")
    private String xmid;

    @ApiModelProperty(value = "档案id")
    private String daid;

    @ApiModelProperty(value = "目录号")
    private String mlh;

    @ApiModelProperty(value = "案卷号")
    private String ajh;

    @ApiModelProperty(value = "档案模型")
    private String damx;

    @ApiModelProperty(value = "档案号")
    private String dah;

    @ApiModelProperty(value = "案卷件数")
    private String ajjs;

    @ApiModelProperty(value = "案卷页数")
    private String ajys;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "归档时间",example = "2018-10-01 12:18:48")
    private Date gdsj;

    @ApiModelProperty(value = "归档人员姓名")
    private String gdrxm;

    @ApiModelProperty(value = "归档信息")
    private String gdxx;

    @ApiModelProperty(value = "备注")
    private String bz;

    public String getGdxxid() {
        return gdxxid;
    }

    public void setGdxxid(String gdxxid) {
        this.gdxxid = gdxxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getDaid() {
        return daid;
    }

    public void setDaid(String daid) {
        this.daid = daid;
    }

    public String getMlh() {
        return mlh;
    }

    public void setMlh(String mlh) {
        this.mlh = mlh;
    }

    public String getAjh() {
        return ajh;
    }

    public void setAjh(String ajh) {
        this.ajh = ajh;
    }

    public String getDamx() {
        return damx;
    }

    public void setDamx(String damx) {
        this.damx = damx;
    }

    public String getDah() {
        return dah;
    }

    public void setDah(String dah) {
        this.dah = dah;
    }

    public String getAjjs() {
        return ajjs;
    }

    public void setAjjs(String ajjs) {
        this.ajjs = ajjs;
    }

    public String getAjys() {
        return ajys;
    }

    public void setAjys(String ajys) {
        this.ajys = ajys;
    }

    public Date getGdsj() {
        return gdsj;
    }

    public void setGdsj(Date gdsj) {
        this.gdsj = gdsj;
    }

    public String getGdrxm() {
        return gdrxm;
    }

    public void setGdrxm(String gdrxm) {
        this.gdrxm = gdrxm;
    }

    public String getGdxx() {
        return gdxx;
    }

    public void setGdxx(String gdxx) {
        this.gdxx = gdxx;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Override
    public String toString() {
        return "BdcGdxxDO{" +
                "gdxxid='" + gdxxid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", daid='" + daid + '\'' +
                ", mlh='" + mlh + '\'' +
                ", ajh='" + ajh + '\'' +
                ", damx='" + damx + '\'' +
                ", dah='" + dah + '\'' +
                ", ajjs='" + ajjs + '\'' +
                ", ajys='" + ajys + '\'' +
                ", gdsj='" + gdsj + '\'' +
                ", gdrxm='" + gdrxm + '\'' +
                ", gdxx='" + gdxx + '\'' +
                ", bz='" + bz + '\'' +
                '}';
    }
}
