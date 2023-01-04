package cn.gtmap.realestate.exchange.core.qo.fcjy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/10/13
 * @description 人才锁定相关接口QO
 */
@ApiModel(value = "BdcRcsdQO", description = "人才锁定相关接口QO")
public class BdcRcsdQO {

    @ApiModelProperty(value = "项目id")
    private String xmid;

    @ApiModelProperty(value = "产权证号")
    private String cqzh;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    @Override
    public String toString() {
        return "BdcRcsdQO{" +
                "xmid='" + xmid + '\'' +
                ", cqzh='" + cqzh + '\'' +
                '}';
    }
}
