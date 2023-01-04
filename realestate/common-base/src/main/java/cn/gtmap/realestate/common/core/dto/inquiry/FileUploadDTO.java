package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/1/16
 * @description 文件上传
 */
@ApiModel(value = "FileUploadDTO", description = "文件上传返回信息")
public class FileUploadDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文件名")
    private String fileName;

    @ApiModelProperty("是否有数据")
    private boolean dataExists;

    @ApiModelProperty("是否上传成功")
    private boolean success;

    @ApiModelProperty("返回code")
    private String code;

    @ApiModelProperty("返回message")
    private String message;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDataExists() {
        return dataExists;
    }

    public void setDataExists(boolean dataExists) {
        this.dataExists = dataExists;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
