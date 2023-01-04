package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/3/10
 * @description 特殊业务配置自定义字典项表
 */
@Table(name = "BDC_TSYWPZ_ZDYZDX")
@ApiModel(value = "BdcTsywZdyzdxDO", description = "特殊业务配置自定义字典项表")
public class BdcTsywZdyzdxDO {

    @Id
    @ApiModelProperty(value = "主键")
    private String id;

    @Id
    @ApiModelProperty(value = "自定义字典项标识")
    private String zdyzdxbs;

    @Id
    @ApiModelProperty(value = "字典项实际值")
    private String zdxsjz;

    @Id
    @ApiModelProperty(value = "字典项显示值")
    private String zdxxsz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZdyzdxbs() {
        return zdyzdxbs;
    }

    public void setZdyzdxbs(String zdyzdxbs) {
        this.zdyzdxbs = zdyzdxbs;
    }

    public String getZdxsjz() {
        return zdxsjz;
    }

    public void setZdxsjz(String zdxsjz) {
        this.zdxsjz = zdxsjz;
    }

    public String getZdxxsz() {
        return zdxxsz;
    }

    public void setZdxxsz(String zdxxsz) {
        this.zdxxsz = zdxxsz;
    }
}
