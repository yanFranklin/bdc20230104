package cn.gtmap.realestate.common.core.vo.natural;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/3/5
 * @description 返回结果
 */
@ApiModel(value = "ResultDataVO", description = "返回结果")
public class ResultDataVO {

    @ApiModelProperty("是否成功")
    private boolean success;

    @ApiModelProperty("返回信息")
    private String message;

    public ResultDataVO() {
    }
    public ResultDataVO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResultDataVO(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
