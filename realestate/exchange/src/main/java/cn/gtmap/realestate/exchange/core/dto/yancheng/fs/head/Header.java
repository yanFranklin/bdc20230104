package cn.gtmap.realestate.exchange.core.dto.yancheng.fs.head;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType(propOrder = {"eInvoiceTag", "eInvoiceID", "version"})
public class Header implements Serializable {

    private static final long serialVersionUID = -2663272143541451964L;

    private String eInvoiceTag;

    private String eInvoiceID;

    private String version;

    @XmlElement(name = "EInvoiceTag",nillable = true)
    public String geteInvoiceTag() {
        return eInvoiceTag;
    }

    public void seteInvoiceTag(String eInvoiceTag) {
        this.eInvoiceTag = eInvoiceTag;
    }

    @XmlElement(name = "EInvoiceID",nillable = true)
    public String geteInvoiceID() {
        return eInvoiceID;
    }

    public void seteInvoiceID(String eInvoiceID) {
        this.eInvoiceID = eInvoiceID;
    }

    public String getVersion() {
        return version;
    }

    @XmlElement(name = "Version",nillable = true)
    public void setVersion(String version) {
        this.version = version;
    }


    public static final class HeaderBuilder {
        private String eInvoiceTag;
        private String eInvoiceID;
        private String version;

        private HeaderBuilder() {
        }

        public static HeaderBuilder aHeader() {
            return new HeaderBuilder();
        }

        public HeaderBuilder withEInvoiceTag(String eInvoiceTag) {
            this.eInvoiceTag = eInvoiceTag;
            return this;
        }

        public HeaderBuilder withEInvoiceID(String eInvoiceID) {
            this.eInvoiceID = eInvoiceID;
            return this;
        }

        public HeaderBuilder withVersion(String version) {
            this.version = version;
            return this;
        }

        public Header build() {
            Header header = new Header();
            header.setVersion(version);
            header.eInvoiceTag = this.eInvoiceTag;
            header.eInvoiceID = this.eInvoiceID;
            return header;
        }
    }
}
