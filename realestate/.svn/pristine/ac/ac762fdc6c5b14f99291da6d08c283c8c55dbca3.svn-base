package cn.gtmap.realestate.exchange.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 共享外网申请信息 附件信息
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/3/20 21:11
 */
@Table(name = "GX_WW_SQXX_FjXX")
@ApiModel(value = "GxWwSqxxFjxxDO", description = "共享外网申请附件信息")
public class GxWwSqxxFjxxDO {
    @Id
    @ApiModelProperty(value = "附件 id")
    private String fjid;
    @ApiModelProperty(value = "材料 id")
    private String clid;
    @ApiModelProperty(value = "附件名称")
    private String fjmc;
    @ApiModelProperty(value = "附件路径")
    private String fjlj;
    @ApiModelProperty(value = "序号")
    private String xh;

    @Override
    public String toString() {
        return "GxWwSqxxFjxxDO{" +
                "fjid='" + fjid + '\'' +
                ", clid='" + clid + '\'' +
                ", fjmc='" + fjmc + '\'' +
                ", fjlj='" + fjlj + '\'' +
                ", xh='" + xh + '\'' +
                '}';
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getFjid() {
        return fjid;
    }

    public void setFjid(String fjid) {
        this.fjid = fjid;
    }

    public String getClid() {
        return clid;
    }

    public void setClid(String clid) {
        this.clid = clid;
    }

    public String getFjmc() {
        return fjmc;
    }

    public void setFjmc(String fjmc) {
        this.fjmc = fjmc;
    }

    public String getFjlj() {
        return fjlj;
    }

    public void setFjlj(String fjlj) {
        this.fjlj = fjlj;
    }
}
