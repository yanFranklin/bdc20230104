package cn.gtmap.realestate.exchange.core.dto.nantong.jsyh;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2018/8/6
 * @description
 */
@XmlRootElement(name = "head")
public class RequestHeadModel {

    private String lhVersion;//版本号
    private String lhResSysNo;//系统号
    private String lhRequestDate;//请求日期

    @XmlElement(name = "LH_VERSION")
    public String getLhVersion() {
        return lhVersion;
    }

    public void setLhVersion(String lhVersion) {
        this.lhVersion = lhVersion;
    }

    @XmlElement(name = "LH_RES_SYS_NO")
    public String getLhResSysNo() {
        return lhResSysNo;
    }

    public void setLhResSysNo(String lhResSysNo) {
        this.lhResSysNo = lhResSysNo;
    }

    @XmlElement(name = "LH_REQUEST_DATE")
    public String getLhRequestDate() {
        return lhRequestDate;
    }

    public void setLhRequestDate(String lhRequestDate) {
        this.lhRequestDate = lhRequestDate;
    }

    @Override
    public String toString() {
        return "RequestHeadModel{" +
                "lhVersion='" + lhVersion + '\'' +
                ", lhResSysNo='" + lhResSysNo + '\'' +
                ", lhRequestDate='" + lhRequestDate + '\'' +
                '}';
    }
}
