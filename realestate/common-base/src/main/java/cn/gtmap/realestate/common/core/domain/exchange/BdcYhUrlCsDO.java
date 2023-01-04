package cn.gtmap.realestate.common.core.domain.exchange;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href ="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.3, 2021/01/08
 * @description 不动产银行地址参数表
 */
@Table(
        name = "BDC_YH_URL_CS"
)
@ApiModel(description = "不动产银行地址参数表")
public class BdcYhUrlCsDO {
    @Id
    /**主键*/
    @ApiModelProperty(value = "主键ID")
    private String id;
    @ApiModelProperty(value = "银行名称")
    private String yhmc;
    @ApiModelProperty(value = "银行系统url")
    private String yhurl;
    @ApiModelProperty(value = "地区版本 如 yancheng")
    private String version;
    @ApiModelProperty(value = "操作类型 如 delete")
    private String handlerType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYhmc() {
        return yhmc;
    }

    public void setYhmc(String yhmc) {
        this.yhmc = yhmc;
    }

    public String getYhurl() {
        return yhurl;
    }

    public void setYhurl(String yhurl) {
        this.yhurl = yhurl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHandlerType() {
        return handlerType;
    }

    public void setHandlerType(String handlerType) {
        this.handlerType = handlerType;
    }
}
