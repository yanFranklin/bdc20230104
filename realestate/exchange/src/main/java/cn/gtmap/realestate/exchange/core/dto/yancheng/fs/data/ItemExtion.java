package cn.gtmap.realestate.exchange.core.dto.yancheng.fs.data;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

public class ItemExtion implements Serializable {

    private static final long serialVersionUID = -2765765572642473952L;

    private String itemDetailName;

    @XmlElement(name = "ItemDetailName")
    public String getItemDetailName() {
        return itemDetailName;
    }

    public void setItemDetailName(String itemDetailName) {
        this.itemDetailName = itemDetailName;
    }
}
