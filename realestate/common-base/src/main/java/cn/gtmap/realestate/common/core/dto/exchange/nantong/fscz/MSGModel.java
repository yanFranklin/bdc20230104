package cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MSG")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class MSGModel {


    private MOFModel mof;

    @XmlElement(name = "MOF")
    public MOFModel getMof() {
        return mof;
    }

    public void setMof(MOFModel mof) {
        this.mof = mof;
    }
}
