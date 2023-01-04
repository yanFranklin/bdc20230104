package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-25
 * @description 户室图请求实体
 */
@ApiModel(value = "FwHstRequestDTO", description = "户室图DTO")
public class FwHstRequestDTO {

    @ApiModelProperty(value = "房屋主键")
    private String fwHsIndex;

    @ApiModelProperty(value = "户室图主键")
    private String fwHstIndex;

    @ApiModelProperty(value = "下载ID")
    private String downId;

    @ApiModelProperty(value = "上传用户名")
    private String jlyhm;

    @ApiModelProperty(value = "户室图名称")
    private String hstmc;

    @ApiModelProperty(value = "房屋类型")
    private String fwlx;

    public String getFwlx() {
        return fwlx;
    }

    public void setFwlx(String fwlx) {
        this.fwlx = fwlx;
    }

    public String getJlyhm() {
        return jlyhm;
    }

    public void setJlyhm(String jlyhm) {
        this.jlyhm = jlyhm;
    }

    public String getHstmc() {
        return hstmc;
    }

    public void setHstmc(String hstmc) {
        this.hstmc = hstmc;
    }

    public String getFwHsIndex() {
        return fwHsIndex;
    }

    public void setFwHsIndex(String fwHsIndex) {
        this.fwHsIndex = fwHsIndex;
    }

    public String getFwHstIndex() {
        return fwHstIndex;
    }

    public void setFwHstIndex(String fwHstIndex) {
        this.fwHstIndex = fwHstIndex;
    }

    public String getDownId() {
        return downId;
    }

    public void setDownId(String downId) {
        this.downId = downId;
    }
}
