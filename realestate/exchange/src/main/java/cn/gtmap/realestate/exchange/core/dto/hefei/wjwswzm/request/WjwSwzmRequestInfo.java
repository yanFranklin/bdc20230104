package cn.gtmap.realestate.exchange.core.dto.hefei.wjwswzm.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-10
 * @description 卫计委 出生证明请求接口
 */
@XStreamAlias("requestinfo")
public class WjwSwzmRequestInfo {

    // 请求时间
    @XStreamAlias("requestDate")
    private String requestDate;

    // 请求机构组织机构代码
    @XStreamAlias("requestOrgCode")
    private String requestOrgCode;

    // 请求机构组织机构名称
    @XStreamAlias("requestOrgName")
    private String requestOrgName;

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestOrgCode() {
        return requestOrgCode;
    }

    public void setRequestOrgCode(String requestOrgCode) {
        this.requestOrgCode = requestOrgCode;
    }

    public String getRequestOrgName() {
        return requestOrgName;
    }

    public void setRequestOrgName(String requestOrgName) {
        this.requestOrgName = requestOrgName;
    }
}
