package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfskxxhq.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/9
 * @description 2.12.存量房计税信息获取【A005】Taxbizml
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "TAXBIZML")
public class YrbClfskxxhqTaxbizml {
    private YrbClfskxxhqRequestDTO clfskxxhqlist;

    @XmlElement(name = "CLFSKXXHQLIST")
    public YrbClfskxxhqRequestDTO getClfskxxhqlist() {
        return clfskxxhqlist;
    }

    public void setClfskxxhqlist(YrbClfskxxhqRequestDTO clfskxxhqlist) {
        this.clfskxxhqlist = clfskxxhqlist;
    }


    @Override
    public String toString() {
        return "YrbClfskxxhqTaxbizml{" +
                "clfskxxhqlist=" + clfskxxhqlist +
                '}';
    }
}
