package cn.gtmap.realestate.exchange.core.dto.hefei.wjwcszm.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-10
 * @description
 */
@XStreamAlias("data")
public class WjwCszmRequestData {

    // 出生证明编号
    @XStreamAlias("BirthCode")
    private String BirthCode;

    // 母亲姓名
    @XStreamAlias("MomName")
    private String MomName;

    // 母亲证件编码
    @XStreamAlias("MomIdCode")
    private String MomIdCode;

    public String getBirthCode() {
        return BirthCode;
    }

    public void setBirthCode(String birthCode) {
        BirthCode = birthCode;
    }

    public String getMomName() {
        return MomName;
    }

    public void setMomName(String momName) {
        MomName = momName;
    }

    public String getMomIdCode() {
        return MomIdCode;
    }

    public void setMomIdCode(String momIdCode) {
        MomIdCode = momIdCode;
    }
}
