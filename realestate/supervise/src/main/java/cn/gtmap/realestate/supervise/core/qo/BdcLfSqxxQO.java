package cn.gtmap.realestate.supervise.core.qo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhuyong@gmail.com">zhuyong</a>
 * @version V1.0, 2021/09/13
 * @description 职责权能监管-授权信息查询QO
 */
public class BdcLfSqxxQO {
    @ApiModelProperty(value = "授权人员")
    private String sqry;

    @ApiModelProperty(value = "授权说明")
    private String sqsm;

    @ApiModelProperty(value = "审批文件名称")
    private String spwjmc;

    @ApiModelProperty(value = "操作人")
    private String czr;

    @ApiModelProperty(value = "授权时间起")
    private String sqsjq;

    @ApiModelProperty(value = "授权时间止")
    private String sqsjz;

    @ApiModelProperty(value = "授权类型")
    private String sqlx;

    @ApiModelProperty(value = "所属单位")
    private String bmmc;

    @ApiModelProperty(value = "所属科室")
    private String ks;


    public String getSqry() {
        return sqry;
    }

    public void setSqry(String sqry) {
        this.sqry = sqry;
    }

    public String getSqsm() {
        return sqsm;
    }

    public void setSqsm(String sqsm) {
        this.sqsm = sqsm;
    }

    public String getSpwjmc() {
        return spwjmc;
    }

    public void setSpwjmc(String spwjmc) {
        this.spwjmc = spwjmc;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    public String getSqsjq() {
        return sqsjq;
    }

    public void setSqsjq(String sqsjq) {
        this.sqsjq = sqsjq;
    }

    public String getSqsjz() {
        return sqsjz;
    }

    public void setSqsjz(String sqsjz) {
        this.sqsjz = sqsjz;
    }

    public String getSqlx() {
        return sqlx;
    }

    public void setSqlx(String sqlx) {
        this.sqlx = sqlx;
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public String getKs() {
        return ks;
    }

    public void setKs(String ks) {
        this.ks = ks;
    }

    @Override
    public String toString() {
        return "BdcLfSqxxQO{" +
                "sqry='" + sqry + '\'' +
                ", sqsm='" + sqsm + '\'' +
                ", spwjmc='" + spwjmc + '\'' +
                ", czr='" + czr + '\'' +
                ", sqsjq='" + sqsjq + '\'' +
                ", sqsjz='" + sqsjz + '\'' +
                ", sqlx='" + sqlx + '\'' +
                ", bmmc='" + bmmc + '\'' +
                ", ks='" + ks + '\'' +
                '}';
    }
}
