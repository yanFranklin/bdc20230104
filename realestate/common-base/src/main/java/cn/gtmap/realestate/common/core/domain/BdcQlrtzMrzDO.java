package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/8/30
 * @description 权利人特征默认值
 */
@Table(name = "BDC_QLRTZ_MRZ")
@ApiModel(value = "BdcQlrtzMrzDO",description = "权利人特征默认值")
public class BdcQlrtzMrzDO {

    @Id
    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "权利类型")
    private Integer qllx;
    @ApiModelProperty(value = "权利人类别")
    private String qlrlb;
    @ApiModelProperty(value = "权利人特征")
    private Integer qlrtz;

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

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public Integer getQlrtz() {
        return qlrtz;
    }

    public void setQlrtz(Integer qlrtz) {
        this.qlrtz = qlrtz;
    }
}
