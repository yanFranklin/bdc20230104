package cn.gtmap.realestate.common.core.domain.exchange.uniformity;

import cn.gtmap.realestate.common.core.domain.exchange.QlfQlYgdjDO;

import javax.xml.bind.annotation.XmlAttribute;

public class QlfQlYgdjBdc extends QlfQlYgdjDO {

    private String dfygdjzlmc;
    private String dfdjlxmc;
    private String dffwjgmc;


    @XmlAttribute(
            name = "DFYGDJZLMC"
    )
    public String getDfygdjzlmc() {
        return dfygdjzlmc;
    }

    public void setDfygdjzlmc(String dfygdjzlmc) {
        this.dfygdjzlmc = dfygdjzlmc;
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

    @XmlAttribute(
            name = "DFFWJGMC"
    )

    public String getDffwjgmc() {
        return dffwjgmc;
    }

    public void setDffwjgmc(String dffwjgmc) {
        this.dffwjgmc = dffwjgmc;
    }
}
