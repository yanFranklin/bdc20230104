package cn.gtmap.realestate.common.core.dto.exchange.openapi;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author <a href="mailto:zedeqiang@gtmap.com">zedq</a>
 * @version 1.0
 * @date 2021/03/02 10:43
 */
public class BdcOpenApiResponseDetailDTO implements Serializable {

    private static final long serialVersionUID = -2276026555059548106L;

    @ApiModelProperty(value = "响应编码key")
    private String respCode;
    @ApiModelProperty(value = "响应编码value")
    private String respCodeValue;
    @ApiModelProperty(value = "响应描述key")
    private String respMsg;
    @ApiModelProperty(value = "响应描述value")
    private String respMsgValue;
    @ApiModelProperty(value = "响应情况")
    private String respDesc;
    @ApiModelProperty(value = "响应情况code")
    private String respDetailCode;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespCodeValue() {
        return respCodeValue;
    }

    public void setRespCodeValue(String respCodeValue) {
        this.respCodeValue = respCodeValue;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getRespMsgValue() {
        return respMsgValue;
    }

    public void setRespMsgValue(String respMsgValue) {
        this.respMsgValue = respMsgValue;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public String getRespDetailCode() {
        return respDetailCode;
    }

    public void setRespDetailCode(String respDetailCode) {
        this.respDetailCode = respDetailCode;
    }
}
