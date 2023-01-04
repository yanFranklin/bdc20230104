package cn.gtmap.realestate.exchange.core.dto.bengbu.gspapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ROOT")
public class SwxxQO {

    private String XM;
    private String CXRZJHM;


    public String getXM() {
        return XM;
    }

    public void setXM(String XM) {
        this.XM = XM;
    }

    public String getCXRZJHM() {
        return CXRZJHM;
    }

    public void setCXRZJHM(String CXRZJHM) {
        this.CXRZJHM = CXRZJHM;
    }
}
