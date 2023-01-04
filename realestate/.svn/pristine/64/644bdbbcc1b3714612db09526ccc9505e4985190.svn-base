package cn.gtmap.realestate.common.core.dto.exchange.court.kz;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 法院提供的司法控制请求-被执行人基本信息
 *
 * @author wangyinghao
 */
@XmlRootElement(name = "BDCKZQQ")
@XStreamAlias("BDCKZQQ")
public class CourtKzBdcQQItem {
    @ApiModelProperty(value = "控制请求单号")
    private String CXQQDH;

    @ApiModelProperty(value = "类别")
    private String LB;

    @ApiModelProperty(value = "性质")
    private String XZ;

    @ApiModelProperty(value = "被查询人姓名")
    private String XM;

    @ApiModelProperty(value = "国家或地区")
    private String GJ;

    @ApiModelProperty(value = "证件种类")
    private String ZJZL;

    @ApiModelProperty(value = "证件号")
    private String ZJH;

    @ApiModelProperty(value = "证件号")
    private String FIFTEENZJH;

    @ApiModelProperty(value = "发证机关所在地")
    private String FZJG;

    @ApiModelProperty(value = "执行法院名称")
    private String FYMC;

    @ApiModelProperty(value = "承办法官")
    private String CBR;

    @ApiModelProperty(value = "执行案号")
    private String AH;

    @ApiModelProperty(value = "控制通知书名称")
    private String CKH;

    @ApiModelProperty(value = "控制权利")
    @XmlElement(name = "BDCQLLIST")
    @XStreamAlias("BDCQLLIST")
    List<CourtKzBdcQlItem>  bdcQlList;

    public String getCXQQDH() {
        return CXQQDH;
    }

    public void setCXQQDH(String CXQQDH) {
        this.CXQQDH = CXQQDH;
    }

    public String getLB() {
        return LB;
    }

    public void setLB(String LB) {
        this.LB = LB;
    }

    public String getXZ() {
        return XZ;
    }

    public void setXZ(String XZ) {
        this.XZ = XZ;
    }

    public String getXM() {
        return XM;
    }

    public void setXM(String XM) {
        this.XM = XM;
    }

    public String getGJ() {
        return GJ;
    }

    public void setGJ(String GJ) {
        this.GJ = GJ;
    }

    public String getZJZL() {
        return ZJZL;
    }

    public void setZJZL(String ZJZL) {
        this.ZJZL = ZJZL;
    }

    public String getZJH() {
        return ZJH;
    }

    public void setZJH(String ZJH) {
        this.ZJH = ZJH;
    }

    public String getFIFTEENZJH() {
        return FIFTEENZJH;
    }

    public void setFIFTEENZJH(String FIFTEENZJH) {
        this.FIFTEENZJH = FIFTEENZJH;
    }

    public String getFZJG() {
        return FZJG;
    }

    public void setFZJG(String FZJG) {
        this.FZJG = FZJG;
    }

    public String getFYMC() {
        return FYMC;
    }

    public void setFYMC(String FYMC) {
        this.FYMC = FYMC;
    }

    public String getCBR() {
        return CBR;
    }

    public void setCBR(String CBR) {
        this.CBR = CBR;
    }

    public String getAH() {
        return AH;
    }

    public void setAH(String AH) {
        this.AH = AH;
    }

    public String getCKH() {
        return CKH;
    }

    public void setCKH(String CKH) {
        this.CKH = CKH;
    }

    public List<CourtKzBdcQlItem> getBdcQlList() {
        return bdcQlList;
    }

    public void setBdcQlList(List<CourtKzBdcQlItem> bdcQlList) {
        this.bdcQlList = bdcQlList;
    }
}
