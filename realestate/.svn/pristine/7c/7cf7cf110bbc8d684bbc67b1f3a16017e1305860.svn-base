package cn.gtmap.realestate.common.core.domain.inquiry;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2021/01/14
 * @description 临时参数表DO对象
 */
@Table(name = "BDC_XT_LSCS")
public class BdcXtLscsDO {
    @ApiModelProperty("参数名称")
    private String csmc;

    @ApiModelProperty("参数值")
    private String csz;

    @ApiModelProperty("参数值1")
    private String csz1;

    @ApiModelProperty("参数值2")
    private String csz2;


    @ApiModelProperty("参数值3")
    private String csz3;

    /**
     * 应用重启时候删除临时参数数据： 1 删除 ； 0或空不删除
     */
    @ApiModelProperty("是否删除")
    private Integer sfsc;


    public Integer getSfsc() {
        return sfsc;
    }

    public void setSfsc(Integer sfsc) {
        this.sfsc = sfsc;
    }

    public String getCsmc() {
        return csmc;
    }

    public void setCsmc(String csmc) {
        this.csmc = csmc;
    }

    public String getCsz() {
        return csz;
    }

    public void setCsz(String csz) {
        this.csz = csz;
    }

    public String getCsz1() {
        return csz1;
    }

    public void setCsz1(String csz1) {
        this.csz1 = csz1;
    }

    public String getCsz2() {
        return csz2;
    }

    public void setCsz2(String csz2) {
        this.csz2 = csz2;
    }

    public String getCsz3() {
        return csz3;
    }

    public void setCsz3(String csz3) {
        this.csz3 = csz3;
    }

    @Override
    public String toString() {
        return "BdcXtLscsDO{" +
                "csmc='" + csmc + '\'' +
                ", csz='" + csz + '\'' +
                ", csz1='" + csz1 + '\'' +
                ", csz2='" + csz2 + '\'' +
                ", csz3='" + csz3 + '\'' +
                ", sfsc=" + sfsc +
                '}';
    }
}
