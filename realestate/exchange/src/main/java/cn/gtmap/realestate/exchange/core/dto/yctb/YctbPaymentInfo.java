package cn.gtmap.realestate.exchange.core.dto.yctb;

import java.io.Serializable;
import java.util.List;

public class YctbPaymentInfo implements Serializable {

    private static final long serialVersionUID = -860859060022319315L;

    private String paymentstatus;
    private String sqr;
    private String sqrlxdh;
    private String sszje;
    private String ywh;
    private String zdzl;

    private List<YctbJfxx> jfList;

    public List<YctbJfxx> getJfList() {
        return jfList;
    }

    public void setJfList(List<YctbJfxx> jfList) {
        this.jfList = jfList;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
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

    public String getSszje() {
        return sszje;
    }

    public void setSszje(String sszje) {
        this.sszje = sszje;
    }

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getZdzl() {
        return zdzl;
    }

    public void setZdzl(String zdzl) {
        this.zdzl = zdzl;
    }

    public static final class YctbPaymentInfoBuilder {
        private String paymentstatus;
        private String sqr;
        private String sqrlxdh;
        private String sszje;
        private String ywh;
        private String zdzl;
        private List<YctbJfxx> jfList;

        private YctbPaymentInfoBuilder() {
        }

        public static YctbPaymentInfoBuilder anYctbPaymentInfo() {
            return new YctbPaymentInfoBuilder();
        }

        public YctbPaymentInfoBuilder withPaymentstatus(String paymentstatus) {
            this.paymentstatus = paymentstatus;
            return this;
        }

        public YctbPaymentInfoBuilder withSqr(String sqr) {
            this.sqr = sqr;
            return this;
        }

        public YctbPaymentInfoBuilder withSqrlxdh(String sqrlxdh) {
            this.sqrlxdh = sqrlxdh;
            return this;
        }

        public YctbPaymentInfoBuilder withSszje(String sszje) {
            this.sszje = sszje;
            return this;
        }

        public YctbPaymentInfoBuilder withYwh(String ywh) {
            this.ywh = ywh;
            return this;
        }

        public YctbPaymentInfoBuilder withZdzl(String zdzl) {
            this.zdzl = zdzl;
            return this;
        }

        public YctbPaymentInfoBuilder withJfList(List<YctbJfxx> jfList) {
            this.jfList = jfList;
            return this;
        }

        public YctbPaymentInfo build() {
            YctbPaymentInfo yctbPaymentInfo = new YctbPaymentInfo();
            yctbPaymentInfo.setPaymentstatus(paymentstatus);
            yctbPaymentInfo.setSqr(sqr);
            yctbPaymentInfo.setSqrlxdh(sqrlxdh);
            yctbPaymentInfo.setSszje(sszje);
            yctbPaymentInfo.setYwh(ywh);
            yctbPaymentInfo.setZdzl(zdzl);
            yctbPaymentInfo.setJfList(jfList);
            return yctbPaymentInfo;
        }
    }
}
