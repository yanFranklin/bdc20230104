package cn.gtmap.realestate.common.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 2022/7/4,3.0
 * @description 不动产portal的配置项信息，前端读取后台配置项
 */
@ApiModel(value = "BdcPortalPzDTO", description = "不动产portal的配置项信息")
public class BdcPortalPzDTO implements Serializable {

    private static final long serialVersionUID = 576925265254444893L;

    @ApiModelProperty(value = "待办量跳转页面")
    private String dbltzym;

    @ApiModelProperty(value = "ofd是否在线预览")
    private Boolean ofdSfzxyl;

    @ApiModelProperty(value = "是否配置专项页面办理量")
    private Boolean zxywbll;

    @ApiModelProperty(value = "流程id以,分割多个流程名称id")
    private String lcid;

    public String getDbltzym() {
        return dbltzym;
    }

    public Boolean getZxywbll() {
        return zxywbll;
    }

    public void setZxywbll(Boolean zxywbll) {
        this.zxywbll = zxywbll;
    }

    public String getLcid() {
        return lcid;
    }

    public void setLcid(String lcid) {
        this.lcid = lcid;
    }

    public void setDbltzym(String dbltzym) {
        this.dbltzym = dbltzym;
    }

    public Boolean getOfdSfzxyl() {return ofdSfzxyl;}

    public void setOfdSfzxyl(Boolean ofdSfzxyl) {this.ofdSfzxyl = ofdSfzxyl;}

    @Override
    public String toString() {
        return "BdcPortalPzDTO{" +
                "dbltzym='" + dbltzym + '\'' +
                ", ofdSfzxyl=" + ofdSfzxyl +
                ", zxywbll=" + zxywbll +
                ", lcid='" + lcid + '\'' +
                '}';
    }
}
