package cn.gtmap.realestate.common.core.domain.exchange.uniformity;

import cn.gtmap.realestate.common.core.domain.exchange.KttGyJzxDO;

import javax.xml.bind.annotation.XmlAttribute;

public class KttGyJzxBdc extends KttGyJzxDO {
    private String dfjzxlbmc;
    private String dfjzxwzmc;
    private String dfjxxzmc;


    @XmlAttribute(
            name = "DFJZXLBMC"
    )
    public String getDfjzxlbmc() {
        return dfjzxlbmc;
    }

    public void setDfjzxlbmc(String dfjzxlbmc) {
        this.dfjzxlbmc = dfjzxlbmc;
    }


    @XmlAttribute(
            name = "DFJZXWZMC"
    )
    public String getDfjzxwzmc() {
        return dfjzxwzmc;
    }

    public void setDfjzxwzmc(String dfjzxwzmc) {
        this.dfjzxwzmc = dfjzxwzmc;
    }


    @XmlAttribute(
            name = "DFJXXZMC"
    )
    public String getDfjxxzmc() {
        return dfjxxzmc;
    }

    public void setDfjxxzmc(String dfjxxzmc) {
        this.dfjxxzmc = dfjxxzmc;
    }
}
