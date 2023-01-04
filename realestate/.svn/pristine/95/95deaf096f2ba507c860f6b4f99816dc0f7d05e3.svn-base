package cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz;

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
public class CFGModel {

    private HeadModel headModel;

    private DataModel dataModel;

    @XmlElement(name = "Head")
    public HeadModel getHeadModel() {
        return headModel;
    }

    public void setHeadModel(HeadModel headModel) {
        this.headModel = headModel;
    }

    @XmlElement(name = "Data")
    public DataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(DataModel dataModel) {
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
