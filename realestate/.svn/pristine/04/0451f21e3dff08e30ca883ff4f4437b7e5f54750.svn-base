package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @description 登记类型与业务类型关系
 */
@Table(name = "BDC_DJLX_DJXL_GX")
public class BdcDjlxDjxlGxDO {
    @Id
    /**主键id*/
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 登记类型
     */
    @ApiModelProperty(value = "登记类型")
    private Integer djlx;
    /**
     * 登记小类
     */
    @ApiModelProperty(value = "登记小类")
    private String djxl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDjlx() {
        return djlx;
    }

    public void setDjlx(Integer djlx) {
        this.djlx = djlx;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    @Override
    public String toString() {
        return "BdcDjlxDjxlGxDO{" +
                "id='" + id + '\'' +
                ", djlx=" + djlx +
                ", djxl='" + djxl + '\'' +
                '}';
    }
}
