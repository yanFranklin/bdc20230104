package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.ewmjkxx.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/11
 * @description 1.13.    房产交易缴款结果查询【A019】
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "TAXBIZML")
public class YrbEwmjkxxTaxbizml {

    private List<YrbEwmjkxxRequest> fcjyzfquerylist;

    @XmlElement(name = "FCJYZFQUERYLIST")
    public List<YrbEwmjkxxRequest> getFcjyzfquerylist() {
        return fcjyzfquerylist;
    }

    public void setFcjyzfquerylist(List<YrbEwmjkxxRequest> fcjyzfquerylist) {
        this.fcjyzfquerylist = fcjyzfquerylist;
    }

    @Override
    public String toString() {
        return "YrbEwmjkxxDTO{" +
                "fcjyzfquerylist=" + fcjyzfquerylist +
                '}';
    }
}
