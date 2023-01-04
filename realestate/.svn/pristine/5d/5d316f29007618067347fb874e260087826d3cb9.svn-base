package cn.gtmap.realestate.exchange.core.dto.yancheng.fs.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType(propOrder = {"itemCode", "itemName", "itemQuantity", "itemUnit", "itemStd", "itemAmount", "itemRemark", "itemExt"})
public class Item implements Serializable {

    private static final long serialVersionUID = 5827924212972766925L;

    private String itemCode;
    private String itemName;
    private Double itemAmount;
    private String itemUnit;
    private Integer itemQuantity;
    private Double itemStd;
    private ItemExtion itemExt;
    private String itemRemark;

    @XmlElement(name = "ItemRemark",nillable = true)
    public String getItemRemark() {
        return itemRemark;
    }

    public void setItemRemark(String itemRemark) {
        this.itemRemark = itemRemark;
    }

    @XmlElement(name = "ItemCode")
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @XmlElement(name = "ItemName")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @XmlElement(name = "ItemAmount")
    public Double getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(Double itemAmount) {
        this.itemAmount = itemAmount;
    }

    @XmlElement(name = "ItemUnit")
    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    @XmlElement(name = "ItemQuantity")
    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @XmlElement(name = "ItemStd")
    public Double getItemStd() {
        return itemStd;
    }

    public void setItemStd(Double itemStd) {
        this.itemStd = itemStd;
    }

    @XmlElement(name = "ItemExt", nillable = true)
    public ItemExtion getItemExt() {
        return itemExt;
    }

    public void setItemExt(ItemExtion itemExtion) {
        this.itemExt = itemExtion;
    }

    public static final class ItemBuilder {
        private String itemCode;
        private String itemName;
        private Double itemAmount;
        private String itemUnit;
        private Integer itemQuantity;
        private Double itemStd;

        private ItemBuilder() {
        }

        public static ItemBuilder anItem() {
            return new ItemBuilder();
        }

        public ItemBuilder withItemCode(String itemCode) {
            this.itemCode = itemCode;
            return this;
        }

        public ItemBuilder withItemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public ItemBuilder withItemAmount(Double itemAmount) {
            this.itemAmount = itemAmount;
            return this;
        }

        public ItemBuilder withItemUnit(String itemUnit) {
            this.itemUnit = itemUnit;
            return this;
        }

        public ItemBuilder withItemQuantity(Integer itemQuantity) {
            this.itemQuantity = itemQuantity;
            return this;
        }

        public ItemBuilder withItemStd(Double itemStd) {
            this.itemStd = itemStd;
            return this;
        }


        public Item build() {
            Item item = new Item();
            item.setItemCode(itemCode);
            item.setItemName(itemName);
            item.setItemAmount(itemAmount);
            item.setItemUnit(itemUnit);
            item.setItemQuantity(itemQuantity);
            item.setItemStd(itemStd);
            return item;
        }
    }
}
