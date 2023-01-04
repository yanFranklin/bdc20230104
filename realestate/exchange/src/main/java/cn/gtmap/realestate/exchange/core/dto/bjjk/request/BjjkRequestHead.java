package cn.gtmap.realestate.exchange.core.dto.bjjk.request;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-28
 * @description
 */
public class BjjkRequestHead {

    // 用户ID
    private String id;

    // 加密令牌
    private String token;

    // 登记部门名称
    private String deptName;

    // 登记人员名称
    private String userName;

    // 查询接口标志
    private String requestType;

    // 查询请求单号 8位日期+6位行政区代码+6位流水号  20位数
    private String cxqqdh;

    // 业务号
    private String businessNumber;
    // 行政区划
    private String xzqh;

    public String getXzqh() {
        return xzqh;
    }

    public void setXzqh(String xzqh) {
        this.xzqh = xzqh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getCxqqdh() {
        return cxqqdh;
    }

    public void setCxqqdh(String cxqqdh) {
        this.cxqqdh = cxqqdh;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }
}
