package cn.gtmap.realestate.common.core.dto.natural;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/3/20
 * @description
 */
@ApiModel(value = "DtcxConfigCheckDTO", description = "动态查询查询条件验证")
public class DtcxConfigCheckDTO {

    @ApiModelProperty("是否验证通过 1=成功 0=失败")
    private Integer success = 0;

    @ApiModelProperty("验证结果")
    private String result = "fail";

    @ApiModelProperty("错误的记录")
    private List<String> errorId = new ArrayList<>();

    public DtcxConfigCheckDTO() {
    }

    public DtcxConfigCheckDTO(Integer success, String result, List<String> errorId) {
        this.success = success;
        this.result = result;
        this.errorId = errorId;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<String> getErrorId() {
        return errorId;
    }

    public void setErrorId(List<String> errorId) {
        this.errorId = errorId;
    }
}
