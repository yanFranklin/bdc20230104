package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/12/17.
 * @description 中国市区县字典表
 */
@Table(name = "BDC_ZD_ZGSQX")
public class BdcZdZgSqxDO {

    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "市区县代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "市区县名称")
    private String mc;
    /**
     * 父节点代码
     */
    @ApiModelProperty(value = "父节点代码")
    private String fjddm;

    public Integer getDm() {
        return dm;
    }

    public void setDm(Integer dm) {
        this.dm = dm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getFjddm() {
        return fjddm;
    }

    public void setFjddm(String fjddm) {
        this.fjddm = fjddm;
    }

    @Override
    public String toString() {
        return "BdcZdZgSqxDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                ", fjddm='" + fjddm + '\'' +
                '}';
    }
}
