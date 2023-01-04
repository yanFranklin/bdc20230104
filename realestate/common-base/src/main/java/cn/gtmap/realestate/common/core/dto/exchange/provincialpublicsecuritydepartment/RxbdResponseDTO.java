package cn.gtmap.realestate.common.core.dto.exchange.provincialpublicsecuritydepartment;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @program: bdcdj3.0
 * @description: 人像比对接口响应DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-10-10 17:25
 **/
public class RxbdResponseDTO {

    @ApiModelProperty("异常信息")
    private String exception;

    @ApiModelProperty("是否存在 Y/N")
    private String exist;
    @ApiModelProperty("相似度")
    private String verify;

    @ApiModelProperty("名称")
    private String mc;

    @ApiModelProperty("证件号")
    private String zjh;


    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getExist() {
        return exist;
    }

    public void setExist(String exist) {
        this.exist = exist;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }
}
