package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @Description: 登记原因与抵押方式关系
 */
@Table(name = "BDC_DJYY_DYFS_GX")
@ApiModel(value = "BdcDjyyDyfsGxDO", description = "登记原因与抵押方式关系")
public class BdcDjyyDyfsGxDO {

    @Id
    /**主键id*/
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 登记原因
     */
    @ApiModelProperty(value = "登记原因")
    private String djyy;
    /**
     * 抵押方式
     */
    @ApiModelProperty(value = "抵押方式")
    private Integer dyfs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public Integer getDyfs() {
        return dyfs;
    }

    public void setDyfs(Integer dyfs) {
        this.dyfs = dyfs;
    }

    @Override
    public String toString() {
        return "BdcDjyyDyfsGxDO{" +
                "id='" + id + '\'' +
                ", djyy='" + djyy + '\'' +
                ", dyfs=" + dyfs +
                '}';
    }
}
