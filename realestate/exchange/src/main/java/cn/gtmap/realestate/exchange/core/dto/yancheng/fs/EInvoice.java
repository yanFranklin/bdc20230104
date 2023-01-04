package cn.gtmap.realestate.exchange.core.dto.yancheng.fs;

import cn.gtmap.realestate.exchange.core.dto.yancheng.fs.data.EInvoiceData;
import cn.gtmap.realestate.exchange.core.dto.yancheng.fs.head.Header;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@XmlRootElement(name = "EInvoice")
public class EInvoice implements Serializable {

    private static final long serialVersionUID = -3120824271914634925L;

    @XmlElement(name = "Header",nillable = true)
    private Header header;

    @XmlElement(name = "EInvoiceData",nillable = true)
    private EInvoiceData eInvoiceData;

//    @XmlElement(name = "eInvoiceSignature")
//    private EInvoiceSignature eInvoiceSignature;

    @XmlTransient
    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @XmlTransient
    public EInvoiceData geteInvoiceData() {
        return eInvoiceData;
    }

    public void seteInvoiceData(EInvoiceData eInvoiceData) {
        this.eInvoiceData = eInvoiceData;
    }

    public static final class EInvoiceBuilder {
        private Header header;
        private EInvoiceData eInvoiceData;

        private EInvoiceBuilder() {
        }

        public static EInvoiceBuilder anEInvoice() {
            return new EInvoiceBuilder();
        }

        public EInvoiceBuilder withHeader(Header header) {
            this.header = header;
            return this;
        }

        public EInvoiceBuilder withEInvoiceData(EInvoiceData eInvoiceData) {
            this.eInvoiceData = eInvoiceData;
            return this;
        }

        public EInvoice build() {
            EInvoice eInvoice = new EInvoice();
            eInvoice.setHeader(header);
            eInvoice.eInvoiceData = this.eInvoiceData;
            return eInvoice;
        }
    }

//    public EInvoiceSignature geteInvoiceSignature() {
//        return eInvoiceSignature;
//    }
//
//    public void seteInvoiceSignature(EInvoiceSignature eInvoiceSignature) {
//        this.eInvoiceSignature = eInvoiceSignature;
//    }


}
