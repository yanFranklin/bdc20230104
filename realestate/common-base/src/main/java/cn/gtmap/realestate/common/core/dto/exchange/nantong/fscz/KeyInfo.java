package cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @Date 2021/11/30
 * @description 签名
 */
@XmlRootElement(name = "Key_Info")
public class KeyInfo {

    /**
     * 标识符	数据项名称	类型	长度	数据项描述	强制/可选
     * Number	证书序号	String	[1,60]		O
     * Issuer	证书证书颁发者名称	GBString	[1,100]		O
     */
    @XmlElement(name = "Number")
    private String Number;
    @XmlElement(name = "Issuer")
    private String Issuer;

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getIssuer() {
        return Issuer;
    }

    public void setIssuer(String issuer) {
        Issuer = issuer;
    }

    @Override
    public String toString() {
        return "KeyInfo{" +
                "Number='" + Number + '\'' +
                ", Issuer='" + Issuer + '\'' +
                '}';
    }
}
