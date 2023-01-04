package cn.gtmap.realestate.exchange.core.dto.yancheng.fs.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType(propOrder = {"payerPartyType", "payerPartyCode", "payerPartyName", "payerAcct", "payerOpBk"})
public class PayerParty implements Serializable {

    private static final long serialVersionUID = 8023231619849648660L;

    private String payerPartyType;

    private String payerPartyCode;

    private String payerPartyName;

    private String payerAcct;

    private String payerOpBk;

    @XmlElement(name = "PayerPartyType",nillable = true)
    public String getPayerPartyType() {
        return payerPartyType;
    }

    public void setPayerPartyType(String payerPartyType) {
        this.payerPartyType = payerPartyType;
    }

    @XmlElement(name = "PayerPartyCode",nillable = true)
    public String getPayerPartyCode() {
        return payerPartyCode;
    }

    public void setPayerPartyCode(String payerPartyCode) {
        this.payerPartyCode = payerPartyCode;
    }

    @XmlElement(name = "PayerPartyName",nillable = true)
    public String getPayerPartyName() {
        return payerPartyName;
    }

    public void setPayerPartyName(String payerPartyName) {
        this.payerPartyName = payerPartyName;
    }

    @XmlElement(name = "PayerAcct",nillable = true)
    public String getPayerAcct() {
        return payerAcct;
    }

    public void setPayerAcct(String payerAcct) {
        this.payerAcct = payerAcct;
    }

    @XmlElement(name = "PayerOpBk",nillable = true)
    public String getPayerOpBk() {
        return payerOpBk;
    }

    public void setPayerOpBk(String payerOpBk) {
        this.payerOpBk = payerOpBk;
    }


    public static final class PayerPartyBuilder {
        private String payerPartyType;
        private String payerPartyCode;
        private String payerPartyName;
        private String payerAcct;
        private String payerOpBk;

        private PayerPartyBuilder() {
        }

        public static PayerPartyBuilder aPayerParty() {
            return new PayerPartyBuilder();
        }

        public PayerPartyBuilder withPayerPartyType(String payerPartyType) {
            this.payerPartyType = payerPartyType;
            return this;
        }

        public PayerPartyBuilder withPayerPartyCode(String payerPartyCode) {
            this.payerPartyCode = payerPartyCode;
            return this;
        }

        public PayerPartyBuilder withPayerPartyName(String payerPartyName) {
            this.payerPartyName = payerPartyName;
            return this;
        }

        public PayerPartyBuilder withPayerAcct(String payerAcct) {
            this.payerAcct = payerAcct;
            return this;
        }

        public PayerPartyBuilder withPayerOpBk(String payerOpBk) {
            this.payerOpBk = payerOpBk;
            return this;
        }

        public PayerParty build() {
            PayerParty payerParty = new PayerParty();
            payerParty.setPayerPartyType(payerPartyType);
            payerParty.setPayerPartyCode(payerPartyCode);
            payerParty.setPayerPartyName(payerPartyName);
            payerParty.setPayerAcct(payerAcct);
            payerParty.setPayerOpBk(payerOpBk);
            return payerParty;
        }
    }
}
