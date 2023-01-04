package cn.gtmap.realestate.exchange.core.dto.nantong.jsyh;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2018/8/6
 * @description
 */
@XmlRootElement(name = "body")
public class RequestBodyModel {
    private String xzqhdm;//行政区划代码
    private String cqzlx;//产权证类型
    private String cqzbh;//产权证编号
    private String cqrzjlx;//产权证证件类型
    private String cqrzjhm;//产权证证件号码
    private String cqrxm;//产权人姓名
    private String parmName;//备注字段
    private String parmValue;//备注字段

    @XmlElement(name = "XZQHDM")
    public String getXzqhdm() {
        return xzqhdm;
    }

    public void setXzqhdm(String xzqhdm) {
        this.xzqhdm = xzqhdm;
    }

    @XmlElement(name = "CQZLX")
    public String getCqzlx() {
        return cqzlx;
    }

    public void setCqzlx(String cqzlx) {
        this.cqzlx = cqzlx;
    }

    @XmlElement(name = "CQZBH")
    public String getCqzbh() {
        return cqzbh;
    }

    public void setCqzbh(String cqzbh) {
        this.cqzbh = cqzbh;
    }

    @XmlElement(name = "CQRZJLX")
    public String getCqrzjlx() {
        return cqrzjlx;
    }

    public void setCqrzjlx(String cqrzjlx) {
        this.cqrzjlx = cqrzjlx;
    }

    @XmlElement(name = "CQRZJHM")
    public String getCqrzjhm() {
        return cqrzjhm;
    }

    public void setCqrzjhm(String cqrzjhm) {
        this.cqrzjhm = cqrzjhm;
    }

    @XmlElement(name = "CQRXM")
    public String getCqrxm() {
        return cqrxm;
    }

    public void setCqrxm(String cqrxm) {
        this.cqrxm = cqrxm;
    }



    @XmlElement(name = "PARM_NAME")
    public String getParmName() {
        return parmName;
    }

    public void setParmName(String parmName) {
        this.parmName = parmName;
    }

    @XmlElement(name = "PARM_VALUE")
    public String getParmValue() {
        return parmValue;
    }

    public void setParmValue(String parmValue) {
        this.parmValue = parmValue;
    }

    @Override
    public String toString() {
        return "RequestBodyModel{" +
                "xzqhdm='" + xzqhdm + '\'' +
                ", cqzlx='" + cqzlx + '\'' +
                ", cqzbh='" + cqzbh + '\'' +
                ", cqrzjlx='" + cqrzjlx + '\'' +
                ", cqrzjhm='" + cqrzjhm + '\'' +
                ", cqrxm='" + cqrxm + '\'' +
                ", parmName='" + parmName + '\'' +
                ", parmValue='" + parmValue + '\'' +
                '}';
    }
}
