package cn.gtmap.realestate.exchange.core.dto.yancheng.fs.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

public class EInvoiceData implements Serializable {

    private static final long serialVersionUID = -7158907226757217867L;

    @XmlElement(name = "Main")
    private Main main;

    @XmlElement(name = "Details")
    private Details details;

    private String auxDetails;

    @XmlElement(name = "AuxDetails",nillable = true)
    public String getAuxDetails() {
        return auxDetails;
    }

    public void setAuxDetails(String auxDetails) {
        this.auxDetails = auxDetails;
    }

    @XmlTransient
    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @XmlTransient
    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }


    public static final class EInvoiceDataBuilder {
        private Main main;
        private Details details;

        private EInvoiceDataBuilder() {
        }

        public static EInvoiceDataBuilder anEInvoiceData() {
            return new EInvoiceDataBuilder();
        }

        public EInvoiceDataBuilder withMain(Main main) {
            this.main = main;
            return this;
        }

        public EInvoiceDataBuilder withDetails(Details details) {
            this.details = details;
            return this;
        }

        public EInvoiceData build() {
            EInvoiceData eInvoiceData = new EInvoiceData();
            eInvoiceData.setMain(main);
            eInvoiceData.setDetails(details);
            return eInvoiceData;
        }
    }
}
