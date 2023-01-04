package cn.gtmap.realestate.common.core.qo.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/2/21
 * @description 打印数据配置查询QO实体
 */
@ApiModel(value = "BdcDysjPzQO", description = "打印数据配置查询QO实体")
public class BdcDysjPzQO {
    @ApiModelProperty(value = "打印类型")
    String dylx;
    @ApiModelProperty(value = "打印子表ID")
    String dyzbid;
    @ApiModelProperty(value = "用途/名称")
    String ytmc;

    public String getDylx() {
        return dylx;
    }

    public void setDylx(String dylx) {
        this.dylx = dylx;
    }

    public String getDyzbid() {
        return dyzbid;
    }

    public void setDyzbid(String dyzbid) {
        this.dyzbid = dyzbid;
    }

    public String getYtmc() {
        return ytmc;
    }

    public void setYtmc(String ytmc) {
        this.ytmc = ytmc;
    }

    @Override
    public String toString() {
        return "BdcDysjPzQO{" +
                "dylx='" + dylx + '\'' +
                ", dyzbid='" + dyzbid + '\'' +
                ", ytmc='" + ytmc + '\'' +
                '}';
    }
}
