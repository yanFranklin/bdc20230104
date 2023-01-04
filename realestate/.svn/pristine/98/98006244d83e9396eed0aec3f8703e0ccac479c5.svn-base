package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @description 业务类型与权利类型关系
 */
@Table(name = "BDC_DJXL_QLLX_GX")
public class BdcDjxlQllxGxDO {
    @Id
    /**主键id*/
    @ApiModelProperty(value = "主键ID")
    private String id;
    /**权利类型*/
    @ApiModelProperty(value = "权利类型")
    private Integer qllx;
    /**
     * 业务类型
     */
    @ApiModelProperty(value = "登记小类")
    private String djxl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    @Override
    public String toString() {
        return "BdcDjxlQllxGxDO{" +
                "id='" + id + '\'' +
                ", qllx=" + qllx +
                ", djxl='" + djxl + '\'' +
                '}';
    }
}
