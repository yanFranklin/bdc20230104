package cn.gtmap.realestate.common.core.domain.building;

import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Blob;
import java.util.Date;

@Table(name = "S_SJ_CBZDSYT")
public class CbztsytDO {

    @Id
    private String cbzdsytbh;

    private String cbfmc;

    private String cbhtbh;

    private String blc;
    private String ctblc;

    private String ctsj;

    private String scsj;

    private String wjmc;
    private Blob tx;
    private Blob mxd;
    private String dwmc;
    private String jlyhm;

    private String scjqm;
    private Blob cbzdsyt_img;
    private String uniqueid;
    private String paperwidth;
    private String paperheight;
    private String sfgz;
    private String downid;
    private String txdownid;
    private String mxddownid;
    private String uploadpath;

    private Blob slt;
    private String yxt;
    private String bz;
    private Date gxsj;

    public String getCbzdsytbh() {
        return cbzdsytbh;
    }

    public void setCbzdsytbh(String cbzdsytbh) {
        this.cbzdsytbh = cbzdsytbh;
    }

    public String getCbfmc() {
        return cbfmc;
    }

    public void setCbfmc(String cbfmc) {
        this.cbfmc = cbfmc;
    }

    public String getCbhtbh() {
        return cbhtbh;
    }

    public void setCbhtbh(String cbhtbh) {
        this.cbhtbh = cbhtbh;
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

    public String getCtsj() {
        return ctsj;
    }

    public void setCtsj(String ctsj) {
        this.ctsj = ctsj;
    }

    public String getScsj() {
        return scsj;
    }

    public void setScsj(String scsj) {
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

    public Blob getCbzdsyt_img() {
        return cbzdsyt_img;
    }

    public void setCbzdsyt_img(Blob cbzdsyt_img) {
        this.cbzdsyt_img = cbzdsyt_img;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
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

    public String getYxt() {
        return yxt;
    }

    public void setYxt(String yxt) {
        this.yxt = yxt;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    @Override
    public String toString() {
        return "CbztsytDO{" +
                "cbzdsytbh='" + cbzdsytbh + '\'' +
                ", cbfmc='" + cbfmc + '\'' +
                ", cbhtbh='" + cbhtbh + '\'' +
                ", blc='" + blc + '\'' +
                ", ctblc='" + ctblc + '\'' +
                ", ctsj='" + ctsj + '\'' +
                ", scsj='" + scsj + '\'' +
                ", wjmc='" + wjmc + '\'' +
                ", tx=" + tx +
                ", mxd=" + mxd +
                ", dwmc='" + dwmc + '\'' +
                ", jlyhm='" + jlyhm + '\'' +
                ", scjqm='" + scjqm + '\'' +
                ", cbzdsyt_img=" + cbzdsyt_img +
                ", uniqueid='" + uniqueid + '\'' +
                ", paperwidth='" + paperwidth + '\'' +
                ", paperheight='" + paperheight + '\'' +
                ", sfgz='" + sfgz + '\'' +
                ", downid='" + downid + '\'' +
                ", txdownid='" + txdownid + '\'' +
                ", mxddownid='" + mxddownid + '\'' +
                ", uploadpath='" + uploadpath + '\'' +
                ", slt=" + slt +
                ", yxt='" + yxt + '\'' +
                ", bz='" + bz + '\'' +
                ", gxsj=" + gxsj +
                '}';
    }
}
