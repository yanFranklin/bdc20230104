package cn.gtmap.realestate.exchange.core.bo.xsd;


import java.io.Serializable;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-24
 * @description 记录 最外层请求的日志 实体
 */
public class LogBO extends TagWithRefereceBO implements Serializable {

    private static final long serialVersionUID = 4232506824585503202L;

    // 业务系统日志 功能名称 对应 AuditEventBO.viewTypeName
    private String logEventName;

    // 业务日志 维度 标志 请求方
    private String requester;

    // 业务日志 响应方
    private String responser;

    // 第三方标志业务维度
    private String dsfFlag;

    // 提供给外围系统的地址
    private String bdcdz;

    // 日志服务
    private String logService;

    // 接口响应时间
    private long useTime;

    // 接口bean ID
    private String beanId;

    // 从请求参数中 自定义 记录日志
    private String reqCustom;

    // 从响应结构中 自定义 记录日志
    private String respCustom;

    public String getLogEventName() {
        return logEventName;
    }

    public void setLogEventName(String logEventName) {
        this.logEventName = logEventName;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getResponser() {
        return responser;
    }

    public void setResponser(String responser) {
        this.responser = responser;
    }

    public String getDsfFlag() {
        return dsfFlag;
    }

    public void setDsfFlag(String dsfFlag) {
        this.dsfFlag = dsfFlag;
    }

    public String getBdcdz() {
        return bdcdz;
    }

    public void setBdcdz(String bdcdz) {
        this.bdcdz = bdcdz;
    }

    public String getLogService() {
        return logService;
    }

    public void setLogService(String logService) {
        this.logService = logService;
    }

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public String getReqCustom() {
        return reqCustom;
    }

    public void setReqCustom(String reqCustom) {
        this.reqCustom = reqCustom;
    }

    public String getRespCustom() {
        return respCustom;
    }

    public void setRespCustom(String respCustom) {
        this.respCustom = respCustom;
    }
}
