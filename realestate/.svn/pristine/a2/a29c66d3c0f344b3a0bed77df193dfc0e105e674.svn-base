package cn.gtmap.realestate.common.core.domain.exchange.uniformity;

import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.HeadModel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by zdd on 2015/11/19.
 */
@XmlType(name = "messageModel", propOrder = {"headModel", "dataModel"})
@XmlRootElement(name = "Message")
public class MessageModelBdc {

    private HeadModel headModel;

    private DataModelBdc dataModel;

    @XmlElement(name = "Head")
    public HeadModel getHeadModel() {
        return headModel;
    }

    public void setHeadModel(HeadModel headModel) {
        this.headModel = headModel;
    }

    @XmlElement(name = "Data")
    public DataModelBdc getDataModel() {
        return dataModel;
    }

    public void setDataModel(DataModelBdc dataModel) {
        this.dataModel = dataModel;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "headModel=" + headModel +
                ", dataModel=" + dataModel +
                '}';
    }
}
