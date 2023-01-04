package cn.gtmap.realestate.common.core.domain.inquiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author wangyinghao
 * @version 1.0, 2018/11/13
 * @description 附件默认台账配置
 */
@Table(name = "BDC_DSFFJ_MRML_PZ")
@ApiModel(value = "BdcDsffjMrmlPzDO", description = "第三方附件默认目录配置")
public class BdcDsffjMrmlPzDO implements Serializable {

    @Id
    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "附件类型")
    private String fjlx;

    @ApiModelProperty(value = "默认目录")
    private String mrml;

    @ApiModelProperty(value = "顺序")
    private Integer sxh;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFjlx() {
        return fjlx;
    }

    public void setFjlx(String fjlx) {
        this.fjlx = fjlx;
    }

    public String getMrml() {
        return mrml;
    }

    public void setMrml(String mrml) {
        this.mrml = mrml;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    @Override
    public String toString() {
        return "BdcFjMrmlPzDO{" +
                "id='" + id + '\'' +
                ", fjlx='" + fjlx + '\'' +
                ", mrml='" + mrml + '\'' +
                ", sx=" + sxh +
                '}';
    }
}
