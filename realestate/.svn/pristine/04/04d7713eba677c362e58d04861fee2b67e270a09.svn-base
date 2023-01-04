package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/3/1
 * @description 项目移交单关系对象
 */
@Table(name = "BDC_XM_YJD_GX")
@ApiModel(value = "BdcXmYjdGx", description = "项目移交单关系对象")
public class BdcXmYjdGxDO {
    @ApiModelProperty("关系ID")
    @Id
    String gxid;
    @ApiModelProperty("项目ID")
    String xmid;
    @ApiModelProperty("移交单ID")
    String yjdid;
    @ApiModelProperty("顺序号")
    Integer sxh;

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

    public String getYjdid() {
        return yjdid;
    }

    public void setYjdid(String yjdid) {
        this.yjdid = yjdid;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    @Override
    public String toString() {
        return "BdcXmYjdGxDO{" +
                "gxid='" + gxid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", sxh='" + sxh + '\'' +
                ", yjdid='" + yjdid + '\'' +
                '}';
    }

}
