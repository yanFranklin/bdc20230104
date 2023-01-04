package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.sea;

import java.io.Serializable;
import java.util.Date;

public class ZhZxdjxxDTO implements Serializable {
    
    private static final long serialVersionUID = -2222021370938497171L;

    private String ysdm;//varchar2
    private String bdcdyh;//varchar2
    private String ywh;//varchar2
    private String zxlx;//varchar
    private String zxyy;//varchar2
    private String sqr;//varchar
    private Date sqsj;//date
    private String djr;//varchar
    private Date djsj;//date
    private String shr;//varchar
    private String bz;//varchar

    private String hyglh;//varchar

    public String getYsdm() {
        return ysdm;
    }

    public void setYsdm(String ysdm) {
        this.ysdm = ysdm;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getZxlx() {
        return zxlx;
    }

    public void setZxlx(String zxlx) {
        this.zxlx = zxlx;
    }

    public String getZxyy() {
        return zxyy;
    }

    public void setZxyy(String zxyy) {
        this.zxyy = zxyy;
    }

    public String getSqr() {
        return sqr;
    }

    public void setSqr(String sqr) {
        this.sqr = sqr;
    }

    public Date getSqsj() {
        return sqsj;
    }

    public void setSqsj(Date sqsj) {
        this.sqsj = sqsj;
    }

    public String getDjr() {
        return djr;
    }

    public void setDjr(String djr) {
        this.djr = djr;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getShr() {
        return shr;
    }

    public void setShr(String shr) {
        this.shr = shr;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getHyglh() {
        return hyglh;
    }

    public void setHyglh(String hyglh) {
        this.hyglh = hyglh;
    }
}
