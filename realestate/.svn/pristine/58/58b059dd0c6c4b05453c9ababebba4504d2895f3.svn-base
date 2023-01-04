package cn.gtmap.realestate.common.core.domain.inquiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/13
 * @description 地块附件信息表
 */
@Table(name = "BDC_DSFFJ_XX_ITEM")
@ApiModel(value = "BdcDsffjXxItemDO", description = "第三方附件信息表")
public class BdcDsffjXxItemDO implements Serializable {

    @Id
    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "地块编号")
    private String dkbh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "文件中心spaceid")
    private String wjzxid;

    @ApiModelProperty(value = "创建时间")
    private Date cjsj;

    @ApiModelProperty(value = "创建人")
    private String cjr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public String getWjzxid() {
        return wjzxid;
    }

    public void setWjzxid(String wjzxid) {
        this.wjzxid = wjzxid;
    }

    @Override
    public String toString() {
        return "BdcFjDkfjxxDO{" +
                "id='" + id + '\'' +
                ", dkbh='" + dkbh + '\'' +
                ", zl='" + zl + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", wjzxid='" + wjzxid + '\'' +
                ", cjsj=" + cjsj +
                ", cjr='" + cjr + '\'' +
                '}';
    }
}
