package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 受理小区信息字典表
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-06-01 10:04
 **/
@Table(name = "BDC_SL_ZD_XQXX")
public class BdcSlZdXqxx {

    @ApiModelProperty(value = "代码")
    private String dm;

    @ApiModelProperty(value = "名称")
    private String mc;

    @ApiModelProperty(value = "街道代码")
    private String jddm;

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getJddm() {
        return jddm;
    }

    public void setJddm(String jddm) {
        this.jddm = jddm;
    }
}
