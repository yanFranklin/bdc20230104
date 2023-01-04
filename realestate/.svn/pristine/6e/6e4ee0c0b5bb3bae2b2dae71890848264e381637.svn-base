package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/6/17
 * @description 第三方字典关系对照表实体DO定义
 */
@Table(name = "BDC_ZD_DSFZDGXDZB")
public class BdcZdDsfzdgxDO {
    /**
     * ID
     */
    @Id
    @ApiModelProperty(value = "ID")
    private String id;
    /**
     * 字典表名称
     */
    @ApiModelProperty(value = "字典表名称")
    private String zdbs;
    /**
     * 第三方字典项
     */
    @ApiModelProperty(value = "第三方字典值")
    private String dsfzdz;

    /**
     * 登记系统字典值
     */
    @ApiModelProperty(value = "不动产字典值")
    private String bdczdz;
    /**
     * 第三方系统字典值
     */
    @ApiModelProperty(value = "第三方系统标识")
    private String dsfxtbs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZdbs() {
        return zdbs;
    }

    public void setZdbs(String zdbs) {
        this.zdbs = zdbs;
    }


    public String getDsfzdz() {
        return dsfzdz;
    }

    public void setDsfzdz(String dsfzdz) {
        this.dsfzdz = dsfzdz;
    }

    public String getBdczdz() {
        return bdczdz;
    }

    public void setBdczdz(String bdczdz) {
        this.bdczdz = bdczdz;
    }

    public String getDsfxtbs() {
        return dsfxtbs;
    }

    public void setDsfxtbs(String dsfxtbs) {
        this.dsfxtbs = dsfxtbs;
    }

    @Override
    public String toString() {
        return "BdcZdDsfzdgxDO{" +
                "id='" + id + '\'' +
                ", zdbs='" + zdbs + '\'' +
                ", dsfzdz='" + dsfzdz + '\'' +
                ", bdczdz='" + bdczdz + '\'' +
                ", dsfxtbs='" + dsfxtbs + '\'' +
                '}';
    }
}
