package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @description 业务类型与登记原因关系
 */
@Table(name = "BDC_DJXL_DJYY_GX")
public class BdcDjxlDjyyGxDO {
    @Id
    /**主键id*/
    @ApiModelProperty(value = "主键")
    private String id;
    /**登记原因*/
    @ApiModelProperty(value = "登记原因")
    private String djyy;
    /**
     * 登记小类
     */
    @ApiModelProperty(value = "登记小类")
    private String djxl;

    /**
     * 是否默认
     */
    @ApiModelProperty(value = "是否默认")
    private Integer sfmr;

    /**
     * 是否默认
     */
    @ApiModelProperty(value = "工作流定义ID")
    private String gzldyid;

    @ApiModelProperty(value = "顺序号")
    private Integer sxh;

    public Integer getSfmr() {
        return sfmr;
    }

    public void setSfmr(Integer sfmr) {
        this.sfmr = sfmr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcDjxlDjyyGxDO{");
        sb.append("id='").append(id).append('\'');
        sb.append(", djyy='").append(djyy).append('\'');
        sb.append(", djxl='").append(djxl).append('\'');
        sb.append(", sfmr=").append(sfmr);
        sb.append(", gzldyid='").append(gzldyid).append('\'');
        sb.append(", sxh=").append(sxh);
        sb.append('}');
        return sb.toString();
    }
}
