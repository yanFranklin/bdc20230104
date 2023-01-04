package cn.gtmap.realestate.common.core.domain.exchange.uniformity;

import cn.gtmap.realestate.common.core.domain.exchange.QlfQlNydsyqDO;

import javax.xml.bind.annotation.XmlAttribute;

public class QlfQlNydsyqBdc extends QlfQlNydsyqDO {
    private String dfdjlxmc;
    private String dfqllxmc;
    private String dfsyttlxmc;
    private String dfyzyfsmc;


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
            name = "DFSYTTLXMC"
    )
    public String getDfsyttlxmc() {
        return dfsyttlxmc;
    }

    public void setDfsyttlxmc(String dfsyttlxmc) {
        this.dfsyttlxmc = dfsyttlxmc;
    }

    @XmlAttribute(
            name = "DFYZYFSMC"
    )
    public String getDfyzyfsmc() {
        return dfyzyfsmc;
    }

    public void setDfyzyfsmc(String dfyzyfsmc) {
        this.dfyzyfsmc = dfyzyfsmc;
    }
}
