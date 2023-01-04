package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @program: realestate
 * @description: 新模式获取大云服务器模板配置地址
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-04-16 15:58
 **/
@ApiModel(value = "BdcNewPrintDTO", description = "新模式打印传参DTO")
public class BdcNewPrintDTO implements Serializable {
    private static final long serialVersionUID = -8871931470884949096L;

    @ApiModelProperty(value = "打印类型")
    private String dylx;

    @ApiModelProperty(value = "调用生成xml应用名称")
    private String appName;

    @ApiModelProperty(value = "数据源xml服务地址")
    private String dataurl;

    @ApiModelProperty(value = "打印模板地址")
    private String modelurl;

    @ApiModelProperty(value = "是否开启静默打印")
    private String hidemodel;

    public String getDylx() {
        return dylx;
    }

    public void setDylx(String dylx) {
        this.dylx = dylx;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDataurl() {
        return dataurl;
    }

    public void setDataurl(String dataurl) {
        this.dataurl = dataurl;
    }

    public String getModelurl() {
        return modelurl;
    }

    public void setModelurl(String modelurl) {
        this.modelurl = modelurl;
    }

    public String getHidemodel() {
        return hidemodel;
    }

    public void setHidemodel(String hidemodel) {
        this.hidemodel = hidemodel;
    }

    @Override
    public String toString() {
        return "BdcNewPrintDTO{" +
                "dylx='" + dylx + '\'' +
                ", appName='" + appName + '\'' +
                ", dataurl='" + dataurl + '\'' +
                ", modelurl='" + modelurl + '\'' +
                ", hidemodel=" + hidemodel +
                '}';
    }
}
