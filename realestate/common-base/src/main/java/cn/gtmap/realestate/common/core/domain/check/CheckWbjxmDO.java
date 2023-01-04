package cn.gtmap.realestate.common.core.domain.check;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author lst
 * @version 1.0, 2020-01-03
 * @description 检测过的未办结项目列表 做补漏检测
 */
@Table(name = "CHECK_WBJXM")
@ApiModel(value = "CheckWbjxmDO",description = "检测过的未办结项目,做补漏检测")
public class CheckWbjxmDO {

    @Id
    @ApiModelProperty(value = "项目id")
    private String xmid;
    @ApiModelProperty(value = "检查日期")
    private Date jcsj;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Date getJcsj() {
        return jcsj;
    }

    public void setJcsj(Date jcsj) {
        this.jcsj = jcsj;
    }

    @Override
    public String toString() {
        return "CheckWbjxm{" +
                "xmid='" + xmid + '\'' +
                ", jcsj=" + jcsj +
                '}';
    }
}
