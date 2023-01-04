package cn.gtmap.realestate.common.core.dto.exchange.court.kz;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

/**
 * 法院提供的司法控制请求-头
 *
 * @author wangyinghao
 */
public class CourtKzHeader {
    @XmlElement(name = "CREATETIME")
    String CREATETIME;

    @XmlElement(name = "DIGITALSIGN")
    String DIGITALSIGN;

    @XmlElement(name = "RESPONSECODE")
    String RESPONSECODE;

    @XmlElement(name = "RESPONSEINFO")
    String RESPONSEINFO;

    public void setCREATETIME(String CREATETIME) {
        this.CREATETIME = CREATETIME;
    }

    public void setDIGITALSIGN(String DIGITALSIGN) {
        this.DIGITALSIGN = DIGITALSIGN;
    }

    public void setRESPONSECODE(String RESPONSECODE) {
        this.RESPONSECODE = RESPONSECODE;
    }

    public void setRESPONSEINFO(String RESPONSEINFO) {
        this.RESPONSEINFO = RESPONSEINFO;
    }
}
