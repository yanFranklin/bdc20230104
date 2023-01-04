package cn.gtmap.realestate.exchange.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 共享外网申请信息 材料信息
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/3/20 21:11
 */
@Table(name = "GX_WW_SQXX_CLXX")
@ApiModel(value = "GxWwSqxxClxxDO", description = "共享外网申请材料信息")
public class GxWwSqxxClxxDO {
    @Id
    @ApiModelProperty(value = "材料 id")
    private String clid;
    @ApiModelProperty(value = "材料 id")
    private String clmc;
    @ApiModelProperty(value = "申请信息 id")
    private String sqxxid;
    @ApiModelProperty(value = "材料类型")
    private String cllx;
    @ApiModelProperty(value = "序号")
    private Integer xh;

    public String getCllx() {
        return cllx;
    }

    public void setCllx(String cllx) {
        this.cllx = cllx;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public String getClid() {
        return clid;
    }

    public void setClid(String clid) {
        this.clid = clid;
    }

    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }

    public String getSqxxid() {
        return sqxxid;
    }

    public void setSqxxid(String sqxxid) {
        this.sqxxid = sqxxid;
    }

    @Override
    public String toString() {
        return "GxWwSqxxClxxDO{" +
                "clid='" + clid + '\'' +
                ", clmc='" + clmc + '\'' +
                ", sqxxid='" + sqxxid + '\'' +
                ", cllx='" + cllx + '\'' +
                ", xh='" + xh + '\'' +
                '}';
    }
}
