package cn.gtmap.realestate.common.core.domain.rules;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/11/15,1.0
 * @description
 */
@Table( name ="BDC_GZ_XZYZLW" )
@ApiModel(value = "BdcGzXzyzlwDO", description = "例外信息")
public class BdcGzXzyzlwDO implements Serializable {


    private static final long serialVersionUID =  8428576014676353679L;

    @Id
    @ApiModelProperty(value = "例外id")
    private String lwid;

    @ApiModelProperty(value = "操作机器ip")
    private String czjqip;

    @ApiModelProperty(value = "操作人员id")
    private String czryid;

    @ApiModelProperty(value = "操作时间",example = "2018-10-01 12:18:48")
    private Date czsj;

    @ApiModelProperty(value = "例外类型(1:跳过查封，2:跳过锁定)")
    private String lwlx;

    @ApiModelProperty(value = "例外文号")
    private String lwwh;

    @ApiModelProperty(value = "例外原因")
    private String lwyy;

    @ApiModelProperty(value = "工作流定义id")
    private String gzldyid;

    public String getLwid() {
        return this.lwid;
    }

    public void setLwid(String lwid) {
        this.lwid = lwid;
    }

    public String getCzjqip() {
        return czjqip;
    }

    public void setCzjqip(String czjqip) {
        this.czjqip = czjqip;
    }

    public String getCzryid() {
        return this.czryid;
    }

    public void setCzryid(String czryid) {
        this.czryid = czryid;
    }

    public Date getCzsj() {
        return this.czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public String getLwlx() {
        return this.lwlx;
    }

    public void setLwlx(String lwlx) {
        this.lwlx = lwlx;
    }

    public String getLwyy() {
        return this.lwyy;
    }

    public void setLwyy(String lwyy) {
        this.lwyy = lwyy;
    }

    public String getLwwh() {
        return lwwh;
    }

    public void setLwwh(String lwwh) {
        this.lwwh = lwwh;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    @Override
    public String toString() {
        return "BdcGzXzyzlwDO{" +
                "lwid='" + lwid + '\'' +
                ", czjqip='" + czjqip + '\'' +
                ", czryid='" + czryid + '\'' +
                ", czsj=" + czsj +
                ", lwlx='" + lwlx + '\'' +
                ", lwwh='" + lwwh + '\'' +
                ", lwyy='" + lwyy + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                '}';
    }
}
