package cn.gtmap.realestate.common.core.domain.exchange.uniformity;

import cn.gtmap.realestate.common.core.domain.exchange.QltFwFdcqDzDO;

import javax.xml.bind.annotation.XmlAttribute;

public class QltFwFdcqDzBdc extends QltFwFdcqDzDO {
    private String dfdjlxmc;
    private String dfqllxmc;


    @XmlAttribute(
            name = "DFDJLXMC"
    )
    public String getDfdjlxmc() {
        return dfdjlxmc;
    }

    public void setDfdjlxmc(String dfdjlxmc) {
        this.dfdjlxmc = dfdjlxmc;
    }


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
