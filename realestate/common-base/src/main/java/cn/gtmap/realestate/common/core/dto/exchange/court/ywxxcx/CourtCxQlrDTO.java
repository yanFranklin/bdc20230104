package cn.gtmap.realestate.common.core.dto.exchange.court.ywxxcx;

import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/12/07 15:13
 */
@XmlRootElement(name = "QLRXX")
public class CourtCxQlrDTO {

    @XmlElement(name = "BDCDYH")
    @ApiModelProperty(value = "不动产单元号")
    public String bdcdyh;

    @XmlElement(name = "SXH")
    @ApiModelProperty(value = "顺序号")
    public String sxh;

    @XmlElement(name = "QLRMC")
    @ApiModelProperty(value = "权利人名称")
    public String qlrmc;

    @XmlElement(name = "BDCQZH")
    @ApiModelProperty(value = "不动产权证号")
    public String bdcqzh;

    @XmlElement(name = "QZYSXLH")
    @ApiModelProperty(value = "权证印刷序列号")
    public String qzysxlh;

    @XmlElement(name = "SFCZR")
    @ApiModelProperty(value = "是否持证人")
    public String sfczr;

    @XmlElement(name = "ZJZL")
    @ApiModelProperty(value = "证件种类")
    public String zjzl;

    @XmlElement(name = "ZJH")
    @ApiModelProperty(value = "证件号")
    public String zjh;

    @XmlElement(name = "FZJG")
    @ApiModelProperty(value = "发证机关")
    public String fzjg;

    @XmlElement(name = "SSHY")
    @ApiModelProperty(value = "所属行业")
    public String sshy;

    @XmlElement(name = "GJ")
    @ApiModelProperty(value = "国家/地区")
    public String gj;

    @XmlElement(name = "HJSZSS")
    @ApiModelProperty(value = "户籍所在省市")
    public String hjszss;

    @XmlElement(name = "XB")
    @ApiModelProperty(value = "性别")
    public String xb;

    @XmlElement(name = "DH")
    @ApiModelProperty(value = "电话")
    public String dh;

    @XmlElement(name = "DZ")
    @ApiModelProperty(value = "地址")
    public String dz;

    @XmlElement(name = "YB")
    @ApiModelProperty(value = "邮编")
    public String yb;

    @XmlElement(name = "GZDW")
    @ApiModelProperty(value = "工作单位")
    public String gzdw;

    @XmlElement(name = "DZYJ")
    @ApiModelProperty(value = "电子邮件")
    public String dzyj;

    @XmlElement(name = "QLRLX")
    @ApiModelProperty(value = "权利人类型")
    public String qlrlx;

    @XmlElement(name = "QLBL")
    @ApiModelProperty(value = "权利比例")
    public String qlbl;

    @XmlElement(name = "GYFS")
    @ApiModelProperty(value = "共有方式")
    public String gyfs;

    @XmlElement(name = "GYQK")
    @ApiModelProperty(value = "共有情况")
    public String gyqk;

    @XmlElement(name = "BZ")
    @ApiModelProperty(value = "备注")
    public String bz;

    @XmlElement(name = "YWH")
    @ApiModelProperty(value = "业务号")
    public String ywh;

    @XmlElement(name = "QSZT")
    @ApiModelProperty(value = "权属状态")
    public String qszt;

    @XmlElement(name = "BDCLX")
    @ApiModelProperty(value = "不动产类型")
    public String bdclx;

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public void setSxh(String sxh) {
        this.sxh = sxh;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public void setQzysxlh(String qzysxlh) {
        this.qzysxlh = qzysxlh;
    }

    public void setSfczr(String sfczr) {
        this.sfczr = sfczr;
    }

    public void setZjzl(String zjzl) {
        this.zjzl = zjzl;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public void setFzjg(String fzjg) {
        this.fzjg = fzjg;
    }

    public void setSshy(String sshy) {
        this.sshy = sshy;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }

    public void setHjszss(String hjszss) {
        this.hjszss = hjszss;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    public void setYb(String yb) {
        this.yb = yb;
    }

    public void setGzdw(String gzdw) {
        this.gzdw = gzdw;
    }

    public void setDzyj(String dzyj) {
        this.dzyj = dzyj;
    }

    public void setQlrlx(String qlrlx) {
        this.qlrlx = qlrlx;
    }

    public void setQlbl(String qlbl) {
        this.qlbl = qlbl;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }
}
