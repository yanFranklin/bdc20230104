package cn.gtmap.realestate.exchange.core.dto.fssr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 1.0  2022/5/24 9:59
 * @description 缴费办理
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ROOT")
public class JfblDTO {

    // 缴款类型 0：一般费用  1：税费统缴
    @JSONField(name = "jklx")
    private Integer jklx;

    // 处理标志。空白
    @JSONField(name = "dwdm")
    private String dwdm;

    // 处理标志。空白
    @JSONField(name = "md5String")
    private String md5String;

    // 处理标志。空白
    @JSONField(name = "FLAG")
    private String FLAG;

    // 处理信息。空白
    @JSONField(name = "MESSAGE")
    private String MESSAGE;

    // 数据节点：对应一条一般缴款书信息
    @JSONField(name = "DATA")
    private JfblDataDTO DATA;

    public Integer getJklx() {
        return jklx;
    }

    public void setJklx(Integer jklx) {
        this.jklx = jklx;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public JfblDataDTO getDATA() {
        return DATA;
    }

    public void setDATA(JfblDataDTO DATA) {
        this.DATA = DATA;
    }

    public String getDwdm() {
        return dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
    }

    public String getMd5String() {
        return md5String;
    }

    public void setMd5String(String md5String) {
        this.md5String = md5String;
    }

    @Override
    public String toString() {
        return "JfblDTO{" +
                "jklx='" + jklx + '\'' +
                ", dwdm='" + dwdm + '\'' +
                ", md5String='" + md5String + '\'' +
                ", FLAG='" + FLAG + '\'' +
                ", MESSAGE='" + MESSAGE + '\'' +
                ", DATA=" + DATA +
                '}';
    }
}
