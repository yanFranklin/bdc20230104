package cn.gtmap.realestate.etl.core.domian.nantong;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 证书信息表
 */
@Table(name = "bj_inf_apply_zs")
public class BjInfApplyZs {
    @Id
    @ApiModelProperty(value = "唯一标识")
    private String id;
    @ApiModelProperty(value = "项目id")
    private String internalNo;
    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;
    @ApiModelProperty(value = "面积")
    private String mj;
    @ApiModelProperty(value = "用途")
    private String yt;
    @ApiModelProperty(value = "使用期限")
    private String syqx;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "附记")
    private String fj;
    @ApiModelProperty(value = "权利其他状况")
    private String qlqtzk;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInternalNo() {
        return internalNo;
    }

    public void setInternalNo(String internalNo) {
        this.internalNo = internalNo;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getMj() {
        return mj;
    }

    public void setMj(String mj) {
        this.mj = mj;
    }

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }

    public String getSyqx() {
        return syqx;
    }

    public void setSyqx(String syqx) {
        this.syqx = syqx;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getQlqtzk() {
        return qlqtzk;
    }

    public void setQlqtzk(String qlqtzk) {
        this.qlqtzk = qlqtzk;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }
}
