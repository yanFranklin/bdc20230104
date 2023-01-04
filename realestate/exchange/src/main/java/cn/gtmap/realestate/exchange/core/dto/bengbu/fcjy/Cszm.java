package cn.gtmap.realestate.exchange.core.dto.bengbu.fcjy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ROOT")
public class Cszm {


    /**
     * cszh : T340072765
     * xsemqxm : 蒲文艳
     * xsefqxm : 刘小文
     */

    private String CSZH;
    private String XSEMQXM;
    private String XSEFQXM;

    public String getCSZH() {
        return CSZH;
    }

    public void setCSZH(String CSZH) {
        this.CSZH = CSZH;
    }

    public String getXSEMQXM() {
        return XSEMQXM;
    }

    public void setXSEMQXM(String XSEMQXM) {
        this.XSEMQXM = XSEMQXM;
    }

    public String getXSEFQXM() {
        return XSEFQXM;
    }

    public void setXSEFQXM(String XSEFQXM) {
        this.XSEFQXM = XSEFQXM;
    }
}
