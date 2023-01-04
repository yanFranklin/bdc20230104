package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/31
 * @description 用海用岛坐标
 */
@Table(name = "BDC_BDCDJB_ZHJBXX_YHYDZB")
@ApiModel(value = "BdcBdcdjbZhjbxxYhydzbDO", description = "登记簿用海用岛坐标")
public class BdcBdcdjbZhjbxxYhydzbDO {
    @Id
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;
    /**宗海/海岛代码*/
    @ApiModelProperty(value = "宗海/海岛代码")
    private String zhhddm;
    /**序号*/
    @ApiModelProperty(value = "序号")
    private Integer xh;
    /**北纬*/
    @ApiModelProperty(value = "北纬")
    private Double bw;
    /**东经*/
    @ApiModelProperty(value = "东经")
    private Double dj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZhhddm() {
        return zhhddm;
    }

    public void setZhhddm(String zhhddm) {
        this.zhhddm = zhhddm;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public Double getBw() {
        return bw;
    }

    public void setBw(Double bw) {
        this.bw = bw;
    }

    public Double getDj() {
        return dj;
    }

    public void setDj(Double dj) {
        this.dj = dj;
    }

    @Override
    public String toString() {
        return "BdcBdcdjbZhjbxxYhydzbDO{" +
                "id='" + id + '\'' +
                ", zhhddm='" + zhhddm + '\'' +
                ", xh=" + xh +
                ", bw=" + bw +
                ", dj=" + dj +
                '}';
    }
}
