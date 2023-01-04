package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/10/24
 * @description 水电气业务办理状态responseDTO
 */
public class BdcSdqBlztResponseDTO {

    @ApiModelProperty(value = "返回结果")
    private boolean result;

    @ApiModelProperty(value = "返回信息")
    private String message;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BdcSdqBlztResponseDTO{" +
                "result=" + result +
                ", message='" + message + '\'' +
                '}';
    }
}
