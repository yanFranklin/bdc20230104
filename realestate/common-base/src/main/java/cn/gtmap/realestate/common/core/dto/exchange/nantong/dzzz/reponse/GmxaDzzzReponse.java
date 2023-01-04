package cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.reponse;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wangyinghao
 */
public class GmxaDzzzReponse {
    /**
     * 是否成功
     */
    @ApiModelProperty("是否成功")
    Boolean success;

    /**
     * 提示内容
     */
    @ApiModelProperty("提示内容")
    String message;

    /**
     * 错误码
     */
    @ApiModelProperty("错误码")
    String errorCode;

    /**
     * 错误消息
     */
    @ApiModelProperty("错误消息")
    String errorMsg;

    /**
     * 匹配的总数
     */
    @ApiModelProperty("匹配的总数")
    Integer total;

    public Boolean isSuccess() {
        return success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
