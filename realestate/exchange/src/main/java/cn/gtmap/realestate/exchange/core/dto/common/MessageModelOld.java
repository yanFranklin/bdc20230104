package cn.gtmap.realestate.exchange.core.dto.common;

import cn.gtmap.realestate.common.core.domain.exchange.HeadModel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by zdd on 2015/11/19.
 */
@XmlType(name = "messageModel", propOrder = {"headModel", "dataModel"})
@XmlRootElement(name = "Message")
public class MessageModelOld {

    private HeadModel headModel;

    private DataModelOld dataModel;

    @XmlElement(name = "Head")
    public HeadModel getHeadModel() {
        return headModel;
    }

    public void setHeadModel(HeadModel headModel) {
        this.headModel = headModel;
    }

    @XmlElement(name = "Data")
    public DataModelOld getDataModel() {
        return dataModel;
    }

    public void setDataModel(DataModelOld dataModel) {
        this.dataModel = dataModel;
    }


    @Override
    public String toString() {
        return "MessageModelOld{" +
                "headModel=" + headModel +
                ", dataModel=" + dataModel +
                '}';
    }
}
