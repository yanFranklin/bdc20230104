package cn.gtmap.realestate.common.core.domain.exchange.uniformity;

import cn.gtmap.realestate.common.core.domain.exchange.QltQlLqDO;

import javax.xml.bind.annotation.XmlAttribute;

public class QltQlLqBdc extends QltQlLqDO {
    private String dfdjlxmc;
    private String dfqllxmc;
    private String dflzmc;
    private String dfqymc;

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


    @XmlAttribute(
            name = "DFLZMC"
    )
    public String getDflzmc() {
        return dflzmc;
    }

    public void setDflzmc(String dflzmc) {
        this.dflzmc = dflzmc;
    }

    @XmlAttribute(
            name = "DFQYMC"
    )
    public String getDfqymc() {
        return dfqymc;
    }

    public void setDfqymc(String dfqymc) {
        this.dfqymc = dfqymc;
    }
}
