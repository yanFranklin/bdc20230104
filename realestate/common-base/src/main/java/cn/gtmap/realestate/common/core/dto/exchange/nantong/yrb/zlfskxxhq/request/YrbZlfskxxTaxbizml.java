package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfskxxhq.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/9
 * @description 1.4.    增量房计税信息获取【A004】Taxbizml
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "TAXBIZML")
public class YrbZlfskxxTaxbizml {
    private YrbZlfskxxRequestDTO zlfskxxhqlist;

    @XmlElement(name = "ZLFSKXXHQLIST")
    public YrbZlfskxxRequestDTO getZlfskxxhqlist() {
        return zlfskxxhqlist;
    }

    public void setZlfskxxhqlist(YrbZlfskxxRequestDTO zlfskxxhqlist) {
        this.zlfskxxhqlist = zlfskxxhqlist;
    }

    @Override
    public String toString() {
        return "YrbZlfskxxTaxbizml{" +
                "zlfskxxhqlist=" + zlfskxxhqlist +
                '}';
    }
}
