package cn.gtmap.realestate.exchange.core.dto.fssr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cn.gtmap.realestate.exchange.core.dto.fssr.JfblResponseDataDTO;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 1.0  2022/5/24 9:59
 * @description 缴费办理接口响应
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ROOT")
public class JfblResponseDTO {

    @XmlElement(name = "CODE", required = true)
    private String CODE;

    @XmlElement(name = "MSG", required = true)
    private String MSG;

    @XmlElement(name = "TIMESTAMP")
    private String TIMESTAMP;

    @XmlElement(name = "DATA", required = true)
    private JfblResponseDataDTO DATA;

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public String getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(String TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }

    public JfblResponseDataDTO getDATA() {
        return DATA;
    }

    public void setDATA(JfblResponseDataDTO DATA) {
        this.DATA = DATA;
    }

    @Override
    public String toString() {
        return "JfblResponseDTO{" +
                "CODE='" + CODE + '\'' +
                ", MSG='" + MSG + '\'' +
                ", TIMESTAMP='" + TIMESTAMP + '\'' +
                ", DATA=" + DATA +
                '}';
    }
}
