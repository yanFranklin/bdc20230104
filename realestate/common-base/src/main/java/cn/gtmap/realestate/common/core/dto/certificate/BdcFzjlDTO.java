package cn.gtmap.realestate.common.core.dto.certificate;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/27
 * @description 发证记录DTO对象
 */
public class BdcFzjlDTO {
    @ApiModelProperty(value = "不动产证书DTOList")
    List<BdcFzjlZsDTO> bdcFzjlZsDTOList;

    @ApiModelProperty(value = "项目ID")
    String xmid;

    @ApiModelProperty(value = "受理编号")
    String slbh;

    @DateTimeFormat(pattern = "yyyy年MM月dd日")
    @JsonFormat(pattern = "yyyy年MM月dd日")
    @ApiModelProperty(value = "发证日期")
    Date fzrq;

    @ApiModelProperty(value = "申请人（项目的所有权利人，以空格拼接）")
    String sqr;

    @ApiModelProperty(value = "申请人联系电话（项目的所有权利人的联系电话，以空格拼接）")
    String sqrlxdh;

    @ApiModelProperty(value = "坐落")
    String zl;

    @ApiModelProperty(value = "缮证人")
    String szr;

    @ApiModelProperty(value = "发证人")
    String fzr;

    @ApiModelProperty(value = "备注")
    String bz;

    @ApiModelProperty(value = "是否合并显示")
    Boolean sfhb;

    @ApiModelProperty(value = "是否提示邮储银行")
    String sftsYcyh;

    @ApiModelProperty(value = "发证意见")
    String fzyj;

    @ApiModelProperty(value = "有证书的项目id")
    List<String> xmidList;

    public List<BdcFzjlZsDTO> getBdcFzjlZsDTOList() {
        return bdcFzjlZsDTOList;
    }

    public void setBdcFzjlZsDTOList(List<BdcFzjlZsDTO> bdcFzjlZsDTOList) {
        this.bdcFzjlZsDTOList = bdcFzjlZsDTOList;
    }

    public String getFzyj() { return fzyj; }

    public void setFzyj(String fzyj) { this.fzyj = fzyj; }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getSqr() {
        return sqr;
    }

    public void setSqr(String sqr) {
        this.sqr = sqr;
    }

    public String getSqrlxdh() {
        return sqrlxdh;
    }

    public void setSqrlxdh(String sqrlxdh) {
        this.sqrlxdh = sqrlxdh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getSzr() {
        return szr;
    }

    public void setSzr(String szr) {
        this.szr = szr;
    }

    public String getFzr() {
        return fzr;
    }

    public void setFzr(String fzr) {
        this.fzr = fzr;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Date getFzrq() {
        return fzrq;
    }

    public void setFzrq(Date fzrq) {
        this.fzrq = fzrq;
    }

    public Boolean getSfhb() {
        return sfhb;
    }

    public void setSfhb(Boolean sfhb) {
        this.sfhb = sfhb;
    }

    public String getSftsYcyh() {
        return sftsYcyh;
    }

    public void setSftsYcyh(String sftsYcyh) {
        this.sftsYcyh = sftsYcyh;
    }

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }

    @Override
    public String toString() {
        return "BdcFzjlDTO{" +
                "bdcFzjlZsDTOList=" + bdcFzjlZsDTOList +
                ", xmid='" + xmid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", fzrq=" + fzrq +
                ", sqr='" + sqr + '\'' +
                ", sqrlxdh='" + sqrlxdh + '\'' +
                ", zl='" + zl + '\'' +
                ", szr='" + szr + '\'' +
                ", fzr='" + fzr + '\'' +
                ", bz='" + bz + '\'' +
                ", sfhb=" + sfhb +
                ", sftsYcyh='" + sftsYcyh + '\'' +
                ", fzyj='" + fzyj + '\'' +
                ", xmidList=" + xmidList +
                '}';
    }
}
