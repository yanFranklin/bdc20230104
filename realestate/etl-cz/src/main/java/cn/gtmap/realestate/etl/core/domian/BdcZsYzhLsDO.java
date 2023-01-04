package cn.gtmap.realestate.etl.core.domian;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0, 2021/07/08
 * @description 证书印制号临时表(常州新老系统过渡临时表，上线后废弃)
 */
@Table(name = "BDC_ZS_YZH_LS")
public class BdcZsYzhLsDO {
    @Id
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 三方业务流水号
     */
    @ApiModelProperty(value = "三方业务流水号")
    private String ywlsh;

    /**
     * 证号
     */
    @ApiModelProperty(value = "证号")
    private String zh;

    /**
     * 印制号
     */
    @ApiModelProperty(value = "印制号")
    private String yzh;

    /**
     * 打证机编号
     */
    @ApiModelProperty(value = "打证机编号")
    private String dzjbh;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String zt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYwlsh() {
        return ywlsh;
    }

    public void setYwlsh(String ywlsh) {
        this.ywlsh = ywlsh;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getYzh() {
        return yzh;
    }

    public void setYzh(String yzh) {
        this.yzh = yzh;
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
