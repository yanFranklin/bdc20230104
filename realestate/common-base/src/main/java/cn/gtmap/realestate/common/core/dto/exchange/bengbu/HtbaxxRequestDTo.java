package cn.gtmap.realestate.common.core.dto.exchange.bengbu;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 2020/05/12,1.0
 * @description
 */
@XmlRootElement(name = "root")
public class HtbaxxRequestDTo {

    private HtbaxxParamRequestDTo param;

    public HtbaxxParamRequestDTo getParam() {
        return param;
    }

    public void setParam(HtbaxxParamRequestDTo param) {
        this.param = param;
    }
}
