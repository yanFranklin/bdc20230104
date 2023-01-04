package cn.gtmap.realestate.common.core.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 不动产 短信 请求日志
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0
 * @description
 */
@Table(name = "bdc_msg_log")
public class BdcMsgLog {

    // bdcSmsRequest 请求序列表 唯一
    @Id
    private String logid;

    // 参数
    private String param;

    // 抛出异常信息
    private String exception;

    // 是否成功 0 成功  1失败
    private String issucc;

    // 响应代码
    private String returncode;

    // 错误号码
    private String phone;

    // 发送日期
    private Date sendtime;


    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getIssucc() {
        return issucc;
    }

    public void setIssucc(String issucc) {
        this.issucc = issucc;
    }

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }
}