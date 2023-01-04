package cn.gtmap.realestate.common.core.domain.etl;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @program: realestate
 * @description: 合同备案权利人信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-14 11:06
 **/
@Table(name = "HTBA_QLR")
public class HtbaQlrDO implements Serializable {

    @Id
    @ApiModelProperty("权利人id")
    private String qlrid;
    @ApiModelProperty("备案id")
    private String baid;
    @ApiModelProperty("权利人名称")
    private String qlrmc;
    @ApiModelProperty("证件种类")
    private Integer zjzl;
    @ApiModelProperty("证件号")
    private String zjh;
    @ApiModelProperty("联系电话")
    private String lxdh;
    @ApiModelProperty("权利人类别")
    private String qlrlb;

    public String getQlrid() {
        return qlrid;
    }

    public void setQlrid(String qlrid) {
        this.qlrid = qlrid;
    }

    public String getBaid() {
        return baid;
    }

    public void setBaid(String baid) {
        this.baid = baid;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public Integer getZjzl() {
        return zjzl;
    }

    public void setZjzl(Integer zjzl) {
        this.zjzl = zjzl;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }


    @Override
    public String toString() {
        return "HtbaQlrDO{" +
                "qlrid='" + qlrid + '\'' +
                ", baid='" + baid + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", zjzl=" + zjzl +
                ", zjh='" + zjh + '\'' +
                ", lxdh='" + lxdh + '\'' +
                ", qlrlb='" + qlrlb + '\'' +
                '}';
    }
}
