package cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.jsfxx.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date  2021/8/5
 * @description 互联网请求缴费书信息接口参数
 */
public class JfsxxHlwParam {

    @ApiModelProperty(value = "收费信息id")
    private String sfxxid;

    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "财政分配的登陆用户编码")
    private String creater;

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getSfxxid() {
        return sfxxid;
    }

    public void setSfxxid(String sfxxid) {
        this.sfxxid = sfxxid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    @Override
    public String toString() {
        return "JfsxxHlwParam{" +
                "sfxxid='" + sfxxid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", creater='" + creater + '\'' +
                '}';
    }
}
