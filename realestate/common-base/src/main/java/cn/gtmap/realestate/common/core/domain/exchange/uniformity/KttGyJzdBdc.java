package cn.gtmap.realestate.common.core.domain.exchange.uniformity;

import cn.gtmap.realestate.common.core.domain.exchange.KttGyJzdDO;

import javax.xml.bind.annotation.XmlAttribute;

public class KttGyJzdBdc extends KttGyJzdDO {
    private String dfjblxmc;
    private String dfjzdlxmc;

    @XmlAttribute(
            name = "DFJBLXMC"
    )
    public String getDfjblxmc() {
        return dfjblxmc;
    }

    public void setDfjblxmc(String dfjblxmc) {
        this.dfjblxmc = dfjblxmc;
    }

    @XmlAttribute(
            name = "DFJZDLXMC"
    )
    public String getDfjzdlxmc() {
        return dfjzdlxmc;
    }

    public void setDfjzdlxmc(String dfjzdlxmc) {
        this.dfjzdlxmc = dfjzdlxmc;
    }
}
