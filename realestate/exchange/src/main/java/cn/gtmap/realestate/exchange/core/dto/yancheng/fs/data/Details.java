package cn.gtmap.realestate.exchange.core.dto.yancheng.fs.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

public class Details implements Serializable {

    private static final long serialVersionUID = -3724942269484081733L;

    @XmlElement(name = "Item")
    private List<Item> item;

    @XmlTransient
    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }


    public static final class DetailsBuilder {
        private List<Item> item;

        private DetailsBuilder() {
        }

        public static DetailsBuilder aDetails() {
            return new DetailsBuilder();
        }

        public DetailsBuilder withItem(List<Item> item) {
            this.item = item;
            return this;
        }

        public Details build() {
            Details details = new Details();
            details.setItem(item);
            return details;
        }
    }
}
