package cn.gtmap.realestate.exchange.core.dto.yancheng.fs.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType(propOrder = {"sealId", "sealName", "sealHash"})
public class InvoicingPartySeal implements Serializable {

    private static final long serialVersionUID = -1059347769056400478L;

    private String sealId;
    private String sealName;
    private String sealHash;

    @XmlElement(name = "SealId",nillable = true)
    public String getSealId() {
        return sealId;
    }

    public void setSealId(String sealId) {
        this.sealId = sealId;
    }

    @XmlElement(name = "SealName",nillable = true)
    public String getSealName() {
        return sealName;
    }

    public void setSealName(String sealName) {
        this.sealName = sealName;
    }

    @XmlElement(name = "SealHash",nillable = true)
    public String getSealHash() {
        return sealHash;
    }

    public void setSealHash(String sealHash) {
        this.sealHash = sealHash;
    }


    public static final class InvoicingPartySealBuilder {
        private String sealId;
        private String sealName;
        private String sealHash;

        private InvoicingPartySealBuilder() {
        }

        public static InvoicingPartySealBuilder anInvoicingPartySeal() {
            return new InvoicingPartySealBuilder();
        }

        public InvoicingPartySealBuilder withSealId(String sealId) {
            this.sealId = sealId;
            return this;
        }

        public InvoicingPartySealBuilder withSealName(String sealName) {
            this.sealName = sealName;
            return this;
        }

        public InvoicingPartySealBuilder withSealHash(String sealHash) {
            this.sealHash = sealHash;
            return this;
        }

        public InvoicingPartySeal build() {
            InvoicingPartySeal invoicingPartySeal = new InvoicingPartySeal();
            invoicingPartySeal.setSealId(sealId);
            invoicingPartySeal.setSealName(sealName);
            invoicingPartySeal.setSealHash(sealHash);
            return invoicingPartySeal;
        }
    }
}
