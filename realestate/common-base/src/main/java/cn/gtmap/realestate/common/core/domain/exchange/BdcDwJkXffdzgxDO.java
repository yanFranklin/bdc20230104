package cn.gtmap.realestate.common.core.domain.exchange;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/30 14:38
 * @description
 */
@Table(name = "BDC_DW_JK_XFFDZGX")
public class BdcDwJkXffdzgxDO {

    @Id
    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "消费方")
    private String xff;

    @ApiModelProperty(value = "权限标识")
    private String qxbs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXff() {
        return xff;
    }

    public void setXff(String xff) {
        this.xff = xff;
    }

    public String getQxbs() {
        return qxbs;
    }

    public void setQxbs(String qxbs) {
        this.qxbs = qxbs;
    }
}
