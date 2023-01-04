package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class FailInfo implements Serializable {

    private static final long serialVersionUID = 3649467981820440714L;

    @ApiModelProperty("错误码")
    private String code;

    @ApiModelProperty("错误描述")
    private String message;

    public static FailInfoBuilder builder() {
        return new FailInfoBuilder();
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FailInfo() {
    }

    public FailInfo(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static class FailInfoBuilder {
        private String code;
        private String message;

        FailInfoBuilder() {
        }

        public FailInfoBuilder code(String code) {
            this.code = code;
            return this;
        }

        public FailInfoBuilder message(String message) {
            this.message = message;
            return this;
        }

        public FailInfo build() {
            return new FailInfo(this.code, this.message);
        }

        @Override
        public String toString() {
            return "FailInfo.FailInfoBuilder(code=" + this.code + ", message=" + this.message + ")";
        }
    }
}
