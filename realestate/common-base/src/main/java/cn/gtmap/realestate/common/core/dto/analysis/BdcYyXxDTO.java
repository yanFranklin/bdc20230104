package cn.gtmap.realestate.common.core.dto.analysis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2018/12/20
 * @description
 */
@ApiModel(value = "BdcYyXxDTO", description = "异议信息")
public class BdcYyXxDTO {

    @ApiModelProperty("异议事项")
    private String yysx;

    @ApiModelProperty(value = "登簿时间", example = "2018-12-20 18:00:00")
    private String djsj;

    @ApiModelProperty("异议证明号")
    private String bdcqzmh;

    @ApiModelProperty("申请人")
    private String sqr;

    @ApiModelProperty("附记")
    private String fj;

    public String getYysx() {
        return yysx;
    }

    public void setYysx(String yysx) {
        this.yysx = yysx;
    }

    public String getDjsj() {
        return djsj;
    }

    public void setDjsj(String djsj) {
        this.djsj = djsj;
    }

    public String getBdcqzmh() {
        return bdcqzmh;
    }

    public void setBdcqzmh(String bdcqzmh) {
        this.bdcqzmh = bdcqzmh;
    }

    public String getSqr() {
        return sqr;
    }

    public void setSqr(String sqr) {
        this.sqr = sqr;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }
}
