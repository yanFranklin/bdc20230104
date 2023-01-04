package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wangyinghao
 */
public class BdcSlFjtzDTO implements Serializable {
    private static final long serialVersionUID = 5863737481731574423L;
    @ApiModelProperty(value = "地块编号")
    private String dkbh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    public String getDkbh() {
        return dkbh;
    }

    public void setDkbh(String dkbh) {
        this.dkbh = dkbh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }
}
