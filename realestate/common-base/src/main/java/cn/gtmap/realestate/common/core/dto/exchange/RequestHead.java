package cn.gtmap.realestate.common.core.dto.exchange;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-26
 * @description
 */
public class RequestHead {

    private String regionCode;

    private String orgid;

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Override
    public String toString() {
        return "RequestHead{" +
                "regionCode='" + regionCode + '\'' +
                ", orgid='" + orgid + '\'' +
                '}';
    }
}
