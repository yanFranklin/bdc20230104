package cn.gtmap.realestate.common.core.dto.exchange.court;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * 法院提供的司法控制请求-头
 *
 * @author wangyinghao
 */
@XmlRootElement(name = "RESULTLIST")
@XStreamAlias("RESULTLIST")
public class CourtKzjgDTO {
    @XStreamAlias("errMsg")
    String errMsg;

    @XmlElementWrapper(name = "KZJGLIST")
    @XmlElement(name = "JG")
    @XStreamAlias("KZJGLIST")
    List<kzjgItem> kzjgItems;

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }


    public void setKzjgItems(List<kzjgItem> kzjgItems) {
        this.kzjgItems = kzjgItems;
    }

    @XStreamAlias("JG")
    public static class kzjgItem {
        @XStreamAlias("CXQQDH")
        String CXQQDH;
        @XStreamAlias("RESULT")
        String RESULT;
        @XStreamAlias("MSG")
        String MSG;

        public void setCXQQDH(String CXQQDH) {
            this.CXQQDH = CXQQDH;
        }

        public void setRESULT(String RESULT) {
            this.RESULT = RESULT;
        }

        public void setMSG(String MSG) {
            this.MSG = MSG;
        }
    }


}
