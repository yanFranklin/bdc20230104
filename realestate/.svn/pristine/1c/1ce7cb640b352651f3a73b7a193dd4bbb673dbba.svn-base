package cn.gtmap.realestate.exchange.core.dto.yctb;

import java.io.Serializable;
import java.util.List;

public class YctbGetPayUrlResponse implements Serializable {

    private static final long serialVersionUID = 2088662002211152751L;

    private String payurl;
    private String taxpaymenturl;
    private List<YctbTaxInfo> taxList;

    public List<YctbTaxInfo> getTaxList() {
        return taxList;
    }

    public void setTaxList(List<YctbTaxInfo> taxList) {
        this.taxList = taxList;
    }

    public String getPayurl() {
        return payurl;
    }

    public void setPayurl(String payurl) {
        this.payurl = payurl;
    }

    public String getTaxpaymenturl() {
        return taxpaymenturl;
    }

    public void setTaxpaymenturl(String taxpaymenturl) {
        this.taxpaymenturl = taxpaymenturl;
    }


    public static final class YctbGetPayUrlResponseBuilder {
        private String payurl;
        private String taxpaymenturl;
        private List<YctbTaxInfo> taxList;

        private YctbGetPayUrlResponseBuilder() {
        }

        public static YctbGetPayUrlResponseBuilder anYctbGetPayUrlResponse() {
            return new YctbGetPayUrlResponseBuilder();
        }

        public YctbGetPayUrlResponseBuilder withPayurl(String payurl) {
            this.payurl = payurl;
            return this;
        }

        public YctbGetPayUrlResponseBuilder withTaxpaymenturl(String taxpaymenturl) {
            this.taxpaymenturl = taxpaymenturl;
            return this;
        }

        public YctbGetPayUrlResponseBuilder withTaxList(List<YctbTaxInfo> taxList) {
            this.taxList = taxList;
            return this;
        }

        public YctbGetPayUrlResponse build() {
            YctbGetPayUrlResponse yctbGetPayUrlResponse = new YctbGetPayUrlResponse();
            yctbGetPayUrlResponse.setPayurl(payurl);
            yctbGetPayUrlResponse.setTaxpaymenturl(taxpaymenturl);
            yctbGetPayUrlResponse.setTaxList(taxList);
            return yctbGetPayUrlResponse;
        }
    }
}
