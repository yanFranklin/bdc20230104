package cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz;

import javax.xml.bind.annotation.*;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @Date 2021/11/30
 * @description 签名
 */
@XmlType(propOrder = {"value", "format", "algorithm", "time", "key_Info"})
@XmlRootElement(name = "Sign_Info")
@XmlAccessorType(XmlAccessType.FIELD)
public class SignInfo {

    /**
     * 标识符	数据项名称	类型	长度	数据项描述	强制/可选
     Value	签名值	String		对电子票据头部、票面信息的签名值（签名原文为电子票据数据 Header和EInvoiceData内容），base64格式。	M
     Format	签名格式类型	String		签名格式类型，
     目前固定 DETACH	M
     Algorithm	摘要算法	String		摘要算法，默认SM2	M
     Time	签名时间	UTCDateTime			M
     Key_Info	证书信息			详见Key_Info节点描述	O

     报文体内部XML节点TAG：Voucher->Sign_Info->Key_Info
     标识符	数据项名称	类型	长度	数据项描述	强制/可选
     Number	证书序号	String	[1,60]		O
     Issuer	证书证书颁发者名称	GBString	[1,100]		O

     */
    @XmlElement(name = "Value")
    private String value;
    @XmlElement(name = "Format")
    private String format;
    @XmlElement(name = "Algorithm")
    private String algorithm;
    @XmlElement(name = "Time")
    private String time;
    @XmlElement(name = "Key_Info")
    private KeyInfo key_Info;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public KeyInfo getKey_Info() {
        return key_Info;
    }

    public void setKey_Info(KeyInfo key_Info) {
        this.key_Info = key_Info;
    }

    @Override
    public String toString() {
        return "SignInfo{" +
                "value='" + value + '\'' +
                ", format='" + format + '\'' +
                ", algorithm='" + algorithm + '\'' +
                ", time='" + time + '\'' +
                ", key_Info=" + key_Info +
                '}';
    }
}
