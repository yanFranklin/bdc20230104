package cn.gtmap.realestate.common.core.domain.accept;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wangyinghao
 * @version 1.0  2021/7/22
 * @description 资金监管监管协议对象
 */
@Table(name = "BDC_SL_ZJJGXY")
@ApiModel(value = "BdcSlZjjgxyDO", description = "资金监管监管协议对象")
public class BdcSlZjjgxyDO {
    @Id
    @ApiModelProperty(value = "监管协议编号")
    private String jgxybh;

    @ApiModelProperty(value = "合同编号")
    private String htbh;

    @ApiModelProperty(value = "合同备案编号")
    private String htbah;

    @ApiModelProperty(value = "原产权证号")
    private String ycqzh;

    @ApiModelProperty(value = "房屋坐落")
    private String fwzl;

    @ApiModelProperty(value = "出卖人名称，多个英文逗号隔开")
    private String cmr;

    @ApiModelProperty(value = "买受人名称，多个英文逗号隔开")
    private String smr;

    @ApiModelProperty(value = "成交面积（平方米）")
    private String cjmj;

    @ApiModelProperty(value = "成交金额（元）")
    private double cjje;

    @ApiModelProperty(value = "监管金额（元）")
    private double jgje;

    @ApiModelProperty(value = "经办人")
    private String jbr;

    @ApiModelProperty(value = "电子办证凭证")
    private String dzbzpz;

    @ApiModelProperty(value = "贷款状态")
    private String dkzt;

    @ApiModelProperty(value = "生成时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date scsj;

    public String getJgxybh() {
        return jgxybh;
    }

    public void setJgxybh(String jgxybh) {
        this.jgxybh = jgxybh;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getHtbah() {
        return htbah;
    }

    public void setHtbah(String htbah) {
        this.htbah = htbah;
    }

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }

    public String getFwzl() {
        return fwzl;
    }

    public void setFwzl(String fwzl) {
        this.fwzl = fwzl;
    }

    public String getCmr() {
        return cmr;
    }

    public void setCmr(String cmr) {
        this.cmr = cmr;
    }

    public String getSmr() {
        return smr;
    }

    public void setSmr(String smr) {
        this.smr = smr;
    }

    public String getCjmj() {
        return cjmj;
    }

    public void setCjmj(String cjmj) {
        this.cjmj = cjmj;
    }

    public double getCjje() {
        return cjje;
    }

    public void setCjje(double cjje) {
        this.cjje = cjje;
    }

    public double getJgje() {
        return jgje;
    }

    public void setJgje(double jgje) {
        this.jgje = jgje;
    }

    public String getJbr() {
        return jbr;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    public String getDzbzpz() {
        return dzbzpz;
    }

    public void setDzbzpz(String dzbzpz) {
        this.dzbzpz = dzbzpz;
    }

    public String getDkzt() {
        return dkzt;
    }

    public void setDkzt(String dkzt) {
        this.dkzt = dkzt;
    }

    public Date getScsj() {
        return scsj;
    }

    public void setScsj(Date scsj) {
        this.scsj = scsj;
    }

    @Override
    public String toString() {
        return "BdcSlZjjgxyDO{" +
                "jgxybh='" + jgxybh + '\'' +
                ", htbh='" + htbh + '\'' +
                ", htbah='" + htbah + '\'' +
                ", ycqzh='" + ycqzh + '\'' +
                ", fwzl='" + fwzl + '\'' +
                ", cmr='" + cmr + '\'' +
                ", smr='" + smr + '\'' +
                ", cjmj='" + cjmj + '\'' +
                ", cjje=" + cjje +
                ", jgje=" + jgje +
                ", jbr='" + jbr + '\'' +
                ", dzbzpz='" + dzbzpz + '\'' +
                ", dkzt='" + dkzt + '\'' +
                ", scsj=" + scsj +
                '}';
    }
}
