package cn.gtmap.realestate.common.core.qo.building;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/14
 * @description 外网申请不动产单元QO
 */
public class WwsqBdcdyxxQO {

    @ApiModelProperty("权利人名称")
    private String qlrmc;

    @ApiModelProperty("坐落")
    private String zl;

    @ApiModelProperty(value = "坐落全模糊")
    private String zlmh;

    @ApiModelProperty("不动产单元号")
    private String bdcdyh;

    @ApiModelProperty("不动产单元编号")
    private String bdcdybh;

    @ApiModelProperty("权利人证件号")
    private String qlrzjh;

    @ApiModelProperty("权利人证件号")
    private String bdclx;

    @ApiModelProperty("户室ID,蚌埠特殊字段")
    private String houseid;

    @ApiModelProperty("权籍管理代码")
    private String qjgldm;



    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getZlmh() {
        return zlmh;
    }

    public void setZlmh(String zlmh) {
        this.zlmh = zlmh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcdybh() {
        return bdcdybh;
    }

    public void setBdcdybh(String bdcdybh) {
        this.bdcdybh = bdcdybh;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    public String getHouseid() {
        return houseid;
    }

    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    @Override
    public String toString() {
        return "WwsqBdcdyxxQO{" +
                "qlrmc='" + qlrmc + '\'' +
                ", zl='" + zl + '\'' +
                ", zlmh='" + zlmh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdybh='" + bdcdybh + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", bdclx='" + bdclx + '\'' +
                ", houseid='" + houseid + '\'' +
                ", qjgldm='" + qjgldm + '\'' +
                '}';
    }
}
