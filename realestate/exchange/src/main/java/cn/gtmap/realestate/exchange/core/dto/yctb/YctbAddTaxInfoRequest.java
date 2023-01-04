package cn.gtmap.realestate.exchange.core.dto.yctb;

import java.io.Serializable;

public class YctbAddTaxInfoRequest implements Serializable {

    private static final long serialVersionUID = 7610684570375597703L;

    private YctbPaymentInfo payMent;
    private YctbTaxDetail taxDetail;
    private String qxdm;

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public YctbPaymentInfo getPayMent() {
        return payMent;
    }

    public void setPayMent(YctbPaymentInfo payMent) {
        this.payMent = payMent;
    }

    public YctbTaxDetail getTaxDetail() {
        return taxDetail;
    }

    public void setTaxDetail(YctbTaxDetail taxDetail) {
        this.taxDetail = taxDetail;
    }

    public static final class YctbAddTaxInfoRequestBuilder {
        private YctbPaymentInfo payment;
        private YctbTaxDetail taxDetail;

        private YctbAddTaxInfoRequestBuilder() {
        }

        public static YctbAddTaxInfoRequestBuilder anYctbAddTaxInfoRequest() {
            return new YctbAddTaxInfoRequestBuilder();
        }

        public YctbAddTaxInfoRequestBuilder withPayment(YctbPaymentInfo payment) {
            this.payment = payment;
            return this;
        }

        public YctbAddTaxInfoRequestBuilder withTaxDetail(YctbTaxDetail taxDetail) {
            this.taxDetail = taxDetail;
            return this;
        }

        public YctbAddTaxInfoRequest build() {
            YctbAddTaxInfoRequest yctbAddTaxInfoRequest = new YctbAddTaxInfoRequest();
            yctbAddTaxInfoRequest.setPayMent(payment);
            yctbAddTaxInfoRequest.setTaxDetail(taxDetail);
            return yctbAddTaxInfoRequest;
        }
    }
}
