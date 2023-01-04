package cn.gtmap.realestate.common.core.domain.exchange.uniformity;

import cn.gtmap.realestate.common.core.domain.exchange.DjtDjSlsqDO;

import javax.xml.bind.annotation.XmlAttribute;

public class DjtDjSlsqBdc extends DjtDjSlsqDO {
    private String dfdjlxdm;
    private String dfdjlxmc;

    @XmlAttribute(
            name = "DFDJLXDM"
    )
    public String getDfdjlxdm() {
        return dfdjlxdm;
    }

    public void setDfdjlxdm(String dfdjlxdm) {
        this.dfdjlxdm = dfdjlxdm;
    }

    @XmlAttribute(
            name = "DFDJLXMC"
    )
    public String getDfdjlxmc() {
        return dfdjlxmc;
    }

    public void setDfdjlxmc(String dfdjlxmc) {
        this.dfdjlxmc = dfdjlxmc;
    }


}
