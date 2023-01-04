package cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/1/12
 * @description 水电气过户请求
 */
@ApiModel(value = "SdqGhRequestDTO",description = "水电气过户请求")
public class SdqGhRequestDTO {

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    @ApiModelProperty(value = "权利人证件种类")
    private String qlrzjzl;

    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;

    @ApiModelProperty(value = "权利人电话")
    private String qlrdh;

    @ApiModelProperty(value = "义务人名称")
    private String ywrmc;

    @ApiModelProperty(value = "义务人证件种类")
    private String ywrzjzl;

    @ApiModelProperty(value = "义务人证件号")
    private String ywrzjh;

    @ApiModelProperty(value = "义务人电话")
    private String ywrdh;

    @ApiModelProperty(value = "面积")
    private String mj;

    @ApiModelProperty(value = "用途")
    private String yt;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "登记时间")
    private Date djsj;

    @ApiModelProperty(value = "户号")
    private String consno;

    @ApiModelProperty(value = "水电气主键")
    private String sdqid;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "过户附件")
    private List<SdqGhFileDTO> data;

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrzjzl() {
        return qlrzjzl;
    }

    public void setQlrzjzl(String qlrzjzl) {
        this.qlrzjzl = qlrzjzl;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getQlrdh() {
        return qlrdh;
    }

    public void setQlrdh(String qlrdh) {
        this.qlrdh = qlrdh;
    }

    public String getYwrmc() {
        return ywrmc;
    }

    public void setYwrmc(String ywrmc) {
        this.ywrmc = ywrmc;
    }

    public String getYwrzjzl() {
        return ywrzjzl;
    }

    public void setYwrzjzl(String ywrzjzl) {
        this.ywrzjzl = ywrzjzl;
    }

    public String getYwrzjh() {
        return ywrzjh;
    }

    public void setYwrzjh(String ywrzjh) {
        this.ywrzjh = ywrzjh;
    }

    public String getYwrdh() {
        return ywrdh;
    }

    public void setYwrdh(String ywrdh) {
        this.ywrdh = ywrdh;
    }

    public String getMj() {
        return mj;
    }

    public void setMj(String mj) {
        this.mj = mj;
    }

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getConsno() {
        return consno;
    }

    public void setConsno(String consno) {
        this.consno = consno;
    }

    public String getSdqid() {
        return sdqid;
    }

    public void setSdqid(String sdqid) {
        this.sdqid = sdqid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SdqGhFileDTO> getData() {
        return data;
    }

    public void setData(List<SdqGhFileDTO> data) {
        this.data = data;
    }

    public String getDwdm() {
        return dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
    }

    @Override
    public String toString() {
        return "SdqGhRequestDTO{" +
                "slbh='" + slbh + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", qlrzjzl='" + qlrzjzl + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", qlrdh='" + qlrdh + '\'' +
                ", ywrmc='" + ywrmc + '\'' +
                ", ywrzjzl='" + ywrzjzl + '\'' +
                ", ywrzjh='" + ywrzjh + '\'' +
                ", ywrdh='" + ywrdh + '\'' +
                ", mj='" + mj + '\'' +
                ", yt='" + yt + '\'' +
                ", zl='" + zl + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", djsj=" + djsj +
                ", consno='" + consno + '\'' +
                ", sdqid='" + sdqid + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", status='" + status + '\'' +
                ", dwdm='" + dwdm + '\'' +
                ", data=" + data +
                '}';
    }
}
