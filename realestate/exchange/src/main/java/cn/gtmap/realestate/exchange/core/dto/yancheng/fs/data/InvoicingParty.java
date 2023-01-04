package cn.gtmap.realestate.exchange.core.dto.yancheng.fs.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType(propOrder = {"invoicingPartyCode", "invoicingPartyName", "recName", "recAcct", "recOpBk"})
public class InvoicingParty implements Serializable {

    private static final long serialVersionUID = -2433056759469142767L;

    private String invoicingPartyCode;
    private String invoicingPartyName;
    private String recName;
    private String recAcct;
    private String recOpBk;

    @XmlElement(name = "InvoicingPartyCode",nillable = true)
    public String getInvoicingPartyCode() {
        return invoicingPartyCode;
    }

    public void setInvoicingPartyCode(String invoicingPartyCode) {
        this.invoicingPartyCode = invoicingPartyCode;
    }

    @XmlElement(name = "InvoicingPartyName",nillable = true)
    public String getInvoicingPartyName() {
        return invoicingPartyName;
    }

    public void setInvoicingPartyName(String invoicingPartyName) {
        this.invoicingPartyName = invoicingPartyName;
    }

    @XmlElement(name = "RecName",nillable = true)
    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    @XmlElement(name = "RecAcct",nillable = true)
    public String getRecAcct() {
        return recAcct;
    }

    public void setRecAcct(String recAcct) {
        this.recAcct = recAcct;
    }

    @XmlElement(name = "RecOpBk",nillable = true)
    public String getRecOpBk() {
        return recOpBk;
    }

    public void setRecOpBk(String recOpBk) {
        this.recOpBk = recOpBk;
    }


    public static final class InvoicingPartyBuilder {
        private String invoicingPartyCode;
        private String invoicingPartyName;
        private String recName;
        private String recAcct;
        private String recOpBk;

        private InvoicingPartyBuilder() {
        }

        public static InvoicingPartyBuilder anInvoicingParty() {
            return new InvoicingPartyBuilder();
        }

        public InvoicingPartyBuilder withInvoicingPartyCode(String invoicingPartyCode) {
            this.invoicingPartyCode = invoicingPartyCode;
            return this;
        }

        public InvoicingPartyBuilder withInvoicingPartyName(String invoicingPartyName) {
            this.invoicingPartyName = invoicingPartyName;
            return this;
        }

        public InvoicingPartyBuilder withRecName(String recName) {
            this.recName = recName;
            return this;
        }

        public InvoicingPartyBuilder withRecAcct(String recAcct) {
            this.recAcct = recAcct;
            return this;
        }

        public InvoicingPartyBuilder withRecOpBk(String recOpBk) {
            this.recOpBk = recOpBk;
            return this;
        }

        public InvoicingParty build() {
            InvoicingParty invoicingParty = new InvoicingParty();
            invoicingParty.setInvoicingPartyCode(invoicingPartyCode);
            invoicingParty.setInvoicingPartyName(invoicingPartyName);
            invoicingParty.setRecName(recName);
            invoicingParty.setRecAcct(recAcct);
            invoicingParty.setRecOpBk(recOpBk);
            return invoicingParty;
        }
    }
}
