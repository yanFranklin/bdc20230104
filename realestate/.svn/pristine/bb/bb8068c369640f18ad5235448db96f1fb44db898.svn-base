package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/31
 * @description 用海状况
 */
@Table(name ="BDC_BDCDJB_ZHJBXX_YHZK")
@ApiModel(value = "BdcBdcdjbZhjbxxYhzkDO", description = "登记簿用海状况")
public class BdcBdcdjbZhjbxxYhzkDO {
    @Id
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;
    /**宗海代码*/
    @ApiModelProperty(value = "宗海代码")
    private String zhdm;
    /**用海方式*/
    @ApiModelProperty(value = "用海方式")
    private Integer yhfs;
    /**用海面积*/
    @ApiModelProperty(value = "用海面积")
    private Double yhmj;
    /**具体用途*/
    @ApiModelProperty(value = "具体用途")
    private String jtyt;
    /**使用金数额*/
    @ApiModelProperty(value = "使用金数额")
    private Double syjse;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZhdm() {
        return zhdm;
    }

    public void setZhdm(String zhdm) {
        this.zhdm = zhdm;
    }

    public Integer getYhfs() {
        return yhfs;
    }

    public void setYhfs(Integer yhfs) {
        this.yhfs = yhfs;
    }

    public Double getYhmj() {
        return yhmj;
    }

    public void setYhmj(Double yhmj) {
        this.yhmj = yhmj;
    }

    public String getJtyt() {
        return jtyt;
    }

    public void setJtyt(String jtyt) {
        this.jtyt = jtyt;
    }

    public Double getSyjse() {
        return syjse;
    }

    public void setSyjse(Double syjse) {
        this.syjse = syjse;
    }

    @Override
    public String toString() {
        return "BdcBdcdjbZhjbxxYhzkDO{" +
                "id='" + id + '\'' +
                ", zhdm='" + zhdm + '\'' +
                ", yhfs=" + yhfs +
                ", yhmj=" + yhmj +
                ", jtyt='" + jtyt + '\'' +
                ", syjse=" + syjse +
                '}';
    }
}
