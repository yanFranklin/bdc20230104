package cn.gtmap.realestate.common.core.domain.exchange.uniformity;

import cn.gtmap.realestate.common.core.domain.exchange.QlfFwFdcqQfsyqDO;

import javax.xml.bind.annotation.XmlAttribute;

public class QlfFwFdcqQfsyqBdc extends QlfFwFdcqQfsyqDO {
    private String dfqllxmc;


    @XmlAttribute(
            name = "DFQLLXMC"
    )
    public String getDfqllxmc() {
        return dfqllxmc;
    }

    public void setDfqllxmc(String dfqllxmc) {
        this.dfqllxmc = dfqllxmc;
    }

}
