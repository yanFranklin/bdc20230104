package cn.gtmap.realestate.common.core.domain.natural;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2021/01/14
 * @description 临时参数表DO对象
 */
@Table(name = "ZRZY_XT_LSCS")
public class ZrzyXtLscsDO {
    @ApiModelProperty("参数名称")
    private String csmc;

    @ApiModelProperty("参数值")
    private String csz;

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

    @Override
    public String toString() {
        return "BdcXtLscsDO{" +
                "csmc='" + csmc + '\'' +
                ", csz='" + csz + '\'' +
                '}';
    }
}
