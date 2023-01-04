package cn.gtmap.realestate.common.core.qo.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: <a href="@mailto:hynkoala@163.com">hanyaning</a>
 * @version: V1.0, 2019.03.18
 * @description:
 */
@Api(value = "BdcDtcxQO", description = "动态查询QO")
public class BdcDtcxQO {
    @ApiModelProperty("查询服务名称")
    private String cxmc;
    @ApiModelProperty("查询服务代号")
    private String cxdh;
    @ApiModelProperty("创建人")
    private String cjr;
    @ApiModelProperty("变更人")
    private String bgr;
    @ApiModelProperty("服务地址")
    private String url;

    @ApiModelProperty("创建时间(起)")
    private String cjqssj;

    @ApiModelProperty("创建时间(止)")
    private String cjjssj;

    @ApiModelProperty("是否接口查询")
    private Integer sfjkcx;

    public String getCxdh() {
        return cxdh;
    }

    public void setCxdh(String cxdh) {
        this.cxdh = cxdh;
    }

    public String getCxmc() {
        return cxmc;
    }

    public void setCxmc(String cxmc) {
        this.cxmc = cxmc;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public String getBgr() {return bgr;}

    public void setBgr(String bgr) {this.bgr = bgr;}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCjqssj() {
        return cjqssj;
    }

    public void setCjqssj(String cjqssj) {
        this.cjqssj = cjqssj;
    }

    public String getCjjssj() {
        return cjjssj;
    }

    public void setCjjssj(String cjjssj) {
        this.cjjssj = cjjssj;
    }

    public Integer getSfjkcx() {
        return sfjkcx;
    }

    public void setSfjkcx(Integer sfjkcx) {
        this.sfjkcx = sfjkcx;
    }
}