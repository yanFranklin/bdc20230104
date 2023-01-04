package cn.gtmap.realestate.common.core.dto.register;

import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * 发送短信传输对象
 *
 * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
 * @version 1.0, 2022/08/01
 */
public class BdcSendMsgDTO {

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String dh;
    /**
     * 大云配置的短信类型，用于找到对应的短信模板
     */
    @ApiModelProperty(value = "短信类型")
    private String msgtype;

    /**
     * 模板参数
     */
    @ApiModelProperty(value = "模板参数")
    private Map templateparam;

    public String getDh() { return dh; }

    public void setDh(String dh) { this.dh = dh; }

    public String getMsgtype() { return msgtype; }

    public void setMsgtype(String msgtype) { this.msgtype = msgtype; }

    public Map getTemplateparam() { return templateparam; }

    public void setTemplateparam(Map templateparam) { this.templateparam = templateparam; }

    @Override
    public String toString() {
        return "BdcSendMsgDTO{" +
                "dh='" + dh + '\'' +
                ", msgtype='" + msgtype + '\'' +
                ", templateparam=" + templateparam +
                '}';
    }
}
