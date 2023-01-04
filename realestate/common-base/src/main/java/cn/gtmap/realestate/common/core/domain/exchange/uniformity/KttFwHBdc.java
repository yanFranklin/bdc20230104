package cn.gtmap.realestate.common.core.domain.exchange.uniformity;

import cn.gtmap.realestate.common.core.domain.exchange.KttFwHDO;

import javax.xml.bind.annotation.XmlAttribute;

public class KttFwHBdc extends KttFwHDO {

    private String dfhxjgmc;
    private String dffwyt1mc;
    private String dffwyt2mc;
    private String dffwyt3mc;


    @XmlAttribute(
            name = "DFHXJGMC"
    )
    public String getDfhxjgmc() {
        return dfhxjgmc;
    }

    public void setDfhxjgmc(String dfhxjgmc) {
        this.dfhxjgmc = dfhxjgmc;
    }

    @XmlAttribute(
            name = "DFFWYT1MC"
    )
    public String getDffwyt1mc() {
        return dffwyt1mc;
    }

    public void setDffwyt1mc(String dffwyt1mc) {
        this.dffwyt1mc = dffwyt1mc;
    }

    @XmlAttribute(
            name = "DFFWYT2MC"
    )
    public String getDffwyt2mc() {
        return dffwyt2mc;
    }

    public void setDffwyt2mc(String dffwyt2mc) {
        this.dffwyt2mc = dffwyt2mc;
    }

    @XmlAttribute(
            name = "DFFWYT3MC"
    )
    public String getDffwyt3mc() {
        return dffwyt3mc;
    }

    public void setDffwyt3mc(String dffwyt3mc) {
        this.dffwyt3mc = dffwyt3mc;
    }
}
