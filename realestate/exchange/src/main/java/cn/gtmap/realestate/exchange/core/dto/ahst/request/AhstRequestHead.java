package cn.gtmap.realestate.exchange.core.dto.ahst.request;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2020/11/20 18:55
 * @description 安徽省厅请求头
 */
public class AhstRequestHead {
    // 部门名称
    private String deptName;

    // 用户名
    private String userName;

    // 接口调用类型
    private String requestType;

    // 流水号
    private String cxqqdh;

    // 行政区划代码
    private String xzqh;

    // 加密后业务参数
    private String param;

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

    public String getXzqh() {
        return xzqh;
    }

    public void setXzqh(String xzqh) {
        this.xzqh = xzqh;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
