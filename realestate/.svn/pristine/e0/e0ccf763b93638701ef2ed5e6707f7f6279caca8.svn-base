package cn.gtmap.realestate.common.core.dto.certificate.eCertificate;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 【连云港市】电子证照信息
 */
public class CertOwnerDzzzDTO {
    private Status status;
    private Custom custom;

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setCustom(Custom custom) {
        this.custom = custom;
    }

    public Custom getCustom() {
        return custom;
    }


    public static class Status {

        private String text;
        private int code;

        public void setText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

    }

    public static class Custom {

        private String text;
        private String code;
        private List<Info> info = new ArrayList<>();

        public void setText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setInfo(List<Info> info) {
            this.info = info;
        }

        public List<Info> getInfo() {
            return info;
        }

    }

    public static class Info {

        private Certinfoextension certinfoextension;
        private Certinfo certinfo;

        public void setCertinfoextension(Certinfoextension certinfoextension) {
            this.certinfoextension = certinfoextension;
        }

        public Certinfoextension getCertinfoextension() {
            return certinfoextension;
        }

        public void setCertinfo(Certinfo certinfo) {
            this.certinfo = certinfo;
        }

        public Certinfo getCertinfo() {
            return certinfo;
        }

    }

    public static class Certinfoextension {

        private String zh;

        @JsonFormat(pattern = "YYYY-MM-dd")
        private Date nyr;
        private String qllx;
        private String fj;
        private String yt;
        private String file;
        private String gyqk;
        private String qlqtqk;
        private String syqx;
        private String bh;
        private String bdcdyh;
        private String qlxz;
        private String qlr;
        private String mj;
        private String zl;

        public void setZh(String zh) {
            this.zh = zh;
        }

        public String getZh() {
            return zh;
        }

        public void setNyr(Date nyr) {
            this.nyr = nyr;
        }

        public Date getNyr() {
            return nyr;
        }

        public void setQllx(String qllx) {
            this.qllx = qllx;
        }

        public String getQllx() {
            return qllx;
        }

        public void setFj(String fj) {
            this.fj = fj;
        }

        public String getFj() {
            return fj;
        }

        public void setYt(String yt) {
            this.yt = yt;
        }

        public String getYt() {
            return yt;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getFile() {
            return file;
        }

        public void setGyqk(String gyqk) {
            this.gyqk = gyqk;
        }

        public String getGyqk() {
            return gyqk;
        }

        public void setQlqtqk(String qlqtqk) {
            this.qlqtqk = qlqtqk;
        }

        public String getQlqtqk() {
            return qlqtqk;
        }

        public void setSyqx(String syqx) {
            this.syqx = syqx;
        }

        public String getSyqx() {
            return syqx;
        }

        public void setBh(String bh) {
            this.bh = bh;
        }

        public String getBh() {
            return bh;
        }

        public void setBdcdyh(String bdcdyh) {
            this.bdcdyh = bdcdyh;
        }

        public String getBdcdyh() {
            return bdcdyh;
        }

        public void setQlxz(String qlxz) {
            this.qlxz = qlxz;
        }

        public String getQlxz() {
            return qlxz;
        }

        public void setQlr(String qlr) {
            this.qlr = qlr;
        }

        public String getQlr() {
            return qlr;
        }

        public void setMj(String mj) {
            this.mj = mj;
        }

        public String getMj() {
            return mj;
        }

        public void setZl(String zl) {
            this.zl = zl;
        }

        public String getZl() {
            return zl;
        }

    }


    public static class Certinfo {

        private String certawarddept;
        private String certname;
//        @JsonFormat(pattern = "yyyy-MM-dd")
        private String expiredatefrom = "";

        private String awarddate = "";
        private String certownerno;
//        @JsonFormat(pattern = "yyyy-MM-dd")
        private String expiredateto = "";
        private String certownername;

        public void setCertawarddept(String certawarddept) {
            this.certawarddept = certawarddept;
        }

        public String getCertawarddept() {
            return certawarddept;
        }

        public void setCertname(String certname) {
            this.certname = certname;
        }

        public String getCertname() {
            return certname;
        }

        public void setExpiredatefrom(String expiredatefrom) {
            this.expiredatefrom = expiredatefrom;
        }

        public String getExpiredatefrom() {
            return expiredatefrom;
        }

        public void setAwarddate(String awarddate) {
            this.awarddate = awarddate;
        }

        public String getAwarddate() {
            return awarddate;
        }

        public void setCertownerno(String certownerno) {
            this.certownerno = certownerno;
        }

        public String getCertownerno() {
            return certownerno;
        }

        public void setExpiredateto(String expiredateto) {
            this.expiredateto = expiredateto;
        }

        public String getExpiredateto() {
            return expiredateto;
        }

        public void setCertownername(String certownername) {
            this.certownername = certownername;
        }

        public String getCertownername() {
            return certownername;
        }

    }
}
