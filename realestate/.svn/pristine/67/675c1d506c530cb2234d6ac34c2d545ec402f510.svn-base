package cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @Date 2021/11/30
 * @description 通知信息
 */
@XmlRootElement(name = "Recipient_Addr")
public class RecipientAddr {

    /**
     * 标识符	数据项名称	类型	长度	数据项描述	强制/可选
     Email	通知邮件地址	String	[1,50]		O
     Telephone	通知人电话	GBString	[1,50]		O


     */
    @XmlElement(name = "Email")
    private String Email;
    @XmlElement(name = "Telephone")
    private String Telephone;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    @Override
    public String toString() {
        return "RecipientAddr{" +
                "Email='" + Email + '\'' +
                ", Telephone='" + Telephone + '\'' +
                '}';
    }
}
