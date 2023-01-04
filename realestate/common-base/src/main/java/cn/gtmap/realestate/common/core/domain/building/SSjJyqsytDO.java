package cn.gtmap.realestate.common.core.domain.building;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Blob;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/4
 * @description 土地经营权图件
 */
@Table(name = "s_sj_jyqsyt")
public class SSjJyqsytDO {

    @Id
    @ApiModelProperty(value = "宗地图编号")
    private String zdtbh;

    @ApiModelProperty(value = "经营权权利人主键")
    private String jyqDkQlrIndex;

    @ApiModelProperty(value = "比例尺")
    private String blc;

    @ApiModelProperty(value = "出图比例尺")
    private String ctblc;

    @ApiModelProperty(value = "出图时间")
    private Date ctsj;

    @ApiModelProperty(value = "上传时间")
    private Date scsj;

    @ApiModelProperty(value = "文件名称")
    private String wjmc;

    @ApiModelProperty(value = "图形")
    private Blob tx;

    @ApiModelProperty(value = "MXD")
    private Blob mxd;

    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @ApiModelProperty(value = "上传用户名")
    private String jlyhm;

    @ApiModelProperty(value = "上传机器名")
    private String scjqm;

    @ApiModelProperty(value = "宗地图图片")
    private Blob zdtImg;

    @ApiModelProperty(value = "纸张宽度")
    private String paperwidth;

    @ApiModelProperty(value = "纸张高度")
    private String paperheight;

    @ApiModelProperty(value = "标识ID")
    private String uniqueid;

    @ApiModelProperty(value = "是否盖章")
    private String sfgz;

    @ApiModelProperty(value = "图片文件下载ID")
    private String downid;

    @ApiModelProperty(value = "图件mdb下载ID")
    private String txdownid;

    @ApiModelProperty(value = "图件mxd下载ID")
    private String mxddownid;

    @ApiModelProperty(value = "图件上传相对路径")
    private String uploadpath;

    @ApiModelProperty(value = "矢量图")
    private Blob slt;

    @ApiModelProperty(value = "影像图")
    private Blob yxt;

    @ApiModelProperty(value = "更新时间")
    private Date gxsj;

    @ApiModelProperty(value = "备注")
    private String bz;

    public String getZdtbh() {
        return zdtbh;
    }

    public void setZdtbh(String zdtbh) {
        this.zdtbh = zdtbh;
    }

    public String getJyqDkQlrIndex() {
        return jyqDkQlrIndex;
    }

    public void setJyqDkQlrIndex(String jyqDkQlrIndex) {
        this.jyqDkQlrIndex = jyqDkQlrIndex;
    }

    public String getBlc() {
        return blc;
    }

    public void setBlc(String blc) {
        this.blc = blc;
    }

    public String getCtblc() {
        return ctblc;
    }

    public void setCtblc(String ctblc) {
        this.ctblc = ctblc;
    }

    public Date getCtsj() {
        return ctsj;
    }

    public void setCtsj(Date ctsj) {
        this.ctsj = ctsj;
    }

    public Date getScsj() {
        return scsj;
    }

    public void setScsj(Date scsj) {
        this.scsj = scsj;
    }

    public String getWjmc() {
        return wjmc;
    }

    public void setWjmc(String wjmc) {
        this.wjmc = wjmc;
    }

    public Blob getTx() {
        return tx;
    }

    public void setTx(Blob tx) {
        this.tx = tx;
    }

    public Blob getMxd() {
        return mxd;
    }

    public void setMxd(Blob mxd) {
        this.mxd = mxd;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getJlyhm() {
        return jlyhm;
    }

    public void setJlyhm(String jlyhm) {
        this.jlyhm = jlyhm;
    }

    public String getScjqm() {
        return scjqm;
    }

    public void setScjqm(String scjqm) {
        this.scjqm = scjqm;
    }

    public Blob getZdtImg() {
        return zdtImg;
    }

    public void setZdtImg(Blob zdtImg) {
        this.zdtImg = zdtImg;
    }

    public String getPaperwidth() {
        return paperwidth;
    }

    public void setPaperwidth(String paperwidth) {
        this.paperwidth = paperwidth;
    }

    public String getPaperheight() {
        return paperheight;
    }

    public void setPaperheight(String paperheight) {
        this.paperheight = paperheight;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getSfgz() {
        return sfgz;
    }

    public void setSfgz(String sfgz) {
        this.sfgz = sfgz;
    }

    public String getDownid() {
        return downid;
    }

    public void setDownid(String downid) {
        this.downid = downid;
    }

    public String getTxdownid() {
        return txdownid;
    }

    public void setTxdownid(String txdownid) {
        this.txdownid = txdownid;
    }

    public String getMxddownid() {
        return mxddownid;
    }

    public void setMxddownid(String mxddownid) {
        this.mxddownid = mxddownid;
    }

    public String getUploadpath() {
        return uploadpath;
    }

    public void setUploadpath(String uploadpath) {
        this.uploadpath = uploadpath;
    }

    public Blob getSlt() {
        return slt;
    }

    public void setSlt(Blob slt) {
        this.slt = slt;
    }

    public Blob getYxt() {
        return yxt;
    }

    public void setYxt(Blob yxt) {
        this.yxt = yxt;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }
}
