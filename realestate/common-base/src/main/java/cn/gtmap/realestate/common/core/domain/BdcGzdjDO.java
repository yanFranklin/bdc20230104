package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/8/30
 * @description 更正登记
 */
@Table(name = "BDC_GZDJ")
public class BdcGzdjDO {

    @Id
    @ApiModelProperty(value = "更正ID")
    private String gzid;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "更正登记类型")
    private String gzdjlx;
    @ApiModelProperty(value = "更正依据")
    private String gzyj;
    @ApiModelProperty(value = "申请主体")
    private String gzgt;
    @ApiModelProperty(value = "更正内容")
    private String gznr;

    public String getGzid() {
        return gzid;
    }

    public void setGzid(String gzid) {
        this.gzid = gzid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getGzdjlx() {
        return gzdjlx;
    }

    public void setGzdjlx(String gzdjlx) {
        this.gzdjlx = gzdjlx;
    }

    public String getGzyj() {
        return gzyj;
    }

    public void setGzyj(String gzyj) {
        this.gzyj = gzyj;
    }

    public String getGzgt() {
        return gzgt;
    }

    public void setGzgt(String gzgt) {
        this.gzgt = gzgt;
    }

    public String getGznr() {
        return gznr;
    }

    public void setGznr(String gznr) {
        this.gznr = gznr;
    }

    @Override
    public String toString() {
        return "BdcGzdjDO{" +
                "gzid='" + gzid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", gzdjlx='" + gzdjlx + '\'' +
                ", gzyj='" + gzyj + '\'' +
                ", gzgt='" + gzgt + '\'' +
                ", gznr='" + gznr + '\'' +
                '}';
    }
}
