package cn.gtmap.realestate.common.core.domain.engine;

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
@Table( name ="BDC_GZ_LW" )
@ApiModel(value = "BdcGzLwDO", description = "例外信息")
public class BdcGzLwDO implements Serializable {
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

    @ApiModelProperty(value = "例外原因")
    private String lwyy;

    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;

    @ApiModelProperty(value = "例外文号")
    private String lwwh;

    @ApiModelProperty(value = "（需要例外的）子规则ID")
    private String gzid;


    public String getLwid() {
        return lwid;
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
        return czryid;
    }

    public void setCzryid(String czryid) {
        this.czryid = czryid;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public String getLwyy() {
        return lwyy;
    }

    public void setLwyy(String lwyy) {
        this.lwyy = lwyy;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getLwwh() {
        return lwwh;
    }

    public void setLwwh(String lwwh) {
        this.lwwh = lwwh;
    }

    public String getGzid() {
        return gzid;
    }

    public void setGzid(String gzid) {
        this.gzid = gzid;
    }

    @Override
    public String toString() {
        return "BdcGzXzyzlwDO{" +
                "lwid='" + lwid + '\'' +
                ", czjqip='" + czjqip + '\'' +
                ", czryid='" + czryid + '\'' +
                ", czsj=" + czsj +
                ", lwyy='" + lwyy + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", lwwh='" + lwwh + '\'' +
                ", gzid='" + gzid + '\'' +
                '}';
    }
}
