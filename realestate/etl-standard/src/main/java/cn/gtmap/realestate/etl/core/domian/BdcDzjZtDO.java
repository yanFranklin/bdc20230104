package cn.gtmap.realestate.etl.core.domian;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0, 2021/08/03
 * @description 打证机状态临时表(常州新老系统过渡临时表，上线后废弃)
 */
@Table(name = "BDC_DZJ_ZT")
public class BdcDzjZtDO {

    /**
     * 三方业务流水号
     */
    @ApiModelProperty(value = "三方业务流水号")
    private String ywlsh;

    /**
     * 打证机编号
     */
    @ApiModelProperty(value = "打证机编号")
    private String dzjbh;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态 :1-查询成功、2-取消打证、3-已更新")
    private String zt;

    public String getYwlsh() {
        return ywlsh;
    }

    public void setYwlsh(String ywlsh) {
        this.ywlsh = ywlsh;
    }

    public String getDzjbh() {
        return dzjbh;
    }

    public void setDzjbh(String dzjbh) {
        this.dzjbh = dzjbh;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }
}
