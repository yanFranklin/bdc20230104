package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @date 2022/11/30  11:35
 * @description 不动产受理数据修改申请信息
 */
@Table(name = "BDC_SL_SJXG")
@ApiModel(value = "BdcSlSjxgDO", description = "不动产受理数据修改申请信息")
public class BdcSlSjxgDO implements Serializable {

    private static final long serialVersionUID = 1682386775169215800L;

    @Id
    @ApiModelProperty(value = "数据修改id")
    private String sjxgid;

    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;

    @ApiModelProperty(value = "问题类型")
    private String wtlx;

    @ApiModelProperty(value = "申请人")
    private String sqr;

    @ApiModelProperty(value = "申请时间")
    private Date sqsj;

    @ApiModelProperty(value = "问题描述")
    private String wtms;


    public String getSjxgid() {
        return sjxgid;
    }

    public void setSjxgid(String sjxgid) {
        this.sjxgid = sjxgid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getWtlx() {
        return wtlx;
    }

    public void setWtlx(String wtlx) {
        this.wtlx = wtlx;
    }

    public String getSqr() {
        return sqr;
    }

    public void setSqr(String sqr) {
        this.sqr = sqr;
    }

    public Date getSqsj() {
        return sqsj;
    }

    public void setSqsj(Date sqsj) {
        this.sqsj = sqsj;
    }

    public String getWtms() {
        return wtms;
    }

    public void setWtms(String wtms) {
        this.wtms = wtms;
    }

    @Override
    public String toString() {
        return "BdcSlSjxgDO{" +
                "sjxgid='" + sjxgid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", wtlx='" + wtlx + '\'' +
                ", sqr='" + sqr + '\'' +
                ", sqsj=" + sqsj +
                ", wtms='" + wtms + '\'' +
                '}';
    }
}
