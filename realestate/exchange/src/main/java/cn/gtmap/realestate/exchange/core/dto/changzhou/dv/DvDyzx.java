package cn.gtmap.realestate.exchange.core.dto.changzhou.dv;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "DV_DYZX")
public class DvDyzx {

    private String fczh;
    private String bdcdyh;
    private String zl;
    private String bdcdjzmh;
    private String dyfw;
    private Double dyje;
    private Date dykssj;
    private Date dyjssj;
    private String roomid;
    private String qlrmc;
    private String qlrzjzl;
    private String qlrzjh;
    private String dyqrmc;
    private String dyqrdlrmc;
    private String dyqrdlrzjzl;
    private String dyqrdlrzjh;

    public String getFczh() {
        return fczh;
    }

    public void setFczh(String fczh) {
        this.fczh = fczh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdjzmh() {
        return bdcdjzmh;
    }

    public void setBdcdjzmh(String bdcdjzmh) {
        this.bdcdjzmh = bdcdjzmh;
    }

    public String getDyfw() {
        return dyfw;
    }

    public void setDyfw(String dyfw) {
        this.dyfw = dyfw;
    }

    public Double getDyje() {
        return dyje;
    }

    public void setDyje(Double dyje) {
        this.dyje = dyje;
    }

    public Date getDykssj() {
        return dykssj;
    }

    public void setDykssj(Date dykssj) {
        this.dykssj = dykssj;
    }

    public Date getDyjssj() {
        return dyjssj;
    }

    public void setDyjssj(Date dyjssj) {
        this.dyjssj = dyjssj;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
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

    public String getDyqrmc() {
        return dyqrmc;
    }

    public void setDyqrmc(String dyqrmc) {
        this.dyqrmc = dyqrmc;
    }

    public String getDyqrdlrmc() {
        return dyqrdlrmc;
    }

    public void setDyqrdlrmc(String dyqrdlrmc) {
        this.dyqrdlrmc = dyqrdlrmc;
    }

    public String getDyqrdlrzjzl() {
        return dyqrdlrzjzl;
    }

    public void setDyqrdlrzjzl(String dyqrdlrzjzl) {
        this.dyqrdlrzjzl = dyqrdlrzjzl;
    }

    public String getDyqrdlrzjh() {
        return dyqrdlrzjh;
    }

    public void setDyqrdlrzjh(String dyqrdlrzjh) {
        this.dyqrdlrzjh = dyqrdlrzjh;
    }

    @Override
    public String toString() {
        return "DvDyzx{" +
                "fczh='" + fczh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", bdcdjzmh='" + bdcdjzmh + '\'' +
                ", dyfw='" + dyfw + '\'' +
                ", dyje=" + dyje +
                ", dykssj=" + dykssj +
                ", dyjssj=" + dyjssj +
                ", roomid='" + roomid + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", qlrzjzl='" + qlrzjzl + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", dyqrmc='" + dyqrmc + '\'' +
                ", dyqrdlrmc='" + dyqrdlrmc + '\'' +
                ", dyqrdlrzjzl='" + dyqrdlrzjzl + '\'' +
                ", dyqrdlrzjh='" + dyqrdlrzjh + '\'' +
                '}';
    }
}
