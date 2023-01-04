package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfrwjs.request;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/12
 * @description 其他
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "QT")
public class YrbZlfQtRequest {


    private String sffgyrdp;

    @XmlElement(name = "SFFGYRDP")
    public String getSffgyrdp() {
        return sffgyrdp;
    }

    public void setSffgyrdp(String sffgyrdp) {
        this.sffgyrdp = sffgyrdp;
    }


    @Override
    public String toString() {
        return "YrbZlfQtRequest{" +
                "sffgyrdp='" + sffgyrdp + '\'' +
                '}';
    }
}
