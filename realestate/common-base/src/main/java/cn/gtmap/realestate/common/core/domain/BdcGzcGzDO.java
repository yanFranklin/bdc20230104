package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 */
@Table(
        name = "BDC_GZCGZ"
)
@ApiModel(value = "BDC_GZCGZ", description = "公证处公证信息")
public class BdcGzcGzDO {
    @Id
    /**公证书编号*/
    @ApiModelProperty(value = "公证书编号")
    private String gzsbh;

    /**公证处名称*/
    @ApiModelProperty(value = "公证处名称")
    private String gzcmc;

    /**公证员*/
    @ApiModelProperty(value = "公证员")
    private String gzy;

    /**出证时间*/
    @ApiModelProperty(value = "出证时间")
    private String czsj;

    /**公证事项*/
    @ApiModelProperty(value = "公证事项")
    private String gzsx;

    /**用途*/
    @ApiModelProperty(value = "用途")
    private String yt;

    public String getGzsbh() {
        return gzsbh;
    }

    public void setGzsbh(String gzsbh) {
        this.gzsbh = gzsbh;
    }

    public String getGzcmc() {
        return gzcmc;
    }

    public void setGzcmc(String gzcmc) {
        this.gzcmc = gzcmc;
    }

    public String getGzy() {
        return gzy;
    }

    public void setGzy(String gzy) {
        this.gzy = gzy;
    }

    public String getCzsj() {
        return czsj;
    }

    public void setCzsj(String czsj) {
        this.czsj = czsj;
    }

    public String getGzsx() {
        return gzsx;
    }

    public void setGzsx(String gzsx) {
        this.gzsx = gzsx;
    }

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }
}
