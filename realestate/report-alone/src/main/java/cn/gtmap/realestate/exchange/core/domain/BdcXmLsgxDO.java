package cn.gtmap.realestate.exchange.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.3, 2018/11/05
 * @description 项目历史关系
 */
@Table(
        name = "BDC_XM_LSGX"
)
@ApiModel(description = "不动产项目历史关系")
public class BdcXmLsgxDO {
    @Id
    /**主键*/
    @ApiModelProperty(value = "主键ID")
    private String gxid;
    /**项目id*/
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    /**原项目id*/
    @ApiModelProperty(value = "原项目ID")
    private String yxmid;
    /**是否注销原权利  0:否  1：是*/
    @ApiModelProperty(value = "是否注销原权利  0:否  1：是")
    private Integer zxyql;
    /**是否外联项目 0:否  1：是*/
    @ApiModelProperty(value = "是否外联项目  0:否  1：是")
    private Integer wlxm;

    public Integer getWlxm() {
        return wlxm;
    }

    public void setWlxm(Integer wlxm) {
        this.wlxm = wlxm;
    }

    public Integer getZxyql() {
        return zxyql;
    }

    public void setZxyql(Integer zxyql) {
        this.zxyql = zxyql;
    }

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getYxmid() {
        return yxmid;
    }

    public void setYxmid(String yxmid) {
        this.yxmid = yxmid;
    }

    @Override
    public String toString() {
        return "BdcXmLsgxDO{" +
                "gxid='" + gxid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", yxmid='" + yxmid + '\'' +
                ", zxyql=" + zxyql +
                '}';
    }
}
