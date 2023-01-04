package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfrwjs.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/12
 * @description 增值税发票信息
 */

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "ZLFFPXX")
public class YrbZlfFpxxRequest {


    /**
     * fpdm : 发票代码
     * fphm : 发票号码
     */

    private String fpdm;
    private String fphm;

    @XmlElement(name = "FPDM")
    public String getFpdm() {
        return fpdm;
    }

    public void setFpdm(String fpdm) {
        this.fpdm = fpdm;
    }

    @XmlElement(name = "FPHM")
    public String getFphm() {
        return fphm;
    }

    public void setFphm(String fphm) {
        this.fphm = fphm;
    }
}
